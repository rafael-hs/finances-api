(ns financial.auxiliaries
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [financial.handler :refer [app]]
            [clj-http.client :as http]))

(def server (atom nil))
(def default-port 3001)

(defn address-for [route]
  (str "http://localhost:" default-port route))

(def request-for (comp http/get address-for))

(defn start-server [port]
  (swap! server
    (fn [_] (run-jetty app {:port port :join? false}))))

(defn stop-server []
  (.stop @server))

(defn content [route] (:body (request-for route)))
