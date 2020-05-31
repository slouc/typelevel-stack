package slouc.myproject.persistence

import java.util.UUID

import doobie.free.connection.ConnectionIO

trait UserRepo {

  def get(id: UUID): ConnectionIO[String]
}
