package com.cardif.satelite.callcenter.service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.AuditRegistroVehicular;

public interface AuditRegistroVehicularService {

  public void insertar(AuditRegistroVehicular auditRegistroVehicular) throws SyncconException;

  public boolean actualizar(AuditRegistroVehicular auditRegistroVehicular) throws SyncconException;

  public AuditRegistroVehicular getPk(String idRegistro) throws SyncconException;

  /**
   * @usuario Modificacion: lmaron
   * @modificacion se agrego el metodo encontrar placa
   * @fecha 16/02/2014
   */
  public AuditRegistroVehicular findPlaca(String placa) throws SyncconException;

}
