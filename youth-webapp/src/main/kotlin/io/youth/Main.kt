package io.youth

import io.youth.service.person.AddPerson
import io.youth.service.person.EditPerson
import io.youth.service.person.RemovePerson
import io.youth.service.person.personCommand
import io.youth.service.person.personQuery
import org.jooby.Request
import org.jooby.Results
import org.jooby.Status.NOT_FOUND
import org.jooby.Status.UNPROCESSABLE_ENTITY
import org.jooby.flyway.Flywaydb
import org.jooby.handlers.CorsHandler
import org.jooby.json.Gzon
import org.jooby.run
import org.jooby.rx.Rx
import org.jooby.rx.RxJdbc

private fun Request.uuid() = this.param("uuid").value()
private fun Request.addPerson() = this.body().to(AddPerson::class.java)
private fun Request.editPerson() = this.body().to(EditPerson::class.java)

fun main(args: Array<String>) {
  run(*args) {
    use("*", CorsHandler())
    use(Gzon())
    use(Flywaydb())
    use(Rx())
    use(RxJdbc())

    use("/person")
      .get("/fetch/all") { -> personQuery().all() }

      .get("/:uuid") { req -> personQuery().one(req.uuid()) ?: Results.with(NOT_FOUND) }

      .post { req -> personCommand().apply(req.addPerson()) ?: Results.with(UNPROCESSABLE_ENTITY) }

      .put { req -> personCommand().apply(req.editPerson()) ?: Results.with(UNPROCESSABLE_ENTITY) }

      .delete("/:uuid") { req -> personCommand().apply(RemovePerson(req.uuid())) ?: Results.with(UNPROCESSABLE_ENTITY) }
  }
}

