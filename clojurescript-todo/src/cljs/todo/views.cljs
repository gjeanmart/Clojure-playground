(ns todo.views
  (:require
   [re-frame.core :as rf]
   [todo.events :as events]
   [todo.subs :as subs]
   [todo.components.modal :as modal]
   [todo.pages.home-page :as home-page]
   [todo.pages.settings-page :as settings-page]
   [todo.pages.about-page :as about-page]
   [todo.pages.list-page :as list-page]))

(defn- render-page [active-page]
  (let [page (:page active-page)]
    (case page
      :home-page [home-page/render]
      :list-page [list-page/render (:id active-page)]
      :settings-page [settings-page/render]
      :about-page [about-page/render]
      [:div])))

(defn- render-header-bar
  "Render header bar"
  [] 
  (fn [active-page list]
    (let [page (:page active-page)
          id (:id active-page)
          next-id (count list)]
      [:nav {:class "navbar navbar-expand-lg navbar-light bg-light border-bottom"}

       [:button {:class "navbar-toggler"
                 :type "button"
                 :data-toggle "collapse"
                 :data-target "#navbarSupportedContent"
                 :aria-controls "navbarSupportedContent"
                 :aria-expanded "false" 
                 :aria-label "Toggle navigation"}
        [:span {:class "navbar-toggler-icon"}]]

       [:div {:class "collapse navbar-collapse" :id "navbarSupportedContent"}
        [:ul {:class "navbar-nav ml-auto mt-2 mt-lg-0"}
         
         [:div {:class "only-mobile"}
          (map #(vector :li {:class "nav-item"} 
                        [:a { ;:key (str "header-list-" (:id %))
                             :href (str "#/list/" (:id %))
                             :style {:overflow "hidden"}
                             :class (str "nav-link " (when (and (= page :list-page) (= (str id) (str (:id %)))) "active"))}
                         (:name %)]) list)
          [:div {:class "dropdown-divider"}]
          [:li {:class "nav-item"}
           [:span {:class "nav-link cursor-pointer" :on-click #(rf/dispatch [::events/show-modal [modal/add-list-modal next-id]])} "Add new list"]]
          [:div {:class "dropdown-divider"}]]
         
         [:li {:class "nav-item"} [:a {:class "nav-link" :href "#"} "Home"]]
         [:li {:class "nav-item"} [:a {:class "nav-link" :href "#/settings"} "Settings"]]
         [:li {:class "nav-item"} [:a {:class "nav-link" :href "#/about"} "About"]]]]])))

(defn- render-sidebar
  "Render component sidebar"
  [] 
  (fn [active-page list]
    (let [page (:page active-page)
          id (:id active-page)
          next-id (count list)]
      [:div {:class "only-desktop bg-light border-right" :id "sidebar-wrapper"}
       [:div {:class "sidebar-heading d-flex justify-content-between align-items-center"}
        [:a {:href "/#"} "(to-do)"] ;TODO add logo
        [:span {:class "fas fa-plus float-right cursor-pointer"
                :title "Add list"
                :on-click #(rf/dispatch [::events/show-modal [modal/add-list-modal next-id]])}]]
       [:div {:class "list-group list-group-flush"}
        (if-not (empty? list)
          (map #(vector :a {:key (str "sidebar-list-" (:id %))
                            :href (str "#/list/" (:id %))
                            :style {:overflow "hidden"}
                            :class (str "list-group-item list-group-item-action "
                                        (when (and (= page :list-page) (= (str id) (str (:id %)))) "active"))}
                        (:name %)) list)
          [:div {:class "d-flex justify-content-center mt-2"}
           [:span {:class "fas fa-circle-notch fa-pulse fa-3x fa-fw"}]])]])))

(defn main []
  (let [active-page (rf/subscribe [::subs/active-page])
        list (rf/subscribe [:firebase/on-value {:path [:list]}])
        visible-list (filterv #(= (:visible %) true) @list)
        modal (rf/subscribe [::subs/modal])]
    
    [:div {:class "d-flex mb-2" :id "wrapper"}
     (when (:show @modal) (:content @modal))

     [render-sidebar @active-page visible-list]

     [:div {:id "page-content-wrapper"}
      [render-header-bar @active-page visible-list]
      [render-page @active-page]]]))
