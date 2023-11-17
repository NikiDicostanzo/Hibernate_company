package com.caribu.filiale.data;

import com.caribu.filiale.model.Supplier;
import com.caribu.filiale.model.SupplierDTO;
import com.caribu.filiale.model.SupplierList;

import java.util.Optional;
import java.util.function.Function;

class SupplierDTOMapper implements Function<Supplier, SupplierDTO> {
  @Override
  public SupplierDTO apply(Supplier supplier) {
    return new SupplierDTO(supplier.getId(), supplier.getUserId(), supplier.getName(), supplier.getSurname(), supplier.getDate());
  }
}
