package com.cardif.satelite.configuracion.service;

import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.satelite.TipoTrabajador;

public interface TipoTrabajadorService {
  public List<TipoTrabajador> buscar() throws SyncconException;
}
