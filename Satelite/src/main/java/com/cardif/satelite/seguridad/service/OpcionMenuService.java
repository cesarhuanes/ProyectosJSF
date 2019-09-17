package com.cardif.satelite.seguridad.service;

import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.OpcionMenu;

public interface OpcionMenuService {

  public List<OpcionMenu> buscar() throws SyncconException;

  public List<OpcionMenu> buscar(Long codPerfil) throws SyncconException;

  public void eliminar(Long codOpcion) throws SyncconException;

  public void insertar(OpcionMenu opcionMenu) throws SyncconException;

  public void actualizar(OpcionMenu opcionMenu) throws SyncconException;

  public OpcionMenu obtener(Long codOpcion) throws SyncconException;

}
