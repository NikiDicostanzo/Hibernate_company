package com.caribu.filiale.data;

import com.caribu.filiale.model.Client;
import com.caribu.filiale.model.ClientDTO;

import java.util.function.Function;

public class ClientEntityMapper implements Function<ClientDTO, Client> {

  @Override
  public Client apply(ClientDTO clientDTO) {
    Client client = new Client();
    client.setId(clientDTO.getId());
    client.setClientId(clientDTO.getClientId());
    client.setCompanyName(clientDTO.getCompanyName());
    return client;
  }
}
