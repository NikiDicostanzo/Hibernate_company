package com.caribu.filiale.service;

import io.vertx.core.Future;
import com.caribu.filiale.model.SupplierDTO;

import java.util.Optional;

public interface SupplierService {

  Future<SupplierDTO> createSupplier(SupplierDTO supplier);

  Future<Optional<SupplierDTO>> findSupplierById(Integer id);

}
