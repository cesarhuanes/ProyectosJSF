package com.cardif.satelite.tesoreria.controller;

import static com.cardif.satelite.constantes.ErrorConstants.ERROR_SYNCCON;
import static com.cardif.satelite.constantes.ErrorConstants.MSJ_ERROR;
import static com.cardif.satelite.constantes.ErrorConstants.MSJ_ERROR_GENERAL;

import java.io.File;
import java.io.FileInputStream;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.log4j.Logger;
import org.richfaces.model.selection.SimpleSelection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import com.cardif.framework.controller.BaseController;
import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.configuracion.service.ParametroService;
import com.cardif.satelite.constantes.Constantes;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.Parametro;
import com.cardif.satelite.model.Perfil;
import com.cardif.satelite.model.satelite.CargoCentralizado;
import com.cardif.satelite.tesoreria.service.CargoCentralizadoService;

@Controller("cargoCentralizadoController")
@Scope("request")
public class CargoCentralizadoController extends BaseController {

  public static final Logger LOGGER = Logger.getLogger(CargoCentralizadoController.class);

  @Autowired
  CargoCentralizadoService cargoCentralizadoService;

  @Autowired
  ParametroService parametroService;

  private List<CargoCentralizado> listaComprobantesAprobados;
  private List<CargoCentralizado> listaComprobantesDesaprobados;

  private SimpleSelection selection;
  private CargoCentralizado cargoCentralizado;

  private int rowSelected;

  private boolean flagEditar = false;
  private boolean flagExportar = false;
  private boolean flagSW = true;

  private List<SelectItem> itemSistemas;
  private List<SelectItem> itemtipoComprobante;
  private List<SelectItem> itemMoneda;
  private List<SelectItem> itemIndicadorDeIgv;
  private List<SelectItem> itemEstado;

  /* Filtros Busqueda Comprobantes aprovados */
  private Date filAprobFechaRegistroContableDesde;
  private Date filAprobFechaRegistroContableHasta;
  private Date filAprobFechaCargaDesde;
  private Date filAprobFechaCargaHasta;

  private String filAprobSistema;
  private String filAprobNroComprobante;
  private String filAprobRucDni;
  private Date filAprobFecEmision;
  private String filAprobRazonSocial;
  private String filAprobMoneda;
  private Date filAprobFecVencimiento;
  private String filAprobEstado;
  private Date filAprobFecPago;

  /* Filtros Busqueda Comprobantes rechazados */
  private String filRechazadoSistema;
  private String filRechazadoNroComprobante;
  private String filRechazadoRucDni;
  private Date filRechazadoFecEmision;
  private String filRechazadoRazonSocial;
  private Date filRechazadoFechaCargaDesde;
  private Date filRechazadoFechaCargaHasta;

  /* Campos utilizados para actualzar comprobantes */
  private double indicadorImporteTotal;
  private Date updateFecEntregaTesoreria;
  private Date updateFecRecepcionContable;

  private int cantidadActualizados = 0;

  HttpSession session;

  private boolean verActualizar;

  @Override
  @PostConstruct
  public String inicio() {
    LOGGER.info("Inicio");
    String respuesta = null;
    if (!tieneAcceso()) {
      LOGGER.debug("No cuenta con los accesos necesarios");
      return "accesoDenegado";
    }
    try {
      /*
       * String opcionSeleccionada = ""; session = (HttpSession) FacesContext .getCurrentInstance().getExternalContext().getSession(false); if
       * (session != null) { opcionSeleccionada = (String) session.getAttribute("opcion"); }
       */
      // setCargoCentralizado(new CargoCentralizado());

      List<Parametro> listaSistemas = parametroService.buscar(Constantes.COD_PARAM_TIPO_SISTEMA_CC, Constantes.TIP_PARAM_DETALLE);
      setItemSistemas(new ArrayList<SelectItem>());
      itemSistemas.add(new SelectItem("", "- Seleccionar -"));
      for (Parametro p : listaSistemas) {
        itemSistemas.add(new SelectItem(p.getCodValor(), p.getNomValor()));
      }

      List<Parametro> listaTipoComprobante = parametroService.buscar(Constantes.COD_PARAM_TIPOS_COMPROBANTE, Constantes.TIP_PARAM_DETALLE);
      setItemtipoComprobante(new ArrayList<SelectItem>());
      itemtipoComprobante.add(new SelectItem("", "- Seleccionar -"));
      for (Parametro p : listaTipoComprobante) {
        itemtipoComprobante.add(new SelectItem(p.getCodValor(), p.getNomValor()));
      }

      List<Parametro> listaMoneda = parametroService.buscar(Constantes.COD_PARAM_MONEDA, Constantes.TIP_PARAM_DETALLE);
      setItemMoneda(new ArrayList<SelectItem>());
      itemMoneda.add(new SelectItem("", "- Seleccionar -"));
      for (Parametro p : listaMoneda) {
        itemMoneda.add(new SelectItem(p.getCodValor(), p.getNomValor()));
      }

      List<Parametro> listaIndicadorIGV = parametroService.buscar(Constantes.COD_PARAM_INDICADOR_IGV, Constantes.TIP_PARAM_DETALLE);
      setItemIndicadorDeIgv(new ArrayList<SelectItem>());
      itemIndicadorDeIgv.add(new SelectItem("", "- Seleccionar -"));
      for (Parametro p : listaIndicadorIGV) {
        itemIndicadorDeIgv.add(new SelectItem(p.getCodValor(), p.getNomValor()));
      }

      List<Parametro> listaEstadoComprobante = parametroService.buscar(Constantes.COD_PARAM_E_COMPROBANTE, Constantes.TIP_PARAM_DETALLE);
      setItemEstado(new ArrayList<SelectItem>());
      itemEstado.add(new SelectItem("", "- Seleccionar -"));
      for (Parametro p : listaEstadoComprobante) {
        if (!p.getCodValor().equals("REV") && !p.getCodValor().equals("REC")) {
          itemEstado.add(new SelectItem(p.getCodValor(), p.getNomValor()));
        }
      }

      mostrarActualizarComprobantes();

    } catch (SyncconException ex) {
      log.error(ERROR_SYNCCON + ex.getMessageComplete());
      FacesMessage facesMsg = new FacesMessage(ex.getSeveridad(), ex.getMessage(), null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    } catch (Exception e) {
      LOGGER.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
      respuesta = MSJ_ERROR;
    }
    LOGGER.info("Final");
    return respuesta;
  }

  private void mostrarActualizarComprobantes() {
    Object objPerfil = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("perfil");
    if (objPerfil != null && objPerfil instanceof Perfil) {
      Perfil perfil = (Perfil) objPerfil;
      String authority = perfil.getAuthority();
      if (authority != null) {

        if (authority.equalsIgnoreCase("AMER_ROLE_CONT_FINAN_JEFE") || authority.equalsIgnoreCase("AMER_ROLE_CONT_OPERA_JEFE") || authority.equalsIgnoreCase("AMER_ROLE_CONT_OPERA")
            || authority.equalsIgnoreCase("AMER_ROLE_CONT_FINAN") || authority.equalsIgnoreCase("AMER_ROLE_ADMIN")) {
          setVerActualizar(true);
          LOGGER.info("VerActualizar" + isVerActualizar());
        }
      }
    }
  }

  public String obtenerComprobantesAprobados() {
    LOGGER.info("[ Inicio ]");
    String respuesta = "";
    try {
      cantidadActualizados = 0;
      if (filAprobFechaRegistroContableHasta != null && filAprobFechaRegistroContableDesde != null) {
        if (filAprobFechaRegistroContableHasta.before(filAprobFechaRegistroContableDesde)) {
          throw new SyncconException(ErrorConstants.COD_ERROR_RANGO_FECHAS, FacesMessage.SEVERITY_INFO);
        }
      }

      if (filAprobFechaCargaHasta != null && filAprobFechaCargaDesde != null) {
        if (filAprobFechaCargaHasta.before(filAprobFechaCargaDesde)) {
          throw new SyncconException(ErrorConstants.COD_ERROR_RANGO_FECHAS, FacesMessage.SEVERITY_INFO);
        }
      }

      listaComprobantesAprobados = new ArrayList<CargoCentralizado>();
      listaComprobantesAprobados = cargoCentralizadoService.obtenerComprobantesAprobados(filAprobFechaRegistroContableDesde, filAprobFechaRegistroContableHasta, filAprobSistema,
          filAprobNroComprobante, filAprobRucDni, filAprobFecEmision, filAprobRazonSocial, filAprobMoneda, filAprobFecVencimiento, filAprobFechaCargaDesde, filAprobFechaCargaHasta, filAprobFecPago,
          filAprobEstado);
      if (listaComprobantesAprobados.size() == 0) {
        respuesta = null;
        throw new SyncconException(ErrorConstants.COD_ERROR_LISTA_VACIA, FacesMessage.SEVERITY_INFO);
      } else {

        setIndicadorImporteTotal(0.0);

        for (CargoCentralizado cc : listaComprobantesAprobados) {

          if (cc.getFecModificacion() != null) {
            cantidadActualizados += 1;
          } else if (cc.getFecModificacion() == null && listaComprobantesAprobados.size() > 0) {
            this.cantidadActualizados += 1;
          }

          indicadorImporteTotal += cc.getImporteTotal().setScale(2, RoundingMode.CEILING).doubleValue();

        }
        log.info("cantidad actualizados:" + cantidadActualizados);//
        if (flagSW) {
          flagEditar = true;
        }

      }

      respuesta = null;
    } catch (SyncconException ex) {
      log.error(ERROR_SYNCCON + ex.getMessageComplete());
      FacesMessage facesMsg = new FacesMessage(ex.getSeveridad(), ex.getMessage(), null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    } catch (Exception e) {
      LOGGER.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
      respuesta = MSJ_ERROR;
    }
    LOGGER.info("[ Fin ]");
    return respuesta;

  }

  public String obtenerComprobantesRechazados() {
    LOGGER.info("[ Inicio ]");
    String respuesta = "";

    try {

      if (filRechazadoFechaCargaHasta != null && filRechazadoFechaCargaDesde != null) {
        if (filRechazadoFechaCargaHasta.before(filRechazadoFechaCargaDesde)) {
          throw new SyncconException(ErrorConstants.COD_ERROR_RANGO_FECHAS, FacesMessage.SEVERITY_INFO);
        }
      }
      listaComprobantesDesaprobados = new ArrayList<CargoCentralizado>();
      listaComprobantesDesaprobados = cargoCentralizadoService.obtenerComprobantesRechazados(filRechazadoSistema, filRechazadoNroComprobante, filRechazadoRucDni, filRechazadoFecEmision,
          filRechazadoRazonSocial, filRechazadoFechaCargaDesde, filRechazadoFechaCargaHasta);
      if (listaComprobantesDesaprobados.size() == 0) {
        throw new SyncconException(ErrorConstants.COD_ERROR_LISTA_VACIA, FacesMessage.SEVERITY_INFO);
      }
      respuesta = null;
    } catch (SyncconException ex) {
      log.error(ERROR_SYNCCON + ex.getMessageComplete());
      FacesMessage facesMsg = new FacesMessage(ex.getSeveridad(), ex.getMessage(), null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    } catch (Exception e) {
      LOGGER.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
      respuesta = MSJ_ERROR;
    }
    LOGGER.info("[ Fin ]");
    return respuesta;

  }

  public String actualizar() {
    LOGGER.info("[ Inicio ]");
    String respuesta = null;
    String usuario = null;
    Date fecActual = null;
    try {

      usuario = SecurityContextHolder.getContext().getAuthentication().getName();
      fecActual = new Date(System.currentTimeMillis());

      // if (updateFecEntregaTesoreria != null) {
      // cargoCentralizadoService.updateCombrobantesFechaEntregaTesoreria(listaComprobantesAprobados,
      // updateFecEntregaTesoreria, fecActual, usuario);
      // }
      //
      // if (updateFecRecepcionContable != null) {
      // cargoCentralizadoService.updateCombrobantesFechaRecepcionContable(listaComprobantesAprobados,
      // updateFecRecepcionContable, fecActual, usuario);
      // }

      LOGGER.info("updateFecEntregaTesoreria: " + updateFecEntregaTesoreria + ", updateFecRecepcionContable: " + updateFecRecepcionContable);
      if (updateFecRecepcionContable != null && updateFecEntregaTesoreria != null) {
        // SE AGREGO VALIDACION DE FECHAS OBSERVACIONES ANITA SOTO
        if (updateFecEntregaTesoreria.before(updateFecRecepcionContable)) {
          throw new SyncconException(ErrorConstants.COD_ERROR_RANGO_FECHAS_CONTABLES, FacesMessage.SEVERITY_INFO);
        }
        cargoCentralizadoService.updateCombrobantesAprobados(listaComprobantesAprobados, updateFecEntregaTesoreria, updateFecRecepcionContable, fecActual, usuario);
      } else {
        if (updateFecEntregaTesoreria != null) {
          cargoCentralizadoService.updateCombrobantesFechaEntregaTesoreria(listaComprobantesAprobados, updateFecEntregaTesoreria, fecActual, usuario);
        } else if (updateFecRecepcionContable != null) {
          cargoCentralizadoService.updateCombrobantesFechaRecepcionContable(listaComprobantesAprobados, updateFecRecepcionContable, fecActual, usuario);
        } else {
          cargoCentralizadoService.updateCombrobantesFechaNull(listaComprobantesAprobados, fecActual, usuario);
        }
      }

      updateFecRecepcionContable = null;
      updateFecEntregaTesoreria = null;

      respuesta = null;
    } catch (SyncconException ex) {
      log.error(ERROR_SYNCCON + ex.getMessageComplete());
      FacesMessage facesMsg = new FacesMessage(ex.getSeveridad(), ex.getMessage(), null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    } catch (Exception e) {
      LOGGER.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
      respuesta = MSJ_ERROR;
    }

    LOGGER.info("[ Fin ]");
    return respuesta;
  }

  public void refrescar() {
    LOGGER.info("[ Inicio ]");
    try {
      // se habilita el boton actualizar
      flagEditar = true;
      flagExportar = true;
      flagSW = false;
      obtenerComprobantesAprobados();
    } catch (Exception e) {
      LOGGER.error("[ Error " + e.getMessage() + " ]");
    }
    LOGGER.info("[ Fin ]");
  }

  public String exportar() {
    LOGGER.info("Inicio");
    // String respuesta = null;
    Map<String, Object> beans = new HashMap<String, Object>();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
    String FechaActual = sdf.format(new Date(System.currentTimeMillis()));

    try {
      String rutaTemp = System.getProperty("java.io.tmpdir") + "CargoCentralizado_" + System.currentTimeMillis() + ".xlsx";
      LOGGER.debug("Ruta Archivo: " + rutaTemp);

      FacesContext fc = FacesContext.getCurrentInstance();
      ServletContext sc = (ServletContext) fc.getExternalContext().getContext();
      String rutaTemplate = sc.getRealPath(File.separator + "excel" + File.separator + "template_exportar_cargo_centralizado.xlsx");

      beans.put("exportar", listaComprobantesAprobados);

      XLSTransformer transformer = new XLSTransformer();
      transformer.transformXLS(rutaTemplate, beans, rutaTemp);

      File archivoResp = new File(rutaTemp);
      FileInputStream in = new FileInputStream(archivoResp);

      HttpServletResponse response = (HttpServletResponse) contexto.getResponse();

      byte[] loader = new byte[(int) archivoResp.length()];

      response.addHeader("Content-Disposition", "attachment;filename=" + "CargoCentralizado_" + FechaActual + ".xlsx");
      response.setContentType("application/vnd.ms-excel");

      ServletOutputStream out = response.getOutputStream();

      while ((in.read(loader)) > 0) {
        out.write(loader, 0, loader.length);
      }
      in.close();
      out.close();

      FacesContext.getCurrentInstance().responseComplete();
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }
    log.info("Fin");
    return null;
  }

  public String obtenerCargo() {
    LOGGER.info("Inicio");
    String respuesta = null;
    try {
      Iterator<Object> iterator = getSelection().getKeys();
      if (iterator.hasNext()) {
        cargoCentralizado = cargoCentralizadoService.obtenerPorPK(listaComprobantesAprobados.get((Integer) iterator.next()).getPk());
      }
    } catch (SyncconException ex) {
      log.error(ERROR_SYNCCON + ex.getMessageComplete());
      FacesMessage facesMsg = new FacesMessage(ex.getSeveridad(), ex.getMessage(), null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    } catch (Exception e) {
      LOGGER.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
      respuesta = MSJ_ERROR;
    }
    LOGGER.info("Fin");
    return respuesta;
  }

  public void limpiar() {
    LOGGER.info("[ Inicio  ]");
    try {

      cargoCentralizado = new CargoCentralizado();
      listaComprobantesAprobados = null;
      listaComprobantesDesaprobados = null;
      flagEditar = false;
      flagExportar = false;
      flagSW = true;
      cantidadActualizados = 0;

      setFilAprobFechaRegistroContableDesde(null);
      setFilAprobFechaRegistroContableHasta(null);
      setFilAprobSistema(null);
      setFilAprobNroComprobante(null);
      setFilAprobRucDni(null);
      setFilAprobFecEmision(null);
      setFilAprobRazonSocial(null);
      setFilAprobMoneda(null);
      setFilAprobFecVencimiento(null);
      setFilAprobFechaCargaDesde(null);
      setFilAprobFechaCargaHasta(null);
      setFilAprobFecPago(null);
      setFilAprobEstado(null);

      setFilRechazadoSistema(null);
      setFilRechazadoNroComprobante(null);
      setFilRechazadoRucDni(null);
      setFilRechazadoFecEmision(null);
      setFilRechazadoRazonSocial(null);
      setFilRechazadoFechaCargaDesde(null);
      setFilRechazadoFechaCargaHasta(null);

      setIndicadorImporteTotal(0.0);
      setUpdateFecEntregaTesoreria(null);
      setUpdateFecRecepcionContable(null);

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
    LOGGER.info("[ Fin ]");
  }

  public SimpleSelection getSelection() {
    return selection;
  }

  public void setSelection(SimpleSelection selection) {
    this.selection = selection;
  }

  public CargoCentralizado getCargoCentralizado() {
    return cargoCentralizado;
  }

  public void setCargoCentralizado(CargoCentralizado cargoCentralizado) {
    this.cargoCentralizado = cargoCentralizado;
  }

  public int getRowSelected() {
    return rowSelected;
  }

  public void setRowSelected(int rowSelected) {
    this.rowSelected = rowSelected;
  }

  public boolean isFlagEditar() {
    return flagEditar;
  }

  public void setFlagEditar(boolean flagEditar) {
    this.flagEditar = flagEditar;
  }

  public List<SelectItem> getItemtipoComprobante() {
    return itemtipoComprobante;
  }

  public void setItemtipoComprobante(List<SelectItem> itemtipoComprobante) {
    this.itemtipoComprobante = itemtipoComprobante;
  }

  public List<SelectItem> getItemMoneda() {
    return itemMoneda;
  }

  public void setItemMoneda(List<SelectItem> itemMoneda) {
    this.itemMoneda = itemMoneda;
  }

  public List<SelectItem> getItemIndicadorDeIgv() {
    return itemIndicadorDeIgv;
  }

  public void setItemIndicadorDeIgv(List<SelectItem> itemIndicadorDeIgv) {
    this.itemIndicadorDeIgv = itemIndicadorDeIgv;
  }

  public List<SelectItem> getItemSistemas() {
    return itemSistemas;
  }

  public void setItemSistemas(List<SelectItem> itemSistemas) {
    this.itemSistemas = itemSistemas;
  }

  public String getFilAprobSistema() {
    return filAprobSistema;
  }

  public void setFilAprobSistema(String filAprobSistema) {
    this.filAprobSistema = filAprobSistema;
  }

  public String getFilAprobNroComprobante() {
    return filAprobNroComprobante;
  }

  public void setFilAprobNroComprobante(String filAprobNroComprobante) {
    this.filAprobNroComprobante = filAprobNroComprobante;
  }

  public String getFilAprobRucDni() {
    return filAprobRucDni;
  }

  public void setFilAprobRucDni(String filAprobRucDni) {
    this.filAprobRucDni = filAprobRucDni;
  }

  public Date getFilAprobFecEmision() {
    return filAprobFecEmision;
  }

  public void setFilAprobFecEmision(Date filAprobFecEmision) {
    this.filAprobFecEmision = filAprobFecEmision;
  }

  public String getFilAprobRazonSocial() {
    return filAprobRazonSocial;
  }

  public void setFilAprobRazonSocial(String filAprobRazonSocial) {
    this.filAprobRazonSocial = filAprobRazonSocial;
  }

  public String getFilAprobMoneda() {
    return filAprobMoneda;
  }

  public void setFilAprobMoneda(String filAprobMoneda) {
    this.filAprobMoneda = filAprobMoneda;
  }

  public Date getFilAprobFecVencimiento() {
    return filAprobFecVencimiento;
  }

  public void setFilAprobFecVencimiento(Date filAprobFecVencimiento) {
    this.filAprobFecVencimiento = filAprobFecVencimiento;
  }

  public String getFilAprobEstado() {
    return filAprobEstado;
  }

  public void setFilAprobEstado(String filAprobEstado) {
    this.filAprobEstado = filAprobEstado;
  }

  public Date getFilAprobFecPago() {
    return filAprobFecPago;
  }

  public void setFilAprobFecPago(Date filAprobFecPago) {
    this.filAprobFecPago = filAprobFecPago;
  }

  public String getFilRechazadoSistema() {
    return filRechazadoSistema;
  }

  public void setFilRechazadoSistema(String filRechazadoSistema) {
    this.filRechazadoSistema = filRechazadoSistema;
  }

  public String getFilRechazadoNroComprobante() {
    return filRechazadoNroComprobante;
  }

  public void setFilRechazadoNroComprobante(String filRechazadoNroComprobante) {
    this.filRechazadoNroComprobante = filRechazadoNroComprobante;
  }

  public String getFilRechazadoRucDni() {
    return filRechazadoRucDni;
  }

  public void setFilRechazadoRucDni(String filRechazadoRucDni) {
    this.filRechazadoRucDni = filRechazadoRucDni;
  }

  public Date getFilRechazadoFecEmision() {
    return filRechazadoFecEmision;
  }

  public void setFilRechazadoFecEmision(Date filRechazadoFecEmision) {
    this.filRechazadoFecEmision = filRechazadoFecEmision;
  }

  public String getFilRechazadoRazonSocial() {
    return filRechazadoRazonSocial;
  }

  public void setFilRechazadoRazonSocial(String filRechazadoRazonSocial) {
    this.filRechazadoRazonSocial = filRechazadoRazonSocial;
  }

  public Date getFilAprobFechaRegistroContableDesde() {
    return filAprobFechaRegistroContableDesde;
  }

  public void setFilAprobFechaRegistroContableDesde(Date filAprobFechaRegistroContableDesde) {
    this.filAprobFechaRegistroContableDesde = filAprobFechaRegistroContableDesde;
  }

  public Date getFilAprobFechaRegistroContableHasta() {
    return filAprobFechaRegistroContableHasta;
  }

  public void setFilAprobFechaRegistroContableHasta(Date filAprobFechaRegistroContableHasta) {
    this.filAprobFechaRegistroContableHasta = filAprobFechaRegistroContableHasta;
  }

  public List<SelectItem> getItemEstado() {
    return itemEstado;
  }

  public void setItemEstado(List<SelectItem> itemEstado) {
    this.itemEstado = itemEstado;
  }

  public List<CargoCentralizado> getListaComprobantesAprobados() {
    return listaComprobantesAprobados;
  }

  public void setListaComprobantesAprobados(List<CargoCentralizado> listaComprobantesAprobados) {
    this.listaComprobantesAprobados = listaComprobantesAprobados;
  }

  public List<CargoCentralizado> getListaComprobantesDesaprobados() {
    return listaComprobantesDesaprobados;
  }

  public void setListaComprobantesDesaprobados(List<CargoCentralizado> listaComprobantesDesaprobados) {
    this.listaComprobantesDesaprobados = listaComprobantesDesaprobados;
  }

  public double getIndicadorImporteTotal() {
    return indicadorImporteTotal;
  }

  public void setIndicadorImporteTotal(double indicadorImporteTotal) {
    this.indicadorImporteTotal = indicadorImporteTotal;
  }

  public Date getUpdateFecEntregaTesoreria() {
    return updateFecEntregaTesoreria;
  }

  public void setUpdateFecEntregaTesoreria(Date updateFecEntregaTesoreria) {
    this.updateFecEntregaTesoreria = updateFecEntregaTesoreria;
  }

  public Date getUpdateFecRecepcionContable() {
    return updateFecRecepcionContable;
  }

  public void setUpdateFecRecepcionContable(Date updateFecRecepcionContable) {
    this.updateFecRecepcionContable = updateFecRecepcionContable;
  }

  public boolean isFlagExportar() {
    return flagExportar;
  }

  public void setFlagExportar(boolean flagExportar) {
    this.flagExportar = flagExportar;
  }

  public int getCantidadActualizados() {
    return cantidadActualizados;
  }

  public void setCantidadActualizados(int cantidadActualizados) {
    this.cantidadActualizados = cantidadActualizados;
  }

  public Date getFilAprobFechaCargaDesde() {
    return filAprobFechaCargaDesde;
  }

  public void setFilAprobFechaCargaDesde(Date filAprobFechaCargaDesde) {
    this.filAprobFechaCargaDesde = filAprobFechaCargaDesde;
  }

  public Date getFilAprobFechaCargaHasta() {
    return filAprobFechaCargaHasta;
  }

  public void setFilAprobFechaCargaHasta(Date filAprobFechaCargaHasta) {
    this.filAprobFechaCargaHasta = filAprobFechaCargaHasta;
  }

  public boolean isVerActualizar() {
    return verActualizar;
  }

  public void setVerActualizar(boolean verActualizar) {
    this.verActualizar = verActualizar;
  }

  public Date getFilRechazadoFechaCargaHasta() {
    return filRechazadoFechaCargaHasta;
  }

  public void setFilRechazadoFechaCargaHasta(Date filRechazadoFechaCargaHasta) {
    this.filRechazadoFechaCargaHasta = filRechazadoFechaCargaHasta;
  }

  public Date getFilRechazadoFechaCargaDesde() {
    return filRechazadoFechaCargaDesde;
  }

  public void setFilRechazadoFechaCargaDesde(Date filRechazadoFechaCargaDesde) {
    this.filRechazadoFechaCargaDesde = filRechazadoFechaCargaDesde;
  }

}
