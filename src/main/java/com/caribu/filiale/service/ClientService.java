package com.caribu.filiale.service;

import io.vertx.core.Future;
import com.caribu.filiale.model.ClientDTO;
import java.util.Optional;

public interface ClientService {

  Future<ClientDTO> createClient (ClientDTO client);

  // Future<Void> removeTask (Integer id);

  Future<Optional<ClientDTO>> findClientById (Integer id);

  // Future<TasksList> findTasksByUser (Integer userId);
}
