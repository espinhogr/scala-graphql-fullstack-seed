# README #

Seed project for Play framework + ScalaJS using Apollo client and Sangria.


## Development Guide

### Prerequisites

- Node installed on the dev box
- SBT installed on the dev box

## Commands

- run: runs the server and live reloads the changes
- web_client/Compile/managedSources: generates schema.graphql and query/mutation objects for the client
- assembly: generates the uber jar 

## Problems

- WebpackDevServer is not used at the moment
- Some scalajs is not picked up by Intellij as valid (i.e. the components defined with macros).
- When a new dependency is added to the npmDependencies the bundle.js is not rebuilt.
- The build is slow because some files are not cached (static query generation and two rounds of js bundling) and the dist directory is deleted.
- Add test framework
- Refetching the queries instead of changing the apollo cache.

## Credits

https://github.com/shadaj/create-react-scala-app.g8/tree/master/src/main/g8

https://github.com/boosh/play-scalajs-seed/blob/master/build.sbt

https://github.com/KyleU/boilerplay
