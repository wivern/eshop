package domain.price

import org.squeryl.KeyedEntity
import org.squeryl.dsl.OneToMany

/**
 * @author VKoulakov
 * @since 24.03.2016 17:46
 */
case class PriceType(id: Long, name: String) extends KeyedEntity[Long]{
  lazy val prices: OneToMany[Price] = Prices.typeToPrices.left(this)
}
