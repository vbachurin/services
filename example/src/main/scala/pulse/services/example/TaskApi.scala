package pulse.services.example

import fs2.Strategy
import pulse.services.core.Component


/**
  * Created by Andrew on 19.03.2017.
  */
trait TaskApi extends Component {
  val settings: Settings

  implicit val strategy = Strategy.fromFixedDaemonPool(settings.threadPoolMaxCount)
}
