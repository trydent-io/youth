package io.youth.persona.time;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static io.youth.persona.time.LocalDateFormat.DEFAULT;

public interface LocalDateString {
  static LocalDateString defaultPattern() {
    return new LocalDateStringImpl(DateTimeFormatter.ofPattern(DEFAULT.pattern()));
  }

  LocalDate from(String date);
}
