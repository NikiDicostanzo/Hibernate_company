package com.caribu.filiale.service;

import java.util.Optional;
import java.util.concurrent.CompletionStage;

import org.hibernate.reactive.stage.Stage;

import com.caribu.filiale.data.OperatorDTOMapper;
import com.caribu.filiale.data.OperatorEntityMapper;
import com.caribu.filiale.model.Operator;
import com.caribu.filiale.model.OperatorDTO;

import io.vertx.core.Future;

public class FilialeServiceImpl implements FilialeService {

  private Stage.SessionFactory sessionFactory;

  public FilialeServiceImpl(Stage.SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  public Future<OperatorDTO> addOperator(OperatorDTO operator) {
    OperatorEntityMapper entityMapper = new OperatorEntityMapper();
    Operator entity = entityMapper.apply(operator);
    CompletionStage<Void> result = sessionFactory.withTransaction((s, t) -> s.persist(entity));
    OperatorDTOMapper dtoMapper = new OperatorDTOMapper();
    Future<OperatorDTO> future = Future.fromCompletionStage(result).map(v -> dtoMapper.apply(entity));
    return future;
  }


  @Override
  public Future<Optional<OperatorDTO>> getOperatorById(Integer id) {
    OperatorDTOMapper dtoMapper = new OperatorDTOMapper();
    CompletionStage<Operator> result = sessionFactory.withTransaction((s, t) -> s.find(Operator.class, id));
    Future<Optional<OperatorDTO>> future = Future.fromCompletionStage(result)
        .map(r -> Optional.ofNullable(r))
        .map(r -> r.map(dtoMapper));
    return future;
  }
}
