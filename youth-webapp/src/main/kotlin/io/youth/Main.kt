package io.youth

import io.youth.service.person.AddPerson
import io.youth.service.person.EditPerson
import io.youth.service.person.PersonModule
import io.youth.service.person.RemovePerson
import io.youth.service.person.personCommand
import io.youth.service.person.personQuery
import io.youth.service.person.profilePersons
import org.jooby.Err
import org.jooby.MediaType.json
import org.jooby.Request
import org.jooby.Results
import org.jooby.Route
import org.jooby.Status
import org.jooby.Status.NOT_FOUND
import org.jooby.Status.UNPROCESSABLE_ENTITY
import org.jooby.flyway.Flywaydb
import org.jooby.handlers.CorsHandler
import org.jooby.json.Gzon
import org.jooby.pac4j.Auth
import org.jooby.pac4j.AuthStore
import org.jooby.require
import org.jooby.run
import org.jooby.rx.Rx
import org.jooby.rx.RxJdbc
import org.pac4j.core.profile.CommonProfile
import org.pac4j.http.client.direct.ParameterClient
import org.pac4j.jwt.config.signature.SecretSignatureConfiguration
import org.pac4j.jwt.config.signature.SignatureConfiguration
import org.pac4j.jwt.credentials.authenticator.JwtAuthenticator
import org.pac4j.oauth.client.FacebookClient
import org.pac4j.oidc.client.OidcClient
import org.pac4j.oidc.config.OidcConfiguration
import org.pac4j.oidc.profile.OidcProfile
import java.util.Objects.isNull


private fun Request.uuid() = this.param("uuid").value()
private fun Request.addPerson() = this.body().to(AddPerson::class.java)
private fun Request.editPerson() = this.body().to(EditPerson::class.java)

val getUserProfile: (req: Request) -> CommonProfile = {
  val id = it.ifGet<String>(Auth.ID).orElseGet { it.session().get(Auth.ID).value(null) }
  if (isNull(id)) throw Err(Status.UNAUTHORIZED)
  val store = it.require(AuthStore::class)

  store.get(id).get()
}

data class OpenIDProfile(val client: String, val profile: CommonProfile)
data class Token(val token: String)

val handler = Route.OneArgHandler {
  val profile = getUserProfile(it)

  OpenIDProfile(
    client = profile::class.java.simpleName.replace("Profile", ""),
    profile = profile
  )
}

class GoogleClient(conf: OidcConfiguration) : OidcClient<OidcProfile>(conf)

fun main(args: Array<String>) {
  run(*args) {
    use("*", CorsHandler())
    use(Gzon())
    use(Flywaydb())
    use(Rx())
    use(RxJdbc())

    use(PersonModule())

//    get("*") { req, _ -> req.session().get(Auth.ID).toOptional().ifPresent { req.set("logged", it) } }

/*    get("/token") { req ->
      val profile = getUserProfile(req)
      val config = req.require(Config::class.java)
      val sign = SecretSignatureConfiguration(config.getString("jwt.salt"))
      val jwtGenerator = JwtGenerator<CommonProfile>(sign)
      val token = jwtGenerator.generate(profile)

      Token(token)
    }*/

    assets("/", "index.html")
    assets("/static/**")

    use(Auth()
      .client("/auth/facebook/**") {
        FacebookClient(it.getString("fb.key"), it.getString("fb.secret")).apply {
          scope = "public_profile"
        }
      }
      .client("/auth/google/**") {
        OidcConfiguration().apply {
          clientId = it.getString("oidc.clientID")
          secret = it.getString("oidc.secret")
          discoveryURI = it.getString("oidc.discoveryURI")
          addCustomParam("prompt", "consent")
        }.let {
          GoogleClient(it)
        }
      }
      .client("/auth/jwt/**") { conf ->
        SecretSignatureConfiguration(conf.getString("jwt.salt"))
          .let { arrayOf<SignatureConfiguration>(it).toMutableList() }
          .let { JwtAuthenticator(it) }
          .let {
            ParameterClient("token", it).apply {
              isSupportGetRequest = true
              isSupportPostRequest = false
            }
          }
      }
    )

    get("/auth/profile", handler)

    get("/auth/google", handler)

    get("/auth/facebook", handler)

    get("/auth/jwt", handler)

    get("/auth/token", handler)


    use("/api/person")
      .get { -> "Person API by using HATEOAS is on the way" }

      .get("/fetch/all") { -> profilePersons().toList() }

      .get("/fetch/count") { -> profilePersons().count() }

      .get("/:uuid") { req -> personQuery().one(req.uuid()) ?: Results.with(NOT_FOUND) }

      .post { req -> personCommand().apply(req.addPerson()) ?: Results.with(UNPROCESSABLE_ENTITY) }

      .put { req -> personCommand().apply(req.editPerson()) ?: Results.with(UNPROCESSABLE_ENTITY) }

      .delete("/:uuid") { req -> personCommand().apply(RemovePerson(req.uuid())) ?: Results.with(UNPROCESSABLE_ENTITY) }

      .consumes(json)
      .produces(json)
  }
}

