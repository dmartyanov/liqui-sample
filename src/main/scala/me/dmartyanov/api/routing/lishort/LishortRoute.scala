package me.dmartyanov.api.routing.lishort

import me.dmartyanov.util.ExecutionContextHelper
import org.json4s.{DefaultFormats, Formats}
import spray.httpx.Json4sJacksonSupport
import spray.routing.HttpService

/**
  * Created by hellraiser on 10/20/14.
  */
trait LishortRoute extends HttpService with Json4sJacksonSupport {
  this: LishortModule with ExecutionContextHelper =>

  override implicit def json4sJacksonFormats: Formats = DefaultFormats

  val lishortRoute = {
    path("token") {
      get {
        onComplete(default) {
          complete(_)
        }
      } ~
        post {
          onComplete(default) {
            complete(_)
          }
        }
    } ~ pathPrefix("link") {
      pathEnd {
        get {
          onComplete(default) {
            complete(_)
          }
        } ~
          post {
            onComplete(default) {
              complete(_)
            }
          }
      } ~ path(Rest) { code =>
        get {
          onComplete(default) {
            complete(_)
          }
        } ~ post {
          onComplete(default) {
            complete(_)
          }
        }
      } ~ path(Rest / "clicks") { code =>
        get {
          onComplete(default) {
            complete(_)
          }
        }
      }
    } ~
      pathPrefix("folder") {
        pathEnd {
          get {
            onComplete(default) {
              complete(_)
            }
          }
        } ~ path(Rest) { id =>
          get {
            onComplete(default) {
              complete(_)
            }
          }
        }
      }
  }
}
