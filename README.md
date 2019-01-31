# README #

Seed project for Play framework + ScalaJS using Apollo client and Sangria.


## Development Guide

### Prerequisites

- Node installed on the dev box
- SBT installed on the dev box

## Problems

- WebpackDevServer is not used at the moment
- Some scalajs is not picked up by Intellij as valid (i.e. the components defined with macros).
- When a new dependency is added to the npmDependencies the bundle.js is not rebuilt.
- When a graphql query is changed if the browser is refreshed the new query does not get picked up
- The build is slow because some files are not cached

## Credits

https://github.com/shadaj/create-react-scala-app.g8/tree/master/src/main/g8

https://github.com/boosh/play-scalajs-seed/blob/master/build.sbt

https://github.com/KyleU/boilerplay
