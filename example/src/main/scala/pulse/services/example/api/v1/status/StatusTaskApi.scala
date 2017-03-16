package pulse.services.example.api.v1.status

import io.finch._
import pulse.services.core.Component
import fs2.Task

/**
  * Created by Andrew on 16.03.2017.
  */
object StatusTaskApi extends Component {
  def statusApi() = status

  def status: Endpoint[Status] =
    get("v1" :: "status") {
      _transformTask(Task.now(Ok(Status("fine"))))
    }
}
