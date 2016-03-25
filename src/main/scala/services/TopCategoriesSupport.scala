package services

import domain.Category
import org.scalatra.ScalatraBase
import org.scalatra.scalate.ScalateSupport

/**
 * @author VKoulakov
 * @since 21.03.2016 16:26
 */
trait TopCategoriesSupport {
  this: ScalatraBase with ScalateSupport =>
  before() {
    val categories = Category.topCategories().iterator.toList
    templateAttributes("topCategories") = categories
  }
}
