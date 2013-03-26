(ns hardhat.core
  (:require [clojure.string :as string]))

(deftype RawHTML [html]
  Object
  (toString [_] html))

(defn keyword-name
  [kwd]
  (apply str (drop 1 (str kwd))))

(defn escape
  "Escape all html in a string"
  [html]
  (->
    html
    (string/replace "<" "&lt;")
    (string/replace ">" "&gt;")))

(defmulti convert-to-tag class)

(defmethod convert-to-tag clojure.lang.IPersistentVector
  [[tag-name & content]]
  (let [open ["<" (keyword-name tag-name) ">"] close ["</" (keyword-name tag-name) ">"]]
    (string/join (concat open (map convert-to-tag content) close))))

(defmethod convert-to-tag String
  [string]
  (escape string))

(defmethod convert-to-tag RawHTML
  [raw-html]
  (.html raw-html))

(defn html
  "Create an html string"
  [param]
  (->RawHTML (convert-to-tag param)))
