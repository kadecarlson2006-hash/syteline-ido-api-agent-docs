#!/usr/bin/env python3
"""Offline CI: run the claim extractor over every skill, no pilot calls.

Catches regex/import/parsing breakage in the harness before a maintainer runs
the live validate_all.py against the pilot. Always exits 0 unless the extractor
itself throws — extracting "no claims" is a valid result.
"""

from __future__ import annotations

import sys
from pathlib import Path

sys.path.insert(0, str(Path(__file__).parent))

from extract_claims import extract_claims


def main() -> int:
    if len(sys.argv) != 2:
        print("usage: ci_extract_only.py <skills-dir>", file=sys.stderr)
        return 2
    skills_dir = Path(sys.argv[1])
    if not skills_dir.is_dir():
        print(f"error: {skills_dir} is not a directory", file=sys.stderr)
        return 2

    total_idos = total_methods = total_tables = 0
    skill_count = 0
    errors: list[str] = []

    for skill_dir in sorted(skills_dir.iterdir()):
        if not skill_dir.is_dir():
            continue
        if not (skill_dir / "SKILL.md").is_file():
            continue
        skill_count += 1
        try:
            claims = extract_claims(skill_dir)
        except Exception as exc:
            errors.append(f"{skill_dir.name}: {type(exc).__name__}: {exc}")
            continue
        total_idos += len(claims.idos)
        total_methods += len(claims.methods)
        total_tables += len(claims.tables)

    print(
        f"extracted from {skill_count} skill(s): "
        f"{total_idos} ido claim(s), {total_methods} method claim(s), {total_tables} table claim(s)"
    )
    if errors:
        print(f"\n{len(errors)} extractor failure(s):")
        for e in errors:
            print(f"  - {e}")
        return 1
    return 0


if __name__ == "__main__":
    sys.exit(main())
