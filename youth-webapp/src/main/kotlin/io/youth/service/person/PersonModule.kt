package io.youth.service.person

import com.github.davidmoten.rx.jdbc.Database
import com.google.inject.Binder
import com.typesafe.config.Config
import org.jooby.Env
import org.jooby.Jooby

class PersonModule: Jooby.Module {
  override fun configure(e: Env?, c: Config?, b: Binder?) {
    b?.let {
      it.bind(PersonQuery::class.java).toConstructor(PersonQuery::class.java.getConstructor(Database::class.java))

      it.bind(PersonCommand::class.java).toConstructor(PersonCommand::class.java.getConstructor(Database::class.java))

      it.bind(ProfilePersons::class.java).toConstructor(ProfilePersons::class.java.getConstructor(Database::class.java))
      it.bind(CachedProfilePersons::class.java).toConstructor(CachedProfilePersons::class.java.getConstructor(ProfilePersons::class.java))
    }
  }
}
