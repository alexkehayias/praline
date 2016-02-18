(defproject praline "0.1.0-SNAPSHOT"
  :description "A wrapper around the Chocolatier game engine that provides inspection tools"
  :source-paths ["src/clj" "src/cljs"]
  :test-paths ["test/clj" "test/cljs"]
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.228"]

                 ;; Web server
                 [ring "1.2.0"]
                 [compojure "1.1.5"]

                 ;; React bindings
                 [reagent "0.5.1" :exclusions [org.clojure/tools.reader]]
                 [reagent-forms "0.5.13"]
                 [reagent-utils "0.1.5"]

                 ;; Async channels
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"
                  :exclude [org.clojure/clojurescript]]]

  :plugins [[lein-cljsbuild "1.1.1" :exclude [org.clojure/clojurescript]]
            [lein-figwheel "0.5.0-1" :exclude [org.clojure/clojurescript]]
            [refactor-nrepl "1.1.0"]]

  :profiles {:dev {:dependencies [[com.cemerick/piggieback "0.2.1"
                                   :exclude [org.clojure/clojurescript]]
                                  [org.clojure/tools.nrepl "0.2.12"]]
                   :repl-options {:nrepl-middleware
                                  [cemerick.piggieback/wrap-cljs-repl]}}}

  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"]

  :cljsbuild {:builds
              [{:id "dev"
                :source-paths ["src/cljs" "test/cljs"]
                :figwheel true
                :compiler {:main "praline.core"
                           :asset-path "/js/compiled/out"
                           :output-to "resources/public/js/compiled/praline.js"
                           :output-dir "resources/public/js/compiled/out"
                           :source-map-timestamp true
                           :pretty-print true}}]}

  :figwheel {:http-server-root "public" ;; default and assumes "resources"
             :server-port 1222
             :css-dirs ["resources/public/css"] ;; watch and update CSS

             ;; Start an nREPL server into the running figwheel process
             :nrepl-port 8998
             ;; Need this now to get nrepl to work
             :nrepl-middleware ["cider.nrepl/cider-middleware"
                                "refactor-nrepl.middleware/wrap-refactor"
                                "cemerick.piggieback/wrap-cljs-repl"]

             ;; Server Ring Handler (optional)
             ;; if you want to embed a ring handler into the figwheel http-kit
             ;; server, this is for simple ring servers, if this
             ;; doesn't work for you just run your own server :)
             ;; :ring-handler praline.server/app
             })
