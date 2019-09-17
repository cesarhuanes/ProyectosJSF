package com.cardif.satelite.siniestro.service;

import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.SiniDesempleo;

public interface SiniDesempleoService {
  SiniDesempleo listar(String nroSiniestro) throws SyncconException;

  List<SiniDesempleo> listar() throws SyncconException;

  public int actualizarSiniDesempleo(SiniDesempleo siniDesempleo) throws SyncconException;

  public int insertarSiniDesempleo(SiniDesempleo siniDesempleo) throws SyncconException;

  public int eliminarSiniDesempleo(String siniDesempleo) throws SyncconException;
}
