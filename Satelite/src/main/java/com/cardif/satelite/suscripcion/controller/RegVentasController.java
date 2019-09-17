package com.cardif.satelite.suscripcion.controller;

import static com.cardif.satelite.constantes.Constantes.COD_PARAM_TIPOS_COMPROBANTE;
import static com.cardif.satelite.constantes.Constantes.COD_PARAM_TIPOS_DOCUMENTO;
import static com.cardif.satelite.constantes.Constantes.SUSCRIPCION_JOB_MIG_REG_VENTAS;
import static com.cardif.satelite.constantes.Constantes.TIP_PARAM_DETALLE;
import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_PERIODO;
import static com.cardif.satelite.constantes.ErrorConstants.ERROR_SYNCCON;
import static com.cardif.satelite.constantes.ErrorConstants.MSJ_ERROR;
import static com.cardif.satelite.constantes.ErrorConstants.MSJ_ERROR_GENERAL;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.richfaces.model.selection.SimpleSelection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.configuracion.service.ParametroService;
import com.cardif.satelite.model.MigRegVentas;
import com.cardif.satelite.model.Parametro;
import com.cardif.satelite.model.TstVentas;
import com.cardif.satelite.ssis.service.SsisService;
import com.cardif.satelite.suscripcion.bean.ConsultaTstVentas;
import com.cardif.satelite.suscripcion.service.RegVentasService;

@Controller("regVentasController")
@Scope("request")
public class RegVentasController {

  public static final Logger log = Logger.getLogger(RegVentasController.class);

  @Autowired
  private ParametroService parametroService;
  @Autowired
  private RegVentasService regVentasService;
  @Autowired
  private SsisService ssisService;

  private List<SelectItem> productoItems;
  private List<SelectItem> tipComprobanteItems;
  private List<SelectItem> tipDocumentoItems;

  private List<ConsultaTstVentas> listaTstVentas;

  private SimpleSelection selection;

  private TstVentas tstVentas;
  private String correlativo;
  private String tipComprobante;
  private String producto;
  private String poliza;
  private String nroSerie;
  private String correlativoSerie;
  private String tipDocumento;
  private String numDocumento;
  private String periodo;
  private Date fechaDesde;
  private Date fechaHasta;

  private String tipConsulta;

  @PostConstruct
  public String inicio() {
    log.info("Inicio");
    try {
      listaTstVentas = new ArrayList<ConsultaTstVentas>();
      selection = new SimpleSelection();
      tstVentas = new TstVentas();

      SimpleDateFormat formateador = new SimpleDateFormat("yyyyMMdd");
      String fechaActual = formateador.format(new Date());
      periodo = fechaActual.substring(0, 6);

      List<Parametro> listaTiposComprobante = parametroService.buscar(COD_PARAM_TIPOS_COMPROBANTE, TIP_PARAM_DETALLE);
      List<Parametro> listaTiposDocumento = parametroService.buscar(COD_PARAM_TIPOS_DOCUMENTO, TIP_PARAM_DETALLE);

      /* Lista de Tipos de Comprobante */
      tipComprobanteItems = new ArrayList<SelectItem>();
      tipComprobanteItems.add(new SelectItem("", "- Seleccionar -"));
      for (Parametro p : listaTiposComprobante) {
        tipComprobanteItems.add(new SelectItem(Long.valueOf(p.getCodValor()), p.getNomValor()));
      }

      /* Lista de Tipos de Documento */
      tipDocumentoItems = new ArrayList<SelectItem>();
      tipDocumentoItems.add(new SelectItem("", "- Seleccionar -"));
      for (Parametro p : listaTiposDocumento) {
        tipDocumentoItems.add(new SelectItem(p.getCodValor(), p.getNomValor()));
      }

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }
    log.info("Fin");
    return null;
  }

  public String cargar() {
    log.info("Inicio");
    MigRegVentas migRegVentas = null;
    String respuesta = null;
    try {
      if (regVentasService.existePeriodo(Integer.valueOf(periodo))) {
        throw new SyncconException(COD_ERROR_PERIODO, FacesMessage.SEVERITY_INFO);
      } else {
        String usuario = SecurityContextHolder.getContext().getAuthentication().getName();
        migRegVentas = new MigRegVentas();
        migRegVentas.setPeriodo(Integer.valueOf(periodo));
        migRegVentas.setEstado("PENDIENTE");
        migRegVentas.setUsuCreacion(usuario);
        regVentasService.insertar(migRegVentas);
        ssisService.lanzarJob(SUSCRIPCION_JOB_MIG_REG_VENTAS);
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
    log.info("Fin");
    return respuesta;
  }

  public String consultar() {
    log.info("Inicio");
    String respuesta = null;
    try {
      tipConsulta = "1";
      if (StringUtils.isBlank(correlativo)) {
        listaTstVentas = regVentasService.buscar(correlativo, tipComprobante, producto, poliza, nroSerie, correlativoSerie, tipDocumento, numDocumento, fechaDesde, fechaHasta);
      } else {
        listaTstVentas = regVentasService.buscarPorCorrelativo(correlativo);
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
    log.info("Fin");
    return respuesta;
  }

  public String verNoReferenciados() {
    log.info("Inicio");
    String respuesta = null;

    try {
      tipConsulta = "2";

      if (StringUtils.isBlank(correlativo)) {
        listaTstVentas = regVentasService.buscarNoReferenciados(correlativo, tipComprobante, producto, poliza, nroSerie, correlativoSerie, tipDocumento, numDocumento, fechaDesde, fechaHasta);
      } else {
        listaTstVentas = regVentasService.buscarPorCorrelativo(correlativo);
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
    log.info("Fin");
    return respuesta;
  }

  public String obtener() {
    log.info("Inicio");
    String respuesta = null;
    tstVentas = null;
    try {
      Iterator<Object> iterator = getSelection().getKeys();
      if (iterator.hasNext()) {
        int key = (Integer) iterator.next();
        Long pk = listaTstVentas.get(key).getPk();
        tstVentas = regVentasService.obtener(pk);

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

  public String actualizar() {
    log.info("Inicio");
    String respuesta = null;
    try {
      String usuario = SecurityContextHolder.getContext().getAuthentication().getName();
      tstVentas.setUsuModificacion(usuario);
      regVentasService.actualizar(tstVentas);

      if (StringUtils.isBlank(correlativo)) {
        if (tipConsulta.equals("1")) {
          listaTstVentas = regVentasService.buscar(correlativo, tipComprobante, producto, poliza, nroSerie, correlativoSerie, tipDocumento, numDocumento, fechaDesde, fechaHasta);
        } else {
          listaTstVentas = regVentasService.buscarNoReferenciados(correlativo, tipComprobante, producto, poliza, nroSerie, correlativoSerie, tipDocumento, numDocumento, fechaDesde, fechaHasta);
        }
      } else {
        listaTstVentas = regVentasService.buscarPorCorrelativo(correlativo);
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
    log.info("Fin");
    return respuesta;
  }

  public void limpiar() {
    log.info("Inicio");

    correlativo = null;
    producto = null;
    tipComprobante = null;
    poliza = null;
    nroSerie = null;
    correlativoSerie = null;
    tipDocumento = null;
    numDocumento = null;
    fechaDesde = null;
    fechaHasta = null;

    listaTstVentas = null;

    FacesContext context = FacesContext.getCurrentInstance();
    Application application = context.getApplication();
    ViewHandler viewHandler = application.getViewHandler();
    UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());
    context.setViewRoot(viewRoot);
    context.renderResponse(); // Optional

    log.info("Fin");
  }

  public void action() {
    log.info("Acc: " + tstVentas.getFechaemisionref());
  }

  public SimpleSelection getSelection() {
    return selection;
  }

  public void setSelection(SimpleSelection selection) {
    this.selection = selection;
  }

  public String getCorrelativo() {
    return correlativo;
  }

  public void setCorrelativo(String correlativo) {
    this.correlativo = correlativo;
  }

  public String getTipComprobante() {
    return tipComprobante;
  }

  public void setTipComprobante(String tipComprobante) {
    this.tipComprobante = tipComprobante;
  }

  public String getPoliza() {
    return poliza;
  }

  public void setPoliza(String poliza) {
    this.poliza = poliza;
  }

  public String getNumDocumento() {
    return numDocumento;
  }

  public void setNumDocumento(String numDocumento) {
    this.numDocumento = numDocumento;
  }

  public Date getFechaDesde() {
    return fechaDesde;
  }

  public void setFechaDesde(Date fechaDesde) {
    this.fechaDesde = fechaDesde;
  }

  public Date getFechaHasta() {
    return fechaHasta;
  }

  public void setFechaHasta(Date fechaHasta) {
    this.fechaHasta = fechaHasta;
  }

  public String getProducto() {
    return producto;
  }

  public void setProducto(String producto) {
    this.producto = producto;
  }

  public List<ConsultaTstVentas> getListaTstVentas() {
    return listaTstVentas;
  }

  public void setListaTstVentas(List<ConsultaTstVentas> listaTstVentas) {
    this.listaTstVentas = listaTstVentas;
  }

  public RegVentasService getRegVentasService() {
    return regVentasService;
  }

  public void setRegVentasService(RegVentasService regVentasService) {
    this.regVentasService = regVentasService;
  }

  public ParametroService getParametroService() {
    return parametroService;
  }

  public void setParametroService(ParametroService parametroService) {
    this.parametroService = parametroService;
  }

  public List<SelectItem> getTipComprobanteItems() {
    return tipComprobanteItems;
  }

  public void setTipComprobanteItems(List<SelectItem> tipComprobanteItems) {
    this.tipComprobanteItems = tipComprobanteItems;
  }

  public List<SelectItem> getTipDocumentoItems() {
    return tipDocumentoItems;
  }

  public void setTipDocumentoItems(List<SelectItem> tipDocumentoItems) {
    this.tipDocumentoItems = tipDocumentoItems;
  }

  public List<SelectItem> getProductoItems() {
    return productoItems;
  }

  public void setProductoItems(List<SelectItem> productoItems) {
    this.productoItems = productoItems;
  }

  public String getTipDocumento() {
    return tipDocumento;
  }

  public void setTipDocumento(String tipDocumento) {
    this.tipDocumento = tipDocumento;
  }

  public TstVentas getTstVentas() {
    return tstVentas;
  }

  public void setTstVentas(TstVentas tstVentas) {
    this.tstVentas = tstVentas;
  }

  public String getPeriodo() {
    return periodo;
  }

  public void setPeriodo(String periodo) {
    this.periodo = periodo;
  }

  public String getNroSerie() {
    return nroSerie;
  }

  public void setNroSerie(String nroSerie) {
    this.nroSerie = nroSerie;
  }

  public String getCorrelativoSerie() {
    return correlativoSerie;
  }

  public void setCorrelativoSerie(String correlativoSerie) {
    this.correlativoSerie = correlativoSerie;
  }

  public String getTipConsulta() {
    return tipConsulta;
  }

  public void setTipConsulta(String tipConsulta) {
    this.tipConsulta = tipConsulta;
  }

}
