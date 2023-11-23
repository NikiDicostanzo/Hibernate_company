package com.caribu.filiale;

import io.vertx.core.*;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.openapi.RouterBuilder;
import io.vertx.ext.web.validation.ValidationHandler;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;

import org.hibernate.cfg.Configuration;
import org.hibernate.reactive.provider.ReactiveServiceRegistryBuilder;
import org.hibernate.reactive.stage.Stage;
import org.hibernate.service.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.caribu.filiale.controller.OperatorController;
import com.caribu.filiale.model.Operator;
import com.caribu.filiale.service.OperatorServiceImpl;
import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.NetworkConfig;
import com.caribu.filiale.service.OperatorService;

import java.util.Properties;

public class WebVerticle extends AbstractVerticle {
  private static final Logger LOG = LoggerFactory.getLogger(WebVerticle.class);
  private final OperatorService operatorService;
  private OperatorController operatorController;
  public static final int HTTP_PORT = 10001;

  public WebVerticle(OperatorService operatorService) {
    this.operatorService = operatorService; 
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
      operatorController = new OperatorController(operatorService);
      RouterBuilder.create(vertx, "openapi.yml")
        .onSuccess(routerBuilder -> {
            routerBuilder.operation("AddOperator").handler(ctx -> operatorController.addOperator(ctx));
            routerBuilder.operation("GetOperator").handler(ctx -> operatorController.getOperatorById(ctx));

            Router restApi = routerBuilder.createRouter();

          // Create HTTP server and attach routes
            vertx.createHttpServer()
              .requestHandler(restApi)
              .listen(HTTP_PORT, ar -> {
                if (ar.succeeded()) {
                  LOG.info("HTTP server running on port {}", HTTP_PORT);
                  startPromise.complete();
                } else {
                  LOG.error("Could not start a HTTP server", ar.cause());
                  startPromise.fail(ar.cause());
                }
              });
          });
        
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


    // Configure clustering
    Config hazelcastConfig = new Config();
    hazelcastConfig.getNetworkConfig().setPort(6000) // Set the initial port for clustering
              .setPortAutoIncrement(true);

    NetworkConfig networkConfig = hazelcastConfig.getNetworkConfig();

    // Network configurations for discovery over TCP/IP instead of multicast
    JoinConfig joinConfig = networkConfig.getJoin();
    joinConfig.getMulticastConfig().setEnabled(false);
    joinConfig.getTcpIpConfig().setEnabled(true).addMember("127.0.0.1");
    // some configuration settings
    ClusterManager mgr = new HazelcastClusterManager(hazelcastConfig);
    VertxOptions options = new VertxOptions().setClusterManager(mgr);

    // Deploy verticle
    Vertx
      .clusteredVertx(options, cluster -> {
       if (cluster.succeeded()) {
           cluster.result().deployVerticle(new WebVerticle(operatorService), res -> {
              if (res.succeeded()) {
                   System.out.println("Application is up and running");
                   LOG.info("Deployment id is: " + res.result());
               } else {
                   LOG.error("Deployment failed!");
               }
           });
       } else {
           LOG.error("Cluster up failed: " + cluster.cause());
       }
    });
  }

  private void failureHandler(RoutingContext errorContext) {
        
        if (errorContext.response().ended()) {
          // Ignore completed response
          LOG.info("------");
          return;
        }
        LOG.info("Route Error:", errorContext.failure());
        errorContext.response()
          .setStatusCode(500)
          .end(new JsonObject().put("message: Something went wrong, path: ", errorContext.normalizedPath()).toString());
    }
}
