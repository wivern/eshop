package controllers

/**
 * @author VKoulakov
 * @since 22.03.2016 18:09
 */

import domain.Product
import domain.order.{Cart, CartItem}
import org.json4s.{DefaultFormats, FieldSerializer, Formats}
import org.scalatra.json.JacksonJsonSupport
import org.scalatra.{BadRequest, NotFound, Ok}
import services.CartSupport

class ApiController extends ControllerBase with JacksonJsonSupport with CartSupport {
  protected implicit lazy val jsonFormats: Formats = DefaultFormats +
    FieldSerializer[Cart]() +
    FieldSerializer[CartItem]()

  before() {
    contentType = formats("json")
  }

  protected def getCart = currentCart

  get("/cart") {
    val cart: Cart = getCart
    session.put("eshop.Cart", cart)
    cart
  }
  post("/cart/:product") {
    val cart: Cart = getCart
    val productId = params.getOrElse("product", halt(400)).toLong
    val quantity = params.getOrElse("quantity", "1").toInt
    val product: Option[Product] = Product.get(productId)
    product match {
      case Some(p) =>
        val items = cart.items
        if (!items.exists(i => i.product.id == productId)) {
          val newCart = new Cart(items ::: List(new CartItem(p, quantity)))
          session.put("eshop.Cart", newCart)
          Ok(newCart)
        } else BadRequest("reason" -> "Product is already in cart")
      case None => NotFound("reason" -> "Product not found")
    }
  }
  delete("/cart/:product") {
    val cart: Cart = getCart
    val productId = params.getOrElse("product", halt(400)).toLong
    val newCart = new Cart(cart.items.filter(i => i.product.id != productId))
    session.put("eshop.Cart", newCart)
    newCart
  }
  put("/cart/:product") {
    val cart: Cart = getCart
    val productId = params.getOrElse("product", halt(400)).toLong
    val quantity = params.getOrElse("quantity", "1").toInt
    Product.get(productId) match {
      case Some(p) =>
        val item: CartItem = cart.items.find(i => i.product == p).getOrElse(halt(404))
        val newCart = new Cart(cart.items.map { case item => new CartItem(p, quantity); case x => x })
        session.put("eshop.Cart", newCart)
        newCart
      case None => NotFound
    }

  }
}
