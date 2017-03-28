package pulse.services.example

import java.io.File

import scopt.OptionParser

object CliParameters {
  def apply(args: List[String]): CliParameters = {
    val parser = new OptionParser[CliParameters]("common") {
      opt[Boolean]("use-task-api")
        .action((b, c) => c.copy(useTaskApi = b))
        .text("Pass true or false for use-task-api parameter")

      opt[File]("status-avro-schema")
        .action((f, c) => c.copy(statusAvroSchema = f))
        .text("Pass valid file path to status.avsc")
    }

    parser.parse(args, CliParameters()) match {
      case Some(config) => config
      case None => throw new InvalidCmdArgsException("Invalid arguments passed")
    }
  }
}

case class CliParameters(statusAvroSchema: File = new File("."), useTaskApi: Boolean = false)
