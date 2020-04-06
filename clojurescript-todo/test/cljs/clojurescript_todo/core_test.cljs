(ns clojurescript-todo.core-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [clojurescript-todo.core :as core]))

(deftest fake-test
  (testing "fake description"
    (is (= 1 2))))
