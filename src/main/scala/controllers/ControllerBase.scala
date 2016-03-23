package controllers

import auth.AuthenticationSupport
import org.fusesource.scalate.Binding
import org.scalatra.i18n.Messages
import org.scalatra.{FlashMapSupport, ScalatraServlet}
import org.scalatra.scalate.{ScalateI18nSupport, ScalateSupport}
import util.DatabaseSessionSupport

/**
 * @author VKoulakov
 * @since 21.03.2016 12:28
 */
abstract class ControllerBase extends ScalatraServlet with ScalateSupport with DatabaseSessionSupport
with AuthenticationSupport with FlashMapSupport with ScalateI18nSupport{
  before(){
    templateEngine.bindings ::= Binding("messages", classOf[Messages].getName, importMembers = true, isImplicit = true)
  }

}
