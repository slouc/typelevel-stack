package slouc.myproject.zio

import zio.ZIO
import zio.blocking.Blocking
import zio.clock.Clock

package object server {

  type AppEnvironment   = Clock with Blocking
  type AppTaskThrow[+A] = ZIO[AppEnvironment, Throwable, A]

}
