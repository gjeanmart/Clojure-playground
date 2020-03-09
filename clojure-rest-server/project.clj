(defproject clojure-rest-server "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [compojure "1.6.1"] ;; Compojure - A basic routing library
                 [http-kit "2.3.0"] ;; Our Http library for client/server
                 [ring/ring-defaults "0.3.2"] ;; Ring defaults - for query params etc
                 [org.clojure/data.json "0.2.6"]] ;; Clojure data.JSON library
  :main ^:skip-aot clojure-rest-server.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
