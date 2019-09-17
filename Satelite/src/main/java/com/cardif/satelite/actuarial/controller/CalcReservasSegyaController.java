package com.cardif.satelite.actuarial.controller;

import static com.cardif.satelite.constantes.Constantes.REPORTE_CALCULO_RESERVAS_SEGYA;
import static com.cardif.satelite.constantes.Constantes.TIP_PARAM_DETALLE;
import static com.cardif.satelite.constantes.Constantes.COD_PARAM_TIPO_VENTA;
import static com.cardif.satelite.constantes.Constantes.COD_PARAM_ESTADO_VENTA;
import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_REPORTE_EJECUCION;
import static com.cardif.satelite.constantes.ErrorConstants.ERROR_SYNCCON;
import static com.cardif.satelite.constantes.ErrorConstants.MSJ_ERROR;
import static com.cardif.satelite.constantes.ErrorConstants.MSJ_ERROR_GENERAL;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import com.cardif.framework.controller.BaseController;
import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.actuarial.service.ActReservaSegyaService;
import com.cardif.satelite.configuracion.service.ParametroService;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.ActReservaSegya;
import com.cardif.satelite.model.Parametro;
import com.cardif.satelite.model.segya.Socio;
import com.cardif.satelite.segya.service.SocioService;
import com.cardif.satelite.ssis.service.SsisService;

/*
 * lmaron: man Coaseguro
 * */
@Controller("calcReservasSegyaController")
@Scope("request")
public class CalcReservasSegyaController extends BaseController {

  public static final Logger log = Logger.getLogger(CalcReservasSegyaController.class);

  @Autowired
  private ParametroService parametroService;
  @Autowired
  private ActReservaSegyaService actReservaSegyaService;
  @Autowired
  private SocioService socioService;

  @Autowired
  private SsisService ssisService;

  private Parametro parametro;
  private ActReservaSegya actReservaSegya;

  private List<SelectItem> tipReporteItems;
  private List<SelectItem> socioItems;
  private List<SelectItem> tipVentaItems;
  private List<SelectItem> estVentaItems;

  private final int CODSOCIOALL = 9;
  private final String ESTADO = "PENDIENTE";

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

      loadCombos();

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
      respuesta = MSJ_ERROR;
    }
    log.info("Final");
    return respuesta;
  }

  public String procesar() {
    log.info("Inicio");
    String respuesta = null;
    String socioSegya = "";

    try {

      if (actReservaSegya.getFecFin().before(actReservaSegya.getFecInicio())) {
        throw new SyncconException(ErrorConstants.COD_ERROR_FEC_VIG_FIN, FacesMessage.SEVERITY_INFO);
      } else if (actReservaSegya.getFecCierre().before(actReservaSegya.getFecInicio())) {
        throw new SyncconException(ErrorConstants.COD_ERROR_FEC_CIERRE_INI, FacesMessage.SEVERITY_INFO);
      } else if (actReservaSegya.getFecCierre().before(actReservaSegya.getFecFin())) {
        throw new SyncconException(ErrorConstants.COD_ERROR_FEC_CIERRE_FIN, FacesMessage.SEVERITY_INFO);
      } else {

        if (ESTADO.equalsIgnoreCase(actReservaSegyaService.consultaEstado())) {
          reloadContext();
          throw new SyncconException(COD_ERROR_REPORTE_EJECUCION, FacesMessage.SEVERITY_INFO);
        } else {

          actReservaSegya.setUsuCreacion(SecurityContextHolder.getContext().getAuthentication().getName());
          /* cambio el tipo de reporte */
          actReservaSegya.setCodReporte("0");
          actReservaSegya.setEstado(ESTADO);
          if (Integer.parseInt(actReservaSegya.getCodSocio()) != CODSOCIOALL) {
            socioSegya = socioService.obtener(Integer.parseInt(actReservaSegya.getCodSocio())).getDescripcion();
            actReservaSegya.setSocio(socioSegya);
          } else {
            socioSegya = "Todos";
            actReservaSegya.setCodSocio("0");
            actReservaSegya.setSocio(socioSegya);
          }

          if (actReservaSegyaService.insertar(actReservaSegya) == 1) {
            ssisService.lanzarJob(REPORTE_CALCULO_RESERVAS_SEGYA);
          } else {
            throw new SyncconException(ErrorConstants.COD_ERROR_JOB, FacesMessage.SEVERITY_INFO);
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
    log.info("Fin");
    return respuesta;
  }

  public void limpiar() {
    log.info("[ Inicio Limpiar ]");
    try {
      loadCombos();
      reloadContext();
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }
    log.info("[ Fin Limpiar ]");
  }

  private void reloadContext() {
    log.info("[ Inicio  ]");
    try {
      FacesContext context = FacesContext.getCurrentInstance();
      Application application = context.getApplication();
      ViewHandler viewHandler = application.getViewHandler();
      UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());
      context.setViewRoot(viewRoot);
      context.renderResponse();
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }
    log.info("[ Limpiar ]");
  }

  private void loadCombos() {
    log.info("[ INICIO ]");
    try {

      actReservaSegya = new ActReservaSegya();

      socioItems = new ArrayList<SelectItem>();
      socioItems.add(new SelectItem("", "- Seleccionar -"));
      /* todos */
      socioItems.add(new SelectItem(CODSOCIOALL, "Todos"));
      List<Socio> lista = socioService.buscar();

      for (Socio s : lista) {
        socioItems.add(new SelectItem(s.getCodigoSocio().toString(), s.getDescripcion()));
      }

      List<Parametro> listaTipVenta = parametroService.buscar(COD_PARAM_TIPO_VENTA, TIP_PARAM_DETALLE);
      tipVentaItems = new ArrayList<SelectItem>();
      tipVentaItems.add(new SelectItem("", "- Seleccionar -"));

      for (Parametro p : listaTipVenta) {
        tipVentaItems.add(new SelectItem(p.getCodValor(), p.getNomValor()));
      }

      List<Parametro> listaEstVenta = parametroService.buscar(COD_PARAM_ESTADO_VENTA, TIP_PARAM_DETALLE);
      estVentaItems = new ArrayList<SelectItem>();
      estVentaItems.add(new SelectItem("", "- Seleccionar -"));

      for (Parametro p : listaEstVenta) {
        estVentaItems.add(new SelectItem(p.getCodValor(), p.getNomValor()));
      }
    } catch (SyncconException ex) {
      log.error(ERROR_SYNCCON + ex.getMessageComplete());
      FacesMessage facesMsg = new FacesMessage(ex.getSeveridad(), ex.getMessage(), null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }
    log.info("[ FIN ]");
  }

  public List<SelectItem> getSocioItems() {
    return socioItems;
  }

  public void setSocioItems(List<SelectItem> socioItems) {
    this.socioItems = socioItems;
  }

  public Parametro getParametro() {
    return parametro;
  }

  public void setParametro(Parametro parametro) {
    this.parametro = parametro;
  }

  public List<SelectItem> getTipReporteItems() {
    return tipReporteItems;
  }

  public void setTipReporteItems(List<SelectItem> tipReporteItems) {
    this.tipReporteItems = tipReporteItems;
  }

  public List<SelectItem> getTipVentaItems() {
    return tipVentaItems;
  }

  public void setTipVentaItems(List<SelectItem> tipVentaItems) {
    this.tipVentaItems = tipVentaItems;
  }

  public List<SelectItem> getEstVentaItems() {
    return estVentaItems;
  }

  public void setEstVentaItems(List<SelectItem> estVentaItems) {
    this.estVentaItems = estVentaItems;
  }

  public ActReservaSegya getActReservaSegya() {
    return actReservaSegya;
  }

  public void setActReservaSegya(ActReservaSegya actReservaSegya) {
    this.actReservaSegya = actReservaSegya;
  }

}
