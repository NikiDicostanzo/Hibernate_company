package com.caribu.filiale.data;

import com.caribu.filiale.model.Filiale;
import com.caribu.filiale.model.FilialeDTO;

import java.util.function.Function;

public class FilialeEntityMapper implements Function<FilialeDTO, Filiale> {

  @Override
  public Filiale apply(FilialeDTO filialeDTO) {
    Filiale filiale = new Filiale();
    filiale.setId(filialeDTO.getId());
    filiale.setFilialeId(filialeDTO.getFilialeId());
    filiale.setCompanyName(filialeDTO.getCompanyName());
    return filiale;
  }
}
