(ns clojure-rest-server.core
  (:require [compojure.core :refer :all]
            [clojure-rest-server.data :as data]
            [org.httpkit.server :as server]
            [compojure.route :as route]
            [ring.middleware.defaults :refer :all]
            [clojure.pprint :as pp]
            [clojure.string :as str]
            [clojure.data.json :as json])
  (:gen-class))

; Single page
(defn single-page [req]
  {:status  200
   :headers {"Content-Type" "text/html"}
   :body    "<div><h1>Hello World</h1></div>"})

; api-root
(defn api-root [req]
     {:status  200
      :headers {"Content-Type" "application/json"}
      :body    "{\"msg\": \"api root - nothing interesting\"}"})

; api-say-hi
(defn api-say-hi [req]
      {:status  200
       :headers {"Content-Type" "application/json"}
       :body    (-> (format "{\"msg\": \"hi %s\"}" (:name (:params req))))})

; api-get-all-users
(defn api-get-all-users [req]
      {:status  200
       :headers {"Content-Type" "application/json"}
       :body    (-> (do
                      (def users (data/get-all-users))
                      (json/write-str users)))})

; api-get-user
(defn api-get-user [id]
      {:status  200
       :headers {"Content-Type" "application/json"}
       :body    (-> (data/get-user id))})

; api-create-user
(defn api-create-user [req]
      {:status  200
       :headers {"Content-Type" "application/json"}
       :body    (-> (do
                      (println "req="req)
                      (def id (:id (:params req)))
                      (println "id="id)))})

;;(data/add-user (:id (:params req)) (:firstname (:params req)) (:lastname (:params req)))


;; Define routes
(defroutes app-routes
  (GET "/" [] single-page)
  (GET "/api" [] api-root)
  (GET "/api/say-hi" [] api-say-hi)
  (GET "/api/user" [] api-get-all-users)
  (GET "/api/user/:id" [id] (api-get-user id))
  (POST "/api/user" [] api-create-user)
  (route/not-found "Error, page not found!"))

(defn -main
  "This is our main entry point"
  [& args]
  (let [port (Integer/parseInt (or (System/getenv "PORT") "3000"))]
    ; Run the server with Ring.defaults middleware
    (server/run-server (wrap-defaults #'app-routes (assoc-in site-defaults [:security :anti-forgery] false)) {:port port})
    ; Run the server without ring defaults
    ;(server/run-server #'app-routes {:port port})
    (println (str "Running webserver at http:/127.0.0.1:" port "/"))))
