package com.caribu.filiale.model;


public class ClientDTO {
    private Integer id;
    private Integer clientId;
    private String companyName;;

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