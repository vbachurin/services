package pulse.services.core

import com.twitter.util._
import FutureMonad._
import com.twitter.finagle.ListeningServer
import com.twitter.server.TwitterServer
import com.typesafe.scalalogging.Logger

trait ServerApp extends TwitterServer {

  implicit val logger = Logger(classOf[ServerApp])

  val server: ListeningServer

  def main(): Unit = {
    getOrLogError(stateMachine.transitTo(Starting)) { startingState =>
      Await.ready(startingState)
      // this handler is called from onExit
      //sys.addShutdownHook(onExitRequested)
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
      Map(((Init, Starting) -> startServerInternal),
        ((Started, Stopping) -> stopServerInternal))

    val initialState = Init
  }

  private def startServerInternal(state: LifeCycle): Future[LifeCycle] = {
    Future.apply(Started)
  }

  private def stopServerInternal(state: LifeCycle): Future[LifeCycle] = {
    server.close().map(x => Stopped)
  }

  private def getOrLogError(transitioned: Either[String, Future[LifeCycle]])(continuation: Future[LifeCycle] => Unit): Unit = {
    transitioned match {
      case Right(state) =>
        continuation(state)
      case Left(failure) => logger.error(failure)
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
