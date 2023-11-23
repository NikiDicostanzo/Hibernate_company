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

  public WebVerticle(OperatorService operatorService) {
    this.operatorService = operatorService; //TODO SERVICE
  }
  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    HttpServer server = vertx.createHttpServer();
    Router router = Router.router(vertx);

    router.route("/*").handler(BodyHandler.create());
   
    router.get("/operator/:id").handler(context -> {
      Integer id = Integer.valueOf(context.pathParam("id"));
      operatorService.findOperatorById(id) 
          .onSuccess(result -> {
          System.out.println("GET");
          if (result.isPresent()) {
            JsonObject body = JsonObject.mapFrom(result.get());
            context.response().setStatusCode(200).end(body.encode());
          } else {
            context.response().setStatusCode(404).end();
          }
        })
        .onFailure(err -> context.response().setStatusCode(500).end());
    });

          //          context.body().asJsonObject()!!!!
    router.post("/operator").handler(context -> {
      // RequestParameters params = context.get("id");//pathParam("accountId");//context.get(ValidationHandler.REQUEST_CONTEXT_KEY); // (1)
      // JsonObject json = params.body().getJsonObject(); // (2)

      JsonObject json = context.body().asJsonObject();

      //"userid":"132123","name":"Mario","surname":null,"date":null
      //Integer id = Integer.parseInt((json.getString("id")));
      Integer userId = Integer.parseInt((json.getString("userid")));
      String name = (json.getString("name"));
      String surname = (json.getString("surname"));
      System.err.println("context: " + context.body().asJsonObject());
     
      OperatorDTO operator = new OperatorDTO(null, userId, name, surname);
      operatorService.createOperator(operator)
          .onSuccess(result -> {
          JsonObject responseBody = JsonObject.mapFrom(result);
          context.response().setStatusCode(201).end(responseBody.encode());
        })
          .onFailure(err -> {
            context.response().setStatusCode(500).end();
            System.out.println("Error");});
    });

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
