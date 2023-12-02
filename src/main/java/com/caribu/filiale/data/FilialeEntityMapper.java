package com.caribu.filiale.data;

import java.util.function.Function;

import com.caribu.filiale.model.Filiale;
import com.caribu.filiale.model.FilialeDTO;

public class FilialeEntityMapper implements Function<FilialeDTO, Filiale> {

  @Override
  public Filiale apply(FilialeDTO filialeDTO) {
    Filiale filiale = new Filiale();
    filiale.setFilialeName(filialeDTO.getFilialeName());
    filiale.setClient(filialeDTO.getClient());
    filiale.setOperator(filialeDTO.getOperator());
    return filiale;
  }
}
