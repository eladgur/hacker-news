(ns core.cache
  (:require [toucan.db :as db]
            [core.models :refer [Post]]))

(def page-size 30)

(defn get-top-posts-from-db []
  (db/query {:select   [:*]
             :from     [Post]
             :order-by [:score]
             :limit    page-size}))

(defonce top-posts (atom (get-top-posts-from-db)))
