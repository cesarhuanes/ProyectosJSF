package com.cardif.satelite.seguridad.service.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.OpcionMenu;
import com.cardif.satelite.seguridad.dao.OpcionMenuMapper;
import com.cardif.satelite.seguridad.service.OpcionMenuService;

@Service("opcionMenuService")
public class OpcionMenuServiceImpl implements OpcionMenuService, Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -7645540927094529196L;

  public static final Logger log = Logger.getLogger(OpcionMenuServiceImpl.class);

  @Autowired
  public OpcionMenuMapper opcionMenuMapper;

  @Override
  public List<OpcionMenu> buscar() throws SyncconException {
    log.info("Inicio");
    List<OpcionMenu> lista = null;
    int size = 0;
    try {
      if (log.isDebugEnabled())
        log.debug("Input []");
      lista = opcionMenuMapper.selectOpciones();
      size = (lista != null) ? lista.size() : 0;
      if (log.isDebugEnabled())
        log.debug("Output [Registros = " + size + "]");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
    }
    log.info("Fin");
    return lista;
  }

  @Override
  public List<OpcionMenu> buscar(Long codPerfil) throws SyncconException {
    log.info("Inicio");
    List<OpcionMenu> lista = null;
    int size = 0;
    try {
      if (log.isDebugEnabled())
        log.debug("Input [" + codPerfil + "]");

      lista = opcionMenuMapper.selectByPerfil(codPerfil);

      size = (lista != null) ? lista.size() : 0;

      if (log.isDebugEnabled())
        log.debug("Output [Registros = " + size + "]");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
    }
    log.info("Fin");
    return lista;
  }

  @Override
  public void eliminar(Long codOpcion) throws SyncconException {
    log.info("Inicio");
    try {
      if (log.isDebugEnabled())
        log.debug("Input [" + codOpcion + "]");

      opcionMenuMapper.deleteByPrimaryKey(codOpcion);

      if (log.isDebugEnabled())
        log.debug("Output []");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_ELIMINAR);
    }
    log.info("Fin");
  }

  @Override
  public void insertar(OpcionMenu opcionMenu) throws SyncconException {
    log.info("Inicio");
    try {
      if (log.isDebugEnabled())
        log.debug("Input [" + opcionMenu + "]");
      opcionMenuMapper.insert(opcionMenu);
      if (log.isDebugEnabled())
        log.debug("Output []");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_INSERTAR);
    }
    log.info("Fin");
  }

  @Override
  public void actualizar(OpcionMenu opcionMenu) throws SyncconException {
    log.info("Inicio");
    try {
      if (log.isDebugEnabled())
        log.debug("Input [" + opcionMenu + "]");
      opcionMenuMapper.updateByPrimaryKey(opcionMenu);
      if (log.isDebugEnabled())
        log.debug("Output []");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_ACTUALIZAR);
    }
    log.info("Fin");
  }

  @Override
  public OpcionMenu obtener(Long codOpcion) throws SyncconException {
    log.info("Inicio");
    OpcionMenu opcion = null;
    try {
      opcion = opcionMenuMapper.selectByPrimaryKey(codOpcion);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
    }
    log.info("Fin");
    return opcion;
  }

}
