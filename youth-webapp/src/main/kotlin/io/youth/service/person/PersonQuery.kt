package io.youth.service.person

import com.github.davidmoten.rx.jdbc.Database
import io.youth.service.Injection

internal val personAll = "select $personFields from person"
internal val personOne = "$personAll where uuid = ?"



class PersonQuery(private val db: Database) {
  fun one(uuid: String): Person? = db
    .select(personOne)
    .parameter(uuid)
    .asPerson()
    .toBlocking()
    .single()
}

val personQuery: Injection = {
  it
    .bind(PersonQuery::class.java)
    .toConstructor(PersonQuery::class.java.getConstructor(Database::class.java))
}
