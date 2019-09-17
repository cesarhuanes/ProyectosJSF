package com.cardif.satelite.soat.falabella.service.impl;

import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_INSERTAR;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.callcenter.bean.ConsultaRegPropietario;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.soat.falabella.dao.PropietarioMapper;
import com.cardif.satelite.soat.falabella.service.PropietarioService;
import com.cardif.satelite.soat.model.Propietario;

@Service("propietarioService")
public class PropietarioServiceImpl implements PropietarioService {

  public static final Logger log = Logger.getLogger(PropietarioServiceImpl.class);
  @Autowired
  private PropietarioMapper propietarioMapper;

  @Override
  public ConsultaRegPropietario obtener(String dni) throws SyncconException {
    log.info("Inicio");
    int size = 0;
    ConsultaRegPropietario listapropietario = null;
    try {
      if (log.isDebugEnabled())
        log.debug("Input [" + dni + "]");
      listapropietario = propietarioMapper.selectPropietario(dni);
      size = (listapropietario != null) ? listapropietario.toString().length() : 0;
      if (log.isDebugEnabled())
        log.debug("Output [Regitros:" + size + "]");
      if (log.isDebugEnabled())
        log.debug("Output [" + BeanUtils.describe(dni) + "]");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_OBTENER);
    }
    log.info("Fin");
    return listapropietario;
  }

  @Override
  public int insertar(Propietario propietario) throws SyncconException {
    log.info("[ Inicio ]");
    int numRowsAfected = 0;
    try {
      if (log.isDebugEnabled())
        log.debug("Input [ " + BeanUtils.describe(propietario).toString() + " ]");
      numRowsAfected = propietarioMapper.insert(propietario);
      if (log.isDebugEnabled())
        log.debug("[ Output | [ " + numRowsAfected + "] ]");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_INSERTAR);
    }
    log.info("[ Fin ]");
    return numRowsAfected;
  }

  @Override
  public boolean actualizar(Propietario propietario) throws SyncconException {
    log.info("[ Inicio ]");
    boolean flag;
    try {
      if (log.isDebugEnabled())
        log.debug("Input [ " + BeanUtils.describe(propietario).toString() + " ]");
      propietarioMapper.updateByPrimaryKey(propietario);
      flag = true;
      if (log.isDebugEnabled())
        log.debug("[ Output | [ " + flag + "] ]");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_INSERTAR);
    }
    log.info("[ Fin ]");
    return flag;
  }

  @Override
  public Propietario obtenerPropietario(String dni) throws SyncconException {
    log.info("Inicio");
    int size = 0;
    Propietario propietario = null;
    try {
      if (log.isDebugEnabled())
        log.debug("Input [" + dni + "]");
      propietario = propietarioMapper.obtener(dni);
      size = (propietario != null) ? propietario.toString().length() : 0;
      if (log.isDebugEnabled())
        log.debug("Output [Regitros:" + size + "]");
      if (log.isDebugEnabled())
        log.debug("Output [" + BeanUtils.describe(dni) + "]");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_OBTENER);
    }
    log.info("Fin");
    return propietario;
  }
}
