/**
 * 
 */
package com.cardif.satelite.soat.rpp.service;

import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.callcenter.bean.ConsultaRegVehicular;
import com.cardif.satelite.soat.model.VehiculoPe;

public interface VehiculoPeServiceRpp {

  public List<ConsultaRegVehicular> buscarPlaca(String placa) throws SyncconException;

  public int insertarVehiculo(VehiculoPe vehiculoPe) throws SyncconException;

  public VehiculoPe findVehiculo(VehiculoPe vehiculoPe) throws SyncconException;

  public int actualizar(VehiculoPe vehiculoPe) throws SyncconException;

}
