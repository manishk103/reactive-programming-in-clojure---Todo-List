(ns reagent-practice.prod
  (:require [reagent-practice.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
