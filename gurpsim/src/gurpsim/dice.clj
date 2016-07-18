(ns gurpsim.dice)

(defn roll-single-die
  "Rolls single d6"
  []
  (+ (rand-int 6) 1))

(defn roll
  "rolls xd6"
  ([]
  (roll 3))
  ([x]
  ;; (apply pcalls (repeat x roll-single-die))))
   (repeatedly x roll-single-die)))

(defn sum-roll
  "Sums a given roll, or rolls and then sums."
  ([roll]
   (reduce + roll))
  ([]
   (reduce + (roll))))

(defn pass?
  "Tests whether a given roll passes."
  [roll value]
  (if (<= value 16)
    (<= (sum-roll roll) value)
    (<= (sum-roll roll) 16)))

(def fail? (complement pass?))

