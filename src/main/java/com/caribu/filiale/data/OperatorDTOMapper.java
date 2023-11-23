package com.caribu.filiale.data;

import com.caribu.filiale.model.Operator;
import com.caribu.filiale.model.OperatorDTO;

import java.util.function.Function;

public class OperatorDTOMapper implements Function<Operator, OperatorDTO> {
  @Override
  public OperatorDTO apply(Operator operator) {
    return new OperatorDTO(operator.getId(), operator.getUserId(), operator.getName(), operator.getSurname());
  }
}
