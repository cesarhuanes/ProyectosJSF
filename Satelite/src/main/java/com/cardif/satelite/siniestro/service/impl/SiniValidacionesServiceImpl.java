package com.cardif.satelite.siniestro.service.impl;

import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_OBTENER;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.SiniValidaciones;
import com.cardif.satelite.siniestro.dao.SiniValidacionesMapper;
import com.cardif.satelite.siniestro.service.SiniValidacionesService;

@Service("siniValidacionesService")
public class SiniValidacionesServiceImpl implements SiniValidacionesService {
  public static final Logger log = Logger.getLogger(SiniValidacionesServiceImpl.class);
  @Autowired
  SiniValidacionesMapper siniValidacionesMapper;

  @Override
  public SiniValidaciones obtener(String nroProducto, String codSocio) throws SyncconException {
    log.info("Inicio");
    SiniValidaciones siniValidaciones = null;
    try {
      siniValidaciones = siniValidacionesMapper.obtener(nroProducto, codSocio);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_OBTENER);
    }
    log.info("Fin");

    return siniValidaciones;
  }

}
