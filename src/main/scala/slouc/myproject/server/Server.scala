package slouc.myproject.server

import cats.effect.{ConcurrentEffect, ContextShift, Sync, Timer}
import cats.implicits._
import fs2.Stream
import io.chrisdavenport.log4cats.Logger
import io.chrisdavenport.log4cats.slf4j.Slf4jLogger
import org.http4s.client.blaze.BlazeClientBuilder
import org.http4s.implicits._
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.server.middleware.{Logger => HttpLogger}
import slouc.myproject.persistence.Database
import slouc.myproject.service.{FortyTwo, Jokes}

import scala.concurrent.ExecutionContext.global

object Server {

  def stream[F[_] : Database : ConcurrentEffect](implicit T: Timer[F], C: ContextShift[F]): Stream[F, Nothing] = {
    {
      for {
        client <- BlazeClientBuilder[F](global).stream
        jokeAlg     = Jokes.impl[F](client)
        fortyTwoAlg = FortyTwo.impl[F]()

        httpApp = (
          Routes.jokeRoutes[F](jokeAlg) <+> Routes.fortyTwoRoutes[F](fortyTwoAlg)
          ).orNotFound

        finalHttpApp = HttpLogger.httpApp(true, true)(httpApp)

        exitCode <- BlazeServerBuilder[F]
          .bindHttp(8080, "0.0.0.0")
          .withHttpApp(finalHttpApp)
          .serve
      } yield exitCode
    }
  }.drain
}
