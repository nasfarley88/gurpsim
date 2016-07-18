(ns gurpsim.core
  (:gen-class)
  ;; (:require [gurpsim.character :as character])
  )

(require '[gurpsim.combat :as combat]
         '[gurpsim.dice :as dice]
         '[gurpsim.character :as character]
         '[gurpsim.skills :as skills]
         '[seesaw.core :as gui]
)

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))


(defn take-turns
  "Takes turns for a 2 combatants

  Turns must take the signature [combat-state] (where combat-state is a vector
  of combatants (as a seq) and stats (as a map). Turns should return the same as
  they take."
  ;; ([combatants end-condition? turns]
  ;;  (take-turns attacker defender end-condition? turns {}))
  ([combat-state end-condition? turns]
  (let [resultant-state ((first turns) combat-state)]
    (if (end-condition? resultant-state)
      resultant-state
      (recur resultant-state end-condition? (rest turns))))))

(defn simple-attack
  "A simple attack."
  [attacker]
  (combat/hit? (dice/roll) (character/base-stat-value attacker :st)))

(defn simple-dodge
  "A simple dodge."
  [defender]
  (dice/pass? (dice/roll) (character/dodge defender)))

(defn attack-damage
  "Simple attack damage."
  [attacker defender]
  (assoc defender :current-hp (- (:current-hp defender) (dice/sum-roll (dice/roll 1)))))

(defn negative-hp?
  "Tests if a character has negative HP."
  [character]
  (neg? (character/current-hp character)))

(defn attack-then-dodge-fn
  "A simple turn that takes a single attacker and defender. One attacks, and one
  defends via dodge."
  [combat-state attack dodge attack-damage]
  (let [attacker (ffirst combat-state)
        defender (last (first combat-state))
        stats (last combat-state)]
    (if (and
         (negative-hp? attacker)
         (dice/fail? (dice/roll) (character/ht attacker)))
      [[(assoc attacker :unconcious true) defender]
       (assoc stats
              :what-happened (str (:name attacker) " fainted")
              :turns (+ 1 (:turns stats)))]
      (if (attack attacker)
        (if (not (dodge defender))
          ;; Attacker and defender switch places
          [[(attack-damage attacker defender)
            attacker]
           stats]
          [[defender attacker] stats])
        [[defender attacker] stats]))))
        ;;    (assoc stats
        ;;           :what-happened "Attack successful." 
        ;;           :turns (+ 1 (:turns stats)))]
        ;;   [[defender attacker] (assoc stats
        ;;                               :what-happened "Attack dodged."
        ;;                               :turns (+ 1 (:turns stats)))])
        ;; [[defender attacker] (assoc stats 
        ;;                             :what-happened "Attack missed."
        ;;                             :turns (+ 1 (:turns stats)))]))))

(def attack-then-dodge #(attack-then-dodge-fn % simple-attack simple-dodge attack-damage))



;; I know I shouldn't but... I just did. Really I should use let
(def attacker (character/create 10 34 12 14 :name "Attacker"))
(def attacker (assoc attacker :name (str (:name attacker) " Dodge " (character/dodge attacker))))
(def defender (character/create 10 14 10 14 :name "Defender"))
(def defender (assoc defender :name (str (:name defender) " Dodge " (character/dodge defender))))
(def default-combat-state [[attacker defender] {:turns 0}])

(defn take-turns-demo
  ""
  []
  (take-turns default-combat-state #(combat/unconcious? (first (first %))) (repeat attack-then-dodge)))


(defn test
  ""
  [n]
  (time (sort (frequencies (apply pcalls (repeat n #(:what-happened (last (take-turns-demo)))))))))

