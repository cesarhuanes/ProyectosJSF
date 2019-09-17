package com.cardif.satelite.suscripcion.controller;

//import static com.cardif.satelite.constantes.ErrorConstants.ERROR_SYNCCON;
import static com.cardif.satelite.constantes.ErrorConstants.MSJ_ERROR;
import static com.cardif.satelite.constantes.ErrorConstants.MSJ_ERROR_GENERAL;

import java.io.File;
import java.io.FileInputStream;
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
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
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
import com.cardif.satelite.configuracion.service.ParametroAutomatService;
import com.cardif.satelite.configuracion.service.SocioService;
import com.cardif.satelite.constantes.Constantes;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.ControlPaquete;
import com.cardif.satelite.model.ParametroAutomat;
import com.cardif.satelite.model.Socio;
import com.cardif.satelite.model.TramaMaster;
import com.cardif.satelite.model.acsele.Product;
import com.cardif.satelite.model.satelite.MasterPrecioSoat;
import com.cardif.satelite.model.satelite.TramaDiaria;
import com.cardif.satelite.suscripcion.bean.ConfLayout;
import com.cardif.satelite.suscripcion.bean.ConsultaFechaCargaMaster;
import com.cardif.satelite.suscripcion.bean.ConsultaMasterPrecio;
import com.cardif.satelite.suscripcion.bean.TramaEjecucion;
import com.cardif.satelite.suscripcion.service.CargMasterSoatService;
import com.cardif.satelite.suscripcion.service.ProcesosETLsService;
import com.cardif.satelite.util.SateliteConstants;
import com.cardif.satelite.util.SateliteUtil;
import com.cardif.satelite.util.Utilitarios;

//import com.cardif.framework.excepcion.SyncconException;
/**
 * @author maronlu
 */
@Controller("cargMasterSoatController")
@Scope("request")
public class CargMasterSoatController extends BaseController {
  public static final Logger log = Logger.getLogger(CargMasterSoatController.class);
  

  private List<TramaMaster> listaMasterPrecio;
  private List<ConsultaFechaCargaMaster> listaFechaCarga;
  private List<MasterPrecioSoat> listaMasterExport;
  private List<SelectItem> tipoSocioItems;
  @Autowired
  private CargMasterSoatService cargMasterSoatService;

  @Autowired
  private SocioService socioService;
 
  
  
  // 1.- Bandeja de carga Master
  private String socio = null;
  private Date fechaVigencia = null;
  private Date fechaCarga = null;
  private SimpleSelection selection;
  private Boolean flagBtnCargarSoat;
  private String msjValidarMaster = "";
  
  // 2.- Nuevo Master  
  private final static double fB = 1024.0;
  private Product product;
  private MasterPrecioSoat masterPrecioSoat;
  private String localidad = null;
  private boolean upload;
  private String nombreArchivo;
  private ArrayList<String> listaArchivos;
  private Date fechaInicio;
  private Date fechaFin;
  private File archivoFinal;
  private String idsocio;
  private Integer numero;
  
  private String numeroCargaMaster = "";
  
  //Parametros Paquetes
  private String usuarioDB="";
  @Autowired
  private ParametroAutomatService parametroService;
  private String NuevoURL_paquete;
  @Autowired
  private ProcesosETLsService procesosETLsService = null; 

  @Autowired
  private CargMasterSoatService cargTramaMasterSoatService = null;
  
  
  
  //TESTS
  private boolean buttonRendered = true;
  private boolean enabled=false;
  private Long startTime;
  private List<ConfLayout> listaLayout;
  
  private TramaEjecucion tramaEjecucion;
  
  //archivo
  //private String archivo;
  private  File archivo;
  private ArrayList<File> files = new ArrayList<File>();
  private int uploadsAvailable = 5;
  private boolean autoUpload = false;
  private boolean useFlash = false;
  
  private boolean flagBtnCargar;
  
  public int getSize() {
      if (getFiles().size()>0){
          return getFiles().size();
      }else 
      {
          return 0;
      }
  }

  // private boolean flagCarga;
  @PostConstruct
  @Override
  public String inicio() {
    log.info("Inicio");
    String respuesta = null;

    if (!tieneAcceso()) {
      log.debug("No cuenta con los accesos necesarios");
      return "accesoDenegado";
    }

    try {

      usuarioDB = SecurityContextHolder.getContext().getAuthentication().getName();
      product = new Product();
     // listaMasterPrecio = new ArrayList<TramaMaster>();
      listaFechaCarga = new ArrayList<ConsultaFechaCargaMaster>();
      listaMasterExport = null;
      listaArchivos = new ArrayList<String>();
      listaLayout = new  ArrayList<ConfLayout>();
      flagBtnCargar = false;
      
      // Get lista Socios      
      List<Socio> lstSocios = new ArrayList<Socio>();
      lstSocios = cargMasterSoatService.getListaSocios();
      
      tipoSocioItems = new ArrayList<SelectItem>();     
      listaMasterPrecio = new ArrayList<TramaMaster>();
      this.numeroCargaMaster = "0";
      
      //Validacion fecha
	  //this.fechaInicio = null;
	  //this.fechaFin = null;	  
      tipoSocioItems.add(new SelectItem("", "--Seleccionar--"));
      for (Socio socio : lstSocios) {
    	  if (log.isDebugEnabled()) {log.debug("SOCIO ==>ID: " + socio.getId() + " - NOMBRE_SOCIO: " + socio.getNombreSocio());}
    	  tipoSocioItems.add(new SelectItem(socio.getId().toString(),socio.getNombreSocio()));
    	  
      }
      
  	  //log.info("idsocio::"+socio);	
     // listaMasterPrecio = cargMasterSoatService.getListaCargaMaster(socio,fechaVigencia,fechaCarga);
      
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
      respuesta = MSJ_ERROR;
    }
    log.info("Fin");
    return respuesta;
  }

  public String btnConsultarCargaMaster(){
	  if(log.isInfoEnabled()){log.info("Inicio");}
	  log.debug("Entro :::::::: btnConsultarCargaMaster()");
	  String respuesta = null;		  		

	  		try {
	  			
				if (!validarFiltrosBusqueda()){}
	  			log.debug("Entro :::::::: btnConsultarCargaMaster():::::::: 2");
	  		  	if(log.isInfoEnabled()){ log.debug("Input [ 1.-"+idsocio+"  2.-"+fechaInicio +"  2.-"+fechaFin + " ]");}
	  		  	
	  		  	 
	  		      this.listaMasterPrecio = cargMasterSoatService.getListaCargaMaster(this.idsocio,this.fechaVigencia,this.fechaCarga);
	  		    this.numeroCargaMaster = String.valueOf(listaMasterPrecio.size());
	  		  if (log.isInfoEnabled()) {log.info("Se encontraron [" + (null != this.listaMasterPrecio ? this.listaMasterPrecio.size() : 0) + "] registros de CARGA.");}

  			}catch (Exception e) {				
				log.error("Exception ("+ e.getClass().getName() + ") - ERROR" + e.getMessage());
				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR,ErrorConstants.MSJ_ERROR_GENERAL,null);
				FacesContext.getCurrentInstance().addMessage(null,  facesMsg);
				respuesta = ErrorConstants.MSJ_ERROR;				
			}
	  		
	  		
	  if(log.isInfoEnabled()){log.info("fin");}
	  return respuesta;
  }
  
  // Llamado de Paquete 09/01/2017 --> Carga Master Soat
  @SuppressWarnings("unused")
	private int  progressBarStatus;	
	public String procesandoCarga() {
		
		String respuesta = "";
		int count = 0;
		progressBarStatus = 0;
		
		//ejecucionPakete();
		//tramaEjecucion = new TramaEjecucion();
		//tramaEjecucion.setFlagEstado("----");
		Thread thread = new Thread(new Runnable() {
			
		    @Override
		    public void run() {	
		    	try {
		    		int x=0;
		    	    
		    		procesosETLsService.procesosCargaMaster();
		    		
		    	    log.info("================================================== INICIO ====================================================");
				
		    	    do{		
			    
					setEnabled(true);
			        setButtonRendered(false);
			        setStartTime(new Date().getTime());			     
			        
					tramaEjecucion = cargTramaMasterSoatService.ejecucionTrama();
					x++;
					log.info("linea" + x );					
					
				    Thread.sleep(5000);
		      		
				}while(tramaEjecucion.getEstadoProceso().equalsIgnoreCase("pendiente"));	
		    	    
		    	    log.info("====================================================  FIN ================================================");
					
		    	} catch (InterruptedException e) {
		    		log.info("Mesagge "+e.getStackTrace());
				}catch (SyncconException e) {
					log.info("Mesegge" + e.getStackTrace());
				}
		    }
		            
		});
		        
		thread.start();
	
        
		return respuesta;
	}
	
	// Ejecutar Carga Trama Master  09/01/2017
	/*public String ejecutarCargaTramaMaster(){
		
		log.info("Ingreso al ejecutar Control Paquete");
		String respuesta ="";
		Long   tramaID = null;
		if (log.isInfoEnabled()) {log.info("Inicio....");}		
		try {
			
			log.debug("Input [ 1.-"+ BeanUtils.describe(listaLayout) +" ]");
			tramaEjecucion = new TramaEjecucion();			
			tramaID = cargTramaMasterSoatService.selectIdTrama(SateliteConstants.FLAG_TRAMA_MASTER);			
			log.debug("tramaID :" + tramaID);
			ControlPaquete controlPaquete = new ControlPaquete();
			controlPaquete.setEstado(0);
			controlPaquete.setFechaCreacion(Calendar.getInstance().getTime());
			controlPaquete.setFechaInicio(Calendar.getInstance().getTime());
			controlPaquete.setFlagEjecucion(0);
			controlPaquete.setTotalRegistro(0);
			controlPaquete.setUsuarioCreacion(SecurityContextHolder.getContext().getAuthentication().getName().toUpperCase());
			controlPaquete.setProceso(SateliteConstants.SUSCRIPCION_TRAMA_MASTER_FLAG);
			controlPaquete.setIdTrama(Integer.valueOf(tramaID.toString()));	
			setEnabled(false);
			
			if(cargTramaMasterSoatService.insertarControlPaquete(controlPaquete)){
					tramaEjecucion = cargTramaMasterSoatService.ejecucionTrama();
					respuesta = SateliteConstants.SIGUIENTE_CONTROL_PAQUETE;									
			}
			
			log.debug("Output [ 1.- ]");
			
		} catch (Exception e){
					log.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
					log.error("SyncconException() -->" + ExceptionUtils.getStackTrace(e));
					//log.error(message);
					FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
					FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			    	respuesta = ErrorConstants.MSJ_ERROR;
		} 	
		
		if (log.isInfoEnabled()) {log.info("Fin");}
			
		return respuesta;
	}
	*/
  
  public String btnRegistrarArchivoMaster(){
	  if(log.isInfoEnabled()){log.info("Inicio");}
	  	
	  	String respuesta=null;
	  	Boolean status = false;
	  	Boolean validaStatus = false;
		Long   tramaID = null;
	  
	  	try {			
	  		//String archivo=null;	
		  	log.debug("Input [ 1.- ]");
		  	// validar fecha activa de vigencia de la carga Master	
		
		  	log.info("nombreArchivo::"+nombreArchivo);	   
		  	
		  	//VALIDACION DE FECHAS [INI]			
			if(this.fechaInicio != null && this.fechaFin!=null){
				if(fechaFin.before(fechaInicio)){
					//throw new SyncconException(ErrorConstants.COD_ERROR_RANGO_FECHAS, FacesMessage.SEVERITY_INFO);                    
					FacesMessage faces = new FacesMessage(FacesMessage.SEVERITY_INFO,"La Fecha / Hora Inicio no debe ser mayor a la Fecha/ Hora Fin", null);
			  		FacesContext.getCurrentInstance().addMessage(null, faces);
			  		msjValidarMaster="*";
			  		return respuesta;

				}
			}			  	
		    //VALIDACION DE FECHAS [FIN]
		  	
		  	if (nombreArchivo == null || nombreArchivo.isEmpty()){
		  		SateliteUtil.mostrarMensaje(SateliteConstants.MENSAJE_CARGADO_ARCHIVO);
		  		msjValidarMaster="*";
		  	}else{

		  		
		  		
		  	if(!nombreArchivo.endsWith(SateliteConstants.NomeclaturaExcelMaster)){
		  		
		  		FacesMessage faces = new FacesMessage(FacesMessage.SEVERITY_INFO,"El nombre del archivo no cumple con la nomenclatura 'Master_Final.xlsx'", null);
		  		FacesContext.getCurrentInstance().addMessage(null, faces);
		  		msjValidarMaster="*";
		  		return respuesta;
		  	}    
		    //Debe cargar un archivo
		  	
		  	if (validarHojaArchivo(archivoFinal,nombreArchivo.toUpperCase())) {

		  	validaStatus = cargMasterSoatService.selectValidarMaster(socio);//idsocio	
		  	log.debug("validaStatus"+validaStatus);
		  	log.debug("archivo"+nombreArchivo);
		  	msjValidarMaster="";
		  	
		  	if(validaStatus==true){
		  		
		  		SateliteUtil.mostrarMensaje(FacesMessage.SEVERITY_INFO, "Ya existe una conciliación cerrada para este Periodo ");
		  		
		  	}else if(validaStatus==false){
		  		
		  		// Creando la ruta del servidor/ donde se almacena el archivo 09/01/2017
		  		
		  		List<ParametroAutomat> listaTipoTramas = parametroService.buscarPorCodParam(Constantes.SUSCRIPCION_RUTA_SERVIDOR_SSIS);
			    String rutaTemp ="";
			    String rutaPaquete ="";
			    Long date = Calendar.getInstance().getTimeInMillis();
			    
				for (ParametroAutomat parametroAutomat : listaTipoTramas) {
					if(parametroAutomat.getNumOrden()==1){
						rutaTemp = parametroAutomat.getNombreValor() + Constantes.SUSCRIPCION_RUTA_SERVIDOR_SSIS_MASTER  + "TRAMA_MASTER_" + date +"_"+ nombreArchivo;
					}else if(parametroAutomat.getNumOrden()==2){

						rutaPaquete = parametroAutomat.getNombreValor() + Constantes.SUSCRIPCION_RUTA_SERVIDOR_SSIS_MASTER  + "TRAMA_MASTER_" + date +"_"+ nombreArchivo;
					}
				}
				
				NuevoURL_paquete = rutaPaquete;
				log.info("rutaTemp --- >" + rutaTemp);
					log.info("archivoFinal.getPath()  --- >" +  archivoFinal.getPath() + "  rutaTemp ---->" + rutaTemp);
				Utilitarios.copyFiles(new File(archivoFinal.getPath()),new File(rutaTemp));
	  		
			  	// registrar nueva carga Master	
			  	TramaMaster master = new TramaMaster();	
			  	 
			  	master.setIdSocio(Integer.parseInt(socio));
			  	master.setRutaArchivo(nombreArchivo);
			  	master.setFechaInicio(fechaInicio);
			  	master.setFechaFin(fechaFin);
			  	master.setUsuarioCreacion(usuarioDB);
			  	master.setNumRegistros(numero);
			  	master.setFechaCreacion(Calendar.getInstance().getTime());
			  	master.setEstadoMaster(SateliteConstants.TRAMA_MASTER_VIGENTE);
			  	master.setEstado(Integer.parseInt(SateliteConstants.ESTADO_REGISTRO_ACTIVO));
			  	
			  	status = cargMasterSoatService.registrarArchivoMaster(master);
			  	log.info("STATUS:::"+status);
			  	
			  	// Insertar Control Paquete --> 10/01/17
			  	//ejecutarCargaTramaMaster();
			  	
			  	//log.debug("Input [ 1.-"+ BeanUtils.describe(listaLayout) +" ]");
				tramaEjecucion = new TramaEjecucion();			
				log.debug("IDSOCIO :" + Integer.parseInt((socio)));
				tramaID = cargTramaMasterSoatService.selectIdTrama(SateliteConstants.FLAG_TRAMA_MASTER,Integer.parseInt((socio)));			
				log.debug("tramaID :" + tramaID);
				ControlPaquete controlPaquete = new ControlPaquete();
				controlPaquete.setEstado(1);
				controlPaquete.setFechaCreacion(Calendar.getInstance().getTime());
				controlPaquete.setFechaInicio(Calendar.getInstance().getTime());
				controlPaquete.setFlagEjecucion(0);
				controlPaquete.setTotalRegistro(0);
				controlPaquete.setUsuarioCreacion(SecurityContextHolder.getContext().getAuthentication().getName().toUpperCase());
				controlPaquete.setProceso(SateliteConstants.SUSCRIPCION_TRAMA_MASTER_FLAG);
				controlPaquete.setIdTrama(Integer.valueOf(tramaID.toString()));	
				setEnabled(false);
				
				if(cargTramaMasterSoatService.insertarControlPaquete(controlPaquete)){
						tramaEjecucion = cargTramaMasterSoatService.ejecucionTrama();
						respuesta = SateliteConstants.SIGUIENTE_CONTROL_PAQUETE;									
				}
			  			 	
			  	//Limpiar datos del modal Suscripción > SOAT > Master SOAT
			  	this.socio = null;
			  	log.info("idsocio::"+socio);	
			  	this.fechaInicio = null;
			  	log.info("fechaInicio::"+fechaInicio);	
			  	this.fechaInicio = null;
			  	log.info("fechaFin::"+fechaFin);	
			  	msjValidarMaster="";
			  	
		  		if(status){
		  			
		  			//invocar al Job 10/01/2017
		  			procesandoCarga();
		  			
		  			// --------------
		  			btnConsultarCargaMaster();
		  			log.info("btnConsultarCargaMaster()::::::  ");
		  			SateliteUtil.mostrarMensaje(FacesMessage.SEVERITY_INFO, "La carga finalizó correctamente!");
		  		}	
		  	  }
		  	
		  	 }else{
		        	SateliteUtil.mostrarMensaje("Hoja de archivo incorrecta, modificar a 'hoja1'");
		        }
		  	

		  	}
		  		
		  	if(log.isInfoEnabled()){ log.info("respuesta status :" + status);}		  	
		  	log.debug("Output [ 1.- ]");
		  	
	  	} catch (Exception e) {
	  		log.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
	    	respuesta = ErrorConstants.MSJ_ERROR;		}
	  	  	
	  if(log.isInfoEnabled()){log.info("Fin");}
	  
	  return respuesta;
  }

  
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
				 
			}
			
			if(sheetName.equals("hoja1")){
				sw = true;
			}else{
				sw=false;
			}
			log.info("Nombre de la Hoja: "+sheetName);
		    log.info("tipo.toUpperCase()" + tipo.toUpperCase());
		    log.info("status validacion" + sw);
			
		} catch (Exception e) {
			log.info(e.getStackTrace());
		}

	   
		
		return sw;
		
	}
  
  
  
  
	private boolean validarFiltrosBusqueda()
	{log.debug("Entro :::::::: validarFiltrosBusqueda():::::::: 2");
		if (log.isDebugEnabled()) {log.info("Inicio - Fin");}
		boolean flag = true;
		
		if ((StringUtils.isBlank(socio)  && null == fechaVigencia && null == fechaCarga))
		{
			log.debug("socio:  "+socio);
			log.debug("fechaVigencia:  "+fechaVigencia);
			log.debug("fechaCarga:  "+fechaCarga);
			flag = false;
		}
		return flag;
	} //validarFiltrosBusqueda
	
  public String btnConsultarArchivoMaster(){
	  if(log.isInfoEnabled()){log.info("Inicio");}
	  		String respuesta = null;
	  		List<TramaDiaria> lista = new ArrayList<TramaDiaria>();

	  		try {
			
	  			log.debug("Input [1.-]");	  			
  		  	 	lista = cargMasterSoatService.consultarArchivoBySocio(socio);	  			
	  			if(log.isInfoEnabled()){log.debug("Output [1.- Respuesta "+ lista.size()+"]");}
	  			
			} catch (Exception e) {
				log.error("Exception (" + e.getClass().getName()+") - ERROR" + e.getMessage());
				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
				FacesContext.getCurrentInstance().addMessage(null, facesMsg);
				respuesta = ErrorConstants.MSJ_ERROR;
			}  		
	  			  		
	  if(log.isInfoEnabled()){log.info("Fin");}
	  return respuesta;
  }
  
  public String btnEliminarArchivoMaster(){
	  if(log.isInfoEnabled()){log.info("Inicio");}
	  		String respuesta = null;
	  		Boolean status = false;
	  		try {				
	  			String idMaster ="";
	  		    FacesContext fc = FacesContext.getCurrentInstance();
		  	    Map<String,String> params =   fc.getExternalContext().getRequestParameterMap();
		  	    idMaster =  params.get("idMasterDEL"); 
		  	    
		  	  	    
		  	 		  	    
	  			if(log.isInfoEnabled()){log.debug("Input [1.- " +idMaster+ " ]");}	  
	  			
	  				status = cargMasterSoatService.eliminarArchivoMaster(idMaster);	 
	  	
	  				if(status){	
	  					btnConsultarCargaMaster();
	  					}
	  			if(log.isInfoEnabled()){log.debug("Output [1.- status "+status+" ]");}
	  			
			} catch (Exception e) {
				log.error("Exception (" + e.getClass().getName() + ") - ERROR" + e.getMessage());
				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
				FacesContext.getCurrentInstance().addMessage(null, facesMsg);
				respuesta = ErrorConstants.MSJ_ERROR;
			}	  		
	  		
	  if(log.isInfoEnabled()){log.info("Fin");}
	  return respuesta;
  }
  
  public String btnExportarCargaMaster(String  idMaster){
	  if(log.isInfoEnabled()){log.info("Inicio");}
	  
	  
	 
	  	 String respuesta = null;
	  	 Boolean status = false;
	  	 try {		 	
	  		 	FacesContext fc = FacesContext.getCurrentInstance();
	  		 //	Map<String,String> params = fc.getExternalContext().getInitParameterMap();
	  		 //	idMaster = params.get("idMasterDEL");
	  		 	log.info("-----------------------------------------------------------------------------------------------------------------------------------------");
	  		 	if(log.isInfoEnabled()){log.debug("Input [1.-"+idMaster+" ]");}	  

	  		 	log.info("-----------------------------------------------------------------------------------------------------------------------------------------");
	  		 	
	  		 	Map<String, Object> beans = new HashMap<String, Object>();
	  		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	  			ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
	  			String FechaActual = sdf.format(new Date(System.currentTimeMillis()));
	  			 
	  		 	List<ConsultaMasterPrecio> lista = new ArrayList<ConsultaMasterPrecio>();	  		 	
	  		 	lista = cargMasterSoatService.consultaMasterDetalle(idMaster);	
	  		 	
	  		 	String rutaTemp = System.getProperty("java.io.tmpdir") + "Reporte_Detalle_Master_" + System.currentTimeMillis() + ".xls";
			    log.debug("Ruta Archivo: " + rutaTemp);
		       
		        ServletContext sc = (ServletContext) fc.getExternalContext().getContext();
		        String rutaTemplate = sc.getRealPath(File.separator + "excel" + File.separator + "template_exportar_detalle_master.xls");
		        beans.put("exportar", lista);
		        XLSTransformer transformer = new XLSTransformer();
		        transformer.transformXLS(rutaTemplate, beans, rutaTemp);
		        File archivoResp = new File(rutaTemp);
		        FileInputStream in = new FileInputStream(archivoResp);
		        HttpServletResponse response = (HttpServletResponse) contexto.getResponse();
		        byte[] loader = new byte[(int) archivoResp.length()];
		        response.addHeader("Content-Disposition", "attachment;filename=" + "Reporte_Detalle_Master_" + FechaActual + ".xls");
		        response.setContentType("application/vnd.ms-excel");
		        ServletOutputStream out = response.getOutputStream();

		        while ((in.read(loader)) > 0) {
		          out.write(loader, 0, loader.length);
		        }
		        
		        in.close();
		        out.close();
				
		        FacesContext.getCurrentInstance().responseComplete();
	  		 	
	  		 	if(log.isInfoEnabled()){log.debug("Output [2.-"+lista.size()+"]");}
	  	 
		} catch (Exception e) {
			log.info(e.getStackTrace());
			log.error("Exception  (" + e.getClass().getName() +") - ERROR"+ e.getMessage());
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = ErrorConstants.MSJ_ERROR;
		}
	  if(log.isInfoEnabled()){log.info("Fin");}
	  return respuesta;
  }
  
	  
	  @SuppressWarnings("unused")
	private static double getFileSizeInMB (double f) {
	        f = f / Math.pow(fB,2);
	        int fs = (int) Math.pow(10,2);
	        return Math.rint(f*fs)/fs;
	    }
	  
  
  public String listener(UploadEvent event){
    log.info("CargMAsterSoatController::::::: Inicio listener");
	  	
    String respuesta = null;
   
   
    try {

      nombreArchivo = new String();
      UploadItem item = event.getUploadItem();     
      File archivo = item.getFile();
      
     // double fL = archivo.length();
      //double sizeMB = getFileSizeInMB(fL);
      
     // log.info("Peso archivo: " + sizeMB);
      
      //if(sizeMB>50.00){
    	 // log.info("Peso archivo excedio: " + sizeMB);
    	  //msjValidarMaster="*";
    	  //SateliteUtil.mostrarMensaje(SateliteConstants.MENSAJE_CARGADO_ARCHIVO_MAX);
      //}else{
      
	      nombreArchivo = item.getFileName().substring(item.getFileName().lastIndexOf("\\") + 1);
	     // log.info("Nombre de Archivo: " + nombreArchivo);
	      archivoFinal = File.createTempFile("Master_", nombreArchivo);      
          
	      FileUtils.copyFile(archivo, archivoFinal);
	      archivo.delete();
	      numero =  cargMasterSoatService.numeroRegistrosExcel(archivoFinal);
	      flagBtnCargar=false;
	      log.info("flagBtnCargar: "+flagBtnCargar);
      //}

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
      respuesta = MSJ_ERROR;
    }

    log.debug("Fin");
    return respuesta;
  }
  


public Integer getNumero() {
	return numero;
}

public void setNumero(Integer numero) {
	this.numero = numero;
}


/**
 * @return the fechaInicio
 */
public Date getFechaInicio() {
	return fechaInicio;
}


/**
 * @param fechaInicio the fechaInicio to set
 */
public void setFechaInicio(Date fechaInicio) {
	this.fechaInicio = fechaInicio;
}


/**
 * @return the fechaFin
 */
public Date getFechaFin() {
	return fechaFin;
}


/**
 * @param fechaFin the fechaFin to set
 */
public void setFechaFin(Date fechaFin) {
	this.fechaFin = fechaFin;
}


/**
 * @return the usuarioDB
 */
public String getUsuarioDB() {
	return usuarioDB;
}


/**
 * @param usuarioDB the usuarioDB to set
 */
public void setUsuarioDB(String usuarioDB) {
	this.usuarioDB = usuarioDB;
}


public List<SelectItem> getTipoSocioItems() {
    return tipoSocioItems;
  }

  public void setTipoSocioItems(List<SelectItem> tipoSocioItems) {
    this.tipoSocioItems = tipoSocioItems;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }
  
  public MasterPrecioSoat getMasterPrecioSoat() {
    return masterPrecioSoat;
  }

  public void setMasterPrecioSoat(MasterPrecioSoat masterPrecioSoat) {
    this.masterPrecioSoat = masterPrecioSoat;
  }

  public String getSocio() {
    return socio;
  }

  public void setSocio(String socio) {
    this.socio = socio;
  }

  public SimpleSelection getSelection() {
    return selection;
  }

  public void setSelection(SimpleSelection selection) {
    this.selection = selection;
  }

  public boolean isUpload() {
    return upload;
  }

  public void setUpload(boolean upload) {
    this.upload = upload;
  }

  public String getNombreArchivo() {
    return nombreArchivo;
  }

  public void setNombreArchivo(String nombreArchivo) {
    this.nombreArchivo = nombreArchivo;
  }

  public File getArchivoFinal() {
    return archivoFinal;
  }

  public void setArchivoFinal(File archivoFinal) {
    this.archivoFinal = archivoFinal;
  }
  public List<MasterPrecioSoat> getListaMasterExport() {
    return listaMasterExport;
  }

  public void setListaMasterExport(List<MasterPrecioSoat> listaMasterExport) {
    this.listaMasterExport = listaMasterExport;
  }

  public String getLocalidad() {
    return localidad;
  }

  public void setLocalidad(String localidad) {
    this.localidad = localidad;
  }

  public ArrayList<String> getListaArchivos() {
    return listaArchivos;
  }

  public void setListaArchivos(ArrayList<String> listaArchivos) {
    this.listaArchivos = listaArchivos;
  }

  public List<ConsultaFechaCargaMaster> getListaFechaCarga() {
    return listaFechaCarga;
  }

  public void setListaFechaCarga(List<ConsultaFechaCargaMaster> listaFechaCarga) {
    this.listaFechaCarga = listaFechaCarga;
  }


/**
 * @return the fechaVigencia
 */
public Date getFechaVigencia() {
	return fechaVigencia;
}


/**
 * @param fechaVigencia the fechaVigencia to set
 */
public void setFechaVigencia(Date fechaVigencia) {
	this.fechaVigencia = fechaVigencia;
}


/**
 * @return the fechaCarga
 */
public Date getFechaCarga() {
	return fechaCarga;
}


/**
 * @param fechaCarga the fechaCarga to set
 */
public void setFechaCarga(Date fechaCarga) {
	this.fechaCarga = fechaCarga;
}


public List<TramaMaster> getListaMasterPrecio() {
	return listaMasterPrecio;
}


public void setListaMasterPrecio(List<TramaMaster> listaMasterPrecio) {
	this.listaMasterPrecio = listaMasterPrecio;
}


public String getIdsocio() {
	return idsocio;
}


public void setIdsocio(String idsocio) {
	this.idsocio = idsocio;
}





public Boolean getFlagBtnCargarSoat() {
	return flagBtnCargarSoat;
}


public void setFlagBtnCargarSoat(Boolean flagBtnCargarSoat) {
	this.flagBtnCargarSoat = flagBtnCargarSoat;
}


public String getMsjValidarMaster() {
	return msjValidarMaster;
}


public void setMsjValidarMaster(String msjValidarMaster) {
	this.msjValidarMaster = msjValidarMaster;
}




public ArrayList<File> getFiles() {
	return files;
}


public void setFiles(ArrayList<File> files) {
	this.files = files;
}


public int getUploadsAvailable() {
	return uploadsAvailable;
}


public void setUploadsAvailable(int uploadsAvailable) {
	this.uploadsAvailable = uploadsAvailable;
}


public boolean isAutoUpload() {
	return autoUpload;
}


public void setAutoUpload(boolean autoUpload) {
	this.autoUpload = autoUpload;
}


public boolean isUseFlash() {
	return useFlash;
}


public void setUseFlash(boolean useFlash) {
	this.useFlash = useFlash;
}

public boolean isFlagBtnCargar() {
	return flagBtnCargar;
}

public void setFlagBtnCargar(boolean flagBtnCargar) {
	this.flagBtnCargar = flagBtnCargar;
}

public boolean isButtonRendered() {
	return buttonRendered;
}

public void setButtonRendered(boolean buttonRendered) {
	this.buttonRendered = buttonRendered;
}

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

public String getNuevoURL_paquete() {
	return NuevoURL_paquete;
}

public void setNuevoURL_paquete(String nuevoURL_paquete) {
	NuevoURL_paquete = nuevoURL_paquete;
}

public List<ConfLayout> getListaLayout() {
	return listaLayout;
}

public void setListaLayout(List<ConfLayout> listaLayout) {
	this.listaLayout = listaLayout;
}

public String getNumeroCargaMaster() {
	return numeroCargaMaster;
}

public void setNumeroCargaMaster(String numeroCargaMaster) {
	this.numeroCargaMaster = numeroCargaMaster;
}
  


  
  
}
