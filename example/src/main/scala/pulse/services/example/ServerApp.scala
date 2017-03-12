package pulse.services.example

trait ServerApp {

  final def main(args: Array[String]): Unit = run(args.toList)

  private def run(args: List[String]): Unit = ???
}
