(ns messages.routes.messages
  (:require [compojure.core :refer [GET POST defroutes]]
            [clojure.data.json :as json]
            [messages.models.message :as message]))

;; Lists all messages

(defroutes routes
  (GET "/messages" []
    (json/write-str (message/all))))
