package pulse.services.core

import cats.Monad
import com.twitter.util.Future

/**
  * Created by Andrew on 13.03.2017.
  */
object FutureMonad {

  implicit val future = new Monad[Future] {
    override def pure[A](x: A): Future[A] = Future.value[A](x)

    override def flatMap[A, B](fa: Future[A])(f: (A) => Future[B]): Future[B] = fa.flatMap(f)

    override def tailRecM[A, B](a: A)(f: (A) => Future[Either[A, B]]): Future[B] = ???
  }

}
