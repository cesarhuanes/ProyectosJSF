package com.cardif.satelite.soat.falabella.service.impl;

import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_ACTUALIZAR;
import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_INSERTAR;
import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_OBTENER;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.soat.falabella.dao.DireccionPropietarioVehiculoMapper;
import com.cardif.satelite.soat.falabella.service.DireccionPropietarioVehiculoService;
import com.cardif.satelite.soat.model.DireccionPropietarioVehiculo;
import com.cardif.satelite.soat.model.VehiculoPe;

@Service("direccionPropietarioVehiculoService")
public class DireccionPropietarioVehiculoServiceImpl implements DireccionPropietarioVehiculoService {

  public static final Logger log = Logger.getLogger(DireccionPropietarioVehiculoServiceImpl.class);

  @Autowired
  DireccionPropietarioVehiculoMapper direccionPropietarioVehiculoMapper;

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
      // numRowsAffected =
      // direccionPropietarioVehiculoMapper.updateDireccionPropietarioVehiculo(direccionPropietarioVehiculo.getPlaca(),
      // direccionPropietarioVehiculo.getVersion(),
      // direccionPropietarioVehiculo.getDireccion(),
      // direccionPropietarioVehiculo.getPropietario(),placaOld);
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
