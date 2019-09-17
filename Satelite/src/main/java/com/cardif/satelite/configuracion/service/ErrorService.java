package com.cardif.satelite.configuracion.service;

import java.util.List;

import com.cardif.framework.excepcion.SyncconException;

/**
 * @author 2ariasju
 * 
 */
public interface ErrorService {

  public List<com.cardif.satelite.model.Error> listar() throws SyncconException;
}
