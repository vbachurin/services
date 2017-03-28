package pulse.services
package core

import com.twitter.util._
import com.twitter.util.Future._
import com.twitter.finagle.{ListeningServer, NullServer}
import com.twitter.server.TwitterServer
import com.typesafe.scalalogging.Logger

trait ServerApp extends TwitterServer {
  override def allowUndefinedFlags: Boolean = true

  private implicit val logger = Logger[this.type]

  private val serverState = new StateMachine[LifeCycle, ListeningServer] {
    def initialState = (Init, NullServer)

    val transitionFunc: TransitionFunction = {
      case Transition(Init, Started, _) =>
        sys.addShutdownHook {
          stopServer()
        }
        value(server(args.toList))
      case Transition(Started, Stopped, srv) =>
        srv.close().map(_ => srv)
    }
  }

  def startServer(): ListeningServer = {
    val starting = serverState.transitTo(Started).onSuccess(s => logger.info(s"Server is started on ${s.boundAddress}"))
    Await.result(starting)
  }

  def stopServer(): Unit = {
    serverState.transitTo(Stopped).onSuccess(_ => logger.info("Server is stopped"))
  }

  def server(args: List[String]): ListeningServer

  def main(): Unit = {
    Await.ready(startServer())
  }

  init {
  }

  postmain {
  }

  onExit {
  }

  premain {
  }

  private sealed trait LifeCycle

  private case object Init extends LifeCycle

  private case object Started extends LifeCycle

  private case object Stopped extends LifeCycle

}
