package com.cardif.satelite.siniestro.service;

import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.SiniDesgravamen;

public interface SiniDesgravamenService {
  SiniDesgravamen listar(String nroSiniestro) throws SyncconException;

  List<SiniDesgravamen> listar() throws SyncconException;

  public int actualizarSiniDesgravamen(SiniDesgravamen siniDesgravamen) throws SyncconException;

  public int validaTipoTarjeta(String numeroTarjeta) throws SyncconException;

  public int insertarSiniDesgravamen(SiniDesgravamen siniDesgravamen) throws SyncconException;

  public int eliminarSiniDesgravamen(String siniDesgravamen) throws SyncconException;
}
