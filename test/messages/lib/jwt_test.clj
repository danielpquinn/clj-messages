(ns messages.lib.jwt-test
  (:require [clojure.test :refer :all]
            [clojure.string :as str]
            [messages.lib.jwt :as jwt]))

;; Test base 64 url encode function

(deftest encode-base64url
  (testing "jwt/encode-base64url"
    (is (= "dGVzdGluZw==" (jwt/encode-base64url "testing")))))

;; Test token generation

(deftest generate-token
  (testing "jwt/generate-token"
    (is (= (jwt/generate-token "foo" "{\"some\":\"json\"}") "eyJzb21lIjoianNvbiJ9.I9CFuG67fqDjs1ogZm3Ky3c_qYDSN8V5vxSWCG4OSS8="))))

;; Test token validation

(deftest validate-token-valid
  (testing "jwt/validate-token is valid"
    (let [token (jwt/generate-token "foo" "{\"some\":\"json\"}")]
      (is (= (jwt/validate-token "foo" token) true)))))

;; Test that token that has been tempered with is invalid

(deftest validate-token-invalid
  (testing "jwt/validate-token is invalid"
    (let [token (jwt/generate-token "foo" "{\"some\":\"json\"}")
          signature (last (str/split token #"\."))
          tampered-token (str (jwt/encode-base64url "{\"bad\":\"payload\"}") "." signature)]
            (is (= (jwt/validate-token "foo" tampered-token) false)))))
