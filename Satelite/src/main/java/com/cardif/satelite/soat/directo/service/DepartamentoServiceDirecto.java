/**
 * 
 */
package com.cardif.satelite.soat.directo.service;

import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.soat.model.Departamento;

/**
 * @modificacion jhurtado
 * @descripcion se cambio el metodo obtener con el tipo de dato de la tabla Departamento
 * @fecha 13/02/2014
 */
public interface DepartamentoServiceDirecto {

  public List<Departamento> buscar() throws SyncconException;

  public Departamento obtener(String codDepartamento) throws SyncconException;
  
  public Departamento obtenerPorNom(String nomDepartamento) throws SyncconException;

}
