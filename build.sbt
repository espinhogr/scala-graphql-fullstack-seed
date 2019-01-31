
resolvers += Resolver.sonatypeRepo("releases")

lazy val schema = Schema.schema

lazy val shared = Shared.shared

lazy val web_client = WebClient.web_client

lazy val server = Server.server

asciiGraphWidth := 999

// Sets the sbt console (or shell) in the "server" project so that commands like "run" work.
Global / onLoad := (Global / onLoad).value andThen { "project server" :: _ }
