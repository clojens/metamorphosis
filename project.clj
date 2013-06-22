(defproject metamorphosis "0.1.2"
  :description "Macro protocols for clojure"
  :url "https://github.com/hugoduncan/metamorphosis"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]]
  :test-selectors {:default (complement :not-lein)})
