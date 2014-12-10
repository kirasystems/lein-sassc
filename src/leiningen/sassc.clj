(ns leiningen.sassc
  "Compile SASS/SCSS source into a CSS file."
  (:require [clojure.java.shell :as shell]
            [clojure.string :as string]
            [me.raynes.fs :as fs]
            [robert.hooke :as hooke]
            [leiningen.compile :as lcompile]
            [leiningen.clean :as lclean]
            [leiningen.help :as lhelp]
            [leiningen.core.main :as lmain]))


(defn- create-config [node]
  (let [default {:src         "src/scss/main.scss"
                 :output-to   "target/sassc/main.css"
                 :style       "nested"
                 :import-path "src/scss"}]
    (merge default node)))


(defn- run-sassc-command
  "Run sassc command, compile a SASS/SCSS file."
  [config]
  (let [{:keys [src output-to style import-path]} config
        command (str "sassc -t " style " -I " import-path " " src " " output-to)]
    (println command)
    (apply shell/sh (string/split command #"\s+"))))


(defn- compile-node [node]
  (let [config (create-config node)]
    (-> (:output-to config)
        (fs/parent)
        (fs/mkdirs))
    (run-sassc-command config)))


(defn- once
  "Compile the :sassc project once."
  [project]
  (println "Compile SASS/SCSS files.")
  (doseq [node (:sassc project)]
    (as-> (compile-node node) %
          (case (:exit %)
            0 (println (:out %))
            1 (println (:err %))))))


(defn- clean
  "Remove automatically generated files."
  [project]
  (println "Deleting files generated by lein-sassc.")
  (doseq [node (:sassc project)]
    (fs/delete (:output-to node))))


(defn- abort [s]
  (println s)
  (lmain/abort))


(defn sassc
  "Run the sassc plugin."
  {:help-arglists '([once clean])
   :subtasks [#'once #'clean]}
  ([project]
     (abort (lhelp/help-for "sassc")))
  ([project subtask & args]
     (case (keyword subtask)
       :once  (once project)
       :clean (clean project)
       (abort (str "Subtask" \" subtask \" "not found. "
                   (lhelp/subtask-help-for *ns* #'sassc))))))


;; activate hooks. (first args) is project.

(defn compile-hook [task & args]
  (apply task args)
  (once (first args)))


(defn clean-hook [task & args]
  (apply task args)
  (clean (first args)))


(defn activate []
  (hooke/add-hook #'lcompile/compile #'compile-hook)
  (hooke/add-hook #'lclean/clean #'clean-hook))
