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

## Operating it

`docs/operator-quickstart.md` — clone to verified checkout. Every
command there was executed before it was committed, including a
catalog-vs-`data/` drift check (demonstrated failing on an injected
one-character change) and a liveness check of the cited URLs.

## Scope

A **read-only reference/archive** catalog — not an Advisor⊣Governor
actuation actor. It proposes or executes nothing on Tegucigalpa's
behalf.

Coverage is reported honestly (see `ordinance.facts/coverage`): a
municipality not in `catalog` has **no spec-basis**, full stop — never
fabricate one.

## Data

- `facts.edn` — verifiable source register (added 2026-09-08 by the
  ingest scout): one entity per source; `scripts/verify-facts.cljk`
  re-fetches every URL live and `scripts/mutation-check.cljk` proves it
  discriminates (15/15 caught, not-caught 0). Two entries are cited (the
  1578 founding via Wikipedia/Wikidata and the official AMDC portal).
  The **Ley de Municipalidades** (Decreto 134-90, www.tsc.gob.hn) is
  live but is **not** cited here: that host intermittently refuses the
  fleet-growth gate's automated client at connection level, and an entry
  one judge cannot reliably reach would REFUSE every gate run. It is
  recorded `:coverage/not-covered` in `facts.edn`'s header, not claimed
  absent.
- `src/ordinance/facts.cljk` — the catalog, source of truth.
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
