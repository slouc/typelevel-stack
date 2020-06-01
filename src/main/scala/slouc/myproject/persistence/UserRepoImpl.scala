package slouc.myproject.persistence

import java.util.UUID

import doobie.free.connection.ConnectionIO

import doobie.implicits._
import doobie.postgres.implicits._

object UserRepoImpl extends UserRepo {

  override def get(id: UUID): ConnectionIO[String] =
    sql"""select email from users where id=$id""".query[String].unique

  override def create(email: String): ConnectionIO[Int] =
    sql"""insert into users(id, email) values (${UUID.randomUUID()}, $email)""".update.run
}
