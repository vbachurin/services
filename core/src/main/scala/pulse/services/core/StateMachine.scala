package pulse.services.core

import java.util.concurrent.atomic.AtomicReference

import cats.Monad

/**
  * Created by Andrew on 12.03.2017.
  */
trait StateMachine[State, M[_]] {
  val transitions: Map[(State, State), State => M[State]]

  val initialState: State

  private lazy val state = new AtomicReference[State](initialState)

  def transitTo(newState: State)(implicit monad: Monad[M]): Either[String, M[State]] = {
    val currentState = state.get()
    transitions.get((currentState, newState)) match {
      case Some(transition) if state.compareAndSet(currentState, newState) =>
        val updatedState: M[State] = monad.flatMap(monad.pure(newState))(transition)
        Right(monad.flatMap(updatedState)(x => {
          state.set(x)
          updatedState
        }))
      case None => Left(s"There is no transition from $currentState to $newState")
      case _ => transitTo(newState)
    }
  }
}