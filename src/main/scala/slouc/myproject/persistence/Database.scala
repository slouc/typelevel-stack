package slouc.myproject.persistence

import java.util.UUID

trait Database[F[_]] {

  def get(id: UUID): F[String]
}

object Database {

  def apply[F[_] : Database]: Database[F] = implicitly[Database[F]]
}
