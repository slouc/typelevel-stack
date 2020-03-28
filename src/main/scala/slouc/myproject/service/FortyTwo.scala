package slouc.myproject.service

import cats.effect.{IO, Sync}
import org.http4s.client.dsl.Http4sClientDsl
import slouc.myproject.persistence.Database

trait FortyTwo[F[_]] {
  def get(database: Database[F]): F[String]
}

object FortyTwo {
  def apply[F[_]](implicit ev: FortyTwo[F]): FortyTwo[F] = ev

  final case class JokeError(e: Throwable) extends RuntimeException

  def impl[F[_] : Sync](): FortyTwo[F] = new FortyTwo[F] {
    val dsl = new Http4sClientDsl[F] {}
    def get(database: Database[F]): F[String] = {
      database.get()
    }
  }
}
