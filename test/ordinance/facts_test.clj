(ns ordinance.facts-test
  (:require [clojure.string :as str]
            [clojure.test :refer [deftest is]]
            [ordinance.facts :as facts]))

(deftest tegucigalpa-has-spec-basis
  (let [sb (facts/spec-basis "tegucigalpa")]
    (is (= 2 (count sb)))
    (is (every? #(str/starts-with? (:ordinance/url %) "https://") sb))
    (is (every? :ordinance/number sb))))

(deftest unknown-municipality-has-no-spec-basis
  (is (nil? (facts/spec-basis "comayagua")))
  (is (nil? (facts/spec-basis "zzz"))))

(deftest coverage-is-honest
  (let [c (facts/coverage ["tegucigalpa" "comayagua"])]
    (is (= 2 (:requested c)))
    (is (= 1 (:covered c)))
    (is (= ["comayagua"] (:missing-municipalities c)))))

(deftest by-topic-filters
  (is (= 2 (count (facts/by-topic "tegucigalpa" :governance))))
  (is (empty? (facts/by-topic "tegucigalpa" :labor)))
  (is (empty? (facts/by-topic "comayagua" :governance))))
