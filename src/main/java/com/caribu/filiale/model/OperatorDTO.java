package com.caribu.filiale.model;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class OperatorDTO {
    private Integer id;
    private Integer userId;
    private String name;
    private String surname;

    // @ManyToOne
    // @JoinColumn(name = "filialeId", nullable = true)
    // private Filiale filiale;

    // public Filiale getFiliale() {
    //     return filiale;
    // }

    // public void setFiliale(Filiale filiale) {
    //     this.filiale = filiale;
    // }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public OperatorDTO(Integer id, Integer operatorId, String name, String surname) {
        this.id = id;
        this.userId = operatorId;
        this.name = name;
        this.surname = surname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

}