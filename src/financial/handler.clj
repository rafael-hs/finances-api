(ns financial.handler
  (:require [compojure.core :refer [GET
                                    POST
                                    defroutes]]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults
                                              api-defaults]]
            [ring.middleware.json :refer [wrap-json-body]]
            [cheshire.core :as json]
            [financial.db :as db]))

(defn as-json [content & [status]]
  {:status (or status 200)
   :headers {"Content-Type" "application/json; charset=utf-8"}
   :body (json/generate-string content)})

(defroutes app-routes
  (GET "/" [] "Hello World")
  (GET "/balance" [] (as-json {:balance 0}))
  (POST "/transactions"
    request (->
              (db/record (:body request))
              (as-json 201)))
  (route/not-found "Resource Not Found"))

(def app
  (->
    (wrap-defaults app-routes api-defaults)
    (wrap-json-body {:keywords? true :bigdecimals? true})))
