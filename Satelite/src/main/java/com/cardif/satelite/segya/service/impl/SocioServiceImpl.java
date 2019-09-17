package com.cardif.satelite.segya.service.impl;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.segya.Socio;
import com.cardif.satelite.segya.dao.SocioMapper;
import com.cardif.satelite.segya.service.SocioService;

@Service("socioService")
public class SocioServiceImpl implements SocioService {
  private static final Logger log = Logger.getLogger(SocioServiceImpl.class);
  @Autowired
  private SocioMapper socioMapper;

  @Override
  public List<Socio> buscar() throws SyncconException {
    log.info("Inicio");
    List<Socio> lista = null;
    int size = 0;
    try {
      if (log.isDebugEnabled())
        log.debug("Input [" + "]");
      lista = socioMapper.selectSocio();
      size = (lista != null) ? lista.size() : 0;
      if (log.isDebugEnabled())
        log.debug("Output [Regitros:" + size + "]");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
    }
    log.info("fin");
    return lista;
  }

  @Override
  public Socio obtener(Integer codSocio) throws SyncconException {
    log.info("Inicio");
    Socio socio = null;
    try {
      if (log.isDebugEnabled())
        log.debug("Input [" + codSocio + "]");
      socio = socioMapper.selectByPrimaryKey(codSocio);
      if (log.isDebugEnabled())
        log.debug("Output [" + BeanUtils.describe(codSocio) + "]");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_OBTENER);
    }
    log.info("Fin");
    return socio;
  }

}
