package pulse.services.core.error

import io.circe.Encoder

/**
  * Created by Andrew on 16.03.2017.
  */
trait ErrorResponseEncoders {

  implicit val exceptionEncoder = Encoder.instance[Throwable] { e =>
    ???
  }

}
