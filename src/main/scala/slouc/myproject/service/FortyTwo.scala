package slouc.myproject.service

import cats.effect.Sync
import cats.implicits._
import io.chrisdavenport.log4cats.slf4j.Slf4jLogger
import org.http4s.client.dsl.Http4sClientDsl
import slouc.myproject.persistence.Database

abstract class FortyTwo[F[_] : Database] {
  def get: F[String]
}

object FortyTwo {
  def apply[F[_]](implicit ev: FortyTwo[F]): FortyTwo[F] = ev

  final case class JokeError(e: Throwable) extends RuntimeException

  def impl[F[_] : Sync : Database](): FortyTwo[F] = new FortyTwo[F] {
    val dsl = new Http4sClientDsl[F] {}
    val loggerF = Slf4jLogger.create[F]
    def get: F[String] =
      for {
        logger <- loggerF
        result <- Database[F].get()
        _      <- logger.info(s"Fetching $result.")
      } yield result
  }
}
