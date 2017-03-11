package pulse.services

import cats.Show
import com.typesafe.scalalogging.Logger
import fs2.Task

// TODO: move to pulse.common once it is ready
package object logging extends LoggingDescriptors {

  def info(message: String)(implicit L: Logger): Task[Unit] = Task.delay {
    L.info(message)
  }

  def warn(message: String)(implicit L: Logger): Task[Unit] = Task.delay {
    L.warn(message)
  }

  def debug(message: String)(implicit L: Logger): Task[Unit] = Task.delay {
    L.debug(message)
  }

  def error(message: String)(implicit L: Logger): Task[Unit] = Task.delay {
    L.error(message)
  }

  /**
   * Describes the specified object
   */
  def describe[T ](obj: T)(implicit S: Show[T]): String = S.show(obj)
}
