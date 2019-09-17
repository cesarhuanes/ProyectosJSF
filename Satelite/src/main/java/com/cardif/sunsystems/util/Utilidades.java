package com.cardif.sunsystems.util;

import static com.cardif.satelite.constantes.Constantes.TIP_PARAM_DETALLE;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.configuracion.service.ParametroService;
import com.cardif.satelite.constantes.Constantes;
import com.cardif.satelite.model.Parametro;
import com.cardif.satelite.tesoreria.bean.JournalBean;
import com.cardif.satelite.tesoreria.controller.ConsultarPagosController;
import com.cardif.sunsystems.bean.AsientoContableBean;

/**
 *
 * @author hurtadojo
 */
public class Utilidades {
  public static final Logger logger = Logger.getLogger(ConsultarPagosController.class);
  private ParametroService parametroService;

  private String journalSource;
  private DateFormat formatPer = new SimpleDateFormat("MMyyyy");

  public List<com.cardif.sunsystems.mapeo.currencyDailyRates.SSC.Payload.DailyConversionRate> leerXmlTipoCambio(String CadenaXml) {
    List<com.cardif.sunsystems.mapeo.currencyDailyRates.SSC.Payload.DailyConversionRate> superlinea = null;
    try {
      // 1. We need to create JAXContext instance
      JAXBContext jaxbContext = JAXBContext.newInstance(com.cardif.sunsystems.mapeo.currencyDailyRates.ObjectFactory.class);

      // 2. Use JAXBContext instance to create the Unmarshaller.
      Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

      // 3. Use the Unmarshaller to unmarshal the XML document to get an instance of JAXBElement.
      StringReader reader = new StringReader(CadenaXml);
      com.cardif.sunsystems.mapeo.currencyDailyRates.SSC expenseObj = (com.cardif.sunsystems.mapeo.currencyDailyRates.SSC) unmarshaller.unmarshal(reader);
      superlinea = expenseObj.getPayload().getDailyConversionRate();
      journalSource = expenseObj.getUser().getName();
    } catch (Exception e) {
      logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
      logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
    }
    return superlinea;
  }

  public List<List<com.cardif.sunsystems.mapeo.pagos.SSC.Payload.Ledger.Line>> LeerXmlPagos(String CadenaXml) {
    List<List<com.cardif.sunsystems.mapeo.pagos.SSC.Payload.Ledger.Line>> superlista = new ArrayList<List<com.cardif.sunsystems.mapeo.pagos.SSC.Payload.Ledger.Line>>();
    try {
      // 1. We need to create JAXContext instance
      JAXBContext jaxbContext = JAXBContext.newInstance(com.cardif.sunsystems.mapeo.pagos.ObjectFactory.class);

      // 2. Use JAXBContext instance to create the Unmarshaller.
      Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

      // 3. Use the Unmarshaller to unmarshal the XML document to get an instance of JAXBElement.
      StringReader reader = new StringReader(CadenaXml);
      com.cardif.sunsystems.mapeo.pagos.SSC expenseObj = (com.cardif.sunsystems.mapeo.pagos.SSC) unmarshaller.unmarshal(reader);
      com.cardif.sunsystems.mapeo.pagos.SSC.Payload.Ledger superlinea = expenseObj.getPayload().getLedger();
      // SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy");

      List<com.cardif.sunsystems.mapeo.pagos.SSC.Payload.Ledger.Line> lineas = superlinea.getLine();

      List<com.cardif.sunsystems.mapeo.pagos.SSC.Payload.Ledger.Line> lineas_agrupadas = null;
      String socioProducto = "";
      for (com.cardif.sunsystems.mapeo.pagos.SSC.Payload.Ledger.Line aa : lineas) {
        boolean salta = false;
        if (socioProducto.equals(aa.getAccounts().getLookupCode() + "")) {
          lineas_agrupadas.add(aa);
          socioProducto = aa.getAccounts().getLookupCode() + "";
          salta = false;
        } else {
          lineas_agrupadas = new ArrayList<com.cardif.sunsystems.mapeo.pagos.SSC.Payload.Ledger.Line>();
          lineas_agrupadas.add(aa);
          socioProducto = aa.getAccounts().getLookupCode() + "";
          salta = true;
        }
        if (salta) {
          superlista.add(lineas_agrupadas);
        }
      }
    } catch (Exception e) {
      superlista = null;
      logger.error(e.getMessage());
    }
    return superlista;
  }

  /* INICIO JARIASS 23/03/2015 */

  public com.cardif.sunsystems.mapeo.direccion.SSC.Payload.Address LeerXmlAddress(String CadenaXml) {
    com.cardif.sunsystems.mapeo.direccion.SSC.Payload.Address direccion = null;
    try {
      // 1. We need to create JAXContext instance
      JAXBContext jaxbContext = JAXBContext.newInstance(com.cardif.sunsystems.mapeo.direccion.ObjectFactory.class);

      // 2. Use JAXBContext instance to create the Unmarshaller.
      Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

      // 3. Use the Unmarshaller to unmarshal the XML document to get an instance of JAXBElement.
      StringReader reader = new StringReader(CadenaXml);

      com.cardif.sunsystems.mapeo.direccion.SSC expenseObj = (com.cardif.sunsystems.mapeo.direccion.SSC) unmarshaller.unmarshal(reader);

      direccion = expenseObj.getPayload().getAddress();

    } catch (Exception e) {
      logger.error("Error al cargar Address");
      logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
      logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
      return null;
    }
    return direccion;
  }

  public List<com.cardif.sunsystems.mapeo.proveedor.SSC.Payload.Supplier> LeerXmlProveedor(String CadenaXml) {
    List<com.cardif.sunsystems.mapeo.proveedor.SSC.Payload.Supplier> suppliers = null;
    try {
      // 1. We need to create JAXContext instance
      JAXBContext jaxbContext = JAXBContext.newInstance(com.cardif.sunsystems.mapeo.proveedor.ObjectFactory.class);

      // 2. Use JAXBContext instance to create the Unmarshaller.
      Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

      // 3. Use the Unmarshaller to unmarshal the XML document to get an instance of JAXBElement.
      StringReader reader = new StringReader(CadenaXml);

      com.cardif.sunsystems.mapeo.proveedor.SSC expenseObj = (com.cardif.sunsystems.mapeo.proveedor.SSC) unmarshaller.unmarshal(reader);

      suppliers = expenseObj.getPayload().getSupplier();

    } catch (Exception e) {
      logger.error("Error al cargar Proveedor");
      logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
      logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
      return null;
    }
    return suppliers;
  }

  public com.cardif.sunsystems.mapeo.detalleBanco.SSC.Payload.BankDetails LeerXmlBankDetails(String CadenaXml) {
    com.cardif.sunsystems.mapeo.detalleBanco.SSC.Payload.BankDetails bankDetail = null;
    try {
      // 1. We need to create JAXContext instance
      JAXBContext jaxbContext = JAXBContext.newInstance(com.cardif.sunsystems.mapeo.detalleBanco.ObjectFactory.class);

      // 2. Use JAXBContext instance to create the Unmarshaller.
      Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

      // 3. Use the Unmarshaller to unmarshal the XML document to get an instance of JAXBElement.
      StringReader reader = new StringReader(CadenaXml);

      com.cardif.sunsystems.mapeo.detalleBanco.SSC expenseObj = (com.cardif.sunsystems.mapeo.detalleBanco.SSC) unmarshaller.unmarshal(reader);

      bankDetail = expenseObj.getPayload().getBankDetails();

    } catch (Exception e) {
      logger.error("Error al cargar BankDetails");
      logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
      logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
      return bankDetail;
    }
    return bankDetail;
  }

  public float tasaCambio(String CurrencyCodeFrom, String CurrencyCodeTo, List<com.cardif.sunsystems.mapeo.currencyDailyRates.SSC.Payload.DailyConversionRate> lista) {
    float ConversionRate = 0;
    for (com.cardif.sunsystems.mapeo.currencyDailyRates.SSC.Payload.DailyConversionRate param : lista) {
      if (param.getCurrencyCodeFrom().equalsIgnoreCase(CurrencyCodeFrom) && param.getCurrencyCodeTo().equalsIgnoreCase(CurrencyCodeTo)) {
        ConversionRate = param.getConversionRate();
      }
    }
    return ConversionRate;
  }

  public static float redondear(int numDecimales, float Valor) {
    BigDecimal value = new BigDecimal(Valor);
    value = value.setScale(numDecimales, RoundingMode.HALF_UP);
    return value.floatValue();
  }

  public static BigDecimal redondear(int numDecimales, BigDecimal suma_trans) {
    BigDecimal value = new BigDecimal(suma_trans.floatValue());
    value = value.setScale(numDecimales, RoundingMode.HALF_UP);
    return value;
  }

  public com.cardif.sunsystems.mapeo.pagos.SSC.Payload.Ledger.Line procesarLineasPagosVarios(com.cardif.sunsystems.mapeo.pagos.SSC.Payload.Ledger.Line aa, float tasaEurosSoles, float tasaDolaresSoles,
      float tasaEurosDolares) {

    if (aa.getCurrencyCode().equals("PEN")) { // calculo de diferencia para dolares
      aa.setTransactionImport(aa.getTransactionAmount());
      aa.setTransactionPen(aa.getTransactionAmount());
      aa.setTransactionEuros(aa.getTransactionAmount() / tasaEurosSoles); // tasa euros
      aa.setDiferenceEuros(redondear(2, aa.getBase2ReportingAmount() - aa.getTransactionEuros()));
    }
    if (aa.getCurrencyCode().equals("EUR")) {
      aa.setTransactionImport(aa.getTransactionAmount());
      aa.setTransactionPen(aa.getTransactionAmount() * tasaEurosSoles); // tasa soles
      aa.setTransactionEuros(aa.getTransactionAmount());// euros
      aa.setDiferencePen(redondear(2, aa.getBaseAmount() - aa.getTransactionPen()));
    }
    if (aa.getCurrencyCode().equals("USD")) { // calculo de diferencia para dolares
      aa.setTransactionImport(aa.getTransactionAmount());
      aa.setTransactionPen(aa.getTransactionAmount() * tasaDolaresSoles); // tasa euros
      aa.setTransactionEuros(aa.getTransactionAmount() / tasaEurosDolares);
      aa.setDiferencePen(redondear(2, aa.getBaseAmount() - aa.getTransactionPen()));
      aa.setDiferenceEuros(redondear(2, aa.getBase2ReportingAmount() - aa.getTransactionEuros()));
    }
    return aa;
  }

  public com.cardif.sunsystems.mapeo.pagos.SSC.Payload.Ledger.Line procesarLineasRetenciones(JournalBean beanProcesos, String ultimoCorrelativo, float tasaEurosSoles, float tasaDolaresSoles,
      float tasaEurosDolares, String medioPagoAnalisisCode10) {

    com.cardif.sunsystems.mapeo.pagos.SSC.Payload.Ledger.Line fila = new com.cardif.sunsystems.mapeo.pagos.SSC.Payload.Ledger.Line();
    com.cardif.sunsystems.mapeo.pagos.SSC.Payload.Ledger.Line.Accounts cuentas = new com.cardif.sunsystems.mapeo.pagos.SSC.Payload.Ledger.Line.Accounts();
    fila.setAccountCode(beanProcesos.getCodigoCuenta());

    cuentas.setDescription(beanProcesos.getNombreProveedor());
    cuentas.setLookupCode(beanProcesos.getRuc() + "");
    cuentas.setLongDescription(beanProcesos.getNombreProveedorCompleto());
    fila.setAccounts(cuentas);

    fila.setTransactionDate(beanProcesos.getFechaTransaccion());
    fila.setTransactionReference(beanProcesos.getReferencia());
    fila.setCurrencyCode(beanProcesos.getMoneda());
    fila.setTransactionAmount(beanProcesos.getImpTransaccionAmount());

    if (beanProcesos.getImpPropinas() == null) {
      fila.setAmountTips("0.0");
    } else {
      fila.setAmountTips(beanProcesos.getImpPropinas());
    }

    if (beanProcesos.isbFlagPago()) {
      fila.setFlagPayment("P");
    } else {
      fila.setFlagPayment("");
    }

    fila.setBaseAmount(beanProcesos.getImpSoles());
    fila.setBase2ReportingAmount(beanProcesos.getImpEuros());
    fila.setDescription(beanProcesos.getGlosa());
    fila.setAccountingPeriod(beanProcesos.getPeriodo());
    fila.setAnalysisCode1(beanProcesos.getCentroCosto());
    fila.setAnalysisCode2(beanProcesos.getSocioProducto());
    fila.setAnalysisCode3(beanProcesos.getCanal());
    fila.setAnalysisCode4(beanProcesos.getProveedorEmpleado());
    fila.setAnalysisCode5(beanProcesos.getRucDniCliente());
    fila.setAnalysisCode6(beanProcesos.getPolizaCliente());
    fila.setAnalysisCode7(beanProcesos.getInversiones());
    fila.setAnalysisCode8(beanProcesos.getNroSiniestro());
    fila.setAnalysisCode9(beanProcesos.getComprobanteSunat());
    fila.setAnalysisCode10(medioPagoAnalisisCode10);
    fila.setJournalNumber(beanProcesos.getDiario());
    fila.setJournalLineNumber(beanProcesos.getLineaDiario());
    fila.setLastCorrelative(ultimoCorrelativo);
    if (fila.getCurrencyCode().equals("PEN")) {
      fila.setTransactionImport(fila.getTransactionAmount());
      fila.setTransactionPen(fila.getTransactionAmount());
      fila.setTransactionEuros(fila.getTransactionAmount() / tasaEurosSoles); // tasa euros
      fila.setDiferenceEuros(redondear(2, fila.getBase2ReportingAmount() - fila.getTransactionEuros()));
    }
    if (fila.getCurrencyCode().equals("EUR")) {
      fila.setTransactionImport(fila.getTransactionAmount());
      fila.setTransactionPen(fila.getTransactionAmount() * tasaEurosSoles); // tasa soles
      fila.setTransactionEuros(fila.getTransactionAmount());// euros
      fila.setDiferencePen(redondear(2, fila.getBaseAmount() - fila.getTransactionPen()));
    }
    if (fila.getCurrencyCode().equals("USD")) { // calculo de diferencia para dolares
      fila.setTransactionImport(fila.getTransactionAmount());
      fila.setTransactionPen(fila.getTransactionAmount() * tasaDolaresSoles); // tasa euros
      fila.setTransactionEuros(fila.getTransactionAmount() / tasaEurosDolares);
      fila.setDiferencePen(redondear(2, fila.getBaseAmount() - fila.getTransactionPen()));
      fila.setDiferenceEuros(redondear(2, fila.getBase2ReportingAmount() - fila.getTransactionEuros()));
    }
    return fila;
  }

  public static String extraerCadena(String cadena, int parametro, boolean izquierda) {
    int longitud = cadena.length();
    String caracter = "";
    if (izquierda) {
      caracter = cadena.substring(longitud - parametro, longitud);
    } else {
      caracter = cadena.substring(0, parametro);
    }
    return caracter;
  }

  public static String extraerCadena(String cadena, int parametro_ini, int parametro) {
    return cadena.substring(parametro_ini, parametro);
  }

  public static String mid(String TestString, int inicio, int caracteres) {
    return TestString.substring(inicio - 1, inicio + caracteres - 1);
  }

  public static String mid(String TestString, int i) {
    int longitud = TestString.length();
    return TestString.substring(i - 1, longitud);
  }

  public List<Parametro> getListaGenerica(String codParamDifCambioLoseEur, String tipParamDetalle) throws SyncconException {
    List<Parametro> listagen = null;
    listagen = parametroService.buscar(codParamDifCambioLoseEur, tipParamDetalle);
    return listagen;
  }

  public String enviarSun(List<AsientoContableBean> lista2, String metodoEjecutado, String codigoMoneda, ParametroService parametroService2, String tipoPago) throws Exception {
    this.parametroService = parametroService2;
    // 1. We need to create JAXContext instance
    JAXBContext jaxbContext = JAXBContext.newInstance(com.cardif.sunsystems.mapeo.ingresoSun.ObjectFactory.class);

    // 2. Use JAXBContext instance to create the Unmarshaller.
    Marshaller marshaller = jaxbContext.createMarshaller();
    com.cardif.sunsystems.mapeo.ingresoSun.SSC jaxbElement = new com.cardif.sunsystems.mapeo.ingresoSun.SSC();

    com.cardif.sunsystems.mapeo.ingresoSun.SSC.MethodContext metodocontexto = new com.cardif.sunsystems.mapeo.ingresoSun.SSC.MethodContext();
    com.cardif.sunsystems.mapeo.ingresoSun.SSC.MethodContext.LedgerPostingParameters posting = new com.cardif.sunsystems.mapeo.ingresoSun.SSC.MethodContext.LedgerPostingParameters();

    if (metodoEjecutado.equals(ConstantesSun.PAGOS_RETENCION)) {
      posting.setPostingType(ConstantesSun.SSC_postingtype_solovalidar);
    } else {
      posting.setPostingType(ConstantesSun.SSC_postingtype_contabilizar_sinerrores);
    }
    posting.setReportingAccount(ConstantesSun.CTA_ReportingAccount);

    metodocontexto.setLedgerPostingParameters(posting);
    jaxbElement.setMethodContext(metodocontexto);

    com.cardif.sunsystems.mapeo.ingresoSun.SSC.User value = new com.cardif.sunsystems.mapeo.ingresoSun.SSC.User();
    value.setName(ConstantesSun.AUTH_USR_SUN_LOGIN);

    jaxbElement.setUser(value);
    com.cardif.sunsystems.mapeo.ingresoSun.SSC.SunSystemsContext contexto = new com.cardif.sunsystems.mapeo.ingresoSun.SSC.SunSystemsContext();
    contexto.setBudgetCode(ConstantesSun.SSC_BudgetCode);
    contexto.setBusinessUnit(ConstantesSun.SSC_BusinessUnit_E01);
    jaxbElement.setSunSystemsContext(contexto);

    jaxbElement.setPayload(new com.cardif.sunsystems.mapeo.ingresoSun.SSC.Payload());
    jaxbElement.getPayload().setLedger(new com.cardif.sunsystems.mapeo.ingresoSun.SSC.Payload.Ledger());
    List<com.cardif.sunsystems.mapeo.ingresoSun.SSC.Payload.Ledger.Line> lista = new ArrayList<com.cardif.sunsystems.mapeo.ingresoSun.SSC.Payload.Ledger.Line>();
    com.cardif.sunsystems.mapeo.ingresoSun.SSC.Payload.Ledger.Line linea = null;
    com.cardif.sunsystems.mapeo.ingresoSun.SSC.Payload.Ledger.Line.DetailLad detailLad = null;
    logger.info("Generando XML para enviar a SUN");
    for (AsientoContableBean asientoContable : lista2) {
      linea = new com.cardif.sunsystems.mapeo.ingresoSun.SSC.Payload.Ledger.Line();
      detailLad = new com.cardif.sunsystems.mapeo.ingresoSun.SSC.Payload.Ledger.Line.DetailLad();
      linea.setAccountCode(asientoContable.getCuentaContable());
      linea.setAccountingPeriod(asientoContable.getPeriodo());
      // linea.setAllocationMarker(asientoContable.getMarcador());
      linea.setAnalysisCode1(asientoContable.getCentroCosto());
      linea.setAnalysisCode2(asientoContable.getSocioProducto());
      linea.setAnalysisCode3(asientoContable.getCanal());
      linea.setAnalysisCode4(asientoContable.getProveedorEmpleado());
      linea.setAnalysisCode5(asientoContable.getRucDniCliente());
      linea.setAnalysisCode6(asientoContable.getPolizaCliente());
      linea.setAnalysisCode7(asientoContable.getInversiones());
      linea.setAnalysisCode8(asientoContable.getNroSiniestro());
      linea.setAnalysisCode9(asientoContable.getTipoComprobanteSunat());
      linea.setAnalysisCode10(asientoContable.getTipoMedioPago());
      linea.setAssetCode(asientoContable.getCodActivoFijo());
      linea.setAssetIndicator(asientoContable.getIndicador());
      linea.setBase2ReportingAmount(asientoContable.getRefImpEuros());
      linea.setBaseAmount(asientoContable.getRefImpSoles());
      linea.setCurrencyCode(asientoContable.getMoneda());
      linea.setDebitCredit(asientoContable.getMarcadorDC());
      linea.setDescription(asientoContable.getGlosa());
      linea.setDueDate(fechatoString(asientoContable.getFechaVencimiento(), "ddMMyyyy"));
      linea.setLinkReference1("N");
      // Se cambia el campo donde se guarda el Nro de Certificado de Retencion, para colocarlo en un campo editable en la anulacion.
      // linea.setLinkReference2(asientoContable.getNroConstancia());
      linea.setSupplementaryExtension(ConstantesSun.SSC_SupplementaryExtension_YES);
      detailLad.setGeneralDescription1(ConstantesSun.EST_ASIENTO_PAGADO);
      detailLad.setGeneralDescription2(asientoContable.getNroConstancia());
      linea.setDetailLad(detailLad);
      linea.setLinkReference3(tipoPago);
      linea.setTransactionAmount(asientoContable.getRefImpTrans());
      linea.setTransactionDate(fechatoString(asientoContable.getFecha(), "ddMMyyyy"));
      linea.setTransactionReference(asientoContable.getReferencia());
      // Marcar como pagado??
      // linea.setAllocationMarker(Parametros.AllocationMarker_W);
      if (metodoEjecutado.equals(ConstantesSun.PAGOS_VARIOS)) {
        List<Parametro> listaJournalType = getListaGenerica(Constantes.MDP_PAG_VARIOS_T_DIARIO_PAGO, TIP_PARAM_DETALLE);
        if (codigoMoneda.equals("PEN")) {
          linea.setJournalType(listaJournalType.get(0).getNomValor());
        } else {
          linea.setJournalType(listaJournalType.get(1).getNomValor());
        }

      }

      if (metodoEjecutado.equals(ConstantesSun.PAGOS_TRANSFERENCIA)) {
        List<Parametro> listaJournalType = getListaGenerica(Constantes.MDP_PAG_TRASNF_T_DIARIO_PAGO, TIP_PARAM_DETALLE);
        if (codigoMoneda.equals("PEN")) {
          linea.setJournalType(listaJournalType.get(0).getNomValor());
        } else {
          linea.setJournalType(listaJournalType.get(1).getNomValor());
        }

      }
      if (metodoEjecutado.equals(ConstantesSun.PAGOS_COMISION) || metodoEjecutado.equals(ConstantesSun.PAGOS_ITF)) {

        List<Parametro> listaJournalType = getListaGenerica(Constantes.MDP_PAG_COMIS_T_DIARIO_PAGO, TIP_PARAM_DETALLE);
        if (codigoMoneda.equals("PEN")) {
          linea.setJournalType(listaJournalType.get(0).getNomValor());
        } else {
          linea.setJournalType(listaJournalType.get(1).getNomValor());
        }
        linea.setJournalSource(journalSource);
      } else if (metodoEjecutado.equals(ConstantesSun.PAGOS_SINIESTROS)) {
        List<Parametro> listaJournalType = getListaGenerica(Constantes.MDP_PAG_SINIESTROS_T_DIARIO_PAGO, TIP_PARAM_DETALLE);
        if (codigoMoneda.equals("PEN")) {
          linea.setJournalType(listaJournalType.get(0).getNomValor());
        } else {
          linea.setJournalType(listaJournalType.get(1).getNomValor());
        }

      } else if (metodoEjecutado.equals(ConstantesSun.PAGOS_RETENCION)) {
        List<Parametro> listaJournalType = getListaGenerica(Constantes.MDP_PAG_RETENCION_T_DIARIO_PAGO, TIP_PARAM_DETALLE);
        if (codigoMoneda.equals("PEN")) {
          linea.setJournalType(listaJournalType.get(0).getNomValor());
        } else {
          linea.setJournalType(listaJournalType.get(1).getNomValor());
        }
      }
      if (metodoEjecutado.equals(ConstantesSun.PAGOS_DETRACCION) || metodoEjecutado.equals(ConstantesSun.PAGOS_DETRACCION_MASIVA)) {
        List<Parametro> listaJournalType = getListaGenerica(Constantes.MDP_PAG_DETRACCIONES_T_DIARIO_PAGO, TIP_PARAM_DETALLE);
        if (codigoMoneda.equals("PEN")) {
          linea.setJournalType(listaJournalType.get(0).getNomValor());
        } else {
          linea.setJournalType(listaJournalType.get(1).getNomValor());
        }
      }

      lista.add(linea);
    }
    jaxbElement.getPayload().getLedger().getLine().addAll(lista);
    Writer writer = new StringWriter();
    marshaller.marshal(jaxbElement, writer);
    logger.info("XML Respuesta: " + writer.toString());
    return writer.toString();
  }

  public String fechatoString(Date fecha, String formato) {
    if (fecha == null) {
      fecha = Calendar.getInstance().getTime();
    }
    SimpleDateFormat fm = new SimpleDateFormat(formato);
    return fm.format(fecha);
  }

  public List<AsientoContableBean> generaAsientoDetraccionesMasiva(com.cardif.sunsystems.mapeo.pagos.SSC.Payload.Ledger.Line aa, Date fechaPago, Date fecha_actuali, String ctaprov, String ctacom,
      BigDecimal tceuro, String glosaPago) {
    List<AsientoContableBean> lista = null;
    AsientoContableBean beanLista = null;
    Date fecha_actual = (Date) fecha_actuali.clone();
    String periodo = "0" + formatPer.format(fecha_actuali);
    BigDecimal suma_soles = new BigDecimal("0.00");
    BigDecimal suma_euros = new BigDecimal("0.00");
    BigDecimal suma_trans = new BigDecimal("0.00");
    BigDecimal imptrans = new BigDecimal("0.00");
    BigDecimal impsoles = new BigDecimal("0.00");
    BigDecimal impeuros = new BigDecimal("0.00");
    lista = new ArrayList<AsientoContableBean>();
    if (aa.getFlagPayment().equalsIgnoreCase("P")) {
      /*****************************/
      beanLista = new AsientoContableBean();

      beanLista.setLayout("1;2;3");
      // Cuenta Proveedor
      beanLista.setCuentaContable(aa.getAccountCode() + "");
      // Libro
      beanLista.setNombreLibro("A");
      // Fecha Transaccion (Fecha Proceso)
      beanLista.setFecha(fecha_actual);
      // periodo
      beanLista.setPeriodo(periodo);
      // Glosa
      beanLista.setGlosa("PAGO DETRACCION " + aa.getTransactionReference());
      // Referencia / Nro Documento Cheque
      beanLista.setReferencia(aa.getTransactionReference());
      // Importe Transaccion
      imptrans = new BigDecimal(aa.getTransactionAmount());
      beanLista.setImporteTransaccion(imptrans);
      // beanLista.setImporteTransaccion(imptrans.abs());

      impsoles = new BigDecimal(aa.getBaseAmount());
      beanLista.setImporteSoles(impsoles);
      // beanLista.setImporteSoles(impsoles.abs());

      impeuros = new BigDecimal(aa.getBase2ReportingAmount());
      if (impeuros == BigDecimal.ZERO) {
        // impsoles = impsoles.abs();
        // Importe Euros
        beanLista.setImporteEuros(new BigDecimal(Utilidades.redondear(2, impsoles.divide(tceuro, 2, RoundingMode.HALF_UP).floatValue())));
      } else {
        // Importe Euros
        beanLista.setImporteEuros(impeuros);
      }
      // Marcador debito/credito
      if (beanLista.getImporteTransaccion().doubleValue() >= 0) {
        beanLista.setMarcadorDC("D");
        beanLista.setImporteTransaccion(redondear(2, beanLista.getImporteTransaccion()));
        beanLista.setImporteSoles(redondear(2, beanLista.getImporteSoles()));
        beanLista.setImporteEuros(redondear(2, beanLista.getImporteEuros()));
      } else {
        beanLista.setMarcadorDC("C");
        beanLista.setImporteTransaccion(redondear(2, beanLista.getImporteTransaccion()));
        beanLista.setImporteSoles(redondear(2, beanLista.getImporteSoles()));
        beanLista.setImporteEuros(redondear(2, beanLista.getImporteEuros()));
      }
      // Moneda
      beanLista.setMoneda(aa.getCurrencyCode());
      // Centro de Costos
      beanLista.setCentroCosto(aa.getAnalysisCode1());
      // Socio producto
      beanLista.setSocioProducto(aa.getAnalysisCode2());
      // Canal
      beanLista.setCanal(aa.getAnalysisCode3());
      // Proveedor/Empleado
      // beanLista.setProveedorEmpleado(aa.getAccountCode() + "");
      // RUC/DNI cliente
      beanLista.setRucDniCliente(aa.getAccounts().getLookupCode() + "");
      beanLista.setNomProveedor(aa.getAccounts().getLongDescription());
      // Poliza del cliente
      beanLista.setPolizaCliente(aa.getAnalysisCode6());
      // Inversiones
      beanLista.setInversiones(aa.getAnalysisCode7());
      // Nro Siniestro
      beanLista.setNroSiniestro(aa.getAnalysisCode8());
      // Tipo comp. SUNAT
      beanLista.setTipoComprobanteSunat(aa.getAnalysisCode9());
      // Impuesto IGV
      beanLista.setTipoMedioPago(aa.getAnalysisCode10());
      // Fecha vencimiento
      beanLista.setFechaVencimiento(fechaPago);
      // Numero de Constancia
      beanLista.setNroConstancia(aa.getNroConstancia());
      // Cuenta Proveedor
      beanLista.setCtaProveedor(aa.getAccounts().getLookupCode() + "");
      beanLista.setNomProveedor(aa.getAccounts().getLongDescription());

      // Sumatoria
      if (beanLista.getImporteTransaccion().doubleValue() >= 0) {
        suma_trans = Utilidades.redondear(2, suma_trans).add(beanLista.getImporteTransaccion());
        suma_soles = Utilidades.redondear(2, suma_soles).add(beanLista.getImporteSoles());
        suma_euros = Utilidades.redondear(2, suma_euros).add(beanLista.getImporteEuros());
      } else {
        suma_trans = Utilidades.redondear(2, suma_trans).add(beanLista.getImporteTransaccion());
        suma_soles = Utilidades.redondear(2, suma_soles).add(beanLista.getImporteSoles());
        suma_euros = Utilidades.redondear(2, suma_euros).add(beanLista.getImporteEuros());
      }
      lista.add(beanLista);

      // Agregar un ficticio de resumen de sumas
      AsientoContableBean ficticio = new AsientoContableBean();
      ficticio.setGlosa("LINEA FICTICIA");
      ficticio.setImporteSoles(suma_soles);
      ficticio.setImporteEuros(suma_euros);
      ficticio.setImporteTransaccion(suma_trans);
      ficticio.setCodActivoFijo(ctacom);
      ficticio.setCtaProveedor(ctaprov);
      ficticio.setNroConstancia(aa.getNroConstancia());
      ficticio.setProveedorEmpleado(lista.get(lista.size() - 1).getProveedorEmpleado());

      lista.add(ficticio);
    }
    return lista;
  }

  public List<AsientoContableBean> generaAsientoPagosVarios(com.cardif.sunsystems.mapeo.pagos.SSC.Payload.Ledger.Line aa, Date fechaPago, String ctaPropinas, String ctaGananciasPen,
      String ctaPerdidasPen, String ctaGananciasEur, String ctaPerdidasEur, String ctaBanco, Date fecha_actuali, float tasaEurosSoles, String glosaPago, String tipoPago) {
    List<AsientoContableBean> lista = null;
    AsientoContableBean beanLista = null;
    Date fecha_actual = (Date) fecha_actuali.clone();
    BigDecimal suma_soles = new BigDecimal("0.00");
    BigDecimal suma_euros = new BigDecimal("0.00");
    BigDecimal suma_trans = new BigDecimal("0.00");
    lista = new ArrayList<AsientoContableBean>();
    String periodo = "0" + formatPer.format(fecha_actuali);
    if (aa.getFlagPayment().equalsIgnoreCase("P")) {
      // Solo Procesar Pagadas
      beanLista = new AsientoContableBean();
      beanLista.setLayout("1;2;3");
      // Cuenta Proveedor
      beanLista.setCuentaContable(aa.getAccountCode() + "");
      // Libro
      beanLista.setNombreLibro("A");
      // Fecha Transaccion (Fecha Proceso)
      beanLista.setFecha(fecha_actual);
      beanLista.setPeriodo(periodo);
      // Glosa

      // beanLista.setGlosa(glosaPago);
      // log.info(glosaPago);
      if (tipoPago.equals(ConstantesSun.PAGOS_SINIESTROS)) {
        beanLista.setGlosa(aa.getDescription());
      } else {
        beanLista.setGlosa(glosaPago);
      }

      // Referencia / Nro Documento Cheque
      beanLista.setReferencia(aa.getTransactionReference());
      if (aa.getJournalLineNumber().equals("07")) {
        // Importe Transaccion
        beanLista.setImporteTransaccion(new BigDecimal(aa.getTransactionImport()).negate());
        // Importe Soles
        beanLista.setImporteSoles(new BigDecimal(aa.getTransactionPen()).negate());
        // Importe Euros
        beanLista.setImporteEuros(new BigDecimal(aa.getTransactionEuros()).negate());
      } else {
        // Importe Transaccion
        beanLista.setImporteTransaccion(new BigDecimal(aa.getTransactionImport()));
        // Importe Soles
        beanLista.setImporteSoles(new BigDecimal(aa.getTransactionPen()));
        // Importe Euros
        beanLista.setImporteEuros(new BigDecimal(aa.getTransactionEuros()));
      }
      beanLista.setImporteTransaccion(redondear(2, beanLista.getImporteTransaccion()));
      beanLista.setImporteSoles(redondear(2, beanLista.getImporteSoles()));
      beanLista.setImporteEuros(redondear(2, beanLista.getImporteEuros()));
      /*
       * if (aa.getJournalLineNumber().equals("07")) { // Marcador debito/credito beanLista.setMarcadorDC("C"); } else { // Marcador debito/credito
       * beanLista.setMarcadorDC("D"); }
       */

      // Marcador debito/credito
      if (beanLista.getImporteTransaccion().doubleValue() >= 0)
        beanLista.setMarcadorDC("D");
      else
        beanLista.setMarcadorDC("C");

      // Moneda
      beanLista.setMoneda(aa.getCurrencyCode());
      // Centro de Costos
      beanLista.setCentroCosto(aa.getAnalysisCode1());
      // Socio producto
      beanLista.setSocioProducto(aa.getAnalysisCode2());
      // Canal
      beanLista.setCanal(aa.getAnalysisCode3());
      // Proveedor/Empleado
      beanLista.setProveedorEmpleado(aa.getAccountCode());
      // RUC/DNI cliente
      beanLista.setRucDniCliente(aa.getAccounts().getLookupCode() + "");
      beanLista.setNomProveedor(aa.getAccounts().getLongDescription());
      // Poliza del cliente
      beanLista.setPolizaCliente(aa.getAnalysisCode6());
      // Inversiones
      beanLista.setInversiones(aa.getAnalysisCode7());
      // Nro Siniestro
      beanLista.setNroSiniestro(aa.getAnalysisCode8());
      // Tipo comp. SUNAT
      beanLista.setTipoComprobanteSunat(aa.getAnalysisCode9());
      // Impuesto IGV
      beanLista.setTipoMedioPago(aa.getAnalysisCode10());
      // Fecha vencimiento
      beanLista.setFechaVencimiento(fechaPago);
      // Numero de Constancia
      beanLista.setNroConstancia(null);
      // Cuenta Proveedor
      beanLista.setCtaProveedor(aa.getAccounts().getLookupCode() + "");
      beanLista.setNomProveedor(aa.getAccounts().getLongDescription());

      suma_soles = redondear(2, beanLista.getImporteSoles()).add(suma_soles);
      suma_euros = redondear(2, beanLista.getImporteEuros()).add(suma_euros);
      suma_trans = redondear(2, beanLista.getImporteTransaccion()).add(suma_trans);

      lista.add(beanLista);

      // /Diferencia Soles, se trata de una provision de dolares////
      if (Utilidades.redondear(2, aa.getDiferencePen()) != 0.00) {
        // if (aa.getDiferencePen() != 0) {
        
        
        /***************************** Generamos linea para la diferencia de cambio SOLES *****************************/
        beanLista = new AsientoContableBean();

        beanLista.setLayout("1;2;3");
        if (aa.getDiferencePen() > 0) {
          // Cuenta Proveedor
          beanLista.setCuentaContable(ctaGananciasPen);
        } else if (aa.getDiferencePen() < 0) {
          // Cuenta Proveedor
          beanLista.setCuentaContable(ctaPerdidasPen);
        }

        // Libro
        beanLista.setNombreLibro("A");
        // Fecha Transaccion (Fecha Proceso)
        beanLista.setFecha(fecha_actual);
        // periodo
        beanLista.setPeriodo(periodo);
        // Glosa
        beanLista.setGlosa("Diferencia de Cambio");
        // Referencia / Nro Documento Cheque
        beanLista.setReferencia(aa.getTransactionReference());
        // Importe Transaccion
        beanLista.setImporteTransaccion(new BigDecimal("0.0"));
        if (aa.getDiferencePen() > 0) {
          // Importe Soles
          beanLista.setImporteSoles(new BigDecimal(aa.getDiferencePen()).abs().negate());
        } else if (aa.getDiferencePen() < 0) {
          // Importe Soles
          beanLista.setImporteSoles(new BigDecimal(aa.getDiferencePen()).abs());
        }
        // Importe Euros
        beanLista.setImporteEuros(new BigDecimal("0.0"));
        // Marcador debito/credito
        if (beanLista.getImporteSoles().doubleValue() >= 0)
          beanLista.setMarcadorDC("D");
        else
          beanLista.setMarcadorDC("C");

        beanLista.setImporteTransaccion(redondear(2, beanLista.getImporteTransaccion()));
        beanLista.setImporteSoles(redondear(2, beanLista.getImporteSoles()));
        beanLista.setImporteEuros(redondear(2, beanLista.getImporteEuros()));
        // Moneda, como se trata de colocar la diferencia de cambio en una cuenta soles, la modena de la operacion debe ser soles
        beanLista.setMoneda(ConstantesSun.UTL_MONEDA_SOLES);
        // Centro de Costos
        beanLista.setCentroCosto(aa.getAnalysisCode1());
        // Socio producto
        beanLista.setSocioProducto(aa.getAnalysisCode2());
        // Canal
        beanLista.setCanal(aa.getAnalysisCode3());
        // Proveedor/Empleado
        beanLista.setProveedorEmpleado(aa.getAnalysisCode4());
        // RUC/DNI cliente
        beanLista.setRucDniCliente(aa.getAccounts().getLookupCode() + "");
        // RUC/DNI cliente
        beanLista.setNomProveedor(aa.getAccounts().getLongDescription());
        // Poliza del cliente
        beanLista.setPolizaCliente(aa.getAnalysisCode6());
        // Inversiones
        beanLista.setInversiones(aa.getAnalysisCode7());
        // Nro Siniestro
        beanLista.setNroSiniestro(aa.getAnalysisCode8());
        // Tipo comp. SUNAT
        beanLista.setTipoComprobanteSunat(aa.getAnalysisCode9());
        // Impuesto IGV
        beanLista.setTipoMedioPago(aa.getAnalysisCode10());
        // Fecha vencimiento
        beanLista.setPeriodo(periodo);
        beanLista.setFechaVencimiento(fechaPago);
        // Numero de Constancia
        beanLista.setNroConstancia(null);

        // Cuenta Proveedor
        beanLista.setCtaProveedor(aa.getAccounts().getLookupCode() + "");

        // Nom Proveedor
        beanLista.setNomProveedor(aa.getAccounts().getLongDescription());

        lista.add(beanLista);

        /***************************** Generamos linea para la provision *****************************/
        beanLista = new AsientoContableBean();

        beanLista.setLayout("1;2;3");
        // Cuenta Proveedor
        beanLista.setCuentaContable(aa.getAccountCode() + "");
        // Libro
        beanLista.setNombreLibro("A");
        // Fecha Transaccion (Fecha Proceso)
        beanLista.setFecha(fecha_actual);
        // periodo
        beanLista.setPeriodo(periodo);
        // Glosa
        beanLista.setGlosa("Diferencia de Cambio");
        // Referencia / Nro Documento Cheque
        beanLista.setReferencia(aa.getTransactionReference());
        // Importe Transaccion
        beanLista.setImporteTransaccion(new BigDecimal("0.0"));

        if (aa.getDiferencePen() > 0) {
          // Importe Soles
          beanLista.setImporteSoles(new BigDecimal(aa.getDiferencePen()).abs());
        } else if (aa.getDiferencePen() < 0) {
          // Importe Soles
          beanLista.setImporteSoles(new BigDecimal(aa.getDiferencePen()).abs().negate());
        }
        // Importe Euros
        beanLista.setImporteEuros(new BigDecimal("0.0"));

        // Marcador debito/credito
        if (beanLista.getImporteSoles().doubleValue() >= 0)
          beanLista.setMarcadorDC("D");
        else
          beanLista.setMarcadorDC("C");

        beanLista.setImporteTransaccion(redondear(2, beanLista.getImporteTransaccion()));
        beanLista.setImporteSoles(redondear(2, beanLista.getImporteSoles()));
        beanLista.setImporteEuros(redondear(2, beanLista.getImporteEuros()));
        // Moneda
        beanLista.setMoneda(aa.getCurrencyCode());
        // Centro de Costos
        beanLista.setCentroCosto(aa.getAnalysisCode1());
        // Socio producto
        beanLista.setSocioProducto(aa.getAnalysisCode2());
        // Canal
        beanLista.setCanal(aa.getAnalysisCode3());
        // Proveedor/Empleado
        beanLista.setProveedorEmpleado(aa.getAccountCode() + "");
        // RUC/DNI cliente
        beanLista.setRucDniCliente(aa.getAccounts().getLookupCode() + "");

        // Poliza del cliente
        beanLista.setPolizaCliente(aa.getAnalysisCode6());
        // Inversiones
        beanLista.setInversiones(aa.getAnalysisCode7());
        // Nro Siniestro
        beanLista.setNroSiniestro(aa.getAnalysisCode8());
        // Tipo comp. SUNAT
        beanLista.setTipoComprobanteSunat(aa.getAnalysisCode9());
        // Impuesto IGV
        beanLista.setTipoMedioPago(aa.getAnalysisCode10());
        // Fecha vencimiento
        beanLista.setFechaVencimiento(fechaPago);
        // Numero de Constancia
        beanLista.setNroConstancia(null);

        // Cuenta Proveedor
        beanLista.setCtaProveedor(aa.getAccounts().getLookupCode() + "");
        beanLista.setNomProveedor(aa.getAccounts().getLongDescription());
        lista.add(beanLista);
      }

    }

    // Agregar un ficticio de resumen de sumas
    AsientoContableBean ficticio = new AsientoContableBean();
    ficticio.setGlosa("LINEA FICTICIA");
    ficticio.setImporteSoles(suma_soles);
    ficticio.setImporteEuros(suma_euros);
    ficticio.setImporteTransaccion(suma_trans);
    ficticio.setProveedorEmpleado(lista.get(0).getProveedorEmpleado());
    ficticio.setTipoMedioPago(lista.get(0).getTipoMedioPago());
    ficticio.setCtaProveedor(aa.getAccounts().getLookupCode() + "");
    ficticio.setNomProveedor(aa.getAccounts().getLongDescription());
    lista.add(ficticio);
    return lista;
  }

  public List<AsientoContableBean> generaAsientoDetracciones(com.cardif.sunsystems.mapeo.pagos.SSC.Payload.Ledger.Line aa, Date fechaPago, String ctaBanco, Date fecha_actuali, String ctaComision,
      String glosaPago) {
    List<AsientoContableBean> lista = null;
    AsientoContableBean beanLista = null;
    Date fecha_actual = (Date) fecha_actuali.clone();
    // BigDecimal suma_soles = new BigDecimal("0.00");
    // BigDecimal suma_euros = new BigDecimal("0.00");
    // BigDecimal suma_trans = new BigDecimal("0.00");
    String periodo = "0" + formatPer.format(fecha_actuali);
    lista = new ArrayList<AsientoContableBean>();
    if (aa.getFlagPayment().equalsIgnoreCase("P")) {

      // Solo Procesar Pagadas
      beanLista = new AsientoContableBean();
      beanLista.setLayout("1;2;3");
      // Cuenta Proveedor
      beanLista.setCuentaContable(aa.getAccountCode() + "");
      // Libro
      beanLista.setNombreLibro("A");
      // Fecha Transaccion (Fecha Proceso)
      beanLista.setFecha(fecha_actual);
      // periodo
      beanLista.setPeriodo(periodo);
      // Glosa
      beanLista.setGlosa("PAGO DETRACCION " + aa.getTransactionReference());
      // Referencia / Nro Documento Cheque
      beanLista.setReferencia(aa.getTransactionReference());
      // Importe Transaccion
      beanLista.setImporteTransaccion(new BigDecimal(aa.getTransactionAmount()));
      // Importe Soles
      beanLista.setImporteSoles(new BigDecimal(aa.getBaseAmount()));
      // Importe Euros
      beanLista.setImporteEuros(new BigDecimal(aa.getBase2ReportingAmount()));

      // Marcador debito/credito
      if (beanLista.getImporteTransaccion().doubleValue() >= 0)
        beanLista.setMarcadorDC("D");
      else
        beanLista.setMarcadorDC("C");

      beanLista.setImporteTransaccion(redondear(2, beanLista.getImporteTransaccion()));
      beanLista.setImporteSoles(redondear(2, beanLista.getImporteSoles()));
      beanLista.setImporteEuros(redondear(2, beanLista.getImporteEuros()));

      // Moneda
      beanLista.setMoneda(aa.getCurrencyCode());

      beanLista.setMoneda(aa.getCurrencyCode());
      // Centro de Costos
      beanLista.setCentroCosto(aa.getAnalysisCode1());
      // Socio producto
      beanLista.setSocioProducto(aa.getAnalysisCode2());
      // Canal
      beanLista.setCanal(aa.getAnalysisCode3());
      // Proveedor/Empleado
      beanLista.setProveedorEmpleado(aa.getAccountCode() + "");
      // RUC/DNI cliente
      beanLista.setRucDniCliente(aa.getAccounts().getLookupCode() + "");
      // Poliza del cliente
      beanLista.setPolizaCliente(aa.getAnalysisCode6());
      // Inversiones
      beanLista.setInversiones(aa.getAnalysisCode7());
      // Nro Siniestro
      beanLista.setNroSiniestro(aa.getAnalysisCode8());
      // Tipo comp. SUNAT
      beanLista.setTipoComprobanteSunat(aa.getAnalysisCode9());
      // Impuesto IGV
      beanLista.setTipoMedioPago(aa.getAnalysisCode10());
      // Fecha vencimiento
      beanLista.setFechaVencimiento(fechaPago);
      // Numero de Constancia
      beanLista.setNroConstancia(aa.getNroConstancia());
      // Cuenta Proveedor
      beanLista.setCtaProveedor(aa.getAccounts().getLookupCode() + "");
      beanLista.setNomProveedor(aa.getAccounts().getLongDescription());
      lista.add(beanLista);

      // ************************/
      beanLista = new AsientoContableBean();

      beanLista.setLayout("1;2;3");
      // Cuenta Proveedor
      beanLista.setCuentaContable(ctaBanco);
      // Libro
      beanLista.setNombreLibro("A");
      // Fecha Transaccion (Fecha Proceso)
      beanLista.setFecha(fecha_actual);
      // periodo
      // beanLista.setPeriodo(fechaPago.get(Calendar.YEAR) + "/" +
      // String.format("%03d", fechaPago.get(Calendar.MONTH) + 1));
      beanLista.setPeriodo(periodo);
      // Glosa
      beanLista.setGlosa("PAGO DETRACCION " + aa.getTransactionReference());
      // Referencia / Nro Documento Cheque
      beanLista.setReferencia(aa.getNroConstancia());

      // Importe Transaccion
      beanLista.setImporteTransaccion(new BigDecimal(aa.getBaseAmount()).negate());
      // Importe Soles
      beanLista.setImporteSoles(new BigDecimal(aa.getBaseAmount()).negate());
      // Importe Euros
      beanLista.setImporteEuros(new BigDecimal(aa.getBase2ReportingAmount()).negate());

      // Marcador debito/credito
      if (beanLista.getImporteTransaccion().doubleValue() >= 0)
        beanLista.setMarcadorDC("D");
      else
        beanLista.setMarcadorDC("C");

      beanLista.setImporteTransaccion(redondear(2, beanLista.getImporteTransaccion()));
      beanLista.setImporteSoles(redondear(2, beanLista.getImporteSoles()));
      beanLista.setImporteEuros(redondear(2, beanLista.getImporteEuros()));
      // Moneda
      if (ctaBanco.startsWith("101")) {
        // Moneda
        beanLista.setMoneda("PEN");
      } else {
        // Moneda
        beanLista.setMoneda("USD");
      }
      // Centro de Costos
      beanLista.setCentroCosto(aa.getAnalysisCode1());
      // Socio producto
      beanLista.setSocioProducto(aa.getAnalysisCode2());
      // Canal
      beanLista.setCanal(aa.getAnalysisCode3());
      // Proveedor/Empleado
      beanLista.setProveedorEmpleado(aa.getAnalysisCode4());
      // RUC/DNI cliente
      beanLista.setRucDniCliente(aa.getAccounts().getLookupCode() + "");
      // Poliza del cliente
      beanLista.setPolizaCliente(aa.getAnalysisCode6());
      // Inversiones
      beanLista.setInversiones(aa.getAnalysisCode7());
      // Nro Siniestro
      beanLista.setNroSiniestro(aa.getAnalysisCode8());
      // Tipo comp. SUNAT
      beanLista.setTipoComprobanteSunat(aa.getAnalysisCode9());
      // Impuesto IGV
      beanLista.setTipoMedioPago(aa.getAnalysisCode10());
      // Fecha vencimiento
      beanLista.setFechaVencimiento(fechaPago);
      // Numero de Constancia
      beanLista.setNroConstancia(aa.getNroConstancia());
      // Cuenta Proveedor
      beanLista.setCtaProveedor(aa.getAccounts().getLookupCode() + "");
      beanLista.setNomProveedor(aa.getAccounts().getLongDescription());
      lista.add(beanLista);
      
    }

    return lista;
  }

  public List<AsientoContableBean> generaAsientoRetenciones(com.cardif.sunsystems.mapeo.pagos.SSC.Payload.Ledger.Line aa, Date fechaPago, String ctaPropinas, String ctaGananciasPen,
      String ctaPerdidasPen, String ctaGananciasEur, String ctaPerdidasEur, Date fecha_actuali, float tasaEurosSoles, String glosaPago) {
    List<AsientoContableBean> lista = null;
    AsientoContableBean beanLista = null;
    Date fecha_actual = (Date) fecha_actuali.clone();
    BigDecimal suma_soles = new BigDecimal("0.00");
    BigDecimal suma_euros = new BigDecimal("0.00");
    BigDecimal suma_trans = new BigDecimal("0.00");
    BigDecimal suma_soles_2 = new BigDecimal("0.00");
    BigDecimal suma_euros_2 = new BigDecimal("0.00");
    BigDecimal suma_trans_2 = new BigDecimal("0.00");
    String periodo = "0" + formatPer.format(fecha_actuali);
    // String monedaRetencion = "";
    // String ProveedorEmpleado = "";
    String NumeroConstancia = "";
    String CtaProveedor = "";
    lista = new ArrayList<AsientoContableBean>();
    if (aa.getFlagPayment().equalsIgnoreCase("P")) {
      // Solo Procesar Pagadas
      beanLista = new AsientoContableBean();
      beanLista.setLayout("1;2;3");
      // Cuenta Proveedor
      beanLista.setCuentaContable(aa.getAccountCode() + "");

      // Libro
      beanLista.setNombreLibro("A");
      // Fecha Transaccion (Fecha Proceso)
      beanLista.setFecha(fecha_actual);
      // periodo
      beanLista.setPeriodo(periodo);
      // Glosa
      // beanLista.setGlosa(aa.getLossGain());
      beanLista.setGlosa(glosaPago);
      // Referencia / Nro Documento Cheque
      beanLista.setReferencia(aa.getTransactionReference());

      // Importe Transaccion
      beanLista.setImporteTransaccion(new BigDecimal(aa.getTransactionImport()));
      // Importe Soles
      beanLista.setImporteSoles(new BigDecimal(aa.getTransactionPen()));
      // Importe Euros
      beanLista.setImporteEuros(new BigDecimal(aa.getTransactionEuros()));

      // Marcador Debito/Credito
      if (beanLista.getImporteTransaccion().doubleValue() >= 0)
        beanLista.setMarcadorDC("D");
      else
        beanLista.setMarcadorDC("C");

      /*
       * if (aa.getJournalLineNumber().equals("07")) { beanLista.setMarcadorDC("C"); } else { beanLista.setMarcadorDC("D"); }
       */
      beanLista.setImporteTransaccion(redondear(2, beanLista.getImporteTransaccion()));
      beanLista.setImporteSoles(redondear(2, beanLista.getImporteSoles()));
      beanLista.setImporteEuros(redondear(2, beanLista.getImporteEuros()));
      // Moneda
      beanLista.setMoneda(aa.getCurrencyCode());
      // monedaRetencion = aa.getCurrencyCode();
      // Centro de Costos
      beanLista.setCentroCosto(aa.getAnalysisCode1());
      // Socio producto
      beanLista.setSocioProducto(aa.getAnalysisCode2());
      // Canal
      beanLista.setCanal(aa.getAnalysisCode3());
      // Proveedor/Empleado
      beanLista.setProveedorEmpleado(aa.getAccountCode() + "");
      // RUC/DNI cliente
      beanLista.setRucDniCliente(aa.getAccounts().getLookupCode() + "");
      // Poliza del cliente
      beanLista.setPolizaCliente(aa.getAnalysisCode6());
      // Inversiones
      beanLista.setInversiones(aa.getAnalysisCode7());
      // Nro Siniestro
      beanLista.setNroSiniestro(aa.getAnalysisCode8());
      // Tipo comp. SUNAT
      beanLista.setTipoComprobanteSunat(aa.getAnalysisCode9());
      // Impuesto IGV
      beanLista.setTipoMedioPago(aa.getAnalysisCode10());
      // Fecha vencimiento
      beanLista.setFechaVencimiento(fechaPago);
      // Numero de Constancia
      beanLista.setNroConstancia(aa.getLastCorrelative());
      NumeroConstancia = aa.getLastCorrelative();
      // Cuenta Proveedor
      beanLista.setCtaProveedor(aa.getAccounts().getLookupCode() + "");
      beanLista.setNomProveedor(aa.getAccounts().getLongDescription());

      CtaProveedor = aa.getAccounts().getLookupCode() + "";
      if (aa.getJournalLineNumber().equals("07")) {
      }

      suma_soles_2 = redondear(2, new BigDecimal(aa.getBaseAmount())).add(suma_soles_2);
      suma_euros_2 = redondear(2, new BigDecimal(aa.getBase2ReportingAmount())).add(suma_euros_2);
      suma_trans_2 = redondear(2, new BigDecimal(aa.getTransactionAmount())).add(suma_trans_2);

      suma_soles = redondear(2, beanLista.getImporteSoles()).add(suma_soles);
      suma_euros = redondear(2, beanLista.getImporteEuros()).add(suma_euros);
      suma_trans = redondear(2, beanLista.getImporteTransaccion()).add(suma_trans);
      lista.add(beanLista);


      // /Diferencia Soles, se trata de una provision de dolares////
      if (Utilidades.redondear(2, aa.getDiferencePen()) != 0.0) {
        beanLista = new AsientoContableBean();

        /***************************** Generamos linea para la diferencia de cambio SOLES *****************************/
        beanLista.setLayout("1;2;3");
        // Cuenta Proveedor
        if (aa.getDiferencePen() > 0) {
          beanLista.setCuentaContable(ctaGananciasPen);
        } else if (aa.getDiferencePen() < 0) {
          beanLista.setCuentaContable(ctaPerdidasPen);
        }

        // Libro
        beanLista.setNombreLibro("A");
        // Fecha Transaccion (Fecha Proceso)
        beanLista.setFecha(fecha_actual);
        // periodo
        beanLista.setPeriodo(periodo);
        // Glosa
        beanLista.setGlosa("Diferencia de Cambio");
        // Referencia / Nro Documento Cheque
        beanLista.setReferencia(aa.getTransactionReference());
        // Importe Transaccion
        beanLista.setImporteTransaccion(new BigDecimal("0.0"));

        if (aa.getDiferencePen() > 0) {
          // Importe Soles
          beanLista.setImporteSoles(new BigDecimal(aa.getDiferencePen()).abs().negate());
        } else if (aa.getDiferencePen() < 0) {
          // Importe Soles
          beanLista.setImporteSoles(new BigDecimal(aa.getDiferencePen()).abs());
        }

        // Importe Euros
        beanLista.setImporteEuros(new BigDecimal("0.0"));

        // Marcador C/D
        if (beanLista.getImporteSoles().doubleValue() >= 0)
          beanLista.setMarcadorDC("D");
        else
          beanLista.setMarcadorDC("C");

        beanLista.setImporteTransaccion(redondear(2, beanLista.getImporteTransaccion()));
        beanLista.setImporteSoles(redondear(2, beanLista.getImporteSoles()));
        beanLista.setImporteEuros(redondear(2, beanLista.getImporteEuros()));
        // Moneda, como se trata de colocar la diferencia de cambio en una cuenta soles, la modena de la operacion debe ser soles
        beanLista.setMoneda(ConstantesSun.UTL_MONEDA_SOLES);
        // Centro de Costos
        beanLista.setCentroCosto(aa.getAnalysisCode1());
        // Socio producto
        beanLista.setSocioProducto(aa.getAnalysisCode2());
        // Canal
        beanLista.setCanal(aa.getAnalysisCode3());
        // Proveedor/Empleado
        beanLista.setProveedorEmpleado(aa.getAnalysisCode4());
        // RUC/DNI cliente
        beanLista.setRucDniCliente(aa.getAccounts().getLookupCode() + "");
        // Poliza del cliente
        beanLista.setPolizaCliente(aa.getAnalysisCode6());
        // Inversiones
        beanLista.setInversiones(aa.getAnalysisCode7());
        // Nro Siniestro
        beanLista.setNroSiniestro(aa.getAnalysisCode8());
        // Tipo comp. SUNAT
        beanLista.setTipoComprobanteSunat(aa.getAnalysisCode9());
        // Impuesto IGV
        beanLista.setTipoMedioPago(aa.getAnalysisCode10());
        // Periodo
        beanLista.setPeriodo(periodo);
        // Fecha vencimiento
        beanLista.setFechaVencimiento(fechaPago);
        // Numero de Constancia
        beanLista.setNroConstancia(aa.getLastCorrelative());

        // Cuenta Proveedor
        beanLista.setCtaProveedor(aa.getAccounts().getLookupCode() + "");
        beanLista.setNomProveedor(aa.getAccounts().getLongDescription());

        lista.add(beanLista);

        /***************************** Generamos linea para la provision *****************************/
        beanLista = new AsientoContableBean();

        beanLista.setLayout("1;2;3");
        // Cuenta Proveedor
        beanLista.setCuentaContable(aa.getAccountCode() + "");

        // Libro
        beanLista.setNombreLibro("A");
        // Fecha Transaccion (Fecha Proceso)
        beanLista.setFecha(fecha_actual);
        // periodo
        beanLista.setPeriodo(periodo);
        // Glosa
        beanLista.setGlosa("Diferencia de Cambio");
        // Referencia / Nro Documento Cheque
        beanLista.setReferencia(aa.getTransactionReference());
        // Importe Transaccion
        beanLista.setImporteTransaccion(new BigDecimal("0.0"));

        if (aa.getDiferencePen() > 0) {
          // Importe Soles
          beanLista.setImporteSoles(new BigDecimal(aa.getDiferencePen()).abs());
        } else if (aa.getDiferencePen() < 0) {
          // Importe Soles
          beanLista.setImporteSoles(new BigDecimal(aa.getDiferencePen()).abs().negate());
        }
        // Importe Euros
        beanLista.setImporteEuros(new BigDecimal("0.0"));

        // Marcador C/D
        if (beanLista.getImporteSoles().doubleValue() >= 0)
          beanLista.setMarcadorDC("D");
        else
          beanLista.setMarcadorDC("C");

        beanLista.setImporteTransaccion(redondear(2, beanLista.getImporteTransaccion()));
        beanLista.setImporteSoles(redondear(2, beanLista.getImporteSoles()));
        beanLista.setImporteEuros(redondear(2, beanLista.getImporteEuros()));
        // Moneda
        beanLista.setMoneda(aa.getCurrencyCode());
        // Centro de Costos
        beanLista.setCentroCosto(aa.getAnalysisCode1());
        // Socio producto
        beanLista.setSocioProducto(aa.getAnalysisCode2());
        // Canal
        beanLista.setCanal(aa.getAnalysisCode3());
        // Proveedor/Empleado
        beanLista.setProveedorEmpleado(aa.getAccountCode() + "");
        // RUC/DNI cliente
        beanLista.setRucDniCliente(aa.getAccounts().getLookupCode() + "");
        // Poliza del cliente
        beanLista.setPolizaCliente(aa.getAnalysisCode6());
        // Inversiones
        beanLista.setInversiones(aa.getAnalysisCode7());
        // Nro Siniestro
        beanLista.setNroSiniestro(aa.getAnalysisCode8());
        // Tipo comp. SUNAT
        beanLista.setTipoComprobanteSunat(aa.getAnalysisCode9());
        // Impuesto IGV
        beanLista.setTipoMedioPago(aa.getAnalysisCode10());
        // Fecha vencimiento
        beanLista.setFechaVencimiento(fechaPago);
        // Numero de Constancia
        beanLista.setNroConstancia(aa.getLastCorrelative());

        // Cuenta Proveedor
        beanLista.setCtaProveedor(aa.getAccounts().getLookupCode() + "");
        beanLista.setNomProveedor(aa.getAccounts().getLongDescription());
        lista.add(beanLista);
      }

    }

    // Agregar un ficticio de resumen de sumas
    AsientoContableBean ficticio = new AsientoContableBean();
    ficticio.setGlosa("LINEA FICTICIA");
    ficticio.setImporteSoles(suma_soles);
    ficticio.setImporteEuros(suma_euros);
    ficticio.setImporteTransaccion(suma_trans);
    ficticio.setRefImpSoles(suma_soles_2.toString());
    ficticio.setRefImpEuros(suma_euros_2.toString());
    ficticio.setRefImpTrans(suma_trans_2.toString());
    // CODIGOS QUE FALTABAN:
    ficticio.setCentroCosto(aa.getAnalysisCode1());
    // Socio producto
    ficticio.setSocioProducto(aa.getAnalysisCode2());
    // Proveedor/Empleado
    ficticio.setProveedorEmpleado(aa.getAccountCode() + "");
    // RUC/DNI cliente
    ficticio.setRucDniCliente(aa.getAccounts().getLookupCode() + "");
    // Comprobante sunat:
    ficticio.setTipoComprobanteSunat(aa.getAnalysisCode9());
    // Cuenta Proveedor:
    ficticio.setCtaProveedor(aa.getAccounts().getLookupCode() + "");
    ficticio.setNomProveedor(aa.getAccounts().getLongDescription());
    // Moneda
    ficticio.setMoneda(aa.getCurrencyCode());

    ficticio.setNroConstancia(NumeroConstancia);
    ficticio.setCtaProveedor(CtaProveedor);
    ficticio.setTipoMedioPago(lista.get(0).getTipoMedioPago());
    lista.add(ficticio);
    return lista;
  }

  private String toBigdecimal(String amountTips) {
    BigDecimal ab;
    if (amountTips.trim().equals("")) {
      ab = new BigDecimal("0.0");
    } else {
      ab = new BigDecimal(amountTips);
    }
    return ab.toString();
  }

  public String validarEnvioSun(List<AsientoContableBean> lista2, HashMap<Integer, Object> resultados, String numCuentaBanco) {
    String CodigoCuenta = null;
    String CodigoMoneda = null;
    String Moneda = null;
    String IndCuenta = null;
    int cantidad = 0;
    String Descripcion = null;
    String ReferenciaTrans = null;
    String Resultado = "EnvioSUN";
    String mensajesValidacion = "";
    cantidad = 0;
    BigDecimal ImporteTrans = new BigDecimal(BigInteger.ZERO);
    BigDecimal ImporteBase = new BigDecimal(BigInteger.ZERO);
    BigDecimal ImporteEuro = new BigDecimal(BigInteger.ZERO);

    BigDecimal SumaImporteTrans = new BigDecimal(BigInteger.ZERO);
    BigDecimal SumaImporteBase = new BigDecimal(BigInteger.ZERO);
    BigDecimal SumaImporteEuro = new BigDecimal(BigInteger.ZERO);
    resultados.clear();
    for (int i = 0; i < lista2.size(); i++) {
      AsientoContableBean fila = lista2.get(i);
      if (fila.getLayout() == null) {

      }
      CodigoCuenta = fila.getCuentaContable();
      Descripcion = fila.getGlosa();
      if (Descripcion == null) {
        Descripcion = "";
      }
      // Descripcion = "..";
      ReferenciaTrans = fila.getReferencia();
      if (ReferenciaTrans == null) {
        ReferenciaTrans = "";
      }
      // ReferenciaTrans = "..";
      Moneda = fila.getMoneda();
      IndCuenta = CodigoCuenta.substring(0, 1);
      if (IndCuenta.equals("P")) {
        CodigoMoneda = extraerCadena(CodigoCuenta, 3, true);
        CodigoMoneda = CodigoMoneda.substring(0, 1);
        fila.setMarcador("P");
      } else {
        CodigoMoneda = Utilidades.mid(CodigoCuenta, 3, 1);
      }

      CodigoMoneda = numCuentaBanco.substring(2, 3);
      logger.info("Codigo Moneda: " + CodigoMoneda + "--- Cuenta: " + fila.getCuentaContable());
      if (CodigoCuenta.equals("")) {
        mensajesValidacion = mensajesValidacion + "\n" + " Codigo de Cuenta, campo no debe estar vacio, revisar";
        logger.info(mensajesValidacion);
      }

      if (Descripcion.equals("")) {
        mensajesValidacion = mensajesValidacion + "\n" + " Glosa,  campo no debe estar vacio, revisar";
        logger.info(mensajesValidacion);
      } else {
        if (Descripcion.length() > 50) {
          mensajesValidacion = mensajesValidacion + "\n" + " Descripcion de Glosa muy largo, tiene actualmente " + Descripcion.length() + "caracteres";
          logger.info(mensajesValidacion);
        }
      }
      if (ReferenciaTrans.equals("")) {
        mensajesValidacion = mensajesValidacion + "\n" + " Referencia Transaccion,  campo no debe estar vacio, revisar";
        logger.info(mensajesValidacion);
      }
      if (Moneda.equals("")) {
        mensajesValidacion = mensajesValidacion + "\n" + " Codigo de Moneda, campo no debe estar vacio, revisar";
        logger.info(mensajesValidacion);
      }
      if ((!Moneda.equals("PEN")) && (!Moneda.equals("USD")) && (!Moneda.equals("EUR")) && (!Moneda.equals("CAN"))) {
        mensajesValidacion = mensajesValidacion + "\n" + " El Codigo de Moneda " + Moneda + " no definida en SunSystems, revisar valor";
        logger.info(mensajesValidacion);
      }

      if (CodigoMoneda.equals("1") && (!Moneda.equals("PEN"))) {
        logger.info(mensajesValidacion);
      }
      if (CodigoMoneda.equals("2") && (!Moneda.equals("USD"))) {
        logger.info(mensajesValidacion);
      }
      if (!mensajesValidacion.replace("\r", "").replace("\n", "").replace("  ", "").equals("")) {
        resultados.put(i, mensajesValidacion);
      }
      cantidad = cantidad + 1;

      ImporteBase = fila.getImporteSoles();
      ImporteTrans = fila.getImporteTransaccion();
      ImporteEuro = fila.getImporteEuros();
      SumaImporteBase = Utilidades.redondear(2, SumaImporteBase).add(Utilidades.redondear(2, ImporteBase));
      SumaImporteTrans = Utilidades.redondear(2, SumaImporteTrans).add(Utilidades.redondear(2, ImporteTrans));
      SumaImporteEuro = Utilidades.redondear(2, SumaImporteEuro).add(Utilidades.redondear(2, ImporteEuro));

    }
    if (resultados.size() == 0) {
      logger.info("Sin Errores de Validacion por iteracion");
      if (CodigoMoneda.equals("2") && CodigoMoneda.equals("USD")) {
        if (SumaImporteBase.floatValue() != 0.0 || SumaImporteEuro.floatValue() != 0.0) {
          // Resultado = "KO";
          mensajesValidacion = "Asiento descuadrado por Importe Base, Transaccion o Euro";
          logger.info(mensajesValidacion + " SumaImporteBase: " + SumaImporteBase.floatValue() + " SumaImporteEuro: " + SumaImporteEuro.floatValue());
        }
      } else {
        if (SumaImporteBase.floatValue() != 0.0 || SumaImporteTrans.floatValue() != 0.0 || SumaImporteEuro.floatValue() != 0.0) {
          // Resultado = "KO";
          mensajesValidacion = "Asiento descuadrado por Importe Base, Transaccion o Euro";
          logger.info(
              mensajesValidacion + " SumaImporteBase: " + SumaImporteBase.floatValue() + " SumaImporteTrans: " + SumaImporteTrans.floatValue() + " SumaImporteEuro :" + SumaImporteEuro.floatValue());
        }
      }
    } else {
      logger.error("Con Errores de Validacion por iteracion");
      Resultado = "KO \n" + mensajesValidacion;
    }
    return Resultado;
  }

  public List<AsientoContableBean> ultimasLineasDetraccionesMasivas(List<AsientoContableBean> lista2, String ctaBanco, Date fecha_actual, Date fecha_proceso, String ctaprov, BigDecimal suma_trans,
      BigDecimal suma_soles, BigDecimal suma_euros, String ctaComision, BigDecimal ImpComision, String CentroCosto, BigDecimal tceuro, String ctacom, String numeroConstancia) {
    String periodo = "0" + formatPer.format(fecha_actual);
    if (lista2.size() > 0) {
      AsientoContableBean ultimo_asiento = lista2.get(lista2.size() - 1);
      AsientoContableBean beanLista = null;

      beanLista = new AsientoContableBean();

      beanLista.setLayout("1;2;3");
      // Cuenta Proveedor
      beanLista.setCuentaContable(ctaBanco);
      // Libro
      beanLista.setNombreLibro("A");
      // Fecha Transaccion (Fecha Proceso)
      beanLista.setFecha(fecha_actual);
      // periodo
      beanLista.setPeriodo(periodo);
      // Glosa
      beanLista.setGlosa("PAGO DETRACCION LOTE # OPER #");
      // Referencia / Nro Documento Cheque
      beanLista.setReferencia(ctaprov);
      // Importe Transaccion
      beanLista.setImporteTransaccion(suma_trans.negate());
      // Importe Soles
      beanLista.setImporteSoles(suma_soles.negate());
      // Importe Euros
      beanLista.setImporteEuros(suma_euros.negate());
      // Marcador debito/credito
      beanLista.setMarcadorDC("C");
      // Moneda
      if (Utilidades.mid(ctaBanco, 3, 1).equals("1")) {
        beanLista.setMoneda("PEN");
      } else {
        beanLista.setMoneda("USD");
      }

      // Centro de Costos
      beanLista.setCentroCosto(ultimo_asiento.getCentroCosto());
      // Socio producto
      beanLista.setSocioProducto(ultimo_asiento.getSocioProducto());
      // Canal
      beanLista.setCanal(ultimo_asiento.getCanal());
      // Proveedor/Empleado
      beanLista.setProveedorEmpleado("");
      // RUC/DNI cliente ******************************************************
      beanLista.setRucDniCliente(ultimo_asiento.getRucDniCliente());
      // Poliza del cliente
      beanLista.setPolizaCliente(ultimo_asiento.getPolizaCliente());
      // Inversiones
      beanLista.setInversiones(ultimo_asiento.getInversiones());
      // Nro Siniestro
      beanLista.setNroSiniestro(ultimo_asiento.getNroSiniestro());
      // Tipo comp. SUNAT
      beanLista.setTipoComprobanteSunat(ultimo_asiento.getTipoComprobanteSunat());
      // Impuesto IGV ******************************************
      beanLista.setTipoMedioPago(ultimo_asiento.getTipoMedioPago());
      // Fecha vencimiento
      // beanLista.setFechaVencimiento(form.parse(txtFechaPago.getText()));
      beanLista.setFechaVencimiento(fecha_proceso);
      // Numero de Constancia
      beanLista.setNroConstancia(numeroConstancia);

      // Cuenta Proveedor *********************************
      beanLista.setCtaProveedor(ultimo_asiento.getCtaProveedor());

      lista2.add(beanLista);

      /***********************************************************************/
      beanLista = new AsientoContableBean();

      beanLista.setLayout("1;2;3");
      // Cuenta Proveedor
      beanLista.setCuentaContable(ctaComision);
      // Libro
      beanLista.setNombreLibro("A");
      // Fecha Transaccion (Fecha Proceso)
      beanLista.setFecha(fecha_actual);
      // periodo
      beanLista.setPeriodo(periodo);
      // Glosa
      beanLista.setGlosa("PAGO COMISION DETRACCION LOTE # OPER #");
      // Referencia / Nro Documento Cheque
      beanLista.setReferencia(ctacom);
      // Importe Transaccion
      beanLista.setImporteTransaccion(ImpComision);
      // Importe Soles
      beanLista.setImporteSoles(ImpComision);
      // Importe Euros
      beanLista.setImporteEuros(ImpComision.divide(tceuro, 2, RoundingMode.HALF_UP));
      // Marcador debito/credito
      beanLista.setMarcadorDC("D");
      // Moneda
      if (Utilidades.mid(ctaBanco, 3, 1).equals("1")) {
        beanLista.setMoneda("PEN");
      } else {
        beanLista.setMoneda("USD");
      }

      // Centro de Costos
      beanLista.setCentroCosto(CentroCosto);
      // Socio producto
      beanLista.setSocioProducto(ultimo_asiento.getSocioProducto());
      // Canal
      beanLista.setCanal(ultimo_asiento.getCanal());
      // Proveedor/Empleado
      beanLista.setProveedorEmpleado("");
      // RUC/DNI cliente ******************************************************
      beanLista.setRucDniCliente(ultimo_asiento.getRucDniCliente());
      // Poliza del cliente
      beanLista.setPolizaCliente(ultimo_asiento.getPolizaCliente());
      // Inversiones
      beanLista.setInversiones(ultimo_asiento.getInversiones());
      // Nro Siniestro
      beanLista.setNroSiniestro(ultimo_asiento.getNroSiniestro());
      // Tipo comp. SUNAT
      beanLista.setTipoComprobanteSunat(ultimo_asiento.getTipoComprobanteSunat());
      // Impuesto IGV ******************************************
      beanLista.setTipoMedioPago(ultimo_asiento.getTipoMedioPago());
      // Fecha vencimiento
      // beanLista.setFechaVencimiento(form.parse(txtFechaPago.getText()));
      beanLista.setFechaVencimiento(fecha_proceso);
      // Numero de Constancia
      beanLista.setNroConstancia(numeroConstancia);

      // Cuenta Proveedor *********************************
      beanLista.setCtaProveedor(ultimo_asiento.getCtaProveedor());

      lista2.add(beanLista);

      /***********************************************************************/
      beanLista = new AsientoContableBean();

      beanLista.setLayout("1;2;3");
      // Cuenta Proveedor
      beanLista.setCuentaContable(ctaBanco);
      // Libro
      beanLista.setNombreLibro("A");
      // Fecha Transaccion (Fecha Proceso)
      beanLista.setFecha(fecha_actual);
      // periodo
      beanLista.setPeriodo(periodo);
      // Glosa
      beanLista.setGlosa("PAGO COMISION DETRACCION LOTE # OPER #");
      // Referencia / Nro Documento Cheque
      beanLista.setReferencia(ctacom);
      // Importe Transaccion
      beanLista.setImporteTransaccion(ImpComision.negate());
      // Importe Soles
      beanLista.setImporteSoles(ImpComision.negate());
      // Importe Euros
      beanLista.setImporteEuros(ImpComision.divide(tceuro, 2, RoundingMode.HALF_UP).negate());
      // Marcador debito/credito
      beanLista.setMarcadorDC("C");
      // Moneda

      if (Utilidades.mid(ctaBanco, 3, 1).equals("1")) {
        beanLista.setMoneda("PEN");
      } else {
        beanLista.setMoneda("USD");
      }

      // Centro de Costos
      beanLista.setCentroCosto(CentroCosto);
      // Socio producto
      beanLista.setSocioProducto(ultimo_asiento.getSocioProducto());
      // Canal
      beanLista.setCanal(ultimo_asiento.getCanal());
      // Proveedor/Empleado
      // beanLista.setProveedorEmpleado("");
      // RUC/DNI cliente ******************************************************
      beanLista.setRucDniCliente(ultimo_asiento.getRucDniCliente());
      // Poliza del cliente
      beanLista.setPolizaCliente(ultimo_asiento.getPolizaCliente());
      // Inversiones
      beanLista.setInversiones(ultimo_asiento.getInversiones());
      // Nro Siniestro
      beanLista.setNroSiniestro(ultimo_asiento.getNroSiniestro());
      // Tipo comp. SUNAT
      beanLista.setTipoComprobanteSunat(ultimo_asiento.getTipoComprobanteSunat());
      // Impuesto IGV ******************************************
      beanLista.setTipoMedioPago(ultimo_asiento.getTipoMedioPago());
      // Fecha vencimiento
      // beanLista.setFechaVencimiento(form.parse(txtFechaPago.getText()));
      beanLista.setFechaVencimiento(fecha_proceso);
      // Numero de Constancia
      beanLista.setNroConstancia(numeroConstancia);

      // Cuenta Proveedor *********************************
      beanLista.setCtaProveedor(ultimo_asiento.getCtaProveedor());

      lista2.add(beanLista);

    }
    return lista2;
  }

  public String generarPayloadActualizarMarcador(int numeroDiario, String NumeroLinea) throws JAXBException {
    // 1. We need to create JAXContext instance
    JAXBContext jaxbContext = JAXBContext.newInstance(com.cardif.sunsystems.mapeo.actualizaMarcador.ObjectFactory.class);

    // 2. Use JAXBContext instance to create the Unmarshaller.
    Marshaller marshaller = jaxbContext.createMarshaller();
    com.cardif.sunsystems.mapeo.actualizaMarcador.SSC jaxbElement = new com.cardif.sunsystems.mapeo.actualizaMarcador.SSC();
    com.cardif.sunsystems.mapeo.actualizaMarcador.SSC.ErrorContext error = new com.cardif.sunsystems.mapeo.actualizaMarcador.SSC.ErrorContext();
    com.cardif.sunsystems.mapeo.actualizaMarcador.SSC.User user = new com.cardif.sunsystems.mapeo.actualizaMarcador.SSC.User();
    com.cardif.sunsystems.mapeo.actualizaMarcador.SSC.SunSystemsContext system = new com.cardif.sunsystems.mapeo.actualizaMarcador.SSC.SunSystemsContext();
    com.cardif.sunsystems.mapeo.actualizaMarcador.SSC.Payload payload = new com.cardif.sunsystems.mapeo.actualizaMarcador.SSC.Payload();
    com.cardif.sunsystems.mapeo.actualizaMarcador.SSC.Payload.AllocationMarkers allocation = new com.cardif.sunsystems.mapeo.actualizaMarcador.SSC.Payload.AllocationMarkers();

    allocation.setJournalLineNumber(Integer.parseInt(NumeroLinea));
    allocation.setJournalNumber(numeroDiario);
    // allocation.setAllocationMarker(Parametros.AllocationMarker_W);
    payload.setAllocationMarkers(allocation);

    user.setName(ConstantesSun.AUTH_USR_SUN_LOGIN);
    system.setBudgetCode(ConstantesSun.SSC_BudgetCode);
    system.setBusinessUnit(ConstantesSun.SSC_BusinessUnit_E01);
    error.setErrorOutput(3);

    jaxbElement.setErrorContext(error);
    jaxbElement.setPayload(payload);
    jaxbElement.setUser(user);
    jaxbElement.setSunSystemsContext(system);

    Writer writer = new StringWriter();
    marshaller.marshal(jaxbElement, writer);

    return writer.toString();
  }

  public String generarPayloadActualizarMarcadorSun(int numeroDiario, String NumeroLinea, String marca) throws JAXBException {
    // 1. We need to create JAXContext instance
    JAXBContext jaxbContext = JAXBContext.newInstance(com.cardif.sunsystems.mapeo.actualizaMarcador2.ObjectFactory.class);

    // 2. Use JAXBContext instance to create the Unmarshaller.
    Marshaller marshaller = jaxbContext.createMarshaller();
    com.cardif.sunsystems.mapeo.actualizaMarcador2.SSC jaxbElement = new com.cardif.sunsystems.mapeo.actualizaMarcador2.SSC();
    com.cardif.sunsystems.mapeo.actualizaMarcador2.SSC.SunSystemsContext system = new com.cardif.sunsystems.mapeo.actualizaMarcador2.SSC.SunSystemsContext();
    com.cardif.sunsystems.mapeo.actualizaMarcador2.SSC.Payload payload = new com.cardif.sunsystems.mapeo.actualizaMarcador2.SSC.Payload();
    com.cardif.sunsystems.mapeo.actualizaMarcador2.SSC.Payload.AllocationMarkers allocation = new com.cardif.sunsystems.mapeo.actualizaMarcador2.SSC.Payload.AllocationMarkers();
    com.cardif.sunsystems.mapeo.actualizaMarcador2.SSC.Payload.AllocationMarkers.Actions actions = new com.cardif.sunsystems.mapeo.actualizaMarcador2.SSC.Payload.AllocationMarkers.Actions();

    actions.setAllocationMarker(marca);
    allocation.setJournalLineNumber(Integer.parseInt(NumeroLinea));
    allocation.setJournalNumber(numeroDiario);
    allocation.setActions(actions);
    payload.setAllocationMarkers(allocation);

    system.setBudgetCode(ConstantesSun.SSC_BudgetCode);
    system.setBusinessUnit(ConstantesSun.SSC_BusinessUnit_E01);

    jaxbElement.setPayload(payload);
    jaxbElement.setSunSystemsContext(system);

    Writer writer = new StringWriter();
    marshaller.marshal(jaxbElement, writer);
    logger.info("Payload Actualizar Marcador: " + writer.toString());
    return writer.toString();
  }

  public List<com.cardif.sunsystems.mapeo.cuentaBancaria.SSC.Payload.Accounts> leerXmlCuentaBancaria(String CadenaXml) {
    List<com.cardif.sunsystems.mapeo.cuentaBancaria.SSC.Payload.Accounts> superlinea = null;
    try {
      // 1. We need to create JAXContext instance
      JAXBContext jaxbContext = JAXBContext.newInstance(com.cardif.sunsystems.mapeo.cuentaBancaria.ObjectFactory.class);

      // 2. Use JAXBContext instance to create the Unmarshaller.
      Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

      // 3. Use the Unmarshaller to unmarshal the XML document to get an instance of JAXBElement.
      StringReader reader = new StringReader(CadenaXml);
      com.cardif.sunsystems.mapeo.cuentaBancaria.SSC expenseObj = (com.cardif.sunsystems.mapeo.cuentaBancaria.SSC) unmarshaller.unmarshal(reader);
      superlinea = expenseObj.getPayload().getAccounts();

    } catch (Exception e) {
      logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
      logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
    }
    return superlinea;
  }

  public HashMap<Integer, String> leerXmlsalidaSun(String resultado) {
    HashMap<Integer, String> salida = new HashMap<Integer, String>();
    try {
      // 1. We need to create JAXContext instance
      JAXBContext jaxbContext = JAXBContext.newInstance(com.cardif.sunsystems.mapeo.salidaSun.ObjectFactory.class);

      // 2. Use JAXBContext instance to create the Unmarshaller.
      Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

      // 3. Use the Unmarshaller to unmarshal the XML document to get an instance of JAXBElement.
      StringReader reader = new StringReader(resultado);
      com.cardif.sunsystems.mapeo.salidaSun.SSC objeto = (com.cardif.sunsystems.mapeo.salidaSun.SSC) unmarshaller.unmarshal(reader);

      List<com.cardif.sunsystems.mapeo.salidaSun.SSC.Payload.Ledger.Line> lineas = objeto.getPayload().getLedger().getLine();
      for (com.cardif.sunsystems.mapeo.salidaSun.SSC.Payload.Ledger.Line linea : lineas) {
        if (linea.getStatus().equals("fail")) {
          List<com.cardif.sunsystems.mapeo.salidaSun.SSC.Payload.Ledger.Line.Messages.Message> listamensaje = linea.getMessages().getMessage();
          if (listamensaje != null) {
            for (com.cardif.sunsystems.mapeo.salidaSun.SSC.Payload.Ledger.Line.Messages.Message mensaje : listamensaje) {
              // Obtener los mensajes
              if (mensaje.getLevel().equals("error")) {
                salida.put(linea.getReference(), linea.getJournalLineNumber() + ConstantesSun.UTL_CHR_SEPARADOR + mensaje.getUserText());
                logger.error(linea.getReference() + ";" + linea.getStatus() + ";" + mensaje.getUserText() + ";" + linea.getJournalLineNumber() + ";" + linea.getJournalNumber());
                break;
              }
            }
          }
        }
      }
    } catch (Exception e) {
      logger.error("Errores en Utilidades.LeerXmlsalidaSun");
      salida = null;
    }
    return salida;
  }

  public HashMap<Integer, Integer> leerXmlsalidaSunExitoso(String resultado) {
    HashMap<Integer, Integer> salida = new HashMap<Integer, Integer>();
    try {
      // 1. We need to create JAXContext instance
      JAXBContext jaxbContext = JAXBContext.newInstance(com.cardif.sunsystems.mapeo.salidaSun.ObjectFactory.class);

      // 2. Use JAXBContext instance to create the Unmarshaller.
      Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

      // 3. Use the Unmarshaller to unmarshal the XML document to get an instance of JAXBElement.
      StringReader reader = new StringReader(resultado);
      com.cardif.sunsystems.mapeo.salidaSun.SSC objeto = (com.cardif.sunsystems.mapeo.salidaSun.SSC) unmarshaller.unmarshal(reader);

      List<com.cardif.sunsystems.mapeo.salidaSun.SSC.Payload.Ledger.Line> lineas = objeto.getPayload().getLedger().getLine();
      for (com.cardif.sunsystems.mapeo.salidaSun.SSC.Payload.Ledger.Line linea : lineas) {
        if (linea.getStatus().equals("success")) {
          salida.put(linea.getJournalLineNumber(), linea.getJournalNumber());
        }
      }
    } catch (Exception e) {
      salida = null;
    }
    return salida;
  }

  public Document loadXMLFromResource(String xml) throws ParserConfigurationException, SAXException, IOException {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    InputStream In = getClass().getResourceAsStream(xml);
    return builder.parse(In);
  }

  public String documenttoString(Document newDoc) throws TransformerFactoryConfigurationError, TransformerException {
    DOMSource domSource = new DOMSource(newDoc);
    Transformer transformer = TransformerFactory.newInstance().newTransformer();
    StringWriter sw = new StringWriter();
    StreamResult sr = new StreamResult(sw);
    transformer.transform(domSource, sr);
    return sw.toString();
  }

  public static String completarCeros(String numero, String cantidad) {

    return String.format("%0" + cantidad + "d", Integer.parseInt(numero));
  }
  
  public List<List<com.cardif.sunsystems.mapeo.reportes.SSC.Payload.Ledger.Line>> LeerXmlReportes(String CadenaXml) {
	    List<List<com.cardif.sunsystems.mapeo.reportes.SSC.Payload.Ledger.Line>> superlista = new ArrayList<List<com.cardif.sunsystems.mapeo.reportes.SSC.Payload.Ledger.Line>>();
	    try {
	      // 1. We need to create JAXContext instance
	      JAXBContext jaxbContext = JAXBContext.newInstance(com.cardif.sunsystems.mapeo.reportes.ObjectFactory.class);

	      // 2. Use JAXBContext instance to create the Unmarshaller.
	      Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

	      // 3. Use the Unmarshaller to unmarshal the XML document to get an instance of JAXBElement.
	      StringReader reader = new StringReader(CadenaXml);
	      com.cardif.sunsystems.mapeo.reportes.SSC expenseObj = (com.cardif.sunsystems.mapeo.reportes.SSC) unmarshaller.unmarshal(reader);
	      com.cardif.sunsystems.mapeo.reportes.SSC.Payload.Ledger superlinea = expenseObj.getPayload().getLedger();
	      // SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy");

	      List<com.cardif.sunsystems.mapeo.reportes.SSC.Payload.Ledger.Line> lineas = superlinea.getLine();

	      List<com.cardif.sunsystems.mapeo.reportes.SSC.Payload.Ledger.Line> lineas_agrupadas = null;
	      String socioProducto = "";
	      for (com.cardif.sunsystems.mapeo.reportes.SSC.Payload.Ledger.Line aa : lineas) {
	        boolean salta = false;
	        if (socioProducto.equals(aa.getAccounts().getLookupCode() + "")) {
	          lineas_agrupadas.add(aa);
	          socioProducto = aa.getAccounts().getLookupCode() + "";
	          salta = false;
	        } else {
	          lineas_agrupadas = new ArrayList<com.cardif.sunsystems.mapeo.reportes.SSC.Payload.Ledger.Line>();
	          lineas_agrupadas.add(aa);
	          socioProducto = aa.getAccounts().getLookupCode() + "";
	          salta = true;
	        }
	        if (salta) {
	          superlista.add(lineas_agrupadas);
	        }
	      }
	    } catch (Exception e) {
	      superlista = null;
	      logger.error(e.getMessage());
	    }
	    return superlista;
  }
  
}
