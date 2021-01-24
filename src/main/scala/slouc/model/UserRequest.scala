package slouc.model

import org.http4s.circe._
import cats.effect.IO
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder}

final case class UserRequest(email: String)

object UserRequest {

  implicit val decoder: Decoder[UserRequest] = deriveDecoder[UserRequest]
  implicit val encoder: Encoder[UserRequest] = deriveEncoder[UserRequest]
  implicit val entityDecoder = jsonOf[IO, UserRequest]
}
