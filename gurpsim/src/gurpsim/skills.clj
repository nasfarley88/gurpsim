(ns gurpsim.skills)

(defn get-skill-level
  "Gets skill level from character"
  [skill-keyword character]
  (get-in character [:skills skill-keyword :level]))

(defn effective-skill-level
  "Gets effective skill level from difficulty."
  ([difficulty stat level]
  (condp = difficulty
    :easy (+ stat level)
    :average (+ stat -1 level)
    :hard (+ stat -2 level)
    :very-hard (+ stat -3 level))))

(defn mod-for-tl-iq-based
  "Modify effective level for Tech Level difference (IQ based skill)."
  [player-skill-tl object-tl]
  (let [tl-diff (- object-tl player-skill-tl)]
    (cond
      (> tl-diff 3) Double/NEGATIVE_INFINITY
      (= tl-diff 3) -15
      (= tl-diff 2) -10
      (= tl-diff 1) -5
      (= tl-diff 0) 0
      (< tl-diff 0) (+ (* -2 (Math/abs tl-diff)) 1))))


(defn mod-for-tl-not-iq-based
  "Modify effective level for Tech Leve difference (not IQ based skill)."
  [player-skill-tl object-tl]
  (- (Math/abs (- object-tl player-skill-tl))))


(defn guns
  "Gets the effective skill level for Guns

  If no player-skill-tl and gun-tl is given, this function assumes they are both
  0 (i.e. there is no difference in tech level)"
  ([dx level]
   (guns dx level 0 0))
  ([dx level player-skill-tl gun-tl]
   (+
    (effective-skill-level :easy dx level)
    (mod-for-tl-not-iq-based player-skill-tl gun-tl))))
