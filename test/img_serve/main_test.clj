(ns img-serve.main-test
  (:require [clojure.test :refer :all]
            [img-serve.main :refer :all]
            [img-serve.db :refer [get-image]]))

(deftest test-image-response
  (testing "happy path content-type header is image/jpg"
    (with-redefs [get-image (fn [_ _] "test-location")]
      (let [response (image-response {:db-path "test"
                                      :images {:id "1"
                                               :location "test-location"}}
                                     "1")]
        (is (= {"Content-Type" "image/jpg"} (:headers response)))
        (is (= 200 (:status response))))))
  (testing "id not found produces 404"
    ))