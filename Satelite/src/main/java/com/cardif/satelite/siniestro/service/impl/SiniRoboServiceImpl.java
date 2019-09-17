package com.cardif.satelite.siniestro.service.impl;

import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_ACTUALIZAR;
import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_ELIMINAR;
import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_INSERTAR;
import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_OBTENER;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.SiniRobo;
import com.cardif.satelite.siniestro.dao.SiniRoboMapper;
import com.cardif.satelite.siniestro.service.SiniRoboService;

@Service("siniRoboService")
public class SiniRoboServiceImpl implements SiniRoboService {

  public static final Logger log = Logger.getLogger(SiniRoboServiceImpl.class);

  @Autowired
  SiniRoboMapper siniRoboMapper;

  @Override
  public SiniRobo listar(String nroSiniestro) throws SyncconException {

    log.info("Inicio");
    SiniRobo siniRobo = null;
    try {

      siniRobo = siniRoboMapper.listar(nroSiniestro);

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_OBTENER);
    }
    log.info("Fin");
    return siniRobo;
  }

  @Override
  public List<SiniRobo> listar() throws SyncconException {
    log.info("Inicio");
    List<SiniRobo> lista;
    try {
      lista = siniRoboMapper.listarT();
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_OBTENER);
    }

    log.info("Fin");
    return lista;

  }

  @Override
  public int actualizarSiniRobo(SiniRobo siniRobo) throws SyncconException {
    log.info("Inicio");
    try {
      siniRoboMapper.updateByPrimaryKeySelective(siniRobo);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_ACTUALIZAR);
    }
    log.info("Fin");
    return 0;
  }

  @Override
  public int insertarSiniRobo(SiniRobo siniRobo) throws SyncconException {
    log.info("Inicio");
    try {
      siniRoboMapper.insertSelective(siniRobo);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_INSERTAR);
    }
    log.info("Fin");
    return 0;
  }

  @Override
  public int eliminarSiniRobo(String siniRobo) throws SyncconException {
    log.info("Inicio");
    try {
      siniRoboMapper.deleteByPrimaryKey(siniRobo);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_ELIMINAR);
    }
    log.info("Fin");
    return 0;
  }

}
