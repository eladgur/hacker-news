(ns core.util.http-client
  (:require [clj-http.client :as client]
            [core.routes :as r]
            [clojure.string :as str]
            [clojure.data.json :as json]))

(def base-url "http://localhost:3000")

(defn ->url [end-point]
  (str base-url end-point))

(def find-by-id-url (->url r/find-by-id-endpoint))
(def create-and-update-url (->url r/create-and-update-endpoint))
(def upvote-url (->url r/upvote-endpoint))
(def downvote-url (->url r/downvote-endpoint))
(def top-posts-url (->url r/top-posts-endpoint))

(def req-config {:content-type :json
                 :as           :json})

(defn req-body->req-config [req-body]
  (assoc req-config :form-params req-body))

(defn req-with-json-body [req-body http-method create-and-update-url]
  (->> (req-body->req-config req-body)
       (http-method create-and-update-url)))

(defn find-by-id [id]
  (let [id-str (str id)]
    (-> (str/replace find-by-id-url #":id" id-str)
        (client/get req-config))))

(defn create! [req-body]
  (req-with-json-body req-body client/post create-and-update-url))

(defn update! [req-body]
  (req-with-json-body req-body client/put create-and-update-url))

(defn upvote [req-body]
  (req-with-json-body req-body client/post upvote-url))

(defn downvote [req-body]
  (req-with-json-body req-body client/post downvote-url))

(defn get-top-posts []
  (-> (client/get top-posts-url)
      (update :body #(json/read-str % :key-fn keyword))))

