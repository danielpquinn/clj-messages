(ns messages.models.migration
  (:require [clojure.java.jdbc :as jdbc]
            [messages.config :as config]))

;; Bootstraps application database

;; Utility functions

(defn table-exists? [table]
  (-> (jdbc/query config/connection-string
                  [(str "select count(*) from information_schema.tables "
                        "where table_name='" table "'")])
      first :count pos?))

(defn drop-table! [name key]
  (if (table-exists? name)
    (do
      (jdbc/db-do-commands config/connection-string
                           (jdbc/drop-table-ddl key))
      (println (str "Dropped table " name)))))

;; Drop existing tables

(drop-table! "messages" :messages)
(drop-table! "tokens" :tokens)
(drop-table! "users" :users)

;; Create users table

(jdbc/db-do-commands config/connection-string
                     (jdbc/create-table-ddl
                       :users
                       [[:id :serial "PRIMARY KEY"]
                        [:email :varchar "UNIQUE" "NOT NULL"]
                        [:username :varchar "UNIQUE" "NOT NULL"]
                        [:password :varchar "NOT NULL"]
                        [:email_verified :boolean "NOT NULL" "DEFAULT FALSE"]
                        [:created_at :timestamp "NOT NULL" "DEFAULT CURRENT_TIMESTAMP"]]))

(println "Created users table")

;; Create messages table

(jdbc/db-do-commands config/connection-string
                     (jdbc/create-table-ddl
                       :messages
                       [[:id :serial "PRIMARY KEY"]
                        [:body :varchar "NOT NULL"]
                        [:user_id :integer "NOT NULL" "REFERENCES users (id)" "ON DELETE CASCADE"]
                        [:created_at :timestamp "NOT NULL" "DEFAULT CURRENT_TIMESTAMP"]]))

;; Create tokens table

(jdbc/db-do-commands config/connection-string
                     (jdbc/create-table-ddl
                       :tokens
                       [[:id :serial "PRIMARY KEY"]
                        [:token :varchar "NOT NULL"]
                        [:user_id :integer "NOT NULL" "REFERENCES users (id)" "ON DELETE CASCADE"]]))

(println "Created messages table")
