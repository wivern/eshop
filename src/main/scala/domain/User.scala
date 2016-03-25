package domain

import org.squeryl.KeyedEntity
import data.squeryl.MoneyTypes._

object User{
  import Catalogue._

  def findByEmailAndPassword(login: String, password: String) : Option[User] = {
    transaction(users.where(u => u.email === login and u.password === password).singleOption)
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
