/**
 * 
 */
package com.cardif.satelite.upload.service;

import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.upload.Cliente;

/**
 * @author 2ariasju
 *
 */
public interface ClienteService {

  public List<Cliente> buscar(Long socioId, Long sequence) throws SyncconException;

  public List<Cliente> buscarParcial(Long socioId, Long sequence, int inicio, int cantidad) throws SyncconException;

}
