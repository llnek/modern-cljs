(set-env!
 :source-paths #{"src/main/clojure" "src/main/cljs"}
 :resource-paths #{"src/web/main"}

 :dependencies '[[org.clojars.magomimmo/domina "2.0.0-SNAPSHOT"]
                [org.clojure/clojure "1.8.0"]         ;; add CLJ
                 [org.clojure/clojurescript "1.8.51"]
                 [adzerk/boot-cljs "1.7.228-1"]
                 [adzerk/boot-reload "0.4.7"]
                 [hiccups "0.3.0"]
                 [compojure "1.5.0"]
                  [adzerk/boot-cljs-repl "0.3.0"]
[com.cemerick/piggieback "0.2.1" :scope "test"]
[weasel "0.7.0" :scope "test"]
[org.clojure/tools.nrepl "0.2.12" :scope "test"]
                 [pandeiro/boot-http "0.7.3"]])

(require '[adzerk.boot-cljs :refer [cljs]]
         '[adzerk.boot-reload :refer [reload]]
         '[adzerk.boot-cljs-repl :refer [cljs-repl start-repl]]
         '[pandeiro.boot-http :refer [serve]])

(deftask dev
  "Launch Immediate Feedback Development Environment"
  []
  (comp
   (serve :handler 'modern-cljs.core/handler ;; add ring handler
          :resource-root "public"            ;; add resource-path
          :reload true)
   (watch)
   (reload)
   (cljs-repl) ;; before cljs task
   (cljs)
   (target :dir #{"public"})))

