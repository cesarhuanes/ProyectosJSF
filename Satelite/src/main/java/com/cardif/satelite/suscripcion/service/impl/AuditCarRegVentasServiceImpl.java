package com.cardif.satelite.suscripcion.service.impl;

import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_INSERTAR;
import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_OBTENER;

import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.AuditCarRegVentas;
import com.cardif.satelite.suscripcion.dao.AuditCarRegVentasMapper;
import com.cardif.satelite.suscripcion.service.AuditCarRegVentasService;

@Service("auditCarRegVentasService")
public class AuditCarRegVentasServiceImpl implements AuditCarRegVentasService {

  public static final Logger logger = Logger.getLogger(AuditCarRegVentasServiceImpl.class);

  private AuditCarRegVentas auditCarRegVentas;

  @Autowired
  private AuditCarRegVentasMapper auditCarRegVentasMapper;

  @Override
  public boolean consultaPeriodo(int periodo) throws SyncconException {
    logger.info("[ Inicio ]");
    boolean existe = false;
    try {
      if (auditCarRegVentasMapper.consultarPeriodo(periodo) > 0)
        existe = true;
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_OBTENER);
    }
    logger.info("[ Fin ]");
    return existe;
  }

  @Override
  public String ultimoPeriodo() throws SyncconException {
    logger.info("[ Inicio ]");
    String periodo;
    try {
      periodo = auditCarRegVentasMapper.ultimoPeriodo();
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_OBTENER);
    }
    logger.info("[ Fin ]");
    return periodo;
  }

  @Override
  public void insertar(AuditCarRegVentas auditCarRegVentas) throws SyncconException {
    logger.info("[ Inicio ]");
    try {
      if (logger.isDebugEnabled())
        logger.debug("input [ " + BeanUtils.describe(auditCarRegVentas).toString() + " ]");
      auditCarRegVentas.setFecCreacion(new Date(System.currentTimeMillis()));
      auditCarRegVentasMapper.insert(auditCarRegVentas);
      if (logger.isDebugEnabled())
        logger.debug("[ Output [ok] ]");
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_INSERTAR);
    }
    logger.info("[ Fin ]");
  }

  public AuditCarRegVentas getAuditCarRegVentas() {
    return auditCarRegVentas;
  }

  public void setAuditCarRegVentas(AuditCarRegVentas auditCarRegVentas) {
    this.auditCarRegVentas = auditCarRegVentas;
  }

}
