package pulse.services.example.api.v1

import pulse.services.core.error.ErrorResponseEncoders
import pulse.services.example.api.v1.status.StatusResponseEncoders

/**
  * Created by Andrew on 16.03.2017.
  */
trait ResponseEncoders extends ErrorResponseEncoders with StatusResponseEncoders {
}
