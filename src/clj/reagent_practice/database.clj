(ns reagent-practice.database
  (:require [monger.core :as mg]
            [monger.collection :as mc])
  (:import org.bson.types.ObjectId))

;; DB Accessor
;; ===========
(def db (mg/get-db (mg/connect) "mydb"))


;; Helper
;; ======
(defn clean-note [{:keys [_id val]}]
  {:_id (str _id) :val val})


;; Bridge
;; ======
(defn save-note! [note]
  (:_id (clean-note (mc/insert-and-return db "todo_list" {:val note}))))

(defn delete-note! [id]
  (mc/remove-by-id db "todo_list" (ObjectId. id)))

(defn get-notes []
  (->> (mc/find-maps db "todo_list")
       (map clean-note)))

(defn get-note-by-id [id]
  (let [note (mc/find-map-by-id db "todo_list" (ObjectId. id))]
    (clean-note note)))
