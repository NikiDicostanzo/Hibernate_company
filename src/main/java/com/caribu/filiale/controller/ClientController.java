package com.caribu.filiale.controller;

import com.caribu.filiale.model.ClientDTO;
import com.caribu.filiale.service.ClientService;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;


public class ClientController {

    private ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }
    
    public void addClient(RoutingContext context) {
        JsonObject json = context.body().asJsonObject();
        Integer clientId = Integer.parseInt((json.getString("operatorid")));
        String companyName = (json.getString("companyName"));
        
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

    public void getClientById(RoutingContext context) {
        String companyName = context.pathParam("companyName");  
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
            .onFailure(err -> {  
                    context.response().setStatusCode(500).end();
                    System.out.print("NON esiste " + companyName);
            });
    }
    
    // Ricevo Company name -> verifico che non E :  - Ritorno id 
    //                                              - Aggiungo e ritorno nuovo id
    public void addClientIfNotExistsByName(RoutingContext context) {
       
        String companyName = context.pathParam("companyName");  
        System.out.print("Check: "+ companyName);
        clientService.addClientIfNotExistsByName(companyName)
            .onSuccess(result -> {
                    JsonObject body = JsonObject.mapFrom(result);
                    System.out.println("Eseguito correttamente: " + body);
                    context.response().setStatusCode(200).end(body.encode());
            })
            .onFailure(err -> {  
                    context.response().setStatusCode(500).end();
                    System.out.print("Errore per addClientIfNotExistsByName");
            });
    }


  /*   int getRandom( int min, int max) {
        int random_int = (int) Math.floor(Math.random() * (max - min + 1) + min);
        return random_int;
    } */
}
