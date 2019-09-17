package com.cardif.satelite.seguridad.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;
import org.springframework.stereotype.Controller;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.OpcionMenu;
import com.cardif.satelite.model.Perfil;
import com.cardif.satelite.seguridad.service.OpcionMenuService;
import com.cardif.satelite.seguridad.service.PerfilService;
import com.cardif.satelite.seguridad.service.SeguridadManager;
import com.cardif.satelite.util.PropertiesUtil;

@Controller("loginController")
@Scope("session")
public class LoginController implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -1073229911228321219L;

  public static final Logger log = Logger.getLogger(LoginController.class);

  private String usuario;
  private String contrasena;

  // Opcion seleccionada en el menu
  private String current;

  private List<OpcionMenu> listaOpciones;

  @Autowired
  private SeguridadManager seguridadManager;
  @Autowired
  private OpcionMenuService opcionMenuService;
  @Autowired
  private PerfilService perfilService;

  private String ambiente;

  private String nombreUsuario;
  private String valor;
  private Perfil perfil;

  public String validarUsuario() {

    log.info("Inicio");
    String validacion = "";
    try {
      seguridadManager.validarUsuario(usuario, contrasena);
      String aut = null;

      for (GrantedAuthority authority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
        aut = authority.getAuthority();
        break;
      }
      perfil = perfilService.obtener(aut);
      FacesContext context = FacesContext.getCurrentInstance();
      context.getExternalContext().getSessionMap().put("perfil", perfil);

      if (perfil != null) {
        listaOpciones = opcionMenuService.buscar(perfil.getCodPerfil());
        LdapUserDetailsImpl ldapUser = (LdapUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String dn = ldapUser.getDn();
        nombreUsuario = dn.substring(3, dn.indexOf(","));
        log.info("Usuario Logeado: " + nombreUsuario);
        validacion = "valido";
      } else {
        throw new SyncconException(ErrorConstants.COD_ERROR_PERFIL);
      }
      log.debug("Usuario Conectado: " + usuario);

      StringBuilder sb = new StringBuilder();
      sb.append("AMBIENTE DE ");
      sb.append(PropertiesUtil.getProperty("AB"));
      sb.append(" ");
      sb.append(PropertiesUtil.getProperty("VS"));
      ambiente = sb.toString();

    } catch (SyncconException ex) {
      log.error("ERROR SYNCCON: " + ex.getMessageComplete());
      FacesMessage facesMsg = new FacesMessage(ex.getSeveridad(), ex.getMessage(), null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
      validacion = "invalido";
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
      validacion = "invalido";
    }
    log.info("Fin");
    return validacion;
  }

  public String cerrarSesion() {
    log.info("Inicio");

    seguridadManager.cerrarSesion();

    log.info("Fin");
    return "cerrar";
  }

  public String getContrasena() {
    return contrasena;
  }

  public void setContrasena(String contrasena) {
    this.contrasena = contrasena;
  }

  public String getUsuario() {
    return usuario;
  }

  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }

  public List<OpcionMenu> getListaOpciones() {
    return listaOpciones;
  }

  public void setListaOpciones(List<OpcionMenu> listaOpciones) {
    this.listaOpciones = listaOpciones;
  }

  public void setNombreUsuario(String nombreUsuario) {
    this.nombreUsuario = nombreUsuario;
  }

  public String getNombreUsuario() {
    return nombreUsuario;
  }

  public String getCurrent() {
    return current;
  }

  public void setCurrent(String current) {
    this.current = current;
  }

  public String getValor() {
    return valor;
  }

  public void setValor(String valor) {
    this.valor = valor;
  }

  public String getAmbiente() {
    return ambiente;
  }

  public void setAmbiente(String ambiente) {
    this.ambiente = ambiente;
  }

  public Perfil getPerfil() {
    return perfil;
  }

  public void setPerfil(Perfil perfil) {
    this.perfil = perfil;
  }

}