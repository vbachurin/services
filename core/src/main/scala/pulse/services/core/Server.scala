package pulse.services.core

import com.twitter.server.TwitterServer
import fs2.Task

abstract class Server extends TwitterServer {

  def main(): Unit = server(args)

  def server(args: Array[String]): fs2.Stream[Task, Unit]

}
