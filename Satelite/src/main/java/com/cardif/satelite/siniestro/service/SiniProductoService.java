package com.cardif.satelite.siniestro.service;

import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.SiniProducto;

public interface SiniProductoService {

  public List<SiniProducto> listar() throws SyncconException;

  public List<SiniProducto> listar(String codRamo) throws SyncconException;

  public SiniProducto obtener(String nroProducto) throws SyncconException;
}
