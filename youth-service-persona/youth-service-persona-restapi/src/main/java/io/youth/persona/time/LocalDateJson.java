package io.youth.persona.time;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static io.youth.persona.time.LocalDateFormat.*;

public interface LocalDateJson extends JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {
  static LocalDateJson defaultPattern() {
    return new LocalDateJsonImpl(DateTimeFormatter.ofPattern(DEFAULT.pattern()));
  }

  static LocalDateJson of(LocalDateFormat format) {
    return new LocalDateJsonImpl(DateTimeFormatter.ofPattern(format.pattern()));
  }

  static LocalDateJson of(String pattern) {
    return new LocalDateJsonImpl(DateTimeFormatter.ofPattern(pattern));
  }
}
