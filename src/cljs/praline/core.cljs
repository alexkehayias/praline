(ns ^:figwheel-always praline.core
    (:require [clojure.set :refer [difference union]]
              [goog.string :as gs]
              [reagent.core :as reagent :refer [atom]]
              [praline.styles :refer [default-style]]
              [praline.mock :refer [mock-state]]
              [praline.controls :refer [input-number
                                        input-keyword
                                        input-string]]))


(enable-console-print!)

;; TODO track changes in the state under -inspector-changes

(defprotocol InspectComponent
  "Provides methods for returning a Reagent component for inspection"
  (inspect [this label key-path app-state state]
    "Returns a component for inspecting this type"))

(defn handle-show-path [child-paths app-state]
  (fn [e]
    (.preventDefault e)
    (let [child-set (set child-paths)]
      (if (some child-set (:visible @app-state))
        ;; Hide children
        (swap! app-state update :visible difference child-set)
        ;; Show children
        (swap! app-state update :visible union child-set)))))

(extend-type PersistentArrayMap
  InspectComponent
  (inspect [this label key-path app-state state]
    (let [visible (:visible @app-state)
          child-paths (map #(conj key-path %) (keys this))]
      [:div.row.gutter
       [:div.grid.twelve
        [:div.parent {:on-click (handle-show-path child-paths app-state)} label]
        (for [[k v] this
              :when (some #{(conj key-path k)} visible)]
          ^{:key k}
          [inspect v (name k) (conj key-path k) app-state state])]])))

(extend-type PersistentHashMap
  InspectComponent
  (inspect [this label key-path app-state state]
    (let [visible (:visible @app-state)
          child-paths (map #(conj key-path %) (keys this))]
      [:div.row.gutter
       [:div.grid.twelve
        [:div.parent {:on-click (handle-show-path child-paths app-state)} label]
        (for [[k v] this
              :when (some #{(conj key-path k)} visible)]
          ^{:key k}
          [inspect v (name k) (conj key-path k) app-state state])]])))

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

(extend-type function
  InspectComponent
  (inspect [this label key-path app-state state]
    [:div.row.gutter
     [:div.grid.four [:p [:b label]]]
     ;; Stringifying the function can cause hangs, for now just
     ;; indicate that it's a function
     [:div.grid.eight [:code "function"]]]))

(extend-type object
  InspectComponent
  (inspect [this label key-path app-state state]
    [:div.row.gutter
     [:div.grid.four [:p [:b label]]]
     ;; For now str
     [:div.grid.eight [:code "object"]]]))

(extend-type string
  InspectComponent
  (inspect [this label key-path app-state state]
    [:div.row.gutter
     [:div.grid.four [:p [:b label]]]
     [:div.grid.eight [input-string state key-path this]]]))

(extend-type nil
  InspectComponent
  (inspect [this label key-path app-state state]
    [:div.row.gutter
     [:div.grid.four [:p [:b label]]]
     [:div.grid.eight (str this)]]))

(defn inspector [state app-state]
  [:div
   [:style {:dangerouslySetInnerHTML default-style}]
   [inspect @state "@" [] app-state state]])

(defn mount-inspector
  "Mount the inspector to the DOM. Safe to call multiple times"
  [state app-state]
  (when-let [el (.getElementById js/document "praline")]
    (.remove el))
  (let [el (.createElement js/document "div")]
    (aset el "id" "praline")
    (.appendChild (.-body js/document) el)
    (reagent/render [inspector state app-state] el)))

(defn inspector-state
  "Returns a state atom that is used by the inspector"
  [init]
  (atom init))

(defn inspector-app-state
  []
  (atom {:visible #{}}))
