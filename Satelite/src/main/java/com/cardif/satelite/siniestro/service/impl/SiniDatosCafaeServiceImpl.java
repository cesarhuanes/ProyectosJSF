package com.cardif.satelite.siniestro.service.impl;

import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_ACTUALIZAR;
import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_ELIMINAR;
import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_INSERTAR;
import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_OBTENER;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.SiniDatosCafae;
import com.cardif.satelite.siniestro.dao.SiniDatosCafaeMapper;
import com.cardif.satelite.siniestro.service.SiniDatosCafaeService;

@Service("siniDatosCafaeService")
public class SiniDatosCafaeServiceImpl implements SiniDatosCafaeService {
  public static final Logger log = Logger.getLogger(SiniDatosCafaeServiceImpl.class);
  @Autowired
  SiniDatosCafaeMapper siniDatosCafaeMapper;

  @Override
  public int actualizarSiniDatosCafae(SiniDatosCafae siniDatosCafae) throws SyncconException {
    log.info("Inicio");

    try {
      siniDatosCafaeMapper.updateByPrimaryKeySelective(siniDatosCafae);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_ACTUALIZAR);
    }
    log.info("Fin");
    return 0;
  }

  @Override
  public int insertarSiniDatosCafae(SiniDatosCafae siniDatosCafae) throws SyncconException {
    log.info("Inicio");

    try {
      if (log.isDebugEnabled())
        log.debug("Input [ " + BeanUtils.describe(siniDatosCafae) + " ]");

      siniDatosCafaeMapper.insert(siniDatosCafae);

      if (log.isDebugEnabled())
        log.debug("Output [OK]");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_INSERTAR);
    }
    log.info("Fin");

    return 0;
  }

  @Override
  public int eliminarSiniSiniDatosCafae(String nroSiniestro) throws SyncconException {
    log.info("Inicio");

    try {
      if (log.isDebugEnabled())
        log.debug("Input [ " + nroSiniestro + " ]");

      siniDatosCafaeMapper.deleteByPrimaryKey(nroSiniestro);

      if (log.isDebugEnabled())
        log.debug("Output [OK]");

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_ELIMINAR);
    }

    log.info("[ Fin ]");
    return 0;

  }

  @Override
  public SiniDatosCafae listar(String nroSiniestro) throws SyncconException {
    log.info("Inicio");
    SiniDatosCafae siniDatosCafae = new SiniDatosCafae();
    try {

      siniDatosCafae = siniDatosCafaeMapper.listar(nroSiniestro);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_OBTENER);
    }
    log.info("[ Fin ]");
    return siniDatosCafae;

  }

  @Override
  public List<SiniDatosCafae> listar() throws SyncconException {
    log.info("[ Inicio ]");
    List<SiniDatosCafae> lista1;
    try {

      lista1 = siniDatosCafaeMapper.listarT();
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_OBTENER);
    }
    log.info("[ Fin ]");
    return lista1;

  }

}
