#!/usr/bin/env nbb
;; mutation-check.cljs — breaks facts.edn one way at a time and requires the
;; verifier to catch EVERY break by the reason it names. Structural mutations
;; cost no network; the URL and must-contain mutations fetch live.
;;
;;   nbb scripts/mutation-check.cljs
;;
;; This suite is adapted from the sibling municipality register (guatemala-city)
;; and re-measured against THIS register's facts: the source ids, municipality
;; token, provenance set and country code that make each mutation bite are the
;; ones in facts.edn, not the sibling's.
(ns mutation-check
  (:require [clojure.edn :as edn]
            [kotoba.lang.text :as str]
            ["child_process" :as cp]
            ["fs" :as fs]
            ["os" :as os]
            ["path" :as path]))

(def base-facts
  (edn/read-string (str/trim (fs/readFileSync (path/join (.cwd (js/require "process")) "facts.edn") "utf8"))))

(defn- expect [name reason]
  {:name name :expect-reason reason})

(defn- repl [pred new-props]
  (fn [fs] (mapv #(if (pred %) (merge % new-props) %) fs)))

(def mutations
  [(assoc (expect "m1-url-404" "http-status")
          :mutate (repl #(= "municipal-portal.amdc" (:source/id %))
                        {:source/url "https://en.wikipedia.org/wiki/NoSuchPageIngestScoutCheck12345"}))
   (assoc (expect "m2-bad-host" "fetch-failed")
          :mutate (repl #(= "ordinance.1578-founding" (:source/id %))
                        {:source/url "https://nonexistent-host-xyz-12345.example/wiki"}))
   (assoc (expect "m3-scheme-http" "url-not-https")
          :mutate (repl #(= "ordinance.1578-founding" (:source/id %))
                        {:source/url "http://en.wikipedia.org/wiki/Tegucigalpa"}))
   (assoc (expect "m4-kind-bogus" "unknown-kind")
          :mutate (fn [fs] (update fs 0 assoc :ordinance/kind :bogus-kind)))
   (assoc (expect "m5-provenance-bogus" "unknown-provenance")
          :mutate (fn [fs] (update fs 0 assoc :ordinance/url-provenance :bogus)))
   (assoc (expect "m6-id-duplicate" "duplicate-id")
          :mutate (fn [fs] (update fs 0 assoc :source/id (:source/id (second fs)))))
   (assoc (expect "m7-missing-url" "missing-url")
          :mutate (fn [fs] (mapv #(dissoc % :source/url) fs)))
   (assoc (expect "m8-empty-title" "empty-title")
          :mutate (fn [fs] (update fs 0 assoc :ordinance/title "")))
   (assoc (expect "m9-empty-date" "missing-enacted-date")
          :mutate (fn [fs] (update fs 0 dissoc :ordinance/enacted-date)))
   (assoc (expect "m10-empty-number" "empty-number")
          :mutate (fn [fs] (update fs 0 assoc :ordinance/number "")))
   (assoc (expect "m11-country-lowercase" "country-case")
          :mutate (fn [fs] (mapv #(assoc % :ordinance/country "hnd") fs)))
   (assoc (expect "m12-municipality-mismatch" "municipality-mismatch")
          :mutate (fn [fs] (mapv #(assoc % :ordinance/municipality "tokyo") fs)))
   (assoc (expect "m13-must-contain-corrupt" "must-contain-missing")
          :mutate (repl #(= "ordinance.1578-founding" (:source/id %))
                        {:page/must-contain ["Antarctic Treaty ratified by Tegucigalpa in 1999"]}))
   (assoc (expect "m14-topic-missing" "missing-topic")
          :mutate (fn [fs] (mapv #(dissoc % :ordinance/topic) fs)))
   (assoc (expect "m15-id-not-string" "missing-id")
          :mutate (fn [fs] (update fs 0 dissoc :source/id)))])

(defn run-mutation [m]
  (let [tmp (path/join (os/tmpdir) (str "facts-mut-" (.random js/Math) ".edn"))]
    (fs.writeFileSync tmp (pr-str ((:mutate m) base-facts)))
    (let [r (try
              {:exit 0
               :out (cp/execFileSync "nbb" #js ["scripts/verify-facts.cljs" "--facts" tmp]
                                     #js {:cwd (.cwd (js/require "process")) :encoding "utf8"})}
              (catch :default e
                {:exit (or (.-status e) 1)
                 :out (str (or (.-stdout e) "") "\n" (.-message e))}))]
      (fs.unlinkSync tmp)
      r)))

(defn caught? [m]
  (let [r (run-mutation m)]
    (and (str/includes? (:out r) (:expect-reason m))
         (not (str/includes? (:out r) "ALL PASS")))))

(def results
  (mapv (fn [m]
          {:name (:name m)
           :caught (try
                     (caught? m)
                     (catch :default e
                       (println (str "mutation error " (:name m) ": " (.-message e)))
                       false))})
        mutations))

(doseq [r results]
  (println (str (:name r) ": " (if (:caught r) "caught" "NOT-CAUGHT"))))

(let [nc (count (remove :caught results))
      c (count results)]
  (println (str "caught=" (- c nc) " inconclusive=0 not-caught=" nc " of " c))
  (when (not= nc 0)
    (js/process.exit 1)))