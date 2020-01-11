(ns core.handlers
  (:require [compojure.api.sweet :refer [GET POST PUT DELETE routes]]
            [toucan.db :as db]
            [schema.core :as s]
            [ring.util.http-response :refer [ok not-found created internal-server-error]]
            [core.models :refer [Post]]
            [core.util.string-util :as str]
            [clj-time.coerce :as tc]
            [clj-time.core :as t]
            [honeysql.core :as hsql]
            [core.cache :as cache]))

(defn find-by-id-handler [id]
  (if-let [post (Post id)]
    (ok (select-keys post [:id :author :text :votes]))
    (not-found)))

(defn calc-score [post-creation-date votes]
  (let [curr-date (t/now)
        days-from-now (t/in-days (t/interval post-creation-date curr-date))
        date-score (* 1000 days-from-now)
        vote-score votes]
    (- vote-score date-score)))

(defn req-body->post [req-body]
  (let [date (t/now)
        timestamp (tc/to-timestamp date)
        votes 0
        score (calc-score date votes)]
    (assoc req-body :created_on timestamp
                    :votes votes
                    :score score)))

(defn create-handler [req-body]
  (if-let [{:keys [id] :as post} (db/insert! Post (req-body->post req-body))]
    (do (cache/update-async!)
        (->> (select-keys post [:id :text :votes :author])
             (created (str "/posts/" id))))
    (internal-server-error)))

(defn update-handler [{:keys [id] :as req-body}]
  (if (db/update! Post id req-body)
    (ok req-body)
    (not-found)))

(defn update-post-votes! [id hsql-op]
  (when (db/update! Post id {:votes (hsql/call hsql-op :votes 1)})
    {:id     id
     :status :votes-updated}))

(defn vote-handler [{:keys [id] :as req-body} vote-type]
  (if-let [return-map (update-post-votes! id vote-type)]
    (do
      (cache/update-async!)
      (ok return-map))
    (not-found)))

(defn top-posts []
  (if-let [posts (cache/get-top-posts)]
    (ok (map #(select-keys % [:id :text :votes]) posts))
    (internal-server-error)))
