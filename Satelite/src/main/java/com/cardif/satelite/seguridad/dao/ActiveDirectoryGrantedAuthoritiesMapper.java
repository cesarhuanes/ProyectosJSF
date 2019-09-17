package com.cardif.satelite.seguridad.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.Perfil;
import com.cardif.satelite.seguridad.bean.SyncconAuthority;
import com.cardif.satelite.seguridad.service.PerfilService;

/**
 * Maps groups defined in LDAP to roles for a specific user.
 */
public class ActiveDirectoryGrantedAuthoritiesMapper implements GrantedAuthoritiesMapper {

  public static final Logger logger = Logger.getLogger(ActiveDirectoryGrantedAuthoritiesMapper.class);

  @Autowired
  private PerfilService perfilService;

  // CRDF PE MIG

  @Override
  public Collection<? extends GrantedAuthority> mapAuthorities(final Collection<? extends GrantedAuthority> authorities) {

    List<SyncconAuthority> roles = new ArrayList<SyncconAuthority>();

    for (GrantedAuthority authority : authorities) {
      logger.debug("Grupo: " + authority.getAuthority());
      SyncconAuthority rol = getAuthority(authority.getAuthority());
      if (rol != null) {
        roles.add(rol);
        break;
      }
    }

    return roles;
  }

  private SyncconAuthority getAuthority(String authority) {
    try {
      Perfil perfil = perfilService.obtenerAuthority(authority);
      if (perfil != null && perfil.getAuthority() != null) {
        SyncconAuthority syncconAuthority = new SyncconAuthority(perfil.getAuthority());
        return syncconAuthority;
      }
    } catch (SyncconException e) {
      logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
      logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
    }
    return null;
  }
}
