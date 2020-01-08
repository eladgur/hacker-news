(ns core.vote-tests
  (:require [clojure.test :refer :all]
            [core.util.http-client :as hc]
            [core.util.test-util :as tu]))

(defn assert-num-of-votes [id expected-votes]
  (let [votes (get-in (hc/find-by-id id) [:body :votes])]
    (is (= votes expected-votes))))

(deftest update-test
  (let [post {:author "Author"
              :text   "Text"}
        id (tu/create-post post)
        m-id {:id id}]
    (hc/upvote m-id)
    (assert-num-of-votes id 1)
    (hc/downvote m-id)
    (assert-num-of-votes id 0)))
