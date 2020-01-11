(ns core.util.test-util
  (:require [core.util.http-client :as hc]
            [clojure.test :refer :all]
            [toucan.db :as db]
            [core.models :refer [Post]]))

(def ids (atom []))

(defn create-post [post]
  (let [{:keys [status body]} (hc/create! post)
        post-id (:id body)]
    (swap! ids conj post-id)
    (is (= status 201))
    post-id))

(defn- delete! [ids]
  (if (sequential? ids)
    (run! #(db/delete! Post :id %) ids)
    (db/delete! Post :id ids)))

(defn delete-created-posts! []
  (delete! @ids))

(defn test-fixture [f]
  (f)
  (delete-created-posts!))