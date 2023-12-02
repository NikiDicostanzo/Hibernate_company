package com.caribu.filiale.service;

import java.util.Optional;

import com.caribu.filiale.model.OperatorDTO;

import io.vertx.core.Future;

public interface FilialeService {

  Future<OperatorDTO> addOperator(OperatorDTO operator);
  Future<Optional<OperatorDTO>> getOperatorById(Integer id);

}
