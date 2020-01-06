(ns core.util.test-util
  (:require [clojure.test :refer :all]
            [clj-http.client :as client]
            [clojure.data.json :as json]
            [core.routes :as r]))

(defn req-body->req-config [req-body]
  {:form-params  req-body
   :content-type :json
   :as           :json})

(defn find-by-id [id]
  (->> (req-body->req-config {:id id})
       (client/post r/find-by-id-url)))

(defn create! [req-body]
  (->> (req-body->req-config req-body)
       (client/post r/create-and-update-url)))

(defn update! [req-body]
  (->> (req-body->req-config req-body)
       (client/put r/create-and-update-url)))

(defn upvote [id]
  (->> (req-body->req-config {:id id})
       (client/post r/upvote-url)))

(defn downvote [id]
  (->> (req-body->req-config {:id id})
       (client/post r/downvote-url)))

(defn create-post [post]
  (let [{:keys [status body]} (create! post)
        post-id (:id body)]
    (is (= status 201))
    post-id))




