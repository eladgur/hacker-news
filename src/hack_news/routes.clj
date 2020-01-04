(ns hack-news.routes
  (:require [schema.core :as s]
            [hack-news.models :refer [Post]]
            [hack-news.string-util :as su]
            [clj-time.core :as t]
            [clj-time.coerce :as tc]
            [hack-news.handlers :as handlers]
            [compojure.api.sweet :refer [GET POST PUT DELETE api routes]]
            [hack-news.config :as config]))

(s/defschema PostRequestSchema
  {:author (s/constrained s/Str #(su/non-blank-with-max-length? 30 %))
   :text   (s/constrained s/Str #(su/non-blank-with-max-length? 1000 %))})

(def post-entity-routes
  (api {:swagger config/swagger-config}
       [(POST "/posts" http-req
          :body [req-body PostRequestSchema]
          (handlers/create-handler req-body))

        (PUT "/posts/:id" http-req
          :path-params [id :- s/Int]
          :body [req-body PostRequestSchema]
                (handlers/update-handler id req-body))]))
