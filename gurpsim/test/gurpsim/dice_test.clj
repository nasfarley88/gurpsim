(ns gurpsim.dice-test
  (:require [gurpsim.dice :as sut]
            [clojure.test :as t]))


;; TODO make this test more rigorous. It's for something random, so I'm not sure
;; how to deal with that.
(t/deftest roll-single-die
  (t/testing "Rolling a single die"
    (let [die-roll (sut/roll-single-die)]
      (t/is (> 7 die-roll))
      (t/is (< 0 die-roll)))))


(t/deftest pass?
  (t/testing "Testing pass values"
    (t/testing "If value is greater than 16 (should return false)"
      (t/is (not (sut/pass? [1 1 1] Double/NEGATIVE_INFINITY))))
    (t/testing "If value is 8 and roll is [1 1 1] (should return true)"
      (t/is (sut/pass? [1 1 1] 8)))))

(t/deftest fail?
  (t/testing "Testing fail values"
    (t/testing "Test against value of negative infinity"
      (t/is (sut/fail? [1 1 1] Double/NEGATIVE_INFINITY)))
    (t/testing "If value is 8 and roll is [1 1 1] (should return true)"
      (t/is (not (sut/fail? [1 1 1] 8))))))

(t/deftest sum-roll
  (t/testing "Testing summation of rolls."
    (t/is (= 18 (sut/sum-roll [6 6 6])))))
