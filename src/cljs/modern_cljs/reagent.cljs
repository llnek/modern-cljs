(ns modern-cljs.reagent
  (:require [reagent.core :as r]))

(def app-state (r/atom {:foo {:bar "Hello, world!"
                                    :baz {:quux "Woot"}}}))

(defn inside-app-state []
  [:div (str "Inside app-state: " @app-state)])

(def foo-cursor (r/cursor app-state [:foo]))

(defn inside-foo-cursor []
  [:div (str "Inside foo-cursor: " @foo-cursor)])

(def foobar-cursor (r/cursor app-state [:foo :bar]))

(defn inside-foobar-cursor []
  [:div (str "Inside foobar-cursor: " @foobar-cursor)])

(def foobaz-cursor (r/cursor app-state [:foo :baz]))

(defn inside-foobaz-cursor []
  [:div (str "Inside foobaz-cursor: " @foobaz-cursor)])

(def foobazquux-cursor (r/cursor app-state [:foo :baz :quux]))

(defn inside-foobazquux-cursor []
  [:div (str "Inside foobazquux-cursor: " @foobazquux-cursor)])

(defn home []
  [:div
   [inside-app-state]
   [inside-foo-cursor]
   [inside-foobar-cursor]
   [inside-foobaz-cursor]
   [inside-foobazquux-cursor]
   ])


(defn foo []
  (let [component-state (r/atom {:count 0})]
    (fn []
      [:div 
       [:p "Current count is: " (:count @component-state)]
       [:button {:on-click #(swap! component-state update-in [:count] inc)} 
        "Increment"]])))

(defn bar []
  [:div "Hello, world!"])

(defn baz []
  (fn []
    [:div "Hello, world!"]))

(defn comment-component [author comment]
  [:div 
   [:h2 author]
   comment])

(defn comment-list []
  [:div  
   [comment-component "Pete Hunt" "This is one comment"] 
   [comment-component "Jordan Walke" 
    [:p "This is " [:em "another"]  " comment"]]])

(defn comment-form []
  [:div "Hello, world! I am a comment-form"])

(comment (defn comment-box []
           [:div 
            [:h1 "Comments"]
            [comment-list]
            [comment-form]]))

(defn comment-box []
  [:div 
   [:h1 "Comments"]
   [comment-list]
   [comment-form]])

(def expanded (r/atom true))

(defn on-header-click []
  (swap! expanded not))

(defn expandable-view []
  [:div.exapandable
   [:div.header {:on-click on-header-click}
    "Click me to expand and collapse the body"]
   (if @expanded
     [:div.body "I'm the body"])])


(defn ^:export init []
  (if (and js/document
           (aget js/document "getElementById"))
    (r/render-component 
     [comment-box] 
     (.getElementById js/document "root"))))

