package controllers

/**
 * @author VKoulakov
 * @since 22.03.2016 18:09
 */

import domain.order.{Cart, CartItem}
import domain.{Category, Product}
import org.json4s.{DefaultFormats, FieldSerializer, Formats}
import org.scalatra.json.JacksonJsonSupport
import org.scalatra.swagger.{Swagger, SwaggerSupport}
import org.scalatra.{BadRequest, NotFound, Ok}
import services.pagination.{Page, PaginationSupport}
import services.{CartSupport, TopCategoriesSupport}
import util.DefaultParams

class ApiController(implicit val swagger: Swagger) extends ControllerBase with JacksonJsonSupport with CartSupport with TopCategoriesSupport
with SwaggerSupport with PaginationSupport{

  import FieldSerializer._

  val categorySerializer = FieldSerializer[Category](
    ignore("children") orElse ignore("products")
  )
  val productSerializer = FieldSerializer[Product](
    ignore("categories")
  )

  protected implicit lazy val jsonFormats: Formats = DefaultFormats +
    FieldSerializer[Cart]() +
    FieldSerializer[CartItem]() +
    FieldSerializer[Page[Product]]() +
    categorySerializer +
    productSerializer

  protected val applicationDescription = "The EShop API. It exposes operation for browsing catalogue, cart management."

  before() {
    contentType = formats("json")
  }

  protected def getCart = currentCart

  val getCartOp = (apiOperation[Cart]("getCart")
    summary "Retrive user`s cart"
    notes "User`s cart currently stored in the session"
    )

  /* Cart */
  get("/cart", operation(getCartOp)) {
    val cart: Cart = getCart
    session.put("eshop.Cart", cart)
    cart
  }

  val addToCartOp = (apiOperation[Cart]("addToCart")
    summary "Add product to user`s cart"
    parameters pathParam[Long]("product").description("Id of product to be added to the cart")
    )
  post("/cart/:product", operation(addToCartOp)) {
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
  val removeFromCartOp = (apiOperation[Cart]("removeFromCart")
    summary "Remove specified product from the user`s cart"
    parameters pathParam[Long]("product").description("Id of the product to be removed from the cart")
  )
  delete("/cart/:product", operation(removeFromCartOp)) {
    val cart: Cart = getCart
    val productId = params.getOrElse("product", halt(400)).toLong
    val newCart = new Cart(cart.items.filter(i => i.product.id != productId))
    session.put("eshop.Cart", newCart)
    newCart
  }
  val modifyCartOp = (apiOperation[Cart]("modifyCartItem")
    summary "Modify quantity of product in the cart"
    parameters(pathParam[Long]("product").description("Id of product in the cart to be modified"),
      queryParam[Int]("quantity").description("Quantity of product that would be set"))
  )
  put("/cart/:product", operation(modifyCartOp)) {
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
  /* Catalogue */
  get("/catalogue") {
    Category.topCategories().toList
  }
  get("/catalogue/:parent") {
    val parentId = params.getOrElse("parent", halt(400)).toLong
    Category.children(parentId).toList
  }
  get("/catalogue/products/:category") {
    val categoryId = params.getOrElse("category", halt(400)).toLong
    val page = params.getOrElse("page", "-1").toInt
    val pageLength = params.getOrElse("size", DefaultParams.PAGE_LENGTH.toString).toInt
    page match {
      case -1 => Product.byCategory (categoryId).toList
      case _ => paginate(Product.byCategoryPaging(categoryId, page * pageLength, pageLength).toList, page, {
        Product.byCategoryCount(categoryId).single.measures
      })
    }
  }
  get("/catalogue/product/:id") {
    val productId = params.getOrElse("id", halt(400)).toLong
    Product.get(productId).getOrElse(NotFound(reason = "Product not found"))
  }
  get("/catalogue/product/:id/categories") {
    val productId = params.getOrElse("id", halt(400)).toLong
    Category.byProduct(productId).toList
  }
}
