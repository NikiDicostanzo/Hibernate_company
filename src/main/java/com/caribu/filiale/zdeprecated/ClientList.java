package com.caribu.filiale.zdeprecated;
    
import java.util.List;

import com.caribu.filiale.model.ClientDTO;

public class ClientList {
    private List<ClientDTO> clients;

    public List<ClientDTO> getClients() {
        return clients;
    }

    public void setClients(List<ClientDTO> clients) {
        this.clients = clients;
    }

    public ClientList(List<ClientDTO> clients) {
        this.clients = clients;
    }

    
}