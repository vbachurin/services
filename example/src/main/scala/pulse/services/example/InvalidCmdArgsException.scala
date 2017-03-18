package pulse.services.example

case class InvalidCmdArgsException(message: String, cause: Exception = null) extends RuntimeException (message, cause)
