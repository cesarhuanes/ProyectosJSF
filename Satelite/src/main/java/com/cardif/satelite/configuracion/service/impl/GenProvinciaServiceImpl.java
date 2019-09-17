package com.cardif.satelite.configuracion.service.impl;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.configuracion.dao.GenProvinciaMapper;
import com.cardif.satelite.configuracion.service.GenProvinciaService;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.satelite.GenProvincia;

@Service("genProvinciaService")
public class GenProvinciaServiceImpl implements GenProvinciaService {
  private final Logger logger = Logger.getLogger(GenProvinciaServiceImpl.class);

  @Autowired
  private GenProvinciaMapper genProvinciaMapper;

  @Override
  public List<GenProvincia> buscar(String codDepartamento) throws SyncconException {
    if (logger.isInfoEnabled()) {
      logger.info("Inicio");
    }
    List<GenProvincia> lista = null;
    int size = 0;

    try {
      if (logger.isDebugEnabled()) {
        logger.debug("Input [" + codDepartamento + "]");
      }
      lista = genProvinciaMapper.selectProvincia(codDepartamento);
      size = (lista != null) ? lista.size() : 0;

      if (logger.isDebugEnabled()) {
        logger.debug("Output [Regitros:" + size + "]");
      }
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
    }
    logger.info("Fin");
    return lista;
  }

  @Override
  public GenProvincia obtener(String codProvincia) throws SyncconException {
    if (logger.isInfoEnabled()) {
      logger.info("Inicio");
    }
    GenProvincia provincia = null;

    try {
      if (logger.isDebugEnabled()) {
        logger.debug("Input [" + codProvincia + "]");
      }
      provincia = genProvinciaMapper.selectByPrimaryKey(codProvincia);

      if (logger.isDebugEnabled()) {
        logger.debug("Output [" + BeanUtils.describe(provincia) + "]");
      }
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_OBTENER);
    }
    if (logger.isInfoEnabled()) {
      logger.info("Fin");
    }
    return provincia;
  }
}
