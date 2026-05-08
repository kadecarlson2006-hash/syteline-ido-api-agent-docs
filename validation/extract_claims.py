"""Extract concrete claims from a skill folder.

Looks for IDO names, (IDO, method) pairs, and SQL table names embedded in skill
text via REST URL patterns and filter expressions. The result is intentionally
a *lower bound* — we only extract claims we can pattern-match with confidence,
not everything the prose might be implying.
"""

from __future__ import annotations

import re
from dataclasses import dataclass, field
from pathlib import Path


_LOAD_RE = re.compile(r"/load/([A-Za-z][A-Za-z0-9_]+)")
_INVOKE_RE = re.compile(r"/invoke/([A-Za-z][A-Za-z0-9_]+)\?method=([A-Za-z][A-Za-z0-9_]+)")
_UPDATE_RE = re.compile(r"/update/([A-Za-z][A-Za-z0-9_]+)")
_COLLECTION_FILTER_RE = re.compile(r"CollectionName\s*=\s*N?'([A-Za-z][A-Za-z0-9_]+)'")
_TABLENAME_FILTER_RE = re.compile(r"tableName\s*=\s*N?'([A-Za-z][A-Za-z0-9_]+)'")

_PLACEHOLDER_NAMES = {
    "IDO", "TABLE", "COLLECTION", "ITEM", "METHOD",
    "SOMEIDO", "YOURIDO", "MYIDO", "EXAMPLE",
}


@dataclass
class Claims:
    idos: set[str] = field(default_factory=set)
    methods: set[tuple[str, str]] = field(default_factory=set)
    tables: set[str] = field(default_factory=set)
    write_idos: set[str] = field(default_factory=set)


def _is_placeholder(name: str) -> bool:
    upper = name.upper()
    if upper in _PLACEHOLDER_NAMES:
        return True
    if upper.startswith("YOUR") or upper.startswith("MY"):
        return True
    return False


def extract_claims(skill_dir: Path) -> Claims:
    claims = Claims()
    candidates = [skill_dir / "SKILL.md"]
    for sub in ("reference", "references"):
        sub_dir = skill_dir / sub
        if sub_dir.is_dir():
            candidates.extend(sub_dir.rglob("*"))
    for path in candidates:
        if not path.is_file():
            continue
        if path.suffix.lower() not in {".md", ".txt"}:
            continue
        try:
            text = path.read_text(encoding="utf-8", errors="replace")
        except OSError:
            continue
        for m in _LOAD_RE.finditer(text):
            name = m.group(1)
            if not _is_placeholder(name):
                claims.idos.add(name)
        for m in _INVOKE_RE.finditer(text):
            ido, method = m.group(1), m.group(2)
            if _is_placeholder(ido) or _is_placeholder(method):
                continue
            claims.idos.add(ido)
            claims.methods.add((ido, method))
        for m in _UPDATE_RE.finditer(text):
            name = m.group(1)
            if _is_placeholder(name):
                continue
            claims.idos.add(name)
            claims.write_idos.add(name)
        for m in _COLLECTION_FILTER_RE.finditer(text):
            name = m.group(1)
            if not _is_placeholder(name):
                claims.idos.add(name)
        for m in _TABLENAME_FILTER_RE.finditer(text):
            claims.tables.add(m.group(1))
    return claims
