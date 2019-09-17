/**
 * 
 */
package com.cardif.satelite.soat.falabella.service.impl;

import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_INSERTAR;
import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_OBTENER;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.callcenter.bean.ConsultaRegVehicular;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.soat.falabella.dao.VehiculoPeMapper;
import com.cardif.satelite.soat.falabella.service.VehiculoPeService;
import com.cardif.satelite.soat.model.VehiculoPe;

@Service("vehiculoPeService")
public class VehiculoPeServiceImpl implements VehiculoPeService {

  public static final Logger log = Logger.getLogger(VehiculoPeServiceImpl.class);

  @Autowired
  private VehiculoPeMapper vehiculoPeMapper;

  @Override
  public VehiculoPe obtener(String placa) throws SyncconException {
    log.info("Inicio");
    VehiculoPe vehiculoPe = null;
    try {
      if (log.isDebugEnabled())
        log.debug("Input [" + placa + "]");
      vehiculoPe = vehiculoPeMapper.selectVehiculo(placa);

      if (log.isDebugEnabled())
        log.debug("Output [" + BeanUtils.describe(vehiculoPe) + "]");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_OBTENER);
    }
    log.info("Fin");
    return vehiculoPe;
  }

  @Override
  public List<ConsultaRegVehicular> buscarPlaca(String placa) throws SyncconException {
    log.info("Inicio");
    List<ConsultaRegVehicular> lista = null;
    int size = 0;

    try {
      if (log.isDebugEnabled())
        log.debug("Input [" + placa + "]");
      lista = vehiculoPeMapper.selectVehicular(placa);
      size = (lista != null) ? lista.size() : 0;
      if (log.isDebugEnabled())
        log.debug("Output [Registros = " + size + "]");
      if (log.isDebugEnabled())
        log.debug("Output [" + BeanUtils.describe(lista) + "]");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_OBTENER);
    }

    log.info("Final");
    return lista;
  }

  @Override
  public ConsultaRegVehicular buscarPropietario(String dni) throws SyncconException {

    log.info("Inicio");
    ConsultaRegVehicular consultaRegVehicular = null;
    try {
      if (log.isDebugEnabled())
        log.debug("Input [" + dni + "]");
      consultaRegVehicular = vehiculoPeMapper.selectRegistro(dni);

      if (log.isDebugEnabled())
        log.debug("Output [" + BeanUtils.describe(consultaRegVehicular) + "]");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_OBTENER);
    }
    log.info("Fin");
    return consultaRegVehicular;
  }

  @Override
  public int insertarVehiculo(VehiculoPe vehiculoPe) throws SyncconException {
    log.info("[ Inicio ]");
    int numRowsAfected = 0;
    try {
      if (log.isDebugEnabled())
        log.debug("Input [ " + BeanUtils.describe(vehiculoPe).toString() + " ]");
      numRowsAfected = vehiculoPeMapper.insert(vehiculoPe);
      if (log.isDebugEnabled())
        log.debug("[ Output [ " + numRowsAfected + "] ]");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_INSERTAR);
    }
    log.info("[ Fin ]");
    return numRowsAfected;
  }

  @Override
  public VehiculoPe findVehiculo(VehiculoPe vehiculoPe) throws SyncconException {
    log.info("[ Inicio ]");
    VehiculoPe vehiculoPe2 = null;

    try {
      if (log.isDebugEnabled())
        log.debug("Input [ " + BeanUtils.describe(vehiculoPe).toString() + " ]");
      vehiculoPe2 = vehiculoPeMapper.selectVehiculoByPlaca(vehiculoPe.getPlaca());
      if (log.isDebugEnabled())
        log.debug("[ Output [ " + BeanUtils.describe(vehiculoPe2) + "] ]");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_OBTENER);
    }
    log.info("[ Fin ]");
    return vehiculoPe2;
  }

  @Override
  public int actualizar(VehiculoPe vehiculoPe) throws SyncconException {
    log.info("[ Inicio ]");
    int numRowsAffected = 0;

    try {
      if (log.isDebugEnabled())
        log.debug("Input [ " + BeanUtils.describe(vehiculoPe).toString() + " ]");
      numRowsAffected = vehiculoPeMapper.updateByPlaca(vehiculoPe);
      if (log.isDebugEnabled())
        log.debug("[ Output [ " + numRowsAffected + "] ]");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_OBTENER);
    }
    log.info("[ Fin ]");
    return numRowsAffected;
  }

}
