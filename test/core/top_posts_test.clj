(ns core.top-posts-test
  (:require [clojure.test :refer :all]
            [core.util.http-client :as hc]
            [core.util.test-util :as tu]
            [toucan.db :as db]
            [core.models :refer [Post]]
            [core.cache :as cache]))

(use-fixtures :once tu/test-fixture)

(defn ->post [x]
  (let [text (str "dummy" x)
        post {:author text
              :text   text}]
    (tu/create-post post)))

(defn contains-same-elements? [actual expected]
  (let [actual-set (into #{} actual)
        expected-set (into #{} expected)]
    (= actual-set expected-set)))

(defn query-top-posts []
  (db/query {:select   [:id]
             :from     [Post]
             :order-by [:score]
             :limit    cache/page-size}))

(defn fill-db-if-empty []
  (when (= 0 (db/count Post)
           (->> (range 30)
                (map ->post)))))


(defn clean-if-inserted [inserted-ids]
  (some->> inserted-ids
           (map #(db/delete! Post :id %))))

(deftest top-posts-test
  (let [inserted-ids (fill-db-if-empty)
        expected-ids (map :id (query-top-posts))
        actual-ids (->> (hc/get-top-posts)
                        :body
                        (map :id))]
    (clean-if-inserted inserted-ids)
    (is (contains-same-elements? actual-ids expected-ids))))
