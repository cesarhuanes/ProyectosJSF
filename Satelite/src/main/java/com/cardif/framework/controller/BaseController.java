package com.cardif.framework.controller;

import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cardif.satelite.model.OpcionMenu;
import com.cardif.satelite.seguridad.controller.LoginController;

@Controller("baseController")
@Scope("request")
public abstract class BaseController {
  public static final Logger log = Logger.getLogger(BaseController.class);

  @Autowired
  private LoginController loginController;

  public boolean tieneAcceso() {
    boolean acceso = false;

    if (llamadoMenu()) {
      return true;
    }
    List<OpcionMenu> opcionMenuList = loginController.getListaOpciones();

    if (opcionMenuList != null) {
      for (OpcionMenu opcion : opcionMenuList) {
        if (opcion.getUrl() != null && !opcion.getUrl().equals("") && getRequestURL().contains(opcion.getUrl().replace("xhtml", "jsf"))) {
          acceso = true;
        }
      }
    }

    log.debug("No cuenta con los accesos necesarios");
    return acceso;
  }

  public boolean llamadoMenu() {
    boolean var = false;

    if (getRequestURL().indexOf("principal") > 0 || getRequestURL().indexOf("login") > 0) {
      return true;
    }
    return var;
  }

  public String getRequestURL() {
    Object request = FacesContext.getCurrentInstance().getExternalContext().getRequest();
    if (request instanceof HttpServletRequest) {
      return ((HttpServletRequest) request).getRequestURL().toString();
    } else {
      return "";
    }
  }

  public abstract String inicio();

}
