(ns todo.pages.settings-page
    (:require
     [re-frame.core :as rf]))

(defn render
  "Render page Settings"
  []
  [:div {:class "container-fluid"}
   [:h1 {:class "mt-2"} "Settings"]])