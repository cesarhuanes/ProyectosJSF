package com.cardif.satelite.actuarial.controller;

/*
 * 
 */
import static com.cardif.satelite.constantes.Constantes.COD_PARAM_SOCIO;
import static com.cardif.satelite.constantes.Constantes.COD_PARAM_TIPO_SISTEMA;
import static com.cardif.satelite.constantes.Constantes.TIP_PARAM_DETALLE;
import static com.cardif.satelite.constantes.ErrorConstants.ERROR_SYNCCON;
import static com.cardif.satelite.constantes.ErrorConstants.MSJ_ERROR;
import static com.cardif.satelite.constantes.ErrorConstants.MSJ_ERROR_GENERAL;

import java.math.BigDecimal;
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
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.richfaces.model.selection.SimpleSelection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import com.cardif.framework.controller.BaseController;
import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.acsele.service.ProductService;
import com.cardif.satelite.actuarial.bean.ConsultaActCoaseguro;
import com.cardif.satelite.actuarial.service.ActCoaseguroService;
import com.cardif.satelite.configuracion.service.ParametroService;
import com.cardif.satelite.constantes.Constantes;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.ActCoaseguro;
import com.cardif.satelite.model.Parametro;
import com.cardif.satelite.model.acsele.Product;
import com.cardif.satelite.model.segya.Socio;
import com.cardif.satelite.segya.service.SocioService;

/*
 * lmaron: man Coaseguro
 */

/**
 * 
 * @author jhurtado
 * @tipo MODIFICACION
 * @detalles: validaciones en listas botones
 * 
 */

@Controller("manActCoaseguroController")
@Scope("request")
public class ManActCoaseguroController extends BaseController {
  public static final Logger log = Logger.getLogger(ManActCoaseguroController.class);
  @Autowired
  private ParametroService parametroService;
  @Autowired
  private ProductService productoService;
  @Autowired
  private SocioService socioService;
  @Autowired
  private ActCoaseguroService actCoaseguroService;

  private List<ConsultaActCoaseguro> listaActCoaseguro;
  private ActCoaseguro actCoaseguro;
  private Product product;
  private Parametro parametro;
  private Socio socioSegya;
  private Long codSistema;
  private String socio;
  private String codSocio;
  private Long codProducto;
  private String producto;

  // Mantenimiento
  private String manSistema;

  private boolean esNuevo;
  private boolean estadoEliminado;
  private boolean estadoEditar;
  private SimpleSelection selection;
  private List<SelectItem> sistemaItems;
  private List<SelectItem> socioItems;
  private List<SelectItem> productoItems;

  private List<SelectItem> socioItemsMant;
  private List<SelectItem> productoItemsMant;

  private String sitema_temp = "";

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
      actCoaseguro = new ActCoaseguro();
      listaActCoaseguro = new ArrayList<ConsultaActCoaseguro>();
      selection = new SimpleSelection();
      esNuevo = false;
      estadoEliminado = false;
      socioItemsMant = new ArrayList<SelectItem>();
      productoItemsMant = new ArrayList<SelectItem>();

      product = new Product();
      // aux="";
      /*** Tipo de Sistema ***************/
      List<Parametro> listaSistema = parametroService.buscar(COD_PARAM_TIPO_SISTEMA, TIP_PARAM_DETALLE);
      sistemaItems = new ArrayList<SelectItem>();
      sistemaItems.add(new SelectItem("", "- Seleccionar -"));
      for (Parametro p : listaSistema) {
        sistemaItems.add(new SelectItem(Long.parseLong(p.getCodValor()), p.getNomValor()));
      }
      /*** socio Acsele ***************/
      socioItems = new ArrayList<SelectItem>();
      socioItems.add(new SelectItem("", "- Seleccionar -"));

      socioItemsMant = new ArrayList<SelectItem>();
      socioItemsMant.add(new SelectItem("", "- Seleccionar -"));

      /*** lista de productos ***************/

      productoItems = new ArrayList<SelectItem>();
      productoItems.add(new SelectItem("", "- Seleccionar -"));
      productoItemsMant = new ArrayList<SelectItem>();
      productoItemsMant.add(new SelectItem("", "- Seleccionar -"));

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

    log.info("Final");
    return respuesta;
  }

  /*-------------------------------------------------------*/

  public String cambiarEstado() {
    log.info("Inicio");
    String respuesta = "";
    try {
      estadoEliminado = true;
      estadoEditar = true;
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
    log.info("Fin");
    return respuesta;
  }

  public String consultar() {
    log.info("Inicio");

    String respuesta = null;

    actCoaseguro = new ActCoaseguro();
    listaActCoaseguro = null;
    selection = new SimpleSelection();

    try {
      listaActCoaseguro = new ArrayList<ConsultaActCoaseguro>();
      estadoEliminado = false;
      setEstadoEditar(false);
      // reloadContext();

      log.debug("test: " + codSistema);
      listaActCoaseguro = actCoaseguroService.buscar(codSistema, codSocio, codProducto);
      // log.info("Fecha Inicio"+listaActCoaseguro.get(0).getFecIniCoaseguro());
      /*
       * Collections.sort(listaActCoaseguro, new Comparator<ConsultaActCoaseguro>() { public int compare(ConsultaActCoaseguro co, ConsultaActCoaseguro
       * co1) { return co.getFecFinCoaseguro().compareTo(co1.getFecFinCoaseguro()!= 0); } });
       */
      if (listaActCoaseguro.isEmpty()) {
        estadoEditar = false;
        estadoEliminado = false;
      }
      // log.info("Fecha Inicio"+listaActCoaseguro.get(0).getFecIniCoaseguro());
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

  // -----------------------------------------------

  public String insertar() {
    log.info("Inicio");
    String respuesta = null;
    try {
      esNuevo = true;
      estadoEliminado = false;
      actCoaseguro = new ActCoaseguro();
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
      respuesta = MSJ_ERROR;
    }
    log.info("Fin");
    return respuesta;
  }

  public String aceptar() {
    log.info("Inicio");
    String respuesta = null;

    try {

      if (esNuevo) {
        if (actCoaseguro.getCodSistema() == 2) {
          socioSegya = socioService.obtener(Integer.parseInt(actCoaseguro.getCodSocio()));
          actCoaseguro.setProducto("ExtraGarantia");
          actCoaseguro.setSocio(socioSegya.getDescripcion());
        } else {
          socio = parametroService.obtener(Constantes.COD_PARAM_SOCIO, Constantes.TIP_PARAM_DETALLE, actCoaseguro.getCodSocio()).getNomValor();
          product = productoService.obtener(BigDecimal.valueOf(actCoaseguro.getCodProducto()));
          actCoaseguro.setProducto(product.getDescription());
          actCoaseguro.setSocio(socio);
        }

        Date fecha = actCoaseguroService.fechaMax(actCoaseguro.getCodSistema(), actCoaseguro.getCodSocio(), actCoaseguro.getCodProducto());

        if (actCoaseguro.getFecFinCoaseguro().before(actCoaseguro.getFecIniCoaseguro())) {
          throw new SyncconException(ErrorConstants.COD_ERROR_FEC_VIG_FIN, FacesMessage.SEVERITY_INFO);
        }

        if (fecha == null) {

        } else {

          if (actCoaseguro.getFecIniCoaseguro().before(fecha)) {
            throw new SyncconException(ErrorConstants.COD_ERROR_RANGO_FEC, FacesMessage.SEVERITY_INFO);
          }
        }

        actCoaseguro.setUsuCreacion(SecurityContextHolder.getContext().getAuthentication().getName());
        actCoaseguroService.insertar(actCoaseguro);
      } else {
        log.debug("Datos act: " + getCodSistema());

        Date fecha = actCoaseguroService.fechaMax(actCoaseguro.getCodSistema(), actCoaseguro.getCodSocio(), actCoaseguro.getCodProducto());

        Date fechaAnt = actCoaseguroService.fechaMaxAnt(actCoaseguro.getCodSistema(), actCoaseguro.getCodSocio(), actCoaseguro.getCodProducto(), fecha);

        if (actCoaseguro.getFecFinCoaseguro().before(actCoaseguro.getFecIniCoaseguro())) {
          throw new SyncconException(ErrorConstants.COD_ERROR_FEC_VIG_FIN, FacesMessage.SEVERITY_INFO);
        }

        if (fecha == null || fechaAnt == null) {

        } else {

          if (actCoaseguro.getFecIniCoaseguro().before(fechaAnt)) {
            throw new SyncconException(ErrorConstants.COD_ERROR_RANGO_FEC, FacesMessage.SEVERITY_INFO);
          }
        }

        actCoaseguro.setUsuModificacion(SecurityContextHolder.getContext().getAuthentication().getName());
        actCoaseguroService.actualizar(actCoaseguro);
      }

      // listaActCoaseguro = actCoaseguroService.buscar(codSistema,
      // codSocio, codProducto);
      /*
       * Collections.sort(listaActCoaseguro, new Comparator<ConsultaActCoaseguro>() { public int compare(ConsultaActCoaseguro co, ConsultaActCoaseguro
       * co1) { return co.getFecFinCoaseguro().compareTo(co1.getFecFinCoaseguro()); } });
       */
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

  public void buscarSocio(ValueChangeEvent event) {
    log.info("Inicio");

    try {
      if (event.getNewValue() != null) {

        socioItems = new ArrayList<SelectItem>();
        socioItems.add(new SelectItem("", "- Seleccionar -"));

        socioItemsMant = new ArrayList<SelectItem>();
        socioItemsMant.add(new SelectItem("", "- Seleccionar -"));

        if ((Long) event.getNewValue() == 1) {
          log.info("Acsele");
          List<Parametro> listaSocioAcsele = parametroService.buscar(COD_PARAM_SOCIO, TIP_PARAM_DETALLE);
          socioItems = new ArrayList<SelectItem>();
          socioItems.add(new SelectItem("", "- Seleccionar -"));
          socioItemsMant = new ArrayList<SelectItem>();
          socioItemsMant.add(new SelectItem("", "- Seleccionar -"));

          for (Parametro p : listaSocioAcsele) {
            socioItems.add(new SelectItem(p.getCodValor(), p.getNomValor()));
            socioItemsMant.add(new SelectItem(p.getCodValor(), p.getNomValor()));
          }

          sitema_temp = "A";

        } else {

          List<Socio> lista = null;
          log.info("segya");
          lista = socioService.buscar();
          for (Socio s : lista) {
            socioItems.add(new SelectItem(s.getCodigoSocio().toString(), s.getDescripcion()));
            socioItemsMant.add(new SelectItem(s.getCodigoSocio().toString(), s.getDescripcion()));
          }
          sitema_temp = "S";
        }
      } else {

        log.info("No se enviaron datos en el combo Sistema");
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

    log.info("Fin");
  }

  public void buscarProducto(ValueChangeEvent event) {
    log.info("Inicio");

    try {

      if (event.getNewValue() != null) {
        List<Product> lista = null;
        productoItems = new ArrayList<SelectItem>();
        productoItems.add(new SelectItem("", "- Seleccionar -"));

        productoItemsMant = new ArrayList<SelectItem>();
        productoItemsMant.add(new SelectItem("", "- Seleccionar -"));
        log.debug("codSocio: " + (String) event.getNewValue());
        // if(codSistema == 2){
        if (sitema_temp.equalsIgnoreCase("S")) {
          productoItems.add(new SelectItem("1", "ExtraGarantia"));
          productoItemsMant.add(new SelectItem("1", "ExtraGarantia"));
        }
        // else {
        else if (sitema_temp.equalsIgnoreCase("A")) {
          lista = productoService.buscar((String) event.getNewValue());
          for (Product pd : lista) {
            productoItems.add(new SelectItem(pd.getProductid().longValue(), pd.getDescription()));
            productoItemsMant.add(new SelectItem(pd.getProductid().longValue(), pd.getDescription()));
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

  public String obtener() {
    log.info("Inicio");
    esNuevo = false;
    String respuesta = null;
    actCoaseguro = null;
    try {
      Iterator<Object> iterator = getSelection().getKeys();
      if (iterator.hasNext()) {
        int key = (Integer) iterator.next();
        ConsultaActCoaseguro cc = listaActCoaseguro.get(key);
        log.debug("Key: " + key);
        Long pk = cc.getCodCoaseguro();
        actCoaseguro = actCoaseguroService.obtener(pk);
      }

      if (actCoaseguro == null) {
        throw new SyncconException(ErrorConstants.COD_ERROR_SELECCION, FacesMessage.SEVERITY_INFO);
      }

      socioItemsMant = new ArrayList<SelectItem>();
      socioItemsMant.add(new SelectItem("", "- Seleccionar -"));
      socioItemsMant.add(new SelectItem(actCoaseguro.getCodSocio(), actCoaseguro.getSocio()));

      productoItemsMant = new ArrayList<SelectItem>();
      productoItemsMant.add(new SelectItem("", "- Seleccionar -"));
      productoItemsMant.add(new SelectItem(actCoaseguro.getCodProducto(), actCoaseguro.getProducto()));

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

  public String actualizar() {
    log.info("Inicio");
    String respuesta = null;
    try {
      String usuario = SecurityContextHolder.getContext().getAuthentication().getName();
      actCoaseguro.setUsuModificacion(usuario);

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
      respuesta = MSJ_ERROR;
    }
    log.info("Fin");
    return respuesta;
  }

  public String eliminar() {
    String respuesta = null;
    log.info("Inicio");
    ConsultaActCoaseguro consultaCoaseguro = null;
    Iterator<Object> iterator = getSelection().getKeys();
    Object key = new Object();
    try {
      while (iterator.hasNext()) {
        key = iterator.next();
        consultaCoaseguro = listaActCoaseguro.get((Integer) key);

        if (actCoaseguro == null) {
          throw new SyncconException(ErrorConstants.COD_ERROR_SELECCION, FacesMessage.SEVERITY_INFO);
        }
      }

      if (consultaCoaseguro == null) {
        throw new SyncconException(ErrorConstants.COD_ERROR_SELECCION, FacesMessage.SEVERITY_INFO);
      }

      actCoaseguroService.eliminar(consultaCoaseguro.getCodCoaseguro());
      consultar();
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
    actCoaseguro = new ActCoaseguro();
    sistemaItems = new ArrayList<SelectItem>();
    codSocio = null;
    codSistema = null;
    listaActCoaseguro = null;
    selection = new SimpleSelection();
    try {

      listaActCoaseguro = new ArrayList<ConsultaActCoaseguro>();
      estadoEliminado = false;
      setEstadoEditar(false);

      sistemaItems.add(new SelectItem("", "- Seleccionar -"));
      List<Parametro> listaSistema = parametroService.buscar(COD_PARAM_TIPO_SISTEMA, TIP_PARAM_DETALLE);

      for (Parametro p : listaSistema) {
        sistemaItems.add(new SelectItem(p.getCodValor(), p.getNomValor()));
      }

      socioItems = new ArrayList<SelectItem>();
      socioItems.add(new SelectItem("", "- Seleccionar -"));

      productoItems = new ArrayList<SelectItem>();
      productoItems.add(new SelectItem("", "- Seleccionar -"));

      socioItemsMant = new ArrayList<SelectItem>();
      socioItemsMant.add(new SelectItem("", "- Seleccionar -"));

      productoItemsMant = new ArrayList<SelectItem>();
      productoItemsMant.add(new SelectItem("", "- Seleccionar -"));

      reloadContext();

    } catch (SyncconException ex) {
      log.error(ERROR_SYNCCON + ex.getMessageComplete());
      FacesMessage facesMsg = new FacesMessage(ex.getSeveridad(), ex.getMessage(), null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

    log.info("Fin");
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

  public List<SelectItem> getSistemaItems() {
    return sistemaItems;
  }

  public void setSistemaItems(List<SelectItem> sistemaItems) {
    this.sistemaItems = sistemaItems;
  }

  public List<SelectItem> getSocioItems() {
    return socioItems;
  }

  public void setSocioItems(List<SelectItem> socioItems) {
    this.socioItems = socioItems;
  }

  public ActCoaseguro getActCoaseguro() {
    return actCoaseguro;
  }

  public void setActCoaseguro(ActCoaseguro actCoaseguro) {
    this.actCoaseguro = actCoaseguro;
  }

  public List<SelectItem> getProductoItems() {
    return productoItems;
  }

  public void setProductoItems(List<SelectItem> productoItems) {
    this.productoItems = productoItems;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
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

  public Socio getSocioSegya() {
    return socioSegya;
  }

  public void setSocioSegya(Socio socioSegya) {
    this.socioSegya = socioSegya;
  }

  public Parametro getParametro() {
    return parametro;
  }

  public void setParametro(Parametro parametro) {
    this.parametro = parametro;
  }

  public List<ConsultaActCoaseguro> getListaActCoaseguro() {
    return listaActCoaseguro;
  }

  public void setListaActCoaseguro(List<ConsultaActCoaseguro> listaActCoaseguro) {
    this.listaActCoaseguro = listaActCoaseguro;
  }

  public SimpleSelection getSelection() {
    return selection;
  }

  public void setSelection(SimpleSelection selection) {
    this.selection = selection;
  }

  public Long getCodSistema() {
    return codSistema;
  }

  public void setCodSistema(Long codSistema) {
    this.codSistema = codSistema;
  }

  public boolean isEsNuevo() {
    return esNuevo;
  }

  public void setEsNuevo(boolean esNuevo) {
    this.esNuevo = esNuevo;
  }

  public String getManSistema() {
    return manSistema;
  }

  public void setManSistema(String manSistema) {
    this.manSistema = manSistema;
  }

  public boolean isEstadoEliminado() {
    return estadoEliminado;
  }

  public void setEstadoEliminado(boolean estadoEliminado) {
    this.estadoEliminado = estadoEliminado;
  }

  public boolean isEstadoEditar() {
    return estadoEditar;
  }

  public void setEstadoEditar(boolean estadoEditar) {
    this.estadoEditar = estadoEditar;
  }

  public List<SelectItem> getSocioItemsMant() {
    return socioItemsMant;
  }

  public void setSocioItemsMant(List<SelectItem> socioItemsMant) {
    this.socioItemsMant = socioItemsMant;
  }

  public List<SelectItem> getProductoItemsMant() {
    return productoItemsMant;
  }

  public void setProductoItemsMant(List<SelectItem> productoItemsMant) {
    this.productoItemsMant = productoItemsMant;
  }

}
