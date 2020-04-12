(ns todo.css
  (:require [garden.def :refer [defstyles]]
            [garden.stylesheet :refer [at-media]]))

(defstyles screen
  [:body {:overflow-x "hidden"}]
  [:.only-mobile
   {:display "block"}]
  [:.only-desktop
   {:display "none"}]
  [:#sidebar-wrapper
   {:min-height "100vh"}
   {:margin-left "-15rem"}
   {:-webkit-transition "margin .25s ease-out"}
   {:-moz-transition "margin .25s ease-out"}
   {:-o-transition "margin .25s ease-out"}
   {:transition "margin .25s ease-out"}
   [:.sidebar-heading
    {:padding "0.875rem 1.25rem"}
    [:a
     {:text-decoration "none"}
     {:font-size "1.2rem"}]]
   [:.list-group
    {:width "15rem"}]]
  [:#page-content-wrapper
   {:min-width "100vw"}]
  [:#wrapper.toggled
   [:#sidebar-wrapper
    {:margin-left 0}]]

  (at-media {:min-width "768px"}
            [:.only-mobile
             {:display "none"}]
            [:.only-desktop
             {:display "block"}]
            [:#sidebar-wrapper
             {:margin-left 0}]
            [:#page-content-wrapper
             {:min-width 0}
             {:width "100%"}]
            [:#wrapper.toggled
             [:#sidebar-wrapper
              {:margin-left "-15rem"}]])

  [:.sidebar-item-active
   {:color "#007bff"}]

  [:.task
   [:label]
   [:.done
    {:text-decoration "line-through"}]
   [:input
    {:margin-right "15px"}]]

  [:.cursor-pointer
   {:cursor "pointer"}])
