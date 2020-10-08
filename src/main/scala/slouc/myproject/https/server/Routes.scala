package slouc.myproject.https.server

import cats.effect.IO
import org.http4s.HttpRoutes
import org.http4s.circe.CirceEntityEncoder._
import org.http4s.dsl.Http4sDsl
import slouc.myproject.model.UserRequest
import slouc.myproject.service.UserService

object Routes {

  def userRoutes(users: UserService): HttpRoutes[IO] = {
    val dsl = new Http4sDsl[IO] {}
    import dsl._
    HttpRoutes.of[IO] {

      case GET -> Root / "healthy" => Ok()

      case GET -> Root / "ready"  => Ok()

      case GET -> Root / "users" =>
        for {
          users <- users.getAll()
          resp  <- Ok(users)
        } yield resp

      case GET -> Root / "users" / UUIDVar(id) =>
        for {
          user <- users.get(id)
          resp  <- Ok(user)
        } yield resp

      case req @ POST -> Root / "users" =>
        for {
          body <- req.as[UserRequest]
          _    <- users.create(body.email)
          resp <- Ok()
        } yield resp

    }
  }

}
