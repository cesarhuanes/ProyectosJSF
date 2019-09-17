package com.cardif.satelite.suscripcion.controller;

import static com.cardif.satelite.constantes.ErrorConstants.ERROR_SYNCCON;
import static com.cardif.satelite.constantes.ErrorConstants.MSJ_ERROR;
import static com.cardif.satelite.constantes.ErrorConstants.MSJ_ERROR_GENERAL;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;
import org.richfaces.model.selection.SimpleSelection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import com.cardif.framework.controller.BaseController;
import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.acsele.service.ProductService;
import com.cardif.satelite.configuracion.service.CanalProductoService;
import com.cardif.satelite.configuracion.service.ParametroAutomatService;
import com.cardif.satelite.configuracion.service.ProductoService;
import com.cardif.satelite.constantes.Constantes;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.CanalProducto;
import com.cardif.satelite.model.ControlPaquete;
import com.cardif.satelite.model.Layout;
import com.cardif.satelite.model.ParametroAutomat;
import com.cardif.satelite.model.Producto;
import com.cardif.satelite.model.acsele.Product;
import com.cardif.satelite.model.satelite.ConciCabTramaDiaria;
import com.cardif.satelite.model.satelite.ConciCabTramaMensual;
import com.cardif.satelite.model.satelite.ConciTramaDiaria;
import com.cardif.satelite.model.satelite.ConciTramaMensual;
import com.cardif.satelite.suscripcion.bean.ConfLayout;
import com.cardif.satelite.suscripcion.bean.SocioProductoBean;
import com.cardif.satelite.suscripcion.bean.TramaDiariaBean;
import com.cardif.satelite.suscripcion.bean.TramaEjecucion;
import com.cardif.satelite.suscripcion.service.CargTramaDiariaSoatService;
import com.cardif.satelite.suscripcion.service.CargaTramaMensualService;
import com.cardif.satelite.suscripcion.service.ProcesosETLsService;
import com.cardif.satelite.telemarketing.service.BaseCabeceraService;
import com.cardif.satelite.util.SateliteConstants;
import com.cardif.satelite.util.SateliteUtil;
import com.cardif.satelite.util.Utilitarios;

/**
 * @author torresgu2
 * @create 2014.07.11
 * @version 0.1
 * @description Carga de la Trama Mensual para Conciliacion SOAT
 */
@Controller("cargaTramaMensualController")
@Scope("request")
public class CargaTramaMensualController extends BaseController {

  public static final Logger log = Logger.getLogger(CargaTramaMensualController.class);

  @Autowired
  private CargaTramaMensualService cargaTramaMensualService = null;
  
  @Autowired
  private CargTramaDiariaSoatService cargTramaDiariaSoatService = null;
  
  @Autowired
  private ProductService productService = null;
	@Autowired
	private ProductoService productoService = null;
  @Autowired
  private ParametroAutomatService parametroService = null;
  
  @Autowired
  private BaseCabeceraService baseCabeceraService = null;
  
  @Autowired
  private CanalProductoService canalProductoService = null;
  
	@Autowired
	private ProcesosETLsService procesosETLsService; 

	  private List<SelectItem> listaExcel;
	  private List<SelectItem> listaTabla;
	  private List<SelectItem> selectListaTabla;
	  private List<ConfLayout> listaLayout;
	  private String excel;
	  private String tabla;
	  private String selectTabla ="";
	  private String value;
	  private String socio;
	  
	  private List<SelectItem> tipoCanalItems;
	  private List<SelectItem> tipoSocioItems;
	  private List<ConciCabTramaMensual> listaArchivos;
	  private List<ConciCabTramaMensual> listaColumnasTabla;
	  private SimpleSelection selection;
	  private Product product;
	  private ConciCabTramaDiaria conciCabTramaDiaria;
	  private ConciTramaDiaria conciTramaDiaria;
	  private boolean upload;
	  private String nombreArchivo;
	  private File archivoFinal;
	  private String codProducto;
	  private String producto;
	  private Long nroRegistros;
	  private boolean flagBtnEliminar = false;
	  private boolean flagBtnSiguiente = false;
	  private Date fecCarga;
	  private String codCanal;
	  private Integer correlativoSerie;
	  private Integer codSocio;
	  private List<SocioProductoBean> listaSocio;	 
	  private ConciCabTramaMensual cabTramaMensual;
	  private ConciTramaMensual tramaMensual;
	  private List<ConciCabTramaMensual> listaTramas;
	  private List<SelectItem> productoItems;
	  private List<Layout> listaObservada;
      private String NuevoURL_paquete;


	  private TramaEjecucion tramaEjecucion;
	  private boolean habilitarCorrelativo = false;
	  
	  @PostConstruct
	  @Override
	  public String inicio() {
	    
	    if(log.isInfoEnabled()){log.info("Inicio");}
	    
	    String respuesta = null;
	  
	    try {
	    	
	    	  if (!tieneAcceso()) {
	    	      log.info("No cuenta con los accesos necesarios");
	    	      return "accesoDenegado";
	    	    }
	    	
	    	
	      productoItems = new ArrayList<SelectItem>();
	      flagBtnEliminar = false;
	      nombreArchivo = new String();
	      tipoCanalItems = new  ArrayList<SelectItem>();
		  tipoSocioItems = new  ArrayList<SelectItem>();
		  listaArchivos = new  ArrayList<ConciCabTramaMensual>();
		  listaColumnasTabla = new  ArrayList<ConciCabTramaMensual>();
		  nombreArchivo = null;
		  codCanal = null;
		  codSocio = null;
		  fecCarga = null;
		  correlativoSerie = null;
		  /*********************** Carga de Combos **************************************************************/
			 tipoCanalItems = new ArrayList<SelectItem>();
			 tipoCanalItems.add(new SelectItem("", "- Seleccione -"));					 
			List<CanalProducto> listaCanal=null;
			listaCanal = canalProductoService.buscarPorEstado(Constantes.CANAL_PROD_ESTADO_ACTIVO);  //cargTramaDiariaSoatService.listaCanalesProducto(event.getNewValue().toString());
			for (CanalProducto canalProducto : listaCanal) {
					tipoCanalItems.add(new SelectItem(canalProducto.getId(),canalProducto.getNombreCanal()));
			}	
	      
					
	    } catch (Exception e) {
	      log.error(e.getMessage(), e);
	      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
	      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
	      respuesta = ErrorConstants.MSJ_ERROR;
	    }
	    if(log.isInfoEnabled()){log.info("Fin");}
	    return respuesta;
	  }
	  
		public String cambiarEstado() {	
			 if(log.isInfoEnabled()){log.info("Inicio");}
			   			String respuesta = "";
			try {
				flagBtnEliminar = true;
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			 if(log.isInfoEnabled()){log.info("Fin");}
			   	return respuesta;
		}

		public void socioChangeListener(ValueChangeEvent event) {
			try {
				
				int value = -1;

				if(log.isInfoEnabled()){log.info("Inicio");}
				 if (event.getNewValue() != null) {
					if(log.isDebugEnabled()){
					log.debug("Input [ idproducto" +event.getNewValue().toString() +"]" );
					}
					Producto producto = null;
					
					producto = cargTramaDiariaSoatService.selectProductoByID(Integer.valueOf(event.getNewValue().toString()));
					
					if(producto.getTramaDiaria() == Constantes.PRODUCTO_MOD_TRAMA_DIARIA_NO && producto.getTramaMensual() == Constantes.PRODUCTO_MOD_TRAMA_MENSUAL_SI ){
						habilitarCorrelativo = false;						
					}else{						
						habilitarCorrelativo = true;					
					}
					if(log.isDebugEnabled()){
					log.debug("habilitarCorrelativo :" + habilitarCorrelativo);
					}
					value = cargTramaDiariaSoatService.selectCorrelativo(event.getNewValue().toString());
					if(value==-1){
						correlativoSerie  = null;
					}else{
						correlativoSerie = value;
					}
				 }
				 if(log.isInfoEnabled()){log.info("Final");}			 
			} catch (NumberFormatException e1) {
				
				log.error(e1.getMessage(), e1);
				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
				FacesContext.getCurrentInstance().addMessage(null, facesMsg);			
			} catch (SyncconException e1) {
				log.error(e1.getMessage(), e1);
				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
				FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			}
		}
		
		
	  public String cargarTramaDiaria(){
		  
		  if(log.isInfoEnabled()){log.info("Inicio");}
		  
			String respuesta = null;
			String cadena = archivoFinal + "";
			int resultado = 0;
			int resultadoTXT= 0;
			Boolean status = false;
					
			try {
				
				NuevoURL_paquete = "";

				
				if(!StringUtils.isEmpty(nombreArchivo)){
					
					List<ParametroAutomat> listaTipoTramas = parametroService.buscarPorCodParam(Constantes.SUSCRIPCION_RUTA_SERVIDOR_SSIS);
				    String rutaTemp ="";
				    String rutaPaquete ="";
				    Long date = Calendar.getInstance().getTimeInMillis();
				    
				    
				      if (validarHojaArchivo(archivoFinal,cadena.toUpperCase())) {
				    	  
				      
					for (ParametroAutomat parametroAutomat : listaTipoTramas) {
						if(parametroAutomat.getNumOrden()==1){
							rutaTemp = parametroAutomat.getNombreValor() + Constantes.SUSCRIPCION_RUTA_SERVIDOR_SSIS_MENSUAL  + "TRAMA_MENSUAL_" + date +"_"+ nombreArchivo;
						}else if(parametroAutomat.getNumOrden()==2){

							rutaPaquete = parametroAutomat.getNombreValor() + Constantes.SUSCRIPCION_RUTA_SERVIDOR_SSIS_MENSUAL  + "TRAMA_MENSUAL_" + date +"_"+ nombreArchivo;
						}
					}
					
					NuevoURL_paquete = rutaPaquete;
					
					SocioProductoBean selecProducto = new SocioProductoBean();	
					
					for (SocioProductoBean producto : this.listaSocio) {
						if(log.isDebugEnabled()){log.info("select producto" + BeanUtils.describe(producto));}
						if(log.isDebugEnabled()){log.info("this.codSocio.toString()" + this.codSocio.toString());}

						if(producto.getIdProducto() == Long.valueOf(this.codSocio.toString())){
							selecProducto=producto;
						}
					}
					
					codProducto = selecProducto.getIdProducto().toString();
					
					if(log.isDebugEnabled()){
					log.info("Input [1.-"+selecProducto.getTipoArchivo()+"2.-"+this.codProducto+", 3.- "+cadena +" ]");
					}
					
					if(selecProducto.getTipoArchivo().trim().toUpperCase().equals(SateliteConstants.TRAMA_TIPO_ARCHIVO_TXT)){
						

						resultadoTXT=cadena.toUpperCase().indexOf("TXT");
						log.info("resultado: "+resultado);
						
						if(resultadoTXT != -1){
							
							if(log.isDebugEnabled()){log.info("FUNCION LEER TXT");}
							
							status = ingresarCabeceraTXT(codProducto,selecProducto.getSeparador());
							
						}else{
							throw new SyncconException(ErrorConstants.COD_ERROR_PERIODO_TIPO_ARCHIVO_TXT,FacesMessage.SEVERITY_INFO);
						}
					}
					if(selecProducto.getTipoArchivo().trim().toUpperCase().equals(SateliteConstants.TRAMA_TIPO_ARCHIVO_EXCEL)){
						
						log.info("TIPO DE ARCHIVO : "+cadena);
						
						if(cadena.toUpperCase().contains("XLSX")==true){
							
							status = ingresarCabecera(codProducto);		
							
						}else if(cadena.toUpperCase().contains("XLS")==true){
							
							status = ingresarCabecera(codProducto);		
						
						}else{
							throw new SyncconException(ErrorConstants.COD_ERROR_PERIODO_TIPO_ARCHIVO_XLSX,FacesMessage.SEVERITY_INFO);
							
						}	
						
					}
					
					
					// copia al servidor de base de datos
					Utilitarios.copyFiles(new File(archivoFinal.getPath()),new File(rutaTemp));
					
					
				      }else{
				        	SateliteUtil.mostrarMensaje("Hoja de archivo incorrecta, modificar a 'hoja1'");
				        }
					
				}else {
					SateliteUtil.mostrarMensaje(SateliteConstants.MENSAJE_CARGADO_ARCHIVO);
				}
							
				if( status == true ){				
					respuesta = SateliteConstants.SIGUIENTE_TRAMA_MENSUAL;	
				}
				
			} catch (SyncconException ex) {
			      log.error(ErrorConstants.ERROR_SYNCCON + ex.getMessageComplete());
			      FacesMessage facesMsg = new FacesMessage(ex.getSeveridad(), ex.getMessage(), null);
			      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			    } 
			
			catch (Exception e) {
				log.error(e.getMessage(), e);
				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
				FacesContext.getCurrentInstance().addMessage(null, facesMsg);
				respuesta = MSJ_ERROR;
			}	
					
			if(log.isInfoEnabled()){log.info("Fin");}
			return respuesta;
	 }
	  
	  //validacion de nombre de hoja 12-01-17
	  @SuppressWarnings("unused")
	private Boolean validarHojaArchivo(File file,String tipo){
			Boolean sw = false;
			
			try {
				String sheetName ="";
				if(tipo.toUpperCase().contains("XLSX")==true){
					XSSFSheet  xssfReader = null;
					XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream (file));
					int numSheets = wb.getNumberOfSheets();
					
					XSSFSheet sheet = wb.getSheetAt(0);// sheetIndex);
			         sheetName = wb.getSheetName(0);
					
				}else if(tipo.toUpperCase().contains("XLS")==true){
					 HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream (file));
					 int numSheets = wb.getNumberOfSheets();
					 HSSFSheet sheet = wb.getSheetAt(0);// sheetIndex);
				         sheetName = wb.getSheetName(0);
					 
				}else if(tipo.toUpperCase().contains("TXT")==true){
					sw = true;
				}
				
				if(StringUtils.isNotBlank(sheetName)){
					if(sheetName.equals("hoja1")){
						sw = true;
					}else{
						sw=false;
					}	
				}
				
				
				
				log.info("Nombre de la Hoja: "+sheetName);
			    log.info("tipo.toUpperCase()" + tipo.toUpperCase());
			    log.info("status validacion" + sw);
				
			} catch (Exception e) {
				log.info(e.getStackTrace());
			}

		   
			
			return sw;
			
		}
	  
	  
	  private Boolean ingresarCabecera(String idproducto) {
		  
			if(log.isInfoEnabled()){ log.info("Inicio");}
			
			Boolean status = false;
			String usuario = SecurityContextHolder.getContext().getAuthentication().getName();
			Date fechaActual = new Date(System.currentTimeMillis());		
			listaObservada = new ArrayList<Layout>();
			
			try {
				
				selectListaTabla = new ArrayList<SelectItem>();
				buscarListaLayout();	
				
				if(log.isDebugEnabled()){
					log.info("Nombre Archivo: " + nombreArchivo);
					
				}
				
				if (StringUtils.isBlank(nombreArchivo)) {
					throw new SyncconException(ErrorConstants.COD_ERROR_ARCHIVO, FacesMessage.SEVERITY_INFO);
				}
				
				if(!StringUtils.isNotEmpty(codSocio.toString())){
					SateliteUtil.mostrarMensaje("Seleccionar un producto");
					return status=false;
				}
				
				nroRegistros = Long.valueOf(cargTramaDiariaSoatService.numeroRegistrosExcel(archivoFinal));
				
				listaObservada = cargTramaDiariaSoatService.validarExcelByBaseDatos(archivoFinal, baseCabeceraService.obtenerCabeceraExcelAll(codSocio)); 
				
				if(log.isDebugEnabled()){
				log.info("listaObservada.size()" + listaObservada.size());
				}
			
					// dentro de esta lista se excluyen las cabeceras que se repiten en lista base de datos y archivo excel
					for (Layout items : listaObservada) {
						for (int x =0; x < listaLayout.size();x++) {
							if(items.getColumnaExcel().equalsIgnoreCase(listaLayout.get(x).getValueExcel())){
								listaLayout.get(x).setTipodata("true");
							}
						}
						listaExcel.add(new SelectItem(items.getColumnaExcel()+SateliteConstants.FlAG_PREFIJO_EXCEL,items.getColumnaExcel()));
					}
	
					conciCabTramaDiaria = new ConciCabTramaDiaria();
					conciCabTramaDiaria.setProducto(idproducto);
					conciCabTramaDiaria.setTipoCargo(SateliteConstants.TIPO_DE_CARGA);
					conciCabTramaDiaria.setNomArchivo(nombreArchivo);
					conciCabTramaDiaria.setFecCarga(fecCarga);
					conciCabTramaDiaria.setNumRegistros(nroRegistros);
					conciCabTramaDiaria.setUsuCreacion(usuario.toUpperCase());
					conciCabTramaDiaria.setFecCreacion(fechaActual);
					conciCabTramaDiaria.setCanal(codCanal);
					conciCabTramaDiaria.setEstado(SateliteConstants.ESTADO_REGISTRO_INACTIVO);
					conciCabTramaDiaria.setEstadoConsolidado(SateliteConstants.ESTADO_CONSOLIDADO_PENDIENTE);
					conciCabTramaDiaria.setCorrelativo(correlativoSerie);
					conciCabTramaDiaria.setRutaArchivo(NuevoURL_paquete);	
					conciCabTramaDiaria.setFlagTrama(SateliteConstants.FLAG_TRAMA_MENSUAL);
					if(cargTramaDiariaSoatService.insertar(conciCabTramaDiaria)){					
						status = true;				
					}				
					
				
			} catch (SyncconException ex) {
				status = false;
				log.error(ERROR_SYNCCON + ex.getMessageComplete());
				FacesMessage facesMsg = new FacesMessage(ex.getSeveridad(),
						ex.getMessage(), null);
				FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			} catch (Exception e) {
				status = false;
				log.error(e.getMessage(), e);
				FacesMessage facesMsg = new FacesMessage(
						FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
				FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			}
			if(log.isInfoEnabled()){ log.info("Fin");}			
			return status;
		}
	  
	  private Boolean ingresarCabeceraTXT(String codProducto,String separador) {
			
		  if(log.isInfoEnabled()){log.info("Inicio");}
			Boolean status = false;
			String usuario = SecurityContextHolder.getContext().getAuthentication().getName();
			Date fechaActual = new Date(System.currentTimeMillis());
			String numColumnas="-1";
			String[] dataTXT = new String[2]; 
			Boolean valTXT = false;

			try {
				
				listaExcel = new ArrayList<SelectItem>();
				
			//	buscarListaLayout();
				log.info("Nombre Archivo: " + nombreArchivo);
				
				if (StringUtils.isBlank(nombreArchivo)) {
					throw new SyncconException(ErrorConstants.COD_ERROR_ARCHIVO, FacesMessage.SEVERITY_INFO);
				}
				
				if(!StringUtils.isNotEmpty(codSocio.toString())){
					SateliteUtil.mostrarMensaje("Seleccionar un producto");
					return status=false;
				}
				
				dataTXT = cargTramaDiariaSoatService.numeroRegistrosTXT(archivoFinal,separador,codProducto);
				log.info("separador "+separador);
				List<Layout>  lista =  baseCabeceraService.obtenerCabeceraExcelAll(codSocio);	
				numColumnas = dataTXT[0].toString();					
				nroRegistros = Long.valueOf(dataTXT[1]);
			
				if(!separador.trim().equals(SateliteConstants.TRAMA_SEPARADOR_04)){
					if(lista.size() == Integer.parseInt(numColumnas)){
						buscarListaLayout();				
						conciCabTramaDiaria = new ConciCabTramaDiaria();
						conciCabTramaDiaria.setProducto(codProducto);
						conciCabTramaDiaria.setTipoCargo(SateliteConstants.TIPO_DE_CARGA);
						conciCabTramaDiaria.setNomArchivo(nombreArchivo);
						conciCabTramaDiaria.setFecCarga(fecCarga);
						conciCabTramaDiaria.setNumRegistros(nroRegistros);
						conciCabTramaDiaria.setUsuCreacion(usuario.toUpperCase());
						conciCabTramaDiaria.setFecCreacion(fechaActual);
						conciCabTramaDiaria.setCanal(codCanal);
						conciCabTramaDiaria.setEstado(SateliteConstants.ESTADO_REGISTRO_INACTIVO);
						conciCabTramaDiaria.setEstadoConsolidado(SateliteConstants.ESTADO_CONSOLIDADO_PENDIENTE);
						conciCabTramaDiaria.setCorrelativo(correlativoSerie);
						conciCabTramaDiaria.setRutaArchivo(NuevoURL_paquete);		
						conciCabTramaDiaria.setFlagTrama(SateliteConstants.FLAG_TRAMA_MENSUAL);
						cargTramaDiariaSoatService.insertar(conciCabTramaDiaria);					
						status=true;
						
					}else{
						SateliteUtil.mostrarMensaje(FacesMessage.SEVERITY_INFO,"El numero de columnas del archivo txt, no conincide con la columnas del Layout ");				
						status=false;
					}
				}else{
					valTXT = Boolean.valueOf(numColumnas);
					if(valTXT){
						buscarListaLayout();				
						conciCabTramaDiaria = new ConciCabTramaDiaria();
						conciCabTramaDiaria.setProducto(codProducto);
						conciCabTramaDiaria.setTipoCargo(SateliteConstants.TIPO_DE_CARGA);
						conciCabTramaDiaria.setNomArchivo(nombreArchivo);
						conciCabTramaDiaria.setFecCarga(fecCarga);
						conciCabTramaDiaria.setNumRegistros(nroRegistros);
						conciCabTramaDiaria.setUsuCreacion(usuario.toUpperCase());
						conciCabTramaDiaria.setFecCreacion(fechaActual);
						conciCabTramaDiaria.setCanal(codCanal);
						conciCabTramaDiaria.setEstado(SateliteConstants.ESTADO_REGISTRO_INACTIVO);
						conciCabTramaDiaria.setEstadoConsolidado(SateliteConstants.ESTADO_CONSOLIDADO_PENDIENTE);
						conciCabTramaDiaria.setCorrelativo(correlativoSerie);
						conciCabTramaDiaria.setRutaArchivo(NuevoURL_paquete);		
						conciCabTramaDiaria.setFlagTrama(SateliteConstants.FLAG_TRAMA_MENSUAL);
						cargTramaDiariaSoatService.insertar(conciCabTramaDiaria);					
						status=true;					
					}else{
						SateliteUtil.mostrarMensaje(FacesMessage.SEVERITY_INFO,"El tamaño de longitud del archivo txt, no conincide con la columnas del Layout ");				
						status=false;
					}	
				}
					
				
				
				
			} catch (Exception e) {
				log.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
				FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			}
			
			if(log.isInfoEnabled()){log.info("Fin");}

			return status;
		}
	 
	  
	  public String btnSiguiente(){
		  String respuesta ="";
			
			if (log.isInfoEnabled()) {log.info("Inicio");}
			
			try {
				
				if(log.isDebugEnabled()){
				log.debug("Input [ 1.- "+codSocio+" ]");		
				}
				
				if(StringUtils.isNotEmpty(codSocio.toString())){	
						
					selectListaTabla = new ArrayList<SelectItem>();
					
					buscarListaLayout();				
					respuesta = SateliteConstants.SIGUIENTE_TRAMA_MENSUAL;		
				}else{				
					SateliteUtil.mostrarMensaje("Seleccionar un producto");				
				}
				
					} catch (Exception e){
						log.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
						log.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
						FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
						FacesContext.getCurrentInstance().addMessage(null, facesMsg);
				    	respuesta = ErrorConstants.MSJ_ERROR;
				    	
					} 	

			if (log.isInfoEnabled()) {log.info("Fin");}
			
			return respuesta;
	  }
	  


		private void buscarListaLayout() {

			if (log.isInfoEnabled()) {log.info("Inicio");}		
			try {
			
				// carga de columnas de la tabla DETALLE_TRAMA_DIARIA
				
				listaLayout  = null;
				listaLayout  = new ArrayList<ConfLayout>();
				listaExcel  = null;
				listaExcel = new ArrayList<SelectItem>();
				listaTabla = null;
				listaTabla = new ArrayList<SelectItem>();			
				
				log.info("Busqueda de parametros de carga de trama"); 			
				List<ConfLayout> listaxls = new ArrayList<ConfLayout>();
				listaxls = baseCabeceraService.obtenerCabeceraExcel(codSocio);			
				List<TramaDiariaBean> listadb = new ArrayList<TramaDiariaBean>();			
				listadb = baseCabeceraService.obtenerTabla();							
				///listaDBtmp = listadb;

				log.debug("Output [ resultado = "+ listaxls.size() +" ]");
				
				
				for (ConfLayout layout : listaxls) {	
					log.info("excel data" + BeanUtils.describe(layout));
					if(StringUtils.isNotBlank(layout.getValueTabla()) && StringUtils.isNotBlank(layout.getValueExcel())){
						
						listaLayout.add(layout);
								
						//listaLayout.add(new Layout(layout.getIdcanal(), layout.getColumnaExcel(), layout.getColumnaTabla(),layout.getLabel().replace(SateliteConstants.FlAG_PREFIJO_EXCEL, ""),layout.getDescripcion()));						
					}else{						
						if(StringUtils.isNotBlank(layout.getLabelExcel())){
							
							listaExcel.add(new SelectItem(layout.getValueExcel(),layout.getLabelExcel(),layout.getTipoDataExcel()));					

						}else{
							
							listaExcel.add(new SelectItem(layout.getValueExcel(),layout.getValueExcel(),layout.getTipoDataExcel()));					
							
						}
					}					
				}		
				
				// carga de columnas de la tabla DETALLE_TRAMA_LAYOUT		
				List<TramaDiariaBean> tmp = new ArrayList<TramaDiariaBean>();
				
				for (int i = 0; i < listadb.size();i++ ) {
					for (int j = 0; j < listaLayout.size();j++)  {							
						if(listadb.get(i).getNombreColumna().equalsIgnoreCase(listaLayout.get(j).getValueTabla())){												
							tmp.add(listadb.get(i));						
						}				
					}
					
				}
				
				for (TramaDiariaBean tramaDiariaBean : tmp) {
					listadb.remove(tramaDiariaBean);
				}
					
				//LLENA LA LISTA DE FORMATO UNICO
				for (TramaDiariaBean tramaDiariaBean : listadb) {
					log.info( "Data :" + BeanUtils.describe(tramaDiariaBean));
					listaTabla.add(new SelectItem(tramaDiariaBean.getNombreColumna(),tramaDiariaBean.getLabel(),tramaDiariaBean.getTipoDato()));				
				}
					
			} catch (Exception e){
						log.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
						FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
						FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			} 			
			
			if (log.isInfoEnabled()) {log.info("Fin");}
		}

		
		public String ejecutarCargaTramaDiaria(){
			String respuesta ="";
			Long tramaID = null;
			
			if (log.isInfoEnabled()) {log.info("Inicio");}
			
			try {
				if(log.isDebugEnabled()){
				log.debug("Input [ 1.-"+ BeanUtils.describe(listaLayout) +" ]");
				}
				
				tramaEjecucion = new TramaEjecucion();
				tramaID = cargTramaDiariaSoatService.selectIdTrama(SateliteConstants.FLAG_TRAMA_MENSUAL);
				ControlPaquete controlPaquete = new ControlPaquete();
				controlPaquete.setEstado(1);
				controlPaquete.setFechaCreacion(Calendar.getInstance().getTime());
				controlPaquete.setFechaInicio(Calendar.getInstance().getTime());
				controlPaquete.setFlagEjecucion(0);
				controlPaquete.setTotalRegistro(0);
				controlPaquete.setUsuarioCreacion(SecurityContextHolder.getContext().getAuthentication().getName().toUpperCase());
				controlPaquete.setProceso(SateliteConstants.SUSCRIPCION_TRAMA_MENSUAL_FLAG);
				controlPaquete.setIdTrama(Integer.valueOf(tramaID.toString()));	
				setEnabled(false);
				
				if(cargTramaDiariaSoatService.insertarControlPaquete(controlPaquete)){
					tramaEjecucion = cargTramaDiariaSoatService.ejecucionTrama();
					respuesta = SateliteConstants.SIGUIENTE_CONTROL_PAQUETE_MENSUAL;
				 }
				
				log.debug("Output [ 1.- ]");
				
			} catch (Exception e){
						log.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
						log.error("SyncconException() -->" + ExceptionUtils.getStackTrace(e));
						FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
						FacesContext.getCurrentInstance().addMessage(null, facesMsg);
				    	respuesta = ErrorConstants.MSJ_ERROR;
			} 	
			
			if (log.isInfoEnabled()) {log.info("Fin");}
				
			return respuesta;
		}
		
		
		
		
		public String eliminar() {
			String respuesta = null;
			if(log.isInfoEnabled()){log.info("Inicio");}
			
			ConciCabTramaMensual consultaCabecera = null;
			Iterator<Object> iterator = getSelection().getKeys();
			Object key = new Object();
			try {
				
				while (iterator.hasNext()) {
					key = iterator.next();
					consultaCabecera = listaArchivos.get((Integer) key);
				}

				if (consultaCabecera == null) {
					throw new SyncconException(ErrorConstants.COD_ERROR_SELECCION,
							FacesMessage.SEVERITY_INFO);
				}
				if(log.isDebugEnabled()){
					log.info("Cabecera key: " + consultaCabecera.getSecArchivo());					
				}
				
				cargaTramaMensualService.eliminar(consultaCabecera.getSecArchivo());
				flagBtnEliminar = false;
				if(log.isDebugEnabled()){

				log.info("FLag: " + flagBtnEliminar);
				}
				buscar();

			} catch (SyncconException ex) {
				log.error(ERROR_SYNCCON + ex.getMessageComplete());
				FacesMessage facesMsg = new FacesMessage(ex.getSeveridad(),
						ex.getMessage(), null);
				FacesContext.getCurrentInstance().addMessage(null, facesMsg);
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


		
		public String procesandoCarga() {
			
			String respuesta = "";
			
			
			Thread thread = new Thread(new Runnable() {
				
				@Override
				public void run() {
					Boolean ws = true;
					try {
							 int x = 0;	
							 procesosETLsService.procesosTramaMensual();
								
						do {
							
							setEnabled(true);
					        setButtonRendered(false);
					        setStartTime(new Date().getTime());		 
							tramaEjecucion = cargTramaDiariaSoatService.ejecucionTrama();
							x++;
							log.info("linea" + x );										
						    Thread.sleep(5000);
							
						} while (tramaEjecucion.getEstadoProceso().equalsIgnoreCase("pendiente"));
						
					} catch (Exception e) {
			    		log.info("Mesagge "+e.getStackTrace());
					}					
				}
			});
			
			thread.start();
			
			return respuesta;
			
		}
		public String startProcess() {
		        setEnabled(true);
		        setButtonRendered(false);
		        setStartTime(new Date().getTime());
		        return null;
		}

		public Long getCurrentValue(){
		        if (isEnabled()){
		            Long current = (new Date().getTime() - startTime)/100;
		            if (current>100){
		                setButtonRendered(true);
		            }else if (current.equals(0)){
		                return new Long(1);
		            }
		            return (new Date().getTime() - startTime)/100;
		        } if (startTime == null) {
		            return Long.valueOf(-1);
		        }
		        else
		            return Long.valueOf(100);
		}
		
		public String exportarObservado() {
			
			if(log.isInfoEnabled()){log.info("Inicio");}
			
			
			 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			 ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
			 String FechaActual = sdf.format(new Date(System.currentTimeMillis()));
			 long horaInicial =  System.currentTimeMillis();
			
			try {
				
					String rutaTemp = productoService.obtenerRutaArchivo(codProducto,SateliteConstants.FLAG_TRAMA_MENSUAL);
				    log.debug("Ruta Archivo: " + rutaTemp);			     
			        File archivoResp = new File(rutaTemp);
			        FileInputStream in = new FileInputStream(archivoResp);
			        HttpServletResponse response = (HttpServletResponse) contexto.getResponse();
			        byte[] loader = new byte[(int) archivoResp.length()];
			        response.addHeader("Content-Disposition", "attachment;filename=" + "Reporte_Observados_" + FechaActual + ".xls");
			        response.setContentType("application/vnd.ms-excel");
			        ServletOutputStream out = response.getOutputStream();

			        while ((in.read(loader)) > 0) {
			          out.write(loader, 0, loader.length);
			        }
			        
			        in.close();
			        out.close();
					
			        FacesContext.getCurrentInstance().responseComplete();
				            
			}catch (SyncconException e) {
				e.printStackTrace();
			}catch (Exception ex) {
	            ex.printStackTrace();
	        }
			log.info("hora:--->: " + (System.currentTimeMillis() - horaInicial));
			
			if(log.isInfoEnabled()){log.info("Fin");}
			return "";
		}	
		
		private String obtenerObservaciones(Integer linea, List<HashMap<String, Object>> obj, List<Layout> lstTemp) {
			
			if (log.isInfoEnabled()) { log.info("Input [1.- "+linea+" 2.- "+ obj.size()+" 3.- "+ lstTemp.size() +"]");}	
				String strObservaciones = "";			
				for (HashMap<String, Object> hashMap : obj) {				
					if(Integer.parseInt(hashMap.get("NRO_REGISTRO").toString())==linea){
						for (int i = 0; i < lstTemp.size(); i++) {
							if(null!=lstTemp.get(i).getColumnaExcel() && null != lstTemp.get(i).getColumnaTabla()){	
								
								String strkey = lstTemp.get(i).getColumnaTabla();
								try {
										//log.info("MAP ----->:"+hashMap.get(strkey).toString());
										hashMap.get(strkey).toString();
								} catch (Exception e) {
									strObservaciones = strObservaciones + " , " + lstTemp.get(i).getColumnaExcel(); 
								}													
							 }
						 }		
						continue;			
					}
				}		
			if (log.isInfoEnabled()) {log.info("Output [1.-"+strObservaciones+"]");}
				
			return strObservaciones;
		}
		public String buscar() {
			
			if(log.isInfoEnabled()){log.info("Inicio");}
			
			String respuesta = null;
			if(log.isDebugEnabled()){
			log.debug("cod. Producto: " + this.codSocio.toString());
			}
			producto = "";
			try {
				selection = new SimpleSelection();
				listaArchivos = new ArrayList<ConciCabTramaMensual>();
				listaArchivos = cargaTramaMensualService.buscarSocio(this.codSocio.toString());
				if(log.isDebugEnabled()){
				log.debug("flagEliminar: " + flagBtnEliminar);
				}
			} catch (SyncconException ex) {
				log.error(ERROR_SYNCCON + ex.getMessageComplete());
				FacesMessage facesMsg = new FacesMessage(ex.getSeveridad(),
						ex.getMessage(), null);
				FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				FacesMessage facesMsg = new FacesMessage(
						FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
				FacesContext.getCurrentInstance().addMessage(null, facesMsg);
				respuesta = MSJ_ERROR;
			}
			if(log.isInfoEnabled()){
			log.info("Fin");
			}
			return respuesta;
		}
		
		/**
		 * retorna hacia la pagina de trama mensual
		 * 
		 * @return String siguiente paso
		 */
		public String regresarTramaDiaria(){
			String respuesta = null;
			listaArchivos.clear();
			nombreArchivo = null;
			codCanal = null;
			codSocio = null;
			fecCarga = null;
			correlativoSerie = null;
			respuesta = SateliteConstants.REGRESAR_TRAMA_MENSUAL;
			return respuesta;
		}
		
		public void canalChangeListener(ValueChangeEvent event) {
			try {

				if(log.isDebugEnabled()){log.info("Inicio");}
				 if (event.getNewValue() != null) {
					log.debug("Input [ idproducto" +event.getNewValue().toString() +"]" );	
					listaSocio = new ArrayList<SocioProductoBean>();	
					tipoSocioItems = new ArrayList<SelectItem>();
					listaSocio = cargTramaDiariaSoatService.listaSocioProducto(Integer.valueOf(event.getNewValue().toString()),SateliteConstants.FLAG_TRAMA_MENSUAL);
					tipoSocioItems.add(new SelectItem("", "- Seleccione -"));	
					for (SocioProductoBean socio : listaSocio) {
//						tipoSocioItems.add(new SelectItem(socio.getIdProducto(), socio.getNombreSocio() +" -- " + socio.getNombreProducto()));
						tipoSocioItems.add(new SelectItem(socio.getIdProducto(), socio.getNombreProducto()));
					}					
				 }
				 if(log.isDebugEnabled()){log.info("Final");}
			} catch (NumberFormatException e1) {
				
				log.error(e1.getMessage(), e1);
				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
				FacesContext.getCurrentInstance().addMessage(null, facesMsg);			
			
			} catch (SyncconException e1) {
				log.error(e1.getMessage(), e1);
				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
				FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			}

		}
		
		public String listener(UploadEvent event) {
			if(log.isInfoEnabled()){
			log.info("Inicio");
			}
			String respuesta = null;
			try {
				
				nombreArchivo = new String();
				UploadItem item = event.getUploadItem();
				File archivo = item.getFile();
				nombreArchivo = item.getFileName().substring(item.getFileName().lastIndexOf("\\") + 1);
				archivoFinal = File.createTempFile("TRAMASOAT_", nombreArchivo);
				if(log.isDebugEnabled()){
				log.debug("Archivo final: " + archivoFinal);
				}
				FileUtils.copyFile(archivo, archivoFinal);
				archivo.delete();
				
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				FacesMessage facesMsg = new FacesMessage(
						FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
				FacesContext.getCurrentInstance().addMessage(null, facesMsg);
				respuesta = MSJ_ERROR;
			}
			
			log.debug("Fin");
			return respuesta;
}
		//private List<TramaDiariaBean> listaDBtmp;
		public String  seleccionListas(ActionEvent event ){
			String respuesta ="";
			String validaExcel ="";
			
			if (log.isInfoEnabled()) {log.info("Inicio");}
			
			try {
				
				log.debug("Input [ 1.- "+excel+" 2.- "+ tabla +" 3.- "+ codSocio +"]");
			
				if(StringUtils.isNotEmpty(excel)==true && StringUtils.isNotEmpty(tabla)==true && StringUtils.isNotEmpty(codSocio.toString())){			
					
					ConfLayout confLayout = selectAdd(excel,tabla,codSocio);			
					
					log.info("-------------- COMPARACION TIPO DATA------------------"+ BeanUtils.describe(confLayout));
					log.info("Compare :" + confLayout.getTipoDataExcel().toUpperCase() + " = " + confLayout.getTipoDataTabla().toUpperCase());
					
					if(confLayout.getTipoDataExcel().toUpperCase().trim().equals(confLayout.getTipoDataTabla().toUpperCase().trim())){
						
						validaExcel = excel.replaceAll(SateliteConstants.FlAG_PREFIJO_EXCEL, "");	
						if(excel.contains(SateliteConstants.FlAG_PREFIJO_EXCEL)){
							
							Layout obj = new Layout();
							obj.setIdcanal(Long.valueOf(codSocio));
							obj.setColumnaExcel(validaExcel);
							obj.setColumnaTabla(confLayout.getValueTabla());
							obj.setEstado(Integer.parseInt(SateliteConstants.ESTADO_REGISTRO_ACTIVO));
							obj.setTipodata(SateliteConstants.FLAG_TIPO_DATA);
							obj.setObligatorio(Integer.parseInt(SateliteConstants.ESTADO_REGISTRO_INACTIVO));
							obj.setLabel(confLayout.getLabelTabla());
							obj.setUsuarioCreacion(SecurityContextHolder.getContext().getAuthentication().getName());
							obj.setFechaCreacion(Calendar.getInstance().getTime());
							obj.setDescripcion(validaExcel);
							cargTramaDiariaSoatService.insertLayout(obj);
							log.debug("Insertar");
							
						}else{
							
							cargTramaDiariaSoatService.actualizarLayout(confLayout);
							log.debug("Actualizar");
							
						}
						
						listaLayout.add(confLayout);				
						listaExcel.remove(getItem(listaExcel,excel.replaceAll(SateliteConstants.FlAG_PREFIJO_EXCEL, "")));
						listaTabla.remove(getItem(listaTabla,tabla));	
						
					}else{
						
						FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "¡ Seleccionar columnas con el mismo tipo de dato !", null);
						FacesContext.getCurrentInstance().addMessage(null, facesMsg);
						
						return respuesta;
					}				
					
								
					
					log.info("LISTA SELECIONADO excel:" +excel + " tabla"+tabla);					
				}
				
				log.debug("Output [ 1.- ]");
			} catch (Exception e){
						log.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
						FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
						FacesContext.getCurrentInstance().addMessage(null, facesMsg);
				    	
					} 	
			if (log.isInfoEnabled()) {log.info("Fin");}
			return respuesta;
		}
		private ConfLayout selectAdd( String valExcel,String valTabla,Integer socio) {
			
			ConfLayout layout = new ConfLayout();
			
				for (SelectItem item : listaExcel) {
					if (item.getValue().equals(valExcel)) {
						layout.setId(socio.toString());
						layout.setValueExcel(item.getValue().toString());
						layout.setLabelExcel(item.getLabel().toLowerCase());
						if(null !=item.getDescription()){
							layout.setTipoDataExcel(item.getDescription());						
						}
						else{
							layout.setTipoDataExcel("true");
						}
					}
				}
				
				for (SelectItem item : listaTabla) {
					if (item.getValue().equals(valTabla)) {
						layout.setValueTabla(item.getValue().toString());
						layout.setLabelTabla(item.getLabel().toLowerCase());
						if(null !=item.getDescription()){
							layout.setTipoDataTabla(item.getDescription());
						}else{
							layout.setTipoDataTabla("false");
						}
					}
				}
			
			return layout;
		}
		public SelectItem getItem(List<SelectItem> items, String value) {
		        for (SelectItem si : items) {	          
		            if (si.getValue().toString().equalsIgnoreCase(value)) {
		                return si;
		            }
		        }
		        return null;
		}
		
		public void removeListas(){
			
			if (log.isInfoEnabled()) {log.info("Inicio");}
			ConfLayout conflayout = null;
			Layout layout = new Layout();
			Iterator<Object> iterator = getSelection().getKeys();
			Object key = new Object();			
			try {
				

				while(iterator.hasNext()){		
					
					key = iterator.next();
					conflayout = listaLayout.get((Integer)key);
					
					if(conflayout == null){	
						
						SateliteUtil.mostrarMensaje("Error al seleccionar");	
						
					}else{
						log.info("actualizar :"+ BeanUtils.describe(conflayout));
						listaExcel.add(new SelectItem(conflayout.getValueExcel(), conflayout.getLabelExcel(),conflayout.getTipoDataExcel()));
						listaTabla.add(new SelectItem(conflayout.getValueTabla(),conflayout.getLabelTabla(),conflayout.getTipoDataTabla()));						
						listaLayout.remove(conflayout);					
						conflayout.setLabelTabla("");
						conflayout.setValueTabla("");
						cargTramaDiariaSoatService.actualizarLayout(conflayout);	
						
					}
				}			
				
				log.debug("Output [ 1.- ]");
			} catch (Exception e){
						log.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
						FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
						FacesContext.getCurrentInstance().addMessage(null, facesMsg);			    
			} 	
			if (log.isInfoEnabled()) {log.info("Fin");}			
		}
/**
 * @return the listaExcel
 */
public List<SelectItem> getListaExcel() {
	return listaExcel;
}

/**
 * @param listaExcel the listaExcel to set
 */
public void setListaExcel(List<SelectItem> listaExcel) {
	this.listaExcel = listaExcel;
}

/**
 * @return the listaTabla
 */
public List<SelectItem> getListaTabla() {
	return listaTabla;
}

/**
 * @param listaTabla the listaTabla to set
 */
public void setListaTabla(List<SelectItem> listaTabla) {
	this.listaTabla = listaTabla;
}

/**
 * @return the selectListaTabla
 */
public List<SelectItem> getSelectListaTabla() {
	return selectListaTabla;
}

/**
 * @param selectListaTabla the selectListaTabla to set
 */
public void setSelectListaTabla(List<SelectItem> selectListaTabla) {
	this.selectListaTabla = selectListaTabla;
}

/**
 * @return the listaLayout
 */
public List<ConfLayout> getListaLayout() {
	return listaLayout;
}

/**
 * @param listaLayout the listaLayout to set
 */
public void setListaLayout(List<ConfLayout> listaLayout) {
	this.listaLayout = listaLayout;
}

/**
 * @return the excel
 */
public String getExcel() {
	return excel;
}

/**
 * @param excel the excel to set
 */
public void setExcel(String excel) {
	this.excel = excel;
}

/**
 * @return the tabla
 */
public String getTabla() {
	return tabla;
}

/**
 * @param tabla the tabla to set
 */
public void setTabla(String tabla) {
	this.tabla = tabla;
}

/**
 * @return the selectTabla
 */
public String getSelectTabla() {
	return selectTabla;
}

/**
 * @param selectTabla the selectTabla to set
 */
public void setSelectTabla(String selectTabla) {
	this.selectTabla = selectTabla;
}

/**
 * @return the value
 */
public String getValue() {
	return value;
}

/**
 * @param value the value to set
 */
public void setValue(String value) {
	this.value = value;
}

/**
 * @return the tipoCanalItems
 */
public List<SelectItem> getTipoCanalItems() {
	return tipoCanalItems;
}

/**
 * @param tipoCanalItems the tipoCanalItems to set
 */
public void setTipoCanalItems(List<SelectItem> tipoCanalItems) {
	this.tipoCanalItems = tipoCanalItems;
}

/**
 * @return the tipoSocioItems
 */
public List<SelectItem> getTipoSocioItems() {
	return tipoSocioItems;
}

/**
 * @param tipoSocioItems the tipoSocioItems to set
 */
public void setTipoSocioItems(List<SelectItem> tipoSocioItems) {
	this.tipoSocioItems = tipoSocioItems;
}



/**
 * @return the listaArchivos
 */
public List<ConciCabTramaMensual> getListaArchivos() {
	return listaArchivos;
}

/**
 * @param listaArchivos the listaArchivos to set
 */
public void setListaArchivos(List<ConciCabTramaMensual> listaArchivos) {
	this.listaArchivos = listaArchivos;
}

/**
 * @return the listaColumnasTabla
 */
public List<ConciCabTramaMensual> getListaColumnasTabla() {
	return listaColumnasTabla;
}

/**
 * @param listaColumnasTabla the listaColumnasTabla to set
 */
public void setListaColumnasTabla(List<ConciCabTramaMensual> listaColumnasTabla) {
	this.listaColumnasTabla = listaColumnasTabla;
}

/**
 * @return the selection
 */
public SimpleSelection getSelection() {
	return selection;
}

/**
 * @param selection the selection to set
 */
public void setSelection(SimpleSelection selection) {
	this.selection = selection;
}

/**
 * @return the product
 */
public Product getProduct() {
	return product;
}

/**
 * @param product the product to set
 */
public void setProduct(Product product) {
	this.product = product;
}



/**
 * @return the baseCabeceraService
 */
public BaseCabeceraService getBaseCabeceraService() {
	return baseCabeceraService;
}

/**
 * @param baseCabeceraService the baseCabeceraService to set
 */
public void setBaseCabeceraService(BaseCabeceraService baseCabeceraService) {
	this.baseCabeceraService = baseCabeceraService;
}



/**
 * @return the cargTramaDiariaSoatService
 */
public CargTramaDiariaSoatService getCargTramaDiariaSoatService() {
	return cargTramaDiariaSoatService;
}

/**
 * @param cargTramaDiariaSoatService the cargTramaDiariaSoatService to set
 */
public void setCargTramaDiariaSoatService(
		CargTramaDiariaSoatService cargTramaDiariaSoatService) {
	this.cargTramaDiariaSoatService = cargTramaDiariaSoatService;
}

/**
 * @return the conciTramaDiaria
 */
public ConciTramaDiaria getConciTramaDiaria() {
	return conciTramaDiaria;
}

/**
 * @param conciTramaDiaria the conciTramaDiaria to set
 */
public void setConciTramaDiaria(ConciTramaDiaria conciTramaDiaria) {
	this.conciTramaDiaria = conciTramaDiaria;
}

/**
 * @return the upload
 */
public boolean isUpload() {
	return upload;
}

/**
 * @param upload the upload to set
 */
public void setUpload(boolean upload) {
	this.upload = upload;
}

/**
 * @return the nombreArchivo
 */
public String getNombreArchivo() {
	return nombreArchivo;
}

/**
 * @param nombreArchivo the nombreArchivo to set
 */
public void setNombreArchivo(String nombreArchivo) {
	this.nombreArchivo = nombreArchivo;
}

/**
 * @return the archivoFinal
 */
public File getArchivoFinal() {
	return archivoFinal;
}

/**
 * @param archivoFinal the archivoFinal to set
 */
public void setArchivoFinal(File archivoFinal) {
	this.archivoFinal = archivoFinal;
}

/**
 * @return the codProducto
 */
public String getCodProducto() {
	return codProducto;
}

/**
 * @param codProducto the codProducto to set
 */
public void setCodProducto(String codProducto) {
	this.codProducto = codProducto;
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
 * @return the nroRegistros
 */
public Long getNroRegistros() {
	return nroRegistros;
}

/**
 * @param nroRegistros the nroRegistros to set
 */
public void setNroRegistros(Long nroRegistros) {
	this.nroRegistros = nroRegistros;
}

/**
 * @return the flagBtnEliminar
 */
public boolean isFlagBtnEliminar() {
	return flagBtnEliminar;
}

/**
 * @param flagBtnEliminar the flagBtnEliminar to set
 */
public void setFlagBtnEliminar(boolean flagBtnEliminar) {
	this.flagBtnEliminar = flagBtnEliminar;
}

/**
 * @return the flagBtnSiguiente
 */
public boolean isFlagBtnSiguiente() {
	return flagBtnSiguiente;
}

/**
 * @param flagBtnSiguiente the flagBtnSiguiente to set
 */
public void setFlagBtnSiguiente(boolean flagBtnSiguiente) {
	this.flagBtnSiguiente = flagBtnSiguiente;
}

/**
 * @return the fecCarga
 */
public Date getFecCarga() {
	return fecCarga;
}

/**
 * @param fecCarga the fecCarga to set
 */
public void setFecCarga(Date fecCarga) {
	this.fecCarga = fecCarga;
}

/**
 * @return the codCanal
 */
public String getCodCanal() {
	return codCanal;
}

/**
 * @param codCanal the codCanal to set
 */
public void setCodCanal(String codCanal) {
	this.codCanal = codCanal;
}

/**
 * @return the correlativoSerie
 */
public Integer getCorrelativoSerie() {
	return correlativoSerie;
}

/**
 * @param correlativoSerie the correlativoSerie to set
 */
public void setCorrelativoSerie(Integer correlativoSerie) {
	this.correlativoSerie = correlativoSerie;
}

/**
 * @return the codSocio
 */
public Integer getCodSocio() {
	return codSocio;
}

/**
 * @param codSocio the codSocio to set
 */
public void setCodSocio(Integer codSocio) {
	this.codSocio = codSocio;
}

/**
 * @return the listaSocio
 */
public List<SocioProductoBean> getListaSocio() {
	return listaSocio;
}

/**
 * @param listaSocio the listaSocio to set
 */
public void setListaSocio(List<SocioProductoBean> listaSocio) {
	this.listaSocio = listaSocio;
}

/**
 * @return the cabTramaMensual
 */
public ConciCabTramaMensual getCabTramaMensual() {
	return cabTramaMensual;
}

/**
 * @param cabTramaMensual the cabTramaMensual to set
 */
public void setCabTramaMensual(ConciCabTramaMensual cabTramaMensual) {
	this.cabTramaMensual = cabTramaMensual;
}

/**
 * @return the tramaMensual
 */
public ConciTramaMensual getTramaMensual() {
	return tramaMensual;
}

/**
 * @param tramaMensual the tramaMensual to set
 */
public void setTramaMensual(ConciTramaMensual tramaMensual) {
	this.tramaMensual = tramaMensual;
}

/**
 * @return the listaTramas
 */
public List<ConciCabTramaMensual> getListaTramas() {
	return listaTramas;
}

/**
 * @param listaTramas the listaTramas to set
 */
public void setListaTramas(List<ConciCabTramaMensual> listaTramas) {
	this.listaTramas = listaTramas;
}

/**
 * @return the productoItems
 */
public List<SelectItem> getProductoItems() {
	return productoItems;
}

/**
 * @param productoItems the productoItems to set
 */
public void setProductoItems(List<SelectItem> productoItems) {
	this.productoItems = productoItems;
}

/**
 * @return the socio
 */
public String getSocio() {
	return socio;
}

/**
 * @param socio the socio to set
 */
public void setSocio(String socio) {
	this.socio = socio;
}

/**
 * @return the tramaEjecucion
 */
public TramaEjecucion getTramaEjecucion() {
	return tramaEjecucion;
}

/**
 * @param tramaEjecucion the tramaEjecucion to set
 */
public void setTramaEjecucion(TramaEjecucion tramaEjecucion) {
	this.tramaEjecucion = tramaEjecucion;
}

/**
 * @return the listaObservada
 */
public List<Layout> getListaObservada() {
	return listaObservada;
}

/**
 * @param listaObservada the listaObservada to set
 */
public void setListaObservada(List<Layout> listaObservada) {
	this.listaObservada = listaObservada;
}



/**
 * @return the habilitarCorrelativo
 */
public boolean isHabilitarCorrelativo() {
	return habilitarCorrelativo;
}

/**
 * @param habilitarCorrelativo the habilitarCorrelativo to set
 */
public void setHabilitarCorrelativo(boolean habilitarCorrelativo) {
	this.habilitarCorrelativo = habilitarCorrelativo;
}


//TESTS
	
	private boolean buttonRendered = true;
private boolean enabled=false;
private Long startTime;





public boolean isEnabled() {
    return enabled;
}

public void setEnabled(boolean enabled) {
    this.enabled = enabled;
}

public Long getStartTime() {
    return startTime;
}

public void setStartTime(Long startTime) {
    this.startTime = startTime;
}

public boolean isButtonRendered() {
    return buttonRendered;
}

public void setButtonRendered(boolean buttonRendered) {
    this.buttonRendered = buttonRendered;
}

public String getNuevoURL_paquete() {
	return NuevoURL_paquete;
}

public void setNuevoURL_paquete(String nuevoURL_paquete) {
	NuevoURL_paquete = nuevoURL_paquete;
}

  	

}
