package com.cardif.satelite.tesoreria.controller;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.lang.exception.ExceptionUtils;
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
import com.cardif.satelite.tesoreria.model.ComprobanteElectronico;
import com.cardif.satelite.tesoreria.service.ComprobanteRetencionService;
import com.cardif.satelite.util.SateliteUtil;
import com.cardif.satelite.util.Utilitarios;
import com.cardif.sunsystems.util.ConstantesSun;

@Controller("anulacionCertificadoController")
@Scope("request")
public class AnulacionCertificadoController extends BaseController {

  public static final Logger logger = Logger.getLogger(AnulacionCertificadoController.class);

  @Autowired
  private ComprobanteRetencionService comprobanteRetencionService;

  @Autowired
  private ParametroService parametroService;

  private List<ComprobanteElectronico> listaCertificados;
  private String nroDiario;
  private String nroComprobanteRetencion;
  private Date fechaDesde;
  private Date fechaHasta;
  private String proveedor;
  private String rucProveedor;
  private String unidadNegocio;
  private String causa;
  private Boolean disabledBtnAnular = true;
  private ComprobanteElectronico certificadoActual;

  private SimpleSelection selection;
  private SimpleSelection selectionActivo;
  private List<SelectItem> comboListaUnidadNegocio;
  private List<SelectItem> comboListaCausa;

  @PostConstruct
  @Autowired
  public String inicio() {

    if (logger.isInfoEnabled()) {
      logger.info("Inicio");
    }

    this.nroDiario = null;
    this.nroComprobanteRetencion = null;
    this.fechaDesde = null;
    this.fechaHasta = null;
    this.proveedor = null;
    this.rucProveedor = null;
    this.unidadNegocio = null;
    this.listaCertificados = new ArrayList<ComprobanteElectronico>();

    this.comboListaCausa = new ArrayList<SelectItem>();
    this.comboListaUnidadNegocio = new ArrayList<SelectItem>();
    this.selection = new SimpleSelection();
    disabledBtnAnular = true;
    this.certificadoActual = new ComprobanteElectronico();
    String respuesta = null;
    try {

      // *****************************************************************************
      // ************************ CARGAS LOS VALORES INCIALES
      // ************************
      // *****************************************************************************

      if (logger.isInfoEnabled()) {
        logger.info("Buscando la lista de UNIDADES DE NEGOCIO.");
      }
      List<Parametro> listaUnidadNegocios = parametroService.buscar(Constantes.COD_PARAM_COMPANIA, Constantes.TIP_PARAM_DETALLE);
      this.comboListaUnidadNegocio.add(new SelectItem("", "- Seleccione -"));

      for (Parametro unidadNegocio : listaUnidadNegocios) {
        if (logger.isDebugEnabled()) {
          logger.debug("UNIDAD_NEGOCIO ==> COD_VALOR: " + unidadNegocio.getCodValor() + " - NOM_VALOR: " + unidadNegocio.getNomValor());
        }
        this.comboListaUnidadNegocio.add(new SelectItem(unidadNegocio.getCodValor(), unidadNegocio.getNomValor()));
      }

      if (logger.isInfoEnabled()) {
        logger.info("Buscando la lista de UNIDADES DE NEGOCIO.");
      }
      // **********************************************************************************
      // *************************** CARGA LOS VALORES INICIALES DE CAUSAS DE ANULACION
      // ***************************
      // **********************************************************************************
      List<Parametro> listaCausasRetencion = parametroService.buscar(ConstantesSun.COD_PARAM_CAUSA_ANULACION, Constantes.TIP_PARAM_DETALLE);

      this.comboListaCausa.add(new SelectItem("", "- Seleccione -"));

      for (Parametro causaRetencion : listaCausasRetencion) {
        if (logger.isDebugEnabled()) {
          logger.debug("UNIDAD_NEGOCIO ==> COD_VALOR: " + causaRetencion.getCodValor() + " - NOM_VALOR: " + causaRetencion.getNomValor());
        }

        this.comboListaCausa.add(new SelectItem(causaRetencion.getCodValor(), causaRetencion.getNomValor()));

      }

    } catch (SyncconException e) {

      logger.error("SyncconException() - ERROR: " + ErrorConstants.ERROR_SYNCCON + e.getMessageComplete());
      logger.error("SyncconException() -->" + ExceptionUtils.getStackTrace(e));
      FacesMessage facesMsg = new FacesMessage(e.getSeveridad(), e.getMessage(), null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);

    } catch (Exception e) {

      logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
      logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
      respuesta = ErrorConstants.MSJ_ERROR;

    }
    if (logger.isInfoEnabled()) {
      logger.info("Fin");
    }

    return respuesta;
  }

  public String buscar() {

    this.listaCertificados.clear();
    this.certificadoActual = null;
    try {

      this.unidadNegocio = MessageFormat.format("E0{0}", unidadNegocio);

      if (this.fechaHasta != null && this.fechaDesde != null) {
        if (!this.fechaHasta.after(this.fechaDesde) && !this.fechaDesde.equals(this.fechaHasta)) {

          SateliteUtil.mostrarMensaje("La fecha hasta debe ser mayor a la fecha desde");
          return null;
        }
      }

      this.listaCertificados = comprobanteRetencionService.buscarRetencionesEmitidasAnuladas(unidadNegocio, nroDiario, fechaDesde, fechaHasta, rucProveedor, nroComprobanteRetencion);

    } catch (SyncconException e) {
      logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
      logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
    }
    logger.info("Lista size:" + listaCertificados.size());
    return null;
  }

  public String anularCertificado() {

    if (logger.isInfoEnabled()) {
      logger.info("Inicio");
    }

    String respuesta = null;
    Boolean IsOk = Boolean.FALSE;

    try {

      if (Utilitarios.verificarRangoDeFechas(certificadoActual.getFechaEmision(), Calendar.getInstance().getTime(), Constantes.PLAZO_DIAS_HABILES_SUNAT) == Boolean.FALSE) {
        SateliteUtil.mostrarMensaje("La fecha de emisión del certificado ha superado los 7 días, debe reversar el pago");
      }
      if (certificadoActual.getCausaAnulacion() != null && certificadoActual.getMotivoAnulacion() != null) {

        if (logger.isInfoEnabled()) {
          logger.info("Input [1.- " + certificadoActual.getNroComprobanteElectronico() + ",2.-" + certificadoActual.getProNroDocumento() + ",3.-" + certificadoActual.getProRazonSocial() + ",4.-"
              + certificadoActual.getMonedaPago() + ",5.-" + certificadoActual.getImporteRetPer() + ",7.-" + certificadoActual.getMotivoAnulacion() + ",8.-" + certificadoActual.getCausaAnulacion()
              + ",9.-" + certificadoActual.getNroDiario() + " ]");
        }

        IsOk = comprobanteRetencionService.anularRetencion(unidadNegocio, certificadoActual);

        if (logger.isInfoEnabled()) {
          logger.info("Output [Respuesta: " + IsOk + "]");
        }

        if (IsOk == false) {

          FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, ErrorConstants.MSJ_ERROR_GENERAL, null);
          FacesContext.getCurrentInstance().addMessage(null, facesMsg);
        }
        buscar();
      }

    } catch (Exception e) {
      logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
      logger.error("Exception (" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));

      FacesMessage faceMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, faceMsg);
      respuesta = ErrorConstants.MSJ_ERROR;
    }

    if (logger.isInfoEnabled()) {
      logger.info("Fin");
    }

    return respuesta;
  }

  public void onSelectionChanged() {

    if (selectionActivo != null) {
      Iterator<Object> it = selectionActivo.getKeys();
      while (it.hasNext()) {
        int key = Integer.parseInt(it.next().toString());
        System.out.println("key: " + key);
        certificadoActual = listaCertificados.get(key);

      }
      disabledBtnAnular = false;
      System.out.println("disabledBtnAnular -->:" + disabledBtnAnular);
      System.out.println("Nuevo cod certificado: " + certificadoActual.getNroComprobanteElectronico());
    } else {
      System.out.println("No selection is set.");
    }

  }

  public void seleccionarCertificadoActivo() {
    logger.info("Inicio");

    certificadoActual = (ComprobanteElectronico) SateliteUtil.verSeleccionado(selectionActivo, listaCertificados);

    logger.info("Fin");
  }

  public void validafechafinal(ValueChangeEvent event) {
    FacesMessage faceMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "La fecha final debe ser mayor a la fecha inicial", null);
    FacesContext.getCurrentInstance().addMessage(null, faceMsg);
  }

  public String getCausa() {
    return causa;
  }

  public void setCausa(String causa) {
    this.causa = causa;
  }

  public List<SelectItem> getComboListaCausa() {
    return comboListaCausa;
  }

  public void setComboListaCausa(List<SelectItem> comboListaCausa) {
    this.comboListaCausa = comboListaCausa;
  }

  public SimpleSelection getSelectionActivo() {
    return selectionActivo;
  }

  public void setSelectionActivo(SimpleSelection selectionActivo) {
    this.selectionActivo = selectionActivo;
  }

  public List<SelectItem> getComboListaUnidadNegocio() {
    return comboListaUnidadNegocio;
  }

  public void setComboListaUnidadNegocio(List<SelectItem> comboListaUnidadNegocio) {
    this.comboListaUnidadNegocio = comboListaUnidadNegocio;
  }

  public SimpleSelection getSelection() {
    return selection;
  }

  public void setSelection(SimpleSelection selection) {
    this.selection = selection;
  }

  public String getNroDiario() {
    return nroDiario;
  }

  public void setNroDiario(String nroDiario) {
    this.nroDiario = nroDiario;
  }

  public String getNroComprobanteRetencion() {
    return nroComprobanteRetencion;
  }

  public void setNroComprobanteRetencion(String nroComprobanteRetencion) {
    this.nroComprobanteRetencion = nroComprobanteRetencion;
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

  public String getProveedor() {
    return proveedor;
  }

  public void setProveedor(String proveedor) {
    this.proveedor = proveedor;
  }

  public String getRucProveedor() {
    return rucProveedor;
  }

  public void setRucProveedor(String rucProveedor) {
    this.rucProveedor = rucProveedor;
  }

  public String getUnidadNegocio() {
    return unidadNegocio;
  }

  public void setUnidadNegocio(String unidadNegocio) {
    this.unidadNegocio = unidadNegocio;
  }

  public List<ComprobanteElectronico> getListaCertificados() {
    return listaCertificados;
  }

  public void setListaCertificados(List<ComprobanteElectronico> listaCertificados) {
    this.listaCertificados = listaCertificados;
  }

  public ComprobanteElectronico getCertificadoActual() {
    return certificadoActual;
  }

  public void setCertificadoActual(ComprobanteElectronico certificadoActual) {
    this.certificadoActual = certificadoActual;
  }

  public Boolean getDisabledBtnAnular() {
    return disabledBtnAnular;
  }

  public void setDisabledBtnAnular(Boolean disabledBtnAnular) {
    this.disabledBtnAnular = disabledBtnAnular;
  }
}
