package com.cardif.satelite.suscripcion.service;

import java.io.File;

import com.cardif.framework.excepcion.SyncconException;

public interface SusTramaAnulacionMensualService {

  public void actEstadosRegistros(File excelFile, Long codTrama) throws SyncconException;
}
