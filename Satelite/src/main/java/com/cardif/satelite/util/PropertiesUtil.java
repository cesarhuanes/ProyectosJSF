package com.cardif.satelite.util;

import static com.cardif.satelite.constantes.Constantes.COD_PARAM_PROPIEDADES_SATELITE;
import static com.cardif.satelite.constantes.Constantes.TIP_PARAM_DETALLE;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.configuracion.service.ParametroService;
import com.cardif.satelite.model.Parametro;

public class PropertiesUtil {

  public static final Logger logger = Logger.getLogger(PropertiesUtil.class);
  private static Map<String, String> propertiesMap;

  @Autowired
  private ParametroService parametroService;

  private PropertiesUtil() {

  }

  public void loadProperties() {
    propertiesMap = new HashMap<String, String>();
    try {
      List<Parametro> parametroList = parametroService.buscar(COD_PARAM_PROPIEDADES_SATELITE, TIP_PARAM_DETALLE);
      for (Parametro parametro : parametroList) {
        String keyStr = parametro.getCodValor();
        propertiesMap.put(keyStr, parametro.getNomValor());
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
