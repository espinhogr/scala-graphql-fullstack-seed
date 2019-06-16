# README #

Seed project for Play framework + ScalaJS using React, Apollo client and Sangria.
Under the hood it uses:
- GraphQL
- Apollo Client
- Play framework
- Sangria
- Silhouette (Authentication)
- Slinky (React for Scala.js)
- Antd (Components for React)
- Slick with codegen and play-evolutions


## Development Guide

### Prerequisites

1. Docker installed on the dev box.
   See: [https://github.com/frgomes/bash-scripts/blob/master/sysadmin-install/install-docker-ce.sh](https://github.com/frgomes/bash-scripts/blob/master/sysadmin-install/install-docker-ce.sh) 

2. Node installed on the dev box.
  See: [http://github.com/frgomes/bash-scripts/blob/master/user-install/install-node.sh](http://github.com/frgomes/bash-scripts/blob/master/user-install/install-node.sh) 

3. SBT installed on the dev box
  See: [https://github.com/frgomes/bash-scripts/blob/master/user-install/install-scala.sh](https://github.com/frgomes/bash-scripts/blob/master/user-install/install-scala.sh) 

4. Follow the instructions in the file `project/WebClient.scala`.

5. If an error at build time appears saying that module `style-loader` cannot be found, run `npm install -g style-loader css-loader`

### IDE support
Follow the setup [here](https://slinky.shadaj.me/docs/installation/) for IntelliJ support.

### Build instructions

* Make sure you have already built and published locally ``apollo-scalajs`` according to instructions at `project/WebClient.scala`.

* Start a MySQL container via Docker Compose.
```bash
$ docker-compose up -d
```

**Note**: Only MySQL is supported at the moment. In order to support other databases, we should have multiple sets of DDLs and evolutions per database vendor.

* Run test cases:
```bash
$ sbt clean test
```

* Runs the server and live reloads changes:
```
$ sbt run
```

* Generates a fat jar:
```
$ sbt assembly
```

## Code generation

Generates schema.graphql and query/mutation objects for the client.
You shouldn't need to use this explicitly.
```
$ sbt web_client/Compile/managedSources
```

## Usage

The default login for the user interface is:
- username: admin
- password: admin

It is created via evolutions, remove it from there if you want to change it. Also if you want to create another one manually there is an app called _ManualUserGenerator_ to do that.

## Problems

- WebpackDevServer is not used at the moment
- When a new dependency is added to the npmDependencies the bundle.js is not rebuilt.
- The build is slow because some files are not cached (static query generation and two rounds of js bundling) and the dist directory is deleted.
- Add test framework for front-end
- Refetching the queries instead of changing the apollo cache.

## Credits

https://github.com/shadaj/create-react-scala-app.g8/tree/master/src/main/g8

https://github.com/boosh/play-scalajs-seed/blob/master/build.sbt

https://github.com/KyleU/boilerplay
