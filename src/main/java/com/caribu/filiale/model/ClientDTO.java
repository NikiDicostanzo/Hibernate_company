package com.caribu.filiale.model;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class ClientDTO {
    private Integer id;
    private Integer clientId;
    private String companyName;;

    // @ManyToOne
    // @JoinColumn(name = "filialeId", nullable = true)
    // private Filiale filiale;

    // public Filiale getFiliale() {
    //     return filiale;
    // }
    // public void setFiliale(Filiale filiale) {
    //     this.filiale = filiale;
    // }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer userId) {
        this.clientId = userId;
    }

    public ClientDTO(Integer id, Integer clientId, String companyName) {
        this.id = id;
        this.clientId = clientId;
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