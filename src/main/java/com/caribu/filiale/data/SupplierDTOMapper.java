package com.caribu.filiale.data;

import com.caribu.filiale.model.Supplier;
import com.caribu.filiale.model.SupplierDTO;

import java.util.function.Function;

public class SupplierDTOMapper implements Function<Supplier, SupplierDTO> {
  @Override
  public SupplierDTO apply(Supplier supplier) {
    return new SupplierDTO(supplier.getId(), supplier.getSupplierId(), supplier.getCompanyName());
  }
}
