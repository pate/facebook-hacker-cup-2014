(ns labels.core)
(require '[clojure.string :as s])

(defn f [n coll]
  (if (zero? n)
    []
    (let [base (count coll)
          n (dec n)
          r (mod n base)]
      (conj (f (quot n base) coll) (nth coll r)))))

(defn read-case []
  (let [args (s/split (read-line) #" ")]
        [(first args) (Long/parseLong (second args))]))

(defn solve-case [n-case args]
  (let [[letters n] args]
  (println (str "Case #" n-case ": " (s/join (f n (seq letters)))))))

(defn -main []
  (let [n-cases (Long/parseLong (read-line))]
    (dotimes [n n-cases]
      (solve-case (inc n) (read-case)))))
