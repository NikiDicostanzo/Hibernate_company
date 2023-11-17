package com.caribu.filiale.data;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
class TestcontainersTest {

  @Container
  PostgreSQLContainer container = new PostgreSQLContainer("postgres:13.1-alpine")
    .withDatabaseName("hib")
    .withUsername("postgres")
    .withPassword("secret");

  @Test
  void testContainersIsRunningTest(){
    Assertions.assertTrue(container.isCreated());
    Assertions.assertTrue(container.isRunning());
    System.out.println("grfgs");
  }
}
