#!/usr/bin/env python3
"""Validate a single skill against the SL10 pilot (read-only).

Usage: validate_skill.py <skill-dir> [--out <path.json>]

Reads the skill's SKILL.md (and reference content), extracts concrete claims
about IDOs, methods, and tables, verifies each via meta-IDO queries against
the pilot, and writes a JSON report. Never calls /invoke or /update.
"""

from __future__ import annotations

import argparse
import json
import os
import sys
import time
from pathlib import Path
from typing import Any

sys.path.insert(0, str(Path(__file__).parent))

from extract_claims import extract_claims
from pilot_client import PilotClient, PilotConfig


def validate(skill_dir: Path, client: PilotClient) -> dict[str, Any]:
    started = time.time()
    claims = extract_claims(skill_dir)
    report: dict[str, Any] = {
        "skill": skill_dir.name,
        "claude_user": os.environ.get("SYTELINE_AGENT_USERNAME", "?"),
        "started_at": started,
        "claims": {
            "idos": [],
            "methods": [],
            "tables": [],
            "write_idos": sorted(claims.write_idos),
        },
        "errors": [],
    }
    for name in sorted(claims.idos):
        try:
            ok = client.ido_exists(name)
        except Exception as exc:
            report["errors"].append(f"ido({name}): {exc}")
            ok = False
        report["claims"]["idos"].append({"name": name, "verified": ok})
    for ido, method in sorted(claims.methods):
        meta: dict[str, Any] | None
        param_count: int | None
        try:
            meta = client.method_exists(ido, method)
            param_count = client.method_param_count(ido, method) if meta else None
        except Exception as exc:
            report["errors"].append(f"method({ido}.{method}): {exc}")
            meta = None
            param_count = None
        report["claims"]["methods"].append({
            "ido": ido,
            "method": method,
            "verified": bool(meta),
            "method_type": meta.get("MethodType") if meta else None,
            "param_count": param_count,
        })
    for name in sorted(claims.tables):
        try:
            ok = client.table_exists(name)
        except Exception as exc:
            report["errors"].append(f"table({name}): {exc}")
            ok = False
        report["claims"]["tables"].append({"name": name, "verified": ok})

    finished = time.time()
    report["finished_at"] = finished
    report["duration_s"] = round(finished - started, 2)
    report["summary"] = _summarize(report["claims"])
    return report


def _summarize(claims: dict[str, Any]) -> dict[str, str]:
    def count(items: list[dict[str, Any]]) -> int:
        return sum(1 for it in items if it.get("verified"))

    ido_total = len(claims["idos"])
    method_total = len(claims["methods"])
    table_total = len(claims["tables"])
    ido_ok = count(claims["idos"])
    method_ok = count(claims["methods"])
    table_ok = count(claims["tables"])
    total = ido_total + method_total + table_total
    ok = ido_ok + method_ok + table_ok
    if total == 0:
        status = "no-claims"
    elif ok == total:
        status = "PASS"
    elif ok == 0:
        status = "FAIL"
    else:
        status = "PARTIAL"
    return {
        "ido": f"{ido_ok}/{ido_total}",
        "method": f"{method_ok}/{method_total}",
        "table": f"{table_ok}/{table_total}",
        "status": status,
    }


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("skill_dir", type=Path)
    parser.add_argument("--out", type=Path, default=None)
    args = parser.parse_args()

    if not args.skill_dir.is_dir():
        print(f"error: {args.skill_dir} is not a directory", file=sys.stderr)
        return 2

    try:
        client = PilotClient(PilotConfig.from_env())
    except KeyError as exc:
        print(f"error: missing env var {exc}", file=sys.stderr)
        return 2

    report = validate(args.skill_dir, client)
    out_path = args.out or (Path(__file__).parent / "results" / f"{args.skill_dir.name}.json")
    out_path.parent.mkdir(parents=True, exist_ok=True)
    out_path.write_text(json.dumps(report, indent=2), encoding="utf-8")
    s = report["summary"]
    print(f"{report['skill']}: {s['status']} (idos {s['ido']}, methods {s['method']}, tables {s['table']})")
    return 0


if __name__ == "__main__":
    sys.exit(main())
