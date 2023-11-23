package com.caribu.filiale.zdeprecated;

import io.vertx.core.Future;

import com.caribu.filiale.model.Operator;
import com.caribu.filiale.model.OperatorDTO;
import com.caribu.filiale.model.OperatorList;
import com.caribu.filiale.service.OperatorService;

import java.util.Objects;
import java.util.Optional;

public class OperatorServiceImpl implements OperatorService {

  private OperatorService repository;

  public OperatorServiceImpl(OperatorService repository) {
    this.repository = repository;
  }

  @Override
  public Future<OperatorDTO> addOperator(OperatorDTO operatorDTO) {
    return repository.addOperator(operatorDTO);
  }

  // @Override
  // public Future<OperatorDTO> updateProject(Principal principal, OperatorDTO
  // operatorDTO) {
  // Integer operatorId = operatorDTO.id();
  // return repository.findOperatorById(operatorId).compose(result -> {
  // if (result.isEmpty()) {
  // return Future.failedFuture(new RuntimeException());
  // }
  // OperatorDTO operator = result.get();
  // if (Objects.equals(operator.userId(), principal.userId())) {
  // return repository.updateProject(operatorDTO);
  // } else {
  // return Future.failedFuture(new NotOwnerException());
  // }
  // });
  // }

  @Override
  public Future<Optional<OperatorDTO>> getOperatorById(Integer id) {
    return repository.getOperatorById(id);
  }

  // @Override
  // public Future<Void> removeProject(Principal principal, Integer id) {
  // return repository.findProjectById(id).compose(result -> {
  // if (result.isEmpty()) {
  // return Future.failedFuture(new RuntimeException());
  // }
  // ProjectDTO project = result.get();
  // if (Objects.equals(project.userId(), principal.userId())) {
  // return repository.removeProject(id);
  // } else {
  // return Future.failedFuture(new NotOwnerException());
  // }
  // });
  // }

  // @Override
  // public Future<OperatorList> findOperatorsByUser(Integer userId) {
  // return repository.findOperatorsByUser(userId);
  // }
}
