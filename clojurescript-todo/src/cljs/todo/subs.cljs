(ns todo.subs
  (:require
   [re-frame.core :as rf]))

(rf/reg-sub
 ::active-page
 (fn [db _]
   (:active-page db)))

(rf/reg-sub
 ::modal
 (fn [db _]
   (:modal db)))

(rf/reg-sub
 ::error
 (fn [db _]
   (:error db)))

(rf/reg-sub
 :user
 (fn [db _]
   (:user db)))