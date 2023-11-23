package com.caribu.filiale.controller;

import com.caribu.filiale.model.OperatorDTO;
import com.caribu.filiale.service.OperatorService;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;


public class OperatorController {
    private OperatorService operatorService;

    public OperatorController(OperatorService operatorService) {
        this.operatorService = operatorService;
    }

    public void addOperator(RoutingContext context) {
        JsonObject json = context.body().asJsonObject();

        Integer userId = Integer.parseInt((json.getString("userid")));
        String name = (json.getString("name"));
        String surname = (json.getString("surname"));
        System.err.println("context: " + context.body().asJsonObject());

        OperatorDTO operator = new OperatorDTO(null, userId, name, surname);
        operatorService.addOperator(operator)
            .onSuccess(result -> {
                JsonObject responseBody = JsonObject.mapFrom(result);
                context.response().setStatusCode(201).end(responseBody.encode());
            })
            .onFailure(err -> {
                context.response().setStatusCode(500).end();
                System.out.println("Error");
            });
    }

    public void getOperatorById(RoutingContext context) {
        Integer id = Integer.valueOf(context.pathParam("id"));
        operatorService.getOperatorById(id)
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
    }
}