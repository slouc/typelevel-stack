package slouc.myproject.persistence

trait Database[F[_]] {

  def get(): F[String]
}
