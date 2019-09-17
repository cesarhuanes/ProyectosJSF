package com.cardif.satelite.tesoreria.service.impl;

import static com.cardif.satelite.constantes.Constantes.TIP_PARAM_DETALLE;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.configuracion.service.ParametroService;
import com.cardif.satelite.constantes.Constantes;
import com.cardif.satelite.model.LoteCabecera;
import com.cardif.satelite.model.LoteFactura;
import com.cardif.satelite.model.LoteProveedor;
import com.cardif.satelite.model.Parametro;
import com.cardif.satelite.tesoreria.bean.JournalBean;
import com.cardif.satelite.tesoreria.dao.AsientoContableSecuencialMapper;
import com.cardif.satelite.tesoreria.dao.LoteCabeceraMapper;
import com.cardif.satelite.tesoreria.dao.LoteFacturaMapper;
import com.cardif.satelite.tesoreria.dao.LoteProveedorMapper;
import com.cardif.satelite.tesoreria.dao.PagoComisionItfMapper;
import com.cardif.satelite.tesoreria.model.PagoComisionItf;
import com.cardif.satelite.tesoreria.service.ProcesoPagos;
import com.cardif.sunsystems.bean.AsientoContableBean;
import com.cardif.sunsystems.controller.SunsystemsController;
import com.cardif.sunsystems.util.ConstantesSun;

@Service("procesoPagos")
public class ProcesoPagosImpl implements ProcesoPagos {
  public static final Logger log = Logger.getLogger(ProcesoPagosImpl.class);

  // INICIO JARIASS SYNCCON 19/01/2015
  private String tipoDiario;
  // INICIO JARIASS SYNCCON 19/01/2015
  private String nroLote;
  private String opBanco;
  private String opComision;

  // FIN JARIASS SYNCCON 19/01/2015
  @Autowired
  private ParametroService parametroService;

  @Autowired(required = true)
  private AsientoContableSecuencialMapper asientoMaper;

  @Autowired
  private PagoComisionItfMapper pagoComisionItfMapper;

  @Autowired
  private LoteCabeceraMapper loteCabeceraMapper;

  @Autowired
  private LoteProveedorMapper loteProveedorMapper;

  @Autowired
  private LoteFacturaMapper loteFacturaMapper;

  private SunsystemsController controlSun = SunsystemsController.getInstance();

  @Override
  public HashMap<String, BigDecimal> traerTipoCambioActual(String fechaProceso)
      throws NullPointerException, ParserConfigurationException, SAXException, IOException, TransformerFactoryConfigurationError, TransformerException {
    HashMap<String, BigDecimal> val = new HashMap<String, BigDecimal>();
    val = controlSun.extraerTipoCambio(fechaProceso);

    return val;
  }

  @Override
  public void setLoteAndOp(String nroLote, String opBanco, String opComision) {
    this.nroLote = nroLote;
    this.opBanco = opBanco;
    this.opComision = opComision;

  }

  @Override
  public List<AsientoContableBean> procesarPago(List<List<JournalBean>> listaProcesos, String tipoPago, Date fechaPago, float tasaEurosSoles, float tasaEurosDolares, float tasaDolaresSoles,
      String ctaBanco, String glosaPago, String referencia, String medioPagoAnalisisCode10, Date fecTransaccion) throws SyncconException {
    List<AsientoContableBean> asientos_contables = null;
    controlSun.setLoteAndOp(nroLote, opBanco, opComision);
    asientos_contables = controlSun.generaAsientosContables(listaProcesos, tipoPago, fechaPago, tasaEurosSoles, tasaEurosDolares, tasaDolaresSoles, ctaBanco, parametroService, asientoMaper, glosaPago,
        referencia, medioPagoAnalisisCode10, fecTransaccion);
    tipoDiario = controlSun.getTipoDiario();
    return asientos_contables;
  }

  @Override
  public List<SelectItem> traerListaTipoPagoItems() throws SyncconException {
    List<Parametro> listaTipoPagos = parametroService.buscar(Constantes.MDP_TIPO_PAGOS, TIP_PARAM_DETALLE);
    List<SelectItem> lista_retorno = new ArrayList<SelectItem>();
    lista_retorno.add(new SelectItem("", "- Seleccionar -"));
    for (Parametro p : listaTipoPagos) {
      lista_retorno.add(new SelectItem(p.getCodValor(), p.getNomValor()));
    }
    return lista_retorno;
  }

  @Override
  public List<SelectItem> traerListaMedioPagoItems() throws SyncconException {
    List<SelectItem> lista_retorno = new ArrayList<SelectItem>();
    List<Parametro> listaMedioPagos = parametroService.selectMediosPago(Constantes.MDP_MEDIO_PAGOS, TIP_PARAM_DETALLE);
    lista_retorno.add(new SelectItem("", "- Seleccionar -"));
    for (Parametro p : listaMedioPagos) {
      lista_retorno.add(new SelectItem(p.getCodValor(), p.getNomValor()));
    }
    return lista_retorno;
  }

  @Override
  public List<SelectItem> traerListaMarcadorItems() throws SyncconException {
    List<SelectItem> lista_retorno = new ArrayList<SelectItem>();
    List<Parametro> listaMarcador = parametroService.buscar(Constantes.MDP_TIPO_MARCADOR_PAGOS, TIP_PARAM_DETALLE);
    lista_retorno.add(new SelectItem("", "- Seleccionar -"));
    for (Parametro p : listaMarcador) {
      lista_retorno.add(new SelectItem(p.getCodValor(), p.getNomValor()));
    }
    return lista_retorno;
  }

  @Override
  public List<SelectItem> traerListaCtaBancos() throws SyncconException {
    List<SelectItem> lista_retorno = new ArrayList<SelectItem>();
    List<Parametro> listaMarcador = parametroService.buscar(Constantes.MDP_CTAS_BANCO, TIP_PARAM_DETALLE);
    lista_retorno.add(new SelectItem("", "- Seleccionar -"));
    String codValor = null;
    for (Parametro p : listaMarcador) {
      codValor = p.getCodValor();
      lista_retorno.add(new SelectItem(codValor, p.getNomValor() + " (" + getMoneda(codValor) + ")"));
    }
    return lista_retorno;
  }

  private String getMoneda(String numCuentaBanco) {
    String moneda = "NO EXISTE";
    if ((numCuentaBanco).substring(0, 3).equals("101")) {
      moneda = "PEN";
    } else if ((numCuentaBanco).substring(0, 3).equals("102")) {
      moneda = "USD";
    }
    return moneda;
  }

  @Override
  public List<List<JournalBean>> consultarRegistros(String idProveedorFrom, String idProveedorTo, String fecPeriodoInicio, String fecPeriodoFin) throws Exception {
    ArrayList<JournalBean> lista = null;
    List<List<JournalBean>> superlistabean = new ArrayList<List<JournalBean>>();
    JournalBean element = null;
    if (log.isDebugEnabled()) {
      log.debug("Input Extracción Proveedor [1.- " + idProveedorFrom + ", " + "2.- " + idProveedorTo + ", " + "3.- " + fecPeriodoInicio + ", " + "4.- " + fecPeriodoFin + "] , ");
    }

    List<List<com.cardif.sunsystems.mapeo.pagos.SSC.Payload.Ledger.Line>> superlista = controlSun.extraerCuentas(ConstantesSun.MDP_BUSCAR_RETENCION, idProveedorFrom, idProveedorTo, fecPeriodoInicio,
        fecPeriodoFin);

    if (superlista == null) {
      superlista = new ArrayList<List<com.cardif.sunsystems.mapeo.pagos.SSC.Payload.Ledger.Line>>();
    }
    for (List<com.cardif.sunsystems.mapeo.pagos.SSC.Payload.Ledger.Line> ab : superlista) {
      lista = new ArrayList<JournalBean>();
      for (com.cardif.sunsystems.mapeo.pagos.SSC.Payload.Ledger.Line sublista : ab) {
        element = new JournalBean();
        element.setCodigoCuenta(sublista.getAccountCode());
        element.setNombreProveedor(sublista.getAccounts().getDescription());
        element.setNombreProveedorCompleto(sublista.getAccounts().getLongDescription());
        element.setFechaTransaccion(sublista.getTransactionDate());
        element.setReferencia(sublista.getTransactionReference());
        element.setDebitoCredito(sublista.getDebitCredit());
        element.setMoneda(sublista.getCurrencyCode());
        element.setImpTransaccionAmount(sublista.getTransactionAmount());
        element.setImpPropinas(sublista.getAmountTips());
        element.setImpSoles(sublista.getBaseAmount());
        element.setImpEuros(sublista.getBase2ReportingAmount());
        element.setGlosa(sublista.getDescription());
        element.setPeriodo(sublista.getAccountingPeriod());
        element.setRuc(sublista.getAccounts().getLookupCode() + "");
        element.setCentroCosto(sublista.getAnalysisCode1());
        element.setSocioProducto(sublista.getAnalysisCode2());
        element.setCanal(sublista.getAnalysisCode3());
        element.setProveedorEmpleado(sublista.getAnalysisCode4());
        element.setRucDniCliente(sublista.getAnalysisCode5());
        element.setPolizaCliente(sublista.getAnalysisCode6());
        element.setInversiones(sublista.getAnalysisCode7());
        element.setNroSiniestro(sublista.getAnalysisCode8());
        element.setComprobanteSunat(sublista.getAnalysisCode9());
        element.setTipoMedioPago(sublista.getAnalysisCode10());
        element.setDiario(sublista.getJournalNumber());
        element.setLineaDiario(sublista.getJournalLineNumber());

        lista.add(element);
      }
      superlistabean.add(lista);
    }
    return superlistabean;
  }

  @Override
  public List<String> enviarSun(List<AsientoContableBean> asientoContable, String metodoEjecutado, HashMap<Integer, Object> resultados, String tipoPago, String numCuentaBanco) {

    List<AsientoContableBean> tmp = new ArrayList<AsientoContableBean>();
    List<String> listaRespuestaSun = new ArrayList<String>();
    List<List<AsientoContableBean>> asientosContables = new ArrayList<List<AsientoContableBean>>();
    List<AsientoContableBean> tmp2 = new ArrayList<AsientoContableBean>();
    HashMap<Integer, Object> resultadosTmp = new HashMap<Integer, Object>();

    for (AsientoContableBean ab : asientoContable) {
      if (ab.getLayout() != null) {
        tmp2.add(ab);

      } else if (ab.getLayout() == null || asientoContable.indexOf(ab) == (asientoContable.size() - 1)) {
        asientosContables.add(tmp2);
        tmp2 = new ArrayList<AsientoContableBean>();
      }

      if (ab.getLayout() != null) {
        tmp.add(ab);
      }
    }
    if (tmp2.size() > 0) {
      asientosContables.add(tmp2);
      tmp2 = new ArrayList<AsientoContableBean>();
    }

    for (int i = 0; i < asientosContables.size(); i++) {

      List<AsientoContableBean> listaSun = asientosContables.get(i);
      resultadosTmp = new HashMap<Integer, Object>();

      String resultmp = controlSun.prepararEnvioSun(listaSun, metodoEjecutado, resultadosTmp, parametroService, tipoPago, numCuentaBanco);

      listaRespuestaSun.add(resultmp);
      if (resultadosTmp.size() > 0) {
        resultados.put(i, resultadosTmp);
      }
    }
    return listaRespuestaSun;
  }

  @Override
  public void registrarExcelComisiones(PagoComisionItf pagoComisionItf) {
    pagoComisionItfMapper.insertarPagoComisionItf(pagoComisionItf);
  }

  @Override
  public int contarExcelByName(String nom_excel) {
    return pagoComisionItfMapper.countExcel(nom_excel);
  }

  @Override
  public HashMap<Integer, String> procesarResultadoSun(String resultado) {

    return controlSun.procesaResultadoSun(resultado);
  }

  @Override
  public HashMap<Integer, Integer> procesarResultadoSunExitoso(String resultado) {

    return controlSun.procesaResultadoSunExitoso(resultado);
  }

  @Override
  public String marcarLineasPagados(JournalBean bean) throws Exception {

    return controlSun.marcarLineasPagados(bean.getDiario(), bean.getLineaDiario(), ConstantesSun.SSC_AllocationMarker_W);
  }

  @Override
  public String getTipoDiario() {
    return tipoDiario;
  }

  public void setTipoDiario(String tipoDiario) {
    this.tipoDiario = tipoDiario;
  }

  public String getNroLote() {
    return nroLote;
  }

  public void setNroLote(String nroLote) {
    this.nroLote = nroLote;
  }

  public String getOpBanco() {
    return opBanco;
  }

  public void setOpBanco(String opBanco) {
    this.opBanco = opBanco;
  }

  public String getOpComision() {
    return opComision;
  }

  public void setOpComision(String opComision) {
    this.opComision = opComision;
  }

  @Override
  public int selectMaxLoteSecuencial(String tipoPago, String banco, String moneda) {
    return loteCabeceraMapper.selectMaxSecuencial(tipoPago, banco, moneda);
  }

  @Override
  public void registrarLoteCabecera(LoteCabecera lotePago) {
    loteCabeceraMapper.insert(lotePago);
  }

  @Override
  public void registrarLoteProveedor(LoteProveedor lotePago) {
    loteProveedorMapper.insert(lotePago);
  }

  @Override
  public void registrarLoteFactura(LoteFactura lotePago) {
    loteFacturaMapper.insert(lotePago);
  }

  @Override
  public String getTipoDiarioTransferencia(String moneda) throws SyncconException {
    String codValor = "";
    if (moneda.equalsIgnoreCase("PEN")) {
      codValor = "1";
    } else {
      codValor = "2";
    }
    return parametroService.obtenerDescripcion(Constantes.MDP_PAG_TRASNF_T_DIARIO_PAGO, TIP_PARAM_DETALLE, codValor);
  }
}
