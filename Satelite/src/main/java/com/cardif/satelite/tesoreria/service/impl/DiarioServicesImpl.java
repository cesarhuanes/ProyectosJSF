package com.cardif.satelite.tesoreria.service.impl;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.tesoreria.bean.JournalBean;
import com.cardif.satelite.tesoreria.handler.PayloadHandler;
import com.cardif.satelite.tesoreria.service.DiarioServices;
import com.cardif.sunsystems.mapeo.journal.SSC;
import com.cardif.sunsystems.services.SunComponenteService;
import com.cardif.sunsystems.util.ConstantesSun;

@Service("diarioService")
public class DiarioServicesImpl implements DiarioServices {
  public static final Logger logger = Logger.getLogger(ComprobanteRetencionServiceImpl.class);
  private SunComponenteService sunComponent = null;
  private PayloadHandler payloadHandler;

  @Override
  public List<JournalBean> buscarRetenciones(String unidadNegocio, String fechaDesde, String fechaHasta, String proveedorDesde, String proveedorHasta, String rucProveedor, String nroComprobante)
      throws SyncconException {
    List<JournalBean> listaJournalBean = null;

    if (logger.isInfoEnabled()) {
      logger.info("Inicio");
    }

    try {
      sunComponent = new SunComponenteService();
      payloadHandler = PayloadHandler.newInstance();

      String payload = payloadHandler.generarPayloadRetencion(unidadNegocio, fechaDesde, fechaHasta, proveedorDesde, proveedorHasta, rucProveedor, nroComprobante);

      if (logger.isDebugEnabled()) {
        logger.debug("Payload generado para consultar: " + payload);
      }

      String journalResultXML = sunComponent.ejecutaConsulta(ConstantesSun.SSC_ComponenteJournal, ConstantesSun.SSC_MetodoQuery, payload);
      if (logger.isInfoEnabled()) {
        logger.info("Resultado en formato XML: \n" + journalResultXML);
      }

      // Realizando el UNMARSHALLER de la respuesta
      JAXBContext jc = JAXBContext.newInstance(com.cardif.sunsystems.mapeo.journal.SSC.class);
      Unmarshaller unmarshaller = jc.createUnmarshaller();
      StringReader reader = new StringReader(journalResultXML);
      com.cardif.sunsystems.mapeo.journal.SSC comprobanteRetencion = (com.cardif.sunsystems.mapeo.journal.SSC) unmarshaller.unmarshal(reader);

      if (logger.isDebugEnabled()) {
        logger.debug("La respuesta XML fue convertida a objeto: " + comprobanteRetencion);
      }

      listaJournalBean = new ArrayList<JournalBean>();
      if (null != comprobanteRetencion.getPayload() && null != comprobanteRetencion.getPayload().getLedger() && null != comprobanteRetencion.getPayload().getLedger().getLine()
          && 0 < comprobanteRetencion.getPayload().getLedger().getLine().size()) {

        if (logger.isInfoEnabled()) {
          logger.info("Transformando la informacion a una lista de beans.");
        }

        for (SSC.Payload.Ledger.Line line : comprobanteRetencion.getPayload().getLedger().getLine()) {
          JournalBean journalBean = new JournalBean();

          // Seccion valida cuando se trata de un cuenta de retencion (2012,2022,40) o banco (10)
          journalBean.setFechaTransaccion(line.getTransactionDate());
          journalBean.setReferenciaVinculada2(line.getLinkReference2());
          journalBean.setMoneda(line.getCurrencyCode());
          journalBean.setImporteBase(Float.valueOf(line.getBaseAmount()));
          journalBean.setCodigoCuenta(line.getAccountCode());
          journalBean.setRuc(line.getAccounts().getLookupCode());

          // Seccion valida cuando se trata de una cuenta proveedora "P" o "42"
          if (line.getAccounts() != null) {
            journalBean.setRuc(line.getAccounts().getLookupCode());
          }
          journalBean.setReferenciaTransaccion(line.getTransactionReference());
          journalBean.setImporteTransaccion(line.getTransactionAmount());

          journalBean.setDebitoCredito(line.getDebitCredit());
          journalBean.setLineaDiario(line.getJournalLineNumber());
          journalBean.setDiario(line.getJournalNumber());
          journalBean.setGlosa(line.getDescription());
          journalBean.setTipoDiario(line.getJournalType());

          if (line.getDetailLad() != null) {
            journalBean.setDescripcionGeneral1(line.getDetailLad().getGeneralDescription1());
            journalBean.setDescripcionGeneral2(line.getDetailLad().getGeneralDescription2());
            journalBean.setDescripcionGeneral3(line.getDetailLad().getGeneralDescription3());
          }

          listaJournalBean.add(journalBean);

        }

      } else {

      }
    } catch (Exception e) {
      logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
      logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
    }

    return listaJournalBean;

  }

  @Override
  public List<JournalBean> buscarDetalleRetenciones(String unidadNegocio, String fechaEmision, String nroComprobante) throws SyncconException {
    List<JournalBean> listaJournalBean = null;

    if (logger.isInfoEnabled()) {
      logger.info("Inicio");
    }

    try {
      sunComponent = new SunComponenteService();
      payloadHandler = PayloadHandler.newInstance();

      String payload = payloadHandler.generarPayloadDetalleRetencion(unidadNegocio, fechaEmision, nroComprobante);

      if (logger.isDebugEnabled()) {
        logger.debug("Payload generado para consultar: " + payload);
      }

      String journalResultXML = sunComponent.ejecutaConsulta(ConstantesSun.SSC_ComponenteJournal, ConstantesSun.SSC_MetodoQuery, payload);
      if (logger.isInfoEnabled()) {
        logger.info("Resultado en formato XML: \n" + journalResultXML);
      }

      // Realizando el UNMARSHALLER de la respuesta
      JAXBContext jc = JAXBContext.newInstance(com.cardif.sunsystems.mapeo.journal.SSC.class);
      Unmarshaller unmarshaller = jc.createUnmarshaller();
      StringReader reader = new StringReader(journalResultXML);
      com.cardif.sunsystems.mapeo.journal.SSC comprobanteRetencion = (com.cardif.sunsystems.mapeo.journal.SSC) unmarshaller.unmarshal(reader);

      listaJournalBean = new ArrayList<JournalBean>();
      if (null != comprobanteRetencion.getPayload() && null != comprobanteRetencion.getPayload().getLedger() && null != comprobanteRetencion.getPayload().getLedger().getLine()
          && 0 < comprobanteRetencion.getPayload().getLedger().getLine().size()) {
        
        if (logger.isDebugEnabled()) {
          logger.debug("La respuesta XML fue convertida a objeto: " + comprobanteRetencion);
        }

        if (logger.isInfoEnabled()) {
          logger.info("Transformando la informacion a una lista de beans.");
        }

        for (SSC.Payload.Ledger.Line line : comprobanteRetencion.getPayload().getLedger().getLine()) {
          JournalBean journalBean = new JournalBean();

          // Seccion valida cuando se trata de un cuenta de retencion (2012,2022,40) o banco (10)
          journalBean.setFechaTransaccion(line.getTransactionDate());
          journalBean.setReferenciaVinculada2(line.getLinkReference2());
          journalBean.setMoneda(line.getCurrencyCode());
          journalBean.setImporteBase(Math.abs(Float.valueOf(line.getBaseAmount())));
          journalBean.setCodigoCuenta(line.getAccountCode());
          journalBean.setRuc(line.getAccounts().getLookupCode());
          journalBean.setComprobanteSunat(line.getAnalysisCode9());

          // Seccion valida cuando se trata de una cuenta proveedora "P" o "42"
          if (line.getAccounts() != null) {
            journalBean.setRuc(line.getAccounts().getLookupCode());
          }
          journalBean.setReferenciaTransaccion(line.getTransactionReference());
          journalBean.setImporteTransaccion(Math.abs(line.getTransactionAmount()));

          journalBean.setDebitoCredito(line.getDebitCredit());
          journalBean.setLineaDiario(line.getJournalLineNumber());
          journalBean.setDiario(line.getJournalNumber());
          journalBean.setGlosa(line.getDescription());
          journalBean.setTipoDiario(line.getJournalType());

          if (line.getDetailLad() != null) {
            journalBean.setDescripcionGeneral1(line.getDetailLad().getGeneralDescription1());
            journalBean.setDescripcionGeneral2(line.getDetailLad().getGeneralDescription2());
            journalBean.setDescripcionGeneral3(line.getDetailLad().getGeneralDescription3());
          }

          listaJournalBean.add(journalBean);

        }

      } else {
        if (logger.isDebugEnabled()) {
          logger.debug("No se encontro ninguna detalle de retencion con los datos indicados");
        }
        
      }
    } catch (Exception e) {
      logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
      logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
    }

    return listaJournalBean;
  }

  @Override
  public JournalBean buscarProvision(String unidadNegocio, String proveedor, String rucProveedor, String nroComprobante) throws SyncconException {
    JournalBean journalBean = null;

    if (logger.isInfoEnabled()) {
      logger.info("Inicio");
    }

    try {
      sunComponent = new SunComponenteService();
      payloadHandler = PayloadHandler.newInstance();

      String payload = payloadHandler.generarPayloadProvision(unidadNegocio, proveedor, rucProveedor, nroComprobante);

      if (logger.isDebugEnabled()) {
        logger.debug("Payload generado para consultar: " + payload);
      }

      String journalResultXML = sunComponent.ejecutaConsulta(ConstantesSun.SSC_ComponenteJournal, ConstantesSun.SSC_MetodoQuery, payload);
      if (logger.isInfoEnabled()) {
        logger.info("Resultado en formato XML: \n" + journalResultXML);
      }

      // Realizando el UNMARSHALLER de la respuesta
      JAXBContext jc = JAXBContext.newInstance(com.cardif.sunsystems.mapeo.journal.SSC.class);
      Unmarshaller unmarshaller = jc.createUnmarshaller();
      StringReader reader = new StringReader(journalResultXML);
      com.cardif.sunsystems.mapeo.journal.SSC comprobanteRetencion = (com.cardif.sunsystems.mapeo.journal.SSC) unmarshaller.unmarshal(reader);

      if (null != comprobanteRetencion.getPayload() && null != comprobanteRetencion.getPayload().getLedger() && null != comprobanteRetencion.getPayload().getLedger().getLine()
          && 0 < comprobanteRetencion.getPayload().getLedger().getLine().size()) {
        
        if (logger.isDebugEnabled()) {
          logger.debug("La respuesta XML fue convertida a objeto: " + comprobanteRetencion);
        }

        if (logger.isInfoEnabled()) {
          logger.info("Transformando la informacion a una lista de beans.");
        }

        for (SSC.Payload.Ledger.Line line : comprobanteRetencion.getPayload().getLedger().getLine()) {
          journalBean = new JournalBean();

          // Seccion valida cuando se trata de un cuenta de retencion (2012,2022,40) o banco (10)
          journalBean.setFechaTransaccion(line.getTransactionDate());
          journalBean.setReferenciaVinculada2(line.getLinkReference2());
          journalBean.setMoneda(line.getCurrencyCode());
          journalBean.setImporteBase(Float.valueOf(line.getBaseAmount()));
          journalBean.setCodigoCuenta(line.getAccountCode());
          journalBean.setRuc(line.getAccounts().getLookupCode());

          // Seccion valida cuando se trata de una cuenta proveedora "P" o "42"
          if (line.getAccounts() != null) {
            journalBean.setRuc(line.getAccounts().getLookupCode());
          }
          journalBean.setReferenciaTransaccion(line.getTransactionReference());
          journalBean.setImporteTransaccion(line.getTransactionAmount());

          journalBean.setDebitoCredito(line.getDebitCredit());
          journalBean.setLineaDiario(line.getJournalLineNumber());
          journalBean.setDiario(line.getJournalNumber());
          journalBean.setGlosa(line.getDescription());
          journalBean.setTipoDiario(line.getJournalType());

          if (line.getDetailLad() != null) {
            journalBean.setDescripcionGeneral1(line.getDetailLad().getGeneralDescription1());
            journalBean.setDescripcionGeneral2(line.getDetailLad().getGeneralDescription2());
            journalBean.setDescripcionGeneral3(line.getDetailLad().getGeneralDescription3());
          }
        }
      } else {
        journalBean = new JournalBean();

        if (logger.isDebugEnabled()) {
          logger.debug("No se encontro ninguna provision con los datos indicados");
        }

      }
    } catch (Exception e) {
      logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
      logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
    }

    return journalBean;
  }

  @Override
  public List<JournalBean> buscarRetencionesEmitidas(String unidadNegocio, String nroDiario, String fechaDesde, String fechaHasta, String proveedorDesde, String proveedorHasta, String rucProveedor,
      String nroComprobanteRetencion) throws SyncconException {
    List<JournalBean> listaJournalBean = null;

    if (logger.isInfoEnabled()) {
      logger.info("Inicio");
    }

    try {
      sunComponent = new SunComponenteService();
      payloadHandler = PayloadHandler.newInstance();

      String payload = payloadHandler.generaPayloadRetencionEmitida(unidadNegocio, nroDiario, fechaDesde, fechaHasta, proveedorDesde, proveedorHasta, rucProveedor, nroComprobanteRetencion);

      if (logger.isDebugEnabled()) {
        logger.debug("Payload generado para consultar: " + payload);
      }

      String journalResultXML = sunComponent.ejecutaConsulta(ConstantesSun.SSC_ComponenteJournal, ConstantesSun.SSC_MetodoQuery, payload);
      if (logger.isInfoEnabled()) {
        logger.info("Resultado en formato XML: \n" + journalResultXML);
      }

      // Realizando el UNMARSHALLER de la respuesta
      JAXBContext jc = JAXBContext.newInstance(com.cardif.sunsystems.mapeo.journal.SSC.class);
      Unmarshaller unmarshaller = jc.createUnmarshaller();
      StringReader reader = new StringReader(journalResultXML);
      com.cardif.sunsystems.mapeo.journal.SSC comprobanteRetencion = (com.cardif.sunsystems.mapeo.journal.SSC) unmarshaller.unmarshal(reader);

      if (logger.isDebugEnabled()) {
        logger.debug("La respuesta XML fue convertida a objeto: " + comprobanteRetencion);
      }

      listaJournalBean = new ArrayList<JournalBean>();
      if (null != comprobanteRetencion.getPayload() && null != comprobanteRetencion.getPayload().getLedger() && null != comprobanteRetencion.getPayload().getLedger().getLine()
          && 0 < comprobanteRetencion.getPayload().getLedger().getLine().size()) {

        if (logger.isInfoEnabled()) {
          logger.info("Transformando la informacion a una lista de beans.");
        }

        for (SSC.Payload.Ledger.Line line : comprobanteRetencion.getPayload().getLedger().getLine()) {
          JournalBean journalBean = new JournalBean();

          // Seccion valida cuando se trata de un cuenta de retencion (2012,2022,40) o banco (10)
          journalBean.setFechaTransaccion(line.getTransactionDate());
          journalBean.setReferenciaVinculada2(line.getLinkReference2());
          journalBean.setMoneda(line.getCurrencyCode());
          journalBean.setImporteBase(Float.valueOf(line.getBaseAmount()));
          journalBean.setCodigoCuenta(line.getAccountCode());
          journalBean.setRuc(line.getAccounts().getLookupCode());
          journalBean.setDiario(line.getJournalNumber());

          // Seccion valida cuando se trata de una cuenta proveedora "P" o "42"
          if (line.getAccounts() != null) {
            journalBean.setRuc(line.getAccounts().getLookupCode());
          }
          journalBean.setReferenciaTransaccion(line.getTransactionReference());
          journalBean.setImporteTransaccion(line.getTransactionAmount());

          journalBean.setDebitoCredito(line.getDebitCredit());
          journalBean.setLineaDiario(line.getJournalLineNumber());
          journalBean.setDiario(line.getJournalNumber());
          journalBean.setGlosa(line.getDescription());
          journalBean.setTipoDiario(line.getJournalType());

          if (line.getDetailLad() != null) {
            journalBean.setDescripcionGeneral1(line.getDetailLad().getGeneralDescription1());
            journalBean.setDescripcionGeneral2(line.getDetailLad().getGeneralDescription2());
            journalBean.setDescripcionGeneral3(line.getDetailLad().getGeneralDescription3());
          }

          listaJournalBean.add(journalBean);

        }

      } else {

      }
    } catch (Exception e) {
      logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
      logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
    }

    return listaJournalBean;

  }

  @Override
  public boolean actualizaAsientoPagoRetencion(String unidadNegocio, String nroDiario, String estadoAsiento, String nroCertificado) throws SyncconException {
    if (logger.isInfoEnabled()) {
      logger.info("Inicio");
    }
    boolean flag = false;

    try {
      sunComponent = new SunComponenteService();
      payloadHandler = PayloadHandler.newInstance();

      String payload = payloadHandler.generaPayloadLedgerTransactionLedgerAnalysisUpdate(unidadNegocio, nroDiario, estadoAsiento, nroCertificado);
      if (logger.isDebugEnabled()) {
        logger.debug("Payload generado para consultar [request]: " + payload);
      }

      String ledgerResultXML = sunComponent.ejecutaConsulta(ConstantesSun.SSC_ComponenteLedgerTransaction, ConstantesSun.SSC_MetodoLedgerAnalysisUpdate, payload);
      if (logger.isInfoEnabled()) {
        logger.info("Resultado de Sunsystems en formato XML: \n" + ledgerResultXML);
      }

      /*
       * Se realiza el UNMARSHALLER para la respuesta
       */
      JAXBContext jc = JAXBContext.newInstance(com.cardif.sunsystems.mapeo.ledgerTransaction.SSC.class);
      Unmarshaller unmarshaller = jc.createUnmarshaller();
      StringReader reader = new StringReader(ledgerResultXML);
      com.cardif.sunsystems.mapeo.ledgerTransaction.SSC respuestaActualizacion = (com.cardif.sunsystems.mapeo.ledgerTransaction.SSC) unmarshaller.unmarshal(reader);

      if (logger.isDebugEnabled()) {
        logger.debug("La respuesta XML fue convertida a objeto: " + respuestaActualizacion);
      }

      String respuestaEstado = respuestaActualizacion.getPayload().getLedgerUpdate().get(0).getStatus();
      if (logger.isInfoEnabled()) {
        logger.info("La estado de respuesta del XML es: " + respuestaEstado);
      }

      if (respuestaEstado.equalsIgnoreCase(ConstantesSun.SSC_MetodoLedgerTransaction_StatusSUCCESS)) {
        if (logger.isInfoEnabled()) {
          logger.info("La actualizacion en SUNSYSTEMS fue realizada correctamente.");
        }
        flag = true;
      } else if (respuestaEstado.equalsIgnoreCase(ConstantesSun.SSC_MetodoLedgerTransaction_StatusFAIL)) {
        if (logger.isInfoEnabled()) {
          logger.info("Hubo un problema al realizar la actualizacion en SUNSYSTEMS.");
        }
        flag = false;

        String userText = respuestaActualizacion.getPayload().getLedgerUpdate().get(0).getMessages().getMessage().get(0).getUserText();
        if (logger.isInfoEnabled()) {
          logger.info("Mensaje de respuesta de la actualizacion a SUNSYSTEMS: " + userText);
        }
      } else {
        logger.error("Este ESTADO no se encuentra MAPEADO - VERIFICAR.");
      }
    } catch (Exception e) {
      logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
      logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
      throw new SyncconException(ErrorConstants.COD_ERROR_SUNSYSTEMS_LEDGERTRANSACTION_UPDATE);
    }
    if (logger.isInfoEnabled()) {
      logger.info("Fin");
    }
    return flag;
  } // actualizaAsientoPagoRetencion

}
