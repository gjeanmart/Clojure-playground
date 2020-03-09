(ns clojure-rest_client.api_client
  (:require [cheshire.core :as json])
  (:require [clj-http.client :as http])
  (:gen-class))

(def api-endpoint "https://reqres.in/api")

(defstruct User :Email :FirstName :LastName)

(defn get-user
  [id]
  (try
    (do
      (def response (http/get (format "%s/users/%s" api-endpoint id)))
      (let [{data :data} (json/parse-string (:body response) true)]
        (struct User (:email data) (:first_name data) (:last_name data))))
    (catch Exception ex
      (do
        (println "Exception while calling api" (.getMessage ex))
        (throw (Exception. (format "api-client exception (code: 001, msg: %s)" (.getMessage ex))))))))
