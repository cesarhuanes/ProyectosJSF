package com.cardif.satelite.ssis.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.ssis.dao.SsisMapper;
import com.cardif.satelite.ssis.service.SsisService;

@Service("ssisService")
public class SsisServiceImpl implements SsisService {

  public static final Logger log = Logger.getLogger(SsisServiceImpl.class);

  @Autowired
  private SsisMapper ssisMapper;

  /*
   * (non-Javadoc)
   * 
   * @see com.cardif.satelite.ssis.service.MgrSsisService#lanzarJob(java.lang.String )
   */

  @Override
  public void lanzarJob(String nombreJob) throws SyncconException {

    log.info("Inicio");

    try {
      if (log.isDebugEnabled())
        log.debug("Input [" + nombreJob + "]");

      ssisMapper.ejecutarJob(nombreJob);

      if (log.isDebugEnabled())
        log.debug("Output [Job Ejecutado]");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_JOB);
    }

    log.info("Fin");
  }

}