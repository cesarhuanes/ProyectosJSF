package com.cardif.satelite.segya.service;

import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.segya.Socio;

public interface SocioService {

  public List<Socio> buscar() throws SyncconException;

  public Socio obtener(Integer codSocio) throws SyncconException;

}
