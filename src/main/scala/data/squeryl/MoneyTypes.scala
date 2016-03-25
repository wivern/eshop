package data.squeryl

import org.joda.money.{CurrencyUnit, Money}
import org.squeryl.PrimitiveTypeMode
import org.squeryl.dsl._

/**
 * @author VKoulakov
 * @since 25.03.2016 13:01
 */
object MoneyTypes extends PrimitiveTypeMode {
  private val ps = PrimitiveTypeSupport

  implicit val jodaMoneyTEF = new NonPrimitiveJdbcMapper[String, Money, TString](ps.stringTEF, this) {
    override def convertFromJdbc(v: String): Money = try{ Money.parse(v) } catch { case _:Exception => Money.nonNull(null, CurrencyUnit.EUR) }

    override def convertToJdbc(v: Money): String = v.toString
  }

  implicit val optionJodaMoneyTEF = new TypedExpressionFactory[Option[Money], TOptionString]
    with DeOptionizer[String, Money, TString, Option[Money], TOptionString] {
    override def deOptionizer: TypedExpressionFactory[Money, TString] with JdbcMapper[String, Money] = jodaMoneyTEF
  }

  implicit def jodaMoneyToTE(m: Money) : TypedExpression[Money, TString] = jodaMoneyTEF.create(m)

  implicit def optionJodaMoneyToTE(m: Option[Money]): TypedExpression[Option[Money], TOptionString] = optionJodaMoneyTEF.create(m)

}
