package com.cardif.satelite.callcenter.service.impl;

import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_INSERTAR;
import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_OBTENER;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.callcenter.dao.AuditRegistroVehicularMapper;
import com.cardif.satelite.callcenter.service.AuditRegistroVehicularService;
import com.cardif.satelite.model.AuditRegistroVehicular;

@Service("auditRegistroVehicularService")
public class AuditRegistroVehicularServiceImpl implements AuditRegistroVehicularService {

  public static final Logger logger = Logger.getLogger(AuditRegistroVehicularServiceImpl.class);

  @Autowired
  AuditRegistroVehicularMapper auditRegistroVehicularMapper;

  @Override
  public void insertar(AuditRegistroVehicular auditRegistroVehicular) throws SyncconException {
    logger.info("[ Inicio ]");
    try {
      if (logger.isDebugEnabled())
        logger.debug("input [ " + BeanUtils.describe(auditRegistroVehicular).toString() + " ]");
      auditRegistroVehicularMapper.insert(auditRegistroVehicular);
      if (logger.isDebugEnabled())
        logger.debug("[ Output [ok] ]");
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_INSERTAR);
    }
    logger.info("[ Fin ]");

  }

  @Override
  public synchronized boolean actualizar(AuditRegistroVehicular auditRegistroVehicular) throws SyncconException {
    logger.info("[ Inicio ]");
    boolean exito = false;
    try {
      if (logger.isDebugEnabled())
        logger.debug("input [ " + BeanUtils.describe(auditRegistroVehicular).toString() + " ]");
      auditRegistroVehicularMapper.updateByPrimaryKey(auditRegistroVehicular);
      if (logger.isDebugEnabled())
        logger.debug("[ Output [ok] ]");
      exito = true;
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_INSERTAR);
    }
    logger.info("[ Fin ]");
    return exito;
  }

  @Override
  public synchronized AuditRegistroVehicular getPk(String idRegistro) throws SyncconException {
    logger.info("[ Inicio ]");
    AuditRegistroVehicular auditRegistroVehicular = null;
    try {
      if (logger.isDebugEnabled())
        logger.debug("input [ " + idRegistro + " ]");
      auditRegistroVehicular = auditRegistroVehicularMapper.selectPK(idRegistro);
      if (logger.isDebugEnabled())
        logger.debug("[ Output [" + BeanUtils.describe(auditRegistroVehicular) + "] ]");
      // logger.debug("[ Output [" + auditRegistroVehicular.getPk() +
      // "] ]");
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_INSERTAR);
    }
    logger.info("[ Fin ]");
    return auditRegistroVehicular;
  }

  /**
   * @author Modificacion: lmaron
   * @modificacion se agrego el Encontrar placa
   * @fecha 16/02/2014
   */

  @Override
  public AuditRegistroVehicular findPlaca(String placa) throws SyncconException {
    logger.info("Inicio");
    AuditRegistroVehicular auditRegistroVehicular = null;
    try {
      if (logger.isDebugEnabled())
        logger.debug("input [ " + BeanUtils.describe(placa).toString() + " ]");
      auditRegistroVehicular = auditRegistroVehicularMapper.selectAudit(placa);
      if (logger.isDebugEnabled())
        logger.debug("[ Output [ok] ]");
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_OBTENER);
    }
    logger.info("Fin");
    return auditRegistroVehicular;
  }

}
