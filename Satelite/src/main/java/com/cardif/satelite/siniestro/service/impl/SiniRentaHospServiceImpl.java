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
import com.cardif.satelite.model.SiniRentaHosp;
import com.cardif.satelite.siniestro.dao.SiniRentaHospMapper;
import com.cardif.satelite.siniestro.service.SiniRentaHospService;

@Service("siniRentaHospService")
public class SiniRentaHospServiceImpl implements SiniRentaHospService {
  public static final Logger log = Logger.getLogger(SiniRentaHospServiceImpl.class);
  @Autowired
  SiniRentaHospMapper siniRentaHospMapper;

  @Override
  public SiniRentaHosp listar(String nroSiniestro) throws SyncconException {
    SiniRentaHosp siniRentaHosp;
    log.info("Inicio");
    try {
      siniRentaHosp = siniRentaHospMapper.listar(nroSiniestro);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_OBTENER);
    }
    log.info("Fin");
    return siniRentaHosp;
  }

  @Override
  public List<SiniRentaHosp> listar() throws SyncconException {
    log.info("Inicio");
    List<SiniRentaHosp> lista;
    try {
      lista = siniRentaHospMapper.listarT();
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_OBTENER);
    }
    log.info("Fin");
    return lista;
  }

  @Override
  public int actualizarRentaHosp(SiniRentaHosp siniRentaHosp) throws SyncconException {
    log.info("Inicio");
    try {
      siniRentaHospMapper.updateByPrimaryKey(siniRentaHosp);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_ACTUALIZAR);
    }
    log.info("Fin");

    return 0;
  }

  @Override
  public int insertarSiniRentaHosp(SiniRentaHosp siniRentaHosp) throws SyncconException {
    log.info("Inicio");
    try {
      siniRentaHospMapper.insertSelective(siniRentaHosp);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_INSERTAR);
    }
    log.info("Fin");

    return 0;
  }

  @Override
  public int eliminarSiniRentaHosp(String siniRentaHosp) throws SyncconException {
    log.info("Inicio");
    try {
      siniRentaHospMapper.deleteByPrimaryKey(siniRentaHosp);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_ELIMINAR);
    }
    log.info("Fin");
    return 0;
  }

}
