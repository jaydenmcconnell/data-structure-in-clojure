(ns fruitbasket.analytics
  (:require [fruitbasket.analytics.fruit.apple :as apple]
            [fruitbasket.analytics.fruit.kiwi :as kiwi]
            [fruitbasket.analytics.fruit.pear :as pear]))

(defn get-something [data]
  (count data))

;; opts - idea to use "merge" taken from https://clojureverse.org/t/opts-concept-map-or-vector-with-additional-parameters-as-last-function-argument/7554
(def opts-default
  {:apple-color :red
   :kiwi-color :brown
   :pear-color :yellow})

(defn get [data & {:as opts}]
  (let [;; opts merging
        opts (merge opts-default opts)
        ;; apple functions REMOVES analyzed data, leaving just unanalysed
        {:keys [apple, unanalysed-data]} (apple/get data opts)
        ;; the other functions are simpler - they don't remove analyzed data
        kiwi (kiwi/get unanalysed-data opts)
        pear (pear/get unanalysed-data opts)
        ;; and something simple that is just in this namespace
        something (get-something unanalysed-data)]
    {:apple apple
     :kiwi kiwi
     :pear pear
     :something something}))

;; in REPL
;; (require 'fruitbasket.analytics)

;; (fruitbasket.analytics/get "GreenApple BrownKiwi YellowPear")

;; =>
;;   {:apple {:color :green, :count 3}
;;    :kiwi {:color :brown, :count 21}
;;    :pear {:color :yellow, :count 21}
;;    :something 21}





;; (fruitbasket.analytics/get "RedApple BrownKiwi YellowPear")

;; =>
;;   {:apple {:color :red, :count 3}
;;    :kiwi {:color :brown, :count 29}
;;    :pear {:color :yellow, :count 29}
;;    :something 29}
