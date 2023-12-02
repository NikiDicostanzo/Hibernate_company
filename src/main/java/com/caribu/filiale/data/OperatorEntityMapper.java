package com.caribu.filiale.data;

import java.util.function.Function;

import com.caribu.filiale.model.Operator;
import com.caribu.filiale.model.OperatorDTO;

public class OperatorEntityMapper implements Function<OperatorDTO, Operator> {

  @Override
  public Operator apply(OperatorDTO operatorDTO) {
    Operator operator = new Operator();
    operator.setId(operatorDTO.getId());
    operator.setOperatorId(operatorDTO.getOperatorId());
    operator.setName(operatorDTO.getName());
    operator.setSurname(operatorDTO.getSurname());

    return operator;
  }
}
