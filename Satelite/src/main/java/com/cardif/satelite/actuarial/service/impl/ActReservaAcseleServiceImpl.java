package com.cardif.satelite.actuarial.service.impl;

import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_INSERTAR;
import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_OBTENER;

import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.actuarial.dao.ActReservaAcseleMapper;
import com.cardif.satelite.actuarial.service.ActReservaAcseleService;
import com.cardif.satelite.model.ActReservaAcsele;

@Service("actReservaAcseleService")
public class ActReservaAcseleServiceImpl implements ActReservaAcseleService {

  public static final Logger logger = Logger.getLogger(ActReservaAcseleServiceImpl.class);

  @Autowired
  private ActReservaAcseleMapper actReservaAcseleMapper;

  @Override
  public int insertar(ActReservaAcsele actReservaAcsele) throws SyncconException {
    int numRowsAfected = 0;
    logger.info("[ Inicio ]");

    try {
      logger.debug("Input: [" + BeanUtils.describe(actReservaAcsele) + "]");
      actReservaAcsele.setFecCreacion(new Date(System.currentTimeMillis()));
      numRowsAfected = actReservaAcseleMapper.insert(actReservaAcsele);
      logger.info("[ NUMERO DE FILAS AFECTAS ACSEL-E : " + numRowsAfected + " ]");
      logger.debug("Output: [Ok]");
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_INSERTAR);
    }

    logger.info("[ Fin ]");
    return numRowsAfected;

  }

  @Override
  public String consultaEstado() throws SyncconException {
    logger.info("[ Inicio ]");
    String estado;
    try {
      estado = actReservaAcseleMapper.consultaEstado();
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_OBTENER);
    }
    logger.info("[ Fin ]");
    return estado;
  }

}
