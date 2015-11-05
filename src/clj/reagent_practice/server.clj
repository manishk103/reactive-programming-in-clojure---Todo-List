(ns reagent-practice.server
  (:require [reagent-practice.handler :refer [app]]
            [environ.core :refer [env]]
            [ring.adapter.jetty :refer [run-jetty]])
  (:gen-class))

(defn -main [& args]
  (let [port (Integer/parseInt (or (env :port) "3000"))]
    (run-jetty app {:port port :join? false})))


 #_(use 'reagent-practice.repl)
 #_(start-server)
 #_(stop-server)
