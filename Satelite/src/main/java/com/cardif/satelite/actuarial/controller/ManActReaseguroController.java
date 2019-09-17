package com.cardif.satelite.actuarial.controller;

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
import com.cardif.satelite.actuarial.bean.ConsultaActReaseguro;
import com.cardif.satelite.actuarial.service.ActReaseguroService;
import com.cardif.satelite.configuracion.service.ParametroService;
import com.cardif.satelite.constantes.Constantes;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.ActReaseguro;
import com.cardif.satelite.model.Parametro;
import com.cardif.satelite.model.acsele.Product;
import com.cardif.satelite.model.segya.Socio;
import com.cardif.satelite.segya.service.SocioService;

@Controller("manActReaseguroController")
@Scope("request")
public class ManActReaseguroController extends BaseController {

  public static final Logger log = Logger.getLogger(ManActReaseguroController.class);
  @Autowired
  private ParametroService parametroService;
  @Autowired
  private ProductService productoService;
  @Autowired
  private SocioService socioService;
  @Autowired
  private ActReaseguroService actReaseguroService;

  private ActReaseguro actReaseguro;
  private List<ConsultaActReaseguro> listaActReaseguro;
  private List<ActReaseguro> listamodel;
  private Product product;
  private Parametro parametro;
  private Socio socioSegya;
  private Long codSistema;
  private String sistema;
  private String socio;
  private String codSocio;
  private Long codProducto;
  private String producto;
  private Date fecIniReaseguro;
  private Date fecFinReaseguro;
  private float porcentaje;
  private String aux;
  private String manSistema;

  private boolean esNuevo;
  private boolean estadoEliminar;
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

      actReaseguro = new ActReaseguro();
      listaActReaseguro = new ArrayList<ConsultaActReaseguro>();
      estadoEliminar = false;
      selection = new SimpleSelection();
      esNuevo = false;
      product = new Product();
      selection = new SimpleSelection();
      socioItemsMant = new ArrayList<SelectItem>();
      productoItemsMant = new ArrayList<SelectItem>();

      /*** Tipo de Sistema ***************/
      List<Parametro> listaSistema = parametroService.buscar(COD_PARAM_TIPO_SISTEMA, TIP_PARAM_DETALLE);
      sistemaItems = new ArrayList<SelectItem>();
      sistemaItems.add(new SelectItem("", "- Seleccionar -"));

      for (Parametro p : listaSistema) {
        sistemaItems.add(new SelectItem(p.getCodValor(), p.getNomValor()));
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

  public String cambiarEstado() {
    log.info("Inicio");
    String respuesta = "";
    try {
      estadoEliminar = true;
      estadoEditar = true;
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
    log.info("Fin");
    return respuesta;
  }

  public void buscarSocio(ValueChangeEvent event) {

    log.info("Inicio");
    /*** socio Acsele ***************/
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

        } else if (event.getNewValue().toString().equals("2")) {

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

        if (sitema_temp.equalsIgnoreCase("S")) {

          productoItems.add(new SelectItem("1", "ExtraGarantia"));
          productoItemsMant.add(new SelectItem("1", "ExtraGarantia"));

        } else if (sitema_temp.equalsIgnoreCase("A")) {
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

  public String insertar() {
    log.info("Inicio");
    String respuesta = null;

    try {
      esNuevo = true;
      actReaseguro = new ActReaseguro();
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

        if (actReaseguro.getCodSistema() == 2) {
          socioSegya = socioService.obtener(Integer.parseInt(actReaseguro.getCodSocio()));
          actReaseguro.setProducto("ExtraGarantia");
          actReaseguro.setSocio(socioSegya.getDescripcion());
        } else {
          socio = parametroService.obtener(Constantes.COD_PARAM_SOCIO, Constantes.TIP_PARAM_DETALLE, actReaseguro.getCodSocio()).getNomValor();
          product = productoService.obtener(BigDecimal.valueOf(actReaseguro.getCodProducto()));
          actReaseguro.setProducto(product.getDescription());
          actReaseguro.setSocio(socio);
        }

        Date fecha = actReaseguroService.fechaMax(actReaseguro.getCodSistema(), actReaseguro.getCodSocio(), actReaseguro.getCodProducto());

        if (actReaseguro.getFecFinReaseguro().before(actReaseguro.getFecIniReaseguro())) {
          throw new SyncconException(ErrorConstants.COD_ERROR_FEC_VIG_FIN, FacesMessage.SEVERITY_INFO);
        }

        if (fecha == null) {
        } else {
          if (actReaseguro.getFecIniReaseguro().before(fecha)) {
            throw new SyncconException(ErrorConstants.COD_ERROR_RANGO_FEC, FacesMessage.SEVERITY_INFO);
          }
        }
        actReaseguro.setUsuCreacion(SecurityContextHolder.getContext().getAuthentication().getName());
        actReaseguroService.insertar(actReaseguro);

      } else {
        Date fecha = actReaseguroService.fechaMax(actReaseguro.getCodSistema(), actReaseguro.getCodSocio(), actReaseguro.getCodProducto());
        Date fechaAnt = actReaseguroService.fechaMaxAnt(actReaseguro.getCodSistema(), actReaseguro.getCodSocio(), actReaseguro.getCodProducto(), fecha);
        if (actReaseguro.getFecFinReaseguro().before(actReaseguro.getFecIniReaseguro())) {
          throw new SyncconException(ErrorConstants.COD_ERROR_FEC_VIG_FIN, FacesMessage.SEVERITY_INFO);
        }
        if (fecha == null || fechaAnt == null) {

        } else {
          if (actReaseguro.getFecIniReaseguro().before(fechaAnt)) {
            throw new SyncconException(ErrorConstants.COD_ERROR_RANGO_FEC, FacesMessage.SEVERITY_INFO);
          }
        }

        actReaseguro.setUsuModificacion(SecurityContextHolder.getContext().getAuthentication().getName());
        actReaseguroService.actualizar(actReaseguro);
      }

      listaActReaseguro = actReaseguroService.buscar(actReaseguro.getCodSistema(), actReaseguro.getCodSocio(), actReaseguro.getCodProducto());

      /*
       * Collections.sort(listaActReaseguro, new Comparator<ConsultaActReaseguro>() { public int compare(ConsultaActReaseguro co, ConsultaActReaseguro
       * co1) { return co.getFecFinReaseguro().compareTo(co1.getFecFinReaseguro()); } });
       */
      // log.info("Estado eliminar: " + estadoEliminar);

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

    actReaseguro = new ActReaseguro();
    listaActReaseguro = null;
    selection = new SimpleSelection();

    try {

      listaActReaseguro = new ArrayList<ConsultaActReaseguro>();
      estadoEliminar = false;
      setEstadoEditar(false);

      log.debug("test: " + codSistema);

      listaActReaseguro = actReaseguroService.buscar(codSistema, codSocio, codProducto);
      /*
       * Collections.sort(listaActReaseguro, new Comparator<ConsultaActReaseguro>() { public int compare(ConsultaActReaseguro co, ConsultaActReaseguro
       * co1) { return co.getFecFinReaseguro().compareTo(co1.getFecFinReaseguro()); } });
       */
      if (listaActReaseguro.isEmpty()) {
        log.info("ESTADO DE BOTONES FALSE");
        estadoEditar = false;
        estadoEliminar = false;
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

  public String actualizar() {

    log.info("Inicio");
    String respuesta = null;

    try {
      String usuario = SecurityContextHolder.getContext().getAuthentication().getName();
      actReaseguro.setUsuModificacion(usuario);

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
    ConsultaActReaseguro consultaReaseguro = null;
    Iterator<Object> iterator = getSelection().getKeys();
    Object key = new Object();

    try {

      while (iterator.hasNext()) {
        key = iterator.next();
        consultaReaseguro = listaActReaseguro.get((Integer) key);
        if (actReaseguro == null) {
          throw new SyncconException(ErrorConstants.COD_ERROR_SELECCION, FacesMessage.SEVERITY_INFO);
        }
      }

      if (consultaReaseguro == null) {
        estadoEliminar = true;
        throw new SyncconException(ErrorConstants.COD_ERROR_SELECCION, FacesMessage.SEVERITY_INFO);

      }

      actReaseguroService.eliminar(consultaReaseguro.getCodReaseguro());
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

  public String obtener() {

    log.info("Inicio");
    esNuevo = false;
    String respuesta = null;
    actReaseguro = null;

    try {
      Iterator<Object> iterator = getSelection().getKeys();
      if (iterator.hasNext()) {
        int key = (Integer) iterator.next();
        ConsultaActReaseguro cr = listaActReaseguro.get(key);
        log.debug("Key: " + key);
        Long pk = cr.getCodReaseguro();

        actReaseguro = actReaseguroService.obtener(pk);
      }

      else if (actReaseguro == null) {
        throw new SyncconException(ErrorConstants.COD_ERROR_SELECCION, FacesMessage.SEVERITY_INFO);
      }

      socioItemsMant = new ArrayList<SelectItem>();
      socioItemsMant.add(new SelectItem("", "- Seleccionar -"));
      socioItemsMant.add(new SelectItem(actReaseguro.getCodSocio(), actReaseguro.getSocio()));

      productoItemsMant = new ArrayList<SelectItem>();
      productoItemsMant.add(new SelectItem("", "- Seleccionar -"));
      productoItemsMant.add(new SelectItem(actReaseguro.getCodProducto(), actReaseguro.getProducto()));

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
    actReaseguro = new ActReaseguro();
    sistemaItems = new ArrayList<SelectItem>();
    codSocio = null;
    codSistema = null;
    listaActReaseguro = null;
    selection = new SimpleSelection();
    try {

      listaActReaseguro = new ArrayList<ConsultaActReaseguro>();
      estadoEliminar = false;
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

  public ParametroService getParametroService() {
    return parametroService;
  }

  public void setParametroService(ParametroService parametroService) {
    this.parametroService = parametroService;
  }

  public ProductService getProductoService() {
    return productoService;
  }

  public void setProductoService(ProductService productoService) {
    this.productoService = productoService;
  }

  public SocioService getSocioService() {
    return socioService;
  }

  public void setSocioService(SocioService socioService) {
    this.socioService = socioService;
  }

  public ActReaseguroService getActReaseguroService() {
    return actReaseguroService;
  }

  public void setActReaseguroService(ActReaseguroService actReaseguroService) {
    this.actReaseguroService = actReaseguroService;
  }

  public ActReaseguro getActReaseguro() {
    return actReaseguro;
  }

  public void setActReaseguro(ActReaseguro actReaseguro) {
    this.actReaseguro = actReaseguro;
  }

  public List<ConsultaActReaseguro> getListaActReaseguro() {
    return listaActReaseguro;
  }

  public void setListaActReaseguro(List<ConsultaActReaseguro> listaActReaseguro) {
    this.listaActReaseguro = listaActReaseguro;
  }

  public List<ActReaseguro> getListamodel() {
    return listamodel;
  }

  public void setListamodel(List<ActReaseguro> listamodel) {
    this.listamodel = listamodel;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public Parametro getParametro() {
    return parametro;
  }

  public void setParametro(Parametro parametro) {
    this.parametro = parametro;
  }

  public Socio getSocioSegya() {
    return socioSegya;
  }

  public void setSocioSegya(Socio socioSegya) {
    this.socioSegya = socioSegya;
  }

  public Long getCodSistema() {
    return codSistema;
  }

  public void setCodSistema(Long codSistema) {
    this.codSistema = codSistema;
  }

  public String getSistema() {
    return sistema;
  }

  public void setSistema(String sistema) {
    this.sistema = sistema;
  }

  public String getSocio() {
    return socio;
  }

  public void setSocio(String socio) {
    this.socio = socio;
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

  public String getProducto() {
    return producto;
  }

  public void setProducto(String producto) {
    this.producto = producto;
  }

  public Date getFecIniReaseguro() {
    return fecIniReaseguro;
  }

  public void setFecIniReaseguro(Date fecIniReaseguro) {
    this.fecIniReaseguro = fecIniReaseguro;
  }

  public Date getFecFinReaseguro() {
    return fecFinReaseguro;
  }

  public void setFecFinReaseguro(Date fecFinReaseguro) {
    this.fecFinReaseguro = fecFinReaseguro;
  }

  public float getPorcentaje() {
    return porcentaje;
  }

  public void setPorcentaje(float porcentaje) {
    this.porcentaje = porcentaje;
  }

  public String getAux() {
    return aux;
  }

  public void setAux(String aux) {
    this.aux = aux;
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

  public SimpleSelection getSelection() {
    return selection;
  }

  public void setSelection(SimpleSelection selection) {
    this.selection = selection;
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

  public List<SelectItem> getProductoItems() {
    return productoItems;
  }

  public void setProductoItems(List<SelectItem> productoItems) {
    this.productoItems = productoItems;
  }

  public static Logger getLog() {
    return log;
  }

  // 12/02/2014 jmedina Se agregaron set y get para la variable estadoEliminar
  public boolean isEstadoEliminar() {
    return estadoEliminar;
  }

  public void setEstadoEliminar(boolean estadoEliminar) {
    this.estadoEliminar = estadoEliminar;
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
