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
import com.cardif.satelite.tesoreria.service.ChequeService;
import com.cardif.satelite.tesoreria.service.FirmanteService;
import com.cardif.satelite.util.SateliteUtil;

@Controller
@Scope("request")
public class ClaveController extends BaseController implements Serializable {

  private static final long serialVersionUID = 1L;

  public static final Logger LOGGER = Logger.getLogger(ConfigController.class);

  @Autowired
  private ChequeService chequeService;

  @Autowired
  private FirmanteService firmanteService;
  private String claveAntigua;

  private String clave;
  private String claveVerificacion;
  private Firmante firmante;

  @Override
  @PostConstruct
  public String inicio() {
    if (tieneAcceso()) {

      LOGGER.info("Inicio");
      String usuario = SecurityContextHolder.getContext().getAuthentication().getName();
      firmante = chequeService.buscarFirmante(usuario);

      LOGGER.info("Fin");
    }
    return null;
  }

  public String configurar() {
    String usuario = SecurityContextHolder.getContext().getAuthentication().getName();

    if (claveAntigua == null || claveAntigua.isEmpty()) {
      SateliteUtil.mostrarMensaje("De ingresar la contraseña antigua");
      return null;
    }

    if (clave == null || claveVerificacion == null || clave.isEmpty() || claveVerificacion.isEmpty()) {
      SateliteUtil.mostrarMensaje("De ingresar la nueva contraseña 2 veces");
      return null;
    }

    boolean existe = firmanteService.validarFirmante(usuario, claveAntigua);

    if (existe) {

      if (!clave.equals(claveVerificacion)) {
        SateliteUtil.mostrarMensaje("Las contraseñas nuevas no coinciden");
        return null;
      }

      firmanteService.configurarClave(firmante, clave);
      SateliteUtil.mostrarMensaje("La contraseña se actualizó exitosamente");

    } else {
      SateliteUtil.mostrarMensaje("Error al ingresar contraseña antigua");

    }

    return null;
  }

  public String getClave() {
    return clave;
  }

  public void setClave(String clave) {
    this.clave = clave;
  }

  public String getClaveVerificacion() {
    return claveVerificacion;
  }

  public void setClaveVerificacion(String claveVerificacion) {
    this.claveVerificacion = claveVerificacion;
  }

  public Firmante getFirmante() {
    return firmante;
  }

  public void setFirmante(Firmante firmante) {
    this.firmante = firmante;
  }

  public String getClaveAntigua() {
    return claveAntigua;
  }

  public void setClaveAntigua(String claveAntigua) {
    this.claveAntigua = claveAntigua;
  }

}
