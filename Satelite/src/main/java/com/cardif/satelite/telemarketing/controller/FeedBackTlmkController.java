package com.cardif.satelite.telemarketing.controller;

import static com.cardif.satelite.constantes.Constantes.TIP_PARAM_DETALLE;
import static com.cardif.satelite.constantes.ErrorConstants.ERROR_SYNCCON;
import static com.cardif.satelite.constantes.ErrorConstants.MSJ_ERROR;
import static com.cardif.satelite.constantes.ErrorConstants.MSJ_ERROR_GENERAL;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.richfaces.model.selection.SimpleSelection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cardif.framework.controller.BaseController;
import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.configuracion.service.ParametroService;
import com.cardif.satelite.constantes.Constantes;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.Parametro;
import com.cardif.satelite.model.TlmkBaseCabecera;
import com.cardif.satelite.segya.service.SocioService;
import com.cardif.satelite.telemarketing.service.BaseCabeceraService;
import com.cardif.satelite.telemarketing.service.FeedBackService;
import com.cardif.satelite.util.PropertiesUtil;

@Controller("feedBackTlmkController")
@Scope("request")
public class FeedBackTlmkController extends BaseController {

  public static final Logger log = Logger.getLogger(FeedBackTlmkController.class);

  @Autowired
  private ParametroService parametroService;
  @Autowired
  private SocioService socioService;
  @Autowired
  private BaseCabeceraService baseCabeceraService;
  @Autowired
  private FeedBackService feedBackService;

  private String codSocio;
  private Long codProducto;

  private List<TlmkBaseCabecera> listaBaseCabecera;

  private boolean esNuevo;
  private int tramaIndex = 0;

  private SimpleSelection selection;
  private List<SelectItem> socioItems;

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

      List<Parametro> listaSocio = parametroService.buscar(Constantes.COD_PARAM_SOCIO_TLMK, TIP_PARAM_DETALLE);

      socioItems = new ArrayList<SelectItem>();
      socioItems.add(new SelectItem("", "- Seleccionar -"));

      for (Parametro p : listaSocio) {
        socioItems.add(new SelectItem(p.getCodValor(), p.getNomValor()));
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

  public String buscarBases(ValueChangeEvent event) {
    log.info("Inicio");
    String respuesta = null;

    try {
      if (event.getNewValue() != null) {
        listaBaseCabecera = baseCabeceraService.buscar((String) event.getNewValue());
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

  public String generarFeedBack() {
    log.info("Inicio");
    String respuesta = null;
    TlmkBaseCabecera base = null;
    SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy_hhmm");
    try {
      Iterator<Object> iterator = getSelection().getKeys();

      if (iterator.hasNext()) {

        int key = (Integer) iterator.next();
        base = listaBaseCabecera.get(key);
      }

      if (base == null) {
        throw new SyncconException(ErrorConstants.COD_ERROR_SELECCION, FacesMessage.SEVERITY_INFO);
      }

      if ("BC".equals(base.getCodSocio())) {
        String nombreArchivoInput = "rptaVentasBBVA.xlsx";
        File archivoIn = new File(PropertiesUtil.getProperty("BC") + "input\\" + nombreArchivoInput);

        if (!archivoIn.exists()) {
          throw new SyncconException(ErrorConstants.COD_ERROR_ARCHIVO_FEEDBACK, FacesMessage.SEVERITY_INFO);
        }

        feedBackService.cargarArchivo(base.getCodBase(), base.getCodSocio(), archivoIn);

        String nombreArchivoOutput = "FEEDBACK_BBVA_" + sdf.format(new Date(System.currentTimeMillis())) + ".txt";

        File archivoOut = new File(PropertiesUtil.getProperty("BC") + "output\\" + nombreArchivoOutput);

        feedBackService.generarArchivo(base.getCodBase(), base.getCodSocio(), archivoOut);

        // mover archivo a procesados
        String nombreArchivoNuevo = "RPTA_VENTAS_BBVA_" + sdf.format(new Date(System.currentTimeMillis())) + ".xlsx";

        File archivoNuevo = new File(PropertiesUtil.getProperty("BC") + "\\input\\procesados\\" + nombreArchivoNuevo);

        FileUtils.moveFile(archivoIn, archivoNuevo);
      } else if ("RP".equals(base.getCodSocio())) {
        String nombreArchivoInput = "rptaVentaRIPLEY.xlsx";
        File archivoIn = new File(PropertiesUtil.getProperty("RP") + "input\\" + nombreArchivoInput);

        if (!archivoIn.exists()) {
          throw new SyncconException(ErrorConstants.COD_ERROR_ARCHIVO_FEEDBACK, FacesMessage.SEVERITY_INFO);
        }
      } else if ("IB".equals(base.getCodSocio())) {
        String nombreArchivoInput = "rptaVentaINTERBANK.xlsx";
        File archivoIn = new File(PropertiesUtil.getProperty("IB") + "input\\" + nombreArchivoInput);

        if (!archivoIn.exists()) {
          throw new SyncconException(ErrorConstants.COD_ERROR_ARCHIVO_FEEDBACK, FacesMessage.SEVERITY_INFO);
        }
      } else if ("SB".equals(base.getCodSocio())) {
        String nombreArchivoInput = "rptaVentaSCOTIABANK.xlsx";
        File archivoIn = new File(PropertiesUtil.getProperty("SB") + "input\\" + nombreArchivoInput);

        if (!archivoIn.exists()) {
          throw new SyncconException(ErrorConstants.COD_ERROR_ARCHIVO_FEEDBACK, FacesMessage.SEVERITY_INFO);
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

  public String getCodSocio() {
    return codSocio;
  }

  public void setCodSocio(String codSocio) {
    this.codSocio = codSocio;
  }

  public Long getCodProducto() {
    return codProducto;
  }

  public void setCodProducto(Long codProducto) {
    this.codProducto = codProducto;
  }

  public boolean isEsNuevo() {
    return esNuevo;
  }

  public void setEsNuevo(boolean esNuevo) {
    this.esNuevo = esNuevo;
  }

  public SimpleSelection getSelection() {
    return selection;
  }

  public void setSelection(SimpleSelection selection) {
    this.selection = selection;
  }

  public List<SelectItem> getSocioItems() {
    return socioItems;
  }

  public void setSocioItems(List<SelectItem> socioItems) {
    this.socioItems = socioItems;
  }

  public int getTramaIndex() {
    return tramaIndex;
  }

  public void setTramaIndex(int tramaIndex) {
    this.tramaIndex = tramaIndex;
  }

  public List<TlmkBaseCabecera> getListaBaseCabecera() {
    return listaBaseCabecera;
  }

  public void setListaBaseCabecera(List<TlmkBaseCabecera> listaBaseCabecera) {
    this.listaBaseCabecera = listaBaseCabecera;
  }

}