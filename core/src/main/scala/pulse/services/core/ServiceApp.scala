package pulse.services
package core

trait ServiceApp {

  final def main(args: Array[String]): Unit = run(args.toList)

  private def run(args: List[String]): Unit = ???

}
