(ns todo.pages.about-page
    (:require
     [re-frame.core :as rf]))

(defn render
  "Render page About"
  []
  [:div {:class "container-fluid"}
   [:h1 {:class "mt-2"} "About"]])