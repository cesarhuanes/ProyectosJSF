package com.cardif.satelite.ssis.service;

import com.cardif.framework.excepcion.SyncconException;

public interface SsisService {

  /**
   * Metodo que se utilizar para invocar a jobs de la base de datos
   * 
   * @param nombreJob
   * @throws SyncconException
   */
  public void lanzarJob(String nombreJob) throws SyncconException;

}