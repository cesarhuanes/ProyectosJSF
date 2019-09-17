package com.cardif.satelite.actuarial.service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.ActReservaAcsele;

public interface ActReservaAcseleService {

  public int insertar(ActReservaAcsele actReservaAcsele) throws SyncconException;

  public String consultaEstado() throws SyncconException;
}
