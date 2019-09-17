package com.cardif.satelite.soat.directo.service;

import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.soat.model.UsoVehiculo;

public interface UsoVehiculoServiceDirecto {

  public List<UsoVehiculo> getListUsoVehiculo() throws SyncconException;

  // public List<UsoVehiculoDirecto> getListUsoVehiculoByCodUso(String codUso) throws SyncconException;

}
