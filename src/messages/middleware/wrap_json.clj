(ns messages.middleware.wrap-json)

;; Add json headers to response

(defn wrap-json [handler]
  (fn [request]
    (let [response (handler request)]
      (assoc-in response [:headers "Content-Type"] "application/json"))))
