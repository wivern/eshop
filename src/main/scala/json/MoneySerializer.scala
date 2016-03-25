package json

import org.joda.money.{CurrencyUnit, Money}
import org.json4s.JsonAST.{JField, JObject}
import org.json4s._

/**
 * @author VKoulakov
 * @since 25.03.2016 17:16
 */
class MoneySerializer extends CustomSerializer[Money](format => ( {
  case x: JObject => x.obj.sortBy { case (k, _) => k } match {
    case JField("amount", JDouble(a)) :: JField("currency", JString(c)) :: Nil =>
      Money.of(CurrencyUnit.of(c), a)
    }
  } , {
  case x: Money =>
    JObject(JField("amount", JDouble(x.getAmount.doubleValue)), JField("currency", JString(x.getCurrencyUnit.getCode)))
  }
))