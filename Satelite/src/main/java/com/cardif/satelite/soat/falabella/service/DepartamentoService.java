package com.cardif.satelite.soat.falabella.service;

import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.soat.model.Departamento;

public interface DepartamentoService {

  public List<Departamento> buscar() throws SyncconException;

  public Departamento obtener(String codDepartamento) throws SyncconException;

  public Departamento obtenerPorNom(String nomDepartamento) throws SyncconException;

}
