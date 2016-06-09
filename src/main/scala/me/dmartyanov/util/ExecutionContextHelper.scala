package me.dmartyanov.util

import scala.concurrent.ExecutionContext

/**
  * Created by hellraiser on 10/20/14.
  */
trait ExecutionContextHelper {
  implicit val executionContext: ExecutionContext
}
