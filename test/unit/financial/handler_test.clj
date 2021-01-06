(ns financial.handler-test
  (:require [midje.sweet :refer [fact
                                 facts
                                 =>
                                 against-background]]
            [ring.mock.request :as mock]
            [financial.handler :refer [app]]
            [cheshire.core :as json]))

(facts "return 'Hello World' in root route"
  (let [response (app (mock/request :get "/"))]
    (fact "the return status is 200"
      (:status response) => 200)

    (fact "the body text is 'Hello Wolrd'"
      (:body response) => "Ola Mundo")))

(facts "invalid route not found"
  (let [response (app (mock/request :get "/invalid"))]
    (fact "error code is 404"
      (:status response) => 404)

    (fact "the body text is 'Recurso não encontrado'"
      (:body response) => "Recurso não encontrado")))

(facts "The initial balance is 0"
  (against-background
    (json/generate-string {:saldo 0}) => "{\"saldo\":0}")

  (let [response (app (mock/request :get "/saldo"))]
    (fact "return status code is 200"
      (:status response) => 200)

    (fact "body text is a JSON which the key is balance and the value is 0"
      (:body response) => "{\"saldo\":0}")

    (fact "the format is 'application/json'"
      (get-in response [:headers "Content-Type"]) => "application/json; charset=utf-8")))
