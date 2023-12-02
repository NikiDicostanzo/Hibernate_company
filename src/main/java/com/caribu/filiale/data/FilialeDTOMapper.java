package com.caribu.filiale.data;

import java.util.function.Function;

import com.caribu.filiale.model.Filiale;
import com.caribu.filiale.model.FilialeDTO;

public class FilialeDTOMapper implements Function<Filiale, FilialeDTO> {
  @Override
  public FilialeDTO apply(Filiale filiale) {
    return new FilialeDTO(filiale.getFilialeName(),filiale.getOperator(),filiale.getClient());
  }
}
