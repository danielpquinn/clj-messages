(ns messages.config)

;; Database config

(def config {:psql-host "127.0.0.1"
             :psql-port "5432"
             :psql-user "postgres"
             :psql-db "messages"
             :psql-password ""})

;; Build up database connection string

(def connection-string (str "postgresql://"
                            (:psql-user config) ":"
                            (:psql-password config) "@"
                            (:psql-host config) ":"
                            (:psql-port config) "/"
                            (:psql-db config)))
