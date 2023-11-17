package com.caribu.filiale.data;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.hibernate.cfg.Configuration;
import org.hibernate.reactive.provider.ReactiveServiceRegistryBuilder;
import org.hibernate.reactive.stage.Stage;
import org.hibernate.service.ServiceRegistry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.caribu.filiale.model.Operator;

import java.time.LocalDateTime;
import java.util.Properties;
import java.util.concurrent.CompletionStage;

@ExtendWith(VertxExtension.class)
class HibernateConfigurationTest {

  @Test
  void initializeHibernateWithCodeTest(Vertx vertx, VertxTestContext context){
    // 1. Create Properties with config data
    System.out.println("QUI");
    // 1. Create Properties with config data
    Properties hibernateProps = new Properties();

    // url
    hibernateProps.put("hibernate.connection.url", "jdbc:postgresql://localhost:5432/hib");
    // credentials
    hibernateProps.put("hibernate.connection.username", "postgres");
    hibernateProps.put("hibernate.connection.password", "secret");

    // schema generation
    hibernateProps.put("javax.persistence.schema-generation.database.action", "create");

    // dialect *
    hibernateProps.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL95Dialect");

    // 2. Create Hibernate Configuration
    Configuration hibernateConfiguration = new Configuration();
    hibernateConfiguration.setProperties(hibernateProps);
    hibernateConfiguration.addAnnotatedClass(Operator.class);

    // 3. Create ServiceRegistry
    ServiceRegistry serviceRegistry = new ReactiveServiceRegistryBuilder()
        .applySettings(hibernateConfiguration.getProperties()).build();

    // 4. Create SessionFactory
    Stage.SessionFactory sessionFactory = hibernateConfiguration
        .buildSessionFactory(serviceRegistry).unwrap(Stage.SessionFactory.class);

    // Do something with db
    Operator task = new Operator();
      task.setName("Hello this is new task");
    //task.setSurname("ROssi");
  //  task.setUserId(2);
    //task.setDate("sdfkspd");

    System.out.println("Task ID before insertion is: " + task.getId());

    CompletionStage<Void> insertionResult = sessionFactory.withTransaction((s, t) -> s.persist(task));

    Future<Void> future = Future.fromCompletionStage(insertionResult);
    context.verify(() -> future.onFailure(err -> context.failNow(err)).onSuccess(r -> {
      System.out.println("Task ID after insertion i1s: " + task.getId());
      context.completeNow();
    }));

  }
}
