package pulse.services.example.api.v1.status

import io.finch.{Endpoint, _}

/**
  * Created by Andrew on 16.03.2017.
  */
object StatusApi {
  def statusApi() = status

  def status: Endpoint[Status] =
    get("v1" :: "status") {
      Ok(Status("fine"))
    }
}
