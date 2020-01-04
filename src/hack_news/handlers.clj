(ns hack-news.handlers
  (:require [compojure.api.sweet :refer [GET POST PUT DELETE routes]]
            [toucan.db :as db]
            [schema.core :as s]
            [ring.util.http-response :refer [ok not-found created]]
            [hack-news.models :refer [Post]]
            [hack-news.string-util :as str]))

(defn id->created [name id]
  (created (str "/" name "/" id) {:id id}))

(s/defschema PostRequestSchema
  {:author (s/constrained s/Str #(str/non-blank-with-max-length? 30 %))
   :text   (s/constrained s/Str #(str/non-blank-with-max-length? 1000 %))})

(defn resource-id-path [name]
  (str "/" name "/:id"))

(defn create-handler [req-body]
  (->> (db/insert! Post req-body)
       :id
       (id->created "posts")))
