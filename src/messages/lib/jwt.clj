(ns messages.lib.jwt
  (:require [clojure.string :as str]
            [clojure.data.json :as json]))

;; Note: This does not implement the JWT standard. We have no need for
;; supporting mutliple hashing algorithms and we can assume that the
;; token's type is "JWT". This means we can omit the token header

;; Algorithm used to create signature

(def algorithm "HmacSHA256")

;; Use java base64 util to encode token segments

(def encoder (java.util.Base64/getUrlEncoder))

;; Convert a string to a byte array and use encoder to get base64 url encoded string

(defn encode-base64url [string]
  (.encodeToString encoder (.getBytes string)))

;; Generate a signature given a secret and some data

(defn generate-signature [secret data]
  (let [mac (javax.crypto.Mac/getInstance algorithm)
        key (javax.crypto.spec.SecretKeySpec. (.getBytes secret) algorithm)]
          (.init mac key)
          (.encodeToString encoder (.doFinal mac (.getBytes data)))))

;; Generate a token

(defn generate-token [secret payload]
  (let [encoded-payload (encode-base64url payload)
        signature (generate-signature secret encoded-payload)]
          (str encoded-payload "." signature)))

;; Validate token signature

(defn validate-token [secret token]
  (let [parts (str/split token #"\.")
        payload (first parts)
        signature (last parts)]
        (= (generate-signature secret payload) signature)))
