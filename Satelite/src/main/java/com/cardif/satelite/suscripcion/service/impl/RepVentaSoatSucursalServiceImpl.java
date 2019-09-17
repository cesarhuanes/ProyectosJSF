package com.cardif.satelite.suscripcion.service.impl;

import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_OBTENER;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.suscripcion.bean.RepVentaSoatSucursalBean;
import com.cardif.satelite.suscripcion.dao.PagoMapper;
import com.cardif.satelite.suscripcion.service.RepVentaSoatSucursalService;

@Service("repVentaSoatSucursalService")
public class RepVentaSoatSucursalServiceImpl implements RepVentaSoatSucursalService {
  public static final Logger logger = Logger.getLogger(RepVentaSoatSucursalServiceImpl.class);
  @Autowired
  PagoMapper pagoMapper;

  @Override
  public List<RepVentaSoatSucursalBean> listarReporteSoatSucursal(String desde, String hasta, String socio, String sucursal) throws SyncconException {

    logger.info("Inicio");
    List<RepVentaSoatSucursalBean> lista = null;
    try {
      if (logger.isDebugEnabled())
        logger.debug("Input [" + desde + "," + hasta + "," + socio + "," + sucursal + "]");

      lista = new ArrayList<RepVentaSoatSucursalBean>();
      lista = pagoMapper.listaReporte(desde, hasta, socio, sucursal);

      if (logger.isDebugEnabled())
        logger.debug("Output [Registros = " + lista.size() + "]");
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_OBTENER);
    }
    logger.info("Fin");
    return lista;

  }

  @Override
  public List<RepVentaSoatSucursalBean> listarReporteSoatSucursal(String desde, String hasta, String socio) throws SyncconException {
    logger.info("Inicio");
    List<RepVentaSoatSucursalBean> lista = null;
    try {
      if (logger.isDebugEnabled())
        logger.debug("Input [" + desde + "," + hasta + "," + socio + "]");

      lista = new ArrayList<RepVentaSoatSucursalBean>();
      lista = pagoMapper.listaReporte2(desde, hasta, socio);

      if (logger.isDebugEnabled())
        logger.debug("Output [Registros = " + lista.size() + "]");
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_OBTENER);
    }
    logger.info("Fin");
    return lista;
  }
}
