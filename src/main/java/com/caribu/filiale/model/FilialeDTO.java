package com.caribu.filiale.model;

import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

public class FilialeDTO {
    private String filialeName;
    
    /* Integer id;
    
    @OneToMany
    @JoinColumn(name = "operatorId")
    private Operator operator;
    
    @OneToMany
    @JoinColumn(name = "clientId")
    private Client client;
    
    public FilialeDTO(Integer id, String filialeName, Operator operator, Supplier supplier, Client client) {
        this.id = id;
        this.filialeName = filialeName;
        this.operator = operator;
        this.supplier = supplier;
        this.client = client;
    }
    
    public Integer getIdOperator() {
        if (operator == null) {
            return -1;
        } else {
            return operator.getId();
        }
    }
    
    public Operator getOperator() {
        return operator;
    }
    
    public void setOperator(Operator operator) {
        this.operator = operator;
    }
    
    public Integer getIdClient() {
        if (client == null) {
            return -1;
        } else {
            return client.getId();
        }
    }
    
    public Client getClient() {
        return client;
    }
    
    public void setClient(Client client) {
        this.client = client;
    }
    */
    public String getFilialeName() {
        return filialeName;
    }

    public void setFilialeName(String filialeName) {
        this.filialeName = filialeName;
    }
}