(defproject messages "0.1.0-SNAPSHOT"
  :description "User accounts and messages"
  :url "https://danielpquinn.com"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/data.json "0.2.6"]
                 [org.clojure/java.jdbc "0.6.2-alpha1"]
                 [org.postgresql/postgresql "9.4-1201-jdbc41"]
                 [ring/ring-core "1.4.0"]
                 [ring/ring-jetty-adapter "1.4.0"]
                 [org.mindrot/jbcrypt "0.3m"]
                 [compojure "1.5.1"]]
  :main messages.core)
