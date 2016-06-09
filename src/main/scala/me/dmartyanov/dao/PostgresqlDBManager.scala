package me.dmartyanov.dao

import java.sql.DriverManager

import me.dmartyanov.util.{ConfigHelper, LoggerHelper}

import scala.util.{Failure, Success, Try}

/**
  * Created by hellraiser on 10/20/14.
  */
trait PostgresqlDBManager extends ConfigHelper {
  this: LoggerHelper =>

  lazy val host = config.get[String](s"db.host")

  lazy val dbname = config.get[String](s"db.dbname")

  lazy val port = config.get[Int]("db.port")

  def url = s"""jdbc:postgresql://$host:$port/$dbname"""

  lazy val user = config.get[String]("db.user")
  lazy val password = config.get[String]("db.password")

  def connection = Try(DriverManager.getConnection(url, user, password)) match {
    case Success(conn) => conn
    case Failure(err) => sys.error(s"Couldn't connect to $url; Error: ${err.getMessage}")
  }

}
