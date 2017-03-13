package pulse.services.core

import com.twitter.util._
import pulse.services.logging._
import FutureMonad._
import com.twitter.finagle.ListeningServer
import com.twitter.server.TwitterServer

// TODO: use logging instead of stdout
// TODO: add error handling of futures. Maybe it's better to convert them to tasks
trait ServerApp extends TwitterServer {

  val server: ListeningServer

  def main(): Unit = {
    getOrLogError(stateMachine.transitTo(Starting)) { startingState =>
      Await.ready(startingState)
      sys.addShutdownHook(onExitRequested)
      Await.ready(server)
    }
  }

  def onExitRequested(): Unit = {
    getOrLogError(stateMachine.transitTo(Stopping)) { closingState =>
      Await.ready(closingState)
    }
  }

  private val stateMachine = new StateMachine[LifeCycle, Future] {
    val transitions: Map[(LifeCycle, LifeCycle), LifeCycle => Future[LifeCycle]] =
      Map(((Init, Starting) -> startServer),
        ((Started, Stopping) -> stopServer))

    val initialState = Init
  }

  private def startServer(state: LifeCycle): Future[LifeCycle] = {
    Future.apply(Started)
  }

  private def stopServer(state: LifeCycle): Future[LifeCycle] = {
    server.close().map(x => Stopped)
  }

  private def getOrLogError(transitioned: Either[String, Future[LifeCycle]])(continuation: Future[LifeCycle] => Unit): Unit = {
    transitioned match {
      case Right(state) =>
        continuation(state)
      case Left(failure) => println(failure)
    }
  }

  private sealed trait LifeCycle

  private case object Init extends LifeCycle

  private case object Starting extends LifeCycle

  private case object Started extends LifeCycle

  private case object Stopping extends LifeCycle

  private case object Stopped extends LifeCycle

  init {
  }

  postmain {
  }

  onExit {
    onExitRequested()
  }

  premain {
  }
}
