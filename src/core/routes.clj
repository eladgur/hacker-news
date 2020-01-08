(ns core.routes
  (:require [schema.core :as s]
            [core.models :refer [Post]]
            [core.util.string-util :as su]
            [core.handlers :as handlers]
            [compojure.api.sweet :refer [GET POST PUT DELETE api routes]]
            [core.config :as config]))

(def max-text-size 1000)
(def find-by-id-endpoint "/api/posts/:id")
(def create-and-update-endpoint "/api/posts")
(def upvote-endpoint "/api/posts/upvote")
(def downvote-endpoint "/api/posts/downvote")

(s/defschema OnlyIdSchema
  {:id s/Int})

(s/defschema CreateSchema
  {:author (s/constrained s/Str #(su/non-blank-with-max-length? 30 %))
   :text   (s/constrained s/Str #(su/non-blank-with-max-length? max-text-size %))})

(s/defschema UpdateSchema
  {:id   s/Int
   :text (s/constrained s/Str #(su/non-blank-with-max-length? max-text-size %))})

(def post-entity-routes
  (api {:swagger config/swagger-config}
       [(GET find-by-id-endpoint  [id]
          :path-params [id :- s/Int]
          (handlers/find-by-id-handler id))

        (POST create-and-update-endpoint http-req
          :body [req-body CreateSchema]
          (handlers/create-handler req-body))

        (PUT create-and-update-endpoint http-req
          :body [req-body UpdateSchema]
          (handlers/update-handler req-body))

        (POST upvote-endpoint http-req
          :body [req-body OnlyIdSchema]
          (handlers/upvote-handler req-body))

        (POST downvote-endpoint http-req
          :body [req-body OnlyIdSchema]
          (handlers/downvote-handler req-body))]))
