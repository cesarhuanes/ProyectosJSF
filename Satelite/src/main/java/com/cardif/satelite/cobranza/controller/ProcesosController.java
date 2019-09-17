package com.cardif.satelite.cobranza.controller;


import static com.cardif.satelite.constantes.Constantes.COD_PARAM_VIA_COBRO;
import static com.cardif.satelite.constantes.Constantes.TIP_PARAM_DETALLE;
import static com.cardif.satelite.constantes.Constantes.COD_PARAM_TIPO_PROCESO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.richfaces.model.selection.SimpleSelection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.cobranza.bean.ConsultaProceso;
import com.cardif.satelite.cobranza.service.AuditCobranzaService;
import com.cardif.satelite.cobranza.service.ProcesoService;
import com.cardif.satelite.configuracion.service.ParametroService;
import com.cardif.satelite.constantes.Constantes;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.AuditCobranza;
import com.cardif.satelite.model.Parametro;
import com.cardif.satelite.ssis.service.SsisService;

@Controller("procesosController")
@Scope("request")
public class ProcesosController implements Serializable {

  private static final long serialVersionUID = 1942847492283736725L;
  public static final Logger log = Logger.getLogger(ProcesosController.class);

  @Autowired
  private ParametroService parametroService;
  @Autowired
  private SsisService ssisService;
  @Autowired
  private ProcesoService procesoService;
  @Autowired
  private AuditCobranzaService auditCobranzaService;

  private List<SelectItem> viasCobroItems;
  private List<SelectItem> tiposProcesosItems;
  private List<SelectItem> estadosItems;

  private List<ConsultaProceso> listaProcesos;
  private List<AuditCobranza> listaAuditEnvio;
  private List<AuditCobranza> listaAuditRpta;

  private SimpleSelection selection;
  private String codVia;
  private String tipoProceso;
  private String estadoProceso;
  private Date fechaIni;
  private Date fechaFin;

  HttpSession session;

  @PostConstruct
  public String inicio() {
    log.info("Inicio");
    try {

      String opcionSeleccionada = "";
      session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
      if (session != null) {
        opcionSeleccionada = (String) session.getAttribute("opcion");
      }

      listaProcesos = new ArrayList<ConsultaProceso>();
      selection = new SimpleSelection();

      List<Parametro> listaViaCobro = parametroService.buscar(COD_PARAM_VIA_COBRO, TIP_PARAM_DETALLE);
      List<Parametro> listaTipoProceso = parametroService.buscar(COD_PARAM_TIPO_PROCESO, TIP_PARAM_DETALLE);

      viasCobroItems = new ArrayList<SelectItem>();
      viasCobroItems.add(new SelectItem("", "- Seleccionar -"));

      for (Parametro p : listaViaCobro) {
        viasCobroItems.add(new SelectItem(p.getCodValor(), p.getNomValor()));
      }

      if (opcionSeleccionada.equals("301") || opcionSeleccionada.equals("302")) {
        viasCobroItems.remove(2);
      }
      if (opcionSeleccionada.equals("303") || opcionSeleccionada.equals("304")) {
        viasCobroItems.remove(1);
        // viasCobroItems.remove(1);
      }

      tiposProcesosItems = new ArrayList<SelectItem>();
      tiposProcesosItems.add(new SelectItem("", "- Seleccionar -"));

      for (Parametro p : listaTipoProceso) {
        tiposProcesosItems.add(new SelectItem(p.getNomValor(), p.getNomValor()));
      }

      estadosItems = new ArrayList<SelectItem>();
      estadosItems.add(new SelectItem("", "- Seleccionar -"));
      estadosItems.add(new SelectItem("FINISHED", "Finalizado"));
      estadosItems.add(new SelectItem("RUNNING", "En Ejecucion"));
      estadosItems.add(new SelectItem("ERROR", "Error"));

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, ErrorConstants.MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }
    log.info("Fin");
    return null;
  }

  public String ejecutarEnvio() {
    log.info("Inicio");
    log.debug("codVia=" + codVia);
    String respuesta = null;
    try {

      if (codVia.equals("1")) {
        ssisService.lanzarJob(Constantes.COBRANZA_CS_JOB_ENVIO);
      } else if (codVia.equals("2")) {
        // ssisService.lanzarJob(Constantes.COBRANZA_VISA_JOB_ENVIO);
        ssisService.lanzarJob(Constantes.COBRANZA_IB_JOB_ENVIO);
      }

      AuditCobranza auditCobranza = new AuditCobranza();
      auditCobranza.setTipProceso("ENVIO");
      auditCobranza.setViaCobro(codVia);
      auditCobranza.setUsuEjecucion(SecurityContextHolder.getContext().getAuthentication().getName());
      auditCobranza.setFecEjecucion(new Date(System.currentTimeMillis()));
      auditCobranzaService.insertar(auditCobranza);

      listaAuditEnvio = auditCobranzaService.consultar("ENVIO", codVia);

    } catch (SyncconException ex) {
      log.error("ERROR SYNCCON: " + ex.getMessageComplete());
      FacesMessage facesMsg = new FacesMessage(ex.getSeveridad(), ex.getMessage(), null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
      respuesta = "error";
    }
    log.info("Fin");
    return respuesta;

  }

  public String ejecutarRespuesta() {
    log.info("Inicio");
    log.debug("codVia=" + codVia);
    String respuesta = null;
    try {

      if (codVia.equals("1")) {
        ssisService.lanzarJob(Constantes.COBRANZA_CS_JOB_RESPUESTA);
      } else if (codVia.equals("2")) {
        // ssisService.lanzarJob(Constantes.COBRANZA_VISA_JOB_RESPUESTA);
        ssisService.lanzarJob(Constantes.COBRANZA_IB_JOB_RESPUESTA);
      }

      AuditCobranza auditCobranza = new AuditCobranza();
      auditCobranza.setTipProceso("RESPUESTA");
      auditCobranza.setViaCobro(codVia);
      auditCobranza.setUsuEjecucion(SecurityContextHolder.getContext().getAuthentication().getName());
      auditCobranza.setFecEjecucion(new Date(System.currentTimeMillis()));
      auditCobranzaService.insertar(auditCobranza);

      listaAuditRpta = auditCobranzaService.consultar("RESPUESTA", codVia);

    } catch (SyncconException ex) {
      log.error("ERROR SYNCCON: " + ex.getMessageComplete());
      FacesMessage facesMsg = new FacesMessage(ex.getSeveridad(), ex.getMessage(), null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
      respuesta = "error";
    }
    log.info("Fin");
    return respuesta;
  }

  public String consultarProcesos() {
    log.info("Inicio");
    String respuesta = null;
    try {
      listaProcesos = procesoService.consultar(tipoProceso, estadoProceso, fechaIni, fechaFin);
    } catch (SyncconException ex) {
      log.error("ERROR SYNCCON: " + ex.getMessageComplete());
      FacesMessage facesMsg = new FacesMessage(ex.getSeveridad(), ex.getMessage(), null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
      respuesta = "error";
    }
    log.info("Fin");
    return respuesta;
  }

  public String cargarAuditEnvio() {
    log.info("Inicio");
    String respuesta = null;
    try {
      listaAuditEnvio = auditCobranzaService.consultar("ENVIO", codVia);
    } catch (SyncconException ex) {
      log.error("ERROR SYNCCON: " + ex.getMessageComplete());
      FacesMessage facesMsg = new FacesMessage(ex.getSeveridad(), ex.getMessage(), null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
      respuesta = "error";
    }
    log.info("Fin");
    return respuesta;

  }

  public String cargarAuditRpta() {
    log.info("Inicio");
    String respuesta = null;
    try {
      listaAuditRpta = auditCobranzaService.consultar("RESPUESTA", codVia);
    } catch (SyncconException ex) {
      log.error("ERROR SYNCCON: " + ex.getMessageComplete());
      FacesMessage facesMsg = new FacesMessage(ex.getSeveridad(), ex.getMessage(), null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
      respuesta = "error";
    }
    log.info("Fin");
    return respuesta;

  }

  public ParametroService getParametroService() {
    return parametroService;
  }

  public void setParametroService(ParametroService parametroService) {
    this.parametroService = parametroService;
  }

  public List<SelectItem> getViasCobroItems() {
    return viasCobroItems;
  }

  public void setViasCobroItems(List<SelectItem> viasCobroItems) {
    this.viasCobroItems = viasCobroItems;
  }

  public List<SelectItem> getTiposProcesosItems() {
    return tiposProcesosItems;
  }

  public void setTiposProcesosItems(List<SelectItem> tiposProcesosItems) {
    this.tiposProcesosItems = tiposProcesosItems;
  }

  public List<SelectItem> getEstadosItems() {
    return estadosItems;
  }

  public void setEstadosItems(List<SelectItem> estadosItems) {
    this.estadosItems = estadosItems;
  }

  public String getCodVia() {
    return codVia;
  }

  public void setCodVia(String codVia) {
    this.codVia = codVia;
  }

  public String getTipoProceso() {
    return tipoProceso;
  }

  public void setTipoProceso(String tipoProceso) {
    this.tipoProceso = tipoProceso;
  }

  public String getEstadoProceso() {
    return estadoProceso;
  }

  public void setEstadoProceso(String estadoProceso) {
    this.estadoProceso = estadoProceso;
  }

  public Date getFechaIni() {
    return fechaIni;
  }

  public void setFechaIni(Date fechaIni) {
    this.fechaIni = fechaIni;
  }

  public Date getFechaFin() {
    return fechaFin;
  }

  public void setFechaFin(Date fechaFin) {
    this.fechaFin = fechaFin;
  }

  public static Logger getLog() {
    return log;
  }

  public SsisService getSsisService() {
    return ssisService;
  }

  public void setSsisService(SsisService ssisService) {
    this.ssisService = ssisService;
  }

  public List<ConsultaProceso> getListaProcesos() {
    return listaProcesos;
  }

  public void setListaProcesos(List<ConsultaProceso> listaProcesos) {
    this.listaProcesos = listaProcesos;
  }

  public SimpleSelection getProceso() {
    return selection;
  }

  public void setProceso(SimpleSelection proceso) {
    this.selection = proceso;
  }

  public SimpleSelection getSelection() {
    return selection;
  }

  public void setSelection(SimpleSelection selection) {
    this.selection = selection;
  }

  public List<AuditCobranza> getListaAuditEnvio() {
    return listaAuditEnvio;
  }

  public void setListaAuditEnvio(List<AuditCobranza> listaAuditEnvio) {
    this.listaAuditEnvio = listaAuditEnvio;
  }

  public List<AuditCobranza> getListaAuditRpta() {
    return listaAuditRpta;
  }

  public void setListaAuditRpta(List<AuditCobranza> listaAuditRpta) {
    this.listaAuditRpta = listaAuditRpta;
  }

}