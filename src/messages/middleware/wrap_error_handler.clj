(ns messages.middleware.wrap-error-handler)

;; Catch errors thrown in handlers and create an appropriate response

(defn wrap-error-handler [handler]
  (fn [request]
    (try (handler request)
         (catch Exception e
                {:status 400 :body (.getMessage e)}))))
