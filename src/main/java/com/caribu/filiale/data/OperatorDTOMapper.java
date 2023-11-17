package com.caribu.filiale.data;

import com.caribu.filiale.model.Operator;
import com.caribu.filiale.model.OperatorDTO;
import com.caribu.filiale.model.OperatorList;

import java.util.Optional;
import java.util.function.Function;

class OperatorDTOMapper implements Function<Operator, OperatorDTO> {
  @Override
  public OperatorDTO apply(Operator operator) {
    return new OperatorDTO(operator.getId(), operator.getUserId(), operator.getName(), operator.getSurname(), operator.getDate());
  }
}
