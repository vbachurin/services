package pulse.services.core

import java.util.concurrent.atomic.AtomicBoolean

import com.twitter.util.{Await, Future}
import com.typesafe.scalalogging.Logger
import pulse.services._

import scala.util.control.NonFatal

/**
  * Created by Andrew on 12.03.2017.
  */

trait StateMachine[Step, State] {

  case class Transition(currentEvent: Step, nextEvent: Step, state: State)

  type TransitionFunction = PartialFunction[Transition, Future[State]]

  def initialState: Step <> State

  val transitionFunc: TransitionFunction

  def transitTo(newStep: Step)(implicit logger: Logger): Future[State] = {
    if (!isInProgress.compareAndSet(false, true)) {
      Future.exception(new Error(s"Transition is in progress from $currentStep"))
    } else {
      purelyTransitTo(currentState)(newStep)
        .onSuccess(newState => {
          logger.debug(s"Transited from $currentStep to $newStep with $newState")
          currentStep = newStep
          currentState = Future.value(newState)
        })
    }.onFailure({ case NonFatal(e) =>
      logger.error(s"An error while transiting from $currentStep to $newStep", e)
    }).respond(_ => isInProgress.set(false))
  }

  def purelyTransitTo(currentState: Future[State])(newStep: Step): Future[State] =
    currentState.flatMap(state =>
      transitionFunc.lift(Transition(currentStep, newStep, state)) match {
        case Some(transitResult) => transitResult
        case None => Future.exception(new Error(s"Transition from $currentStep to $newStep is not supported"))
      }
    )

  private var currentStep = initialState._1

  private var currentState = Future.value(initialState._2)

  private val isInProgress = new AtomicBoolean(false)
}