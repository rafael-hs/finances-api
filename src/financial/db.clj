(ns financial.db)

(def records
  (atom []))

(defn transactions []
  @records)

(defn clean []
  (reset! records []))

(defn record [transaction]
  (let [updated-collection (swap! records conj transaction)]
    (merge transaction {:id (count updated-collection)})))
