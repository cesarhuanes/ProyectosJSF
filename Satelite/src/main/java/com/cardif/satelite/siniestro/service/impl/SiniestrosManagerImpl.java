package com.cardif.satelite.siniestro.service.impl;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.SiniPoliza;
import com.cardif.satelite.model.SiniSiniestro;
import com.cardif.satelite.siniestro.service.SiniPolizaService;
import com.cardif.satelite.siniestro.service.SiniProductoService;
import com.cardif.satelite.siniestro.service.SiniSiniestroService;
import com.cardif.satelite.siniestro.service.SiniestrosManager;

public class SiniestrosManagerImpl implements SiniestrosManager {
  public static final Logger log = Logger.getLogger(SiniestrosManagerImpl.class);

  @Autowired
  SiniSiniestroService siniSiniestroService;
  @Autowired
  SiniPolizaService siniPolizaService;
  @Autowired
  SiniProductoService siniProductoService;

  @Override
  public synchronized int insertarSiniestroPoliza(SiniSiniestro siniSiniestro, SiniPoliza siniPoliza) throws SyncconException {
    log.info("Inicio");
    int numRowAfected = 0;
    try {
      if (log.isDebugEnabled())
        log.debug("Input1 [ " + BeanUtils.describe(siniSiniestro).toString() + " ]");
      if (log.isDebugEnabled())
        log.debug("Input2 [ " + BeanUtils.describe(siniPoliza).toString() + " ]");

      numRowAfected = siniSiniestroService.insertarSiniSiniestro(siniSiniestro, siniProductoService.obtener(siniPoliza.getNroProducto()).getCodProductoSini());

      siniPoliza.setNroSiniestro(siniSiniestro.getNroSiniestro());
      siniPolizaService.insertarSiniPoliza(siniPoliza);

      log.debug("Output [Numero de filas afectadas : " + numRowAfected + " ]");
    } catch (SyncconException e) {
      throw e;
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_INSERTAR);
    }
    log.info("[ Fin ]");
    return numRowAfected;
  }
}
