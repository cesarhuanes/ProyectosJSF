package com.cardif.satelite.configuracion.service;

import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.Parametro;

/**
 * @author 2ariasju
 * 
 */
public interface ParametroService {

  List<Parametro> selectMediosPago(String codParam, String tipParam);

  public List<Parametro> buscar(String codParam, String tipParam) throws SyncconException;

  public Parametro obtener(String codParam, String tipParam, String codValor) throws SyncconException;

  public String obtenerDescripcion(String codSocio, String codParam, String tipParam) throws SyncconException;

}
