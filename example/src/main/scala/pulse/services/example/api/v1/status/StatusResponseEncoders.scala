package pulse.services.example.api.v1.status

import io.circe.syntax._
import io.finch.Encode

/**
  * Created by Andrew on 16.03.2017.
  */
trait StatusResponseEncoders {
  implicit val statusEncoder = io.circe.Encoder.instance[Status] { s =>
    Map("status" -> Map("description" -> s.description)).asJson
  }
//
//  implicit val statusEncoder2 = Encode[Status, ]//(x => ) .instance[Status] { s =>
//    Map("status" -> Map("description" -> s.description)).asJson
//  }



//  implicit val statusDecoder = new Decoder[Status] {
//    final def apply(c: HCursor): Decoder.Result[Status] = ???
//  }
}
