package com.cardif.satelite.configuracion.service.impl;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.configuracion.dao.GenDepartamentoMapper;
import com.cardif.satelite.configuracion.service.GenDepartamentoService;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.satelite.GenDepartamento;

@Service("genDepartamentoService")
public class GenDepartamentoServiceImpl implements GenDepartamentoService {
  public static final Logger log = Logger.getLogger(GenDepartamentoServiceImpl.class);

  @Autowired
  private GenDepartamentoMapper genDepartamentoMapper;

  @Override
  public List<GenDepartamento> buscar() throws SyncconException {
    log.info("Inicio");
    List<GenDepartamento> lista = null;
    int size = 0;
    try {
      if (log.isDebugEnabled())
        log.debug("Input [" + "]");
      lista = genDepartamentoMapper.selectDepartamento();
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
  public GenDepartamento obtener(String codDepartamento) throws SyncconException {
    log.info("Inicio");
    GenDepartamento departamento = null;
    try {
      if (log.isDebugEnabled())
        log.debug("Input [" + codDepartamento + "]");
      departamento = genDepartamentoMapper.selectByPrimaryKey(codDepartamento);
      if (log.isDebugEnabled())
        log.debug("Output [" + BeanUtils.describe(departamento) + "]");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_OBTENER);
    }
    log.info("Fin");
    return departamento;
  }

  @Override
  public List<GenDepartamento> buscar(String codPais) throws SyncconException {
    log.info("Inicio");
    List<GenDepartamento> lista = null;
    int size = 0;
    try {
      if (log.isDebugEnabled())
        log.debug("Input [" + "]");
      lista = genDepartamentoMapper.selectDepartamentoPais(codPais);
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
