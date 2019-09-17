/**
 * 
 */
package com.cardif.satelite.configuracion.service.impl;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.configuracion.dao.ParametroMapper;
import com.cardif.satelite.configuracion.service.ParametroService;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.Parametro;

@Service("parametroService")
public class ParametroServiceImpl implements ParametroService {

  public static final Logger log = Logger.getLogger(ParametroServiceImpl.class);

  @Autowired
  private ParametroMapper parametroMapper;

  /*
   * (non-Javadoc)
   * 
   * @see com.cardif.satelite.configuracion.service.ParametroService#buscar(java .lang.String, java.lang.String)
   */
  @Override
  public List<Parametro> buscar(String codParam, String tipParam) throws SyncconException {
    log.info("Inicio");
    List<Parametro> lista = null;
    int size = 0;
    try {
      if (log.isDebugEnabled())
        log.debug("Input [1.-" + codParam + ", 2.-" + tipParam+"]");
      lista = parametroMapper.selectParametro(codParam, tipParam);
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
  public Parametro obtener(String codParam, String tipParam, String codValor) throws SyncconException {
    log.info("Inicio");
    Parametro param = null;
    try {
      if (log.isDebugEnabled())
        log.debug("Input [1.-" + codParam + ", 2.-" + tipParam + ", 3.-" + codValor+"]");
      param = parametroMapper.obtenerParametro(codParam, tipParam, codValor);
      if (log.isDebugEnabled())
        log.debug("Output [ " + BeanUtils.describe(param) + "]");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_OBTENER);
    }
    log.info("Fin");
    return param;
  }

  @Override
  public String obtenerDescripcion(String codSocio, String codParam, String tipParam) throws SyncconException {
    log.info("Inicio");
    String socioDescripcion = null;
    try {
      if (log.isDebugEnabled())
        log.debug("Input [1.-" + codSocio + " 2.-" + codParam + " 3.-" + tipParam + " ]");
      socioDescripcion = parametroMapper.consultarDescripcion(codSocio, codParam, tipParam);
      if (log.isDebugEnabled())
        log.debug("Output [ " + socioDescripcion + "]");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_OBTENER);
    }
    log.info("Fin");
    return socioDescripcion;
  }

  @Override
  public List<Parametro> selectMediosPago(String codParam, String tipParam) {
    return parametroMapper.selectMediosPago(codParam, tipParam);
  }
}
