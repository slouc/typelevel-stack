package slouc.myproject.persistence

trait Database[F[_]] {

  def get(): F[String]
}

object Database {

  def apply[F[_] : Database]: Database[F] = implicitly[Database[F]]
}
