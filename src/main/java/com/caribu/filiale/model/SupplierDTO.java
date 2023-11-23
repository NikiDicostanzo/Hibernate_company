package com.caribu.filiale.model;

public class SupplierDTO {
    private Integer id;
    private Integer supplierId;
    private String companyName;

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }    

    public SupplierDTO(Integer id, Integer userId, String companyName) {
        this.id = id;
        this.supplierId = userId;
        this.companyName = companyName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String name) {
        this.companyName = name;
    }
}