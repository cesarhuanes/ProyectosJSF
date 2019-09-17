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
import com.cardif.satelite.model.SiniDesempleo;
import com.cardif.satelite.siniestro.dao.SiniDesempleoMapper;
import com.cardif.satelite.siniestro.service.SiniDesempleoService;

@Service("siniDesempleoService")
public class SiniDesempleoServiceImpl implements SiniDesempleoService {
  public static final Logger logger = Logger.getLogger(SiniDesempleoServiceImpl.class);
  @Autowired
  SiniDesempleoMapper siniDesempleoMapper;

  @Override
  public SiniDesempleo listar(String nroSiniestro) throws SyncconException {
    logger.info("[ Inicio ]");
    SiniDesempleo siniDesempleo;
    try {

      siniDesempleo = siniDesempleoMapper.listar(nroSiniestro);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_OBTENER);
    }
    logger.info("[ Fin ]");
    return siniDesempleo;

  }

  @Override
  public List<SiniDesempleo> listar() throws SyncconException {
    logger.info("[ Inicio ]");
    List<SiniDesempleo> lista;
    try {
      lista = siniDesempleoMapper.listarT();
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_OBTENER);
    }
    logger.info("Fin");
    return lista;
  }

  @Override
  public int actualizarSiniDesempleo(SiniDesempleo siniDesempleo) throws SyncconException {
    logger.info("Inicio");
    try {
      siniDesempleoMapper.updateByPrimaryKey(siniDesempleo);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_ACTUALIZAR);
    }
    logger.info("Fin");

    return 0;
  }

  @Override
  public int insertarSiniDesempleo(SiniDesempleo siniDesempleo) throws SyncconException {
    logger.info("[ Inicio ]");
    try {
      siniDesempleoMapper.insert(siniDesempleo);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_INSERTAR);
    }
    logger.info("Fin");

    return 0;
  }

  @Override
  public int eliminarSiniDesempleo(String siniDesempleo) throws SyncconException {
    logger.info("Inicio");
    try {
      siniDesempleoMapper.deleteByPrimaryKey(siniDesempleo);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_ELIMINAR);
    }
    logger.info("Fin");

    return 0;
  }

}
