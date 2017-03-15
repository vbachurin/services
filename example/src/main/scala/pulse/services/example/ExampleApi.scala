package pulse.services.example

import com.twitter.finagle.Service
import com.twitter.finagle.http.{Request, Response}
import pulse.services.example.api.v1.ResponseEncoders
import pulse.services.example.api.v1.status.StatusApi._

/**
  * Created by Andrew on 16.03.2017.
  */
object ExampleApi extends ResponseEncoders {
  private def api = statusApi()

  def apiService: Service[Request, Response] = api.toService
}
