package com.cardif.satelite.siniestro.service;

import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.SiniDatosCafae;

public interface SiniDatosCafaeService {
  SiniDatosCafae listar(String nroSiniestro) throws SyncconException;

  List<SiniDatosCafae> listar() throws SyncconException;

  public int actualizarSiniDatosCafae(SiniDatosCafae siniDatosCafae) throws SyncconException;

  public int insertarSiniDatosCafae(SiniDatosCafae siniDatosCafae) throws SyncconException;

  public int eliminarSiniSiniDatosCafae(String nroSiniestro) throws SyncconException;
}
