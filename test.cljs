(ns reagent-practice.core
  (:require [reagent.core :refer [atom render]]))

(def notes (atom []))

(defn get-value-by-id [id]
  (.-value (.getElementById js/document id)))

(defn save-note! []
  (swap! notes conj (get-value-by-id "note")))

(defn home-page []
  [:div {:style {:padding "15px"
                 :border "1px solid"
                 :margin-left "10%"
                 :margin-right "30%"}}
   [:p {:style {:font-weight "bold"}}
    "Todo List"]
   [:ul {:style {:font-size "14px"}}
    (for [note @notes]
      [:li note ])]
   [:input#note {:style {:margin-right "2px"}}]
   [:button {:onClick save-note!}
    "Add "]])

;; Initialize app
(defn mount-root []
  (render [home-page] (.getElementById js/document "app")))

(defn init! []
  (mount-root))
