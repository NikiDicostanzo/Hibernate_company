package com.caribu.filiale.data;

import java.util.function.Function;

import com.caribu.filiale.model.Operator;
import com.caribu.filiale.model.OperatorDTO;

public class OperatorDTOMapper implements Function<Operator, OperatorDTO> {
  @Override
  public OperatorDTO apply(Operator operator) {
    return new OperatorDTO(operator.getId(), operator.getOperatorId(), operator.getName(), operator.getSurname());
  }
}
