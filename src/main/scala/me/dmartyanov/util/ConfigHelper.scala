package me.dmartyanov.util

import java.util.concurrent.TimeUnit

import com.typesafe.config.Config

import scala.collection.JavaConversions._
import scala.concurrent.duration._
import scala.language.implicitConversions
import scala.reflect.runtime.universe._

/**
  * Created by hellraiser on 10/20/14.
  */
trait ConfigHelper {

  implicit class ExtendedConfig(c: Config) {
    private val StringTag = typeTag[String]
    private val DurationTag = typeTag[Duration]
    private val StringListTag = typeTag[List[String]]

    def getOpt[T](path: String)(implicit tag: TypeTag[T]): Option[T] =
      if (c.hasPath(path)) (
        tag match {
          case StringTag => Some(c.getString(path))
          case TypeTag.Int => Some(c.getInt(path))
          case TypeTag.Float => Some(c.getDouble(path).toFloat)
          case DurationTag => Some((c.getDuration(path, TimeUnit.MILLISECONDS)).millis)
          case StringListTag => Some(c.getStringList(path).toList)
          case _ => throw new IllegalArgumentException(s"Configuration option type $tag not implemented")
        }
        ).asInstanceOf[Option[T]]
      else None

    def get[T](path: String, default: => T)(implicit tag: TypeTag[T]) = getOpt(path).getOrElse(default)

    def get[T](path: String)(implicit tag: TypeTag[T]) = getOpt(path)
      .getOrElse(throw new RuntimeException(s"Configuration value at $path not found"))
  }

  val config: Config

}
