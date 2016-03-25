package domain.price

import domain.Catalogue
import org.squeryl.Schema

//import org.squeryl.PrimitiveTypeMode._
//import org.squeryl.customtypes.CustomTypesMode._

import data.squeryl.MoneyTypes._

/**
 * @author VKoulakov
 * @since 24.03.2016 17:55
 */
object Prices extends Schema {
  val priceTypes = table[PriceType]("PRICETYPES")
  val prices = table[Price]("PRICES")

  val typeToPrices = oneToManyRelation(priceTypes, prices).
    via((t, p) => t.id === p.priceTypeId)

  val productToPrices = oneToManyRelation(Catalogue.products, prices).
    via((p, pr) => p.id === pr.productId)

  on(priceTypes)(p => declare(
    p.name is(unique, indexed)
  ))
}

