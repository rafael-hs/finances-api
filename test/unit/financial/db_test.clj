(ns financial.db-test
  (:require [midje.sweet :refer [facts fact => before  against-background]]
            [financial.db :as db]))

(facts "store a transaction in atom"
  (against-background [(before :facts (db/clean))]

    (fact "starts a empty transaction collection"
      (count (db/transactions)) => 0)

    (fact "a transacation is the first record"
      (db/record {:value 7 :type "revenue"})
      => {:id 1 :value 7 :type "revenue"}
      (count (db/transactions)) => 1)))
