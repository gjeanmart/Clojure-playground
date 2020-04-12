(ns todo.config)

(defonce debug?
  ^boolean js/goog.DEBUG)

(defonce firebase-app-info
  {
   :apiKey "AIzaSyAqxAM5GSqGvOZWQZHcfPoPumpbVpzIsBQ"
   :authDomain "YOUR-APP.firebaseapp.com"
   :databaseURL "https://clojurescript-todo.firebaseio.com"
   :projectId "clojurescript-todo"
   :storageBucket "YOUR-APP.appspot.com"
   :messagingSenderId "000000000000"
   })