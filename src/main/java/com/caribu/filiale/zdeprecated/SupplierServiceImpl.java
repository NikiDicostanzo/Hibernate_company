package com.caribu.filiale.zdeprecated;

import io.vertx.core.Future;

import com.caribu.filiale.model.Supplier;
import com.caribu.filiale.model.SupplierDTO;
import com.caribu.filiale.service.SupplierService;

import java.util.Optional;

public class SupplierServiceImpl implements SupplierService {

  private SupplierService repository;

  public SupplierServiceImpl(SupplierService repository) {
    this.repository = repository;
  }

  @Override
  public Future<SupplierDTO> createSupplier(SupplierDTO supplier) {
    return repository.createSupplier(supplier);
  }

  // @Override
  // public Future<SupplierDTO> updateSupplier(SupplierDTO supplier) {
  // return repository.updateSupplier(supplier);
  // }

  @Override
  public Future<Optional<SupplierDTO>> findSupplierById(Integer id) {
    return repository.findSupplierById(id);
  }

  // @Override
  // public Future<Void> removeSupplier(Integer id) {
  // return repository.removeSupplier(id);
  // }

  // @Override
  // public Future<SuppliersList> findSuppliersByUser(Integer userId) {
  // return repository.findSuppliersByUser(userId);
  // }
}
