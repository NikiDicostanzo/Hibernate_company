package com.caribu.filiale.service;

import io.vertx.core.Future;

import com.caribu.filiale.model.OperatorDTO;
import com.caribu.filiale.model.OperatorList;

import java.util.Optional;

public interface OperatorService {

  Future<OperatorDTO> createOperator (OperatorDTO operator);

  // Future<Void> removeTask (Integer id);

  Future<Optional<OperatorDTO>> findOperatorById (Integer id);

  // Future<TasksList> findTasksByUser (Integer userId);
}
