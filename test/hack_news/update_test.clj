(ns hack-news.update-test
  (:require [clojure.test :refer :all]
            [clj-http.client :as client]
            [hack-news.test-util :refer :all]))

(deftest update-test
  (let [post {:author "author - before"
              :text   "text - before"}
        {:keys [status body]} (create-post post)
        post-id (:id body)
        _ (is (= status 201))
        new-author "author - after"
        new-text "text - after"
        new-post (assoc post :author new-author
                             :text new-text)
        route (str "/posts/" post-id)
        {:keys [status body]} (request http-put-fn route new-post)
        {:keys [text id author]} body]

    (are [actual expected] (= actual expected)
                           status 200
                           id post-id
                           author new-author
                           text new-text)))


