package pulse.services
package example

import core.Server
import fs2.Task

import fs2.{Stream => Process}

object Main extends Server {

  def server (args: Array[String]): Process[Task, Unit] = ???
}
