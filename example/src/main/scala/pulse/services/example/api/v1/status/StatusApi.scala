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
  def statusApi(): Endpoint[:+:[Status, :+:[String, CNil]]] = status :+: updateStatus

  def status: Endpoint[Status] =
    get("v1" :: "status") {
      Future(Ok(Status("fine")))
    }
  def updateStatus: Endpoint[String] = post("v1" :: "status" :: stringBody) {
    s: String => {
      // TODO: change your path
      val bytes = AvroUtils.jsonToAvroBytes(s, "/Users/eugene/work/pulse/fgpservices/example/src/main/scala/pulse/services/example/status.avsc")
      val str = new String(bytes, Charset.defaultCharset())
      Future(Ok(""))
    }
  }

}
