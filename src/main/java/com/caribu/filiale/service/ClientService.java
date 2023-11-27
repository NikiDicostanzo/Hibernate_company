package com.caribu.filiale.service;

import io.vertx.core.Future;
import com.caribu.filiale.model.ClientDTO;
import java.util.Optional;

public interface ClientService {

  Future<ClientDTO> addClient(ClientDTO client);

  // Future<Void> removeTask (Integer id);

  Future<Optional<ClientDTO>> getClientBycompanyName(String companyName);

  Future<Optional<ClientDTO>> findClientByCompany(String companyName);
}
