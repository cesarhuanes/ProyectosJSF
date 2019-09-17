package com.cardif.satelite.tesoreria.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.satelite.CargoCentralizado;
import com.cardif.satelite.tesoreria.dao.CargoCentralizadoMapper;
import com.cardif.satelite.tesoreria.service.CargoCentralizadoService;

@Service("cargoCentralizadoService")
public class CargoCentralizadoServiceImpl implements CargoCentralizadoService {
  public static final Logger LOGGER = Logger.getLogger(CargoCentralizadoServiceImpl.class);

  @Autowired
  CargoCentralizadoMapper cargoCentralizadoMapper;

  @Override
  public List<CargoCentralizado> obtenerListaCargoCentralizado() throws SyncconException {
    LOGGER.info(" Inicio ");
    List<CargoCentralizado> listaCargoCentralizado = null;
    try {
      if (LOGGER.isDebugEnabled())
        LOGGER.debug("Input []");
      listaCargoCentralizado = cargoCentralizadoMapper.obtenerListaCargoCentralizado();
      if (LOGGER.isDebugEnabled())
        LOGGER.debug("Output [Registros = " + listaCargoCentralizado.size() + "]");
    } catch (Exception e) {
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
    }
    LOGGER.info(" Fin ");
    return listaCargoCentralizado;
  }

  @Override
  public CargoCentralizado obtenerPorPK(Long pk) throws SyncconException {
    LOGGER.info(" Inicio ");
    CargoCentralizado cargoCentralizado = null;
    try {
      LOGGER.info("Input [" + pk + "]");
      cargoCentralizado = cargoCentralizadoMapper.obtenerComprobantesPorPk(pk);
      if (LOGGER.isDebugEnabled())
        LOGGER.debug("Output [Bean = " + BeanUtils.describe(cargoCentralizado).toString() + "]");
    } catch (Exception e) {
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
    }
    LOGGER.info(" Fin ");
    return cargoCentralizado;
  }

  @Override
  public List<CargoCentralizado> obtenerComprobantesAprobados(Date filAprobFechaRegistroContableDesde, Date filAprobFechaRegistroContableHasta, String filAprobSistema, String filAprobNroComprobante,
      String filAprobRucDni, Date filAprobFecEmision, String filAprobRazonSocial, String filAprobMoneda, Date filAprobFecVencimiento, Date filAprobFechaCargaDesde, Date filAprobFechaCargaHasta,
      Date filAprobFecPago, String filAprobEstado) throws SyncconException {
    LOGGER.info(" Inicio ");
    List<CargoCentralizado> listaComprobantesAprobados = null;
    try {

      filAprobSistema = StringUtils.isBlank(filAprobSistema) ? null : filAprobSistema;
      filAprobNroComprobante = StringUtils.isBlank(filAprobNroComprobante) ? null : filAprobNroComprobante;
      filAprobRucDni = StringUtils.isBlank(filAprobRucDni) ? null : filAprobRucDni;
      filAprobRazonSocial = StringUtils.isBlank(filAprobRazonSocial) ? null : filAprobRazonSocial;
      filAprobMoneda = StringUtils.isBlank(filAprobMoneda) ? null : filAprobMoneda;
      filAprobEstado = StringUtils.isBlank(filAprobEstado) ? null : filAprobEstado;

      if (LOGGER.isDebugEnabled())
        LOGGER.debug("Input 1.- " + filAprobSistema + "]" + "/n" + "[2.- " + filAprobNroComprobante + "]" + "/n" + "[3.- " + filAprobRucDni + "]" + "/n" + "[4.- " + filAprobRazonSocial + "]" + "/n"
            + "[5.- " + filAprobMoneda + "]" + "/n" + "[6.- " + filAprobEstado + "]" + "/n");
      listaComprobantesAprobados = cargoCentralizadoMapper.obtenerComprobantesAprobados(filAprobFechaRegistroContableDesde, filAprobFechaRegistroContableHasta, filAprobSistema, filAprobNroComprobante,
          filAprobRucDni, filAprobFecEmision, filAprobRazonSocial, filAprobMoneda, filAprobFecVencimiento, filAprobFechaCargaDesde, filAprobFechaCargaHasta, filAprobFecPago, filAprobEstado);
      if (LOGGER.isDebugEnabled())
        LOGGER.debug("Output [Registros = " + listaComprobantesAprobados.size() + "]");
    } catch (Exception e) {
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
    }
    LOGGER.info(" Fin ");
    return listaComprobantesAprobados;
  }

  @Override
  @SuppressWarnings("deprecation")
  public boolean updateCombrobantesAprobados(List<CargoCentralizado> listaComprobantesAprobados, Date updateFecEntregaTesoreria, Date updateFecRecepcionContable, Date fecModificacion,
      String usuarioModificacion) throws SyncconException {
    LOGGER.info(" Inicio ");
    boolean exito = false;
    CargoCentralizado cargoCentralizado = null;
    try {
      LOGGER.info("Input [" + listaComprobantesAprobados.size() + " " + updateFecEntregaTesoreria.toGMTString() + " " + updateFecRecepcionContable.toGMTString() + "]");
      cargoCentralizado = new CargoCentralizado();
      for (CargoCentralizado cc : listaComprobantesAprobados) {
        cargoCentralizado = cargoCentralizadoMapper.actualizarComprobantesAprobados(updateFecEntregaTesoreria, updateFecRecepcionContable, fecModificacion, usuarioModificacion, cc.getNroDocumento());
      }
      exito = cargoCentralizado != null ? true : false;
      LOGGER.info("Output [Registros actualizados = " + exito + "]");
    } catch (Exception e) {
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_ACTUALIZAR);
    }
    LOGGER.info(" Fin ");
    return exito;
  }

  @Override
  public List<CargoCentralizado> obtenerComprobantesRechazados(String filRechazadoSistema, String filRechazadoNroComprobante, String filRechazadoRucDni, Date filRechazadoFecEmision,
      String filRechazadoRazonSocial, Date filRechazadoFechaCargaDesde, Date filRechazadoFechaCargaHasta) throws SyncconException {

    LOGGER.info(" Inicio ");
    List<CargoCentralizado> listaComprobantesRechazados = null;
    try {

      filRechazadoSistema = StringUtils.isBlank(filRechazadoSistema) ? null : filRechazadoSistema;
      filRechazadoNroComprobante = StringUtils.isBlank(filRechazadoNroComprobante) ? null : filRechazadoNroComprobante;
      filRechazadoRucDni = StringUtils.isBlank(filRechazadoRucDni) ? null : filRechazadoRucDni;
      filRechazadoRazonSocial = StringUtils.isBlank(filRechazadoRazonSocial) ? null : filRechazadoRazonSocial;

      if (LOGGER.isDebugEnabled())
        LOGGER.debug("Input 1.- " + filRechazadoSistema + "]" + "/n" + "[2.- " + filRechazadoNroComprobante + "]" + "/n" + "[3.- " + filRechazadoRucDni + "]" + "/n" + "[4.- " + filRechazadoRazonSocial
            + "]" + "/n");
      listaComprobantesRechazados = cargoCentralizadoMapper.obtenerComprobantesRechazados(filRechazadoSistema, filRechazadoNroComprobante, filRechazadoRucDni, filRechazadoFecEmision,
          filRechazadoRazonSocial, filRechazadoFechaCargaDesde, filRechazadoFechaCargaHasta);
      if (LOGGER.isDebugEnabled())
        LOGGER.debug("Output [Registros = " + listaComprobantesRechazados.size() + "]");
    } catch (Exception e) {
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
    }
    LOGGER.info(" Fin ");
    return listaComprobantesRechazados;
  }

  @Override
  @SuppressWarnings("deprecation")
  public boolean updateCombrobantesFechaEntregaTesoreria(List<CargoCentralizado> listaComprobantesAprobados, Date updateFecEntregaTesoreria, Date fecModificacion, String usuarioModificacion)
      throws SyncconException {
    LOGGER.info(" Inicio ");
    boolean exito = false;
    CargoCentralizado cargoCentralizado = null;
    try {
      LOGGER.info("Input [" + listaComprobantesAprobados.size() + " " + updateFecEntregaTesoreria.toGMTString() + "]");
      cargoCentralizado = new CargoCentralizado();
      for (CargoCentralizado cc : listaComprobantesAprobados) {
        cargoCentralizado = cargoCentralizadoMapper.actualizarFecEntregaTesoreria(updateFecEntregaTesoreria, fecModificacion, usuarioModificacion, cc.getNroDocumento());
      }
      exito = cargoCentralizado != null ? true : false;
      LOGGER.info("Output [Registros actualizados = " + exito + "]");
    } catch (Exception e) {
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_ACTUALIZAR);
    }
    LOGGER.info(" Fin ");
    return exito;
  }

  @Override
  @SuppressWarnings("deprecation")
  public boolean updateCombrobantesFechaRecepcionContable(List<CargoCentralizado> listaComprobantesAprobados, Date updateFecRecepcionContable, Date fecModificacion, String usuarioModificacion)
      throws SyncconException {
    LOGGER.info(" Inicio ");
    boolean exito = false;
    CargoCentralizado cargoCentralizado = null;
    try {
      LOGGER.info("Input [" + listaComprobantesAprobados.size() + " " + updateFecRecepcionContable.toGMTString() + "]");
      cargoCentralizado = new CargoCentralizado();
      for (CargoCentralizado cc : listaComprobantesAprobados) {
        cargoCentralizado = cargoCentralizadoMapper.actualizarFechaRecepcionContable(updateFecRecepcionContable, fecModificacion, usuarioModificacion, cc.getNroDocumento());
      }
      exito = cargoCentralizado != null ? true : false;
      LOGGER.info("Output [Registros actualizados = " + exito + "]");
    } catch (Exception e) {
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_ACTUALIZAR);
    }
    LOGGER.info(" Fin ");
    return exito;
  }

  @Override
  public boolean updateCombrobantesFechaNull(List<CargoCentralizado> listaComprobantesAprobados, Date fecModificacion, String usuarioModificacion) throws SyncconException {
    LOGGER.info(" Inicio ");
    boolean exito = false;
    CargoCentralizado cargoCentralizado = null;
    try {
      cargoCentralizado = new CargoCentralizado();
      for (CargoCentralizado cc : listaComprobantesAprobados) {
        cargoCentralizado = cargoCentralizadoMapper.actualizarFechaNull(fecModificacion, usuarioModificacion, cc.getNroDocumento());
      }
      exito = cargoCentralizado != null ? true : false;
      LOGGER.info("Output [Registros actualizados = " + exito + "]");
    } catch (Exception e) {
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_ACTUALIZAR);
    }
    LOGGER.info(" Fin ");
    return exito;
  }

}
