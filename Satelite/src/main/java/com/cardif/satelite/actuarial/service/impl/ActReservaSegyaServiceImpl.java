package com.cardif.satelite.actuarial.service.impl;

import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_INSERTAR;
import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_OBTENER;

import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.actuarial.dao.ActReservaSegyaMapper;
import com.cardif.satelite.actuarial.service.ActReservaSegyaService;
import com.cardif.satelite.model.ActReservaSegya;

@Service("actReservaSegyaService")
public class ActReservaSegyaServiceImpl implements ActReservaSegyaService {

  public static final Logger log = Logger.getLogger(ActCoaseguroServiceImpl.class);
  @Autowired
  private ActReservaSegyaMapper actReservaSegyaMapper;

  @Override
  public int insertar(ActReservaSegya actReservaSegya) throws SyncconException {
    int numRowsAfected = 0;
    log.info("inicio");

    try {
      log.debug("Input: [" + BeanUtils.describe(actReservaSegya) + "]");
      actReservaSegya.setFecCreacion(new Date(System.currentTimeMillis()));
      numRowsAfected = actReservaSegyaMapper.insert(actReservaSegya);
      log.info("[ NUMERO DE FILAS AFECTAS : " + numRowsAfected + "]");
      log.debug("Output: [Ok]");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_INSERTAR);
    }

    log.info("Fin");
    return numRowsAfected;
  }

  @Override
  public String consultaEstado() throws SyncconException {
    log.info("[ Inicio ]");
    String estado;
    try {
      estado = actReservaSegyaMapper.consultaEstado();
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_OBTENER);
    }
    log.info("[ Fin ]");
    return estado;
  }

}
