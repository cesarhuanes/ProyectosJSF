package com.cardif.satelite.suscripcion.controller;

import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.richfaces.model.selection.SimpleSelection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import com.cardif.framework.controller.BaseController;
import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.configuracion.service.ParametroAutomatService;
import com.cardif.satelite.constantes.Constantes;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.CanalProducto;
import com.cardif.satelite.model.Layout;
import com.cardif.satelite.model.ParametroAutomat;
import com.cardif.satelite.model.Producto;
import com.cardif.satelite.model.Socio;
import com.cardif.satelite.suscripcion.service.ConfiguracionNuevoSocioService;
import com.cardif.satelite.util.SateliteConstants;
import com.cardif.satelite.util.SateliteUtil;
import com.cardif.satelite.util.Utilitarios;
import com.sun.org.apache.commons.beanutils.BeanUtils;

@Controller("configuracionNuevoSocioController")
@Scope("request")
public class ConfiguracionNuevoSocioController extends BaseController{

	public static final Logger logger = Logger.getLogger(ConfiguracionNuevoSocioController.class);
	
	
	@Autowired
	ConfiguracionNuevoSocioService configuracionNuevoSocioService = null; 
	
	@Autowired
	private ParametroAutomatService parametroService;
	
	
	// Paso 1 : Pantalla Socio 
	private String ruc;
	private String socio;
	private Socio selectSocio;
	private Socio nuevoSocio;
	private SimpleSelection seleccionSocio;
	private List<Socio> listaSocio;
	private Boolean checkBoxGrupo;
	private Boolean checkBoxGrupoNuevo; 
	private Boolean validaStatus = false;
	
	
	// Paso 2 : Pantalla Producto
	private String labelNombreSocio;
	private String producto;
	private Producto selectProducto;
	private Producto nuevoProducto;
	private SimpleSelection seleccionProducto;
	private List<Producto> listaProducto; 
	private List<SelectItem> listaCanal;
	private List<SelectItem> listaArchivos;
	private String valueIdCanal;
	
	// Paso 3 : Pantalla Layout
	private String nombreColumna;
	private List<SelectItem> listaTipoData;
	private List<SelectItem> listaSeparador;
	private Boolean obligatorio;
	private Layout selectLayout;
	private SimpleSelection seleccionLayout;
	private List<Layout> listaLayout;
	private String tipoData;
	private int posicion;
	private String descripcion;
	private String tipoFormatoFecha;
	private List<SelectItem> listaTipoFormatoFecha;
	
	private String labelNombreCanal;
	
	private Boolean flagBtnEliminar; 
	private Boolean flagBtnModificarSocio;
	private Boolean flagBtnModificarCanal;
	private Boolean requiredSeparador=false;
	private Boolean requiredSeparadorModf = false;
	private Boolean flagNvocheckBoxGrupo = false;
	private Boolean flagModfcheckBoxGrupo = false;
	
	private Boolean flagViewTipoFormatoFecha = false;
	private Boolean flagModuloSucripcion=false;
	private Boolean flagModuloSucripcion1=false;
	private Boolean flagtramadiaria=false;
	private Boolean flagTramamensual=false;
	
	private Boolean tramaDiaria;
	private Boolean tramaMensual;
	private Boolean moduloSuscripcion;
	private Boolean moduloImpresion;
	//checked
	private Boolean nvotramaDiaria;
	private Boolean nvotramaMensual;
	private Boolean nvomoduloSuscripcion;
	private Boolean nvomoduloImpresion;
	private boolean habilitarTXT = false;
	
	private Boolean btnlongitud=false;
	// 
	private String numeroSocios = "";
	String usuarioDB=null;
	
	private String msjValidarRuc = "";
	/*
	 * Navegacion de Paginas
	 */
	private String tituloCabecera;
	
	private String nroPaginaGrilla;
	
	public String getNroPaginaGrilla() {
		return nroPaginaGrilla;
	}

	public void setNroPaginaGrilla(String nroPaginaGrilla) {
		this.nroPaginaGrilla = nroPaginaGrilla;
	}

	@PostConstruct
	@Override
	public String inicio() {
		
		if(logger.isInfoEnabled()){logger.info("Inicio");}
		
		String respuesta = null;

		try{
			
			if(!tieneAcceso())
			{
				if(logger.isDebugEnabled()){logger.debug("No cuenta con los accesos necesarios.");}
				return "accesoDenegado";
			}
		
			flagBtnEliminar = false;
			flagBtnModificarSocio = false;
			flagBtnModificarCanal = false;
			requiredSeparador=false; 
			ruc = null;
			socio = null;
			seleccionSocio = new  SimpleSelection();
			selectSocio = null;
			labelNombreSocio = null;
			producto = null;
			seleccionProducto = new SimpleSelection();
			selectProducto = null;
			nuevoProducto = new Producto();
			nuevoSocio = new Socio();
			this.tipoData="0";
			this.numeroSocios = "0";
			/* Iniciando sin agregar nada adicional a la cabecera */
			this.tituloCabecera = null;			
			
			usuarioDB = SecurityContextHolder.getContext().getAuthentication().getName(); 
			
			listaTipoData = new ArrayList<SelectItem>();
			listaTipoData.add(new SelectItem("0","- Seleccione -"));
			listaTipoData.add(new SelectItem(SateliteConstants.TIPO_DE_DATA1,SateliteConstants.TIPO_DE_DATA1));
			listaTipoData.add(new SelectItem(SateliteConstants.TIPO_DE_DATA2,SateliteConstants.TIPO_DE_DATA2));
			listaTipoData.add(new SelectItem(SateliteConstants.TIPO_DE_DATA3,SateliteConstants.TIPO_DE_DATA3));
			
			listaSeparador = new ArrayList<SelectItem>();
			listaSeparador.add(new SelectItem("","- Seleccione -"));
			listaSeparador.add(new SelectItem(SateliteConstants.TRAMA_SEPARADOR_01, SateliteConstants.TRAMA_SEPARADOR_01));
			listaSeparador.add(new SelectItem(SateliteConstants.TRAMA_SEPARADOR_02, SateliteConstants.TRAMA_SEPARADOR_02));
			listaSeparador.add(new SelectItem(SateliteConstants.TRAMA_SEPARADOR_03, SateliteConstants.TRAMA_SEPARADOR_03));
			listaSeparador.add(new SelectItem(SateliteConstants.TRAMA_SEPARADOR_04, SateliteConstants.TRAMA_SEPARADOR_04));
			
			listaTipoFormatoFecha = new ArrayList<SelectItem>();
			List<ParametroAutomat> listaTipoTramas = parametroService.buscarPorCodParam(Constantes.COD_PARAM_TIPO_FORMATO_FECHA);
			listaTipoFormatoFecha.add(new SelectItem("", "- Seleccione -"));
			for (ParametroAutomat tipoTrama : listaTipoTramas)
			{
				if (logger.isDebugEnabled()) {logger.debug("TIPO_TRAMA ==> COD_VALOR: " + tipoTrama.getCodValor() + " - NOMBRE_VALOR: " + tipoTrama.getNombreValor());}
				listaTipoFormatoFecha.add(new SelectItem(tipoTrama.getCodValor(), tipoTrama.getNombreValor()));
			}

			flagViewTipoFormatoFecha = false;
			
			buscarSocio();

			
		} catch (Exception e) {
	
			logger.error("Exception(" + e.getClass().getName()+ ") - ERROR"+ ErrorConstants.ERROR_SYNCCON,null);
			logger.error("Exception(" + e.getStackTrace() + ")"); 			
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.ERROR_SYNCCON, null);
			FacesContext.getCurrentInstance().addMessage(null,facesMsg);
		}
		
		if(logger.isInfoEnabled()){logger.info("Fin");}
		
		return respuesta;
	}
	
	public String tipoArchivoChangeListener(){
		
		return null;
	}
	
	public void cambiarEstado(){
		if (seleccionLayout!= null) {
			Iterator<Object> it = seleccionLayout.getKeys();
		      while (it.hasNext()) {
		        int key = Integer.parseInt(it.next().toString());
		        	logger.info("key: " + key);
		        	selectLayout = listaLayout.get(key);
		      }
		}else {
	    	logger.info("No selection is set.");
	    }
		
		flagBtnEliminar = true;
	} 
	
	public String siguienteSocioProducto(){
		String respuesta = "";
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		
		try {
			producto = null;
			
			logger.debug("Input [ 1.-"+ BeanUtils.describe(selectSocio) +" ]");			
			logger.debug("busqueda de productos por Socio");
			
			if (selectSocio != null && StringUtils.isNotBlank(selectSocio.getRucSocio())) {
				
				// Obtener Producto por Socios
				
				labelNombreSocio = " "+selectSocio.getNombreSocio();				
				listaProducto = new ArrayList<Producto>(); 
				listaProducto = configuracionNuevoSocioService.listaProductoByIdsocio(Integer.valueOf(selectSocio.getId().toString()),producto );
				
				respuesta = "socioproducto";
				
				// cargar Tipo de Archivo 
				
				listaArchivos = new ArrayList<SelectItem>();
				listaArchivos.add(new SelectItem("","- Seleccione -"));
				listaArchivos.add(new SelectItem(SateliteConstants.TRAMA_TIPO_ARCHIVO_EXCEL, SateliteConstants.TRAMA_TIPO_ARCHIVO_EXCEL));
				listaArchivos.add(new SelectItem(SateliteConstants.TRAMA_TIPO_ARCHIVO_TXT, SateliteConstants.TRAMA_TIPO_ARCHIVO_TXT));
				//listaArchivos.add(new SelectItem(SateliteConstants.TRAMA_TIPO_ARCHIVO_BD, SateliteConstants.TRAMA_TIPO_ARCHIVO_BD));
			 
				// cargar Tipo de Canal
				
				List<CanalProducto> lista = new ArrayList<CanalProducto>();
				listaCanal = new ArrayList<SelectItem>();
				listaCanal.add(new SelectItem("","- Seleccione -"));
				lista = configuracionNuevoSocioService.listaCanal();
				
				for (CanalProducto canalProducto : lista) {
					listaCanal.add(new SelectItem(canalProducto.getId().toString(), canalProducto.getNombreCanal()));
				}
				flagBtnModificarSocio = false;
				flagBtnModificarCanal = false;
				logger.debug("Output [ Registros = " + listaProducto.size()+ " ]");
				
			}else{
				SateliteUtil.mostrarMensaje(FacesMessage.SEVERITY_INFO,"Seleccionar un registro de la bandeja socios.");	
			}
			
				flagModuloSucripcion=true;
				flagModuloSucripcion1=true;
				moduloSuscripcion=true;
				setNvomoduloSuscripcion(true);		
		} 
		catch (Exception e){
					
				logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
				FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		    	respuesta = ErrorConstants.MSJ_ERROR;
		
		} 	
		
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		
		return respuesta;
	}
	
	public void buscarProducto(){
		
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		
		try {
			
			logger.debug("Input [ 1.-" +producto+ " ]");
			
			logger.debug("Busqueda de por nombre productos");
			
			listaProducto = new ArrayList<Producto>(); 
			listaProducto = configuracionNuevoSocioService.listaProductoByIdsocio(Integer.valueOf(selectSocio.getId().toString()),producto );
			limpiarSeleccionRegistrosProductoGrilla();
			
			logger.debug("Output [ Registros = " + listaProducto.size()+ " ]");
			
		} catch (Exception e){
					logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
					FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
					FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			    	
		}
		
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		
	}
	
	public void limpiarSeleccionRegistrosProductoGrilla(){
		//limpiar seleccion de registros
		seleccionProducto = new SimpleSelection();
		selectProducto = new Producto();
		
		//Deshabilitar boton modificar
		flagBtnModificarCanal = false;
		
		//Establecer la primera pagina de la grilla por defecto
		nroPaginaGrilla = Constantes.INDEX_INICIAL_GRILLA;
	}
	
	 public void onSelectionChanged() throws SyncconException {
 
		    if (seleccionSocio != null) {
			
				  msjValidarRuc = "";
		    	
			      Iterator<Object> it = seleccionSocio.getKeys();
			      while (it.hasNext()) {
			    	  
			        int key = Integer.parseInt(it.next().toString());
			        logger.info("key: " + key);
			        
			        selectSocio = listaSocio.get(key);
			        
			        logger.debug("Fecha de Contrato: "+selectSocio.getFechaContratos());
			        logger.debug("Fecha de check: "+selectSocio.getGrupoComercio());
			   
			      }     
			      
			      if(selectSocio.getFechaContratos()==null){
			    	  checkBoxGrupo = false;
			    	  flagModfcheckBoxGrupo = false;
			    	  
			      }else{
			    	  checkBoxGrupo = true;
			    	  flagModfcheckBoxGrupo = true;
			      }
		    }  
		   
			logger.debug("Check Grupo Comercio DESHABILITADO: "+selectSocio.getGrupoComercio());

			flagBtnModificarSocio = true;
			logger.debug("flagBtnModificarSocio: "+flagBtnModificarSocio);
	 }
	
	 public void onSelectionChangedProducto() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		 if (seleccionProducto != null) {
			 Iterator<Object> it = seleccionProducto.getKeys();
		      while (it.hasNext()) {
		        int key = Integer.parseInt(it.next().toString());		        	
		        	logger.info("key: " + key);		        	
		        	selectProducto  = listaProducto.get(key);	
		      }
		      		      
		      valueIdCanal = selectProducto.getIdcanal().toString();
		      tramaDiaria = (selectProducto.getTramaDiaria()==1) ? true:false;
		      tramaMensual = (selectProducto.getTramaMensual()==1)?true:false;
		      moduloImpresion = (selectProducto.getModImpresion()==1)?true:false;
		      moduloSuscripcion = (selectProducto.getModSuscripcion()==1)?true:false;
		      
		      if(selectProducto.getTipoArchivo().equals(SateliteConstants.TRAMA_TIPO_ARCHIVO_TXT)){
		    	  requiredSeparadorModf = true;
		    	  
		    	    listaSeparador =  new ArrayList<SelectItem>();
		    	    listaSeparador.add(new SelectItem("","- Seleccione -"));
					listaSeparador.add(new SelectItem(SateliteConstants.TRAMA_SEPARADOR_01, SateliteConstants.TRAMA_SEPARADOR_01));
					listaSeparador.add(new SelectItem(SateliteConstants.TRAMA_SEPARADOR_02, SateliteConstants.TRAMA_SEPARADOR_02));
					listaSeparador.add(new SelectItem(SateliteConstants.TRAMA_SEPARADOR_03, SateliteConstants.TRAMA_SEPARADOR_03));
					listaSeparador.add(new SelectItem(SateliteConstants.TRAMA_SEPARADOR_04, SateliteConstants.TRAMA_SEPARADOR_04));
		      
		      }else{
		    	  requiredSeparadorModf = false;
		    	  listaSeparador =  new ArrayList<SelectItem>();
		    	  selectProducto.setSeparador("");
		      }
		      
		      
		      
		}else {
			logger.info("No selection is set.");
		}
			flagBtnModificarCanal = true;
		 
	 }
	 
	public String siguienteConfiguracionTrama(){
		String respuesta = "";		
		btnlongitud=false;
		if (logger.isInfoEnabled()) {logger.info("Inicio");}		
		try {	
			if (selectProducto != null && StringUtils.isNotBlank(selectProducto.getNombreProducto())) {
				
				logger.debug("Input [ 1.-"+ selectProducto.getId()+" ]");
				labelNombreCanal = " "+ selectProducto.getNombreProducto();
				logger.debug("Input [ 2 TIPO ARCHIVO.-"+ selectProducto.getTipoArchivo()+" ]");
				
				/*habilitarTXT = (selectProducto.getTipoArchivo().toString().equalsIgnoreCase(SateliteConstants.TRAMA_TIPO_ARCHIVO_TXT))? true:false; 
				
				buscarTramaAll();
				flagBtnEliminar = false;
				
				respuesta = "tramasocio";
				btnLimpiarCamposTrama();
				*/
				// Modificado by Cindy Rodriguez 13/01/17
				if (selectProducto.getTipoArchivo().toString().equalsIgnoreCase(SateliteConstants.TRAMA_TIPO_ARCHIVO_TXT)){
					habilitarTXT = true; 
					btnlongitud =true;
				}else{
					habilitarTXT = false; 
					btnlongitud =false;
				}
				//____________________________________________________________________________
				
				buscarTramaAll();
				flagBtnEliminar = false;	
				respuesta = "tramasocio";
				btnLimpiarCamposTrama();
				
				
			}else{
				SateliteUtil.mostrarMensaje(FacesMessage.SEVERITY_INFO,"Registre un socio canal o seleccione un registro de la grilla.");
			}
		} catch (Exception e){
					logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
					FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
					FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			    	respuesta = ErrorConstants.MSJ_ERROR;
				} 	
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		
		return respuesta;
	}
	
	
	public void buscarTramaAll(){
		listaLayout = new ArrayList<Layout>();
		try {			
			listaLayout = configuracionNuevoSocioService.listaLayoutByIdproducto(Integer.valueOf(selectProducto.getId().toString()));
			logger.debug("Output [ Registros = " + listaLayout.size()+ " ]");
			
			Integer cantotal=listaLayout.size();
			if(cantotal.equals(0)){
				flagBtnEliminar = false;
				
			}else{
				flagBtnEliminar = true;
			}
			
			
			
		}catch (Exception e){
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);	    	
		} 	
	}
	
	
	public String buscarSocio(){
		String respuesta = null;		
		if (logger.isInfoEnabled()) {logger.info("Inicio");}		
		try {			
			logger.debug("Input [ 1.- "+this.ruc+" 2.- "+ this.socio+"]");
			logger.debug("Busqueda de socios por Ruc y Razon social");			
			listaSocio = new ArrayList<Socio>();
			listaSocio = configuracionNuevoSocioService.ListaSocioByRucRazonSocial(this.ruc, this.socio);
			
			this.numeroSocios = String.valueOf(listaSocio.size());
			
			
			limpiarSeleccionRegistrosSocioGrilla();
			
			logger.debug("Output [ Registros = "+ listaSocio.size()+" ]");
			}catch (Exception e){
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
	    	respuesta = ErrorConstants.MSJ_ERROR;
		    } 		
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return respuesta;	
	}

	public String btnnuevoSocio(){
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		int grupo = -1;
		String rpt = "";
		try {
			
			Date fechaContrato = null;
			
			if(StringUtils.isNotBlank(nuevoSocio.getFechaContratos())){
				  DateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.ROOT);
				  fechaContrato  = (Date) formatter.parse(nuevoSocio.getFechaContratos());
			}
			
			logger.debug("Input [ 1.-"+ BeanUtils.describe(nuevoSocio)+" ]");
			
			//Validacion de campos vacios
			if(!StringUtils.isNotBlank(nuevoSocio.getNombreSocio())){
				nuevoSocio.getNombreSocio().trim();
				msjValidarRuc = Constantes.MENSAJE_VALIDACION_CAMPOS_VACIOS;
				return rpt;
			}
			
			if(!StringUtils.isNotBlank(nuevoSocio.getAbreviatura())){
				nuevoSocio.getAbreviatura().trim();
				msjValidarRuc = Constantes.MENSAJE_VALIDACION_CAMPOS_VACIOS;
				return rpt;
			}

			if(!Utilitarios.validateCharacter(nuevoSocio.getNombreSocio())){
				
				if (!Utilitarios.validateCharacter(nuevoSocio.getAbreviatura())){
					
					String primeroDosDigitosRUC = String.valueOf(nuevoSocio.getRucSocio().charAt(0)) + String.valueOf(nuevoSocio.getRucSocio().charAt(1)); 
					
					if(nuevoSocio.getRucSocio().length() < 11){
						msjValidarRuc = "El nro de RUC tiene que contener 11 digitos.";
						return rpt;
					}
			
					validaStatus = configuracionNuevoSocioService.ListaSocioByRuc(nuevoSocio.getRucSocio());
					logger.info("validaStatus:: "+validaStatus);
		
					if(validaStatus==true){
						
						msjValidarRuc = "El RUC ingresado "+ nuevoSocio.getRucSocio() + ", ya existe.";
						
					}else if(validaStatus==false){

						if(primeroDosDigitosRUC.equals(Constantes.DIGITOS_RUC_PERSONA_NATURAL) || primeroDosDigitosRUC.equals(Constantes.DIGITOS_RUC_PERSONA_JURIDICAS)){
							grupo = (checkBoxGrupoNuevo == true)?1:0;	
			
							if (checkBoxGrupoNuevo==true && fechaContrato==null ){
								msjValidarRuc = "Ingresar Fecha de Contrato";
								logger.info("Fecha Contrato :: "+nuevoSocio.getFechaContrato());
							
							}else{
								
								nuevoSocio.setGrupoComercio(grupo);
								nuevoSocio.setFechaCreacion(Calendar.getInstance().getTime());
								nuevoSocio.setUsuarioCreacion(usuarioDB);
								nuevoSocio.setFechaModificacion(Calendar.getInstance().getTime());
								nuevoSocio.setEstado(Integer.parseInt(SateliteConstants.ESTADO_REGISTRO_ACTIVO));
								nuevoSocio.setFechaContrato(fechaContrato);
								
								if(fechaContrato != null){
								logger.info(Utilitarios.convertirFechaACadena(fechaContrato, "DD/MM/YYYY"));
								
									try {
										nuevoSocio.setFechaContratos(Utilitarios.convertirFechaACadena(fechaContrato, "DD/MM/YYYY"));
									} catch (Exception e) {
										if(logger.isDebugEnabled()){
											logger.debug("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());								
										}
									}
								}
								
								
								configuracionNuevoSocioService.nuevoSocio(nuevoSocio);
								buscarSocio();
								
								//Limpiar mensaje de error
								msjValidarRuc = "";
								FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El nuevo socio fue registrado correctamente", null));	
							}
							
						}else {
							msjValidarRuc = "Los digitos iniciales para el RUC, "
									+ " deben comenzar con " + Constantes.DIGITOS_RUC_PERSONA_NATURAL + " para personas naturales y " + Constantes.DIGITOS_RUC_PERSONA_JURIDICAS 
									+ " para personas juridicas.";
						}
					}
			

				}else{
					msjValidarRuc = "Descripción Abreviatura: Carácteres Extraños no permitidos!";
				}

				}else{
					msjValidarRuc = "Descripción Razòn Social: Carácteres Extraños no permitidos!";
				}
			
			logger.debug("Output [ 1.- ]");
			
		} catch (Exception e){
					logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
					FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
					FacesContext.getCurrentInstance().addMessage(null, facesMsg);			    	
		} 	
		if (logger.isInfoEnabled()) {logger.info("Fin");}	
		return rpt;
	}
	
	public String btnModificarSocio(){		
		String rpt = "";
		int grupo = -1;
		if (logger.isInfoEnabled()) {logger.info("btnModificarSocio");}
		
		try {
			if(selectSocio!=null){
			
				msjValidarRuc = "";
				
				//validar vacios
				if(!StringUtils.isNotBlank(selectSocio.getRucSocio())){
					selectSocio.getRucSocio().trim();
					msjValidarRuc = Constantes.MENSAJE_VALIDACION_CAMPOS_VACIOS;
					return rpt;
				}
				
				if(!StringUtils.isNotBlank(selectSocio.getNombreSocio())){
					selectSocio.getNombreSocio().trim();
					msjValidarRuc = Constantes.MENSAJE_VALIDACION_CAMPOS_VACIOS;
					return rpt;
				}
				
				if(!StringUtils.isNotBlank(selectSocio.getAbreviatura())){
					selectSocio.getAbreviatura().trim();
					msjValidarRuc = Constantes.MENSAJE_VALIDACION_CAMPOS_VACIOS;
					return rpt;
				}
				
				//validar RUC - inicio de 20-10
				String primeroDosDigitosRUC = String.valueOf(selectSocio.getRucSocio().charAt(0)) + String.valueOf(selectSocio.getRucSocio().charAt(1)); 
				if(!(primeroDosDigitosRUC.equals(Constantes.DIGITOS_RUC_PERSONA_NATURAL) || primeroDosDigitosRUC.equals(Constantes.DIGITOS_RUC_PERSONA_JURIDICAS))){
					msjValidarRuc = "Los dígitos iniciales para el RUC, "
							+ " deben comenzar con " + Constantes.DIGITOS_RUC_PERSONA_NATURAL + " para personas naturales y " + Constantes.DIGITOS_RUC_PERSONA_JURIDICAS 
							+ " para personas jurídicas.";
					return rpt;
				}
				
				// cuando tiene fecha 
				logger.debug("Input [ 1.-"+BeanUtils.describe(selectSocio)+" ]");
				if (logger.isInfoEnabled()) {logger.info("btnModificarSocio:::::: selectSocio!=null");}
				
				Date fechaContrato = null;
				if(StringUtils.isNotBlank(selectSocio.getFechaContratos())){
					DateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.US);
				    fechaContrato = (Date)formatter.parse(selectSocio.getFechaContratos());
				     
				}
				
				if(!Utilitarios.validateCharacter(selectSocio.getNombreSocio())){
				
					if (!Utilitarios.validateCharacter(selectSocio.getAbreviatura())){
							
						logger.debug("checkBoxGrupo:: "+checkBoxGrupo);
						if (checkBoxGrupo==true && fechaContrato==null){
							msjValidarRuc = "Ingresar Fecha de Contrato.";
							logger.info("Fecha Contrato :: "+selectSocio.getFechaContratos());
						
						}else{
						
							logger.debug("FechaContratos :: "+selectSocio.getFechaContratos());
							logger.debug("fechaContrato:: " +fechaContrato);
							grupo = (checkBoxGrupo) ? 1 : 0;	
							selectSocio.setGrupoComercio(grupo);	
							selectSocio.setFechaModificacion(Calendar.getInstance().getTime());
							selectSocio.setUsuarioModificacion(usuarioDB);
							if(StringUtils.isNotBlank(selectSocio.getFechaContratos())){
								//selectSocio.setFechaContratos(fechaContrato.toString());
								selectSocio.setFechaContrato(fechaContrato);
							}else{
								selectSocio.setFechaContrato(null);
							}

							if(fechaContrato != null){
								logger.info(Utilitarios.convertirFechaACadena(fechaContrato, "dd/MM/yy"));
								try {
									selectSocio.setFechaContratos(Utilitarios.convertirFechaACadena(fechaContrato, "dd/MM/yy"));								
								} catch (Exception e) {
									if(logger.isDebugEnabled()){
										logger.debug("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());								
									}
								}
							}
							else
							{
								selectSocio.setFechaContratos("");
							}
							
							configuracionNuevoSocioService.modificarSocio(selectSocio);
							
							buscarSocio();
							
							msjValidarRuc = "";
							FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El registro fue modificado correctamente", null));	
							logger.debug("Output [ 1.- ]");
						
						}
					
					}else{
						msjValidarRuc = "Descripción Abreviatura: Carácteres Extraños no permitidos!";
					}
				
				}else{
					msjValidarRuc = "Descripción Razón Social: Carácteres Extraños no permitidos!";
				}

			}else{
				SateliteUtil.mostrarMensaje(FacesMessage.SEVERITY_INFO,"Seleccionar un registro, de la bandeja socios.");
			}		

			
		} catch (Exception e){
					logger.error(e.getStackTrace());
					logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
					FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
					FacesContext.getCurrentInstance().addMessage(null, facesMsg);			    	
		} 	
		
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return rpt;
	}
	
	public void limpiarSeleccionRegistrosSocioGrilla(){
		//limpiar seleccion de registros
		seleccionSocio = new SimpleSelection();
		selectSocio = new Socio();
		flagBtnModificarSocio = false;
		
		//Establecer la primera pagina de la grilla por defecto
		nroPaginaGrilla = Constantes.INDEX_INICIAL_GRILLA;
	}
	
	public String btnModificarProducto(){
		String rpt = "";
		String separador="";
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		
		try {
		
			if(selectProducto!=null){
				
				//validar campos vacios
				if(!StringUtils.isNotBlank(selectProducto.getNombreProducto())){
					msjValidarRuc = Constantes.MENSAJE_VALIDACION_CAMPOS_VACIOS;
					return rpt;
				}
				
				separador=selectProducto.getSeparador();
				// Modificado by Cindy Rodriguez 13/01/17
				if(!Utilitarios.validateCharacterDesCanal(selectProducto.getNombreProducto())){
				//____________________________________________________________________________
				if(logger.isInfoEnabled()){ logger.debug("Input [ 1."+ BeanUtils.describe(selectProducto)+" ]");}
				if (selectProducto.getTipoArchivo().equals("TXT") &&  StringUtils.isNotBlank(selectProducto.getSeparador())==false ) {
					msjValidarRuc = "Seleccionar el Separador correspondiente del archivo TXT.";
					
				}else{
					
					if (tramaDiaria==false && tramaMensual==false){
						msjValidarRuc = "Activar la trama correspondiente.";	
					}else{
						
						if(separador.equals("")){
							separador="";
						}else{
							separador=selectProducto.getSeparador();
						}
						
						//Validacion de producto, no se repitan segun tipo (Espaciales,Sucursal,Telemarketing)
						listaProducto = new ArrayList<Producto>(); 
						listaProducto = configuracionNuevoSocioService.listaProductoByIdsocio(Integer.valueOf(selectSocio.getId().toString()),producto );
						boolean existeTipoCanal = false; 
						Integer tmpIdCanalExistente = null;
						for(int i=0; i<listaProducto.size(); i++){
							if(listaProducto.get(i).getIdcanal() == Integer.parseInt(valueIdCanal)){
								existeTipoCanal = true;
								tmpIdCanalExistente = listaProducto.get(i).getIdcanal();
								break;
							}
						}
						
						boolean registrarMismoCanal = false;
						if(existeTipoCanal){
							if(Integer.parseInt(valueIdCanal) == tmpIdCanalExistente){
								registrarMismoCanal = true;
							}
						}
						
						if(registrarMismoCanal || !existeTipoCanal){
							
							selectProducto.setTramaDiaria((tramaDiaria == true)?1:0);
							selectProducto.setTramaMensual((tramaMensual== true)?1:0);
							selectProducto.setModImpresion((moduloImpresion== true)?1:0);
							selectProducto.setModSuscripcion((moduloSuscripcion== true)?1:0);
							selectProducto.setFechaModificacion(Calendar.getInstance().getTime());
							selectProducto.setUsuarioModificacion(usuarioDB);
							selectProducto.setIdcanal(Integer.parseInt(valueIdCanal));
							selectProducto.setSeparador(separador);
							configuracionNuevoSocioService.modificarProducto(selectProducto);
							
							msjValidarRuc="";
							buscarProducto();
							FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El socio canal fue modificado correctamente", null));
						
						}else{
							msjValidarRuc = Constantes.MENSAJE_VALIDACION_CONF_SOCIO_CANAL_REPETIDO;
						}
				 }
				}
				
				}else{
					msjValidarRuc = "Descripción Canal: Carácteres Extraños no permitidos!";				

				}
				
						
			}else{
				SateliteUtil.mostrarMensaje(FacesMessage.SEVERITY_INFO,"Seleccionar un registro de la bandeja socios canal. ");
			}
			
		} catch (Exception e){
					logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
					FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
					FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			    	//respuesta = ErrorConstants.MSJ_ERROR;
				} 	
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return rpt;
	}
	
	public String btnNuevoProducto()
	{
		String rpt = "";
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		try 
		{
			//validar campos vacios
			if(!StringUtils.isNotBlank(nuevoProducto.getNombreProducto())){
				msjValidarRuc = Constantes.MENSAJE_VALIDACION_CAMPOS_VACIOS;
				return rpt;
			}
			// Modificado by Cindy Rodriguez 13/01/17
			if(!Utilitarios.validateCharacterDesCanal(nuevoProducto.getNombreProducto())){
			//____________________________________________________________________________	
			
				logger.info("nuevoProducto.getSeparador"+nuevoProducto.getSeparador());
				logger.info("nuevoProducto.getTipoArchivo"+nuevoProducto.getTipoArchivo());
				
				logger.info("check trama diaria "+nvotramaDiaria);
				logger.info("check trama mensual "+nvotramaMensual);
				
				if (nuevoProducto.getTipoArchivo().equals("TXT") &&  StringUtils.isNotBlank(nuevoProducto.getSeparador())==false ) {
					msjValidarRuc = "Seleccionar el Separador correspondiente del archivo TXT.";				
				}else{
					
					if (nvotramaDiaria==false && nvotramaMensual==false){
						msjValidarRuc = "Activar la trama correspondiente.";	
					}else{
						
						if(logger.isInfoEnabled()){ logger.debug("Input [ 1.-"+ BeanUtils.describe(nuevoProducto)+" ]");	}		
						
						//Validacion de producto, no se repitan segun tipo (Espaciales,Sucursal,Telemarketing)
						listaProducto = new ArrayList<Producto>(); 
						listaProducto = configuracionNuevoSocioService.listaProductoByIdsocio(Integer.valueOf(selectSocio.getId().toString()),producto );
						boolean existeTipoCanal = false; 
						for(int i=0; i<listaProducto.size(); i++){
							if(listaProducto.get(i).getIdcanal() == nuevoProducto.getIdcanal()){
								existeTipoCanal = true;
								break;
							}
						}
						
						if(!existeTipoCanal){
							nuevoProducto.setEstado(Integer.valueOf(SateliteConstants.ESTADO_REGISTRO_ACTIVO));
							nuevoProducto.setIdSocio(selectSocio.getId());
							nuevoProducto.setTramaDiaria((nvotramaDiaria == true)?1:0);
							nuevoProducto.setTramaMensual((nvotramaMensual== true)?1:0);
							nuevoProducto.setModImpresion((nvomoduloImpresion== true)?1:0);
							nuevoProducto.setModSuscripcion((nvomoduloSuscripcion== true)?1:0);
							nuevoProducto.setFechaCreacion(Calendar.getInstance().getTime());
							nuevoProducto.setUsuarioModificacion(usuarioDB);
							configuracionNuevoSocioService.insertProducto(nuevoProducto);
							
							buscarProducto();
							msjValidarRuc = "";
							
							FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El nuevo socio canal fue registrado correctamente", null));
						}else{
							msjValidarRuc = Constantes.MENSAJE_VALIDACION_CONF_SOCIO_CANAL_REPETIDO;
						}
							
					}
				
				}
			}else{
					msjValidarRuc = "Descripción Canal: Carácteres Extraños no permitidos!";				
			
			}
			
		} 
		catch (Exception e){
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			//respuesta = ErrorConstants.MSJ_ERROR;
		} 	
			if (logger.isInfoEnabled()) {logger.info("Fin");}
			return rpt;
	}
	
	public String btnNuevaTrama(ActionEvent actionEvent){
		
		Boolean  status_columna = false, status_posicion= false;
		
		String respuesta="";
		if (logger.isInfoEnabled()) {logger.info("Inicio");}		
		try {
			
			//Valida campos vacios
			if (!StringUtils.isNotBlank(nombreColumna) || !StringUtils.isNotBlank(descripcion)){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Constantes.MENSAJE_VALIDACION_CAMPOS_VACIOS , null));
				return respuesta;
			}

			
			//Valida que la posicion no sea negativa
			if(posicion < 0){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "La Longitud no puede ser negativa." , null));
				return respuesta;
			}
		
			if (StringUtils.isNotEmpty(tipoData) && StringUtils.isNotEmpty(nombreColumna)) {	
				
				for (int i = 0; i < listaLayout.size(); i++) {
					
					if(listaLayout.get(i).getPosicion() == posicion ){	
						
						log.info("ENTIDAD posicion :" + posicion );
						
						status_posicion = false;
										
					}
					
					if(listaLayout.get(i).getColumnaExcel().toString().equalsIgnoreCase(nombreColumna) == true){
						
						log.info("ENTIDAD :" + BeanUtils.describe(listaLayout.get(i)));
						log.info("ENTIDAD nombreColumna "+ nombreColumna);
						
						status_columna = true;
					
					} 	
					
				}
				
				if(status_columna == true ||  status_posicion == true){
					
					if(status_columna){
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "La siguente Columna  "+nombreColumna +"  ya existe" , null));
					}
					
					if(status_posicion){
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "La posicion "  + posicion + " esta duplicado", null));
					}
					
				}else{
					
					logger.debug("getTipoArchivo:"+selectProducto.getTipoArchivo());
			
					
					if(selectProducto.getTipoArchivo().equals("TXT") && posicion==0 ){
						
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Indicar la longitud del campo correspondiente. ", null));
					}else{

						if(tipoData.equals("0")){
								FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Indicar el Tipo de Dato del campo correspondiente. ", null));
						}else{
				
					Layout layout = new Layout();
					layout.setIdcanal(selectProducto.getId());
					layout.setTipodata(tipoData);
					layout.setColumnaExcel(nombreColumna);
					layout.setColumnaTabla("");
					layout.setObligatorio((obligatorio ==true)?1:0);
					layout.setEstado(Integer.valueOf(SateliteConstants.ESTADO_REGISTRO_ACTIVO));
					layout.setUsuarioCreacion(usuarioDB);
					layout.setFechaCreacion(Calendar.getInstance().getTime());
					layout.setPosicion(posicion);
					layout.setDescripcion(descripcion);
					layout.setFormato( (flagViewTipoFormatoFecha) ? tipoFormatoFecha : "");
										
					logger.debug("Input [ 1.-" + BeanUtils.describe(layout)+" ]");
					configuracionNuevoSocioService.insertLayout(layout);
					flagBtnEliminar = true;
					
					//volver a cargar campos grilla
					buscarTramaAll();
					
					
//					//Limpiar campos input
					nombreColumna = "";
					descripcion = "";
					posicion = 0;
					tipoData = null;
					tipoFormatoFecha = null;
					obligatorio = false;
					//Volver a ocultar formato fecha
					flagViewTipoFormatoFecha = false;
					 
					}
					}
//					respuesta = "tramasocio";
				}
				
		}
		
			
			logger.debug("Output [ 1.- ]");
		} catch (Exception e){
			
					logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
					FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
					FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			    	respuesta = ErrorConstants.MSJ_ERROR;
				} 	
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		
		
//		tipoData = null;
//		nombreColumna = null;
//		obligatorio = false;
		
		return respuesta;
	}
	
	public void eliminarLayout(){
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		
		try {
			logger.debug("Input [ 1.- " + selectLayout.getPk()+"]");
			
			//listaLayout = configuracionNuevoSocioService.listaLayoutByIdproducto(Integer.valueOf(selectProducto.getId().toString()));
			//logger.debug("Output [ Registros = " + listaLayout.size()+ " ]");
			
			if(selectLayout != null){
				configuracionNuevoSocioService.eliminarLayout(selectLayout.getPk());
				buscarTramaAll();
				//flagBtnEliminar = true;
				seleccionLayout = null;
			}
			
			logger.debug("Output [ 1.- ]");
		} catch (Exception e){
					logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
					FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
					FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			    	//respuesta = ErrorConstants.MSJ_ERROR;
				} 	
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		
	}
	
	public void tipoArchivochangeListener( ValueChangeEvent event){
		if(logger.isInfoEnabled()){logger.info("Inicio");}

		logger.info(event.getNewValue().toString());
//				flagModuloSucripcion=true;
//				flagModuloSucripcion1=true;
//				moduloSuscripcion=true;
		
		if(event.getNewValue()!=null){
			logger.info(" !null" + event.getNewValue().toString());
			
			
			if(event.getNewValue().toString().equals(SateliteConstants.TRAMA_TIPO_ARCHIVO_TXT)){
				requiredSeparador=true;
				listaSeparador = new ArrayList<SelectItem>();
				listaSeparador.add(new SelectItem("","- Seleccione -"));
				listaSeparador.add(new SelectItem(SateliteConstants.TRAMA_SEPARADOR_01, SateliteConstants.TRAMA_SEPARADOR_01));
				listaSeparador.add(new SelectItem(SateliteConstants.TRAMA_SEPARADOR_02, SateliteConstants.TRAMA_SEPARADOR_02));
				listaSeparador.add(new SelectItem(SateliteConstants.TRAMA_SEPARADOR_03, SateliteConstants.TRAMA_SEPARADOR_03));
				listaSeparador.add(new SelectItem(SateliteConstants.TRAMA_SEPARADOR_04, SateliteConstants.TRAMA_SEPARADOR_04));
			}else {
				requiredSeparador=false;
				listaSeparador = new ArrayList<SelectItem>();
				
			}			
		}		
		logger.info("requiredSeparador :" + requiredSeparador);
		if(logger.isInfoEnabled()){logger.info("Fin");}
	}

	
	public void tipoArchivochangeListenerModf( ValueChangeEvent event){
		
		if(logger.isInfoEnabled()){logger.info("Inicio");}
		logger.info(event.getNewValue().toString());
		if(event.getNewValue()!=null){
			logger.info(" !null" + event.getNewValue().toString());
			if(event.getNewValue().toString().equals(SateliteConstants.TRAMA_TIPO_ARCHIVO_TXT)){
				requiredSeparadorModf=true;
				listaSeparador = new ArrayList<SelectItem>();
				listaSeparador.add(new SelectItem("","- Seleccione -"));
				listaSeparador.add(new SelectItem(SateliteConstants.TRAMA_SEPARADOR_01, SateliteConstants.TRAMA_SEPARADOR_01));
				listaSeparador.add(new SelectItem(SateliteConstants.TRAMA_SEPARADOR_02, SateliteConstants.TRAMA_SEPARADOR_02));
				listaSeparador.add(new SelectItem(SateliteConstants.TRAMA_SEPARADOR_03, SateliteConstants.TRAMA_SEPARADOR_03));
				listaSeparador.add(new SelectItem(SateliteConstants.TRAMA_SEPARADOR_04, SateliteConstants.TRAMA_SEPARADOR_04));
			}else {
				requiredSeparadorModf=false;
				selectProducto.setSeparador("");
				listaSeparador = new ArrayList<SelectItem>();
			}		
		}		
		logger.info("requiredSeparador :" + requiredSeparador);
		if(logger.isInfoEnabled()){logger.info("Fin");}
	}
	
	
	public void canalChangeListenerUpdate(ValueChangeEvent event){
		if(logger.isInfoEnabled()){logger.info("Inicio");}
		
			logger.info(event.getNewValue().toString());		
		
		if(logger.isInfoEnabled()){logger.info("Fin");}
	}
	
	
	public void grupoNvoChangeListener( ValueChangeEvent event){
		
	
		if(logger.isInfoEnabled()){logger.info("Inicio");}
		logger.info(event.getNewValue().toString());
		if(event.getNewValue()!=null){
			logger.info(" !null" + event.getNewValue().toString());
			

			if(event.getNewValue().toString().equals("true")){
				flagNvocheckBoxGrupo = true;
			}else {
				flagNvocheckBoxGrupo = false;
			}		
		}		
		logger.info("requiredSeparador :" + requiredSeparador);
		if(logger.isInfoEnabled()){logger.info("Fin");}
	}
	
	public void grupoModfChangeListener( ValueChangeEvent event){
		
		if(logger.isInfoEnabled()){logger.info("Inicio");}
		logger.info(event.getNewValue().toString());
	
		if(event.getNewValue()!=null){
			
			logger.info(" !null" + event.getNewValue().toString());
			
			if(event.getNewValue().toString().equals("true")){
				
				flagModfcheckBoxGrupo = true;
				logger.info("flagModfcheckBoxGrupo true::"+flagModfcheckBoxGrupo);
		
			}else {
				
				flagModfcheckBoxGrupo = false;
				selectSocio.setFechaContratos("");
				
				logger.info("flagModfcheckBoxGrupo false::"+flagModfcheckBoxGrupo);
			}		
		}		
		
		logger.info("requiredSeparador :" + requiredSeparador);
		if(logger.isInfoEnabled()){logger.info("Fin");}
	}
	
	public String listarFormatoFecha(ValueChangeEvent event){

		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		logger.info(event.getNewValue().toString());
		String respuesta = null;
		
		try
		{
			if (null != event.getNewValue())
			{
				logger.info(" !null" + event.getNewValue().toString());
				if(event.getNewValue().toString().equals(SateliteConstants.TIPO_DE_DATA3)){
					flagViewTipoFormatoFecha=true;				
				}else {
					flagViewTipoFormatoFecha=false;
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
	}
	
	public String regresarProducto(){
		String respuesta ="pasodos";
			flagBtnModificarCanal = true;			
		return respuesta;
	}
	
	public String regresarSocio(){
		String respuesta ="pasouno";
			flagBtnModificarSocio=true;
		return respuesta;
	}
	
	public String btnLimpiarCamposNuevoSocio(){
		String rpt = "";
		
		msjValidarRuc = "";
		nuevoSocio = new Socio();
		checkBoxGrupoNuevo = false;
		flagNvocheckBoxGrupo = false;
		return rpt;
	}
	
	
	public String btnLimpiarCamposNuevoProd(){
		String rpt = "";
		msjValidarRuc = "";
		
		nuevoProducto=null;
		nuevoProducto= new Producto();
		nvotramaDiaria=false;
		nvotramaMensual=false;
		nvomoduloImpresion=false;
		nvomoduloSuscripcion=false;
	
		moduloSuscripcion=false;
		
		requiredSeparador=false;
		
		flagModuloSucripcion=false;
		flagModuloSucripcion1=false;
		moduloSuscripcion=true;
		setNvomoduloSuscripcion(true);	
		
		
		
		return rpt;
	}
	
	
	public String btnLimpiarCamposTrama(){
		String rpt = "";

		nombreColumna = "";
		descripcion = "";
		posicion = 0;
		tipoData = null;
		tipoFormatoFecha = null;
		obligatorio = false;
		//Volver a ocultar formato fecha
		flagViewTipoFormatoFecha = false;
		return rpt;
		
	}
	
	
	public String getRuc() {
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	public String getSocio() {
		return socio;
	}

	public void setSocio(String socio) {
		this.socio = socio;
	}

	
	public List<SelectItem> getListaArchivos() {
		return listaArchivos;
	}

	public void setListaArchivos(List<SelectItem> listaArchivos) {
		this.listaArchivos = listaArchivos;
	}

	public Boolean getTramaDiaria() {
		return tramaDiaria;
	}

	public void setTramaDiaria(Boolean tramaDiaria) {
		this.tramaDiaria = tramaDiaria;
	}

	public Boolean getTramaMensual() {
		return tramaMensual;
	}

	public void setTramaMensual(Boolean tramaMensual) {
		this.tramaMensual = tramaMensual;
	}


	public Boolean getModuloImpresion() {
		return moduloImpresion;
	}

	public void setModuloImpresion(Boolean moduloImpresion) {
		this.moduloImpresion = moduloImpresion;
	}
	public String getNombreColumna() {
		return nombreColumna;
	}

	public void setNombreColumna(String nombreColumna) {
		this.nombreColumna = nombreColumna;
	}

	public List<SelectItem> getListaTipoData() {
		return listaTipoData;
	}

	public void setListaTipoData(List<SelectItem> listaTipoData) {
		this.listaTipoData = listaTipoData;
	}
	
	/**
	 * @return the selectSocio
	 */
	public Socio getSelectSocio() {
		return selectSocio;
	}

	/**
	 * @param selectSocio the selectSocio to set
	 */
	public void setSelectSocio(Socio selectSocio) {
		this.selectSocio = selectSocio;
	}

	/**
	 * @return the listaSocio
	 */
	public List<Socio> getListaSocio() {
		return listaSocio;
	}

	/**
	 * @param listaSocio the listaSocio to set
	 */
	public void setListaSocio(List<Socio> listaSocio) {
		this.listaSocio = listaSocio;
	}

	/**
	 * @return the seleccionSocio
	 */
	public SimpleSelection getSeleccionSocio() {
		return seleccionSocio;
	}

	/**
	 * @param seleccionSocio the seleccionSocio to set
	 */
	public void setSeleccionSocio(SimpleSelection seleccionSocio) {
		this.seleccionSocio = seleccionSocio;
	}

	/**
	 * @return the labelNombreSocio
	 */
	public String getLabelNombreSocio() {
		return labelNombreSocio;
	}

	/**
	 * @param labelNombreSocio the labelNombreSocio to set
	 */
	public void setLabelNombreSocio(String labelNombreSocio) {
		this.labelNombreSocio = labelNombreSocio;
	}

	/**
	 * @return the producto
	 */
	public String getProducto() {
		return producto;
	}

	/**
	 * @param producto the producto to set
	 */
	public void setProducto(String producto) {
		this.producto = producto;
	}

	/**
	 * @return the selectProducto
	 */
	public Producto getSelectProducto() {
		return selectProducto;
	}

	/**
	 * @param selectProducto the selectProducto to set
	 */
	public void setSelectProducto(Producto selectProducto) {
		this.selectProducto = selectProducto;
	}

	/**
	 * @return the seleccionProducto
	 */
	public SimpleSelection getSeleccionProducto() {
		return seleccionProducto;
	}

	/**
	 * @param seleccionProducto the seleccionProducto to set
	 */
	public void setSeleccionProducto(SimpleSelection seleccionProducto) {
		this.seleccionProducto = seleccionProducto;
	}

	/**
	 * @return the listaProducto
	 */
	public List<Producto> getListaProducto() {
		return listaProducto;
	}

	/**
	 * @param listaProducto the listaProducto to set
	 */
	public void setListaProducto(List<Producto> listaProducto) {
		this.listaProducto = listaProducto;
	}

	/**
	 * @return the seleccionLayout
	 */
	public SimpleSelection getSeleccionLayout() {
		return seleccionLayout;
	}

	/**
	 * @param seleccionLayout the seleccionLayout to set
	 */
	public void setSeleccionLayout(SimpleSelection seleccionLayout) {
		this.seleccionLayout = seleccionLayout;
	}

	/**
	 * @return the listaLayout
	 */
	public List<Layout> getListaLayout() {
		return listaLayout;
	}

	/**
	 * @param listaLayout the listaLayout to set
	 */
	public void setListaLayout(List<Layout> listaLayout) {
		this.listaLayout = listaLayout;
	}


	/**
	 * @return the checkBoxGrupo
	 */
	public Boolean getCheckBoxGrupo() {
		return checkBoxGrupo;
	}

	/**
	 * @param checkBoxGrupo the checkBoxGrupo to set
	 */
	public void setCheckBoxGrupo(Boolean checkBoxGrupo) {
		this.checkBoxGrupo = checkBoxGrupo;
	}

	/**
	 * @return the nuevoSocio
	 */
	public Socio getNuevoSocio() {
		return nuevoSocio;
	}

	/**
	 * @param nuevoSocio the nuevoSocio to set
	 */
	public void setNuevoSocio(Socio nuevoSocio) {
		this.nuevoSocio = nuevoSocio;
	}

	/**
	 * @return the checkBoxGrupoNuevo
	 */
	public Boolean getCheckBoxGrupoNuevo() {
		return checkBoxGrupoNuevo;
	}

	/**
	 * @param checkBoxGrupoNuevo the checkBoxGrupoNuevo to set
	 */
	public void setCheckBoxGrupoNuevo(Boolean checkBoxGrupoNuevo) {
		this.checkBoxGrupoNuevo = checkBoxGrupoNuevo;
	}

	/**
	 * @return the nuevoProducto
	 */
	public Producto getNuevoProducto() {
		return nuevoProducto;
	}

	/**
	 * @param nuevoProducto the nuevoProducto to set
	 */
	public void setNuevoProducto(Producto nuevoProducto) {
		this.nuevoProducto = nuevoProducto;
	}

	/**
	 * @return the listaCanal
	 */
	public List<SelectItem> getListaCanal() {
		return listaCanal;
	}

	/**
	 * @param listaCanal the listaCanal to set
	 */
	public void setListaCanal(List<SelectItem> listaCanal) {
		this.listaCanal = listaCanal;
	}

	/**
	 * @return the moduloSuscripcion
	 */
	public Boolean getModuloSuscripcion() {
		return moduloSuscripcion;
	}

	/**
	 * @param moduloSuscripcion the moduloSuscripcion to set
	 */
	public void setModuloSuscripcion(Boolean moduloSuscripcion) {
		this.moduloSuscripcion = moduloSuscripcion;
	}

	/**
	 * @return the nvotramaDiaria
	 */
	public Boolean getNvotramaDiaria() {
		return nvotramaDiaria;
	}

	/**
	 * @param nvotramaDiaria the nvotramaDiaria to set
	 */
	public void setNvotramaDiaria(Boolean nvotramaDiaria) {
		this.nvotramaDiaria = nvotramaDiaria;
	}

	/**
	 * @return the nvotramaMensual
	 */
	public Boolean getNvotramaMensual() {
		return nvotramaMensual;
	}

	/**
	 * @param nvotramaMensual the nvotramaMensual to set
	 */
	public void setNvotramaMensual(Boolean nvotramaMensual) {
		this.nvotramaMensual = nvotramaMensual;
	}

	/**
	 * @return the nvomoduloSuscripcion
	 */
	public Boolean getNvomoduloSuscripcion() {
		return nvomoduloSuscripcion;
	}

	/**
	 * @param nvomoduloSuscripcion the nvomoduloSuscripcion to set
	 */
	public void setNvomoduloSuscripcion(Boolean nvomoduloSuscripcion) {
		this.nvomoduloSuscripcion = nvomoduloSuscripcion;
	}

	/**
	 * @return the nvomoduloImpresion
	 */
	public Boolean getNvomoduloImpresion() {
		return nvomoduloImpresion;
	}

	/**
	 * @param nvomoduloImpresion the nvomoduloImpresion to set
	 */
	public void setNvomoduloImpresion(Boolean nvomoduloImpresion) {
		this.nvomoduloImpresion = nvomoduloImpresion;
	}

	
	
	/**
	 * @return the tipoData
	 */
	public String getTipoData() {
		return tipoData;
	}

	/**
	 * @param tipoData the tipoData to set
	 */
	public void setTipoData(String tipoData) {
		this.tipoData = tipoData;
	}

	/**
	 * @return the obligatorio
	 */
	public Boolean getObligatorio() {
		return obligatorio;
	}

	/**
	 * @param obligatorio the obligatorio to set
	 */
	public void setObligatorio(Boolean obligatorio) {
		this.obligatorio = obligatorio;
	}

	/**
	 * @return the flagBtnEliminar
	 */
	public Boolean getFlagBtnEliminar() {
		return flagBtnEliminar;
	}

	/**
	 * @param flagBtnEliminar the flagBtnEliminar to set
	 */
	public void setFlagBtnEliminar(Boolean flagBtnEliminar) {
		this.flagBtnEliminar = flagBtnEliminar;
	}

	/**
	 * @return the selectLayout
	 */
	public Layout getSelectLayout() {
		return selectLayout;
	}

	/**
	 * @param selectLayout the selectLayout to set
	 */
	public void setSelectLayout(Layout selectLayout) {
		this.selectLayout = selectLayout;
	}

	/**
	 * @return the flagBtnModificarSocio
	 */
	public Boolean getFlagBtnModificarSocio() {
		return flagBtnModificarSocio;
	}

	/**
	 * @param flagBtnModificarSocio the flagBtnModificarSocio to set
	 */
	public void setFlagBtnModificarSocio(Boolean flagBtnModificarSocio) {
		this.flagBtnModificarSocio = flagBtnModificarSocio;
	}

	/**
	 * @return the flagBtnModificarCanal
	 */
	public Boolean getFlagBtnModificarCanal() {
		return flagBtnModificarCanal;
	}

	/**
	 * @param flagBtnModificarCanal the flagBtnModificarCanal to set
	 */
	public void setFlagBtnModificarCanal(Boolean flagBtnModificarCanal) {
		this.flagBtnModificarCanal = flagBtnModificarCanal;
	}

	/**
	 * @return the labelNombreCanal
	 */
	public String getLabelNombreCanal() {
		return labelNombreCanal;
	}

	/**
	 * @param labelNombreCanal the labelNombreCanal to set
	 */
	public void setLabelNombreCanal(String labelNombreCanal) {
		this.labelNombreCanal = labelNombreCanal;
	}

	/**
	 * @return the requiredSeparador
	 */
	public Boolean getRequiredSeparador() {
		return requiredSeparador;
	}

	/**
	 * @param requiredSeparador the requiredSeparador to set
	 */
	public void setRequiredSeparador(Boolean requiredSeparador) {
		this.requiredSeparador = requiredSeparador;
	}

	/**
	 * @return the requiredSeparadorModf
	 */
	public Boolean getRequiredSeparadorModf() {
		return requiredSeparadorModf;
	}

	/**
	 * @param requiredSeparadorModf the requiredSeparadorModf to set
	 */
	public void setRequiredSeparadorModf(Boolean requiredSeparadorModf) {
		this.requiredSeparadorModf = requiredSeparadorModf;
	}

	/**
	 * @return the flagNvocheckBoxGrupo
	 */
	public Boolean getFlagNvocheckBoxGrupo() {
		return flagNvocheckBoxGrupo;
	}

	/**
	 * @param flagNvocheckBoxGrupo the flagNvocheckBoxGrupo to set
	 */
	public void setFlagNvocheckBoxGrupo(Boolean flagNvocheckBoxGrupo) {
		this.flagNvocheckBoxGrupo = flagNvocheckBoxGrupo;
	}

	/**
	 * @return the flagModfcheckBoxGrupo
	 */
	public Boolean getFlagModfcheckBoxGrupo() {
		return flagModfcheckBoxGrupo;
	}

	/**
	 * @param flagModfcheckBoxGrupo the flagModfcheckBoxGrupo to set
	 */
	public void setFlagModfcheckBoxGrupo(Boolean flagModfcheckBoxGrupo) {
		this.flagModfcheckBoxGrupo = flagModfcheckBoxGrupo;
	}

	/**
	 * @return the posicion
	 */
	public int getPosicion() {
		return posicion;
	}

	/**
	 * @param posicion the posicion to set
	 */
	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}

	/**
	 * @return the habilitarTXT
	 */
	public boolean isHabilitarTXT() {
		return habilitarTXT;
	}

	/**
	 * @param habilitarTXT the habilitarTXT to set
	 */
	public void setHabilitarTXT(boolean habilitarTXT) {
		this.habilitarTXT = habilitarTXT;
	}

	/**
	 * @return the listaSeparador
	 */
	public List<SelectItem> getListaSeparador() {
		return listaSeparador;
	}

	/**
	 * @param listaSeparador the listaSeparador to set
	 */
	public void setListaSeparador(List<SelectItem> listaSeparador) {
		this.listaSeparador = listaSeparador;
	}

	/**
	 * @return the valueIdCanal
	 */
	public String getValueIdCanal() {
		return valueIdCanal;
	}

	/**
	 * @param valueIdCanal the valueIdCanal to set
	 */
	public void setValueIdCanal(String valueIdCanal) {
		this.valueIdCanal = valueIdCanal;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String getTipoFormatoFecha() {
		return tipoFormatoFecha;
	}

	public void setTipoFormatoFecha(String tipoFormatoFecha) {
		this.tipoFormatoFecha = tipoFormatoFecha;
	}

	public List<SelectItem> getListaTipoFormatoFecha() {
		return listaTipoFormatoFecha;
	}

	public void setListaTipoFormatoFecha(List<SelectItem> listaTipoFormatoFecha) {
		this.listaTipoFormatoFecha = listaTipoFormatoFecha;
	}

	public Boolean getFlagViewTipoFormatoFecha() {
		return flagViewTipoFormatoFecha;
	}

	public void setFlagViewTipoFormatoFecha(Boolean flagViewTipoFormatoFecha) {
		this.flagViewTipoFormatoFecha = flagViewTipoFormatoFecha;
	}
	

	public String getTituloCabecera() {
		return tituloCabecera;
	}

	public void setTituloCabecera(String tituloCabecera) {
		this.tituloCabecera = tituloCabecera;
	}	
	

	public String getMsjValidarRuc() {
		return msjValidarRuc;
	}

	public void setMsjValidarRuc(String msjValidarRuc) {
		this.msjValidarRuc = msjValidarRuc;
	}

	public Boolean getValidaStatus() {
		return validaStatus;
	}

	public void setValidaStatus(Boolean validaStatus) {
		this.validaStatus = validaStatus;
	}

	public Boolean getFlagModuloSucripcion() {
		return flagModuloSucripcion;
	}

	public void setFlagModuloSucripcion(Boolean flagModuloSucripcion) {
		this.flagModuloSucripcion = flagModuloSucripcion;
	}

	public Boolean getFlagtramadiaria() {
		return flagtramadiaria;
	}

	public void setFlagtramadiaria(Boolean flagtramadiaria) {
		this.flagtramadiaria = flagtramadiaria;
	}

	public Boolean getFlagTramamensual() {
		return flagTramamensual;
	}

	public void setFlagTramamensual(Boolean flagTramamensual) {
		this.flagTramamensual = flagTramamensual;
	}

	public Boolean getFlagModuloSucripcion1() {
		return flagModuloSucripcion1;
	}

	public void setFlagModuloSucripcion1(Boolean flagModuloSucripcion1) {
		this.flagModuloSucripcion1 = flagModuloSucripcion1;
	}

	public String getNumeroSocios() {
		return numeroSocios;
	}

	public void setNumeroSocios(String numeroSocios) {
		this.numeroSocios = numeroSocios;
	}

	public Boolean getBtnlongitud() {
		return btnlongitud;
	}

	public void setBtnlongitud(Boolean btnlongitud) {
		this.btnlongitud = btnlongitud;
	}
	
	
	
	
	
}
