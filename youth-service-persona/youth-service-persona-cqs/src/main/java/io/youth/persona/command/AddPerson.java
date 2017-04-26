package io.youth.persona.command;

import com.google.inject.Inject;
import io.youth.persona.sql.tables.records.PersonRecord;
import org.jooq.DSLContext;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Map;
import java.util.function.Function;

import static io.youth.persona.sql.tables.Person.PERSON;

public final class AddPerson implements Function<Map<String, Object>, PersonRecord> {
  private final DSLContext context;

  @Inject
  public AddPerson(DSLContext context) {
    this.context = context;
  }

  @Override
  public PersonRecord apply(Map<String, Object> params) {
    final PersonRecord person = this.context.newRecord(PERSON);
    person.firstName(params.get("firstName").toString());
    person.firstFamilyName(params.get("firstFamilyName").toString());
    person.birthDate(Date.valueOf(LocalDate.parse(params.get("birthDate").toString())));
    person.fiscalCode(params.get("fiscalCode").toString());
    person.store();
    person.refresh();

    return person;
  }
}
