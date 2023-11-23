package com.caribu.filiale.data;

import com.caribu.filiale.model.Client;
import com.caribu.filiale.model.ClientDTO;

import java.util.function.Function;

public class ClientDTOMapper implements Function<Client, ClientDTO> {
  @Override
  public ClientDTO apply(Client client) {
    return new ClientDTO(client.getId(), client.getClientId(), client.getCompanyName());
  }
}
