package me.dmartyanov.system.boot

import akka.actor.{Actor, ActorLogging}
import me.dmartyanov.api.core.ApiHttpService

/**
  * Created by hellraiser on 10/20/14.
  */
class ServiceActor extends Actor with ActorLogging
  with ApiHttpService {

  import spray.routing.RejectionHandler.Default

  def actorRefFactory = context

  override def receive = runRoute(services)
}
