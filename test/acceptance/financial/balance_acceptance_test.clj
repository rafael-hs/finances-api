(ns financial.balance-acceptance-test
  (:require [midje.sweet :refer [fact
                                 =>
                                 against-background
                                 before
                                 after]]
            [cheshire.core :as json]
            [financial.auxiliaries :refer [start-server
                                            stop-server
                                            default-port
                                            content]]))

(against-background [(before :facts (start-server default-port))
                     (after :facts (stop-server))]
  (fact "The initial balance is 0" :acceptance ;; o rótulo que o Midje vai enxergar
    (json/parse-string (content "/saldo") true) => {:saldo 0}))
