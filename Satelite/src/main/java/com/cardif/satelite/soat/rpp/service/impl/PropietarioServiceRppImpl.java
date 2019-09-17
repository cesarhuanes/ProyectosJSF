package com.cardif.satelite.soat.rpp.service.impl;

import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_INSERTAR;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.soat.model.Propietario;
import com.cardif.satelite.soat.rpp.dao.PropietarioRppMapper;
import com.cardif.satelite.soat.rpp.service.PropietarioServiceRpp;

/*
 * Usuario_Creacion: lmaron
 * fecha: 07/02/2014
 * Ticket: 
 * */

@Service("propietarioServiceRpp")
public class PropietarioServiceRppImpl implements PropietarioServiceRpp {

  public static final Logger log = Logger.getLogger(PropietarioServiceRppImpl.class);
  @Autowired
  private PropietarioRppMapper propietarioRppMapper;

  @Override
  public Propietario obtener(String dni) throws SyncconException {
    log.info("Inicio");
    Propietario propietario = null;
    try {
      if (log.isDebugEnabled())
        log.debug("Input [" + dni + "]");

      propietario = propietarioRppMapper.selectPropietario(dni);

      if (log.isDebugEnabled())
        log.debug("Output [" + BeanUtils.describe(propietario) + "]");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_OBTENER);
    }
    log.info("Fin");
    return propietario;
  }

  /**
   * @autor jhurtado
   * @descripcion metodo que permite ingresar un propietario si es que no existe
   * @fecha 10/02/2014
   */

  @Override
  public int insertar(Propietario propietario) throws SyncconException {
    log.info("[ Inicio ]");
    int numRowsAfected = 0;
    try {
      if (log.isDebugEnabled())
        log.debug("Input [ " + BeanUtils.describe(propietario).toString() + " ]");
      numRowsAfected = propietarioRppMapper.insert(propietario);
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
      propietarioRppMapper.updateByPrimaryKey(propietario);
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
}
