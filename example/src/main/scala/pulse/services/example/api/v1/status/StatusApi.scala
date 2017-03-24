package pulse.services.example.api.v1.status

import java.nio.charset.Charset

import com.twitter.finagle.Http
import com.twitter.util.{Await, Future}
import io.finch._
import io.finch.circe._
import io.circe.generic.auto._
import pulse.services.example.avro.AvroUtils
import pulse.services.example.avro.AvroUtils.jsonToAvroBytes
import shapeless.{:+:, CNil}
import java.lang._
/**
  * Created by Andrew on 16.03.2017.
  */
object StatusApi {
  def statusApi() = status :+: updateStatus

  def status: Endpoint[Status] =
    get("v1" :: "status") {
      Future(Ok(Status("fine")))
    }

  def updateStatus: Endpoint[String] = post("v1" :: "status" :: stringBody) {
    s: String => {
      // TODO: change your path
      val bytes = AvroUtils.jsonToAvroBytes(s, "/Users/eugene/work/pulse/fgpservices/example/src/main/scala/pulse/services/example/status.avsc")
      bytes match {
        case Right(b) => Future(Ok(new String(b, Charset.defaultCharset())))
        case Left(ex) => {
          print(s"Error during json to avro serialization: ${ex.getMessage}")
          Future(InternalServerError(new Exception(ex.getMessage)))
        }
      }
    }
  }

}
