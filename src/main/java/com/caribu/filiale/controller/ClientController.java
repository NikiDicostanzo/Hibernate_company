package com.caribu.filiale.controller;

import com.caribu.filiale.model.ClientDTO;
import com.caribu.filiale.service.ClientService;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

/* 
addClient: 
- input: company name
TODO: checks if company name exists. If exists -> return ID
If not exists: return "SUCCESS" + new ID */

public class ClientController {

    private ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }
    
    //public void addClient(RoutingContext context) {
    public void addClient(RoutingContext context, Integer clientId, String companyName) {
        /* JsonObject json = context.body().asJsonObject();

        Integer clientId = Integer.parseInt((json.getString("operatorid")));
        String companyName = (json.getString("companyName"));
        */
        System.err.println("ClientId: " + clientId + " Company Name: " + companyName);

        ClientDTO client = new ClientDTO(null, clientId, companyName);
        clientService.addClient(client)
                .onSuccess(result -> {
                    JsonObject responseBody = JsonObject.mapFrom(result);
                    context.response().setStatusCode(201).end(responseBody.encode());
                })
                .onFailure(err -> {
                    context.response().setStatusCode(500).end();
                    System.out.println("Error");
                });
    }
    
    // Ricevo Company name -> verifico che non E :  - Ritorno id 
    //                                              - Aggiungo e ritorno nuovo id
    public void getClientById(RoutingContext context) {
        String companyName = context.pathParam("companyName");  //Integer.valueOf(
        clientService.findClientByCompany(companyName)
            .onSuccess(result -> {
                System.out.println("GET");
                if (result.isPresent()) {
                    JsonObject body = JsonObject.mapFrom(result.get());
                    context.response().setStatusCode(200).end(body.encode());
                } else {
                    context.response().setStatusCode(404).end();
                }
            })
                .onFailure(err -> {  // Non esiste allora chiamo POST
                    System.out.print("2. NON esiste " + companyName);
                    Integer clientId = getRandom( 10000, 50000); //TODO check ultimo id
                    addClient(context, clientId, companyName);
            });//context.response().setStatusCode(500).end());
    }


    int getRandom( int min, int max) {
        int random_int = (int) Math.floor(Math.random() * (max - min + 1) + min);
        return random_int;
    }
}
