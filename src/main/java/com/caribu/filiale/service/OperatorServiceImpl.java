package com.caribu.filiale.service;

import io.vertx.core.Future;
import org.hibernate.reactive.stage.Stage;

import com.caribu.filiale.data.OperatorEntityMapper;
import com.caribu.filiale.data.OperatorDTOMapper;
import com.caribu.filiale.model.Operator;
import com.caribu.filiale.model.OperatorDTO;
import com.caribu.filiale.model.OperatorList;

import javax.persistence.criteria.*;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

public class OperatorServiceImpl implements OperatorService {

  private Stage.SessionFactory sessionFactory;

  public OperatorServiceImpl(Stage.SessionFactory sessionFactory) {
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
  
  // @Override
  // public Future<Void> removeTask(Integer id) {
  // CriteriaBuilder criteriaBuilder = sessionFactory.getCriteriaBuilder();
  // CriteriaDelete<Task> criteriaDelete =
  // criteriaBuilder.createCriteriaDelete(Task.class);
  // Root<Task> root = criteriaDelete.from(Task.class);
  // Predicate predicate = criteriaBuilder.equal(root.get("id"), id);
  // criteriaDelete.where(predicate);

  // CompletionStage<Integer> result = sessionFactory.withTransaction((s,t) ->
  // s.createQuery(criteriaDelete).executeUpdate());
  // Future<Void> future = Future.fromCompletionStage(result).compose(r ->
  // Future.succeededFuture());
  // return future;
  // }

  @Override
  public Future<Optional<OperatorDTO>> getOperatorById(Integer id) {
    OperatorDTOMapper dtoMapper = new OperatorDTOMapper();
    CompletionStage<Operator> result = sessionFactory.withTransaction((s, t) -> s.find(Operator.class, id));
    Future<Optional<OperatorDTO>> future = Future.fromCompletionStage(result)
        .map(r -> Optional.ofNullable(r))
        .map(r -> r.map(dtoMapper));
    return future;
  }

  // @Override
  // public Future<TasksList> findTasksByUser(Integer userId) {
  // TaskDTOMapper dtoMapper = new TaskDTOMapper();
  // CriteriaBuilder criteriaBuilder = sessionFactory.getCriteriaBuilder();
  // CriteriaQuery<Task> criteriaQuery = criteriaBuilder.createQuery(Task.class);
  // Root<Task> root = criteriaQuery.from(Task.class);
  // Predicate predicate = criteriaBuilder.equal(root.get("userId"), userId);
  // criteriaQuery.where(predicate);
  // CompletionStage<List<Task>> result = sessionFactory().withTransaction((s,t)
  // -> s.createQuery(criteriaQuery).getResultList());
  // Future<TasksList> future = Future.fromCompletionStage(result)
  // .map(list -> list.stream().map(dtoMapper).collect(Collectors.toList()))
  // .map(list -> new TasksList(list));
  // return future;
  // }
}
