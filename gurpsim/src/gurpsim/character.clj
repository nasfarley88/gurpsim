(ns gurpsim.character)

(defn base-stat-value
  "Checks the value of a stat for a given keyword"
  [character stat]
  (+ (get-in character [stat :value])
     (if (get-in character [stat :mod])
       (get-in character [stat :mod])
       0)))

(defn st
  "Check the value of st"
  [character]
  (base-stat-value character :st))

(defn iq
  "Check the value of iq"
  [character]
  (base-stat-value character :iq))

(defn dx
  "Check the value of dx"
  [character]
  (base-stat-value character :dx))

(defn ht
  "Check the value of ht"
  [character]
  (base-stat-value character :ht))

(defn basic-speed
  "Calculates basic speed"
  ([character]
   (basic-speed
    (base-stat-value character :dx) (base-stat-value character :ht)))
  ([dx ht]
   ;; TODO create multi arity function to take into account size etc.
   (float (/ (+ dx ht) 4)))
  )

(defn basic-move
  "Calculates basic move."
  [character]
  (int (basic-speed character)))

(defn dodge
  "Calculates dodge."
  [character]
  ;; TODO include advantages like Enhanced Dodge
  (+ (basic-move character) 3)
  )

(defn get-level
  [character stat]
  (get-in character [stat :level]))

(defn hp
  "Calculates max hp."
  [character]
  (+ (base-stat-value character :st) (get-in character [:hp :level])))

(defn current-hp
  "Calculates max hp."
  [character]
  (:current-hp character))

(defn per
  "Calculates max per."
  [character]
  (+ (base-stat-value character :iq) (get-in character [:per :level])))

(defn will
  "Calculates max will."
  [character]
  (+ (base-stat-value character :iq ) (get-in character [:will :level])))

(defn fp
  "Calculates max fp."
  [character]
  (+ (base-stat-value character :ht) (get-in character [:fp :level])))


(defn create
  "Create a skeleton character from the four stats"
  ([] 
   (create 10 10 10 10))
  ;; TODO create an arity that accepts only keys
  ([st dx iq ht & {:keys [
                          hp
                          per
                          will
                          fp
                          name
                          advantages
                          disadvantages
                          skills
                          quirks
                          weapons
                          armour
                          ]
                   :or {
                        hp 0
                        per 0
                        will 0
                        fp 0
                        name "X"
                        advantages {}
                        disadvantages {}
                        skills {}
                        quirks {}
                        weapons {}
                        armour {}}}]
   {
    :name name
    :st {:value st}
    :dx {:value dx}
    :iq {:value iq}
    :ht {:value ht}
    :hp {:level hp}
    :current-hp st
    :per {:level per}
    :will {:level will}
    :fp {:level fp}
    :basic-speed {:level 0}
    :basic-move {:level 0}
    :advantages advantages
    :disadvantages disadvantages
    :skills skills
    :quirks quirks
    :weapons weapons
    :armour armour
    }))

(def test-char (create
                9
                10
                12
                10
                :name "Annie Lightbulb"
                :skills {:guns-pistol {:level 3}}))
