package pulse.services
package example

import com.twitter.finagle.{Http, ListeningServer, Service}
import com.twitter.finagle.http.{Request, Response}
import com.twitter.util.Future
import core.ServerApp

object Main extends ServerApp {

  //def server (args: Array[String]): Process[Task, Unit] = ???
  val server: ListeningServer = {
    val service: Service[Request, Response] = new Service[Request, Response] {
      def apply(req: Request): Future[Response] = Future.value(Response())
    }

    Http.server.serve(":8080", service)
  }
}
