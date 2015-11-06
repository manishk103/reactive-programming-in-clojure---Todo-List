(ns reagent-practice.handler
  (:require [compojure.core :refer [GET defroutes]]
            [compojure.route :refer [not-found resources]]
            [ring.middleware.defaults :refer [site-defaults wrap-defaults]]
            [hiccup.core :refer [html]]
            [hiccup.page :refer [include-js include-css]]
            [prone.middleware :refer [wrap-exceptions]]
            [ring.middleware.reload :refer [wrap-reload]]
            [environ.core :refer [env]]
            [reagent-practice.database :refer :all]))

(def home-page
  (html
   [:html
    [:head
     [:meta {:charset "utf-8"}]
     [:meta {:name "viewport"
             :content "width=device-width, initial-scale=1"}]
     (include-css (if (env :dev) "css/site.css" "css/site.min.css"))]
    [:body
     [:div#app]
     (include-js "js/app.js")]]))

(defroutes routes
  (GET "/" [] home-page)

  (GET "/notes" []
    (prn-str {:notes (get-notes)}))

  (GET "/save-note" [note]
    (prn-str {:_id (save-note! note)}))

  (GET "/delete-note" [id]
    (let [resp {:success true}]
      (delete-note! id)
      (prn-str resp)))

  (GET "/get-note-by-id" [id]
    (prn-str (get-note-by-id id)))

  (resources "/")
  (not-found "Not Found"))

(def app
  (let [handler (wrap-defaults #'routes site-defaults)]
    (if (env :dev) (-> handler wrap-exceptions wrap-reload) handler)))
