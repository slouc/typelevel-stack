package slouc.persistence

import pureconfig._
import pureconfig.generic.auto._

case class DatabaseConfig(
  driver: String,
  url: String,
  username: String,
  password: String
)

object DatabaseConfig {
  val config = ConfigSource.default.at("database").loadOrThrow[DatabaseConfig]
}
