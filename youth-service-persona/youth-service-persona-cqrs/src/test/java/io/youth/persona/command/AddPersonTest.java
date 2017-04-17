package io.youth.persona.command;

import io.youth.persona.sql.tables.records.PersonRecord;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static io.youth.persona.sql.tables.Person.PERSON;

public class AddPersonTest {
  private static final String userName = "root";
  private static final String password = "piramide";
  private static final String url = "jdbc:mysql://localhost:3306/persona?useSSL=false&serverTimezone=Europe/Rome";

  private Connection connection;
  private DSLContext context;
  private Map<String, Object> person;

  @Before
  public void before() throws SQLException {
    this.connection = DriverManager.getConnection(url, userName, password);
    this.context = DSL.using(connection, SQLDialect.MYSQL);
    this.person = new HashMap<String, Object>() {{
      put("firstName", "Luca");
      put("firstFamilyName", "Guadagnini");
      put("birthDate", LocalDate.of(1981, 4, 26));
      put("fiscalCode", "GDGLCU81D26L378B");
    }};

  }

  @After
  public void after() throws SQLException {
    this.context.deleteFrom(PERSON).execute();
    this.connection.close();
  }

  @Test
  public void addPersonWithMandatoryFields() {
    final AddPerson addPerson = new AddPerson(context);
    final PersonRecord record = addPerson.apply(person);

    Assert.assertNotNull(record);
  }

  @Test(expected = DataAccessException.class)
  public void addDuplicatePersonThenException() {
    final AddPerson addPerson = new AddPerson(context);
    addPerson.apply(person);
    addPerson.apply(person);
  }

  @Test
  public void addPersonWithSecondaryFields() {
    final AddPerson addPerson = new AddPerson(context);
    person.put("secondName", "Marco");
    person.put("secondFamilyName", "Ruggera");

    final PersonRecord record = addPerson.apply(person);

    Assert.assertNotNull(record);
  }

  @Test
  public void addPersonThenValidUUID() {
    final AddPerson addPerson = new AddPerson(context);
    final PersonRecord record = addPerson.apply(person);

    Assert.assertNotNull(record.uuid());
    Assert.assertEquals(36, record.uuid().length());
  }
}
