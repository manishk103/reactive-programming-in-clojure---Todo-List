(ns reagent-practice.core
  (:require [reagent.core :refer [atom render]]
            [ajax.core :refer [GET]]))

;; App DB
;; ======
(def notes (atom []))


;; Helpers
;; =======
(defn get-value-by-id [id]
  (.-value (.getElementById js/document id)))

(defn delete-note-by-id! [id]
  (swap! notes
         #(vec (filter (fn [note]
                         (not= id (:_id note)))
                       %))))

(defn save-note! []
  (let [note-val (get-value-by-id "note")]
    (GET "/save-note"
       {:params  {:note note-val}
        :handler (fn [response]
                   (let [data (cljs.reader/read-string response)
                         note {:_id  (:_id data)
                               :val note-val}]
                     (.log js/console data)
                     (swap! notes conj note)))})))

(defn delete-note! [id]
  (GET "/delete-note"
       {:params  {:id id}
        :handler (fn [response]
                   (delete-note-by-id! id))}))

(defn home-page []
  [:div
   [:h "Todo List"]
   [:ul {:style {:font-size "14px"}}
    (for [{:keys [_id val]} @notes]
      [:li {:onClick #(delete-note! _id)}
        val])]
   [:input#note {:style {:margin-right "2px"}}]
   [:button {:onClick save-note!}
    "Add"]])


;; Initialize app
;; ==============
(defn init! []
  (GET "/notes"
       {:params  {}
        :handler (fn [response]
                   (let [data (cljs.reader/read-string response)
                         db-notes (:notes data)]
                     (reset! notes (vec db-notes))))})
  (render [home-page] (.getElementById js/document "app")))
