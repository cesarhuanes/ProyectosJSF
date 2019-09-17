package com.cardif.satelite.tesoreria.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import com.cardif.framework.controller.BaseController;
import com.cardif.satelite.tesoreria.model.Firmante;
import com.cardif.satelite.tesoreria.service.FirmanteService;
import com.cardif.satelite.util.SateliteUtil;

@Controller
@Scope("session")
public class LoginFirmanteController extends BaseController implements Serializable {

  private static final long serialVersionUID = 1L;

  public static final Logger LOGGER = Logger.getLogger(LoginFirmanteController.class);
  private String clave;

  @Autowired
  private FirmanteService firmanteService;

  private Firmante firmante;
  private boolean logged;

  @Override
  @PostConstruct
  public String inicio() {
    if (tieneAcceso()) {

      LOGGER.info("Inicio");

      setLogged(false);

      LOGGER.info("Fin");
    }
    return null;
  }

  public String acceder() {
    String usuario = SecurityContextHolder.getContext().getAuthentication().getName();

    logged = firmanteService.validarFirmante(usuario, clave);
    if (logged)
      return "aprobacionCheques";
    else
      SateliteUtil.mostrarMensaje("Contraseña inválida");
    return null;
  }

  public String getClave() {
    return clave;
  }

  public void setClave(String clave) {
    this.clave = clave;
  }

  public Firmante getFirmante() {
    return firmante;
  }

  public void setFirmante(Firmante firmante) {
    this.firmante = firmante;
  }

  public boolean isLogged() {
    return logged;
  }

  public void setLogged(boolean logged) {
    this.logged = logged;
  }

}
