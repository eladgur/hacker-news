(ns core.util.test-util
  (:require [core.util.http-client :as hc]
            [clojure.test :refer :all]))

(defn create-post [post]
  (let [{:keys [status body]} (hc/create! post)
        post-id (:id body)]
    (is (= status 201))
    post-id))