package com.caribu.filiale.service;

import io.vertx.core.Future;
import com.caribu.filiale.model.FilialeDTO;
import java.util.Optional;

public interface FilialeService {

  Future<FilialeDTO> createFiliale (FilialeDTO filiale);

  // Future<Void> removeTask (Integer id);

  Future<Optional<FilialeDTO>> findFilialeById (Integer id);

  // Future<TasksList> findTasksByUser (Integer userId);
}
