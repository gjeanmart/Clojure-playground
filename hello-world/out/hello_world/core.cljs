(ns hello-world.core
  (:require react-dom))

(.render js/ReactDOM
  (.createElement js/React "h2" nil "Hello, React2")
  (.getElementById js/document "app"))
