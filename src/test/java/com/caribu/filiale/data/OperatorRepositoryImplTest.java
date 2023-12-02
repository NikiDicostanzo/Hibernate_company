package com.caribu.filiale.data;

import java.util.Properties;

import org.hibernate.cfg.Configuration;
import org.hibernate.reactive.provider.ReactiveServiceRegistryBuilder;
import org.hibernate.reactive.stage.Stage;
import org.hibernate.service.ServiceRegistry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.caribu.filiale.model.Operator;
import com.caribu.filiale.model.OperatorDTO;
import com.caribu.filiale.service.FilialeServiceImpl;

import io.vertx.core.Vertx;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;

@Testcontainers
@ExtendWith(VertxExtension.class)
class OperatorRepositoryImplTest {

  private final String DB_NAME = "hib";
  private final String DB_USER = "postgres";
  private final String DB_PASSWORD = "secret";

  @Container
  PostgreSQLContainer container = new PostgreSQLContainer("postgres:13-alpine")
      .withDatabaseName(DB_NAME)
      .withUsername(DB_USER)
      .withPassword(DB_PASSWORD);

  FilialeServiceImpl repository;

  @BeforeEach
  void setup(Vertx vertx, VertxTestContext context) {
    Properties hibernateProps = new Properties();
    String url = "jdbc:postgresql://localhost:" + Integer.toString(container.getFirstMappedPort()) + "/" + DB_NAME;
    hibernateProps.put("hibernate.connection.url", url);
    hibernateProps.put("hibernate.connection.username", DB_USER);
    hibernateProps.put("hibernate.connection.password", DB_PASSWORD);
    hibernateProps.put("javax.persistence.schema-generation.database.action", "create");
    hibernateProps.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL95Dialect");
    hibernateProps.put("hibernate.generate_statistics", true);
    // hibernateProps.put("hibernate.show_sql", true);
    // hibernateProps.put("hibernate.format_sql", true);
    Configuration hibernateConfiguration = new Configuration();
    hibernateConfiguration.setProperties(hibernateProps);
    hibernateConfiguration.addAnnotatedClass(Operator.class);
    ServiceRegistry serviceRegistry = new ReactiveServiceRegistryBuilder()
        .applySettings(hibernateConfiguration.getProperties()).build();
    Stage.SessionFactory sessionFactory = hibernateConfiguration
        .buildSessionFactory(serviceRegistry).unwrap(Stage.SessionFactory.class);
    repository = new FilialeServiceImpl(sessionFactory);
    context.completeNow();
  }

  @Test
  void createTaskTest(Vertx vertx, VertxTestContext context) {
    OperatorDTO operatorDTO = new OperatorDTO(null, 12314, "Pippo", "Rossi");
    context.verify(() -> {
      repository.addOperator(operatorDTO)
          .onFailure(err -> context.failNow(err))
          .onSuccess(result -> {
            Assertions.assertNotNull(result);
            Assertions.assertNotNull(result.getId());
            Assertions.assertEquals(1, result.getId());
            System.out.print("OOOK");
            context.completeNow();
          });
    });
  }
}
