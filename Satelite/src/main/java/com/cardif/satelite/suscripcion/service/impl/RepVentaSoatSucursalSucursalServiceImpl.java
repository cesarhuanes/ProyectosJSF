package com.cardif.satelite.suscripcion.service.impl;

import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_OBTENER;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.soatsucursal.Sucursal;
import com.cardif.satelite.suscripcion.dao.SucursalMapper;
import com.cardif.satelite.suscripcion.service.RepVentaSoatSucursalSucursalService;

@Service("repVentaSoatSucursalSucursalService")
public class RepVentaSoatSucursalSucursalServiceImpl implements RepVentaSoatSucursalSucursalService {
  public static final Logger logger = Logger.getLogger(RepVentaSoatSucursalSucursalServiceImpl.class);
  @Autowired
  SucursalMapper sucursalMapper;

  @Override
  public List<Sucursal> listar() throws SyncconException {
    logger.info("Inicio");
    List<Sucursal> lista = null;
    try {
      lista = new ArrayList<Sucursal>();
      lista = sucursalMapper.listaSucursal();
      if (logger.isDebugEnabled())
        logger.debug("Output [Registros = " + lista.size() + "]");
    } catch (Exception e) {
      System.err.println(e.getMessage());
      throw new SyncconException(COD_ERROR_BD_OBTENER);
    }
    logger.info("Fin");
    return lista;
  }

  @Override
  public List<Sucursal> listarPorSocio(String socio) throws SyncconException {
    logger.info("Inicio");
    List<Sucursal> lista = null;
    try {
      lista = new ArrayList<Sucursal>();
      lista = sucursalMapper.obtenerSucursal(socio);
      if (logger.isDebugEnabled())
        logger.debug("Output [Registros = " + lista.size() + "]");
    } catch (Exception e) {
      System.err.println(e.getMessage());
      throw new SyncconException(COD_ERROR_BD_OBTENER);
    }
    logger.info("Fin");
    return lista;
  }
}
