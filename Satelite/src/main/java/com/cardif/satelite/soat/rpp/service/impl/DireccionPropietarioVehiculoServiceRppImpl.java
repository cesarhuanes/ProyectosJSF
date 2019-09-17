package com.cardif.satelite.soat.rpp.service.impl;

import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_ACTUALIZAR;
import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_INSERTAR;
import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_OBTENER;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.soat.model.DireccionPropietarioVehiculo;
import com.cardif.satelite.soat.model.VehiculoPe;
import com.cardif.satelite.soat.rpp.dao.DireccionPropietarioVehiculoRppMapper;
import com.cardif.satelite.soat.rpp.service.DireccionPropietarioVehiculoServiceRpp;

@Service("direccionPropietarioVehiculoServiceRpp")
public class DireccionPropietarioVehiculoServiceRppImpl implements DireccionPropietarioVehiculoServiceRpp {

  public static final Logger log = Logger.getLogger(DireccionPropietarioVehiculoServiceRppImpl.class);

  @Autowired
  DireccionPropietarioVehiculoRppMapper direccionPropietarioVehiculoMapper;

  @Override
  public synchronized int insertar(DireccionPropietarioVehiculo direccionPropietarioVehiculo) throws SyncconException {
    log.info("[ Inicio ]");
    int numRowsAfected = 0;
    try {
      if (log.isDebugEnabled())
        log.debug("Input [ " + BeanUtils.describe(direccionPropietarioVehiculo).toString() + " ]");
      numRowsAfected = direccionPropietarioVehiculoMapper.insert(direccionPropietarioVehiculo);
      if (log.isDebugEnabled())
        log.debug("[ Output | Numero de filas Afectadas Direccion Propietario Vehiculo [ " + numRowsAfected + "] ]");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_INSERTAR);
    }

    log.info("[ Fin ]");
    return numRowsAfected;

  }

  @Override
  public DireccionPropietarioVehiculo getDireccionPropietarioVehiculo(VehiculoPe vehiculope) throws SyncconException {
    log.info("[ Inicio ]");
    DireccionPropietarioVehiculo direccionPropietarioVehiculo = null;
    try {
      if (log.isDebugEnabled())
        log.debug("Input [ " + BeanUtils.describe(vehiculope) + " ]");
      direccionPropietarioVehiculo = direccionPropietarioVehiculoMapper.getDPVbyPlaca(vehiculope.getPlaca());
      if (log.isDebugEnabled())
        log.debug("Input [ " + BeanUtils.describe(direccionPropietarioVehiculo) + " ]");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_OBTENER);
    }
    log.info("[ Fin ]");
    return direccionPropietarioVehiculo;
  }

  @Override
  public int actualizar(DireccionPropietarioVehiculo direccionPropietarioVehiculo) throws SyncconException {
    log.info("[ Inicio ]");
    int numRowsAffected = 0;
    try {
      if (log.isDebugEnabled())
        log.debug("Input [ " + BeanUtils.describe(direccionPropietarioVehiculo).toString() + " ]");
      numRowsAffected = direccionPropietarioVehiculoMapper.updateByPlaca(direccionPropietarioVehiculo);
      if (log.isDebugEnabled())
        log.debug("[ Output | [ " + numRowsAffected + "] ]");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_ACTUALIZAR);
    }

    log.info("[ Fin ]");
    return numRowsAffected;
  }

}
