package slouc.myproject.persistence

import cats.effect.{Blocker, IO}
import doobie.implicits._
import doobie.util.transactor.Transactor

object DatabaseIO {

  implicit val databaseIO = new Database[IO] {

    import doobie.util.ExecutionContexts

    implicit val cs = IO.contextShift(ExecutionContexts.synchronous)

    def xa =
      Transactor.fromDriverManager[IO](
        "org.postgresql.Driver",
        "jdbc:postgresql:postgres",
        "postgres",
        "1234",
        Blocker.liftExecutionContext(ExecutionContexts.synchronous)
      )

    def get(): IO[String] = {
      val query = sql"select email from users limit 1".query[String].unique
      query.transact(xa)
    }
  }

}
