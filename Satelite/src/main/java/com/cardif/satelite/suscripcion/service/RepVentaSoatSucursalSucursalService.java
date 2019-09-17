package com.cardif.satelite.suscripcion.service;

import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.soatsucursal.Sucursal;

public interface RepVentaSoatSucursalSucursalService {
  List<Sucursal> listar() throws SyncconException;

  List<Sucursal> listarPorSocio(String socio) throws SyncconException;
}
