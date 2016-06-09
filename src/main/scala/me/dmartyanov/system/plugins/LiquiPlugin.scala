package me.dmartyanov.system.plugins

import java.sql.Connection

import liquibase.Liquibase
import liquibase.changelog.ChangeSet
import liquibase.database.jvm.JdbcConnection
import liquibase.resource.{ClassLoaderResourceAccessor, CompositeResourceAccessor, FileSystemResourceAccessor}
import me.dmartyanov.system.Global.ConfigurationComponent
import me.dmartyanov.util.LoggerHelper

import scala.collection.JavaConversions._
import scala.util.{Failure, Success, Try}

/**
  * Created by hellraiser on 10/20/14.
  */
object LiquiPlugin extends LoggerHelper with ConfigurationComponent {

  val availableContexts = Set("test", "dev", "prod")

  private def getScriptDescriptions(changeSets: Seq[ChangeSet]) = {
    changeSets.zipWithIndex.map {
      case (cl, num) =>
        "" + num + ". " + cl.getId +
          Option(cl.getDescription).map(" (" + _ + ")").getOrElse("") +
          " by " + cl.getAuthor
    }.mkString("\n")
  }

  def start(connection: Connection, dbPath: String) = {
    log("Running migrations", INFO)
    Try(getLiquibase(connection)) match {
      case Success(liqui) => config.get[String]("app.mode") match {
        case mode: String if availableContexts.contains(mode) => {
          val liquiMigrations = liqui.listUnrunChangeSets(mode)
          Try(liqui.update(mode)) match {
            case Success(_) =>
              log(s"Migrations were completed successfully: \n${getScriptDescriptions(liquiMigrations)}\n" +
                s" on database [$dbPath]", INFO)
            case Failure(err) => {
              val msg = s"Couldn't apply migrations: [${getScriptDescriptions(liquiMigrations)}]" +
                s" on database [$dbPath]; error:  [${err.getMessage}}]"
              log(msg, ERROR)
              throw new RuntimeException(msg, err)
            }
          }
        }
        case m => {
          val msg = s"Liquibase couldn't be applyed on mode ${m}"
          log(msg, ERROR)
          throw new RuntimeException(msg)
        }
      }
      case Failure(err) => throw err
    }
  }

  def getLiquibase(connection: Connection) = {
    val fileAccessor = new FileSystemResourceAccessor(this.getClass.getResource("/changelog").getPath)
    val classLoaderAccessor = new ClassLoaderResourceAccessor(this.getClass.getClassLoader)
    val fileOpener = new CompositeResourceAccessor(fileAccessor, classLoaderAccessor)
    new Liquibase("migrations.xml", fileOpener, new JdbcConnection(connection))
  }
}
