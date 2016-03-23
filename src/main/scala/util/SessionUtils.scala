package util

import java.util.NoSuchElementException
import javax.servlet.http.HttpServletRequest

import org.scalatra.SessionSupport

/**
 * @author VKoulakov
 * @since 23.03.2016 11:32
 */
object SessionUtils {
  def sessionValue[T](key: String)(implicit controller : SessionSupport, request: HttpServletRequest): Option[T] = {
    try {
      Some(controller.session(key).asInstanceOf[T])
    }catch{
      case e:NoSuchElementException => None
    }
  }
}
