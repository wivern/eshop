package services

import domain.order.{CartItem, Cart}
import org.scalatra.{ScalatraBase, SessionSupport}
import util.SessionUtils

/**
 * @author VKoulakov
 * @since 23.03.2016 16:31
 */
trait CartSupport { this: ScalatraBase with SessionSupport =>
  val CART_KEY = "eshop.Cart"

  def currentCart = SessionUtils.sessionValue(CART_KEY).getOrElse(new Cart(List.empty[CartItem]))

  def cartContains(product: domain.Product) = currentCart.items.exists(item => item.product == product)

}
