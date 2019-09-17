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
import com.cardif.satelite.soat.falabella.dao.ProvinciaMapper;
import com.cardif.satelite.soat.falabella.service.ProvinciaService;
import com.cardif.satelite.soat.model.Provincia;

/**
 * @author 2ariasju
 *
 */
@Service("provinciaService")
public class ProvinciaServiceImpl implements ProvinciaService {

  public static final Logger log = Logger.getLogger(ProvinciaServiceImpl.class);

  @Autowired
  private ProvinciaMapper provinciaMapper;

  @Override
  public List<Provincia> buscar(String codDepartamento) throws SyncconException {
    log.info("Inicio");
    List<Provincia> lista = null;
    int size = 0;
    try {
      if (log.isDebugEnabled())
        log.debug("Input [" + "]");
      lista = provinciaMapper.selectProvincia(codDepartamento);
      // lista = provinciaMapper.selectProvincia("15");
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
  public Provincia obtener(String codProvincia) throws SyncconException {
    log.info("Inicio");
    Provincia provincia = null;
    try {
      if (log.isDebugEnabled())
        log.debug("Input [" + codProvincia + "]");
      provincia = provinciaMapper.selectByPrimaryKey(codProvincia);
      if (log.isDebugEnabled())
        log.debug("Output [" + BeanUtils.describe(provincia) + "]");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_OBTENER);
    }
    log.info("Fin");
    return provincia;
  }

  @Override
  public Provincia obtenerPorNom(String nomProvincia, String codDepartamento) throws SyncconException {
    log.info("Inicio");
    Provincia provincia = null;
    try {
      if (log.isDebugEnabled())
        log.debug("Input [" + nomProvincia + "]");
      provincia = provinciaMapper.selectProvinciaNom(nomProvincia, codDepartamento);

      if (log.isDebugEnabled())
        log.debug("Output [" + BeanUtils.describe(provincia) + "]");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_OBTENER);
    }
    log.info("Fin");
    return provincia;
  }

}
