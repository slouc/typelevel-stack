package slouc.myproject.server

import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._
import slouc.myproject.persistence.DatabaseIO._

object Main extends IOApp {
  def run(args: List[String]) =
    Server.stream[IO].compile.drain.as(ExitCode.Success)

}
