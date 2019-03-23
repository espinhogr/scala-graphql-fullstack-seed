# README #

Seed project for Play framework + ScalaJS using Apollo client and Sangria.


## Development Guide

### Prerequisites

- Node installed on the dev box
- SBT installed on the dev box
- Follow the instructions in the file `project/WebClient.scala`

## Setup
Follow the setup [here](https://slinky.shadaj.me/docs/installation/) for IntelliJ support.

## Commands

- run: runs the server and live reloads the changes
- web_client/Compile/managedSources: generates schema.graphql and query/mutation objects for the client
- assembly: generates the uber jar 

## Problems

- WebpackDevServer is not used at the moment
- When a new dependency is added to the npmDependencies the bundle.js is not rebuilt.
- The build is slow because some files are not cached (static query generation and two rounds of js bundling) and the dist directory is deleted.
- Add test framework
- Refetching the queries instead of changing the apollo cache.

## Credits

https://github.com/shadaj/create-react-scala-app.g8/tree/master/src/main/g8

https://github.com/boosh/play-scalajs-seed/blob/master/build.sbt

https://github.com/KyleU/boilerplay
