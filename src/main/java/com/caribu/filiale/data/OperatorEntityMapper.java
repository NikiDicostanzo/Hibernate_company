package com.caribu.filiale.data;

import com.caribu.filiale.model.Operator;
import com.caribu.filiale.model.OperatorDTO;

import java.util.function.Function;

public class OperatorEntityMapper implements Function<OperatorDTO, Operator> {
  
  @Override
  public Operator apply(OperatorDTO operatorDTO) {
    Operator operator = new Operator();
    operator.setId(operatorDTO.getId());
    operator.setUserId(operatorDTO.getUserId());
    operator.setName(operatorDTO.getName());
    operator.setSurname(operatorDTO.getSurname());
    
    return operator;
  }
}
