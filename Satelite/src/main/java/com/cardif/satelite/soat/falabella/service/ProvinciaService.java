package com.cardif.satelite.soat.falabella.service;

import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.soat.model.Provincia;

public interface ProvinciaService {

  public List<Provincia> buscar(String codDepartamento) throws SyncconException;

  public Provincia obtener(String codProvincia) throws SyncconException;

  public Provincia obtenerPorNom(String nomProvincia, String codDepartamento) throws SyncconException;
}
