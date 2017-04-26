package io.youth.persona.time;

public enum LocalDateFormat {
  DEFAULT("yyyy-MM-dd");

  private final String pattern;

  LocalDateFormat(String pattern) {
    this.pattern = pattern;
  }

  public String pattern() { return this.pattern; }
}
