package domain.order

import domain.Product
/**
 * @author VKoulakov
 * @since 22.03.2016 17:00
 */
case class CartItem(product: Product,
                    quantity: Int){
}
