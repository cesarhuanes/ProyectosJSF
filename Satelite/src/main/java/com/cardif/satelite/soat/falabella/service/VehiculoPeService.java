/**
 * 
 */
package com.cardif.satelite.soat.falabella.service;

import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.callcenter.bean.ConsultaRegVehicular;
import com.cardif.satelite.soat.model.VehiculoPe;

/**
 * @author 2ariasju
 * 
 */
public interface VehiculoPeService {

  public VehiculoPe obtener(String placa) throws SyncconException;

  public ConsultaRegVehicular buscarPropietario(String dni) throws SyncconException;

  public List<ConsultaRegVehicular> buscarPlaca(String placa) throws SyncconException;

  /**
   * @author jhurtado
   * @descripcion inserta registros a la tabla vehiculo y devuelve el numero de filas afectadas
   */
  public int insertarVehiculo(VehiculoPe vehiculoPe) throws SyncconException;

  /**
   * @author jhurtado
   * @descripcion busca vehiculo por placa
   */
  public VehiculoPe findVehiculo(VehiculoPe vehiculoPe) throws SyncconException;

  public int actualizar(VehiculoPe vehiculoPe) throws SyncconException;

}
