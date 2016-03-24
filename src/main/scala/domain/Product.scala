package domain

import domain.price.{Price, Prices}
import org.squeryl.KeyedEntity
import org.squeryl.PrimitiveTypeMode._
import util.DefaultParams

object Product {

  import Catalogue._

  def all() = transaction {
    from(products)(p => select(p))
  }

  def get(id: Long): Option[Product] = {
    try {
      Some(transaction(products.get(id)))
    } catch {
      case e: Exception => None
    }
  }

  def byCategoryCount(categoryId: Long) = transaction {
    from(productCategories, products)((pc, p) =>
      where(p.id === pc.productId and pc.categoryId === categoryId)
        compute countDistinct(p.id))
  }

  def byCategory(categoryId: Long) = transaction {
    from(productCategories, products)((pc, p) => where(p.id === pc.productId and pc.categoryId === categoryId)
      select p
      orderBy p.name
    )
  }

  def byCategoryPaging(categoryId: Long, offset: Int, pageLength: Int = DefaultParams.PAGE_LENGTH) = transaction {
    byCategory(categoryId).page(offset, pageLength)
  }
}

/**
 * @author VKoulakov
 * @since 17.03.2016 15:29
 */
case class Product(id: Long,
                   name: String,
                   index: String,
                   description: Option[String],
                   retailPriceType: Option[Long]
                    ) extends KeyedEntity[Long] {
  def this(id: Long, name: String, index: String) = this(id, name, index, None, None)

  lazy val categories = Catalogue.productCategories.left(this)

  def retailPrice = retailPriceType match {
    case None => null
    case Some(p) => Price.byPriceTypeAndProduct(p, id)
  }
}
