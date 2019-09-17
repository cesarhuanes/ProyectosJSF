package com.cardif.satelite.configuracion.service;

import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.satelite.GenProvincia;

public interface GenProvinciaService {
  public List<GenProvincia> buscar(String codDepartamento) throws SyncconException;

  public GenProvincia obtener(String codProvincia) throws SyncconException;
}
