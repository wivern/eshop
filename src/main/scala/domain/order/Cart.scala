package domain.order

/**
 * @author VKoulakov
 * @since 22.03.2016 16:59
 */
case class Cart(val items: List[CartItem]) {
  val count = items.size
}
