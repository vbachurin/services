package pulse.services.example

import java.io.Closeable

package object extensions {

    def bracket[A <: Closeable, B](obj: A)(body: A => B) = {
      use(obj)(o => o.close())(body)
    }

    def use[A, B](obj: => A)(close: A => Unit)(body: A => B): Either[Throwable, B] = {
      try {
        Right(body(obj))
      }
      catch {
        case ex: Throwable => Left(ex)
      }
      finally {
        close(obj)
      }
    }

}
