scalacOptions ++= Seq( "-unchecked", "-deprecation" )

resolvers += Resolver.typesafeRepo("releases")


resolvers += Resolver.url("jetbrains-bintray", url("http://dl.bintray.com/jetbrains/sbt-plugins/"))(Resolver.ivyStylePatterns)


// The Play plugin
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.6.20")

// SBT-Web plugins
addSbtPlugin("com.typesafe.sbt" % "sbt-web" % "1.4.4")

libraryDependencies += "org.webjars.npm" % "source-map" % "0.7.3"

// Scala.js
addSbtPlugin("org.portable-scala" % "sbt-scalajs-crossproject" % "0.6.0")

addSbtPlugin("org.scala-js" % "sbt-scalajs" % "0.6.26")

addSbtPlugin("com.vmunier" % "sbt-web-scalajs" % "1.0.8-0.6" exclude("org.scala-js", "sbt-scalajs"))

addSbtPlugin("ch.epfl.scala" % "sbt-scalajs-bundler" % "0.14.0")

addSbtPlugin("ch.epfl.scala" % "sbt-web-scalajs-bundler" % "0.14.0")

addSbtPlugin("com.typesafe.sbt" % "sbt-digest" % "1.1.3") // Checksum on sbt-web files

addSbtPlugin("com.typesafe.sbt" % "sbt-gzip" % "1.0.2")

// Dependency Resolution
addSbtPlugin("io.get-coursier" % "sbt-coursier" % "1.0.0")

// App Packaging
addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.9")

// Utilities
addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.9.0") // dependencyGraph

addSbtPlugin("com.orrsella" % "sbt-stats" % "1.0.7") // stats