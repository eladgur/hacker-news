(ns hack-news.test-util
  (:require [clj-http.client :as client]
            [clojure.data.json :as json]))

(def base-url "http://localhost:3000")

(def http-post-fn client/post)
(def http-get-fn client/get)

(defn ->json [text]
  (some-> text
          (json/read-str :key-fn keyword)))

(defn request [http-method-fn req-body]
  (some-> (http-method-fn (str base-url "/posts") {:form-params req-body :content-type :json})
          (update :body ->json)))

(defn create-post [post]
  (request http-post-fn post))
