package io.youth.persona.time;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

final class LocalDateStringImpl implements LocalDateString {
  private final DateTimeFormatter formatter;

  LocalDateStringImpl(DateTimeFormatter formatter) {
    this.formatter = formatter;
  }

  @Override
  public LocalDate from(String date) {
    return LocalDate.from(formatter.parse(date));
  }
}
