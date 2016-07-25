(ns messages.models.user
  (:require [clojure.java.jdbc :as jdbc]
            [messages.config :as config])
  (:import (org.mindrot.jbcrypt BCrypt)))

;; List all users

(defn all []
  (into [] (jdbc/query config/connection-string
                       ["select * from users"])))

;; Validate user input

(defn valid-create-input? [user]
  (and (string? (get user "username"))
       (string? (get user "email"))
       (string? (get user "password"))
       (>= (count (get user "username")) 3)
       (<= (count (get user "username")) 30)
       (>= (count (get user "password")) 6)
       (<= (count (get user "password")) 30)
       (not= (re-matches #"^\S+@\S+\.\S+$" (get user "email")) nil)))

;; Find users where value matches a given key

(defn find-where [field value]
  (println (str "select * from users where " field " = '" value "'"))
  (into [] (jdbc/query config/connection-string
                       [(str "select * from users where " field " = '" value "'")])))

;; Serialize user

(defn serialize [user]
  (select-keys user [:username :email :id]))

;; Create a new user

(defn create! [user]
  (if (not (valid-create-input? user))
    (throw (Exception. "Invalid input")))
  (let [hashed (BCrypt/hashpw (get user "password")(BCrypt/gensalt))]
    (jdbc/insert! config/connection-string
                  :users
                  [:email :username :password]
                  [(get user "email") (get user "username") hashed])
    (let [created-users (find-where "email" (get user "email"))]
      (serialize (get created-users 0)))))
