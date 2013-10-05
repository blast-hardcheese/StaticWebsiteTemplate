package controllers

import play.api._
import play.api.mvc._

object Application extends Controller {
  def buildLang(lang: String) = play.api.i18n.Lang(lang.toLowerCase)

  def index = Action {
    Ok(views.html.index())
  }

}
