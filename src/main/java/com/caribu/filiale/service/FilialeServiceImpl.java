package com.caribu.filiale.service;

import io.vertx.core.Future;
import org.hibernate.reactive.stage.Stage;

import com.caribu.filiale.data.FilialeEntityMapper;
import com.caribu.filiale.data.FilialeDTOMapper;
import com.caribu.filiale.model.Filiale;
import com.caribu.filiale.model.FilialeDTO;

import javax.persistence.criteria.*;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

public class FilialeServiceImpl implements FilialeService{

  private Stage.SessionFactory sessionFactory;

  public FilialeServiceImpl(Stage.SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  public Future<FilialeDTO> createFiliale(FilialeDTO filiale) {
    FilialeEntityMapper entityMapper = new FilialeEntityMapper();
    Filiale entity = entityMapper.apply(filiale);
    CompletionStage<Void> result = sessionFactory.withTransaction((s, t) -> s.persist(entity));
    FilialeDTOMapper dtoMapper = new FilialeDTOMapper();
    Future<FilialeDTO> future = Future.fromCompletionStage(result).map(v -> dtoMapper.apply(entity));
    return future;
  }

  // @Override
  // public Future<Void> removeTask(Integer id) {
  //   CriteriaBuilder criteriaBuilder = sessionFactory.getCriteriaBuilder();
  //   CriteriaDelete<Task> criteriaDelete = criteriaBuilder.createCriteriaDelete(Task.class);
  //   Root<Task> root = criteriaDelete.from(Task.class);
  //   Predicate predicate = criteriaBuilder.equal(root.get("id"), id);
  //   criteriaDelete.where(predicate);

  //   CompletionStage<Integer> result = sessionFactory.withTransaction((s,t) -> s.createQuery(criteriaDelete).executeUpdate());
  //   Future<Void> future = Future.fromCompletionStage(result).compose(r -> Future.succeededFuture());
  //   return future;
  // }

  @Override
  public Future<Optional<FilialeDTO>> findFilialeById(Integer id) {
    FilialeDTOMapper dtoMapper = new FilialeDTOMapper();
    CompletionStage<Filiale> result = sessionFactory.withTransaction((s,t) -> s.find(Filiale.class, id));
    Future<Optional<FilialeDTO>> future = Future.fromCompletionStage(result)
      .map(r -> Optional.ofNullable(r))
      .map(r -> r.map(dtoMapper));
    return future;
  }

  // @Override
  // public Future<TasksList> findTasksByUser(Integer userId) {
  //   TaskDTOMapper dtoMapper = new TaskDTOMapper();
  //   CriteriaBuilder criteriaBuilder = sessionFactory.getCriteriaBuilder();
  //   CriteriaQuery<Task> criteriaQuery = criteriaBuilder.createQuery(Task.class);
  //   Root<Task> root = criteriaQuery.from(Task.class);
  //   Predicate predicate = criteriaBuilder.equal(root.get("userId"), userId);
  //   criteriaQuery.where(predicate);
  //   CompletionStage<List<Task>> result = sessionFactory().withTransaction((s,t) -> s.createQuery(criteriaQuery).getResultList());
  //   Future<TasksList> future = Future.fromCompletionStage(result)
  //     .map(list -> list.stream().map(dtoMapper).collect(Collectors.toList()))
  //     .map(list -> new TasksList(list));
  //   return future;
  // }
}
