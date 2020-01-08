(ns core.create-test
  (:require [clojure.test :refer :all]
            [core.util.http-client :as hc]))

(deftest create-test
  (let [d-author "dummy author"
        d-text "dummay text"
        post {:author d-author
              :text   d-text}
        {:keys [status body]} (hc/create! post)
        {:keys [votes text id author create_on]} body]

    (are [actual expected] (= actual expected)
                           status 201
                           votes 0
                           author d-author
                           text d-text)))
