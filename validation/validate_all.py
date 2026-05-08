#!/usr/bin/env python3
"""Orchestrate per-skill validation across the claude1..claude5 user pool.

Filters skills to Tier A+B (per skill-inventory.md) by default. Runs N=5
parallel processes, each pinned to one claude{N} user. Aggregates results
into MANIFEST.md.

Usage:
  validate_all.py --skills-dir ../skills
  validate_all.py --skills-dir ../skills --skill syteline-rest-api-playbook
  validate_all.py --skills-dir ../skills --tier A
"""

from __future__ import annotations

import argparse
import json
import os
import subprocess
import sys
from concurrent.futures import ProcessPoolExecutor, as_completed
from pathlib import Path
from typing import Any

sys.path.insert(0, str(Path(__file__).parent))


TIER_A = [
    "syteline-rest-api-playbook",
    "syteline-rest-api-v1",
    "syteline-rest-api-v2",
    "syteline-ido-request-interface",
    "syteline-ido-samples",
    "syteline-soap-api",
]
TIER_B = [
    "syteline-ido-api",
    "syteline-ido-definitions",
    "syteline-collections",
    "syteline-custom-ido",
    "syteline-extension-classes",
    "syteline-sql-schema",
    "syteline-critical-numbers",
    "syteline-dataviews",
    "syteline-admin-architecture",
    "syteline-admin-config",
    "syteline-admin-licensing",
    "syteline-admin-processes",
    "syteline-admin-replication",
    "syteline-admin-reports",
    "syteline-admin-transport",
    "syteline-customization",
]


def pick_skills(skills_dir: Path, tiers: list[str], single: str | None) -> list[Path]:
    if single:
        cand = skills_dir / single
        return [cand] if cand.is_dir() else []
    pool: list[str] = []
    if "A" in tiers:
        pool += TIER_A
    if "B" in tiers:
        pool += TIER_B
    return [skills_dir / name for name in pool if (skills_dir / name).is_dir()]


def run_one(
    skill_path: Path,
    claude_user: str,
    password: str,
    base_url: str,
    site: str,
    results_dir: Path,
) -> dict[str, Any]:
    out_path = results_dir / f"{skill_path.name}.json"
    env = os.environ.copy()
    env.update({
        "SYTELINE_BASE_URL": base_url,
        "DEFAULT_SITE": site,
        "SYTELINE_AGENT_USERNAME": claude_user,
        "SYTELINE_AGENT_PASSWORD": password,
    })
    cmd = [
        sys.executable,
        str(Path(__file__).parent / "validate_skill.py"),
        str(skill_path),
        "--out", str(out_path),
    ]
    completed = subprocess.run(cmd, env=env, capture_output=True, text=True)
    if completed.returncode != 0:
        return {
            "skill": skill_path.name,
            "claude_user": claude_user,
            "summary": {"ido": "0/0", "method": "0/0", "table": "0/0", "status": "ERROR"},
            "errors": [completed.stderr.strip() or completed.stdout.strip()],
        }
    return json.loads(out_path.read_text(encoding="utf-8"))


def write_manifest(path: Path, reports: list[dict[str, Any]]) -> None:
    reports = sorted(reports, key=lambda r: r.get("skill", ""))
    statuses: dict[str, int] = {}
    for r in reports:
        s = r["summary"]["status"]
        statuses[s] = statuses.get(s, 0) + 1
    lines = [
        "# Validation Manifest",
        "",
        f"Skills checked: **{len(reports)}**",
        "",
        "## Status counts",
        "",
        "| Status | Count |",
        "|---|---|",
    ]
    for status in sorted(statuses):
        lines.append(f"| {status} | {statuses[status]} |")
    lines += [
        "",
        "## Per skill",
        "",
        "| Skill | User | Status | IDOs | Methods | Tables | Errors |",
        "|---|---|---|---|---|---|---|",
    ]
    for r in reports:
        s = r.get("summary", {})
        lines.append(
            f"| {r.get('skill', '?')} | {r.get('claude_user', '?')} | "
            f"{s.get('status', '?')} | {s.get('ido', '?')} | {s.get('method', '?')} | "
            f"{s.get('table', '?')} | {len(r.get('errors', []))} |"
        )
    path.write_text("\n".join(lines) + "\n", encoding="utf-8")


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("--skills-dir", type=Path, required=True)
    parser.add_argument("--tier", default="A,B")
    parser.add_argument("--skill", default=None, help="validate just this one skill")
    parser.add_argument("--workers", type=int, default=5)
    parser.add_argument("--results-dir", type=Path, default=None)
    parser.add_argument("--manifest", type=Path, default=None)
    args = parser.parse_args()

    base_url = os.environ.get("SYTELINE_BASE_URL")
    site = os.environ.get("DEFAULT_SITE", "Pilot_Vortx")
    password = os.environ.get("SYTELINE_AGENT_PASSWORD")
    if not base_url or not password:
        print("error: SYTELINE_BASE_URL and SYTELINE_AGENT_PASSWORD must be set", file=sys.stderr)
        return 2

    tiers = [t.strip() for t in args.tier.split(",") if t.strip()]
    skills = pick_skills(args.skills_dir, tiers, args.skill)
    if not skills:
        print("no skills selected", file=sys.stderr)
        return 1

    results_dir = args.results_dir or (Path(__file__).parent / "results")
    results_dir.mkdir(parents=True, exist_ok=True)

    claude_users = [f"claude{i}" for i in range(1, args.workers + 1)]
    assignments = [(skills[i], claude_users[i % len(claude_users)]) for i in range(len(skills))]

    print(f"validating {len(skills)} skill(s) across {args.workers} worker(s)")
    reports: list[dict[str, Any]] = []
    with ProcessPoolExecutor(max_workers=args.workers) as pool:
        futures = {
            pool.submit(run_one, sp, user, password, base_url, site, results_dir): sp.name
            for sp, user in assignments
        }
        for fut in as_completed(futures):
            try:
                report = fut.result()
                reports.append(report)
                s = report["summary"]
                print(f"  {report.get('skill', '?')}: {s.get('status', '?')} "
                      f"(idos {s.get('ido', '?')}, methods {s.get('method', '?')}, tables {s.get('table', '?')})")
            except Exception as exc:
                print(f"  {futures[fut]}: ERROR {exc}")

    manifest_path = args.manifest or (Path(__file__).parent / "MANIFEST.md")
    write_manifest(manifest_path, reports)
    print(f"manifest written to {manifest_path}")
    return 0


if __name__ == "__main__":
    sys.exit(main())
