(ns core.util.http-client
  (:require [clj-http.client :as client]
            [core.routes :as r]
            [clojure.string :as str]))

(def base-url "http://localhost:3000")
(def find-by-id-url (str base-url r/find-by-id-endpoint))
(def create-and-update-url (str base-url r/create-and-update-endpoint))
(def upvote-url (str base-url r/upvote-endpoint))
(def downvote-url (str base-url r/downvote-endpoint))

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
