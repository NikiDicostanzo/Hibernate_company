package com.caribu.filiale.model;


public class OperatorDTO {
    private Integer id;
    private Integer userId;
    private String name;
    private String surname;
    
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public OperatorDTO(Integer id, Integer userId, String name, String surname) {
        this.id = id;
        this.userId = userId;
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