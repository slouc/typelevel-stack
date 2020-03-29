package slouc.myproject.persistence

trait Database[F[_]] {

  def get(id: Int): F[String]
}

object Database {

  def apply[F[_] : Database]: Database[F] = implicitly[Database[F]]
}
