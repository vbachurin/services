package pulse.services

import cats.MonadError
import com.twitter.util.{Awaitable, Future}

/**
  * Created by Andrew on 19.03.2017.
  */
package object core {
  implicit val future = new MonadError[Future, Throwable] {
    def pure[A](x: A): Future[A] = Future.value[A](x)

    def flatMap[A, B](fa: Future[A])(f: (A) => Future[B]): Future[B] = fa.flatMap(f)

    def tailRecM[A, B](a: A)(f: (A) => Future[Either[A, B]]): Future[B] = ???

    def raiseError[A](e: Throwable): Future[A] = Future.exception(e)

    def handleErrorWith[A](fa: Future[A])(f: (Throwable) => Future[A]): Future[A] = ???
  }
}
