package com.cardif.satelite.suscripcion.controller;

import static com.cardif.satelite.constantes.ErrorConstants.ERROR_SYNCCON;
import static com.cardif.satelite.constantes.ErrorConstants.MSJ_ERROR;
import static com.cardif.satelite.constantes.ErrorConstants.MSJ_ERROR_GENERAL;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;
import org.richfaces.model.selection.SimpleSelection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import com.cardif.framework.controller.BaseController;
import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.SusTramaCabeceraIB;
import com.cardif.satelite.model.SusTramaInterbank;
import com.cardif.satelite.model.SusTramaInterbankKey;
import com.cardif.satelite.suscripcion.bean.SusAnulacionMesBean;
import com.cardif.satelite.suscripcion.bean.SusTramaExportarReporte;
import com.cardif.satelite.suscripcion.service.SusTramaAnulacionMensualService;
import com.cardif.satelite.suscripcion.service.SusTramaCabeceraIBService;
import com.cardif.satelite.suscripcion.service.SusTramaInterbankService;
import com.cardif.satelite.util.Utilitarios;

@Controller("proAnulaMultIBController")
@Scope("request")
public class ProAnulaMultIBController extends BaseController {
  public static final Logger log = Logger.getLogger(ProAnulaMultIBController.class);
  final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
  @Autowired
  private SusTramaInterbankService sustramaInterbankService;
  @Autowired
  private SusTramaAnulacionMensualService susTramaAnulacionMensualService;
  @Autowired
  private SusTramaCabeceraIBService susTramaCabeceraIBService;

  List<SusTramaCabeceraIB> lista;
  List<SusTramaInterbank> lista1;
  List<SusAnulacionMesBean> listaTrama;
  List<SusTramaInterbankKey> listaPk;
  private String nombreArchivo;
  private File tempFile;
  private boolean upload;
  private boolean btnEstado;
  private boolean btnEstadoMes;
  private String periodo; // 201401
  private SimpleSelection selection;
  private int canRegis = 0;
  private int canRecha = 0;

  Long pk = null;

  @Override
  @PostConstruct
  public String inicio() {
    log.info("Inicio");
    String respuesta = null;
    if (!tieneAcceso()) {
      log.debug("No cuenta con los accesos necesarios");
      return "accesoDenegado";
    }
    try {
      selection = new SimpleSelection();
      lista = null;
      lista = new ArrayList<SusTramaCabeceraIB>();
      lista = susTramaCabeceraIBService.listarAnulacion();
      for (SusTramaCabeceraIB listarTa : lista) {
        log.info("VAlores: " + listarTa.getCodTrama());
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
      respuesta = MSJ_ERROR;
    }
    log.info("Final");
    return respuesta;
  }

  private void ingresarTramaDiaria(long codTrama) throws Exception {
    int nrolinea = 0;
    log.debug("inicio");
    FileReader fr = new FileReader(tempFile);
    BufferedReader br = new BufferedReader(fr);

    String linea = br.readLine();

    while (linea != null) {
      nrolinea++;
      if (linea.substring(57, 59).equalsIgnoreCase("03") || linea.substring(57, 59).equalsIgnoreCase("3")) {
        linea = linea.replace("Ñ", "Ñ");
        ingresarArchi(StringUtils.replace(StringUtils.replace(Utilitarios.replaceCaracters(linea), "ñ", "ñ"), "º", "º"), codTrama);
        canRegis++;
      } else if (linea.substring(57, 59).equalsIgnoreCase("01")) {
        canRecha++;
      }
      linea = br.readLine();
    }
    log.debug("el numero lineas ingresadas :   " + nrolinea);
    fr.close();
    log.debug("cantidad de rechazados :" + canRecha);
    log.debug("cantidad de registrados :" + canRegis);
    log.debug("Fin");
  }

  public String registroDiario() {
    String respuesta = null;
    log.debug("inicio");
    String cadena = tempFile + "";

    try {
      int resultado = cadena.indexOf("txt");

      if (resultado != -1) {
        SusTramaCabeceraIB susTramaCabeceraIB;
        log.debug("el valor de periodo es :" + getPeriodo());
        susTramaCabeceraIB = susTramaCabeceraIBService.obtener(getPeriodo());
        if (susTramaCabeceraIB == null) {
          ingresarCabeceraNueva();
          log.debug("el valor de periodo es :" + periodo);
          susTramaCabeceraIB = susTramaCabeceraIBService.obtener(getPeriodo());
          log.debug("el valor de periodo es :" + periodo);

        } else if (susTramaCabeceraIB.getEstado().equalsIgnoreCase("CERRADO")) {
          throw new SyncconException(ErrorConstants.COD_ERROR_PERIODO_CERRADO, FacesMessage.SEVERITY_INFO);
        }

        ingresarTramaDiaria(susTramaCabeceraIB.getCodTrama());

        actualizarCabeceraPk(susTramaCabeceraIB);

      } else {
        throw new SyncconException(ErrorConstants.COD_ERROR_PERIODO_TIPO_ARCHIVO_TXT, FacesMessage.SEVERITY_INFO);
      }
      lista = susTramaCabeceraIBService.listarAnulacion();
    } catch (SyncconException ex) {
      log.error(ERROR_SYNCCON + ex.getMessageComplete());
      FacesMessage facesMsg = new FacesMessage(ex.getSeveridad(), ex.getMessage(), null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
      // throw new SyncconException(ErrorConstants.COD_ERROR_GENERAL,
      // FacesMessage.SEVERITY_INFO);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
      respuesta = MSJ_ERROR;

    }

    return respuesta;
  }

  //
  public String listarDetalle() {
    log.info("Inicio");
    String respuesta = null;

    try {
      lista1 = new ArrayList<SusTramaInterbank>();
      Iterator<Object> iterator = getSelection().getKeys();
      if (iterator.hasNext()) {
        int key = (Integer) iterator.next();
        Long pk = lista.get(key).getCodTrama();
        lista1 = sustramaInterbankService.obtenerLista(pk);
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
      respuesta = MSJ_ERROR;
    }
    log.info("Fin");
    return respuesta;
  }

  private int ingresarCabeceraNueva() throws Exception {
    int filasafectadas = 0;
    SusTramaCabeceraIB susTramaCabeceraIB = new SusTramaCabeceraIB();
    susTramaCabeceraIB.setEstado("PENDIENTE");
    Date fecActual;
    fecActual = new Date(System.currentTimeMillis());
    susTramaCabeceraIB.setFecCarga(fecActual);
    susTramaCabeceraIB.setFecCreacion(fecActual);
    susTramaCabeceraIB.setPeriodo(periodo);
    susTramaCabeceraIB.setUsuCreacion(SecurityContextHolder.getContext().getAuthentication().getName());

    susTramaCabeceraIB.setCanRechazados(0);
    susTramaCabeceraIB.setCanRegistros(0);
    filasafectadas = susTramaCabeceraIBService.insertarDatosTxt(susTramaCabeceraIB);
    return filasafectadas;
  }

  private int actualizarCabeceraPk(SusTramaCabeceraIB susTramaCabeceraIB) throws Exception {
    int filasafectadas = 0;

    susTramaCabeceraIB.setCanRechazados(canRecha);
    susTramaCabeceraIB.setCanRegistros(canRegis);
    susTramaCabeceraIB.setUsuModificacion(SecurityContextHolder.getContext().getAuthentication().getName());
    filasafectadas = susTramaCabeceraIBService.actualizarCabecera(susTramaCabeceraIB);
    canRecha = 0;
    canRegis = 0;
    return filasafectadas;
  }

  public String listener(UploadEvent event) {
    log.debug("[ Inicio Upload files ]");
    String respuesta = null;
    try {
      UploadItem item = event.getUploadItem();
      File archivo = item.getFile();
      nombreArchivo = item.getFileName().substring(item.getFileName().lastIndexOf("\\") + 1);
      tempFile = File.createTempFile("INTERBANK_", nombreArchivo);
      btnEstado = true;
      btnEstadoMes = true;

      FileUtils.copyFile(archivo, tempFile);

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
      respuesta = MSJ_ERROR;
    }
    log.debug("[ Fin Upload files ]");
    return respuesta;
  }

  public void clear() {
    log.info("[ Inicio Clear ]");
    nombreArchivo = new String();
    btnEstado = false;
    btnEstadoMes = false;
    log.info("[ Fin Clear ]");
  }

  private void ingresarArchi(String lineCert, Long codTramaRegistrado) throws Exception, SyncconException {
    log.info("[ Inicio ingresarArchi ]");
    SusTramaInterbank susTramaInterbankBean = new SusTramaInterbank();
    susTramaInterbankBean.setCodTrama(codTramaRegistrado);
    susTramaInterbankBean.setProducto("IB_Multiproteccion");
    susTramaInterbankBean.setNroCertificado(lineCert.substring(4, 24));
    susTramaInterbankBean.setTipoPlan(lineCert.substring(24, 34));
    susTramaInterbankBean.setCodOficina(Long.parseLong(lineCert.substring(36, 40)));
    susTramaInterbankBean.setCodVendedor(lineCert.substring(40, 55));
    susTramaInterbankBean.setMedPago(lineCert.substring(55, 57));
    susTramaInterbankBean.setEstCertificado(Long.parseLong(lineCert.substring(57, 59)));
    susTramaInterbankBean.setPrima(Long.parseLong(lineCert.substring(74, 89)));
    susTramaInterbankBean.setMonPrima(lineCert.substring(89, 92));
    susTramaInterbankBean.setFecInicioVigencia(sdf.parse(lineCert.substring(100, 108)));
    susTramaInterbankBean.setNom1Cer(lineCert.substring(373, 433));// 60
    susTramaInterbankBean.setNom2Cer(lineCert.substring(433, 493));
    susTramaInterbankBean.setApe1Cer(lineCert.substring(493, 553));
    susTramaInterbankBean.setApe2Cer(lineCert.substring(553, 613));
    susTramaInterbankBean.setNroDocumento(lineCert.substring(613, 633));
    susTramaInterbankBean.setTipDocumento(lineCert.substring(633, 636));
    if (!(lineCert.substring(3349, 3350).trim().isEmpty())) {
      susTramaInterbankBean.setMovAnulacion(Long.parseLong(lineCert.substring(3349, 3350)));
    } else {
      susTramaInterbankBean.setMovAnulacion(new Long("4"));
    }

    // }catch(Exception e){ log.debug("mensaje : "+e.getMessage()); }
    // susTramaInterbankBean.setMovAnulacion(new
    // Long(lineCert.substring(3349, 3350).trim().isEmpty() ? "4" :
    // lineCert.substring(3349, 3350)));

    log.debug("despues del mov susTramaInterbankBean.getMovAnulacion() " + susTramaInterbankBean.getMovAnulacion());
    // JCAT en caso no venga el dato se coloca NULO
    if ("00000000".equals(lineCert.substring(636, 644))) {
      susTramaInterbankBean.setFinVigencia(null);
      susTramaInterbankBean.setFecSolAnulacion(null);
    } else {
      susTramaInterbankBean.setFinVigencia(sdf.parse(lineCert.substring(636, 644)));
      susTramaInterbankBean.setFecSolAnulacion(susTramaInterbankBean.getFinVigencia());
    }
    susTramaInterbankBean.setEstadoDocumento("SIN DEVOLUCION");
    sustramaInterbankService.ingresa(susTramaInterbankBean);
    log.info("[ Fin ingresarArchi ]");
  }

  public String ingresarTramaMes() {
    log.debug("inicio");
    String cadena = tempFile + "";
    String respuesta = null;
    try {
      int res = cadena.indexOf("xlsx");

      if (res != -1) {
        SusTramaCabeceraIB susTramaCabeceraIB;
        susTramaCabeceraIB = susTramaCabeceraIBService.obtener(periodo);
        if (susTramaCabeceraIB != null) {
          if (!susTramaCabeceraIB.getEstado().contentEquals("CERRADO")) {
            try {
              ingresarM();
            } catch (Exception e) {
              log.error(e.getMessage(), e);
              FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
              FacesContext.getCurrentInstance().addMessage(null, facesMsg);
              log.error("[ ERROR : " + e.getMessage() + " ]");
            }
            lista = susTramaCabeceraIBService.listarAnulacion();
          } else {
            throw new SyncconException(ErrorConstants.COD_ERROR_PERIODO_CERRADO_NO_PROCESAR, FacesMessage.SEVERITY_INFO);

          }
        } else {
          throw new SyncconException(ErrorConstants.COD_ERROR_PERIODO_COD_NOEXISTE, FacesMessage.SEVERITY_INFO);

        }
      } else {
        throw new SyncconException(ErrorConstants.COD_ERROR_PERIODO_TIPO_ARCHIVO_XLSX, FacesMessage.SEVERITY_INFO);

      }

    } catch (SyncconException ex) {
      log.error(ERROR_SYNCCON + ex.getMessageComplete());
      FacesMessage facesMsg = new FacesMessage(ex.getSeveridad(), ex.getMessage(), null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
      respuesta = MSJ_ERROR;

    }
    log.debug("Fin");
    return respuesta;

  }

  private String ingresarM() throws SyncconException, Exception {
    log.info("[ Inicio cargar controller excel]");
    String respuesta = null;

    try {
      if (nombreArchivo.isEmpty()) {
        throw new SyncconException(ErrorConstants.COD_ERROR_ARCHIVO, FacesMessage.SEVERITY_INFO);
      }
      susTramaCabeceraIBService.procesarMes(periodo);
      susTramaAnulacionMensualService.actEstadosRegistros(tempFile, susTramaCabeceraIBService.obtener(periodo).getCodTrama());
      tempFile.deleteOnExit();
      nombreArchivo = new String();
      respuesta = "procesoIniciado";
    } catch (SyncconException ex) {
      throw new SyncconException(ErrorConstants.COD_ERROR_LEER_ARCHIVO_EXCEL, FacesMessage.SEVERITY_INFO);

    } catch (Exception e) {
      log.error("[ ERROR : " + e.getMessage() + " ]");
    }
    log.info("[ Fin cargar ]");
    return respuesta;
  }

  public String exportarMes() {

    log.debug("Inicio");
    String respuesta = null;
    Iterator<Object> iterator = getSelection().getKeys();
    if (iterator.hasNext()) {
      int key = (Integer) iterator.next();
      pk = lista.get(key).getCodTrama();
    }

    if (pk != null) {

      try {
        SusTramaCabeceraIB susTramaCabeceraIB;
        susTramaCabeceraIB = susTramaCabeceraIBService.obtener(pk);

        if (susTramaCabeceraIB == null) {
          throw new SyncconException(ErrorConstants.COD_ERROR_PERIODO_COD_NOEXISTE, FacesMessage.SEVERITY_INFO);
        }
        if (susTramaCabeceraIB != null && susTramaCabeceraIB.getEstado().equals("PENDIENTE")) {
          throw new SyncconException(ErrorConstants.COD_ERROR_PERIODO_PENDIENTE, FacesMessage.SEVERITY_INFO);
        }

        exportarMes2();
        susTramaCabeceraIBService.procesarMesCerrado(pk);
        lista = susTramaCabeceraIBService.listarAnulacion();

      } catch (SyncconException ex) {
        log.error(ERROR_SYNCCON + ex.getMessageComplete());
        FacesMessage facesMsg = new FacesMessage(ex.getSeveridad(), ex.getMessage(), null);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
      } catch (Exception e) {
        log.error(e.getMessage(), e);
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
        respuesta = MSJ_ERROR;
      }

    }
    log.info("Fin");
    return respuesta;
  }

  private List<SusTramaExportarReporte> listaReporte(Long codTrama) throws Exception {

    log.debug("Inicio");
    log.debug("Imput : " + codTrama);
    List<SusTramaExportarReporte> listaExportar = null;
    listaExportar = new ArrayList<SusTramaExportarReporte>();
    log.debug("antes edl  tamaño de listaExportar : " + listaExportar.size());
    listaExportar = sustramaInterbankService.listaReporteEnviados(codTrama);
    log.debug("tamaño de listaExportar : " + listaExportar.size());

    log.debug("Fin");
    return listaExportar;
  }

  public String exportarMes2() {
    log.info("Inicio");

    ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
    String FechaActual = sdf.format(new Date(System.currentTimeMillis()));
    try {
      Map<String, Object> beans = new HashMap<String, Object>();
      String rutaTemp = System.getProperty("java.io.tmpdir") + "CA003-Anulaciones_Multiprot_" + System.currentTimeMillis() + ".xlsx";
      log.debug("Ruta Archivo: " + rutaTemp);
      FacesContext fc = FacesContext.getCurrentInstance();
      ServletContext sc = (ServletContext) fc.getExternalContext().getContext();
      String rutaTemplate = sc.getRealPath(File.separator + "excel" + File.separator + "anulacion_suscripcion_IB.xlsx");
      log.info("---el pk es --: " + pk);
      beans.put("exportar", listaReporte(pk));

      XLSTransformer transformer = new XLSTransformer();
      transformer.transformXLS(rutaTemplate, beans, rutaTemp);
      File archivoResp = new File(rutaTemp);
      FileInputStream in = new FileInputStream(archivoResp);
      HttpServletResponse response = (HttpServletResponse) contexto.getResponse();

      byte[] loader = new byte[(int) archivoResp.length()];
      response.addHeader("Content-Disposition", "attachment;filename=" + "CA003-Anulaciones_Multiprot_" + FechaActual + ".xlsx");
      response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
      ServletOutputStream out = response.getOutputStream();
      while ((in.read(loader)) > 0) {
        out.write(loader, 0, loader.length);
      }
      in.close();
      out.close();
      pk = null;
      FacesContext.getCurrentInstance().responseComplete();

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

    log.info("Fin");
    return null;
  }

  public String cambiarPeriodo() {
    log.info("Inicio");
    Iterator<Object> iterator = getSelection().getKeys();
    try {
      if (iterator.hasNext()) {
        int key = (Integer) iterator.next();
        pk = lista.get(key).getCodTrama();
      }
      SusTramaCabeceraIB susTramaCabeceraIB;
      susTramaCabeceraIB = susTramaCabeceraIBService.obtener(pk);

      periodo = susTramaCabeceraIB.getPeriodo();

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

    log.info("Fin");
    return null;
  }

  public List<SusAnulacionMesBean> getListaTrama() {
    return listaTrama;
  }

  public void setListaTrama(List<SusAnulacionMesBean> listaTrama) {
    this.listaTrama = listaTrama;
  }

  public String getNombreArchivo() {
    return nombreArchivo;
  }

  public File getTempFile() {
    return tempFile;
  }

  public boolean isUpload() {
    return upload;
  }

  public void setNombreArchivo(String nombreArchivo) {
    this.nombreArchivo = nombreArchivo;
  }

  public void setTempFile(File tempFile) {
    this.tempFile = tempFile;
  }

  public void setUpload(boolean upload) {
    this.upload = upload;
  }

  public boolean isBtnEstado() {
    return btnEstado;
  }

  public void setBtnEstado(boolean btnEstado) {
    this.btnEstado = btnEstado;
  }

  public List<SusTramaInterbank> getLista1() {
    return lista1;
  }

  public void setLista1(List<SusTramaInterbank> lista1) {
    this.lista1 = lista1;
  }

  public List<SusTramaCabeceraIB> getLista() {
    return lista;
  }

  public void setLista(List<SusTramaCabeceraIB> lista) {
    this.lista = lista;
  }

  public String getPeriodo() {
    return periodo;
  }

  public void setPeriodo(String periodo) {
    this.periodo = periodo;
  }

  public SimpleSelection getSelection() {

    return selection;
  }

  public void setSelection(SimpleSelection selection) {
    this.selection = selection;
  }

  public boolean isBtnEstadoMes() {
    return btnEstadoMes;
  }

  public void setBtnEstadoMes(boolean btnEstadoMes) {
    this.btnEstadoMes = btnEstadoMes;
  }

  public List<SusTramaInterbankKey> getListaPk() {
    return listaPk;
  }

  public void setListaPk(List<SusTramaInterbankKey> listaPk) {
    this.listaPk = listaPk;
  }

  public int getCanRegis() {
    return canRegis;
  }

  public int getCanRecha() {
    return canRecha;
  }

  public void setCanRegis(int canRegis) {
    this.canRegis = canRegis;
  }

  public void setCanRecha(int canRecha) {
    this.canRecha = canRecha;
  }

}
