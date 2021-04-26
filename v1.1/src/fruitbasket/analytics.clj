(ns fruitbasket.analytics
  (:require [fruitbasket.analytics.fruit.apple :as apple]
            [fruitbasket.analytics.fruit.kiwi :as kiwi]
            [fruitbasket.analytics.fruit.pear :as pear]))

(defn add-missing-apple [structured data opts]
  ;; apple function is more complicated than other
  ;;   since it REMOVES analyzed data, leaving just unanalysed
  (let [{:keys [apple, unanalysed-data]} (apple/get data opts)]
    {:unanalysed-data unanalysed-data
     :structured (assoc structured :apple apple)}))

(defn add-missing-kiwi [structured data opts]
  (assoc structured
         :kiwi (kiwi/get data opts)))

(defn add-missing-pear [structured data opts]
  (assoc structured
         :pear (pear/get data opts)))

(defn add-missing-something [structured data]
  ;; and something simple that is just handled in this namespace
  (assoc structured
         :something (count data)))

;; opts - idea to use "merge" taken from https://clojureverse.org/t/opts-concept-map-or-vector-with-additional-parameters-as-last-function-argument/7554
(def opts-default
  {:apple-color :red
   :kiwi-color :brown
   :pear-color :yellow})

(defn get [data & {:as opts}]
  (let [;; opts merging
        opts (merge opts-default opts)
        ;; apple functions REMOVES analyzed data, leaving just unanalysed
        {:keys [structured, unanalysed-data]} (add-missing-apple {} data opts)]
    ;; the other functions are simpler - they don't remove analyzed data
    (-> structured
        (add-missing-kiwi unanalysed-data opts)
        ;; get this later:
        ;;   (add-missing-pear unanalysed-data opts)
        (add-missing-something unanalysed-data))))

;; in REPL
;; (require 'fruitbasket.analytics)
;; (fruitbasket.analytics/get "GreenApple BrownKiwi YellowPear")

;; =>
;;   {:apple {:color :green, :count 3}
;;    :kiwi {:color :brown, :count 21}
;;    :something 21}


;; (fruitbasket.analytics/add-missing-pear
;;  {:apple {:color :green, :count 3}
;;   :kiwi {:color :brown, :count 21}
;;   :something 21}
;;  "abcdef" {})

;; =>
;;   {:apple {:color :green, :count 3}
;;    :kiwi {:color :brown, :count 21}
;;    :something 21
;;    :pear {:color nil, :count 6}}





;; (fruitbasket.analytics/get "RedApple BrownKiwi YellowPear")

;; =>
;;   {:apple {:color :red, :count 3}
;;    :kiwi {:color :brown, :count 21}
;;    :something 21}

;; (fruitbasket.analytics/add-missing-pear
;;  {:apple {:color :red, :count 3}
;;   :kiwi {:color :brown, :count 21}
;;   :something 21}
;;  "xyz" {})

;; =>
;;  {:apple {:color :red, :count 3}
;;   :kiwi {:color :brown, :count 21}
;;   :something 21
;;   :pear {:color nil, :count 3}}
