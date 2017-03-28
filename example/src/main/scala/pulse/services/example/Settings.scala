package pulse.services.example

import java.io.File

import com.typesafe.config.Config


/**
  * Created by Andrew on 19.03.2017.
  */

trait Settings {

  val threadPoolMaxCount: Int

  val useTaskApi: Boolean

  val statusAvroSchema: File
}

object Settings {
  def apply(config: Config, cliSettings: CliParameters) = new Settings {

    val threadPoolMaxCount: Int = config.getInt("threadpool.maxCount")

    val useTaskApi = cliSettings.useTaskApi

    val statusAvroSchema = cliSettings.statusAvroSchema
  }
}
