package com.caribu.filiale.model;

import javax.persistence.*;

@Entity
@Table(name = "supplier")
public class Supplier {

    @Id
    @GeneratedValue
    private Integer id;
    private Integer supplierId;
    private String companyName;

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer userId) {
        this.supplierId = userId;
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