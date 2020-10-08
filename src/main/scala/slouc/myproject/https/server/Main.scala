package slouc.myproject.https.server

import java.util.concurrent.Executors

import cats.effect.{ExitCode, IO, IOApp}
import org.http4s.implicits._
import org.http4s.server.blaze.BlazeServerBuilder
import slouc.myproject.persistence.{DatabaseIO, UserRepoImpl}
import slouc.myproject.service.UserService

import scala.concurrent.ExecutionContext

object Main extends IOApp {

  def run(args: List[String]): IO[ExitCode] = {

    val ec = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(1))

    val dbTransactor = DatabaseIO.transactor
    val userRepo     = UserRepoImpl
    val userService  = new UserService(userRepo, dbTransactor)
    val httpApp      = Routes.userRoutes(userService).orNotFound

    BlazeServerBuilder[IO](ec)
      .bindHttp(8080, "0.0.0.0")
      .withHttpApp(httpApp)
      .resource
      .use(_ => IO.never)
      .as(ExitCode.Success)
  }

}
