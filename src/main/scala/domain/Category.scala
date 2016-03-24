package domain

import org.squeryl.KeyedEntity
import org.squeryl.PrimitiveTypeMode._
import org.squeryl.dsl.OneToMany


/**
 * @author VKoulakov
 * @since 17.03.2016 15:31
 */
object Category {

  import Catalogue._

  def topCategories() = transaction {
    from(categories)(c => where(c.parentId.isNull) select c orderBy c.name)
  }

  def get(id: Long) = transaction(Catalogue.categories.get(id))

  def children(parent: Long) = transaction {
    from(categories)(c =>
      where(c.parentId === parent) select c
        orderBy c.name
    )
  }
  def byProduct(productId: Long) = transaction {
    from(productCategories, categories)( (pc, c) =>
      where(c.id === pc.categoryId and pc.productId === productId)
      select c
      orderBy c.name
    )
  }
}


case class Category(id: Long,
                    name: String,
                    parentId: Option[Long],
                    description: Option[String]) extends KeyedEntity[Long] {
  def this(id: Long, name: String) = this(id, name, None, None)

  lazy val products = Catalogue.productCategories.right(this)
  lazy val children: OneToMany[Category] = Catalogue.categoryToChildren.left(this)
}
