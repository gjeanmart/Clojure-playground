(ns todo.events
  (:require
   [re-frame.core :as rf]
   [cljs-time.core :as t]
   [todo.utils.common :as common]
   [todo.db :as db]
   ))

(rf/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))

(rf/reg-event-db
 ::set-active-page
 (fn [db [_ active-page]]
   (assoc db :active-page active-page)
   ))

(rf/reg-event-db
 ::show-modal
 (fn [db [_ content]]
   (js/setTimeout (fn [] (.modal (js/jQuery "#global-modal") "show")) 100) ;; BIG HACK... SHOULD TRY SOMETHING ELSE....
   (assoc db :modal {:show true :content content})))

(rf/reg-event-db
 ::hide-modal
 (fn [db _]
   (.modal (js/jQuery "#global-modal") "hide")
   (assoc db :modal {:show false :content nil})))

(rf/reg-event-db 
 :set-user 
 (fn [db [_ user]] 
   (assoc db :user user)))

(rf/reg-event-db
 :show-error
 (fn [db [_ error]]
   (assoc db :error {:show true :msg error})))

;; ##############################################
;; LIST

(rf/reg-event-fx
 ::add-new-list
 (fn [_ [_ id name callback]]
   {:firebase/write {:path [:list (str id)]
                     :value {:id id :name @name :date-created (common/to-timestamp (t/now)) :visible true}
                     :on-success #(common/redirect (str "/list/" id) callback)
                     :on-failure [:show-error]}}))

(rf/reg-event-fx
 ::update-list-field
 (fn [_ [_ list-id field value callback]]
   {:firebase/write {:path [:list (str list-id) field]
                     :value value
                     :on-success callback
                     :on-failure [:show-error]}}))

;; ##############################################
;; TASK

(rf/reg-event-fx
 ::add-new-task
 (fn [_ [_ list-id task-id title callback]]
   {:firebase/write {:path [:list (str list-id) :tasks (str task-id)] ; /list/<list-id>/tasks/<task-id>/
                     :value {:id task-id
                             :title title
                             :date-created (common/to-timestamp (t/now))
                             :visible true
                             :done false}
                     :on-success callback
                     :on-failure [:show-error]}}))

(rf/reg-event-fx
 ::update-task-field
 (fn [_ [_ list-id task-id field value callback]]
   {:firebase/write {:path [:list (str list-id) :tasks (str task-id) field]
                     :value value
                     :on-success callback
                     :on-failure [:show-error]}}))

