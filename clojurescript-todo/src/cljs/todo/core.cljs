(ns todo.core
  (:require
   [reagent.core :as reagent]
   [re-frame.core :as rf]
   [com.degel.re-frame-firebase :as firebase]
   [todo.events :as events]
   [todo.routes :as routes]
   [todo.views :as views]
   [todo.config :as config]
   ))


(defn dev-setup []
  (when config/debug?
    (println "############# dev mode")))

(defn ^:dev/after-load mount-root []
  (rf/clear-subscription-cache!)
  (reagent/render [views/main]
                  (.getElementById js/document "app")))

(defn ^:export firebase-init []
  (firebase/init :firebase-app-info      config/firebase-app-info
                 :get-user-sub           [:user]
                 :set-user-event         [:set-user]
                 :default-error-handler  [:show-error]))

(defn init []
  (routes/app-routes)
  (rf/dispatch-sync [::events/initialize-db])
  (dev-setup)
  (firebase-init)
  (mount-root))
