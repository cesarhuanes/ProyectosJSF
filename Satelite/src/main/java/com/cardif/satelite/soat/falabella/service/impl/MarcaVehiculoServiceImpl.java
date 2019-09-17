/**
 * 
 */
package com.cardif.satelite.soat.falabella.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.soat.falabella.dao.MarcaVehiculoMapper;
import com.cardif.satelite.soat.falabella.service.MarcaVehiculoService;
import com.cardif.satelite.soat.model.MarcaVehiculo;

@Service("marcaVehiculoService")
public class MarcaVehiculoServiceImpl implements MarcaVehiculoService {

  public static final Logger log = Logger.getLogger(MarcaVehiculoServiceImpl.class);

  @Autowired
  private MarcaVehiculoMapper marcaVehiculoMapper;

  @Override
  public List<MarcaVehiculo> buscar() throws SyncconException {
    log.info("Inicio");
    List<MarcaVehiculo> lista = null;
    int size = 0;
    try {
      if (log.isDebugEnabled())
        log.debug("Input [" + "]");
      lista = marcaVehiculoMapper.selectMarca();
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

  @Override
  public MarcaVehiculo obtener(String codMarca) throws SyncconException {
    log.info("Inicio");
    MarcaVehiculo marca = null;
    try {
      if (log.isDebugEnabled())
        log.debug("Input [" + codMarca + "]");
      marca = marcaVehiculoMapper.selectByPrimaryKey(new BigDecimal(codMarca));
      if (log.isDebugEnabled())
        log.debug("Output [" + BeanUtils.describe(marca) + "]");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_OBTENER);
    }
    log.info("Fin");
    return marca;
  }

}
