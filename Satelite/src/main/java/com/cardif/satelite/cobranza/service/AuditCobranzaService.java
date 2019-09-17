package com.cardif.satelite.cobranza.service;

import java.util.ArrayList;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.AuditCobranza;

public interface AuditCobranzaService {

  public ArrayList<AuditCobranza> consultar(String tipProceso, String viaCobro) throws SyncconException;

  public void insertar(AuditCobranza auditCobranza) throws SyncconException;

  public void actualizar(AuditCobranza auditCobranza) throws SyncconException;
}
