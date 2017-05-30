package io.youth.service.person

import com.github.davidmoten.rx.jdbc.Database
import com.google.inject.Inject

data class ContactPerson(
  val uuid: String,
  val fullName: String,
  val picture: String,
  val email: String,
  val gender: Gender
)

internal val queryAll = "select $fields from person"
internal val queryOne = "$queryAll where uuid = ?"

class PersonQuery @Inject constructor(private val db: Database) {
  fun all(): Iterable<ContactPerson> = db
    .select(queryAll)
    .asPerson()
    .asObservable()
    .map {
      ContactPerson(
        uuid = it.uuid,
        fullName = it.firstName + ' ' + (it.secondName ?: "") + ' ' + it.lastName,
        picture = "/static/someone.jpg",
        email = "luke.skywalker@rebels.com",
        gender = it.gender
      )
    }
    .toList()
    .toBlocking()
    .toIterable()
    .first()

  fun one(uuid: String): Person? = db
    .select(queryOne)
    .parameter(uuid)
    .asPerson()
    .toBlocking()
    .single()
}
