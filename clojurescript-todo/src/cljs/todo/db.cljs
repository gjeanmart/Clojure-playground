(ns todo.db)

(def default-db
  {:active-page {:page :home-page}
   :error {:show false :msg nil}
   :modal {:show false :content [nil]}})
