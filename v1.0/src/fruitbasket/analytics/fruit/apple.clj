(ns fruitbasket.analytics.fruit.apple
  (:require [clojure.string :as str]))

(defn get [data opts]
  ;; just a silly example that some analytics functions removes
  ;;   analyzed data so subsequent analytics functions have easier job
  (let [[unanalysed-data, color] (if (str/includes? data "GreenApple")
                                   [(str/replace data "GreenApple" "") , :green]
                                   [data, (:apple-color opts)])]
    ;; returning result AND rest of data (unanalyzed data)
    {:unanalysed-data unanalysed-data
     :apple {:color color
             :count 3}}))

;; in REPL
;; (require 'fruitbasket.analytics.fruit.apple)
;; (fruitbasket.analytics.fruit.apple/get "something BlueApple something" {})
;; (fruitbasket.analytics.fruit.apple/get "something GreenApple something" {})
