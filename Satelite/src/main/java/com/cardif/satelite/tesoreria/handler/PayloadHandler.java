package com.cardif.satelite.tesoreria.handler;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.cardif.sunsystems.util.ConstantesSun;
import com.cardif.sunsystems.util.Utilidades;

public class PayloadHandler {
  private final Logger logger = Logger.getLogger(PayloadHandler.class);

  private Utilidades utilidades;

  private PayloadHandler() {
    utilidades = new Utilidades();
  }

  public static synchronized PayloadHandler newInstance() {
    return new PayloadHandler();
  }

  /**
   * @param unidadNegocio
   *          Empresa a la que se asocia el proveedor (CARDIF Seguros o Servicios).
   * @param fechaDesde
   *          Fecha incial para el rando de fechas de pago.
   * @param fechaHasta
   *          Fecha final para el rando de fechas de pago.
   * @param proveedorDesde
   *          Codigo inicial para el rango de proveedores.
   * @param proveedorHasta
   *          Codigo final para el rango de proveedores.
   * @param rucProveedor
   *          Nro de RUC del proveedor
   * @param nroComprobante
   *          Nro de comprobante (Nro Certificado de retencion).
   * @return String con el XML de conuslta a SSC.
   * @throws Exception
   */
  public String generarPayloadRetencion(String unidadNegocio, String fechaDesde, String fechaHasta, String proveedorDesde, String proveedorHasta, String rucProveedor, String nroComprobante)

  throws Exception {
    if (logger.isDebugEnabled()) {
      logger.debug("Inicio");
    }
    String response = null;

    try {
      Document doc = utilidades.loadXMLFromResource("/com/cardif/sunsystems/plantillas/journalQueryINRetencion.xml");

      // Llenando la data en el objeto Document
      NodeList nodeList = doc.getElementsByTagName("BusinessUnit");
      Node businessUnit = nodeList.item(0);
      businessUnit.setTextContent(unidadNegocio);

      NodeList items = doc.getElementsByTagName("Item");

      for (int ind = 0; ind < items.getLength(); ind++) {
        Node element = items.item(ind);

        if (element.getNodeType() == Node.ELEMENT_NODE) {
          NamedNodeMap atributos = element.getAttributes();

          if (atributos.getNamedItem("name").getNodeValue().equals("/Ledger/Line/AccountCode")) {
            atributos.getNamedItem("minvalue").setNodeValue(proveedorDesde);
            atributos.getNamedItem("maxvalue").setNodeValue(proveedorHasta);
          }

          if (atributos.getNamedItem("name").getNodeValue().equals("/Ledger/Line/TransactionDate")) {
            atributos.getNamedItem("minvalue").setNodeValue(fechaDesde);
            atributos.getNamedItem("maxvalue").setNodeValue(fechaHasta);
          }

//          // Si el RUC del proveedor viene vacio, se cambia el filtro
//          if (atributos.getNamedItem("name").getNodeValue().equals("/Ledger/Line/Accounts/LookupCode")) {
//            if (rucProveedor == null || rucProveedor == ConstantesSun.UTL_CHR_VACIO) {
//              atributos.getNamedItem("operator").setNodeValue(ConstantesSun.SSC_filter_operador_Not_Equal);
//              atributos.getNamedItem("value").setNodeValue(ConstantesSun.UTL_CHR_VACIO);
//            }
//            atributos.getNamedItem("value").setNodeValue(rucProveedor);
//          }

//          // Si el numero de certificado viene vacio, se cambia el filtro
//          if (atributos.getNamedItem("name").getNodeValue().equals("/Ledger/Line/LinkReference2")) {
//            if (nroComprobante == null || nroComprobante == ConstantesSun.UTL_CHR_VACIO) {
//              atributos.getNamedItem("operator").setNodeValue(ConstantesSun.SSC_filter_operador_Not_Equal);
//              atributos.getNamedItem("value").setNodeValue(ConstantesSun.UTL_CHR_VACIO);
//            }
//            atributos.getNamedItem("value").setNodeValue(nroComprobante);
//          }

          // En esta consulta no se usa la referencia de transaccion para la busqueda.
          if (atributos.getNamedItem("name").getNodeValue().equals("/Ledger/Line/TransactionReference")) {
            atributos.getNamedItem("operator").setNodeValue(ConstantesSun.SSC_filter_operador_Not_Equal);
            atributos.getNamedItem("value").setNodeValue(ConstantesSun.UTL_CHR_VACIO);
          }
          
//          FIXME: Descomentar cuandon termine el periodo de pruebas en TEST
          // filtro por estado
          if (atributos.getNamedItem("name").getNodeValue().equals("/Ledger/Line/DetailLad/GeneralDescription1")) {
              atributos.getNamedItem("value").setNodeValue(ConstantesSun.EST_ASIENTO_PAGADO);
            }
            
          
        }
      } // for

      response = utilidades.documenttoString(doc);
    } catch (Exception e) {
      logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
      logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
      throw new Exception("No se pudo generar el Payload de RETENCION.");
    }
    if (logger.isDebugEnabled()) {
      logger.debug("Fin");
    }
    return response;
  } // generarPayloadRetencion

  /**
   * @param unidadNegocio
   *          Empresa a la que se asocia el proveedor (CARDIF Seguros o Servicios).
   * @param fechaDesde
   *          Fecha incial para el rando de fechas de pago.
   * @param fechaHasta
   *          Fecha final para el rando de fechas de pago.
   * @param nroComprobante
   *          Nro de comprobante (Nro Certificado de retencion).
   * @return String con el XML de conuslta a SSC.
   * @throws Exception
   */
  public String generarPayloadDetalleRetencion(String unidadNegocio, String fechaEmision, String nroComprobante) throws Exception {
    if (logger.isDebugEnabled()) {
      logger.debug("Inicio");
    }
    String response = null;

    try {
      Document doc = utilidades.loadXMLFromResource("/com/cardif/sunsystems/plantillas/journalQueryINDetalleRetencion.xml");

      // Llenando la data en el objeto Document
      NodeList nodeList = doc.getElementsByTagName("BusinessUnit");
      Node businessUnit = nodeList.item(0);
      businessUnit.setTextContent(unidadNegocio);

      NodeList items = doc.getElementsByTagName("Item");

      for (int ind = 0; ind < items.getLength(); ind++) {
        Node element = items.item(ind);

        if (element.getNodeType() == Node.ELEMENT_NODE) {
          NamedNodeMap atributos = element.getAttributes();

          if (atributos.getNamedItem("name").getNodeValue().equals("/Ledger/Line/TransactionDate")) {
            atributos.getNamedItem("value").setNodeValue(fechaEmision);
          }

          // Si el numero de certificado viene vacio, se cambia el filtro (CERTIFICADO EN GENERAL DESCRIPTION 2)
          if (atributos.getNamedItem("name").getNodeValue().equals("/Ledger/Line/DetailLad/GeneralDescription2")) {
            if (nroComprobante == null || nroComprobante == ConstantesSun.UTL_CHR_VACIO) {
              atributos.getNamedItem("operator").setNodeValue(ConstantesSun.SSC_filter_operador_Not_Equal);
              atributos.getNamedItem("value").setNodeValue(ConstantesSun.UTL_CHR_VACIO);
            }
            atributos.getNamedItem("value").setNodeValue(nroComprobante);
          }

          // Si el numero de certificado viene vacio, se cambia el filtro (CERTIFICADO EN LINK REFERENCE 2)
          if (atributos.getNamedItem("name").getNodeValue().equals("/Ledger/Line/LinkReference2")) {
            if (nroComprobante == null || nroComprobante == ConstantesSun.UTL_CHR_VACIO) {
              atributos.getNamedItem("operator").setNodeValue(ConstantesSun.SSC_filter_operador_Not_Equal);
              atributos.getNamedItem("value").setNodeValue(ConstantesSun.UTL_CHR_VACIO);
            }
            atributos.getNamedItem("value").setNodeValue(nroComprobante);
          }
        }
      } // for

      response = utilidades.documenttoString(doc);
    } catch (Exception e) {
      logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
      logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
      throw new Exception("No se pudo generar el Payload de RETENCION.");
    }
    if (logger.isDebugEnabled()) {
      logger.debug("Fin");
    }
    return response;
  }

  public String generarPayloadProvision(String unidadNegocio, String proveedor, String rucProveedor, String nroComprobante)

  throws Exception {
    if (logger.isDebugEnabled()) {
      logger.debug("Inicio");
    }
    String response = null;

    try {
      Document doc = utilidades.loadXMLFromResource("/com/cardif/sunsystems/plantillas/journalQueryINProvision.xml");

      // Llenando la data en el objeto Document
      NodeList nodeList = doc.getElementsByTagName("BusinessUnit");
      Node businessUnit = nodeList.item(0);
      businessUnit.setTextContent(unidadNegocio);

      NodeList items = doc.getElementsByTagName("Item");

      for (int ind = 0; ind < items.getLength(); ind++) {
        Node element = items.item(ind);

        if (element.getNodeType() == Node.ELEMENT_NODE) {
          NamedNodeMap atributos = element.getAttributes();

          if (atributos.getNamedItem("name").getNodeValue().equals("/Ledger/Line/AccountCode")) {
            atributos.getNamedItem("minvalue").setNodeValue(proveedor);
            atributos.getNamedItem("maxvalue").setNodeValue(proveedor);
          }

          // Si el RUC del proveedor viene vacio, se cambia el filtro
          if (atributos.getNamedItem("name").getNodeValue().equals("/Ledger/Line/Accounts/LookupCode")) {
            if (rucProveedor == ConstantesSun.UTL_CHR_VACIO) {
              atributos.getNamedItem("operator").setNodeValue(ConstantesSun.SSC_filter_operador_Not_Equal);
            }
            atributos.getNamedItem("value").setNodeValue(rucProveedor);
          }

          // Si el numero de certificado viene vacio, se cambia el filtro
          if (atributos.getNamedItem("name").getNodeValue().equals("/Ledger/Line/TransactionReference")) {
            if (nroComprobante == ConstantesSun.UTL_CHR_VACIO) {
              atributos.getNamedItem("operator").setNodeValue(ConstantesSun.SSC_filter_operador_Not_Equal);
            }
            atributos.getNamedItem("value").setNodeValue(nroComprobante);
          }

        }
      } // for

      response = utilidades.documenttoString(doc);
    } catch (Exception e) {
      logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
      logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
      throw new Exception("No se pudo generar el Payload de RETENCION.");
    }
    if (logger.isDebugEnabled()) {
      logger.debug("Fin");
    }
    return response;
  } // generarPayloadProvision

  /**
   * @param unidadNegocio
   *          Empresa a la que se asocia el proveedor (CARDIF Seguros o Servicios).
   * @param codProveedor
   *          Codigo del proveedor "P" o "42".
   * @param rucProveedor
   *          Nro de RUC del proveedor.
   * @return String con el XML de conuslta a SSC.
   * @throws Exception
   *           Mensaje con el problema general.
   */
  public String generarPayloadProveedor(String unidadNegocio, String codProveedor, String rucProveedor) throws Exception {
    if (logger.isDebugEnabled()) {
      logger.debug("Inicio");
    }
    String response = null;

    try {
      Document doc = utilidades.loadXMLFromResource("/com/cardif/sunsystems/plantillas/supplierQueryIN.xml");

      // Llenando la data en el objeto Document
      NodeList nodeList = doc.getElementsByTagName("BusinessUnit");
      Node businessUnit = nodeList.item(0);
      businessUnit.setTextContent(unidadNegocio);

      NodeList items = doc.getElementsByTagName("Item");

      for (int ind = 0; ind < items.getLength(); ind++) {
        Node element = items.item(ind);

        if (element.getNodeType() == Node.ELEMENT_NODE) {
          NamedNodeMap atributos = element.getAttributes();

          if (atributos.getNamedItem("name").getNodeValue().equals("/Supplier/AccountCode")) {
            if (codProveedor == ConstantesSun.UTL_CHR_VACIO) {
              atributos.getNamedItem("operator").setNodeValue(ConstantesSun.SSC_filter_operador_Not_Equal);
            }
            atributos.getNamedItem("value").setNodeValue(codProveedor);
          }

          if (atributos.getNamedItem("name").getNodeValue().equals("/Supplier/LookupCode")) {
            if (rucProveedor == ConstantesSun.UTL_CHR_VACIO) {
              atributos.getNamedItem("operator").setNodeValue(ConstantesSun.SSC_filter_operador_Not_Equal);
            }
            atributos.getNamedItem("value").setNodeValue(rucProveedor);
          }
        }
      } // for

      response = utilidades.documenttoString(doc);
    } catch (Exception e) {
      logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
      logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
      throw new Exception("No se pudo generar el Payload de PROVEEDOR.");
    }
    if (logger.isDebugEnabled()) {
      logger.debug("Fin");
    }
    return response;
  } // generarPayloadProveedor

  /**
   * @param unidadNegocio
   *          Empresa a la que se asocia el proveedor (CARDIF Seguros o Servicios).
   * @param Fecha
   *          Fecha efectiva en la que se busca el tipo de cambio
   * @param monedaDesde
   *          Moneda origen de la conversion
   * @param monedaHasta
   *          Moneda destino de la conversion
   * @return String con el XML de conuslta a SSC.
   * @throws Exception
   *           Mensaje con el problema general.
   */
  public String generarPayloadTipoCambio(String unidadNegocio, String Fecha, String monedaDesde, String monedaHasta) throws Exception {
    if (logger.isDebugEnabled()) {
      logger.debug("Inicio");
    }
    String response = null;

    try {
      Document doc = utilidades.loadXMLFromResource("/com/cardif/sunsystems/plantillas/currencyDailyRatesQueryIN.xml");

      // Llenando la data en el objeto Document
      NodeList nodeList = doc.getElementsByTagName("BusinessUnit");
      Node businessUnit = nodeList.item(0);
      businessUnit.setTextContent(unidadNegocio);

      NodeList items = doc.getElementsByTagName("Item");

      for (int ind = 0; ind < items.getLength(); ind++) {
        Node element = items.item(ind);

        if (element.getNodeType() == Node.ELEMENT_NODE) {
          NamedNodeMap atributos = element.getAttributes();

          if (atributos.getNamedItem("name").getNodeValue().equals("/DailyConversionRate/CurrencyCodeFrom")) {
            if (monedaDesde == ConstantesSun.UTL_CHR_VACIO) {
              atributos.getNamedItem("operator").setNodeValue(ConstantesSun.SSC_filter_operador_Not_Equal);
            }
            atributos.getNamedItem("value").setNodeValue(monedaDesde);
          }

          if (atributos.getNamedItem("name").getNodeValue().equals("/DailyConversionRate/CurrencyCodeTo")) {
            if (monedaHasta == ConstantesSun.UTL_CHR_VACIO) {
              atributos.getNamedItem("operator").setNodeValue(ConstantesSun.SSC_filter_operador_Not_Equal);
            }
            atributos.getNamedItem("value").setNodeValue(monedaHasta);
          }

          if (atributos.getNamedItem("name").getNodeValue().equals("/DailyConversionRate/EffectiveDate")) {
            if (Fecha == ConstantesSun.UTL_CHR_VACIO) {
              atributos.getNamedItem("operator").setNodeValue(ConstantesSun.SSC_filter_operador_Not_Equal);
            }
            atributos.getNamedItem("value").setNodeValue(Fecha);
          }
        }
      } // for

      response = utilidades.documenttoString(doc);
    } catch (Exception e) {
      logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
      logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
      throw new Exception("No se pudo generar el Payload de TIPO DE CAMBIO.");
    }
    if (logger.isDebugEnabled()) {
      logger.debug("Fin");
    }
    return response;
  } // generarPayloadTipoCambio

  public String generaPayloadRetencionEmitida(String unidadNegocio, String nroDiario, String fechaDesde, String fechaHasta, String proveedorDesde, String proveedorHasta, String rucProveedor,
      String nroComprobanteRetencion) throws Exception {
    if (logger.isDebugEnabled()) {
      logger.debug("Inicio");
    }
    String response = null;

    try {
      Document doc = utilidades.loadXMLFromResource("/com/cardif/sunsystems/plantillas/journalQueryINRetencionEmitida.xml");

      // Llenando la data en el objeto Document
      NodeList nodeList = doc.getElementsByTagName("BusinessUnit");
      Node businessUnit = nodeList.item(0);
      businessUnit.setTextContent(unidadNegocio);

      NodeList items = doc.getElementsByTagName("Item");

      for (int ind = 0; ind < items.getLength(); ind++) {
        Node element = items.item(ind);

        if (element.getNodeType() == Node.ELEMENT_NODE) {
          NamedNodeMap atributos = element.getAttributes();

          if (atributos.getNamedItem("name").getNodeValue().equals("/Ledger/Line/AccountCode")) {
              atributos.getNamedItem("minvalue").setNodeValue(proveedorDesde);
              atributos.getNamedItem("maxvalue").setNodeValue(proveedorHasta);
          }

          // Si el numero de diario viene vacio, se cambia el filtro
          if (atributos.getNamedItem("name").getNodeValue().equals("/Ledger/Line/JournalNumber")) {
            if (nroDiario == null || nroDiario == ConstantesSun.UTL_CHR_VACIO) {
              atributos.getNamedItem("operator").setNodeValue(ConstantesSun.SSC_filter_operador_Not_Equal);
              atributos.getNamedItem("value").setNodeValue(ConstantesSun.UTL_NUM_VACIO);
            } else {
              atributos.getNamedItem("value").setNodeValue(nroDiario);
            }
          }

          // Si el numero de certificado viene vacio, se cambia el filtro
          if (atributos.getNamedItem("name").getNodeValue().equals("/Ledger/Line/DetailLad/GeneralDescription2")) {
            if (nroComprobanteRetencion == null || nroComprobanteRetencion == ConstantesSun.UTL_CHR_VACIO) {
              atributos.getNamedItem("operator").setNodeValue(ConstantesSun.SSC_filter_operador_Not_Equal);
              atributos.getNamedItem("value").setNodeValue(ConstantesSun.UTL_CHR_VACIO);
            } else {
              atributos.getNamedItem("value").setNodeValue(nroComprobanteRetencion);
            }
          }

          if (atributos.getNamedItem("name").getNodeValue().equals("/Ledger/Line/TransactionDate")) {
            atributos.getNamedItem("minvalue").setNodeValue(fechaDesde);
            atributos.getNamedItem("maxvalue").setNodeValue(fechaHasta);
          }
          
        }
      } // for

      response = utilidades.documenttoString(doc);
    } catch (Exception e) {
      logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
      logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
      throw new Exception("No se pudo generar el Payload de RETENCION.");
    }
    if (logger.isDebugEnabled()) {
      logger.debug("Fin");
    }
    return response;
  } // generaPayloadRetencionEmitida
  
  public String generaPayloadLedgerTransactionLedgerAnalysisUpdate(String unidadNegocio, String nroDiario, String estadoAsiento, String nroCertificado) throws Exception {
    if (logger.isDebugEnabled()) {
      logger.debug("Inicio");
    }
    String response = null;
    NodeList nodeList = null;

    try {
      Document doc = utilidades.loadXMLFromResource("/com/cardif/sunsystems/plantillas/ledgerTransactionLedgerAnalysisUpdateINRetencion.xml");

      // Llenando la data en el objeto Document
      nodeList = doc.getElementsByTagName("BusinessUnit");
      Node businessUnit = nodeList.item(0);
      businessUnit.setTextContent(unidadNegocio);
      
      nodeList = doc.getElementsByTagName("JournalNumber");
      Node journalNumber = nodeList.item(0);
      journalNumber.setTextContent(nroDiario);
      
      nodeList = doc.getElementsByTagName("GeneralDescription1");
      Node generalDescription1 = nodeList.item(0);
      generalDescription1.setTextContent(estadoAsiento);
      
      nodeList = doc.getElementsByTagName("GeneralDescription2");
      Node generalDescription2 = nodeList.item(0);
      generalDescription2.setTextContent(nroCertificado);
      
      response = utilidades.documenttoString(doc);
    } catch (Exception e) {
      logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
      logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
      throw new Exception("No se pudo generar el Payload de RETENCION.");
    }
    if (logger.isDebugEnabled()) {
      logger.debug("Fin");
    }
    return response;    
  }

}
