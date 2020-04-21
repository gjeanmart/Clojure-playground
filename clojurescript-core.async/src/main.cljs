(ns main
  (:require
   [promesa.core :as p]
   [httpurr.client.node :as h]
   [cljs.core.async :refer [go go-loop]]
   [cljs.core.async.interop :refer [<p!]]))

(defn- get-body
  [response]
  (js->clj (js/JSON.parse (:body response))))


(defn simple-interop []
  (.then (js/Promise.resolve 1)
         (fn [result]
           (println "simple-interop:" result))))

(defn simple-async []
  (go
    (let [result (<p! (js/Promise.resolve 1))]
      (println "simple-async:" result))))

(defn simple-async-short []
  (go-loop [promise (js/Promise.resolve 1) 
            result (<p! promise)]
    (println "simple-async-short:" result)))


(defn multiple-resolve-async []
  (go
    (let [total (atom 0)]
      (swap! total + (<p! (p/promise 1)))
      (swap! total + (<p! (p/promise 2)))
      (swap! total + (<p! (p/promise 3)))
      (println "multiple-resolve - total of 1+2+3 is " @total))))


(defn call-http-interop []
  (-> (h/get "https://jsonplaceholder.typicode.com/todos/1")
      (.then (fn [response]
               (let [content (get-body response)]
                 (println "call-http-interop" content))))))

(defn call-http-async []
  (go
    (let [response (<p! (h/get "https://jsonplaceholder.typicode.com/todos/1"))
          content (get-body response)]
      (println "call-http:" content))))

(defn call-http-async-short []
  (go-loop [response (<p! (h/get "https://jsonplaceholder.typicode.com/todos/1"))
            content (get-body response)]
    (println "call-http:" content)))

(defn call-http-promise-async []
  (p/promise
   (go
     (let [response (<p! (h/get "https://jsonplaceholder.typicode.com/todos/1"))]
       (get-body response))
     )
   )
  )

(defn call-multiple-http-async []
  (go
    (let [response (<p! (p/all [(h/get "https://jsonplaceholder.typicode.com/todos/1") 
                                (h/get "https://jsonplaceholder.typicode.com/todos/2") 
                                (h/get "https://jsonplaceholder.typicode.com/todos/3")]))
          content (map get-body response)]
      (println "call-multiple-http:" content))))


(defn call-synchronous-http-async []
    (go-loop [i 1
              agg []]
      (let [response (<p! (h/get (str "https://jsonplaceholder.typicode.com/todos/" i)))
            content (get-body response)]
        (when (<= i 3)
          (recur (inc i) (conj agg content)))
        (println "call-synchronous-http" agg))))

(defn call-nested-http-interop []
  (-> (h/get "https://jsonplaceholder.typicode.com/todos/1")
      (.then (fn [response1]
               (let [body1 (get-body response1)]
                 (-> (h/get "https://jsonplaceholder.typicode.com/todos/2")
                     (.then (fn [response2]
                              (let [body2 (get-body response2)]
                                (-> (h/get "https://jsonplaceholder.typicode.com/todos/3")
                                    (.then (fn [response3]
                                             (let [body3 (get-body response3)]
                                               (println "call-nested-http-interop" [body1 body2 body3]))))))))))))))

(defn call-nested-http-async []
  (go
    (let [response1 (<p! (h/get (str "https://jsonplaceholder.typicode.com/todos/1")))
          content1 (get-body response1)
          response2 (<p! (h/get (str "https://jsonplaceholder.typicode.com/todos/2")))
          content2 (get-body response2)
          response3 (<p! (h/get (str "https://jsonplaceholder.typicode.com/todos/3")))
          content3 (get-body response3)
          content [content1 content2 content3]]
      (println "call-nested-http:" content))))
  


(defn main! []
  (println "App loaded!")
  (simple-interop)
  (simple-async)
  (simple-async-short)
  (multiple-resolve-async)
  (call-http-async)
  (call-multiple-http-async)
  (call-synchronous-http-async)
  (call-nested-http-async)
  ;(go
  ;  (println "call-http-promise:" (<p! (call-http-promise))))
  (call-http-interop)
  (call-nested-http-interop)
  )