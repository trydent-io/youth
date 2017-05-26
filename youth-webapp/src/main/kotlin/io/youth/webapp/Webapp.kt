package io.youth.webapp

import com.github.davidmoten.rx.jdbc.Database
import io.youth.YouthException
import io.youth.service.AddCompletePerson
import io.youth.service.AddPartialPerson
import io.youth.service.EditCompletePerson
import io.youth.service.EditLikedPerson
import io.youth.service.EditPartialPerson
import io.youth.service.Persons
import javaslang.control.Try
import org.jooby.Jooby
import org.jooby.Request
import org.jooby.handlers.CorsHandler
import org.jooby.jdbc.Jdbc
import org.jooby.json.Gzon
import org.jooby.run
import org.jooby.rx.Rx
import org.jooby.rx.RxJdbc
import java.util.Optional

//fun DBI.persons(f: (p: Persons) -> Any): Any = this.inTransaction { handle, status -> f(handle.attach(Persons::class.java)) }

//fun Jooby.dbi(): DBI = this.require(DBI::class.java)
private fun Jooby.db(): Database = this.require(Database::class.java)

private fun Jooby.onStartedRun(f: () -> Unit): Jooby = this.onStarted(Try.CheckedRunnable { f() })

fun Database.persons(): Persons = Persons(this)

fun Request.uuid(): String = this.param("uuid").value()
fun Request.addCompletePerson(): Optional<AddCompletePerson> = this.body().toOptional(AddCompletePerson::class.java)
fun Request.editCompletePerson(): Optional<EditCompletePerson> = this.body().toOptional(EditCompletePerson::class.java)
fun Request.addPartialPerson(): Optional<AddPartialPerson> = this.body().toOptional(AddPartialPerson::class.java)
fun Request.editPartialPerson(): Optional<EditPartialPerson> = this.body().toOptional(EditPartialPerson::class.java)
fun Request.editLikedPerson(): Optional<EditLikedPerson> = this.body().toOptional(EditLikedPerson::class.java)

fun main(args: Array<String>) {
  run(*args) {
    use("*", CorsHandler())
    use(Gzon())
    use(Jdbc())
    use(Rx())
    use(RxJdbc())

    onStartedRun { db().persons().create() }

    use("/person")
      .get("/all") { -> db().persons().all() }

      .get("/one/:uuid") { req -> db().persons().one(req.uuid()).orElseThrow { YouthException() } }

      .post("/complete") { req ->
        req.addCompletePerson()
          .flatMap { (firstName, secondName, lastName) ->
            db().persons()
              .add(
                firstName = firstName,
                secondName = secondName,
                lastName = lastName
              )
          }
          .orElseThrow { YouthException() }
      }

      .post("/partial") { req ->
        req.addPartialPerson()
          .flatMap { (firstName, lastName) ->
            db().persons()
              .add(
                firstName = firstName,
                lastName = lastName
              )
          }
          .orElseThrow { YouthException() }
      }

      .put("/complete") { req ->
        req.editCompletePerson()
          .flatMap { (uuid, firstName, secondName, lastName) ->
            db().persons().edit(
              uuid = uuid,
              firstName = firstName,
              secondName = secondName,
              lastName = lastName
            )
          }
          .orElseThrow { YouthException() }
      }

      .put("/partial") { req ->
        req.editPartialPerson()
          .flatMap { (uuid, firstName, lastName) ->
            db().persons().edit(
              uuid = uuid,
              firstName = firstName,
              lastName = lastName
            )
          }
          .orElseThrow { YouthException() }
      }

      .put("/liked") { req ->
        req.editLikedPerson().map { (uuid, liked) -> db().persons().editLiked(uuid, liked) }
      }

      .delete("/:uuid") { req -> db().persons().remove(req.uuid()) }
  }
}

