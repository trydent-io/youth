package io.youth.persona.sql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/// integration test for flyway
public class PersonTableTest {
  private static final Logger log = LoggerFactory.getLogger(PersonTableTest.class);

  private static final String URL = "jdbc:h2:mem:test;mode=MYSQL;init=create schema if not exists persona\\;set schema persona;";
  private static final String USERNAME = "root";
  private static final String PASSWORD = EMPTY;

  private Connection connection;

  @Before
  public void before() throws SQLException {
    final HikariConfig config = new HikariConfig();
    config.setJdbcUrl(URL);
    config.setUsername(USERNAME);
    config.setPassword(PASSWORD);

    final HikariDataSource dataSource = new HikariDataSource(config);
    connection = dataSource.getConnection();
    final Flyway flyway = new Flyway();
    flyway.setDataSource(dataSource);
    flyway.migrate();
  }

  @After
  public void after() throws SQLException {
    connection.close();
  }

  @Test
  public void existsTable() throws SQLException {
    final Statement statement = connection.createStatement();
    final boolean executed = statement.execute("select exists(select `uuid` from `persona`.`person` limit 1)");

    assertTrue(executed);
  }

  @Test
  public void insertPersonThenId() throws SQLException {
    final PreparedStatement statement = connection.prepareStatement("insert into person(uuid, first_name, first_family_name, fiscal_code, birth_date) values(uuid(), ?, ?, ?, ?);");
    statement.setString(1, "Luke");
    statement.setString(2, "Skywalker");
    statement.setString(3, "SKYLKU80M10H501I");
    statement.setDate(4, Date.valueOf(LocalDate.of(1977, 8, 10)));
    final int id = statement.executeUpdate();

    assertEquals(1, id);
  }

  @Test
  public void selectPersonThenUuidLength() throws SQLException {
    final PreparedStatement statement = connection.prepareStatement("select `uuid` from person limit 1");
    final ResultSet results = statement.executeQuery();

    assertTrue(results.next());
    assertEquals(36, results.getString(1).length());
  }

  @Test
  public void deletePersonThenAffecteds() throws SQLException {
    final PreparedStatement statement = connection.prepareStatement("delete from person where id = 1");
    final int none = statement.executeUpdate();

    assertEquals(0, none);
  }
}
