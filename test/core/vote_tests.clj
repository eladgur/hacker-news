(ns core.vote-tests
  (:require [clojure.test :refer :all]
            [core.util.test-util :as tu]))

(defn assert-num-of-votes [id expected-votes]
  (let [votes (get-in (tu/find-by-id id) [:body :votes])]
    (is (= votes expected-votes))))

(deftest update-test
  (let [post {:author "Author"
              :text   "Text"}
        id (tu/create-post post)]
    (tu/upvote id)
    (assert-num-of-votes id 1)
    (tu/downvote id)
    (assert-num-of-votes id 0)))
