package pulse.services
package example

import com.twitter.finagle.{Http, ListeningServer}
import core.ServerApp

object Main extends ServerApp {
  def server(args: List[String]): ListeningServer = {
    val config = Config(args)
    Http.server.serve(":8080", ExampleApi(config.useTaskApi).apiService)
  }
}
