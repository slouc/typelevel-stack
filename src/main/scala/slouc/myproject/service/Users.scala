package slouc.myproject.service

import java.util.UUID

import cats.effect.Sync
import cats.implicits._
import io.chrisdavenport.log4cats.slf4j.Slf4jLogger
import org.http4s.client.dsl.Http4sClientDsl
import slouc.myproject.persistence.Database

abstract class Users[F[_] : Database] {
  def get(id: Int): F[String]
}

object Users {
  def apply[F[_]](implicit ev: Users[F]): Users[F] = ev

  final case class JokeError(e: Throwable) extends RuntimeException

  def impl[F[_] : Sync : Database](): Users[F] = new Users[F] {
    val dsl = new Http4sClientDsl[F] {}
    val loggerF = Slf4jLogger.create[F]
    def get(id: Int): F[String] =
      for {
        logger <- loggerF
        _      <- logger.info(s"Fetched user ID = $id.")
        result <- Database[F].get(id)
        _      <- logger.info(s"Fetched user ID = $id, email = $result.")
      } yield result
  }
}
