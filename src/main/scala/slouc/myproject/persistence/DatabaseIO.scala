package slouc.myproject.persistence

import java.util.UUID

import cats.effect.{Blocker, IO}
import doobie.implicits._
import doobie.util.transactor.Transactor
import doobie.postgres.implicits._

object DatabaseIO {

  import doobie.util.ExecutionContexts

  val config = DatabaseConfig.config

  implicit val databaseIO = new Database[IO] {

    implicit val cs = IO.contextShift(ExecutionContexts.synchronous)

    def xa =
      Transactor.fromDriverManager[IO](
        config.driver,
        config.url,
        config.username,
        config.password,
        Blocker.liftExecutionContext(ExecutionContexts.synchronous)
      )

    def get(id: UUID): IO[String] = {
      val query = sql"""select email from users where id=$id""".query[String].unique
      query.transact(xa)
    }
  }

}
