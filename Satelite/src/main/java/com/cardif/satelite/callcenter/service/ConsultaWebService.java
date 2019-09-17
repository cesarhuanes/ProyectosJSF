package com.cardif.satelite.callcenter.service;

import java.math.BigInteger;

import com.bnpparibas.cardif.offline.ws.schema.offline.v2.Policy;
import com.bnpparibas.cardif.offline.ws.schema.offline.v2.PolicyArray;
import com.bnpparibas.cardif.offline.ws.schema.offline.v2.ThirdPartyArray;
import com.bnpparibas.cardif.offline.ws.service.offline.v2.WebSocioPortType;
import com.cardif.framework.excepcion.SyncconException;

/**
 * @author huamanju
 *
 */
public interface ConsultaWebService {

  /**
   * @author huamanju
   * @param stub
   * @param docu
   * @return
   * @throws SyncconException
   */
  public ThirdPartyArray findThirdPartyByDocumentNumber(WebSocioPortType web, String docu) throws SyncconException;

  /**
   * @author huamanju
   * @param stub
   * @param id
   * @return
   * @throws SyncconException
   */
  public PolicyArray findPolicyByThirdPartyTptId(WebSocioPortType port, long id) throws SyncconException;

  /**
   * @author huamanju
   * @param stub
   * @param id
   * @return
   * @throws SyncconException
   */
  public Policy findPolicyDetailsByPolicyIdOrNumber(WebSocioPortType port, BigInteger id) throws SyncconException;

}
