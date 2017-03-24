package pulse.services.example.api.v1.status

import java.nio.charset.Charset

import com.twitter.util.Future
import io.finch._
import io.finch.circe._
import io.circe.generic.auto._
import pulse.services.core.Component
import fs2.Task
import pulse.services.example.avro.AvroUtils

/**
  * Created by Andrew on 16.03.2017.
  */
object StatusTaskApi extends Component {
  def statusApi() = status :+: updateStatus

  def status: Endpoint[Status] =
    get("v1" :: "status") {
      _transformTask(Task.now(Ok(Status("fine"))))
    }

  def updateStatus: Endpoint[String] = post("v1" :: "status" :: stringBody) {
    s: String => {
      val bytes = AvroUtils.jsonToAvroBytes(s, "/Users/eugene/work/pulse/fgpservices/example/src/main/scala/pulse/services/example/status.avsc")
      bytes match {
        case Right(b) => _transformTask(Task.now(Ok(new String(b, Charset.defaultCharset())))
        case Left(ex) => {
          print(s"Error during json to avro serialization: ${ex.getMessage}")
          _transformTask(Task.now(InternalServerError(new Exception(ex.getMessage)))
        }
      }
    }
  }
}
