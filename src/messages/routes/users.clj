(ns messages.routes.users
  (:require [clojure.data.json :as json]
            [compojure.core :refer [GET POST defroutes]]
            [compojure.response :as response]
            [messages.models.user :as user]))

;; Define user related routes

(defroutes routes

  ;; List users
  (GET "/users" [request]
    (json/write-str (map #(assoc % :created_at (str (:created_at %)))
                         (user/all))))

  ;; Create a new user
  (POST "/signup" {body :body}
    (println body)
    (let [input (json/read-str (slurp body))]
      (json/write-str (user/create! input)))))
