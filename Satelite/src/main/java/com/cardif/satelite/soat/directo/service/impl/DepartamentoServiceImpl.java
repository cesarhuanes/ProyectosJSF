package com.cardif.satelite.soat.directo.service.impl;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.soat.directo.dao.DepartamentoDirectoMapper;
import com.cardif.satelite.soat.directo.service.DepartamentoServiceDirecto;
import com.cardif.satelite.soat.model.Departamento;

/**
 * @author 2ariasju
 *
 */
@Service("departamentoServiceDirecto")
public class DepartamentoServiceImpl implements DepartamentoServiceDirecto {

  public static final Logger log = Logger.getLogger(DepartamentoServiceImpl.class);

  @Autowired
  private DepartamentoDirectoMapper departamentoDirectoMapper;

  @Override
  public List<Departamento> buscar() throws SyncconException {
    log.info("Inicio");
    List<Departamento> lista = null;
    int size = 0;
    try {
      if (log.isDebugEnabled())
        log.debug("Input [" + "]");
      lista = departamentoDirectoMapper.selectDepartamento();
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
  public Departamento obtener(String codDepartamento) throws SyncconException {
    log.info("Inicio");
    Departamento departamento = null;
    try {
      if (log.isDebugEnabled())
        log.debug("Input [" + codDepartamento + "]");
      departamento = departamentoDirectoMapper.selectDepartamentoPorCod(codDepartamento);
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
  public Departamento obtenerPorNom(String nomDepartamento) throws SyncconException {
    log.info("Inicio");
    Departamento departamento = null;
    try {
      if (log.isDebugEnabled())
        log.debug("Input [" + nomDepartamento + "]");
      departamento = departamentoDirectoMapper.selectDepartamentoNom(nomDepartamento);
      if (log.isDebugEnabled())
        log.debug("Output [" + BeanUtils.describe(departamento) + "]");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_OBTENER);
    }
    log.info("Fin");
    return departamento;
  }
}
