(ns ordinance.facts
  "Municipal-ordinance compliance catalog for Tegucigalpa -- the
  FORTY-NINTH municipality-level entry (see
  cloud-itonami-municipality-jpn-tokyo, -usa-washington-dc, -gbr-london,
  -can-toronto, -deu-berlin, -fra-paris, -nld-amsterdam, -esp-madrid,
  -kor-seoul, -ita-roma, -aus-sydney, -arg-buenos-aires, -fin-helsinki,
  -dnk-copenhagen, -nor-oslo, -bel-brussels, -chl-santiago, -col-bogota,
  -cri-san-jose, -bra-sao-paulo, -ury-montevideo, -zaf-cape-town,
  -ecu-quito, -swe-gothenburg, -pry-asuncion, -mex-guadalajara,
  -fra-lyon, -ind-new-delhi, -pol-warsaw, -ken-nairobi, -tha-bangkok,
  -are-abu-dhabi, -vnm-hanoi, -idn-jakarta, -phl-manila, -egy-cairo,
  -tur-ankara, -nga-abuja, -sau-riyadh, -mys-kuala-lumpur, -aut-vienna,
  -che-bern, -irl-dublin, -nzl-wellington, -cze-prague, -prt-lisbon,
  -pan-panama-city, -gtm-guatemala-city for the first forty-eight) per
  ADR-2607141700 (cloud-itonami-compliance-fact-federation). Honduras's
  first entry across the municipality axis, closing the LAST of the 5
  structural country-without-municipality gaps identified at tick 141
  (POL/GTM/HND/PAN/PRT) -- after this entry, every country-axis
  jurisdiction in the federation also has a municipality-axis entry.

  Tegucigalpa is Honduras's capital (Wikidata Q3238); no ambiguity
  about its CURRENT status. Its capital status is itself historically
  layered -- it became the PERMANENT seat of government on 30 October
  1880 under President Marco Aurelio Soto, moved from the earlier
  capital, Comayagua -- but this catalog does not assert the 1880
  relocation as a fact entry because no directly-read primary/official
  source for that specific date was checked this tick (only
  Wikipedia's narrative account); only the two facts below were
  directly verified against primary or Wikipedia/Wikidata-corroborated
  sources.

  Ley de Municipalidades (Decreto Número 134-90 del Congreso Nacional)
  -- decree number and title directly confirmed by reading the
  official Honduran Tribunal Superior de Cuentas (TSC) PDF cover page
  via the Read-tool saved-path fallback (WebFetch itself reported the
  PDF as illegible/binary), which shows verbatim: 'DECRETO NUMERO
  134-90', 'EL CONGRESO NACIONAL', 'LEY DE MUNICIPALIDADES'. The
  1990-10-29 date is corroborated by CEPAL's own structured legal
  instrument database (plataformaurbana.cepal.org), which lists this
  decree's date of publication as '1990-10-29' -- the TSC PDF's cover
  page itself does not show the exact enactment date or Gaceta Oficial
  number, so that specific field is sourced from CEPAL's independent
  record rather than the directly-read PDF; this is noted honestly
  rather than treated as PDF-confirmed.

  Tegucigalpa's founding -- directly confirmed via both
  en.wikipedia.org (which states the city was 'established on
  September 29, 1578' by Spanish settlers as 'Real de Minas de San
  Miguel de Tegucigalpa') and Wikidata Q3238 (inception '29 September
  1578 Gregorian'). Both sources independently agree with NO
  discrepancy (unlike the Panama City and Guatemala City entries added
  in the two preceding ticks, where a genuine date/lineage discrepancy
  was found and had to be resolved).

  An ordinance not in this table has NO spec-basis, full stop; extend
  `catalog`, do not invent an id/url/date.")

(def catalog
  "municipality-slug -> vector of ordinance entries."
  {"tegucigalpa"
   [{:ordinance/id "tegucigalpa.decreto-134-90-ley-de-municipalidades"
     :ordinance/title "Ley de Municipalidades (Decreto Número 134-90 del Congreso Nacional de Honduras)"
     :ordinance/municipality "tegucigalpa"
     :ordinance/country "HND"
     :ordinance/kind :local-act
     :ordinance/number "Decreto 134-90"
     :ordinance/url "https://www.tsc.gob.hn/web/leyes/Ley_de_Municipalidades.pdf"
     :ordinance/url-provenance :official-tsc-honduras
     :ordinance/enacted-date "1990-10-29"
     :ordinance/retrieved-at "2026-07-18"
     :ordinance/topic #{:governance}}
    {:ordinance/id "tegucigalpa.1578-founding"
     :ordinance/title "Tegucigalpa was founded as Real de Minas de San Miguel de Tegucigalpa on 29 September 1578 by Spanish settlers"
     :ordinance/municipality "tegucigalpa"
     :ordinance/country "HND"
     :ordinance/kind :local-act
     :ordinance/number "1578"
     :ordinance/url "https://en.wikipedia.org/wiki/Tegucigalpa"
     :ordinance/url-provenance :wikipedia-and-wikidata-corroborated
     :ordinance/enacted-date "1578-09-29"
     :ordinance/retrieved-at "2026-07-18"
     :ordinance/topic #{:governance}}]})

(defn spec-basis [muni] (get catalog muni))

(defn coverage
  ([] (coverage (keys catalog)))
  ([munis]
   (let [have (filter catalog munis)
         missing (remove catalog munis)]
     {:requested (count munis)
      :covered (count have)
      :covered-municipalities (vec (sort have))
      :missing-municipalities (vec (sort missing))
      :note (str "cloud-itonami-municipality-hnd-tegucigalpa Wave 0 (ADR-2607141700): "
                 (count (get catalog "tegucigalpa")) " Tegucigalpa entries seeded "
                 "with TSC/CEPAL and Wikipedia+Wikidata citations. "
                 "Extend `ordinance.facts/catalog`, never fabricate an id/url.")})))

(defn by-topic [muni topic]
  (filterv #(contains? (:ordinance/topic %) topic) (spec-basis muni)))
