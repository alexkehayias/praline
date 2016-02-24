(ns ^:figwheel-always praline.dev
    (:require [praline.core :refer [mk-inspector-state
                                    mk-inspector-app-state
                                    mount-inspector]]
            [praline.mock :refer [mock-state]]))

(def *test-state* (mk-inspector-state mock-state))
(def *test-app-state* (mk-inspector-app-state))

(mount-inspector *test-state* *test-app-state*)
