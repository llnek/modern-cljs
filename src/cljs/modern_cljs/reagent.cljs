(ns modern-cljs.reagent
  (:require [reagent.core :as r]
            [clojure.string :as string]
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

(defn form-on-click [_ comment]
  (let [author (string/trim (:author @comment))
        text (string/trim (:text @comment))]
    (reset! comment {:author "" :text ""})
    (when-not (or (string/blank? author) (string/blank? text))
      (swap! data conj {:id (.getTime (js/Date.)) :author author :text text}))))

(defn comment-form []
  (let [comment (r/atom {:author "" :text ""})] 
    (fn [] 
      [:form
       [:input {:type "text"
                :placeholder "Your name"
                :value (:author @comment)
                :on-change #(swap! comment assoc :author (-> %
                                                             .-target
                                                             .-value))}]
       [:input {:type "text"
                :placeholder "Say something"
                :value (:text @comment)
                :on-change #(swap! comment assoc :text (-> %
                                                           .-target
                                                           .-value))}]
       [:input {:type "button"
                :value "Post"
                :on-click #(form-on-click % comment)}]])))

(defn comment-box [comments]
  [:div 
   [:h1 "Comments"]
   [comment-list comments]
   [comment-form]])

(defn ^:export init []
  (if (and js/document
           (aget js/document "getElementById"))
    (r/render-component 
     [comment-box data] 
     (.getElementById js/document "root"))))

