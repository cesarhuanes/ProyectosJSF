package com.cardif.satelite.telemarketing.controller;

import static com.cardif.satelite.constantes.ErrorConstants.ERROR_SYNCCON;
import static com.cardif.satelite.constantes.ErrorConstants.MSJ_ERROR;
import static com.cardif.satelite.constantes.ErrorConstants.MSJ_ERROR_GENERAL;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.richfaces.model.selection.SimpleSelection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cardif.framework.controller.BaseController;
import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.datamart.OdsPolizaHm;
import com.cardif.satelite.model.datamart.OdsProductoDe;
import com.cardif.satelite.model.datamart.OdsSocioMm;
import com.cardif.satelite.telemarketing.bean.ReporteTLMK;
import com.cardif.satelite.telemarketing.service.ConsultaTelemarketingService;

//
@Controller("consultaTLMKController")
@Scope("request")
public class ConsultaTelemarketingController extends BaseController {

  public static final Logger logger = Logger.getLogger(ConsultaTelemarketingController.class);

  @Autowired
  private ConsultaTelemarketingService consultaTelemarketingService;

  private String socio;
  private String producto;
  private String cuotade;
  private String cuotaa;
  private String segmento;
  private Date fechaInicial;
  private Date fechaFinal;

  private List<SelectItem> socioItems;
  private List<SelectItem> productoItems;
  private List<SelectItem> segmentoItems;

  ReporteTLMK reporteTLMK;
  private List<ReporteTLMK> listaReporteTLMK;

  private SimpleSelection selection;

  @Override
  @PostConstruct
  public String inicio() {
    logger.info("Inicio");

    String respuesta = null;
    if (!tieneAcceso()) {
      logger.debug("No cuenta con los accesos necesarios");
      return "accesoDenegado";
    }
    try {

      listaReporteTLMK = new ArrayList<ReporteTLMK>();
      selection = new SimpleSelection();
      reporteTLMK = new ReporteTLMK();

      socio = null;
      producto = null;

      List<OdsSocioMm> listaSociosDatamart = consultaTelemarketingService.obtenerSocios();

      socioItems = new ArrayList<SelectItem>();
      socioItems.add(new SelectItem("", "- Seleccionar -"));

      for (OdsSocioMm odssmm : listaSociosDatamart) {
        socioItems.add(new SelectItem(odssmm.getCodSocio(), odssmm.getNbrSocio()));
      }

      // PRODUCTOS
      productoItems = new ArrayList<SelectItem>();
      productoItems.add(new SelectItem("", "- Seleccionar -"));

      // SEGMENTO
      segmentoItems = new ArrayList<SelectItem>();
      segmentoItems.add(new SelectItem("", "- Seleccionar -"));
      segmentoItems.add(new SelectItem("AD", "Adquisicion"));
      segmentoItems.add(new SelectItem("CS", "Cross Sell"));

    } catch (SyncconException ex) {
      log.error(ERROR_SYNCCON + ex.getMessageComplete());
      FacesMessage facesMsg = new FacesMessage(ex.getSeveridad(), ex.getMessage(), null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
      respuesta = MSJ_ERROR;
    }

    logger.info("Final");

    return respuesta;
  }

  public void buscarProducto(ValueChangeEvent event) {
    logger.info("Inicio");
    logger.info("EVENT: " + (String) event.getNewValue());

    try {
      if (event.getNewValue() != null) {
        OdsPolizaHm odsphma = new OdsPolizaHm();
        List<OdsPolizaHm> listaOdsPolizaHm = null;

        odsphma.setCodSocio(new BigDecimal((String) event.getNewValue()));
        listaOdsPolizaHm = consultaTelemarketingService.obtenerCodigoProducto(odsphma);

        productoItems = new ArrayList<SelectItem>();

        List<OdsProductoDe> listaProductosDatamart = null;

        for (int i = 0; i < listaOdsPolizaHm.size(); i++) {
          listaProductosDatamart = consultaTelemarketingService.obtenerProductos(String.valueOf(listaOdsPolizaHm.get(i).getCodProducto()));
          for (OdsPolizaHm odpode : listaOdsPolizaHm) {
            for (OdsProductoDe odspde : listaProductosDatamart) {
              if (odpode.getCodProducto().equals(odspde.getCodProducto().toString())) {
                productoItems.add(new SelectItem(odspde.getCodProducto().longValue(), odspde.getDesProducto()));
              }
            }
          }
        }

      } else {
        logger.info("no se envian valores en el combo socio ");
      }
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }
    logger.info("Fin");
  }

  public String procesar() {
    logger.info("Inicio");
    String respuesta = null;
    String fechaIni = "";
    String fechaFin = "";

    try {

      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

      if (cuotade.isEmpty() && cuotaa.isEmpty()) {
        cuotade = "0";
        cuotaa = "1000";
      }

      if ((fechaInicial == null && fechaFinal == null)) {
        if (Integer.parseInt(cuotade) > Integer.parseInt(cuotaa)) {
          throw new SyncconException(ErrorConstants.COD_ERROR_CUOTAS, FacesMessage.SEVERITY_INFO);
        } else {
          listaReporteTLMK = consultaTelemarketingService.obtenerReporteAD(Integer.valueOf(producto), new BigDecimal(socio), fechaIni, fechaFin, Integer.parseInt(cuotade), Integer.parseInt(cuotaa),
              segmento);
        }
      } else {
        if ((fechaInicial == null && fechaFinal != null) || (fechaInicial != null && fechaFinal == null)) {
          throw new SyncconException(ErrorConstants.COD_ERROR_FEC_VIG_FIN_EMPTY, FacesMessage.SEVERITY_INFO);
        } else {
          if (Integer.parseInt(cuotade) > Integer.parseInt(cuotaa)) {
            throw new SyncconException(ErrorConstants.COD_ERROR_CUOTAS, FacesMessage.SEVERITY_INFO);
          } else {
            fechaIni = sdf.format(fechaInicial);
            fechaFin = sdf.format(fechaFinal);
            listaReporteTLMK = consultaTelemarketingService.obtenerReporteAD(Integer.valueOf(producto), new BigDecimal(socio), fechaIni, fechaFin, Integer.parseInt(cuotade), Integer.parseInt(cuotaa),
                segmento);
          }
        }
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
    logger.info("Fin");
    return respuesta;
  }

  public String exportar() {
    logger.info("Inicio");
    Map<String, Object> beans = new HashMap<String, Object>();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
    String FechaActual = sdf.format(new Date(System.currentTimeMillis()));

    try {
      String rutaTemp = System.getProperty("java.io.tmpdir") + "Reporte_Clientes_" + System.currentTimeMillis() + ".xls";
      logger.debug("Ruta Archivo: " + rutaTemp);

      FacesContext fc = FacesContext.getCurrentInstance();
      ServletContext sc = (ServletContext) fc.getExternalContext().getContext();
      String rutaTemplate = sc.getRealPath(File.separator + "excel" + File.separator + "template_exportar_telemarketing.xls");

      beans.put("exportar", listaReporteTLMK);

      XLSTransformer transformer = new XLSTransformer();
      transformer.transformXLS(rutaTemplate, beans, rutaTemp);

      File archivoResp = new File(rutaTemp);
      FileInputStream in = new FileInputStream(archivoResp);

      HttpServletResponse response = (HttpServletResponse) contexto.getResponse();

      byte[] loader = new byte[(int) archivoResp.length()];

      response.addHeader("Content-Disposition", "attachment;filename=" + "Reporte_Cliente_" + FechaActual + ".xls");
      response.setContentType("application/vnd.ms-excel");

      ServletOutputStream out = response.getOutputStream();

      while ((in.read(loader)) > 0) {
        out.write(loader, 0, loader.length);
      }
      in.close();
      out.close();

      FacesContext.getCurrentInstance().responseComplete();
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }
    log.info("Fin");
    return null;
  }

  public void limpiar() throws SyncconException {
    log.info("Limpiar");

    // inicio();
    preLimpiar();
    cuotade = "";
    cuotaa = "";
    segmento = "";
    fechaInicial = null;
    fechaFinal = null;

    FacesContext context = FacesContext.getCurrentInstance();
    Application application = context.getApplication();
    ViewHandler viewHandler = application.getViewHandler();
    UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());
    context.setViewRoot(viewRoot);
    context.renderResponse(); // Optional

    log.info("Fin");
  }

  public String getSocio() {
    return socio;
  }

  public void setSocio(String socio) {
    this.socio = socio;
  }

  public String getProducto() {
    return producto;
  }

  public void setProducto(String producto) {
    this.producto = producto;
  }

  public String getCuotade() {
    return cuotade;
  }

  public void setCuotade(String cuotade) {
    this.cuotade = cuotade;
  }

  public String getCuotaa() {
    return cuotaa;
  }

  public void setCuotaa(String cuotaa) {
    this.cuotaa = cuotaa;
  }

  public String getSegmento() {
    return segmento;
  }

  public void setSegmento(String segmento) {
    this.segmento = segmento;
  }

  public Date getFechaInicial() {
    return fechaInicial;
  }

  public void setFechaInicial(Date fechaInicial) {
    this.fechaInicial = fechaInicial;
  }

  public Date getFechaFinal() {
    return fechaFinal;
  }

  public void setFechaFinal(Date fechaFinal) {
    this.fechaFinal = fechaFinal;
  }

  public List<SelectItem> getSocioItems() {
    return socioItems;
  }

  public void setSocioItems(List<SelectItem> socioItems) {
    this.socioItems = socioItems;
  }

  public List<SelectItem> getProductoItems() {
    return productoItems;
  }

  public void setProductoItems(List<SelectItem> productoItems) {
    this.productoItems = productoItems;
  }

  public List<SelectItem> getSegmentoItems() {
    return segmentoItems;
  }

  public void setSegmentoItems(List<SelectItem> segmentoItems) {
    this.segmentoItems = segmentoItems;
  }

  public ReporteTLMK getReporteTLMK() {
    return reporteTLMK;
  }

  public void setReporteTLMK(ReporteTLMK reporteTLMK) {
    this.reporteTLMK = reporteTLMK;
  }

  public List<ReporteTLMK> getListaReporteTLMK() {
    return listaReporteTLMK;
  }

  public void setListaReporteTLMK(List<ReporteTLMK> listaReporteTLMK) {
    this.listaReporteTLMK = listaReporteTLMK;
  }

  public SimpleSelection getSelection() {
    return selection;
  }

  public void setSelection(SimpleSelection selection) {
    this.selection = selection;
  }

  public void preLimpiar() {

    try {
      listaReporteTLMK = new ArrayList<ReporteTLMK>();
      selection = new SimpleSelection();
      reporteTLMK = new ReporteTLMK();

      socio = null;
      producto = null;

      List<OdsSocioMm> listaSociosDatamart = consultaTelemarketingService.obtenerSocios();

      socioItems = new ArrayList<SelectItem>();
      socioItems.add(new SelectItem("", "- Seleccionar -"));

      for (OdsSocioMm odssmm : listaSociosDatamart) {
        socioItems.add(new SelectItem(odssmm.getCodSocio(), odssmm.getNbrSocio()));
      }

      // PRODUCTOS
      productoItems = new ArrayList<SelectItem>();
      productoItems.add(new SelectItem("", "- Seleccionar -"));

      // SEGMENTO
      segmentoItems = new ArrayList<SelectItem>();
      segmentoItems.add(new SelectItem("", "- Seleccionar -"));
      segmentoItems.add(new SelectItem("AD", "Adquisicion"));
      segmentoItems.add(new SelectItem("CS", "Cross Sell"));
    } catch (Exception e) {
      logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
      logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
    }

  }
}
