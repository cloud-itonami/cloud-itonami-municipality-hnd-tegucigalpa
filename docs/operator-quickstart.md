# Operator Quickstart — Tegucigalpa municipal-ordinance catalog

Shortest path from clone to a **verified** local checkout of
`cloud-itonami-municipality-hnd-tegucigalpa` (ADR-2607141700,
`cloud-itonami-compliance-fact-federation`).

Every command below was executed against `main` before this file was
committed; the outputs quoted are the ones actually observed, not
expected values written from the docstring.

## What this repo is (and is not)

A **read-only reference/archive** catalog of citation metadata
(id / title / url / dates) for Tegucigalpa. It is *not* an
Advisor⊣Governor actuation actor: it proposes nothing, executes
nothing, and holds no credential. There is no service to start and no
port to open — "operating" it means verifying that what it claims is
still true.

## Prerequisites

- Clojure CLI 1.12+ — verified with `1.12.5.1654`
- Java 17+ — verified with OpenJDK `24.0.2`
- `git`, `curl`

```bash
clojure --version
java -version
```

## 1. Clone

```bash
git clone https://github.com/cloud-itonami/cloud-itonami-municipality-hnd-tegucigalpa.git
cd cloud-itonami-municipality-hnd-tegucigalpa
```

## 2. Run the tests

```bash
kbb -M:test
```

Observed:

```
Ran 4 tests containing 11 assertions.
0 failures, 0 errors.
```

The suite pins the two invariants that matter for a citation catalog:
every entry carries an `https://` URL and a `:ordinance/number`, and an
un-catalogued municipality returns `nil` rather than a fabricated
answer.

## 3. Read the catalog's own coverage claim

```bash
kbb -M -e '(require (quote [ordinance.facts :as f])) (prn (f/coverage))'
```

Observed:

```clojure
{:requested 1, :covered 1, :covered-municipalities ["tegucigalpa"],
 :missing-municipalities [], :note "... 2 Tegucigalpa entries seeded ..."}
```

`coverage` reports honestly: pass it municipalities you care about and
it names the ones with **no spec-basis** instead of inventing them.

```bash
kbb -M -e '(require (quote [ordinance.facts :as f])) (prn (f/coverage ["tegucigalpa" "comayagua"]))'
```

Observed `:covered 1` with `:missing-municipalities ["comayagua"]` —
Comayagua is Honduras's former capital and is deliberately absent.

## 4. Verify `data/` has not drifted from the catalog

`data/datascript-tx.edn` is **derived** from `ordinance.facts/catalog`.
Nothing regenerates it automatically, so it can be hand-edited into
disagreement with its source. This check is what catches that:

```bash
kbb -M -e '(require (quote [clojure.edn :as edn]) (quote [clojure.set :as set]) (quote [ordinance.facts :as f])) (let [norm (fn [m] (update m :ordinance/topic set)) file (set (map norm (edn/read-string (slurp "data/datascript-tx.edn")))) code (set (map norm (mapcat val f/catalog)))] (if (= file code) (println "IN SYNC —" (count code) "entries") (do (println "DRIFT") (println "  only in data/datascript-tx.edn:" (pr-str (set/difference file code))) (println "  only in ordinance.facts/catalog:" (pr-str (set/difference code file))) (System/exit 1))))'
```

Observed on a clean tree: `IN SYNC — 2 entries` (exit 0).

**This check discriminates.** Changing `:enacted-date` from
`1990-10-29` to `1990-10-30` in `data/datascript-tx.edn` alone makes it
print `DRIFT`, name both differing entries, and exit **1** — verified
before this file was written. A check that only ever passes would tell
you nothing here.

## 5. Check that the cited sources are still reachable

A citation catalog whose URLs have rotted still looks perfectly healthy
to steps 2–4. Ask the network instead:

```bash
kbb -M -e '(require (quote [ordinance.facts :as f])) (doseq [e (mapcat val f/catalog)] (println (:ordinance/url e)))' > /tmp/ordinance-urls.txt
while read -r u; do printf '%s  %s\n' "$(curl -sS -L -o /dev/null -w '%{http_code}' --max-time 25 "$u")" "$u"; done < /tmp/ordinance-urls.txt
```

Observed 2026-09-02:

```
200  https://www.tsc.gob.hn/web/leyes/Ley_de_Municipalidades.pdf
200  https://en.wikipedia.org/wiki/Tegucigalpa
```

The URL list is extracted from the catalog rather than pasted here, so
this step keeps working as entries are added. Note the extraction is
written to a file first: reading `$?` after a pipeline gives you the
exit status of the **last** command, not of the extraction.

A non-2xx here is a finding to record, not a reason to quietly swap in
a different URL — see step 7.

## 6. Lint

```bash
kbb -M:lint
```

Observed: `linting took 923ms, errors: 0, warnings: 0`.

## 7. Extending the catalog

`ordinance.facts/catalog` is the source of truth; `data/datascript-tx.edn`
and `schema/ordinance.edn` follow it.

1. Add the entry to `catalog` in `src/ordinance/facts.cljk`, with a URL
   you have actually opened and a `:ordinance/url-provenance` that says
   what kind of source it is (`:official-tsc-honduras`,
   `:wikipedia-and-wikidata-corroborated`, …).
2. Mirror it into `data/datascript-tx.edn` and re-run step 4 until it
   prints `IN SYNC`.
3. Re-run steps 2, 5 and 6.
4. Record in the `ordinance.facts` docstring what you read directly
   versus what is secondary corroboration. The existing entries do this:
   the TSC PDF cover page confirms the decree number and title but *not*
   the enactment date, so `1990-10-29` is attributed to CEPAL's separate
   database rather than presented as PDF-confirmed.

**An ordinance not in this table has no spec-basis, full stop.** Never
invent an id, url, or date to fill a gap — a missing entry is a
reported gap, a fabricated one is a silent error that reads as data.

## Constraints

- No force-push; keep the AGPL-3.0-or-later headers.
- Law text itself remains Honduras's; this repo stores citation
  metadata only, never full text.
- No invented users/revenue/coverage numbers.
- Secrets stay out of this repo (it needs none).
