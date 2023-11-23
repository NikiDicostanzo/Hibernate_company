package com.caribu.filiale.zdeprecated;

import io.vertx.core.Future;
import java.util.Optional;

import com.caribu.filiale.model.SupplierDTO;

public interface SupplierService {

  Future<SupplierDTO> createSupplier (SupplierDTO supplier);

  //Future<SupplierDTO> updateSupplier(SupplierDTO supplier);

  Future<Optional<SupplierDTO>> findSupplierById (Integer id);

  //Future<Void> removeSupplier (Integer id);

 // Future<SupplierList> findSuppliersByUser (Integer userId);
}
