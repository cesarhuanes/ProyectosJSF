package com.cardif.satelite.soat.directo.service;

import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.soat.model.CategoriaClase;

public interface CategoriaClaseServiceDirecto {
  public List<CategoriaClase> buscar() throws SyncconException;

}
