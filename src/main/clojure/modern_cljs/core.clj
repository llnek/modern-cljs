(ns modern-cljs.core
  (:require [compojure.core :refer [defroutes GET]]
            [compojure.route :refer [not-found files resources]]))

(defroutes handler
  (GET "/" [] "Hello from Compojure!")  ;; for testing only
  (files "/" {:root "public"})          ;; to serve static resources
  (resources "/" {:root "public"})      ;; to serve anything else
  (not-found "Page Not Found"))         ;; page not found




