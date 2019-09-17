package com.cardif.satelite.suscripcion.service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.AuditCarRegVentas;

public interface AuditCarRegVentasService {

  public boolean consultaPeriodo(int periodo) throws SyncconException;

  public String ultimoPeriodo() throws SyncconException;

  public void insertar(AuditCarRegVentas auditCarRegVentas) throws SyncconException;

}
