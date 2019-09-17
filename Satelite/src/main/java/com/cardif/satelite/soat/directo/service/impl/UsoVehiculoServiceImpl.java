package com.cardif.satelite.soat.directo.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.soat.directo.dao.UsoVehiculoDirectoMapper;
import com.cardif.satelite.soat.directo.service.UsoVehiculoServiceDirecto;
import com.cardif.satelite.soat.model.UsoVehiculo;

@Service("usoVehiculoServiceDirecto")
public class UsoVehiculoServiceImpl implements UsoVehiculoServiceDirecto {

  public static final Logger log = Logger.getLogger(UsoVehiculoServiceImpl.class);

  @Autowired
  private UsoVehiculoDirectoMapper usoVehiculoDirectoMapper;

  List<UsoVehiculo> lista = null;

  @Override
  public List<UsoVehiculo> getListUsoVehiculo() throws SyncconException {
    log.info("Inicio");

    int size = 0;
    try {
      if (log.isDebugEnabled())
        log.debug("Input [" + size + "]");
      lista = usoVehiculoDirectoMapper.selectUsoVehiculo();
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
