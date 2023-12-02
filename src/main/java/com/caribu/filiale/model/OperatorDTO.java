package com.caribu.filiale.model;

public class OperatorDTO {
    private Integer id;
    private Integer operatorId;
    private String name;
    private String surname;


    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer userId) {
        this.operatorId = userId;
    }

    public OperatorDTO(Integer id, Integer operatorId, String name, String surname) {
        this.id = id;
        this.operatorId = operatorId;
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