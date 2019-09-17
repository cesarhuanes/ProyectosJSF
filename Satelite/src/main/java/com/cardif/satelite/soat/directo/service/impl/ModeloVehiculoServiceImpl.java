/**
 * 
 */
package com.cardif.satelite.soat.directo.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.soat.directo.dao.ModeloVehiculoDirectoMapper;
import com.cardif.satelite.soat.directo.service.ModeloVehiculoServiceDirecto;
import com.cardif.satelite.soat.model.ModeloVehiculo;

/**
 * @author 2ariasju
 *
 */
@Service("modeloVehiculoServiceDirecto")
public class ModeloVehiculoServiceImpl implements ModeloVehiculoServiceDirecto {

  public static final Logger log = Logger.getLogger(ModeloVehiculoServiceImpl.class);

  @Autowired
  private ModeloVehiculoDirectoMapper modeloVehiculoDirectoMapper;

  @Override
  public ModeloVehiculo obtener(String codModelo) throws SyncconException {
    log.info("Inicio");
    ModeloVehiculo modelo = null;
    try {
      if (log.isDebugEnabled())
        log.debug("Input [" + codModelo + "]");
      modelo = modeloVehiculoDirectoMapper.selectByPrimaryKey(new BigDecimal(codModelo));
      if (log.isDebugEnabled())
        log.debug("Output [" + BeanUtils.describe(codModelo) + "]");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_OBTENER);
    }
    log.info("Fin");
    return modelo;
  }

  @Override
  public List<ModeloVehiculo> buscarModelo(BigDecimal codMarca) throws SyncconException {
    log.info("Inicio");
    List<ModeloVehiculo> lista = null;
    int size = 0;
    try {
      if (log.isDebugEnabled())
        log.debug("Input [" + codMarca + "]");
      lista = modeloVehiculoDirectoMapper.selectModelo(codMarca);
      size = (lista != null) ? lista.size() : 0;
      if (log.isDebugEnabled())
        log.debug("Output [Regitros:" + size + "]");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
    }
    log.info("Fin");
    return lista;
  }
}
