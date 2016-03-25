package domain.price

import org.joda.money.Money
import org.slf4j.LoggerFactory
import org.squeryl.KeyedEntity
import org.squeryl.dsl.ManyToOne
import data.squeryl.MoneyTypes._
import util.DefaultParams

/**
 * @author VKoulakov
 * @since 24.03.2016 17:59
 */
case class Price(id: Long, priceTypeId: Long, productId: Long, amount: Money) extends KeyedEntity[Long]{
  lazy val priceType: ManyToOne[PriceType] = Prices.typeToPrices.right(this)
  lazy val product: ManyToOne[domain.Product] = Prices.productToPrices.right(this)
}

object Price{
  import Prices._

  private def logger = LoggerFactory.getLogger(getClass)

  def byProduct(productId: Long) = transaction{
    from(prices)(p => where(p.productId === productId) select p)
  }

  def byPriceType(priceTypeId: Long, page: Int = 0, pageLength: Int = DefaultParams.PAGE_LENGTH) = transaction{
    from(prices)(p => where(p.priceTypeId === priceTypeId) select p).page(page * pageLength, pageLength)
  }

  def byPriceTypeAndProduct(priceTypeId: Long, productId: Long) : Option[Price] = transaction{
    try {
      from(prices)(p => where(p.priceTypeId === priceTypeId and p.productId === productId) select p).singleOption
    }catch{
      case e: Exception => logger.error("Failed to load price", e); None
    }
  }

}