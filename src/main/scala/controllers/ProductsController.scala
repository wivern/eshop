package controllers

import domain.Category
import org.scalatra.NotFound

/**
 * @author VKoulakov
 * @since 21.03.2016 18:15
 */
class ProductsController extends ControllerBase with TopCategoriesSupport {
  get("/:catId") {
    val catId = try {
      params("catId").toLong
    } catch {
      case e: NumberFormatException => None
    }
    val category = catId match {
      case c: Long => Category.get(c)
      case None => NotFound
    }
    contentType = "text/html; charset=UTF-8"
    scaml("/products.scaml", "category" -> category)
  }
}
