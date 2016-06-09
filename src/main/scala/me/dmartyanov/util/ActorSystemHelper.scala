package me.dmartyanov.util

import akka.actor.ActorSystem

/**
  * Created by hellraiser on 10/20/14.
  */
trait ActorSystemHelper {
  implicit def actorSystem: ActorSystem
}
