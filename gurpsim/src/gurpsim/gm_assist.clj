(ns gurpsim.gm-assist)

(require '[gurpsim.combat :as combat]
         '[gurpsim.dice :as dice]
         '[gurpsim.character :as character]
         '[gurpsim.skills :as skills]
         '[gurpsim.core :as core]
         '[seesaw.core :as ui]
         )


(defn zombie-window
  "Makes a window of zombies"
  []
  (-> (ui/frame
       :title "Zombies"
       :content (ui/grid-panel
                 :columns 3
                 :items ["" "Zombie 1" ""
                         "Zombie 6" "" "Zombie 2"
                         "Zombie 5" "" "Zombie 3"
                         "" "Zombie 4" ""]))
      ui/pack! ui/show!))
