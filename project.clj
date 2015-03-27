(defproject img-serve "0.1"
  :description "Micro-service to serve up images"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/data.json "0.2.6"]
                 [compojure "1.3.2"]
                 [com.taoensso/timbre "3.4.0"]
                 [http-kit "2.1.19"]
                 [com.stuartsierra/component "0.2.3"]]

  :main ^:skip-aot img-serve.main)
