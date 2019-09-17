package com.cardif.satelite.actuarial.service.impl;

import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_ACTUALIZAR;
import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_BUSCAR;
import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_ELIMINAR;
import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_INSERTAR;
import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_OBTENER;

import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.actuarial.bean.ConsultaActCoaseguro;
import com.cardif.satelite.actuarial.dao.ActCoaseguroMapper;
import com.cardif.satelite.actuarial.service.ActCoaseguroService;
import com.cardif.satelite.model.ActCoaseguro;

@Service("actCoaseguroService")
public class ActCoaseguroServiceImpl implements ActCoaseguroService {
  public static final Logger log = Logger.getLogger(ActCoaseguroServiceImpl.class);
  @Autowired
  private ActCoaseguroMapper actCoaseguroMapper;
  private ActCoaseguro actCoaseguro;

  @Override
  public void insertar(ActCoaseguro actCoaseguro) throws SyncconException {
    log.info("inicio");
    try {
      log.debug("Input: [" + BeanUtils.describe(actCoaseguro) + "]");

      actCoaseguro.setFecCreacion(new Date(System.currentTimeMillis()));
      actCoaseguroMapper.insert(actCoaseguro);

      log.debug("Output: [Ok]");

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_INSERTAR);
    }
    log.info("Fin");

  }

  @Override
  public List<ConsultaActCoaseguro> buscar(Long codSistema, String codSocio, Long codProducto) throws SyncconException {
    log.info("Inicio");
    List<ConsultaActCoaseguro> lista = null;
    int size = 0;
    try {
      if (log.isDebugEnabled())
        log.debug("Input [1.-" + codSistema + ", 2.-" + codSocio + ", " + "3.-" + codProducto + "]");

      codSistema = (codSistema == 0) ? null : codSistema;
      codSocio = StringUtils.isBlank(codSocio) ? null : codSocio;
      codProducto = (codProducto == 0) ? null : codProducto;

      // if filtro para seleccionar solo el sistema
      lista = actCoaseguroMapper.selectActCoaseguro(codSistema, codSocio, codProducto);

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

      fecha = actCoaseguroMapper.selectFechaCoaseguro(codSistema, codSocio, codProducto);

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

      fechaAnt = actCoaseguroMapper.selectFechaCoaseguroAnt(codSistema, codSocio, codProducto, fecFinReaseguro);

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_BUSCAR);
    }

    log.info("Fin");

    return fechaAnt;

  }

  @Override
  public ActCoaseguro obtener(Long codCoaseguro) throws SyncconException {
    log.info("Inicio");
    ActCoaseguro resultCoaseguro = null;

    try {
      if (log.isDebugEnabled())
        log.debug("Input [" + codCoaseguro + "]");
      resultCoaseguro = actCoaseguroMapper.selectByPrimaryKey(codCoaseguro);

      if (log.isDebugEnabled())
        log.debug("Output [" + BeanUtils.describe(resultCoaseguro) + "]");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_OBTENER);
    }
    log.info("Fin");
    return resultCoaseguro;
  }

  @Override
  public void actualizar(ActCoaseguro actCoaseguro) throws SyncconException {
    log.info("Inicio");
    try {
      if (log.isDebugEnabled())
        log.debug("input [" + BeanUtils.describe(actCoaseguro).toString() + "]");
      actCoaseguro.setFecModificacion(new Date(System.currentTimeMillis()));
      actCoaseguroMapper.updateByPrimaryKey(actCoaseguro);

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_ACTUALIZAR);
    }
    log.info("Fin");

  }

  @Override
  public void eliminar(Long codCoaseguro) throws SyncconException {
    log.info("Inicio");
    try {
      if (log.isDebugEnabled())
        log.debug("Input [" + codCoaseguro + "]");
      actCoaseguroMapper.deleteByPrimaryKey(codCoaseguro);
      if (log.isDebugEnabled())
        log.debug("Output []");

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_ELIMINAR);
    }
    log.info("Fin");

  }

  public ActCoaseguro getActCoaseguro() {
    return actCoaseguro;
  }

  public void setActCoaseguro(ActCoaseguro actCoaseguro) {
    this.actCoaseguro = actCoaseguro;
  }

}
