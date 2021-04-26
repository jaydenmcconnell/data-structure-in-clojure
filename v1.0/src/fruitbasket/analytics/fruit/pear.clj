(ns fruitbasket.analytics.fruit.pear)

(defn get [data opts]
  ;; just stupid example
  {:color (:pear-color opts)
   :count (count data)})
