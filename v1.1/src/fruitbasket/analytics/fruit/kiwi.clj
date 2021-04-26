(ns fruitbasket.analytics.fruit.kiwi)

(defn get [data opts]
  ;; just stupid example
  {:color (:kiwi-color opts)
   :count (count data)})
