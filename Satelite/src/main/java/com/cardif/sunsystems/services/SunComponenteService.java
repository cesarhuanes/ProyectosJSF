package com.cardif.sunsystems.services;

import java.net.MalformedURLException;
import java.net.URL;

import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.cardif.satelite.constantes.Constantes;
import com.systemsunion.connect.webservices.ComponentExecutorExecuteRequest;

public class SunComponenteService {
  private static String SERVICE_WSDL;
  private static URL URL_SERVICE_WSDL;
  private static Logger logger = Logger.getLogger(SunComponenteService.class);

  static {
    Context env;
    try {
      env = (Context) new InitialContext().lookup("java:comp/env");
      SERVICE_WSDL = (String) env.lookup("URL_COMPONENTE_SUN");

      URL_SERVICE_WSDL = new URL(SERVICE_WSDL);

    } catch (NamingException e) {
      logger.error("Error al obtener endpoint de capa de sun ", e);
           
    }

    catch (MalformedURLException e) {
      logger.error("Error al obtener endpoint de capa de sun ", e);
    }

  }

  public SunComponenteService() {
  }

  public com.systemsunion.connect.webservices.ComponentExecutorPortType getPuertoExecutor() {
    logger.info("URL_SERVICE_WSDL antes instanciar ComponentExecutor: " + URL_SERVICE_WSDL.toString());
    com.systemsunion.connect.webservices.ComponentExecutor service = new com.systemsunion.connect.webservices.ComponentExecutor(URL_SERVICE_WSDL);
    logger.info("ComponentExecutorPort: " + service.getComponentExecutorPort().toString());
    com.systemsunion.connect.webservices.ComponentExecutorPortType port = service.getComponentExecutorPort();
    logger.info("Despues de Obtener ComponentExecutorPort: " + port.toString());
    return port;
  }

  public String ejecutaConsulta(String Component, String Method, String Payload) {
    FacesContext context = FacesContext.getCurrentInstance();
    String token = (String) context.getExternalContext().getSessionMap().get(Constantes.MDP_AUTH_SUNSYSTEMS_TOKEN);
    com.systemsunion.connect.webservices.ComponentExecutorExecuteRequest parameters = null;
    parameters = new ComponentExecutorExecuteRequest();
    parameters.setAuthentication(token);
    parameters.setComponent(Component);
    parameters.setMethod(Method);
    parameters.setPayload(Payload.replace("\r", "").replace("\n", "").replace("  ", ""));
    logger.info("Parametros antes realizar el envio de la trama Payload");
    logger.info("Component: " + Component);
    logger.info("Method: " + Method);
    logger.debug("Payload: " + Payload);

    com.systemsunion.connect.webservices.ComponentExecutorExecuteResponse result = getPuertoExecutor().execute(parameters);
    logger.debug("Response: " + result.getResponse());
    return result.getResponse();
  } 
  
}
