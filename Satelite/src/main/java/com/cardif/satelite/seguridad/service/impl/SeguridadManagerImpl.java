package com.cardif.satelite.seguridad.service.impl;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.bnpparibas.cardif.peru.security.ldap.ActiveDirectoryLdapAuthenticationCardifProvider;
import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.configuracion.service.ParametroService;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.seguridad.dao.ActiveDirectoryGrantedAuthoritiesMapper;
import com.cardif.satelite.seguridad.service.SeguridadManager;

@Service("seguridadManager")
public class SeguridadManagerImpl implements SeguridadManager {

  public static final Logger log = Logger.getLogger(SeguridadManagerImpl.class);

  @Autowired
  private ActiveDirectoryGrantedAuthoritiesMapper grantedAuthoritiesMapper;

  @Autowired
  private ParametroService parametroService;

  @Override
  public void validarUsuario(String usuario, String contrasena) throws SyncconException {

    String domain = parametroService.obtenerDescripcion("DOM", "064", "D");
    String url = parametroService.obtenerDescripcion("URL", "064", "D");
    String uTyp = parametroService.obtenerDescripcion("UTYP", "064", "D");
    ActiveDirectoryLdapAuthenticationCardifProvider providerLdap = new ActiveDirectoryLdapAuthenticationCardifProvider(domain, url);
    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(usuario, contrasena);
    providerLdap.setConvertSubErrorCodesToExceptions(true);
    providerLdap.setUseAuthenticationRequestCredentials(true);
    providerLdap.setAuthoritiesMapper(grantedAuthoritiesMapper);

    log.info("Inicio");
    try {

      Authentication authenticate = providerLdap.authenticate(token, uTyp);

      if (authenticate.isAuthenticated()) {
        SecurityContextHolder.getContext().setAuthentication(authenticate);
      }
    } catch (BadCredentialsException be) {
      log.error(be.getMessage(), be);

      throw new SyncconException(ErrorConstants.COD_ERROR_AUTENTICACION);
    } catch (DisabledException de) {
      log.error(de.getMessage(), de);
      throw new SyncconException(ErrorConstants.COD_ERROR_AUTENTICACION);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_AUTENTICACION);
    }
    log.info("Fin");
  }

  @Override
  public void cerrarSesion() {

    ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();

    HttpSession ses = (HttpSession) contexto.getSession(false);

    ses.invalidate();

  }
}