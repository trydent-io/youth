package io.youth.service.person

import com.github.davidmoten.rx.jdbc.QuerySelect
import org.h2.util.MathUtils.randomInt
import org.jooby.Jooby
import java.io.InputStream
import java.util.UUID

enum class Gender(val value: String) {
  male("male"),
  female("female"),
  beyond("beyond")
}

class FiscalCode private constructor(val value: String) {
  companion object {
    private val regex: Regex = "[a-zA-Z]{6}\\d\\d[a-zA-Z]\\d\\d[a-zA-Z]\\d\\d\\d[a-zA-Z]".toRegex()

    fun of(v: String): FiscalCode? = regex
      .takeIf { it.matches(v) }
      .let { FiscalCode(v) }
  }
}

class BirthName private constructor(val value: String) {
  companion object {
    fun of(v: String?): BirthName? = v?.takeIf { it.length <= 65 }?.let { BirthName(it) }
  }
}

class FamilyName private constructor(val value: String) {
  companion object {
    fun of(v: String?): FamilyName? = v?.takeIf { it.length <= 255 }?.let { FamilyName(it) }
  }
}

fun asUuid(u: String): UUID = try {
  UUID.fromString(u)
} finally {
}

data class Person(
  val uuid: String,
  val firstName: String,
  val secondName: String?,
  val lastName: String,
  val fiscalCode: String,
  //  val birth: LocalDate,
  val gender: Gender
)

val f: FiscalCode = FiscalCode.of("hadoken")!!

data class Profile(
  val picture: InputStream?,
  val email: String?,
  val mobilePhone: String,
  val homePhone: String?
)

internal fun QuerySelect.Builder.asPerson() = this.get {
  Person(
    uuid = it.getString("uuid"),
    firstName = it.getString("firstName"),
    secondName = it.getString("secondName"),
    lastName = it.getString("lastName"),
    fiscalCode = it.getString("fiscalCode"),
    gender = Gender.valueOf(it.getString("gender"))
  )
}

internal fun QuerySelect.Builder.asProfilePerson() = this.get {
  ProfilePerson(
    uuid = it.getString("uuid"),
    fullName = it.getString("fullName").replace("  ", " "),
    picture = it.getString("picture") ?: "static/male/male_avatar${randomInt(20) + 1}.svg",
    email = it.getString("email") ?: "info.rebels@republic.ffa",
    mobilePhone = it.getString("mobilePhone")
  )
}

fun Jooby.personCommand() = this.require(PersonCommand::class.java)!!
fun Jooby.personQuery() = this.require(PersonQuery::class.java)!!

internal val personFields = "uuid, firstName, secondName, lastName, fiscalCode, gender"
internal val personParams = "?, ?, ?, ?, ?, ?"
internal val profilePersonFields = "uuid, fullName, fiscalCode, gender, picture, email, mobilephone"

