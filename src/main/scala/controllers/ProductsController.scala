package controllers

import domain.{Category,Product}
import services.TopCategoriesSupport

/**
 * @author VKoulakov
 * @since 21.03.2016 18:15
 */
class ProductsController extends ControllerBase with TopCategoriesSupport {
  import services.PricingService._
  get("/:catId") {
    val catId = params.getOrElse("catId", halt(400)).toLong
    val category = Category.get(catId)
    val products = productItem(Product.byCategory(catId).iterator.toList)
    contentType = "text/html; charset=UTF-8"
    scaml("/products.scaml", "category" -> category, "products" -> products)
  }
}
