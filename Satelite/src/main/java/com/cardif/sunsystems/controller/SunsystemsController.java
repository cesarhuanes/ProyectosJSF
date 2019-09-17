package com.cardif.sunsystems.controller;

import static com.cardif.satelite.constantes.Constantes.TIP_PARAM_DETALLE;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.configuracion.service.ParametroService;
import com.cardif.satelite.constantes.Constantes;
import com.cardif.satelite.model.Parametro;
import com.cardif.satelite.tesoreria.bean.JournalBean;
import com.cardif.satelite.tesoreria.dao.AsientoContableSecuencialMapper;
import com.cardif.satelite.tesoreria.model.AsientoContableSecuencial;
import com.cardif.sunsystems.bean.AsientoContableBean;
import com.cardif.sunsystems.mapeo.pagos.SSC.Payload.Ledger.Line;
import com.cardif.sunsystems.services.SunComponenteService;
import com.cardif.sunsystems.services.SunSeguridadService;
import com.cardif.sunsystems.util.ConstantesSun;
import com.cardif.sunsystems.util.Utilidades;

public class SunsystemsController {
  static SunsystemsController instancia = null;
  private static Logger logger = Logger.getLogger(SunsystemsController.class);

  public static synchronized SunsystemsController getInstance() {
    if (instancia == null) {
      instancia = new SunsystemsController();
    }
    return instancia;
  }

  @Autowired
  private AsientoContableSecuencialMapper asientoMaper;
  SunComponenteService componentes = null;
  private DateFormat formatPer = new SimpleDateFormat("MMyyyy");

  private String nroLote;

  private String opBanco;
  private String opComision;
  private ParametroService parametroService;
  SunSeguridadService seguridad = null;

  private String tipoDiario;

  Utilidades utiles = null;

  public SunsystemsController() {
    seguridad = new SunSeguridadService();
    utiles = new Utilidades();
  }

  public String actualizarMarcaProvisiones(String Payload) {
    logger.info("Inicio");
    String ResultadoXml = componentes.ejecutaConsulta(ConstantesSun.SSC_ComponenteJournal, ConstantesSun.SSC_MetodoLoadAndPost, Payload);
    logger.info("Fin");
    return ResultadoXml;
  }

  public String autentificarWebService(String user, String pass) throws Exception {
    try {
      logger.info("Ingresando AutenticacionWSDL");
      String auth = seguridad.autentificarUsuario(user, pass);
      logger.info("Luego de llamar al WSDL");
      componentes = new SunComponenteService();
      logger.info("Devuele el componente SunServiceComponente");
      return auth;
    } catch (NullPointerException e) {
      return null;
    }
  }

  public String crearPayloadMarcaProvisiones(JournalBean provision) {
    String xmlGeneradotoSun = "";
    return actualizarMarcaProvisiones(xmlGeneradotoSun);
  }

  private String ctaGananciasEur() throws SyncconException {
    List<Parametro> listactaGananciasEur = getListaGenerica(Constantes.MDP_DIF_CAMBIO_WIN_EUR, TIP_PARAM_DETALLE);
    return listactaGananciasEur.get(0).getNomValor();
  }

  private String ctaGananciasEurUSD() throws SyncconException {
    List<Parametro> listactaGananciasEur = getListaGenerica(Constantes.MDP_DIF_CAMBIO_WIN_EUR_USD, TIP_PARAM_DETALLE);
    return listactaGananciasEur.get(0).getNomValor();
  }

  private String ctaGananciasPen() throws SyncconException {
    List<Parametro> listactaGananciasPen = getListaGenerica(Constantes.MDP_DIF_CAMBIO_WIN_PEN, TIP_PARAM_DETALLE);
    return listactaGananciasPen.get(0).getNomValor();
  }

  private String ctaGananciasUSD() throws SyncconException {
    List<Parametro> listactaGananciasUsd = getListaGenerica(Constantes.MDP_DIF_CAMBIO_WIN_USD, TIP_PARAM_DETALLE);
    return listactaGananciasUsd.get(0).getNomValor();
  }

  // CUENTAS para SOLES:
  private String ctaPerdidasEur() throws SyncconException {
    List<Parametro> listactaPerdidasEur = getListaGenerica(Constantes.MDP_DIF_CAMBIO_LOSE_EUR, TIP_PARAM_DETALLE);
    return listactaPerdidasEur.get(0).getNomValor();
  }

  private String ctaPerdidasEurUSD() throws SyncconException {
    List<Parametro> listactaPerdidasEur = getListaGenerica(Constantes.MDP_DIF_CAMBIO_LOSE_EUR_USD, TIP_PARAM_DETALLE);
    return listactaPerdidasEur.get(0).getNomValor();
  }

  private String ctaPerdidasPen() throws SyncconException {
    List<Parametro> listactaPerdidasPen = getListaGenerica(Constantes.MDP_DIF_CAMBIO_LOSE_PEN, TIP_PARAM_DETALLE);
    return listactaPerdidasPen.get(0).getNomValor();
  }

  private String ctaPerdidasUSD() throws SyncconException {
    List<Parametro> listactaPerdidasUsd = getListaGenerica(Constantes.MDP_DIF_CAMBIO_LOSE_USD, TIP_PARAM_DETALLE);
    return listactaPerdidasUsd.get(0).getNomValor();
  }

  private String ctaRetencionesDolares() throws SyncconException {
    List<Parametro> listactaGananciasPen = getListaGenerica(Constantes.MDP_PAG_RETENCION_CTA_USD, TIP_PARAM_DETALLE);
    return listactaGananciasPen.get(0).getNomValor();
  }

  private String ctaRetencionesSoles() throws SyncconException {
    List<Parametro> listactaGananciasPen = getListaGenerica(Constantes.MDP_PAG_RETENCION_CTA_PEN, TIP_PARAM_DETALLE);
    return listactaGananciasPen.get(0).getNomValor();
  }

  public String enviartoServiceSun(String Payload) {
    logger.info("Inicio");
    String ResultadoXml = componentes.ejecutaConsulta(ConstantesSun.SSC_ComponenteJournal, ConstantesSun.SSC_MetodoLoadAndPost, Payload);
    logger.info("Respuesta Enviar SUN: " + ResultadoXml);
    logger.info("Fin");
    return ResultadoXml;
  }

  private String envioSun(String codigoMoneda, List<AsientoContableBean> lista2, String MetodoEjecutado, String tipoPago) {
    try {
      logger.info("EnvioSun");
      String xmlGeneradotoSun = utiles.enviarSun(lista2, MetodoEjecutado, codigoMoneda, parametroService, tipoPago);
      logger.info("El Payload para enviar a SUN es : " + xmlGeneradotoSun);
      logger.info("Fin");
      return enviartoServiceSun(xmlGeneradotoSun);
    } catch (Exception ex) {
      // ex.printStackTrace();
      logger.error(ex);
      return ex.getMessage();
    }
  }

  public com.cardif.sunsystems.mapeo.direccion.SSC.Payload.Address extraerAddress(String addressCode) throws Exception {
    String Payload = null;
    com.cardif.sunsystems.mapeo.direccion.SSC.Payload.Address direccion = null;
    Payload = generarPayloadAddress(addressCode);
    logger.info("Extraccion Address: " + Payload);
    String ResultadoXml = componentes.ejecutaConsulta(ConstantesSun.SSC_ComponenteAddress, ConstantesSun.SSC_MetodoQuery, Payload);
    logger.info("Resultado Address: " + ResultadoXml);
    direccion = utiles.LeerXmlAddress(ResultadoXml);
    return direccion;
  }

  public com.cardif.sunsystems.mapeo.detalleBanco.SSC.Payload.BankDetails extraerBankDetails(String bankDetailsCode) throws Exception {
    String Payload = null;
    com.cardif.sunsystems.mapeo.detalleBanco.SSC.Payload.BankDetails bankDetail = null;
    try {
      Payload = generarPayloadBankDetails(bankDetailsCode);
      logger.info("Extraccion Bank Details: " + Payload);
      String ResultadoXml = componentes.ejecutaConsulta(ConstantesSun.SSC_ComponenteBankDetails, ConstantesSun.SSC_MetodoQuery, Payload);
      logger.info("Respuesta Bank Details: " + ResultadoXml);
      bankDetail = utiles.LeerXmlBankDetails(ResultadoXml);
    } catch (Exception e) {
      logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
      logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
    }
    return bankDetail;
  }

  public String extraerChequesSun(String Payload) throws Exception {
    logger.info("Inicio");
    String ResultadoXml = componentes.ejecutaConsulta(ConstantesSun.SSC_ComponenteJournal, ConstantesSun.SSC_MetodoQuery, Payload);
    logger.info("Fin");
    return ResultadoXml;
  }

  public List<List<com.cardif.sunsystems.mapeo.pagos.SSC.Payload.Ledger.Line>> extraerCuentas(String tipoCuenta, String AccountCodeMin, String AccountCodeMax, String AccountingPeriodMin,
      String AccountingPeriodMax) throws Exception {
    String Payload = null;
    List<List<com.cardif.sunsystems.mapeo.pagos.SSC.Payload.Ledger.Line>> lista = null;
    Payload = generarPayload(AccountingPeriodMin, AccountingPeriodMax, AccountCodeMin, AccountCodeMax, ConstantesSun.SSC_Debit_Credit, ConstantesSun.SSC_AllocationMarker_P,
        ConstantesSun.SSC_AllocationMarker_W, ConstantesSun.SSC_AnalysisCode9, tipoCuenta);
    logger.info("Extracción Extracción Cuentas: " + Payload);
    String ResultadoXml = componentes.ejecutaConsulta(ConstantesSun.SSC_ComponenteJournal, ConstantesSun.SSC_MetodoQuery, Payload);
    logger.info("Respuesta Extracción Cuentas: " + ResultadoXml);
    lista = utiles.LeerXmlPagos(ResultadoXml);
    return lista;
  }

  public List<com.cardif.sunsystems.mapeo.cuentaBancaria.SSC.Payload.Accounts> extraerCuentasBanco() {
    String Payload = null;
    String ResultadoXml;
    HashMap<String, Object> respuesta = new HashMap<String, Object>();
    List<com.cardif.sunsystems.mapeo.cuentaBancaria.SSC.Payload.Accounts> lista = null;
    try {
      Payload = generarPayloadCuentasBancarias();
      ResultadoXml = componentes.ejecutaConsulta(ConstantesSun.SSC_ComponenteAccounts, ConstantesSun.SSC_MetodoQuery, Payload);
      lista = utiles.leerXmlCuentaBancaria(ResultadoXml);
      if (lista.size() > 0) {
        respuesta.put("codigo", "0");
      }
    } catch (Exception e) {
      logger.error(e);
      respuesta.put("codigo", "9");
    }

    return lista;
  }

  public List<com.cardif.sunsystems.mapeo.proveedor.SSC.Payload.Supplier> extraerProveedor(String lookupCode) throws Exception {
    String Payload = null;
    List<com.cardif.sunsystems.mapeo.proveedor.SSC.Payload.Supplier> proveedores = null;
    Payload = generarPayloadProveedor(lookupCode);
    logger.info("Extraccion Proveedor: " + Payload);
    String ResultadoXml = componentes.ejecutaConsulta(ConstantesSun.SSC_ComponenteSupplier, ConstantesSun.SSC_MetodoQuery, Payload);
    logger.info("Resultado Proveedor: " + ResultadoXml);
    proveedores = utiles.LeerXmlProveedor(ResultadoXml);
    return proveedores;
  }

  public HashMap<String, BigDecimal> extraerTipoCambio(String fechaProceso) throws ParserConfigurationException, SAXException, IOException, TransformerFactoryConfigurationError, TransformerException {
    logger.info("Extrayendo cambio");
    String Payload = null;
    String ResultadoXml = null;
    List<com.cardif.sunsystems.mapeo.currencyDailyRates.SSC.Payload.DailyConversionRate> lista;
    HashMap<String, BigDecimal> respuesta = new HashMap<String, BigDecimal>();
    Payload = generarPayloadTipoCambio(fechaProceso);
    try {
      ResultadoXml = componentes.ejecutaConsulta(ConstantesSun.SSC_ComponenteCurrencyDailyRates, ConstantesSun.SSC_MetodoQuery, Payload);
      logger.info("Obtener Tipo de Cambio: " + Payload);

      logger.info("Respuesta Tipo de Cambio: " + ResultadoXml);
    } catch (Exception e) {
      logger.info("No se pueden obtener los tipos de cambio");
      return null;
    }

    lista = utiles.leerXmlTipoCambio(ResultadoXml);

    if (lista.size() > 0) {
      respuesta.put("eur-pen", Utilidades.redondear(4, new BigDecimal(utiles.tasaCambio("EUR", "PEN", lista))));
      respuesta.put("usd-pen", Utilidades.redondear(4, new BigDecimal(utiles.tasaCambio("USD", "PEN", lista))));
      respuesta.put("eur-usd", Utilidades.redondear(4, new BigDecimal(utiles.tasaCambio("EUR", "USD", lista))));

      respuesta.put("codigo", new BigDecimal("0"));
    } else {
      respuesta.put("codigo", new BigDecimal("9"));

    }

    return respuesta;
  }

  public List<AsientoContableBean> generaAsientosContables(List<List<JournalBean>> listaProcesos, String tipoPago, Date fechaPago, float tasaEurosSoles, float tasaEurosDolares, float tasaDolaresSoles,
      String ctaBanco, ParametroService parametroService2, AsientoContableSecuencialMapper asientoMaper, String glosaPago, String referencia, String medioPagoAnalisisCode10, Date fecTransaccion)
      throws SyncconException {
    List<AsientoContableBean> lista2 = null;
    this.parametroService = parametroService2;
    this.asientoMaper = asientoMaper;
    String periodo = "0" + formatPer.format(fecTransaccion);

    if (tipoPago.equals(ConstantesSun.PAGOS_VARIOS) || tipoPago.equals(ConstantesSun.PAGOS_SINIESTROS)) {

      List<AsientoContableBean> lista = null;
      lista2 = new ArrayList<AsientoContableBean>();
      BigDecimal suma_soles = null;
      BigDecimal suma_euros = null;
      BigDecimal suma_trans = null;
      AsientoContableBean ultimo_elemento = null;
      String provEmpleado = "";
      String ctaProveedor = "";

      String codMoneda = listaProcesos.get(0).get(0).getMoneda();

      // Cargar cuentas de DB:
      String ctaPropinas = getctaPropinas();
      String ctaGanancias = null, ctaPerdidas = null, ctaGananciasEur = null, ctaPerdidasEur = null;
      if (codMoneda.equalsIgnoreCase("PEN") || codMoneda.equalsIgnoreCase(Constantes.COD_MONEDA_SOLES)) {
        ctaGanancias = ctaGananciasPen();
        ctaPerdidas = ctaPerdidasPen();
        ctaGananciasEur = ctaGananciasEur();
        ctaPerdidasEur = ctaPerdidasEur();
      } else if (codMoneda.equalsIgnoreCase("USD") || codMoneda.equalsIgnoreCase(Constantes.COD_MONEDA_DOLAR)) {
        ctaGanancias = ctaGananciasUSD();
        ctaPerdidas = ctaPerdidasUSD();
        ctaGananciasEur = ctaGananciasEurUSD();
        ctaPerdidasEur = ctaPerdidasEurUSD();
      }

      if (tipoPago.equals(ConstantesSun.PAGOS_VARIOS)) {
        tipoDiario = tipoDiarioPagosVarios();
      } else {
        tipoDiario = tipoDiarioSiniestros();
      }

      for (List<JournalBean> ab : listaProcesos) {

        suma_soles = new BigDecimal("0.00");
        suma_euros = new BigDecimal("0.00");
        suma_trans = new BigDecimal("0.00");
        for (JournalBean bean : ab) {

          lista = utiles.generaAsientoPagosVarios(getLineFromBean(bean, tasaDolaresSoles, tasaEurosSoles, tasaEurosDolares, medioPagoAnalisisCode10), fechaPago, ctaPropinas, ctaGanancias, ctaPerdidas,
              ctaGananciasEur, ctaPerdidasEur, ctaBanco, fecTransaccion, tasaEurosSoles, glosaPago, tipoPago);

          int ultimo_id = lista.size() - 1;
          ultimo_elemento = lista.get(ultimo_id);

          if (ultimo_elemento.getGlosa().equals("LINEA FICTICIA")) {
            suma_soles = suma_soles.add(ultimo_elemento.getImporteSoles());
            suma_euros = suma_euros.add(ultimo_elemento.getImporteEuros());
            suma_trans = suma_trans.add(ultimo_elemento.getImporteTransaccion());
            provEmpleado = ultimo_elemento.getProveedorEmpleado() + "";
            ctaProveedor = ultimo_elemento.getCtaProveedor();
          }
          if (lista.remove(ultimo_elemento)) {
            logger.info("Exitoso eliminacion de FICTICIO");
          }
          lista2.addAll(lista);
        }

        JournalBean ultimo_asiento = listaProcesos.get(0).get(0);
        AsientoContableBean beanLista = null;
        if (suma_trans.doubleValue() != 0.0) {
          beanLista = new AsientoContableBean();
          beanLista.setLayout("1;2;3");
          beanLista.setCuentaContable(ctaBanco);

          // Libro
          beanLista.setNombreLibro("A");
          // Fecha Transaccion (Fecha Proceso)
          beanLista.setFecha(fecTransaccion);
          // periodo
          beanLista.setPeriodo(periodo);
          // Glosa
          beanLista.setGlosa(lista.get(0).getGlosa());

          // Referencia / Nro Documento Cheque
          beanLista.setReferencia("");

          beanLista.setReferencia(referencia);
          // Importe Transaccion
          beanLista.setImporteTransaccion(suma_trans.negate());
          // Importe Soles
          beanLista.setImporteSoles(suma_soles.negate());
          // Importe Euros
          beanLista.setImporteEuros(suma_euros.negate());
          // Marcador debito/credito
          if (beanLista.getImporteTransaccion().doubleValue() >= 0)
            beanLista.setMarcadorDC("D");
          else
            beanLista.setMarcadorDC("C");

          if (ctaBanco.substring(0, 3).equals("101")) {
            // Moneda
            beanLista.setMoneda("PEN");
          } else {
            // Moneda
            beanLista.setMoneda("USD");
          }

          // Centro de Costos

          beanLista.setCentroCosto(ultimo_asiento.getCentroCosto());
          // Socio producto
          beanLista.setSocioProducto(ultimo_asiento.getSocioProducto());
          // Canal
          beanLista.setCanal(ultimo_asiento.getCanal());
          // Proveedor/Empleado
          beanLista.setProveedorEmpleado(provEmpleado);
          // beanLista.setProveedorEmpleado(ultimo_asiento.getCanal());
          // RUC/DNI cliente
          // ******************************************************
          beanLista.setRucDniCliente(ultimo_asiento.getRucDniCliente());
          // Poliza del cliente
          beanLista.setPolizaCliente(ultimo_asiento.getPolizaCliente());
          // Inversiones
          beanLista.setInversiones(ultimo_asiento.getInversiones());
          // Nro Siniestro
          beanLista.setNroSiniestro(ultimo_asiento.getNroSiniestro());
          // Tipo comp. SUNAT
          beanLista.setTipoComprobanteSunat(lista.get(0).getTipoComprobanteSunat());
          // Impuesto IGV ******************************************
          // medioPagoAnalisisCode10
          beanLista.setTipoMedioPago(medioPagoAnalisisCode10);
          // Fecha vencimiento
          beanLista.setFechaVencimiento(fechaPago);
          // Numero de Constancia
          beanLista.setNroConstancia(null);

          // Cuenta Proveedor *********************************
          beanLista.setCtaProveedor(ctaProveedor + "");
          lista2.add(beanLista);
        }

        lista2.add(new AsientoContableBean());
      }
      lista2.remove(lista2.size() - 1);
      return lista2;

    } else if (tipoPago.equals(ConstantesSun.PAGOS_DETRACCION)) {
      logger.info("Es una detracción INDIVIDUAL");
      logger.info("");
      List<AsientoContableBean> lista = null;
      Date fecha_actual = (Date) fecTransaccion.clone();
      lista2 = new ArrayList<AsientoContableBean>();

      // Cargar cuentas de DB:
      String codMoneda = listaProcesos.get(0).get(0).getMoneda();
      String ctaComision = getCtaComision();
      if (codMoneda.equalsIgnoreCase("PEN") || codMoneda.equalsIgnoreCase(Constantes.COD_MONEDA_SOLES)) {
        tipoDiario = tipoDiarioDetraccionPEN();
      } else if (codMoneda.equalsIgnoreCase("USD") || codMoneda.equalsIgnoreCase(Constantes.COD_MONEDA_DOLAR)) {
        tipoDiario = tipoDiarioDetraccionUSD();
      }

      for (List<JournalBean> ab : listaProcesos) {
        for (JournalBean sublista : ab) {
          lista = utiles.generaAsientoDetracciones(getLineFromBean(sublista, medioPagoAnalisisCode10), fechaPago, ctaBanco, fecha_actual, ctaComision, glosaPago);
          lista2.addAll(lista);
        }
        lista2.add(new AsientoContableBean());
      }

      lista2.remove(lista2.size() - 1);
      String glosaIndivid = lista2.get(0).getGlosa();
      List<Integer> listaRemover = new ArrayList<Integer>();

      for (AsientoContableBean beanAsientoContable : lista2) {
        if (beanAsientoContable.getCuentaContable() == null) {
          listaRemover.add(listaRemover.indexOf(beanAsientoContable));
          continue;
        }

        if (beanAsientoContable.getCuentaContable().startsWith("10")) {

          if (lista2.get(0).getMoneda().equals("USD")) {
            beanAsientoContable.setImporteTransaccion(beanAsientoContable.getImporteSoles());
          }

          String glosaAux = "PAGO DETRACCION " + beanAsientoContable.getReferencia();
          beanAsientoContable.setGlosa(glosaAux);

          beanAsientoContable.setReferencia(opBanco);
        }
        beanAsientoContable.setGlosa(glosaIndivid);

      }
      for (int i = 0; i < listaRemover.size(); i++) {
        lista2.remove(listaRemover.get(i));
      }

      return lista2;
    } else if (tipoPago.equals(ConstantesSun.PAGOS_DETRACCION_MASIVA)) {
      logger.info("Es una detraccion MASIVA");
      List<AsientoContableBean> lista = null;
      Date fecha_actual = (Date) fecTransaccion.clone();

      // List<BeanAsientoContable>
      lista2 = new ArrayList<AsientoContableBean>();
      BigDecimal suma_soles = null;
      BigDecimal suma_euros = null;
      BigDecimal suma_trans = null;
      String ctaprov = "";
      String ctacom = "";
      String numeroConstancia = "";
      AsientoContableBean ultimo_elemento = null;
      BigDecimal tceuro = new BigDecimal(tasaEurosDolares);

      // Cargar cuentas:
      String codMoneda = listaProcesos.get(0).get(0).getMoneda();
      if (codMoneda.equalsIgnoreCase("PEN") || codMoneda.equalsIgnoreCase(Constantes.COD_MONEDA_SOLES)) {
        tipoDiario = tipoDiarioDetraccionPEN();
      } else if (codMoneda.equalsIgnoreCase("USD") || codMoneda.equalsIgnoreCase(Constantes.COD_MONEDA_DOLAR)) {
        tipoDiario = tipoDiarioDetraccionUSD();
      }

      //
      for (List<JournalBean> ab : listaProcesos) {
        suma_soles = new BigDecimal("0.00");
        suma_euros = new BigDecimal("0.00");
        suma_trans = new BigDecimal("0.00");
        for (JournalBean sublista : ab) {
          lista = utiles.generaAsientoDetraccionesMasiva(getLineFromBean(sublista, medioPagoAnalisisCode10), fechaPago, fecha_actual, ctaprov, ctacom, tceuro, glosaPago);

          int ultimo_id = lista.size() - 1;
          ultimo_elemento = lista.get(ultimo_id);
          if (ultimo_elemento.getGlosa().equals("LINEA FICTICIA")) {
            suma_soles = suma_soles.add(ultimo_elemento.getImporteSoles());
            suma_euros = suma_euros.add(ultimo_elemento.getImporteEuros());
            suma_trans = suma_trans.add(ultimo_elemento.getImporteTransaccion());
            ctacom = ultimo_elemento.getCodActivoFijo();
            ctaprov = ultimo_elemento.getCtaProveedor();
            numeroConstancia = ultimo_elemento.getNroConstancia();
          }
          if (lista.remove(ultimo_elemento)) {
            logger.info("Exitoso eliminacion de FICTICIO");
          }
          lista2.addAll(lista);

        }
      }

      lista2 = utiles.ultimasLineasDetraccionesMasivas(lista2, ctaBanco, fecha_actual, fechaPago, ctaprov, suma_trans, suma_soles, suma_euros, getCtaComision(), new BigDecimal(getImpComision()),
          getCentroCosto(), tceuro, ctacom, numeroConstancia);
      lista2.add(new AsientoContableBean());

      lista2.remove(lista2.size() - 1);
      List<Integer> listaRemover = new ArrayList<Integer>();
      for (AsientoContableBean beanAsientoContable : lista2) {
        if (beanAsientoContable.getCuentaContable() == null) {
          listaRemover.add(listaRemover.indexOf(beanAsientoContable));
          continue;
        }
        if (beanAsientoContable.getCuentaContable().startsWith("10")) {

          if (lista2.get(0).getMoneda().equals("USD")) {
            beanAsientoContable.setImporteTransaccion(beanAsientoContable.getImporteSoles());
          }
        }

        String glosaAux = "", ref = "";
        if (beanAsientoContable.getGlosa().contains("PAGO DETRACCION") && !beanAsientoContable.getCuentaContable().startsWith("P")) {
          glosaAux = "PAGO DETRACCION LOTE " + nroLote + "-" + opBanco;
          ref = nroLote + "-" + opBanco;
        } else if (beanAsientoContable.getGlosa().contains("PAGO COMISION")) {
          glosaAux = "PAGO COMISION DETRACCION LOTE " + nroLote + "-" + opComision;
          ref = nroLote + "-" + opComision;
        }

        if (beanAsientoContable.getCuentaContable().startsWith("P")) {
          glosaAux = "PAGO DETRACCION " + beanAsientoContable.getReferencia();
          ref = beanAsientoContable.getReferencia();
        }
        beanAsientoContable.setGlosa(glosaAux);
        beanAsientoContable.setReferencia(ref);
      }
      for (int i = 0; i < listaRemover.size(); i++) {
        lista2.remove(listaRemover.get(i));
      }
      return lista2;
    } else if (tipoPago.equals(ConstantesSun.PAGOS_RETENCION)) {
      List<AsientoContableBean> lista = null;
      lista2 = new ArrayList<AsientoContableBean>();
      BigDecimal suma_soles = new BigDecimal("0.00");
      BigDecimal suma_euros = new BigDecimal("0.00");
      BigDecimal suma_trans = new BigDecimal("0.00");
      BigDecimal suma_soles_2 = new BigDecimal("0.00");
      BigDecimal suma_euros_2 = new BigDecimal("0.00");
      BigDecimal suma_trans_2 = new BigDecimal("0.00");
      // BigDecimal pctRetencion = new BigDecimal("0.00");
      BigDecimal suma_soles_ret = new BigDecimal("0.00");
      BigDecimal suma_euros_ret = new BigDecimal("0.00");
      BigDecimal suma_trans_ret = new BigDecimal("0.00");

      // retencion
      BigDecimal suma_soles_retenido = new BigDecimal("0.00");
      BigDecimal suma_euros_retenido = new BigDecimal("0.00");
      BigDecimal suma_trans_retenido = new BigDecimal("0.00");

      AsientoContableBean ultimo_elemento = null;

      String nroCertifiadoRetencion = "";
      Integer nroCorrelativoRetencion = 0;

      // Cargando cuentas:
      String codMoneda = listaProcesos.get(0).get(0).getMoneda();

      // Cargar cuentas de DB:
      String ctaPropinas = getctaPropinas();
      String ctaGanancias = null, ctaPerdidas = null, ctaGananciasEur = null, ctaPerdidasEur = null, ctaRetenciones = null;
      if (codMoneda.equalsIgnoreCase("PEN") || codMoneda.equalsIgnoreCase(Constantes.COD_MONEDA_SOLES)) {
        ctaGanancias = ctaGananciasPen();
        ctaPerdidas = ctaPerdidasPen();
        ctaGananciasEur = ctaGananciasEur();
        ctaPerdidasEur = ctaPerdidasEur();
        ctaRetenciones = ctaRetencionesSoles();
        tipoDiario = tipoDiarioRetenPEN();
      } else if (codMoneda.equalsIgnoreCase("USD") || codMoneda.equalsIgnoreCase(Constantes.COD_MONEDA_DOLAR)) {
        ctaGanancias = ctaGananciasUSD();
        ctaPerdidas = ctaPerdidasUSD();
        ctaGananciasEur = ctaGananciasEurUSD();
        ctaPerdidasEur = ctaPerdidasEurUSD();
        ctaRetenciones = ctaRetencionesDolares();
        tipoDiario = tipoDiarioRetenUSD();
      }
      //
      Parametro parametro = parametroService.obtener(ConstantesSun.COD_PARAM_PORCENT_CALCULO, Constantes.TIP_PARAM_DETALLE, ConstantesSun.COD_VALOR_TASA_RETENCION);
      float porcentaRetencion = Float.parseFloat(parametro.getNomValor()) / 100;

      for (List<JournalBean> ab : listaProcesos) {
        suma_soles = new BigDecimal("0.00");
        suma_euros = new BigDecimal("0.00");
        suma_trans = new BigDecimal("0.00");

        // Variables para monto Retenido
        suma_soles_retenido = new BigDecimal("0.00");
        suma_euros_retenido = new BigDecimal("0.00");
        suma_trans_retenido = new BigDecimal("0.00");
        // fin variables Retendido

        String monedaRetencion = "";
        String ProveedorEmpleado = "";
        String centroCosto = "";
        String socioProducto = "";
        String ruc = "";
        String comprobSunat = "";
        String NumeroConstancia = "";
        String CtaProveedor = "";

        FacesContext context = FacesContext.getCurrentInstance();
        Object correlativoSession = context.getExternalContext().getSessionMap().get(ConstantesSun.MDP_FEL_CORRELATIVO_GENERADO);

        // Validamos si existe un numero ya generado para reutilizarlo.
        if (correlativoSession == null) {
          nroCorrelativoRetencion = incrementarCorrelativoSeguros();
          context.getExternalContext().getSessionMap().put(ConstantesSun.MDP_FEL_CORRELATIVO_GENERADO, nroCorrelativoRetencion);
        } else {
          nroCorrelativoRetencion = (Integer) correlativoSession;
        }

        // Preparamos el numero de certificado concatenando la serie y el secuencial
        nroCertifiadoRetencion = ConstantesSun.FEL_SERIE_RETENCION_DIGITAL + Utilidades.completarCeros(String.valueOf(nroCorrelativoRetencion), ConstantesSun.FEL_CDP_LONG_CORR);

        for (JournalBean sublista : ab) {
          if (logger.isDebugEnabled()) {
            logger.debug("Nro Correlativo Retencion a usar : " + nroCorrelativoRetencion);
          }

          com.cardif.sunsystems.mapeo.pagos.SSC.Payload.Ledger.Line tmp;
          tmp = utiles.procesarLineasRetenciones(sublista, nroCertifiadoRetencion, tasaEurosSoles, tasaDolaresSoles, tasaEurosDolares, medioPagoAnalisisCode10);

          lista = utiles.generaAsientoRetenciones(tmp, fechaPago, ctaPropinas, ctaGanancias, ctaPerdidas, ctaGananciasEur, ctaPerdidasEur, fecTransaccion, tasaEurosSoles, glosaPago);

          int ultimo_id = lista.size() - 1;
          ultimo_elemento = lista.get(ultimo_id);
          if (ultimo_elemento.getGlosa().equals("LINEA FICTICIA")) {
            suma_soles = suma_soles.add(ultimo_elemento.getImporteSoles());
            suma_euros = suma_euros.add(ultimo_elemento.getImporteEuros());
            suma_trans = suma_trans.add(ultimo_elemento.getImporteTransaccion());

            // Cambio Cesar : Monto retenido
            suma_soles_retenido = suma_soles_retenido.add(Utilidades.redondear(2, ultimo_elemento.getImporteSoles().multiply(new BigDecimal(String.valueOf(porcentaRetencion)))));
            suma_euros_retenido = suma_euros_retenido.add(Utilidades.redondear(2, ultimo_elemento.getImporteEuros().multiply(new BigDecimal(String.valueOf(porcentaRetencion)))));
            suma_trans_retenido = suma_trans_retenido.add(Utilidades.redondear(2, ultimo_elemento.getImporteTransaccion().multiply(new BigDecimal(String.valueOf(porcentaRetencion)))));

            suma_trans_2 = suma_trans_2.add(new BigDecimal(ultimo_elemento.getRefImpTrans()));
            suma_euros_2 = suma_euros_2.add(new BigDecimal(ultimo_elemento.getRefImpEuros()));
            suma_soles_2 = suma_soles_2.add(new BigDecimal(ultimo_elemento.getRefImpSoles()));
            monedaRetencion = ultimo_elemento.getMoneda();

            ProveedorEmpleado = ultimo_elemento.getProveedorEmpleado();
            centroCosto = ultimo_elemento.getCentroCosto();
            socioProducto = ultimo_elemento.getSocioProducto();
            ruc = ultimo_elemento.getRucDniCliente();
            comprobSunat = ultimo_elemento.getTipoComprobanteSunat();
            NumeroConstancia = ultimo_elemento.getNroConstancia();
            CtaProveedor = ultimo_elemento.getCtaProveedor();
          }
          if (lista.remove(ultimo_elemento)) {
            logger.info("Exitoso eliminacion de FICTICIO");
          }
          lista2.addAll(lista);

        }

        List<AsientoContableBean> lista_11 = new ArrayList<AsientoContableBean>();
        JournalBean consultaLimpia = new JournalBean();
        consultaLimpia.setProveedorEmpleado(ProveedorEmpleado);

        consultaLimpia.setCentroCosto(centroCosto);
        consultaLimpia.setSocioProducto(socioProducto);
        consultaLimpia.setRucDniCliente(ruc);
        consultaLimpia.setComprobanteSunat(comprobSunat);

        consultaLimpia.setNumeroConstancia(NumeroConstancia);
        consultaLimpia.setCodigoCuenta(CtaProveedor);
        consultaLimpia.setGlosa(lista2.get(0).getGlosa());
        consultaLimpia.setReferencia(referencia);
        AsientoContableBean beanLista = null;

        if (suma_trans.doubleValue() != 0.0) {
          BigDecimal porcentaje_retencion = new BigDecimal(getPctRetencion()).divide(new BigDecimal("100.00"));
          if (suma_soles_2.doubleValue() > Double.parseDouble(getMontoMinimoRetencion())) {
            beanLista = new AsientoContableBean();
            beanLista.setLayout("1;2;3");
            if (monedaRetencion.equals("PEN")) {
              beanLista.setCuentaContable(ctaRetencionesSoles());
            } else {
              beanLista.setCuentaContable(ctaRetencionesDolares());
            }

            // Libro
            beanLista.setNombreLibro("A");
            // Fecha Transaccion (Fecha Proceso)
            beanLista.setFecha(fecTransaccion);
            // periodo
            beanLista.setPeriodo(periodo);
            // Glosa
            beanLista.setGlosa(consultaLimpia.getGlosa());
            // Referencia / Nro Documento Cheque
            beanLista.setReferencia(consultaLimpia.getReferencia());
            // Importe Transaccion

            beanLista.setImporteTransaccion(new BigDecimal(consultaLimpia.getImpTransaccionAmount()).multiply(porcentaje_retencion).negate());
            // Importe Soles
            beanLista.setImporteSoles(new BigDecimal(consultaLimpia.getImpSoles()).multiply(porcentaje_retencion).negate());
            // Importe Euros
            beanLista.setImporteEuros(new BigDecimal(consultaLimpia.getImpEuros()).multiply(porcentaje_retencion).negate());
            // Marcador debito/credito
            if (beanLista.getImporteTransaccion().doubleValue() >= 0)
              beanLista.setMarcadorDC("D");
            else
              beanLista.setMarcadorDC("C");
            // Moneda
            beanLista.setMoneda(monedaRetencion);

            // Centro de Costos
            beanLista.setCentroCosto(consultaLimpia.getCentroCosto());
            // Socio producto
            beanLista.setSocioProducto(consultaLimpia.getSocioProducto());
            // Canal
            beanLista.setCanal(consultaLimpia.getCanal());
            // Proveedor/Empleado
            //
            beanLista.setProveedorEmpleado(consultaLimpia.getProveedorEmpleado());
            // RUC/DNI cliente
            beanLista.setRucDniCliente(consultaLimpia.getRucDniCliente());
            // Poliza del cliente
            beanLista.setPolizaCliente(consultaLimpia.getPolizaCliente());
            // Inversiones
            beanLista.setInversiones(consultaLimpia.getInversiones());
            // Nro Siniestro
            beanLista.setNroSiniestro(consultaLimpia.getNroSiniestro());
            // Tipo comp. SUNAT
            beanLista.setTipoComprobanteSunat(consultaLimpia.getComprobanteSunat());
            // Impuesto IGV
            beanLista.setTipoMedioPago(medioPagoAnalisisCode10);
            // Fecha vencimiento
            beanLista.setFechaVencimiento(fechaPago);
            // Numero de Constancia
            //
            beanLista.setNroConstancia(consultaLimpia.getNumeroConstancia());

            // Cuenta Proveedor
            //
            beanLista.setCtaProveedor(CtaProveedor);

            suma_soles_ret = beanLista.getImporteSoles().add(suma_soles_ret);
            suma_euros_ret = beanLista.getImporteEuros().add(suma_euros_ret);
            suma_trans_ret = beanLista.getImporteTransaccion().add(suma_trans_ret);

          }
          if (consultaLimpia.getImpSoles() > Double.parseDouble(getMontoMinimoRetencion())) {
            lista_11.add(beanLista);
          }

          // CTA 20
          /************ LA LINEA DEL 3% (CTA 20) *****************/
          // INICIO SYNCCON JARIASS 19/01/2015
          beanLista = new AsientoContableBean();

          beanLista.setLayout("1;2;3");

          // Libro
          beanLista.setNombreLibro("A");
          // Fecha Transaccion (Fecha Proceso)
          beanLista.setFecha(fecTransaccion);
          // periodo
          beanLista.setPeriodo(periodo);
          // Glosa
          beanLista.setGlosa(lista.get(0).getGlosa());
          // Referencia / Nro Documento Cheque
          beanLista.setReferencia(consultaLimpia.getReferencia());

          // Cambio en el calculo de Retenciones 18/07/2016 - Cesar
          /*
           * beanLista.setImporteTransaccion(suma_trans.multiply(new BigDecimal("0.03")).abs().negate());
           * beanLista.setImporteSoles(suma_soles.multiply(new BigDecimal("0.03")).abs().negate()); beanLista.setImporteEuros(suma_euros.multiply(new
           * BigDecimal("0.03")).abs().negate());
           */

          beanLista.setImporteTransaccion(suma_trans_retenido.negate());
          beanLista.setImporteSoles(suma_soles_retenido.negate());
          beanLista.setImporteEuros(suma_euros_retenido.negate());
          // Fin cambio Calculo Retencion

          // Marcador debito/credito
          if (beanLista.getImporteTransaccion().doubleValue() >= 0)
            beanLista.setMarcadorDC("D");
          else
            beanLista.setMarcadorDC("C");

          beanLista.setImporteTransaccion(Utilidades.redondear(2, beanLista.getImporteTransaccion()));
          beanLista.setImporteSoles(Utilidades.redondear(2, beanLista.getImporteSoles()));
          beanLista.setImporteEuros(Utilidades.redondear(2, beanLista.getImporteEuros()));

          suma_trans_ret = beanLista.getImporteTransaccion();
          suma_soles_ret = beanLista.getImporteSoles();
          suma_euros_ret = beanLista.getImporteEuros();

          // Moneda
          if (ctaBanco.substring(0, 3).equals("101")) {
            // Moneda
            beanLista.setMoneda("PEN");
          } else {
            // Moneda
            beanLista.setMoneda("USD");
          }
          beanLista.setCuentaContable(ctaRetenciones);
          // Cuenta Proveedor

        }

        // Centro de Costos
        beanLista.setCentroCosto(consultaLimpia.getCentroCosto());
        // Socio producto
        beanLista.setSocioProducto(consultaLimpia.getSocioProducto());
        // Canal
        beanLista.setCanal(consultaLimpia.getCanal());
        // Proveedor/Empleado
        beanLista.setProveedorEmpleado(consultaLimpia.getProveedorEmpleado());
        // RUC/DNI cliente
        beanLista.setRucDniCliente(consultaLimpia.getRucDniCliente());
        // Poliza del cliente
        beanLista.setPolizaCliente(consultaLimpia.getPolizaCliente());
        // Inversiones
        beanLista.setInversiones(consultaLimpia.getInversiones());
        // Nro Siniestro
        beanLista.setNroSiniestro(consultaLimpia.getNroSiniestro());
        // Tipo comp. SUNAT
        beanLista.setTipoComprobanteSunat(lista.get(0).getTipoComprobanteSunat());
        // Impuesto IGV
        // beanLista.setTipoMedioPago(consultaLimpia.getTipoMedioPago());
        beanLista.setTipoMedioPago(medioPagoAnalisisCode10);
        // Fecha vencimiento
        beanLista.setFechaVencimiento(fechaPago);
        // Numero de Constancia
        beanLista.setNroConstancia(consultaLimpia.getNumeroConstancia());

        // Numero de Constancia
        beanLista.setNroConstancia(consultaLimpia.getNumeroConstancia());

        // Cuenta Proveedor
        beanLista.setCtaProveedor(CtaProveedor);

        lista2.add(beanLista);
        // FIN SYNCCON JARIASS 19/01/2015

        // CTA 10
        beanLista = new AsientoContableBean();
        beanLista.setLayout("1;2;3");

        // Cuenta Proveedor
        beanLista.setCuentaContable(ctaBanco);
        // Libro
        beanLista.setNombreLibro("A");
        // Fecha Transaccion (Fecha Proceso)
        beanLista.setFecha(fecTransaccion);
        // periodo
        beanLista.setPeriodo(periodo);
        // Glosa
        beanLista.setGlosa(consultaLimpia.getGlosa());
        // Referencia / Nro Documento Cheque
        beanLista.setReferencia(consultaLimpia.getReferencia());

        // Importe Transaccion
        beanLista.setImporteTransaccion(suma_trans.add(suma_trans_ret).negate());
        // Importe Soles
        beanLista.setImporteSoles(suma_soles.add(suma_soles_ret).negate());
        // Importe Euros
        beanLista.setImporteEuros(suma_euros.add(suma_euros_ret).negate());
        // Marcador debito/credito
        if (beanLista.getImporteTransaccion().doubleValue() >= 0)
          beanLista.setMarcadorDC("D");
        else
          beanLista.setMarcadorDC("C");

        if (ctaBanco.substring(0, 3).equals("101")) {
          // Moneda
          beanLista.setMoneda("PEN");
        } else {
          // Moneda
          beanLista.setMoneda("USD");
        }
        // Centro de Costos
        beanLista.setCentroCosto(consultaLimpia.getCentroCosto());
        // Socio producto
        beanLista.setSocioProducto(consultaLimpia.getSocioProducto());
        // Canal
        beanLista.setCanal(consultaLimpia.getCanal());
        // Proveedor/Empleado
        beanLista.setProveedorEmpleado(consultaLimpia.getProveedorEmpleado());
        // RUC/DNI cliente
        beanLista.setRucDniCliente(consultaLimpia.getRucDniCliente());
        // Poliza del cliente
        beanLista.setPolizaCliente(consultaLimpia.getPolizaCliente());
        // Inversiones
        beanLista.setInversiones(consultaLimpia.getInversiones());
        // Nro Siniestro
        beanLista.setNroSiniestro(consultaLimpia.getNroSiniestro());
        // Tipo comp. SUNAT
        beanLista.setTipoComprobanteSunat(lista.get(0).getTipoComprobanteSunat());
        // Impuesto IGV
        // beanLista.setTipoMedioPago(consultaLimpia.getTipoMedioPago());
        beanLista.setTipoMedioPago(medioPagoAnalisisCode10);
        // Fecha vencimiento
        beanLista.setFechaVencimiento(fechaPago);
        // Numero de Constancia
        beanLista.setNroConstancia(consultaLimpia.getNumeroConstancia());

        // Cuenta Proveedor
        beanLista.setCtaProveedor(CtaProveedor);
        lista2.add(beanLista);
        lista2.add(new AsientoContableBean());
      }
      // Se añade?
      // lista2.addAll(lista_11);

    }

    return lista2;
  }

  private String generarPayload(String accountingPeriodMin, String accountingPeriodMax, String accountCodeMin, String accountCodeMax, String debitCredit, String allocationMarker,
      String allocationMarker2, String analysisCode9, String tipoPago) throws Exception {
    Document doc = null;
    if (tipoPago.equals(ConstantesSun.MDP_BUSCAR_RETENCION)) {
      doc = utiles.loadXMLFromResource("/com/cardif/sunsystems/plantillas/journalQueryINProvisionPago.xml");
    }

    NodeList items = doc.getElementsByTagName("Item");
    int cont = 0;
    for (int j = 0; j < items.getLength(); j++) {
      Node tmp = items.item(j);
      if (tmp.getNodeType() == Node.ELEMENT_NODE) {
        NamedNodeMap atributos = tmp.getAttributes();
        if (tipoPago.equals(ConstantesSun.MDP_BUSCAR_RETENCION)) {
          if (atributos.getNamedItem("name").getNodeValue().equals("/Ledger/Line/AccountingPeriod")) {
            atributos.getNamedItem("minvalue").setNodeValue(accountingPeriodMin);
            atributos.getNamedItem("maxvalue").setNodeValue(accountingPeriodMax);
          }

          if (atributos.getNamedItem("name").getNodeValue().equals("/Ledger/Line/AccountCode")) {
            if (accountCodeMin.equals("") || accountCodeMax.equals("")) {
              tmp.getParentNode().removeChild(tmp);
            } else {
              atributos.getNamedItem("minvalue").setNodeValue(accountCodeMin);
              atributos.getNamedItem("maxvalue").setNodeValue(accountCodeMax);
            }
          }
          if (atributos.getNamedItem("name").getNodeValue().equals("/Ledger/Line/AllocationMarker")) {
            if (cont == 0) {
              atributos.getNamedItem("value").setNodeValue(allocationMarker);
              cont++;
            } else {
              atributos.getNamedItem("value").setNodeValue(allocationMarker2);
              cont = 0;
            }

          }
          // if (atributos.getNamedItem("name").getNodeValue().equals("/Ledger/Line/DebitCredit")) {
          // atributos.getNamedItem("value").setNodeValue(debitCredit);
          // }
          if (atributos.getNamedItem("name").getNodeValue().equals("/Ledger/Line/AnalysisCode9")) {
            atributos.getNamedItem("value").setNodeValue(analysisCode9);
          }
        }
      }
    }
    return utiles.documenttoString(doc);
  }

  private String generarPayloadAddress(String addressCode) throws Exception {
    Document doc = null;

    doc = utiles.loadXMLFromResource("/com/cardif/sunsystems/plantillas/datosDireccion.xml");
    NodeList items = doc.getElementsByTagName("Item");

    for (int j = 0; j < items.getLength(); j++) {
      Node tmp = items.item(j);
      if (tmp.getNodeType() == Node.ELEMENT_NODE) {
        NamedNodeMap atributos = tmp.getAttributes();
        if (atributos.getNamedItem("name").getNodeValue().equals("/Address/AddressCode")) {
          atributos.getNamedItem("value").setNodeValue(addressCode);
        }

      }
    }
    return utiles.documenttoString(doc);
  }

  private String generarPayloadBankDetails(String bankDetailsCode) throws Exception {
    Document doc = null;
    doc = utiles.loadXMLFromResource("/com/cardif/sunsystems/plantillas/datosBankDetails.xml");
    NodeList tempNode2 = doc.getElementsByTagName("Item");
    for (int j = 0; j < tempNode2.getLength(); j++) {
      Node tmp = tempNode2.item(j);
      if (tmp.getNodeType() == Node.ELEMENT_NODE) {
        NamedNodeMap atributos = tmp.getAttributes();
        if (atributos.getNamedItem("name").getNodeValue().equals("/BankDetails/BankDetailsCode")) {
          atributos.getNamedItem("value").setNodeValue(bankDetailsCode);
        }
      }
    }
    return utiles.documenttoString(doc);
  }

  private String generarPayloadCuentasBancarias() throws Exception {
    Document doc = null;
    doc = utiles.loadXMLFromResource("/com/cardif/sunsystems/plantillas/cuentaBanco.xml");
    return utiles.documenttoString(doc);
  }

  private String generarPayloadProveedor(String lookupCode) throws Exception {
    Document doc = null;
    doc = utiles.loadXMLFromResource("/com/cardif/sunsystems/plantillas/datosProveedor.xml");
    NodeList tempNode2 = doc.getElementsByTagName("Item");

    for (int j = 0; j < tempNode2.getLength(); j++) {
      Node tmp = tempNode2.item(j);
      if (tmp.getNodeType() == Node.ELEMENT_NODE) {
        NamedNodeMap atributos = tmp.getAttributes();
        if (atributos.getNamedItem("name").getNodeValue().equals("/Supplier/LookupCode")) {
          atributos.getNamedItem("value").setNodeValue(lookupCode);
        }
      }
    }
    return utiles.documenttoString(doc);
  }

  private String generarPayloadTipoCambio(String fechaProceso) throws ParserConfigurationException, SAXException, IOException, TransformerFactoryConfigurationError, TransformerException {
    Document doc = null;
    doc = utiles.loadXMLFromResource("/com/cardif/sunsystems/plantillas/tipoCambio.xml");
    NodeList tempNode2 = doc.getElementsByTagName("Item");
    for (int j = 0; j < tempNode2.getLength(); j++) {
      Node tmp = tempNode2.item(j);
      if (tmp.getNodeType() == Node.ELEMENT_NODE) {
        NamedNodeMap atributos = tmp.getAttributes();
        if (atributos.getNamedItem("name").getNodeValue().equals("/DailyConversionRate/EffectiveDate")) {
          atributos.getNamedItem("value").setNodeValue(fechaProceso);
        }
      }
    }
    return utiles.documenttoString(doc);
  }

  private String getCentroCosto() throws SyncconException {
    List<Parametro> listactaGananciasPen = getListaGenerica(Constantes.MDP_CENT_COST_DETRAC_TRANS_COMISION, TIP_PARAM_DETALLE);
    return listactaGananciasPen.get(0).getNomValor();
  }

  private String getCtaComision() throws SyncconException {
    List<Parametro> listactaGananciasPen = getListaGenerica(Constantes.MDP_CTA_GASTO_DETRAC_TRANS_COMISION, TIP_PARAM_DETALLE);
    return listactaGananciasPen.get(0).getNomValor();
  }

  private String getctaPropinas() throws SyncconException {
    List<Parametro> listactaGananciasPen = getListaGenerica(Constantes.MDP_PAG_VARIOS_CTA_PROPINA, TIP_PARAM_DETALLE);
    return listactaGananciasPen.get(0).getNomValor();
  }

  private String getImpComision() throws SyncconException {
    List<Parametro> listactaGananciasPen = getListaGenerica(Constantes.MDP_PAG_DETRACCIONES_IMP_COMISION, TIP_PARAM_DETALLE);
    return listactaGananciasPen.get(0).getNomValor();
  }

  private com.cardif.sunsystems.mapeo.pagos.SSC.Payload.Ledger.Line getLineFromBean(JournalBean beanProcesos, float tasaDolaresSoles, float tasaEurosSoles, float tasaEurosDolares,
      String medioPagoAnalisisCode10) {
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

    fila = utiles.procesarLineasPagosVarios(fila, tasaEurosSoles, tasaDolaresSoles, tasaEurosDolares);
    return fila;
  }

  private Line getLineFromBean(JournalBean beanProcesos, String medioPagoAnalisisCode10) {
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
    fila.setAnalysisCode10(beanProcesos.getTipoMedioPago());
    fila.setAnalysisCode10(medioPagoAnalisisCode10);
    fila.setJournalNumber(beanProcesos.getDiario());
    fila.setJournalLineNumber(beanProcesos.getLineaDiario());
    fila.setNroCheque(beanProcesos.getNroCheque());
    fila.setNroConstancia(beanProcesos.getNumeroConstancia());
    fila.setRefCtaBancoProvision(beanProcesos.getRefCtaBancoProvision());
    fila.setRefCtaBancoComision(beanProcesos.getRefCtaBancoComision());
    return fila;
  }

  public List<Parametro> getListaGenerica(String codParamDifCambioLoseEur, String tipParamDetalle) throws SyncconException {
    List<Parametro> listagen = null;
    listagen = parametroService.buscar(codParamDifCambioLoseEur, tipParamDetalle);
    return listagen;
  }

  private String getMontoMinimoRetencion() throws SyncconException {
    List<Parametro> listactaGananciasPen = getListaGenerica(Constantes.MDP_PAG_RETENCION_MONTO_MIN, TIP_PARAM_DETALLE);
    return listactaGananciasPen.get(0).getNomValor();
  }

  public String getNroLote() {
    return nroLote;
  }

  public String getOpBanco() {
    return opBanco;
  }

  public String getOpComision() {
    return opComision;
  }

  private String getPctRetencion() throws SyncconException {
    List<Parametro> listactaGananciasPen = getListaGenerica(Constantes.MDP_PAG_RETENCION_IMP, TIP_PARAM_DETALLE);
    return listactaGananciasPen.get(0).getNomValor();
  }

  public String getTipoDiario() {
    return tipoDiario;
  }

  private String getUltimoCorrelativoSeguros() {
    return asientoMaper.ultimoSecuencialSeguros();
  }

  public String horaActual() {
    long tiempo = System.currentTimeMillis();
    return String.valueOf(tiempo);
  }

  private int incrementarCorrelativoSeguros() {
    AsientoContableSecuencial asiento = new AsientoContableSecuencial();
    asiento.setFechaAsiento(Calendar.getInstance().getTime());
    asientoMaper.insertar(asiento);
    return Integer.valueOf(getUltimoCorrelativoSeguros());
  }

  public String marcarLineasPagados(int diario, String lineaDiario, String marca) throws Exception {
    logger.info("Inicio");
    String payloadToSun = utiles.generarPayloadActualizarMarcadorSun(diario, lineaDiario, marca);
    logger.info("Actualiza envia SUN: " + payloadToSun);
    String ResultadoXml = componentes.ejecutaConsulta(ConstantesSun.SSC_AllocationMarkerUpdate, ConstantesSun.SSC_AmendMarker, payloadToSun);
    logger.info("Mensaje de respuesta de SUN Actualizar: " + ResultadoXml);
    logger.info("Fin");
    return null;
  }

  public String prepararEnvioSun(List<AsientoContableBean> asientoContable, String metodoEjecutado, HashMap<Integer, Object> resultados, ParametroService parametroService2, String tipoPago,
      String numCuentaBanco) {
    logger.info("Inicio");
    List<AsientoContableBean> lista2 = null;
    this.parametroService = parametroService2;
    AsientoContableBean bean;
    String Resultado;
    lista2 = asientoContable;
    String resultado = "VACIO";
    logger.info("metodoEjecutado : " + metodoEjecutado);
    if (metodoEjecutado.equals(ConstantesSun.PAGOS_DETRACCION) || metodoEjecutado.equals(ConstantesSun.PAGOS_DETRACCION_MASIVA)) {
      if (lista2.size() > 0) {
        Resultado = utiles.validarEnvioSun(asientoContable, resultados, numCuentaBanco);

        if (Resultado.equals("EnvioSUN")) {

          for (int i = 0; i < lista2.size(); i++) {
            bean = lista2.get(i);
            logger.info("IMPORTANTE:: Se envia el pago de : " + bean.getCtaProveedor()+", REFERENCIA: "+bean.getReferencia()+", NRO DETRACION :"+bean.getNroConstancia());
            bean.setRefImpSoles(bean.getImporteSoles().toString());
            bean.setRefImpTrans(bean.getImporteTransaccion().toString());
            bean.setRefImpEuros(bean.getImporteEuros().toString());
            lista2.set(i, bean);
          }
          if (lista2.get(0).getMoneda().equals("PEN")) {
            resultado = envioSun("PEN", lista2, metodoEjecutado, tipoPago);
          }
          if (lista2.get(0).getMoneda().equals("USD")) {
            resultado = envioSun("USD", lista2, metodoEjecutado, tipoPago);
          }
        } else {
          resultado = Resultado;
        }
      }
    } else if (metodoEjecutado.equals(ConstantesSun.PAGOS_VARIOS) || metodoEjecutado.equals(ConstantesSun.PAGOS_RETENCION) || metodoEjecutado.equals(ConstantesSun.PAGOS_SINIESTROS)) {
      if (lista2.size() > 0) {
        Resultado = utiles.validarEnvioSun(lista2, resultados, numCuentaBanco);

        if (Resultado.equals("EnvioSUN")) {
          logger.info("Entra a envioSun");
          for (int i = 0; i < lista2.size(); i++) {
            bean = lista2.get(i);
            logger.info("IMPORTANTE:: Se envia el pago de : " + bean.getCtaProveedor()+", REFERENCIA: "+bean.getReferencia()+", NRO RETENCION :"+bean.getNroConstancia()+", NRO SINIESTRO : "+bean.getNroSiniestro());
            bean.setRefImpSoles(bean.getImporteSoles().toString());
            bean.setRefImpTrans(bean.getImporteTransaccion().toString());
            bean.setRefImpEuros(bean.getImporteEuros().toString());
            lista2.set(i, bean);
          }
          resultado = envioSun(lista2.get(0).getMoneda(), lista2, metodoEjecutado, tipoPago);
        } else {
          resultado = Resultado;
        }
      }
    }

    else if (metodoEjecutado.equals(ConstantesSun.PAGOS_TRANSFERENCIA) || metodoEjecutado.equals(ConstantesSun.PAGOS_COMISION) || metodoEjecutado.equals(ConstantesSun.PAGOS_ITF)) {
      if (lista2.size() > 0) {
        logger.info("IMPORTANTE:: Se envia el pago de : " + lista2.get(0).getCuentaContable()+", REFERENCIA: "+lista2.get(0).getReferencia()+", NRO DETRACION/RETENCION :"+lista2.get(0).getNroConstancia());
        resultado = envioSun(lista2.get(0).getMoneda(), lista2, metodoEjecutado, tipoPago);
      }
    }

    logger.info("Respuesta Envio SUN: " + resultado);
    logger.info("Fin");
    return resultado;
  }

  public HashMap<Integer, String> procesaResultadoSun(String resultado) {
    logger.info("Inicio");
    logger.info(resultado);
    HashMap<Integer, String> mapa = utiles.leerXmlsalidaSun(resultado);
    logger.info("Fin");
    return mapa;
  }

  public HashMap<Integer, Integer> procesaResultadoSunExitoso(String resultado) {
    logger.info("Inicio");
    logger.info(resultado);
    HashMap<Integer, Integer> mapa = utiles.leerXmlsalidaSunExitoso(resultado);
    logger.info("Fin");
    return mapa;
  }

  public void setLoteAndOp(String nroLote, String opBanco, String opComision) {
    this.nroLote = nroLote;
    this.opBanco = opBanco;
    this.opComision = opComision;
  }

  public void setNroLote(String nroLote) {
    this.nroLote = nroLote;
  }

  public void setOpBanco(String opBanco) {
    this.opBanco = opBanco;
  }

  public void setOpComision(String opComision) {
    this.opComision = opComision;
  }

  public void setTipoDiario(String tipoDiario) {
    this.tipoDiario = tipoDiario;
  }

  private String tipoDiarioDetraccionPEN() throws SyncconException {
    Parametro p = parametroService.obtener(Constantes.MDP_PAG_DETRACCIONES_T_DIARIO_PAGO, TIP_PARAM_DETALLE, "1");
    return p.getNomValor();
  }

  private String tipoDiarioDetraccionUSD() throws SyncconException {
    Parametro p = parametroService.obtener(Constantes.MDP_PAG_DETRACCIONES_T_DIARIO_PAGO, TIP_PARAM_DETALLE, "2");
    return p.getNomValor();
  }

  private String tipoDiarioPagosVarios() throws SyncconException {
    Parametro p = parametroService.obtener(Constantes.MDP_PAG_VARIOS_T_DIARIO_PAGO, TIP_PARAM_DETALLE, "1");
    return p.getNomValor();
  }

  private String tipoDiarioRetenPEN() throws SyncconException {
    Parametro p = parametroService.obtener(Constantes.MDP_PAG_RETENCION_T_DIARIO_PAGO, TIP_PARAM_DETALLE, "1");
    return p.getNomValor();
  }

  private String tipoDiarioRetenUSD() throws SyncconException {
    Parametro p = parametroService.obtener(Constantes.MDP_PAG_RETENCION_T_DIARIO_PAGO, TIP_PARAM_DETALLE, "2");
    return p.getNomValor();
  }

  // TIPOS DE DIARIO:
  private String tipoDiarioSiniestros() throws SyncconException {
    Parametro p = parametroService.obtener(Constantes.MDP_PAG_SINIESTROS_T_DIARIO_PAGO, TIP_PARAM_DETALLE, "1");
    return p.getNomValor();
  }
  public List<List<com.cardif.sunsystems.mapeo.reportes.SSC.Payload.Ledger.Line>> extraerCuentasModeloUnoReporteSBS(String tipoCuenta, String AccountCodeMin, String AccountCodeMax, String AccountingPeriodMin,
	      String AccountingPeriodMax) throws Exception {
	    String Payload = null;
	    List<List<com.cardif.sunsystems.mapeo.reportes.SSC.Payload.Ledger.Line>> lista = null;
	    Payload = generarPayloadModeloUnoReporteSBS(AccountingPeriodMin, AccountingPeriodMax, AccountCodeMin, AccountCodeMax, ConstantesSun.SSC_Debit_Credit, ConstantesSun.SSC_AllocationMarker_P,
	        ConstantesSun.SSC_AllocationMarker_W, ConstantesSun.SSC_AnalysisCode9, tipoCuenta);
	    logger.info("Extracción Extracción Cuentas: " + Payload);
	    String ResultadoXml = componentes.ejecutaConsulta(ConstantesSun.SSC_ComponenteJournal, ConstantesSun.SSC_MetodoQuery, Payload);
	    logger.info("Respuesta Extracción Cuentas: " + ResultadoXml);
	    //lista = utiles.LeerXmlPagos(ResultadoXml);
	    lista = utiles.LeerXmlReportes(ResultadoXml);	    
	    return lista;
  }
  private String generarPayloadModeloUnoReporteSBS(String accountingPeriodMin, String accountingPeriodMax, String accountCodeMin, String accountCodeMax, String debitCredit, String allocationMarker,
	      String allocationMarker2, String analysisCode9, String tipoPago) throws Exception {
	    Document doc = null;
	    if (tipoPago.equals(ConstantesSun.MDP_BUSCAR_RETENCION)) {
	      doc = utiles.loadXMLFromResource("/com/cardif/sunsystems/plantillas/journalQueryINProvisionPagoReporteModeloUnoSBS.xml");
	    }

	    NodeList items = doc.getElementsByTagName("Item");
	    int cont = 0;
	    for (int j = 0; j < items.getLength(); j++) {
	      Node tmp = items.item(j);
	      if (tmp.getNodeType() == Node.ELEMENT_NODE) {
	        NamedNodeMap atributos = tmp.getAttributes();
	        if (tipoPago.equals(ConstantesSun.MDP_BUSCAR_RETENCION)) {
	          if (atributos.getNamedItem("name").getNodeValue().equals("/Ledger/Line/AccountingPeriod")) {
	            atributos.getNamedItem("minvalue").setNodeValue(accountingPeriodMin);
	            atributos.getNamedItem("maxvalue").setNodeValue(accountingPeriodMax);
	          }

	          if (atributos.getNamedItem("name").getNodeValue().equals("/Ledger/Line/AccountCode")) {
	            if (accountCodeMin.equals("") || accountCodeMax.equals("")) {
	              tmp.getParentNode().removeChild(tmp);
	            } else {
	              atributos.getNamedItem("minvalue").setNodeValue(accountCodeMin);
	              atributos.getNamedItem("maxvalue").setNodeValue(accountCodeMax);
	            }
	          }
	          if (atributos.getNamedItem("name").getNodeValue().equals("/Ledger/Line/AllocationMarker")) {
	            if (cont == 0) {
	              atributos.getNamedItem("value").setNodeValue(allocationMarker);
	              cont++;
	            } else {
	              atributos.getNamedItem("value").setNodeValue(allocationMarker2);
	              cont = 0;
	            }

	          }
	     	    if (atributos.getNamedItem("name").getNodeValue().equals("/Ledger/Line/AnalysisCode9")) {
	            atributos.getNamedItem("value").setNodeValue(analysisCode9);
	          }
	        }
	      }
	    }
	    return utiles.documenttoString(doc);
  }
}
