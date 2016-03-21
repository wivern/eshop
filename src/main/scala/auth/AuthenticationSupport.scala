package auth

import domain.User
import org.scalatra.ScalatraBase
import org.scalatra.auth.{ScentryConfig, ScentrySupport}
import util.StringUtil

/**
 * @author VKoulakov
 * @since 18.03.2016 13:58
 */
trait AuthenticationSupport extends ScentrySupport[User] {
  self: ScalatraBase =>

  protected def requireAuth() = {
    val context = request.getServletContext.getContextPath
    val path = request.getRequestURI.substring(context.length)
    if (!isAuthenticated) redirect(scentryConfig.login + "?redirect=" + StringUtil.urlEncode(path))
  }

  protected def fromSession = {
    case id: String => User.findByEmail(id)
  }

  protected def toSession = {
    case usr: User => usr.email
  }

  protected val scentryConfig = new ScentryConfig {
    override val login: String = "/login"
  }.asInstanceOf[ScentryConfiguration]

  override protected def configureScentry(): Unit = {
    scentry.unauthenticated {
      scentry.strategies("UserPassword").unauthenticated()
    }
  }

  override protected def registerAuthStrategies(): Unit = {
    scentry.register("UserPassword", app => new UserPasswordAuthStrategy(app))
  }
}
