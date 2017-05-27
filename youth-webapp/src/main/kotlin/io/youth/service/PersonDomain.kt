package io.youth.service

import com.github.davidmoten.rx.jdbc.Database
import com.github.davidmoten.rx.jdbc.QuerySelect
import com.github.davidmoten.rx.jdbc.QueryUpdate

class Uuid {
  fun asString(): String = java.util.UUID.randomUUID().toString()
}

private val create = """
  create table if not exists person (
    id int(11) not null auto_increment,
    uuid varchar(36) not null,
    firstName varchar(127) not null,
    secondName varchar(127),
    lastName varchar(255) not null,
    liked int(1) not null default 0,
    primary key(id)
  )
"""
private val projection = "uuid, firstName, secondName, lastName, liked"
private val all = "select $projection from person p"
private val one = "select $projection from person p where p.uuid = :uuid"
private val add = "insert into person($projection) values(:uuid, :firstName, :secondName, :lastName, :liked)"
private val edit = "update person set firstName = :firstName, secondName = :secondName, lastName = :lastName where uuid = :uuid"
private val editLiked = "update person set liked = :liked where uuid = :uuid"
private val remove = "delete from person where uuid = :uuid"

private fun QuerySelect.Builder.byUuid(value: String) = this.parameter("uuid", value)
private fun QuerySelect.Builder.asPerson() = this.autoMap(Person::class.java)

private fun QueryUpdate.Builder.uuid(value: String) = this.parameter("uuid", value)
private fun QueryUpdate.Builder.firstName(value: String) = this.parameter("firstName", value)
private fun QueryUpdate.Builder.secondName(value: String?) = this.parameter("secondName", value)
private fun QueryUpdate.Builder.lastName(value: String) = this.parameter("lastName", value)
private fun QueryUpdate.Builder.liked(value: Int) = this.parameter("liked", value)

fun Database.persons() = Persons(this)

data class Person(
  val uuid: String,
  val firstName: String,
  val secondName: String?,
  val lastName: String,
  val liked: Int
)

class Persons(val db: Database) {
  fun create(): Int = db
    .update(create)
    .count()
    .toBlocking()
    .single()

  fun all(): Iterable<Person> = db
    .select(all)
    .asPerson()
    .asObservable()
    .toList()
    .toBlocking()
    .toIterable()
    .first()

  fun one(uuid: String): Person? = db
    .select(one)
    .byUuid(uuid)
    .asPerson()
    .toBlocking()
    .single()

  fun add(firstName: String, secondName: String? = null, lastName: String): Person? = Uuid().asString().let {
    db
      .select(one)
      .byUuid(it)
      .dependsOn(
        db.update(add)
          .uuid(it).firstName(firstName).secondName(secondName).lastName(lastName).liked(0)
          .count())
      .asPerson()
      .toBlocking()
      .single()
  }

  fun editLiked(uuid: String, liked: Int): Int = db
    .update(editLiked)
    .uuid(uuid).liked(liked)
    .count()
    .toBlocking()
    .single()

  fun edit(uuid: String, firstName: String, secondName: String? = null, lastName: String): Person? = db
    .select(one)
    .byUuid(uuid)
    .dependsOn(
      db.update(edit)
        .uuid(uuid).firstName(firstName).secondName(secondName).lastName(lastName)
        .count())
    .asPerson()
    .toBlocking()
    .single()

  fun remove(uuid: String): Int = db
    .update(remove)
    .uuid(uuid)
    .count()
    .toBlocking()
    .singleOrDefault(0)
}
