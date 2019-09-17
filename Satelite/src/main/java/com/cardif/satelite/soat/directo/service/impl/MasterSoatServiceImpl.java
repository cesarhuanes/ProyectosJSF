/**
 * 
 */
package com.cardif.satelite.soat.directo.service.impl;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.MasterPreciosSoat;
import com.cardif.satelite.soat.directo.service.MasterSoatServiceDirecto;
import com.cardif.satelite.soat.falabella.dao.MasterPreciosSoatMapper;

@Service("masterSoatServiceDirecto")
public class MasterSoatServiceImpl implements MasterSoatServiceDirecto {

  public static final Logger log = Logger.getLogger(MasterSoatServiceImpl.class);

  @Autowired
  private MasterPreciosSoatMapper masterPreciosSoatMapper;

  @Override
  public MasterPreciosSoat obtener(String codSocio, String departamento, String uso, String categoria, int nroAsientos, String marca, String modelo) throws SyncconException {
    log.info("Inicio");
    MasterPreciosSoat masterPreciosSoat = null;
    try {
      if (log.isDebugEnabled())
        log.debug("Input [" + codSocio + "," + departamento + "," + uso + "," + categoria + "," + nroAsientos + "," + marca + "," + modelo + "]");
      masterPreciosSoat = masterPreciosSoatMapper.getPrecio(codSocio, departamento, uso, categoria, nroAsientos, marca, modelo);
      if (log.isDebugEnabled())
        log.debug("Output [" + BeanUtils.describe(masterPreciosSoat) + "]");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_OBTENER);
    }
    log.info("Fin");
    return masterPreciosSoat;
  }

}
