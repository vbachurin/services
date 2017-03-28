package pulse.services.core

import cats.{~>, _}
import com.twitter.util.{Future, Promise, Return, Throw}
import fs2.{Strategy, Task}


trait Component {

  private val _taskTransform = new (Task ~> Future) {
    override def apply[A](fa: Task[A]): Future[A] = {
      val promise = new Promise[A]()

      fa.unsafeRunAsync {
        case Right(content) => promise.setValue(content)
        case Left (failure) => promise.setException(failure)
      }

      promise
    }
  }

  protected implicit def _transformTask[A](fa: Task[A]): Future[A] = _taskTransform(fa)
}
