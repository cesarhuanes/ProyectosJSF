package com.cardif.satelite.suscripcion.controller;

import static com.cardif.satelite.constantes.Constantes.SUSCRIPCION_CARG_REG_VENTAS;
import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_CARGA_NULO;
import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_PERIODO;
import static com.cardif.satelite.constantes.ErrorConstants.ERROR_SYNCCON;
import static com.cardif.satelite.constantes.ErrorConstants.MSJ_ERROR;
import static com.cardif.satelite.constantes.ErrorConstants.MSJ_ERROR_GENERAL;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import com.cardif.framework.controller.BaseController;
import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.AuditCarRegVentas;
import com.cardif.satelite.ssis.service.SsisService;
import com.cardif.satelite.suscripcion.service.AuditCarRegVentasService;
import com.cardif.satelite.suscripcion.service.CargRegVentasTempService;

/**
 * 
 * @author JOANHUVE
 * 
 */
@Controller("cargRegVentasTempController")
@Scope("request")
public class CargRegVentasTempController extends BaseController {

  public static final Logger log = Logger.getLogger(CargRegVentasTempController.class);

  private String periodo;
  private boolean upload;
  private File tempFile;

  @Autowired
  private CargRegVentasTempService cargRegVentasTempService;
  @Autowired
  private AuditCarRegVentasService auditCarRegVentasService;

  @Autowired
  private SsisService ssisService;

  String nombreArchivo;

  @Override
  @PostConstruct
  public String inicio() {

    log.info("[ Inicio ]");
    String respuesta = null;
    if (!tieneAcceso()) {
      log.debug("[ No tiene los accesos suficientes ]");
      return "accesoDenegado";
    } else {
      try {
        periodo = getFechaActual(String.valueOf(auditCarRegVentasService.ultimoPeriodo()));
      } catch (SyncconException e) {
        log.error(e.getMessage(), e);
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
      }
    }
    log.info("[ fin ]");
    return respuesta;

  }

  public void limpiar() {
    log.info("[ Inicio Limpiar ]");
    // periodo = getFechaActual();
    try {
      periodo = getFechaActual(String.valueOf(auditCarRegVentasService.ultimoPeriodo()));
      FacesContext context = FacesContext.getCurrentInstance();
      Application application = context.getApplication();
      ViewHandler viewHandler = application.getViewHandler();
      UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());
      context.setViewRoot(viewRoot);
      context.renderResponse(); // Optional
    } catch (SyncconException e) {
      log.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

    log.info("[ Fin Limpiar ]");
  }

  public String listener(UploadEvent event) {
    log.debug("[ Inicio Upload files ]");
    String respuesta = null;
    try {
      UploadItem item = event.getUploadItem();
      File archivo = item.getFile();
      nombreArchivo = item.getFileName().substring(item.getFileName().lastIndexOf("\\") + 1);
      tempFile = File.createTempFile("CRV", nombreArchivo);
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
    log.info("[ Fin Clear ]");
  }

  /**
   * @carga registro de ventas
   * @return
   */
  public synchronized String cargRegVentasTemp() {
    log.info("[ Inicio cargar ]");
    String respuesta = null;
    try {
      if (nombreArchivo.isEmpty()) {
        SyncconException ex = new SyncconException(ErrorConstants.COD_ERROR_ARCHIVO, FacesMessage.SEVERITY_INFO);
        FacesMessage facesMsg = new FacesMessage(ex.getSeveridad(), ex.getMessage(), null);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
        return respuesta;
      }
      log.info("Archivo a cargar [" + nombreArchivo + " ]");
      cargRegVentasTempService.insertarDatosExcel(tempFile);
      tempFile.deleteOnExit();
      nombreArchivo = new String();
      respuesta = "procesoIniciado";

    } catch (SyncconException ex) {
      log.error("ERROR SYNCCON: " + ex.getMessageComplete());
      FacesMessage facesMsg = new FacesMessage(ex.getSeveridad(), ex.getMessage(), null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    } catch (Exception e) {
      log.error("[ ERROR : " + e.getMessage() + " ]");
    }
    log.info("[ Fin cargar ]");
    return respuesta;
  }

  public String procesar() {
    log.info("[ Inicio ]");
    String respuesta = null;
    AuditCarRegVentas auditCarRegVentas = null;
    try {
      if (nombreArchivo != null) {
        if (auditCarRegVentasService.consultaPeriodo(Integer.valueOf(periodo))) {
          throw new SyncconException(COD_ERROR_PERIODO, FacesMessage.SEVERITY_INFO);
        } else {
          cargRegVentasTempService.deleteRows();
          String usuario = SecurityContextHolder.getContext().getAuthentication().getName();
          auditCarRegVentas = new AuditCarRegVentas();
          auditCarRegVentas.setPeriodo(Integer.valueOf(periodo));
          auditCarRegVentas.setEstado("PENDIENTE");
          auditCarRegVentas.setUsuCreacion(usuario);
          auditCarRegVentasService.insertar(auditCarRegVentas);
          respuesta = cargRegVentasTemp();
          if (respuesta != null) {
            ssisService.lanzarJob(SUSCRIPCION_CARG_REG_VENTAS);
          }
        }
      } else {
        throw new SyncconException(COD_ERROR_CARGA_NULO, FacesMessage.SEVERITY_INFO);
      }

    } catch (SyncconException ex) {
      log.error(ERROR_SYNCCON + ex.getMessageComplete());
      FacesMessage facesMsg = new FacesMessage(ex.getSeveridad(), ex.getMessage(), null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
      respuesta = "error";
    }
    log.info("[ Fin ]");
    return respuesta;
  }

  public String getFechaActual(String date) {
    String dateFormat;
    Calendar c = null;
    SimpleDateFormat formateador = null;
    String respuesta = null;
    Date dt;
    try {
      if (!date.equals("null")) {
        dt = new SimpleDateFormat("yyyyMM").parse(date);
        c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.MONTH, 1);
        formateador = new SimpleDateFormat("yyyyMM");
        dateFormat = formateador.format(c.getTime());
        respuesta = dateFormat;
      } else {
        formateador = new SimpleDateFormat("yyyyMM");
        dateFormat = formateador.format(new Date());
        respuesta = dateFormat;
      }

    } catch (ParseException e) {
      log.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
      respuesta = "error";
    }
    return respuesta;
  }

  public String getPeriodo() {
    return periodo;
  }

  public void setPeriodo(String periodo) {
    this.periodo = periodo;
  }

  public boolean isUpload() {
    return upload;
  }

  public void setUpload(boolean upload) {
    this.upload = upload;
  }

  public File getTempFile() {
    return tempFile;
  }

  public void setTempFile(File tempFile) {
    this.tempFile = tempFile;
  }

}
