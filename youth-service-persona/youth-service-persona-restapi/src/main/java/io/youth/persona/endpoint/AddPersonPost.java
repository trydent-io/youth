package io.youth.persona.endpoint;

import com.google.inject.Inject;
import io.youth.persona.command.AddPerson;
import javaslang.Function4;
import org.jooby.mvc.Consumes;
import org.jooby.mvc.POST;
import org.jooby.mvc.Path;
import org.jooby.mvc.Produces;

import java.time.LocalDate;

@Path("/command/addPerson")
@Consumes("application/x-www-form-urlencoded")
@Produces("application/json")
public final class AddPersonPost implements Function4<String, String, LocalDate, String, Object> {
  private final AddPerson addPerson;

  @Inject
  public AddPersonPost(AddPerson addPerson) {
    this.addPerson = addPerson;
  }

  @POST
  @Override
  public Object apply(String firstName, String firstFamilyName, LocalDate birthDate, String fiscalCode) {
    return "";
  }
}
