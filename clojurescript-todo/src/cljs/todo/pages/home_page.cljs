(ns todo.pages.home-page
    (:require
     [re-frame.core :as rf]))

(defn render
  "Render page Home"
  []
  [:div {:class "container-fluid"}
   [:h1 {:class "mt-2"} "Home"]])