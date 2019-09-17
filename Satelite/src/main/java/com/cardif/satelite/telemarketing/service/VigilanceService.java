package com.cardif.satelite.telemarketing.service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.telemarketing.bean.BaseVigilance;

public interface VigilanceService {

  Long getNumeroDeVigilance() throws SyncconException;

  Boolean algunaCargaVigilanceEstaPendiente() throws SyncconException;

  String buscarEstado(String codigoBase) throws SyncconException;

  void insertarBaseVigilance() throws SyncconException;

  String getCodigoBase() throws SyncconException;

  void actualizarBaseVigilance(String codigoBase) throws SyncconException;

  BaseVigilance getUltimoBaseVigilance() throws SyncconException;

}
