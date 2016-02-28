(ns praline.styles
  "Provides inline styling of the inspector to avoid having to setup css files")

;; TODO make this configurable
(def default-style
  "
#praline {
  font-family: helvetica, arial, sans-serif;
  z-index: 999999;
  right: 0;
  position: fixed;
  height: 100%;
  overflow: auto;
  width: auto;
  max-width: 300px;
  top: 0;
}

/*Sets the background for the transparency of nested elements in the inspector*/
#praline > :first-child {
  background: #1589FF !important;
  padding-bottom: 0.5em;
  border-left: 1px solid #1e1e1e;
  border-bottom: 1px solid #1e1e1e;
}

#praline p {
  margin: 0;
  padding: 4px;
}

#praline .row {
  display: flex;
}

#praline .row :first-child {
  padding-right: 0.5em;
}

#praline .row .gutter {
  padding: 0em 0 0.5em 0.5em;
}

#praline .row .center {
  align-items: center;
  text-align: center;
}

#praline .row .grid {
  color: #eeeeee;
  background-color: #373737;
  background: rgba(0,0,0,.4);
  word-wrap: break-word;
}

#praline .row .grid.cell{
  flex: 1;
}

#praline .row .grid.cell.center{
  align-self: center;
  flex: 1;
}

#praline .row .grid.four {
  flex: 0 0 33.3333%;
}

#praline .row .grid.six {
  flex: 0 0 50%;
}

#praline .row .grid.three {
  flex: 0 0 25%;
}

#praline .row .grid.four {
  flex: 0 0 33.33333%;
}

#praline .row .grid.eight {
  flex: 0 0 66.66666%;
}

#praline .row .grid.nine {
  flex: 0 0 75%;
}

#praline .row .grid.twelve {
  flex: 0 0 100%;
}

#praline .row .parent {
  font-size: 1.17em;
  font-weight: bold;
  padding: 1em 0 0.5em 0.5em;
  margin: 0;
  text-transform: capitalize;
  cursor: pointer;
}

")
