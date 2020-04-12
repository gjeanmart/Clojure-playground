(ns todo.components.modal
    (:require
     [reagent.core :as reagent]
     [re-frame.core :as rf]
     [cuerdas.core :as str]
     [todo.events :as events]
     [todo.utils.common :as common]))

(defn- render
  "Render modal"
  []
  (fn [title close confirm body]
    [:div {:class "modal fade modal-fullscreen" :id "global-modal" :tabIndex "-1" :role "dialog" :data-backdrop "static" :data-keyboard "false"}
     [:div {:class "modal-dialog" :role "document"}
      [:div {:class "modal-content"}
       [:div {:class "modal-header"}
        [:h5 {:class "modal-title" :id "global-modal-label"} title]
        [:button {:type "button" :class "close" :on-click (:fn close)}
         [:span {:class "fa fa-window-close" :aria-hidden "true"}]]]
       [:div {:class "modal-body"}
        [body]]
       [:div {:class "modal-footer"}
        [:button {:type "button" :class "btn btn-secondary" :on-click (:fn close)} (:label close)]
        [:button {:type "button" :class "btn btn-primary" :on-click (:fn confirm) :disabled (:disabled confirm)} (:label confirm)]]]]]))


(defn add-list-modal
  "Modal to add/edit a new list"
  []
  (fn [id & [inital-value]]
    (let [name (reagent/atom (if (nil? inital-value) "" inital-value))
          callback #(do (rf/dispatch [::events/hide-modal] (reset! name "")))
          is-update? (if (nil? inital-value) false true)
          confirm (if is-update?
                    #(rf/dispatch [::events/update-list-field id :name @name callback])
                    #(rf/dispatch [::events/add-new-list id name callback]))]
      (fn []
        [render
         "Add a new list"
         {:label "Close" :fn callback}
         {:label "Confirm" :fn confirm 
          ;:disabled (str/blank? @name)
          }
         (fn []
           [:form
            [:div {:class "form-group"}
             [:label {:for "list-name" :class "col-form-label"} "Name"]
             [:input {:type "text"
                      :class "form-control"
                      :id "list-name"
                      :value @name
                      :on-change #(reset! name (-> % .-target .-value))}]]])]))))

(defn confirm-delete-task-modal
  "Modal to confirm deletion of a task"
  []
  (fn [list-id id]
    (let [close #(rf/dispatch [::events/hide-modal])]
      [render
       "Delete task"
       {:label "No" :fn close}
       {:label "Yes" :fn #(rf/dispatch [::events/update-task-field list-id id :visible false close])}
       (fn []
         [:div "Are you sure you want to delete this task?"])])))

(defn confirm-delete-list-modal
  "Modal to confirm deletion of a list"
  []
  (fn [list-id]
    (let [callback #(common/redirect "/")
          close #(rf/dispatch [::events/hide-modal])]
      [render
       "Delete list"
       {:label "No" :fn close}
       {:label "Yes" :fn #(rf/dispatch [::events/update-list-field list-id :visible false (do (close) (callback))])}
       (fn []
         [:div "Are you sure you want to delete this task?"])])))