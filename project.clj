(defproject hack-news "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/tools.nrepl "0.2.13"]

                 ; Web
                 [prismatic/schema "1.1.9"]
                 [metosin/compojure-api "2.0.0-alpha26"]
                 [ring/ring-jetty-adapter "1.6.3"]
                 [ring/ring-devel "1.6.3"]

                 ; Database
                 [toucan "1.1.9"]
                 [org.postgresql/postgresql "42.2.4"]

                 ; Testing
                 [clj-http "3.10.0"]
                 [org.clojure/data.json "0.2.7"]]

  :repl-options {:init-ns core.main
                 :init (-main)}
  :init-ns core.main)
