package com.cardif.satelite.soat.comparabien.service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.soat.model.DireccionPropietarioVehiculo;
import com.cardif.satelite.soat.model.VehiculoPe;

public interface DireccionPropietarioVehiculoServiceComparaBien {

  public int insertar(DireccionPropietarioVehiculo direccionPropietarioVehiculo) throws SyncconException;

  public DireccionPropietarioVehiculo getDireccionPropietarioVehiculo(VehiculoPe vehiculope) throws SyncconException;

  public int actualizar(DireccionPropietarioVehiculo direccionPropietarioVehiculo) throws SyncconException;

}
