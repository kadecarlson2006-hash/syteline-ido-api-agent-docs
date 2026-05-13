const state = {
  collections: [],
  collectionFilter: '',
  accessFilter: '',
  showReplaced: false,
  favorites: loadFavorites(),
  selected: null,
  overview: null,
  extenders: [],
  tables: [],
  properties: [],
  methods: [],
  tableFilters: {},
  tablePage: 0,
  tableBindingFilter: null,  // { alias, name } when a table row is active
  propFilters: {},
  propClass: '',
  methodFilters: {},
};

const $ = (sel) => document.querySelector(sel);
const $$ = (sel) => Array.from(document.querySelectorAll(sel));

// ---------- Favorites (localStorage) ----------
function loadFavorites() {
  try { return new Set(JSON.parse(localStorage.getItem('ido-favorites') || '[]')); }
  catch { return new Set(); }
}
function saveFavorites() {
  localStorage.setItem('ido-favorites', JSON.stringify([...state.favorites]));
}

async function fetchJSON(url) {
  const res = await fetch(url);
  if (!res.ok) {
    let msg = `HTTP ${res.status}`;
    try {
      const body = await res.json();
      if (body.error) msg = body.error;
    } catch {}
    throw new Error(msg);
  }
  return res.json();
}

function toast(msg) {
  const el = $('#toast');
  el.textContent = msg;
  el.classList.remove('hidden');
  clearTimeout(toast._t);
  toast._t = setTimeout(() => el.classList.add('hidden'), 4000);
}

function debounce(fn, ms) {
  let t;
  return (...args) => {
    clearTimeout(t);
    t = setTimeout(() => fn(...args), ms);
  };
}

function escapeHtml(str) {
  if (str === null || str === undefined) return '';
  return String(str)
    .replace(/&/g, '&amp;')
    .replace(/</g, '&lt;')
    .replace(/>/g, '&gt;')
    .replace(/"/g, '&quot;');
}

// ---------- ENV / boot ----------
async function loadEnv() {
  try {
    const env = await fetchJSON('/api/env');
    // Decorate the env pill with bracketing dots to fit the instrument-strip
    // styling. Hover to see the full base URL.
    $('#env-pill').textContent = `· ${env.site} ·`;
    $('#env-pill').title = env.baseUrl;
  } catch (err) {
    $('#env-pill').textContent = '· ERR ·';
    toast(err.message);
  }
}

// ---------- Collections ----------
async function loadCollections() {
  const params = new URLSearchParams();
  if (state.collectionFilter) params.set('q', state.collectionFilter);
  if (state.accessFilter && state.accessFilter !== '__favorites__') params.set('accessAs', state.accessFilter);
  try {
    const { items } = await fetchJSON(`/api/collections?${params}`);
    state.collections = items;
    renderCollections();
  } catch (err) {
    toast(`Loading collections failed: ${err.message}`);
  }
}

function renderCollections() {
  const list = $('#collections-list');
  const all = state.collections;
  const replaced = all.filter((c) => String(c.ReplaceFlag) === '1');
  let visible = state.showReplaced ? all : all.filter((c) => String(c.ReplaceFlag) !== '1');
  if (state.accessFilter === '__favorites__') {
    visible = visible.filter((c) => state.favorites.has(c.CollectionName));
  }

  $('#collections-count').textContent = `${visible.length}`;
  list.innerHTML = visible
    .map((c) => {
      const access = c.AccessAs || 'Custom';
      const sel = state.selected === c.CollectionName ? 'selected' : '';
      const isFav = state.favorites.has(c.CollectionName);
      return `<li class="${sel}" data-name="${escapeHtml(c.CollectionName)}">
        <div class="list-item-main">
          <div class="font-medium">${escapeHtml(c.CollectionName)}</div>
          <div class="text-xs text-slate-500">${escapeHtml(access)}</div>
        </div>
        <button class="fav-btn${isFav ? ' is-fav' : ''}" data-fav="${escapeHtml(c.CollectionName)}" title="${isFav ? 'Remove from favorites' : 'Add to favorites'}">★</button>
      </li>`;
    })
    .join('');
  list.querySelectorAll('li').forEach((li) => {
    li.addEventListener('click', () => selectIdo(li.dataset.name));
  });
  list.querySelectorAll('.fav-btn').forEach((btn) => {
    btn.addEventListener('click', (e) => {
      e.stopPropagation();
      const name = btn.dataset.fav;
      if (state.favorites.has(name)) state.favorites.delete(name);
      else state.favorites.add(name);
      saveFavorites();
      renderCollections();
    });
  });

  const footer = $('#collections-footer');
  if (footer) {
    if (!state.showReplaced && replaced.length > 0) {
      footer.innerHTML = `<span>${replaced.length} replaced hidden · <button id="show-replaced-btn">show</button></span>`;
      footer.querySelector('#show-replaced-btn').addEventListener('click', () => {
        state.showReplaced = true;
        renderCollections();
      });
    } else if (state.showReplaced && replaced.length > 0) {
      footer.innerHTML = `<span><button id="hide-replaced-btn">hide replaced</button></span>`;
      footer.querySelector('#hide-replaced-btn').addEventListener('click', () => {
        state.showReplaced = false;
        renderCollections();
      });
    } else {
      footer.innerHTML = '';
    }
  }
}

// ---------- Selected IDO ----------
async function selectIdo(name) {
  state.selected = name;
  state.tableFilters = {};
  state.tablePage = 0;
  state.tableBindingFilter = null;
  state.propFilters = {};
  state.propClass = '';
  state.methodFilters = {};
  state.extenders = [];
  $$('.prop-chip').forEach((b) => b.classList.toggle('chip-on', b.dataset.class === ''));
  renderCollections();
  $('#detail-empty').classList.add('hidden');
  $('#detail-content').classList.remove('hidden');
  $('#ov-name').textContent = name;
  $('#ov-sub').textContent = 'Loading…';
  $('#ov-extended').textContent = '';
  $('#ov-primary-table').textContent = '';
  $('#ov-primary-table').classList.add('hidden');
  $('#tables-body').innerHTML = '<tr><td colspan="5" class="muted px-3 py-3">Loading…</td></tr>';
  $('#props-body').innerHTML = '<tr><td colspan="6" class="muted px-3 py-3">Loading…</td></tr>';
  $('#methods-body').innerHTML = '<tr><td colspan="3" class="muted px-3 py-3">Loading…</td></tr>';

  const [overviewR, tablesR, propsR, methodsR] = await Promise.allSettled([
    fetchJSON(`/api/ido/${encodeURIComponent(name)}/overview`),
    fetchJSON(`/api/ido/${encodeURIComponent(name)}/tables`),
    fetchJSON(`/api/ido/${encodeURIComponent(name)}/properties`),
    fetchJSON(`/api/ido/${encodeURIComponent(name)}/methods`),
  ]);

  if (state.selected !== name) return;

  if (overviewR.status === 'fulfilled') {
    state.overview = overviewR.value.item;
    state.extenders = overviewR.value.extenders || [];
    renderOverview();
  } else {
    $('#ov-sub').textContent = `Overview error: ${overviewR.reason.message}`;
  }
  if (tablesR.status === 'fulfilled') {
    state.tables = tablesR.value.items;
    renderTables();
  } else {
    $('#tables-body').innerHTML = `<tr><td colspan="5" class="muted px-3 py-3">Error: ${escapeHtml(tablesR.reason.message)}</td></tr>`;
  }
  if (propsR.status === 'fulfilled') {
    state.properties = propsR.value.items;
    renderProps();
  } else {
    $('#props-body').innerHTML = `<tr><td colspan="6" class="muted px-3 py-3">Error: ${escapeHtml(propsR.reason.message)}</td></tr>`;
  }
  if (methodsR.status === 'fulfilled') {
    state.methods = methodsR.value.items;
    renderMethods();
  } else {
    $('#methods-body').innerHTML = `<tr><td colspan="3" class="muted px-3 py-3">Error: ${escapeHtml(methodsR.reason.message)}</td></tr>`;
  }

  // Load chain data: IDOs that extend+replace this one add their schema on top
  const replacers = state.extenders.filter((e) => String(e.ReplaceFlag) === '1');
  if (replacers.length === 0) return;

  const chainLoads = replacers.map((ext) => {
    const n = ext.CollectionName;
    return Promise.allSettled([
      fetchJSON(`/api/ido/${encodeURIComponent(n)}/tables`),
      fetchJSON(`/api/ido/${encodeURIComponent(n)}/properties`),
      fetchJSON(`/api/ido/${encodeURIComponent(n)}/methods`),
    ]).then(([tR, pR, mR]) => ({
      name: n,
      tables:     tR.status === 'fulfilled' ? tR.value.items.map((i) => ({ ...i, _sourceIdo: n })) : [],
      properties: pR.status === 'fulfilled' ? pR.value.items.map((i) => ({ ...i, _sourceIdo: n })) : [],
      methods:    mR.status === 'fulfilled' ? mR.value.items.map((i) => ({ ...i, _sourceIdo: n })) : [],
    }));
  });

  const chainResults = await Promise.all(chainLoads);
  if (state.selected !== name) return;

  for (const chain of chainResults) {
    state.tables     = [...state.tables,     ...chain.tables];
    state.properties = [...state.properties, ...chain.properties];
    state.methods    = [...state.methods,    ...chain.methods];
  }
  renderTables();
  renderProps();
  renderMethods();
}

function renderOverview() {
  const ov = state.overview;
  if (!ov) {
    $('#ov-sub').textContent = 'Not found';
    $('#ov-body').classList.add('hidden');
    return;
  }

  const access = ov.AccessAs ? `Core (${ov.AccessAs})` : 'Custom';
  $('#ov-sub').textContent = access;

  const extBadges = [];
  if (String(ov.DevelopmentFlag) === '1') extBadges.push(`<span class="badge badge-warn">Dev</span>`);
  if (String(ov.DerHasProdVersion) === '1') extBadges.push(`<span class="badge badge-primary">Prod version</span>`);
  $('#ov-extended').innerHTML = extBadges.join(' ');

  const body = $('#ov-body');
  const parts = [];

  if (ov.CollectionDesc) {
    parts.push(`<div class="ov-desc">${escapeHtml(ov.CollectionDesc)}</div>`);
  }

  function field(label, valueHtml) {
    return `<div class="ov-field"><span class="ov-field-label">${label}</span><span class="ov-field-value">${valueHtml}</span></div>`;
  }
  function fmt(val) { return val ? escapeHtml(String(val)) : '<span class="muted">—</span>'; }
  function fmtDate(val) {
    if (!val) return '<span class="muted">—</span>';
    const d = new Date(val);
    return isNaN(d) ? escapeHtml(val) : escapeHtml(d.toLocaleDateString('en-US', { year: 'numeric', month: 'short', day: 'numeric' }));
  }

  const rev = ov.RevisionNo !== undefined && ov.RevisionNo !== null && ov.RevisionNo !== ''
    ? `<code>${escapeHtml(String(ov.RevisionNo))}</code>`
    : '<span class="muted">—</span>';
  parts.push(field('Revision', `${rev} &nbsp;·&nbsp; ${fmtDate(ov.RevisionDate)}`));
  parts.push(field('Last updated', `${fmt(ov.UpdatedBy)} &nbsp;·&nbsp; ${fmtDate(ov.RecordDate)}`));
  if (ov.ExtClassName) parts.push(field('Extension class', `<code>${escapeHtml(ov.ExtClassName)}</code>`));

  // Inheritance chain — build a ladder showing parent (Extends) and children (extenders)
  const replacers = state.extenders.filter((e) => String(e.ReplaceFlag) === '1');
  const hasChain = ov.Extends || replacers.length > 0;
  if (hasChain) {
    const nodes = [];

    if (ov.Extends) {
      // This IDO extends a parent — show parent above
      const parentAccess = ''; // we don't have parent's AccessAs here
      nodes.push(`<div class="chain-node">
        <button class="chain-name ov-ido-link" data-ido="${escapeHtml(ov.Extends)}">${escapeHtml(ov.Extends)}</button>
        <span class="chain-tag">parent</span>
      </div>
      <div class="chain-connector">└─►</div>
      <div class="chain-node chain-here">
        <span class="chain-name">${escapeHtml(ov.CollectionName)}</span>
        <span class="chain-tag">${String(ov.ReplaceFlag) === '1' ? 'extends &amp; replaces · here' : 'extends · here'}</span>
      </div>`);
    } else {
      // This is the base IDO — show it first
      nodes.push(`<div class="chain-node chain-here">
        <span class="chain-name">${escapeHtml(ov.CollectionName)}</span>
        <span class="chain-tag">base · here</span>
      </div>`);
      for (const ext of replacers) {
        nodes.push(`<div class="chain-connector">└─►</div>
        <div class="chain-node">
          <button class="chain-name ov-ido-link" data-ido="${escapeHtml(ext.CollectionName)}">${escapeHtml(ext.CollectionName)}</button>
          <span class="chain-tag">${ext.AccessAs || 'Custom'} · extends &amp; replaces</span>
        </div>`);
      }
    }

    parts.push(`<div class="chain-section">
      <span class="ov-field-label">Inheritance chain</span>
      <div class="chain-ladder">${nodes.join('')}</div>
    </div>`);
  }

  body.innerHTML = parts.join('');
  body.classList.toggle('hidden', parts.length === 0);

  body.querySelectorAll('.ov-ido-link').forEach((btn) =>
    btn.addEventListener('click', () => selectIdo(btn.dataset.ido)),
  );
}

// ---------- Filtering helpers ----------
function matchesFilter(value, filter) {
  if (!filter) return true;
  if (value === null || value === undefined) return false;
  return String(value).toLowerCase().includes(filter.toLowerCase());
}

// ---------- Tables ----------
const TABLE_PAGE_SIZE = 10;

function tableSort(r) {
  if (r._sourceIdo) return 0;
  if (String(r.TableType) === '3') return 1;
  return 2;
}

function renderTables() {
  const allRows = state.tables
    .filter((r) => Object.entries(state.tableFilters).every(([f, v]) => matchesFilter(r[f], v)))
    .sort((a, b) => tableSort(a) - tableSort(b));

  const totalPages = Math.max(1, Math.ceil(allRows.length / TABLE_PAGE_SIZE));
  if (state.tablePage >= totalPages) state.tablePage = totalPages - 1;

  const rows = allRows.slice(state.tablePage * TABLE_PAGE_SIZE, (state.tablePage + 1) * TABLE_PAGE_SIZE);

  $('#tables-count').textContent = `${allRows.length} / ${state.tables.length}`;

  $('#tables-body').innerHTML = rows
    .map((r) => {
      const t = String(r.TableType ?? '');
      const badge =
        t === '3' ? '<span class="badge badge-primary">Primary</span>'
        : t === '0' ? '<span class="badge badge-secondary">Secondary</span>'
        : `<span class="badge">${escapeHtml(t)}</span>`;
      const sourceBadge = r._sourceIdo
        ? ` <span class="badge badge-chain" title="From ${escapeHtml(r._sourceIdo)}">${escapeHtml(r._sourceIdo)}</span>`
        : '';
      const isActive = state.tableBindingFilter && state.tableBindingFilter.alias === r.TableAlias;
      return `<tr class="row-clickable${isActive ? ' table-row-active' : ''}" data-alias="${escapeHtml(r.TableAlias)}" data-tablename="${escapeHtml(r.TableName)}">
        <td class="font-medium">${escapeHtml(r.TableName)}${sourceBadge}</td>
        <td>${escapeHtml(r.TableAlias)}</td>
        <td>${badge}</td>
        <td>${escapeHtml(r.JoinType)}</td>
        <td class="text-xs muted">${escapeHtml(r.JoinText)}</td>
      </tr>`;
    })
    .join('');

  $('#tables-body').querySelectorAll('tr.row-clickable').forEach((tr) => {
    tr.addEventListener('click', () => {
      const { alias, tablename } = tr.dataset;
      state.tableBindingFilter =
        state.tableBindingFilter && state.tableBindingFilter.alias === alias
          ? null
          : { alias, name: tablename };
      renderTables();
      renderProps();
    });
  });

  // Pagination controls
  const pag = $('#tables-pagination');
  if (pag) {
    if (totalPages <= 1) {
      pag.innerHTML = '';
    } else {
      const start = state.tablePage * TABLE_PAGE_SIZE + 1;
      const end = Math.min((state.tablePage + 1) * TABLE_PAGE_SIZE, allRows.length);
      pag.innerHTML = `
        <button class="pag-btn" id="tprev" ${state.tablePage === 0 ? 'disabled' : ''}>←</button>
        <span class="pag-info">${start}–${end} of ${allRows.length}</span>
        <button class="pag-btn" id="tnext" ${state.tablePage >= totalPages - 1 ? 'disabled' : ''}>→</button>`;
      pag.querySelector('#tprev').addEventListener('click', () => { state.tablePage--; renderTables(); });
      pag.querySelector('#tnext').addEventListener('click', () => { state.tablePage++; renderTables(); });
    }
  }

  // Primary base table in the overview header
  const primary = state.tables.find((t) => String(t.TableType) === '3' && !t._sourceIdo);
  const el = $('#ov-primary-table');
  if (el && primary) {
    el.textContent = `${primary.TableName} · ${primary.TableAlias}`;
    el.classList.remove('hidden');
  }
}

// ---------- Properties ----------
// Property classification — derived from the IDO metadata, mirrors how Syteline
// itself treats these properties at runtime:
//   - Subcollection: has SubCollectionName set (nested IDO reference)
//   - Derived:       no ColumnName binding but has a PropertyValue SQL expression
//                    (also PropertyType === '1' in some cases)
//   - Bound:         maps directly to a column (ColumnName set)
function classifyProperty(row) {
  if (row.SubCollectionName) return 'sub';
  if (!row.ColumnName && (row.PropertyValue || String(row.PropertyType) === '1')) return 'derived';
  return 'bound';
}

// Non-sargable patterns in a derived expression. Filters on properties whose
// PropertyValue matches any of these can't use an index — SQL Server must
// evaluate the expression per row. See SL10HD-429 for the canonical example
// (SLItemJobRevisions.Job = COALESCE(JobJob, ItemrevJob, ItwhseJob)).
const NON_SARGABLE_PATTERN = /\b(COALESCE|CASE\b|ISNULL\s*\(|NULLIF\s*\(|CONVERT\s*\(|CAST\s*\(|dbo\.\w+\s*\()/i;

function isNonSargable(row) {
  if (!row.PropertyValue) return false;
  return NON_SARGABLE_PATTERN.test(row.PropertyValue);
}

function propClassMatches(row) {
  const isRO = row.IsReadOnly === '1' || row.IsReadOnly === 1;
  const cls = classifyProperty(row);
  switch (state.propClass) {
    case 'writable':
      return cls === 'bound' && !isRO;
    case 'readonly':
      return isRO;
    case 'derived':
      return cls === 'derived';
    case 'sub':
      return cls === 'sub';
    case 'key':
      return Number(row.KeySequence) > 0;
    case 'nonsargable':
      return isNonSargable(row);
    default:
      return true;
  }
}

// Synthetic field used by the column/expression filter input — searches both
// the underlying column name and the derived SQL expression in one box.
function columnOrExpression(row) {
  return [row.ColumnName, row.PropertyValue].filter(Boolean).join(' ');
}

function truncate(str, max = 70) {
  if (!str) return '';
  const s = String(str);
  return s.length > max ? `${s.slice(0, max - 1)}…` : s;
}

function renderProps() {
  const bf = state.tableBindingFilter;

  // Update the binding-filter indicator above the props table
  const indicator = $('#prop-binding-indicator');
  if (indicator) {
    if (bf) {
      indicator.innerHTML = `<span>showing properties bound to</span> <strong>${escapeHtml(bf.alias)}</strong> <button id="clear-binding" title="Show all properties">✕</button>`;
      indicator.classList.remove('hidden');
      indicator.querySelector('#clear-binding').addEventListener('click', () => {
        state.tableBindingFilter = null;
        renderTables();
        renderProps();
      });
    } else {
      indicator.innerHTML = '';
      indicator.classList.add('hidden');
    }
  }

  const rows = state.properties
    .filter((r) => {
      if (!propClassMatches(r)) return false;
      if (bf && r.ColumnTableAlias !== bf.alias) return false;
      return Object.entries(state.propFilters).every(([f, v]) => {
        if (!v) return true;
        const target = f === '_columnOrExpression' ? columnOrExpression(r) : r[f];
        return matchesFilter(target, v);
      });
    })
    .sort((a, b) => (a._sourceIdo ? 0 : 1) - (b._sourceIdo ? 0 : 1));
  $('#props-count').textContent = `${rows.length} / ${state.properties.length}`;
  $('#props-body').innerHTML = rows
    .map((r) => {
      const cls = classifyProperty(r);
      const clsLabel = cls === 'derived' ? 'Derived' : cls === 'sub' ? 'Subcollection' : 'Bound';
      const clsBadgeClass = cls === 'derived' ? 'badge-derived' : cls === 'sub' ? 'badge-sub' : '';
      const isRO = r.IsReadOnly === '1' || r.IsReadOnly === 1;
      const isRequired = r.IsRequired === '1' || r.IsRequired === 1;
      const keySeq = Number(r.KeySequence) || 0;
      const flags = [
        keySeq > 0 ? `<span class="badge badge-key" title="KeySequence=${keySeq}">K${keySeq}</span>` : '',
        isRequired ? '<span class="badge badge-required" title="Required">R</span>' : '',
        isRO ? '<span class="badge" title="Read-only">RO</span>' : '',
      ].filter(Boolean).join(' ');
      const sargWarn = isNonSargable(r)
        ? `<span class="warn-icon" title="Expression contains a non-sargable construct (COALESCE/CASE/ISNULL/UDF). Filters on this property can't use indexes.">⚠️</span>`
        : '';
      // Show the column for bound props; show the (truncated) expression for derived.
      const columnCell = r.ColumnName
        ? escapeHtml(r.ColumnName)
        : r.PropertyValue
          ? `<code class="text-xs muted" title="${escapeHtml(r.PropertyValue)}">${escapeHtml(truncate(r.PropertyValue))}</code>`
          : '';
      const sourceBadge = r._sourceIdo
        ? ` <span class="badge badge-chain" title="From ${escapeHtml(r._sourceIdo)}">${escapeHtml(r._sourceIdo)}</span>`
        : '';
      const domainBadge = r.DomainIDOName
        ? ` <span class="badge badge-domain" title="Lookup: ${escapeHtml(r.DomainIDOName)}.${escapeHtml(r.DomainProperty || '?')}">▾ ${escapeHtml(r.DomainIDOName)}</span>`
        : '';
      return `<tr class="row-clickable" data-prop="${escapeHtml(r.PropertyName)}">
        <td class="font-medium">${sargWarn}${escapeHtml(r.PropertyName)}${sourceBadge}${domainBadge}</td>
        <td>${escapeHtml(r.DataType)}</td>
        <td><span class="badge ${clsBadgeClass}">${escapeHtml(clsLabel)}</span></td>
        <td>${columnCell}</td>
        <td>${escapeHtml(r.ColumnTableAlias)}</td>
        <td>${flags}</td>
      </tr>`;
    })
    .join('');
  $('#props-body')
    .querySelectorAll('tr.row-clickable')
    .forEach((tr) => tr.addEventListener('click', () => openPropertyDrawer(tr.dataset.prop)));
}

// ---------- Property detail drawer ----------
function openPropertyDrawer(propName) {
  const row = state.properties.find((r) => r.PropertyName === propName);
  if (!row) return;
  const drawer = $('#prop-drawer');
  drawer.classList.add('open');
  $('#prop-drawer-title').textContent = propName;
  $('#prop-drawer-sub').textContent = state.selected;

  const cls = classifyProperty(row);
  const isRO = row.IsReadOnly === '1' || row.IsReadOnly === 1;
  const isRequired = row.IsRequired === '1' || row.IsRequired === 1;
  const keySeq = Number(row.KeySequence) || 0;

  // For derived expressions, identify other property names referenced in the
  // expression (PascalCase identifiers that exist as siblings on this IDO).
  // Makes drilling into the dependency chain one click.
  const referenced = [];
  if (row.PropertyValue) {
    const propNames = new Set(state.properties.map((p) => p.PropertyName));
    const seen = new Set();
    const re = /\b[A-Z][A-Za-z0-9_]*\b/g;
    let match;
    while ((match = re.exec(row.PropertyValue)) !== null) {
      const token = match[0];
      if (token === propName) continue;
      if (propNames.has(token) && !seen.has(token)) {
        seen.add(token);
        referenced.push(token);
      }
    }
  }

  const sections = [];

  // Description (if set)
  if (row.PropertyDesc) {
    sections.push(`<p class="drawer-prop-desc">${escapeHtml(row.PropertyDesc)}</p>`);
  }

  // Class + flags summary
  const flagBadges = [
    `<span class="badge ${cls === 'derived' ? 'badge-derived' : cls === 'sub' ? 'badge-sub' : ''}">${cls === 'derived' ? 'Derived' : cls === 'sub' ? 'Subcollection' : 'Bound'}</span>`,
    keySeq > 0 ? `<span class="badge badge-key">Key #${keySeq}</span>` : '',
    isRequired ? '<span class="badge badge-required">Required</span>' : '',
    isRO ? '<span class="badge">Read-only</span>' : '',
    isNonSargable(row) ? '<span class="badge badge-warn">⚠️ Non-sargable expression</span>' : '',
  ].filter(Boolean).join(' ');
  sections.push(`<div class="flex flex-wrap gap-1">${flagBadges}</div>`);

  // If non-sargable, surface the why immediately
  if (isNonSargable(row)) {
    sections.push(`<div class="rounded border border-amber-200 bg-amber-50 px-3 py-2 text-xs text-amber-900">
      <strong>Performance note:</strong> This property's expression contains a construct that prevents index seeks
      (COALESCE / CASE / ISNULL / NULLIF / scalar UDF). Any filter using this property forces SQL Server
      to evaluate the expression for every row in the base table.
    </div>`);
  }

  // Expression / Column section
  if (row.PropertyValue) {
    sections.push(`<div>
      <div class="text-xs muted mb-1">PropertyValue (derived SQL expression)</div>
      <pre class="text-xs bg-slate-50 border rounded p-2 overflow-auto whitespace-pre-wrap">${escapeHtml(row.PropertyValue)}</pre>
    </div>`);
  }
  if (row.ColumnName) {
    sections.push(`<div>
      <div class="text-xs muted mb-1">Bound column</div>
      <code class="text-xs">${escapeHtml(row.ColumnTableAlias || row.ColumnTableName || '?')}.${escapeHtml(row.ColumnName)}</code>
    </div>`);
  }

  // Referenced sibling properties — clickable
  if (referenced.length) {
    const chips = referenced
      .map(
        (n) =>
          `<button class="prop-ref text-xs border rounded px-2 py-0.5 bg-slate-50 hover:bg-slate-100" data-prop="${escapeHtml(n)}">${escapeHtml(n)}</button>`,
      )
      .join(' ');
    sections.push(`<div>
      <div class="text-xs muted mb-1">References other properties on this IDO</div>
      <div class="flex flex-wrap gap-1">${chips}</div>
    </div>`);
  }

  // Subcollection target
  if (row.SubCollectionName) {
    sections.push(`<div>
      <div class="text-xs muted mb-1">Subcollection</div>
      <button class="ido-link text-xs border rounded px-2 py-0.5 bg-slate-50 hover:bg-slate-100" data-ido="${escapeHtml(row.SubCollectionName)}">${escapeHtml(row.SubCollectionName)} →</button>
    </div>`);
  }

  // Domain (dropdown / lookup source)
  if (row.DomainIDOName) {
    const listProps = row.DomainListProperties
      ? row.DomainListProperties.split(',').map((p) => `<code>${escapeHtml(p.trim())}</code>`).join(' ')
      : '<span class="muted">—</span>';
    sections.push(`<div class="domain-card">
      <div class="domain-card-label">▾ Domain · Lookup source</div>
      <table class="domain-table">
        <tr>
          <td>IDO</td>
          <td><button class="ido-link" data-ido="${escapeHtml(row.DomainIDOName)}">${escapeHtml(row.DomainIDOName)} →</button></td>
        </tr>
        <tr>
          <td>Property</td>
          <td>${row.DomainProperty ? `<code>${escapeHtml(row.DomainProperty)}</code>` : '<span class="muted">—</span>'}</td>
        </tr>
        <tr>
          <td>List props</td>
          <td>${listProps}</td>
        </tr>
      </table>
    </div>`);
  }

  // Raw metadata grid (anything else useful)
  const meta = [
    ['DataType', row.DataType],
    ['DataLength', row.DataLength],
    ['PropertyClass', row.PropertyClass],
    ['PropertyType', row.PropertyType],
    ['Sequence', row.Sequence],
    ['DefaultValue', row.DefaultValue],
    ['Validators', row.Validators],
    ['PropertyValueArguments', row.PropertyValueArguments],
    ['PseudoKeyFlag', row.PseudoKeyFlag],
  ].filter(([, v]) => v !== undefined && v !== null && v !== '');
  if (meta.length) {
    sections.push(`<div>
      <div class="text-xs muted mb-1">Metadata</div>
      <table class="text-xs border w-full">
        <tbody>
          ${meta
            .map(
              ([k, v]) =>
                `<tr><td class="px-2 py-1 muted w-40">${escapeHtml(k)}</td><td class="px-2 py-1"><code>${escapeHtml(v)}</code></td></tr>`,
            )
            .join('')}
        </tbody>
      </table>
    </div>`);
  }

  $('#prop-drawer-body').innerHTML = sections.join('');

  // Wire up clickable references inside the drawer
  $('#prop-drawer-body')
    .querySelectorAll('.prop-ref')
    .forEach((btn) =>
      btn.addEventListener('click', () => openPropertyDrawer(btn.dataset.prop)),
    );
  $('#prop-drawer-body')
    .querySelectorAll('.ido-link')
    .forEach((btn) =>
      btn.addEventListener('click', () => {
        $('#prop-drawer').classList.remove('open');
        selectIdo(btn.dataset.ido);
      }),
    );
}

// ---------- Methods ----------
function renderMethods() {
  const rows = state.methods
    .filter((r) => Object.entries(state.methodFilters).every(([f, v]) => matchesFilter(r[f], v)))
    .sort((a, b) => (a._sourceIdo ? 0 : 1) - (b._sourceIdo ? 0 : 1));
  $('#methods-count').textContent = `${rows.length} / ${state.methods.length}`;
  $('#methods-body').innerHTML = rows
    .map((r) => {
      const t = String(r.MethodType ?? '');
      const label =
        t === '2' ? 'Stored Proc'
        : t === '3' ? 'Extension'
        : t === '0' ? 'System'
        : t;
      const sourceBadge = r._sourceIdo
        ? ` <span class="badge badge-chain" title="From ${escapeHtml(r._sourceIdo)}">${escapeHtml(r._sourceIdo)}</span>`
        : '';
      return `<tr class="row-clickable" data-method="${escapeHtml(r.MethodName)}">
        <td class="font-medium">${escapeHtml(r.MethodName)}${sourceBadge}</td>
        <td><span class="badge">${escapeHtml(label)}</span></td>
        <td class="text-xs muted">View params →</td>
      </tr>`;
    })
    .join('');
  $('#methods-body')
    .querySelectorAll('tr.row-clickable')
    .forEach((tr) => tr.addEventListener('click', () => openMethodDrawer(tr.dataset.method)));
}

// ---------- Method drawer ----------
async function openMethodDrawer(method) {
  const drawer = $('#drawer');
  drawer.classList.add('open');
  $('#drawer-title').textContent = method;
  $('#drawer-sub').textContent = state.selected;
  const wrap = $('#drawer-body-wrap');
  wrap.innerHTML = '<div class="method-section-loading">Loading…</div>';

  const base = `/api/ido/${encodeURIComponent(state.selected)}/methods/${encodeURIComponent(method)}`;
  const paramsR = await fetchJSON(`${base}/parameters`).then((v) => ({ status: 'fulfilled', value: v })).catch((e) => ({ status: 'rejected', reason: e }));
  const resultSetsR = await fetchJSON(`${base}/resultsets`).then((v) => ({ status: 'fulfilled', value: v })).catch((e) => ({ status: 'rejected', reason: e }));

  const sections = [];

  // Parameters section
  const params = paramsR.status === 'fulfilled' ? paramsR.value.items : [];
  const paramError = paramsR.status === 'rejected' ? paramsR.reason.message : null;
  const paramRows = paramError
    ? `<tr><td colspan="5" class="muted px-2 py-3">Error: ${escapeHtml(paramError)}</td></tr>`
    : params.length === 0
      ? `<tr><td colspan="5" class="muted px-2 py-3">No parameters</td></tr>`
      : params.map((p) => {
          const inFlag = p.InputFlag === '1' || p.InputFlag === 1;
          const outFlag = p.OutputFlag === '1' || p.OutputFlag === 1;
          const dirBadge =
            inFlag && outFlag ? '<span class="badge badge-inout">IN/OUT</span>'
            : outFlag ? '<span class="badge badge-out">OUT</span>'
            : inFlag ? '<span class="badge badge-in">IN</span>'
            : '<span class="badge">—</span>';
          const spType = p.SpDataLength
            ? `${p.SpDataType}(${p.SpDataLength}${p.SpDataScale ? ',' + p.SpDataScale : ''})`
            : p.SpDataType;
          return `<tr>
            <td>${escapeHtml(p.Sequence)}</td>
            <td class="font-medium">${escapeHtml(p.ParameterName)}</td>
            <td>${escapeHtml(p.DataType)}</td>
            <td class="text-xs muted">${escapeHtml(spType)}</td>
            <td>${dirBadge}</td>
          </tr>`;
        }).join('');

  sections.push(`<div class="method-section">
    <div class="method-section-head">
      <span class="method-section-label">Parameters</span>
      <span class="method-section-count">${params.length}</span>
    </div>
    <table class="data-table">
      <thead><tr><th>#</th><th>Name</th><th>Type</th><th>SP Type</th><th>Dir</th></tr></thead>
      <tbody>${paramRows}</tbody>
    </table>
  </div>`);

  // Result sets section (CLM output) — only shown when results exist; errors are silently swallowed
  const resultSets = resultSetsR.status === 'fulfilled' ? resultSetsR.value.items : [];
  if (resultSets.length > 0) {
    const rsRows = resultSets.map((r) => `<tr>
          <td>${escapeHtml(r.Sequence)}</td>
          <td class="font-medium">${escapeHtml(r.PropertyName)}</td>
        </tr>`).join('');

    sections.push(`<div class="method-section method-section-results">
      <div class="method-section-head">
        <span class="method-section-label">Result Set · CLM output properties</span>
        <span class="method-section-count">${resultSets.length}</span>
      </div>
      <table class="data-table">
        <thead><tr><th>#</th><th>PropertyName</th></tr></thead>
        <tbody>${rsRows}</tbody>
      </table>
    </div>`);
  }

  wrap.innerHTML = sections.join('');
}

// ---------- Cross-IDO search ----------
async function runSearch() {
  const type = $('#search-type').value;
  const q = $('#search-input').value.trim();
  if (!q) return;
  try {
    const { items } = await fetchJSON(`/api/search/${type}?q=${encodeURIComponent(q)}`);
    renderSearchResults(type, q, items);
  } catch (err) {
    toast(`Search failed: ${err.message}`);
  }
}

function renderSearchResults(type, q, items) {
  $('#search-results-wrap').classList.remove('hidden');
  const labelMap = { properties: 'property', expressions: 'expression', methods: 'method', tables: 'table' };
  $('#search-results-title').textContent = `${items.length} match${items.length === 1 ? '' : 'es'} for ${labelMap[type] || type} "${q}"`;
  const headCols = {
    properties: ['IDO', 'PropertyName', 'DataType', 'ColumnName'],
    expressions: ['IDO', 'PropertyName', 'DataType', 'Expression'],
    methods: ['IDO', 'MethodName', 'MethodType'],
    tables: ['IDO', 'TableName', 'TableType', 'JoinType'],
  }[type];
  const rowCols = {
    properties: (r) => [r.CollectionName, r.PropertyName, r.DataType, r.ColumnName],
    expressions: (r) => [
      r.CollectionName,
      r.PropertyName,
      r.DataType,
      `<code class="text-xs" title="${escapeHtml(r.PropertyValue || '')}">${escapeHtml(truncate(r.PropertyValue, 80))}</code>`,
    ],
    methods: (r) => {
      const t = String(r.MethodType ?? '');
      const label = t === '2' ? 'Stored Proc' : t === '3' ? 'Extension' : t === '0' ? 'System' : t;
      return [r.CollectionName, r.MethodName, label];
    },
    tables: (r) => {
      const t = String(r.TableType ?? '');
      const label = t === '3' ? 'Primary' : t === '0' ? 'Secondary' : t;
      return [r.CollectionName, r.TableName, label, r.JoinType];
    },
  }[type];
  $('#search-head').innerHTML = headCols
    .map((c) => `<th class="px-3 py-2">${escapeHtml(c)}</th>`)
    .join('');
  // Cells produced by rowCols are usually strings (auto-escaped) but the
  // 'expressions' search returns the Expression cell as pre-built HTML
  // (wrapping a <code> tag) — detect that case and pass through as-is.
  const isHtml = (s) => typeof s === 'string' && /^\s*</.test(s);
  $('#search-body').innerHTML = items
    .map((r) => {
      const cells = rowCols(r);
      return `<tr class="row-clickable" data-name="${escapeHtml(r.CollectionName)}">
        ${cells
          .map(
            (c, i) =>
              `<td class="px-3 py-1.5${i === 0 ? ' font-medium' : ''}">${isHtml(c) ? c : escapeHtml(c)}</td>`,
          )
          .join('')}
      </tr>`;
    })
    .join('');
  $('#search-body')
    .querySelectorAll('tr.row-clickable')
    .forEach((tr) => tr.addEventListener('click', () => selectIdo(tr.dataset.name)));
}

// ---------- Event wiring ----------
function wire() {
  $('#collections-filter').addEventListener(
    'input',
    debounce((e) => {
      state.collectionFilter = e.target.value;
      loadCollections();
    }, 250),
  );
  $$('.access-chip').forEach((btn) => {
    btn.addEventListener('click', () => {
      $$('.access-chip').forEach((b) => b.classList.remove('chip-on'));
      btn.classList.add('chip-on');
      state.accessFilter = btn.dataset.access;
      loadCollections();
    });
  });

  // Table/property/method filter rows
  document.querySelectorAll('.filter-row input').forEach((input) => {
    input.addEventListener(
      'input',
      debounce(() => {
        // Each filter input lives inside the card that owns one of the data
        // tables. Walk up to the enclosing card (legacy .bg-white kept as a
        // fallback for older markup) and route the value to the matching state.
        const card = input.closest('.dossier-card, .bg-white');
        if (!card) return;
        const field = input.dataset.field;
        const value = input.value;
        if (card.querySelector('#tables-body')) {
          state.tableFilters[field] = value;
          state.tablePage = 0;
          renderTables();
        } else if (card.querySelector('#props-body')) {
          state.propFilters[field] = value;
          renderProps();
        } else if (card.querySelector('#methods-body')) {
          state.methodFilters[field] = value;
          renderMethods();
        }
      }, 150),
    );
  });

  $$('.prop-chip').forEach((btn) => {
    btn.addEventListener('click', () => {
      $$('.prop-chip').forEach((b) => b.classList.remove('chip-on'));
      btn.classList.add('chip-on');
      state.propClass = btn.dataset.class;
      renderProps();
    });
  });

  $('#search-btn').addEventListener('click', runSearch);
  $('#search-input').addEventListener('keydown', (e) => {
    if (e.key === 'Enter') runSearch();
  });
  $('#search-close').addEventListener('click', () =>
    $('#search-results-wrap').classList.add('hidden'),
  );
  $('#drawer-close').addEventListener('click', () => $('#drawer').classList.remove('open'));
  $('#prop-drawer-close').addEventListener('click', () => $('#prop-drawer').classList.remove('open'));
  document.addEventListener('keydown', (e) => {
    if (e.key === 'Escape') {
      $('#drawer').classList.remove('open');
      $('#prop-drawer').classList.remove('open');
    }
  });
}

wire();
loadEnv();
loadCollections();
