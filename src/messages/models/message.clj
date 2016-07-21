(ns messages.models.message
  (:require [messages.config :as config]
            [clojure.java.jdbc :as jdbc]))

;; List all messages

(defn all []
  (into [] (jdbc/query config/connection-string ["select * from messages order by id desc"])))

;; Create a new message

(defn create [message]
  (jdbc/insert! config/connection-string :messages [:body] [message]))
