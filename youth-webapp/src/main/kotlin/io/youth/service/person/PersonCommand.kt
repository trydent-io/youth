package io.youth.service.person

import com.github.davidmoten.rx.jdbc.Database
import com.google.inject.Inject
import io.youth.bus.Command
import io.youth.service.newUuid
import java.util.function.Function

data class AddPerson(
  val firstName: String,
  val secondName: String?,
  val lastName: String,
  val fiscalCode: String,
  val gender: String
) : Command()

data class EditPerson(
  val uuid: String,
  val firstName: String,
  val secondName: String?,
  val lastName: String,
  val fiscalCode: String,
  val gender: String
) : Command()

data class RemovePerson(val uuid: String) : Command()

internal val adding = "insert into person($fields) values(?, ?, ?, ?, ?)"
internal val editing = "update person set firstName = ?, secondName = ?, lastName = ?, gender = ? where uuid = ?"
internal val removing = "delete from person where uuid = ?"

class PersonCommand @Inject constructor(private val db: Database) : Function<Command, Person?> {
  private val add: (a: AddPerson) -> Person? = {
    it.let { (firstName, secondName, lastName, gender) ->
      newUuid().let { uuid ->
        db
          .select(queryOne)
          .parameter(uuid)
          .dependsOn(
            db.update(adding)
              .parameter(uuid)
              .parameter(firstName)
              .parameter(secondName)
              .parameter(lastName)
              .parameter(gender)
              .count())
          .asPerson()
          .toBlocking()
          .single()
      }
    }
  }

  private val edit: (e: EditPerson) -> Person? = {
    it.let { (uuid, firstName, secondName, lastName, gender) ->
      db
        .select(queryOne)
        .parameter(uuid)
        .dependsOn(
          db.update(editing)
            .parameter(firstName)
            .parameter(secondName)
            .parameter(lastName)
            .parameter(gender)
            .parameter(uuid)
            .count())
        .asPerson()
        .toBlocking()
        .single()
    }
  }

  private val remove: (r: RemovePerson) -> Person? = {
    it.let { (uuid) ->
      /*db
        .update(removing)
        .parameter(uuid)
        .count()
        .toBlocking()
        .single()*/
      db
        .select(queryOne)
        .parameter(uuid)
        .asPerson()
        .toBlocking()
        .single()
    }
  }

  override fun apply(command: Command): Person? = when(command) {
    is AddPerson -> add(command)
    is EditPerson -> edit(command)
    is RemovePerson -> remove(command)
    else -> throw RuntimeException()
  }
}
