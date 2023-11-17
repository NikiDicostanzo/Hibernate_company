package com.caribu.filiale.model;
    
import java.util.List;

public class SupplierList {
    private List<SupplierDTO> suppliers;

    public List<SupplierDTO> getSuppliers() {
        return suppliers;
    }

    public void setSupplier(List<SupplierDTO> suppliers) {
        this.suppliers = suppliers;
    }

    public SupplierList(List<SupplierDTO> suppliers) {
        this.suppliers = suppliers;
    }

    
}