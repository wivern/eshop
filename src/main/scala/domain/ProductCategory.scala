package domain

import org.squeryl.KeyedEntity
import org.squeryl.PrimitiveTypeMode._
import org.squeryl.dsl.CompositeKey2

/**
 * @author VKoulakov
 * @since 17.03.2016 16:30
 */
case class ProductCategory(productId: Long, categoryId: Long) extends KeyedEntity[CompositeKey2[Long, Long]] {
  override def id: CompositeKey2[Long, Long] = compositeKey(productId, categoryId)
}
