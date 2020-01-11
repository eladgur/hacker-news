(ns core.cache
  (:require [toucan.db :as db]
            [core.models :refer [Post]]
            [clojure.tools.logging :as log]))

(def page-size 30)
(defonce ^:private top-posts (agent []))

(defn- get-top-posts-from-db []
  (db/query {:select   [:*]
             :from     [Post]
             :order-by [:score]
             :limit    page-size}))

(defn- update-agent [curr-val]
  (if-let [fetched-top-posts (not-empty (get-top-posts-from-db))]
    fetched-top-posts
    curr-val))

(defn update-async! []
  (log/info ::update-async!)
  (send-off top-posts update-agent))

(defn get-top-posts []
  (if-let [posts (not-empty @top-posts)]
    posts
    (do (update-async!) (get-top-posts-from-db))))
