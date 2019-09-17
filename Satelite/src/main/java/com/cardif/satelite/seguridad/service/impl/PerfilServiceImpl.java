package com.cardif.satelite.seguridad.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.Perfil;
import com.cardif.satelite.seguridad.dao.OpcionMenuPerfilMapper;
import com.cardif.satelite.seguridad.dao.PerfilMapper;
import com.cardif.satelite.seguridad.service.PerfilService;

@Service("perfilService")
public class PerfilServiceImpl implements PerfilService {

  public static final Logger log = Logger.getLogger(PerfilService.class);

  @Autowired
  public PerfilMapper perfilMapper;
  @Autowired
  public OpcionMenuPerfilMapper opcionMenuPerfilMapper;

  @Override
  public List<Perfil> buscarPerfil(String nomPerfil, String descPerfil) throws SyncconException {
    log.info("Inicio");
    List<Perfil> lista = null;
    int size = 0;
    try {
      if (log.isDebugEnabled())
        log.debug("Input [1.-" + nomPerfil + ", 2.-" + descPerfil);
      lista = perfilMapper.selectLike(nomPerfil, descPerfil);
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
  public List<Perfil> buscarPerfil() throws SyncconException {
    log.info("Inicio");
    List<Perfil> lista = null;
    int size = 0;
    try {
      if (log.isDebugEnabled())
        log.debug("Input []");
      lista = perfilMapper.selectAll();
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
  public Perfil obtener(Long codPerfil) throws SyncconException {
    log.info("Inicio");
    Perfil perfil = null;
    try {
      if (log.isDebugEnabled())
        log.debug("Input [" + codPerfil + "]");
      perfil = perfilMapper.selectByPrimaryKey(codPerfil);
      if (log.isDebugEnabled())
        log.debug("Output [" + BeanUtils.describe(perfil).toString() + "]");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_OBTENER);
    }
    log.info("Fin");
    return perfil;
  }

  @Override
  public Perfil obtener(String authority) throws SyncconException {
    log.info("Inicio");
    Perfil perfil = null;
    try {
      if (log.isDebugEnabled())
        log.debug("Input [" + authority + "]");
      perfil = perfilMapper.selectByAuthority(authority);
      if (log.isDebugEnabled())
        log.debug("Output [" + BeanUtils.describe(perfil).toString() + "]");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_OBTENER);
    }
    log.info("Fin");
    return perfil;
  }

  @Override
  public Perfil obtenerAuthority(String ldapGrupo) throws SyncconException {
    log.info("Inicio");
    Perfil perfil = null;
    try {
      if (log.isDebugEnabled())
        log.debug("Input [" + ldapGrupo + "]");
      ldapGrupo = ldapGrupo.substring(3, ldapGrupo.indexOf(","));
      perfil = perfilMapper.selectByLdapGrupo(ldapGrupo);
      if (log.isDebugEnabled())
        log.debug("Output [" + BeanUtils.describe(perfil).toString() + "]");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_OBTENER);
    }
    log.info("Fin");
    return perfil;
  }

  @Override
  public void insertar(Perfil perfil) throws SyncconException {
    log.info("Inicio");
    try {
      if (log.isDebugEnabled())
        log.debug("Input [" + BeanUtils.describe(perfil).toString() + "]");
      if (perfil.getCodPerfil() == 0)
        perfil.setCodPerfil(null);
      perfil.setFecCreacion(new Date(System.currentTimeMillis()));
      perfilMapper.insert(perfil);
      if (log.isDebugEnabled())
        log.debug("Output []");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_INSERTAR);
    }
    log.info("Fin");
  }

  @Override
  public void actualizar(Perfil perfil) throws SyncconException {
    log.info("Inicio");
    try {
      if (log.isDebugEnabled())
        log.debug("Input [" + BeanUtils.describe(perfil).toString() + "]");
      perfil.setFecModificacion(new Date(System.currentTimeMillis()));
      perfilMapper.updateByPrimaryKey(perfil);
      if (log.isDebugEnabled())
        log.debug("Output []");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_ACTUALIZAR);
    }
    log.info("Fin");
  }

  @Override
  public void eliminar(Long codPerfil) throws SyncconException {
    log.info("Inicio");
    try {
      if (log.isDebugEnabled())
        log.debug("Input [" + codPerfil + "]");
      perfilMapper.deleteByPrimaryKey(codPerfil);
      if (log.isDebugEnabled())
        log.debug("Output []");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_ELIMINAR);
    }
    log.info("Fin");
  }

}
