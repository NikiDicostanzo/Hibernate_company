package com.caribu.filiale.model;
    
import java.util.List;

public class ClientList {
    private List<ClientDTO> clients;

    public List<ClientDTO> getClients() {
        return clients;
    }

    public void setClients(List<ClientDTO> operators) {
        this.clients = operators;
    }

    public ClientList(List<ClientDTO> operators) {
        this.clients = operators;
    }    
}