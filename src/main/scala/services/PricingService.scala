package services

import domain.Product
import domain.price.{Price, ProductItem}

/**
 * @author VKoulakov
 * @since 25.03.2016 16:10
 */
object PricingService {
  def productItem(product: Product) : ProductItem = {
    val price = product.retailPriceType match {
      case Some(t) => Price.byPriceTypeAndProduct(t, product.id)
      case None => None
    }
    new ProductItem(product, price.orNull)
  }

  def productItem(products: List[Product]) : List[ProductItem] = {
    products.map{ p => productItem(p) }
  }
}
