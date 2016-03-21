package util

import java.net.URLEncoder

/**
 * @author VKoulakov
 * @since 18.03.2016 18:43
 */
object StringUtil {
  def urlEncode(value: String): String = URLEncoder.encode(value, "UTF-8").replace("+", "%20")
}
