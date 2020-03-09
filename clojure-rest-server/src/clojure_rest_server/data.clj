(ns clojure-rest-server.data
  (:require [clojure.string :as str])
  (:gen-class))

(defstruct User :id :firstname :lastname)
(def users (atom {}))

(defn add-user
  [id firstname lastname]
  (def user (struct User id (str/capitalize firstname) (str/capitalize lastname)))
  (swap! users conj {id user}))

(defn get-all-users
  []
  (vals @users))

(defn get-user
  [id]
  (get @users id))
