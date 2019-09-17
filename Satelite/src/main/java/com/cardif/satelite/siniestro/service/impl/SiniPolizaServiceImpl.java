package com.cardif.satelite.siniestro.service.impl;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.SiniPoliza;
import com.cardif.satelite.siniestro.dao.SiniPolizaMapper;
import com.cardif.satelite.siniestro.service.SiniPolizaService;

@Service("siniPolizaService")
public class SiniPolizaServiceImpl implements SiniPolizaService {
  public static final Logger log = Logger.getLogger(SiniPolizaServiceImpl.class);
  @Autowired
  private SiniPolizaMapper siniPolizaMapper;

  @Override
  public SiniPoliza listar(String nroSiniestro) throws SyncconException {
    SiniPoliza siniPoliza;
    siniPoliza = siniPolizaMapper.listar(nroSiniestro);
    return siniPoliza;
  }

  @Override
  public SiniPoliza obtener(String nroSiniestro) throws SyncconException {
    log.info("Inicio");
    SiniPoliza siniPoliza;
    try {
      if (log.isDebugEnabled())
        log.debug("Input [" + nroSiniestro + "]");
      siniPoliza = siniPolizaMapper.selectByPrimaryKey(nroSiniestro);
      if (log.isDebugEnabled())
        log.debug("Output [" + BeanUtils.describe(siniPoliza) + "]");

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_OBTENER);
    }
    log.info("Fin");
    return siniPoliza;
  }

  @Override
  public List<SiniPoliza> listar() throws SyncconException {
    log.info("Inicio");

    List<SiniPoliza> lista;
    try {
      lista = siniPolizaMapper.listarT();
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
    }
    log.info("Fin");
    return lista;
  }

  @Override
  public int actualizarSiniPoliza(SiniPoliza siniPoliza) throws SyncconException {
    log.info("Inicio");
    try {
      siniPolizaMapper.updateByPrimaryKeySelective(siniPoliza);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_ACTUALIZAR);
    }
    log.info("Fin");
    return 0;
  }

  @Override
  public int insertarSiniPoliza(SiniPoliza siniPoliza) throws SyncconException {
    log.info("Inicio");
    try {
      siniPolizaMapper.insert(siniPoliza);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_INSERTAR);
    }
    log.info("Fin");

    return 0;
  }

  @Override
  public int eliminarSiniPoliza(String siniPoliza) throws SyncconException {
    log.info("Inicio");
    try {
      siniPolizaMapper.deleteByPrimaryKey(siniPoliza);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_ELIMINAR);
    }
    log.info("Fin");
    return 0;
  }

}
