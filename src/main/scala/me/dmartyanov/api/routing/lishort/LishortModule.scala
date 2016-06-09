package me.dmartyanov.api.routing.lishort

import me.dmartyanov.util.ExecutionContextHelper

import scala.concurrent.Future

/**
  * Created by hellraiser on 10/20/14.
  */
trait LishortModule {
  this: ExecutionContextHelper =>

  def default = Future.successful(Map("OK" -> "OK"))
}
