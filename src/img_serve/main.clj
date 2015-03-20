(ns img-serve.main
   (:require [compojure.core :refer [routes GET]]
             [compojure.route :as route]
             [ring.util.response :refer [resource-response file-response]]
             [clojure.data.json :as json]
             [clojure.edn :as edn]
             [org.httpkit.server :as httpd]
             [taoensso.timbre :as timbre :refer [info]]
             [img-serve.db :as db]))

(defn image-response
  [config id]
  (let [response (file-response (db/get-image config id) {:root (:db-path config)})]
    (assoc response :headers {"Content-Type" "image/jpg"})))

(defn make-app
  [config]
  (routes
   (GET "/image/:id" [id] (image-response config id))
   (GET "/tag/:tag" [tag] (json/write-str (db/find-images config tag)))

   (route/resources "/")
   (route/not-found "Not Found")))

;;-----------------------------------------------------------------------------
;; HTTPD concerns

(defn start-httpd!
  [httpd config]
  (let [s (httpd/run-server (make-app config) {:port 9001
                                        :worker-name-prefix "hkit-"})]
    (info "Running web application on http://localhost:9001.")
    (reset! httpd s)))

(defn stop-httpd!
  [httpd]
  (when-let [s @httpd]
    (s))
  (reset! httpd nil))

(defn make-httpd
  []
  (atom nil))

;;-----------------------------------------------------------------------------
;; Application Bootstrap Concerns

(defn- hook-shutdown!
  [f]
  (doto (Runtime/getRuntime)
    (.addShutdownHook (Thread. f))))

(defn- boot!
  [system config]
  (info "Booting system.")
  (let [httpd (make-httpd)]
    (swap! system assoc :httpd httpd)
    (start-httpd! httpd config)))

(defn- crash!
  [system]
  (info "System shutting down.")
  (stop-httpd! (:httpd @system))
  (swap! system dissoc :httpd))

(defonce ^:private system
  (atom {}))

;;-----------------------------------------------------------------------------
;; Application Entry Point

(defn -main
  [& args]
  (let [lock (promise)
        db-path (first args)
        config {:db-path db-path
                :images (edn/read-string (slurp (str db-path "/db.edn")))}]
    (hook-shutdown! (fn [] (crash! system)))
    (hook-shutdown! (fn [] (deliver lock :done)))
    (boot! system config)
    @lock
    (System/exit 0)))

