package com.cardif.satelite.soat.service.impl;

import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_INSERTAR;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.soat.dao.BitacoraSoatMapper;
import com.cardif.satelite.soat.model.BitacoraSoat;
import com.cardif.satelite.soat.service.BitacoraSoatService;

@Service("bitacoraSoatService")
public class BitacoraSoatServiceImpl implements BitacoraSoatService {

  public static final Logger log = Logger.getLogger(BitacoraSoatServiceImpl.class);

  @Autowired
  BitacoraSoatMapper bitacoraSoatMapper;

  @Override
  public int insertar(BitacoraSoat bitacoraSoat) throws SyncconException {
    log.info("[ Inicio ]");
    int numRowsAfected = 0;
    try {
      if (log.isDebugEnabled())
        log.debug("Input [ " + BeanUtils.describe(bitacoraSoat).toString() + " ]");
      numRowsAfected = bitacoraSoatMapper.insert(bitacoraSoat);
      if (log.isDebugEnabled())
        log.debug("[ Output | Numero de filas Afectadas Bitacora Soat [ " + numRowsAfected + "] ]");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_INSERTAR);
    }

    log.info("[ Fin ]");
    return numRowsAfected;
  }

}
