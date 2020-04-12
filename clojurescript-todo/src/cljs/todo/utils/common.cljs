(ns todo.utils.common
  (:require
   [secretary.core :as secretary]
   [cljs-time.coerce :as time-coerce]
   [cljs-time.format :as f]))

(def ^:const clean (f/formatter "yyyy-MM-dd 'at' HH:mm"))

(defn log 
  [& args]
  (.apply js/console.log js/console (to-array args)))

(defn redirect
  [path & [callback-fn]]
  (set! (.-location js/window) (str "#" path)) ;; https://github.com/clj-commons/secretary/issues/58
  (secretary/dispatch! path)
  (when callback-fn (callback-fn)))

(defn format
  "Format a date to a given format"
  [date formatter]
  (f/unparse formatter date))

(defn parse
  "Parse a string of a given format to a date"
  [str formatter]
  (f/parse formatter str))

(defn to-timestamp
  [date]
  (time-coerce/to-long date))

(defn format-from-long
  "Format a unix-epoch date to a given format"
  [long formatter]
  (format (time-coerce/from-long long) formatter))