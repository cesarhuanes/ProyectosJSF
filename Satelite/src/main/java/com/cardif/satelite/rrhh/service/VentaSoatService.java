/**
 * 
 */
package com.cardif.satelite.rrhh.service;

import java.util.Date;
import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.VentaSoatEmpl;

/**
 * @author 2ariasju
 *
 */
public interface VentaSoatService {

  public void insertar(VentaSoatEmpl ventaSoatEmpl) throws SyncconException;

  public VentaSoatEmpl obtener(String nroPoliza) throws SyncconException;

  public void actualizar(VentaSoatEmpl ventaSoatEmpl) throws SyncconException;

  public String obtenerMaxPoliza() throws SyncconException;

  public List<VentaSoatEmpl> listar() throws SyncconException;

  public List<VentaSoatEmpl> buscar(Date desde, Date hasta, String razonSocial) throws SyncconException;

}
