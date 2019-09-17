/**
 * 
 */
package com.cardif.satelite.soat.directo.service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.MasterPreciosSoat;

/**
 * @author 2ariasju
 *
 */
public interface MasterSoatServiceDirecto {

  public MasterPreciosSoat obtener(String codSocio, String departamento, String uso, String categoria, int nroAsientos, String marca, String modelo) throws SyncconException;

}
