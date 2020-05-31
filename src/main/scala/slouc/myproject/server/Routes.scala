package slouc.myproject.server

import cats.effect.IO
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl
import slouc.myproject.service.UserService

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
    }
  }

}
