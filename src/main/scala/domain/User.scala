package domain

import org.squeryl.KeyedEntity
import org.squeryl.PrimitiveTypeMode._

object User{
  import Catalogue._

  def findByEmailAndPassword(login: String, password: String) : Option[User] = {
    val result = transaction(users.where(u => u.email === login and u.password === password).toList)
    result.size match {
      case 0 => Option(null)
      case _ => Option(result.head)
    }
  }

  def findByEmail(email: String) = transaction(users.where(u => u.email === email).single)
}

/**
 * @author VKoulakov
 * @since 18.03.2016 14:00
 */
case class User(id: Long,
                name: String,
                email: String,
                password: String) extends KeyedEntity[Long]{
}
