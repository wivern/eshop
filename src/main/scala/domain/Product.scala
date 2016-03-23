package domain

import org.squeryl.KeyedEntity
import org.squeryl.PrimitiveTypeMode._

object Product{
  def all() = transaction{ from(Catalogue.products)(p => select(p)) }
  def get(id: Long) : Option[Product] = {
    try {
      Some(transaction(Catalogue.products.get(id)))
    }catch {
      case e : Exception => None
    }
  }
}

/**
 * @author VKoulakov
 * @since 17.03.2016 15:29
 */
case class Product(id: Long,
                   name : String,
                   index: String,
                   description: Option[String]) extends KeyedEntity[Long]{
  lazy val categories = Catalogue.productCategories.left(this)
}
