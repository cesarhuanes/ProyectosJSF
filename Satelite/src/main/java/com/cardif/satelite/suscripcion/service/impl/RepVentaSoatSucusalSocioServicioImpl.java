package com.cardif.satelite.suscripcion.service.impl;

import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_OBTENER;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.soatsucursal.Socio;
import com.cardif.satelite.suscripcion.dao.SocioMapper;
import com.cardif.satelite.suscripcion.service.RepVentaSoatSucursalSocioService;

@Service("repVentaSoatSucursalSocioService")
public class RepVentaSoatSucusalSocioServicioImpl implements RepVentaSoatSucursalSocioService {
  public static final Logger logger = Logger.getLogger(RepVentaSoatSucusalSocioServicioImpl.class);
  @Autowired
  SocioMapper socioMapper;

  @Override
  public List<Socio> listarSocio() throws SyncconException {
    logger.info("Inicio");
    List<Socio> lista = null;
    try {
      lista = new ArrayList<Socio>();
      lista = socioMapper.listaSocios();
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
