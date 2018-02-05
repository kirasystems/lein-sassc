(defproject kirasystems/lein-sassc "0.10.6"
  :description "Leiningen plugin to compile SASS/SCSS files with SassC."
  :url "https://github.com/kirasystems/lein-sassc"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :scm {:name "git"
        :url "https://github.com/kirasystems/lein-sassc.git"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [me.raynes/fs "1.4.6"]]
  :hooks [leiningen.sassc]
  :eval-in-leiningen true
  :min-lein-version "2.0.0")
