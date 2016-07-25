(ns messages.routes.users
  (:require [clojure.data.json :as json]
            [compojure.core :refer [GET POST defroutes]]
            [compojure.response :as response]
            [messages.models.user :as user]))

;; Define user related routes

(defroutes routes

  ;; List users
  (GET "/api/v1/users" [request]
    (json/write-str (map #(assoc % :created_at (str (:created_at %)))
                         (user/all))))

  ;; Create a new user
  (POST "/api/v1/signup" {body :body}
    (let [user (user/create! (json/read-str (slurp body)))]
      (json/write-str (user/serialize user)))))
