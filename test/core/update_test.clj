(ns core.update-test
  (:require [clojure.test :refer :all]
            [core.util.http-client :as hc]
            [core.util.test-util :as tu]))

(defn assert-update-response-body [expected-id expected-text {:keys [id text] :as response-body}]
  (are [actual expected] (= actual expected)
                         id expected-id
                         text expected-text))

(defn assert-updated [post-id expected-author expected-new-text]
  (let [{:keys [status body]} (hc/find-by-id post-id)
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
    (->> (hc/update! update-info)
         :body
         (assert-update-response-body id text))
    (assert-updated id author text)))





