package domain

import org.squeryl.Schema
import org.squeryl.PrimitiveTypeMode._

/**
 * @author VKoulakov
 * @since 17.03.2016 15:58
 */
object Catalogue extends Schema{
  val products = table[Product]("PRODUCTS")
  val categories = table[Category]("CATEGORIES")
  val productCategories = manyToManyRelation(products, categories).
    via[ProductCategory]((p, c, pc) => (pc.categoryId === c.id, p.id === pc.productId))
  val users = table[User]("USERS")

  val categoryToChildren = oneToManyRelation(categories, categories).via((p, c) => p.id === c.parentId)

  on(products)(p => declare(
    p.index is(unique, indexed)
  ))
  on(categories)(c => declare(
    c.name is indexed
  ))
  on(users)(u => declare(
    u.email is(unique, indexed)
  ))
}
