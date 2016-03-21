package helpers

/**
 * @author VKoulakov
 * @since 18.03.2016 13:28
 */
object UrlHelpers {
  def currentUrl(path: String)(implicit currentPath : String) = path == currentPath
}
