(ns todo.config)


(goog-define DEBUG false)
(goog-define FIREBASE_API_KEY "AIzaSyAqxAM5GSqGvOZWQZHcfPoPumpbVpzIsBQ")
(goog-define FIREBASE_AUTH_DOMAIN "YOUR-APP.firebaseapp.com")
(goog-define FIREBASE_DB_URL "https://clojurescript-todo.firebaseio.com")
(goog-define FIREBASE_PROJECT_ID "clojurescript-todo")
(goog-define FIREBASE_STORAGE_BUCKET "YOUR-APP.appspot.com")

(defonce debug? DEBUG)

(defonce firebase-app-info
  {
   :apiKey FIREBASE_API_KEY
   :authDomain FIREBASE_AUTH_DOMAIN
   :databaseURL FIREBASE_DB_URL
   :projectId FIREBASE_PROJECT_ID
   :storageBucket FIREBASE_STORAGE_BUCKET
   :messagingSenderId "000000000000"
   })

