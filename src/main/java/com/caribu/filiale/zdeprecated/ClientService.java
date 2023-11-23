package com.caribu.filiale.zdeprecated;

import xyz.yurimednikov.hibernate.model.ProjectDTO;
import io.vertx.core.Future;
import xyz.yurimednikov.hibernate.model.ProjectsList;

import java.util.Optional;

import com.caribu.filiale.model.ClientDTO;

public interface ClientService {

  Future<ClientDTO> createClient (ClientDTO projectDTO);

  //Future<ClientDTO> updateClient(ClientDTO projectDTO);

  Future<Optional<ClientDTO>> findClientById (Integer id);

  //Future<Void> removeClient (Integer id);

  //Future<ClientsList> findClientsByUser (Integer userId);
}
