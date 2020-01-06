(ns core.routes
  (:require [schema.core :as s]
            [core.models :refer [Post]]
            [core.util.string-util :as su]
            [core.handlers :as handlers]
            [compojure.api.sweet :refer [GET POST PUT DELETE api routes]]
            [core.config :as config]))

(def max-text-size 1000)
(def find-by-id-endpoint "/api/posts/find-by-id")
(def create-and-update-endpoint "/api/posts")
(def base-url "http://localhost:3000")

(def find-by-id-url (str base-url find-by-id-endpoint))
(def create-and-update-url (str base-url create-and-update-endpoint))

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
       [(POST find-by-id-endpoint http-req
          :body [req-body OnlyIdSchema]
          (handlers/find-by-id-handler req-body))

        (POST create-and-update-endpoint http-req
          :body [req-body CreateSchema]
          (handlers/create-handler req-body))

        (PUT create-and-update-endpoint http-req
          :body [req-body UpdateSchema]
          (handlers/update-handler req-body))]))
