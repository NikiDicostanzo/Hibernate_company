package com.caribu.filiale.data;

import com.caribu.filiale.model.Filiale;
import com.caribu.filiale.model.FilialeDTO;

import java.util.function.Function;

public class FilialeDTOMapper implements Function<Filiale, FilialeDTO> {
  @Override
  public FilialeDTO apply(Filiale filiale) {
    return new FilialeDTO(filiale.getId(), filiale.getFilialeId(), filiale.getCompanyName());
  }
}
