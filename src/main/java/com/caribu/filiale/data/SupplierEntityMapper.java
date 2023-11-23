package com.caribu.filiale.data;

import com.caribu.filiale.model.Supplier;
import com.caribu.filiale.model.SupplierDTO;

import java.util.function.Function;

public class SupplierEntityMapper implements Function<SupplierDTO, Supplier> {

  @Override
  public Supplier apply(SupplierDTO supplierDTO) {
    Supplier supplier = new Supplier();
    supplier.setId(supplierDTO.getId());
    supplier.setSupplierId(supplierDTO.getSupplierId());
    supplier.setCompanyName(supplierDTO.getCompanyName());

    return supplier;
  }
}
