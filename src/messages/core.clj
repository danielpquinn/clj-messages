(ns messages.core
  (:require [ring.adapter.jetty :as jetty]
            [compojure.handler :as handler]
            [compojure.core :refer :all]
            [messages.middleware.wrap-json :as wrap-json]
            [messages.middleware.wrap-cors :as wrap-cors]
            [messages.middleware.wrap-error-handler :as wrap-error-handler]
            [messages.routes.messages :as messages]
            [messages.routes.users :as users]))

;; Define app routes

(defroutes app-routes
  messages/routes
  users/routes)

(def app (-> (handler/api app-routes)
  wrap-json/wrap-json
  wrap-cors/wrap-cors
  wrap-error-handler/wrap-error-handler))

(jetty/run-jetty app {:port 3000})

