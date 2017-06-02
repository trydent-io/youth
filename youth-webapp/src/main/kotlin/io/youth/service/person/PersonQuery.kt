package io.youth.service.person

import com.github.davidmoten.rx.jdbc.Database
import com.google.inject.Inject

internal val profilePersonAll = "select * from profile_person"
internal val personAll = "select $personFields from person"
internal val personOne = "$personAll where uuid = ?"

class PersonQuery @Inject constructor(private val db: Database) {
  fun all(): Iterable<ProfilePerson> = db
    .select(profilePersonAll)
    .asProfilePerson()
    .asObservable()
    .map {
      Thread.sleep(100)
      it
    }
    .toList()
    .toBlocking()
    .toIterable()
    .first()

  fun one(uuid: String): Person? = db
    .select(personOne)
    .parameter(uuid)
    .asPerson()
    .toBlocking()
    .single()

  fun countProfiles(): Int = all().count()
}
