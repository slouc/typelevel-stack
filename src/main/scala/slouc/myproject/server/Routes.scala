package slouc.myproject.server

import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl
import slouc.myproject.model.UserRequest
import slouc.myproject.service.UserService
import cats.effect.IO

object Routes {

  def userRoutes(users: UserService): HttpRoutes[IO] = {
    val dsl = new Http4sDsl[IO] {}
    import dsl._
    HttpRoutes.of[IO] {

      case GET -> Root / "users" / UUIDVar(id) =>
        for {
          users <- users.get(id)
          resp  <- Ok(users)
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
