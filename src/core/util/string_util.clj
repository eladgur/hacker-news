(ns core.util.string-util
  (:require [clojure.string :as str]))

(def non-blank? (complement str/blank?))

(defn max-length? [length text]
  (<= (count text) length))

(defn non-blank-with-max-length? [length text]
  (and (non-blank? text) (max-length? length text)))
