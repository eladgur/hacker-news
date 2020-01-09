(ns core.main
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [toucan.db :as db]
            [toucan.models :as models]
            [compojure.api.sweet :refer [api]]
            [core.routes :refer [post-entity-routes]]
            [ring.middleware.reload :refer [wrap-reload]]
            [core.config :as config]
            [clojure.tools.nrepl.server :as nrepl]
            [clojure.tools.logging :as log])
  (:gen-class))

(def app (wrap-reload #'post-entity-routes))

(defn -main [& args]
  (nrepl/start-server :bind "0.0.0.0" :port 12345)
  (db/set-default-db-connection! config/db-spec)
  (models/set-root-namespace! 'core.models)
  (run-jetty app {:port 3000}))
