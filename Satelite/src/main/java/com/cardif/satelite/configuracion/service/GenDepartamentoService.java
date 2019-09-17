package com.cardif.satelite.configuracion.service;

import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.satelite.GenDepartamento;

public interface GenDepartamentoService {
  public List<GenDepartamento> buscar() throws SyncconException;

  public GenDepartamento obtener(String codDepartamento) throws SyncconException;

  public List<GenDepartamento> buscar(String codPais) throws SyncconException;
}
