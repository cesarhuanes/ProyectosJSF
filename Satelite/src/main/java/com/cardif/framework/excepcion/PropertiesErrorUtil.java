package com.cardif.framework.excepcion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.cardif.satelite.configuracion.service.ErrorService;
import com.cardif.satelite.model.Error;

public class PropertiesErrorUtil {

  public static final Logger logger = Logger.getLogger(PropertiesErrorUtil.class);
  private static Map<String, String> propertiesMap;
  @Autowired
  private ErrorService errorService;

  public void loadProperties() {
    propertiesMap = new HashMap<String, String>();
    try {
      List<Error> errorList = errorService.listar();
      for (Error error : errorList) {
        String keyStr = String.valueOf(error.getCodError());
        propertiesMap.put(keyStr, error.getNomValor());
      }

    } catch (SyncconException e) {
      logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
      logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
    }

  }

  public static String getProperty(String name) {
    return propertiesMap.get(name);
  }

}
