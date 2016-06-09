package me.dmartyanov.api.core

import me.dmartyanov.api.routing.lishort.{LishortModule, LishortRoute}
import me.dmartyanov.system.Global.ServiceContextComponent
import me.dmartyanov.util.ExecutionContextHelper
import spray.routing.{HttpService, Route}

/**
  * Created by hellraiser on 10/20/14.
  */
trait LishortWebModule extends LishortModule with LishortRoute {
  this: ExecutionContextHelper =>
}

trait ApiHttpService extends HttpService
  with LishortWebModule
  with ServiceContextComponent {

  val services: Route = lishortRoute
}
