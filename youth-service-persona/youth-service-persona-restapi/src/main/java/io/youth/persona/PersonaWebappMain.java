package io.youth.persona;

import io.youth.persona.command.AddPerson;
import io.youth.persona.sql.tables.records.PersonRecord;
import io.youth.persona.time.LocalDateJson;
import io.youth.persona.time.LocalDateString;
import org.jooby.Jooby;
import org.jooby.MediaType;
import org.jooby.flyway.Flywaydb;
import org.jooby.jooq.jOOQ;
import org.jooby.json.Gzon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.HashMap;

public final class PersonaWebappMain extends Jooby {
  private static final Logger log = LoggerFactory.getLogger(PersonaWebappMain.class);

  {
    use(new Flywaydb());
    use(new jOOQ());
    use(new Gzon().doWith((builder, config) ->
      builder.registerTypeAdapter(LocalDate.class, LocalDateJson.defaultPattern())
    ));

//    get("/command/addPerson", AddPersonEndpoint.class);
//    use(AddPersonPost.class);
    post("/command/addPerson", req -> {
      final String firstName = req.param("firstName").to(String.class);
      final String firstFamiltyName = req.param("firstFamilyName").to(String.class);
      final String birthDateString = req.param("birthDate").to(String.class);
      final LocalDate birthDate = LocalDateString.defaultPattern().from(birthDateString);
      final String fiscalCode = req.param("fiscalCode").to(String.class);

      final AddPerson addPerson = require(AddPerson.class);
      final PersonRecord record = addPerson.apply(new HashMap<String, Object>() {{
        put("firstName", firstName);
        put("firstFamilyName", firstFamiltyName);
        put("birthDate", birthDate);
        put("fiscalCode", fiscalCode);
      }});

      return record.uuid();
    })
      .consumes(MediaType.json, MediaType.form)
      .produces(MediaType.text);
  }

  public static void main(String... args) {
    run(PersonaWebappMain::new, args);
  }
}
