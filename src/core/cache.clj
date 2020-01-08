(ns core.cache
  (:require [toucan.db :as db]
            [core.models :refer [Post]]
            [clojure.tools.logging :as log]))

(def page-size 30)

(defn get-top-posts-from-db []
  (db/query {:select   [:*]
             :from     [Post]
             :order-by [:score]
             :limit    page-size}))

(defonce top-posts (atom (get-top-posts-from-db)))

(defn update! []
  (log/info ::update! {:status :start})
  (reset! top-posts (get-top-posts-from-db))
  (log/info ::update! {:status :end}))
