(ns reagent-practice.core
  (:require [reagent.core :refer [atom render]]))

;; App DB
;; ======
(def notes (atom []))


;; Helpers
;; =======
(defn drop-nth-note [coll n]
  (let [[a b] (split-at n coll)]
    (vec (concat a (rest b)))))

(defn get-value-by-id [id]
  (.-value (.getElementById js/document id)))

(defn save-note! []
  (swap! notes conj (get-value-by-id "note")))

(defn delete-note! [idx]
  (swap! notes drop-nth-note idx))

(defn home-page []
  [:div
   [:h "Todo List"]
   [:ul {:style {:font-size "14px"}}
    (for [[idx note] (map-indexed #(list %1 %2) @notes)]
      [:li {:onClick (fn [] (delete-note! idx))}
        note])]
   [:input#note {:style {:margin-right "2px"}}]
   [:button {:onClick save-note!}
    "Add "]])


;; Initialize app
;; ==============
(defn init! []
  (render [home-page] (.getElementById js/document "app")))
