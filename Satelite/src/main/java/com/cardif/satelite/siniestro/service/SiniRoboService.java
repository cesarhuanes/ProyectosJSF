package com.cardif.satelite.siniestro.service;

import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.SiniRobo;

public interface SiniRoboService {
  SiniRobo listar(String nroSiniestro) throws SyncconException;

  List<SiniRobo> listar() throws SyncconException;

  public int actualizarSiniRobo(SiniRobo siniRobo) throws SyncconException;

  public int insertarSiniRobo(SiniRobo siniRobo) throws SyncconException;

  public int eliminarSiniRobo(String siniRobo) throws SyncconException;
}
