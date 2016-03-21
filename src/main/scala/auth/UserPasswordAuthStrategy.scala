package auth

import javax.servlet.http.{HttpServletRequest, HttpServletResponse}

import domain.User
import org.scalatra.ScalatraBase
import org.scalatra.auth.ScentryStrategy
import org.slf4j.LoggerFactory

/**
 * @author VKoulakov
 * @since 18.03.2016 15:02
 */
class UserPasswordAuthStrategy(protected val app: ScalatraBase)(implicit request: HttpServletRequest, response: HttpServletResponse)
  extends ScentryStrategy[User] {

  val logger = LoggerFactory.getLogger(getClass)

  private def login = app.params.getOrElse("login", "")

  private def password = app.params.getOrElse("password", "")

  override def isValid(implicit request: HttpServletRequest): Boolean = {
    logger.info(s"Validatng: ${login}")
    login != null && password != null
  }

  override def authenticate()(implicit request: HttpServletRequest, response: HttpServletResponse): Option[User] = {
    val user = User.findByEmailAndPassword(login, password)
    user match {
      case None => logger.info("Authentication failed")
      case Some(u) => logger.info(s"User ${u.email} authenticated")
    }
    user
  }

  override def unauthenticated()(implicit request: HttpServletRequest, response: HttpServletResponse): Unit = app.redirect("/login")
}
