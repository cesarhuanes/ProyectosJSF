package com.cardif.satelite.suscripcion.service;

import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.suscripcion.bean.RepVentaSoatSucursalBean;

public interface RepVentaSoatSucursalService {
  List<RepVentaSoatSucursalBean> listarReporteSoatSucursal(String desde, String hasta, String socio, String sucursal) throws SyncconException;

  List<RepVentaSoatSucursalBean> listarReporteSoatSucursal(String desde, String hasta, String socio) throws SyncconException;
}
