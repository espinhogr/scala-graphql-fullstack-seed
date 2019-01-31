import play.api._
import play.core.server.Server
import play.core.server.ProdServerStart
import play.core.server.RealServerProcess
import play.core.server.ServerConfig
import play.core.server.ServerProvider

object Bootstrap {
  def main(args: Array[String]): Unit = if (args.isEmpty) {
    startServer(new RealServerProcess(args))
  } else {
    throw new IllegalStateException("Unable to parse arguments.")
  }

  def startServer(process: RealServerProcess): Server = {
    val config: ServerConfig = ProdServerStart.readServerConfigSettings(process)
    val application: Application = {
      val environment = Environment(config.rootDir, process.classLoader, Mode.Prod)
      val context = ApplicationLoader.createContext(environment)
      val loader = ApplicationLoader(context)
      loader.load(context)
    }
    Play.start(application)

    val serverProvider: ServerProvider = ServerProvider.fromConfiguration(process.classLoader, config.configuration)
    val server = serverProvider.createServer(config, application)
    process.addShutdownHook(server.stop())
    server
  }
}
