(ns financeiro.handler
  (:require [compojure.core :refer [GET
                                    defroutes]]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults
                                              site-defaults]]
            [cheshire.core :as json]))

(defn balance-as-json []
{:headers {"Content-Type" "application/json; charset=utf-8"}
 :body (json/generate-string {:saldo 0})})

(defroutes app-routes
  (GET "/" [] "Ola Mundo")
  (GET "/saldo" [] (balance-as-json))
  (route/not-found "Recurso não encontrado"))

(def app
  (wrap-defaults app-routes site-defaults))
