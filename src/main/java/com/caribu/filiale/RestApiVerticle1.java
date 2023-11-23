package com.caribu.filiale;

import org.hibernate.reactive.stage.Stage.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.openapi.RouterBuilder;

public class RestApiVerticle1 extends AbstractVerticle{
    private static final Logger LOG = LoggerFactory.getLogger(RestApiVerticle_deprec.class);
    public static final int HTTP_PORT = 10001;
    private SessionFactory sessionFactory;
    
    private TrattaService trattaService;
    private RichiestaService richiestaService;

    private TrattaController trattaController;
    private RichiestaController richiestaController;

    public RestApiVerticle1(String name, SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
        System.out.println("Hello verticle #######" + name); // For debug purposes
        /* Injection is made in the "start" method BECAUSE we need the instanciated vertx object
        //Inject SessionFactories
        trattaService = new TrattaService(sessionFactory, vertx); 
        richiestaService = new RichiestaService(sessionFactory); */
    }

    @Override
    public void start(final Promise<Void> startPromise) throws Exception {
        System.out.println("Injecting dependencies: ");
        trattaService = new TrattaService(sessionFactory, vertx); // vertx dependency is needed for the event bus 
        richiestaService = new RichiestaService(sessionFactory);
        System.out.println("Starting http server and attaching routes");
        startHttpServerAndAttachRoutes(startPromise);
    }

    //il successivo è per openAPI ma mi sta dando problemi, provo prima strada senza, poi vediamo
    private void startHttpServerAndAttachRoutes(Promise<Void> startPromise) {
        System.out.println("Hello verticle ################");
        RouterBuilder.create(vertx, "openapi.yaml")
            .onSuccess(routerBuilder -> {
                routerBuilder.operation("ping").handler( h -> {
                    h.response().end(new JsonObject().put("message", "ping pong ####").toString());
                });
                //Posso fare qui le DI nel costruttore
                
                // qui definisco controllers per una questione di organizzazione logica del codice
                trattaController = new TrattaController(trattaService);
                richiestaController = new RichiestaController(richiestaService, trattaService);

                routerBuilder.operation("addNewTratta").handler(ctx -> trattaController.addNewTratta(ctx));
                routerBuilder.operation("createNewRichiesta").handler(ctx -> richiestaController.addRichiesta(ctx));
                routerBuilder.operation("getRichiestaById").handler(ctx -> richiestaController.getRichiestaById(ctx));
                

                Router restApi = routerBuilder.createRouter();

                //Create HTTP server and attach routes
                vertx.createHttpServer()
                    .requestHandler(restApi)
                    .listen(HTTP_PORT, ar -> {
                        if(ar.succeeded()) {
                            LOG.info("HTTP server running on port {}", HTTP_PORT);
                            startPromise.complete();
                        } else {
                            LOG.error("Could not start a HTTP server", ar.cause());
                            startPromise.fail(ar.cause());
                        }
                    });
                }
            );
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