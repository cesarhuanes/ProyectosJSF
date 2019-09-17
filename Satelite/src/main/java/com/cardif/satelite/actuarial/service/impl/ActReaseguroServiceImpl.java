package com.cardif.satelite.actuarial.service.impl;

import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_ACTUALIZAR;
import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_BUSCAR;
import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_ELIMINAR;
import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_INSERTAR;
import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_OBTENER;

import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.actuarial.bean.ConsultaActReaseguro;
import com.cardif.satelite.actuarial.dao.ActReaseguroMapper;
import com.cardif.satelite.actuarial.service.ActReaseguroService;
import com.cardif.satelite.model.ActReaseguro;

@Service("actReaseguroService")
public class ActReaseguroServiceImpl implements ActReaseguroService {

  public static final Logger log = Logger.getLogger(ActCoaseguroServiceImpl.class);
  @Autowired
  private ActReaseguroMapper actReaseguroMapper;
  private ActReaseguro actReaseguro;

  @Override
  public void insertar(ActReaseguro actReaseguro) throws SyncconException {

    log.info("inicio");
    try {
      log.debug("Input: [" + BeanUtils.describe(actReaseguro) + "]");

      actReaseguro.setFecCreacion(new Date(System.currentTimeMillis()));
      actReaseguroMapper.insert(actReaseguro);

      log.debug("Output: [Ok]");

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_INSERTAR);
    }
    log.info("Fin");
  }

  @Override
  public List<ConsultaActReaseguro> buscar(Long codSistema, String codSocio, Long codProducto) throws SyncconException {

    log.info("Inicio");
    List<ConsultaActReaseguro> lista = null;
    int size = 0;
    try {
      if (log.isDebugEnabled())
        log.debug("Input [1.-" + codSistema + ", 2.-" + codSocio + ", " + "3.-" + codProducto + "]");

      codSistema = (codSistema == 0) ? null : codSistema;
      // codSocio=StringUtils.isBlank(codSocio)?null:codSocio;
      codSocio = (codSocio.equals("")) ? null : codSocio;
      codProducto = (codProducto == 0) ? null : codProducto;

      // if filtro para seleccionar solo el sistema
      lista = actReaseguroMapper.selectActReaseguro(codSistema, codSocio, codProducto);

      size = (lista != null) ? lista.size() : 0;
      if (log.isDebugEnabled())
        log.debug("Output [Registros = " + size + "]");

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_BUSCAR);
    }
    log.info("Fin");
    return lista;

  }

  @Override
  public Date fechaMax(Long codSistema, String codSocio, Long codProducto) throws SyncconException {

    log.info("Inicio");

    Date fecha;

    try {
      if (log.isDebugEnabled())
        log.debug("Input [1.-" + codSistema + ", 2.-" + codSocio + ", " + "3.-" + codProducto + "]");

      codSistema = (codSistema == 0) ? null : codSistema;
      codSocio = (codSocio.equals("")) ? null : codSocio;
      codProducto = (codProducto == 0) ? null : codProducto;

      fecha = actReaseguroMapper.selectFechaReaseguro(codSistema, codSocio, codProducto);

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_BUSCAR);
    }

    log.info("Fin");

    return fecha;
  }

  @Override
  public Date fechaMaxAnt(Long codSistema, String codSocio, Long codProducto, Date fecFinReaseguro) throws SyncconException {

    log.info("Inicio");

    Date fechaAnt;

    try {
      if (log.isDebugEnabled())
        log.debug("Input [1.-" + codSistema + ", 2.-" + codSocio + ", " + "3.-" + codProducto + "]");

      codSistema = (codSistema == 0) ? null : codSistema;
      codSocio = (codSocio.equals("")) ? null : codSocio;
      codProducto = (codProducto == 0) ? null : codProducto;

      fechaAnt = actReaseguroMapper.selectFechaReaseguroAnt(codSistema, codSocio, codProducto, fecFinReaseguro);

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_BUSCAR);
    }

    log.info("Fin");

    return fechaAnt;

  }

  @Override
  public ActReaseguro obtener(Long codReaseguro) throws SyncconException {

    log.info("Inicio");
    ActReaseguro resultReaseguro = null;

    try {
      if (log.isDebugEnabled())
        log.debug("Input [" + codReaseguro + "]");
      resultReaseguro = actReaseguroMapper.selectByPrimaryKey(codReaseguro);

      if (log.isDebugEnabled())
        log.debug("Output [" + BeanUtils.describe(resultReaseguro) + "]");

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_OBTENER);
    }

    log.info("Fin");
    return resultReaseguro;
  }

  @Override
  public void actualizar(ActReaseguro actReaseguro) throws SyncconException {

    log.info("Inicio");
    try {
      if (log.isDebugEnabled())
        log.debug("input [" + BeanUtils.describe(actReaseguro).toString() + "]");
      actReaseguro.setFecModificacion(new Date(System.currentTimeMillis()));
      actReaseguroMapper.updateByPrimaryKey(actReaseguro);

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_ACTUALIZAR);
    }
    log.info("Fin");

  }

  @Override
  public void eliminar(Long codReaseguro) throws SyncconException {

    log.info("Inicio");
    try {
      if (log.isDebugEnabled())
        log.debug("Input [" + codReaseguro + "]");
      actReaseguroMapper.deleteByPrimaryKey(codReaseguro);
      if (log.isDebugEnabled())
        log.debug("Output []");

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_ELIMINAR);
    }
    log.info("Fin");
  }

  public ActReaseguro getActReaseguro() {
    return actReaseguro;
  }

  public void setActReaseguro(ActReaseguro actReaseguro) {
    this.actReaseguro = actReaseguro;
  }

}
