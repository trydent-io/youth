package io.youth.webapp

import com.github.davidmoten.rx.jdbc.Database
import io.youth.service.AddCompletePerson
import io.youth.service.AddPartialPerson
import io.youth.service.EditCompletePerson
import io.youth.service.EditLikedPerson
import io.youth.service.EditPartialPerson
import io.youth.service.persons
import javaslang.control.Try
import org.jooby.Jooby
import org.jooby.Request
import org.jooby.handlers.CorsHandler
import org.jooby.jdbc.Jdbc
import org.jooby.json.Gzon
import org.jooby.run
import org.jooby.rx.Rx
import org.jooby.rx.RxJdbc

private fun Jooby.db() = this.require(Database::class.java)

private fun Jooby.onStartedRun(f: () -> Unit) = this.onStarted(Try.CheckedRunnable { f() })

private fun Request.uuid() = this.param("uuid").value()
private fun Request.addCompletePerson() = this.body().to(AddCompletePerson::class.java)
private fun Request.editCompletePerson() = this.body().to(EditCompletePerson::class.java)
private fun Request.addPartialPerson() = this.body().to(AddPartialPerson::class.java)
private fun Request.editPartialPerson() = this.body().to(EditPartialPerson::class.java)
private fun Request.editLikedPerson() = this.body().to(EditLikedPerson::class.java)

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

      .get("/one/:uuid") { req -> db().persons().one(req.uuid()) }

      .post("/complete") { req ->
        req.addCompletePerson()?.let { (firstName, secondName, lastName) ->
          db().persons().add(
            firstName = firstName,
            secondName = secondName,
            lastName = lastName
          )
        }
      }

      .post("/partial") { req ->
        req.addPartialPerson()?.let { (firstName, lastName) ->
          db().persons().add(
            firstName = firstName,
            lastName = lastName
          )
        }
      }

      .put("/complete") { req ->
        req.editCompletePerson()?.let { (uuid, firstName, secondName, lastName) ->
          db().persons().edit(
            uuid = uuid,
            firstName = firstName,
            secondName = secondName,
            lastName = lastName
          )
        }
      }

      .put("/partial") { req ->
        req.editPartialPerson()?.let { (uuid, firstName, lastName) ->
          db().persons().edit(
            uuid = uuid,
            firstName = firstName,
            lastName = lastName
          )
        }
      }

      .put("/liked") { req ->
        req.editLikedPerson()?.let { (uuid, liked) -> db().persons().editLiked(uuid, liked) }
      }

      .delete("/:uuid") { req -> db().persons().remove(req.uuid()) }
  }
}

