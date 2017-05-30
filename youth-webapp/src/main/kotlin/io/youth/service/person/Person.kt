package io.youth.service.person

import com.github.davidmoten.rx.jdbc.QuerySelect
import org.jooby.Jooby
import java.io.InputStream

enum class Gender(val value:String) {
  male("male"),
  female("female"),
  beyond("beyond")
}

data class Person(
  val uuid: String,
  val firstName: String,
  val secondName: String?,
  val lastName: String,
  val gender: Gender
)

data class Contact(
  val picture: InputStream?,
  val email: String?,
  val mobilePhone: String,
  val homePhone: String?
)

internal fun QuerySelect.Builder.asPerson() = this.get { Person(
  uuid = it.getString("uuid"),
  firstName = it.getString("firstName"),
  secondName = it.getString("secondName"),
  lastName = it.getString("lastName"),
  gender = Gender.valueOf(it.getString("gender"))
) }

fun Jooby.personCommand() = this.require(PersonCommand::class.java)!!
fun Jooby.personQuery() = this.require(PersonQuery::class.java)!!

internal val fields = "uuid, firstName, secondName, lastName, gender"

