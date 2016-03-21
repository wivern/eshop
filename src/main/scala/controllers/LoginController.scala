package controllers

import auth.AuthenticationSupport
import org.scalatra.ScalatraServlet
import org.scalatra.scalate.ScalateSupport

/**
 * @author VKoulakov
 * @since 18.03.2016 16:26
 */
class LoginController extends ScalatraServlet with ScalateSupport with AuthenticationSupport {
  get("/login"){
    if (isAuthenticated) redirect("/")
    contentType = "text/html; charset=UTF-8"
    scaml("/login.scaml", "layout" -> "")
  }
  post("/login"){
    scentry.authenticate()
    if (isAuthenticated){
      redirect("/")
    } else {
      redirect("/login")
    }
  }
  get("/logout"){
    scentry.logout
    redirect("/")
  }
}
