/**
 * 
 */
package com.cardif.satelite.soat.falabella.service;

import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.soat.model.Distrito;

/**
 * @author 2ariasju
 *
 */
public interface DistritoService {

  /**
   * @param codProvincia
   * @return
   * @throws SyncconException
   */
  public List<Distrito> buscar(String codProvincia) throws SyncconException;

  public Distrito obtener(String codDistrito) throws SyncconException;

  public Distrito obtenerPorNom(String nomDistrito, String codProvincia) throws SyncconException;

}
