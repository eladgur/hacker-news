(ns core.update-test
  (:require [clojure.test :refer :all]
            [clj-http.client :as client]
            [core.util.test-util :as tu]))

(defn assert-update-response-body [post-id {:keys [id status] :as response-body}]
  (are [actual expected] (= actual expected)
                         status "updated"
                         id post-id))

(defn assert-updated [post-id expected-author expected-new-text]
  (let [{:keys [status body]} (tu/find-by-id post-id)
        {:keys [id author text]} body]
    (are [actual expected] (= actual expected)
                           status 200
                           id post-id
                           author expected-author
                           text expected-new-text)))

(deftest update-test
  (let [author "author - before"
        post {:author author
              :text   "text - before"}
        id (tu/create-post post)
        text "text - after"
        update-info {:id   id
                     :text text}]
    (->> (tu/update! update-info)
         :body
         (assert-update-response-body id))
    (assert-updated id author text)))





