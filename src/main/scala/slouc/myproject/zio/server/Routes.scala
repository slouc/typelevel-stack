package slouc.myproject.zio.server

import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl
import zio.interop.catz._

object Routes {

  def userRoutes(): HttpRoutes[AppTaskThrow] = {
    val dsl = new Http4sDsl[AppTaskThrow] {}
    import dsl._
    HttpRoutes.of[AppTaskThrow] {

      case GET -> Root / "healthy" => Ok()
        
      case GET -> Root / "ready"  => Ok()

    }
  }

}
