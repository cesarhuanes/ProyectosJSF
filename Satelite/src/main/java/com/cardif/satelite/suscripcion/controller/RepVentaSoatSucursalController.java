package com.cardif.satelite.suscripcion.controller;

import static com.cardif.satelite.constantes.ErrorConstants.ERROR_SYNCCON;
import static com.cardif.satelite.constantes.ErrorConstants.MSJ_ERROR;
import static com.cardif.satelite.constantes.ErrorConstants.MSJ_ERROR_GENERAL;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cardif.framework.controller.BaseController;
import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.soatsucursal.Socio;
import com.cardif.satelite.model.soatsucursal.Sucursal;
import com.cardif.satelite.suscripcion.bean.RepVentaSoatSucursalBean;
import com.cardif.satelite.suscripcion.service.RepVentaSoatSucursalService;
import com.cardif.satelite.suscripcion.service.RepVentaSoatSucursalSocioService;
import com.cardif.satelite.suscripcion.service.RepVentaSoatSucursalSucursalService;

@Controller("repVentaSoatSucursalController")
@Scope("request")
public class RepVentaSoatSucursalController extends BaseController {

  public static final Logger log = Logger.getLogger(RepVentaSoatSucursalController.class);
  final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
  private Date fecDesde;
  private Date fecHasta;
  private String socio;
  private String sucursal;

  @Autowired
  RepVentaSoatSucursalService repVentaSoatSucursalService;
  @Autowired
  RepVentaSoatSucursalSucursalService repVentaSoatSucursalSucursalService;
  @Autowired
  RepVentaSoatSucursalSocioService repVentaSoatSucursalSocioService;

  private List<RepVentaSoatSucursalBean> lista;
  private List<Socio> listaSocio;
  private List<Sucursal> listaSucursal;

  private List<SelectItem> listaSocioItem;
  private List<SelectItem> listaSucursalItem;

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
      lista = new ArrayList<RepVentaSoatSucursalBean>();
      listaSocio = new ArrayList<Socio>();
      listaSucursal = new ArrayList<Sucursal>();
      listaSocioItem = new ArrayList<SelectItem>();
      listaSucursalItem = new ArrayList<SelectItem>();

      /*** lista de socios ***************/
      List<Socio> listaSocio = repVentaSoatSucursalSocioService.listarSocio();
      listaSocioItem = new ArrayList<SelectItem>();
      listaSocioItem.add(new SelectItem("", "- Seleccionar -"));
      for (Socio s : listaSocio) {
        listaSocioItem.add(new SelectItem(s.getCodSocio(), s.getDscSocio()));
      }

    } catch (SyncconException ex) {
      log.error(ERROR_SYNCCON + ex.getMessageComplete());
      FacesMessage facesMsg = new FacesMessage(ex.getSeveridad(), ex.getMessage(), null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
      respuesta = MSJ_ERROR;
    }

    log.info("Final");
    return respuesta;
  }

  public String consultar() {
    log.info("Inicio");
    String respuesta = null;
    String desde = null;
    String hasta = null;
    try {
      desde = formatearFecha(getFecDesde());
      hasta = formatearFecha(getFecHasta());
      if (getFecDesde().after(getFecHasta()))
        throw new SyncconException(ErrorConstants.COD_ERROR_FEC_VIG_FIN, FacesMessage.SEVERITY_INFO);

      if (getSucursal() == null) {
        lista = repVentaSoatSucursalService.listarReporteSoatSucursal(desde, hasta, getSocio());
      } else {
        lista = repVentaSoatSucursalService.listarReporteSoatSucursal(desde, hasta, getSocio(), getSucursal());
      }

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

    log.info("Fin");
    return respuesta;
  }

  private String formatearFecha(Date fecha) throws Exception {
    String fechaFormato = null;
    SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    if (fecha != null) {
      fechaFormato = DATE_FORMAT.format(fecha);
    }
    return fechaFormato;
  }

  public String procesarSucursal(ValueChangeEvent event) {
    log.info("Inicio");
    String respuesta = null;

    try {
      sucursal = (String) event.getNewValue();
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
      respuesta = MSJ_ERROR;
    }

    log.info("Fin");
    return respuesta;

  }

  public String buscarSucursal(ValueChangeEvent event) {
    log.info("Inicio");
    String respuesta = null;
    String codSucursal = "";
    listaSucursalItem = new ArrayList<SelectItem>();
    try {
      codSucursal = (String) event.getNewValue();
      log.info(" EL CODIGO ES :" + codSucursal);
      setSocio(codSucursal);

      if (event.getNewValue() != null) {
        List<Sucursal> listaSucursal = repVentaSoatSucursalSucursalService.listarPorSocio(codSucursal);
        listaSucursalItem.add(new SelectItem("", "- Seleccionar -"));
        for (Sucursal s : listaSucursal) {
          listaSucursalItem.add(new SelectItem(s.getId(), s.getNombre()));
        }
      } else {
        listaSucursalItem.add(new SelectItem("", "- Seleccionar -"));
      }
    }

    catch (Exception e) {
      log.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
      respuesta = MSJ_ERROR;
    }

    log.info("Fin");
    return respuesta;
  }

  public String exportar() {
    log.info("Inicio");

    ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
    String FechaActual = sdf.format(new Date(System.currentTimeMillis()));
    try {
      if (lista.size() == 0) {
        throw new SyncconException(ErrorConstants.COD_ERROR_SUSCRIP_LISTA_VACIA_AL_EXPORTAR, FacesMessage.SEVERITY_INFO);
      }

      Map<String, Object> beans = new HashMap<String, Object>();
      String rutaTemp = System.getProperty("java.io.tmpdir") + "Reporte_Soat_" + System.currentTimeMillis() + ".xlsx";
      log.debug("Ruta Archivo: " + rutaTemp);
      FacesContext fc = FacesContext.getCurrentInstance();
      ServletContext sc = (ServletContext) fc.getExternalContext().getContext();
      String rutaTemplate = sc.getRealPath(File.separator + "excel" + File.separator + "template_repVentaSoatSucursal.xlsx");

      beans.put("exportar", lista);

      XLSTransformer transformer = new XLSTransformer();
      transformer.transformXLS(rutaTemplate, beans, rutaTemp);
      File archivoResp = new File(rutaTemp);
      FileInputStream in = new FileInputStream(archivoResp);
      HttpServletResponse response = (HttpServletResponse) contexto.getResponse();

      byte[] loader = new byte[(int) archivoResp.length()];
      response.addHeader("Content-Disposition", "attachment;filename=" + "Reporte_Soat_" + FechaActual + ".xlsx");
      response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
      ServletOutputStream out = response.getOutputStream();
      while ((in.read(loader)) > 0) {
        out.write(loader, 0, loader.length);
      }
      in.close();
      out.close();
      FacesContext.getCurrentInstance().responseComplete();

    } catch (SyncconException ex) {
      log.error(ERROR_SYNCCON + ex.getMessageComplete());
      FacesMessage facesMsg = new FacesMessage(ex.getSeveridad(), ex.getMessage(), null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
      // throw new SyncconException(ErrorConstants.COD_ERROR_GENERAL,
      // FacesMessage.SEVERITY_INFO);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

    log.info("Fin");
    return null;
  }

  public List<RepVentaSoatSucursalBean> getLista() {
    return lista;
  }

  public void setLista(List<RepVentaSoatSucursalBean> lista) {
    this.lista = lista;
  }

  public Date getFecHasta() {
    return fecHasta;
  }

  public void setFecHasta(Date fecHasta) {
    this.fecHasta = fecHasta;
  }

  public Date getFecDesde() {
    return fecDesde;
  }

  public void setFecDesde(Date fecDesde) {
    this.fecDesde = fecDesde;
  }

  public List<Sucursal> getListaSucursal() {
    return listaSucursal;
  }

  public void setListaSucursal(List<Sucursal> listaSucursal) {
    this.listaSucursal = listaSucursal;
  }

  public List<Socio> getListaSocio() {
    return listaSocio;
  }

  public void setListaSocio(List<Socio> listaSocio) {
    this.listaSocio = listaSocio;
  }

  public List<SelectItem> getListaSocioItem() {
    return listaSocioItem;
  }

  public void setListaSocioItem(List<SelectItem> listaSocioItem) {
    this.listaSocioItem = listaSocioItem;
  }

  public List<SelectItem> getListaSucursalItem() {
    return listaSucursalItem;
  }

  public void setListaSucursalItem(List<SelectItem> listaSucursalItem) {
    this.listaSucursalItem = listaSucursalItem;
  }

  public String getSocio() {
    return socio;
  }

  public void setSocio(String socio) {
    this.socio = socio;
  }

  public String getSucursal() {
    return sucursal;
  }

  public void setSucursal(String sucursal) {
    this.sucursal = sucursal;
  }

}
