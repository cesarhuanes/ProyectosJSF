package com.cardif.satelite.actuarial.controller;

import static com.cardif.satelite.constantes.Constantes.COD_PARAM_SOCIO;
import static com.cardif.satelite.constantes.Constantes.COD_PARAM_TIPO_VENTA;
import static com.cardif.satelite.constantes.Constantes.REPORTE_CALCULO_RESERVAS_ACSELE;
import static com.cardif.satelite.constantes.Constantes.TIP_PARAM_DETALLE;
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
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import com.cardif.framework.controller.BaseController;
import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.acsele.service.ProductService;
import com.cardif.satelite.actuarial.service.ActReservaAcseleService;
import com.cardif.satelite.configuracion.service.ParametroService;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.ActReservaAcsele;
import com.cardif.satelite.model.Parametro;
import com.cardif.satelite.model.acsele.Product;
import com.cardif.satelite.ssis.service.SsisService;

/**
 * 
 * @author jhurtado
 * 
 */
@Controller("calcReservaAcseleController")
@Scope("request")
public class CalcReservaAcseleController extends BaseController {

  public static final Logger log = Logger.getLogger(CalcReservaAcseleController.class);

  @Autowired
  private ParametroService parametroService;
  @Autowired
  private ProductService productoService;
  @Autowired
  private ActReservaAcseleService actReservaAcseleService;
  @Autowired
  private SsisService ssisService;

  private String socio;
  private String producto;
  private ActReservaAcsele actReservaAcsele;

  private List<SelectItem> socioItems;
  private List<SelectItem> productoItems;
  private List<SelectItem> tipVentaItems;

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

  public void buscarProducto(ValueChangeEvent event) {
    log.info("Inicio");
    log.info("EVENT: " + (String) event.getNewValue());
    try {
      if (event.getNewValue() != null) {
        List<Product> lista = null;
        if (event.getNewValue().equals("ZZ")) {
          productoItems = new ArrayList<SelectItem>();
          productoItems.add(new SelectItem("", "- Seleccionar -"));
          productoItems.add(new SelectItem("Todos", "Todos"));
        } else {
          productoItems = new ArrayList<SelectItem>();
          productoItems.add(new SelectItem("", "- Seleccionar -"));
          // Descomentar en caso de que se requiera buscar todos los
          // productos de un socio, soportarlo en SSIS Acsele
          // productoItems.add(new SelectItem("ZX", "Todos"));
          lista = productoService.buscar((String) event.getNewValue());
          for (Product pd : lista) {
            productoItems.add(new SelectItem(pd.getProductid().longValue(), pd.getDescription()));
          }
        }
      } else {
        log.info("no se envian valores en el combo socio ");
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }
    log.info("Fin");
  }

  public String procesar() {
    log.info("Inicio");
    String respuesta = null;
    try {
      if (actReservaAcsele.getFecFin().before(actReservaAcsele.getFecInicio())) {
        throw new SyncconException(ErrorConstants.COD_ERROR_FEC_VIG_FIN, FacesMessage.SEVERITY_INFO);
      } else if (actReservaAcsele.getFecCierre().before(actReservaAcsele.getFecInicio())) {
        throw new SyncconException(ErrorConstants.COD_ERROR_FEC_CIERRE_INI, FacesMessage.SEVERITY_INFO);
      } else if (actReservaAcsele.getFecCierre().before(actReservaAcsele.getFecFin())) {
        throw new SyncconException(ErrorConstants.COD_ERROR_FEC_CIERRE_FIN, FacesMessage.SEVERITY_INFO);
      } else {

        if (ESTADO.equalsIgnoreCase(actReservaAcseleService.consultaEstado())) {
          reloadContext();
          throw new SyncconException(COD_ERROR_REPORTE_EJECUCION, FacesMessage.SEVERITY_INFO);
        } else {

          actReservaAcsele.setUsuCreacion(SecurityContextHolder.getContext().getAuthentication().getName());
          actReservaAcsele.setCodSocio(socio);
          actReservaAcsele.setProducto(producto);
          actReservaAcsele.setEstado(ESTADO);
          if (producto.equals("Todos")) {
            actReservaAcsele.setProducto("9999");
            actReservaAcsele.setDescripcionProducto(producto);
            actReservaAcsele.setSocio("Todos");
          } else {
            actReservaAcsele.setProducto(producto);
            actReservaAcsele.setDescripcionProducto(productoService.obtenerDescripcion(producto));
            actReservaAcsele.setSocio(parametroService.obtenerDescripcion(socio, COD_PARAM_SOCIO, TIP_PARAM_DETALLE));
          }
          if (actReservaAcseleService.insertar(actReservaAcsele) == 1) {

            ssisService.lanzarJob(REPORTE_CALCULO_RESERVAS_ACSELE);
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
    log.info("[ Limpiar Inicio  ]");
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
    log.info("[ Limpiar fin ]");
  }

  private void loadCombos() {
    log.info("[ INICIO ]");
    try {
      socio = null;
      producto = null;

      actReservaAcsele = new ActReservaAcsele();

      List<Parametro> listaSocioAcsele = parametroService.buscar(COD_PARAM_SOCIO, TIP_PARAM_DETALLE);
      socioItems = new ArrayList<SelectItem>();
      socioItems.add(new SelectItem("", "- Seleccionar -"));
      socioItems.add(new SelectItem("ZZ", "Todos"));

      for (Parametro p : listaSocioAcsele) {
        socioItems.add(new SelectItem(p.getCodValor(), p.getNomValor()));
      }

      // PRODUCTO X SOCIOS
      productoItems = new ArrayList<SelectItem>();
      productoItems.add(new SelectItem("", "- Seleccionar -"));

      // LISTA PARAMETRO SERVICE
      List<Parametro> listaTipVenta = parametroService.buscar(COD_PARAM_TIPO_VENTA, TIP_PARAM_DETALLE);
      tipVentaItems = new ArrayList<SelectItem>();
      tipVentaItems.add(new SelectItem("", "- Seleccionar -"));

      for (Parametro p : listaTipVenta) {
        tipVentaItems.add(new SelectItem(p.getCodValor(), p.getNomValor()));
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

  public List<SelectItem> getTipVentaItems() {
    return tipVentaItems;
  }

  public void setTipVentaItems(List<SelectItem> tipVentaItems) {
    this.tipVentaItems = tipVentaItems;
  }

  public ActReservaAcsele getActReservaAcsele() {
    return actReservaAcsele;
  }

  public void setActReservaAcsele(ActReservaAcsele actReservaAcsele) {
    this.actReservaAcsele = actReservaAcsele;
  }

}
