# Typelevel stack backend

This project contains a small skeleton of a simple web server. It provides a schoolbook way of building a simple backend from basic parts, mostly coming from the [typelevel](https://typelevel.org/) stack.

Libraries:
- [cats](https://typelevel.org/cats/)
- [http4s](https://http4s.org/)
- [circe](https://circe.github.io/circe/)
- [doobie](https://tpolecat.github.io/doobie/)
- [pureconfig](https://pureconfig.github.io/)
- [log4cats](https://christopherdavenport.github.io/log4cats/)

SBT plugins:
- [scalafmt](https://scalameta.org/scalafmt/) for code formatting
- [flyway-sbt](https://github.com/flyway/flyway-sbt) for database migrations.

It uses [docker-compose](https://docs.docker.com/compose/) to pack the server together with a [PostgreSQL](https://www.postgresql.org/) database. This is useful for running the server locally.

Server defines two endpoints:
- GET `/users/[UUID]` for fetching users from the database
- PUT `/users/[UUID]` for adding users to the database

First spin up docker and populate the database, then run the server:
```
> docker-compose up
> sbt
sbt:myproject> flywayMigrate
sbt:myproject> run 
```

You can try out the server by issuing
```
> curl localhost:8080/users/f33f4247-938b-49cc-b8f7-a01d138ff26f
> TODO PUT
```
