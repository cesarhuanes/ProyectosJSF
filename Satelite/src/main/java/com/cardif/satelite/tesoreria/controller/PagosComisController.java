package com.cardif.satelite.tesoreria.controller;

import static com.cardif.satelite.constantes.ErrorConstants.MSJ_ERROR;
import static com.cardif.satelite.constantes.ErrorConstants.MSJ_ERROR_GENERAL;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.soap.SOAPFaultException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.configuracion.service.ParametroService;
import com.cardif.satelite.constantes.Constantes;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.tesoreria.bean.JournalBean;
import com.cardif.satelite.tesoreria.bean.ResultadoErroresContable;
import com.cardif.satelite.tesoreria.model.PagoComisionItf;
import com.cardif.satelite.tesoreria.service.ProcesoPagos;
import com.cardif.satelite.util.SateliteUtil;
import com.cardif.sunsystems.bean.AsientoContableBean;
import com.cardif.sunsystems.util.ConstantesSun;
import com.cardif.sunsystems.util.Utilidades;

@Controller("pagosComisController")
@Scope("request")
public class PagosComisController implements Serializable {
  private static final long serialVersionUID = -8323560799344065774L;
  public static final Logger logger = Logger.getLogger(PagosComisController.class);
  private String rutaExcel = null;
  private String rutaPlantilla = null;
  private String rutaNueva = null;
  private String ctaGasto;
  private String idTipoPago;

  private String valEuroSoles;
  private String valDolarSoles;
  private String valEuroDolares;

  private SimpleDateFormat fm = null;
  private SimpleDateFormat fmPeriodo = null;
  private SimpleDateFormat fmSUN = null;

  private String tipoPago;

  private String nombreExcel;

  private int contCargados = 0;

  private boolean desabBtnProcesarAsientos = true;

  // Auxiliares en procesamiento de Hash Map Correctos y Errores:
  private int contVacios = -1;
  private int sumaLineaAsientos = 0;

  private int contVaciosErrores = -1;
  private int sumaLineaAsientosErrores = 0;

  private List<AsientoContableBean> resultadoContable;
  private List<AsientoContableBean> resultadoContableAux;
  private List<AsientoContableBean> resultadoContableGastos = new ArrayList<AsientoContableBean>();
  private List<SelectItem> listaCtaBanco;

  private String outText = "";

  private boolean success = false;
  private String nomCuenta;

  @Autowired(required = true)
  private ProcesoPagos procesoPagos;

  @Autowired(required = true)
  private ParametroService parametroService;

  private List<JournalBean> listaPagados;
  private List<ResultadoErroresContable> resultadoErroresContable;
  private List<ResultadoErroresContable> resultadoCorrectosContable;

  // Plantilla:
  private HSSFWorkbook libro;
  private int contTemp = 0;

  @PostConstruct
  public String inicio() {
    logger.info("INICIANDO");

    try {
      actualizarTiposCambio(new Date());
      tipoPagoItems = new ArrayList<SelectItem>();
      tipoPagoItems.add(new SelectItem("0", "Seleccionar"));
      tipoPagoItems.add(new SelectItem("1", ConstantesSun.COMIS_TIPO_PAGO_COMIS));
      tipoPagoItems.add(new SelectItem("2", ConstantesSun.COMIS_TIPO_PAGO_ITF));

      resultadoContable = new ArrayList<AsientoContableBean>();
      resultadoContableAux = new ArrayList<AsientoContableBean>();

      listaCtaBanco = new ArrayList<SelectItem>();
      List<SelectItem> listaCtaBancos = procesoPagos.traerListaCtaBancos();

      for (SelectItem c : listaCtaBancos) {
        listaCtaBanco.add(c);
      }

      listaPagados = new ArrayList<JournalBean>();
      fmSUN = new SimpleDateFormat(ConstantesSun.UTL_FORMATO_FECHA_SUNSYSTEMS);
      fm = new SimpleDateFormat("dd/MM/yyyy");
      fmPeriodo = new SimpleDateFormat("MMyyyy");
    } catch (SyncconException e) {
      logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
      logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
    } catch (Exception e) {
      logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
      logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
    }
    return null;
  }

  public List<SelectItem> getListaCtaBanco() {
    return listaCtaBanco;
  }

  public void setListaCtaBanco(List<SelectItem> listaCtaBanco) {
    this.listaCtaBanco = listaCtaBanco;
  }

  public List<AsientoContableBean> getResultadoContable() {
    return resultadoContable;
  }

  public void setResultadoContable(List<AsientoContableBean> resultadoContable) {
    this.resultadoContable = resultadoContable;
  }

  private void enviarMensaje(String mensaje) {
    FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, mensaje, null);
    FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    facesMsg = null;
  }

  public String actualizarTiposCambio(Date fecha) {
    logger.info("Actualizando Tipo Cambio");
    HashMap<String, BigDecimal> lista_val = null;
    try {
      lista_val = procesoPagos.traerTipoCambioActual(fmSUN.format(fecha));

      logger.info("lista_val " + lista_val);

      if (lista_val.get("codigo").compareTo(new BigDecimal("9")) == 0) {
        logger.info("Falla en tipos cambios");
        enviarMensaje("Falla en tipos de cambios");
      }
      if (lista_val instanceof HashMap) {
        logger.info("codigo " + lista_val.get("codigo"));
        if (lista_val.get("codigo").compareTo(new BigDecimal("0")) == 0) {
          valEuroSoles = lista_val.get("eur-pen").toString();
          valDolarSoles = lista_val.get("usd-pen").toString();
          valEuroDolares = lista_val.get("eur-usd").toString();
        } else {
          valEuroSoles = "";
          valDolarSoles = "";
          valEuroDolares = "";
        }

      }
      lista_val = null;
      return null;
    } catch (SOAPFaultException e) {
      logger.error("ERROR SOAP: " + e.getMessage());
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "La autentificacion en SUN ha fallado", null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
      return "error";
    } catch (Exception e) {
      logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
      logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
    }

    return null;
  }

  private String getSelectedMedioPago() {
    String tipoPagoSelected = "";
    for (SelectItem selectItem : tipoPagoItems) {
      if (selectItem.getValue().equals(idTipoPago)) {
        tipoPagoSelected = selectItem.getLabel();
        break;
      }
    }
    return tipoPagoSelected;
  }

  private String getSelectedCuenta() {
    String cuenta = "";
    for (SelectItem selectItem : listaCtaBanco) {
      if (selectItem.getValue().equals(numCuentaBanco + "")) {
        cuenta = selectItem.getLabel();
        break;
      }
    }
    return cuenta;
  }

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

  public String getCtaGasto(String moneda, String medioPago) throws SyncconException {
    String cuenta = null;
    String codValor = "1";
    String codParam = null;
    logger.info("getCtaGasto: Moneda=" + moneda + "  YYY Medio Pago= " + medioPago);
    if (moneda.equals("USD")) {
      codValor = "2";
    }

    if (medioPago.equals(ConstantesSun.COMIS_TIPO_PAGO_COMIS)) {
      codParam = Constantes.MDP_CTA_GASTO_DETRAC_TRANS_COMISION;

    } else if (medioPago.equals(ConstantesSun.COMIS_TIPO_PAGO_ITF)) {
      codParam = Constantes.MDP_CTA_GASTO_ITF;
    }

    cuenta = parametroService.obtener(codParam, Constantes.TIP_PARAM_DETALLE, codValor).getNomValor();
    return cuenta;
  }

  private boolean validarCuentas() {
    if (numCuentaBanco == 0) {
      logger.info("No ha seleccionado cta de banco");
      return false;
    }
    return true;
  }

  private boolean validarEscogioExcel() {
    if (nombreExcel == null) {
      logger.info("No escogió excel");
      return false;
    }
    return true;
  }

  private boolean validarNombreFormaExcel() {
    String[] partes = nombreExcel.split("-");
    if (partes.length != 3 || partes[0].length() > 8 || partes[1].length() > 8) {
      logger.info("lenght distinto de 3");
      return false;
    }
    try {
      for (int i = 0; i < partes[1].length(); i++) {
        String s = partes[1].substring(i, i + 1);
        Integer.parseInt(s);
      }

      for (int i = 0; i < partes[2].length(); i++) {
        String s = partes[1].substring(i, i + 1);
        Integer.parseInt(s);
      }
    } catch (Exception e) {
      return false;
    }
    return true;
  }

  private boolean validarNombreTipoExcel() {
    String[] partes = nombreExcel.split("-");
    if (!partes[0].equals(getSelectedMedioPago())) {
      return false;
    }
    return true;
  }

  private boolean validarRegistroExcel() {
    int numExcel = procesoPagos.contarExcelByName(nombreExcel);
    if (numExcel > 0) {
      return false;
    }
    return true;
  }

  public String obtenerAsiento() {

    if (!validarEscogioExcel()) {
      enviarMensaje("No ha escogido un archivo excel");
      return null;
    }
    if (!importar()) {
      return null;
    }

    logger.info("Tipo Pago: " + getSelectedMedioPago());
    logger.info("Cuenta: " + numCuentaBanco);
    desabBtnEnvioSun = true;

    // Validaciones:

    resultadoContableGastos.clear();
    resultadoContable.clear();

    tipoPago = getSelectedMedioPago();

    if (!validarCuentas()) {
      enviarMensaje("No ha seleccionado cta de cargo o abono");
      return null;
    } else if (tipoPago.equals("Seleccionar")) {
      enviarMensaje("No ha seleccionado el Tipo de Pago");
      return null;
    }

    String moneda = getMoneda(numCuentaBanco);
    BigDecimal montoA;
    AsientoContableBean beanGasto;

    try {

      ctaGasto = getCtaGasto(moneda, tipoPago);

    } catch (Exception e) {
      logger.error("Error al cargar cta Gasto");
    }

    String medioPagoCode = "001";
    nomCuenta = getSelectedCuenta();
    if (nomCuenta.contains("IBK") || nomCuenta.contains("BANBIF")) {
      medioPagoCode = ConstantesSun.COMIS_MEDIO_PAGO_IBK;
    } else {
      medioPagoCode = ConstantesSun.COMIS_MEDIO_PAGO_OTROS_BANCOS;
    }

    logger.info("Nom cuenta: " + nomCuenta);
    AsientoContableBean bean;

    for (AsientoContableBean beanAux : resultadoContableAux) {
      bean = new AsientoContableBean();

      // Primero añado lo que obtuve del excel:
      bean.setFecha(beanAux.getFecha());
      if (tipoPago.equals("ITF")) {
        if (beanAux.getImporteTransaccion().doubleValue() > 0)
          bean.setImporteTransaccion(beanAux.getImporteTransaccion().negate());
        else
          bean.setImporteTransaccion(beanAux.getImporteTransaccion());
      } else {
        bean.setImporteTransaccion(beanAux.getImporteTransaccion());
      }

      bean.setReferencia(beanAux.getReferencia());
      bean.setGlosa(beanAux.getGlosa());
      bean.setPeriodo(beanAux.getPeriodo());

      // Procesando la cuenta 10 del excel
      actualizarTiposCambio(bean.getFecha());
      bean.setMoneda(moneda);
      bean.setCuentaContable(numCuentaBanco + "");
      bean.setFechaVencimiento(bean.getFecha());
      // Confirmar esto
      bean.setTipoMedioPago(medioPagoCode);
      bean.setLayout("1;2");
      bean.setNombreLibro("A");
      bean.setMarcador(ConstantesSun.SSC_AllocationMarker_P);

      montoA = bean.getImporteTransaccion();
      try {
        if (moneda.equals("PEN")) {
          bean.setImporteSoles(Utilidades.redondear(2, montoA));
          bean.setImporteEuros(Utilidades.redondear(2, montoA.divide(new BigDecimal(valEuroSoles), RoundingMode.HALF_UP)));
        } else if (moneda.equals("USD")) {
          bean.setImporteSoles(Utilidades.redondear(2, montoA.multiply(new BigDecimal(valDolarSoles))));
          bean.setImporteEuros(Utilidades.redondear(2, montoA.divide(new BigDecimal(valEuroDolares), RoundingMode.HALF_UP)));
        }
      } catch (NumberFormatException e) {
        SateliteUtil.mostrarMensaje("No hay tipos de cambio para alguna Fecha de Transaaccion del excel");
        resultadoContableGastos.clear();
        resultadoContable.clear();
        return null;
      }

      bean.setRefImpTrans(bean.getImporteTransaccion().toString());
      bean.setRefImpSoles(bean.getImporteSoles().toString());
      bean.setRefImpEuros(bean.getImporteEuros().toString());

      if (bean.getImporteSoles().signum() < 0) {
        bean.setMarcadorDC("C");
      } else {
        bean.setMarcadorDC("D");
      }

      resultadoContable.add(bean);
      // Creando la CTA 47 de gastos
      String centroCosto = null;
      try {
        centroCosto = parametroService.obtener(Constantes.MDP_CENT_COST_DETRAC_TRANS_COMISION, Constantes.TIP_PARAM_DETALLE, "1").getNomValor();
      } catch (SyncconException e) {
        logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
        logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
      }

      String provEmpleado = null;
      try {
        provEmpleado = parametroService.obtener(Constantes.MDP_TRANSF_COMIS_PROV_EMPL, Constantes.TIP_PARAM_DETALLE, "1").getNomValor();
      } catch (SyncconException e) {
        logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
        logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
      }

      beanGasto = new AsientoContableBean();

      beanGasto.setFecha(bean.getFecha());
      beanGasto.setImporteTransaccion(bean.getImporteTransaccion().negate());
      beanGasto.setReferencia(bean.getReferencia());
      beanGasto.setGlosa(bean.getGlosa());
      beanGasto.setPeriodo(bean.getPeriodo());
      beanGasto.setMoneda(bean.getMoneda());
      beanGasto.setCuentaContable(ctaGasto);
      beanGasto.setFechaVencimiento(bean.getFechaVencimiento());
      beanGasto.setTipoMedioPago(bean.getTipoMedioPago());
      beanGasto.setLayout("1;2");
      beanGasto.setNombreLibro("A");
      beanGasto.setMarcador(ConstantesSun.SSC_AllocationMarker_P);
      beanGasto.setImporteSoles(bean.getImporteSoles().negate());
      beanGasto.setImporteEuros(bean.getImporteEuros().negate());
      beanGasto.setRefImpTrans(beanGasto.getImporteTransaccion().toString());
      beanGasto.setRefImpSoles(beanGasto.getImporteSoles().toString());
      beanGasto.setRefImpEuros(beanGasto.getImporteEuros().toString());
      beanGasto.setCentroCosto(centroCosto);
      beanGasto.setProveedorEmpleado(provEmpleado);
      beanGasto.setTipoComprobanteSunat(ConstantesSun.COMIS_TRANSFER_COMPROB_SUNAT);
      if (beanGasto.getImporteSoles().signum() < 0) {
        beanGasto.setMarcadorDC("C");
      } else {
        beanGasto.setMarcadorDC("D");
      }
      resultadoContableGastos.add(beanGasto);

    }

    resultadoContable.addAll(resultadoContableGastos);

    desabBtnEnvioSun = false;

    ctaGasto = null;
    nomCuenta = null;

    return null;
  }

  public String descargarPlantilla() {
    logger.info("Inicio");
    ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
    List<String> listaTitulos = new ArrayList<String>();
    listaTitulos.add("Fecha de Transaccion");
    listaTitulos.add("Importe Transaccion");
    listaTitulos.add("Referencia");
    listaTitulos.add("Glosa");

    String ruta = System.getProperty("java.io.tmpdir") + "plantilla.xls";

    crearExcelPlantilla(listaTitulos, ruta);

    rutaPlantilla = ruta;
    // enviarMensaje("Plantilla descargada");

    listaTitulos = null;
    libro = null;

    // exportarPlantilla();

    try {
      File archivoResp = new File(ruta);
      logger.info("Ruta: " + ruta);
      FileInputStream in = new FileInputStream(archivoResp);
      HttpServletResponse response = (HttpServletResponse) contexto.getResponse();
      byte[] loader = new byte[(int) archivoResp.length()];
      response.addHeader("Content-Disposition", "attachment;filename=" + "PlantillaComisionesITF.xls");
      response.setContentType("application/vnd.ms-excel");

      ServletOutputStream out = response.getOutputStream();

      while ((in.read(loader)) > 0) {
        out.write(loader, 0, loader.length);
      }
      in.close();
      out.close();

      FacesContext.getCurrentInstance().responseComplete();

    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }
    logger.info("Fin");
    return null;
  }

  public String exportarPlantilla() {
    File archivoResp = new File(rutaPlantilla);
    FileInputStream in;
    try {
      in = new FileInputStream(archivoResp);
      HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
      byte[] loader = new byte[(int) archivoResp.length()];
      response.addHeader("Content-Disposition", "attachment;filename=" + archivoResp.getName());
      response.setContentType("application/vnd.ms-excel");

      ServletOutputStream out = response.getOutputStream();

      while ((in.read(loader)) > 0) {
        out.write(loader, 0, loader.length);
      }
      in.close();
      out.close();

      FacesContext.getCurrentInstance().responseComplete();
    } catch (FileNotFoundException e) {
      logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
      logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
    } catch (IOException e) {
      logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
      logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
    }
    return null;
  }

  private void crearExcelPlantilla(List<String> listaTitulos, String directorio) {

    try {

      libro = new HSSFWorkbook();

      HSSFSheet hoja = libro.createSheet("Plantilla");

      HSSFRow filaCab = hoja.createRow(0);

      if (listaTitulos != null && listaTitulos.size() > 0) {
        for (int k = 0; k < listaTitulos.size(); k++) {

          String dato = listaTitulos.get(k).toString();
          HSSFCell celda = filaCab.createCell(k);
          celda.setCellValue(new HSSFRichTextString(dato.toUpperCase()));

          // Manejo de estilos de la celda:
          HSSFCellStyle cellStyle = libro.createCellStyle();
          HSSFFont font = libro.createFont();
          font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
          font.setColor(HSSFColor.WHITE.index);
          cellStyle.setFont(font);
          // Manejo del color de fondo. Utilizar setFillForeground y no
          // “…Background”
          cellStyle.setFillForegroundColor(HSSFColor.GREEN.index);
          cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
          celda.setCellStyle(cellStyle);
        }
      }

      // Les envío un ancho a las columnas
      hoja.setColumnWidth(0, 6000);
      hoja.setColumnWidth(1, 6000);
      hoja.setColumnWidth(2, 7500);
      hoja.setColumnWidth(3, 13140);

      // Lo exporto a la ruta

      FileOutputStream elFichero = new FileOutputStream(directorio);
      libro.write(elFichero);
      elFichero.close();

    } catch (Exception e) {
      directorio = directorio.substring(0, directorio.indexOf(".xls")) + contTemp + ".xls";

      try {
        FileOutputStream elFichero = new FileOutputStream(directorio);
        libro.write(elFichero);
        elFichero.close();
      } catch (IOException e1) {
        enviarMensaje("Error en la exportacion a Excel");
        e1.printStackTrace();
      }
      contTemp++;

    }

    // /***** Abir el excel generado **/
    // if (new File(directorio).exists()) {
    // try {
    // Runtime.getRuntime().exec("rundll32 SHELL32.DLL,ShellExec_RunDLL " +
    // directorio);
    // } catch (Exception ex) {
    // enviarMensaje("NO ES POSIBLE ABRIR EL ARCHIVO DE REPORTE \n POSIBLEMENTE NO EXISTA");
    // }
    //
    // }

  }

  public String enviarPagoSun() {

    listaPagados.clear();
    logger.info("Inicio");
    HashMap<Integer, String> listaErrores = new HashMap<Integer, String>();
    HashMap<Integer, Integer> listaAsientosCorrectos = new HashMap<Integer, Integer>();
    resultadoErroresContable = new ArrayList<ResultadoErroresContable>();
    resultadoCorrectosContable = new ArrayList<ResultadoErroresContable>();
    String retorno = null;

    String comis_O_ITF = "";
    if (tipoPago.equals("COMISION")) {
      comis_O_ITF = ConstantesSun.PAGOS_COMISION;
    } else if (tipoPago.equals("ITF")) {
      comis_O_ITF = ConstantesSun.PAGOS_ITF;
    }

    logger.info("Es un pago de tipo: " + comis_O_ITF);
    try {
      logger.info("Antes de resultadosValidacion");
      HashMap<Integer, Object> resultadosValidacion = new HashMap<Integer, Object>();
      logger.info("Pagos Transfer controller: Antes de procesoPagos.enviarSun()");
      List<String> resultado = procesoPagos.enviarSun(resultadoContable, comis_O_ITF, resultadosValidacion, ConstantesSun.COMIS_MEDIO_PAGO, "trasnferencia");
      logger.info("Pagos Transfer controller: Luego de procesoPagos.enviarSun()");
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
        logger.info("Hay errores");
        retorno = "gotoErroresSunTrans";
        outText = "Errores generados";
        success = false;
      }
      if (listaAsientosCorrectos.size() > 0) {
        logger.info("NO hay errores");
        retorno = "gotoCorrectosSunTrans";
        outText = "Asientos generados";
        success = true;
        FacesContext context = FacesContext.getCurrentInstance();

        PagoComisionItf pago = new PagoComisionItf();
        pago.setFec_creacion(new Date());
        String usuario = SecurityContextHolder.getContext().getAuthentication().getName();

        pago.setUsu_creacion(usuario);
        pago.setNom_excel(nombreExcel);
        procesoPagos.registrarExcelComisiones(pago);
      }

      resultadoContable.clear();
      desabBtnProcesarAsientos = true;
      desabBtnEnvioSun = true;
      logger.info("SUCCESS???: " + success);
    } catch (Exception e) {
      logger.info("Error enviando a SUN: " + e.getMessage());
    } finally {
      numCuentaBanco = 0;
      tipoPago = "";
      resultadoContable = new ArrayList<AsientoContableBean>();
    }

    return null;
  }

  public List<AsientoContableBean> getResultadoContableAux() {
    return resultadoContableGastos;
  }

  public void setResultadoContableAux(List<AsientoContableBean> resultadoContableAux) {
    this.resultadoContableGastos = resultadoContableAux;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public List<JournalBean> getListaPagados() {
    return listaPagados;
  }

  public void setListaPagados(List<JournalBean> listaPagados) {
    this.listaPagados = listaPagados;
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

  public String listener(UploadEvent event) {
    rutaExcel = "";
    rutaNueva = System.getProperty("java.io.tmpdir");
    resultadoContable = new ArrayList<AsientoContableBean>();
    logger.debug("[ Inicio Upload files ]");
    String respuesta = null;
    try {
      UploadItem item = event.getUploadItem();
      logger.error("item es nullo: " + item == null);
      File archivo = item.getFile();
      rutaExcel = archivo.getAbsolutePath();
      nombreExcel = item.getFileName();
      rutaNueva += nombreExcel;

      File excel = new File(rutaNueva);
      int cont = 0;
      if (excel.exists()) {
        cont++;
        rutaNueva = excel.getAbsolutePath().substring(0, excel.getAbsolutePath().lastIndexOf(".")) + "(" + cont + ")."
            + excel.getAbsolutePath().substring(excel.getAbsolutePath().lastIndexOf(".") + 1, excel.getAbsolutePath().length());
        excel = new File(rutaNueva);

      }
      logger.info("Name: " + item.getFileName());

      logger.info("XXXXX");

      logger.info("Ruta Ant: " + archivo.getAbsolutePath());
      logger.info("Ruta Des: " + excel.getAbsolutePath());
      FileUtils.copyFile(archivo, excel);

    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
      respuesta = MSJ_ERROR;
    }
    logger.debug("[ Fin Upload files ]");
    return respuesta;
  }

  public boolean importar() {
    logger.info("Importando...");
    // Primero valido si el excel ya ha sido guardado en la DB:
    resultadoContable.clear();
    resultadoContableGastos.clear();
    resultadoContableAux.clear();

    if (!validarNombreFormaExcel()) {
      enviarMensaje("El nombre del excel no tiene la forma TipoPago-YYYYMMDD-NroSecuencial");
      return false;
    } else if (!validarNombreTipoExcel()) {
      enviarMensaje("El nombre del excel no concuerda con el Tipo de Pago elegido: " + getSelectedMedioPago());
      return false;
    } else if (!validarRegistroExcel()) {
      enviarMensaje("El archivo excel ya ha sido registrado anteriormente");
      return false;
    }

    logger.info("Importando : " + rutaNueva);

    desabBtnEnvioSun = true;
    int cont = -1;
    String campoActual = "";
    try {
      desabBtnProcesarAsientos = true;
      FileInputStream fileInputStream = new FileInputStream(rutaNueva);
      Iterator<Row> rowIterator = null;
      if (rutaNueva.endsWith(".xls")) {

        POIFSFileSystem fsFileSystem = new POIFSFileSystem(fileInputStream);
        HSSFWorkbook libroExcel = new HSSFWorkbook(fsFileSystem);
        HSSFSheet hojaExcel = libroExcel.getSheetAt(0);
        rowIterator = hojaExcel.iterator();
      } else if (rutaNueva.endsWith(".xlsx")) {

        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        XSSFSheet sheet = workbook.getSheetAt(0);
        rowIterator = sheet.iterator();
      }

      while (rowIterator.hasNext()) {
        cont++;
        campoActual = "";
        Row row = rowIterator.next();
        // For each row, iterate through all the columns
        Iterator<Cell> cellIterator = row.cellIterator();
        logger.info("-------------Fila " + cont);

        Cell cell1 = cellIterator.next();
        Cell cell2 = cellIterator.next();
        Cell cell3 = cellIterator.next();
        Cell cell4 = cellIterator.next();

        /*
         * if (cell1.getCellType() == Cell.CELL_TYPE_BLANK || cell2.getCellType() == Cell.CELL_TYPE_BLANK || cell3.getCellType() ==
         * Cell.CELL_TYPE_BLANK || cell4.getCellType() == Cell.CELL_TYPE_BLANK) {
         * 
         * return null; }
         */
        logger.info("------");
        AsientoContableBean bean;
        if (cont == 0) {
          logger.info("---CABECERAS---");
          logger.info("Cell: " + cell1.getStringCellValue());
          logger.info("Cell: " + cell2.getStringCellValue());
          logger.info("Cell: " + cell3.getStringCellValue());
          logger.info("Cell: " + cell4.getStringCellValue());
          logger.info("---FIN CABECERAS---");
        } else {

          campoActual = "";
          bean = new AsientoContableBean();
          try {
            campoActual = "Referencia";
            String referencia = cell3.getNumericCellValue() + "";
            campoActual = "Fecha de Transacción";
            logger.info("Cell: " + fm.format(cell1.getDateCellValue()));
            campoActual = "Importe Transacción";
            logger.info("Cell: " + Utilidades.redondear(2, new BigDecimal(cell2.getNumericCellValue())));
            logger.info("Cell: " + Integer.parseInt(referencia.substring(0, referencia.indexOf("."))));
            campoActual = "Glosa";
            logger.info("Cell: " + cell4.getStringCellValue());
            logger.info("------");

            bean.setFecha(fm.parse(fm.format(cell1.getDateCellValue())));
            bean.setImporteTransaccion(Utilidades.redondear(2, new BigDecimal(cell2.getNumericCellValue())));
            bean.setReferencia(referencia.substring(0, referencia.indexOf(".")));
            bean.setGlosa(getGlosaRecortada(cell4.getStringCellValue()));
            bean.setPeriodo("0" + fmPeriodo.format(cell1.getDateCellValue()));
            resultadoContableAux.add(bean);

          } catch (ParseException e) {
            logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
            logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
            enviarMensaje("Error en el formato de fecha en la fila " + cont);
            return false;
          } catch (IllegalStateException e) {
            logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
            logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
            enviarMensaje("Error en el formanto del campo '" + campoActual + "' en la fila " + cont);
            return false;
          }

        }

      }

      desabBtnProcesarAsientos = false;
      contCargados = resultadoContable.size();
    } catch (FileNotFoundException e) {
      logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
      logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
      return false;
    } catch (IOException e) {
      logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
      logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
      return false;
    } catch (NoSuchElementException e) {
      enviarMensaje("Hay celdas vacias en el excel en la fila " + cont);
      logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
      logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
      return false;
    } catch (Exception e) {
      enviarMensaje("Ha ocurrido un error en la carga del excel");
      logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
      logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
      return false;
    }

    return true;
  }

  private String getGlosaRecortada(String glosa) {
    String result = glosa;
    if (result.length() > 50) {
      result = glosa.substring(0, 50);
    }
    return result;
  }

  public String getIdTipoPago() {
    return idTipoPago;
  }

  public void setIdTipoPago(String idTipoPago) {
    this.idTipoPago = idTipoPago;
  }

  private List<SelectItem> tipoPagoItems;
  private int numCuentaBanco;

  public List<SelectItem> getTipoPagoItems() {
    return tipoPagoItems;
  }

  public void setTipoPagoItems(List<SelectItem> tipoPagoItems) {
    this.tipoPagoItems = tipoPagoItems;
  }

  public int getNumCuentaBanco() {
    return numCuentaBanco;
  }

  public void setNumCuentaBanco(int numCuentaBanco) {
    this.numCuentaBanco = numCuentaBanco;
  }

  public String getOutText() {
    return outText;
  }

  public void setOutText(String outText) {
    this.outText = outText;
  }

  private boolean desabBtnEnvioSun = true;

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

  public List<ResultadoErroresContable> getResultadoErroresContable() {
    return resultadoErroresContable;
  }

  public void setResultadoErroresContable(List<ResultadoErroresContable> resultadoErroresContable) {
    this.resultadoErroresContable = resultadoErroresContable;
  }

}
