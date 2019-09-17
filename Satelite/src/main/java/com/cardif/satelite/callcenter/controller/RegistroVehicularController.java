package com.cardif.satelite.callcenter.controller;

import static com.cardif.satelite.constantes.ErrorConstants.ERROR_SYNCCON;
import static com.cardif.satelite.constantes.ErrorConstants.MSJ_ERROR;
import static com.cardif.satelite.constantes.ErrorConstants.MSJ_ERROR_GENERAL;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.cardif.satelite.callcenter.bean.ConsultaRegPropietario;
import com.cardif.satelite.callcenter.bean.ConsultaRegVehicular;
import com.cardif.satelite.callcenter.service.AuditRegistroVehicularService;
import com.cardif.satelite.configuracion.service.ParametroService;
import com.cardif.satelite.constantes.Constantes;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.AuditRegistroVehicular;
import com.cardif.satelite.model.Parametro;
import com.cardif.satelite.soat.cencosud.service.DireccionPropietarioVehiculoServiceCencosud;
import com.cardif.satelite.soat.cencosud.service.PropietarioServiceCencosud;
import com.cardif.satelite.soat.cencosud.service.VehiculoPeServiceCencosud;
import com.cardif.satelite.soat.comparabien.service.DireccionPropietarioVehiculoServiceComparaBien;
import com.cardif.satelite.soat.comparabien.service.PropietarioServiceComparaBien;
import com.cardif.satelite.soat.comparabien.service.VehiculoPeServiceComparaBien;
import com.cardif.satelite.soat.directo.service.CategoriaClaseServiceDirecto;
import com.cardif.satelite.soat.directo.service.DepartamentoServiceDirecto;
import com.cardif.satelite.soat.directo.service.DireccionPropietarioVehiculoServiceDirecto;
import com.cardif.satelite.soat.directo.service.MarcaVehiculoServiceDirecto;
import com.cardif.satelite.soat.directo.service.ModeloVehiculoServiceDirecto;
import com.cardif.satelite.soat.directo.service.PropietarioServiceDirecto;
import com.cardif.satelite.soat.directo.service.UsoVehiculoServiceDirecto;
import com.cardif.satelite.soat.directo.service.VehiculoPeServiceDirecto;
import com.cardif.satelite.soat.falabella.service.DireccionPropietarioVehiculoService;
import com.cardif.satelite.soat.falabella.service.PropietarioService;
import com.cardif.satelite.soat.falabella.service.VehiculoPeService;
import com.cardif.satelite.soat.model.BitacoraSoat;
import com.cardif.satelite.soat.model.CategoriaClase;
import com.cardif.satelite.soat.model.Departamento;
import com.cardif.satelite.soat.model.DireccionPropietarioVehiculo;
import com.cardif.satelite.soat.model.MarcaVehiculo;
import com.cardif.satelite.soat.model.ModeloVehiculo;
import com.cardif.satelite.soat.model.Propietario;
import com.cardif.satelite.soat.model.UsoVehiculo;
import com.cardif.satelite.soat.model.VehiculoPe;
import com.cardif.satelite.soat.ripley.service.DireccionPropietarioVehiculoServiceRipley;
import com.cardif.satelite.soat.ripley.service.PropietarioServiceRipley;
import com.cardif.satelite.soat.ripley.service.VehiculoPeServiceRipley;
import com.cardif.satelite.soat.rpp.service.DireccionPropietarioVehiculoServiceRpp;
import com.cardif.satelite.soat.rpp.service.PropietarioServiceRpp;
import com.cardif.satelite.soat.rpp.service.VehiculoPeServiceRpp;
import com.cardif.satelite.soat.service.BitacoraSoatService;
import com.cardif.satelite.soat.sucursal.service.DireccionPropietarioVehiculoServiceSucursal;
import com.cardif.satelite.soat.sucursal.service.PropietarioServiceSucursal;
import com.cardif.satelite.soat.sucursal.service.VehiculoPeServiceSucursal;
import com.sun.org.apache.commons.beanutils.BeanUtils;

@Controller("registroVehicularController")
@Scope("request")
public class RegistroVehicularController extends BaseController {

  public static final Logger log = Logger.getLogger(RegistroVehicularController.class);

  // Combos
  @Autowired
  private MarcaVehiculoServiceDirecto marcaVehiculoServiceDirecto;
  @Autowired
  private ModeloVehiculoServiceDirecto modeloVehiculoServiceDirecto;
  @Autowired
  private DepartamentoServiceDirecto departamentoServiceDirecto;
  @Autowired
  private CategoriaClaseServiceDirecto categoriaClaseServiceDirecto;
  @Autowired
  private UsoVehiculoServiceDirecto usoVehiculoServiceDirecto;

  // Vehiculo
  @Autowired
  private VehiculoPeServiceDirecto vehiculoPeServiceDirecto;
  @Autowired
  private VehiculoPeService vehiculoPeServiceFalabella;
  @Autowired
  private VehiculoPeServiceRpp vehiculoPeServiceRpp;
  @Autowired
  private VehiculoPeServiceRipley vehiculoPeServiceRipley;
  @Autowired
  private VehiculoPeServiceCencosud vehiculoPeServiceCencosud;
  @Autowired
  private VehiculoPeServiceComparaBien vehiculoPeServiceComparaBien;
  @Autowired
  private VehiculoPeServiceSucursal vehiculoPeServiceSucursal;

  // Propietario
  @Autowired
  private PropietarioServiceDirecto propietarioServiceDirecto;
  @Autowired
  private PropietarioService propietarioService;
  @Autowired
  private PropietarioServiceCencosud propietarioServiceCencosud;
  @Autowired
  private PropietarioServiceComparaBien propietarioServiceComparaBien;
  @Autowired
  private PropietarioServiceRipley propietarioServiceRipley;
  @Autowired
  private PropietarioServiceRpp propietarioServiceRpp;
  @Autowired
  private PropietarioServiceSucursal propietarioServiceSucursal;

  // Direccion
  @Autowired
  private DireccionPropietarioVehiculoServiceDirecto direccionPropietarioVehiculoServiceDirecto;
  @Autowired
  private DireccionPropietarioVehiculoService direccionPropietarioVehiculoService;
  @Autowired
  private DireccionPropietarioVehiculoServiceCencosud direccionPropietarioVehiculoServiceCencosud;
  @Autowired
  private DireccionPropietarioVehiculoServiceComparaBien direccionPropietarioVehiculoServiceComparaBien;
  @Autowired
  private DireccionPropietarioVehiculoServiceRipley direccionPropietarioVehiculoServiceRipley;
  @Autowired
  private DireccionPropietarioVehiculoServiceRpp direccionPropietarioVehiculoServiceRpp;
  @Autowired
  private DireccionPropietarioVehiculoServiceSucursal direccionPropietarioVehiculoServiceSucursal;

  // Bitacora
  @Autowired
  private AuditRegistroVehicularService auditRegistroVehicularService;

  @Autowired
  private BitacoraSoatService bitacoraSoatService;

  // Parametros Socios
  @Autowired
  private ParametroService parametroService;

  private VehiculoPe vehiculoPe;
  private VehiculoPe vehiculoPeAntiguo;

  private Propietario propietario;

  private DireccionPropietarioVehiculo direccionPropietarioVehiculo;
  private DireccionPropietarioVehiculo direccionPropietarioVehiculoAntiguo;

  private AuditRegistroVehicular auditRegistroVehicular;

  private List<ConsultaRegVehicular> listaConsultaRegVehicular;
  private List<ConsultaRegPropietario> listaConsultaRegPropietario;

  private List<SelectItem> departamentoItems;
  private List<SelectItem> marcaItems;
  private List<SelectItem> usoVehiculoItems;
  private List<SelectItem> categoriaItems;
  private List<SelectItem> modeloItems;
  private List<SelectItem> asientoItems;
  private List<SelectItem> anioItems;
  private List<SelectItem> socioItems;

  private SimpleSelection selection;

  private String socio;

  private boolean flagPropietario;
  private boolean flagVehiculo;
  private boolean estBtnEditar;
  private boolean existe = false;
  private boolean disabledPropietario = false;

  private String crud = "";

  int tipoLimpiar = 0;
  String DNITemp = "";
  private long anio;
  private String errorPlaca;

  @Override
  @PostConstruct
  public String inicio() {

    String respuesta = null;
    if (!tieneAcceso()) {
      log.debug("No cuenta con los accesos necesarios");
      return "accesoDenegado";
    }

    try {

      List<Parametro> socios = parametroService.buscar(Constantes.COD_PARAM_SOCIOS_PLACAS, Constantes.TIP_PARAM_DETALLE);
      socioItems = new ArrayList<SelectItem>();
      socioItems.add(new SelectItem("", "- Seleccionar -"));
      for (Parametro socio : socios) {
        socioItems.add(new SelectItem(socio.getCodValor(), socio.getNomValor()));
      }

      // INICIO LIMPIAR
      limpiar();

      listaConsultaRegVehicular = new ArrayList<ConsultaRegVehicular>();
      listaConsultaRegPropietario = new ArrayList<ConsultaRegPropietario>();
      flagVehiculo = false;
      estBtnEditar = false;
      // FIN LIMPIAR

      cargarCombos();

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

    return respuesta;
  }

  private void limpiar() {
    setVehiculoPe(new VehiculoPe());
    setPropietario(new Propietario());
    setDireccionPropietarioVehiculo(new DireccionPropietarioVehiculo());
    selection = new SimpleSelection();
  }

  private void cargarCombos() throws SyncconException {
    // MARCAS
    marcaItems = new ArrayList<SelectItem>();
    marcaItems.add(new SelectItem("", "- Seleccionar -"));
    List<MarcaVehiculo> listaMarca = marcaVehiculoServiceDirecto.buscar();
    for (MarcaVehiculo marca : listaMarca) {
      marcaItems.add(new SelectItem(marca.getId(), marca.getNombreMarcavehiculo()));
    }

    // USO VEHICULO
    usoVehiculoItems = new ArrayList<SelectItem>();
    usoVehiculoItems.add(new SelectItem("", "- Seleccionar -"));
    List<UsoVehiculo> listaUsoVehiculo = usoVehiculoServiceDirecto.getListUsoVehiculo();
    for (UsoVehiculo uv : listaUsoVehiculo) {
      usoVehiculoItems.add(new SelectItem(uv.getCodUso(), uv.getDescripcionUso()));
    }

    // CATEGORIAS
    categoriaItems = new ArrayList<SelectItem>();
    categoriaItems.add(new SelectItem("", "- Seleccionar -"));
    List<CategoriaClase> listaCategoria = categoriaClaseServiceDirecto.buscar();
    for (CategoriaClase c : listaCategoria) {
      categoriaItems.add(new SelectItem(c.getCodCategoriaClase(), c.getDescripcionCategoriaClase()));
    }

    // ASIENTOS
    asientoItems = new ArrayList<SelectItem>();
    asientoItems.add(new SelectItem("", "- Seleccionar -"));
    for (int i = 2; i <= 16; i++) {
      asientoItems.add(new SelectItem(Long.valueOf(i), String.valueOf(i)));
    }

    // DEPARTAMENTOS
    departamentoItems = new ArrayList<SelectItem>();
    departamentoItems.add(new SelectItem(null, "-Seleccione-"));
    List<Departamento> listaDepartamento = departamentoServiceDirecto.buscar();
    for (Departamento d : listaDepartamento) {
      departamentoItems.add(new SelectItem(new BigDecimal(d.getCodDepartamento().trim()), d.getNombreDepartamento()));
    }

    // ANIOS
    anioItems = new ArrayList<SelectItem>();
    anioItems.add(new SelectItem("", "- Seleccionar -"));
    long year = Calendar.getInstance().get(Calendar.YEAR);
    long aux = year - 30;
    for (long i = year; i >= aux; i--) {
      anioItems.add(new SelectItem(Long.valueOf(i), String.valueOf(i)));
    }

    modeloItems = new ArrayList<SelectItem>();
    modeloItems.add(new SelectItem("", "- Seleccionar -"));
  }

  private void cargarModelos(BigDecimal marcaId) {
    // MODELOS
    modeloItems = new ArrayList<SelectItem>();
    modeloItems.add(new SelectItem("", "- Seleccionar -"));
    List<ModeloVehiculo> lista;
    try {
      lista = modeloVehiculoServiceDirecto.buscarModelo(marcaId);
      for (ModeloVehiculo modelo : lista) {
        modeloItems.add(new SelectItem(modelo.getId(), modelo.getNombreModelovehiculo()));
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

  }

  public String cambiarEstado() {
    log.info("Inicio");
    String respuesta = null;
    try {
      estBtnEditar = true;
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
    log.info("Fin");
    return respuesta;
  }

  public String buscarPlaca() {
    log.info("Inicio");
    String result = null;

    try {
      log.info("SOCIO: " + this.socio + "\tPLACA: " + vehiculoPe.getPlaca());

      if (socio.equals(Constantes.COD_SOCIO_SOAT_DIRECTO)) {
        if (log.isDebugEnabled()) {
          log.debug("BUSCAR vehiculo en SOAT DIRECTO.");
        }
        listaConsultaRegVehicular = vehiculoPeServiceDirecto.buscarPlaca(vehiculoPe.getPlaca());
      } else if (socio.equals(Constantes.COD_SOCIO_SOAT_FALABELLA)) {
        if (log.isDebugEnabled()) {
          log.debug("BUSCAR vehiculo en SOAT FALABELLA.");
        }
        listaConsultaRegVehicular = vehiculoPeServiceFalabella.buscarPlaca(vehiculoPe.getPlaca());
      } else if (socio.equals(Constantes.COD_SOCIO_SOAT_CENCOSUD)) {
        if (log.isDebugEnabled()) {
          log.debug("BUSCAR vehiculo en SOAT CENCOSUD.");
        }
        listaConsultaRegVehicular = vehiculoPeServiceCencosud.buscarPlaca(vehiculoPe.getPlaca());
      } else if (socio.equals(Constantes.COD_SOCIO_SOAT_COMPARA_BIEN)) {
        if (log.isDebugEnabled()) {
          log.debug("BUSCAR vehiculo en SOAT COMPARA BIEN.");
        }
        listaConsultaRegVehicular = vehiculoPeServiceComparaBien.buscarPlaca(vehiculoPe.getPlaca());
      } else if (socio.equals(Constantes.COD_SOCIO_SOAT_RIPLEY)) {
        if (log.isDebugEnabled()) {
          log.debug("BUSCAR vehiculo en SOAT RIPLEY.");
        }
        listaConsultaRegVehicular = vehiculoPeServiceRipley.buscarPlaca(vehiculoPe.getPlaca());
      } else if (socio.equals(Constantes.COD_SOCIO_SOAT_RPP)) {
        if (log.isDebugEnabled()) {
          log.debug("BUSCAR vehiculo en SOAT RPP.");
        }
        listaConsultaRegVehicular = vehiculoPeServiceRpp.buscarPlaca(vehiculoPe.getPlaca());
      } else if (socio.equalsIgnoreCase(Constantes.COD_SOCIO_SOAT_SUCURSAL)) {
        if (log.isDebugEnabled()) {
          log.debug("BUSCAR vehiculo en SOAT SUCURSAL.");
        }
        listaConsultaRegVehicular = vehiculoPeServiceSucursal.buscarPlaca(vehiculoPe.getPlaca());
      } else {
        listaConsultaRegVehicular.clear();
      }

      selection = new SimpleSelection();
      estBtnEditar = false;

      if (listaConsultaRegVehicular.isEmpty()) {
        // INICIO EP - Se añade esto linea para habilitar el boton registrar
        flagVehiculo = true;
        // FIN EP
        if (socio.equals(Constantes.COD_SOCIO_SOAT_DIRECTO)) {
          flagVehiculo = true;
        }
        throw new SyncconException(ErrorConstants.COD_ERROR_CONSUL_PLACA, FacesMessage.SEVERITY_INFO);
      } else {
        flagVehiculo = false; // indica que el vehiculo ya existe
      }

      log.info("placa: " + listaConsultaRegVehicular.get(0).getPlaca());
    } catch (SyncconException ex) {
      log.error(ERROR_SYNCCON + ex.getMessageComplete());
      FacesMessage facesMsg = new FacesMessage(ex.getSeveridad(), ex.getMessage(), null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
      result = MSJ_ERROR;
    }
    log.info("Fin");
    return result;
  }

  /**
   * @tipo MODIFICACION
   * @autor jhurtado
   * @descripcion se modifico la condicion para validar si existe el propietario
   */

  public String buscarPropietario() {
    log.info("Inicio");
    String result = null;

    try {
      flagPropietario = true;
      log.info("dni propietario: " + propietario.getRutPropietario());
      propietario = propietarioServiceDirecto.obtener(propietario.getRutPropietario());
      if (propietario != null) {

        disabledPropietario = true;
        existe = true;
        log.info("Existe : " + existe);
      } else {
        log.info("Existe : " + existe);
        existe = false;
        propietario = new Propietario();
        throw new SyncconException(ErrorConstants.COD_ERROR_CONSUL_PROPIETARIO, FacesMessage.SEVERITY_INFO);
      }

    } catch (SyncconException ex) {
      log.error(ERROR_SYNCCON + ex.getMessageComplete());
      FacesMessage facesMsg = new FacesMessage(ex.getSeveridad(), ex.getMessage(), null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
      result = MSJ_ERROR;
    }
    log.info("Fin");
    return result;
  }

  public void buscarModelo(ValueChangeEvent event) {
    log.info("Inicio");
    try {
      cargarModelos((BigDecimal) event.getNewValue());
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
      setCrud("INSERT");
      String placa = vehiculoPe.getPlaca();
      limpiar();
      vehiculoPe.setPlaca(placa);
      disabledPropietario = false;
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
      respuesta = MSJ_ERROR;
    }
    log.info("Fin");
    return respuesta;
  }

  public String guardarVehiculo() {
    log.info("Inicio");
    String result = "";
    String usuario = "";
    Date fecActual;

    try {
      fecActual = new Date(System.currentTimeMillis());
      usuario = SecurityContextHolder.getContext().getAuthentication().getName();

      if (crud.equals("INSERT")) {
        log.info("[ Inicio Insertar ]");
        // INICIO EP existe: Verifica si existe vehiculo registrado
        if (existe == false) {
          propietario.setVersion(new Long(0));
          propietario.setIdTipoDcto(new BigDecimal("1"));
          // propietario.setId(new BigDecimal("1"));*/
          auditRegistroVehicular = new AuditRegistroVehicular();

          if (propietarioServiceDirecto.insertar(propietario) == 1) {
            auditRegistroVehicular.setNombreTabla("PROPIETARIO");
            auditRegistroVehicular.setIdRegistro(String.valueOf(propietario.getId().intValue()));
            auditRegistroVehicular.setUsuCreacion(usuario);
            auditRegistroVehicular.setFecCreacion(fecActual);
            auditRegistroVehicularService.insertar(auditRegistroVehicular);
          }

          log.info("propietario: " + propietario.getId());

          direccionPropietarioVehiculo.setPlaca(vehiculoPe.getPlaca());
          direccionPropietarioVehiculo.setVersion(new Long(0));
          direccionPropietarioVehiculo.setDireccion(direccionPropietarioVehiculo.getDireccion());
          direccionPropietarioVehiculo.setPropietario(propietario.getId());

          if (direccionPropietarioVehiculoServiceDirecto.insertar(direccionPropietarioVehiculo) == 1) {
            auditRegistroVehicular.setNombreTabla("DIRECCION_PROPIETARIO_VEHICULO");
            auditRegistroVehicular.setIdRegistro(vehiculoPe.getPlaca());
            auditRegistroVehicular.setUsuCreacion(usuario);
            auditRegistroVehicular.setFecCreacion(fecActual);
            auditRegistroVehicularService.insertar(auditRegistroVehicular);
          }

          if (propietarioService.obtener(propietario.getRutPropietario()) == null) {
            propietarioService.insertar(propietario);
          }
          propietario = propietarioService.obtenerPropietario(propietario.getRutPropietario());

          log.info("propietario: " + propietario.getId());

          direccionPropietarioVehiculo.setPlaca(vehiculoPe.getPlaca());
          direccionPropietarioVehiculo.setVersion(new Long(0));
          direccionPropietarioVehiculo.setDireccion(direccionPropietarioVehiculo.getDireccion());
          direccionPropietarioVehiculo.setPropietario(propietario.getId());

          if (listaConsultaRegVehicular.isEmpty()) {
            direccionPropietarioVehiculoService.insertar(direccionPropietarioVehiculo);
          }

          vehiculoPe.setVersion(new Long(0));
          auditRegistroVehicular = new AuditRegistroVehicular();
          if (vehiculoPeServiceDirecto.insertarVehiculo(vehiculoPe) == 1) {
            auditRegistroVehicular.setNombreTabla("VEHICULO_PE");
            auditRegistroVehicular.setIdRegistro(String.valueOf(vehiculoPe.getId().intValue()));
            auditRegistroVehicular.setUsuCreacion(usuario);
            auditRegistroVehicular.setFecCreacion(fecActual);
            auditRegistroVehicularService.insertar(auditRegistroVehicular);
          }

          if (vehiculoPeServiceFalabella.obtener(vehiculoPe.getPlaca()) == null) {
            vehiculoPeServiceFalabella.insertarVehiculo(vehiculoPe);
          } else {
            vehiculoPe = vehiculoPeServiceFalabella.obtener(vehiculoPe.getPlaca());
          }

          log.info("[ Fin Insertar ]");

          tipoLimpiar = 1;
          limpiarPlaca();
        } else {
          log.info("[La placa " + vehiculoPe.getPlaca() + "Se encuentra registrada]");
          FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "El numero de placa: " + vehiculoPe.getPlaca() + " ya se encuentra registrada", null);
          FacesContext.getCurrentInstance().addMessage(null, facesMsg);
          result = "";
        }
        // FIN EP
      } else if (crud.equals("UPDATE")) {

        log.info("[ Inicio Actualizar ]");

        if (!vehiculoPe.getNumeroSerie().matches("^[a-zA-Z0-9]*$")) {
          throw new SyncconException(ErrorConstants.COD_ERROR_REG_VEHICULAR_SERIE_FORMAT, FacesMessage.SEVERITY_INFO);
        }

        BitacoraSoat bitacoraSoatVehiculo = new BitacoraSoat();
        BitacoraSoat bitacoraSoatDireccion = new BitacoraSoat();

        if (socio.equals(Constantes.COD_SOCIO_SOAT_DIRECTO)) {
          vehiculoPeServiceDirecto.actualizar(vehiculoPe);
          direccionPropietarioVehiculoServiceDirecto.actualizar(direccionPropietarioVehiculo);
          bitacoraSoatVehiculo.setNombreBaseDatos("USER_SOAT_DIRECPROD");
          bitacoraSoatDireccion.setNombreBaseDatos("USER_SOAT_DIRECPROD");
        } else if (socio.equals(Constantes.COD_SOCIO_SOAT_FALABELLA)) {
          vehiculoPeServiceFalabella.actualizar(vehiculoPe);
          direccionPropietarioVehiculoService.actualizar(direccionPropietarioVehiculo);
          bitacoraSoatVehiculo.setNombreBaseDatos("USER_SOAT");
          bitacoraSoatDireccion.setNombreBaseDatos("USER_SOAT");
        } else if (socio.equals(Constantes.COD_SOCIO_SOAT_CENCOSUD)) {
          vehiculoPeServiceCencosud.actualizar(vehiculoPe);
          direccionPropietarioVehiculoServiceCencosud.actualizar(direccionPropietarioVehiculo);
          bitacoraSoatVehiculo.setNombreBaseDatos("USER_SOAT_CENCOSUD");
          bitacoraSoatDireccion.setNombreBaseDatos("USER_SOAT_CENCOSUD");
        } else if (socio.equals(Constantes.COD_SOCIO_SOAT_COMPARA_BIEN)) {
          vehiculoPeServiceComparaBien.actualizar(vehiculoPe);
          direccionPropietarioVehiculoServiceComparaBien.actualizar(direccionPropietarioVehiculo);
          bitacoraSoatVehiculo.setNombreBaseDatos("USER_SOAT_COMPARABIEN");
          bitacoraSoatDireccion.setNombreBaseDatos("USER_SOAT_COMPARABIEN");
        } else if (socio.equals(Constantes.COD_SOCIO_SOAT_RIPLEY)) {
          vehiculoPeServiceRipley.actualizar(vehiculoPe);
          direccionPropietarioVehiculoServiceRipley.actualizar(direccionPropietarioVehiculo);
          bitacoraSoatVehiculo.setNombreBaseDatos("USER_SOAT_RIPLEY");
          bitacoraSoatDireccion.setNombreBaseDatos("USER_SOAT_RIPLEY");
        } else if (socio.equals(Constantes.COD_SOCIO_SOAT_RPP)) {
          vehiculoPeServiceRpp.actualizar(vehiculoPe);
          direccionPropietarioVehiculoServiceRpp.actualizar(direccionPropietarioVehiculo);
          bitacoraSoatVehiculo.setNombreBaseDatos("USER_SOAT_RPP");
          bitacoraSoatDireccion.setNombreBaseDatos("USER_SOAT_RPP");
        } else if (socio.equals(Constantes.COD_SOCIO_SOAT_SUCURSAL)) {
          vehiculoPeServiceSucursal.actualizar(vehiculoPe);
          direccionPropietarioVehiculoServiceSucursal.actualizar(direccionPropietarioVehiculo);
          bitacoraSoatVehiculo.setNombreBaseDatos("USER_SOAT_SUCURSAL");
          bitacoraSoatDireccion.setNombreBaseDatos("USER_SOAT_SUCURSAL");
        }

        if (!vehiculoPeAntiguo.equals(vehiculoPe)) {
          bitacoraSoatVehiculo.setFecha(fecActual);
          bitacoraSoatVehiculo.setNombreTabla("VEHICULO_PE");
          bitacoraSoatVehiculo.setOperacion(crud);
          bitacoraSoatVehiculo.setUsuario(usuario);
          bitacoraSoatVehiculo.setValorInicial(vehiculoPeAntiguo.toString());
          bitacoraSoatVehiculo.setValorFinal(vehiculoPe.toString());
          bitacoraSoatService.insertar(bitacoraSoatVehiculo);
        }

        if (!direccionPropietarioVehiculoAntiguo.equals(direccionPropietarioVehiculo)) {
          bitacoraSoatDireccion.setFecha(fecActual);
          bitacoraSoatDireccion.setNombreTabla("DIRECCION_PROPIETARIO_VEHICULO");
          bitacoraSoatDireccion.setOperacion(crud);
          bitacoraSoatDireccion.setUsuario(usuario);
          bitacoraSoatDireccion.setValorInicial(direccionPropietarioVehiculoAntiguo.toString());
          bitacoraSoatDireccion.setValorFinal(direccionPropietarioVehiculo.toString());
          bitacoraSoatService.insertar(bitacoraSoatDireccion);
        }

        buscarPlaca();
        log.info("[ Fin Actualizar ]");
      }
    } catch (SyncconException ex) {
      log.error(ERROR_SYNCCON + ex.getMessageComplete());
      FacesMessage facesMsg = new FacesMessage(ex.getSeveridad(), ex.getMessage(), null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
      result = MSJ_ERROR;
    }

    log.info("Fin");
    return result;
  }

  public String actualizar() {
    log.info("Inicio");
    String respuesta = null;

    try {
      setCrud("UPDATE");
      Iterator<Object> iterator = getSelection().getKeys();

      if (iterator.hasNext()) {
        int key = (Integer) iterator.next();
        String dni = listaConsultaRegVehicular.get(key).getPropietario();
        log.info("dni update: " + dni);

        if (socio.equals(Constantes.COD_SOCIO_SOAT_DIRECTO)) {
          propietario = propietarioServiceDirecto.obtener(String.valueOf(dni));
          vehiculoPe = vehiculoPeServiceDirecto.findVehiculo(vehiculoPe);
          direccionPropietarioVehiculo = direccionPropietarioVehiculoServiceDirecto.getDireccionPropietarioVehiculo(vehiculoPe);
        } else if (socio.equals(Constantes.COD_SOCIO_SOAT_FALABELLA)) {
          propietario = propietarioService.obtenerPropietario(String.valueOf(dni));
          vehiculoPe = vehiculoPeServiceFalabella.findVehiculo(vehiculoPe);
          direccionPropietarioVehiculo = direccionPropietarioVehiculoService.getDireccionPropietarioVehiculo(vehiculoPe);
        } else if (socio.equals(Constantes.COD_SOCIO_SOAT_CENCOSUD)) {
          propietario = propietarioServiceCencosud.obtener(String.valueOf(dni));
          vehiculoPe = vehiculoPeServiceCencosud.findVehiculo(vehiculoPe);
          direccionPropietarioVehiculo = direccionPropietarioVehiculoServiceCencosud.getDireccionPropietarioVehiculo(vehiculoPe);
        } else if (socio.equals(Constantes.COD_SOCIO_SOAT_COMPARA_BIEN)) {
          propietario = propietarioServiceComparaBien.obtener(String.valueOf(dni));
          vehiculoPe = vehiculoPeServiceComparaBien.findVehiculo(vehiculoPe);
          direccionPropietarioVehiculo = direccionPropietarioVehiculoServiceComparaBien.getDireccionPropietarioVehiculo(vehiculoPe);
        } else if (socio.equals(Constantes.COD_SOCIO_SOAT_RIPLEY)) {
          propietario = propietarioServiceRipley.obtener(String.valueOf(dni));
          vehiculoPe = vehiculoPeServiceRipley.findVehiculo(vehiculoPe);
          direccionPropietarioVehiculo = direccionPropietarioVehiculoServiceRipley.getDireccionPropietarioVehiculo(vehiculoPe);
        } else if (socio.equals(Constantes.COD_SOCIO_SOAT_RPP)) {
          propietario = propietarioServiceRpp.obtener(String.valueOf(dni));
          vehiculoPe = vehiculoPeServiceRpp.findVehiculo(vehiculoPe);
          direccionPropietarioVehiculo = direccionPropietarioVehiculoServiceRpp.getDireccionPropietarioVehiculo(vehiculoPe);
        } else if (socio.equals(Constantes.COD_SOCIO_SOAT_SUCURSAL)) {
          propietario = propietarioServiceSucursal.obtener(String.valueOf(dni));
          vehiculoPe = vehiculoPeServiceSucursal.findVehiculo(vehiculoPe);
          direccionPropietarioVehiculo = direccionPropietarioVehiculoServiceSucursal.getDireccionPropietarioVehiculo(vehiculoPe);
        }

        vehiculoPeAntiguo = new VehiculoPe();
        BeanUtils.copyProperties(vehiculoPeAntiguo, vehiculoPe);
        direccionPropietarioVehiculoAntiguo = new DireccionPropietarioVehiculo();
        BeanUtils.copyProperties(direccionPropietarioVehiculoAntiguo, direccionPropietarioVehiculo);

        disabledPropietario = true;
        log.info("disabledPropietario update: " + disabledPropietario);
        flagPropietario = true;

        cargarModelos(vehiculoPe.getMarcaVehiculo());
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

  public void limpiarPropietario() throws SyncconException {
    log.info("Inicio");

    if (crud.equals("INSERT")) {
      log.info("Limpia Insert");
      setPropietario(new Propietario());
      setVehiculoPe(new VehiculoPe());
      existe = false;
      flagPropietario = false;
      disabledPropietario = false;
    } else {
      log.info("Limpia update");
      selection = new SimpleSelection();
    }
    FacesContext context = FacesContext.getCurrentInstance();
    Application application = context.getApplication();
    ViewHandler viewHandler = application.getViewHandler();
    UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());
    context.setViewRoot(viewRoot);
    context.renderResponse(); // Optional

    log.info("Fin");
  }

  public void limpiarPlaca() throws Exception {
    log.info("Inicio");
    disabledPropietario = false;
    flagVehiculo = false;
    flagPropietario = false;
    estBtnEditar = false;
    selection = new SimpleSelection();
    setVehiculoPe(new VehiculoPe());
    setPropietario(new Propietario());
    setErrorPlaca("");

    // if (tipoLimpiar == 0){
    listaConsultaRegVehicular = new ArrayList<ConsultaRegVehicular>();
    // }
    // tipoLimpiar = 0;
    // cargarCombos();

    /*
     * FacesContext context = FacesContext.getCurrentInstance(); Application application = context.getApplication(); ViewHandler viewHandler =
     * application.getViewHandler(); UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());
     * context.setViewRoot(viewRoot); context.renderResponse(); //Optional
     */
    log.info("Fin");
  }

  public SimpleSelection getSelection() {
    return selection;
  }

  public void setSelection(SimpleSelection selection) {
    this.selection = selection;
  }

  public List<SelectItem> getMarcaItems() {
    return marcaItems;
  }

  public void setMarcaItems(List<SelectItem> marcaItems) {
    this.marcaItems = marcaItems;
  }

  public List<SelectItem> getModeloItems() {
    return modeloItems;
  }

  public void setModeloItems(List<SelectItem> modeloItems) {
    this.modeloItems = modeloItems;
  }

  public List<SelectItem> getDepartamentoItems() {
    return departamentoItems;
  }

  public void setDepartamentoItems(List<SelectItem> departamentoItems) {
    this.departamentoItems = departamentoItems;
  }

  public List<SelectItem> getAsientoItems() {
    return asientoItems;
  }

  public void setAsientoItems(List<SelectItem> asientoItems) {
    this.asientoItems = asientoItems;
  }

  public List<SelectItem> getCategoriaItems() {
    return categoriaItems;
  }

  public void setCategoriaItems(List<SelectItem> categoriaItems) {
    this.categoriaItems = categoriaItems;
  }

  public List<SelectItem> getAnioItems() {
    return anioItems;
  }

  public void setAnioItems(List<SelectItem> anioItems) {
    this.anioItems = anioItems;
  }

  public AuditRegistroVehicular getAuditRegistroVehicular() {
    return auditRegistroVehicular;
  }

  public void setAuditRegistroVehicular(AuditRegistroVehicular auditRegistroVehicular) {
    this.auditRegistroVehicular = auditRegistroVehicular;
  }

  public List<SelectItem> getUsoVehiculoItems() {
    return usoVehiculoItems;
  }

  public void setUsoVehiculoItems(List<SelectItem> usoVehiculoItems) {
    this.usoVehiculoItems = usoVehiculoItems;
  }

  public List<ConsultaRegPropietario> getListaConsultaRegPropietario() {
    return listaConsultaRegPropietario;
  }

  public void setListaConsultaRegPropietario(List<ConsultaRegPropietario> listaConsultaRegPropietario) {
    this.listaConsultaRegPropietario = listaConsultaRegPropietario;
  }

  public List<ConsultaRegVehicular> getListaConsultaRegVehicular() {
    return listaConsultaRegVehicular;
  }

  public void setListaConsultaRegVehicular(List<ConsultaRegVehicular> listaConsultaRegVehicular) {
    this.listaConsultaRegVehicular = listaConsultaRegVehicular;
  }

  public String getCrud() {
    return crud;
  }

  public void setCrud(String crud) {
    this.crud = crud;
  }

  public boolean isFlagPropietario() {
    return flagPropietario;
  }

  public void setFlagPropietario(boolean flagPropietario) {
    this.flagPropietario = flagPropietario;
  }

  public boolean isFlagVehiculo() {
    return flagVehiculo;
  }

  public void setFlagVehiculo(boolean flagVehiculo) {
    this.flagVehiculo = flagVehiculo;
  }

  public boolean isEstBtnEditar() {
    return estBtnEditar;
  }

  public void setEstBtnEditar(boolean estBtnEditar) {
    this.estBtnEditar = estBtnEditar;
  }

  public String getDNITemp() {
    return DNITemp;
  }

  public void setDNITemp(String dNITemp) {
    DNITemp = dNITemp;
  }

  public boolean isDisabledPropietario() {
    return disabledPropietario;
  }

  public void setDisabledPropietario(boolean disabledPropietario) {
    this.disabledPropietario = disabledPropietario;
  }

  public VehiculoPe getVehiculoPe() {
    return vehiculoPe;
  }

  public void setVehiculoPe(VehiculoPe vehiculoPe) {
    this.vehiculoPe = vehiculoPe;
  }

  public void setDireccionPropietarioVehiculo(DireccionPropietarioVehiculo direccionPropietarioVehiculo) {
    this.direccionPropietarioVehiculo = direccionPropietarioVehiculo;
  }

  public DireccionPropietarioVehiculo getDireccionPropietarioVehiculo() {
    return direccionPropietarioVehiculo;
  }

  public Propietario getPropietario() {
    return propietario;
  }

  public void setPropietario(Propietario propietario) {
    this.propietario = propietario;
  }

  public long getAnio() {
    return anio;
  }

  public void setAnio(long anio) {
    this.anio = anio;
  }

  public String getErrorPlaca() {
    return errorPlaca;
  }

  public void setErrorPlaca(String errorPlaca) {
    this.errorPlaca = errorPlaca;
  }

  public List<SelectItem> getSocioItems() {
    return socioItems;
  }

  public void setSocioItems(List<SelectItem> socioItems) {
    this.socioItems = socioItems;
  }

  public String getSocio() {
    return socio;
  }

  public void setSocio(String socio) {
    this.socio = socio;
  }

}
