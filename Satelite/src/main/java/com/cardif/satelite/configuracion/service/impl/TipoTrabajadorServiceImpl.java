package com.cardif.satelite.configuracion.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.configuracion.dao.TipoTrabajadorMapper;
import com.cardif.satelite.configuracion.service.TipoTrabajadorService;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.satelite.TipoTrabajador;

@Service("tipoTrabajadorService")
public class TipoTrabajadorServiceImpl implements TipoTrabajadorService {
  public static final Logger log = Logger.getLogger(GenDepartamentoServiceImpl.class);

  @Autowired
  private TipoTrabajadorMapper tipoTrabajadorMapper;

  @Override
  public List<TipoTrabajador> buscar() throws SyncconException {
    log.info("Inicio");
    List<TipoTrabajador> lista = null;
    int size = 0;
    try {
      if (log.isDebugEnabled())
        log.debug("Input [" + "]");
      lista = tipoTrabajadorMapper.selectTipoTrabajador();
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
}
