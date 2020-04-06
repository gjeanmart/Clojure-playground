
# Clojure[Script] Sandbox

Cheatsheet to refresh memory on Clojure and ClojureScript


# Teminology

- Leiningen: TODOs
- shadow-cljs: TODO


## Installation

### On Linux (Ubuntu)

Install the required dependencies

```shell
$ sudo apt install -y curl git default-jdk rlwrap leiningen
```

Download and execute the installer

```shell
$ curl -O https://download.clojure.org/install/linux-install-1.10.1.507.sh
$ chmod +x linux-install-1.10.1.507.sh
$ sudo ./linux-install-1.10.1.507.sh
```

### On MacOS

```shell
$ brew install clojure
```

### IDE

I'm currently using Atom, here is a few packages that's gonna help to code efficiently.

- [proto-repl](https://atom.io/packages/proto-repl) (manually set Lein path)
- parinfer

Commands
- Launch REPL (Read-Eval-Print Loop): Ctrl+, L
- (re)Load file in REPL: Ctrl+, f
- Execute with REPL: Ctrl+, b
- Clear REPL output Ctrl+ k
- Run all tests: Ctrl+, a (x for namespace only)


## Basics

### New project

Start a new project

```
$ lein new app clojure-basics
```


### Import packages

```clojure
(ns clojure-basics.core
  (:require [<package> :as <alias>]) ;; Add package here at the top (with optionally an alias)
  (:gen-class))
```

### Variables

Immutable by design

```clojure
;; Define a variable
(def myInt 10) ;; integer
(def myDec 1.23) ;; decimal
(def myString "hey") ;; string
(def myBoolean false) ;; boolean
```

### Condition on Variables

```clojure
(nil? 1.2) ;; is null?

;; numbers
(pos? 15) ;; is positive?
(number? 12) ;; is a number
(integer? 1) ;; is an integer
(float? 1) ;; is a float
```

### String manipulation

Add dependency `[clojure.string :as str]`

```clojure
(println "Hello " name " !!!") ;; Print string
(format "Hello %s", "greg") ;; Format string
(str/blank? "test") ;; is a blank string
(str/index-of "hello world" "hello") ;; index of
(str/includes "hello world" "hello") ;; contains
(str/split "hello world" #" ") ;; split by separator
(str/join " " ["hello", "world"]) ;; join a string array
(str/replace "hello bob" #"bob" "greg") ;; replace
(str/upper-case "hello world") ;; To upper case
(str/lower-case "HELLO WORLD") ;; To lower case
```

`#` : regex

### List

list of values: `list`

```clojure
(def mylist (list "dog" 1 2 true)) ;; define a list
(def mylist '("dog" 1 2 true)) ;; define a list - short
(println (first mylist)) ;; first element of the list
(println (nth mylist 1)) ;; get element at position i
(println (list* mylist [3 4])) ;; add at the end of a list
(println (cons "a" mylist)) ;; add a the begining of a list
```

list of unique values: `set`

```clojure
(def mySet (set [1 1 2])) ;; define a set
(println mySet) ;; return 1 2
(println (get mySet 2)) ;; get a specific value by position
(println (conj mySet 3)) ;; append a value to the set
(println (contains? mySet 2)) ;; contains
(println (disj mySet 2)) ;; remove a value from the set

```

Vector: `vector`

```clojure
(def myVector (vector 1 "a" :b)) ;; define a vector
(def myVector [1 "a" :b]) ;; define a vector - short
```

Key Value pair: `Map`

```clojure
(def myHM (hash-map "key1" "val1" "key2" "val2")) ;; define a map
(def mySHM (sorted-map 1 "val1" 2 1.2)) ;; define a sorted map
(println (get myHM "key1")) ;; get value by key
(println (find myHM "key1")) ;; get key/value by key
(println (contains? myHM "key1")) ;; check if the map contains a key
(println (keys myHM)) ;; get all the keys
(println (vals myHM)) ;; get all the values
(println (merge myHM mySHM)) ;; merge maps
```


### Change values

#### Atom

Mutable variables

```clojure
(defn myAtom
  [x]
  (def myAtom(atom x))
  (add-watch myAtom :watcher
    (fn [key atom oldVal newVal]
      (println "myAtom change from " oldVal " to " newVal))) ;; Watcher of changes
  (println "1st x" @myAtom)
  (reset! myAtom 10) ;; Update variable by replacement
  (println "2st x" @myAtom)
  (swap! myAtom inc) ;; Update variable by method
  (println "Increment x " @myAtom))

> (myAtom 5)
```

`@` is used to access to value of the variable


#### Agents

```clojure
(defn myAgent
  []
  (def value(agent 0))
  (send value + 2)
  (println)
  (println "val " @value)
  (send value + 3)
  (await-for 100 value)
  (println "val " @value)
  (shutdown-agents))


> (myAgent)
```

### Math

Math functions

```clojure
(defn math
  []
  (println (+ 1 2)) ;; addition
  (println (- 5 2)) ;; substraction
  (println (* 1.5 2)) ;; multiplication
  (println (/ 6 2)) ;; division
  (println (mod 13 10)) ;; modulo
  (println (inc 2)) ;; increment
  (println (dec 2))) ;; decrement
  (println (rand-int 20))) ;; Random
  (println (Math/PI))) ;; Random
and more...
```



### Functions

```clojure
; Define a function
(defn main
  "description of myFunction"
  [arg1 arg2]  ;; arguments
  (def name "Greg") ;; local variable
  (format "my name is %s arg1=%s arg2=%s" name arg1 arg2))

(defn my-function
  "description of my-function"
  [arg1]
  (println "arg1="arg1))

(defn my-function2
  ([x y z] ;; 3 arguments
   (+ x y z)) ;; return x+y+z
  ([x y] ;; 2 arguments
    (+ x y))) ;; return x+y


(defn hello-all
  [& names] ;; array as argument
  (map hello names)) ;; for each names, call hello

(defn hello
  [name]
  (println "Hello " name))
```


## Decision making

### Comparaison

```clojure
(= 3 3) ;; equality
(not= 2 3) ;; not equals
(> 3 2) ;; greater (similar: < <= >=)
```

### Logical operators

```clojure

(and true false) ;; AND
(or true false) ;; OR
(not true) ;; NOT
```

### Condition


```clojure
(defn can-vote
  [age] ;; argument
  (if (>= age 18) ;; condition
    (println "you can vote") ;; if condition is true
    (println "you can't vote"))) ;; if condition is false (else)

(defn can-do-more
  [age]
  (if (>= age 18)
    (do (println "you can drive") ;; execute multiple statements if condition is true
        (println "you can vote"))
    (println "go to shcool"))) ;; else condition

(defn when-ex
  [tof]
  (when tof ;; equivalent to (if <condition> (do <statement1> <statementn>))
    (println "1st thing")
    (println "2nd thing")))

(defn what-grade
  [n]
  (cond ;; multiple choice condition
    (< n 5) (println "preschool") ;; condition - statement
    (= n 5) (println "kindergarden") ;; condition - statement
    (and (> n 5) (<= 18)) (format "go to grade %d" (- n 5)))) ;; condition - statement
```

## Loop

```clojure
(defn one-to-x
  [x]
  (def i (atom 1)) ;; index - mutable var
  (while (<= @i x) ;; While loop
    (do
      (println "i=" @i)
      (swap! i inc)))) ;; increment index

(defn dbl-to-x
  [x]
  (dotimes [i x] ;; start to 0 to x - i index
    (println (* i 2))))

(defn triple-to-x
  [x y]
  (loop [i x] ;; loop
    (when (< i y)
      (println (* i 3))
      (recur (inc i)))))

(defn print-list
  [& nums]
  (doseq [x nums] ;; foreach elements of the list - x current element
    (println "x=" x)))
```    



## I/O

Add dependencies

```clojure
(use 'clojure.java.io)
```

Write t a file

```clojure
(defn write-to-file
  [file content]
  (with-open [wrtr (writer file)]
   (.write wrtr content)))
```   

Read from a file

```clojure
(defn read-from-file
  [file]
  (try
    (println (slurp file))
    (catch Exception ex (println "Error : " (.getMessage ex)))))
```   

Append to a file

```clojure
(defn append-to-file
  [file line]
  (with-open [wrtr (writer file :append true)]
    (.write wrtr line)))
```   

Read line by line (array)

```clojure
(defn read-line-from-file
  [file]
  (with-open [rdr (reader file)]
    (doseq [line (line-seq rdr)]
      (println line))))
```

### Destructuring


```clojure
(defn destruct
  []
  (def vec [1 2 3 4])
  (let [[one two & the-rest] vec]
    (println one two the-rest)))
;; => 1 2 [3 4]
```

```clojure
(def my-hashmap {:key1 "val1" :key2 "val2"})

(let [{a :key1 b :key2} my-hashmap]
  (println a b))
;; => val1 val2
```

### Exception

```clojure
(defn call
  []
  (def response (http/get "http://api/" {:throw-exceptions false}))
  (if (not= (:status response) 200)
    (throw (Exception. (format "Error while calling api (status: %s)" (:status response)))))
    (json/parse-string (:body response) true)))
```

### Data structure

```clojure
(defn struct-map-ex
  []
  (defstruct Customer :Name :Phone) ;; Define the structure with properties
  (def cust1 (struct Customer "Greg" "000222")) ;; Declare a new variable of type X
  (def cust2 (struct-map Customer :Name "jo" :Phone "02234")) ;; Declare a new variable and populate wanted property
  (println cust1) ;; print whole variable
  (println (:Name cust2))) ;; print given property
```

### Anonymous functions

Default syntax

```clojure
((fn [x y] (* x y)) 1 5)
(map (fn [x] (* x x)) (range 1 10))
```

Compact syntax

```clojure
(#(* %1 %2) 2 5) ;; # define an anonymous fct (eq fn[x y]) - %1 represents the first argument, %2 ...
(map #(* % %) (range 1 5)) ;; % always represents the first argument
```

### Clojures

What i would call "Parametrized function"

```clojure
(defn custom-multiplier
  [mult-by]
  #(* % mult-by))

(def multi-by-3 (custom-multiplier 3))

> (multi-by-3 3) ;; 9
```

### Filter list

```clojure
(take 2 [1 2 3]) ;; take the first x elements - [1 2]
(drop 2 [1 2 3]) ;; elimnate the first x element - [3]
(take-while neg? [-1 0 1]) ;; filter by condition (only negative for e.g) - [-1]
(drop-while neg? [-1 0 1]) ;; filter by condition - [0 1] (opposite)
(filter #(> % 2) [1 2 3 4]) ;; filter by function - [3 4]

(defn is-greather-than
  [x]
  #(> % x))
(def is-greater-than-10 (is-greather-than 10))
(filter is-greater-than-10 [1 2 3 10 15 30]) ;; filter with a clojure
```

### Macros

Custom functions / code generating tool

```clojure
(defmacro discount
  ([cond dis1 dis2]
    (list `if cond dis1 dis2)))

(discount (> 25 65) (println "10% off") (println "full price"))
```

```clojure
(defmacro reg-math
  [calc]
  (list (second calc) (first calc) (nth calc 2)))

(reg-math (2 + 5))
```

```clojure
(defmacro do-more
  [cond & body]
  (list `if cond (cons `do body)))

(do-more (< 1 2) (println "hello") (println "world"))
```


### Others syntax




## Complex program

### Rest client

0. Create a new project

```
$ lein new app clojure-rest-client
```

1. Add dependency

Open `project.clj` and add the following dependencies: `[clj-http "3.6.1"]` (http client) and `[cheshire "5.7.1"]` (parser)

```clojure
(defproject clojure-complex "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [clj-http "3.6.1"]  ;; <<< HERE
                 [cheshire "5.7.1"]] ;; <<< and HERE
  :main ^:skip-aot clojure-rest_client.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
```               

2. Create a file call `api-client.clj` into `src/<project>/`

3. Open `api-client.clj` and add the following code

Configure the namespace and import `clj-http.client`

```clojure
(ns clojure-rest_client.api-client
  (:require [clj-http.client :as client])
  (:gen-class))
```

Set up a global variable to store our api endpoint

```clojure
(def api-endpoint "https://reqres.in/api")
```

Define a structure type to return

```clojure
(defstruct User :Email :FirstName :LastName)
```

Define a function to make a HTTP call and handle the response

```clojure
(defn get-user
  [id] ;; user ID wanted
  (try ;; we want to catch potential exception (especially HTTP error 4xx/5xx)
    (do
      (def resp (http/get (format "%s/users/%s" api-endpoint id))) ;; Call
      (let [{data :data} (json/parse-string (:body resp) true)] ;; Parse JSON response and destruct to extract resp.body.data
        (struct User (:email data) (:first_name data) (:last_name data)))) ;; Build up return object of type User
    (catch Exception ex ;; If an exception occurs
      (do
        (println "Exception while calling api" (.getMessage ex)) ;; print details in the console
        (throw (Exception. (format "api-client exception (code: 001, msg: %s)" (.getMessage ex)))))))) ;; raise a new exception
```

4. Open `core.clj` and add the following code

Import `api_client`

```clojure
(ns clojure-rest_client.core
  (:require
    [clojure-rest_client.api_client :as client]) ;; <<< HERE
  (:gen-class))
```

Code your main function like this to
- Define an ID
- Call the client to retrieve user details with id=1
- Print the response

```clojure
(defn -main
  [& args]
  (def id (first args)) ;; Use first argument as user ID to search
  (def user (client/get-user id)) ;; Call our api_client/get-user
  (println "user " id " is " user)) ;; print result
```

5. Run the program

Try it now

```shell
$ lein run 12
user  12  is  {:Email rachel.howell@reqres.in, :FirstName Rachel, :LastName Howell}

$ lein run donotexist
Exception \while calling api clj-http: status 404

Caused by: java.lang.Exception: api-client exception (code: 001, msg: clj-http: status 404)
	at clojure_complex.api_client$get_user.invokeStatic(api_client.clj:20)
	at clojure_complex.api_client$get_user.invoke(api_client.clj:10)
	at clojure_complex.core$_main.invokeStatic(core.clj:9)
	at clojure_complex.core$_main.doInvoke(core.clj:6)
	at clojure.lang.RestFn.invoke(RestFn.java:408)
	at clojure.lang.Var.invoke(Var.java:384)
	at user$eval140.invokeStatic(form-init12011381999112723823.clj:1)
	at user$eval140.invoke(form-init12011381999112723823.clj:1)
	at clojure.lang.Compiler.eval(Compiler.java:7176)
	at clojure.lang.Compiler.eval(Compiler.java:7166)
	at clojure.lang.Compiler.load(Compiler.java:7635)
	... 12 more
```


### Rest server

0. Create a new project

```
$ lein new app clojure-rest-server
```

1. Open `project.clj` at the root level of the project and add the required dependencies to make a rest api

```clojure
(defproject clojure-rest-server "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [http-kit "2.3.0"] ;; Http library for client/server
                 [compojure "1.6.1"] ;; Routing library
                 [ring/ring-defaults "0.3.2"] ;; Query params
                 [org.clojure/data.json "0.2.6"]] ;; Clojure data.JSON library
  :main ^:skip-aot clojure-rest-server.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
```

2. Open our main file `core.clj`

Import the libraries we need in that file

```clojure
(ns clojure-rest-server.core
  (:require [org.httpkit.server :as server]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer :all]
            [clojure.pprint :as pp]
            [clojure.string :as str]
            [clojure.data.json :as json])
  (:gen-class))
```

Let's now define our route

```clojure
(defroutes app-routes
  (GET "/" [] single-page) ;; reference to the event handler single-page defined below
  (GET "/api" [] api) ;; reference to the event handler api defined below
  (route/not-found "Error, page not found!")) ;; Default redirection
```  

Configure our main function to start the webserver

```clojure
(defn -main
  "This is our main entry point"
  [& args]
  (let [port (Integer/parseInt (or (System/getenv "PORT") "3000"))]
    (server/run-server (wrap-defaults #'app-routes site-defaults) {:port port}) ; Run the server with Ring.defaults middleware and load app-route

    (println (str "Running webserver at http:/127.0.0.1:" port "/"))))
```

Finally configure the event handlers to return data

```clojure
; Single page
(defn single-page [req]
  {:status  200
   :headers {"Content-Type" "text/html"}
   :body    "<div><h1>Hello World</h1></div>"})

; api
(defn api [req]
     {:status  200
      :headers {"Content-Type" "application/json"}
      :body    "{\"msg\": \"api root - nothing interesting\"}"})
```      

You can now run `lein run` and try to access our single-page app http://localhost:3000 and api http://localhost:3000/api from your browser

```shell
$ lein run
Running webserver at http:/127.0.0.1:3000/
```

[images]

Let's say we want to use parameters, we can extract those from the `req.params`

```clojure
(defn api-say-hi [req]
      {:status  200
       :headers {"Content-Type" "application/json"}
       :body    (-> (format "{\"msg\": \"hi %s\"}" (:name (:params req))))})
```

Try it: http://localhost:3000/api/say-hi?name=greg



## ClojurScript

In this section, we will describe step by step how to build a clojurescript SPA web application using re-frame framework.

### SPA with re-frame

1. Create a new project with the following options

```shell
$ lein new re-frame clojurescript-todo +garden +re-com +routes +test +less +10x
Generating re-frame project.

$ cd clojurescript-todo
```



2. Run the project in dev mode

```shell
$ lein dev
Compiling Garden...
Compiling "resources/public/css/screen.css"...
Wrote: resources/public/css/screen.css
Successful
Warning: Nashorn engine is planned to be removed from a future JDK release
Compiling {less} css:
less/site.less => resources/public/css/site.css
Done.
running: npm install --save --save-exact react@16.9.0 react-dom@16.9.0 highlight.js@9.15.10 react-highlight.js@1.0.7
npm notice created a lockfile as package-lock.json. You should commit this file.
npm WARN clojurescript-todo No repository field.
npm WARN clojurescript-todo No license field.

+ react-highlight.js@1.0.7
+ highlight.js@9.15.10
+ react-dom@16.9.0
+ react@16.9.0
added 10 packages from 272 contributors and audited 30 packages in 3.337s
found 0 vulnerabilities

shadow-cljs - HTTP server available at http://localhost:8280
shadow-cljs - HTTP server available at http://localhost:8290
shadow-cljs - server version: 2.8.83 running at http://localhost:9630
shadow-cljs - nREPL server started on port 8777
shadow-cljs - watching build :app
[:app] Configuring build.
[:app] Compiling ...
```

Alternatively, you can launch the app in dev mode via vscode (+ calva) like this (Make sure first you open a file wihthin the project like `project.clj`):

![](https://i.imgur.com/zNfo7Ar.png)
![](https://i.imgur.com/WTenCKN.png)
![](https://i.imgur.com/J8Xwxgr.png)
![](https://i.imgur.com/rU3ujaN.png)


You can now access the app in dev mode via [http://localhost:8280](http://localhost:8280). The REPL is launched so it includes the auto-reload and it's easy to debug. 

![](https://i.imgur.com/goCIOiT.png)





## References
- https://www.youtube.com/watch?v=ciGyHkDuPAE
- https://www.tutorialspoint.com/clojure
- https://github.com/dakrone/clj-http HTTP Client
- https://clojure.org/api/cheatsheet CheatSheet
- https://kimh.github.io/clojure-by-example/#use
