package pulse.services.example

import com.typesafe.config.Config


/**
  * Created by Andrew on 19.03.2017.
  */

trait Settings {

  val threadPoolMaxCount: Int

  val useTaskApi: Boolean
}

object Settings {
  def apply(config: Config, cliSettings: CliParameters) = new Settings {

    val threadPoolMaxCount: Int = config.getInt("threadpool.maxCount")

    val useTaskApi = cliSettings.useTaskApi
  }
}
