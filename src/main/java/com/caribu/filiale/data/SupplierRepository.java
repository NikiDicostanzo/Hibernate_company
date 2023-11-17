package com.caribu.filiale.data;


import io.vertx.core.Future;

import com.caribu.filiale.model.SupplierDTO;
import com.caribu.filiale.model.SupplierList;

import java.util.Optional;

// espongo solo le interfaccie delle funzioni HTTP
public interface SupplierRepository {

  Future<SupplierDTO> createSupplier (SupplierDTO supplier);

  // Future<TaskDTO> updateTask (TaskDTO task);

  // Future<Void> removeTask (Integer id);

  Future<Optional<SupplierDTO>> findSupplierById (Integer id);

  // Future<TasksList> findTasksByUser (Integer userId);
}
