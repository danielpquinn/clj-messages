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

;; Drop existing tables

(if (table-exists? "messages")
  (do
    (jdbc/db-do-commands config/connection-string
                         (jdbc/drop-table-ddl :messages))
    (println "Dropped table messages")))

(if (table-exists? "users")
  (do
    (jdbc/db-do-commands config/connection-string
                         (jdbc/drop-table-ddl :users))
    (println "Dropped table users")))

;; Create users table

(jdbc/db-do-commands config/connection-string
                     (jdbc/create-table-ddl
                       :users
                       [[:id :serial "PRIMARY KEY"]
                        [:email :varchar "UNIQUE" "NOT NULL"]
                        [:username :varchar "NOT NULL"]
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

;; Create an admin user

(jdbc/insert! config/connection-string
              :users
              [:email :username :password]
              ["admin@messages.com" "admin" "admin"])

(println "Created messages table")
