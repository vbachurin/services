package pulse.services
package example

import com.twitter.util.Future
import io.finch._
import core._
import fs2.Task

case class Application(cont: Container) extends Component {

  val status: Endpoint[String] = get("status") {
    Ok("fine")
  }

}
