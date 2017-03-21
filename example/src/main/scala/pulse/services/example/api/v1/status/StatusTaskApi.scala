package pulse.services.example.api.v1.status

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
      AvroUtils.jsonToAvroBytes(s, "status.avro")
      _transformTask(Task.now(Ok("")))
    }
  }
}
