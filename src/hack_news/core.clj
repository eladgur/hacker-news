(ns hack-news.core
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [toucan.db :as db]
            [toucan.models :as models]
            [compojure.api.sweet :refer [api]]
            [hack-news.routes :refer [post-entity-routes]]
            [ring.middleware.reload :refer [wrap-reload]]
            [hack-news.config :as config]
            [clojure.tools.nrepl.server :as nrepl]
            [clojure.tools.logging :as log])
  (:gen-class))

(def app (wrap-reload #'post-entity-routes))

(defn -main [& args]
  (log/info :a)
  (nrepl/start-server :bind "0.0.0.0" :port 12345)
  (log/info :a2)
  (db/set-default-db-connection! config/db-spec)
  (models/set-root-namespace! 'hack-news.models)
  (run-jetty app {:port 3000}))
