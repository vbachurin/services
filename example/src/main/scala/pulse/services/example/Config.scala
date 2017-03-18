package pulse.services.example

import scopt.OptionParser

object Config {
  def apply(args: List[String]): Config = {
    val parser = new OptionParser[Config]("common") {
      opt[Boolean]("use-task-api")
        .action((b, c) => c.copy(useTaskApi = b))
        .text("Pass true or false for use-task-api parameter")
    }

    parser.parse(args, Config()) match {
      case Some(config) => config
      case None => throw new InvalidCmdArgsException("Invalid arguments passed")
    }
  }
}

case class Config(useTaskApi: Boolean = false)
