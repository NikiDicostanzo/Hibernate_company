package com.caribu.filiale.model;
    
import java.util.List;

public class OperatorList {
    private List<OperatorDTO> operators;

    public List<OperatorDTO> getOperators() {
        return operators;
    }

    public void setOperators(List<OperatorDTO> operators) {
        this.operators = operators;
    }

    public OperatorList(List<OperatorDTO> operators) {
        this.operators = operators;
    }

    
}