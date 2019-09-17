/**
 * 
 */
package com.cardif.satelite.configuracion.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.configuracion.dao.ErrorMapper;
import com.cardif.satelite.configuracion.service.ErrorService;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.Error;

@Service("errorService")
public class ErrorServiceImpl implements ErrorService {

  public static final Logger log = Logger.getLogger(ErrorServiceImpl.class);

  @Autowired
  private ErrorMapper errorMapper;

  @Override
  public List<Error> listar() throws SyncconException {
    log.info("Inicio");
    List<Error> lista = null;
    int size = 0;
    try {

      lista = errorMapper.selectError();
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
}
