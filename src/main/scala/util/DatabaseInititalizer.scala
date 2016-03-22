package util

import domain._
import org.slf4j.Logger
import org.squeryl.PrimitiveTypeMode._

/**
 * @author VKoulakov
 * @since 17.03.2016 16:07
 */
trait DatabaseInititalizer {
  implicit val logger: Logger
  import Catalogue._
  def initDb() {
    logger.info("Applying database schema")
    transaction {
      Catalogue.printDdl
      Catalogue.create
      products.insert(List(
        new Product(1, "Test1", "TT00001", None),
        new Product(2, "Test2", "TT00002", None),
        new Product(3, "Test3", "TT00003", None)
      ))
      users.insert(List(
        new User(1, "admin", "admin@gmail.com", "admin"),
        new User(2, "John Doe", "user@gmail.com", "user")
      ))
      categories.insert(List(
        new Category(1, "Electronics"),
        new Category(2, "Computers, Tablets & laptop", Some(1), None),
        new Category(3, "Mobile Phone", Some(1), None),
        new Category(4, "Clothes"),
        new Category(5, "Food and beverages"),
        new Category(6, "Health and beauty"),
        new Category(7, "Sports & Leisure"),
        new Category(8, "Books & Entertainments")
      ))
      productCategories.insert(List(
        new ProductCategory(1, 2),
        new ProductCategory(2, 2),
        new ProductCategory(3, 2)
      ))
    }
  }
}
