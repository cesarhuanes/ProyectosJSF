package com.cardif.satelite.callcenter.service.impl;

import java.math.BigInteger;

import javax.faces.application.FacesMessage;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.bnpparibas.cardif.offline.ws.schema.offline.v2.Policy;
import com.bnpparibas.cardif.offline.ws.schema.offline.v2.PolicyArray;
import com.bnpparibas.cardif.offline.ws.schema.offline.v2.ThirdPartyArray;
import com.bnpparibas.cardif.offline.ws.service.offline.v2.WebSocioPortType;
import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.callcenter.service.ConsultaWebService;
import com.cardif.satelite.constantes.ErrorConstants;

/**
 * @author huamanju
 *
 */
@Service("consultaWebService")
public class ConsultaWebServiceImpl implements ConsultaWebService {
  public static final Logger log = Logger.getLogger(ConsultaWebServiceImpl.class);

  private Policy poliza;

  /*
   * (non-Javadoc)
   * 
   * @see com.cardif.satelite.callcenter.service.WebSocioService# findThirdPartyByDocumentNumber
   * (com.bnpparibas.cardif.offline.ws.service.offline.v2.WebSocioServiceStub, java.lang.String)
   */
  @Override
  public ThirdPartyArray findThirdPartyByDocumentNumber(WebSocioPortType port, String docu) throws SyncconException {
    log.info("Inicio");
    ThirdPartyArray listaTercero;
    try {
      log.debug("Input [" + docu + "]");

      listaTercero = port.findThirdPartyByDocumentNumber(docu);

      log.debug("Output [" + listaTercero.getItem().get(0).getName() + "]");

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_DOCU_WS_ACSELE, FacesMessage.SEVERITY_INFO);
    }
    log.info("Fin");
    return listaTercero;
  }

  @Override
  public PolicyArray findPolicyByThirdPartyTptId(WebSocioPortType port, long id) throws SyncconException {
    log.info("Inicio");
    PolicyArray listaPolizas;
    try {
      log.debug("Input [" + id + "]");

      listaPolizas = port.findPolicyByThirdPartyTptId(BigInteger.valueOf(id));

      int size = (listaPolizas != null) ? listaPolizas.getItem().size() : 0;

      log.debug("Output [Registros = " + size + "]");

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_DOCU_WS_ACSELE, FacesMessage.SEVERITY_INFO);
    }
    log.info("Fin");
    return listaPolizas;
  }

  @Override
  public Policy findPolicyDetailsByPolicyIdOrNumber(WebSocioPortType port, BigInteger id) throws SyncconException {
    log.info("Inicio");
    try {

      poliza = port.findPolicyDetailsByPolicyIdOrNumber(id);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_DOCU_WS_ACSELE, FacesMessage.SEVERITY_INFO);
    }
    log.info("Fin");
    return poliza;
  }

}
