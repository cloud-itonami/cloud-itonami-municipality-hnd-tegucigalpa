# cloud-itonami-municipality-hnd-tegucigalpa

Municipal-ordinance compliance catalog for **Tegucigalpa** — the
FORTY-NINTH municipality-level entry, alongside 48 prior entries
including
[`cloud-itonami-municipality-gtm-guatemala-city`](https://github.com/cloud-itonami/cloud-itonami-municipality-gtm-guatemala-city),
[`cloud-itonami-municipality-pan-panama-city`](https://github.com/cloud-itonami/cloud-itonami-municipality-pan-panama-city),
and
[`cloud-itonami-municipality-prt-lisbon`](https://github.com/cloud-itonami/cloud-itonami-municipality-prt-lisbon).
Part of the [`cloud-itonami`](https://github.com/cloud-itonami)
compliance-fact family (ADR-2607141700,
`cloud-itonami-compliance-fact-federation`, in `com-junkawasaki/root`).

Honduras's first entry across the municipality axis — closing the
**last** of the 5 structural country-without-municipality gaps
identified at tick 141 (POL/GTM/HND/PAN/PRT). After this entry, every
country-axis jurisdiction in the federation also has a
municipality-axis entry.

## Sourcing note

The current governing law (Ley de Municipalidades, Decreto Número
134-90) is directly confirmed by reading the official Honduran
Tribunal Superior de Cuentas (TSC) PDF cover page via the Read-tool
saved-path fallback (WebFetch itself reported the PDF as
illegible/binary). The cover page confirms the decree number and
title but not the exact enactment date or gazette number, so the
1990-10-29 date is corroborated instead by CEPAL's own structured
legal-instrument database (`plataformaurbana.cepal.org`) — this is
noted honestly as a secondary corroboration, not a PDF-confirmed
field.

For the founding fact, both `en.wikipedia.org` and Wikidata (Q3238)
independently agree on 29 September 1578, with no discrepancy.

## Scope

A **read-only reference/archive** catalog — not an Advisor⊣Governor
actuation actor. It proposes or executes nothing on Tegucigalpa's
behalf.

Coverage is reported honestly (see `ordinance.facts/coverage`): a
municipality not in `catalog` has **no spec-basis**, full stop — never
fabricate one.

## Data

- `src/ordinance/facts.cljc` — the catalog, source of truth.
- `schema/ordinance.edn` — DataScript schema.
- `data/datascript-tx.edn` — derived DataScript tx-data (query this
  alongside other `cloud-itonami`/`etzhayyim` compliance-fact sources via
  `com-junkawasaki/root`'s `scripts/compliance-fact-query.cljs`).

Both entries directly confirmed: **Ley de Municipalidades** (Decreto
134-90, TSC PDF + CEPAL date corroboration) and the **1578 founding**
(Wikipedia + Wikidata agreement).

## License

AGPL-3.0-or-later (matches the `cloud-itonami-iso3166-*` /
`-municipality-*` / `-assoc-*` / `-lei-*` convention). Law text itself
remains Honduras's; this repo stores only citation metadata
(id/title/url/dates), not full text.
