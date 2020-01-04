(ns hack-news.create-test
  (:require [clojure.test :refer :all]
            [clj-http.client :as client]
            [hack-news.test-util :refer :all]))

(deftest create-test
  (let [d-author "dummy author"
        d-text "dummay text"
        post {:author d-author
              :text   d-text}
        {:keys [status body]} (create-post post)
        {:keys [votes text id author create_on]} body]

    (are [actual expected] (= actual expected)
                           status 201
                           votes 0
                           author d-author
                           text d-text)))
