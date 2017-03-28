package pulse.services.example

import java.io.File

import com.twitter.finagle.Service
import com.twitter.finagle.http.{Request, Response}
import io.finch.circe._
import pulse.services.example.api.v1.ResponseEncoders
import pulse.services.example.api.v1.status.{StatusApi, StatusTaskApi}

/**
  * Created by Andrew on 16.03.2017.
  */
case class ExampleApi(settings: Settings) extends ResponseEncoders {
  private def api = {
    if (settings.useTaskApi)
      StatusTaskApi.statusApi(settings)
    else
      StatusApi.statusApi(settings)
  }

  def apiService: Service[Request, Response] = api.toService
}
