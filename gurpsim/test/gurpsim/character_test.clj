(ns gurpsim.character-test
  (:require [gurpsim.character :as sut]
            [clojure.test :as t]))

;; TODO figure out a way to test sut/create

(t/deftest base-stat-value
  (t/testing "Base stat value with default character"
    (t/is (= 10 (sut/base-stat-value (sut/create) :st)))
    (t/is (= 10 (sut/base-stat-value (sut/create) :dx)))
    (t/is (= 10 (sut/base-stat-value (sut/create) :iq)))
    (t/is (= 10 (sut/base-stat-value (sut/create) :ht)))))

(t/deftest basic-speed
  (t/testing "Basic speed with default character"
    (t/is (= 5.0 (sut/basic-speed (sut/create))))))

(t/deftest basic-move
  (t/testing "Basic speed with default character"
    (t/is (= 5 (sut/basic-move (sut/create))))))

(t/deftest dodge
  (t/testing "Basic speed with default character"
    (t/is (= 8 (sut/dodge (sut/create))))))

(t/deftest hp
  (t/testing "Basic speed with default character"
    (t/is (= 10 (sut/hp (sut/create))))
    (t/is (= 13 (sut/hp (sut/create 10 0 0 0 :hp 3))))))

(t/deftest per
  (t/testing "Basic speed with default character"
    (t/is (= 10 (sut/per (sut/create))))
    (t/is (= 13 (sut/per (sut/create 0 0 10 0 :per 3))))))

(t/deftest will
  (t/testing "Basic speed with default character"
    (t/is (= 10 (sut/will (sut/create))))
    (t/is (= 13 (sut/will (sut/create 0 0 10 0 :will 3))))))

(t/deftest fp
  (t/testing "Basic speed with default character"
    (t/is (= 10 (sut/fp (sut/create))))
    (t/is (= 13 (sut/fp (sut/create 0 0 0 10 :fp 3))))))
