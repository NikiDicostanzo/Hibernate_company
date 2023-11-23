package com.caribu.filiale;

import io.vertx.core.*;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.validation.ValidationHandler;

import org.hibernate.cfg.Configuration;
import org.hibernate.reactive.provider.ReactiveServiceRegistryBuilder;
import org.hibernate.reactive.stage.Stage;
import org.hibernate.service.ServiceRegistry;

import com.caribu.filiale.controller.OperatorController;
import com.caribu.filiale.model.Operator;
import com.caribu.filiale.model.OperatorDTO;
import com.caribu.filiale.service.OperatorServiceImpl;
import com.caribu.filiale.service.OperatorService;

import java.util.HashMap;
import java.util.Properties;

import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.validation.RequestParameters;

/* TODO 
 * Aggiungi: - Hazel
 *           - Controller (handler)
 *           - Service <-> Repository
*/
public class WebVerticle extends AbstractVerticle {

  private final OperatorService operatorService;
  private OperatorController operatorController;

  public WebVerticle(OperatorService operatorService) {
    this.operatorService = operatorService; 
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    HttpServer server = vertx.createHttpServer();
    Router router = Router.router(vertx);

  //  operatorService = new OperatorService();
    operatorController = new OperatorController(operatorService);

    router.route("/*").handler(BodyHandler.create());
    router.get("/operator/:id").handler(ctx -> operatorController.getOperatorById(ctx));
    router.post("/operator").handler(ctx -> operatorController.addOperator(ctx));

    JsonObject config = config();
    Integer port = config.getInteger("port");
    server.requestHandler(router).listen(port).onSuccess(result -> startPromise.complete())
        .onFailure(err -> startPromise.fail(err));
  }

  public static void main(String[] args) {
    // 1. Hibernate configuration
    Properties hibernateProps = new Properties();
    String url = "jdbc:postgresql://localhost:5432/hib";
    hibernateProps.put("hibernate.connection.url", url);
    hibernateProps.put("hibernate.connection.username", "postgres");
    hibernateProps.put("hibernate.connection.password", "secret");
    hibernateProps.put("javax.persistence.schema-generation.database.action", "create");
    hibernateProps.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL95Dialect");
    Configuration hibernateConfiguration = new Configuration();
    hibernateConfiguration.setProperties(hibernateProps);
    hibernateConfiguration.addAnnotatedClass(Operator.class);

    // 2. Session factroy
    ServiceRegistry serviceRegistry = new ReactiveServiceRegistryBuilder()
        .applySettings(hibernateConfiguration.getProperties()).build();
    Stage.SessionFactory sessionFactory = hibernateConfiguration
        .buildSessionFactory(serviceRegistry).unwrap(Stage.SessionFactory.class);

    // 3. Project repository
    OperatorService operatorService = new OperatorServiceImpl(sessionFactory);

    // 5. WebVerticle
    WebVerticle verticle = new WebVerticle(operatorService);

    DeploymentOptions options = new DeploymentOptions();
    JsonObject config = new JsonObject();
    config.put("port", 8888);
    options.setConfig(config);

    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(verticle, options).onFailure(res -> {
      System.out.println(res);
      System.out.println("ERROR");
    })
        .onSuccess(res -> {
          System.out.println(res);
          System.out.println("Application is up and running");
        });
  }

}
