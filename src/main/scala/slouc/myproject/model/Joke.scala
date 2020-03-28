package slouc.myproject.model

import cats.Applicative
import cats.effect.Sync
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder}
import org.http4s.circe.{jsonEncoderOf, jsonOf}
import org.http4s.{EntityDecoder, EntityEncoder}

final case class Joke(joke: String) extends AnyVal

object Joke {
  implicit val jokeDecoder: Decoder[Joke] = deriveDecoder[Joke]
  implicit def jokeEntityDecoder[F[_] : Sync]: EntityDecoder[F, Joke] =
    jsonOf
  implicit val jokeEncoder: Encoder[Joke] = deriveEncoder[Joke]
  implicit def jokeEntityEncoder[F[_] : Applicative]: EntityEncoder[F, Joke] =
    jsonEncoderOf
}
