package pulse.services.example

import java.io.File

import scopt.OptionParser

object Config {
  def apply(args: List[String]): Config = {
    val parser = new OptionParser[Config]("common") {
      opt[Boolean]("use-task-api")
        .action((b, c) => c.copy(useTaskApi = b))
        .text("Pass true or false for use-task-api parameter")

      opt[File]("status-avro-schema")
        .action((f, c) => c.copy(statusAvroSchema = f))
        .text("Pass valid file path to status.avsc")
    }

    parser.parse(args, Config()) match {
      case Some(config) => config
      case None => throw new InvalidCmdArgsException("Invalid arguments passed")
    }
  }
}

case class Config(statusAvroSchema: File = new File("."), useTaskApi: Boolean = false)
