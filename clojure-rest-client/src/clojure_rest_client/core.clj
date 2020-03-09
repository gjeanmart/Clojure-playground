(ns clojure-rest_client.core
  (:require
    [clojure-rest_client.api_client :as client])
  (:gen-class))

(defn -main
  [& args]
  (def id (first args))
  (def user (client/get-user id))
  (println "user " id " is " user))
