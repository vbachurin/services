package pulse.services.example.api.v1.status

import java.net.InetAddress

import com.twitter.io.Buf
import io.circe.syntax._
import io.finch.{Application, Encode}

/**
  * Created by Andrew on 16.03.2017.
  */
trait StatusResponseEncoders {
  implicit val enc = io.finch.Encode.instance[Status, Application.Json] { case (status, charset) =>
    Buf.Utf8(Map("status" -> Map("description" -> status.description)).asJson.toString)
  }

  implicit val enc2 = io.finch.Encode.instance[InetAddress, Application.Json] { case (ad, charset) =>
    Buf.Utf8(ad.toString)
  }
}
