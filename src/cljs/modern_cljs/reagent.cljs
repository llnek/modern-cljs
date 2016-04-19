(ns modern-cljs.reagent
  (:require [reagent.core :as r]
            cljsjs.marked))

(def data (r/atom [{:id 1
                    :author "Pete Hunt"
                    :text "This is one comment"}
                   {:id 2
                    :author "Jordan Walke"
                    :text "This is *another* comment"}]))

(defn comment-component 
  [author comment]
  [:div 
   [:h2 author]
   [:span {:dangerouslySetInnerHTML 
           #js {:__html (js/marked comment)}}]])

(defn comment-list [comments]
  [:div  
   (for [{:keys [id author text]} @comments] 
     ^{:key id} [comment-component author text])])

(defn comment-form [data]
  [:form
        [:input {:type "text"
                 :placeholder "Your name"}]
        [:input {:type "text"
                 :placeholder "Say something"}]
        [:input {:type "button" :value "Post"
                 :on-click #(.log js/console "Posted")}]])

(defn comment-box []
  [:div 
   [:h1 "Comments"]
   [comment-list data]
   [comment-form data]])

(defn ^:export init []
  (if (and js/document
           (aget js/document "getElementById"))
    (r/render-component 
     [comment-box] 
     (.getElementById js/document "root"))))

