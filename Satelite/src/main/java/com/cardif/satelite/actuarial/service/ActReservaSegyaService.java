package com.cardif.satelite.actuarial.service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.ActReservaSegya;

public interface ActReservaSegyaService {
  /*
   * jhurtado cambio parametro de respuesta para controlar el numero de filas insertadas
   */
  public int insertar(ActReservaSegya actReservaSegya) throws SyncconException;

  public String consultaEstado() throws SyncconException;

}
