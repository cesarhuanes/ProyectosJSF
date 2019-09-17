package com.cardif.satelite.suscripcion.service;

import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.soatsucursal.Socio;

public interface RepVentaSoatSucursalSocioService {
  List<Socio> listarSocio() throws SyncconException;
}
