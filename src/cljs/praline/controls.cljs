(ns praline.controls
  (:require [clojure.string :as st]
            [reagent.core :as reagent :refer [atom]]))


(defn change-state! [state key-path parse-f]
  (fn [e]
    (.preventDefault e)
    (swap! state assoc-in key-path (parse-f e))
    nil))

(defn input-number [state key-path n]
  [:input {:type "number"
           :on-change (change-state! state key-path #(-> % .-target .-value int))
           :value n
           :style {:width "80%"}}])

(defn input-keyword [state key-path kw]
  [:input {:type "text"
           :on-change (change-state! state key-path
                                     #(-> %
                                          .-target
                                          .-value
                                          (st/replace  ":" "")
                                          keyword))
           :value kw
           :style {:width "80%"}}])

(defn input-string [state key-path kw]
  [:input {:type "text"
           :on-change (change-state! state key-path #(-> % .-target .-value))
           :value kw
           :style {:width "80%"}}])
