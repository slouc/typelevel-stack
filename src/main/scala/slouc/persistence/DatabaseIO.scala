package slouc.persistence

import cats.effect.{Blocker, IO}
import doobie.util.transactor.Transactor
import doobie.util.ExecutionContexts

object DatabaseIO {

  val config = DatabaseConfig.config

  implicit val cs = IO.contextShift(ExecutionContexts.synchronous)

  def transactor =
    Transactor.fromDriverManager[IO](
      config.driver,
      config.url,
      config.username,
      config.password,
      Blocker.liftExecutionContext(ExecutionContexts.synchronous)
    )

}
