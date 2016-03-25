package controllers

import domain.Product
import helpers.Keys
import org.slf4j.LoggerFactory
import services.{TopCategoriesSupport, CartSupport}
import util.SessionUtils

/**
 * @author VKoulakov
 * @since 14.03.2016 19:05
 */
class IndexController extends ControllerBase with TopCategoriesSupport with CartSupport{
  get("/") {
    contentType = "text/html;charset=UTF-8"
    val products = Product.all.iterator.toList
    scaml("/index.scaml", "products" -> products)
  }
  get("/cart"){
    contentType = "text/html;charset=UTF-8"
    scaml("/products_summary.scaml", "cart" -> currentCart)
  }
  get("/hello") {
    <html>
      <body>
        <h1>Hello, world!!!! Do it with DCEVM</h1>
      </body>
    </html>
  }
  get("/login") {
    if (isAuthenticated) redirect("/")
    val redirectUri = params.get("redirect")
    if (redirectUri.isDefined && redirectUri.get.startsWith("/")) {
      flash += Keys.Flash.Redirect -> redirectUri.get
    }
    contentType = "text/html; charset=UTF-8"
    scaml("/login.scaml", "layout" -> "")
  }
  post("/login") {
    scentry.authenticate()
    if (isAuthenticated) {
      flash.get(Keys.Flash.Redirect).asInstanceOf[Option[String]].map { redirectUrl =>
        if (redirectUrl.stripSuffix("/") == request.getContextPath) {
          redirect("/")
        } else {
          redirect(redirectUrl)
        }
      }.getOrElse {
        redirect("/")
      }
    } else {
      redirect("/login")
    }
  }
  get("/logout") {
    scentry.logout
    redirect("/")
  }
  notFound {
    // Try to render a ScalateTemplate if no route matched
    findTemplate(requestPath) map { path =>
      contentType = "text/html"
      layoutTemplate(path)
    } orElse serveStaticResource() getOrElse resourceNotFound()
  }

}
