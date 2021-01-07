(ns financial.handler-test
  (:require [midje.sweet :refer [fact facts
                                 => against-background]]
            [ring.mock.request :as mock]
            [financial.handler :refer [app]]
            [financial.db :as db]
            [cheshire.core :as json]))

(facts "return 'Hello World' in root route"
  (let [response (app (mock/request :get "/"))]
    (fact "the return status is 200"
      (:status response) => 200)

    (fact "the body text is 'Hello World'"
      (:body response) => "Hello World")))

(facts "invalid route not found"
  (let [response (app (mock/request :get "/invalid"))]
    (fact "error code is 404"
      (:status response) => 404)

    (fact "the body text is 'resource not found'"
      (:body response) => "Resource Not Found")))

(facts "The initial balance is 0"
  (against-background
    (json/generate-string {:balance 0}) => "{\"balance\":0}")

  (let [response (app (mock/request :get "/balance"))]
    (fact "return status code is 200"
      (:status response) => 200)

    (fact "body text is a JSON which the key is balance and the value is 0"
      (:body response) => "{\"balance\":0}")

    (fact "the format is 'application/json'"
      (get-in response [:headers "Content-Type"]) => "application/json; charset=utf-8")))

(facts "record revenue of 10"
  (against-background (db/record {:value 10 :type "revenue"})
    => {:id 1 :value 10 :type "revenue"})

  (let [response
        (app (->
               (mock/request :post "/transactions")
               (mock/json-body {:valor 10 :type "revenue"})))]

    (fact "The status code is equal 201"
      (:status response) => 201)

    (fact "The body text is a JSON with content send and id"
      (:body response) => "{\"id\":1,\"value\":10,\"type\":\"revenue\"}")))
