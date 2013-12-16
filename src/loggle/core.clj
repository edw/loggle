(ns loggle.core)

(def log-level-function (atom (fn [] 0)))

(defn current-log-level
  []
  (@log-level-function))

(defn set-log-level-function!
  [f]
  (reset! log-level-function f))

(defn logf
  [level fmt & args]
  (when (>= (current-log-level) level)
    (binding [*out* *err*] (apply printf fmt args) (flush))))

(defmacro when-log
  [LEVEL & BODY]
  `(when (>= (current-log-level) ~LEVEL)
     (binding [*out* *err*])
     (let [result# (do ~@BODY)]
       (flush)
       result#)))
