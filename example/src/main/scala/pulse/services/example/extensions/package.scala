package pulse.services.example

import scala.util.{Failure, Success, Try}

package object extensions {

  def use[A,B](obj: A)(body: A => B)(implicit M: Managed[A]): Try[B] = Try(body(obj)) match {
    case x: Success[B] => M.close(obj); x
    case x: Failure[B] => M.close(obj); x
  }

}
