package com.cardif.satelite.siniestro.service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.SiniPoliza;
import com.cardif.satelite.model.SiniSiniestro;

public interface SiniestrosManager {

  public int insertarSiniestroPoliza(SiniSiniestro siniSiniestro, SiniPoliza siniPoliza) throws SyncconException;

}
