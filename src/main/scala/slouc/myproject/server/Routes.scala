package slouc.myproject.server

import cats.effect.Sync
import cats.implicits._
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl
import slouc.myproject.persistence.Database
import slouc.myproject.service.{Users, Jokes}

object Routes {

  def jokeRoutes[F[_] : Sync](jokes: Jokes[F]): HttpRoutes[F] = {
    val dsl = new Http4sDsl[F] {}
    import dsl._
    HttpRoutes.of[F] {
      case GET -> Root / "joke" =>
        for {
          joke <- jokes.get
          resp <- Ok(joke)
        } yield resp
    }
  }

  def usersRoutes[F[_] : Sync : Database](users: Users[F]): HttpRoutes[F] = {
    val dsl = new Http4sDsl[F] {}
    import dsl._
    HttpRoutes.of[F] {
      case GET -> Root / "users" / IntVar(id) =>
        for {
          users <- users.get(id)
          resp     <- Ok(users)
        } yield resp
    }
  }

}
