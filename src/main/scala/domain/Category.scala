package domain

import org.squeryl.KeyedEntity
import org.squeryl.dsl.OneToMany
import org.squeryl.PrimitiveTypeMode._


/**
 * @author VKoulakov
 * @since 17.03.2016 15:31
 */
object Category{
  import Catalogue._
  def topCategories() = transaction{categories.where(c => c.parentId.isNull)}
  def get(id : Long) = transaction(Catalogue.categories.get(id))
}


case class Category(id: Long,
                    name: String, parentId: Option[Long]) extends KeyedEntity[Long] {
  lazy val products = Catalogue.productCategories.right(this)
  lazy val children : OneToMany[Category] = Catalogue.categoryToChildren.left(this)
}