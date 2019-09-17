package com.cardif.satelite.siniestro.service;

import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.SiniPoliza;

public interface SiniPolizaService {
  SiniPoliza listar(String nroSiniestro) throws SyncconException;

  List<SiniPoliza> listar() throws SyncconException;

  public int actualizarSiniPoliza(SiniPoliza siniPoliza) throws SyncconException;

  public int insertarSiniPoliza(SiniPoliza siniPoliza) throws SyncconException;

  public int eliminarSiniPoliza(String siniPoliza) throws SyncconException;

  public SiniPoliza obtener(String nroSiniestro) throws SyncconException;
}
