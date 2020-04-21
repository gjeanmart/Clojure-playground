(ns clojure-core.async-basics
  (:require  [clojure.core.async :refer [chan put! take! >! <! buffer dropping-buffer sliding-buffer timeout close! alts! go]]
             ;[clojure.core.async :refer-macros [go go-loop alt!]]
             )
  (:use [clojure.repl :only (source)])
  (:gen-class))

;; bufferless

(def bufferless-chan (chan))

(put!
 bufferless-chan ; channel
 "Futo Maki3" ; order (data)
 #(prn (str "order put? " %))) ; put! callback


(take!
 bufferless-chan
 #(prn (str "order taken: " %))) ; take! callback



(defn take-logger [order]
  (prn (str "order taken: " order)))

(defn put-logger [boolean]
  (prn (str "order put? " boolean)))

(defn put!-order [channel order]
  (put! channel order put-logger))

(defn take!-order [channel]
  (take! channel take-logger))

(defn bot-orders [channel order]
  (dotimes [x 1030]
    (put!-order channel order)))

;(bot-orders bufferless-chan "Sushi!") ; ERROR

;; buffer

(def fixed-chan (chan 10)) ; buffer = 10 values

(bot-orders fixed-chan "Sushi!")

(take!-order fixed-chan)

(defn put!-n-order [channel order n]
  (put! channel (str "#: " n " order: " order) put-logger))

(defn IHOS-orders [channel order]
  (dotimes [x 2100] ; lots-o'-orders
    (put!-n-order channel order x)))

(IHOS-orders fixed-chan "Nigiri!")

(take!-order fixed-chan)  

;; sliding-buffer

(def slide-chan (chan (sliding-buffer 10)))

(IHOS-orders slide-chan "Sashimi")

(take!-order slide-chan)

;; dropping-buffer

(def drop-chan (chan (dropping-buffer 10)))

(IHOS-orders drop-chan "Tofu Katsu")

(take!-order drop-chan)

;; backpressure

(defn put-logger2 [boolean x]
  (prn (str "order " x " put? " boolean)))


(defn backpressured-orders [channel order]
  (go
    (dotimes [x 2100] ; increase number of bot orders
      (put-logger2 (>! channel (str "#: " x " order: " order)) x))))

(def burst-chan (chan 50))

(defn burst-take! [channel]
  (dotimes [x 50] ; take 50 orders at a time
    (take!-order channel)))

(backpressured-orders burst-chan "Umami Tamago")

(burst-take! burst-chan)