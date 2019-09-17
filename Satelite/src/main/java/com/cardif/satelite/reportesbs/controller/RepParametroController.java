package com.cardif.satelite.reportesbs.controller;

import static com.cardif.satelite.constantes.ErrorConstants.ERROR_SYNCCON;
import static com.cardif.satelite.constantes.ErrorConstants.MSJ_ERROR;
import static com.cardif.satelite.constantes.ErrorConstants.MSJ_ERROR_GENERAL;

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
import org.richfaces.model.selection.Selection;
import org.richfaces.model.selection.SimpleSelection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import com.cardif.framework.controller.BaseController;
import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.constantes.Constantes;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.reportesbs.Empresa;
import com.cardif.satelite.model.reportesbs.Parametro;
import com.cardif.satelite.model.reportesbs.Proceso;
import com.cardif.satelite.model.reportesbs.ProcesoParametro;
import com.cardif.satelite.model.reportesbs.Producto;
import com.cardif.satelite.model.reportesbs.ProductoSocio;
import com.cardif.satelite.model.reportesbs.Socio;
import com.cardif.satelite.reportesbs.service.RepParametroService;
import com.cardif.satelite.util.SateliteUtil;

@Controller("repParametroController")
@Scope("request")
public class RepParametroController extends BaseController {

	public static final Logger logger = Logger
			.getLogger(RepParametroController.class);
	private boolean esNuevo;

	@Autowired
	private RepParametroService repParametroService;

	private SimpleSelection selection;
	private Selection procesoSelection;
	private List<ProcesoParametro> listaParametros;

	private List<SelectItem> empresaItems;
	private List<SelectItem> socioItems;
	private List<SelectItem> estadoItems;
	private List<SelectItem> tipoContratoItems;
	private List<SelectItem> productoItems;
	private List<SelectItem> tipoSeguroItems;
	private List<SelectItem> tipoReaseguroItems;
	private List<SelectItem> tipoMovimientoItems;
	private List<SelectItem> detalleCuentaItems;

	private List<SelectItem> empresaItems2;
	private List<SelectItem> socioItems2;
	private List<SelectItem> estadoItems2;
	private List<SelectItem> tipoContratoItems2;
	private List<SelectItem> productoItems2;
	private List<SelectItem> tipoSeguroItems2;
	private List<SelectItem> tipoReaseguroItems2;
	private List<SelectItem> tipoMovimientoItems2;

	private ProcesoParametro procesoParametro;
	private Proceso proceso;
	private Empresa empresa;
	private ProductoSocio productoSocio;

	private String tipoContrato_b;
	private String tipoEstado_b;
	private int producto_b;
	private String detalleCuenta_b;
	private String tipoSeguro_b;
	private String tipoReaseguro_b;
	private String tipoMovimiento_b;
	private int empresa_b;
	private int socio_b;
	private String totalSoles_b;
	private String primaSoles_b;
	private String descSoles_b;
	private String totalDolar_b;
	private String desDolar_b;
	private String primaDola_b;
	private String codigoSbs;
	private String codCuenta;
	private String riesgo;

	private boolean flagCrud;
	private boolean flagGuardar;
	private boolean flagActualizar;
	private boolean readonlyMostrar;
	private boolean readonlyDetalle;
	private boolean readonlyOtros;
	private String styleVerParametro;
	private String stryleDetalle;
	private String styleOtros;
	private String cuentaRepetida;
	private String mensajeCambioEstado;
	private String mensajeCambioEstado2;

	@Override
	@PostConstruct
	public String inicio() {

		String respuesta = null;
		selection = new SimpleSelection();
		procesoParametro = new ProcesoParametro();
		proceso = new Proceso();
		flagCrud = true;
		try {
			listaParametros = new ArrayList<ProcesoParametro>();
			empresaItems = llenarEmpresa();
			socioItems = llenarSocio();
			estadoItems = llenarEstado();
			tipoContratoItems = llenarTipoContrato();
			productoItems = llenarProducto();
			tipoSeguroItems = llenarTipoSeguro();
			tipoReaseguroItems = llenarTipoReaseguro();
			tipoMovimientoItems = llenarTipoMovimiento();

			empresaItems2 = llenarEmpresa2();
			socioItems2 = llenarSocio2();
			estadoItems2 = llenarEstado2();
			tipoContratoItems2 = llenarTipoContrato2();
			productoItems2 = llenarProducto2();
			tipoSeguroItems2 = llenarTipoSeguro2();
			tipoReaseguroItems2 = llenarTipoReaseguro2();
			tipoMovimientoItems2 = llenarTipoMovimiento2();
			detalleCuentaItems = llenarDetalleCuenta();

		} catch (SyncconException e) {
			log.error(e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		}

		logger.info("Final");
		return respuesta;
	}

	private List<SelectItem> llenarEmpresa() throws SyncconException {
		List<Empresa> listaEmpresa = repParametroService.listarEmpresa();
		List<SelectItem> items = new ArrayList<SelectItem>();

		items.add(new SelectItem(0, "TODOS"));

		for (Empresa e : listaEmpresa) {
			items.add(new SelectItem(Long.valueOf(e.getCodEmpresa()), e
					.getDescripcion()));
		}
		return items;
	}

	private List<SelectItem> llenarEmpresa2() throws SyncconException {
		List<Empresa> listaEmpresa = repParametroService.listarEmpresa();
		List<SelectItem> items = new ArrayList<SelectItem>();

		items.add(new SelectItem("", "Seleccionar"));
		for (Empresa e : listaEmpresa) {
			items.add(new SelectItem(Long.valueOf(e.getCodEmpresa()), e
					.getDescripcion()));
		}
		return items;
	}

	private List<SelectItem> llenarSocio() throws SyncconException {
		List<Socio> listaSocio = repParametroService.listarSocio();
		List<SelectItem> items = new ArrayList<SelectItem>();
		items.add(new SelectItem(0, "TODOS"));

		for (Socio s : listaSocio) {
			items.add(new SelectItem(Long.valueOf(s.getCodSocio()), s
					.getDescripcion()));
		}
		return items;
	}

	private List<SelectItem> llenarSocio2() throws SyncconException {
		List<Socio> listaSocio = repParametroService.listarSocio();
		List<SelectItem> items = new ArrayList<SelectItem>();

		items.add(new SelectItem("", "Seleccionar"));

		for (Socio s : listaSocio) {
			items.add(new SelectItem(Long.valueOf(s.getCodSocio()), s
					.getDescripcion()));
		}
		return items;
	}

	private List<SelectItem> llenarEstado() throws SyncconException {
		List<Parametro> listaEstados = repParametroService
				.listarTipoEstado(Constantes.TIPO_ESTADO);
		List<SelectItem> items = new ArrayList<SelectItem>();
		items.add(new SelectItem("T", "TODOS"));

		for (Parametro p : listaEstados) {
			items.add(new SelectItem(p.getCodParametro(), p.getDescripcion()));
		}
		return items;
	}

	private List<SelectItem> llenarEstado2() throws SyncconException {
		List<Parametro> listaEstados = repParametroService
				.listarTipoEstado(Constantes.TIPO_ESTADO);
		List<SelectItem> items = new ArrayList<SelectItem>();
		items.add(new SelectItem("", "Seleccionar"));

		for (Parametro p : listaEstados) {
			items.add(new SelectItem(p.getCodParametro(), p.getDescripcion()));
		}
		return items;
	}

	public List<SelectItem> llenarTipoContrato() throws SyncconException {
		List<Parametro> listaTipoContrato = repParametroService
				.listarTipoContrato(Constantes.TIPO_CONTRATO);
		List<SelectItem> items = new ArrayList<SelectItem>();
		items.add(new SelectItem("T", "TODOS"));

		for (Parametro p : listaTipoContrato) {
			items.add(new SelectItem(p.getCodParametro(), p.getDescripcion()));
		}
		return items;

	}

	public List<SelectItem> llenarTipoContrato2() throws SyncconException {
		List<Parametro> listaTipoContrato = repParametroService
				.listarTipoContrato(Constantes.TIPO_CONTRATO);
		List<SelectItem> items = new ArrayList<SelectItem>();
		items.add(new SelectItem("", "Seleccionar"));
		for (Parametro p : listaTipoContrato) {
			items.add(new SelectItem(p.getCodParametro(), p.getDescripcion()));
		}
		return items;

	}

	public List<SelectItem> llenarProducto() throws SyncconException {
		List<Producto> listaProducto = repParametroService.listarProducto();
		List<SelectItem> items = new ArrayList<SelectItem>();
		items.add(new SelectItem(0, "TODOS"));

		for (Producto p : listaProducto) {
			items.add(new SelectItem(Long.valueOf(p.getCodProducto()), p
					.getDescripcion()));
		}
		return items;

	}

	public List<SelectItem> llenarProducto2() throws SyncconException {
		List<Producto> listaProducto = repParametroService.listarProducto();
		List<SelectItem> items = new ArrayList<SelectItem>();
		items.add(new SelectItem("", "Seleccionar"));

		for (Producto p : listaProducto) {
			items.add(new SelectItem(Long.valueOf(p.getCodProducto()), p
					.getDescripcion()));
		}
		return items;

	}

	public List<SelectItem> llenarDetalleCuenta() throws SyncconException {
		List<Parametro> listaDetalleCuenta = repParametroService
				.listarDetalleCuenta(Constantes.DETALLE_CUENTA);
		List<SelectItem> items = new ArrayList<SelectItem>();
		items.add(new SelectItem("", "Seleccionar"));
		for (Parametro p : listaDetalleCuenta) {
			items.add(new SelectItem(p.getCodParametro(), p.getDescripcion()));
		}
		return items;

	}

	public List<SelectItem> llenarTipoSeguro() throws SyncconException {
		List<Parametro> listaTipoSeguro = repParametroService
				.listarTipo(Constantes.TIPO_SEGURO);
		List<SelectItem> items = new ArrayList<SelectItem>();
		items.add(new SelectItem("T", "TODOS"));

		for (Parametro p : listaTipoSeguro) {
			items.add(new SelectItem(p.getCodParametro(), p.getDescripcion()));
		}
		return items;

	}

	public List<SelectItem> llenarTipoSeguro2() throws SyncconException {
		List<Parametro> listaTipoSeguro = repParametroService
				.listarTipo(Constantes.TIPO_SEGURO);
		List<SelectItem> items = new ArrayList<SelectItem>();

		for (Parametro p : listaTipoSeguro) {
			items.add(new SelectItem(p.getCodParametro(), p.getDescripcion()));
		}
		return items;

	}

	public List<SelectItem> llenarTipoReaseguro() throws SyncconException {
		List<Parametro> listaTipoReaseguro = repParametroService
				.listarTipoReaseguro(Constantes.TIPO_REASEGURO);
		List<SelectItem> items = new ArrayList<SelectItem>();

		items.add(new SelectItem("T", "TODOS"));

		for (Parametro p : listaTipoReaseguro) {
			items.add(new SelectItem(p.getCodParametro(), p.getDescripcion()));
		}
		return items;

	}

	public List<SelectItem> llenarTipoReaseguro2() throws SyncconException {
		List<Parametro> listaTipoReaseguro = repParametroService
				.listarTipoReaseguro(Constantes.TIPO_REASEGURO);
		List<SelectItem> items = new ArrayList<SelectItem>();

		for (Parametro p : listaTipoReaseguro) {
			items.add(new SelectItem(p.getCodParametro(), p.getDescripcion()));
		}
		return items;

	}

	public List<SelectItem> llenarTipoMovimiento() throws SyncconException {
		List<Parametro> listaTipoMovimiento = repParametroService
				.listarTipoMovimiento(Constantes.TIPO_MOVIMIENTO);
		List<SelectItem> items = new ArrayList<SelectItem>();

		items.add(new SelectItem("T", "TODOS"));

		for (Parametro p : listaTipoMovimiento) {
			items.add(new SelectItem(p.getCodParametro(), p.getDescripcion()));
		}
		return items;

	}

	public List<SelectItem> llenarTipoMovimiento2() throws SyncconException {
		List<Parametro> listaTipoMovimiento = repParametroService
				.listarTipoMovimiento(Constantes.TIPO_MOVIMIENTO);
		List<SelectItem> items = new ArrayList<SelectItem>();
		for (Parametro p : listaTipoMovimiento) {
			items.add(new SelectItem(p.getCodParametro(), p.getDescripcion()));
		}
		return items;

	}

	public String buscarParametros() {
		logger.info("Buscando parametro");
		String respuesta = null;
		selection = new SimpleSelection();
		try {
			log.info("tipo_contrato" + tipoContrato_b);
			log.info("tipo_seguro" + tipoSeguro_b);
			log.info("tipo_reaseguro" + tipoReaseguro_b);
			log.info("tipo_movimiento" + tipoMovimiento_b);
			log.info("tipo_cuenta" + tipoEstado_b);
			log.info("empresa" + empresa_b);
			log.info("socio" + socio_b);
			log.info("producto" + producto_b);

			listaParametros = repParametroService.buscarListaProcesos(
					tipoContrato_b, tipoSeguro_b, tipoReaseguro_b,
					tipoMovimiento_b, tipoEstado_b, empresa_b, socio_b,
					producto_b, totalSoles_b, primaSoles_b, descSoles_b,
					totalDolar_b, primaDola_b, desDolar_b, detalleCuenta_b);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = MSJ_ERROR;
		}
		logger.info("Fin");
		return respuesta;

	}

	public String limpiar() {
		logger.info("Limpiando Datos");
		String respuesta = null;
		selection = new SimpleSelection();

		try {

			tipoContrato_b = null;
			tipoEstado_b = null;
			producto_b = 0;
			detalleCuenta_b = null;
			tipoSeguro_b = null;
			tipoReaseguro_b = null;
			tipoMovimiento_b = null;
			empresa_b = 0;
			socio_b = 0;
			totalSoles_b = null;
			primaSoles_b = null;
			descSoles_b = null;
			totalDolar_b = null;
			desDolar_b = null;
			primaDola_b = null;
			listaParametros = new ArrayList<ProcesoParametro>();
			FacesContext context = FacesContext.getCurrentInstance();
			Application application = context.getApplication();
			ViewHandler viewHandler = application.getViewHandler();
			UIViewRoot viewRoot = viewHandler.createView(context, context
					.getViewRoot().getViewId());
			context.setViewRoot(viewRoot);
			context.renderResponse();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = MSJ_ERROR;
		}

		logger.info("Fin");
		return respuesta;
	}

	public String registrarNuevo() {
		logger.info("Nuevo Registro");
		String respuesta = null;
		flagCrud = true;
		readonlyMostrar = false;
		styleVerParametro = "";
		readonlyDetalle = true;
		stryleDetalle = "background:#ddd; color:black;";
		readonlyOtros = false;
		styleOtros = "";
		try {
			proceso = new Proceso();
			respuesta = "siguienteParametro";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = MSJ_ERROR;
		}
		logger.info("Fin");

		return respuesta;

	}

	public String guardarRegistro() {
		String respuesta = null;
		log.info("Guardando Parametro");
		try {
			boolean validarCuentas = false;
			validarCuentas = validarCantidadCuentas(
					proceso.getCuentaSaldoSoles(),
					proceso.getCuentaPrimaSoles(),
					proceso.getCuentaDescuentoSoles(),
					proceso.getCuentaSaldoDolares(),
					proceso.getCuentaPrimaDolares(),
					proceso.getCuentaDescuentoDolares());
			if(!existeRiesgo(proceso.getRiesgo())){
				SateliteUtil
				.mostrarMensaje("Código de riesgo invalido");
				return "";
			}
		
			if (validarCuentas) {
				SateliteUtil
						.mostrarMensaje("Ingresar al menos un número de cuenta");
				return "";
			}
			validarDatosCuenta(proceso);
			if (comparaCuentas(proceso)) {
				SateliteUtil.mostrarMensaje("La cuenta "
						+ this.getCuentaRepetida() + " esta repetida");
				return "";
			}
			if (isCuentaRepetida(proceso)) {
				SateliteUtil.mostrarMensaje("La cuenta "
						+ this.getCuentaRepetida() + " ya ha sido registrada");
				return "";
			}

			if (flagCrud) {
				logger.info("Nuevo registro");
				proceso.setCodEstado(Constantes.TIPO_ESTADO_ACTIVO);
				proceso.setUsuarioCreador(SecurityContextHolder.getContext()
						.getAuthentication().getName());
				proceso.setFechaCreado(new Date(System.currentTimeMillis()));
				log.info("Cuenta Soles Dsto :"
						+ proceso.getCuentaDescuentoSoles());
				log.info("Cuenta Dolares Dsto :"
						+ proceso.getCuentaDescuentoDolares());
				flagGuardar = repParametroService
						.insertarProcesoParametro(proceso);

				if (flagGuardar) {
					logger.info("Registro Grabado");
					SateliteUtil.mostrarMensaje("Registro Grabado");
					listaParametros = repParametroService.buscarListaProcesos(
							tipoContrato_b, tipoSeguro_b, tipoReaseguro_b,
							tipoMovimiento_b, tipoEstado_b, empresa_b, socio_b,
							producto_b, totalSoles_b, primaSoles_b,
							descSoles_b, totalDolar_b, desDolar_b, primaDola_b,
							detalleCuenta_b);
				}
			} else {
				logger.info("Actualiza Registro");
				logger.info("Codigo" + proceso.getCodProcesoParametro());
				proceso.setUsuarioModificador(SecurityContextHolder
						.getContext().getAuthentication().getName());
				proceso.setFechaModificador(new Date(System.currentTimeMillis()));
				flagActualizar = repParametroService
						.actualizarProcesoParametro(proceso);
				if (flagActualizar) {
					logger.info("Registro Actualizado");

					SateliteUtil.mostrarMensaje("Registro Actualizado");
					listaParametros = repParametroService.buscarListaProcesos(
							tipoContrato_b, tipoSeguro_b, tipoReaseguro_b,
							tipoMovimiento_b, tipoEstado_b, empresa_b, socio_b,
							producto_b, totalSoles_b, primaSoles_b,
							descSoles_b, totalDolar_b, desDolar_b, primaDola_b,
							detalleCuenta_b);
				}
			}
			productoSocio = new ProductoSocio();
			productoSocio.setCodProducto(proceso.getCodProducto());
			productoSocio.setCodSocio(proceso.getCodSocio());
			productoSocio.setCodEstado(Constantes.COD_ESTADO_ACTIVO);
			productoSocio.setUsu_creador(SecurityContextHolder.getContext()
					.getAuthentication().getName());
			productoSocio.setFec_creacion(new Date(System.currentTimeMillis()));

			repParametroService.insertaProductoSocio(productoSocio);	
			
			respuesta = "guardarReg";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = MSJ_ERROR;
		}
		logger.info("Fin");
		return respuesta;

	}

	public boolean validarCantidadCuentas(String cuentaSoles,
			String cuentaSolesPrima, String cuentaSolesDsto, String cuentaDolares,
			String cuentaDolaresPrima, String cuentaDolaresDsto) {
		int contador = 0;
		boolean flag = false;
		if (cuentaSoles == null || cuentaSoles.equals("0") || cuentaSoles.equals("")) {
			contador++;
		}
		if (cuentaSolesPrima == null || cuentaSolesPrima.equals("0") || cuentaSolesPrima.equals("")) {
			contador++;
		}
		if (cuentaSolesDsto == null || cuentaSolesDsto.equals("0") || cuentaSolesDsto.equals("")) {
			contador++;
		}
		if (cuentaDolaresPrima == null || cuentaDolaresPrima.equals("0") || cuentaDolaresPrima.equals("")) {
			contador++;
		}
		if (cuentaDolaresDsto == null || cuentaDolaresDsto.equals("0") || cuentaDolaresDsto.equals("")) {
			contador++;
		}
		if (cuentaDolares == null || cuentaDolares.equals("0") || cuentaDolares.equals("")) {
			contador++;
		}
		logger.info("Cantidad de Cuentas :" + contador);
		if (contador == 6) {
			flag = true;
		}
		return flag;
	}

	public String cancelarRegistro() {
		logger.info("Cancelar");
		String respuesta = null;
		readonlyMostrar = false;
		styleVerParametro = "";
		try {
			respuesta = "cancelarReg";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = MSJ_ERROR;
		}
		logger.info("Fin Cancelar");
		return respuesta;

	}

	public String obtenerEstado() {
		log.info("Obteniendo Estado");
		String respuesta = null;
		Long pk = null;
		try {
			Iterator<Object> iterator = getSelection().getKeys();
			if (iterator.hasNext()) {
				int key = (Integer) iterator.next();
				pk = listaParametros.get(key).getCodProcesoParametro();
				log.info("Codigo Parametro" + pk);
			}
			if (pk != null) {
				proceso = repParametroService.buscarProceso(pk);
				log.info("Codigo estado" + proceso.getCodEstado());
				if (proceso.getCodEstado().equals(
						Constantes.TIPO_ESTADO_INACTIVO)) {
					validarDatosCuenta(proceso);
					if (isCuentaRepetida(proceso)) {
						SateliteUtil
								.mostrarMensaje("El registro seleccionado presenta la siguiente cuenta repetida "
										+ this.getCuentaRepetida()
										+ "\n "
										+ "no se puede actualizar el estado");
						return "";
					}
				}
				if (proceso.getCodEstado()
						.equals(Constantes.TIPO_ESTADO_ACTIVO)) {
					proceso.setCodEstado(Constantes.TIPO_ESTADO_INACTIVO);
					this.setMensajeCambioEstado(Constantes.MSJ_INACTIVO);
					this.setMensajeCambioEstado2(Constantes.MSJ_INACTIVO2);
				} else {
					proceso.setCodEstado(Constantes.TIPO_ESTADO_ACTIVO);
					this.setMensajeCambioEstado(Constantes.MSJ_ACTIVO);
					this.setMensajeCambioEstado2(Constantes.MSJ_ACTIVO2);
				}

			} else {
				throw new SyncconException(
						ErrorConstants.COD_ERROR_REGISTRO_NO_SELECCIONADO,
						FacesMessage.SEVERITY_INFO);

			}
		} catch (SyncconException ex) {
			logger.error(ERROR_SYNCCON + ex.getMessageComplete());
			FacesMessage facesMsg = new FacesMessage(ex.getSeveridad(),
					ex.getMessage(), null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = MSJ_ERROR;
		}
		log.info("Fin");
		return respuesta;
	}

	public String actualizarEstado() {
		log.info("Actualiza estado");
		String respuesta = null;
		boolean flagActualizaEstado = false;
		try {

			log.info("Codigo estado" + proceso.getCodEstado());
			validarDatosCuenta(proceso);

			proceso.setUsuarioModificador(SecurityContextHolder.getContext()
					.getAuthentication().getName());
			proceso.setFechaModificador(new Date(System.currentTimeMillis()));
			flagActualizaEstado = repParametroService
					.actualizarProcesoParametro(proceso);
			if (flagActualizaEstado) {
				log.info("Estado actualizado");
				SateliteUtil.mostrarMensaje("Estado actualizado");

				listaParametros = repParametroService.buscarListaProcesos(
						tipoContrato_b, tipoSeguro_b, tipoReaseguro_b,
						tipoMovimiento_b, tipoEstado_b, empresa_b, socio_b,
						producto_b, totalSoles_b, primaSoles_b, descSoles_b,
						totalDolar_b, desDolar_b, primaDola_b, detalleCuenta_b);

			} else {
				log.info("Estado no  actualizado");
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = MSJ_ERROR;
		}
		log.info("Fin");
		return respuesta;
	}

	public String mostrarParametro() {
		logger.info("editar");
		String respuesta = null;
		flagCrud = false;
		readonlyMostrar = true;
		readonlyOtros = false;
		Long pk = null;
		try {

			Iterator<Object> iterator = null;
			iterator = getSelection().getKeys();
			if (iterator.hasNext()) {
				int key = (Integer) iterator.next();
				pk = listaParametros.get(key).getCodProcesoParametro();

			}
			if (pk != null) {
				proceso = repParametroService.buscarProceso(pk);
				formateaCuentas(proceso);
			} else {
				throw new SyncconException(
						ErrorConstants.COD_ERROR_REGISTRO_NO_SELECCIONADO,
						FacesMessage.SEVERITY_INFO);

			}
			styleVerParametro = "background:#ddd; color:black;";
			styleOtros = "";
			if (proceso.getCodTipoCuenta().equals("0803")) {
				stryleDetalle = "";
				readonlyDetalle = false;

			} else {
				readonlyDetalle = true;
				stryleDetalle = "background:#ddd; color:black;";
			}
			respuesta = "mostrarPar";

		} catch (SyncconException ex) {
			logger.error(ERROR_SYNCCON + ex.getMessageComplete());
			FacesMessage facesMsg = new FacesMessage(ex.getSeveridad(),
					ex.getMessage(), null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = MSJ_ERROR;
		}
		logger.info("Fin");
		return respuesta;
	}

	public String copiarParametro() {
		logger.info("Copiar");
		String respuesta = null;
		flagCrud = true;
		readonlyMostrar = false;
		styleVerParametro = "";
		readonlyOtros = false;
		styleOtros = "";
		Long pk = null;
		try {

			Iterator<Object> iterator = null;
			iterator = getSelection().getKeys();
			if (iterator.hasNext()) {
				int key = (Integer) iterator.next();
				pk = listaParametros.get(key).getCodProcesoParametro();

			}
			if (pk != null) {

				proceso = repParametroService.buscarProceso(pk);
				proceso.setCodProcesoParametro(null);
				formateaCuentas(proceso);
			} else {
				throw new SyncconException(
						ErrorConstants.COD_ERROR_REGISTRO_NO_SELECCIONADO,
						FacesMessage.SEVERITY_INFO);

			}

			logger.info("Codigo de cuenta---" + proceso.getCodTipoCuenta());
			if (proceso.getCodTipoCuenta().equals("0803")) {
				stryleDetalle = "";
				readonlyDetalle = false;

			} else {
				readonlyDetalle = true;
				stryleDetalle = "background:#ddd; color:black;";
			}

			respuesta = "mostrarPar";
		} catch (SyncconException ex) {
			logger.error(ERROR_SYNCCON + ex.getMessageComplete());
			FacesMessage facesMsg = new FacesMessage(ex.getSeveridad(),
					ex.getMessage(), null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = MSJ_ERROR;
		}
		logger.info("Fin");
		return respuesta;
	}

	public String consultarParametro() {
		logger.info("Copiar");
		String respuesta = null;
		Long pk = null;

		readonlyMostrar = true;
		styleVerParametro = "background:#ddd; color:black;";
		readonlyDetalle = true;
		stryleDetalle = "background:#ddd; color:black;";
		readonlyOtros = true;
		styleOtros = "background:#ddd; color:black;";
		readonlyOtros = true;
		try {

			Iterator<Object> iterator = null;
			iterator = getSelection().getKeys();
			if (iterator.hasNext()) {
				int key = (Integer) iterator.next();
				pk = listaParametros.get(key).getCodProcesoParametro();
			}
			if (pk != null) {
				proceso = repParametroService.buscarProceso(pk);
				formateaCuentas(proceso);
			} else {
				throw new SyncconException(
						ErrorConstants.COD_ERROR_REGISTRO_NO_SELECCIONADO,
						FacesMessage.SEVERITY_INFO);

			}
			respuesta = "mostrarPar";
		} catch (SyncconException ex) {
			logger.error(ERROR_SYNCCON + ex.getMessageComplete());
			FacesMessage facesMsg = new FacesMessage(ex.getSeveridad(),
					ex.getMessage(), null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = MSJ_ERROR;
		}
		logger.info("Fin");
		return respuesta;
	}

	public String detalleCuentaChangeListener(ValueChangeEvent event) {
		String respuesta = "";
		String codigo = ((String) event.getNewValue()).toString();
		logger.info("Codigo" + codigo);
		if (codigo.trim().equals("0803")) {
			readonlyDetalle = false;
			stryleDetalle = "";
		} else {
			readonlyDetalle = true;
			stryleDetalle = "background:#ddd; color:black;";
			proceso.setDetalleCuenta("");
		}
		return respuesta;
	}

	public String buscarCodigoSBs(ValueChangeEvent event) {
		String respuesta = "";
		List<Empresa> lista = null;
		String codSbs;
		logger.info("buscarCodigoSBs");
		try {
			if (event.getNewValue() != null) {
				lista = repParametroService
						.listaSBS((Long) event.getNewValue());
				codSbs = lista.get(0).getCodSbs();
				logger.info("Cod Sbs:" + codSbs);
				proceso.setCodSbs(Long.parseLong(codSbs));
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = MSJ_ERROR;
		}
		logger.info("Fin");
		return respuesta;
	}

	public int cantidadCuentas(Long codParametro, Long numCuenta) {
		int cantCuentas = 0;
		try {
			if (Long.valueOf(numCuenta) > 0) {
				cantCuentas = repParametroService.cantidadCuentas(codParametro,
						numCuenta);
			}
		} catch (SyncconException e) {
			e.printStackTrace();
		}
		return cantCuentas;
	}

	public boolean isCuentaRepetida(Proceso proceso) {
		if (cantidadCuentas(proceso.getCodProcesoParametro(),
				Long.valueOf(proceso.getCuentaSaldoSoles())) > 0) {
			this.setCuentaRepetida("" + proceso.getCuentaSaldoSoles());
			return true;
		} else if (cantidadCuentas(proceso.getCodProcesoParametro(),
				Long.valueOf(proceso.getCuentaPrimaSoles())) > 0) {
			this.setCuentaRepetida("" + proceso.getCuentaPrimaSoles());
			return true;
		} else if (cantidadCuentas(proceso.getCodProcesoParametro(),
				Long.valueOf(proceso.getCuentaDescuentoSoles())) > 0) {
			this.setCuentaRepetida("" + proceso.getCuentaDescuentoSoles());
			return true;
		} else if (cantidadCuentas(proceso.getCodProcesoParametro(),
				Long.valueOf(proceso.getCuentaSaldoDolares())) > 0) {
			this.setCuentaRepetida("" + proceso.getCuentaSaldoDolares());
			return true;
		} else if (cantidadCuentas(proceso.getCodProcesoParametro(),
				Long.valueOf(proceso.getCuentaPrimaDolares())) > 0) {
			this.setCuentaRepetida("" + proceso.getCuentaPrimaDolares());
			return true;
		} else if (cantidadCuentas(proceso.getCodProcesoParametro(),
				Long.valueOf(proceso.getCuentaDescuentoDolares())) > 0) {
			this.setCuentaRepetida("" + proceso.getCuentaDescuentoDolares());
			return true;
		} else {
			return false;
		}

	}

	public boolean comparaCuentas(Proceso proceso) {
		boolean flag = false;
		String listaCuentas[] = { proceso.getCuentaSaldoSoles(),
				proceso.getCuentaPrimaSoles(),
				proceso.getCuentaDescuentoSoles(),
				proceso.getCuentaSaldoDolares(),
				proceso.getCuentaPrimaDolares(),
				proceso.getCuentaDescuentoDolares() };
		for (int i = 0; i < listaCuentas.length; i++) {
			log.info("Cuentas:" + i + " = " + listaCuentas[i]);
			if (Long.valueOf(listaCuentas[i]) > 0) {
				for (int j = i + 1; j < listaCuentas.length; j++) {
					if (listaCuentas[i].equals(listaCuentas[j])) {
						log.info("Cuentas Iguales:=" + listaCuentas);
						flag = true;
						this.setCuentaRepetida("" + listaCuentas[i]);
						break;
					}
				}
			}
		}
		return flag;
	}

	private void validarDatosCuenta(Proceso proceso) {
		if (proceso.getCuentaDescuentoSoles() == null
				|| proceso.getCuentaDescuentoSoles().equals("")) {
			proceso.setCuentaDescuentoSoles("0");
		}
		if (proceso.getCuentaPrimaSoles() == null
				|| proceso.getCuentaPrimaSoles().equals("")) {
			proceso.setCuentaPrimaSoles("0");
		}
		if (proceso.getCuentaSaldoSoles() == null
				|| proceso.getCuentaSaldoSoles().equals("")) {
			proceso.setCuentaSaldoSoles("0");
		}
		if (proceso.getCuentaDescuentoDolares() == null
				|| proceso.getCuentaDescuentoDolares().equals("")) {
			proceso.setCuentaDescuentoDolares("0");
		}
		if (proceso.getCuentaPrimaDolares() == null
				|| proceso.getCuentaPrimaDolares().equals("")) {
			proceso.setCuentaPrimaDolares("0");
		}
		if (proceso.getCuentaSaldoDolares() == null
				|| proceso.getCuentaSaldoDolares().equals("")) {
			proceso.setCuentaSaldoDolares("0");
		}
		if (proceso.getDetalleCuenta() == null
				|| proceso.getDetalleCuenta().trim() == "") {
			proceso.setDetalleCuenta(" ");
		}

	}
	
	public void formateaCuentas(Proceso proceso){
		if ( proceso.getCuentaDescuentoSoles().equals("0")) {
			proceso.setCuentaDescuentoSoles("");
		}
		if (proceso.getCuentaPrimaSoles().equals("0")) {
			proceso.setCuentaPrimaSoles("");
		}
		if (proceso.getCuentaSaldoSoles().equals("0")) {
			proceso.setCuentaSaldoSoles("");
		}
		if (proceso.getCuentaDescuentoDolares().equals("0")) {
			proceso.setCuentaDescuentoDolares("");
		}
		if ( proceso.getCuentaPrimaDolares().equals("0")) {
			proceso.setCuentaPrimaDolares("");
		}
		if ( proceso.getCuentaSaldoDolares().equals("0")) {
			proceso.setCuentaSaldoDolares("");
		}
	}
	
	public boolean existeRiesgo(String codRiesgo){
		boolean flag=false;
		try {
			flag=repParametroService.existeRiesgo(codRiesgo);
		} catch (SyncconException e) {
			logger.error(e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			
		}
		return flag;
	}
	

	public boolean isEsNuevo() {
		return esNuevo;
	}

	public void setEsNuevo(boolean esNuevo) {
		this.esNuevo = esNuevo;
	}

	public List<SelectItem> getTipoReaseguroItems() {
		return tipoReaseguroItems;
	}

	public void setTipoReaseguroItems(List<SelectItem> tipoReaseguroItems) {
		this.tipoReaseguroItems = tipoReaseguroItems;
	}

	public RepParametroService getRepParametroService() {
		return repParametroService;
	}

	public void setRepParametroService(RepParametroService repParametroService) {
		this.repParametroService = repParametroService;
	}

	public List<SelectItem> getEmpresaItems() {
		return empresaItems;
	}

	public void setEmpresaItems(List<SelectItem> empresaItems) {
		this.empresaItems = empresaItems;
	}

	public List<SelectItem> getSocioItems() {
		return socioItems;
	}

	public void setSocioItems(List<SelectItem> socioItems) {
		this.socioItems = socioItems;
	}

	public List<SelectItem> getEstadoItems() {
		return estadoItems;
	}

	public void setEstadoItems(List<SelectItem> estadoItems) {
		this.estadoItems = estadoItems;
	}

	public List<SelectItem> getTipoContratoItems() {
		return tipoContratoItems;
	}

	public void setTipoContratoItems(List<SelectItem> tipoContratoItems) {
		this.tipoContratoItems = tipoContratoItems;
	}

	public List<SelectItem> getProductoItems() {
		return productoItems;
	}

	public void setProductoItems(List<SelectItem> productoItems) {
		this.productoItems = productoItems;
	}

	public List<SelectItem> getTipoSeguroItems() {
		return tipoSeguroItems;
	}

	public void setTipoSeguroItems(List<SelectItem> tipoSeguroItems) {
		this.tipoSeguroItems = tipoSeguroItems;
	}

	public List<SelectItem> getTipoMovimientoItems() {
		return tipoMovimientoItems;
	}

	public void setTipoMovimientoItems(List<SelectItem> tipoMovimientoItems) {
		this.tipoMovimientoItems = tipoMovimientoItems;
	}

	public List<SelectItem> getDetalleCuentaItems() {
		return detalleCuentaItems;
	}

	public void setDetalleCuentaItems(List<SelectItem> detalleCuentaItems) {
		this.detalleCuentaItems = detalleCuentaItems;
	}

	public List<ProcesoParametro> getListaParametros() {
		return listaParametros;
	}

	public void setListaParametros(List<ProcesoParametro> listaParametros) {
		this.listaParametros = listaParametros;
	}

	public SimpleSelection getSelection() {
		return selection;
	}

	public void setSelection(SimpleSelection selection) {
		this.selection = selection;
	}

	public String getTipoEstado_b() {
		return tipoEstado_b;
	}

	public void setTipoEstado_b(String tipoEstado_b) {
		this.tipoEstado_b = tipoEstado_b;
	}

	public ProcesoParametro getProcesoParametro() {
		return procesoParametro;
	}

	public void setProcesoParametro(ProcesoParametro procesoParametro) {
		this.procesoParametro = procesoParametro;
	}

	public boolean isFlagGuardar() {
		return flagGuardar;
	}

	public void setFlagGuardar(boolean flagGuardar) {
		this.flagGuardar = flagGuardar;
	}

	public String getCodigoSbs() {
		return codigoSbs;
	}

	public void setCodigoSbs(String codigoSbs) {
		this.codigoSbs = codigoSbs;
	}

	public String getCodCuenta() {
		return codCuenta;
	}

	public void setCodCuenta(String codCuenta) {
		this.codCuenta = codCuenta;
	}

	public String getRiesgo() {
		return riesgo;
	}

	public void setRiesgo(String riesgo) {
		this.riesgo = riesgo;
	}

	public Selection getProcesoSelection() {
		return procesoSelection;
	}

	public void setProcesoSelection(Selection procesoSelection) {
		this.procesoSelection = procesoSelection;
	}

	public boolean isFlagActualizar() {
		return flagActualizar;
	}

	public void setFlagActualizar(boolean flagActualizar) {
		this.flagActualizar = flagActualizar;
	}

	public boolean isFlagCrud() {
		return flagCrud;
	}

	public void setFlagCrud(boolean flagCrud) {
		this.flagCrud = flagCrud;
	}

	public boolean isReadonlyMostrar() {
		return readonlyMostrar;
	}

	public void setReadonlyMostrar(boolean readonlyMostrar) {
		this.readonlyMostrar = readonlyMostrar;
	}

	public String getStyleVerParametro() {
		return styleVerParametro;
	}

	public void setStyleVerParametro(String styleVerParametro) {
		this.styleVerParametro = styleVerParametro;
	}

	public String getTipoContrato_b() {
		return tipoContrato_b;
	}

	public void setTipoContrato_b(String tipoContrato_b) {
		this.tipoContrato_b = tipoContrato_b;
	}

	public String getDetalleCuenta_b() {
		return detalleCuenta_b;
	}

	public void setDetalleCuenta_b(String detalleCuenta_b) {
		this.detalleCuenta_b = detalleCuenta_b;
	}

	public String getTipoSeguro_b() {
		return tipoSeguro_b;
	}

	public void setTipoSeguro_b(String tipoSeguro_b) {
		this.tipoSeguro_b = tipoSeguro_b;
	}

	public String getTipoReaseguro_b() {
		return tipoReaseguro_b;
	}

	public void setTipoReaseguro_b(String tipoReaseguro_b) {
		this.tipoReaseguro_b = tipoReaseguro_b;
	}

	public String getTipoMovimiento_b() {
		return tipoMovimiento_b;
	}

	public void setTipoMovimiento_b(String tipoMovimiento_b) {
		this.tipoMovimiento_b = tipoMovimiento_b;
	}

	public Proceso getProceso() {
		return proceso;
	}

	public void setProceso(Proceso proceso) {
		this.proceso = proceso;
	}

	public boolean isReadonlyDetalle() {
		return readonlyDetalle;
	}

	public void setReadonlyDetalle(boolean readonlyDetalle) {
		this.readonlyDetalle = readonlyDetalle;
	}

	public String getStryleDetalle() {
		return stryleDetalle;
	}

	public void setStryleDetalle(String stryleDetalle) {
		this.stryleDetalle = stryleDetalle;
	}

	public boolean isReadonlyOtros() {
		return readonlyOtros;
	}

	public void setReadonlyOtros(boolean readonlyOtros) {
		this.readonlyOtros = readonlyOtros;
	}

	public String getStyleOtros() {
		return styleOtros;
	}

	public void setStyleOtros(String styleOtros) {
		this.styleOtros = styleOtros;
	}

	public int getEmpresa_b() {
		return empresa_b;
	}

	public void setEmpresa_b(int empresa_b) {
		this.empresa_b = empresa_b;
	}

	public int getSocio_b() {
		return socio_b;
	}

	public void setSocio_b(int socio_b) {
		this.socio_b = socio_b;
	}

	public int getProducto_b() {
		return producto_b;
	}

	public void setProducto_b(int producto_b) {
		this.producto_b = producto_b;
	}

	public String getTotalSoles_b() {
		return totalSoles_b;
	}

	public void setTotalSoles_b(String totalSoles_b) {
		this.totalSoles_b = totalSoles_b;
	}

	public List<SelectItem> getEmpresaItems2() {
		return empresaItems2;
	}

	public void setEmpresaItems2(List<SelectItem> empresaItems2) {
		this.empresaItems2 = empresaItems2;
	}

	public List<SelectItem> getSocioItems2() {
		return socioItems2;
	}

	public void setSocioItems2(List<SelectItem> socioItems2) {
		this.socioItems2 = socioItems2;
	}

	public List<SelectItem> getEstadoItems2() {
		return estadoItems2;
	}

	public void setEstadoItems2(List<SelectItem> estadoItems2) {
		this.estadoItems2 = estadoItems2;
	}

	public List<SelectItem> getTipoContratoItems2() {
		return tipoContratoItems2;
	}

	public void setTipoContratoItems2(List<SelectItem> tipoContratoItems2) {
		this.tipoContratoItems2 = tipoContratoItems2;
	}

	public List<SelectItem> getProductoItems2() {
		return productoItems2;
	}

	public void setProductoItems2(List<SelectItem> productoItems2) {
		this.productoItems2 = productoItems2;
	}

	public List<SelectItem> getTipoSeguroItems2() {
		return tipoSeguroItems2;
	}

	public void setTipoSeguroItems2(List<SelectItem> tipoSeguroItems2) {
		this.tipoSeguroItems2 = tipoSeguroItems2;
	}

	public List<SelectItem> getTipoReaseguroItems2() {
		return tipoReaseguroItems2;
	}

	public void setTipoReaseguroItems2(List<SelectItem> tipoReaseguroItems2) {
		this.tipoReaseguroItems2 = tipoReaseguroItems2;
	}

	public List<SelectItem> getTipoMovimientoItems2() {
		return tipoMovimientoItems2;
	}

	public void setTipoMovimientoItems2(List<SelectItem> tipoMovimientoItems2) {
		this.tipoMovimientoItems2 = tipoMovimientoItems2;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public String getPrimaSoles_b() {
		return primaSoles_b;
	}

	public void setPrimaSoles_b(String primaSoles_b) {
		this.primaSoles_b = primaSoles_b;
	}

	public String getDescSoles_b() {
		return descSoles_b;
	}

	public void setDescSoles_b(String descSoles_b) {
		this.descSoles_b = descSoles_b;
	}

	public String getTotalDolar_b() {
		return totalDolar_b;
	}

	public void setTotalDolar_b(String totalDolar_b) {
		this.totalDolar_b = totalDolar_b;
	}

	public String getDesDolar_b() {
		return desDolar_b;
	}

	public void setDesDolar_b(String desDolar_b) {
		this.desDolar_b = desDolar_b;
	}

	public String getPrimaDola_b() {
		return primaDola_b;
	}

	public void setPrimaDola_b(String primaDola_b) {
		this.primaDola_b = primaDola_b;
	}

	public String getCuentaRepetida() {
		return cuentaRepetida;
	}

	public void setCuentaRepetida(String cuentaRepetida) {
		this.cuentaRepetida = cuentaRepetida;
	}

	public String getMensajeCambioEstado() {
		return mensajeCambioEstado;
	}

	public void setMensajeCambioEstado(String mensajeCambioEstado) {
		this.mensajeCambioEstado = mensajeCambioEstado;
	}

	public String getMensajeCambioEstado2() {
		return mensajeCambioEstado2;
	}

	public void setMensajeCambioEstado2(String mensajeCambioEstado2) {
		this.mensajeCambioEstado2 = mensajeCambioEstado2;
	}

	public ProductoSocio getProductoSocio() {
		return productoSocio;
	}

	public void setProductoSocio(ProductoSocio productoSocio) {
		this.productoSocio = productoSocio;
	}

}
