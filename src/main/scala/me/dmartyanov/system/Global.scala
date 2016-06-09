package me.dmartyanov.system

import java.util.concurrent.{LinkedBlockingDeque, ThreadPoolExecutor, TimeUnit}

import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory
import me.dmartyanov.util.{ActorSystemHelper, ConfigHelper, ExecutionContextHelper}

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._
import scala.util.Try

/**
  * Created by hellraiser on 10/20/14.
  */
object Global {

  private lazy val configurationImpl = ConfigFactory.load()

  trait ConfigurationComponent extends ConfigHelper {
    override val config = configurationImpl
  }

  private lazy val actors = ActorSystem("xcarry", configurationImpl)

  trait ActorSystemComponent extends ActorSystemHelper {
    override implicit def actorSystem: ActorSystem = actors
  }

  private lazy val serviceContext = actors.dispatcher

  trait ServiceContextComponent extends ExecutionContextHelper {
    override implicit val executionContext: ExecutionContext = serviceContext
  }

  private lazy val dataBaseContext = ExecutionContext.fromExecutorService(
    new ThreadPoolExecutor(
      Runtime.getRuntime.availableProcessors(),
      Try(configurationImpl.getInt("db.connection.maxPoolSize")).getOrElse(16),
      (Try(configurationImpl.getInt("db.connection.keepAlive")).getOrElse(3) minute) toMillis,
      TimeUnit.MILLISECONDS,
      new LinkedBlockingDeque[Runnable]()
    )
  )

  trait DatabaseContextComponent extends ExecutionContextHelper {
    override implicit val executionContext: ExecutionContext = dataBaseContext
  }

}
