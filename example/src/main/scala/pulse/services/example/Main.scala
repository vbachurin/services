package pulse.services
package example

import com.twitter.finagle.{Http, ListeningServer}
import core.ServerApp
import com.typesafe.config.ConfigFactory

object Main extends ServerApp {

  def server(args: List[String]): ListeningServer = {
    val settings = Settings(ConfigFactory.load(), CliParameters(args))
    Http.server.serve(":8080", ExampleApi(settings).apiService)
  }
}
