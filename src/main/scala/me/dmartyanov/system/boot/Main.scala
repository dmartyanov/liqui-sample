package me.dmartyanov.system.boot

import akka.actor.Props
import akka.io.IO
import me.dmartyanov.dao.PostgresqlDBManager
import me.dmartyanov.system.Global.{ActorSystemComponent, ConfigurationComponent}
import me.dmartyanov.system.plugins.LiquiPlugin
import me.dmartyanov.util.LoggerHelper
import spray.can.Http
import spray.routing.SimpleRoutingApp

/**
  * Created by hellraiser on 10/20/14.
  */
object Main extends App
  with SimpleRoutingApp
  with ActorSystemComponent
  with ConfigurationComponent {

  val dbManager = new PostgresqlDBManager with ConfigurationComponent with LoggerHelper
  LiquiPlugin.start(dbManager.connection, dbManager.url)

  val handler = actorSystem.actorOf(Props[ServiceActor], name = "handler")
  IO(Http) ! Http.Bind(handler, interface = "0.0.0.0", port = config.get[Int]("app.port", 9000))
}
