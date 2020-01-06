(ns core.handlers
  (:require [compojure.api.sweet :refer [GET POST PUT DELETE routes]]
            [toucan.db :as db]
            [schema.core :as s]
            [ring.util.http-response :refer [ok not-found created]]
            [core.models :refer [Post]]
            [clj-time.coerce :as tc]
            [clj-time.core :as t]))

(defn id->created [name {:keys [id] :as m}]
  (created (str "/" name "/" id) m))

(defn find-by-id-handler [{:keys [id]}]
  (if-let [post (Post id)]
    (ok post)
    (not-found)))

(defn create-handler [req-body]
  (->> (assoc req-body :created_on (tc/to-timestamp (t/now))
                       :votes 0)
       (db/insert! Post)
       (id->created "posts")))

(defn update-handler [{:keys [id] :as req-body}]
  (if (db/update! Post id req-body)
    (ok {:id     id
         :status :updated})
    (not-found)))
