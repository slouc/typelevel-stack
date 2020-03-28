package slouc.myproject.server

import cats.effect.{ConcurrentEffect, ContextShift, IO, Timer}
import cats.implicits._
import org.http4s.implicits._
import fs2.Stream
import org.http4s.client.blaze.BlazeClientBuilder
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.server.middleware.Logger
import slouc.myproject.persistence.Database
import slouc.myproject.service.{FortyTwo, Jokes}

import scala.concurrent.ExecutionContext.global

object Server {

  def stream[F[_] : ConcurrentEffect](database: Database[F])(implicit T: Timer[F], C: ContextShift[F]): Stream[F, Nothing] = {
    for {
      client <- BlazeClientBuilder[F](global).stream
      jokeAlg     = Jokes.impl[F](client)
      fortyTwoAlg = FortyTwo.impl[F]()

      httpApp = (
        Routes.jokeRoutes[F](jokeAlg) <+> Routes.fortyTwoRoutes[F](fortyTwoAlg, database)
      ).orNotFound

      finalHttpApp = Logger.httpApp(true, true)(httpApp)

      exitCode <- BlazeServerBuilder[F]
        .bindHttp(8080, "0.0.0.0")
        .withHttpApp(finalHttpApp)
        .serve
    } yield exitCode
  }.drain
}
