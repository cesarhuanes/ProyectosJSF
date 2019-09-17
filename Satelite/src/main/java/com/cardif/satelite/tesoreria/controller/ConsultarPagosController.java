package com.cardif.satelite.tesoreria.controller;

import static com.cardif.satelite.constantes.ErrorConstants.ERROR_SYNCCON;
import static com.cardif.satelite.constantes.ErrorConstants.MSJ_ERROR;
import static com.cardif.satelite.constantes.ErrorConstants.MSJ_ERROR_GENERAL;

import java.io.File;
import java.io.PrintWriter;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.jfree.util.Log;
import org.richfaces.model.selection.SimpleSelection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.LoteCabecera;
import com.cardif.satelite.model.LoteFactura;
import com.cardif.satelite.model.LoteProveedor;
import com.cardif.satelite.tesoreria.bean.JournalBean;
import com.cardif.satelite.tesoreria.bean.ProveedorBean;
import com.cardif.satelite.tesoreria.bean.ResultadoErroresContable;
import com.cardif.satelite.tesoreria.service.DiarioServices;
import com.cardif.satelite.tesoreria.service.ProcesoPagos;
import com.cardif.satelite.util.SateliteUtil;
import com.cardif.sunsystems.bean.AsientoContableBean;
import com.cardif.sunsystems.util.ConstantesSun;
import com.cardif.sunsystems.util.Utilidades;

@Controller("consultarPagosController")
@Scope("request")
public class ConsultarPagosController implements Serializable {
  private static final long serialVersionUID = -8323560799344065774L;
  public static final Logger logger = Logger.getLogger(ConsultarPagosController.class);

  private volatile List<JournalBean> listaProcesos;
  private List<JournalBean> listaProcesosCopy;
  private List<ProveedorBean> listaMarcados = new ArrayList<ProveedorBean>();

  private List<JournalBean> listaPagados;
  private List<List<JournalBean>> splistaProcesos;
  private List<AsientoContableBean> resultadoContable;
  private List<ResultadoErroresContable> resultadoErroresContable;
  private List<ResultadoErroresContable> resultadoCorrectosContable;
  private List<SelectItem> marcadorItems;
  private List<SelectItem> tipoPagoItems;
  private List<SelectItem> medioPagoItems;
  private List<SelectItem> listaCtaBanco;
  private boolean estadoEditar;
  private boolean pasoValidaciones;

  private boolean desabAgregarRef = true;

  @Autowired(required = true)
  private ProcesoPagos procesoPagos;
  @Autowired
  private DiarioServices diarioServices = null;

  private String idProveedorFrom;
  private String idProveedorTo;
  private String fecPeriodoInicio;
  private String fecPeriodoFin;
  private String idMarcador;
  private Date fecPago;
  private Date fecTransaccion;
  private String numCuentaBanco;
  private String idTipoPago;
  private String idMedioPago;
  private String glosaPago;
  private String referencia;
  private String valEuroSoles;
  private String valDolarSoles;
  private String valEuroDolares;
  private SimpleSelection selection;
  private SimpleSelection selectionAsiento;
  private SimpleSelection selectionErrorAsiento;
  private SimpleSelection selectionCorrectosAsiento;
  private SimpleDateFormat fm = null;
  private JournalBean beanEditable;
  private int currentConsultaIndex;
  private String tipoPagoAsiento = "";
  private String indivOMasiv = "";

  // INICIO JARIASS SYNCCON 19/01/2015
  private String nroLote;
  private String opBanco;
  private String opComision;
  private String tipoDiario;
  private boolean detrac;
  private boolean desabLoteComision;
  private boolean desabEditar;
  private boolean desabActualizarM;
  private String tipoMedioPagoAux = "";
  private boolean desmarc;
  private boolean mostrarSelec;
  private boolean trataEditarVacio = false;

  private int contVacios = -1;
  private int sumaLineaAsientos = 0;

  private int contVaciosErrores = -1;
  private int sumaLineaAsientosErrores = 0;

  private boolean datosPagoObligRequerid = false;

  private boolean desabProcesar = true;

  // Filtros tabla:
  private String filtroProv = "";
  private String filtroRef = "";
  private String filtroNumDia = "";

  // Nro Lote:

  private String expresionLote;

  // FIN JARIASS SYNCCON 19/01/2015
  @PostConstruct
  public String inicio() {
    logger.info("Inicio");
    fm = new SimpleDateFormat(ConstantesSun.UTL_FORMATO_FECHA_SUNSYSTEMS);
    listaPagados = new ArrayList<JournalBean>();
    Calendar calendarFechaPago = Calendar.getInstance();
    Calendar calendaraFechaTransaccion = Calendar.getInstance();
    // Aqui se indica una fecha muy lejana para obligar a que se ingrese el tipo de cambio adecuado
    calendaraFechaTransaccion.set(2014, 1, 9);
    detrac = false;
    desabActualizarM = true;
    desabEditar = true;
    fecPago = calendarFechaPago.getTime();
    fecTransaccion = calendaraFechaTransaccion.getTime();

    try {

      tipoPagoItems = new ArrayList<SelectItem>();
      List<SelectItem> listaTipoPagoItems = procesoPagos.traerListaTipoPagoItems();
      for (SelectItem a : listaTipoPagoItems) {
        tipoPagoItems.add(a);
      }

      medioPagoItems = new ArrayList<SelectItem>();
      List<SelectItem> listaMedioPagoItems = procesoPagos.traerListaMedioPagoItems();
      for (SelectItem b : listaMedioPagoItems) {
        medioPagoItems.add(b);
      }

      marcadorItems = new ArrayList<SelectItem>();
      List<SelectItem> listaMarcadorItems = procesoPagos.traerListaMarcadorItems();
      for (SelectItem c : listaMarcadorItems) {
        marcadorItems.add(c);
      }

      listaCtaBanco = new ArrayList<SelectItem>();
      List<SelectItem> listaCtaBancos = procesoPagos.traerListaCtaBancos();

      for (SelectItem c : listaCtaBancos) {
        listaCtaBanco.add(c);
      }

      actualizarTiposCambio();
      selection = new SimpleSelection();
      selectionAsiento = new SimpleSelection();
      selectionErrorAsiento = new SimpleSelection();
      selectionCorrectosAsiento = new SimpleSelection();
      listaProcesos = new CopyOnWriteArrayList<JournalBean>();
      listaProcesosCopy = new ArrayList<JournalBean>();

      resultadoErroresContable = new ArrayList<ResultadoErroresContable>();
      resultadoCorrectosContable = new ArrayList<ResultadoErroresContable>();
    } catch (SyncconException ex) {
      logger.error("ERROR SYNCCON: " + ex.getMessageComplete());
      FacesMessage facesMsg = new FacesMessage(ex.getSeveridad(), ex.getMessage(), null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      logger.error("Hay una excepcion");
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, ErrorConstants.MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }
    logger.info("Fin");
    return null;
  }

  public String mostrarDetract() {
    String tipoPagoAsiento = getSelectedTipoPago();
    indivOMasiv = getSelectedIndivOmasiv();

    if (tipoPagoAsiento.equals("PAGOS DETRACCIONES")) {
      detrac = true;
      if (indivOMasiv.contains("MASIVO")) {
        desabLoteComision = false;
      } else {
        nroLote = "";
        expresionLote = "";
        opComision = "";
        desabLoteComision = true;
      }
    } else {
      detrac = false;
    }

    // Habilitar o Deshabilitar:
    if (mostrarSelec && (tipoPagoAsiento.equals(ConstantesSun.PAGOS_VARIOS) || tipoPagoAsiento.equals(ConstantesSun.PAGOS_RETENCION)) && indivOMasiv.equals("MASIVO")) {
      desabAgregarRef = false;
    } else {
      desabAgregarRef = true;
    }
    return null;
  }

  public String habilitarDetract() {
    String tipoDetrac = "";
    for (SelectItem marcador : tipoPagoItems) {
      if (marcador.getValue().equals(idTipoPago)) {
        tipoDetrac = marcador.getLabel();
        break;
      }
    }

    if (tipoDetrac.equals("MASIVO")) {
      desabLoteComision = false;
    } else {
      desabLoteComision = true;
    }
    return null;
  }

  public String limpiarFiltros() {
    logger.info("Inicio");
    String respuesta = null;
    try {
      setIdProveedorFrom((ConstantesSun.UTL_CHR_VACIO));
      setIdProveedorTo(ConstantesSun.UTL_CHR_VACIO);
      setFecPeriodoInicio(ConstantesSun.UTL_CHR_VACIO);
      setFecPeriodoFin(ConstantesSun.UTL_CHR_VACIO);
      setIdMarcador(ConstantesSun.UTL_CHR_VACIO);
      // setFecPago(NULO);
      // setFecTransaccion(NULO);
      setNumCuentaBanco(ConstantesSun.UTL_CHR_VACIO);
      setIdTipoPago(ConstantesSun.UTL_CHR_VACIO);
      setIdMedioPago(ConstantesSun.UTL_CHR_VACIO);
      setGlosaPago(ConstantesSun.UTL_CHR_VACIO);
      setReferencia(ConstantesSun.UTL_CHR_VACIO);
      selection = new SimpleSelection();
      listaProcesos = new ArrayList<JournalBean>();
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, ErrorConstants.MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }
    logger.info("Fin");
    return respuesta;
  }

  public String consultarProcesos() {
    desabProcesar = true;
    mostrarSelec = false;
    logger.info("Inicio");
    String respuesta = null;
    try {
      desabActualizarM = true;
      desabEditar = true;
      datosPagoObligRequerid = false;
      splistaProcesos = procesoPagos.consultarRegistros(getIdProveedorFrom(), getIdProveedorTo(), getFecPeriodoInicio(), getFecPeriodoFin());
      listaProcesos = ProcesarSuperlista(splistaProcesos);

      if (listaProcesos.size() != 0) {
        desabEditar = false;
        desabProcesar = false;
      } else {
        desabEditar = true;
        desabProcesar = true;
      }
      listaProcesosCopy = new ArrayList<JournalBean>(listaProcesos);
      datosPagoObligRequerid = true;
      // generarTraza(listaCtaBanco, "traza.txt");
      logger.info("Cuenta Banco: " + numCuentaBanco);
    } catch (Exception e) {
      logger.error("Hay un error");
      logger.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
      respuesta = "error";
    }
    logger.info("Fin");

    return respuesta;
  }

  private List<JournalBean> ProcesarSuperlista(List<List<JournalBean>> splistaProcesos2) {
    List<JournalBean> retorno = new ArrayList<JournalBean>();
    logger.info("Inicio");
    for (int i = 0; i < splistaProcesos2.size(); i++) {
      List<JournalBean> aa = splistaProcesos2.get(i);
      retorno.addAll(aa);
    }
    logger.info("Fin");
    return retorno;
  }

  public String enviarPagoSun() {
    logger.info("Inicio");
    listaPagados.clear();
    HashMap<Integer, String> listaErrores = new HashMap<Integer, String>();
    HashMap<Integer, Integer> listaAsientosCorrectos = new HashMap<Integer, Integer>();
    resultadoErroresContable = new ArrayList<ResultadoErroresContable>();
    resultadoCorrectosContable = new ArrayList<ResultadoErroresContable>();
    String retorno = null;
    nroLote = "";
    expresionLote = "";

    if (resultadoContable == null || resultadoContable.size() == 0) {
      logger.info("No hay asientos para procesar!");
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, "No hay asientos para procesar", null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
      return null;
    }

    String tipoPago = tipoMedioPagoAux;
    logger.info("Tipo Pago: " + tipoPago);
    try {
      HashMap<Integer, Object> resultadosValidacion = new HashMap<Integer, Object>();
      List<String> resultado = procesoPagos.enviarSun(resultadoContable, tipoPagoAsiento, resultadosValidacion, tipoPago, numCuentaBanco);

      if (resultadosValidacion.size() > 0) {

        resultadoErroresContable = ProcesarHashMapErrores(resultadosValidacion, "VALIDACION INTERNA");
        retorno = "gotoErrores";
        contVaciosErrores = -1;
        sumaLineaAsientosErrores = 0;
      } else {

        for (int i = 0; i < resultado.size(); i++) {
          listaErrores = procesoPagos.procesarResultadoSun(resultado.get(i));

          if (listaErrores.size() > 0) {
            resultadoErroresContable.addAll(ProcesarHashMapErroresSun(listaErrores, "ERRORES PROCESO SUN"));
            resultadoErroresContable.add(new ResultadoErroresContable());
          } else {
            listaAsientosCorrectos = procesoPagos.procesarResultadoSunExitoso(resultado.get(i));
            resultadoCorrectosContable.addAll(ProcesarHashMapCorrectos(listaAsientosCorrectos));
            resultadoCorrectosContable.add(new ResultadoErroresContable());
            logger.info(listaAsientosCorrectos);
            desabActualizarM = false;

          }
        }

        // Marcar como pagados
        logger.info("Marcando provisiones como marcadas: Allocation Marker = W y Estado de Asiento a PAGADO");
        for (JournalBean bean : listaProcesos) {
          if (bean.isbFlagPago()) {
            String resultadoMarcacion = procesoPagos.marcarLineasPagados(bean);
            // Metodo para actualizar estado de asiento provision con estado "PAGADO".
            Boolean actualizado = diarioServices.actualizaAsientoPagoRetencion(ConstantesSun.SSC_BusinessUnit_E01, String.valueOf(bean.getDiario()), ConstantesSun.EST_ASIENTO_PAGADO,
                ConstantesSun.UTL_CHR_VACIO);
            listaPagados.add(bean);
            logger.info("Resultado Marcacion Provision: " + resultadoMarcacion);
            logger.info("Resultado Estado Provision: " + actualizado);
          }
        }

        contVaciosErrores = -1;
        sumaLineaAsientosErrores = 0;
        contVacios = -1;
        sumaLineaAsientos = 0;
        if (resultadoErroresContable.size() > 0) {
          retorno = "gotoErroresSun";
        }
        if (listaAsientosCorrectos.size() > 0) {
          String auxTipoPago = getSelectedTipoPago();

          actualizarMarcados();

          if (indivOMasiv.equals("MASIVO") && (auxTipoPago.equals(ConstantesSun.PAGOS_VARIOS) || auxTipoPago.equals(ConstantesSun.PAGOS_RETENCION))) {
            if (validarMedioPagoMasivo()) {
              registrarNroLote(listaPagados, auxTipoPago);
            }

          }
          FacesContext context = FacesContext.getCurrentInstance();
          context.getExternalContext().getSessionMap().remove(ConstantesSun.MDP_FEL_CORRELATIVO_GENERADO);
          retorno = "gotoCorrectosSun";
        }
      }

      logger.info(retorno + " SE EJECUTO");
      logger.info("En el envio a SUN, se encontraron " + listaErrores.size() + " errores");
      logger.info("Fin");
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, ErrorConstants.MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }
    return retorno;
  }

  private void registrarNroLote(List<JournalBean> listaPagados, String tipoPago) {
    logger.info("Generando Lote");
    Iterator<JournalBean> iter = listaPagados.iterator();
    JournalBean aux = null;
    LoteProveedor loteProveedor = null;
    LoteFactura loteFactura = null;
    String rucAnterior = null;
    // Obtener secuencial:
    String codBanco = ConstantesSun.COD_BANCO_BBVA;
    String codMoneda = listaPagados.get(0).getMoneda();
    LoteCabecera loteCabecera = new LoteCabecera();
    loteCabecera.setBanco(codBanco);
    loteCabecera.setFec_creacion(new Date());
    loteCabecera.setFec_modificacion(null);
    loteCabecera.setFec_proceso(null);
    String usuario = SecurityContextHolder.getContext().getAuthentication().getName();
    loteCabecera.setUsu_creacion(usuario);
    loteCabecera.setMoneda(codMoneda);
    loteCabecera.setTipo_pago(tipoPago);
    loteCabecera.setUsu_modificacion(null);
    int secuencial = procesoPagos.selectMaxLoteSecuencial(tipoPago, codBanco, codMoneda);

    secuencial++;
    loteCabecera.setSecuencial(secuencial);

    String tipoPagoAbreviado = null;
    if (tipoPago.equals(ConstantesSun.PAGOS_VARIOS)) {
      tipoPagoAbreviado = "PV";
    } else if (tipoPago.equals(ConstantesSun.PAGOS_RETENCION)) {
      tipoPagoAbreviado = "PR";
    }

    String nomCuentaBanco = getSelectedNomCtaBanco().trim();
    nomCuentaBanco = nomCuentaBanco.substring(0, nomCuentaBanco.indexOf("CTA"));
    nomCuentaBanco = SateliteUtil.getTextoSinGuiones(nomCuentaBanco);
    loteCabecera.setCta_banco(nomCuentaBanco);

    expresionLote = tipoPagoAbreviado + codBanco + codMoneda + secuencial;
    loteCabecera.setNro_lote(expresionLote);
    logger.info("Nombre del Lote generado: " + expresionLote);

    procesoPagos.registrarLoteCabecera(loteCabecera);
    logger.info("La cabecera ha sido insertada");
    while (iter.hasNext()) {
      aux = iter.next();
      if (rucAnterior == null || !rucAnterior.equalsIgnoreCase(aux.getRuc())) {
        loteProveedor = new LoteProveedor();
        loteProveedor.setNro_lote(expresionLote);
        loteProveedor.setNom_proveedor(aux.getNombreProveedorCompleto());
        loteProveedor.setTip_doc_proveedor("R");
        loteProveedor.setNum_doc_proveedor(aux.getRuc() + "");
        procesoPagos.registrarLoteProveedor(loteProveedor);
        logger.info("Un nuevo Proveedor ha sido insertado");
        rucAnterior = aux.getRuc();
      }
      BigDecimal importe = Utilidades.redondear(2, new BigDecimal(aux.getImpTransaccionAmount()));

      loteFactura = new LoteFactura();
      loteFactura.setCuenta_contable(aux.getCodigoCuenta());

      loteFactura.setImporte(importe);

      loteFactura.setNro_lote(expresionLote);
      loteFactura.setNum_doc_proveedor(loteProveedor.getNum_doc_proveedor());
      loteFactura.setTip_cod_sunat(aux.getComprobanteSunat());
      loteFactura.setReferencia(aux.getReferencia());
      procesoPagos.registrarLoteFactura(loteFactura);

      logger.info("Una factura ha sido insertado");

    }

    logger.info("El lote ha sido generado: " + expresionLote);
    expresionLote = "        Nro Lote generado: " + expresionLote;
    SateliteUtil.mostrarMensaje("Lote Generado Correctamente");
  }

  ConstantesSun p;

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

  private List<ResultadoErroresContable> ProcesarHashMapErroresSun(HashMap<Integer, String> listaErrores, String origen) {
    Iterator<Entry<Integer, String>> it = listaErrores.entrySet().iterator();
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

  private List<ResultadoErroresContable> ProcesarHashMapErrores(HashMap<Integer, Object> resultadosValidacion, String origen) {

    Iterator<Entry<Integer, Object>> it = resultadosValidacion.entrySet().iterator();
    List<ResultadoErroresContable> lista = new ArrayList<ResultadoErroresContable>();
    ResultadoErroresContable resul;
    Map.Entry e;
    Map.Entry e1;
    int iterador = 1;
    while (it.hasNext()) {
      e = (Map.Entry) it.next();
      logger.info("[" + e.getKey() + "=" + e.getValue());
      HashMap<String, BigDecimal> tmp = (HashMap<String, BigDecimal>) e.getValue();
      Iterator it2 = tmp.entrySet().iterator();
      while (it2.hasNext()) {
        e1 = (Map.Entry) it2.next();
        resul = new ResultadoErroresContable();
        resul.setLineaAsientoError(e1.getKey() + "");
        resul.setMensajeAsientoError(e1.getValue() + "");
        resul.setProcedenciaError(origen);
        resul.setLineaAsientoError(resultadoContable.get(iterador + contVacios + sumaLineaAsientos).getCuentaContable());
        lista.add(resul);
        iterador++;
      }
      lista.add(new ResultadoErroresContable());
    }
    contVaciosErrores++;
    sumaLineaAsientosErrores += resultadosValidacion.size();
    return lista;
  }

  public String cancelaModificacion() {
    logger.info("Inicio");
    String respuesta = null;
    return respuesta;
  }

  public String actualizar() {
    logger.info("Inicio");
    String respuesta = null;
    try {
      Iterator<Object> iterator = getSelection().getKeys();
      if (!iterator.hasNext()) {
        trataEditarVacio = true;
        String msg = "No ha seleccionado ningun registro";
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, msg, null);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
        return null;
      }
      trataEditarVacio = false;
      beanEditable = null;
      beanEditable = new JournalBean();
      if (iterator.hasNext()) {
        int indice_modificar = (Integer) iterator.next();
        beanEditable = listaProcesos.get(indice_modificar);
        logger.info("beanEditable " + beanEditable.getReferencia());

      }
      logger.info("Fin ");
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "No ha seleccionado ning�n registro", null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }
    return respuesta;
  }

  public String obtenerAsiento() {
    pasoValidaciones = false;
    int contMarcados = 0;
    logger.info("Inicio");
    String respuesta = null;
    Calendar fechaPago = Calendar.getInstance();
    resultadoContable = new ArrayList<AsientoContableBean>();

    // Validando obligatorios:
    if (!validarCuentas()) {
      enviarMensaje("No ha seleccionado CTA de banco");
      return null;
    } else if (!validarTipoPago()) {
      enviarMensaje("No ha seleccionado el marcador");
      return null;
    } else if (!validarMedioPago()) {
      enviarMensaje("No ha seleccionado el medio de pago");
      return null;
    } else if (!validarIndividualOMasivo()) {
      enviarMensaje("Debe seleccionar Individual o Masivo");
      return null;
    }

    // RECORTAR GLOSA
    glosaPago = getGlosaRecortada(glosaPago);

    for (JournalBean bean : listaProcesos) {
      if (bean.isbFlagPago()) {
        contMarcados++;
      }
    }
    logger.info("Contador seleccionados: " + contMarcados);
    if (contMarcados == 0) {
      logger.info("No ha seleccionado!");
      enviarMensaje("No ha seleccionado ninguna provision");
      desabProcesar = false;
      return null;
    }

    else if (!validarPagosMasivos()) {
      enviarMensaje("Agregue referencias masivas");
      return null;
    }

    indivOMasiv = getSelectedIndivOmasiv();
    try {
      List<List<JournalBean>> superlista = new ArrayList<List<JournalBean>>();
      List<JournalBean> lineas_agrupadas = null;
      String socioProducto = "";
      for (JournalBean aa : listaProcesos) {

        boolean salta = false;
        if (aa.isbFlagPago()) {
          // Solo mando a procesar las marcadas
          if (socioProducto.equals(aa.getRuc() + "")) {
            lineas_agrupadas.add(aa);
            socioProducto = aa.getRuc() + "";
            salta = false;
          } else {
            lineas_agrupadas = new ArrayList<JournalBean>();
            lineas_agrupadas.add(aa);
            socioProducto = aa.getRuc() + "";
            salta = true;
          }
          if (salta) {
            superlista.add(lineas_agrupadas);
          }
        }
      }

      if (!getIdMarcador().equals("")) {

        // INICIO JARIASS SYNCCON 19/01/2015
        tipoPagoAsiento = getSelectedTipoPago();

        if (indivOMasiv.equals("MASIVO") && tipoPagoAsiento.equals(ConstantesSun.PAGOS_DETRACCION)) {
          tipoPagoAsiento = ConstantesSun.PAGOS_DETRACCION_MASIVA;
        }

        if (!validarPagosSiniestros()) {
          enviarMensaje("Solo puede realizar Pagos de Siniestros Individuales");
          return null;
        } else if (!validarPagosIndividuales(superlista)) {
          enviarMensaje("En pagos individuales, solo se pueden elegir facturas o siniestros de un proveedor");
          return null;
        } else if (!validarMonedasDetracciones()) {
          enviarMensaje("Las detracciones se pagan en soles (PEN)");
          return null;
        } else if (!validarMonedasOtrosPagos(superlista)) {
          enviarMensaje("Moneda de la cta Banco distinta a moneda de facturas");
          return null;
        }

        fechaPago.setTime(getFecPago());

        tipoMedioPagoAux = getSelectedMedioPago();

        String medioPagoAnalisisCode10 = CalculaMedioPago(tipoMedioPagoAux);

        procesoPagos.setLoteAndOp(nroLote, opBanco, opComision);
        logger.info("Num cta Banco: Seleccionada: " + getNumCuentaBanco());

        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        fecTransaccion = format.parse(format.format(fecTransaccion));
        fecPago = format.parse(format.format(fecPago));
        resultadoContable = procesoPagos.procesarPago(superlista, tipoPagoAsiento, fecPago, Float.parseFloat(valEuroSoles), Float.parseFloat(valEuroDolares), Float.parseFloat(valDolarSoles),
            getNumCuentaBanco(), glosaPago, referencia, medioPagoAnalisisCode10, fecTransaccion);
        tipoDiario = procesoPagos.getTipoDiario();

      }

      List<Integer> lsitaElim = new ArrayList<Integer>();
      String glosaDetrac = "";
      boolean nuevoProveedor = false;

      logger.info("RESULTADOS FINALES: " + resultadoContable.size());
      for (int i = 0; i < resultadoContable.size(); i++) {

        if (resultadoContable.get(i).getCuentaContable() == null) {
          lsitaElim.add(resultadoContable.indexOf(resultadoContable.get(i)));

          if (tipoPagoAsiento.equals(ConstantesSun.PAGOS_DETRACCION) && i + 1 < resultadoContable.size()) {
            nuevoProveedor = true;
            glosaDetrac = "PAGO DETRACCION " + resultadoContable.get(i + 1).getReferencia();
          }
          continue;
        }
      }

      // CAMBIOS FINALES
      String moneda = "";
      List<Integer> nuevasLineas = new ArrayList<Integer>();
      if (tipoPagoAsiento.equals(ConstantesSun.PAGOS_DETRACCION)) {
        // Eliminando Lineas vacias
        for (int i = 0; i < lsitaElim.size(); i++) {
          resultadoContable.remove(lsitaElim.get(i).intValue());
        }

        for (int j = 0; j < resultadoContable.size(); j++) {
          if (resultadoContable.get(j).getCuentaContable() != null && resultadoContable.get(j).getCuentaContable().startsWith("P")) {
            moneda = resultadoContable.get(j).getMoneda();

            String glosa = "PAGO DETRACCION " + resultadoContable.get(j).getReferencia();
            resultadoContable.get(j).setGlosa(glosa);
            if (j + 1 < resultadoContable.size()) {
              resultadoContable.get(j + 1).setGlosa(glosa);
            }

            if (j - 2 >= 0 && !resultadoContable.get(j).getCuentaContable().equals(resultadoContable.get(j - 2).getCuentaContable())) {
              nuevasLineas.add(j);
            }

          } else {
            if (moneda.equals("USD")) {
              resultadoContable.get(j).setImporteTransaccion(resultadoContable.get(j).getImporteSoles());
            }
          }

        }

        for (int j = nuevasLineas.size() - 1; j >= 0; j--) {

          resultadoContable.add(nuevasLineas.get(j).intValue(), new AsientoContableBean());
        }
      }

      else if (tipoPagoAsiento.equals(ConstantesSun.PAGOS_DETRACCION_MASIVA)) {
        BigDecimal suma10Soles = new BigDecimal(0);
        BigDecimal suma10Transac = new BigDecimal(0);
        BigDecimal suma10Euros = new BigDecimal(0);

        for (int i = 0; i < lsitaElim.size(); i++) {
          resultadoContable.remove(lsitaElim.get(i).intValue());
        }
        String provEmpleado = "";
        int cont = 0;
        for (int j = 0; j < resultadoContable.size(); j++) {
          if (resultadoContable.get(j).getCuentaContable().startsWith("P")) {
            suma10Soles = suma10Soles.add(resultadoContable.get(j).getImporteSoles());
            suma10Transac = suma10Transac.add(resultadoContable.get(j).getImporteTransaccion());
            suma10Euros = suma10Euros.add(resultadoContable.get(j).getImporteEuros());
            provEmpleado = resultadoContable.get(j).getCuentaContable();
            resultadoContable.get(j).setProveedorEmpleado(provEmpleado);
            cont = 0;
          } else if (resultadoContable.get(j).getCuentaContable().startsWith("10")) {
            if (cont == 0) {
              suma10Soles = suma10Soles.abs().negate();
              suma10Transac = suma10Transac.abs().negate();
              suma10Euros = suma10Euros.abs().negate();
              resultadoContable.get(j).setImporteSoles(suma10Soles);
              resultadoContable.get(j).setImporteTransaccion(suma10Transac);
              resultadoContable.get(j).setImporteEuros(suma10Euros);

              if (resultadoContable.get(0).getMoneda().equals("USD"))
                resultadoContable.get(j).setImporteTransaccion(suma10Soles);

            }
            cont++;
            continue;
          } else if (resultadoContable.get(j).getCuentaContable().startsWith("47")) {
            resultadoContable.get(j).setProveedorEmpleado(provEmpleado);
          }

        }
      }

      else if (tipoPagoAsiento.equals(ConstantesSun.PAGOS_SINIESTROS)) {
        List<Integer> eliminar = new ArrayList<Integer>();

        for (int j = 0; j < resultadoContable.size(); j++) {
          if (resultadoContable.get(j).getCuentaContable() != null && resultadoContable.get(j).getGlosa() != null
              && !(resultadoContable.get(j).getCuentaContable().startsWith("26") && !resultadoContable.get(j).getGlosa().equalsIgnoreCase("Diferencia de Cambio"))
              && !resultadoContable.get(j).getCuentaContable().startsWith("10")) {
            eliminar.add(j);
          }

          if (resultadoContable.get(j).getGlosa() != null) {
            resultadoContable.get(j).setGlosa(getGlosaRecortada(resultadoContable.get(j).getGlosa()));
          }

        }

        for (int j = eliminar.size() - 1; j >= 0; j--) {
          resultadoContable.remove(eliminar.get(j).intValue());
        }

      } else if (tipoPagoAsiento.equals(ConstantesSun.PAGOS_VARIOS) && indivOMasiv.equals("MASIVO")) {
        Log.info("Insertando glosas y referencias PV Masivos");
        Iterator<ProveedorBean> iter = listaMarcados.iterator();
        ProveedorBean entryBean = null;
        while (iter.hasNext()) {
          entryBean = iter.next();
          for (AsientoContableBean bean : resultadoContable) {
            if (bean.getCtaProveedor() != null && bean.getCtaProveedor().equalsIgnoreCase(entryBean.getRuc())) {
              if (bean.getCuentaContable() != null && bean.getCuentaContable().startsWith("P") && !bean.getGlosa().equals("Diferencia de Cambio")) {
                bean.setGlosa(getGlosaRecortada(entryBean.getGlosa()));
              }
              if (bean.getCuentaContable() != null && bean.getCuentaContable().startsWith("10")) {
                bean.setGlosa(getGlosaRecortada(entryBean.getGlosa()));
                bean.setReferencia(entryBean.getReferencia());
                break;
              }

            }
            // ----------
          }
          // ///
        }

      }

      else if (tipoPagoAsiento.equals(ConstantesSun.PAGOS_RETENCION) && indivOMasiv.equals("MASIVO")) {
        Iterator<ProveedorBean> iter = listaMarcados.iterator();
        ProveedorBean entryBean = null;
        Log.info("Insertando glosas y referencias P Retenc Masivos");
        // ////////////
        while (iter.hasNext()) {
          entryBean = iter.next();
          // ///
          for (AsientoContableBean bean : resultadoContable) {

            if (bean.getCtaProveedor() != null && bean.getCtaProveedor().equalsIgnoreCase(entryBean.getRuc())) {
              if (bean.getCuentaContable() != null && bean.getCuentaContable().startsWith("P") && !bean.getGlosa().equals("Diferencia de Cambio")) {
                bean.setGlosa(getGlosaRecortada(entryBean.getGlosa()));
              }
              if (bean.getCuentaContable() != null && (bean.getCuentaContable().startsWith("10") || bean.getCuentaContable().startsWith("20"))) {
                bean.setGlosa(getGlosaRecortada(entryBean.getGlosa()));
                bean.setReferencia(entryBean.getReferencia());

                if (bean.getCuentaContable().startsWith("10")) {
                  break;
                }
              }

            }
            // ----------
          }
          // ///
        }

      }

      logger.info("El asiento contable tiene la dimension de " + resultadoContable.size());
    } catch (SyncconException ex) {
      logger.error("ERROR SYNCCON: " + ex.getMessageComplete());
      FacesMessage facesMsg = new FacesMessage(ex.getSeveridad(), ex.getMessage(), null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    } catch (NoSuchElementException ex) {
      resultadoContable = new ArrayList<AsientoContableBean>();
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Agregue referencias masivas", null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, ErrorConstants.MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }
    logger.info("Fin");
    pasoValidaciones = true;
    return respuesta;
  }

  private String getGlosaRecortada(String glosa) {
    String result = glosa;
    if (result.length() > 50) {
      result = glosa.substring(0, 50);
    }
    return result;
  }

  private String CalculaMedioPago(String medioPago) {
    String retorno;
    if (medioPago.contains("CHEQUE") || medioPago.contains("CAJA CHICA")) {
      retorno = "007";
    } else if (medioPago.contains("PAGO ELECTRONICO")) {
      retorno = "003";
    } else {
      retorno = "003";
    }
    return retorno;
  }

  public String almacenarCambios() {
    logger.info("Inicio");
    // Iterator<Object> iterator = getSelection().getKeys();
    try {
      if (beanEditable.getFlagPago().equals("P")) {
        beanEditable.setbFlagPago(true);
      } else if (beanEditable.getFlagPago().equals("")) {
        beanEditable.setbFlagPago(false);
      } else {
        beanEditable.setbFlagPago(false);
        beanEditable.setFlagPago("");
        throw new SyncconException(ErrorConstants.COD_ERROR_BD_INTEGRIDAD);
      }
      listaProcesos.set(listaProcesos.indexOf(beanEditable), beanEditable);
      logger.info("Nuevo Nro Contancia: " + listaProcesos.get(listaProcesos.indexOf(beanEditable)).getNumeroConstancia());
    } catch (SyncconException ex) {
      logger.error(ERROR_SYNCCON + ex.getMessageComplete());
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

  public String actualizarMarcados() {

    for (JournalBean bean : listaPagados) {
      listaProcesosCopy.remove(bean);
      listaProcesos.remove(bean);
    }
    return null;
  }

  public String editarReg(ValueChangeEvent event) {
    logger.info("Inicio");
    String respuesta = "";
    try {
      Iterator<Object> iterator = getSelection().getKeys();

      if (iterator.hasNext()) {
        int indice_modificar = (Integer) iterator.next();

        check(indice_modificar, event);
      }

    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
      respuesta = MSJ_ERROR;
    }
    logger.info("Fin");
    return respuesta;
  }

  public String regresarAconsulta() {
    String retorno = null;
    retorno = "gotoAsientosContables";
    return retorno;
  }

  public String onSelectOneBooleanChange() {
    filtroProv = "";
    filtroRef = "";
    filtroNumDia = "";
    if (desmarc) {
      for (JournalBean bean : listaProcesos) {
        if (bean.isbFlagPago()) {
          bean.setbFlagPago(false);
        }
      }
    }
    desmarc = false;
    return null;
  }

  public String onSelectOneSeleccionados() {
    filtroProv = "";
    filtroRef = "";
    filtroNumDia = "";
    // String provAnterior = null;
    listaMarcados = new ArrayList<ProveedorBean>();

    if (mostrarSelec) {
      listaProcesos = new ArrayList<JournalBean>();

      Iterator<JournalBean> iterat = listaProcesosCopy.iterator();

      while (iterat.hasNext()) {
        JournalBean bean = iterat.next();

        if (bean.isbFlagPago()) {
          listaProcesos.add(bean);
        }
      }

      // Habilitar o Dasb
      tipoPagoAsiento = getSelectedTipoPago();
      indivOMasiv = getSelectedIndivOmasiv();
      if ((tipoPagoAsiento.equals(ConstantesSun.PAGOS_VARIOS) || tipoPagoAsiento.equals(ConstantesSun.PAGOS_RETENCION)) && indivOMasiv.equals("MASIVO")) {
        desabAgregarRef = false;
      } else {
        desabAgregarRef = true;
      }
    } else {
      listaProcesos = new ArrayList<JournalBean>(listaProcesosCopy);
      desabAgregarRef = true;
    }

    return null;
  }

  public String agregarReferencias() {
    String provAnterior = null;
    listaMarcados = new ArrayList<ProveedorBean>();

    Iterator<JournalBean> iterat = listaProcesosCopy.iterator();

    while (iterat.hasNext()) {
      JournalBean bean = iterat.next();
      String ruc = null;
      if (bean.isbFlagPago()) {
        ruc = bean.getRuc() + "";
        if (provAnterior == null || !ruc.equalsIgnoreCase(provAnterior)) {
          listaMarcados.add(new ProveedorBean(ruc, bean.getNombreProveedor()));
          provAnterior = ruc;
        }
      }
    }

    return null;
  }

  public String cambiarGlosas() {
    for (ProveedorBean bean : listaMarcados) {
      bean.setGlosa(bean.getReferencia() + bean.getGlosaInicial());
    }
    return null;
  }

  public List<ProveedorBean> getListaMarcados() {
    return listaMarcados;
  }

  public void setListaMarcados(List<ProveedorBean> listaMarcados) {
    this.listaMarcados = listaMarcados;
  }

  private void enviarMensaje(String mensaje) {
    FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, mensaje, null);
    FacesContext.getCurrentInstance().addMessage(null, facesMsg);
  }

  private boolean validarCuentas() {
    if (numCuentaBanco.length() == 0) {
      logger.info("No ha seleccionado CTA de Banco");
      return false;
    }
    return true;
  }

  private String getSelectedTipoPago() {
    String tipoPagoSelected = "";
    for (SelectItem selectItem : marcadorItems) {
      if (selectItem.getValue().equals(idMarcador)) {
        tipoPagoSelected = selectItem.getLabel();
        break;
      }
    }
    return tipoPagoSelected;
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

  private boolean validarTipoPago() {
    if (idMarcador.length() == 0) {
      logger.info("No ha seleccionado Tipo de Pago");
      return false;
    }
    return true;
  }

  private boolean validarMedioPago() {
    if (idMedioPago.length() == 0) {
      logger.info("No ha seleccionado Medio de Pago");
      return false;
    }
    return true;
  }

  private boolean validarIndividualOMasivo() {

    if (idTipoPago.length() == 0) {
      return false;
    }
    return true;
  }

  private boolean validarMonedasDetracciones() {
    String mon = getMoneda(numCuentaBanco);

    if ((tipoPagoAsiento.equals(ConstantesSun.PAGOS_DETRACCION) || tipoPagoAsiento.equals(ConstantesSun.PAGOS_DETRACCION_MASIVA)) && mon.equalsIgnoreCase("USD")) {
      return false;
    }
    return true;
  }

  private boolean validarMonedasOtrosPagos(List<List<JournalBean>> superlista) {
    String mon = getMoneda(numCuentaBanco);

    if (!tipoPagoAsiento.equals(ConstantesSun.PAGOS_DETRACCION) && !tipoPagoAsiento.equals(ConstantesSun.PAGOS_DETRACCION_MASIVA)) {
      for (List<JournalBean> list : superlista) {
        if (!list.get(0).getMoneda().equals(mon)) {
          return false;
        }
      }
    }
    return true;
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

  private boolean validarPagosSiniestros() {
    if (getSelectedTipoPago().equals(ConstantesSun.PAGOS_SINIESTROS) && !indivOMasiv.equals("INDIVIDUAL")) {
      return false;
    }
    return true;
  }

  private boolean validarPagosIndividuales(List<List<JournalBean>> superlista) {
    if ((getSelectedTipoPago().equals(ConstantesSun.PAGOS_VARIOS) || getSelectedTipoPago().equals(ConstantesSun.PAGOS_RETENCION) || getSelectedTipoPago().equals(ConstantesSun.PAGOS_SINIESTROS))
        && indivOMasiv.equals("INDIVIDUAL") && superlista.size() > 1) {
      return false;
    }
    return true;
  }

  private boolean validarMedioPagoMasivo() {
    String auxMedioPago = getSelectedMedioPago();
    if ((getSelectedTipoPago().equals(ConstantesSun.PAGOS_VARIOS) || getSelectedTipoPago().equals(ConstantesSun.PAGOS_RETENCION)) && indivOMasiv.equals("MASIVO")) {
      if (!auxMedioPago.equalsIgnoreCase("PAGO ELECTRONIC")) {
        return false;
      }
    }
    return true;
  }

  private boolean validarPagosMasivos() {
    String auxTipoPago = getSelectedTipoPago();
    if (indivOMasiv.equals("MASIVO") && listaMarcados.size() == 0 && (auxTipoPago.equals(ConstantesSun.PAGOS_VARIOS) || auxTipoPago.equals(ConstantesSun.PAGOS_RETENCION))) {
      return false;
    }
    return true;
  }

  public String retornarAsientos() {
    String retorno = null;
    retorno = "gotoAsientosContables";
    return retorno;
  }

  public String actualizarReferencias() {
    logger.info("....Actualizando referencias....");
    Iterator<ProveedorBean> iter = listaMarcados.iterator();

    while (iter.hasNext()) {
      ProveedorBean bean = iter.next();
    }

    return null;
  }

  public String actualizarTiposCambio() {
    HashMap<String, BigDecimal> lista_val = null;
    try {
      lista_val = procesoPagos.traerTipoCambioActual(fm.format(fecTransaccion));

      if (lista_val instanceof HashMap) {

        if (lista_val.get("codigo").compareTo(new BigDecimal("0")) == 0) {
          valEuroSoles = lista_val.get("eur-pen").toString();
          valDolarSoles = lista_val.get("usd-pen").toString();
          valEuroDolares = lista_val.get("eur-usd").toString();
        } else {
          valEuroSoles = "";
          valDolarSoles = "";
          valEuroDolares = "";
          logger.info("Falla en tipos cambios");
          enviarMensaje("No hay tipos de cambio para esta fecha");
        }
      }
    } catch (Exception e) {
      logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
      logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
    }
    return null;
  }

  private synchronized void check(int cod, ValueChangeEvent event) throws Exception {
    boolean nuevo = ((Boolean) event.getNewValue()).booleanValue();
    boolean antiguo = ((Boolean) event.getOldValue()).booleanValue();
    if (nuevo != antiguo) {
      logger.error(event.getOldValue() + " --- " + event.getNewValue());
      JournalBean bean;
      bean = listaProcesos.get(cod);
      boolean flag = ((Boolean) event.getNewValue()).booleanValue();
      if (flag == true) {
        bean.setFlagPago("P");
        bean.setbFlagPago(true);
      } else {
        bean.setFlagPago("");
        bean.setbFlagPago(false);
      }
      listaProcesos.set(cod, bean);
    }
  }

  String filtroProvAnt = "";
  String filtroRefAnt = "";
  String filtroNumDiaAnt = "";

  public String filtrar() {
    synchronized (listaProcesos) {
      logger.info("*****Empieza metodo Filtrar()");

      if (listaProcesosCopy.size() == 0) {
        return null;
      }

      filtroProv = filtroProv.toUpperCase();
      filtroRef = filtroRef.toUpperCase();
      filtroNumDia = filtroNumDia.toUpperCase();

      if (filtroProv.equalsIgnoreCase(filtroProvAnt) && filtroRef.equalsIgnoreCase(filtroRefAnt) && filtroNumDia.equalsIgnoreCase(filtroNumDiaAnt)) {
        return null;
      }

      listaProcesos.clear();
      Iterator<JournalBean> iterat = listaProcesosCopy.iterator();

      List<JournalBean> listaAux = new CopyOnWriteArrayList<JournalBean>();

      while (iterat.hasNext()) {
        JournalBean bean = iterat.next();
        // Si filtro, desmarco
        bean.setbFlagPago(false);

        // Los casos:
        if (filtroProv.length() > 0) {
          if (filtroRef.length() > 0 && filtroNumDia.length() > 0) {
            if (bean.getNombreProveedor() != null && bean.getNombreProveedor().contains(filtroProv) && bean.getReferencia() != null && bean.getReferencia().contains(filtroRef) && bean.getDiario() != 0
                && (bean.getDiario() + "").contains(filtroNumDia)) {
              listaAux.add(bean);
            }
          } else if (filtroRef.length() > 0 && filtroNumDia.length() == 0) {
            if (bean.getNombreProveedor() != null && bean.getNombreProveedor().contains(filtroProv) && bean.getReferencia() != null && bean.getReferencia().contains(filtroRef)) {
              listaAux.add(bean);
            }
          } else if (filtroRef.length() == 0 && filtroNumDia.length() > 0) {
            if (bean.getNombreProveedor() != null && bean.getNombreProveedor().contains(filtroProv) && bean.getDiario() != 0 && (bean.getDiario() + "").contains(filtroNumDia)) {
              listaAux.add(bean);
            }
          } else {
            if (bean.getNombreProveedor() != null && bean.getNombreProveedor().contains(filtroProv)) {
              listaAux.add(bean);
            }
          }
        }

        else {
          if (filtroRef.length() > 0 && filtroNumDia.length() > 0) {
            if (bean.getReferencia() != null && bean.getReferencia().contains(filtroRef) && bean.getDiario() != 0 && (bean.getDiario() + "").contains(filtroNumDia)) {
              listaAux.add(bean);
            }
          } else if (filtroRef.length() > 0 && filtroNumDia.length() == 0) {
            if (bean.getReferencia() != null && bean.getReferencia().contains(filtroRef)) {
              listaAux.add(bean);
            }
          } else if (filtroRef.length() == 0 && filtroNumDia.length() > 0) {
            if (bean.getDiario() != 0 && (bean.getDiario() + "").contains(filtroNumDia)) {
              listaAux.add(bean);
            }
          } else {
            listaAux = new ArrayList<JournalBean>(listaProcesosCopy);
            // listaProcesosCopy = null;

            break;
          }
        }

      }
      listaProcesos.addAll(listaAux);

      filtroProvAnt = filtroProv + "";
      filtroRefAnt = filtroRef + "";
      filtroNumDiaAnt = filtroNumDia + "";
      mostrarSelec = false;
      logger.info(".......Terminando de Filtrar");
      return null;
    }
  }

  private String getSelectedIndivOmasiv() {
    String indivOMasiv = null;
    for (SelectItem marcador : tipoPagoItems) {
      if (marcador.getValue().equals(idTipoPago)) {
        indivOMasiv = marcador.getLabel();
        break;
      }
    }
    return indivOMasiv;
  }

  private String getSelectedNomCtaBanco() {
    String nomCtaBanco = null;
    for (SelectItem marcador : listaCtaBanco) {
      if (marcador.getValue().equals(numCuentaBanco)) {
        nomCtaBanco = marcador.getLabel();
        break;
      }
    }
    return nomCtaBanco;
  }

  /**
   * @return the idProveedorFrom
   */
  public String getIdProveedorFrom() {
    return idProveedorFrom;
  }

  /**
   * @param idProveedorFrom
   *          the idProveedorFrom to set
   */
  public void setIdProveedorFrom(String idProveedorFrom) {
    this.idProveedorFrom = idProveedorFrom;
  }

  /**
   * @return the idProveedorTo
   */
  public String getIdProveedorTo() {
    return idProveedorTo;
  }

  /**
   * @param idProveedorTo
   *          the idProveedorTo to set
   */
  public void setIdProveedorTo(String idProveedorTo) {
    this.idProveedorTo = idProveedorTo;
  }

  /**
   * @return the fecPeriodoInicio
   */
  public String getFecPeriodoInicio() {
    return fecPeriodoInicio;
  }

  /**
   * @param fecPeriodoInicio
   *          the fecPeriodoInicio to set
   */
  public void setFecPeriodoInicio(String fecPeriodoInicio) {
    this.fecPeriodoInicio = fecPeriodoInicio;
  }

  /**
   * @return the fecPeriodoFin
   */
  public String getFecPeriodoFin() {
    return fecPeriodoFin;
  }

  /**
   * @param fecPeriodoFin
   *          the fecPeriodoFin to set
   */
  public void setFecPeriodoFin(String fecPeriodoFin) {
    this.fecPeriodoFin = fecPeriodoFin;
  }

  /**
   * @return the idMarcador
   */
  public String getIdMarcador() {
    return idMarcador;
  }

  /**
   * @param idMarcador
   *          the idMarcador to set
   */
  public void setIdMarcador(String idMarcador) {
    this.idMarcador = idMarcador;
  }

  /**
   * @return the fecPago
   */
  public Date getFecPago() {
    return fecPago;
  }

  /**
   * @param fecPago
   *          the fecPago to set
   */
  public void setFecPago(Date fecPago) {
    this.fecPago = fecPago;
  }

  public Date getFecTransaccion() {
    return fecTransaccion;
  }

  public void setFecTransaccion(Date fecTransaccion) {
    this.fecTransaccion = fecTransaccion;
  }

  /**
   * @return the numCuentaBanco
   */
  public String getNumCuentaBanco() {
    return numCuentaBanco;
  }

  /**
   * @param numCuentaBanco
   *          the numCuentaBanco to set
   */
  public void setNumCuentaBanco(String numCuentaBanco) {
    this.numCuentaBanco = numCuentaBanco;
  }

  /**
   * @return the idTipoPago
   */
  public String getIdTipoPago() {
    return idTipoPago;
  }

  /**
   * @param idTipoPago
   *          the idTipoPago to set
   */
  public void setIdTipoPago(String idTipoPago) {
    this.idTipoPago = idTipoPago;
  }

  /**
   * @return the idMedioPago
   */
  public String getIdMedioPago() {
    return idMedioPago;
  }

  /**
   * @param idMedioPago
   *          the idMedioPago to set
   */
  public void setIdMedioPago(String idMedioPago) {
    this.idMedioPago = idMedioPago;
  }

  /**
   * @return the glosaPago
   */
  public String getGlosaPago() {
    return glosaPago;
  }

  /**
   * @param glosaPago
   *          the glosaPago to set
   */
  public void setGlosaPago(String glosaPago) {
    this.glosaPago = glosaPago;
  }

  /**
   * @return the listaProcesos
   */
  public synchronized List<JournalBean> getListaProcesos() {
    return new CopyOnWriteArrayList<JournalBean>(listaProcesos);
  }

  /**
   * @param listaProcesos
   *          the listaProcesos to set
   */
  public void setListaProcesos(List<JournalBean> listaProcesos) {
    this.listaProcesos = listaProcesos;
  }

  /**
   * @return the marcadorItems
   */
  public List<SelectItem> getMarcadorItems() {
    return marcadorItems;
  }

  /**
   * @param marcadorItems
   *          the marcadorItems to set
   */
  public void setMarcadorItems(List<SelectItem> marcadorItems) {
    this.marcadorItems = marcadorItems;
  }

  /**
   * @return the tipoPagoItems
   */
  public List<SelectItem> getTipoPagoItems() {
    return tipoPagoItems;
  }

  /**
   * @param tipoPagoItems
   *          the tipoPagoItems to set
   */
  public void setTipoPagoItems(List<SelectItem> tipoPagoItems) {
    this.tipoPagoItems = tipoPagoItems;
  }

  /**
   * @return the valEuroSoles
   */
  public String getValEuroSoles() {
    return valEuroSoles;
  }

  /**
   * @param valEuroSoles
   *          the valEuroSoles to set
   */
  public void setValEuroSoles(String valEuroSoles) {
    this.valEuroSoles = valEuroSoles;
  }

  /**
   * @return the valDolarSoles
   */
  public String getValDolarSoles() {
    return valDolarSoles;
  }

  /**
   * @param valDolarSoles
   *          the valDolarSoles to set
   */
  public void setValDolarSoles(String valDolarSoles) {
    this.valDolarSoles = valDolarSoles;
  }

  /**
   * @return the valEuroDolares
   */
  public String getValEuroDolares() {
    return valEuroDolares;
  }

  /**
   * @param valEuroDolares
   *          the valEuroDolares to set
   */
  public void setValEuroDolares(String valEuroDolares) {
    this.valEuroDolares = valEuroDolares;
  }

  /**
   * @return the referencia
   */
  public String getReferencia() {
    return referencia;
  }

  /**
   * @param referencia
   *          the referencia to set
   */
  public void setReferencia(String referencia) {
    this.referencia = referencia;
  }

  /**
   * @return the procesoPagos
   */
  public ProcesoPagos getProcesoPagos() {
    return procesoPagos;
  }

  /**
   * @param procesoPagos
   *          the procesoPagos to set
   */
  public void setProcesoPagos(ProcesoPagos procesoPagos) {
    this.procesoPagos = procesoPagos;
  }

  public List<SelectItem> getMedioPagoItems() {
    return medioPagoItems;
  }

  /**
   * @param medioPagoItems
   *          the medioPagoItems to set
   */
  public void setMedioPagoItems(List<SelectItem> medioPagoItems) {
    this.medioPagoItems = medioPagoItems;
  }

  /**
   * @return the selection
   */
  public SimpleSelection getSelection() {
    return selection;
  }

  /**
   * @param selection
   *          the selection to set
   */
  public void setSelection(SimpleSelection selection) {
    this.selection = selection;
  }

  /**
   * @return the listaCtaBanco
   */
  public List<SelectItem> getListaCtaBanco() {
    return listaCtaBanco;
  }

  /**
   * @param listaCtaBanco
   *          the listaCtaBanco to set
   */
  public void setListaCtaBanco(List<SelectItem> listaCtaBanco) {
    this.listaCtaBanco = listaCtaBanco;
  }

  /**
   * @return the resultadoContable
   */
  public List<AsientoContableBean> getResultadoContable() {
    return resultadoContable;
  }

  /**
   * @param resultadoContable
   *          the resultadoContable to set
   */
  public void setResultadoContable(List<AsientoContableBean> resultadoContable) {
    this.resultadoContable = resultadoContable;
  }

  /**
   * @return the estadoEditar
   */
  public boolean isEstadoEditar() {
    return estadoEditar;
  }

  /**
   * @param estadoEditar
   *          the estadoEditar to set
   */
  public void setEstadoEditar(boolean estadoEditar) {
    this.estadoEditar = estadoEditar;
  }

  /**
   * @return the selectionAsiento
   */
  public SimpleSelection getSelectionAsiento() {
    return selectionAsiento;
  }

  /**
   * @param selectionAsiento
   *          the selectionAsiento to set
   */
  public void setSelectionAsiento(SimpleSelection selectionAsiento) {
    this.selectionAsiento = selectionAsiento;
  }

  public JournalBean getBeanEditable() {
    return beanEditable;
  }

  public void setBeanEditable(JournalBean beanEditable) {
    this.beanEditable = beanEditable;
  }

  /**
   * @return the currentConsultaIndex
   */
  public int getCurrentConsultaIndex() {
    return currentConsultaIndex;
  }

  /**
   * @param currentConsultaIndex
   *          the currentConsultaIndex to set
   */
  public void setCurrentConsultaIndex(int currentConsultaIndex) {
    this.currentConsultaIndex = currentConsultaIndex;
  }

  public String getTipoPagoAsiento() {
    return tipoPagoAsiento;
  }

  public void setTipoPagoAsiento(String tipoPagoAsiento) {
    this.tipoPagoAsiento = tipoPagoAsiento;
  }

  public List<ResultadoErroresContable> getResultadoErroresContable() {
    return resultadoErroresContable;
  }

  public void setResultadoErroresContable(List<ResultadoErroresContable> resultadoErroresContable) {
    this.resultadoErroresContable = resultadoErroresContable;
  }

  public SimpleSelection getSelectionErrorAsiento() {
    return selectionErrorAsiento;
  }

  public void setSelectionErrorAsiento(SimpleSelection selectionErrorAsiento) {
    this.selectionErrorAsiento = selectionErrorAsiento;
  }

  public List<ResultadoErroresContable> getResultadoCorrectosContable() {
    return resultadoCorrectosContable;
  }

  public void setResultadoCorrectosContable(List<ResultadoErroresContable> resultadoCorrectosContable) {
    this.resultadoCorrectosContable = resultadoCorrectosContable;
  }

  public SimpleSelection getSelectionCorrectosAsiento() {
    return selectionCorrectosAsiento;
  }

  public void setSelectionCorrectosAsiento(SimpleSelection selectionCorrectosAsiento) {
    this.selectionCorrectosAsiento = selectionCorrectosAsiento;
  }

  // INICIO JARIASS SYNCCON 19/01/2015
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

  public String getTipoDiario() {
    logger.info("Tipo diario en el GET: " + tipoDiario);
    return tipoDiario;
  }

  public void setTipoDiario(String tipoDiario) {
    this.tipoDiario = tipoDiario;
  }

  public boolean isDetrac() {
    return detrac;
  }

  public void setDetrac(boolean detrac) {
    this.detrac = detrac;
  }

  public boolean isDesabLoteComision() {
    return desabLoteComision;
  }

  public void setDesabLoteComision(boolean desabLoteComision) {
    this.desabLoteComision = desabLoteComision;
  }

  public boolean isDesmarc() {
    return desmarc;
  }

  public void setDesmarc(boolean desmarc) {
    this.desmarc = desmarc;
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
        logger.info("NO ES POSIBLE ABRIR EL ARCHIVO DE REPORTE \n POSIBLEMENTE NO EXISTA");
      }
      out.close();
    } catch (Exception e) {
      logger.error("Error creando traza");
    }

  }

  public boolean isDesabActualizarM() {
    return desabActualizarM;
  }

  public void setDesabActualizarM(boolean desabActualizarM) {
    this.desabActualizarM = desabActualizarM;
  }

  public boolean isDesabEditar() {
    return desabEditar;
  }

  public void setDesabEditar(boolean desabEditar) {
    this.desabEditar = desabEditar;
  }

  public boolean isTrataEditarVacio() {
    return trataEditarVacio;
  }

  public void setTrataEditarVacio(boolean trataEditarVacio) {
    this.trataEditarVacio = trataEditarVacio;
  }

  public boolean isDatosPagoObligRequerid() {
    return datosPagoObligRequerid;
  }

  public boolean isMostrarSelec() {
    return mostrarSelec;
  }

  public void setMostrarSelec(boolean mostrarSelec) {
    this.mostrarSelec = mostrarSelec;
  }

  public void setDatosPagoObligRequerid(boolean datosPagoObligRequerid) {
    this.datosPagoObligRequerid = datosPagoObligRequerid;
  }

  public String getFiltroProv() {
    return filtroProv;
  }

  public void setFiltroProv(String filtroProv) {
    this.filtroProv = filtroProv;
  }

  public String getFiltroRef() {
    return filtroRef;
  }

  public void setFiltroRef(String filtroRef) {
    this.filtroRef = filtroRef;
  }

  public String getFiltroNumDia() {
    return filtroNumDia;
  }

  public void setFiltroNumDia(String filtroNumDia) {
    this.filtroNumDia = filtroNumDia;
  }

  public String getExpresionLote() {
    return expresionLote;
  }

  public void setExpresionLote(String expresionLote) {
    this.expresionLote = expresionLote;
  }

  public boolean isDesabProcesar() {
    return desabProcesar;
  }

  public void setDesabProcesar(boolean desabProcesar) {
    this.desabProcesar = desabProcesar;
  }

  // FIN JARIASS SYNCCON 19/01/2015

  public boolean isPasoValidaciones() {
    return pasoValidaciones;
  }

  public void setPasoValidaciones(boolean pasoValidaciones) {
    this.pasoValidaciones = pasoValidaciones;
  }

  public boolean isDesabAgregarRef() {
    return desabAgregarRef;
  }

  public void setDesabAgregarRef(boolean desabAgregarRef) {
    this.desabAgregarRef = desabAgregarRef;
  }

  String provFilter;

  public String getProvFilter() {
    return provFilter;
  }

  public void setProvFilter(String provFilter) {
    this.provFilter = provFilter;
  }
}
