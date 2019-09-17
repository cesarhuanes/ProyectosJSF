/**
 * 
 */
package com.cardif.satelite.soat.directo.service;

import java.math.BigDecimal;
import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.soat.model.ModeloVehiculo;

/**
 * @author 2ariasju
 *
 */
public interface ModeloVehiculoServiceDirecto {

  public ModeloVehiculo obtener(String codModelo) throws SyncconException;

  public List<ModeloVehiculo> buscarModelo(BigDecimal codMarca) throws SyncconException;

}
