package com.cardif.satelite.tesoreria.controller;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cardif.sunsystems.controller.SunsystemsController;

@Controller("loginSunController")
@Scope("session")
public class LoginSunController {

  private String user;
  private String pass;
  private String token;
  private boolean loged = false;

  private SunsystemsController controlSun = SunsystemsController.getInstance();

  public static final Logger LOGGER = Logger.getLogger(LoginSunController.class);

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public String getPass() {
    return pass;
  }

  public void setPass(String pass) {
    this.pass = pass;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public boolean isLoged() {
    return loged;
  }

  public void setLoged(boolean loged) {
    this.loged = loged;
  }

  public String validarUsuario() {
    String auth = null;
    setLoged(false);
    setToken(null);
    try {
      auth = autentificarWebService();
      if (auth == null) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, "La autentificacion de SUN ha fallado", null);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
        System.out.println("Autentificacion Fallida en SunSystems");
        setLoged(false);
        setToken(null);
      } else {

        System.out.println("Autentificacion Exitosa en SunSystems");
        setLoged(true);
        setUser(null);
        setPass(null);
        setToken(auth);

        return "welcomeSun";
      }

    } catch (Exception e) {
      LOGGER.error(e);
    }

    return null;
  }

  private String autentificarWebService() throws Exception {
    LOGGER.info("Ini Validando ingreso a SUN");
    String resultAuth = controlSun.autentificarWebService(user, pass);
    LOGGER.info("Fin Validando ingreso a SUN: " + resultAuth);
    return resultAuth;
  }

}
