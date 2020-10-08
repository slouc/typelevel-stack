package slouc.myproject.zio.server

import org.http4s.HttpApp
import org.http4s.implicits._
import org.http4s.server.blaze._
import zio.ExitCode
import zio.URIO
import zio.ZEnv
import zio.ZIO
import zio.interop.catz._

object Main extends zio.App{

  override def run(args: List[String]): URIO[ZEnv, ExitCode] = {

    val httpApp: HttpApp[AppTaskThrow] =
      http4sKleisliResponseSyntaxOptionT(Routes.userRoutes()).orNotFound

    def server(): AppTaskThrow[Unit] =
      ZIO.runtime[AppEnvironment].flatMap { implicit runtime =>
        val ec = runtime.platform.executor.asEC

        val httpServer = BlazeServerBuilder[AppTaskThrow](ec).withoutBanner
          .bindHttp(8080, "0.0.0.0")
          .withHttpApp(httpApp)
          .resource
          .toManaged

        httpServer.useForever
      }

    server().toManaged_.useNow.exitCode
  }

}
