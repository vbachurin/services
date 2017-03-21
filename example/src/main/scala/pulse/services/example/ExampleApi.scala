package pulse.services.example

import com.twitter.finagle.Service
import com.twitter.finagle.http.{Request, Response}
import io.finch.circe._
import pulse.services.example.api.v1.ResponseEncoders
import pulse.services.example.api.v1.status.{StatusApi, StatusTaskApi}

/**
  * Created by Andrew on 16.03.2017.
  */
case class ExampleApi(useTaskApi: Boolean) extends ResponseEncoders {
  private def api = if (useTaskApi) StatusTaskApi.statusApi() else StatusApi.statusApi()

  def apiService: Service[Request, Response] = api.toService
}
