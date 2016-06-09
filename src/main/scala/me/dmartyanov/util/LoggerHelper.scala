package me.dmartyanov.util

import org.slf4j.LoggerFactory

/**
  * Created by hellraiser on 10/20/14.
  */
trait LoggerHelper {
  self: LoggerHelper =>

  val ERROR = 1
  val WARN = 2
  val INFO = 3

  val lf = LoggerFactory.getLogger(self.getClass)

  def log(msg: String, priority: Int) = priority match {
    case ERROR => lf.error(msg)
    case WARN => lf.warn(msg)
    case INFO => lf.info(msg)
  }

  def logWarn[T](msg: String)(default: => T) = {
    lf.warn(msg)
    default
  }
}
