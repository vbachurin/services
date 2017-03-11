package pulse.services.logging

import cats.Show

/**
 * Provides standard logging descriptors
 */
trait LoggingDescriptors {
  import scala.compat.Platform.EOL

  implicit val throwableShow = new Show[Throwable] {
    override def show(f: Throwable) = s"exception { message=${f.getMessage}, stacktrace=${f.getStackTrace.mkString("", EOL, EOL)}"
  }

}
