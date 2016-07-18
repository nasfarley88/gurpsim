(ns gurpsim.combat)

(require '[gurpsim.dice :as dice])

(defn hit?
  "Checks if a given roll is a hit"
  [roll value & modifiers]
  (let [real-value (+ value (apply + modifiers))]
    (if (<= real-value 16)
      (<= (dice/sum-roll roll) real-value)
      (<= (dice/sum-roll roll) 16))))

(defn critical-success?
  "Checks if a given roll is a crit"
  [roll value & modifiers]
  (let [real-value (+ value (apply + modifiers))]
    (condp <= real-value
      16 (<= (dice/sum-roll roll) 6)
      15 (<= (dice/sum-roll roll) 5)
      (<= (dice/sum-roll roll) 4))))

(defn total-damage
  "Total up damage from object."
  ([{:keys [roll mod]}]
   (total-damage roll mod))
  ([roll & modifiers]
   (let [total (+ (dice/sum-roll roll) (apply + modifiers))]
     (if (> 1 total)
       1
       total))))

(defn unarmed-thrust
  "TODO Will roll for damage for given strength/brawling/etc."
  [character]
  (dice/roll 1))

(defn unconcious?
  "Determins whether a character is unconcious."
  [character]
  (true? (:unconcious character)))
