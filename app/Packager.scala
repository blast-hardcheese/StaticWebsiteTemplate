package playpackager

import java.io._

import play.api._
import play.api.mvc._
import play.core.StaticApplication

import java.util.Date
import java.text.SimpleDateFormat
import org.apache.commons.io.FileUtils

trait StaticGenerator {
  def mapping:Map[String => Call, String => play.api.templates.HtmlFormat.Appendable]
  def extraMaps:Map[String, String] = Map()
}

object Util {
  def cp(source: File, target: File) {
    for(file <- source.listFiles) {
      val newPath = new File(target.getPath, file.getName)

      if(file.isDirectory) {
        cp(file, newPath)
      } else {
        FileUtils.copyFile(file, newPath)
      }
    }
  }

  def joinPaths(before: String, after: String): String = {
    new File(before, after).getPath
  }

  def scrubPath(_path: String): String = {
    if(_path.endsWith("/")) {
      _path + "index.html"
    } else {
      _path
    }
  }

  def generateSources(app: StaticGenerator): Map[String, String] = {
    val defaultLanguages = ""
    val langs = Play.current.configuration.getString("application.langs").getOrElse(defaultLanguages).split(",")

    val mappings = for(
      lang <- langs;
      (genPath, genPage) <- app.mapping
    ) yield {
      val path: String = genPath(lang).toString
      val source: String = genPage(lang).toString

      path -> source
    }

    (mappings.toMap ++ app.extraMaps).map({ case (k, v) => (scrubPath(k), v) })
  }
}

object Packager extends App {
  import Util._

  // Creating an application in this way stores it in a Play internal variable
  // Without this, we are unable to use config/messages[.*]
  val application = new StaticApplication(new File("."))

  val outputDirectoryFormat = new SimpleDateFormat("yyyy-MM-dd_HHMMSS");
  val targetDir = joinPaths("output", outputDirectoryFormat.format(new Date()))
  val assetsDir = joinPaths(targetDir, "assets")

  cp(new File("public"), new File(assetsDir))

  val sourceMappings = generateSources(controllers.Application)
  for((path, source) <- sourceMappings) {
    val target = new File(joinPaths(targetDir, path))
    target.getParentFile.mkdirs
    target.createNewFile

    val writer = new PrintWriter(target)
    writer.write(source)
    writer.close()
  }

}
