package com.cardif.satelite.tesoreria.controller;

import static com.cardif.satelite.constantes.Constantes.TIP_PARAM_DETALLE;

import java.io.File;
import java.io.PrintWriter;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.xml.ws.soap.SOAPFaultException;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.configuracion.service.ParametroService;
import com.cardif.satelite.constantes.Constantes;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.Parametro;
import com.cardif.satelite.tesoreria.bean.JournalBean;
import com.cardif.satelite.tesoreria.bean.ResultadoErroresContable;
import com.cardif.satelite.tesoreria.service.ProcesoPagos;
import com.cardif.sunsystems.bean.AsientoContableBean;
import com.cardif.sunsystems.util.ConstantesSun;
import com.cardif.sunsystems.util.Utilidades;

@Controller("pagosTransferController")
@Scope("request")
public class PagosTransferController implements Serializable {
  private static final long serialVersionUID = -8323560799344065774L;
  public static final Logger logger = Logger.getLogger(PagosTransferController.class);

  @Autowired(required = true)
  private ProcesoPagos procesoPagos;
  private List<JournalBean> listaProcesos;
  private List<AsientoContableBean> resultadoContable;

  private List<SelectItem> listaCtaBanco;
  private List<SelectItem> medioPagoItems;

  private int numCuentaBanco1;
  private int numCuentaBanco2;
  private BigDecimal montoA;
  private BigDecimal montoB;
  private BigDecimal valorComision;
  private String refA;
  private String refB;
  private String glosaPagoA;
  private String glosaPagoB;
  private boolean comision;
  private String idMedioPago;

  private String tipoDiario = "VPAY1";

  private String medioPagoSelected;
  private String tipoPagoAsiento;

  private String outText = "";

  private boolean success = false;

  private boolean desabBtnEnvioSun = true;

  private boolean desabBtnProcesarAsientos = true;

  public boolean isDesabBtnProcesarAsientos() {
    return desabBtnProcesarAsientos;
  }

  public void setDesabBtnProcesarAsientos(boolean desabBtnProcesarAsientos) {
    this.desabBtnProcesarAsientos = desabBtnProcesarAsientos;
  }

  public boolean isDesabBtnEnvioSun() {
    return desabBtnEnvioSun;
  }

  public void setDesabBtnEnvioSun(boolean desabBtnEnvioSun) {
    this.desabBtnEnvioSun = desabBtnEnvioSun;
  }

  public String getOutText() {
    return outText;
  }

  public void setOutText(String outText) {
    this.outText = outText;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  // Auxiliares en procesamiento de Hash Map Correctos y Errores:
  private int contVacios = -1;
  private int sumaLineaAsientos = 0;

  private int contVaciosErrores = -1;
  private int sumaLineaAsientosErrores = 0;

  public String getTipoDiario() {
    return tipoDiario;
  }

  public void setTipoDiario(String tipoDiario) {
    this.tipoDiario = tipoDiario;
  }

  private SimpleDateFormat fm = null;
  private SimpleDateFormat fmPeriodo = null;

  public List<AsientoContableBean> getResultadoContable() {
    return resultadoContable;
  }

  public void setResultadoContable(List<AsientoContableBean> resultadoContable) {
    this.resultadoContable = resultadoContable;
  }

  public String getIdMedioPago() {
    return idMedioPago;
  }

  public void setIdMedioPago(String idMedioPago) {
    this.idMedioPago = idMedioPago;
  }

  @Autowired
  private ParametroService parametroService;

  @PostConstruct
  public String inicio() {
    logger.info("Inicio");

    comision = false;
    fm = new SimpleDateFormat(ConstantesSun.UTL_FORMATO_FECHA_SUNSYSTEMS);
    fmPeriodo = new SimpleDateFormat("MMyyyy");

    Calendar cal = Calendar.getInstance();
    Calendar cal2 = Calendar.getInstance();
    cal2.set(2014, 1, 9);
    fecPago = cal.getTime();
    fecTransaccion = cal2.getTime();

    tipoPagoAsiento = ConstantesSun.PAGOS_TRANSFERENCIA;

    listaPagados = new ArrayList<JournalBean>();
    try {

      listaCtaBanco = new ArrayList<SelectItem>();
      List<SelectItem> listaCtaBancos = procesoPagos.traerListaCtaBancos();

      for (SelectItem c : listaCtaBancos) {
        listaCtaBanco.add(c);
      }

      listaProcesos = new ArrayList<JournalBean>();

      medioPagoItems = new ArrayList<SelectItem>();
      Parametro medioPagoA = parametroService.obtener(Constantes.MDP_MEDIO_PAGOS, TIP_PARAM_DETALLE, "5");
      Parametro medioPagoB = parametroService.obtener(Constantes.MDP_MEDIO_PAGOS, TIP_PARAM_DETALLE, "8");
      medioPagoItems.add(new SelectItem("0", "Seleccionar"));
      medioPagoItems.add(new SelectItem("1", medioPagoA.getNomValor()));
      medioPagoItems.add(new SelectItem("2", medioPagoB.getNomValor()));

      actualizarTiposCambio();

      resultadoContable = new ArrayList<AsientoContableBean>();

    } catch (SyncconException ex) {
      logger.error("ERROR SYNCCON: " + ex.getMessageComplete());
      FacesMessage facesMsg = new FacesMessage(ex.getSeveridad(), ex.getMessage(), null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, ErrorConstants.MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }
    logger.info("Fin");
    return null;
  }

  private String valEuroSoles;
  private String valDolarSoles;
  private String valEuroDolares;

  private Date fecPago;
  private Date fecTransaccion;

  public List<SelectItem> getListaCtaBanco() {
    return listaCtaBanco;
  }

  public void setListaCtaBanco(List<SelectItem> listaCtaBanco) {
    this.listaCtaBanco = listaCtaBanco;
  }

  public int getNumCuentaBanco1() {
    return numCuentaBanco1;
  }

  public void setNumCuentaBanco1(int numCuentaBanco1) {
    this.numCuentaBanco1 = numCuentaBanco1;
  }

  public int getNumCuentaBanco2() {
    return numCuentaBanco2;
  }

  public void setNumCuentaBanco2(int numCuentaBanco2) {
    this.numCuentaBanco2 = numCuentaBanco2;
  }

  public BigDecimal getMontoA() {
    return montoA;
  }

  public void setMontoA(BigDecimal montoA) {
    this.montoA = montoA;
  }

  public BigDecimal getMontoB() {
    return montoB;
  }

  public void setMontoB(BigDecimal montoB) {
    this.montoB = montoB;
  }

  public String getRefA() {
    return refA;
  }

  public void setRefA(String refA) {
    this.refA = refA;
  }

  public String getRefB() {
    return refB;
  }

  public void setRefB(String refB) {
    this.refB = refB;
  }

  public String getValDolarSoles() {
    return valDolarSoles;
  }

  public void setValDolarSoles(String valDolarSoles) {
    this.valDolarSoles = valDolarSoles;
  }

  public List<SelectItem> getMedioPagoItems() {
    return medioPagoItems;
  }

  public void setMedioPagoItems(List<SelectItem> medioPagoItems) {
    this.medioPagoItems = medioPagoItems;
  }

  public String getValEuroSoles() {
    return valEuroSoles;
  }

  public void setValEuroSoles(String valEuroSoles) {
    this.valEuroSoles = valEuroSoles;
  }

  public String getValEuroDolares() {
    return valEuroDolares;
  }

  public void setValEuroDolares(String valEuroDolares) {
    this.valEuroDolares = valEuroDolares;
  }

  public String actualizarTiposCambio() {
    logger.info("Actualizando Tipo Cambio");

    // Desabilito el botòn Procesar:
    desabBtnProcesarAsientos = true;

    HashMap<String, BigDecimal> lista_val = null;
    try {
      lista_val = procesoPagos.traerTipoCambioActual(fm.format(fecTransaccion));
    } catch (SOAPFaultException e) {
      logger.error("ERROR SOAP: " + e.getMessage());
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "La autentificacion en SUN ha fallado", null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
      return "error";
    } catch (Exception e) {

    }

    if (lista_val instanceof HashMap) {
      if (lista_val.get("codigo").compareTo(new BigDecimal("0")) == 0) {
        valEuroSoles = lista_val.get("eur-pen").toString();
        valDolarSoles = lista_val.get("usd-pen").toString();
        valEuroDolares = lista_val.get("eur-usd").toString();
        desabBtnProcesarAsientos = false;
      } else {
        valEuroSoles = "";
        valDolarSoles = "";
        valEuroDolares = "";
        desabBtnProcesarAsientos = true;
        logger.error("Falla en tipos cambios");
        enviarMensaje("No hay tipos de cambio para esta fecha");
      }
      fecPago = fecTransaccion;
    }

    return null;
  }

  public Date getFecPago() {
    return fecPago;
  }

  public void setFecPago(Date fecPago) {
    this.fecPago = fecPago;
  }

  public Date getFecTransaccion() {
    return fecTransaccion;
  }

  public void setFecTransaccion(Date fecTransaccion) {
    this.fecTransaccion = fecTransaccion;
  }

  public String limpiarFiltros() {
    logger.info("Inicio");
    String respuesta = null;
    try {

      listaProcesos = new ArrayList<JournalBean>();
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, ErrorConstants.MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }
    logger.info("Fin");
    return respuesta;
  }

//  private void enviarMensajeInfo(String mensaje) {
//    FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, mensaje, null);
//    FacesContext.getCurrentInstance().addMessage(null, facesMsg);
//  }

  private void enviarMensaje(String mensaje) {
    FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, mensaje, null);
    FacesContext.getCurrentInstance().addMessage(null, facesMsg);
  }

//  private boolean validarTipoCambio() {
//    log.info("Tipos Cambio en Validar: ");
//    log.info("Euro Soles: " + valEuroSoles);
//    log.info(valDolarSoles);
//    log.info(valEuroSoles);
//    if (valEuroSoles.length() == 0 || valDolarSoles.length() == 0 || valEuroDolares.length() == 0) {
//      return false;
//    }
//    return true;
//  }

  private boolean validarCuentas() {
    if (numCuentaBanco1 == 0 || numCuentaBanco2 == 0) {
      logger.error("No ha seleccionado cta de cargo o abono");
      return false;
    }
    return true;
  }

  private boolean validarCuentasMonedas() {
    if (!getMoneda(numCuentaBanco1).equals(getMoneda(numCuentaBanco2))) {
      logger.error("Las cuentas deben ser de una misma moneda");
      return false;
    }
    return true;
  }

  private boolean validarMontosVacios() {
    if (montoA.signum() == 0 || montoB.signum() == 0) {
      logger.error("Montos vacios");
      return false;
    }
    return true;
  }

  private boolean validarMontosPermitidos() {
    if (montoA.compareTo(montoB) == -1) {
      logger.error("Montos no permitidos");
      return false;
    }
    return true;
  }

  private boolean validarReferencias() {
    if (refA.length() == 0 || refB.length() == 0) {
      logger.error("Validar Referencias");
      return false;
    }
    return true;
  }

  private String getSelectedMedioPago() {
    String medioPagoSelected = "";
    for (SelectItem selectItem : medioPagoItems) {
      if (selectItem.getValue().equals(idMedioPago)) {
        medioPagoSelected = selectItem.getLabel();
        break;
      }
    }
    return medioPagoSelected;
  }

  private String getSelectedCuenta(int numCuentaBanco) {

    String nomBanco = "";
    for (SelectItem selectItem : listaCtaBanco) {
      if (selectItem.getValue().equals(numCuentaBanco + "")) {
        nomBanco = selectItem.getLabel();
        break;
      }
    }
    return nomBanco.substring(0, nomBanco.indexOf(" ("));
  }

  private String getGlosaRecortada(String glosa) {
    String result = glosa;
    if (result.length() > 50) {
      result = glosa.substring(0, 50);
    }
    return result;
  }

  public String obtenerAsiento() {
    String nomCuenta1 = getSelectedCuenta(numCuentaBanco1);
    String nomCuenta2 = getSelectedCuenta(numCuentaBanco2);
    logger.info("Cuenta A: " + numCuentaBanco1 + ": " + nomCuenta1);
    logger.info("Cuenta B: " + numCuentaBanco2 + ": " + nomCuenta2);
    logger.info("Monto A: " + montoA);
    logger.info("Monto B: " + montoB);
    logger.info("Ref A: " + refA);
    logger.info("Ref B: " + refB);
    logger.info("Fec Pago: " + fm.format(fecPago));
    logger.info("Fec Transaccion: " + fm.format(fecTransaccion));

    desabBtnEnvioSun = true;
    resultadoContable.clear();

    glosaPagoA = getGlosaRecortada("Transferencia a " + nomCuenta2);
    glosaPagoB = getGlosaRecortada("Transferencia de " + nomCuenta1);

    medioPagoSelected = getSelectedMedioPago();

    logger.info("Medio Pago: " + medioPagoSelected);

    if (!validarCuentas()) {
      enviarMensaje("No ha seleccionado cta de cargo o abono");
      return null;
    } else if (!validarCuentasMonedas()) {
      enviarMensaje("Las cuentas deben ser de una misma moneda");
      return null;
    } else if (!validarMontosVacios()) {
      enviarMensaje("Debe ingresar montos de cargo y abono");
      return null;
    } else if (!validarMontosPermitidos()) {
      enviarMensaje("El monto de origen debe ser mayor o igual al de destino");
      return null;
    } else if (!validarReferencias()) {
      enviarMensaje("Debe ingresar referencias para cada cuenta");
      return null;
    } else if (medioPagoSelected.equals("Seleccionar")) {
      enviarMensaje("No ha seleccionado Medio de Pago");
      return null;
    }
    logger.info("Pasó las validaciones");
    // Fila 1:
    String moneda1 = getMoneda(numCuentaBanco1);

    AsientoContableBean beanA = new AsientoContableBean();
    beanA.setGlosa(glosaPagoA);
    beanA.setMoneda(moneda1);
    beanA.setMarcadorDC("C");
    beanA.setCuentaContable(numCuentaBanco1 + "");
    beanA.setFechaVencimiento(fecPago);
    beanA.setFecha(fecTransaccion);
    beanA.setPeriodo("0" + fmPeriodo.format(fecTransaccion));
    beanA.setReferencia(refA);
    beanA.setTipoMedioPago(ConstantesSun.MEDIO_PAGO_TRANSFER);
    beanA.setLayout("1;2;3");
    beanA.setNombreLibro("A");
    beanA.setImporteTransaccion(Utilidades.redondear(2, montoA).negate());
    beanA.setMarcador(ConstantesSun.SSC_AllocationMarker_P);
    beanA.setRefImpTrans(beanA.getImporteTransaccion().toString());

    if (moneda1.equals("PEN")) {
      beanA.setImporteSoles(Utilidades.redondear(2, montoA).negate());
      beanA.setImporteEuros(Utilidades.redondear(2, montoA.divide(new BigDecimal(valEuroSoles), RoundingMode.HALF_UP)).negate());
    } else if (moneda1.equals("USD")) {
      beanA.setImporteSoles(Utilidades.redondear(2, montoA.multiply(new BigDecimal(valDolarSoles))).negate());
      beanA.setImporteEuros(Utilidades.redondear(2, montoA.divide(new BigDecimal(valEuroDolares), RoundingMode.HALF_UP)).negate());
    }
    beanA.setRefImpSoles(beanA.getImporteSoles().toString());
    beanA.setRefImpEuros(beanA.getImporteEuros().toString());

    // Fila 3:
    String moneda2 = getMoneda(numCuentaBanco2);

    AsientoContableBean beanC = new AsientoContableBean();
    beanC.setGlosa(glosaPagoB);
    beanC.setMoneda(moneda2);
    beanC.setMarcadorDC("D");
    beanC.setCuentaContable(numCuentaBanco2 + "");
    beanC.setFechaVencimiento(fecPago);
    beanC.setFecha(fecTransaccion);
    beanC.setPeriodo("0" + fmPeriodo.format(fecTransaccion));
    beanC.setReferencia(refB);
    beanC.setTipoMedioPago(ConstantesSun.MEDIO_PAGO_TRANSFER);
    beanC.setLayout("1;2;3");
    beanC.setNombreLibro("A");
    beanC.setMarcador(ConstantesSun.SSC_AllocationMarker_P);
    beanC.setImporteTransaccion(Utilidades.redondear(2, montoB));

    if (moneda1.equals("PEN")) {
      beanC.setImporteSoles(Utilidades.redondear(2, montoB));
      beanC.setImporteEuros(Utilidades.redondear(2, montoB.divide(new BigDecimal(valEuroSoles), RoundingMode.HALF_UP)));
    } else if (moneda1.equals("USD")) {
      beanC.setImporteSoles(Utilidades.redondear(2, montoB.multiply(new BigDecimal(valDolarSoles))));
      beanC.setImporteEuros(Utilidades.redondear(2, montoB.divide(new BigDecimal(valEuroDolares), RoundingMode.HALF_UP)));
    }

    beanC.setRefImpTrans(beanC.getImporteTransaccion().toString());
    beanC.setRefImpSoles(beanC.getImporteSoles().toString());
    beanC.setRefImpEuros(beanC.getImporteEuros().toString());

    // Fila 2: Comisión
    AsientoContableBean beanB = new AsientoContableBean();
    String ctaComision = "";
    String codValor = "1";
    if (beanA.getMoneda().equals("USD")) {
      codValor = "2";
    }
    try {
      ctaComision = parametroService.obtener(Constantes.MDP_CTA_GASTO_DETRAC_TRANS_COMISION, TIP_PARAM_DETALLE, codValor).getNomValor();
    } catch (SyncconException e) {
      logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
      logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
    }
    beanB.setGlosa(ConstantesSun.GLOSA_TRANSFER_COMIS);
    beanB.setMoneda(moneda1);
    beanB.setMarcadorDC("D");
    beanB.setCuentaContable(ctaComision);
    beanB.setFechaVencimiento(fecPago);
    beanB.setFecha(fecTransaccion);
    beanB.setPeriodo("0" + fmPeriodo.format(fecTransaccion));
    beanB.setReferencia(refA);
    beanB.setLayout("1;2;3");
    beanB.setNombreLibro("A");
    beanB.setMarcador(ConstantesSun.SSC_AllocationMarker_P);
    beanB.setTipoMedioPago(ConstantesSun.MEDIO_PAGO_TRANSFER);
    beanB.setProveedorEmpleado(ConstantesSun.COMIS_TRANSFER_PROV_EMPLEADO);
    beanB.setCentroCosto(ConstantesSun.COMIS_TRANSFER_CENTRO_COSTO);
    beanB.setTipoComprobanteSunat(ConstantesSun.COMIS_TRANSFER_COMPROB_SUNAT);

    if (montoA.compareTo(montoB) == 1) {

      beanB.setImporteTransaccion(Utilidades.redondear(2, beanA.getImporteTransaccion().negate().subtract(beanC.getImporteTransaccion())));
      beanB.setImporteSoles(Utilidades.redondear(2, beanA.getImporteSoles().negate().subtract(beanC.getImporteSoles())));
      beanB.setImporteEuros(Utilidades.redondear(2, beanA.getImporteEuros().negate().subtract(beanC.getImporteEuros())));

    } else {
      beanB.setImporteTransaccion(new BigDecimal("0.00"));
      beanB.setImporteSoles(new BigDecimal("0.00"));
      beanB.setImporteEuros(new BigDecimal("0.00"));
    }
    beanB.setRefImpTrans(beanB.getImporteTransaccion().toString());
    beanB.setRefImpSoles(beanB.getImporteSoles().toString());
    beanB.setRefImpEuros(beanB.getImporteEuros().toString());

    resultadoContable.add(beanA);
    resultadoContable.add(beanB);
    resultadoContable.add(beanC);

    try {
      tipoDiario = procesoPagos.getTipoDiarioTransferencia(beanA.getMoneda());
    } catch (SyncconException e) {
      logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
      logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
    }
    desabBtnEnvioSun = false;

    return null;
  };

  private String getMoneda(int numCuentaBanco) {
    String moneda = "";
    if ((numCuentaBanco + "").substring(0, 3).equals("101")) {
      moneda = "PEN";
    } else if ((numCuentaBanco + "").substring(0, 3).equals("102")) {
      moneda = "USD";
    } else {
      moneda = "NO EXISTE";
    }

    return moneda;
  }

  private List<JournalBean> listaPagados;
  private List<ResultadoErroresContable> resultadoErroresContable;
  private List<ResultadoErroresContable> resultadoCorrectosContable;

  public String enviarPagoSun() {

    listaPagados.clear();
    logger.info("Inicio");
    HashMap<Integer, String> listaErrores = new HashMap<Integer, String>();
    HashMap<Integer, Integer> listaAsientosCorrectos = new HashMap<Integer, Integer>();
    resultadoErroresContable = new ArrayList<ResultadoErroresContable>();
    resultadoCorrectosContable = new ArrayList<ResultadoErroresContable>();
    String retorno = null;

    String tipoPago = getSelectedMedioPago();

    try {
      logger.info("Antes de resultadosValidacion");
      HashMap<Integer, Object> resultadosValidacion = new HashMap<Integer, Object>();

      List<String> resultado = procesoPagos.enviarSun(resultadoContable, ConstantesSun.PAGOS_TRANSFERENCIA, resultadosValidacion, tipoPago, "trasnferencia");

      for (int i = 0; i < resultado.size(); i++) {
        listaErrores = procesoPagos.procesarResultadoSun(resultado.get(i));

        if (listaErrores.size() > 0) {
          resultadoErroresContable.addAll(ProcesarHashMapErroresSun(listaErrores, "ERRORES PROCESO SUN"));
          resultadoErroresContable.add(new ResultadoErroresContable());
        } else {
          listaAsientosCorrectos = procesoPagos.procesarResultadoSunExitoso(resultado.get(i));
          // resultadoCorrectosContable =
          // ProcesarHashMapCorrectos(listaAsientosCorrectos);
          resultadoCorrectosContable.addAll(ProcesarHashMapCorrectos(listaAsientosCorrectos));
          resultadoCorrectosContable.add(new ResultadoErroresContable());
          logger.info(listaAsientosCorrectos);

          // desabActualizarM = false;
        }
      }

      contVaciosErrores = -1;
      sumaLineaAsientosErrores = 0;
      contVacios = -1;
      sumaLineaAsientos = 0;

      if (resultadoErroresContable.size() > 0) {
        logger.error("Hay errores");
        retorno = "gotoErroresSunTrans";
        outText = "Errores generados";
        success = false;
      }
      if (listaAsientosCorrectos.size() > 0) {
        logger.error("No hay errores");
        retorno = "gotoCorrectosSunTrans";
        outText = "Asientos generados";
        success = true;
      }
      desabBtnEnvioSun = true;
      resultadoContable.clear();
      logger.info("SUCCESS???: " + success);
    } catch (Exception e) {
      logger.error("Error enviando a SUN: " + e.getMessage());
    } finally {
      idMedioPago = "";
      numCuentaBanco1 = 0;
      numCuentaBanco2 = 0;
      montoA = new BigDecimal("0.0");
      montoB = new BigDecimal("0.0");
      refA = "";
      refB = "";
      resultadoContable = new ArrayList<AsientoContableBean>();
    }

    return null;
  }

  public List<JournalBean> getListaPagados() {
    return listaPagados;
  }

  public void setListaPagados(List<JournalBean> listaPagados) {
    this.listaPagados = listaPagados;
  }

  public List<ResultadoErroresContable> getResultadoErroresContable() {
    return resultadoErroresContable;
  }

  public void setResultadoErroresContable(List<ResultadoErroresContable> resultadoErroresContable) {
    this.resultadoErroresContable = resultadoErroresContable;
  }

  public List<ResultadoErroresContable> getResultadoCorrectosContable() {
    return resultadoCorrectosContable;
  }

  public void setResultadoCorrectosContable(List<ResultadoErroresContable> resultadoCorrectosContable) {
    this.resultadoCorrectosContable = resultadoCorrectosContable;
  }

  private List<ResultadoErroresContable> ProcesarHashMapErroresSun(HashMap<Integer, String> listaErrores, String origen) {
    Iterator it = listaErrores.entrySet().iterator();
    List<ResultadoErroresContable> lista = new ArrayList<ResultadoErroresContable>();
    ResultadoErroresContable resul;
    Map.Entry e1;
    int iterador = 1;
    while (it.hasNext()) {
      e1 = (Map.Entry) it.next();
      resul = new ResultadoErroresContable();
      resul.setLineaAsientoError(e1.getKey() + "");

      String[] elementos = e1.getValue().toString().split(ConstantesSun.UTL_CHR_SEPARADOR);

      resul.setDiario(e1.getKey() + "");
      resul.setLineaDiario(elementos[0]);
      resul.setMensajeAsientoError(elementos[1]);
      resul.setProcedenciaError(origen);
      resul.setLineaAsientoError(resultadoContable.get(iterador + contVaciosErrores + sumaLineaAsientosErrores).getCuentaContable());
      lista.add(resul);
      iterador++;
    }
    contVaciosErrores++;
    sumaLineaAsientosErrores += listaErrores.size();
    return lista;
  }

  private List<ResultadoErroresContable> ProcesarHashMapCorrectos(HashMap<Integer, Integer> listaAsientosCorrectos) {
    ResultadoErroresContable resul;
    List<ResultadoErroresContable> lista = new ArrayList<ResultadoErroresContable>();
    for (Entry<Integer, Integer> e : listaAsientosCorrectos.entrySet()) {
      resul = new ResultadoErroresContable();
      resul.setDiario(e.getKey() + "");
      resul.setLineaDiario(e.getValue() + "");
      resul.setLineaAsientoError(resultadoContable.get(e.getKey() + contVacios + sumaLineaAsientos).getCuentaContable());
      logger.info("[" + e.getKey() + "=" + e.getValue() + "]");
      lista.add(resul);
    }
    contVacios++;
    sumaLineaAsientos += listaAsientosCorrectos.size();
    return lista;
  }

  public String regresarAconsulta() {
    String retorno = null;
    retorno = "gotoAsientosContables";
    return retorno;
  }

  public String retornarAsientos() {
    String retorno = null;
    retorno = "gotoAsientosContables";
    return retorno;
  }

  /**
   * @return the listaProcesos
   */
  public List<JournalBean> getListaProcesos() {
    return listaProcesos;
  }

  /**
   * @param listaProcesos
   *          the listaProcesos to set
   */
  public boolean isComision() {
    return comision;
  }

  public void setComision(boolean comision) {
    this.comision = comision;
  }

  public void setListaProcesos(List<JournalBean> listaProcesos) {
    this.listaProcesos = listaProcesos;
  }

  public BigDecimal getValorComision() {
    return valorComision;
  }

  public void setValorComision(BigDecimal valorComision) {
    this.valorComision = valorComision;
  }

  public boolean isDetrac() {
    return comision;
  }

  public void setDetrac(boolean detrac) {
    this.comision = detrac;
  }

  public void generarTraza(List<SelectItem> lista, String name) {
    File f = new File(name);
    try {
      PrintWriter out = new PrintWriter(f);
      out.println("PRIMERA LINEA---");
      for (SelectItem selectItem : lista) {
        out.println("Cuenta: " + selectItem.getValue() + " ----- Label: " + selectItem.getLabel());
      }
      logger.info("Traza creada!");

      if (f.exists()) {
        // El archivo existe entonces lo abro
        Runtime.getRuntime().exec("rundll32 SHELL32.DLL,ShellExec_RunDLL " + f.getAbsolutePath());
        logger.error("NO ES POSIBLE ABRIR EL ARCHIVO DE REPORTE \n POSIBLEMENTE NO EXISTA");
      }
      out.close();
    } catch (Exception e) {
      logger.error("Error creando traza");
    }

  }

  public String getTipoPagoAsiento() {
    return tipoPagoAsiento;
  }

  public void setTipoPagoAsiento(String tipoPagoAsiento) {
    this.tipoPagoAsiento = tipoPagoAsiento;
  }
}
