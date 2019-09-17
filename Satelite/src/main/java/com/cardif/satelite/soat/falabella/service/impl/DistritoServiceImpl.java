/**
 * 
 */
package com.cardif.satelite.soat.falabella.service.impl;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.soat.falabella.dao.DistritoMapper;
import com.cardif.satelite.soat.falabella.service.DistritoService;
import com.cardif.satelite.soat.model.Distrito;

@Service("distritoService")
public class DistritoServiceImpl implements DistritoService {

  public static final Logger log = Logger.getLogger(DistritoServiceImpl.class);

  @Autowired
  private DistritoMapper distritoMapper;

  @Override
  public List<Distrito> buscar(String codProvincia) throws SyncconException {
    log.info("Inicio");
    List<Distrito> lista = null;
    int size = 0;
    try {
      if (log.isDebugEnabled())
        log.debug("Input [" + codProvincia + "]");
      lista = distritoMapper.selectDistrito(codProvincia);
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
  public Distrito obtener(String codDistrito) throws SyncconException {
    log.info("Inicio");
    Distrito distrito = null;
    try {
      if (log.isDebugEnabled())
        log.debug("Input [" + codDistrito + "]");
      distrito = distritoMapper.selectByPrimaryKey(codDistrito);
      if (log.isDebugEnabled())
        log.debug("Output [" + BeanUtils.describe(codDistrito) + "]");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_OBTENER);
    }
    log.info("Fin");
    return distrito;
  }

  @Override
  public Distrito obtenerPorNom(String nomDistrito, String codProvincia) throws SyncconException {
    log.info("Inicio");
    Distrito distrito = null;
    try {
      if (log.isDebugEnabled())
        log.debug("Input [" + nomDistrito + "]");
      distrito = distritoMapper.selectDistritoNom(nomDistrito, codProvincia);
      if (log.isDebugEnabled())
        log.debug("Output [" + BeanUtils.describe(nomDistrito) + "]");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_OBTENER);
    }
    log.info("Fin");
    return distrito;
  }

}
