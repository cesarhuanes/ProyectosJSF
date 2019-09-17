package com.cardif.satelite.siniestro.service.impl;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.SiniProducto;
import com.cardif.satelite.siniestro.dao.SiniProductoMapper;
import com.cardif.satelite.siniestro.service.SiniProductoService;

@Service("siniProductoService")
public class SiniProductoServiceImpl implements SiniProductoService {
  public static final Logger log = Logger.getLogger(SiniProductoServiceImpl.class);

  @Autowired
  SiniProductoMapper siniProductoMapper;

  @Override
  public List<SiniProducto> listar() throws SyncconException {
    log.info("Inicio");
    List<SiniProducto> lista;
    try {
      if (log.isDebugEnabled())
        log.debug("Input []");
      lista = siniProductoMapper.listarTodos();
      int size = (lista != null) ? lista.size() : 0;
      if (log.isDebugEnabled())
        log.debug("Output [" + size + "]");

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
    }
    log.info("Fin");
    return lista;
  }

  @Override
  public List<SiniProducto> listar(String codRamo) throws SyncconException {
    log.info("Inicio");
    List<SiniProducto> lista;
    try {
      if (log.isDebugEnabled())
        log.debug("Input [" + codRamo + "]");

      lista = siniProductoMapper.listar(codRamo);

      int size = (lista != null) ? lista.size() : 0;

      if (log.isDebugEnabled())
        log.debug("Output [" + size + "]");

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
    }
    log.info("Fin");
    return lista;
  }

  @Override
  public SiniProducto obtener(String nroProducto) throws SyncconException {
    log.info("Inicio");
    SiniProducto siniProducto;
    try {
      if (log.isDebugEnabled())
        log.debug("Input [" + nroProducto + "]");

      siniProducto = siniProductoMapper.selectByPrimaryKey(nroProducto);

      if (log.isDebugEnabled())
        log.debug("Output [" + BeanUtils.describe(siniProducto) + "]");

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_OBTENER);
    }
    log.info("Fin");
    return siniProducto;
  }

}
