(ns messages.middleware.wrap-cors)

;; Allow access from any origin

(defn wrap-cors [handler]
  (fn [request]
    (let [response (handler request)]
      (assoc-in response [:headers "Access-Control-Allow-Origin"] "*"))))
