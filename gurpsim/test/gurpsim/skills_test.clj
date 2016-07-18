(ns gurpsim.skills-test
  (:require [gurpsim.skills :as sut]
            [gurpsim.character :as character]
            [clojure.test :as t]))


;; This is identical to the test character in character, but for the sake of
;; clarity I've included it here for testing. Feel free to edit this character
;; to make it have more edge cases etc. for testing.
(def c (character/create
        9
        10
        12
        10
        :name "Annie Lightbulb"
        :skills {:guns-pistol {:level 3}}))

(t/deftest get-skill-level
  (t/testing "Get :gun-pistol skill level"
    (t/is (= 3 (sut/get-skill-level :guns-pistol c)))))

(t/deftest effective-skill-level
  (t/testing "Get effective skill level (here is guns based on dx)"
    (t/is (= 13 (sut/effective-skill-level :easy
                                           (character/base-stat-value c :dx)
                                           (sut/get-skill-level :guns-pistol c))))))

(t/deftest mod-for-tl-iq-based
  (t/testing "Tests for modify for Tech Level for IQ based skills."
    (t/testing "Tech level too high"
      (t/is (= Double/NEGATIVE_INFINITY (sut/mod-for-tl-iq-based 7 11))))
    (t/testing "Tech level higher but achievable."
      (t/is (= -15 (sut/mod-for-tl-iq-based 7 10)))
      (t/is (= -10 (sut/mod-for-tl-iq-based 7 9)))
      (t/is (= -5 (sut/mod-for-tl-iq-based 7 8))))
    (t/testing "No change in tech level"
      (t/is (= 0 (sut/mod-for-tl-iq-based 7 7))))
    (t/testing "Change for lower tech level"
      (t/is (= -1 (sut/mod-for-tl-iq-based 7 6))))))

(t/deftest mod-for-tl-not-iq-based
  (t/testing "Testing for modify for Tech Level for non IQ based skills."
    (t/testing "Tech level too high"
      (t/is (= -1 (sut/mod-for-tl-not-iq-based 7 8))))
    (t/testing "Tech level too low"
      (t/is (= -1 (sut/mod-for-tl-not-iq-based 7 6))))))

(t/deftest guns
  (t/testing "Testng gun skill calculation"
    (t/is (= 13 (sut/guns (character/base-stat-value c :dx) (sut/get-skill-level :guns-pistol c))))
    (t/is (= 12 (sut/guns (character/base-stat-value c :dx) (sut/get-skill-level :guns-pistol c) 7 6)))))
