#!/usr/bin/env python3
"""Offline CI: every skill folder must have a SKILL.md with valid frontmatter.

Required frontmatter keys: name, description.
Exits non-zero on any failure.
"""

from __future__ import annotations

import sys
from pathlib import Path


REQUIRED_KEYS = ("name", "description")


def parse_frontmatter(text: str) -> dict[str, str] | None:
    if not text.startswith("---"):
        return None
    lines = text.splitlines()
    try:
        end = lines.index("---", 1)
    except ValueError:
        return None
    out: dict[str, str] = {}
    for line in lines[1:end]:
        if ":" not in line or line.lstrip().startswith("#"):
            continue
        key, _, value = line.partition(":")
        key = key.strip()
        value = value.strip().strip('"').strip("'")
        if key and not key.startswith(("-", " ")):
            out[key] = value
    return out


def main() -> int:
    if len(sys.argv) != 2:
        print("usage: ci_check_skills.py <skills-dir>", file=sys.stderr)
        return 2
    skills_dir = Path(sys.argv[1])
    if not skills_dir.is_dir():
        print(f"error: {skills_dir} is not a directory", file=sys.stderr)
        return 2

    failures: list[str] = []
    checked = 0
    for skill_dir in sorted(skills_dir.iterdir()):
        if not skill_dir.is_dir():
            continue
        skill_md = skill_dir / "SKILL.md"
        if not skill_md.is_file():
            failures.append(f"{skill_dir.name}: missing SKILL.md")
            continue
        checked += 1
        text = skill_md.read_text(encoding="utf-8", errors="replace")
        fm = parse_frontmatter(text)
        if fm is None:
            failures.append(f"{skill_dir.name}: SKILL.md has no parseable --- frontmatter ---")
            continue
        for key in REQUIRED_KEYS:
            if not fm.get(key):
                failures.append(f"{skill_dir.name}: missing required frontmatter key '{key}'")

    print(f"checked {checked} skill(s)")
    if failures:
        print(f"\n{len(failures)} failure(s):")
        for f in failures:
            print(f"  - {f}")
        return 1
    print("OK")
    return 0


if __name__ == "__main__":
    sys.exit(main())
