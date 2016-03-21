package controllers

import auth.AuthenticationSupport
import org.scalatra.ScalatraServlet
import org.scalatra.scalate.ScalateSupport
import org.slf4j.LoggerFactory
import util.DatabaseSessionSupport

/**
 * @author VKoulakov
 * @since 17.03.2016 18:52
 */
class AdminController extends ScalatraServlet with ScalateSupport with DatabaseSessionSupport
  with AuthenticationSupport{
  val logger =  LoggerFactory.getLogger(getClass)

  before(){
    requireAuth()
    templateAttributes("currentPath") = requestPath(request)
    templateAttributes("user") = user
  }

  val root = get("/?"){
    contentType = "text/html;charset=UTF-8"
    scaml("/admin/index.scaml", "layout" -> "/WEB-INF/layouts/admin.scaml")
  }

  get("/blank.html"){
    contentType = "text/html; charset=UTF-8"
    scaml("/admin/page.scaml", "layout" -> "/WEB-INF/layouts/admin.scaml", "admin" -> url("/"))
  }
}
