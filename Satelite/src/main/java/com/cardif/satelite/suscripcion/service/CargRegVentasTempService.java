package com.cardif.satelite.suscripcion.service;

import java.io.File;

import com.cardif.framework.excepcion.SyncconException;

/**
 * 
 * @author JOANHUVE
 * 
 */
public interface CargRegVentasTempService {

  public void insertarDatosExcel(File excelFile) throws SyncconException;

  public void deleteRows() throws SyncconException;

}
