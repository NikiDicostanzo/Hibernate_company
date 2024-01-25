package com.caribu.filiale.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "operator")
public class Operator {

    @Id
    @GeneratedValue
    private Integer id;  // UUID
    private Integer operatorId;
    private String name;
    private String surname;


    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer userId) {
        this.operatorId = userId;
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