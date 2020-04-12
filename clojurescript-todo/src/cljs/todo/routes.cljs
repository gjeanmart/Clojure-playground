(ns todo.routes
  (:import [goog History]
           [goog.history EventType])
  (:require-macros [secretary.core :refer [defroute]])
  (:require
   [secretary.core :as secretary]
   [goog.events :as gevents]
   [re-frame.core :as rf]
   [todo.events :as events]
   ))

(defn hook-browser-navigation! []
  (doto (History.)
    (gevents/listen
     EventType/NAVIGATE
     (fn [event]
       (secretary/dispatch! (.-token event))))
    (.setEnabled true)))

(defn app-routes []
  (set! (.-hash js/location) "/")      ;; on app startup set location to "/"
  (secretary/set-config! :prefix "#")
  ;; --------------------
  ;; define routes here
  
  (defroute "/" []
    (rf/dispatch [::events/set-active-page {:page :home-page}]))

  (defroute #"/list/(\d+)" [id]
    (rf/dispatch [::events/set-active-page {:page :list-page :id id}]))
  
  (defroute "/settings" []
    (rf/dispatch [::events/set-active-page {:page :settings-page}]))

  (defroute "/about" []
    (rf/dispatch [::events/set-active-page {:page :about-page}]))


  ;; --------------------
  (hook-browser-navigation!))
