package com.cardif.satelite.configuracion.service.impl;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.configuracion.dao.GenDistritoMapper;
import com.cardif.satelite.configuracion.service.GenDistritoService;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.satelite.GenDistrito;

@Service("genDistritoService")
public class GenDistritoServiceImpl implements GenDistritoService {
  public static final Logger log = Logger.getLogger(GenDistritoServiceImpl.class);

  @Autowired
  private GenDistritoMapper genDistritoMapper;

  @Override
  public List<GenDistrito> buscar(String codProvincia) throws SyncconException {
    log.info("Inicio");
    List<GenDistrito> lista = null;
    int size = 0;
    try {
      if (log.isDebugEnabled())
        log.debug("Input [" + codProvincia + "]");
      lista = genDistritoMapper.selectDistrito(codProvincia);
      size = (lista != null) ? lista.size() : 0;
      if (log.isDebugEnabled())
        log.debug("Output [Regitros:" + size + "]");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
    }
    log.info("Fin");
    return lista;
  }

  @Override
  public GenDistrito obtener(String codDistrito) throws SyncconException {
    log.info("Inicio");
    GenDistrito distrito = null;
    try {
      if (log.isDebugEnabled())
        log.debug("Input [" + codDistrito + "]");
      distrito = genDistritoMapper.selectByPrimaryKey(codDistrito);
      if (log.isDebugEnabled())
        log.debug("Output [" + BeanUtils.describe(codDistrito) + "]");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_OBTENER);
    }
    log.info("Fin");
    return distrito;
  }
}
