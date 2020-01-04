(ns hack-news.test-util
  (:require [clj-http.client :as client]
            [clojure.data.json :as json]))

(def base-url "http://localhost:3000")

(def http-post-fn client/post)
(def http-put-fn client/put)

(defn ->json [text]
  (some-> text
          (json/read-str :key-fn keyword)))

(defn request [http-method-fn route req-body]
  (some-> (http-method-fn (str base-url route) {:form-params req-body :content-type :json})
          (update :body ->json)))

(defn create-post [post]
  (request http-post-fn "/posts" post))
