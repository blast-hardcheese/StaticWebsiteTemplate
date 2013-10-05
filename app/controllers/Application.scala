package controllers

import play.api._
import play.api.mvc._

object Application extends Controller {
  def buildLang(lang: String) = play.api.i18n.Lang(lang.toLowerCase)

  def doIndex(langStr: String) = {
    implicit val lang = buildLang(langStr)
    implicit val currentPage: Function1[String, Call] = routes.Application.indexLang _
    views.html.index()
  }

  def index = Action {
    Ok(doIndex("en-us"))
  }

  def indexLang(langStr: String) = Action {
    Ok(doIndex(langStr))
  }

  def doTemplate(langStr: String) = {
    implicit val lang = buildLang(langStr)
    implicit val currentPage: Function1[String, Call] = routes.Application.template _
    views.html.template()
  }

  def template(langStr: String) = Action {
    Ok(doTemplate(langStr))
  }
}
