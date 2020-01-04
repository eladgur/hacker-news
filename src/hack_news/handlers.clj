(ns hack-news.handlers
  (:require [compojure.api.sweet :refer [GET POST PUT DELETE routes]]
            [toucan.db :as db]
            [schema.core :as s]
            [ring.util.http-response :refer [ok not-found created]]
            [hack-news.models :refer [Post]]
            [hack-news.string-util :as str]
            [clj-time.coerce :as tc]
            [clj-time.core :as t]))

(defn id->created [name {:keys [id] :as m}]
  (created (str "/" name "/" id) m))

(s/defschema PostRequestSchema
  {:author (s/constrained s/Str #(str/non-blank-with-max-length? 30 %))
   :text   (s/constrained s/Str #(str/non-blank-with-max-length? 1000 %))})

(defn resource-id-path [name]
  (str "/" name "/:id"))

(defn create-handler [req-body]
  (->> (assoc req-body :created_on (tc/to-timestamp (t/now))
                       :votes 0)
       (db/insert! Post)
       (id->created "posts")))

(defn update-handler [id req-body]
  (db/update! Post id req-body)
  (ok (assoc req-body :id id)))
