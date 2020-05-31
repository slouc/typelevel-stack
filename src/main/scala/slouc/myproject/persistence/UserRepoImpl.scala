package slouc.myproject.persistence

import java.util.UUID

import doobie.free.connection.ConnectionIO

import doobie.implicits._
import doobie.postgres.implicits._

object UserRepoImpl extends UserRepo {

  def get(id: UUID): ConnectionIO[String] =
    sql"""select email from users where id=$id""".query[String].unique

}
