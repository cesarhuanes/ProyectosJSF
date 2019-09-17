package com.cardif.satelite.siniestro.service.impl;

import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_OBTENER;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.SiniObjetos;
import com.cardif.satelite.siniestro.dao.SiniObjetosMapper;
import com.cardif.satelite.siniestro.service.SiniObjetosService;

@Service("siniObjetosService")
public class SiniObjetosServiceImpl implements SiniObjetosService {
  public static final Logger log = Logger.getLogger(SiniObjetosServiceImpl.class);
  @Autowired
  SiniObjetosMapper siniObjetosMapper;

  @Override
  public SiniObjetos obtener(int codObjeto) throws SyncconException {
    log.info("Inicio");
    log.info("codObjeto :" + codObjeto);

    SiniObjetos siniObjetos = new SiniObjetos();
    try {
      siniObjetos = siniObjetosMapper.obtener(codObjeto);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_OBTENER);
    }
    log.info("Fin");

    return siniObjetos;
  }

}
