package com.caribu.filiale.service;

import java.util.Optional;

import com.caribu.filiale.model.ClientDTO;

import io.vertx.core.Future;

public interface ClientService {

  Future<ClientDTO> addClient(ClientDTO client);
  
  Future<Optional<ClientDTO>> getClientById(Integer id);

  Future<Optional<ClientDTO>> findClientByCompany(String companyName);

  public Future<ClientDTO> addClientIfNotExistsByName(String companyName);
}
