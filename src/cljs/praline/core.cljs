(ns ^:figwheel-always praline.core
    (:require [reagent.core :as reagent :refer [atom]]
              [praline.styles :refer [default-style]]
              [praline.mock :refer [mock-state]]
              [praline.controls :refer [input-number
                                        input-keyword]]))


(enable-console-print!)

(defprotocol InspectComponent
  "Provides methods for returning a Reagent component for inspection"
  (inspect [this label key-path app-state state]
    "Returns a component for inspecting this type"))

(extend-type PersistentArrayMap
  InspectComponent
  (inspect [this label key-path app-state state]
    [:div.row.gutter
     [:div.grid.twelve
      [:h3 label]
      (for [[k v] this]
        ^{:key k}
        [inspect v (name k) (conj key-path k) app-state state])]]))

(extend-type PersistentHashMap
  InspectComponent
  (inspect [this label key-path app-state state]
    [:div.row.gutter
     [:div.grid.twelve
      [:h3 label]
      (for [[k v] this]
        ^{:key k}
        [inspect v (name k) (conj key-path k) app-state state])]]))

(extend-type PersistentVector
  InspectComponent
  (inspect [this label key-path app-state state]
    [:div.row.gutter
     [:div.grid.twelve
      (for [i this]
        ^{:key i}
        [inspect i nil key-path app-state state])]]) )

(extend-type PersistentHashSet
  InspectComponent
  (inspect [this label key-path app-state state]
    (assert false "Not implemented!")))

(extend-type number
  InspectComponent
  (inspect [this label key-path app-state state]
    [:div.row.gutter
     [:div.grid.four [:p [:b label]]]
     [:div.grid.eight [input-number state key-path this]]]))

(extend-type Keyword
  InspectComponent
  (inspect [this label key-path app-state state]
    [:div.row.gutter
     [:div.grid.four [:p [:b label]]]
     [:div.grid.eight [input-keyword state key-path (str this)]]]))

(defn inspector [state app-state]
  [:div
   [:style default-style]
   [inspect @state "Inspector" [] app-state state]])

(defn mount-inspector [state app-state]
  (reagent/render [inspector state app-state]
                  (.getElementById js/document "praline")))

(defn mk-inspector-state
  "Returns a state atom that is used by the inspector"
  [init]
  (atom init))

(def *test-state* (mk-inspector-state mock-state))
(def *test-app-state* (atom {}))

(mount-inspector *test-state* *test-app-state*)
