const state = {
  collections: [],
  collectionFilter: '',
  accessFilter: '',
  selected: null,
  overview: null,
  extended: null,
  tables: [],
  properties: [],
  methods: [],
  tableFilters: {},
  propFilters: {},
  propClass: '',
  methodFilters: {},
};

const $ = (sel) => document.querySelector(sel);
const $$ = (sel) => Array.from(document.querySelectorAll(sel));

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
    $('#env-pill').textContent = `${env.site}`;
    $('#env-pill').title = env.baseUrl;
  } catch (err) {
    $('#env-pill').textContent = 'env error';
    toast(err.message);
  }
}

// ---------- Collections ----------
async function loadCollections() {
  const params = new URLSearchParams();
  if (state.collectionFilter) params.set('q', state.collectionFilter);
  if (state.accessFilter) params.set('accessAs', state.accessFilter);
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
  $('#collections-count').textContent = `${state.collections.length}`;
  list.innerHTML = state.collections
    .map((c) => {
      const access = c.AccessAs || 'Custom';
      const sel = state.selected === c.CollectionName ? 'selected' : '';
      return `<li class="px-3 py-2 ${sel}" data-name="${escapeHtml(c.CollectionName)}">
        <div class="font-medium">${escapeHtml(c.CollectionName)}</div>
        <div class="text-xs text-slate-500">${escapeHtml(access)}</div>
      </li>`;
    })
    .join('');
  list.querySelectorAll('li').forEach((li) => {
    li.addEventListener('click', () => selectIdo(li.dataset.name));
  });
}

// ---------- Selected IDO ----------
async function selectIdo(name) {
  state.selected = name;
  state.tableFilters = {};
  state.propFilters = {};
  state.propClass = '';
  state.methodFilters = {};
  $$('.prop-chip').forEach((b) => b.classList.toggle('chip-on', b.dataset.class === ''));
  renderCollections();
  $('#detail-empty').classList.add('hidden');
  $('#detail-content').classList.remove('hidden');
  $('#ov-name').textContent = name;
  $('#ov-sub').textContent = 'Loading…';
  $('#ov-extended').textContent = '';
  $('#tables-body').innerHTML = '<tr><td colspan="5" class="muted px-3 py-3">Loading…</td></tr>';
  $('#props-body').innerHTML = '<tr><td colspan="6" class="muted px-3 py-3">Loading…</td></tr>';
  $('#methods-body').innerHTML = '<tr><td colspan="3" class="muted px-3 py-3">Loading…</td></tr>';

  // Fire all in parallel
  const [overviewR, tablesR, propsR, methodsR] = await Promise.allSettled([
    fetchJSON(`/api/ido/${encodeURIComponent(name)}/overview`),
    fetchJSON(`/api/ido/${encodeURIComponent(name)}/tables`),
    fetchJSON(`/api/ido/${encodeURIComponent(name)}/properties`),
    fetchJSON(`/api/ido/${encodeURIComponent(name)}/methods`),
  ]);

  if (state.selected !== name) return; // user clicked away

  if (overviewR.status === 'fulfilled') {
    state.overview = overviewR.value.item;
    state.extended = overviewR.value.extended;
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
}

function renderOverview() {
  const ov = state.overview;
  if (!ov) {
    $('#ov-sub').textContent = 'Not found';
    return;
  }
  const access = ov.AccessAs ? `Core (${ov.AccessAs})` : 'Custom';
  $('#ov-sub').textContent = access;
  if (state.extended && state.extended.CollectionName !== ov.CollectionName) {
    $('#ov-extended').innerHTML = `<span class="badge">Extended: ${escapeHtml(state.extended.CollectionName)}</span>`;
  } else {
    $('#ov-extended').innerHTML = '';
  }
}

// ---------- Filtering helpers ----------
function matchesFilter(value, filter) {
  if (!filter) return true;
  if (value === null || value === undefined) return false;
  return String(value).toLowerCase().includes(filter.toLowerCase());
}

// ---------- Tables ----------
function renderTables() {
  const rows = state.tables.filter((r) =>
    Object.entries(state.tableFilters).every(([f, v]) => matchesFilter(r[f], v)),
  );
  $('#tables-count').textContent = `${rows.length} / ${state.tables.length}`;
  $('#tables-body').innerHTML = rows
    .map((r) => {
      const t = String(r.TableType ?? '');
      const badge =
        t === '3' ? '<span class="badge badge-primary">Primary</span>'
        : t === '0' ? '<span class="badge badge-secondary">Secondary</span>'
        : `<span class="badge">${escapeHtml(t)}</span>`;
      return `<tr>
        <td class="font-medium">${escapeHtml(r.TableName)}</td>
        <td>${escapeHtml(r.TableAlias)}</td>
        <td>${badge}</td>
        <td>${escapeHtml(r.JoinType)}</td>
        <td class="text-xs muted">${escapeHtml(r.JoinText)}</td>
      </tr>`;
    })
    .join('');
}

// ---------- Properties ----------
function propClassMatches(row) {
  const isRO = row.IsReadOnly === '1' || row.IsReadOnly === 1;
  const isBound = !!row.ColumnName;
  const cls = String(row.PropertyClass ?? '');
  switch (state.propClass) {
    case 'writable':
      return isBound && !isRO;
    case 'readonly':
      return isRO;
    case 'derived':
      return cls === '1' && !row.ColumnName;
    case 'sub':
      return cls === '3';
    default:
      return true;
  }
}

function renderProps() {
  const rows = state.properties.filter(
    (r) =>
      propClassMatches(r) &&
      Object.entries(state.propFilters).every(([f, v]) => matchesFilter(r[f], v)),
  );
  $('#props-count').textContent = `${rows.length} / ${state.properties.length}`;
  $('#props-body').innerHTML = rows
    .map((r) => {
      const cls = String(r.PropertyClass ?? '');
      const clsLabel =
        cls === '1' ? 'Derived'
        : cls === '3' ? 'Subcollection'
        : cls === '0' || cls === '' ? 'Bound'
        : cls;
      const ro = r.IsReadOnly === '1' || r.IsReadOnly === 1 ? '✓' : '';
      return `<tr>
        <td class="font-medium">${escapeHtml(r.PropertyName)}</td>
        <td>${escapeHtml(r.DataType)}</td>
        <td><span class="badge">${escapeHtml(clsLabel)}</span></td>
        <td>${escapeHtml(r.ColumnName)}</td>
        <td>${escapeHtml(r.ColumnTableAlias)}</td>
        <td>${ro}</td>
      </tr>`;
    })
    .join('');
}

// ---------- Methods ----------
function renderMethods() {
  const rows = state.methods.filter((r) =>
    Object.entries(state.methodFilters).every(([f, v]) => matchesFilter(r[f], v)),
  );
  $('#methods-count').textContent = `${rows.length} / ${state.methods.length}`;
  $('#methods-body').innerHTML = rows
    .map((r) => {
      const t = String(r.MethodType ?? '');
      const label =
        t === '2' ? 'Stored Proc'
        : t === '3' ? 'Extension'
        : t === '0' ? 'System'
        : t;
      return `<tr class="row-clickable" data-method="${escapeHtml(r.MethodName)}">
        <td class="font-medium">${escapeHtml(r.MethodName)}</td>
        <td><span class="badge">${escapeHtml(label)}</span></td>
        <td class="text-xs muted">View params →</td>
      </tr>`;
    })
    .join('');
  $('#methods-body')
    .querySelectorAll('tr.row-clickable')
    .forEach((tr) => tr.addEventListener('click', () => openMethodDrawer(tr.dataset.method)));
}

// ---------- Method parameters drawer ----------
async function openMethodDrawer(method) {
  const drawer = $('#drawer');
  drawer.classList.add('open');
  $('#drawer-title').textContent = method;
  $('#drawer-sub').textContent = state.selected;
  $('#drawer-body').innerHTML = '<tr><td colspan="5" class="muted px-2 py-3">Loading…</td></tr>';
  try {
    const { items } = await fetchJSON(
      `/api/ido/${encodeURIComponent(state.selected)}/methods/${encodeURIComponent(method)}/parameters`,
    );
    $('#drawer-body').innerHTML = items
      .map((p) => {
        const inFlag = p.InputFlag === '1' || p.InputFlag === 1;
        const outFlag = p.OutputFlag === '1' || p.OutputFlag === 1;
        const dirBadge =
          inFlag && outFlag ? '<span class="badge badge-inout">IN/OUT</span>'
          : outFlag ? '<span class="badge badge-out">OUT</span>'
          : inFlag ? '<span class="badge badge-in">IN</span>'
          : '<span class="badge">—</span>';
        const spType = p.SpDataLength ? `${p.SpDataType}(${p.SpDataLength}${p.SpDataScale ? ',' + p.SpDataScale : ''})` : p.SpDataType;
        return `<tr>
          <td class="px-2 py-1.5">${escapeHtml(p.Sequence)}</td>
          <td class="px-2 py-1.5 font-medium">${escapeHtml(p.ParameterName)}</td>
          <td class="px-2 py-1.5">${escapeHtml(p.DataType)}</td>
          <td class="px-2 py-1.5 text-xs muted">${escapeHtml(spType)}</td>
          <td class="px-2 py-1.5">${dirBadge}</td>
        </tr>`;
      })
      .join('');
  } catch (err) {
    $('#drawer-body').innerHTML = `<tr><td colspan="5" class="muted px-2 py-3">Error: ${escapeHtml(err.message)}</td></tr>`;
  }
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
  $('#search-results-title').textContent = `${items.length} match${items.length === 1 ? '' : 'es'} for ${type.slice(0, -1)} "${q}"`;
  const headCols = {
    properties: ['IDO', 'PropertyName', 'DataType', 'ColumnName'],
    methods: ['IDO', 'MethodName', 'MethodType'],
    tables: ['IDO', 'TableName', 'TableType', 'JoinType'],
  }[type];
  const rowCols = {
    properties: (r) => [r.CollectionName, r.PropertyName, r.DataType, r.ColumnName],
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
  $('#search-body').innerHTML = items
    .map((r) => {
      const cells = rowCols(r);
      return `<tr class="row-clickable" data-name="${escapeHtml(r.CollectionName)}">
        ${cells.map((c, i) => `<td class="px-3 py-1.5${i === 0 ? ' font-medium' : ''}">${escapeHtml(c)}</td>`).join('')}
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
        const card = input.closest('.bg-white');
        const field = input.dataset.field;
        const value = input.value;
        if (card.querySelector('#tables-body')) {
          state.tableFilters[field] = value;
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
  document.addEventListener('keydown', (e) => {
    if (e.key === 'Escape') $('#drawer').classList.remove('open');
  });
}

wire();
loadEnv();
loadCollections();
