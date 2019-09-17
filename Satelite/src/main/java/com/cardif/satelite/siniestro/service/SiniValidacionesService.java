package com.cardif.satelite.siniestro.service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.SiniValidaciones;

public interface SiniValidacionesService {

  public SiniValidaciones obtener(String nroProducto, String codSocio) throws SyncconException;
}
