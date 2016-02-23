(ns praline.controls
  (:require [clojure.string :as st]
            [reagent.core :as reagent :refer [atom]]))


(defn input-number [state key-path n]
  [:input {:type "number"
           :on-change (fn [e]
                        (.preventDefault e)
                        (swap! state assoc-in key-path (int (-> e .-target .-value)))
                        nil)
           :value n
           :style {:width "80%"}}])

(defn input-keyword [state key-path kw]
  [:input {:type "text"
           :on-change (fn [e]
                        (.preventDefault e)
                        (swap! state assoc-in key-path update
                               (-> (-> e .-target .-value)
                                   (st/replace  ":" "")
                                   keyword))
                        nil)
           :value kw
           :style {:width "80%"}}])

(defn input-string [state key-path kw]
  [:input {:type "text"
           :on-change (fn [e]
                        (.preventDefault e)
                        (swap! state assoc-in key-path (-> e .-target .-value))
                        nil)
           :value kw
           :style {:width "80%"}}])
