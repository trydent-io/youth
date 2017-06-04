package io.youth.service.person

import com.github.davidmoten.rx.jdbc.Database
import org.jooby.Jooby
import java.util.Objects.isNull
import java.util.concurrent.atomic.AtomicReference

data class ProfilePerson(
  val uuid: String,
  val fullName: String,
  val picture: String?,
  val email: String?,
  val mobilePhone: String?
)

internal val profilePersonAll = "select * from profile_person"

fun Jooby.profilePersons() = this.require(ProfilePersons::class.java)!!

class ProfilePersons(private val db: Database) : Iterable<ProfilePerson> {
  override fun iterator(): Iterator<ProfilePerson> = db
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
    .iterator()
}

class CachedProfilePersons(private val pps: ProfilePersons) : Iterable<ProfilePerson> {
  private val cache: AtomicReference<ProfilePersons> = AtomicReference()

  override fun iterator(): Iterator<ProfilePerson> = (
    if (isNull(cache.get())) cache.updateAndGet { pps } else cache.get())
    .iterator()
}
