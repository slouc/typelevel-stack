package slouc.persistence

import java.util.UUID

import doobie.free.connection.ConnectionIO

trait UserRepo {

  def get(id: UUID): ConnectionIO[String]
  def getAll(): ConnectionIO[List[String]]
  def create(email: String): ConnectionIO[Int]
}
