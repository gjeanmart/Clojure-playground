(ns clojure-basics.core
  (:require [clojure.string :as str])
  (:gen-class))


;; Define a variable
(def myInteger 10) ;; integer
(def myDecilal 1.23) ;; decimal
(def myString "hey") ;; string
(def myBoolean true)

(defn test
  []
  (println "hello world"))

;; List
(def mylist (list "dog" 1 2 true)) ;; define a list
(println (first mylist)) ;; first element of the list
(println (nth mylist 1)) ;; get element at position i
(println (list* mylist [3 4])) ;; add at the end of a list
(println (cons "a" mylist)) ;; add a the begining of a list

(+ 1 1)

(def mySet (set [1 1 2]))
(println mySet) ;; return 1 2
(println (get mySet 2)) ;; get a specific value by position
(println (conj mySet 3)) ;; append a value to the set
(println (contains? mySet 2)) ;; contains
(println (disj mySet 2)) ;; remove a value from the set

(def myHM (hash-map "key1" "val1" "key2" "val2"))
(def mySHM (sorted-map 1 "val1" 2 1.2))
(println (get myHM "key1")) ;; get value by key
(println (find myHM "key1")) ;; get key/value by key
(println (contains? myHM "key1")) ;; check if the map contains a key
(println (keys myHM)) ;; get all the keys
(println (vals myHM)) ;; get all the values
(println (merge myHM mySHM)) ;; merge maps

;; Mutable variables

(defn myAtom
  [x]
  (def myAtom(atom x))
  (add-watch myAtom :watcher
    (fn [key atom oldVal newVal]
      (println "myAtom change from " oldVal " to " newVal)))
  (println "1st x" @myAtom)
  (reset! myAtom 10)
  (println "2st x" @myAtom)
  (swap! myAtom inc)
  (println "Increment x " @myAtom))


(defn agent-ex
  []
  (def tickets-sold(agent 0))
  (send tickets-sold + 15)
  (println)
  (println "Tickets " @tickets-sold)
  (send tickets-sold + 10)
  (await-for 100 tickets-sold)
  (println "Tickets " @tickets-sold)
  (shutdown-agents))


  ;; Math

(defn math
  []
  (println (+ 1 2)) ;; addition
  (println (- 5 2)) ;; substraction
  (println (* 1.5 2)) ;; multiplication
  (println (/ 6 2)) ;; division
  (println (mod 13 10)) ;; modulo
  (println (inc 2)) ;; increment
  (println (dec 2)) ;; decrement
  (println (reduce + [1 2 3]))
  (println (Math/PI)))


;; Functions
(defn my-function
  "description of my-function"
  [arg1]
  (println "arg1="arg1))

(defn myFunction2
  ([x y z] ;; arguments
   (+ x y z))) ;; return x+y+x

(defn hello
  [name]
  (println "Hello " name))

(defn hello-all
  [& names]
  (map hello names))


;; Condition

(defn can-vote
  [age]
  (if (>= age 18)
    (println "you can vote")
    (println "you can't vote")))

(defn can-do-more
  [age]
  (if (>= age 18)
    (do (println "you can drive")
        (println "you can vote"))
    (println "go to shcool")))

(defn when-ex
  [tof]
  (when tof
    (println "1st thing")
    (println "2nd thing")))

(defn what-grade
  [n]
  (cond
    (< n 5) (println "preschool")
    (= n 5) (println "kindergarden")
    (and (> n 5) (<= 18)) (format "go to grade %d" (- n 5))))

;; Loop

(defn one-to-x
  [x]
  (def i (atom 1))
  (while (<= @i x)
    (do
      (println "i=" @i)
      (swap! i inc))))

(defn dbl-to-x
  [x]
  (dotimes [i x]
    (println (* i 2))))

(defn triple-to-x
  [x y]
  (loop [i x]
    (when (< i y)
      (println (* i 3))
      (recur (inc i)))))

(defn print-list
  [& nums]
  (doseq [x nums]
    (println "x=" x)))

;; I/O

(use 'clojure.java.io)

(defn write-to-file
  [file content]
  (with-open [wrtr (writer file)]
   (.write wrtr content)))

(defn read-from-file
  [file]
  (try
    (println (slurp file))
    (catch Exception ex (println "Error : " (.getMessage ex)))))


(defn append-to-file
  [file line]
  (with-open [wrtr (writer file :append true)]
    (.write wrtr line)))

(defn read-line-from-file
  [file]
  (with-open [rdr (reader file)]
    (doseq [line (line-seq rdr)]
      (println line))))

;; destructuring

(defn destruct
  []
  (def vec [1 2 3 4])
  (let [[one two & the-rest] vec]
    (println one two the-rest)))

;; struct maps

(defn struct-map-ex
  []
  (defstruct Customer :Name :Phone)
  (def cust1 (struct Customer "Greg"))
  (def cust2 (struct-map Customer :Name "jo" :Phone "02234"))
  (println cust1)
  (println (:Name cust2)))

;; anonymous function
((fn [x y] (* x y)) 1 5)
(map (fn [x] (* x x)) (range 1 10))

;; compact  anonymous function
(#(* %1 %2) 2 5)
(map #(* % %) (range 1 5))





;; Clojures

(defn custom-multiplier
  [mult-by]
  #(* %1 mult-by))

(def multi-by-3 (custom-multiplier 3))

> (multi-by-3 3)




;; Filter list

(take 2 [1 2 3]) ;; take the first x elements - [1 2]
(drop 2 [1 2 3]) ;; elimnate the first x element - [3]
(take-while neg? [-1 0 1]) ;; filter by condition (only negative for e.g)
(drop-while neg? [-1 0 1]) ;; filter by condition (opposite)
(filter #(> % 2) [1 2 3 4]) ;; filter by function

(defn is-greather-than
  [x]
  #(> % x))
(def is-greater-than-10 (is-greather-than 10))
(filter is-greater-than-10 [1 2 3 10 15 30])



;; macros
;; code generating tool

(defmacro discount
  ([cond dis1 dis2]
    (list `if cond dis1 dis2)))

(discount (> 25 65) (println "10% off") (println "full price"))

(defmacro reg-math
  [calc]
  (list (second calc) (first calc) (nth calc 2)))

(reg-math (2 + 5))


(defmacro do-more
  [cond & body]
  (list `if cond (cons `do body)))

(do-more (< 1 2) (println "hello") (println "world"))
