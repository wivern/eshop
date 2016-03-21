package domain

import org.squeryl.KeyedEntity
import org.squeryl.PrimitiveTypeMode._

object Product{
  def all() = transaction{ from(Catalogue.products)(p => select(p)) }
}

/**
 * @author VKoulakov
 * @since 17.03.2016 15:29
 */
case class Product(id: Long,
                   name : String,
                   index: String) extends KeyedEntity[Long]{
  lazy val categories = Catalogue.productCategories.left(this)
}
