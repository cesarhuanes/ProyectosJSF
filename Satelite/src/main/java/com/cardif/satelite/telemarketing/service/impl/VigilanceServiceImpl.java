package com.cardif.satelite.telemarketing.service.impl;

import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_BUSCAR;
import java.util.Date;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.telemarketing.bean.BaseVigilance;
import com.cardif.satelite.telemarketing.dao.VigilanceMapper;
import com.cardif.satelite.telemarketing.service.VigilanceService;
import com.cardif.satelite.util.SateliteConstants;

@Service("vigilanceService")
public class VigilanceServiceImpl implements VigilanceService {

  public static final Logger LOGGER = Logger.getLogger(VigilanceServiceImpl.class);

  @Autowired
  private VigilanceMapper vigilanceMapper;

  @Override
  public Long getNumeroDeVigilance() throws SyncconException {
    LOGGER.info("Inicio");

    Long resultado = null;

    try {
      resultado = vigilanceMapper.countVigilance();

    } catch (Exception e) {

      LOGGER.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_BUSCAR);

    }
    LOGGER.info("Fin");
    return resultado;
  }

  @Override
  public Boolean algunaCargaVigilanceEstaPendiente() throws SyncconException {
    LOGGER.info("Inicio");

    Boolean resultado = Boolean.FALSE;

    try {
      int count = vigilanceMapper.countBaseVigilance();
      resultado = count > 0 ? Boolean.TRUE : Boolean.FALSE;

    } catch (Exception e) {

      LOGGER.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_BUSCAR);

    }
    LOGGER.info("Fin");
    return resultado;
  }

  @Override
  public String buscarEstado(String codigoBase) throws SyncconException {
    LOGGER.info("Inicio");
    String estado = null;
    try {
      estado = vigilanceMapper.selectEstado(codigoBase);
    } catch (Exception e) {
      LOGGER.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_BUSCAR);
    }

    LOGGER.info("Fin");
    return estado;
  }

  @Override
  public void insertarBaseVigilance() throws SyncconException {
    LOGGER.info("Inicio");
    try {
      BaseVigilance baseVigilance = new BaseVigilance();
      baseVigilance.setEstado(SateliteConstants.PENDIENTE);
      baseVigilance.setUsuCreacion(SecurityContextHolder.getContext().getAuthentication().getName());
      baseVigilance.setFecCreacion(new Date(System.currentTimeMillis()));
      vigilanceMapper.insert(baseVigilance);
    } catch (Exception e) {
      LOGGER.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_BUSCAR);
    }

    LOGGER.info("Fin");
  }

  @Override
  public String getCodigoBase() throws SyncconException {
    LOGGER.info("Inicio");
    String estado = null;
    try {
      estado = vigilanceMapper.getCodigoBase();
    } catch (Exception e) {
      LOGGER.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_BUSCAR);
    }

    LOGGER.info("Fin");
    return estado;
  }

  @Override
  public void actualizarBaseVigilance(String codigoBase) throws SyncconException {

    LOGGER.info("Inicio");
    try {
      Long numeroVigilance = getNumeroDeVigilance();
      vigilanceMapper.update(codigoBase, numeroVigilance);
    } catch (Exception e) {
      LOGGER.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_BUSCAR);
    }

    LOGGER.info("Fin");
  }

  @Override
  public BaseVigilance getUltimoBaseVigilance() throws SyncconException {
    LOGGER.info("Inicio");

    BaseVigilance resultado = null;

    try {
      resultado = vigilanceMapper.getBaseVigilance();

    } catch (Exception e) {

      LOGGER.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_BUSCAR);

    }
    LOGGER.info("Fin");
    return resultado;
  }

}
