(ns todo.pages.list-page
    (:require
     [reagent.core :as reagent]
     [re-frame.core :as rf]
     [cuerdas.core :as str]
     [todo.events :as events]
     [todo.components.modal :as modal]
     [todo.utils.common :as common]))


(defn- render-task []
  (let [edit? (reagent/atom false)
        new-title (reagent/atom "")
        ]
    (fn [list-id task]
      (let [id (:id task)
            title (:title task)
            done (:done task)
            date-created (:date-created task)
            key (str "task-" list-id "-" id)
            update #(do (if-not @edit?
                          (reset! new-title title)
                          (rf/dispatch [::events/update-task-field list-id id :title @new-title]))
                        (reset! edit? (not @edit?)))
            delete #(rf/dispatch [::events/show-modal [modal/confirm-delete-task-modal list-id id]])]
        [:div {:key key
               :class "list-group-item d-flex justify-content-between align-items-center task"
                :style {:min-height "75px"}
               :on-click #(rf/dispatch [::events/update-task-field list-id id :done (not done)])}
         [:label {:class "d-flex align-items-center mb-0 cursor-pointer" :style {:width "100%"}}
          [:input {:type "checkbox"
                   :checked done}]
          (if @edit?
            [:input {:type "text"
                     :class "form-control"
                     :id "title"
                     :value @new-title
                     :on-change #(reset! new-title (-> % .-target .-value))
                     :on-click #(.stopPropagation %)
                     :on-key-press #(when (= 13 (.-charCode %)) (update))}]
            [:div
             [:div {:class (when done "done")} title]
             [:small {:class "text-muted"} (str (common/format-from-long date-created common/clean))]])]
         [:div {:style {:min-width "50px"}}
          [:span {:class "fas fa-trash float-right ml-2 cursor-pointer"
                  :title "Delete task"
                  :on-click #(do  (.stopPropagation %) (delete))}]
          [:span {:class "fas fa-edit float-right ml-2 cursor-pointer"
                  :on-click #(do (.stopPropagation %) (update))}]]]))))

(defn- add-task-form []
  (let [title (reagent/atom "")
        callback #(reset! title "")]
    (fn [list-id next-task-id]
      [:div {:class "d-flex"}
       [:input {:type "text"
                :class "form-control"
                :placeholder "What do you need to do today?"
                :id "title"
                :value @title
                :on-change #(reset! title (-> % .-target .-value))
                :on-key-press #(when (= 13 (.-charCode %)) (rf/dispatch [::events/add-new-task list-id next-task-id @title callback]))}]
       [:button {:type "button"
                 :class "btn btn-primary ml-2"
                 :disabled (str/blank? @title)
                 :on-click #(rf/dispatch [::events/add-new-task list-id next-task-id @title callback])}
        "Add"]])
))

(defn render
  "Render page List"
  [list-id]
  (let [list (rf/subscribe [:firebase/on-value {:path [:list list-id]}])]
    (when-not (empty? @list)
      (let [name (@list :name)
            next-id (count (@list :tasks))
            visible-tasks (filterv #(= (:visible %) true) (@list :tasks))]
        [:div {:class "container-fluid"}
         [:div {:class "mt-2 mb-2 d-flex justify-content-between align-items-center"}
          [:h1 name]
          [:div
           [:span {:class "fas fa-edit cursor-pointer mr-2"
                   :title "Edit list"
                   :on-click #(rf/dispatch [::events/show-modal [modal/add-list-modal list-id name]])}]
           [:span {:class "fas fa-trash cursor-pointer"
                   :title "Delete list"
                   :on-click #(rf/dispatch [::events/show-modal [modal/confirm-delete-list-modal list-id]])}]]]
         [add-task-form list-id next-id]
         [:br]
         (when-not (empty? visible-tasks)
           (map #(vector render-task list-id %) (sort-by :id > visible-tasks)))]))))

