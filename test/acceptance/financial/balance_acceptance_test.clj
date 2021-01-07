(ns financial.balance-acceptance-test
  (:require [midje.sweet :refer [fact
                                 =>
                                 against-background
                                 before
                                 after]]
            [financial.auxiliaries :refer [start-server
                                           stop-server
                                           default-port
                                           content
                                           address-for]]
            [cheshire.core :as json]
            [clj-http.client :as http]))

(against-background [(before :facts (start-server default-port))
                     (after :facts (stop-server))]
  (fact "The initial balance is 0" :acceptance ;; o rótulo que o Midje vai enxergar
    (json/parse-string (content "/balance") true) => {:balance 0})

  (fact "The balance is 10 when a single transaction is revenue of 10" :acceptance
    (http/post (address-for "/transactions")
      {:content-type :json
       :body (json/generate-string {:value 10 :type "revenue"})})

    (json/parse-string (content "/balance") true) => {:balance 10}))
