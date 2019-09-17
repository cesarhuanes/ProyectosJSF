/**
 * 
 */
package com.cardif.satelite.tesoreria.service.impl;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.cardif.satelite.tesoreria.bean.TipoCambioBean;
import com.cardif.satelite.tesoreria.handler.PayloadHandler;
import com.cardif.satelite.tesoreria.service.TipoCambioService;
import com.cardif.sunsystems.services.SunComponenteService;
import com.cardif.sunsystems.util.ConstantesSun;

@Service("tipoCambioService")
public class TipoCambioServiceImpl implements TipoCambioService {
  public static final Logger logger = Logger.getLogger(TipoCambioServiceImpl.class);
  private SunComponenteService sunComponent = null;
  private PayloadHandler payloadHandler;

  @Override
  public TipoCambioBean buscarTipoCambioDia(String unidadNegocio, String Fecha, String monedaDesde, String monedaHasta) {
    TipoCambioBean tipoCambioBean = null;

    if (logger.isInfoEnabled()) {
      logger.info("Inicio");
    }

    try {
      sunComponent = new SunComponenteService();
      payloadHandler = PayloadHandler.newInstance();

      String payload = payloadHandler.generarPayloadTipoCambio(unidadNegocio, Fecha, monedaDesde, monedaHasta);

      if (logger.isDebugEnabled()) {
        logger.debug("Payload generado para consultar: " + payload);
      }

      String journalResultXML = sunComponent.ejecutaConsulta(ConstantesSun.SSC_ComponenteCurrencyDailyRates, ConstantesSun.SSC_MetodoQuery, payload);
      if (logger.isInfoEnabled()) {
        logger.info("Resultado en formato XML: \n" + journalResultXML);
      }

      // Realizando el UNMARSHALLER de la respuesta
      JAXBContext jc = JAXBContext.newInstance(com.cardif.sunsystems.mapeo.currencyDailyRates.SSC.class);
      Unmarshaller unmarshaller = jc.createUnmarshaller();
      StringReader reader = new StringReader(journalResultXML);
      com.cardif.sunsystems.mapeo.currencyDailyRates.SSC tipoCambio = (com.cardif.sunsystems.mapeo.currencyDailyRates.SSC) unmarshaller.unmarshal(reader);

      if (logger.isDebugEnabled()) {
        logger.debug("La respuesta XML fue convertida a objeto: " + tipoCambio);
      }

      if (null != tipoCambio.getPayload() && null != tipoCambio.getPayload().getDailyConversionRate() && 0 < tipoCambio.getPayload().getDailyConversionRate().size()) {
        // if (null != tipoCambio.getPayload().getDailyConversionRate() && 0 < tipoCambio.getPayload().getDailyConversionRate().size()) {
        tipoCambioBean = new TipoCambioBean();

        if (logger.isInfoEnabled()) {
          logger.info("Transformando la informacion a un bean.");
        }

        tipoCambioBean.setTasaConversion(Float.toString(tipoCambio.getPayload().getDailyConversionRate().get(0).getConversionRate()));
        tipoCambioBean.setMonedaDesde(tipoCambio.getPayload().getDailyConversionRate().get(0).getCurrencyCodeFrom());
        tipoCambioBean.setMonedaHasta(tipoCambio.getPayload().getDailyConversionRate().get(0).getCurrencyCodeTo());
        tipoCambioBean.setFechaEfectivaDesde(tipoCambio.getPayload().getDailyConversionRate().get(0).getEffectiveDate());

      }
    } catch (Exception e) {
      logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
      logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
    }

    return tipoCambioBean;
  }

}
