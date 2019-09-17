package com.cardif.satelite.configuracion.service.impl;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.configuracion.dao.GenPaisMapper;
import com.cardif.satelite.configuracion.service.GenPaisService;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.satelite.GenPais;

@Service("genPaisService")
public class GenPaisServiceImpl implements GenPaisService {
  public static final Logger log = Logger.getLogger(GenPaisServiceImpl.class);

  @Autowired
  private GenPaisMapper genPaisMapper;

  @Override
  public List<GenPais> buscar() throws SyncconException {
    log.info("Inicio");
    List<GenPais> lista = null;
    int size = 0;
    try {
      if (log.isDebugEnabled())
        log.debug("Input [" + "]");
      lista = genPaisMapper.selectPais();
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
  public GenPais obtener(String codPais) throws SyncconException {
    log.info("Inicio");
    GenPais pais = null;
    try {
      if (log.isDebugEnabled())
        log.debug("Input [" + codPais + "]");
      pais = genPaisMapper.selectByPrimaryKey(codPais);
      if (log.isDebugEnabled())
        log.debug("Output [" + BeanUtils.describe(pais) + "]");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_OBTENER);
    }
    log.info("Fin");
    return pais;
  }

}
