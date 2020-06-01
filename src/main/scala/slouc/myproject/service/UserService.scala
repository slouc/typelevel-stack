package slouc.myproject.service

import java.util.UUID

import cats.effect.IO
import doobie.implicits._
import doobie.util.transactor.Transactor
import io.chrisdavenport.log4cats.slf4j.Slf4jLogger
import org.http4s.client.dsl.Http4sClientDsl
import slouc.myproject.persistence.UserRepo

class UserService(userRepo: UserRepo, dbTransactor: Transactor[IO]) {

  val dsl     = new Http4sClientDsl[IO] {}
  val loggerF = Slf4jLogger.create[IO]

  def get(id: UUID): IO[String] =
    for {
      logger <- loggerF
      _      <- logger.info(s"Fetching user with ID = $id...")
      result <- userRepo.get(id).transact(dbTransactor)
      _      <- logger.info(s"Fetched user with ID = $id, email = $result.")
    } yield result

  def create(email: String): IO[Int] =
    for {
      logger <- loggerF
      _      <- logger.info(s"Creating user with email = $email...")
      result <- userRepo.create(email).transact(dbTransactor)
      _      <- logger.info(s"Created user ID = $email.")
    } yield result
}
