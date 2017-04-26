package io.youth.persona.webapp.endpoint;

import io.youth.persona.PersonaWebappMain;
import org.jooby.test.JoobyRule;
import org.junit.ClassRule;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.http.ContentType.HTML;
import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.Matchers.notNullValue;

public class AddPersonPostTest {
  @ClassRule
  public static JoobyRule rule = new JoobyRule(new PersonaWebappMain());

  @Test
  public void postAddPersonThenNotNull() {
    given()
      .expect()
      .statusCode(400)
      .when()
      .post("/command/addPerson");
  }

  @Test
  public void postAddPersonWithMandatoryFieldsThenNotNull() {
    given()
      .parameters(
        "firstName", "Luca",
        "firstFamilyName", "Guadagnini",
        "birthDate", "1981-04-26",
        "fiscalCode", "GDGLCU81D26L378B"
      )
      .expect()
      .body(notNullValue())
      .when()
      .post("/command/addPerson");
  }

  @Test
  public void postAddPersonWithMandatoryFieldsThenUUID() {
    given()
      .parameters(
        "firstName", "Luca2326",
        "firstFamilyName", "Guadagnini",
        "birthDate", "1981-04-26",
        "fiscalCode", "GDGLCU81D26L378B"
      )
      .expect()
      .contentType(HTML)
      .body(matchesPattern("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}"))
      .when()
      .post("/command/addPerson");
  }
}
