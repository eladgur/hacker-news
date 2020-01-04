(ns hack-news.create-test
  (:require [clojure.test :refer :all]
            [clj-http.client :as client]
            [clojure.data.json :as json]))

(def base-url "http://localhost:3000")

(defn ->json [text]
  (some-> text
          (json/read-str :key-fn keyword)))

(deftest create-test
  (let [req-body {:author "dummy author"
                  :text   "dummay text "}
        {:keys [status body] :as response} (client/post (str base-url "/posts") {:form-params req-body :content-type :json})
        body (->json body)]
    (is (= status 201))
    (is (int? (:id body)))))
