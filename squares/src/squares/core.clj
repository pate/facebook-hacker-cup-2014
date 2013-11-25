(ns squares.core)

(def test-cases [
            [false
             [0 1 1 0]
             [0 1 1 0]
             [1 1 1 0]
             [0 0 0 1]]

            [false
             [0 1 1 0]
             [0 1 1 0]
             [0 1 1 0]
             [0 0 0 1]]

            [false
             [0 1 1 1]
             [0 1 1 1]
             [0 1 1 1]
             [0 1 1 1]]

            [false
             [1 0 0 1]
             [1 0 0 1]
             [1 0 0 1]
             [1 1 1 0]]

            [true
             [0 1 1 0]
             [0 1 1 0]
             [0 0 0 0]
             [0 0 0 0]]

            [false
             [0 1 1 1]
             [0 1 1 1]
             [0 1 1 1]
             [1 0 0 0]]

            [true
             [0 1 1 1]
             [0 1 1 1]
             [0 1 1 1]
             [0 0 0 0]]

            [true
             [1 1 1 1]
             [1 1 1 1]
             [1 1 1 1]
             [1 1 1 1]]

            [false
             [1 1 1 1 1]
             [1 0 0 0 1]
             [1 0 0 0 1]
             [1 0 0 0 1]
             [1 1 1 1 1]]

            [false
             [1 1 1 1 1]
             [1 0 0 0 1]
             [1 0 1 0 1]
             [1 0 0 0 1]
             [1 1 1 1 1]]

            [false
             [1 1 1 1 1]
             [1 0 1 0 1]
             [1 1 0 1 1]
             [1 0 1 0 1]
             [1 1 1 1 1]]
])

(defn analyse [sums]
  "Takes summed matrix lines and partitions to find running sequences of squares"
  (dissoc (apply merge-with
       (comp vec flatten vector)
       (map (partial apply hash-map)
                  (map (juxt first count) (partition-by identity sums)))
       ) 0)
)


(defn parse-line [s]
  (map #(if (= % \#) 1 0) s))

(map parse-line ["##...##" "..####"])

(defn transpose [s] (apply map vector s))

(defn square? [grid]
  "Takes grid and looks for a filled square."
  (let [rows (apply map + grid)
        cols (apply map + (transpose grid))
        analysed-rows (analyse rows)
        analysed-cols (analyse cols)
        m (apply max (concat (keys analysed-rows) (keys analysed-cols)))
        rows-max (analysed-rows m)
        cols-max (analysed-cols m)]
   (cond
     (> (count analysed-rows) 1) false
     (> (count analysed-cols) 1) false
     (not= rows-max m) false
     (not= cols-max m) false
     :else true
    )))

(defn read-case []
  "reads in size and lines of next case.
   Returns: lazy list of unparsed strings"
  (let [size (Integer/parseInt (read-line))]
    (repeatedly size read-line)))

(defn solve-case [n lines]
  (let [parsed-case (map parse-line lines)]
  (println (str "Case #" n ": " (if (square? parsed-case) "YES" "NO")))
    ))

; tests
(map #(if (= (first %) (square? (rest %))) true (throw (Throwable. "Test failed"))) test-cases)

(defn -main []
  (let [n-cases (Integer/parseInt (read-line))]
    (dotimes [n n-cases]
      (solve-case (inc n) (read-case))
      )))

















