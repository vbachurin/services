package pulse.services
package core

import com.twitter.util._
import FutureMonad._
import com.twitter.finagle.ListeningServer
import com.twitter.server.TwitterServer
import com.typesafe.scalalogging.Logger

trait ServerApp extends TwitterServer {

  private type State = LifeCycle

  override def allowUndefinedFlags: Boolean = true

  private implicit val logger = Logger[this.type]

  private var serverInstance: ListeningServer = _

  private val stateMachine = new StateMachine[State, Future] {
    val transitions: Map[(LifeCycle, LifeCycle), LifeCycle => Future[LifeCycle]] =
      Map((Init, Starting) -> startServerInternal,
        (Started, Stopping) -> stopServerInternal
      )

    val initialState = Init
  }

  def server(args: List[String]): ListeningServer

  def main(): Unit = {
    getOrLogError(stateMachine.transitTo(Starting)) { startingState =>

      Await.ready(startingState)
      serverInstance = server(args.toList)
      Await.ready(serverInstance)
    }
  }

  def onExitRequested(): Unit = {
    getOrLogError(stateMachine.transitTo(Stopping)) { closingState =>
      Await.ready(closingState)
    }
  }

  private def startServerInternal(state: LifeCycle): Future[State] = {
    Future.apply(Started)
  }

  private def stopServerInternal(state: LifeCycle): Future[State] = {
    serverInstance.close().map(_ => Stopped)
  }

  private def getOrLogError(transitioned: Either[String, Future[State]])(continuation: Future[State] => Unit): Unit = {
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
