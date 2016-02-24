# Praline

Provides a reagent component to efficiently inspect and edit state in real time with automatic UI generation. Any data type can be inspected, including nested data, and can be extended to change how you it to be represented. Includes sane defaults common built-in Clojure types.

Example of inspecting a running game using the [Chocolatier game engine](http://github.com/alexkehayias/chocolatier "Chocolatier ClojureScript game engine"):

![Alt text](/../screenshots/praline_demo.gif?raw=true "Praline ClojureScript state inspector")

## Usage

```clojure
(ns user.test
  (:require [praline.core :refer [mount-inspector
                                  inspector-state
                                  inspector-app-state]]))

;; Instantiate the state for inspection
(def my-state (inspector-state {:a {:x 1 :y 2} :b 3}))

;; Mount the component to the DOM
(mount-inspector my-state (inspector-app-state))
```

## Extending the UI

Data types can be rendered in the UI by implementing the `praline.core/InspectComponent` protocol by way of `extend-type`. This makes it trivial to add new UI elements for viewing/editing the inspected state.

### Example

Here's an example of extending the inspector to handle keyword values automatically:

```clojure
(require '[praline.core :refer [InspectComponent]])

(defn input-keyword [state key-path kw]
  [:input {:type "text"
           ;; When the user changes the text field update the state
           ;; atom with the current value
           :on-change (fn [e]
                        (.preventDefault e)
                        (swap! state assoc-in key-path
                                              (-> e
                                                .-target
                                                .-value
                                                (st/replace  ":" "")
                                                keyword)))
           :value (name kw)
           :style {:width "80%"}}])

(extend-type Keyword
  InspectComponent
  (inspect [this label key-path app-state state]
    ;; The inspector uses a css to create a 12 column grid using flexbox
    [:div.row.gutter
     ;; The outer call passes in a label for the field
     [:div.grid.four [:p [:b label]]]
     ;; Show a text input field with the name of the keyword
     [:div.grid.eight [input-keyword state key-path this]]]))

```

### CSS

The inspector uses a simple inline stylesheet to layout the data in a nested grid using flexbox. The following css classes are within the scope of the inspector element, `#praline`, to handle layouts.

- `.row` designates a block and is a container for the `.grid` class
- `.grid` is used with a semantic number to designate how many columns wide it will be i.e `.grid.three` will be three columns wide, `.grid.twelve` would be the full width of the row
- `row.gutter` adds padding between the nested rows to give a visual hierarchy

## Running the Demo

```
lein figwheel
```

Navigate to http://127.0.0.1:1222/index.html to see the demo of mocked up data. When you change any of editable fields it will result in the state being changed.

## License

Copyright © 2016 Alex Kehayias

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
