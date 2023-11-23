package com.caribu.filiale.service;

import io.vertx.core.Future;
import org.hibernate.reactive.stage.Stage;

import com.caribu.filiale.data.SupplierEntityMapper;
import com.caribu.filiale.data.SupplierDTOMapper;
import com.caribu.filiale.model.Supplier;
import com.caribu.filiale.model.SupplierDTO;

import javax.persistence.criteria.*;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

public class SupplierServiceImpl implements SupplierService {

  private Stage.SessionFactory sessionFactory;

  public SupplierServiceImpl(Stage.SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  public Future<SupplierDTO> createSupplier(SupplierDTO supplier) {
    SupplierEntityMapper entityMapper = new SupplierEntityMapper();
    Supplier entity = entityMapper.apply(supplier);
    CompletionStage<Void> result = sessionFactory.withTransaction((s, t) -> s.persist(entity));
    SupplierDTOMapper dtoMapper = new SupplierDTOMapper();
    Future<SupplierDTO> future = Future.fromCompletionStage(result).map(v -> dtoMapper.apply(entity));
    return future;
  }

  @Override
  public Future<Optional<SupplierDTO>> findSupplierById(Integer id) {
    SupplierDTOMapper dtoMapper = new SupplierDTOMapper();
    CompletionStage<Supplier> result = sessionFactory.withTransaction((s, t) -> s.find(Supplier.class, id));
    Future<Optional<SupplierDTO>> future = Future.fromCompletionStage(result)
        .map(r -> Optional.ofNullable(r))
        .map(r -> r.map(dtoMapper));
    return future;
  }
}
