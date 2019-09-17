package com.cardif.satelite.configuracion.service;

import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.satelite.GenDistrito;

public interface GenDistritoService {
  public List<GenDistrito> buscar(String codProvincia) throws SyncconException;

  public GenDistrito obtener(String codDistrito) throws SyncconException;
}
