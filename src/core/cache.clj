(ns core.cache
  (:require [toucan.db :as db]
            [core.models :refer [Post]]
            [clojure.tools.logging :as log]
            [core.config :as config]))

(def page-size 30)
(defonce ^:private top-posts (atom []))

(defn- get-top-posts-from-db []
  (db/query {:select   [:*]
             :from     [Post]
             :order-by [:score]
             :limit    page-size}))

(defn update! []
  (log/info ::update!)
  (reset! top-posts (get-top-posts-from-db)))

(defn get-top-posts []
  (if-let [posts (not-empty @top-posts)]
    posts
    (update!)))
