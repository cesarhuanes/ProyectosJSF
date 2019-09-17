package com.cardif.satelite.soat.sucursal.service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.soat.model.Propietario;

public interface PropietarioServiceSucursal {

  public Propietario obtener(String dni) throws SyncconException;

  public int insertar(Propietario propietario) throws SyncconException;

  public boolean actualizar(Propietario propietario) throws SyncconException;
}