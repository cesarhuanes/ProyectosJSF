/**
 * 
 */
package com.cardif.satelite.soat.directo.service;

import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.callcenter.bean.ConsultaRegVehicular;
import com.cardif.satelite.soat.model.VehiculoPe;

/**
 * @author 2ariasju
 * 
 */
public interface VehiculoPeServiceDirecto {

  // public VehiculoPeDirecto obtener(String placa) throws SyncconException;
  //
  // public ConsultaRegVehicular buscarPropietario(String dni) throws SyncconException;

  public List<ConsultaRegVehicular> buscarPlaca(String placa) throws SyncconException;

  public int insertarVehiculo(VehiculoPe vehiculoPe) throws SyncconException;

  public VehiculoPe findVehiculo(VehiculoPe vehiculoPe) throws SyncconException;

  public int actualizar(VehiculoPe vehiculoPe) throws SyncconException;

}
