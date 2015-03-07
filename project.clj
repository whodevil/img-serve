(defproject img-serve "0.1"
  :description "Micro-service to serve up images"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/data.json "0.2.5"]
                 [compojure "1.1.8"]
                 [com.taoensso/timbre "3.3.1"]
                 [http-kit "2.1.16"]]

  :main ^:skip-aot img-serve.main)
