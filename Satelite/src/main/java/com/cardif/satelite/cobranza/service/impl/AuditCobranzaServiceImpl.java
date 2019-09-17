package com.cardif.satelite.cobranza.service.impl;

import java.util.ArrayList;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.cobranza.dao.AuditCobranzaMapper;
import com.cardif.satelite.cobranza.service.AuditCobranzaService;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.AuditCobranza;

@Service("auditCobranzaService")
public class AuditCobranzaServiceImpl implements AuditCobranzaService {

  public static final Logger log = Logger.getLogger(AuditCobranzaServiceImpl.class);

  @Autowired
  AuditCobranzaMapper auditCobranzaMapper;

  @Override
  public ArrayList<AuditCobranza> consultar(String tipProceso, String viaCobro) throws SyncconException {
    log.info("Inicio");
    ArrayList<AuditCobranza> lista = null;
    int size = 0;
    try {
      if (log.isDebugEnabled())
        log.debug("Input [1.-" + tipProceso + ", 2.-" + viaCobro + "]");

      lista = auditCobranzaMapper.consultar(tipProceso, viaCobro);

      size = (lista != null) ? lista.size() : 0;
      if (log.isDebugEnabled())
        log.debug("Output [Registros = " + size + "]");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
    }
    log.info("Fin");
    return lista;
  }

  @Override
  public void insertar(AuditCobranza auditCobranza) throws SyncconException {
    log.info("Inicio");
    try {
      if (log.isDebugEnabled())
        log.debug("Input [" + BeanUtils.describe(auditCobranza).toString() + "]");
      auditCobranzaMapper.insert(auditCobranza);
      if (log.isDebugEnabled())
        log.debug("Output [Ok]");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_INSERTAR);
    }
    log.info("Fin");
  }

  @Override
  public void actualizar(AuditCobranza auditCobranza) throws SyncconException {
    log.info("Inicio");
    try {
      if (log.isDebugEnabled())
        log.debug("Input [" + BeanUtils.describe(auditCobranza).toString() + "]");
      auditCobranzaMapper.updateByPrimaryKey(auditCobranza);
      if (log.isDebugEnabled())
        log.debug("Output [Ok]");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_ACTUALIZAR);
    }
    log.info("Fin");
  }

}
