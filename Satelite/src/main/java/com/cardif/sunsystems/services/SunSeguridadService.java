package com.cardif.sunsystems.services;

import java.net.MalformedURLException;
import java.net.URL;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.cardif.sunsystems.util.ConstantesSun;
import com.systemsunion.connect.webservices.SecurityProviderAuthenticateRequest;

public class SunSeguridadService {
  private static String SERVICE_WSDL;
  private static URL URL_SERVICE_WSDL;
  private static Logger logger = Logger.getLogger(SunComponenteService.class);
  private String Username = ConstantesSun.AUTH_USR_SUN_LOGIN;
  private String Password = ConstantesSun.AUTH_USR_SUN_PASS;

  static {
    Context env;
    try {
      env = (Context) new InitialContext().lookup("java:comp/env");
      SERVICE_WSDL = (String) env.lookup("URL_SEGURIDAD_SUN");

      URL_SERVICE_WSDL = new URL(SERVICE_WSDL);

    } catch (NamingException e) {
      logger.error("Error al obtener endpoint de capa de sun ", e);

    }

    catch (MalformedURLException e) {
      logger.error("Error al obtener endpoint de capa de sun ", e);
    }

  }

  public com.systemsunion.connect.webservices.SecurityProviderPortType getPuerto() {
    logger.info("URL_SERVICE_WSDL antes instanciar SecurityProvider: " + URL_SERVICE_WSDL.toString());
    com.systemsunion.connect.webservices.SecurityProvider service = new com.systemsunion.connect.webservices.SecurityProvider(URL_SERVICE_WSDL);
    logger.info("Antes de Obtener SecurityProviderPort: " + service.getSecurityProviderPort().toString());
    com.systemsunion.connect.webservices.SecurityProviderPortType port = service.getSecurityProviderPort();
    logger.info("Despues de Obtener SecurityProviderPort: " + port.toString());
    return port;
  }

  public String autentificarUsuario() {
    com.systemsunion.connect.webservices.SecurityProviderAuthenticateRequest parametros_usuario = new SecurityProviderAuthenticateRequest();
    parametros_usuario.setName(Username);
    parametros_usuario.setPassword(Password);
    logger.info("Invocando al getPuerto().authenticate(parametros_usuario)");
    com.systemsunion.connect.webservices.SecurityProviderAuthenticateResponse result = getPuerto().authenticate(parametros_usuario);
    return result.getResponse();
  }

  /* INICIO JARIASS SYNCCON 12/02/2015 */
  public String autentificarUsuario(String user, String pass) {
    try {
      com.systemsunion.connect.webservices.SecurityProviderAuthenticateRequest parametros_usuario = new SecurityProviderAuthenticateRequest();
      parametros_usuario.setName(user);
      parametros_usuario.setPassword(pass);
      logger.info("Antes de Llamar a obtener Puerto: ");
      com.systemsunion.connect.webservices.SecurityProviderPortType port = getPuerto();
      logger.info("Antes de Llamar a la autenticacion: ");
      com.systemsunion.connect.webservices.SecurityProviderAuthenticateResponse result = port.authenticate(parametros_usuario);
      logger.info("Termino la autenticacion: ");

      return result.getResponse();
    } catch (NullPointerException e) {
      return null;
    }

  }
  /* FIN JARIASS SYNCCON 12/02/2015 */
}
