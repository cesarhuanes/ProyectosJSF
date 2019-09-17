package com.cardif.satelite.moduloimpresion.controller;

import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import com.cardif.framework.controller.BaseController;
import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.configuracion.service.DepartamentoService;
import com.cardif.satelite.configuracion.service.DistritoService;
import com.cardif.satelite.configuracion.service.ParametroAutomatService;
import com.cardif.satelite.configuracion.service.ProductoService;
import com.cardif.satelite.configuracion.service.ProvinciaService;
import com.cardif.satelite.configuracion.service.UsoVehiculoService;
import com.cardif.satelite.constantes.Constantes;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.Departamento;
import com.cardif.satelite.model.Distrito;
import com.cardif.satelite.model.ParametroAutomat;
import com.cardif.satelite.model.Provincia;
import com.cardif.satelite.model.UsoVehiculo;
import com.cardif.satelite.model.moduloimpresion.LoteImpresion;
import com.cardif.satelite.model.moduloimpresion.NumeroDocValorado;
import com.cardif.satelite.model.satelite.DetalleTramaDiaria;
import com.cardif.satelite.moduloimpresion.bean.ConsultaArmarLote;
import com.cardif.satelite.moduloimpresion.bean.ConsultaConfirmacionImpresion;
import com.cardif.satelite.moduloimpresion.bean.ConsultaProductoSocio;
import com.cardif.satelite.moduloimpresion.service.ArmarLoteService;
import com.cardif.satelite.suscripcion.handler.PolizaGeneratorHandler;
import com.cardif.satelite.util.Base64Util;
import com.cardif.satelite.util.SateliteUtil;

import cl.cardif.soap.datos.cliente.generaPoliza.DocumentoPDF;

/**
 * Esta clase contiene las funcionalidades para ARMAR UN LOTE a imprimir de un determinado SOCIO.
 * Modulo: IMPRESION
 * 
 * @author INFOPARQUEPERU
 * 		   Jose Manuel Lucas Barrera
 * 		   John Vara
 */
@Controller("armarLoteController")
@Scope("request")
public class ArmarLoteController extends BaseController
{
	private static final Logger logger = Logger.getLogger(ArmarLoteController.class);
	
	@Autowired
	private ArmarLoteService armarLoteService;
	
	@Autowired
	private ProductoService productoService;
	
	@Autowired
	private UsoVehiculoService usoVehiculoService;
	
	@Autowired
	private DepartamentoService departamentoService;
	
	@Autowired
	private ProvinciaService provinciaService;
	
	@Autowired
	private DistritoService distritoService;
	
	@Autowired
	private ParametroAutomatService parametroService;
	
	
	private List<SelectItem> comboListaProductos;
	private List<SelectItem> comboListaUsos;
	private List<SelectItem> comboListaDepartamentos;
	private List<SelectItem> comboListaProvincias;
	private List<SelectItem> comboListaDistritos;
	private List<SelectItem> comboListaRangoHorarios;
	
	private Long idProducto;
	private String numPlaca;
	private String idUsoVehiculo;
	private String idDepartamento;
	private String idProvincia;
	private String idDistrito;
	private Date fechaCompraDesde;
	private Date fechaCompraHasta;
	private Date fechaEntregaHasta;
	private String idRangoHorario;
	
	private List<ConsultaArmarLote> listaPolizasParaArmarLote;
	private List<ConsultaArmarLote> listaLoteImpresionArmado;
	private LoteImpresion loteImpresionObj;
	private List<ConsultaConfirmacionImpresion> listaConfirmacionImpresion;
	private List<ConsultaConfirmacionImpresion> listaConfirmacionImpresionAux;
	
	private List<ConsultaArmarLote> varListaSeleccionados;
	private List<ConsultaArmarLote> varListaNoEliminados;
	
	private boolean disabledBtnSeleccionarTodos;
	private boolean disabledBtnSiguiente;
	private boolean disabledBtnGenerarLote;
	private boolean disabledComponenteStep2;
	
	
	/*
	 * Navegacion de Paginas
	 */
	private boolean panelStep1Flag; 
	private boolean panelStep2Flag;
	private boolean panelStep3Flag;
	
	private String tituloCabecera;
	private String tituloSeleccionarTodos;
	private boolean seleccionarTodosFlag;
	
	private String nroLoteFormateado;
	
	
	/*CONTADOR*/
	private int registrosEntrados=0;
	private int registrosSeleccionados = 0;
	private int registrosImpresion = 0;
	
	@PostConstruct
	@Override
	public String inicio()
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		String respuesta = null;
		
		if (!tieneAcceso())
		{
			if (logger.isDebugEnabled()) {logger.debug("No cuenta con los accesos necesarios.");}
			return "accesoDenegado";
		}
		
		try
		{
			this.comboListaProductos = new ArrayList<SelectItem>();
			this.comboListaUsos = new ArrayList<SelectItem>();
			this.comboListaDepartamentos = new ArrayList<SelectItem>();
			this.comboListaProvincias = new ArrayList<SelectItem>();
			this.comboListaDistritos = new ArrayList<SelectItem>();
			this.comboListaRangoHorarios = new ArrayList<SelectItem>();
			
			this.listaPolizasParaArmarLote = new ArrayList<ConsultaArmarLote>();
			this.listaLoteImpresionArmado = new ArrayList<ConsultaArmarLote>();
			this.listaConfirmacionImpresion = new ArrayList<ConsultaConfirmacionImpresion>();
			this.listaConfirmacionImpresionAux = new ArrayList<ConsultaConfirmacionImpresion>();
			
			this.varListaSeleccionados = new ArrayList<ConsultaArmarLote>();
			this.varListaNoEliminados = new ArrayList<ConsultaArmarLote>();
			this.loteImpresionObj = null;
			
			this.idProducto = null;
			this.numPlaca = null;
			this.idUsoVehiculo = null;
			this.idDepartamento = null;
			this.idProvincia = null;
			this.idDistrito = null;
			this.fechaCompraDesde = null;
			this.fechaCompraHasta = null;
			this.fechaEntregaHasta = null;
			this.idRangoHorario = null;
			this.registrosEntrados=0;
			this.registrosSeleccionados = 0;
			this.registrosImpresion = 0;
			
			
			//*****************************************************************************
			//************************ CARGAS LOS VALORES INCIALES ************************
			//*****************************************************************************
			if (logger.isInfoEnabled()) {logger.info("Buscando la lista de PRODUCTOS.");}

			//List<Producto> listaProductos = null;

			List<ConsultaProductoSocio> listaProductoSocio = productoService.buscarPorModImpresionNombreCanal(Constantes.PRODUCTO_MOD_IMPRESION_SI, Constantes.CANAL_PROD_NOMBRE_DIGITAL);

			this.comboListaProductos.add(new SelectItem("", "- Seleccione -"));
			for (ConsultaProductoSocio productoSocio : listaProductoSocio)
			{
				if (logger.isDebugEnabled()) {logger.debug("PRODUCTO ==> ID: " + productoSocio.getId() + " - ID_SOCIO: " + productoSocio.getIdSocio() + " - NOMBRE_PRODUCTO: " + productoSocio.getNombreProducto() + " - NOMBRE_SOCIO: " + productoSocio.getNombreSocio());}
				this.comboListaProductos.add(new SelectItem(productoSocio.getId(), productoSocio.getNombreSocio().toUpperCase()));
				//+ " - " + productoSocio.getNombreProducto().toUpperCase()
			}
			
			
			if (logger.isInfoEnabled()) {logger.info("Buscando la lista de USOS DE VEHICULOS.");}
			List<UsoVehiculo> listaUsoVehiculos = usoVehiculoService.buscarUsoVehiculos();
			this.comboListaUsos.add(new SelectItem("", "- Seleccione -"));
			for (UsoVehiculo usoVehiculo : listaUsoVehiculos)
			{
				if (logger.isDebugEnabled()) {logger.debug("USO ==> COD_USO: " + usoVehiculo.getCodUso() + " - DESCRIPCION_USO: " + usoVehiculo.getDescripcionUso());}
				this.comboListaUsos.add(new SelectItem(usoVehiculo.getCodUso(), usoVehiculo.getDescripcionUso()));
			}
			
			
			if (logger.isInfoEnabled()) {logger.info("Buscando la lista de DEPARTAMENTOS.");}
			List<Departamento> listaDepartamentos = departamentoService.buscarDepartamentos();
			this.comboListaDepartamentos.add(new SelectItem("", "- Seleccione -"));
			for (Departamento departamento : listaDepartamentos)
			{
				if (logger.isDebugEnabled()) {logger.debug("DEPARTAMENTO ==> COD_DEPARTAMENTO: " + departamento.getCodDepartamento() + " - NOMBRE_DEPARTAMENTO: " + departamento.getNombreDepartamento());}
				this.comboListaDepartamentos.add(new SelectItem(departamento.getCodDepartamento(), departamento.getNombreDepartamento()));
			}
			
			
			/* Agregar el valor inicial para el ComboBox de Provincias */
			this.comboListaProvincias.add(new SelectItem("", "- Seleccione -"));
			
			/* Agregar el valor inicial para el ComboBox de Distritos */
			this.comboListaDistritos.add(new SelectItem("", "- Seleccione -"));
			
			
			if (logger.isInfoEnabled()) {logger.info("Buscando la lista de RANGOS DE HORARIOS DE ENTREGA.");}
			List<ParametroAutomat> listaRangoHorarios = parametroService.buscarPorCodParam(Constantes.COD_PARAM_RANGO_HORARIO);
			this.comboListaRangoHorarios.add(new SelectItem("", "- Seleccione -"));
			for (ParametroAutomat rangoHorario : listaRangoHorarios)
			{
				if (logger.isDebugEnabled()) {logger.debug("RANGO HORARIO ==> COD_VALOR: " + rangoHorario.getCodValor() + " - NOMBRE_VALOR: " + rangoHorario.getNombreValor());}
				this.comboListaRangoHorarios.add(new SelectItem(rangoHorario.getCodValor(), rangoHorario.getNombreValor()));
			}
			
			
			this.disabledBtnSeleccionarTodos = true;
			this.disabledBtnSiguiente = true;
			this.disabledBtnGenerarLote = true;
			
			
			/* Iniciando en la PAGINA 1  */
			this.panelStep1Flag = true;
			this.panelStep2Flag = false;
			this.panelStep3Flag = false;
			
			/* Iniciando sin agregar nada adicional a la cabecera */
			this.tituloCabecera = null;
			
			/* Valor del boton 'Seleccionar Todos' */
			this.tituloSeleccionarTodos = Constantes.BTN_ARMAR_LOTE_SELECT_TODOS;
			
			/* Iniciando el valor FLAG del boton 'Seleccionar Todos' */
			this.seleccionarTodosFlag = true;
		}
		catch (SyncconException e)
		{
			logger.error("SyncconException() - ERROR: " + ErrorConstants.ERROR_SYNCCON + e.getMessageComplete());
			logger.error("SyncconException() -->" + ExceptionUtils.getStackTrace(e));
			FacesMessage facesMsg = new FacesMessage(e.getSeveridad(), e.getMessage(), null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName()  + ") -->" + ExceptionUtils.getStackTrace(e));
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = ErrorConstants.MSJ_ERROR;
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return respuesta;
	} //inicio
	
	public String buscarProvincia(ValueChangeEvent event)
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		String respuesta = null;
		
		try
		{
			this.comboListaProvincias = new ArrayList<SelectItem>();
			this.comboListaProvincias.add(new SelectItem("", "- Seleccione -"));
			
			if (null != event.getNewValue())
			{
				if (logger.isDebugEnabled()) {logger.debug("Buscar provincias relacionadas al departamento: " + (String) event.getNewValue());}
				List<Provincia> listaProvincias = provinciaService.buscarByCodDepartamento((String) event.getNewValue());
				
				if (logger.isDebugEnabled()) {logger.debug("Se encontraron [" + (null != listaProvincias ? listaProvincias.size() : 0) + "] provincias.");}
				for (Provincia provincia : listaProvincias)
				{
					this.comboListaProvincias.add(new SelectItem(provincia.getCodProvincia(), provincia.getNombreProvincia()));
				}
			}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = ErrorConstants.MSJ_ERROR;
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return respuesta;
	} //buscarProvincia
	
	public String buscarDistrito(ValueChangeEvent event)
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		String respuesta = null;
		
		try
		{
			this.comboListaDistritos = new ArrayList<SelectItem>();
			this.comboListaDistritos.add(new SelectItem("", "- Seleccione -"));
			
			if (null != event.getNewValue())
			{
				if (logger.isDebugEnabled()) {logger.debug("Buscar distritos relacionados a la provincia: " + (String) event.getNewValue());}
				List<Distrito> listaDistritos = distritoService.buscarByCodProvincia((String) event.getNewValue());
				
				if (logger.isDebugEnabled()) {logger.debug("Se encontraron [" + (null != listaDistritos ? listaDistritos.size() : 0) + "] distritos.");}
				for (Distrito distrito : listaDistritos)
				{
					comboListaDistritos.add(new SelectItem(distrito.getCodDistrito(), distrito.getNombreDistrito()));
				}
			}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = ErrorConstants.MSJ_ERROR;
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return respuesta;
	} //buscarDistrito
	
	public void selectionCheckArmarLoteListener(ActionEvent event)
	{
		if (logger.isDebugEnabled()) {logger.debug("Inicio");}
		List<ConsultaArmarLote> listaSeleccionados = new ArrayList<ConsultaArmarLote>();
		for (ConsultaArmarLote poliza : this.listaPolizasParaArmarLote)
		{
			if (poliza.isSelected())
			{
				listaSeleccionados.add(poliza);
			}
		}
		
		this.varListaSeleccionados = listaSeleccionados;
		
		/* Habilitando o deshabilitando el boton '> Siguiente' */
		if (null != this.varListaSeleccionados && 0 < this.varListaSeleccionados.size())
		{
			this.disabledBtnSiguiente = false;
			
			if (this.varListaSeleccionados.size() < this.listaPolizasParaArmarLote.size()) /* No se han seleccionado todos los registros */
			{
				this.seleccionarTodosFlag = true;
				this.tituloSeleccionarTodos = Constantes.BTN_ARMAR_LOTE_SELECT_TODOS;
			}
			else /* Se han seleccionado todos los registros */
			{
				this.seleccionarTodosFlag = false;
				this.tituloSeleccionarTodos = Constantes.BTN_ARMAR_LOTE_DESELECT_TODOS;
			}
		}
		else
		{
			this.disabledBtnSiguiente = true;
		}
		
		this.registrosSeleccionados = varListaSeleccionados.size();
		
		if (logger.isDebugEnabled()) {logger.debug("Fin");}
	} //selectionCheckArmarLoteListener
	
	public void selectionCheckEliminarPolizaListener(ActionEvent event)
	{
		if (logger.isDebugEnabled()) {logger.debug("Inicio");}
		List<ConsultaArmarLote> listaNoEliminados = new ArrayList<ConsultaArmarLote>();
		for (ConsultaArmarLote poliza : this.listaLoteImpresionArmado)
		{
			if (poliza.isDeleted())
			{
				poliza.setDeletedCSSStyle("color: gray; text-decoration: line-through;");
			}
			else
			{
				listaNoEliminados.add(poliza);
				poliza.setDeletedCSSStyle("color: black; text-decoration: none;");
			}
		}
		
		
		this.varListaNoEliminados = listaNoEliminados;
		if (logger.isDebugEnabled()) {logger.debug("Fin");}
	} //selectionCheckEliminarPolizaListener
	
	public String procesarBusqueda()
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		String respuesta = null;
		this.registrosSeleccionados = 0;

		try
		{
			if(this.fechaCompraDesde != null &&  this.fechaCompraHasta !=null){
				if(fechaCompraHasta.before(fechaCompraDesde)){
					throw new SyncconException(ErrorConstants.COD_ERROR_RANGO_FECHAS, FacesMessage.SEVERITY_INFO);
				}
			}
			
			if(!StringUtils.isBlank(this.numPlaca)){
			if(this.numPlaca.length()<6  ){
				throw new SyncconException(ErrorConstants.COD_VALIDACION_ARMAR_LOTE,FacesMessage.SEVERITY_INFO);
			}
			}
			if (logger.isDebugEnabled())
			{
				logger.debug("################ PARAMETROS DE LA BUSQUEDA ################" + 
						"\nID_PRODUCTO: " + this.idProducto + "\tPLACA: " + this.numPlaca + "\tID_USO_VEHICULO: " + this.idUsoVehiculo + 
						"\nID_DEPARTAMENTO: " + this.idDepartamento + "\tID_PROVINCIA: " + this.idProvincia + "\tID_DISTRITO: " + this.idDistrito + 
						"\nFECHA_COMPRA_DESDE: " + this.fechaCompraDesde + "\tFECHA_COMPRA_HASTA: " + this.fechaCompraHasta + 
						"\nFECHA_ENTREGA_HASTA: " + this.fechaEntregaHasta + "\tID_RANGO_HORARIO: " + this.idRangoHorario);
			}
			
			/* Reiniciando la variable de registros seleccionados */
			this.varListaSeleccionados = null;
			
			
			if (logger.isDebugEnabled()) {logger.debug("Se obtiene la lista de polizas para armar el lote.");}
			this.listaPolizasParaArmarLote = armarLoteService.buscarPolizas(this.idProducto, this.numPlaca, this.idUsoVehiculo, this.idDepartamento, this.idProvincia, this.idDistrito, 
					this.fechaCompraDesde, this.fechaCompraHasta, this.fechaEntregaHasta, this.idRangoHorario);
			
			int size = (null != this.listaPolizasParaArmarLote) ? this.listaPolizasParaArmarLote.size() : 0;
			if (logger.isInfoEnabled()) {logger.info("Se encontraron [" + size + "] registros de LOTES DE IMPRESION.");}
			
			
			this.disabledBtnSeleccionarTodos = (0 < size) ? false : true;
			this.tituloSeleccionarTodos = Constantes.BTN_ARMAR_LOTE_SELECT_TODOS;
			
			/* Deshabilitando el boton '> Siguiente' */
			this.disabledBtnSiguiente = true;
			this.registrosEntrados = listaPolizasParaArmarLote.size(); 
			
			if(this.listaPolizasParaArmarLote.size() ==0){
				throw new SyncconException(ErrorConstants.COD_ERROR_LISTA_VACIA,FacesMessage.SEVERITY_INFO);
			}
			
			
		}
		catch (SyncconException e)
	    {
	    	logger.error("SyncconException() - " + ErrorConstants.ERROR_SYNCCON + e.getMessageComplete());
	    	FacesMessage facesMsg = new FacesMessage(e.getSeveridad(), e.getMessage(), null);
	    	FacesContext.getCurrentInstance().addMessage(null, facesMsg);
	    }
	    catch (Exception e)
	    {
	    	logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
	    	FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
	    	FacesContext.getCurrentInstance().addMessage(null, facesMsg);
	    	respuesta = ErrorConstants.MSJ_ERROR;
	    }
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return respuesta;
	} //procesarBusqueda
	
	public String seleccionarTodasPolizas()
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		String respuesta = null;
		
		if (this.seleccionarTodosFlag) /* Se seleccionaran todos los registros */
		{
			for (ConsultaArmarLote poliza : this.listaPolizasParaArmarLote)
			{
				poliza.setSelected(true);
			}
			
			this.seleccionarTodosFlag = false;
			this.tituloSeleccionarTodos = Constantes.BTN_ARMAR_LOTE_DESELECT_TODOS;
			
			this.varListaSeleccionados = this.listaPolizasParaArmarLote;
			this.disabledBtnSiguiente = false;
			this.registrosSeleccionados = this.listaPolizasParaArmarLote.size();

		}
		else /* Se deseleccionaran todos los registros */
		{
			for (ConsultaArmarLote poliza : this.listaPolizasParaArmarLote)
			{
				poliza.setSelected(false);
			}
			
			this.seleccionarTodosFlag = true;
			this.tituloSeleccionarTodos = Constantes.BTN_ARMAR_LOTE_SELECT_TODOS;
			
			this.varListaSeleccionados = null;
			this.disabledBtnSiguiente = true;
			this.registrosSeleccionados = 0;

		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return respuesta;
	} //seleccionarTodasPolizas
	
	public String cargarValidarCertificado()
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		String respuesta = null;
			
		try
		{
			if (null != this.varListaSeleccionados && 0 < this.varListaSeleccionados.size())
			{
				if (logger.isDebugEnabled()) {logger.debug("Cargando las polizas seleccionadas para el armado del LOTE. Seleccionadas: " + this.varListaSeleccionados.size());}
				this.listaLoteImpresionArmado = new ArrayList<ConsultaArmarLote>();
				
				for (ConsultaArmarLote conPolizasParaArmarLote : this.varListaSeleccionados)
				{
					/* Para que se pueda escribir en el campo del Numero de Certificado */
					conPolizasParaArmarLote.setValidated(false);
					
					if (logger.isDebugEnabled()) {logger.debug("Poliza seleccionada ==> ID: " + conPolizasParaArmarLote.getId() + " NRO_POLIZA_PE: " + conPolizasParaArmarLote.getNroPolizaPe() + " PLACA: " + conPolizasParaArmarLote.getPlaca());}
					this.listaLoteImpresionArmado.add(conPolizasParaArmarLote);
				}
				
				
				if (logger.isInfoEnabled()) {logger.info("Se cargaron [" + this.listaLoteImpresionArmado.size() + "] polizas para el ARMADO DEL LOTE.");}
				
				
				/* Deshabilitar el boton 'Generar Lote' */
				this.disabledBtnGenerarLote = true;
				
				/* 
				 * Habilitando el CHECK de Eliminados, la caja de texto para validar
				 * el numero de certificado y el boton 'Validar Certificados'
				 */
				this.disabledComponenteStep2 = false;
				
				/* Iniciar en 0 registros */
				this.varListaNoEliminados = new ArrayList<ConsultaArmarLote>();
				
				/*
				 * Cambiando a la PAGINA 2 'armarLoteModImpresionStep2.xhtml'
				 */
				this.panelStep2Flag = true;
				this.panelStep1Flag = false;
				this.panelStep3Flag = false;
				
				/*
				 * Cambiando el titulo de la PAGINA
				 */
				
				this.tituloCabecera = Constantes.MOD_IMP_VALIDACION_CERTIFICADO;
				
				this.registrosImpresion = this.varListaSeleccionados.size();
				// CLEAR INFO DATA
				this.listaPolizasParaArmarLote = null;
				this.registrosEntrados = 0;
				this.registrosSeleccionados = 0;
			}
		}
		catch (Exception e)
	    {
	    	logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
	    	logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
	    	FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
	    	FacesContext.getCurrentInstance().addMessage(null, facesMsg);
	    	respuesta = ErrorConstants.MSJ_ERROR;
	    }
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return respuesta;
	} //cargarValidarCertificado
	
	public String procesoValidarCertificado()
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		String respuesta = null;
		
		try
		{
			if (null != this.listaLoteImpresionArmado && 0 < this.listaLoteImpresionArmado.size())
			{
				
				
				/*
				 * Verifica si no se ha utilizado el listener para eliminar polizas, entonces el objeto varListaNoEliminados debera
				 * tener el mismo numero de registros que el objeto listaLoteImpresionArmado
				 */
				if (0 == this.varListaNoEliminados.size())
				{
					this.varListaNoEliminados = this.listaLoteImpresionArmado;
				}
				
				
				int nroPolizas = this.listaLoteImpresionArmado.size();
				int nroPolEliminadas = this.listaLoteImpresionArmado.size() - this.varListaNoEliminados.size();
				int nroPolValidadas = 0;
				int nroPolizasFaild = 0 ;
				
				if (logger.isDebugEnabled()){logger.debug("\nNro. de Polizas: " + nroPolizas + "\tNro. de Polizas a validar: " + this.varListaNoEliminados.size() + "\tNro. de Polizas Eliminadas: " + nroPolEliminadas);}
				
				
				if (!validarExistanNroCertificados(this.varListaNoEliminados))
				{
					throw new SyncconException(ErrorConstants.COD_ERROR_ARMAR_LOTE_NRO_CERTIFICADO_VACIO);
				}
				
				if (logger.isDebugEnabled()) {logger.debug("Se valido que existan Nro. de certificados en las polizas.");}				
				
				if (!validarFormatoCertificados(this.varListaNoEliminados))
				{
					throw new SyncconException(ErrorConstants.COD_ERROR_ARMAR_LOTE_FORMATO_CERTIFICADOS);
				}
				if (logger.isDebugEnabled()) {logger.debug("Se valido el formato de los certificados correctamente.");}
				
				
				if (!validarDuplicidadCertificados(this.varListaNoEliminados))
				{
					throw new SyncconException(ErrorConstants.COD_ERROR_ARMAR_LOTE_DUPLICIDAD_CERTIFICADOS);
				}
				if (logger.isDebugEnabled()) {logger.debug("Se valido la duplicidad de los certificados correctamente.");}
				
				
				/* Limpiando el CSS de las polizas antes de VALIDAR */
				for (ConsultaArmarLote poliza : this.listaLoteImpresionArmado)
				{
					poliza.setValidatedCSSStyle(Constantes.MOD_IMP_CERT_VALIDAR_BACKGROUND_COLOR_CLEAN);
				}
				
				
				/*
				 * Se valida que el numero de certificado (correlativo + tipo + anio) sea unico y que
				 * pertenezca al PRODUCTO seleccionado en la PAGINA 1 'armarLoteModImpresionStep1.xhtml'
				 */
				Map<Long, Boolean> certValidosMap = armarLoteService.validarNumeroDocValorados(this.varListaNoEliminados);
				if (logger.isInfoEnabled()) {logger.info("Se validaron los certificados de las polizas.");}
				
				for (ConsultaArmarLote poliza : this.varListaNoEliminados)
				{
					Long idDetalleTramaDiaria = poliza.getId();
					if(!poliza.isDeleted()){
						boolean validadoFlag = certValidosMap.get(idDetalleTramaDiaria);
						if (logger.isDebugEnabled()) {logger.debug("Poliza ==>ID: " + idDetalleTramaDiaria + " NRO_POLIZA: " + poliza.getNroPolizaPe() + " VALIDADO_FLAG: " + validadoFlag);}
						
						/*
						 * Obteniendo el puntero del cual se realizara el cambio del valor 'validated'
						 * Siempre retorna un valor en P
						 */
						int p = getPunteroListaValidarCertificadoLote(this.listaLoteImpresionArmado, idDetalleTramaDiaria);
						this.listaLoteImpresionArmado.get(p).setValidated(validadoFlag);
						
						if (validadoFlag)
						{
							nroPolValidadas++;
							this.listaLoteImpresionArmado.get(p).setValidatedCSSStyle(Constantes.MOD_IMP_CERT_VALIDAR_BACKGROUND_COLOR_OK);
						}
						else
						{
							nroPolizasFaild ++;
							this.listaLoteImpresionArmado.get(p).setValidatedCSSStyle(Constantes.MOD_IMP_CERT_VALIDAR_BACKGROUND_COLOR_FAIED);
						}
					}
					
					
				}
				
				if (logger.isDebugEnabled()) {logger.debug("El Nro. de Polizas validadas es: " + nroPolValidadas);}
				
				/*
				 * Eliminando de la ventana visual las polizas eliminadas.
				 * Este escenario solo se realiza cuando todas las polizas que no estan eliminadas, han sido
				 * validadas.
				 */
				
				if (nroPolizas == (nroPolValidadas + nroPolEliminadas))
				{
					if (logger.isDebugEnabled()) {logger.debug("Suprimiendo VISUALMENTE las polizas ELIMINADAS.");}
					
					/*
					 * Pasando las polizas no eliminadas al objeto array que esta enlazado con la tabla
					 * de registros VISUAL
					 */
					this.listaLoteImpresionArmado = this.varListaNoEliminados;
					
					/* Habilitando el boton 'Generar Lote' */
					this.disabledBtnGenerarLote = false;
					
					/*
					 * Deshabilitando los checks de la columna 'Eliminar',las cajas de texto en donde se ingresa el
					 * Nro. de certificado y el boton 'Validar Certificados'
					 */
					this.disabledComponenteStep2 = true;
				}
				
				if(nroPolValidadas==0 && nroPolizasFaild ==0){
					throw new SyncconException(ErrorConstants.COD_INFO_MENSAJE_VALIDACION_CERTIFIVCADO,FacesMessage.SEVERITY_INFO);
				}
				
			}
			this.registrosImpresion = listaLoteImpresionArmado.size();
		}
		catch (SyncconException e)
		{
			logger.error("SyncconException() - " + ErrorConstants.ERROR_SYNCCON + e.getMessageComplete());
	    	FacesMessage facesMsg = new FacesMessage(e.getSeveridad(), e.getMessage(), null);
	    	FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = ErrorConstants.MSJ_ERROR;
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return respuesta;
	} //procesoValidarCertificado
	
	@SuppressWarnings({ "unchecked", "null" })
	public String procesoGenerarLote()
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		String respuesta = null;
		
		try
		{
			/* Generando un objeto MAP en donde se colocaran los PDFs */
			Map<Long, String> mapPDFBase64 = new HashMap<Long, String>(this.listaLoteImpresionArmado.size());
		
			for (ConsultaArmarLote cArmarLote : this.listaLoteImpresionArmado)
			{
				Long pkDetTramaDiaria = cArmarLote.getId();
				String numeroDocVal = cArmarLote.getNumCertificado();
				if (logger.isDebugEnabled()) {logger.debug("Actualizando los datos del registro [" + pkDetTramaDiaria + "] de la tabla DETALLE_TRAMA_DIARIA.");}
				
				DetalleTramaDiaria detTramaDiariaObj = armarLoteService.actualizarPolizaImpresa(pkDetTramaDiaria, numeroDocVal, Constantes.MOD_IMPRESION_ESTADO_EN_ESPERA);
				if (logger.isInfoEnabled()) {logger.info("Se actualizo el objeto DetalleTramaDiaria[" + detTramaDiariaObj.getId() + "].");}
				
				NumeroDocValorado numDocValorado = armarLoteService.actualizarEstadoNumDocValorado(numeroDocVal, Constantes.COD_NUM_DOC_VALORADO_USADO, 
						detTramaDiariaObj.getNroPolizaPe());
				if (logger.isDebugEnabled()) {logger.debug("Se actualizo el objecto NumeroDocValorado[" + numDocValorado.getId() + "].");}
				
				PolizaGeneratorHandler convertirDocumentoGeneratorHandler =  PolizaGeneratorHandler.newInstance();
				DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");

				if (logger.isDebugEnabled()) {
				logger.debug(
						 "\n getNroPolizaPe"+ detTramaDiariaObj.getNroPolizaPe()
						 +"\n  getPlaca " + detTramaDiariaObj.getPlaca()
						 +"\n  getSerie " + detTramaDiariaObj.getSerie()
						 +"\n  getNroAsientos " + detTramaDiariaObj.getNroAsientos()
						 +"\n  getAnioFab " + detTramaDiariaObj.getAnioFab() 
						 +"\n  getCategoriaClase " + detTramaDiariaObj.getCategoriaClase()
						 +"\n  getUsoVehiculo " + detTramaDiariaObj.getUsoVehiculo() 
						 +"\n  getMarca " + detTramaDiariaObj.getMarca() 
						 +"\n  getModelo " + detTramaDiariaObj.getModelo() 
						 +"\n  getMoneda " + detTramaDiariaObj.getPrimaBruta() 
						 +"\n  getFecEmision " + detTramaDiariaObj.getFecVenta()
						 +"\n  getFecEmision " + detTramaDiariaObj.getHoraVenta()
						 +"\n  getFecIniVigencia " + detTramaDiariaObj.getFecIniVigencia()
						 +"\n  getFecFinVigencia " + detTramaDiariaObj.getFecFinVigencia() 
						 +"\n  getNumDoc " + detTramaDiariaObj.getNumDoc()
						 +"\n  getNomContrat " + detTramaDiariaObj.getNomContrat()
						 +"\n  getApelPateContrat " + detTramaDiariaObj.getApelPateContrat() 
						 +"\n  getApelMateContrat " + detTramaDiariaObj.getApelMateContrat()
						 +"\n  getDepartamento " + detTramaDiariaObj.getDepartamento() 
						 +"\n  getProvincia " + detTramaDiariaObj.getProvincia() 
						 +"\n  getDistrito " + detTramaDiariaObj.getDistrito()
						 +"\n  getDireccion " + detTramaDiariaObj.getDireccion()
						 +"\n  getTelMovil " + detTramaDiariaObj.getTelFijo()								
						);
				}
			
					try
					{
						
					DocumentoPDF documento =	convertirDocumentoGeneratorHandler.generarPolizaPDF(detTramaDiariaObj.getNroPolizaPe(),detTramaDiariaObj.getPlaca(),
												detTramaDiariaObj.getSerie(),detTramaDiariaObj.getNroAsientos(), detTramaDiariaObj.getAnioFab(), 
												detTramaDiariaObj.getCategoriaClase(), detTramaDiariaObj.getUsoVehiculo(), 
												detTramaDiariaObj.getMarca(), detTramaDiariaObj.getModelo(),detTramaDiariaObj.getPrimaBruta(), 
												detTramaDiariaObj.getFecVenta(),detTramaDiariaObj.getHoraVenta().toString(), detTramaDiariaObj.getFecIniVigencia(), detTramaDiariaObj.getFecFinVigencia(), 
												detTramaDiariaObj.getNumDoc(),detTramaDiariaObj.getNomContrat(), detTramaDiariaObj.getApelPateContrat(), 
												detTramaDiariaObj.getApelMateContrat(), detTramaDiariaObj.getDepartamento(), detTramaDiariaObj.getProvincia(), 
												detTramaDiariaObj.getDistrito(), detTramaDiariaObj.getDireccion(),detTramaDiariaObj.getTelFijo());	
					
					logger.debug("documento return documento.getPoliza()" + documento.getPoliza());
					
					String convertPDf = 	Base64Util.getInstance().convertBase64ToString(documento.getPoliza());
					
					if (logger.isDebugEnabled()) {logger.debug("Guardando el PDF Base 64 en el objeto MAP." + convertPDf );}
					
					mapPDFBase64.put(Long.valueOf(detTramaDiariaObj.getNroPolizaPe()), convertPDf);
						
					}
					catch(Exception e)
					{
						logger.error(e.getStackTrace());
						logger.error(e.getMessage());
						logger.error("Error al momento de generar el pdf, se setea la poliza en null para almacenarla.Oder compra # ");
					}
				
				
				
				
			}
			
			
			if (logger.isDebugEnabled()) {logger.debug("Concatenando los PDF's en un solo LOTE.");}
			String pdfBase64 = Base64Util.getInstance().concatenarPDF(mapPDFBase64);
			String usuario = SecurityContextHolder.getContext().getAuthentication().getName();
				
			if (logger.isInfoEnabled())
			{
				logger.info("Guardando los datos en las tablas LOTE_IMPRESION y DETALLE_LOTE_IMPRESION." + 
						"\nUSUARIO: " + usuario + 
						"\nPRODUCTO: " + this.idProducto + 
						"\nPDF_BASE64: " + (null != pdfBase64 ? pdfBase64.length() : 0));
			}
			
		if(null != pdfBase64){
			this.loteImpresionObj = armarLoteService.insertarLoteImpresion(Constantes.MOD_IMPRESION_ESTADO_PENDIENTE, pdfBase64, this.idProducto, 
					usuario, this.listaLoteImpresionArmado);
			if (logger.isInfoEnabled()) {logger.info("Se guardo el lote de impresion correctamente en la BD. PK: " + (null != this.loteImpresionObj ? this.loteImpresionObj.getNumLote() : "NULL"));}
			
			
			/*
			 * Buscando las polizas en la DB con el numero del LOTE generado
			 */
			this.listaConfirmacionImpresion = armarLoteService.buscarPolizasParaConfirmarImpresion(this.loteImpresionObj.getNumLote());
			
			/*
			 * Guardar la lista de la confirmacion de impresion en una variable auxiliar
			 * para luego validar, la anterior informacion con la nueva
			 */
			this.listaConfirmacionImpresionAux = (ArrayList<ConsultaConfirmacionImpresion>) SateliteUtil.cloneObject(this.listaConfirmacionImpresion);
			
			if (logger.isDebugEnabled())
			{
				for (ConsultaConfirmacionImpresion conConfirmacionImp : this.listaConfirmacionImpresionAux)
				{
					logger.debug("Poliza_AUX ==>PK: " + conConfirmacionImp.getId() + " NRO_POLIZA: " + conConfirmacionImp.getNroPolizaPe() + " NRO_CERTIFICADO: " + conConfirmacionImp.getNroCertificado());
				}
			}
			
			
			/*
			 * Formateando el numero del LOTE para efectos visuales
			 */
			this.nroLoteFormateado = "#" + SateliteUtil.formatNumber(loteImpresionObj.getNumLote(), Constantes.NUM_LOTE_FORMAT); 
			
			this.panelStep3Flag = true;
			this.panelStep1Flag = false;
			this.panelStep2Flag = false;
			
			/*
			 * Cambiando el titulo de la PAGINA
			 */
			this.tituloCabecera = Constantes.MOD_IMP_CONFIRMACION_IMPRESION;
		}else{
			
			throw new SyncconException(ErrorConstants.COD_INFO_MENSAJE_ERROR_WEBSERVICE,FacesMessage.SEVERITY_INFO);
		}
		
		}
		catch (SyncconException e)
		{
			logger.error("SyncconException() - " + ErrorConstants.ERROR_SYNCCON + e.getMessageComplete());
	    	FacesMessage facesMsg = new FacesMessage(e.getSeveridad(), e.getMessage(), null);
	    	FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
	    	logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
	    	FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
	    	FacesContext.getCurrentInstance().addMessage(null, facesMsg);
	    	respuesta = ErrorConstants.MSJ_ERROR;
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return respuesta;
	} //procesoGenerarLote
	
	public String descargarPDFLoteImpresion()
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		String respuesta = null;
		
		try
		{
			if (null == this.loteImpresionObj)
			{
				throw new SyncconException(ErrorConstants.COD_ERROR_ARMAR_LOTE_IMP_NULA);
			}
			
			if (logger.isDebugEnabled()) {logger.debug("Iniciando la descarga del LOTE DE IMPRESION.");}
			
			String pdfCodificadoLote = this.loteImpresionObj.getPdfCodificadoLote();
			if (StringUtils.isNotBlank(pdfCodificadoLote))
			{
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				long timeInMillis = Calendar.getInstance().getTimeInMillis();
				String fechaHoy = sdf.format(timeInMillis);
				
				byte[] loteImpresionBytes = Base64Util.getInstance().convertStringToBase64(pdfCodificadoLote);
				if (logger.isDebugEnabled()) {logger.debug("Lote a imprimir en bytes: " + (null != loteImpresionBytes ? loteImpresionBytes.length : 0));}
				
				/*
				 * Guardando el PDF del lote en el DISCO DURO para poder manejarlo
				 */
				File fileTemp = Base64Util.getInstance().saveFileToTemp(loteImpresionBytes, "SOAT_Lote_" + fechaHoy + "_" +	this.loteImpresionObj.getNumLote() + "_" + timeInMillis + ".pdf");
				if (logger.isDebugEnabled()) {logger.debug("Se guardo el LOTE en la ruta temporal: " + fileTemp.getAbsolutePath());}
				
				ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
				FileInputStream fis = new FileInputStream(fileTemp);
				
				HttpServletResponse response = (HttpServletResponse) context.getResponse();
				byte[] loader = new byte[(int) fileTemp.length()];
				
				response.addHeader("Content-Disposition", "attachment;filename=SOAT_Lote_" + fechaHoy + "_" + this.loteImpresionObj.getNumLote() + "_" + timeInMillis + ".pdf");
				response.setContentType("application/pdf");
				
				ServletOutputStream sos = response.getOutputStream();
				while ((fis.read(loader)) > 0)
				{
					sos.write(loader, 0, loader.length);
				}
				fis.close();
				sos.close();
				
				FacesContext.getCurrentInstance().responseComplete();
				if (logger.isInfoEnabled()) {logger.info("Se descargo el PDF del LOTE correctamente.");}
			}
			else
			{

				throw new SyncconException(ErrorConstants.COD_ERROR_DESCARGAR_PDF_VACIO);
			}
		}
		catch (SyncconException e)
		{
			logger.error("SyncconException() - " + ErrorConstants.ERROR_SYNCCON + e.getMessageComplete());
			FacesMessage facesMsg = new FacesMessage(e.getSeveridad(), e.getMessage(), null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
	    	respuesta = ErrorConstants.MSJ_ERROR;
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return respuesta;
	} //descargarPDFLoteImpresion
	
	public String guardarLoteImpresion()
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		String respuesta = null;
		
		for (int i = 0; i < listaConfirmacionImpresion.size(); i++) {
			try {
				
				if (listaConfirmacionImpresion.get(i).getImpresoCorrectamente().contains(Constantes.MOD_IMP_IMPRESO_CORRECTAMENTE_REEMPLAZAR)) {
					validarNroCertificadoConfImpresion(listaConfirmacionImpresion.get(i));
					
				}else{
					this.listaConfirmacionImpresion.get(i).setValidatedCSSStyle(Constantes.MOD_IMP_CERT_VALIDAR_BACKGROUND_COLOR_OK);
				}					
				
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		
		
		
		
		try
		{
			if (!validarExistanNroCertificadoStep3(this.listaConfirmacionImpresion))
			{
				throw new SyncconException(ErrorConstants.COD_ERROR_ARMAR_LOTE_NRO_CERTIFICADO_VACIO);				
			}
			if (logger.isDebugEnabled()) {logger.debug("Se valido que existan Nro. de certificados en las polizas.");}
			
			
			if (!validarDuplicidadCertificadoStep3(this.listaConfirmacionImpresion))
			{
				throw new SyncconException(ErrorConstants.COD_ERROR_ARMAR_LOTE_DUPLICIDAD_CERTIFICADOS);
			}
			if (logger.isDebugEnabled()) {logger.debug("Se valido la duplicidad de los certificados correctamente.");}
			
			
			for (ConsultaConfirmacionImpresion cConfirmacionImpresion : this.listaConfirmacionImpresion)
			{
				ConsultaConfirmacionImpresion cConfirmacionImpresionAux = getConfirmacionImpresionAuxObj(this.listaConfirmacionImpresionAux, cConfirmacionImpresion.getId());
				if (logger.isDebugEnabled())
				{
					logger.debug("conf_imp_ID: " + cConfirmacionImpresion.getId() + " conf_imp_NRO_POLIZA: " + cConfirmacionImpresion.getNroPolizaPe() + " conf_imp_NRO_CERT: " + cConfirmacionImpresion.getNroCertificado() + 
							"\nconf_imp_AUX_ID: " + cConfirmacionImpresionAux.getId() + " conf_imp_AUX_NRO_POLIZA: " + cConfirmacionImpresionAux.getNroPolizaPe() + " conf_imp_AUX_CER: " + cConfirmacionImpresionAux.getNroCertificado());					
				}
				
				boolean isOK = armarLoteService.confirmarImpresionPorPoliza(cConfirmacionImpresion, cConfirmacionImpresionAux);
				if (logger.isInfoEnabled()) {logger.info("La poliza(" + cConfirmacionImpresion.getId() + " - " + cConfirmacionImpresion.getNroPolizaPe() + ") fue confirmada correctamente? " + isOK);}
			}
			
			
			boolean isOK = armarLoteService.actualizarEstadoLoteImpresion(this.listaConfirmacionImpresion.get(0).getNumLote(), Constantes.MOD_IMPRESION_ESTADO_IMPRESO);
			if (isOK)
			{
				if (logger.isInfoEnabled()) {logger.info("Se actualizo el estado del LOTE DE IMPRESION a: " + Constantes.MOD_IMPRESION_ESTADO_IMPRESO + " correctamente.");}
			}
			else
			{
				if (logger.isInfoEnabled()) {logger.info("No se actualizo correctamente el estado del LOTE DE IMPRESION.");}
			}
			
			if (logger.isDebugEnabled()) {logger.debug("Se guardo y confirmo el LOTE DE IMPRESION correctamente.");}
		}
		catch (SyncconException e)
		{
			logger.error("SyncconException() - " + ErrorConstants.ERROR_SYNCCON + e.getMessageComplete());
	    	FacesMessage facesMsg = new FacesMessage(e.getSeveridad(), e.getMessage(), null);
	    	FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
	    	logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
	    	FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
	    	FacesContext.getCurrentInstance().addMessage(null, facesMsg);
	    	respuesta = ErrorConstants.MSJ_ERROR;
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return respuesta;
	} //guardarLoteImpresion
	
	public String reiniciarPantallaInicial()
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		String respuesta = null;
		
		try
		{
			this.listaPolizasParaArmarLote = new ArrayList<ConsultaArmarLote>();
			this.listaLoteImpresionArmado = new ArrayList<ConsultaArmarLote>();
			this.listaConfirmacionImpresion = new ArrayList<ConsultaConfirmacionImpresion>();
			this.listaConfirmacionImpresionAux = new ArrayList<ConsultaConfirmacionImpresion>();
			
			this.loteImpresionObj = null;
			
			this.disabledBtnSiguiente = true;

			
			/*
			 * Iniciando en la PAGINA 1
			 */
			this.panelStep1Flag = true;
			this.panelStep2Flag = false;
			this.panelStep3Flag = false;
			
			this.tituloCabecera = null;
			
			
			this.idProducto = null;
			this.numPlaca = new String("");
			this.idUsoVehiculo = new String("");
			this.idDepartamento = new String("");
			this.idProvincia = new String("");
			this.idDistrito = new String("");
			this.fechaCompraDesde = null;
			this.fechaCompraHasta = null;
			this.fechaEntregaHasta = null;
			this.idRangoHorario = new String("");
			
			/* Agregar el valor inicial para el ComboBox de Provincias */
			this.comboListaProvincias.add(new SelectItem("", "- Seleccione -"));
			
			/* Agregar el valor inicial para el ComboBox de Distritos */
			this.comboListaDistritos.add(new SelectItem("", "- Seleccione -"));
			
			if (logger.isDebugEnabled()) {logger.debug("Reiniciado CORRECTO.");}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
	    	logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
	    	FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
	    	FacesContext.getCurrentInstance().addMessage(null, facesMsg);
	    	respuesta = ErrorConstants.MSJ_ERROR;
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return respuesta;
	} //reiniciarPantallaInicial
	
	private int getPunteroListaValidarCertificadoLote(List<ConsultaArmarLote> listaVCertificadoLote, Long idDetTramaDiaria)
	{
		int p = 0;
		
		for (int i=0; i<listaVCertificadoLote.size(); i++)
		{
			if (listaVCertificadoLote.get(i).getId().equals(idDetTramaDiaria))
			{
				p = i; break;
			}
		}
		return p;
	} //getPunteroListaValidarCertificadoLote
	
	private boolean validarExistanNroCertificados(List<ConsultaArmarLote> listaVCertificadoLote)
	{
		boolean flag = true;
		
		for (ConsultaArmarLote cArmarLote : listaVCertificadoLote)
		{
			String numCertificado = cArmarLote.getNumCertificado();
			
			if (StringUtils.isBlank(numCertificado) && !cArmarLote.isDeleted())
			{
				flag = false; break;
			}
		}
		
		return flag;
	} //validarExistanNroCertificados
	
	private boolean validarExistanNroCertificadoStep3(List<ConsultaConfirmacionImpresion> listaConfImpresion)
	{
		boolean flag = true;
		
		for (ConsultaConfirmacionImpresion confImpresion : listaConfImpresion)
		{
			String nroCertificado = confImpresion.getNroCertificado();
			
			if (StringUtils.isBlank(nroCertificado))
			{
				flag = false; break;
			}
		}
		return flag;
	} //validarExistanNroCertificadoStep3
	
	private boolean validarFormatoCertificados(List<ConsultaArmarLote> listaVCertificadoLote)
	{
		boolean flag = true;
		
		for (ConsultaArmarLote cArmarLote : listaVCertificadoLote)
		{
			String numCertificado = cArmarLote.getNumCertificado();
			
			/* Verificando que cumpla con los 12 caracteres */
			if ((12 < numCertificado.length()  &&  numCertificado.length() > 14)  && !cArmarLote.isDeleted())
			{
				flag = false; break;
			}
		}
		return flag;
	} //validarFormatoCertificados
	
	public boolean validarDuplicidadCertificados(List<ConsultaArmarLote> listaVCertificadoLote)
	{
		boolean flag = true;
		
		end :
			for (int i=0; i<listaVCertificadoLote.size(); i++)
			{
				String numCertificado = listaVCertificadoLote.get(i).getNumCertificado();
				
				for (int j=0; j<listaVCertificadoLote.size(); j++)
				{
					/*
					 * Si cumplen con la igualdad, hay duplicidad
					 */
					if(listaVCertificadoLote.get(j).isDeleted()==false && listaVCertificadoLote.get(j).isDeleted() ==false){
					if (listaVCertificadoLote.get(j).getNumCertificado().equalsIgnoreCase(numCertificado) && (i != j))
					{
						flag = false; 
						break end;
					}
					}
					
				}
			}
		return flag;
	} //validarDuplicidadCertificados
	
	public boolean validarDuplicidadCertificadoStep3(List<ConsultaConfirmacionImpresion> listaConfImpresion)
	{
		boolean flag = true;
		
		end :
			for (int i=0; i<listaConfImpresion.size(); i++)
			{
				String nroCertificado = listaConfImpresion.get(i).getNroCertificado();
				
				for (int j=0; j<listaConfImpresion.size(); j++)
				{
					/*
					 * Si cumplen con la igualdad, hay duplicidad
					 */
					if (listaConfImpresion.get(j).getNroCertificado().equalsIgnoreCase(nroCertificado) && (i != j))
					{
						flag = false; 
						break end;
					}
				}
			}
		return flag;
	} //validarDuplicidadCertificadoStep3
	
	private ConsultaConfirmacionImpresion getConfirmacionImpresionAuxObj(List<ConsultaConfirmacionImpresion> listaConfirmacionImp, Long pk)
	{
		ConsultaConfirmacionImpresion response = null;
		
		for (ConsultaConfirmacionImpresion cConfirmacionImpresion : listaConfirmacionImp)
		{
			if (cConfirmacionImpresion.getId().compareTo(pk) == 0)
			{
				response = cConfirmacionImpresion; break;
			}
		}
		return response;
	} //getConfirmacionImpresionAuxObj
	
	public String validarNroCertificadoConfImpresion(ConsultaConfirmacionImpresion bean)
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		String respuesta = null;
		
		try
		{
			ConsultaArmarLote consultaArmarLoteBean = new ConsultaArmarLote();
			consultaArmarLoteBean.setId(bean.getId());
			consultaArmarLoteBean.setNumCertificado(bean.getNroCertificado());
			consultaArmarLoteBean.setIdProducto(bean.getIdProducto());
			
			List<ConsultaArmarLote> lista = new ArrayList<ConsultaArmarLote>();
			lista.add(consultaArmarLoteBean);
			
			
			if (!validarFormatoCertificados(lista))
			{
				throw new SyncconException(ErrorConstants.COD_ERROR_ARMAR_LOTE_FORMATO_CERTIFICADOS);
			}
			if (logger.isDebugEnabled()) {logger.debug("Se valido el formato de los certificados correctamente.");}
			
			Map<Long, Boolean> map = armarLoteService.validarNumeroDocValorados(lista);
			
			boolean valido = false;
			if (null != map)
			{
				valido = map.get(bean.getId());
				
				if (valido)
				{

					for (int i=0; i<this.listaConfirmacionImpresion.size(); i++)
					{
						if (null != this.listaConfirmacionImpresion.get(i).getId() && null != bean.getId() && this.listaConfirmacionImpresion.get(i).getId().equals(bean.getId()))
						{
							this.listaConfirmacionImpresion.get(i).setValidatedCSSStyle(Constantes.MOD_IMP_CERT_VALIDAR_BACKGROUND_COLOR_OK);
							break;
						}
					}
					
					/*FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Se validó el Nro. de Certificado correctamente!", null);
					FacesContext.getCurrentInstance().addMessage(null, facesMsg);*/
				}
				else
				{
					if (logger.isDebugEnabled()) {logger.debug("La validacion del Nro. Certificado retorno: FALSE");}
					bean.setNroCertificado(null);
					
					for (int i=0; i<this.listaConfirmacionImpresion.size(); i++)
					{
						if (null != this.listaConfirmacionImpresion.get(i).getId() && null != bean.getId() && this.listaConfirmacionImpresion.get(i).getId().equals(bean.getId()))
						{
							this.listaConfirmacionImpresion.get(i).setNroCertificado(null); 
							this.listaConfirmacionImpresion.get(i).setValidatedCSSStyle(Constantes.MOD_IMP_CERT_VALIDAR_BACKGROUND_COLOR_FAIED);
							break;
						}
					}
					
					throw new SyncconException(ErrorConstants.COD_ERROR_ARMAR_LOTE_NRO_CERT_NO_DISPONIBLE);
				}
			}
		}
		catch (SyncconException e)
		{
			logger.error("SyncconException() - " + ErrorConstants.ERROR_SYNCCON + e.getMessageComplete());
	    	FacesMessage facesMsg = new FacesMessage(e.getSeveridad(), e.getMessage(), null);
	    	FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
	    	logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
	    	FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
	    	FacesContext.getCurrentInstance().addMessage(null, facesMsg);
	    	respuesta = ErrorConstants.MSJ_ERROR;
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return respuesta;
	} //validarNroCertificadoConfirmarImpresion
	
	public void habilitarTxtNroCertificado(ValueChangeEvent event )
	{
		
		//OBTENER EL CODIGO PRINCIPAL Y ACTUALIZAR EL ESTADO SI O REMPLAZADO
		
		if (logger.isDebugEnabled()) {logger.debug("Inicio");}
		
		
		String valueEstado = event.getNewValue().toString();
		String id = event.getNewValue().toString().replace(Constantes.MOD_IMP_IMPRESO_CORRECTAMENTE_SI, "").replace(Constantes.MOD_IMP_IMPRESO_CORRECTAMENTE_REEMPLAZAR, "").replace(" ", "").trim();		
		
		for (int i=0; i<listaConfirmacionImpresion.size(); i++)
		{
			
			if (listaConfirmacionImpresion.get(i).getId().toString().equals(id.trim()))
			{				
				if(valueEstado.contains("SI")){					
					if (logger.isDebugEnabled()) {logger.debug("Opcion IMPRESO CORRECTAMENTE - SI");}					
					listaConfirmacionImpresion.get(i).setImpresoCorrectamente(Constantes.MOD_IMP_IMPRESO_CORRECTAMENTE_SI +" "+ this.listaConfirmacionImpresion.get(i).getId());
					listaConfirmacionImpresion.get(i).setDisabledText(true);
					listaConfirmacionImpresion.get(i).setNroCertificado(listaConfirmacionImpresion.get(i).getTmpNroCertificado());
				}else if(valueEstado.contains("REEMPLAZAR")){		
					if (logger.isDebugEnabled()) {logger.debug("Opcion IMPRESO CORRECTAMENTE - REEMPLAZAR");}					

					listaConfirmacionImpresion.get(i).setNroCertificado(null);					
					listaConfirmacionImpresion.get(i).setImpresoCorrectamente(Constantes.MOD_IMP_IMPRESO_CORRECTAMENTE_REEMPLAZAR +" "+ this.listaConfirmacionImpresion.get(i).getId());
					listaConfirmacionImpresion.get(i).setDisabledText(false);					
				}				
				break;
			}
			logger.info("ESTADO DE CAJA DE TEXTO" + this.listaConfirmacionImpresion.get(i).isDisabledText());					

		}
		if (logger.isDebugEnabled()) {logger.debug("Fin");}
	} //habilitarTxtNroCertificado
	
	
	//=========================================================================
	//=========================== GET / SET METHODS ===========================
	//=========================================================================
	
	public List<SelectItem> getComboListaProductos()
	{
		return comboListaProductos;
	}
	
	public int getRegistrosImpresion() {
		return registrosImpresion;
	}

	public void setRegistrosImpresion(int registrosImpresion) {
		this.registrosImpresion = registrosImpresion;
	}

	public int getRegistrosEntrados() {
		return registrosEntrados ;
	}
	
	public void setRegistrosEntrados(int registrosEntrados) {
		this.registrosEntrados = registrosEntrados ;
	}

	public int getRegistrosSeleccionados() {
		return registrosSeleccionados ;
	}
	
	public void setRegistrosSeleccionados(int registrosSeleccionados) {
		this.registrosSeleccionados = registrosSeleccionados;
	}

	public void setComboListaProductos(List<SelectItem> comboListaProductos)
	{
		this.comboListaProductos = comboListaProductos;
	}
	
	public List<SelectItem> getComboListaUsos()
	{
		return comboListaUsos;
	}
	
	public void setComboListaUsos(List<SelectItem> comboListaUsos)
	{
		this.comboListaUsos = comboListaUsos;
	}
	
	public List<SelectItem> getComboListaDepartamentos()
	{
		return comboListaDepartamentos;
	}
	
	public void setComboListaDepartamentos(List<SelectItem> comboListaDepartamentos)
	{
		this.comboListaDepartamentos = comboListaDepartamentos;
	}
	
	public List<SelectItem> getComboListaProvincias()
	{
		return comboListaProvincias;
	}
	
	public void setComboListaProvincias(List<SelectItem> comboListaProvincias)
	{
		this.comboListaProvincias = comboListaProvincias;
	}
	
	public List<SelectItem> getComboListaDistritos()
	{
		return comboListaDistritos;
	}
	
	public void setComboListaDistritos(List<SelectItem> comboListaDistritos)
	{
		this.comboListaDistritos = comboListaDistritos;
	}
	
	public List<SelectItem> getComboListaRangoHorarios()
	{
		return comboListaRangoHorarios;
	}
	
	public void setComboListaRangoHorarios(List<SelectItem> comboListaRangoHorarios)
	{
		this.comboListaRangoHorarios = comboListaRangoHorarios;
	}
	
	public Long getIdProducto()
	{
		return idProducto;
	}
	
	public void setIdProducto(Long idProducto)
	{
		this.idProducto = idProducto;
	}
	
	public String getNumPlaca()
	{
		return numPlaca;
	}
	
	public void setNumPlaca(String numPlaca)
	{
		this.numPlaca = numPlaca;
	}
	
	public String getIdUsoVehiculo()
	{
		return idUsoVehiculo;
	}
	
	public void setIdUsoVehiculo(String idUsoVehiculo)
	{
		this.idUsoVehiculo = idUsoVehiculo;
	}
	
	public String getIdDepartamento()
	{
		return idDepartamento;
	}
	
	public void setIdDepartamento(String idDepartamento)
	{
		this.idDepartamento = idDepartamento;
	}
	
	public String getIdProvincia()
	{
		return idProvincia;
	}
	
	public void setIdProvincia(String idProvincia)
	{
		this.idProvincia = idProvincia;
	}
	
	public String getIdDistrito()
	{
		return idDistrito;
	}
	
	public void setIdDistrito(String idDistrito)
	{
		this.idDistrito = idDistrito;
	}
	
	public Date getFechaCompraDesde()
	{
		return fechaCompraDesde;
	}
	
	public void setFechaCompraDesde(Date fechaCompraDesde)
	{
		this.fechaCompraDesde = fechaCompraDesde;
	}
	
	public Date getFechaCompraHasta()
	{
		return fechaCompraHasta;
	}
	
	public void setFechaCompraHasta(Date fechaCompraHasta)
	{
		this.fechaCompraHasta = fechaCompraHasta;
	}
	
	public Date getFechaEntregaHasta()
	{
		return fechaEntregaHasta;
	}
	
	public void setFechaEntregaHasta(Date fechaEntregaHasta)
	{
		this.fechaEntregaHasta = fechaEntregaHasta;
	}
	
	public String getIdRangoHorario()
	{
		return idRangoHorario;
	}
	
	public void setIdRangoHorario(String idRangoHorario)
	{
		this.idRangoHorario = idRangoHorario;
	}
	
	public List<ConsultaArmarLote> getListaPolizasParaArmarLote()
	{
		return listaPolizasParaArmarLote;
	}
	
	public void setListaPolizasParaArmarLote(List<ConsultaArmarLote> listaPolizasParaArmarLote)
	{
		this.listaPolizasParaArmarLote = listaPolizasParaArmarLote;
	}
	
	public List<ConsultaArmarLote> getListaLoteImpresionArmado()
	{
		return listaLoteImpresionArmado;
	}
	
	public void setListaLoteImpresionArmado(List<ConsultaArmarLote> listaLoteImpresionArmado)
	{
		this.listaLoteImpresionArmado = listaLoteImpresionArmado;
	}
	
	public LoteImpresion getLoteImpresionObj()
	{
		return loteImpresionObj;
	}
	
	public void setLoteImpresionObj(LoteImpresion loteImpresionObj)
	{
		this.loteImpresionObj = loteImpresionObj;
	}
	
	public List<ConsultaConfirmacionImpresion> getListaConfirmacionImpresion()
	{
		return listaConfirmacionImpresion;
	}
	
	public void setListaConfirmacionImpresion(List<ConsultaConfirmacionImpresion> listaConfirmacionImpresion)
	{
		this.listaConfirmacionImpresion = listaConfirmacionImpresion;
	}
	
	public List<ConsultaConfirmacionImpresion> getListaConfirmacionImpresionAux()
	{
		return listaConfirmacionImpresionAux;
	}
	
	public void setListaConfirmacionImpresionAux(List<ConsultaConfirmacionImpresion> listaConfirmacionImpresionAux)
	{
		this.listaConfirmacionImpresionAux = listaConfirmacionImpresionAux;
	}
	
	public List<ConsultaArmarLote> getVarListaSeleccionados()
	{
		return varListaSeleccionados;
	}
	
	public void setVarListaSeleccionados(List<ConsultaArmarLote> varListaSeleccionados)
	{
		this.varListaSeleccionados = varListaSeleccionados;
	}
	
	public List<ConsultaArmarLote> getVarListaNoEliminados()
	{
		return varListaNoEliminados;
	}
	
	public void setVarListaNoEliminados(List<ConsultaArmarLote> varListaNoEliminados)
	{
		this.varListaNoEliminados = varListaNoEliminados;
	}
	
	public boolean isDisabledBtnSeleccionarTodos()
	{
		return disabledBtnSeleccionarTodos;
	}
	
	public void setDisabledBtnSeleccionarTodos(boolean disabledBtnSeleccionarTodos)
	{
		this.disabledBtnSeleccionarTodos = disabledBtnSeleccionarTodos;
	}
	
	public boolean isDisabledBtnSiguiente()
	{
		return disabledBtnSiguiente;
	}
	
	public void setDisabledBtnSiguiente(boolean disabledBtnSiguiente)
	{
		this.disabledBtnSiguiente = disabledBtnSiguiente;
	}
	
	public boolean isDisabledBtnGenerarLote()
	{
		return disabledBtnGenerarLote;
	}
	
	public void setDisabledBtnGenerarLote(boolean disabledBtnGenerarLote)
	{
		this.disabledBtnGenerarLote = disabledBtnGenerarLote;
	}
	
	public boolean isDisabledComponenteStep2()
	{
		return disabledComponenteStep2;
	}
	
	public void setDisabledComponenteStep2(boolean disabledComponenteStep2)
	{
		this.disabledComponenteStep2 = disabledComponenteStep2;
	}
	
	public boolean isPanelStep1Flag()
	{
		return panelStep1Flag;
	}
	
	public void setPanelStep1Flag(boolean panelStep1Flag)
	{
		this.panelStep1Flag = panelStep1Flag;
	}
	
	public boolean isPanelStep2Flag()
	{
		return panelStep2Flag;
	}
	
	public void setPanelStep2Flag(boolean panelStep2Flag)
	{
		this.panelStep2Flag = panelStep2Flag;
	}
	
	public boolean isPanelStep3Flag()
	{
		return panelStep3Flag;
	}
	
	public void setPanelStep3Flag(boolean panelStep3Flag)
	{
		this.panelStep3Flag = panelStep3Flag;
	}
	
	public String getTituloCabecera()
	{
		return tituloCabecera;
	}
	
	public void setTituloCabecera(String tituloCabecera)
	{
		this.tituloCabecera = tituloCabecera;
	}
	
	public String getTituloSeleccionarTodos()
	{
		return tituloSeleccionarTodos;
	}
	
	public void setTituloSeleccionarTodos(String tituloSeleccionarTodos)
	{
		this.tituloSeleccionarTodos = tituloSeleccionarTodos;
	}
	
	public boolean isSeleccionarTodosFlag()
	{
		return seleccionarTodosFlag;
	}
	
	public void setSeleccionarTodosFlag(boolean seleccionarTodosFlag)
	{
		this.seleccionarTodosFlag = seleccionarTodosFlag;
	}
	
	public String getNroLoteFormateado()
	{
		return nroLoteFormateado;
	}
	
	public void setNroLoteFormateado(String nroLoteFormateado)
	{
		this.nroLoteFormateado = nroLoteFormateado;
	}
	
} //ArmarLoteController
