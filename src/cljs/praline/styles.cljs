(ns praline.styles
  "Provides inline styling of the inspector to avoid having to setup css files")


(def default-style
  "
#praline {
  font-family: helvetica, arial, sans-serif;
  z-index: 999999;
  right: 0;
  position: absolute;
  height: 100%;
  overflow: auto;
  width: 300px;
}

#praline p {
  margin: 0;
  padding: 4px;
}

#praline h2 {
  color: black;
  font-size: 21px;
  margin: 0.5em 0.2em;
}

#praline .row {
  display: flex;
}

#praline .row .gutter {
  padding: 0em 0 0.5em 0.5em;
}

#praline .row.center {
  align-items: center;
  text-align: center;
}

#praline .row .grid {
  background: rgba(0,0,0,.1);
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

#praline .row h3 {
  padding: 1em 0 0.5em 0.5em;
  margin: 0;
  text-transform: capitalize;
}

")
