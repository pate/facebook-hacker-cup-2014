(ns basketball.core)
(require '[clojure.string :as s])

(defn parse-player [s]
  (let [[name rate height] (s/split s #" ")]
    {:name name,
     :rate (read-string rate),
     :height (read-string height), :time 0}
))

(defn drop-by-index [idx coll]
  (remove #(= (:index %) idx) coll))

(defn play-game [team-size m p playing benched]
  "where m is minutes remaining"
  (if
   (or (<= m 0) (>= p team-size)) playing
   (let [playing (map #(update-in % [:time] inc) playing)
         least-played (first (sort-by (juxt :time :index) benched))
         most-played (last (sort-by (juxt :time :index) playing))
         now-playing (conj (drop-by-index (:index most-played) playing) least-played)
         now-benched (conj (drop-by-index (:index least-played) benched) most-played)]
     (play-game team-size (dec m) p now-playing now-benched))
   ))

(defn solve-case [case]
  "where case is a map of :n, :m, :p and :players"
  (let [{:keys [n m p players]} case
        sorted-players (reverse (sort-by (juxt :rate :height) players))
        drafted (map-indexed #(assoc %2 :index (inc %)) sorted-players)

        evens (filter #(even? (:index %)) drafted)
        even-split (split-at p evens)

        odds (filter #(odd? (:index %)) drafted)
        odds-split (split-at p odds)
        ]
    (s/join " " (sort (concat
           (map :name (play-game (count evens) m p (first even-split) (second even-split)))
           (map :name (play-game (count odds) m p (first odds-split) (second odds-split))))))
    ;[N M P evens odds players]
  ))

(defn parse-case [s]
  (let [lines (s/split-lines s)
        [N M P] (map read-string (s/split (first lines) #" "))]
    {:n N, :m M, :p P, :players (map parse-player (rest lines))}
    ))

(defn read-case []
  (let [[N M P] (map read-string (s/split (read-line) #" "))]
    {:n N, :m M, :p P, :players (map parse-player (repeatedly N read-line))}
    ))

(defn -main []
  (let [T (Integer/parseInt (read-line))]
    (dotimes [n T]
      (println (str "Case #" (inc n) ": " (solve-case (read-case)))))))





