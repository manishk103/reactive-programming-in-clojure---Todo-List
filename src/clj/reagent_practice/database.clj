(ns reagent-practice.database
  (:require [monger.core :as mg]
            [monger.collection :as mc])
  (:import org.bson.types.ObjectId))

;; DB Accessor
;; ===========
(def db (mg/get-db (mg/connect) "mydb"))


;; Bridge
;; ======
(defn save-note! [note]
  (let [{:keys [_id val]} (mc/insert-and-return db "todo_list" {:val note})]
    (str _id)))

(defn delete-note! [id]
  (mc/remove-by-id db "todo_list" (ObjectId. id)))

(defn get-notes []
  (mc/find-maps db "todo_list"))
