import { test } from 'node:test';
import assert from 'node:assert/strict';
import { buildFilter, buildLoadUrl } from '../lib/syteline.js';

test('buildFilter wraps string values in N\'...\' and joins with AND', () => {
  const filter = buildFilter([
    { field: 'CollectionName', op: '=', value: 'SLItems' },
    { field: 'MethodName', op: '=', value: 'JoblowSp' },
  ]);
  assert.equal(filter, "CollectionName = N'SLItems' AND MethodName = N'JoblowSp'");
});

test('buildFilter LIKE wraps with %', () => {
  const filter = buildFilter([{ field: 'PropertyName', op: 'LIKE', value: 'Item' }]);
  assert.equal(filter, "PropertyName LIKE N'%Item%'");
});

test('buildFilter escapes single quotes', () => {
  const filter = buildFilter([{ field: 'PropertyName', op: '=', value: "O'Brien" }]);
  assert.equal(filter, "PropertyName = N'O''Brien'");
});

test('buildFilter drops empty values', () => {
  const filter = buildFilter([
    { field: 'CollectionName', op: '=', value: 'SLItems' },
    { field: 'PropertyName', op: 'LIKE', value: '' },
    { field: 'DataType', op: '=', value: null },
  ]);
  assert.equal(filter, "CollectionName = N'SLItems'");
});

test('buildFilter handles __EMPTY__ as null-or-empty', () => {
  const filter = buildFilter([{ field: 'AccessAs', op: '=', value: '__EMPTY__' }]);
  assert.equal(filter, "(AccessAs IS NULL OR AccessAs = N'')");
});

test('buildLoadUrl URL-encodes filter expressions', () => {
  const url = buildLoadUrl('https://host/ido', 'IdoProperties', {
    properties: 'PropertyName,DataType',
    filter: "CollectionName = N'SLItems'",
    recordcap: 0,
  });
  // URLSearchParams uses '+' for spaces in query strings — Syteline accepts both.
  assert.match(url, /^https:\/\/host\/ido\/load\/IdoProperties\?/);
  assert.match(url, /properties=PropertyName%2CDataType/);
  assert.match(url, /filter=CollectionName(\+|%20)%3D(\+|%20)N%27SLItems%27/);
  assert.match(url, /recordcap=0/);
});

test('buildLoadUrl URL-encodes LIKE % wildcards', () => {
  const url = buildLoadUrl('https://host/ido', 'IdoProperties', {
    filter: "PropertyName LIKE N'%Item%'",
  });
  // '%' must become %25
  assert.match(url, /N%27%25Item%25%27/);
});
