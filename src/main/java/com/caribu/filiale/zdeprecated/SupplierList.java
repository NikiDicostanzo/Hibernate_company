package com.caribu.filiale.zdeprecated;
    
import java.util.List;

import com.caribu.filiale.model.SupplierDTO;

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