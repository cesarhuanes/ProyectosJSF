package com.cardif.satelite.soat.directo.service;

import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.soat.model.MarcaVehiculo;

public interface MarcaVehiculoServiceDirecto {

  public List<MarcaVehiculo> buscar() throws SyncconException;

  public MarcaVehiculo obtener(String codMarca) throws SyncconException;

}
