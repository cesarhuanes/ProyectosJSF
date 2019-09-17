package com.cardif.satelite.configuracion.service;

import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.satelite.GenPais;

public interface GenPaisService {
  public List<GenPais> buscar() throws SyncconException;

  public GenPais obtener(String codPais) throws SyncconException;

}
