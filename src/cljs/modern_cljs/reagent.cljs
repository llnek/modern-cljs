(ns modern-cljs.reagent
  (:require [reagent.core :as r]))

(defn comment-component [author comment]
  [:div 
   [:h2 author]
   comment])

(defn comment-list []
  [:div  
   [comment-component "Pete Hunt" "This is one comment"] 
   [comment-component "Jordan Walke" [:p "This is " [:em "another"] " comment"]]])

(defn comment-form []
  [:div "Hello, world! I am a comment-form"])

(defn comment-box []
  [:div 
   [:h1 "Comments"]
   [comment-list]
   [comment-form]])

(defn ^:export init []
  (if (and js/document
           (aget js/document "getElementById"))
    (r/render-component 
     [comment-box] 
     (.getElementById js/document "root"))))

