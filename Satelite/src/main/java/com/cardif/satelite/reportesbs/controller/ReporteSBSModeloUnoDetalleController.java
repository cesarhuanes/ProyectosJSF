package com.cardif.satelite.reportesbs.controller;

import static com.cardif.satelite.constantes.ErrorConstants.MSJ_ERROR;
import static com.cardif.satelite.constantes.ErrorConstants.MSJ_ERROR_GENERAL;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.richfaces.model.selection.SimpleSelection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import com.cardif.framework.controller.BaseController;
import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.constantes.Constantes;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.reportesbs.CargaOperaciones;
import com.cardif.satelite.model.reportesbs.FirmanteCargo;
import com.cardif.satelite.model.reportesbs.LogErroresReportes;
import com.cardif.satelite.model.reportesbs.Parametro;
import com.cardif.satelite.model.reportesbs.ParametrosSBSModeloUnoDetalle;
import com.cardif.satelite.model.reportesbs.ReporteSBSModeloUnoDetalle;
import com.cardif.satelite.model.reportesbs.ReportesGeneradosSBSModeloUnoDetalle;
import com.cardif.satelite.model.reportesbs.Saldos;
import com.cardif.satelite.reportesbs.bean.JournalBeanReportes;
import com.cardif.satelite.reportesbs.bean.ListaCuentasDataSun;
import com.cardif.satelite.reportesbs.bean.ListaReporte;
import com.cardif.satelite.reportesbs.bean.ListaReportesGeneradosBean;
import com.cardif.satelite.reportesbs.service.LogErroresReportesService;
import com.cardif.satelite.reportesbs.service.ProcesoArchivoService;
import com.cardif.satelite.reportesbs.service.ReporteSBSModeloUnoDetalleService;
import com.cardif.satelite.reportesbs.service.SaldosService;
import com.cardif.satelite.util.SateliteUtil;



@Controller("reporteSBSModeloUnoDetalleController")
@Scope("request")
public class ReporteSBSModeloUnoDetalleController extends BaseController{

	private static final Logger logger = Logger.getLogger(ReporteSBSModeloUnoDetalleController.class);
	
	//NUMERO DE FILAS
	Long codigoReporteReprocesar;
	boolean creadoHoja = false;
	int filasExcel=0;
	int filasMillon=0;
	Saldos objSaldosBean = new Saldos();
	Double saldoDeudorFormula = null;			        
    Double saldoAcreedorFormula = null;
	//rivate HSSFWorkbook libro;
	private String modeloRepore;
	//private String nomFirmanteUno;
	//private String nomFirmanteDos;
	private String cargoFirmanteUno;
	private String cargoFirmanteDos;
	private Date periodoDesde;
	private Date periodoHasta;
	private BigDecimal tipoCambio;
	
	private String firmanteUno;
	private String firmanteDos;
	private String periodoAnio;
	private String periodoAnio2;
	private String periodoTrimestre;
	private String periodoTrimestre2;
	private String estadoReporte;
	private String codigoReporte;
	private String usuarioReporte;
	
	private List<Parametro> listaParametroTrimestre = new ArrayList<Parametro>();
	private List<Parametro> listaEstadoReporte = new ArrayList<Parametro>();
	private List<Parametro> listaParametroAnio = new ArrayList<Parametro>();
	private List<Parametro> listaReporteModelo = new ArrayList<Parametro>();
	
	private List<ListaReportesGeneradosBean> listaReportesGeneradosBean = new ArrayList<ListaReportesGeneradosBean>();
	

	private List<ReportesGeneradosSBSModeloUnoDetalle> listaReporte = new ArrayList<ReportesGeneradosSBSModeloUnoDetalle>();
	
	private List<CargaOperaciones>  cargaOperacionesList= new ArrayList<CargaOperaciones>();
	
	XSSFWorkbook libro = new XSSFWorkbook();					
    XSSFSheet hoja = null;
	
	private List<ListaReporte> objListaReporte = new ArrayList<ListaReporte>();
	
	private List<ListaCuentasDataSun> objListaCuentasDataSunTotal = new ArrayList<ListaCuentasDataSun>();
	private List<ListaCuentasDataSun> objListaCuentasDataSun = new ArrayList<ListaCuentasDataSun>();
	private List<ListaCuentasDataSun> objListaCuentasDataSunCuentasPago = new ArrayList<ListaCuentasDataSun>();
	private List<ListaCuentasDataSun> objListaCuentasDataSunCuentasCuarenta = new ArrayList<ListaCuentasDataSun>();
	
	//private List<ListaCuentasDataSun> objListaCuentasDataSunSinReversion = new ArrayList<ListaCuentasDataSun>();
	
	private List<List<JournalBeanReportes>> splistaProcesos;
	private volatile List<JournalBeanReportes> listaProcesos;
	
	private List<SelectItem> tipoReporteparametrosItems;
	private List<SelectItem> estadoReporteparametrosItems; 
	private List<SelectItem> periodoReporteparametrosItems; 
	private List<SelectItem> anioReporteparametrosItems; 
	
	private String tipoReporteParametro;
	private String tipoReporteParametro2;
	private SimpleSelection selection;
	
	@Autowired
	private ReporteSBSModeloUnoDetalleService reporteSBSModeloUnoDetalleService;

	@Autowired
	private ProcesoArchivoService procesoArchivoService;

	@Autowired(required = true)
	private ReporteSBSModeloUnoDetalleService reporteSBSModeloUnoDetalle;
	
	@Autowired(required = true)
	private LogErroresReportesService logErroresReportesService;
	
	@Autowired(required = true)
	private SaldosService saldosService;
	
	@Override
	@PostConstruct
	public String inicio() {
		
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		String respuesta = null;
		
		if (!tieneAcceso())
		{
			if (logger.isDebugEnabled()) {logger.debug("No cuenta con los accesos necesarios.");}
			return "accesoDenegado";
		}
		
		selection = new SimpleSelection();
		firmanteDos="";
		firmanteUno="";
		cargoFirmanteDos="";
		cargoFirmanteUno="";
		tipoReporteParametro="";
		listaReportesGeneradosBean=new ArrayList<ListaReportesGeneradosBean>();
		objListaReporte = null;
		loadData();
		
		//objListaReporte = null;
		
		//listaProcesos = new CopyOnWriteArrayList<JournalBeanReportes>();
		
		//INSTANCIANDO SERVICES

		return respuesta;		
	}// FIN |INICIO 

	public String limpiar(){
		
		String respuesta = null;
		selection = new SimpleSelection();
		firmanteDos = "";
		firmanteUno = "";
		cargoFirmanteDos = "";
		cargoFirmanteUno = "";
		tipoReporteParametro = "";
		listaReportesGeneradosBean = new ArrayList<ListaReportesGeneradosBean>();
		reloadContext();

		return respuesta;
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
	
	public void loadData(){
		
		try {
			
			listaEstadoReporte = reporteSBSModeloUnoDetalleService.selectParametroListByPrimaryKey("15");
		    estadoReporteparametrosItems = new ArrayList<SelectItem>();
		    estadoReporteparametrosItems.add(new SelectItem("", "- Seleccionar -"));
		    for (Parametro listaEstadoReporte : listaEstadoReporte) {
		    	estadoReporteparametrosItems.add(new SelectItem(listaEstadoReporte.getCodParametro(),listaEstadoReporte.getDescripcion()));
			}
			
			listaParametroTrimestre = reporteSBSModeloUnoDetalleService.selectParametroListByPrimaryKey("14");			
			periodoReporteparametrosItems = new ArrayList<SelectItem>();
			periodoReporteparametrosItems.add(new SelectItem("", "- Seleccionar -"));
			for (Parametro listaParametroTrimestre : listaParametroTrimestre) {				
				periodoReporteparametrosItems.add(new SelectItem(listaParametroTrimestre.getCodParametro(),listaParametroTrimestre.getDescripcion()));				
			}
		
			listaParametroAnio = reporteSBSModeloUnoDetalleService.selectParametroListByPrimaryKey("13");			
			anioReporteparametrosItems = new ArrayList<SelectItem>(); 
			anioReporteparametrosItems.add(new SelectItem("", "- Seleccionar -"));
			
			Calendar c2 = new GregorianCalendar();
				
			int annio = c2.get(Calendar.YEAR);
			
			for (int i = 0; i < 5; i++) {
								
				anioReporteparametrosItems.add(new SelectItem(annio - i, String.valueOf((annio - i))));
			}
			
			/*for (Parametro listaParametroAnio : listaParametroAnio) {				
				anioReporteparametrosItems.add(new SelectItem(listaParametroAnio.getCodParametro(),listaParametroAnio.getDescripcionValor()));				
			} */
		    					
			// CARGAMOS LA LISTA DE REPORTES
			
			
			/*
			listaReporte = reporteSBSModeloUnoDetalleService.selectListReporte();
												
			ListaReportesGeneradosBean reportesGeneradosBean = null;
			
			for (ReportesGeneradosSBSModeloUnoDetalle objListaReporte : listaReporte) {
				
				reportesGeneradosBean = new ListaReportesGeneradosBean();
			
				reportesGeneradosBean.setCodigoProceso(objListaReporte.getCodReporte().longValue());
				reportesGeneradosBean.setTipoReporte(reporteSBSModeloUnoDetalleService.selectParametroByPrimaryKey(objListaReporte.getCodTipoReporte()).getDescripcion());
				reportesGeneradosBean.setPeriodo(reporteSBSModeloUnoDetalleService.selectParametroByPrimaryKey(objListaReporte.getPeriodoTrimestre()).getDescripcion());
				reportesGeneradosBean.setAnio(objListaReporte.getPeriodoAnio());
				reportesGeneradosBean.setTipoCambio(String.valueOf(objListaReporte.getTipoCambio()));
				reportesGeneradosBean.setResultado(String.valueOf(objListaReporte.getEstado()));
				reportesGeneradosBean.setUsuarioProceso(objListaReporte.getUsuarioCreador());
				reportesGeneradosBean.setFechaRegistro(objListaReporte.getFechaRegistro());
				
				listaReportesGeneradosBean.add(reportesGeneradosBean);
			}
			
			*/
			
			tipoReporteparametrosItems = new ArrayList<SelectItem>();
			tipoReporteparametrosItems.add(new SelectItem("", "-Seleccionar-"));
			listaReporteModelo = reporteSBSModeloUnoDetalleService
					.listarReporteModelo("10");
			for (Parametro x : listaReporteModelo) {
				tipoReporteparametrosItems.add(new SelectItem(x
						.getCodParametro(), x.getDescripcion()));
			}
			
							
		} catch (SyncconException e) {
			log.error(e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		}
	}
	
	
	public String buscarReportes(){
		
		log.info("llegue");
		log.info(codigoReporte);
		log.info(usuarioReporte);
		log.info(tipoReporteParametro);
		log.info(periodoAnio);
		log.info(periodoTrimestre);
		log.info(estadoReporte);
		try {		
			
			// CARGAMOS LA LISTA DE REPORTES
			List<ReportesGeneradosSBSModeloUnoDetalle> listaReporteFiltros = null;
					
			//listaReporteFiltros  = reporteSBSModeloUnoDetalleService.consultarReportes(codigoReporte,usuarioReporte,tipoReporteParametro,periodoAnio,periodoTrimestre,estadoReporte);															
			listaReporteFiltros  = reporteSBSModeloUnoDetalleService.consultarReportes(codigoReporte,usuarioReporte,tipoReporteParametro2,periodoAnio2,periodoTrimestre2,estadoReporte);																		
			ListaReportesGeneradosBean reportesGeneradosBean = null;
		
			listaReportesGeneradosBean = new ArrayList<ListaReportesGeneradosBean>();
			
			
			 for (ReportesGeneradosSBSModeloUnoDetalle objListaReporte : listaReporteFiltros) {
							
			      reportesGeneradosBean = new ListaReportesGeneradosBean();
											
				  reportesGeneradosBean.setCodigoProceso(objListaReporte.getCodReporte().longValue());
				  reportesGeneradosBean.setTipoReporte(reporteSBSModeloUnoDetalleService.selectParametroByPrimaryKey(objListaReporte.getCodTipoReporte()).getDescripcion());
				  reportesGeneradosBean.setPeriodo(reporteSBSModeloUnoDetalleService.selectParametroByPrimaryKey(objListaReporte.getPeriodoTrimestre()).getDescripcion());
				  reportesGeneradosBean.setAnio(objListaReporte.getPeriodoAnio());
				  reportesGeneradosBean.setTipoCambio(String.valueOf(objListaReporte.getTipoCambio()));
				  reportesGeneradosBean.setResultado(String.valueOf(objListaReporte.getEstado()));
				  reportesGeneradosBean.setUsuarioProceso(objListaReporte.getUsuarioCreador());
				  reportesGeneradosBean.setFechaRegistro(objListaReporte.getFechaRegistro());
							
				  listaReportesGeneradosBean.add(reportesGeneradosBean);					
		     }							
		
		} catch (Exception e) {
			log.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			log.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
		}
	
		return "";
	}
	
	public void actionPasarDatos(ActionEvent event){
		
		String metodo = (String) event.getComponent().getAttributes().get("codigoMetodo");
		Long codigoReporte;
		
		if(metodo.equalsIgnoreCase("descargarLog")){
			
			codigoReporte = (Long)event.getComponent().getAttributes().get("codigoReporteDescar");
				
			log.info("codigoReporte :"+codigoReporte);
			
			descargarLog(String.valueOf(codigoReporte));
		}
		
		
		if(metodo.equalsIgnoreCase("reprocesar")){
			
			
			codigoReporteReprocesar  = (Long)event.getComponent().getAttributes().get("codigoReporteDescar");
			/*
			log.info("codigoReporte :"+codigoReporte);			
			reprocesar(String.valueOf(codigoReporte));
			*/
		}
		
		if(metodo.equalsIgnoreCase("buscarArchivo")){
			
			
			codigoReporte = (Long)event.getComponent().getAttributes().get("codigoReporteDescar");
			
			log.info("codigoReporte :"+codigoReporte);
			
			buscarArchivoReporte(String.valueOf(codigoReporte));
			
		}
					
	}	
	
	public String buscarArchivoReporte(String codigoReporte){
	
		List<ReportesGeneradosSBSModeloUnoDetalle> listaReporteFiltros = null;
		ReportesGeneradosSBSModeloUnoDetalle objBeanReportes= null;			
		
	    listaReporteFiltros  = reporteSBSModeloUnoDetalleService.consultarReportes(codigoReporte,"","","","","");
	
       if(listaReporteFiltros.size()>0){
    	   
    	   for (int i = 0; i < listaReporteFiltros.size(); i++) {
    		
    		   objBeanReportes = new ReportesGeneradosSBSModeloUnoDetalle();
    		   
    		   objBeanReportes.setCargoFirmante1(listaReporteFiltros.get(i).getCargoFirmante1());
    		   objBeanReportes.setCargoFirmante2(listaReporteFiltros.get(i).getCargoFirmante2());
    		   objBeanReportes.setCodReporte(listaReporteFiltros.get(i).getCodReporte());
    		   objBeanReportes.setCodTipoReporte(listaReporteFiltros.get(i).getCodTipoReporte());
    		   objBeanReportes.setEstado(listaReporteFiltros.get(i).getEstado());
    		   objBeanReportes.setPeriodoAnio(listaReporteFiltros.get(i).getPeriodoAnio());
    		   objBeanReportes.setPeriodoTrimestre(listaReporteFiltros.get(i).getPeriodoTrimestre());
    		   objBeanReportes.setTipoCambio(listaReporteFiltros.get(i).getTipoCambio());
    		   objBeanReportes.setUsuarioCreador(listaReporteFiltros.get(i).getUsuarioCreador());	    		
    	   }	    	  	    	   
       }
       	             
       if (objBeanReportes != null){
    	   
    	   try {
    		   
    		   String nombreReporte = "";
    		   nombreReporte = obtenerNombreReporte(objBeanReportes.getCodTipoReporte(), objBeanReportes.getPeriodoAnio(), objBeanReportes.getPeriodoTrimestre());
    		       		
    		   // BOTCHA
    		   String ruta = reporteSBSModeloUnoDetalleService.selectParametroByPrimaryKey("1601").getDescripcion();
    		   //String ruta = reporteSBSModeloUnoDetalleService.selectParametroByPrimaryKey("1610").getDescripcion();
    		   
    		   ruta = ruta+nombreReporte;
    		  // FileInputStream  elFichero = new FileInputStream(ruta);
               
              // XSSFWorkbook workbook = new XSSFWorkbook(elFichero);
        
               /*
               libro.write(elFichero);
               elFichero.close();
               */
               // descargando el archivo
          
               ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
               
               File archivoResp = new File(ruta);
               FileInputStream in = new FileInputStream(archivoResp);
               
               HttpServletResponse response = (HttpServletResponse) contexto.getResponse();
               byte[] loader = new byte[(int) archivoResp.length()];
                           
               
               //response.addHeader("Content-Disposition", "attachment; filename=" + "ReporteModeloUnoSBS"+".xlsx");
	            //response.addHeader("Content-Disposition", "attachment; filename=" + "ReporteModeloUnoSBS.xlsx");
	            response.addHeader("Content-Disposition", "attachment; filename=" + nombreReporte);
	            //response.setContentType("application/vnd.ms-excel");
	            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");               
               
               ServletOutputStream out = response.getOutputStream();

               while ((in.read(loader)) > 0) {
                 out.write(loader, 0, loader.length);
               }
               in.close();
               out.close();

               FacesContext.getCurrentInstance().responseComplete();
           	             			
			} catch (SyncconException e) {
		
				log.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
				log.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			} catch (MalformedURLException e) {
		
				log.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
				log.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			} catch (IOException e) {
		
				log.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
				log.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			}
    	   
    	   
       }
		
       return "";	
	}
	
	//String codigoReporte
	public String reprocesar(){
				
		    creadoHoja = false;
		    int eliminarLog = 0;
			libro = new XSSFWorkbook();										
			// CARGAMOS LA LISTA DE REPORTES
			List<ReportesGeneradosSBSModeloUnoDetalle> listaReporteFiltros = null;
			ReportesGeneradosSBSModeloUnoDetalle objBeanReportes= null;			
			
		    listaReporteFiltros  = reporteSBSModeloUnoDetalleService.consultarReportes(String.valueOf(codigoReporteReprocesar),usuarioReporte,tipoReporteParametro,periodoAnio,periodoTrimestre,estadoReporte);
		
	       if(listaReporteFiltros.size()>0){
	    	   
	    	   for (int i = 0; i < listaReporteFiltros.size(); i++) {
	    		
	    		   objBeanReportes = new ReportesGeneradosSBSModeloUnoDetalle();
	    		   
	    		   objBeanReportes.setNomFirmante1(listaReporteFiltros.get(i).getNomFirmante1());
	    		   objBeanReportes.setNomFirmante2(listaReporteFiltros.get(i).getNomFirmante2());
	    		   objBeanReportes.setCargoFirmante1(listaReporteFiltros.get(i).getCargoFirmante1());
	    		   objBeanReportes.setCargoFirmante2(listaReporteFiltros.get(i).getCargoFirmante2());
	    		   objBeanReportes.setCodReporte(listaReporteFiltros.get(i).getCodReporte());
	    		   objBeanReportes.setCodTipoReporte(listaReporteFiltros.get(i).getCodTipoReporte());
	    		   objBeanReportes.setEstado(listaReporteFiltros.get(i).getEstado());
	    		   objBeanReportes.setPeriodoAnio(listaReporteFiltros.get(i).getPeriodoAnio());
	    		   objBeanReportes.setPeriodoTrimestre(listaReporteFiltros.get(i).getPeriodoTrimestre());
	    		   objBeanReportes.setTipoCambio(listaReporteFiltros.get(i).getTipoCambio());
	    		   objBeanReportes.setUsuarioCreador(listaReporteFiltros.get(i).getUsuarioCreador());	    		
	    	   }	    	  	    	   
	       }
	       	       
	       if (objBeanReportes != null){
	    	   
	    	   llenarReporte();
	    	   
	    	   int extraccion = 0; 
	    	   
	    	   extraccion = extraerCuentas(objBeanReportes.getPeriodoAnio(),objBeanReportes.getPeriodoTrimestre(),"");
	    	   
	    	   if(extraccion > 0){
	    		   
	    		    eliminarLog = logErroresReportesService.eliminarLogByCodigo(String.valueOf(codigoReporteReprocesar));
	    	   		
	    		    firmanteDos = objBeanReportes.getNomFirmante2();
	    		    firmanteUno = objBeanReportes.getNomFirmante1();
	    		    cargoFirmanteDos = objBeanReportes.getCargoFirmante2();
	    		    cargoFirmanteUno = objBeanReportes.getCargoFirmante1();
	    		   
					for (ListaReporte objListaReporte: objListaReporte){
					
						generarExcel(objListaReporte.getMonedaCuenta(),objListaReporte.getMonedaModelo(),objListaReporte.getTipoReaseguro(),objBeanReportes.getCodTipoReporte(),objBeanReportes.getPeriodoAnio(),objBeanReportes.getPeriodoTrimestre());
					}
	    	   
	    	   }else{
	    		 
	    			SateliteUtil.mostrarMensaje("No hay data para el trimestre y año seleccionado");
	    		   
	    	   }
					
	       }
	       
	       objBeanReportes = null;	       
	
		return "";
	}
	
	public String descargarLog(String codigoReporte){
	
		XSSFWorkbook libroLog = new XSSFWorkbook();	      
        XSSFSheet hojaLog = libroLog.createSheet();	      
	    XSSFRow filaLog = null;	      
        XSSFCell celdaLog = null;
        XSSFRichTextString texto = null;
        SimpleDateFormat newFormatter = new SimpleDateFormat ("dd/MM/yyyy");
        hojaLog.setColumnWidth(1, 20000);
        hojaLog.setColumnWidth(2, 10000);
        hojaLog.setColumnWidth(3, 10000);
        hojaLog.setColumnWidth(4, 20000);        
        
		CellStyle estilosLibro=null;
        XSSFFont estiloTexto = null;		 											
		estiloTexto = libroLog.createFont();	
		estilosLibro = libroLog.createCellStyle();
		estiloTexto.setFontHeightInPoints((short) 8);									      											        
        estilosLibro.setAlignment(CellStyle.ALIGN_CENTER);
        estilosLibro.setVerticalAlignment(CellStyle.ALIGN_FILL);									            	
	    estilosLibro.setFont(estiloTexto);
	    
	    CellStyle estilosLibro1=null;	    
	    estilosLibro1 = libroLog.createCellStyle();										      											        
		estilosLibro1.setAlignment(CellStyle.ALIGN_LEFT);		
		estilosLibro1.setFont(estiloTexto);
		
		CellStyle estilosLibro2=null;	    
		estilosLibro2 = libroLog.createCellStyle();										      											        
		estilosLibro2.setAlignment(CellStyle.ALIGN_LEFT);
		estilosLibro2.setVerticalAlignment(CellStyle.ALIGN_FILL);	
		estilosLibro2.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
		estilosLibro2.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		estilosLibro2.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		estilosLibro2.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		estilosLibro2.setFont(estiloTexto);
		
		try {
			
			List<ReportesGeneradosSBSModeloUnoDetalle> listaReporteFiltros = null;
			ReportesGeneradosSBSModeloUnoDetalle objBeanReportes= null;			
			
		    listaReporteFiltros  = reporteSBSModeloUnoDetalleService.consultarReportes(codigoReporte,"","","","","");
		
	       if(listaReporteFiltros.size()>0){
	    	   
	    	   for (int i = 0; i < listaReporteFiltros.size(); i++) {
	    		
	    		   objBeanReportes = new ReportesGeneradosSBSModeloUnoDetalle();
	    		   
	    		   objBeanReportes.setCargoFirmante1(listaReporteFiltros.get(i).getCargoFirmante1());
	    		   objBeanReportes.setCargoFirmante2(listaReporteFiltros.get(i).getCargoFirmante2());
	    		   objBeanReportes.setCodReporte(listaReporteFiltros.get(i).getCodReporte());
	    		   objBeanReportes.setCodTipoReporte(listaReporteFiltros.get(i).getCodTipoReporte());
	    		   objBeanReportes.setEstado(listaReporteFiltros.get(i).getEstado());
	    		   objBeanReportes.setPeriodoAnio(listaReporteFiltros.get(i).getPeriodoAnio());
	    		   objBeanReportes.setPeriodoTrimestre(listaReporteFiltros.get(i).getPeriodoTrimestre());
	    		   objBeanReportes.setTipoCambio(listaReporteFiltros.get(i).getTipoCambio());
	    		   objBeanReportes.setUsuarioCreador(listaReporteFiltros.get(i).getUsuarioCreador());	    		
	    	   }	    	  	    	   
	       }
	       	       
	       if (objBeanReportes != null){
			
					List<LogErroresReportes> objList = new ArrayList<LogErroresReportes>();
					 			 
						
			       	filaLog = hojaLog.createRow(1);			       	    
			       	celdaLog = filaLog.createCell(2);							       	    		
			       	texto = new XSSFRichTextString("LOG DE ERRORES: MODELO 1 - DETALLE");	 
			       	celdaLog.setCellStyle(estilosLibro);  
			       	celdaLog.setCellValue(texto);				       	    
					
			    	filaLog = hojaLog.createRow(4);			       	    
			       	celdaLog = filaLog.createCell(0);							       	    		
			       	texto = new XSSFRichTextString("Codigo Proceso :" +objBeanReportes.getCodReporte());	
			       	celdaLog.setCellStyle(estilosLibro1); 
			       	celdaLog.setCellValue(texto);	
			       	
			     	celdaLog = filaLog.createCell(3);	
			       	texto = new XSSFRichTextString("Fecha Generación :" +newFormatter.format(new Date()));
			       	celdaLog.setCellStyle(estilosLibro1); 
			       	celdaLog.setCellValue(texto);	
			        
			       	celdaLog = filaLog.createCell(4);							       	    		
			       	texto = new XSSFRichTextString("Usuario :" + objBeanReportes.getUsuarioCreador());
			       	celdaLog.setCellStyle(estilosLibro1); 
			       	celdaLog.setCellValue(texto);	
			       	
			       	/////////
			       	
			       	filaLog = hojaLog.createRow(6);			       	    
			       	celdaLog = filaLog.createCell(0);							       	    		
			       	texto = new XSSFRichTextString("Id");	
			    	celdaLog.setCellStyle(estilosLibro2); 
			       	celdaLog.setCellValue(texto);	
			       	
			     	celdaLog = filaLog.createCell(1);							       	    		
			       	texto = new XSSFRichTextString("Nombre Reporte || Hoja reporte");
			    	celdaLog.setCellStyle(estilosLibro2); 
			       	celdaLog.setCellValue(texto);	
			        
			       	celdaLog = filaLog.createCell(2);							       	    		
			       	texto = new XSSFRichTextString("Glosa");
			    	celdaLog.setCellStyle(estilosLibro2); 
			       	celdaLog.setCellValue(texto);	
			       	
			       	celdaLog = filaLog.createCell(3);							       	    		
			       	texto = new XSSFRichTextString("BO / PLANILLA");
			    	celdaLog.setCellStyle(estilosLibro2); 
			       	celdaLog.setCellValue(texto);	
			       	
			       	celdaLog = filaLog.createCell(4);							       	    		
			       	texto = new XSSFRichTextString("Descripcion del Error");
			       	celdaLog.setCellStyle(estilosLibro2);
			       	celdaLog.setCellValue(texto);	
			       				       	
			        // Se crea el contenido de la celda y se mete en ella.	        	        
					objList = logErroresReportesService.selectListErroresReporteByPrimaryKey(codigoReporte);
					
					log.info("objList LISTA  :"+objList.size());

					for (int i = 0; i < objList.size(); i++) {
											
						 filaLog = hojaLog.createRow(i+7);	
						 
						 for (int j = 0; j < 5; j++) {
							 
							 celdaLog = filaLog.createCell(j);
							 
							 if(j == 0){
								 
								texto = new XSSFRichTextString(String.valueOf(i+1));	 
							 }
							
							 if(j == 1){
								 if(objList.get(i).getNomReporte()!=null ){
									 if(objList.get(i).getNomReporte().toString().length()>0)
									 texto = new XSSFRichTextString(String.valueOf(objList.get(i).getNomReporte()));	
									 else{
										 texto = new XSSFRichTextString(" ");	 
									 }
										 
								 }else{
									 texto = new XSSFRichTextString(" ");
								 }
							 }
							 
							 if(j == 2){
								 
								 if(objList.get(i).getGlosa()!=null ){
									 if(objList.get(i).getGlosa().toString().length()>0)
										 texto = new XSSFRichTextString(String.valueOf(objList.get(i).getGlosa()));	
									 else{
										 texto = new XSSFRichTextString(" ");	 
									 }
										 
								 }else{
									 texto = new XSSFRichTextString(" ");
								 }
								 
										 
							 }
						     
							 if(j == 3){
								 if(objList.get(i).getBoPlanilla()!=null ){
									 if(objList.get(i).getBoPlanilla().toString().length()>0)
										 texto = new XSSFRichTextString(String.valueOf(objList.get(i).getBoPlanilla()));	
									 else{
										 texto = new XSSFRichTextString(" ");	 
									 }
										 
								 }else{
									 texto = new XSSFRichTextString(" ");
								 }
								 
										 
							 }
							 
							 if(j == 4){
								 if(objList.get(i).getDescripcion()!=null ){
									 if(objList.get(i).getDescripcion().toString().length()>0)
										 texto = new XSSFRichTextString(String.valueOf(objList.get(i).getDescripcion()));	
									 else{
										 texto = new XSSFRichTextString(" ");	 
									 }
										 
								 }else{
									 texto = new XSSFRichTextString(" ");
								 }
										 
							 }
							 							 
							 celdaLog.setCellStyle(estilosLibro2);
							 celdaLog.setCellValue(texto);					 				     				     
						 }		
						 
					}
					// BOTCHA		
					//String ruta = reporteSBSModeloUnoDetalleService.selectParametroByPrimaryKey("1610").getDescripcion();					
					String ruta = reporteSBSModeloUnoDetalleService.selectParametroByPrimaryKey("1601").getDescripcion();   
					
					ruta = ruta+"DescargarLogErrores.xlsx";
					
		            FileOutputStream elFichero = new FileOutputStream(ruta);
		            libroLog.write(elFichero);
		            elFichero.close();
		            
		            // descargando el archivo
		            
		            ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		            
		            File archivoResp = new File(ruta);
		            FileInputStream in = new FileInputStream(archivoResp);
		            
		            HttpServletResponse response = (HttpServletResponse) contexto.getResponse();
		            byte[] loader = new byte[(int) archivoResp.length()];
		                                   
		            response.addHeader("Content-Disposition", "attachment; filename=" + "DescargarLogErrores.xlsx");
		            //response.setContentType("application/vnd.ms-excel");
		            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");     
		            
		            ServletOutputStream out = response.getOutputStream();
		
		            while ((in.read(loader)) > 0) {
		              out.write(loader, 0, loader.length);
		            }
		            in.close();
		            out.close();
		
		            FacesContext.getCurrentInstance().responseComplete();
		        	
		            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Se genero correctamente", null);
		            FacesContext.getCurrentInstance().addMessage(null, facesMsg);		            
	         }
			
		} catch (Exception e) {
						
			log.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			log.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
						
		}	
		
		return "";
		
	}

	public String guardarDatosReporte() {
		
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		
		creadoHoja = false;
		int consultaReporte = 0;		
		libro = new XSSFWorkbook();
		try {
			
			if (logger.isInfoEnabled())
			{
				logger.info("\n###################### PARAMETROS DE REGISTRO ######################" +
					"\nMODELO_REPORTE: " + this.modeloRepore + "\tNOM_FIRMANTE_UNO: " + firmanteDos + 
					"\nNOM_FIRMANTE_DOS: " + firmanteUno + "\tCARGO_FIRMANTE_UNO: " + cargoFirmanteUno + 
					"\nCARGO_FIRMANTE_DOS: " + cargoFirmanteDos + "\tPERIODO_DESDE: " + this.periodoDesde +
					"\nPERIODO_HASTA: " + this.periodoHasta + "\tTIPO_CAMBIO: " + this.tipoCambio );
			}
						
			
			if(cargoFirmanteUno.length() == 0 || cargoFirmanteDos.length() ==0 || firmanteUno.length() == 0 || firmanteDos.length() ==0){
				
				SateliteUtil.mostrarMensaje("Seleccione un firmante ");
				
			}else if(tipoReporteParametro.length() <= 0){
							
				SateliteUtil.mostrarMensaje("Seleccione un tipo de reporte");
				
			}else if(periodoAnio.length() <= 0 || periodoTrimestre.length() <= 0){
				
				SateliteUtil.mostrarMensaje("Seleccione un periodo correcto");				
														
			}else{
				
				String modeloRepore = tipoReporteParametro;				
					
				this.tipoCambio = new BigDecimal("3.40");
				
				String nombreUsuarioCreador ="";
				nombreUsuarioCreador =	SecurityContextHolder.getContext().getAuthentication().getName();
			
				consultaReporte = reporteSBSModeloUnoDetalleService.selectReporteListByParamAnterGuardar(periodoAnio,periodoTrimestre,modeloRepore);
				
				log.info("resultado de busqueda de selectParametroListByParamAnterGuardar :"+consultaReporte);
				
				if (consultaReporte == 0){
			
				
					int extraccion = 0;
					
					extraccion = extraerCuentas(periodoAnio,periodoTrimestre,"");
					
					if(extraccion > 0){
					
					ReporteSBSModeloUnoDetalle reporteSBSModeloUnoDetalle = reporteSBSModeloUnoDetalleService.registrarReporteSBSBModeloUno(modeloRepore,cargoFirmanteUno,cargoFirmanteDos,firmanteUno,
							                                                  																firmanteDos,periodoAnio,periodoTrimestre,this.tipoCambio,"1501",nombreUsuarioCreador);												
					
							if(reporteSBSModeloUnoDetalle != null){
						
									llenarReporte();
	
									for (ListaReporte objListaReporte: objListaReporte){
										generarExcel(objListaReporte.getMonedaCuenta(),objListaReporte.getMonedaModelo(),objListaReporte.getTipoReaseguro(),modeloRepore,periodoAnio,periodoTrimestre);
									}	
							
									
									SateliteUtil.mostrarMensaje("Se genero el reporte");	
																																																						
							}else{					
							
								SateliteUtil.mostrarMensaje("Se presento errores al guardar el reporte");					
							
							}												
					
					}else{
						
						SateliteUtil.mostrarMensaje("No hay data para el trimestre y año seleccionado");
						
					}		
						
						
				}else{
					
					SateliteUtil.mostrarMensaje("Ya hay reporte generado para el año y trimestre seleccionado");
					
				}				
			
			}		
			
		} catch (Exception e) {
			
			log.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			log.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
		}
		
		return null;
	}
	
	
	@SuppressWarnings("unused")
	public int extraerCuentas(String periodoAnio, String periodoTrimestre, String monedaModeloCiclas){
				
		logger.info("Inicio");
		
		
		int respuesta = 0;
		BigDecimal monto_operacion = null;

		objListaCuentasDataSunCuentasPago = null;
		objListaCuentasDataSunCuentasCuarenta = null;
		try {
		
			String periodoDesde="";
			String periodoHasta="";
			String cuentas ="";
			String trimestre = reporteSBSModeloUnoDetalleService.selectParametroByPrimaryKey(periodoTrimestre).getCodValor();
			String anio      = periodoAnio;
							
			 objListaCuentasDataSunTotal = new ArrayList<ListaCuentasDataSun>();
		     objListaCuentasDataSun = new ArrayList<ListaCuentasDataSun>();
		     objListaCuentasDataSunCuentasPago = new ArrayList<ListaCuentasDataSun>();
		     objListaCuentasDataSunCuentasCuarenta = new ArrayList<ListaCuentasDataSun>();
		     
		     ListaCuentasDataSun objListaCuentasDataSunBean = null;
			
			if(trimestre.equalsIgnoreCase("PT1")){
				periodoDesde = "001"+anio;
				periodoHasta = "003"+anio;
			}else if(trimestre.equalsIgnoreCase("PT2")){
				periodoDesde = "004"+anio;
				periodoHasta = "006"+anio;								
			}else if(trimestre.equalsIgnoreCase("PT3")){
				periodoDesde = "007"+anio;
				periodoHasta = "009"+anio;
			}else if(trimestre.equalsIgnoreCase("PT4")){
				periodoDesde = "010"+anio;
				periodoHasta = "012"+anio;
			}
								 				
			logger.error("Periodod Desde :"+periodoDesde);
			logger.error("Periodod Hasta :"+periodoHasta);
									
				 List<ParametrosSBSModeloUnoDetalle> objListParametros = reporteSBSModeloUnoDetalleService.listaTramaParametros();
				 
				 cuentas ="";
				 
				 for (ParametrosSBSModeloUnoDetalle objListParametros2 : objListParametros) {


						  if(String.valueOf(objListParametros2.getCuentaDescuentoSoles()).trim().length()>4){
							 if(String.valueOf(objListParametros2.getCuentaDescuentoSoles()).trim().equalsIgnoreCase("0.00")
								|| String.valueOf(objListParametros2.getCuentaDescuentoSoles()).trim().equalsIgnoreCase("0")){
								 logger.error("Cuenta inadecuada :"+objListParametros2.getCuentaDescuentoSoles());	 
							 }else{
								 if(String.valueOf(objListParametros2.getCuentaDescuentoSoles()).trim().substring(0, 2).equalsIgnoreCase("14")){
									 cuentas = cuentas + String.valueOf(objListParametros2.getCuentaDescuentoSoles()) + ",";  
								 }else if(String.valueOf(objListParametros2.getCuentaDescuentoSoles()).trim().substring(0, 2).equalsIgnoreCase("24")){
									 cuentas = cuentas + String.valueOf(objListParametros2.getCuentaDescuentoSoles()) + ",";
								 }else if(String.valueOf(objListParametros2.getCuentaDescuentoSoles()).trim().substring(0, 2).equalsIgnoreCase("40")){
									 cuentas = cuentas + String.valueOf(objListParametros2.getCuentaDescuentoSoles()) + ",";
								 }								 															 							
							 }			
						  } 
		
					      if(String.valueOf(objListParametros2.getCuentaDescuentoDolares()).trim().length()>4){
							 if(String.valueOf(objListParametros2.getCuentaDescuentoDolares()).trim().equalsIgnoreCase("0.00")
								|| String.valueOf(objListParametros2.getCuentaDescuentoDolares()).trim().equalsIgnoreCase("0") ){
								 logger.error("Cuenta inadecuada :"+objListParametros2.getCuentaDescuentoDolares());	 
							 }else{
								 if(String.valueOf(objListParametros2.getCuentaDescuentoDolares()).trim().substring(0, 2).equalsIgnoreCase("14")){
								   cuentas = cuentas + String.valueOf(objListParametros2.getCuentaDescuentoDolares()) + ",";
								 }else if(String.valueOf(objListParametros2.getCuentaDescuentoDolares()).trim().substring(0, 2).equalsIgnoreCase("24")){
								   cuentas = cuentas + String.valueOf(objListParametros2.getCuentaDescuentoDolares()) + ",";	 
								 }else if(String.valueOf(objListParametros2.getCuentaDescuentoDolares()).trim().substring(0, 2).equalsIgnoreCase("40")){
									   cuentas = cuentas + String.valueOf(objListParametros2.getCuentaDescuentoDolares()) + ",";	 
								 }
								 
							 }				 
					      }
					      
					      if(String.valueOf(objListParametros2.getCuentaPrimaDolares()).trim().length()>4){
							 if(String.valueOf(objListParametros2.getCuentaPrimaDolares()).trim().equalsIgnoreCase("0.00")
								|| String.valueOf(objListParametros2.getCuentaPrimaDolares()).trim().equalsIgnoreCase("0")){
								 logger.error("Cuenta inadecuada :"+objListParametros2.getCuentaPrimaDolares());	 
							 }else{
								 if(String.valueOf(objListParametros2.getCuentaPrimaDolares()).trim().substring(0, 2).equalsIgnoreCase("14")){
									 cuentas = cuentas + String.valueOf(objListParametros2.getCuentaPrimaDolares()) + ",";	 
								 }else if(String.valueOf(objListParametros2.getCuentaPrimaDolares()).trim().substring(0, 2).equalsIgnoreCase("24")){
									 cuentas = cuentas + String.valueOf(objListParametros2.getCuentaPrimaDolares()) + ",";
								 }else if(String.valueOf(objListParametros2.getCuentaPrimaDolares()).trim().substring(0, 2).equalsIgnoreCase("40")){
									 cuentas = cuentas + String.valueOf(objListParametros2.getCuentaPrimaDolares()) + ",";
								 }
								 
							 }
					      }
					      
					      if(String.valueOf(objListParametros2.getCuentaPrimaSoles()).trim().length()>4){ 		
							 if(String.valueOf(objListParametros2.getCuentaPrimaSoles()).trim().equalsIgnoreCase("0.00")
								|| String.valueOf(objListParametros2.getCuentaPrimaSoles()).trim().equalsIgnoreCase("0")){
								 logger.error("Cuenta inadecuada :"+objListParametros2.getCuentaPrimaSoles());	 					 
							 }else{
								 if(String.valueOf(objListParametros2.getCuentaPrimaSoles()).trim().substring(0, 2).equalsIgnoreCase("14")){
									 cuentas = cuentas + String.valueOf(objListParametros2.getCuentaPrimaSoles()) + ",";
								 }else if(String.valueOf(objListParametros2.getCuentaPrimaSoles()).trim().substring(0, 2).equalsIgnoreCase("24")){
									 cuentas = cuentas + String.valueOf(objListParametros2.getCuentaPrimaSoles()) + ",";	 
								 }else if(String.valueOf(objListParametros2.getCuentaPrimaSoles()).trim().substring(0, 2).equalsIgnoreCase("40")){
									 cuentas = cuentas + String.valueOf(objListParametros2.getCuentaPrimaSoles()) + ",";	 
								 }
								 
							 }
					      }
							
					      if(String.valueOf(objListParametros2.getCuentaSaldoDolares()).trim().length()>4){
							 if(String.valueOf(objListParametros2.getCuentaSaldoDolares()).trim().equalsIgnoreCase("0.00")
								|| String.valueOf(objListParametros2.getCuentaSaldoDolares()).trim().equalsIgnoreCase("0")){
								 logger.error("Cuenta inadecuada :"+objListParametros2.getCuentaSaldoDolares());						 	 
							 }else{
								 if(String.valueOf(objListParametros2.getCuentaSaldoDolares()).trim().substring(0, 2).equalsIgnoreCase("14")){
									 cuentas = cuentas + String.valueOf(objListParametros2.getCuentaSaldoDolares()) + ",";	 
								 }else if(String.valueOf(objListParametros2.getCuentaSaldoDolares()).trim().substring(0, 2).equalsIgnoreCase("24")){
									 cuentas = cuentas + String.valueOf(objListParametros2.getCuentaSaldoDolares()) + ",";
								 }
								 else if(String.valueOf(objListParametros2.getCuentaSaldoDolares()).trim().substring(0, 2).equalsIgnoreCase("40")){
									 cuentas = cuentas + String.valueOf(objListParametros2.getCuentaSaldoDolares()) + ",";
								 }
								 
							 }
					      }
		
					      if(String.valueOf(objListParametros2.getCuentaSaldoSoles()).trim().length()>4){
							 if(String.valueOf(objListParametros2.getCuentaSaldoSoles()).trim().equalsIgnoreCase("0.00")
								|| String.valueOf(objListParametros2.getCuentaSaldoSoles()).trim().equalsIgnoreCase("0")){
								 logger.error("Cuenta inadecuada :"+objListParametros2.getCuentaSaldoSoles()); 
							 }else{
								 if(String.valueOf(objListParametros2.getCuentaSaldoSoles()).trim().substring(0, 2).equalsIgnoreCase("14")){
									 cuentas = cuentas + String.valueOf(objListParametros2.getCuentaSaldoSoles()) + ",";
								 }else if(String.valueOf(objListParametros2.getCuentaSaldoSoles()).trim().substring(0, 2).equalsIgnoreCase("24")){
									 cuentas = cuentas + String.valueOf(objListParametros2.getCuentaSaldoSoles()) + ",";	 
								 }else if(String.valueOf(objListParametros2.getCuentaSaldoSoles()).trim().substring(0, 2).equalsIgnoreCase("40")){
									 cuentas = cuentas + String.valueOf(objListParametros2.getCuentaSaldoSoles()) + ",";	 
								 }								 
							 }
						  }					      										
				    }				 				 			 			 			 
			 
			
			
			if(!periodoDesde.equalsIgnoreCase("") && !periodoHasta.equalsIgnoreCase("")){	
					 //14ZZZZ				 							 			 
			 // RECORREMOS LAS CUENTAS Y ARMAMOS LISTA
			 String[] parts = cuentas.split(",");
			 for(String obj : parts){
			 
		     if(obj.length()>=3){
		    	 
					   if(obj.substring(0,2).equalsIgnoreCase("14") || obj.substring(0,2).equalsIgnoreCase("40") || obj.substring(0,2).equalsIgnoreCase("24")){ 
						   
						   FacesContext context = FacesContext.getCurrentInstance();
						   String token = (String) context.getExternalContext().getSessionMap().get(Constantes.MDP_AUTH_SUNSYSTEMS_TOKEN);
						   
						   
						     splistaProcesos = reporteSBSModeloUnoDetalleService.consultarRegistros(obj,obj, periodoDesde, periodoHasta);				
						     //splistaProcesos = reporteSBSModeloUnoDetalleService.consultarRegistros("1412020203","1412020203", periodoDesde, periodoHasta);
						     
						     listaProcesos = ProcesarSuperlista(splistaProcesos);
					 			 			 			 
							if(listaProcesos.size()>0){
								
								 for (int i = 0; i < listaProcesos.size(); i++) {
						    			
						    			objListaCuentasDataSunBean = new ListaCuentasDataSun();		    			
						    			objListaCuentasDataSunBean.setCanal(listaProcesos.get(i).getCanal() != null?listaProcesos.get(i).getCanal():"");
						    			objListaCuentasDataSunBean.setCodigoCuenta(listaProcesos.get(i).getCodigoCuenta() != null?listaProcesos.get(i).getCodigoCuenta():"");
						    			objListaCuentasDataSunBean.setDebitoCredito(listaProcesos.get(i).getDebitoCredito()!=null?listaProcesos.get(i).getDebitoCredito():"");
						    			
						    									    			
						    			if(listaProcesos.get(i).getFechaTransaccion()!=null && listaProcesos.get(i).getFechaTransaccion().length()==8){
						    									    									    		    
						    			String dateStringInOriginalFormat = listaProcesos.get(i).getFechaTransaccion();
						    			
						    			SimpleDateFormat originalFormatter = new SimpleDateFormat ("ddMMyyyy");
						    			SimpleDateFormat newFormatter = new SimpleDateFormat ("dd/MM/yyyy");
						    									    			
						    			ParsePosition pos = new ParsePosition(0);
						    			Date dateFromString = originalFormatter.parse(dateStringInOriginalFormat, pos);
						    									    			
						    			String dateStringInNewFormat = newFormatter.format(dateFromString);
						    			
						    			objListaCuentasDataSunBean.setFechaTransaccion(dateStringInNewFormat);
						    			
						    			}else{
						    				objListaCuentasDataSunBean.setFechaTransaccion("");	
						    			}
						    			
						    			
						    			objListaCuentasDataSunBean.setTipoCambio(listaProcesos.get(i).getTipoCambio()>0?listaProcesos.get(i).getTipoCambio():1);
						    			objListaCuentasDataSunBean.setGlosa(listaProcesos.get(i).getGlosa()!=null?listaProcesos.get(i).getGlosa():"");
						    			//objListaCuentasDataSunBean.setImporteSoles(listaProcesos.get(i).getImpSoles());
						    			objListaCuentasDataSunBean.setImporteSoles(listaProcesos.get(i).getImpSoles());
						    			objListaCuentasDataSunBean.setImporteBase(listaProcesos.get(i).getImpSoles());
						    			//objListaCuentasDataSunBean.setImporteBase(20000);
						    			//objListaCuentasDataSunBean.setImporteTransaccion(listaProcesos.get(i).getImporteTransaccion());
						    			objListaCuentasDataSunBean.setImporteTransaccion(listaProcesos.get(i).getImpTransaccionAmount());
						    			objListaCuentasDataSunBean.setLiniaDiario(listaProcesos.get(i).getLineaDiario());
						    			objListaCuentasDataSunBean.setMoneda(listaProcesos.get(i).getMoneda());
						    			objListaCuentasDataSunBean.setNombreProveedor(listaProcesos.get(i).getNombreProveedor()!=null?listaProcesos.get(i).getNombreProveedor():"");
						    			objListaCuentasDataSunBean.setNombreProveedorCompleto(listaProcesos.get(i).getNombreProveedorCompleto()!=null?listaProcesos.get(i).getNombreProveedorCompleto():"");
						    			objListaCuentasDataSunBean.setNroSiniestro(listaProcesos.get(i).getNroSiniestro()!=null?listaProcesos.get(i).getNroSiniestro():"");
						    			objListaCuentasDataSunBean.setNumerodiario(listaProcesos.get(i).getDiario());
						    			objListaCuentasDataSunBean.setPolizaCliente(listaProcesos.get(i).getPolizaCliente()!= null?listaProcesos.get(i).getPolizaCliente():"");
						    			objListaCuentasDataSunBean.setRuc(listaProcesos.get(i).getRuc()!=null?listaProcesos.get(i).getRuc():"");
						    			objListaCuentasDataSunBean.setSocioProducto(listaProcesos.get(i).getSocioProducto()!=null?listaProcesos.get(i).getSocioProducto():"");	
						    			objListaCuentasDataSunBean.setTipoDiario(listaProcesos.get(i).getTipoDiario()!=null?listaProcesos.get(i).getTipoDiario():"");		    			
						    			objListaCuentasDataSunBean.setJournalType(listaProcesos.get(i).getJournalType()!=null?listaProcesos.get(i).getJournalType():"");					  
						    			
						    			if(obj.substring(0,2).equalsIgnoreCase("14") || obj.substring(0,2).equalsIgnoreCase("24") || obj.substring(0,2).equalsIgnoreCase("40")){
						    			
						    				objListaCuentasDataSunTotal.add(objListaCuentasDataSunBean);
						    				
						    			}
						    			
						    			
						    									    									    									    						    					    			
						    	}													

							}					
				      }
					   					
		     	  }			
		     
			    }
				
			 }
				    
			//// APLICAMOS REVERSION
			objListaCuentasDataSunTotal = elimina_Reversion(objListaCuentasDataSunTotal);			
			
			respuesta = objListaCuentasDataSunTotal.size();
			
			if(objListaCuentasDataSunTotal.size() > 0 ){
				
				for (int j = 0; j < objListaCuentasDataSunTotal.size(); j++) {
					
										
					objListaCuentasDataSunBean = new ListaCuentasDataSun();		  
					
					objListaCuentasDataSunBean.setFechaTransaccion(objListaCuentasDataSunTotal.get(j).getFechaTransaccion());
	    			objListaCuentasDataSunBean.setCanal(objListaCuentasDataSunTotal.get(j).getCanal() != null?objListaCuentasDataSunTotal.get(j).getCanal():"");
	    			objListaCuentasDataSunBean.setCodigoCuenta(objListaCuentasDataSunTotal.get(j).getCodigoCuenta() != null?objListaCuentasDataSunTotal.get(j).getCodigoCuenta():"");
	    			objListaCuentasDataSunBean.setDebitoCredito(objListaCuentasDataSunTotal.get(j).getDebitoCredito()!=null?objListaCuentasDataSunTotal.get(j).getDebitoCredito():"");
	    			objListaCuentasDataSunBean.setGlosa(objListaCuentasDataSunTotal.get(j).getGlosa()!=null?objListaCuentasDataSunTotal.get(j).getGlosa():"");
	    			//objListaCuentasDataSunBean.setImporteSoles(listaProcesos.get(i).getImpSoles());
	    			objListaCuentasDataSunBean.setImporteSoles(objListaCuentasDataSunTotal.get(j).getImporteSoles());
	    			objListaCuentasDataSunBean.setImporteBase(objListaCuentasDataSunTotal.get(j).getImporteBase());
	    			//objListaCuentasDataSunBean.setImporteBase(20000);
	    			//objListaCuentasDataSunBean.setImporteTransaccion(listaProcesos.get(i).getImporteTransaccion());
	    			objListaCuentasDataSunBean.setImporteTransaccion(objListaCuentasDataSunTotal.get(j).getImporteTransaccion());
	    			objListaCuentasDataSunBean.setLiniaDiario(objListaCuentasDataSunTotal.get(j).getLiniaDiario());
	    			objListaCuentasDataSunBean.setMoneda(objListaCuentasDataSunTotal.get(j).getMoneda());
	    			objListaCuentasDataSunBean.setNombreProveedor(objListaCuentasDataSunTotal.get(j).getNombreProveedor()!=null?objListaCuentasDataSunTotal.get(j).getNombreProveedor():"");
	    			objListaCuentasDataSunBean.setNombreProveedorCompleto(objListaCuentasDataSunTotal.get(j).getNombreProveedorCompleto()!=null?objListaCuentasDataSunTotal.get(j).getNombreProveedorCompleto():"");
	    			objListaCuentasDataSunBean.setNroSiniestro(objListaCuentasDataSunTotal.get(j).getNroSiniestro()!=null?objListaCuentasDataSunTotal.get(j).getNroSiniestro():"");
	    			objListaCuentasDataSunBean.setNumerodiario(objListaCuentasDataSunTotal.get(j).getNumerodiario());
	    			objListaCuentasDataSunBean.setPolizaCliente(objListaCuentasDataSunTotal.get(j).getPolizaCliente()!= null?objListaCuentasDataSunTotal.get(j).getPolizaCliente():"");
	    			objListaCuentasDataSunBean.setRuc(objListaCuentasDataSunTotal.get(j).getRuc()!=null?objListaCuentasDataSunTotal.get(j).getRuc():"");
	    			objListaCuentasDataSunBean.setSocioProducto(objListaCuentasDataSunTotal.get(j).getSocioProducto()!=null?objListaCuentasDataSunTotal.get(j).getSocioProducto():"");	
	    			objListaCuentasDataSunBean.setTipoDiario(objListaCuentasDataSunTotal.get(j).getTipoDiario()!=null?objListaCuentasDataSunTotal.get(j).getTipoDiario():"");		    			
	    			objListaCuentasDataSunBean.setJournalType(objListaCuentasDataSunTotal.get(j).getJournalType()!=null?objListaCuentasDataSunTotal.get(j).getJournalType():"");
	    			objListaCuentasDataSunBean.setTipoCambio(objListaCuentasDataSunTotal.get(j).getTipoCambio()>0?objListaCuentasDataSunTotal.get(j).getTipoCambio():1);
	    			
	    			if(objListaCuentasDataSunBean.getCodigoCuenta().substring(0,2).equalsIgnoreCase("14") ){
		    			
	    				if(objListaCuentasDataSunTotal.get(j).getDebitoCredito().equalsIgnoreCase("D")){						    				
			    			  objListaCuentasDataSun.add(objListaCuentasDataSunBean);							    			
			    	    }else{						    				
			    			  objListaCuentasDataSunCuentasPago.add(objListaCuentasDataSunBean);	
			    		}
	    				
	    			}else if(objListaCuentasDataSunBean.getCodigoCuenta().substring(0,2).equalsIgnoreCase("24")){
	    				
	    				if(objListaCuentasDataSunTotal.get(j).getDebitoCredito().equalsIgnoreCase("C")){						    				
			    			  objListaCuentasDataSun.add(objListaCuentasDataSunBean);							    			
			    	    }else{						    				
			    			  objListaCuentasDataSunCuentasPago.add(objListaCuentasDataSunBean);	
			    		}
	    				
	    			}else if(objListaCuentasDataSunBean.getCodigoCuenta().substring(0,2).equalsIgnoreCase("40")){
	    				
	    				objListaCuentasDataSunCuentasCuarenta.add(objListaCuentasDataSunBean);
	    				
	    			}
	    			
				}
				
			}
 
			
			
			
		} catch (Exception e) {

			 logger.error("Hay un error");
		     logger.error(e.getMessage(), e);
		     FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
		     FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		     respuesta = 0;
						
		}
		logger.info("Fin");
	    return respuesta;
	}
		
	@SuppressWarnings("unused")
	private List<ListaCuentasDataSun> elimina_Reversion(List<ListaCuentasDataSun> objListaCuentasDataSunConRerversionTotalParam) {
				
		List<ListaCuentasDataSun> objListaCuentasDataSunConRerversionTotalMetodo = new ArrayList<ListaCuentasDataSun>();
		List<ListaCuentasDataSun> objListaEliminar = new ArrayList<ListaCuentasDataSun>();
		
		List<ListaCuentasDataSun> objListaCuentasDataSinReversionAntes = new ArrayList<ListaCuentasDataSun>();
		List<ListaCuentasDataSun> objListaCuentasDataSinReversionDespues = new ArrayList<ListaCuentasDataSun>();

		List<ListaCuentasDataSun> objListaCuentasDataSinReversionFinalInferior = new ArrayList<ListaCuentasDataSun>();
		
		
		ListaCuentasDataSun cuentasDataSunSinReversionBean = null;		
		ListaCuentasDataSun cuentasDataSunConReversionBean = null;
		ListaCuentasDataSun cuentasDataSunDepuradoBean = null;
		
		objListaCuentasDataSunConRerversionTotalMetodo = objListaCuentasDataSunConRerversionTotalParam;
		
		
		int posicion;
	    int posicion2;
	    String busqueda1="REV(";
		String busqueda2="REV D.";	
	    boolean encontro = false;
		String numero_diario = "";
		//INICIO DE GRABAR LAS CUENTAS ENCONTRADAS CON REV
		for (ListaCuentasDataSun objList : objListaCuentasDataSunConRerversionTotalMetodo) {
		
			String glosa = objList.getGlosa();
			numero_diario ="";
			
			Pattern regex = Pattern.compile("\\b" + Pattern.quote(busqueda1) + "\\b", Pattern.CASE_INSENSITIVE);
			Matcher match = regex.matcher(glosa);
			
			Pattern regex2 = Pattern.compile("\\b" + Pattern.quote(busqueda2) + "\\b", Pattern.CASE_INSENSITIVE);
			Matcher match2 = regex2.matcher(glosa);
			
			if(match.find()){
				 posicion = glosa.indexOf('(');
			     posicion2 = glosa.indexOf(')');
			     
			     numero_diario=glosa.substring(posicion+1,posicion2);
			     
			}
			
			if(match2.find()){
				 posicion = glosa.indexOf('.');
			     posicion2 = glosa.indexOf(')');
			     
			     numero_diario=glosa.substring(posicion+1,posicion2);
			     
			}
			
			if(numero_diario.length()>0){
				
			
								
					cuentasDataSunSinReversionBean = new ListaCuentasDataSun();
					
					cuentasDataSunSinReversionBean.setImporteBase(objList.getImporteBase());
					cuentasDataSunSinReversionBean.setImporteTransaccion(objList.getImporteTransaccion());
					cuentasDataSunSinReversionBean.setNumerodiario(Integer.parseInt(numero_diario));
					cuentasDataSunSinReversionBean.setCodigoCuenta(objList.getCodigoCuenta());
					
					objListaCuentasDataSinReversionAntes.add(cuentasDataSunSinReversionBean);		
										
			}
			
		}
			
	
		//TERMINO DE GRABAR LAS CUENTAS ENCONTRADAS CON REV
		//PENULTIMO FILTRO ELIMINAMOS DIARIOS ASOCIADOS
	
		for (ListaCuentasDataSun objListTotalMetodo : objListaCuentasDataSunConRerversionTotalMetodo) {
			
			
			Integer numeroDiario = 0;
			encontro = false;
			cuentasDataSunConReversionBean = new ListaCuentasDataSun();

			for (ListaCuentasDataSun objListaCuentasDataSinReversion : objListaCuentasDataSinReversionAntes) {
				

				double importeBase = 0;
				double importeTransaccion = 0;
				     
				if(objListaCuentasDataSinReversion.getImporteBase() != 0){
					importeBase = objListaCuentasDataSinReversion.getImporteBase() * -1;
				}
				
				if(objListaCuentasDataSinReversion.getImporteTransaccion() != 0){
					importeTransaccion = objListaCuentasDataSinReversion.getImporteTransaccion() * -1;
				}
				
				if(objListTotalMetodo.getImporteBase() == importeBase
				  && objListTotalMetodo.getImporteTransaccion() == importeTransaccion
				  && objListTotalMetodo.getNumerodiario().equals(objListaCuentasDataSinReversion.getNumerodiario())
				  && objListTotalMetodo.getCodigoCuenta().equalsIgnoreCase(objListaCuentasDataSinReversion.getCodigoCuenta())){
					
				  objListaEliminar.add(objListTotalMetodo);				 
				  numeroDiario = objListaCuentasDataSinReversion.getNumerodiario();
				  encontro = true;	
				  break;
				}												
			}
			
			if(encontro){
				cuentasDataSunConReversionBean.setNumerodiario(numeroDiario);
				objListaCuentasDataSinReversionDespues.add(cuentasDataSunConReversionBean);			
			}
						
		//}
		}
						
		objListaCuentasDataSunConRerversionTotalMetodo.removeAll(objListaEliminar);		
		objListaEliminar.clear();
		//ULTIMO FULTRO ELIMINAMOS DIARIO ORIGEN
		
		for (ListaCuentasDataSun objListUltima : objListaCuentasDataSunConRerversionTotalMetodo) {
				
			cuentasDataSunDepuradoBean = new ListaCuentasDataSun();
			
			String glosa = objListUltima.getGlosa();
			numero_diario ="";
			
			Pattern regex = Pattern.compile("\\b" + Pattern.quote(busqueda1) + "\\b", Pattern.CASE_INSENSITIVE);
			Matcher match = regex.matcher(glosa);
			
			Pattern regex2 = Pattern.compile("\\b" + Pattern.quote(busqueda2) + "\\b", Pattern.CASE_INSENSITIVE);
			Matcher match2 = regex2.matcher(glosa);
			
			if(match.find()){
				 posicion = glosa.indexOf('(');
			     posicion2 = glosa.indexOf(')');
			     
			     numero_diario=glosa.substring(posicion+1,posicion2);
			     
			}
			
			if(match2.find()){
				 posicion = glosa.indexOf('.');
			     posicion2 = glosa.indexOf(')');
			     
			     numero_diario=glosa.substring(posicion+1,posicion2);
			     
			}
			
			for (ListaCuentasDataSun objListFinal : objListaCuentasDataSinReversionDespues) {
												
			    if(numero_diario.length()>0 
			       && numero_diario.equalsIgnoreCase(String.valueOf(objListFinal.getNumerodiario()))){			     			
			    	objListaEliminar.add(objListUltima);
			    	break;
			    }
			
			}
				
		}
		
		objListaCuentasDataSunConRerversionTotalMetodo.removeAll(objListaEliminar);
		
		return objListaCuentasDataSunConRerversionTotalMetodo;
	}

	public void grabarErrorLog(long codReporte, String nombreReporte, String glosa, String bo_planilla, String descripcion){
		
		try {
			
			String usuarioLog = SecurityContextHolder.getContext().getAuthentication().getName();
			// BOTCHA
			logErroresReportesService.grabarErrorLog(usuarioLog,codReporte,nombreReporte,glosa,bo_planilla,descripcion);
			
		} catch (Exception e1) {
			log.error("Exception(" + e1.getClass().getName() + ") - ERROR: " + e1.getMessage());
			log.error("Exception(" + e1.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e1));			
		}
		
	}
	
	@SuppressWarnings("unused")
	public String generarExcel(String monedaModelo,String monedaCuenta, String tipoReaseguro,String modeloReporeParam,String periodoAnioParam,String periodoTrimestreParam){
        				
		boolean entroCreacionHoja = false;
	 
		//HSSFCellStyle styleGroup1 = hssfWorkbook.createCellStyle();
	
		boolean entroPrimasDescuentos = false; 
		Double montodescuento = 0.00;
		Double montoSaldoAcreedor = 0.00;
		
		filasExcel = 12;		

		String fechaTransaccion = null;
		int contadorLista=0;		
		//VARIABLES SUMATORIAS   
		BigDecimal primasPagar  = new BigDecimal("0");
		BigDecimal primasCobrar = new BigDecimal("0");
		BigDecimal siniestrosCobrar = new BigDecimal("0");
		BigDecimal siniestrosPagar  = new BigDecimal("0");
		BigDecimal cuentasCobrar    = new BigDecimal("0");
		BigDecimal cuentasPagar     = new BigDecimal("0");
		BigDecimal descuentosComisiones    = new BigDecimal("0");
		BigDecimal saldoDeudor = new BigDecimal("0");
		BigDecimal saldoAcreedor = new BigDecimal("0");
		
		BigDecimal primasPagarSaldos  = new BigDecimal("0");
		BigDecimal primasCobrarSaldos = new BigDecimal("0");
		BigDecimal siniestrosCobrarSaldos = new BigDecimal("0");
		BigDecimal siniestrosPagarSaldos  = new BigDecimal("0");
		BigDecimal cuentasCobrarSaldos    = new BigDecimal("0");
		BigDecimal cuentasPagarSaldos    = new BigDecimal("0");
		BigDecimal descuentosComisionesSaldos    = new BigDecimal("0");
				
		String montoSaldos=null;	
		//libro = new XSSFWorkbook();					
		//XSSFSheet hoja = null;
		//Declaracion de variables del metodo
		int posicion = 0;		
		String  textoSumatoria="";
				
		String codTipoSeguro="";
		String codTipoReaseguro="";
		String codSocio="";		
	    String monedaModeloValor=null;
	    String monedaCuentaValor = null;
		String prefigoNombreHoja = null;
		
		String monedaModeloCiclas = "";
		
	    /*String tipoReaseguroValor = null;*/
	    String tipoContrato = "";
	    String codigoRiesgo = "";
	    String hojaModeloNombre = "";
	    String nombreSocioCuenta = "";
	    
	    String detalleCuentaValor ="";
	    String tipoReaseguroValor ="";
	    String tipoMovimientoValor ="";
	    
	    Integer signo;
	    
	    String cuenta_prima = "";
	    String cuenta_dscto = "";
	    
	    Integer numero_diario;
	    
	    String tipo_diario = "";
	    String glosa ="";
	    
	    Double montoPrima = 0.0;
	    Double montoDescuento = 0.0;
	    
	    Double monto_operacion;
	    
	    String numeroCuenta = null;
	    
	    /*ESTILOS DE LAS CELDAS CABECERA*/	    
		CellStyle estilosLibro=null;
        XSSFFont estiloTexto = null;
        XSSFFont estiloTexto2 = null;
                
        estiloTexto2 = libro.createFont();
		estiloTexto2.setFontHeightInPoints((short) 8);
		estiloTexto2.setColor(XSSFFont.COLOR_RED);
        
		estiloTexto = libro.createFont();	
		estilosLibro = libro.createCellStyle();
		estiloTexto.setFontHeightInPoints((short) 8);									      											        
        estilosLibro.setAlignment(CellStyle.ALIGN_CENTER);
        estilosLibro.setVerticalAlignment(CellStyle.ALIGN_FILL);									        
    	estilosLibro.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
	    estilosLibro.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
	    estilosLibro.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
	    estilosLibro.setFont(estiloTexto);
	    
	    CellStyle estilosLibro1=null;	    
	    estilosLibro1 = libro.createCellStyle();										      											        
		estilosLibro1.setAlignment(CellStyle.ALIGN_LEFT);
		estilosLibro1.setVerticalAlignment(CellStyle.ALIGN_FILL);									        
		estilosLibro1.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		estilosLibro1.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		estilosLibro1.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		estilosLibro1.setFont(estiloTexto);
	    				
	    XSSFDataFormat df = libro.createDataFormat();
		
	    CellStyle estilosLibro2 =null;
	    estilosLibro2 = libro.createCellStyle();	
	    estilosLibro2.setDataFormat(df.getFormat("#,##0.00"));
	    estilosLibro2.setAlignment(CellStyle.ALIGN_RIGHT);
	    estilosLibro2.setVerticalAlignment(CellStyle.ALIGN_FILL);									        
	    estilosLibro2.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
	    estilosLibro2.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
	    estilosLibro2.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
	    estilosLibro2.setFont(estiloTexto);
	    	    	            
	    CellStyle estilosLibro3 = null;
		estilosLibro3 = libro.createCellStyle();
		estilosLibro3.setAlignment(CellStyle.ALIGN_CENTER);
		estilosLibro3.setVerticalAlignment(CellStyle.ALIGN_FILL);
		estilosLibro3.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		estilosLibro3.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		estilosLibro3.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		estilosLibro3.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		estilosLibro3.setFillForegroundColor(HSSFColor.GREEN.index);
		estilosLibro3.setFont(estiloTexto);
		
		
		CellStyle estilosLibro4 = null;
		estilosLibro4 = libro.createCellStyle();
		estilosLibro4.setAlignment(CellStyle.ALIGN_LEFT);
		estilosLibro4.setVerticalAlignment(CellStyle.ALIGN_FILL);
		estilosLibro4.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		estilosLibro4.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		estilosLibro4.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		estilosLibro4.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		estilosLibro4.setFillForegroundColor(HSSFColor.GREEN.index);
		estilosLibro4.setFont(estiloTexto);
		
		CellStyle estilosLibro5 = null;
		estilosLibro5 = libro.createCellStyle();
		estilosLibro5.setDataFormat(df.getFormat("#,##0.00"));
		estilosLibro5.setAlignment(CellStyle.ALIGN_RIGHT);
		estilosLibro5.setVerticalAlignment(CellStyle.ALIGN_FILL);
		estilosLibro5.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		estilosLibro5.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		estilosLibro5.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		estilosLibro5.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		estilosLibro5.setFillForegroundColor(HSSFColor.GREEN.index);
		estilosLibro5.setFont(estiloTexto);
		
		CellStyle estilosLibro6 = null;
		estilosLibro6 = libro.createCellStyle();
		estilosLibro6.setAlignment(CellStyle.ALIGN_CENTER);
		estilosLibro6.setVerticalAlignment(CellStyle.ALIGN_FILL);
		estilosLibro6.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		estilosLibro6.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		estilosLibro6.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		estilosLibro6.setFont(estiloTexto2);
		
		
		CellStyle estilosLibro7 = null;
		estilosLibro7 = libro.createCellStyle();
		estilosLibro7.setAlignment(CellStyle.ALIGN_LEFT);
		estilosLibro7.setVerticalAlignment(CellStyle.ALIGN_FILL);
		estilosLibro7.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		estilosLibro7.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		estilosLibro7.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		estilosLibro7.setFont(estiloTexto2);
		
		CellStyle estilosLibro8 = null;
		estilosLibro8 = libro.createCellStyle();
		estilosLibro8.setDataFormat(df.getFormat("#,##0.00"));
		estilosLibro8.setAlignment(CellStyle.ALIGN_RIGHT);
		estilosLibro8.setVerticalAlignment(CellStyle.ALIGN_FILL);
		estilosLibro8.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		estilosLibro8.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		estilosLibro8.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		estilosLibro8.setFont(estiloTexto2);
	    	    	            

		CellStyle estilosLibro9 = null;
		estilosLibro9 = libro.createCellStyle();
		estilosLibro9.setAlignment(CellStyle.ALIGN_CENTER);
		estilosLibro9.setVerticalAlignment(CellStyle.ALIGN_FILL);
		estilosLibro9.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		estilosLibro9.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		estilosLibro9.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		estilosLibro9.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		estilosLibro9.setFillForegroundColor(HSSFColor.GREEN.index);
		estilosLibro9.setFont(estiloTexto2);
		
		
		CellStyle estilosLibro10 = null;
		estilosLibro10 = libro.createCellStyle();
		estilosLibro10.setAlignment(CellStyle.ALIGN_LEFT);
		estilosLibro10.setVerticalAlignment(CellStyle.ALIGN_FILL);
		estilosLibro10.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		estilosLibro10.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		estilosLibro10.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		estilosLibro10.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		estilosLibro10.setFillForegroundColor(HSSFColor.GREEN.index);
		estilosLibro10.setFont(estiloTexto2);
		
		CellStyle estilosLibro11 = null;
		estilosLibro11 = libro.createCellStyle();
		estilosLibro11.setDataFormat(df.getFormat("#,##0.00"));
		estilosLibro11.setAlignment(CellStyle.ALIGN_RIGHT);
		estilosLibro11.setVerticalAlignment(CellStyle.ALIGN_FILL);
		estilosLibro11.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		estilosLibro11.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		estilosLibro11.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		estilosLibro11.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		estilosLibro11.setFillForegroundColor(HSSFColor.GREEN.index);
		estilosLibro11.setFont(estiloTexto2);
	    
	    int detalleFilas = 0;
    	double validadorRespuesta = 0;
	    		
        try {
			List<ParametrosSBSModeloUnoDetalle> objListParametros = reporteSBSModeloUnoDetalleService.listaTramaParametros();
						
			if (objListParametros.size()>0){
			
				//for (ListaReporte objListaReporte: objListaReporte){
						
					//if(objListaReporte.getMonedaModelo().equalsIgnoreCase("PEN"))
				    if(monedaModelo.equalsIgnoreCase("PEN"))
					{prefigoNombreHoja="PEN"; monedaModeloValor = "Nuevos Soles"; monedaModeloCiclas="PEN";}
					else{prefigoNombreHoja="USD"; monedaModeloValor = "Dolares Americanos"; monedaModeloCiclas ="USD";};
					
					//if(objListaReporte.getMonedaCuenta().equalsIgnoreCase("PEN"))
					if(monedaCuenta.equalsIgnoreCase("PEN"))
					{prefigoNombreHoja=prefigoNombreHoja+"S"; monedaCuentaValor="PEN"; }
					else{prefigoNombreHoja=prefigoNombreHoja+"D"; monedaCuentaValor="USD";};	
															
					//if(objListaReporte.getTipoReaseguro().equalsIgnoreCase("")){};
													
					for (ParametrosSBSModeloUnoDetalle objList : objListParametros) {//INICIOFOR1
            	
						contadorLista=contadorLista+1;
						
						codTipoSeguro    = reporteSBSModeloUnoDetalleService.selectParametroByPrimaryKey(objList.getCodTipoSeguro()).getDescripcion();						
						codTipoReaseguro = reporteSBSModeloUnoDetalleService.selectParametroByPrimaryKey(objList.getCodTipoReaseguro()).getDescripcion();
						
						if(codTipoSeguro.equalsIgnoreCase("REASEGURO") && codTipoReaseguro.equalsIgnoreCase(tipoReaseguro)){//INICIOIF1																				
						
							//EVALUAMOS LA MONEDA
						    if(monedaCuentaValor.equalsIgnoreCase("PEN")){
						    	
						    	numeroCuenta = String.valueOf(objList.getCuentaSaldoSoles());
						    	  if(numeroCuenta.length()>4){
						    		  if(numeroCuenta.substring(0,2).equalsIgnoreCase("14") 
						    		    || numeroCuenta.substring(0,2).equalsIgnoreCase("24") ){
						    			//SE EVALUA QUE LAS CUENTAS TENGAN FORMATO CORRECTO
						    		  }else{
						    			continue;
						    		  }						    		   
						    	  }else{
						    		continue;
						    	  }
						    	  
						    }else{
						    	numeroCuenta =String.valueOf(objList.getCuentaSaldoDolares());
						    	if(numeroCuenta.length()>4){
						    		  if(numeroCuenta.substring(0,2).equalsIgnoreCase("14") 
						    		    || numeroCuenta.substring(0,2).equalsIgnoreCase("24") ){
						    			//SE EVALUA QUE LAS CUENTAS TENGAN FORMATO CORRECTO
						    		  }else{
						    			continue;
						    		  }						    		  
						    	  }else{
						    		continue;
						    	  }
						    };
								
						    //EVALUAMOS LA CLASIFICACION DE LA CUENTA						    
						    detalleCuentaValor  =reporteSBSModeloUnoDetalleService.selectParametroByPrimaryKey(objList.getCodTipoCuenta()).getDescripcion();						    
						    tipoMovimientoValor =reporteSBSModeloUnoDetalleService.selectParametroByPrimaryKey(objList.getCodTipoMovimiento()).getDescripcion();
						    tipoReaseguroValor  =reporteSBSModeloUnoDetalleService.selectParametroByPrimaryKey(objList.getCodTipoReaseguro()).getDescripcion();
						    if(tipoMovimientoValor.equals("PAGAR")){
						    	signo = -1;			    	
						    }else{
						    	signo = 1;
						    }
						    						    
						    cuenta_prima = "";
						    cuenta_dscto ="";
						    
						    //EVALUAMOS LA CUENTA
						    if(detalleCuentaValor.equalsIgnoreCase("PRIMA") && codTipoReaseguro.equalsIgnoreCase("CEDIDO") && tipoMovimientoValor.equalsIgnoreCase("PAGAR") && monedaCuentaValor.equalsIgnoreCase("PEN") ){
						    	cuenta_prima = String.valueOf(objList.getCuentaPrimaSoles());
								cuenta_dscto = String.valueOf(objList.getCuentaDescuentoSoles());						    	
						    }else if(detalleCuentaValor.equalsIgnoreCase("PRIMA") && codTipoReaseguro.equalsIgnoreCase("CEDIDO") && tipoMovimientoValor.equalsIgnoreCase("PAGAR") && !monedaCuentaValor.equalsIgnoreCase("PEN")){
						    	cuenta_prima = String.valueOf(objList.getCuentaPrimaDolares());
								cuenta_dscto = String.valueOf(objList.getCuentaDescuentoDolares());						    	
						    }else{
						    	cuenta_prima = "";
								cuenta_dscto ="";
						    }
						    						   						    
						    if(numeroCuenta!= null){
						    	
						    	tipoContrato= objList.getCodTipoContrato();
						    	codigoRiesgo = objList.getRiesgo().toString();	
						    	
						    	
						    	if(!(codSocio.toString().trim().equalsIgnoreCase(objList.getCodEmpresa().toString().trim())))
								{//INICIOIF2		
						    									          							    		
								    if(!codSocio.equalsIgnoreCase("")){						    			
								   
								    	
								       generarCuentasPagos(objListaCuentasDataSunCuentasPago,filasExcel,hoja,objListParametros,monedaModelo,monedaCuenta,tipoReaseguroValor,codSocio, primasPagar,  primasCobrar,  siniestrosCobrar,  siniestrosPagar,  cuentasCobrar,  cuentasPagar, descuentosComisiones,saldoDeudor,saldoAcreedor,modeloReporeParam,periodoAnioParam,periodoTrimestreParam);						    								    		
								    		
								       primasPagar  = new BigDecimal("0");
									   primasCobrar = new BigDecimal("0");
									   siniestrosCobrar = new BigDecimal("0");
									   siniestrosPagar  = new BigDecimal("0");
									   cuentasCobrar    = new BigDecimal("0");
									   cuentasPagar     = new BigDecimal("0");
									   descuentosComisiones = new BigDecimal("0");
									   saldoDeudor = new BigDecimal("0");
									   saldoAcreedor = new BigDecimal("0");
									   
									   
									   if(hoja.getPhysicalNumberOfRows() == 20){
										   
										   libro.removeSheetAt(libro.getNumberOfSheets()-1);
										   
									   }
                                          									    									       									   	
								       
								   }
								    		
								       nombreSocioCuenta ="";
								       nombreSocioCuenta = reporteSBSModeloUnoDetalleService.selectEmpresaByPrimaryKey(objList.getCodEmpresa()).getDescripcion();				    		
								       hojaModeloNombre = prefigoNombreHoja+"REA"+tipoReaseguro.substring(0,3)+"-"+nombreSocioCuenta;
								       codSocio = objList.getCodEmpresa().toString().trim();
								     								      							       
								       //SE CREA UNA HOJA DENTRO DEL LIBRO
								       hoja = libro.createSheet(hojaModeloNombre);	
								       entroCreacionHoja = true;
								       creadoHoja = true;
								       //METODO ARMA LA CABECERA DEL REPORTE 
								       crearCabeceraReporte(codTipoReaseguro.toUpperCase(),nombreSocioCuenta,monedaModelo,hoja,monedaCuenta,objList.getCodEmpresa(),periodoAnioParam,periodoTrimestreParam);						    		
								       filasExcel = 12;					
								       //METODO ARMA EL CUERPO
								       	
								}//INICIOIF2
						    									    
						    		for (int i = 0; i < objListaCuentasDataSun.size(); i++) {//INICIOFOR1
																    		
						    			  if(objListaCuentasDataSun.get(i).getCodigoCuenta().trim() != "" 
						    			     && numeroCuenta.trim() != ""  
						    			     && objListaCuentasDataSun.get(i).getCodigoCuenta().trim().equalsIgnoreCase(numeroCuenta.trim())
						    			     && codSocio.toString().trim().equalsIgnoreCase(objList.getCodEmpresa().toString().trim())){
						    				  
						    				  double valorCuneta		=	0;
									          double valorDescuento		=	0;
									          double valorSaldoAcreedor	=	0;
									          double valorSaldoDeudor	=	0;
						    				  
						    				  
						    				  filasMillon = hoja.getPhysicalNumberOfRows();
						    				  
						    				  if(filasMillon == 1000000){
						    					  crearHojaMillon(codTipoReaseguro.toUpperCase(),nombreSocioCuenta,monedaModelo,hoja,monedaCuenta,objList.getCodEmpresa(),hojaModeloNombre);
						    				  }
						    				  
		  						    				  						    				  						    			
						    				  XSSFRow fila = hoja.createRow(filasExcel);
						    				  //EVALUAMOS EL MONTO DE LA OPERACION
						    				  if(monedaModelo.equalsIgnoreCase("PEN")){
						    					  monto_operacion = redondeoDouble(Double.valueOf(objListaCuentasDataSun.get(i).getImporteBase()));
						    				  }else{
						    					  monto_operacion = redondeoDouble(Double.valueOf(objListaCuentasDataSun.get(i).getImporteTransaccion()));
						    				  }
						    				  
						    				  //VALIDANDO EN JOURNAL REMAN PARA DOLARES MONTOS IGUALES A 0 NO APLICAN
						    				  if(objListaCuentasDataSun.get(i).getJournalType().equalsIgnoreCase("REMAN") 
						    				     && prefigoNombreHoja.equalsIgnoreCase("USDD") 
						    				     && (monto_operacion == 0 || monto_operacion == 0.00)){
						    					  						    									    					  
						    					  continue;						    					  						    				  
						    				  
						    				  }
						    				  
						    				  
						    				  boolean  noEncontroFormato = false;
						    				  boolean  montoNivelacionMayor = false;
						    				  
						    				  boolean flag=false;
						    				  String glosa2 = objListaCuentasDataSun.get(i).getGlosa().toString();
						    				  
						    				  //VALIDANDO  SI EL MONTO NIVELACION DIFERENTE DE 0   
						    				  if(objListaCuentasDataSun.get(i).getJournalType().equalsIgnoreCase("REMAN") 
								    				     && prefigoNombreHoja.equalsIgnoreCase("USDD") 
								    				     && (monto_operacion > 0 || monto_operacion > 0.00)){
						    					  
						    					  montoNivelacionMayor = true;
						    					  
						    				  }						    				
						    				  
						    				  //NO PINTAR COMO ERROR LAS QUE NIVELACIONES 
									          if(   !(objListaCuentasDataSun.get(i).getJournalType().equalsIgnoreCase("REMAN") ) ){
							    					
									        	  //VALIDANDO EL FORMATO DE LA GLOSA TIENE QUE CONTENER BO O PLANILLA							    				    
										        	noEncontroFormato = true;
										        	boolean  noEncontroGuion=false;
										        	boolean  buscaBO = false;
										        	boolean  buscaPL = false;
							    				    String busquedaBO = "BO";
										        	String busquedaPL = "PL";										        	
										        		
										        	boolean  busca = false;
										        	String[] parte = glosa2.split("_");

										        	for(String obj2 : parte){
				 
											        	if(busquedaBO.equalsIgnoreCase(obj2)){ 
											        		busca = true;
											        		buscaBO = true;
											        		noEncontroFormato=false;
											        		if(!glosa2.contentEquals("BO_")){
											        			noEncontroGuion=true;
											        		}
											        	}else if(busquedaPL.equalsIgnoreCase(obj2)){
											        		busca = true;
											        		buscaPL = true;
											        		noEncontroFormato=false;
															if(!glosa2.contentEquals("PL_")){
																noEncontroGuion=true;									        			
												             }
											        	}
										        	
										        	}
							    				  	
							    				
									        	  
							    			  }		
						    				  
						    				  						    				 
						    				  ReportesGeneradosSBSModeloUnoDetalle listaReporteFiltros = null;																									
											  listaReporteFiltros  = reporteSBSModeloUnoDetalleService.consultarReportesParaErrores(modeloReporeParam,periodoAnioParam,periodoTrimestreParam);	
						    				  
											  if(listaReporteFiltros != null && noEncontroFormato){
												  String nombreReporte = "";
												  nombreReporte = obtenerNombreReporte(modeloReporeParam, periodoAnioParam, periodoTrimestreParam);
												  grabarErrorLog(listaReporteFiltros.getCodReporte().longValue(),nombreReporte+" || "+hojaModeloNombre,glosa2,"","No cuenta con formato correcto para BO o Planilla");
												  reporteSBSModeloUnoDetalleService.actualizarEstadoReporte(listaReporteFiltros.getCodReporte().longValue());
											  }
											  
											  if(listaReporteFiltros != null && montoNivelacionMayor){
												  String nombreReporte = "";
												  nombreReporte = obtenerNombreReporte(modeloReporeParam, periodoAnioParam, periodoTrimestreParam);
												  grabarErrorLog(listaReporteFiltros.getCodReporte().longValue(),nombreReporte+" || "+hojaModeloNombre,glosa2,"","Monto nivelacion superior diferente de 0");
												  reporteSBSModeloUnoDetalleService.actualizarEstadoReporte(listaReporteFiltros.getCodReporte().longValue());
											  }
											  
											  
						    				  
						    										    										    				  
						    				  //OBTENEMOS TIPO DIARIO
						    				  tipo_diario 	=  objListaCuentasDataSun.get(i).getTipoDiario();
						    				  numero_diario =  objListaCuentasDataSun.get(i).getNumerodiario();
						    				  glosa 		=  objListaCuentasDataSun.get(i).getGlosa();
						    				  
						    				  //OBTENEMOS SALDOS DE CUENTA PRIMA Y CUENTA DESCUENTO
											    if(!cuenta_prima.equals("") && !cuenta_dscto.equals("")){
											    	montoPrima     =  obtener_Monto_Prima(objListaCuentasDataSunCuentasCuarenta,cuenta_prima,tipo_diario,numero_diario,glosa,monedaModelo,monto_operacion);
											    	montoDescuento = obtener_Monto_Dscto(objListaCuentasDataSunCuentasCuarenta,cuenta_dscto,tipo_diario,numero_diario,glosa,monedaModelo,monto_operacion,montoPrima);
											    }
											    else{
											    	montoPrima     = 0.0;
											    	montoDescuento = 0.0;
											    }
											    
											    //BUSCAMOS LA PRIMA TOTAL Y DESCUENTO PARA EL TIPO DE DIARIO FC001 O FC003
											    if( numeroCuenta.substring(0,2).equals("24") && (tipo_diario.equals("FC001") || tipo_diario.equals("FC003")) ){
											    	
											    	if(montoPrima == 0.0 && montoDescuento == 0.0){
											    		
											    		montoPrima     = obtener_Monto_Cuenta24_Prima(objListaCuentasDataSun,numeroCuenta,tipo_diario,numero_diario,glosa,monedaModelo,"C",monto_operacion);
											    		montoDescuento = obtener_Monto_Cuenta24_Dscto(objListaCuentasDataSun,numeroCuenta,tipo_diario,numero_diario,glosa,monedaModelo,"D",monto_operacion);
											    		
											    	}											    	
											    }
											    
											    //SI NO SE ENCONTRO MONTO EN CUENTA FC001 O FC003, PINTEMOS LINEA DE COLOR
											    if( montoPrima == -1 && montoDescuento == -1 ){
											    	
											      flag=true;								    												    
											    }
											    else if(numeroCuenta.substring(0,2).equals("24") 
											    		&& ( (montoPrima == 0.0  && montoDescuento == 0.0) || (montoPrima != 0.0 && montoDescuento == 0.0) ) 
											    		&& ( tipoMovimientoValor.equals("PAGAR") &&  detalleCuentaValor.equalsIgnoreCase("PRIMA") &&  codTipoReaseguro.equalsIgnoreCase("CEDIDO")) ){
                                                  
											      flag=true;
											    	
											    }else{
											    
											    }
											    											     
												primasPagarSaldos  = new BigDecimal("0");
												primasCobrarSaldos = new BigDecimal("0");
												siniestrosCobrarSaldos = new BigDecimal("0");
												siniestrosPagarSaldos  = new BigDecimal("0");
												cuentasCobrarSaldos    = new BigDecimal("0");
												cuentasPagarSaldos    = new BigDecimal("0");
												descuentosComisionesSaldos  = new BigDecimal("0");
												
												 
												montodescuento = null;
						        				montoSaldoAcreedor = null;
						        				entroPrimasDescuentos = false;
						        				
						        				XSSFCell celda = null; 
						        				int clasificacion = 0;
						        				
								    			for (int j = 0; j < 17; j++) {//INICIOFOR2
								    			
								    		        hoja.setColumnWidth(6, 15000);								    		     
								    		        hoja.setColumnWidth(1, 10000);	
								    				        	        								        	
								    		        celda = fila.createCell(j);
								    		        
								    				estilosLibro.setAlignment(CellStyle.ALIGN_CENTER);
								    				textoSumatoria ="";	
								    				XSSFRichTextString texto = new XSSFRichTextString("");
								    				
								    				if(j == 0){
								    				fechaTransaccion=objListaCuentasDataSun.get(i).getFechaTransaccion();
								    				texto = new XSSFRichTextString(objListaCuentasDataSun.get(i).getFechaTransaccion()) ;
								    				}
								    				
										        	if(j == 4){								        		
										        	texto = new XSSFRichTextString(objListaCuentasDataSun.get(i).getNumerodiario().toString()) ;								        	
										        	}
										        	
										        	if(j == 6){
										        		
										        	estilosLibro.setAlignment(CellStyle.ALIGN_RIGHT);
										        	celda.setCellStyle(estilosLibro);
										        	texto = new XSSFRichTextString(objListaCuentasDataSun.get(i).getGlosa().toString()) ;
										        	}
										        	
										        	if(j == 7){
											        texto = new XSSFRichTextString(objList.getRiesgo().toString()) ;
											        }
										        	
										        	if(j == 8 || j == 9 || j == 10 || j ==11 || j == 12 || j == 13 || j == 14 || j == 15 ){
										            
										        	String montoFinal ="";
										        	//ARMANDO CLASIFICACION	
										        	
										        	estilosLibro.setAlignment(CellStyle.ALIGN_LEFT);					
										        	celda.setCellStyle(estilosLibro);
										        	clasificacion= 0;
										        	clasificacion = clasificarCuenta(detalleCuentaValor,tipoMovimientoValor,tipoReaseguroValor);
										        		
										        	if( j == clasificacion ){
										        	   										        	
										        		if(numeroCuenta.substring(0,2).equals("14")){
										        			
										        			if(signo == 1 && monto_operacion < 0){
										        				
										        				signo = -1;
										        				
										        				if (montoPrima == -1 && montoDescuento ==-1){
										        					
										        					montoFinal =String.valueOf((signo * monto_operacion));
										        					textoSumatoria = montoFinal;										        								        					
										        					texto = new XSSFRichTextString(montoFinal);
										        				
										        				}else if(montoPrima != 0 && montoDescuento != 0){
										        					
										        					entroPrimasDescuentos = true;
										        					montoFinal =String.valueOf(Math.abs(montoPrima));
										        					textoSumatoria = montoFinal;										        					
										        					texto = new XSSFRichTextString(montoFinal);
										        															        				    										        					
										        					montodescuento = null;
										        					montoSaldoAcreedor = null;
										        					
										        					montodescuento = redondeoDouble(Math.abs(montoDescuento));
										        					montoSaldoAcreedor = (signo * monto_operacion);
										        						
										        					//Worksheets(HOJA_MODELO_NOMBRE).Cells(row_pivote, column_titulo + 6) = Abs(monto_dscto)
							                                        //Worksheets(HOJA_MODELO_NOMBRE).Cells(row_pivote, column_titulo + 8) = SIGNO * monto_operacion										        															        					
										        					
										        				}else{
										        				
										        					montoFinal =String.valueOf((signo * monto_operacion));
										        					textoSumatoria = montoFinal;										        					
										        					texto = new XSSFRichTextString(montoFinal);
										        				}
										        				
										        				signo = 1;
										        				
										        			}else{
										        				
										        				if (montoPrima == -1 && montoDescuento ==-1){
										        					
										        					montoFinal =String.valueOf((signo * monto_operacion));
										        					textoSumatoria = montoFinal;
										        					
										        					texto = new XSSFRichTextString(montoFinal);
										        				
										        				}else if(montoPrima != 0 && montoDescuento != 0){
										        					
										        					entroPrimasDescuentos = true;
										        					montoFinal =String.valueOf(Math.abs(montoPrima));
										        					textoSumatoria = montoFinal;
										        					
										        					montodescuento = null;
										        					montoSaldoAcreedor = null;
										        					
										        					montodescuento = redondeoDouble(Math.abs(montoDescuento));
										        					montoSaldoAcreedor = (signo * monto_operacion);
										        					
										        					texto = new XSSFRichTextString(montoFinal);
										        					
										        					
										        					
										        					
										        				}else{
										        					montoFinal =String.valueOf((signo * monto_operacion));
										        					textoSumatoria = montoFinal;
										        					
										        					texto = new XSSFRichTextString(montoFinal);
										        				}
										        														        				
										        			}
										        			
										        		}else if(numeroCuenta.substring(0,2).equals("24")){
										        			
										        			if(signo == -1 && monto_operacion > 0){
										        				
										        				signo = 1;
										        				
										        				if (montoPrima == -1 && montoDescuento ==-1){
										        					
										        					montoFinal =String.valueOf((signo * monto_operacion));
										        					textoSumatoria = montoFinal;
										        					
										        					texto = new XSSFRichTextString(montoFinal);
										        				
										        				}else if(montoPrima != 0 && montoDescuento != 0){
										        					
										        					entroPrimasDescuentos = true;
										        					montoFinal =String.valueOf(Math.abs(montoPrima));
										        					textoSumatoria = montoFinal;
										        				
										        					montodescuento = null;
										        					montoSaldoAcreedor = null;
										        					
										        					montodescuento = redondeoDouble(Math.abs(montoDescuento));
										        					montoSaldoAcreedor = (signo * monto_operacion);
										        															        					
										        					texto = new XSSFRichTextString(montoFinal);
										        					
										        				}else{
										        					montoFinal =String.valueOf((signo * monto_operacion));
										        					textoSumatoria = montoFinal;
										        					
										        					texto = new XSSFRichTextString(montoFinal);
										        				}
										        				
										        				signo = -1;
										        				
										        			}else{
										        				
										        				if (montoPrima == -1 && montoDescuento ==-1){
										        					
										        					montoFinal =String.valueOf((signo * monto_operacion));
										        					textoSumatoria = montoFinal;
										        					
										        					texto = new XSSFRichTextString(montoFinal);
										        				
										        				}else if(montoPrima != 0 && montoDescuento != 0){
										        					
										        					entroPrimasDescuentos = true;
										        					montoFinal =String.valueOf(Math.abs(montoPrima));
										        					textoSumatoria = montoFinal;
										        				
										        					montodescuento = null;
										        					montoSaldoAcreedor = null;
										        					
										        					montodescuento = redondeoDouble(Math.abs(montoDescuento));
										        					montoSaldoAcreedor = (signo * monto_operacion);
										        					
										        					texto = new XSSFRichTextString(montoFinal);
										        					
										        				}else{
										        					montoFinal =String.valueOf((signo * monto_operacion));
										        					textoSumatoria = montoFinal;
										        					
										        					texto = new XSSFRichTextString(montoFinal);
										        				}
										        														        				
										        			}
										        													        			
										        		 }
										        												        												        		
										              }
										        	
										        	}
										                   			
										        
										          //VALIDAMOS EL ESTILO SI LOS MONTOS DETALLES SON MENORES AL RESUMEN											        	
										          if(j == 0){	
										        	  clasificacion = 0;
										        	  clasificacion = clasificarCuenta(detalleCuentaValor,tipoMovimientoValor,tipoReaseguroValor);
										        	  
												      int ValidarDor = validarMontoDetalle( glosa,
												        		  							objListaCuentasDataSun.get(i).getTipoCambio(),
												        		  							prefigoNombreHoja,
													        			                    clasificacion,
													        			                    monedaCuenta,
													        			                    redondeoDouble(Math.abs((signo * monto_operacion))),
													        			                    redondeoDouble(Math.abs(montoDescuento)),
													        			                    redondeoDouble(Math.abs(montoPrima)) );
												      
												      if(ValidarDor == 1){
												    	  noEncontroFormato = true;
												      }
												      
										          }										         
										          //VALIDAMOS EL ESTILO SI LOS MONTOS DETALLES SON MENORES AL RESUMEN										          
										          
										          if(j == 8 || j == 9 || j == 10 || j ==11 || j == 12 || j == 13 || j == 14 || j == 15 || j == 16){	
										        	  
										        	  
										        	  if(flag){
			                                            	
			                                            	celda.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			    											celda.setCellStyle(estilosLibro5);
			                                            	
			                                          }else{
														    celda.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
														    celda.setCellStyle(estilosLibro2);
			                                          }
			                                          if(noEncontroFormato){
			                                            	celda.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
															celda.setCellStyle(estilosLibro8);  	
			                                          }
										        	  
										        	 
										          }else if(j == 6){
										        	  
										        	  if(flag){															
														  
										        		  celda.setCellStyle(estilosLibro4);
			                                            	
													  }else{
														  
														  celda.setCellStyle(estilosLibro1);
													 
													  }
													  if(noEncontroFormato){
														  
														  celda.setCellStyle(estilosLibro7);	
													  }
										        	  
										        	  
										          }else{
										        	  
										        	  if(flag){
															
															celda.setCellStyle(estilosLibro3);
														
													  }else{
														
														  celda.setCellStyle(estilosLibro);
													  
													  }
													  if(noEncontroFormato){
														
														  celda.setCellStyle(estilosLibro6);	
														
													  }
										        	
										          }
										          
										          
										          
										          if(j == 8){										        	  
										        	  if (texto.getString() != null && texto.getString().length() > 0){
										        	       primasPagar =primasPagar.add(BigDecimal.valueOf(redondeoDouble(Double.parseDouble(textoSumatoria))));
										        	       primasPagarSaldos = BigDecimal.valueOf(redondeoDouble(Double.parseDouble(textoSumatoria)));
										              }
										          }else if(j == 9){
										        	  if (texto.getString() != null && texto.getString().length() > 0){
										        	  primasCobrar =primasCobrar.add(BigDecimal.valueOf(redondeoDouble(Double.parseDouble(textoSumatoria))));
										        	  primasCobrarSaldos = BigDecimal.valueOf(redondeoDouble(Double.parseDouble(textoSumatoria)));
										        	  }
										          }else if(j == 10){
										        	  if (texto.getString() != null && texto.getString().length() > 0){
										        	  siniestrosCobrar =siniestrosCobrar.add(BigDecimal.valueOf(redondeoDouble(Double.parseDouble(textoSumatoria))));
										        	  siniestrosCobrarSaldos =BigDecimal.valueOf(redondeoDouble(Double.parseDouble(textoSumatoria)));
										        	  }
										          }else if(j == 11){
										        	  if (texto.getString() != null && texto.getString().length() > 0){
										        	  siniestrosPagar =siniestrosPagar.add(BigDecimal.valueOf(redondeoDouble(Double.parseDouble(textoSumatoria))));
										        	  siniestrosPagarSaldos =BigDecimal.valueOf(redondeoDouble(Double.parseDouble(textoSumatoria)));
										        	  }
										          }else if(j == 12){
										        	  if (texto.getString() != null && texto.getString().length() > 0){
										        	  cuentasCobrar =cuentasCobrar.add(BigDecimal.valueOf(redondeoDouble(Double.parseDouble(textoSumatoria))));
										        	  cuentasCobrarSaldos =BigDecimal.valueOf(redondeoDouble(Double.parseDouble(textoSumatoria)));
										        	  }
										          }else if(j == 13){
										        	  if (texto.getString() != null && texto.getString().length() > 0){
										        	  cuentasPagar =cuentasPagar.add(BigDecimal.valueOf(redondeoDouble(Double.parseDouble(textoSumatoria))));
										        	  cuentasPagarSaldos =BigDecimal.valueOf(redondeoDouble(Double.parseDouble(textoSumatoria)));
										        	  }
										          }else if(j == 14 && entroPrimasDescuentos == true){
										        	  
										        	  if (montodescuento != null){
										        		  descuentosComisiones =descuentosComisiones.add(BigDecimal.valueOf(montodescuento));
										        		  descuentosComisionesSaldos =BigDecimal.valueOf(montodescuento);
											          }
										        	   
                                                       
										          }else if(j == 15){
										          
											        	if( (primasPagarSaldos.add(siniestrosPagarSaldos).add(cuentasPagarSaldos)).compareTo((primasCobrarSaldos.add(siniestrosCobrarSaldos).add(cuentasCobrarSaldos).add(descuentosComisionesSaldos)))<0){
											        		
											        		  montoSaldos="";										        		  										        		  									        		  
											        		  montoSaldos = String.valueOf(redondeoDouble(Double.parseDouble((primasCobrarSaldos.add(siniestrosCobrarSaldos).add(cuentasCobrarSaldos).add(descuentosComisionesSaldos)).subtract((primasPagarSaldos.add(siniestrosPagarSaldos).add(cuentasPagarSaldos))).toString())));
											        		  texto = new XSSFRichTextString(montoSaldos);
											        		  if(montoSaldos.length()>0){
											        		  saldoDeudor= saldoDeudor.add(BigDecimal.valueOf(Double.parseDouble(montoSaldos)));
											        		  }
											        	}else{
											        		  
											        		  montoSaldos="";										        		  
											        		  texto = new XSSFRichTextString(montoSaldos);
											        	}
											        	  
										          }else if(j == 16){
										        	   
										        	  if(entroPrimasDescuentos == false){
										        	  
											        	   if( (primasPagarSaldos.add(siniestrosPagarSaldos).add(cuentasPagarSaldos)).compareTo((primasCobrarSaldos.add(siniestrosCobrarSaldos).add(cuentasCobrarSaldos)))>0){
												        		
												        		  montoSaldos="";											        		  
												        		  //montoSaldos = formateador.format(redondeoDouble(Double.parseDouble((primasPagarSaldos.add(siniestrosPagarSaldos).add(cuentasPagarSaldos)).subtract((primasCobrarSaldos.add(siniestrosCobrarSaldos).add(cuentasCobrarSaldos))).toString())));
												        		  montoSaldos = String.valueOf(redondeoDouble(Double.parseDouble((primasPagarSaldos.add(siniestrosPagarSaldos).add(cuentasPagarSaldos)).subtract((primasCobrarSaldos.add(siniestrosCobrarSaldos).add(cuentasCobrarSaldos))).toString())));
												        		  texto = new XSSFRichTextString(montoSaldos);
												        		  if(montoSaldos.length()>0){
												        			  saldoAcreedor= saldoAcreedor.add(BigDecimal.valueOf(Double.parseDouble(montoSaldos)));  
												        		  }
												        		  											        		  
												        	}else{
												        		  
												        		  montoSaldos="";											        		  
												        		  texto = new XSSFRichTextString(montoSaldos);
												        	}  
										        	   
										        	  }
										        	   
										        	  if(entroPrimasDescuentos == true){
											        		 
											        		 if(montoSaldoAcreedor != null){
											        		 
											        			saldoAcreedor = saldoAcreedor.add(BigDecimal.valueOf(montoSaldoAcreedor));  			
											        		 
											        		 }
											        		 
											           }
										        	   
										        	   
										           }else if(j == 16 && entroPrimasDescuentos == true){
										        	   
										           
										           }									       

										         
										          if((j == 8 || j == 9 || j == 10 || j ==11 || j == 12 || j == 13 || j == 14 || j == 15 || j == 16) && entroPrimasDescuentos == false){	
										        	  if(texto.length()>0){
										        		  
										        		  celda.setCellValue(Double.parseDouble(texto.toString()));
										        												        		  
										        		  if(j == 8 || j == 9 || j == 10 || j ==11 || j == 12 || j == 13 || j == 14){
										        			  
										        			  valorCuneta = Double.parseDouble(texto.toString());
										        			  
										        		  }else if(j == 15){
										        			  
										        			  valorSaldoDeudor = Double.parseDouble(texto.toString());
										        			  	  
										        		  }else if(j == 16){
										        			  
										        			  valorSaldoAcreedor = Double.parseDouble(texto.toString());
										        		  
										        		  }
										        		  										        		  
										        		  
										        	  }else{
										        		  celda.setCellValue(texto);
										              }
										          }else if((j == 8 || j == 14 || j == 15 || j == 16) && entroPrimasDescuentos == true){        
										        	  
										        	  if (j == 8 || j == 15){
										        	  
											        	  if(texto.length()>0){
											        		  
											        		  	  celda.setCellValue(Double.parseDouble(texto.toString()));	
											        		  
											        		  if(j == 8){
											        			  valorCuneta = Double.parseDouble(texto.toString());
											        		  }
											        		  
											        		  if(j == 15){
											        			  valorSaldoDeudor = Double.parseDouble(texto.toString());
											        		  }
											        		  
											        	  }else{
											        		  celda.setCellValue(texto);
											              }
										        	  
										        	  }else if(j == 14){																						
										        		 
										        		  if(montodescuento!= null){
											        		  celda.setCellValue(montodescuento);
											        		  valorDescuento =  montodescuento;
											        	  }else{
											        		  celda.setCellValue("");
											              }										        		  
										        	  }else if(j == 16){
										        		  
										        		  if(montoSaldoAcreedor != null){
											        		  celda.setCellValue(montoSaldoAcreedor);
											        		  valorSaldoAcreedor = montoSaldoAcreedor;
											        	  }else{
											        		  celda.setCellValue("");
											              }		
										        	  }
										        	  
										          }else{
										        	  
										        	  	celda.setCellValue(texto);  
										          }
										          										          	
										        										        											        	
											  }//INICIOFOR2								    	
								    
								    		  
								    		 

								    			//GLOSA DESCOMPONIENDO GLOSA PARA OBTENER BO										        										        										        	
									        	validadorRespuesta = 0;	
									        	boolean  encontroBO = false;
									        	boolean  encontroPL = false;
									        	
									        	String busqueda1 = "BO";
									        	String busqueda2 = "PL";
									        	String PL = "";
									        	String BO = "";		
									        	boolean  encontro = false;
									        	String[] parts = glosa.split("_");
									        	detalleFilas = 0;			
									        	cargaOperacionesList = new ArrayList<CargaOperaciones>();
									        	
									        	for(String obj : parts){
									        			    	
										        	if(encontro == true){
										        		
										        		if(encontroBO == true){
										        			BO = obj;
										        		 					        													        			
										        		    	cargaOperacionesList = procesoArchivoService.TraerDetalleCuentas(BO,monedaCuenta);
										        													        		    
										        		}
										        		
										        		if(encontroPL==true){
										        			PL = obj;	
										        			cargaOperacionesList = procesoArchivoService.TraerDetalleCuentas(PL,monedaCuenta);										        		
										        		}
										        		
										        		if(cargaOperacionesList.size()>0){
										        			
										        			detalleFilas =  cargaOperacionesList.size();
										        														       			
										        			validadorRespuesta = generarDetalle(filasExcel,hoja,cargaOperacionesList,glosa,fechaTransaccion,
										        					                            valorCuneta,valorDescuento,valorSaldoAcreedor,valorSaldoDeudor,
										        					                            modeloReporeParam,periodoAnioParam,periodoTrimestreParam,
										        					                            glosa2,hojaModeloNombre,obj,objListaCuentasDataSun.get(i).getTipoCambio(),prefigoNombreHoja);											        			
										        		
										        		}
										        												        		
										        		
										        		encontro = false;
										        		encontroBO = false;
										        		encontroPL = false;
										        		break;
										        		
										        	}
										        						 			 
										        	if(busqueda1.equalsIgnoreCase(obj)){ 
										        		encontro = true;
										        		encontroBO = true;
										        	}else if(busqueda2.equalsIgnoreCase(obj)){
										        		encontro = true;
										        		encontroPL = true;
										        	}									        	 
									        	
									        	}
									        	
 
									        	//ERROR DE FORMATO BO/PLANILLA
									        	
									        	//ERROR DE FORMATO BO/PLANILLA
									        	
									        	//GLOSA DESCOMPONIENDO GLOSA PARA OBTENER BO
									        	
								    			  //FILAS EXCEL
									        	filasExcel = filasExcel+detalleFilas+1;									        	
						    		     }			
						    			  

									}//INICIOFOR1						    								    								    								    									        														    	
						    }	    
						    
					    }//INICIOIF1

					}//INICIOFOR1
					
			  if(entroCreacionHoja==true){		
			   
			   generarCuentasPagos(objListaCuentasDataSunCuentasPago,filasExcel,hoja,objListParametros,monedaModelo,monedaCuenta,tipoReaseguroValor,codSocio,primasPagar,primasCobrar,siniestrosCobrar,siniestrosPagar,cuentasCobrar,cuentasPagar,descuentosComisiones,saldoDeudor,saldoAcreedor,modeloReporeParam,periodoAnioParam,periodoTrimestreParam);
			  
			   primasPagar  = new BigDecimal("0");
			   primasCobrar = new BigDecimal("0");
			   siniestrosCobrar = new BigDecimal("0");
			   siniestrosPagar  = new BigDecimal("0");
			   cuentasCobrar    = new BigDecimal("0");
			   cuentasPagar     = new BigDecimal("0");
			   descuentosComisiones = new BigDecimal("0");
			   saldoDeudor = new BigDecimal("0");
			   saldoAcreedor = new BigDecimal("0");
			   
			   
			   if(hoja.getPhysicalNumberOfRows() == 20){										   
				   libro.removeSheetAt(libro.getNumberOfSheets()-1);   
			   }
			   
			  }
		   }
        			
        } catch (SyncconException e1) {			
        	log.error("Exception(" + e1.getClass().getName() + ") - ERROR: " + e1.getMessage());
			log.error("Exception(" + e1.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e1));			
		}        
  	
                            
      //Se salva el libro.  
        if( (monedaCuenta+monedaModelo+tipoReaseguro).equalsIgnoreCase("USDUSDRecibido") &&  creadoHoja == true){
	        	        
	        try {
	        	
	        	String nombreReporte = "";	        		        		        	
	        	nombreReporte = obtenerNombreReporte(modeloReporeParam, periodoAnioParam, periodoTrimestreParam); 
	        	// BOTCHA
	        	//String ruta = reporteSBSModeloUnoDetalleService.selectParametroByPrimaryKey("1610").getDescripcion();
	        	String ruta = reporteSBSModeloUnoDetalleService.selectParametroByPrimaryKey("1601").getDescripcion();
	            
	        	ruta = ruta+nombreReporte;
	        	
	            FileOutputStream elFichero = new FileOutputStream(ruta);
	            libro.write(elFichero);
	            elFichero.close();
	            
	            // descargando el archivo
	       
	            ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
	            
	            File archivoResp = new File(ruta);
	            FileInputStream in = new FileInputStream(archivoResp);
	            
	            HttpServletResponse response = (HttpServletResponse) contexto.getResponse();
	            byte[] loader = new byte[(int) archivoResp.length()];
	                        
	            //response.addHeader("Content-Disposition", "attachment; filename=" + "ReporteModeloUnoSBS"+".xlsx");
	            //response.addHeader("Content-Disposition", "attachment; filename=" + "ReporteModeloUnoSBS.xlsx");
	            response.addHeader("Content-Disposition", "attachment; filename=" + nombreReporte);
	            	            
	            //response.setContentType("application/vnd.ms-excel");
	            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	            
	            ServletOutputStream out = response.getOutputStream();
	
	            while ((in.read(loader)) > 0) {
	              out.write(loader, 0, loader.length);
	            }
	            in.close();
	            out.close();
	
	            FacesContext.getCurrentInstance().responseComplete();
	        	
	            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Se genero correctamente", null);
	            FacesContext.getCurrentInstance().addMessage(null, facesMsg);
	            
	        } catch (Exception e) {
	        	logger.error(e.getMessage(), e);
	            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
	            FacesContext.getCurrentInstance().addMessage(null, facesMsg);
	        }

         }
        
        logger.info("Fin");
        
        return null;
		
	}
	
	

    private int validarMontoDetalle(String glosa, double tipoCambioParam,
			String prefigoNombreHojaParam, int clasificacion, String monedaModeloParam,
			double montoOperacion,
			double montoDescuentoParam, 
			double montoPrimaParam) {
		    
    	    int respuestaValidarMontoDetalle = 0;
    	
    	    boolean  encontroBO = false;
		    boolean  encontroPL = false;
									        	
            String busqueda1 = "BO";
			String busqueda2 = "PL";
			String PL = "";
			String BO = "";		
			boolean  encontro = false;
			String[] parts = glosa.split("_");
						
			List<CargaOperaciones> objList = new ArrayList<CargaOperaciones>();
			CargaOperaciones objBean = new CargaOperaciones();
									        	
			for(String obj : parts){
									        			    	
			      if(encontro == true){
										        		
						if(encontroBO == true){
						
							BO = obj;		
							
							if(BO.equalsIgnoreCase("34989")){
								
								objList = procesoArchivoService.TraerDetalleCuentasValidar(BO,monedaModeloParam);	
							
							}
							
						
						}
												        		
						if(encontroPL==true){
						
							PL = obj;							
							objList = procesoArchivoService.TraerDetalleCuentasValidar(PL,monedaModeloParam);										        		
						
						}
												        		
						if(cargaOperacionesList.size()>0){
							
							
						  											        														       							
						}										        												        		
												        		
						encontro = false;
						encontroBO = false;
					    encontroPL = false;
						break;
										        		
				}
										        						 			 
				if(busqueda1.equalsIgnoreCase(obj)){ 
				
					encontro = true;
					encontroBO = true;
				
				}else if(busqueda2.equalsIgnoreCase(obj)){
				
					encontro = true;
					encontroPL = true;
				
				}									        	 
									        	
		}
    	 
		
		boolean montoDiferente = false; 	
			
		if(objList.size() > 0){			
				
    	
	    	if(clasificacion == 8 && montoDescuentoParam != 0 && montoPrimaParam != 0){
	    		
	    		//objList = procesoArchivoService.TraerDetalleCuentasValidar(BO,monedaModeloParam);
	    	    
	    		if(objList.size() > 0){
	    		
		    		objBean.setPrimaPagarReacedidos(objList.get(0).getPrimaPagarReacedidos());
		    		objBean.setPrimaPagarReaceptados(objList.get(0).getPrimaPagarReaceptados());
		    		objBean.setSiniestroPagarReaaceptados(objList.get(0).getSiniestroPagarReaaceptados());
		    		objBean.setSiniestroPagarReasedidos(objList.get(0).getSiniestroPagarReasedidos());	    		
		    		objBean.setCuentasCobrar(objList.get(0).getCuentasCobrar());
		    		objBean.setCuentasPagar(objList.get(0).getCuentasPagar());
		    		objBean.setDescuentosComisionesRea(objList.get(0).getDescuentosComisionesRea());
		    		objBean.setSaldoAcreeedor(objList.get(0).getSaldoAcreeedor());
		    		objBean.setSaldoDeudor(objList.get(0).getSaldoDeudor());
		    		
		    		if(redondeoDouble(objBean.getPrimaPagarReacedidos().doubleValue()) != redondeoDouble(montoPrimaParam) ){
		    			
		    			montoDiferente = true;	    		    			
		    		}
		    		
		    		if(redondeoDouble(objBean.getDescuentosComisionesRea().doubleValue()) != redondeoDouble(montoDescuentoParam) ){
		    			
		    			montoDiferente = true;
		    		}
		    		
		    		if(redondeoDouble(objBean.getSaldoAcreeedor().doubleValue()) != redondeoDouble(montoOperacion) ){
		    			
		    			montoDiferente = true;
		    		}
	    		}
	    		
	    	}else{
	    		
	    		
	    		
	    		//objList = procesoArchivoService.TraerDetalleCuentas(BO,monedaModeloParam);
	    		
	    		if(objList.size() > 0){
	    		
		    		objBean.setPrimaPagarReacedidos(objList.get(0).getPrimaPagarReacedidos());
		    		objBean.setPrimaPagarReaceptados(objList.get(0).getPrimaPagarReaceptados());
		    		objBean.setSiniestroPagarReaaceptados(objList.get(0).getSiniestroPagarReaaceptados());
		    		objBean.setSiniestroPagarReasedidos(objList.get(0).getSiniestroPagarReasedidos());	    		
		    		objBean.setCuentasCobrar(objList.get(0).getCuentasCobrar());
		    		objBean.setCuentasPagar(objList.get(0).getCuentasPagar());
		    		objBean.setDescuentosComisionesRea(objList.get(0).getDescuentosComisionesRea());
		    		objBean.setSaldoAcreeedor(objList.get(0).getSaldoAcreeedor());
		    		objBean.setSaldoDeudor(objList.get(0).getSaldoDeudor());
		    		
		    		if(clasificacion == 8){
		    			
		    			if(prefigoNombreHojaParam.equalsIgnoreCase("PEND")){
		    			
			    			if( redondeoDouble(objBean.getPrimaPagarReacedidos().doubleValue()*tipoCambioParam) != redondeoDouble(montoOperacion) ){
			    					    			  
			    				montoDiferente = true;	    				
			    			}
		    			
			    			if( redondeoDouble(objBean.getSaldoAcreeedor().doubleValue()*tipoCambioParam) != redondeoDouble(montoOperacion) ){
			    				
			    				montoDiferente = true;	    				
			    			}
		    			
		    			}else{
		    				
		    				if( redondeoDouble(objBean.getPrimaPagarReacedidos().doubleValue()) != redondeoDouble(montoOperacion) ){
				    			  
			    				montoDiferente = true;	    				
			    			}
		    				
		    				if( redondeoDouble(objBean.getSaldoAcreeedor().doubleValue()) != redondeoDouble(montoOperacion) ){
			    				
			    				montoDiferente = true;	    				
			    			}
		    				
		    			}
		    			
		    			
		    			
		    		} else if(clasificacion == 9){
		    			
		    			if(prefigoNombreHojaParam.equalsIgnoreCase("PEND")){
		    			
			    			if( redondeoDouble(objBean.getPrimaPagarReaceptados().doubleValue()*tipoCambioParam) != redondeoDouble(montoOperacion) ){
			    				
			    				montoDiferente = true;
			    			}
			    			
			    			if( redondeoDouble(objBean.getSaldoDeudor().doubleValue()*tipoCambioParam) != redondeoDouble(montoOperacion) ){
			    				
			    				montoDiferente = true;
			    			}
			    			
		    			}else{
		    			   
		    				if( redondeoDouble(objBean.getPrimaPagarReaceptados().doubleValue()) != redondeoDouble(montoOperacion) ){
			    				
			    				montoDiferente = true;
			    			}
			    			
			    			if( redondeoDouble(objBean.getSaldoDeudor().doubleValue()) != redondeoDouble(montoOperacion) ){
			    				
			    				montoDiferente = true;
			    			}
		    				
		    			}
		    			
		    		} else if(clasificacion == 10){
		    			
		    			
		    			if(prefigoNombreHojaParam.equalsIgnoreCase("PEND")){
		    			
			    			if( redondeoDouble(objBean.getSiniestroPagarReasedidos().doubleValue()*tipoCambioParam) != redondeoDouble(montoOperacion) ){
			    				
			    				montoDiferente = true;
			    			}
			    			
			    			if( redondeoDouble(objBean.getSaldoDeudor().doubleValue()*tipoCambioParam) != redondeoDouble(montoOperacion) ){
			    				
			    				montoDiferente = true;
			    			}
		    			
		    			}else{
		    				
		    				if( redondeoDouble(objBean.getSiniestroPagarReasedidos().doubleValue()*tipoCambioParam) != redondeoDouble(montoOperacion) ){
			    				
			    				montoDiferente = true;
			    			}
			    			
			    			if( redondeoDouble(objBean.getSaldoDeudor().doubleValue()*tipoCambioParam) != redondeoDouble(montoOperacion) ){
			    				
			    				montoDiferente = true;
			    			}
		    			
		    				
		    			}	
			    			
		    		} else if(clasificacion == 11){
		    			
		    			if(prefigoNombreHojaParam.equalsIgnoreCase("PEND")){
		    			
			    			if( redondeoDouble(objBean.getSiniestroPagarReaaceptados().doubleValue()*tipoCambioParam) != redondeoDouble(montoOperacion) ){
			    				
			    				montoDiferente = true;
			    			}
			    			
			    			if( redondeoDouble(objBean.getSaldoAcreeedor().doubleValue()*tipoCambioParam) !=  redondeoDouble(montoOperacion) ){
			    				
			    				montoDiferente = true;	    				
			    			}
			    			
		    			}else{
		    				
		    				if( redondeoDouble(objBean.getSiniestroPagarReaaceptados().doubleValue()*tipoCambioParam) != redondeoDouble(montoOperacion) ){
			    				
			    				montoDiferente = true;
			    			}
			    			
			    			if( redondeoDouble(objBean.getSaldoAcreeedor().doubleValue()*tipoCambioParam) !=  redondeoDouble(montoOperacion) ){
			    				
			    				montoDiferente = true;	    				
			    			}
		    				
		    			}
		    			
		    			
		    		} else if(clasificacion == 12){
		    			
		    			if(prefigoNombreHojaParam.equalsIgnoreCase("PEND")){
		    			
			    			if( redondeoDouble(objBean.getCuentasCobrar().doubleValue()*tipoCambioParam) != redondeoDouble(montoOperacion) ){
			    				
			    				montoDiferente = true;
			    			}
			    			
			    			
			    		    if( redondeoDouble(objBean.getSaldoDeudor().doubleValue()*tipoCambioParam) != redondeoDouble(montoOperacion) ){
			    				
			    				montoDiferente = true;
			    			}
			    		    
		    			}else{
		    				
		    				if( redondeoDouble(objBean.getCuentasCobrar().doubleValue()) != redondeoDouble(montoOperacion) ){
			    				
			    				montoDiferente = true;
			    			}
			    			
			    			
			    		    if( redondeoDouble(objBean.getSaldoDeudor().doubleValue()) != redondeoDouble(montoOperacion) ){
			    				
			    				montoDiferente = true;
			    			}
		    				
		    			}
		    			
		    		} else if(clasificacion == 13){
		    			
		    			if(prefigoNombreHojaParam.equalsIgnoreCase("PEND")){
		    			
			    			if( redondeoDouble(objBean.getCuentasPagar().doubleValue()*tipoCambioParam ) != redondeoDouble(montoOperacion) ){
			    				
			    				montoDiferente = true;
			    			}
			    			
			    			if( redondeoDouble(objBean.getSaldoAcreeedor().doubleValue()*tipoCambioParam ) != redondeoDouble(montoOperacion) ){
			    				
			    				montoDiferente = true;	    				
			    			}
			    			
		    			}else{
		    				
		    				if( redondeoDouble(objBean.getCuentasPagar().doubleValue()) != redondeoDouble(montoOperacion) ){
			    				
			    				montoDiferente = true;
			    			}
			    			
			    			if( redondeoDouble(objBean.getSaldoAcreeedor().doubleValue()) != redondeoDouble(montoOperacion) ){
			    				
			    				montoDiferente = true;	    				
			    			}
		    				
		    			}
		    		} 		 	 	 	 		
	    		}
	    	}
    	
		}
		
		if(montoDiferente == true){
			
			respuestaValidarMontoDetalle = 1;			
		}

		return respuestaValidarMontoDetalle;
		
	}

	
	private void crearHojaMillon(String TipoReaseguroParam, String nombreSocioCuentaParam,
			String monedaModeloParam, XSSFSheet hojaParam, String monedaCuentaParam,
			Long codEmpresaParam, String hojaModeloNombreParam) {
	 	
    	 hoja = libro.createSheet(hojaModeloNombreParam+"SegundaHoja");    	 
    	// crearCabeceraReporte(TipoReaseguroParam,nombreSocioCuentaParam,monedaModeloParam,hojaParam,monedaCuentaParam,codEmpresaParam);
	}

	@SuppressWarnings("unused")
	private void generarCuentasPagos(
			List<ListaCuentasDataSun> objListaCuentasDataSunCuentasPago,
			int pocision, XSSFSheet hojaG, List<ParametrosSBSModeloUnoDetalle> objListParametros,String monedaModelo,String monedaCuenta, String tipoReaseguro, String codSocio, BigDecimal primasPagar, BigDecimal primasCobrar, BigDecimal siniestrosCobrar, BigDecimal siniestrosPagar, BigDecimal cuentasCobrar, BigDecimal cuentasPagar, BigDecimal descuentosComisionesParam, BigDecimal saldoDeudorParam, BigDecimal saldoAcreedorParam,
			String modeloReporeParam,String periodoAnioParam,String periodoTrimestreParam) {
    	
		 log.info("Cantidad filas generarCuentasPagos 1:"+hoja.getPhysicalNumberOfRows());
	
		
    	boolean entroPrimasDescuentosInferior = false; 
		Double montodescuento = 0.00;
		Double montoSaldoAcreedor = 0.00;
		
		String prefigoNombreHojaParam = "";
		
		  	if(monedaModelo.equalsIgnoreCase("PEN"))
			{prefigoNombreHojaParam="PEN";}
			else{prefigoNombreHojaParam="USD";};
			
			//if(objListaReporte.getMonedaCuenta().equalsIgnoreCase("PEN"))
			if(monedaCuenta.equalsIgnoreCase("PEN"))
			{prefigoNombreHojaParam=prefigoNombreHojaParam+"S";}
			else{prefigoNombreHojaParam=prefigoNombreHojaParam+"D";};	
		
		
    	//ultimas filas
    	int filasCount = 0;    
    	//VARIABLES SUMATORIAS   
    	BigDecimal primasPagarInferior  = new BigDecimal("0");
    	BigDecimal primasCobrarInferior = new BigDecimal("0");
    	BigDecimal siniestrosCobrarInferior = new BigDecimal("0");
    	BigDecimal siniestrosPagarInferior  = new BigDecimal("0");
    	BigDecimal cuentasCobrarInferior    = new BigDecimal("0");
    	BigDecimal cuentasPagarInferior     = new BigDecimal("0");    	
    	BigDecimal descuentosComisionesInferior    = new BigDecimal("0");
    	BigDecimal saldoDeudorInferior    = new BigDecimal("0");
    	BigDecimal saldoAcreedorInferior    = new BigDecimal("0");
    	    	
    	BigDecimal primasPagarTotal  = new BigDecimal("0");
    	BigDecimal primasCobrarTotal = new BigDecimal("0");
    	BigDecimal siniestrosCobrarTotal = new BigDecimal("0");
    	BigDecimal siniestrosPagarTotal  = new BigDecimal("0");
    	BigDecimal cuentasCobrarTotal   = new BigDecimal("0");
    	BigDecimal cuentasPagarTotal     = new BigDecimal("0");
    	BigDecimal descuentosComisionesTotal    = new BigDecimal("0");
    	BigDecimal descuentosComisionesTotales   = new BigDecimal("0");
    	BigDecimal saldoDeudorTotales   = new BigDecimal("0");
    	BigDecimal saldoAcreedorTotales   = new BigDecimal("0");
    	
    	BigDecimal primasPagarSaldos  =  new BigDecimal("0");
		BigDecimal primasCobrarSaldos =  new BigDecimal("0");
		BigDecimal siniestrosCobrarSaldos =  new BigDecimal("0");
		BigDecimal siniestrosPagarSaldos  =  new BigDecimal("0");
		BigDecimal cuentasCobrarSaldos    =  new BigDecimal("0");
		BigDecimal cuentasPagarSaldos    =  new BigDecimal("0");
		BigDecimal descuentosComisionesSaldos    = new BigDecimal("0");
		
		String montoSaldos=null;
    
		String  textoSumatoria="";
		
    	int pocisionFilas= 0;
    	pocisionFilas = pocision;
    	
    	CellStyle estilosLibro=null;
        XSSFFont estiloTexto = null;	
   	    	
    	for (int i = 0; i < 3; i++) {    	
        	    		
            
            XSSFRow fila = hojaG.createRow(pocisionFilas+i);
        	estiloTexto = libro.createFont();	
        	XSSFCell celda = fila.createCell(0);        	        
        	XSSFRichTextString texto;
        	estilosLibro = libro.createCellStyle();
        	
            estilosLibro.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
            estilosLibro.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
            estilosLibro.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
            		        	
            celda.setCellStyle(estilosLibro);								        		      
            hojaG.setColumnWidth(6, 15000);
            hojaG.setColumnWidth(1, 10000);	
            
            celda = fila.createCell(1);
        	texto = new XSSFRichTextString("");
            celda.setCellValue(texto);								        
            estilosLibro = libro.createCellStyle();
            
            estiloTexto = libro.createFont();		
        	estiloTexto.setFontHeightInPoints((short) 8);
            estiloTexto.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            estiloTexto.setColor(HSSFColor.BLACK.index);
            
            estilosLibro.setAlignment(CellStyle.ALIGN_CENTER);
            estilosLibro.setVerticalAlignment(CellStyle.ALIGN_FILL);
           
            estilosLibro.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);									        
            estilosLibro.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);					
            estilosLibro.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);					
            estilosLibro.setAlignment(CellStyle.ALIGN_CENTER);			        		        
            estilosLibro.setFont(estiloTexto);	
            celda.setCellStyle(estilosLibro);								        
            celda = fila.createCell(2);
            texto = new XSSFRichTextString("");
            celda.setCellValue(texto);								        		        								        
            celda.setCellStyle(estilosLibro);								        
            celda = fila.createCell(3);
            texto = new XSSFRichTextString("");
            celda.setCellValue(texto);				        		        									        									        
            celda.setCellStyle(estilosLibro);
            celda = fila.createCell(4);
            texto = new XSSFRichTextString("");
            celda.setCellValue(texto);				        		        									        									        
            celda.setCellStyle(estilosLibro);
            celda = fila.createCell(5);
            texto = new XSSFRichTextString("");
            celda.setCellValue(texto);				        		        									        									        
            celda.setCellStyle(estilosLibro);
            
            celda = fila.createCell(6);		   
            
            if(i == 1){
            texto = new XSSFRichTextString("Menos (-) Pagos recibidos/ efectuados");
            }else{
            texto = new XSSFRichTextString("");
            }
            
            celda.setCellValue(texto);			        									        		        									        									        
            celda.setCellStyle(estilosLibro);			       		        												        
            
            celda = fila.createCell(7);	        					        		        		       		             									    									       									  									        
            celda.setCellStyle(estilosLibro);									        									   								        							        			        		        									        	
            		               
            celda = fila.createCell(8);	        					        		        			       				        								        									    									       									  									        
            celda.setCellStyle(estilosLibro);	                
            celda = fila.createCell(9);	        					        		        
            celda.setCellStyle(estilosLibro);		            
            celda = fila.createCell(10);	        					        		        			       									    									       									  									        
            celda.setCellStyle(estilosLibro);	        		        
            celda = fila.createCell(11);	        					        		        			       							        									    									       									  									        
            celda.setCellStyle(estilosLibro);		                
            celda = fila.createCell(12);	        					        		        								    									       									  									        
            celda.setCellStyle(estilosLibro);		        
            celda = fila.createCell(13);	        					        		        			       		        	        									    									       									  									       
            celda.setCellStyle(estilosLibro);		        			        		        
            celda = fila.createCell(14);	        					        		        			     									    									       									  									        
            celda.setCellStyle(estilosLibro);
            celda = fila.createCell(15);	        					        		        			       		        	        									    									       									  									       
            celda.setCellStyle(estilosLibro);		        			        		        
            celda = fila.createCell(16);	        					        		        			     									    									       									  									        
            celda.setCellStyle(estilosLibro);    		
            
		}

    	
    	String codTipoSeguro="";
		String codTipoReaseguro="";
		/*String tipoReaseguroValor = null;*/
	    String tipoContrato = "";
	    String codigoRiesgo = "";
	    String detalleCuentaValor ="";
	    String tipoReaseguroValor ="";
	    String tipoMovimientoValor ="";	    
	    Integer signo;
	    
	    String cuenta_prima = "";
	    String cuenta_dscto = "";
	    
	    Integer numero_diario;
	    
	    String tipo_diario = "";
	    String glosa ="";
	    
	    Double montoPrima = 0.0;
	    Double montoDescuento = 0.0;
	    
	    Double monto_operacion;
	    
	    String numeroCuenta = null;
	    
	    
	     estilosLibro=null;
         estiloTexto = null;
	    
	    /*ESTILOS DE LAS CELDAS CABECERA*/	    
		estilosLibro=null;
        estiloTexto = null;		 											
		estiloTexto = libro.createFont();	
		XSSFFont estiloTexto2 = null;
         
	    estiloTexto2 = libro.createFont();
	    estiloTexto2.setFontHeightInPoints((short) 8);
		estiloTexto2.setColor(XSSFFont.COLOR_RED);		
		
		estilosLibro = libro.createCellStyle();
		estiloTexto.setFontHeightInPoints((short) 8);									      											        
        estilosLibro.setAlignment(CellStyle.ALIGN_CENTER);
        estilosLibro.setVerticalAlignment(CellStyle.ALIGN_FILL);									        
    	estilosLibro.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
	    estilosLibro.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
	    estilosLibro.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
	    estilosLibro.setFont(estiloTexto);
	    
	    CellStyle estilosLibro1=null;	    
	    estilosLibro1 = libro.createCellStyle();										      											        
		estilosLibro1.setAlignment(CellStyle.ALIGN_LEFT);
		estilosLibro1.setVerticalAlignment(CellStyle.ALIGN_FILL);									        
		estilosLibro1.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		estilosLibro1.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		estilosLibro1.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		estilosLibro1.setFont(estiloTexto);
	    
	    
		XSSFDataFormat df = libro.createDataFormat();
			
		CellStyle estilosLibro2 =null;
		estilosLibro2 = libro.createCellStyle();	
		estilosLibro2.setDataFormat(df.getFormat("#,##0.00"));
		estilosLibro2.setAlignment(CellStyle.ALIGN_RIGHT);
		estilosLibro2.setVerticalAlignment(CellStyle.ALIGN_FILL);									        
		estilosLibro2.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		estilosLibro2.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		estilosLibro2.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		estilosLibro2.setFont(estiloTexto);
	     			    
		
		 CellStyle estilosLibro3 = null;
			estilosLibro3 = libro.createCellStyle();
			estilosLibro3.setAlignment(CellStyle.ALIGN_CENTER);
			estilosLibro3.setVerticalAlignment(CellStyle.ALIGN_FILL);
			estilosLibro3.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
			estilosLibro3.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
			estilosLibro3.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
			estilosLibro3.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			estilosLibro3.setFillForegroundColor(HSSFColor.GREEN.index);
			estilosLibro3.setFont(estiloTexto);
			
			
			CellStyle estilosLibro4 = null;
			estilosLibro4 = libro.createCellStyle();
			estilosLibro4.setAlignment(CellStyle.ALIGN_LEFT);
			estilosLibro4.setVerticalAlignment(CellStyle.ALIGN_FILL);
			estilosLibro4.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
			estilosLibro4.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
			estilosLibro4.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
			estilosLibro4.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			estilosLibro4.setFillForegroundColor(HSSFColor.GREEN.index);
			estilosLibro4.setFont(estiloTexto);
			
			CellStyle estilosLibro5 = null;
			estilosLibro5 = libro.createCellStyle();
			estilosLibro5.setDataFormat(df.getFormat("#,##0.00"));
			estilosLibro5.setAlignment(CellStyle.ALIGN_RIGHT);
			estilosLibro5.setVerticalAlignment(CellStyle.ALIGN_FILL);
			estilosLibro5.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
			estilosLibro5.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
			estilosLibro5.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
			estilosLibro5.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			estilosLibro5.setFillForegroundColor(HSSFColor.GREEN.index);
			estilosLibro5.setFont(estiloTexto);
			
			CellStyle estilosLibro6 = null;
			estilosLibro6 = libro.createCellStyle();
			estilosLibro6.setAlignment(CellStyle.ALIGN_CENTER);
			estilosLibro6.setVerticalAlignment(CellStyle.ALIGN_FILL);
			estilosLibro6.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
			estilosLibro6.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
			estilosLibro6.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
			estilosLibro6.setFont(estiloTexto2);
			
			
			CellStyle estilosLibro7 = null;
			estilosLibro7 = libro.createCellStyle();
			estilosLibro7.setAlignment(CellStyle.ALIGN_LEFT);
			estilosLibro7.setVerticalAlignment(CellStyle.ALIGN_FILL);
			estilosLibro7.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
			estilosLibro7.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
			estilosLibro7.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
			estilosLibro7.setFont(estiloTexto2);
			
			CellStyle estilosLibro8 = null;
			estilosLibro8 = libro.createCellStyle();
			estilosLibro8.setDataFormat(df.getFormat("#,##0.00"));
			estilosLibro8.setAlignment(CellStyle.ALIGN_RIGHT);
			estilosLibro8.setVerticalAlignment(CellStyle.ALIGN_FILL);
			estilosLibro8.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
			estilosLibro8.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
			estilosLibro8.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
			estilosLibro8.setFont(estiloTexto2);
		    	    	            

			CellStyle estilosLibro9 = null;
			estilosLibro9 = libro.createCellStyle();
			estilosLibro9.setAlignment(CellStyle.ALIGN_CENTER);
			estilosLibro9.setVerticalAlignment(CellStyle.ALIGN_FILL);
			estilosLibro9.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
			estilosLibro9.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
			estilosLibro9.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
			estilosLibro9.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			estilosLibro9.setFillForegroundColor(HSSFColor.GREEN.index);
			estilosLibro9.setFont(estiloTexto2);
			
			
			CellStyle estilosLibro10 = null;
			estilosLibro10 = libro.createCellStyle();
			estilosLibro10.setAlignment(CellStyle.ALIGN_LEFT);
			estilosLibro10.setVerticalAlignment(CellStyle.ALIGN_FILL);
			estilosLibro10.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
			estilosLibro10.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
			estilosLibro10.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
			estilosLibro10.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			estilosLibro10.setFillForegroundColor(HSSFColor.GREEN.index);
			estilosLibro10.setFont(estiloTexto2);
			
			CellStyle estilosLibro11 = null;
			estilosLibro11 = libro.createCellStyle();
			estilosLibro11.setDataFormat(df.getFormat("#,##0.00"));
			estilosLibro11.setAlignment(CellStyle.ALIGN_RIGHT);
			estilosLibro11.setVerticalAlignment(CellStyle.ALIGN_FILL);
			estilosLibro11.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
			estilosLibro11.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
			estilosLibro11.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
			estilosLibro11.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			estilosLibro11.setFillForegroundColor(HSSFColor.GREEN.index);
			estilosLibro11.setFont(estiloTexto2);
		    
		
	    pocisionFilas = pocisionFilas +3;
	    
        try {			
						
			if (objListParametros.size()>0){
																		
					for (ParametrosSBSModeloUnoDetalle objList : objListParametros) {//INICIOFOR1
						
    					  //EVALUAMOS LA MONEDA	
						  if(monedaCuenta.equalsIgnoreCase("PEN")){
							  
						    	numeroCuenta = String.valueOf(objList.getCuentaSaldoSoles());
						    	  if(numeroCuenta.length()>=3){
						    		  if(numeroCuenta.substring(0,2).equalsIgnoreCase("14") 
								         || numeroCuenta.substring(0,2).equalsIgnoreCase("24")){
						    			//SE EVALUA QUE LAS CUENTAS TENGAN FORMATO CORRECTO						    			 
						    		  }else{
						    			continue;
						    		  }						    		  
						    	  }else{
						    		continue;
						    	  }
						    	  
						    }else{
						    	numeroCuenta =String.valueOf(objList.getCuentaSaldoDolares());
						    	if(numeroCuenta.length()>=3){
						    		 if(numeroCuenta.substring(0,2).equalsIgnoreCase("14") 
								       || numeroCuenta.substring(0,2).equalsIgnoreCase("24")){
						    			//SE EVALUA QUE LAS CUENTAS TENGAN FORMATO CORRECTO
						    		  }else{
						    			continue;
						    		  }						    		  
						    	  }else{
						    		continue;
						    	  }
						    };
						
						if(String.valueOf(objList.getCodEmpresa()).equals(codSocio)){
													            	
						codTipoSeguro    = reporteSBSModeloUnoDetalleService.selectParametroByPrimaryKey(objList.getCodTipoSeguro()).getDescripcion();						
						codTipoReaseguro = reporteSBSModeloUnoDetalleService.selectParametroByPrimaryKey(objList.getCodTipoReaseguro()).getDescripcion();
						
						if(codTipoSeguro.equalsIgnoreCase("REASEGURO") && codTipoReaseguro.equalsIgnoreCase(tipoReaseguro)){//INICIOIF1																				
						
												
						    //EVALUAMOS LA CLASIFICACION DE LA CUENTA						    
						    detalleCuentaValor  =reporteSBSModeloUnoDetalleService.selectParametroByPrimaryKey(objList.getCodTipoCuenta()).getDescripcion();						    
						    tipoMovimientoValor =reporteSBSModeloUnoDetalleService.selectParametroByPrimaryKey(objList.getCodTipoMovimiento()).getDescripcion();
						    tipoReaseguroValor  =reporteSBSModeloUnoDetalleService.selectParametroByPrimaryKey(objList.getCodTipoReaseguro()).getDescripcion();
						    if(tipoMovimientoValor.equals("PAGAR")){
						    	signo = -1;			    	
						    }else{
						    	signo = 1;
						    }
						    						    
						    cuenta_prima = "";
						    cuenta_dscto ="";
						    
						    //EVALUAMOS LA CUENTA
						    if(detalleCuentaValor.equalsIgnoreCase("PRIMA") && codTipoReaseguro.equalsIgnoreCase("CEDIDO") && tipoMovimientoValor.equalsIgnoreCase("PAGAR") && monedaCuenta.equalsIgnoreCase("PEN") ){
						    	cuenta_prima = String.valueOf(objList.getCuentaPrimaSoles());
								cuenta_dscto = String.valueOf(objList.getCuentaDescuentoSoles());						    	
						    }else if(detalleCuentaValor.equalsIgnoreCase("PRIMA") && codTipoReaseguro.equalsIgnoreCase("CEDIDO") && tipoMovimientoValor.equalsIgnoreCase("PAGAR") && !monedaCuenta.equalsIgnoreCase("PEN")){
						    	cuenta_prima = String.valueOf(objList.getCuentaPrimaDolares());
								cuenta_dscto = String.valueOf(objList.getCuentaDescuentoDolares());						    	
						    }else{
						    	cuenta_prima = "";
								cuenta_dscto ="";
						    }
						    						   						    
						    if(numeroCuenta!= null){
						    	
						    	tipoContrato= objList.getCodTipoContrato();
						    	codigoRiesgo = objList.getRiesgo().toString();	

						    	//pocisionFilas = pocisionFilas +3;
						    	
						    		for (int i = 0; i < objListaCuentasDataSunCuentasPago.size(); i++) {//INICIOFOR1
																    		
						    			  if(objListaCuentasDataSunCuentasPago.get(i).getCodigoCuenta().trim() != "" 
						    			     && numeroCuenta.trim() != ""  
						    			     && objListaCuentasDataSunCuentasPago.get(i).getCodigoCuenta().trim().equalsIgnoreCase(numeroCuenta.trim())
						    			     && codSocio.toString().trim().equalsIgnoreCase(objList.getCodEmpresa().toString().trim())){
						    				
						    				  
						    				    boolean flag=false;

									        	boolean  noEncontroFormato = false;
									        	boolean  noEncontroGuion=false;
									        	boolean  buscaBO = false;
									        	boolean  buscaPL = false;
						    				    String busquedaBO = "BO";
									        	String busquedaPL = "PL";
									        	String glosa2 = objListaCuentasDataSunCuentasPago.get(i).getGlosa().toString();
									        		
									        	boolean  busca = false;
									        	
									        	/*
									        	String[] parte = glosa2.split("_");
									        	
									        	for(String obj2 : parte){
			 
										        	if(busquedaBO.equalsIgnoreCase(obj2)){ 
										        		busca = true;
										        		buscaBO = true;
										        		noEncontroFormato=false;
										        		if(!glosa2.contentEquals("BO_")){
										        			noEncontroGuion=true;
										        		}
										        	}else if(busquedaPL.equalsIgnoreCase(obj2)){
										        		busca = true;
										        		buscaPL = true;
										        		noEncontroFormato=false;
														if(!glosa2.contentEquals("PL_")){
															noEncontroGuion=true;									        			
											             }
										        	}
									        	
									        	}
									          */
									          /*	
									          ReportesGeneradosSBSModeloUnoDetalle listaReporteFiltros = null;																									
											  listaReporteFiltros  = reporteSBSModeloUnoDetalleService.consultarReportesParaErrores(modeloReporeParam,periodoAnioParam,periodoTrimestreParam);	
						    				  
											  if(listaReporteFiltros != null && noEncontroFormato){
												  String nombreReporte = "";
												  nombreReporte = obtenerNombreReporte(modeloReporeParam, periodoAnioParam, periodoTrimestreParam);
												  grabarErrorLog(listaReporteFiltros.getCodReporte().longValue(),nombreReporte+"  ||   "+"",glosa2,"","No cuenta con formato correcto para BO o Planilla");
												  reporteSBSModeloUnoDetalleService.actualizarEstadoReporte(listaReporteFiltros.getCodReporte().longValue());
											  }
						    				  */
									        	
						    				  //XSSFRow fila = hoja.createRow(filasCount+3+i);
						    				  XSSFRow fila = hojaG.createRow(pocisionFilas);
						    				  
						    				  
						    				  //EVALUAMOS EL MONTO DE LA OPERACION
						    				  if(monedaModelo.equalsIgnoreCase("PEN")){
						    					  monto_operacion = redondeoDouble(Double.valueOf(objListaCuentasDataSunCuentasPago.get(i).getImporteBase()));
						    				  }else{
						    					  monto_operacion = redondeoDouble(Double.valueOf(objListaCuentasDataSunCuentasPago.get(i).getImporteTransaccion()));
						    				  }
						    				  
						    				  //VALIDANDO EN JOURNAL REMAN
						    				  if(objListaCuentasDataSunCuentasPago.get(i).getJournalType().equalsIgnoreCase("REMAN") 
						    				     && prefigoNombreHojaParam.equalsIgnoreCase("USDD") 
						    				     && (monto_operacion == 0 || monto_operacion == 0.00)){
						    					  
						    					  continue;
						    					  
						    				  }
						    				  
						    				  
						    				  //OBTENEMOS TIPO DIARIO
						    				  tipo_diario 	=  objListaCuentasDataSunCuentasPago.get(i).getTipoDiario();
						    				  numero_diario =  objListaCuentasDataSunCuentasPago.get(i).getNumerodiario();
						    				  glosa 		=  objListaCuentasDataSunCuentasPago.get(i).getGlosa();
						    				  
						    				  //OBTENEMOS SALDOS DE CUENTA PRIMA Y CUENTA DESCUENTO
											    if(!cuenta_prima.equals("") && !cuenta_dscto.equals("")){
											    	montoPrima     =  obtener_Monto_Prima(objListaCuentasDataSunCuentasCuarenta,cuenta_prima,tipo_diario,numero_diario,glosa,monedaModelo,monto_operacion);
											    	montoDescuento = obtener_Monto_Dscto(objListaCuentasDataSunCuentasCuarenta,cuenta_dscto,tipo_diario,numero_diario,glosa,monedaModelo,monto_operacion,montoPrima);
											    }
											    else
											    {
											    	montoPrima     = 0.0;
											    	montoDescuento = 0.0;
											    }
											    
											    //BUSCAMOS LA PRIMA TOTAL Y DESCUENTO PARA EL TIPO DE DIARIO FC001 O FC003
											    if( numeroCuenta.substring(0,2).equals("24") && (tipo_diario.equals("FC001") || tipo_diario.equals("FC003")) ){
											    	
											    	if(montoPrima == 0.0 && montoDescuento == 0.0){
											    		
											    		montoPrima     = obtener_Monto_Cuenta24_Prima(objListaCuentasDataSunCuentasPago,numeroCuenta,tipo_diario,numero_diario,glosa,monedaModelo,"C",monto_operacion);
											    		montoDescuento = obtener_Monto_Cuenta24_Dscto(objListaCuentasDataSunCuentasPago,numeroCuenta,tipo_diario,numero_diario,glosa,monedaModelo,"D",monto_operacion);
											    		
											    	}											    	
											    }
											    
											    //SI NO SE ENCONTRO MONTO EN CUENTA FC001 O FC003, PINTEMOS LINEA DE COLOR
											    if( montoPrima == -1 && montoDescuento == -1 ){
											    	/* 'Pintar linea de color
                            							SBS_Alerta_Fila_Modelo row_pivote
											    	 * */
											    	 flag=true;	
											    }
											    else if(numeroCuenta.substring(0,2).equals("24") 
											    		&& ( (montoPrima == 0.0  && montoDescuento == 0.0) || (montoPrima != 0.0 && montoDescuento != 0.0) ) 
											    		&& ( tipoMovimientoValor.equals("PAGAR") &&  detalleCuentaValor.equalsIgnoreCase("PRIMA") &&  codTipoReaseguro.equalsIgnoreCase("CEDIDO")) ){
                                                    /*
                                                     * 'Pintar linea de color
                            							SBS_Alerta_Fila_Modelo row_pivote
                                                     * */   	
											    	 flag=true;	
											    }
											    																    											   
												 primasPagarSaldos  = new BigDecimal("0");
												 primasCobrarSaldos = new BigDecimal("0");
												 siniestrosCobrarSaldos = new BigDecimal("0");
												 siniestrosPagarSaldos  = new BigDecimal("0");
												 cuentasCobrarSaldos    = new BigDecimal("0");
												 cuentasPagarSaldos    = new BigDecimal("0");
												 descuentosComisionesSaldos  = new BigDecimal("0");
											    
												montodescuento = null;
							        			montoSaldoAcreedor = null;
							        			entroPrimasDescuentosInferior = false;
												 
								    			for (int j = 0; j < 17; j++) {//INICIOFOR2
								    												    		
								    				hojaG.setColumnWidth(6, 15000);
								    				hojaG.setColumnWidth(1, 10000);	
								    				
								    				XSSFCell celda = fila.createCell(j);        	        								        	
								    				textoSumatoria ="";
								    				XSSFRichTextString texto = new XSSFRichTextString("");
								    				
								    				if(j == 0){
								    				texto = new XSSFRichTextString(objListaCuentasDataSunCuentasPago.get(i).getFechaTransaccion().toString()) ;
								    				}
								    				
										        	if(j == 4){								        		
										        	texto = new XSSFRichTextString(objListaCuentasDataSunCuentasPago.get(i).getNumerodiario().toString()) ;								        	
										        	}
										        	
										        	if(j == 6){
										        	
										        	texto = new XSSFRichTextString(objListaCuentasDataSunCuentasPago.get(i).getGlosa().toString()) ;
										        	}
										        	
										        	if(j == 7){
											        texto = new XSSFRichTextString(objList.getRiesgo().toString()) ;
											        }
										        	
										        	if(j == 8 || j == 9 || j == 10 || j ==11 || j == 12 || j == 13 || j == 14 || j == 15 ){
										            
										        	String montoFinal ="";
										        	//ARMANDO CLASIFICACION	
										        	int clasificacion = 0;
										        
										        	clasificacion = clasificarCuenta(detalleCuentaValor,tipoMovimientoValor,tipoReaseguroValor);
										        		
										        	if( j == clasificacion ){
										        	   										        	
										        		if(numeroCuenta.substring(0,2).equals("24")){
										        			
										        			if(signo == -1 && monto_operacion > 0){
										        				
										        				signo = 1;
										        				
										        				if (montoPrima == -1 && montoDescuento ==-1){
										        					
										        					montoFinal =String.valueOf((signo * monto_operacion));
										        					textoSumatoria = montoFinal;
										        					
										        					texto = new XSSFRichTextString(montoFinal);
										        				
										        				}else if(montoPrima != 0 && montoDescuento != 0){
										        					
										        					entroPrimasDescuentosInferior = true;
										        					montoFinal =String.valueOf(Math.abs(montoPrima));
										        					textoSumatoria = montoFinal;
										        					
										        					montodescuento = null;
										        					montoSaldoAcreedor = null;
										        					
										        					montodescuento = redondeoDouble(Math.abs(montoDescuento));
										        					montoSaldoAcreedor = (signo * monto_operacion);
										        					
										        					texto = new XSSFRichTextString(montoFinal);
										        					
										        				}else{
										        					montoFinal =String.valueOf((signo * monto_operacion));
										        					textoSumatoria = montoFinal;
										        					/*if(montoFinal.length()>0){
										        					montoFinal = formateador.format(Double.parseDouble(montoFinal));
										        					}*/	
										        					texto = new XSSFRichTextString(montoFinal);
										        				}
										        				
										        				signo = -1;
										        				
										        			}else{
										        				
										        				if (montoPrima == -1 && montoDescuento ==-1){
										        					
										        					montoFinal =String.valueOf((signo * monto_operacion));
										        					textoSumatoria = montoFinal;
										        					/*if(montoFinal.length()>0){
										        					montoFinal = formateador.format(Double.parseDouble(montoFinal));
										        					}*/	
										        					texto = new XSSFRichTextString(montoFinal);
										        				
										        				}else if(montoPrima != 0 && montoDescuento != 0){
										        					
										        					entroPrimasDescuentosInferior = true;
										        					montoFinal =String.valueOf(Math.abs(montoPrima));
										        					textoSumatoria = montoFinal;
										        				
										        					montodescuento = null;
										        					montoSaldoAcreedor = null;
										        					
										        					montodescuento = redondeoDouble(Math.abs(montoDescuento));
										        					montoSaldoAcreedor = (signo * monto_operacion);
										        					
										        					texto = new XSSFRichTextString(montoFinal);
										        					
										        				}else{
										        					montoFinal =String.valueOf((signo * monto_operacion));
										        					textoSumatoria = montoFinal;
										        				
										        					texto = new XSSFRichTextString(montoFinal);
										        				}
										        														        				
										        			}
										        													        			
										        		 }else if(numeroCuenta.substring(0,2).equals("14")){
											        			
											        			if(signo == 1 && monto_operacion < 0){
											        				
											        				signo = -1;
											        				
											        				if (montoPrima == -1 && montoDescuento ==-1){
											        					
											        					montoFinal =String.valueOf((signo * monto_operacion));
											        					textoSumatoria = montoFinal;
											        				
											        					texto = new XSSFRichTextString(montoFinal);
											        				
											        				}else if(montoPrima != 0 && montoDescuento != 0){
											        					
											        					entroPrimasDescuentosInferior = true;
											        					montoFinal =String.valueOf(Math.abs(montoPrima));
											        					textoSumatoria = montoFinal;
											        					
											        					montodescuento = null;
											        					montoSaldoAcreedor = null;
											        					
											        					montodescuento = redondeoDouble(Math.abs(montoDescuento));
											        					montoSaldoAcreedor = (signo * monto_operacion);
											        					
											        					texto = new XSSFRichTextString(montoFinal);
											        					
											        				}else{
											        					montoFinal =String.valueOf((signo * monto_operacion));
											        					textoSumatoria = montoFinal;
											        				
											        					texto = new XSSFRichTextString(montoFinal);
											        				}
											        				
											        				signo = 1;
											        				
											        			}else{
											        				
											        				if (montoPrima == -1 && montoDescuento ==-1){
											        					
											        					montoFinal =String.valueOf((signo * monto_operacion));
											        					textoSumatoria = montoFinal;
											        					
											        					texto = new XSSFRichTextString(montoFinal);
											        				
											        				}else if(montoPrima != 0 && montoDescuento != 0){
											        					
											        					entroPrimasDescuentosInferior = true;
											        					montoFinal =String.valueOf(Math.abs(montoPrima));
											        					textoSumatoria = montoFinal;
											        					
											        					montodescuento = null;
											        					montoSaldoAcreedor = null;
											        					
											        					montodescuento = redondeoDouble(Math.abs(montoDescuento));
											        					montoSaldoAcreedor = (signo * monto_operacion);
											        					
											        					texto = new XSSFRichTextString(montoFinal);
											        					
											        				}else{
											        					montoFinal =String.valueOf((signo * monto_operacion));
											        					textoSumatoria = montoFinal;
											        				
											        					texto = new XSSFRichTextString(montoFinal);
											        				}
											        														        				
											        			}
											        			
											        		}										        		 										        		 
										        		
										              }
										        	
										        	}
										        	
										        	/*
											          if(j == 8 || j == 9 || j == 10 || j ==11 || j == 12 || j == 13 || j == 14 || j == 15 || j == 16){	
											        		 celda.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
												        	 celda.setCellStyle(estilosLibro2);
												      }else if(j == 6){
												        	 celda.setCellStyle(estilosLibro1);
												      }else{
												             celda.setCellStyle(estilosLibro);  
												      }
										        	*/
											          
											          if(j == 8 || j == 9 || j == 10 || j ==11 || j == 12 || j == 13 || j == 14 || j == 15 || j == 16){	
											        	  
											        	  
											        	  if(flag){
				                                            	
				                                            	celda.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				    											celda.setCellStyle(estilosLibro5);
				                                            	
				                                          }else{
															    celda.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
															    celda.setCellStyle(estilosLibro2);
				                                          }
				                                          if(noEncontroFormato){
				                                            	celda.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
																celda.setCellStyle(estilosLibro8);  	
				                                          }
											        	  
											        	  /*celda.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
											        	  celda.setCellStyle(estilosLibro2);*/
											          }else if(j == 6){
											        	  
											        	  if(flag){															
															  
											        		  celda.setCellStyle(estilosLibro4);
				                                            	
														  }else{
															  
															  celda.setCellStyle(estilosLibro1);
														 
														  }
														  if(noEncontroFormato){
															  
															  celda.setCellStyle(estilosLibro7);	
														  }
											        	  
											        	  /* celda.setCellStyle(estilosLibro1); */
											          }else{
											        	  
											        	  if(flag){
																
																celda.setCellStyle(estilosLibro3);
															
														  }else{
															
															  celda.setCellStyle(estilosLibro);
														  
														  }
														  if(noEncontroFormato){
															
															  celda.setCellStyle(estilosLibro6);	
															
														  }
											        	  /*celda.setCellStyle(estilosLibro); */  
											          }
											          
											          
										        	  if(j == 8){											        	  
											        	  if (texto.getString() != null && texto.getString().length() > 0){
											        	  primasPagarInferior =primasPagarInferior.add(BigDecimal.valueOf(redondeoDouble(Double.parseDouble(textoSumatoria))));
											        	  primasPagarSaldos=BigDecimal.valueOf(redondeoDouble(Double.parseDouble(textoSumatoria)));
											        	  }
											          }else if(j == 9){
											        	  if (texto.getString() != null && texto.getString().length() > 0){
											        	  primasCobrarInferior =primasCobrarInferior.add(BigDecimal.valueOf(redondeoDouble(Double.parseDouble(textoSumatoria))));
											        	  primasCobrarSaldos=BigDecimal.valueOf(redondeoDouble(Double.parseDouble(textoSumatoria)));
											        	  }
											          }else if(j == 10){
											        	  if (texto.getString() != null && texto.getString().length() > 0){
											        	  siniestrosCobrarInferior =siniestrosCobrarInferior.add(BigDecimal.valueOf(redondeoDouble(Double.parseDouble(textoSumatoria))));
											        	  siniestrosCobrarSaldos=BigDecimal.valueOf(redondeoDouble(Double.parseDouble(textoSumatoria)));
											        	  }
											          }else if(j == 11){
											        	  if (texto.getString() != null && texto.getString().length() > 0){
											        	  siniestrosPagarInferior =siniestrosPagarInferior.add(BigDecimal.valueOf(redondeoDouble(Double.parseDouble(textoSumatoria))));
											        	  siniestrosPagarSaldos=BigDecimal.valueOf(redondeoDouble(Double.parseDouble(textoSumatoria)));
											        	  }
											          }else if(j == 12){
											        	  if (texto.getString() != null && texto.getString().length() > 0){
											        	  cuentasCobrarInferior =cuentasCobrarInferior.add(BigDecimal.valueOf(redondeoDouble(Double.parseDouble(textoSumatoria))));
											        	  cuentasCobrarSaldos=BigDecimal.valueOf(redondeoDouble(Double.parseDouble(textoSumatoria)));
											        	  }
											          }else if(j == 13){
											        	  if (texto.getString() != null && texto.getString().length() > 0){
											        	  cuentasPagarInferior =cuentasPagarInferior.add(BigDecimal.valueOf(redondeoDouble(Double.parseDouble(textoSumatoria))));
											        	  cuentasPagarSaldos=BigDecimal.valueOf(redondeoDouble(Double.parseDouble(textoSumatoria)));
											        	  }
											          }else if(j == 14 && entroPrimasDescuentosInferior == true){
											        	  
											        	  if (montodescuento != null){
											        		  descuentosComisionesInferior =descuentosComisionesInferior.add(BigDecimal.valueOf(montodescuento));
											        		  descuentosComisionesSaldos =BigDecimal.valueOf(montodescuento);
												          }
											        	  
											          }else if(j == 15){
												          
											        	  if( (primasPagarSaldos.add(siniestrosPagarSaldos).add(cuentasPagarSaldos)).compareTo((primasCobrarSaldos.add(siniestrosCobrarSaldos).add(cuentasCobrarSaldos)))<0){
											        		
											        		  montoSaldos="";										        		  
											        		  montoSaldos = String.valueOf(redondeoDouble(Double.parseDouble((primasCobrarSaldos.add(siniestrosCobrarSaldos).add(cuentasCobrarSaldos)).subtract((primasPagarSaldos.add(siniestrosPagarSaldos).add(cuentasPagarSaldos))).toString())));										        		  
											        		  texto = new XSSFRichTextString(montoSaldos);
											        		  if(montoSaldos.length()>0){
												        		  saldoDeudorInferior= saldoDeudorInferior.add(BigDecimal.valueOf(Double.parseDouble(montoSaldos)));
												        	  }
											        		  
											        	  }else{
											        		  
											        		  montoSaldos="";											        		  
											        		  texto = new XSSFRichTextString(montoSaldos);
											        	  }
											        	  
											           }else if(j == 16){
											        	   
											        	 if(entroPrimasDescuentosInferior == false){ 
											        	   
											        	   if( (primasPagarSaldos.add(siniestrosPagarSaldos).add(cuentasPagarSaldos)).compareTo((primasCobrarSaldos.add(siniestrosCobrarSaldos).add(cuentasCobrarSaldos)))>0){
												        		
												        		  montoSaldos="";											        		  
												        		  montoSaldos = String.valueOf((Double.parseDouble((primasPagarSaldos.add(siniestrosPagarSaldos).add(cuentasPagarSaldos)).subtract((primasCobrarSaldos.add(siniestrosCobrarSaldos).add(cuentasCobrarSaldos))).toString())));											        		  
												        		  texto = new XSSFRichTextString(montoSaldos);
												        		  
												        		  if(montoSaldos.length()>0){
													        		  saldoAcreedorInferior= saldoAcreedorInferior.add(BigDecimal.valueOf(Double.parseDouble(montoSaldos)));
													        	  }
												        		  
												            }else{												        		  
												        		  montoSaldos="";											        		  
												        		  texto = new XSSFRichTextString(montoSaldos);
												        	}   
											           
											        	 }
											        	 
											        	 if(entroPrimasDescuentosInferior == true){
											        		 
											        		 if(montoSaldoAcreedor != null){
											        		 
											        			saldoAcreedorInferior = saldoAcreedorInferior.add(BigDecimal.valueOf(montoSaldoAcreedor));  			
											        		 
											        		 }
											        		 
											        	 }
											        	 
											          
											           }
											          
											        
											            if( (j == 8 || j == 9 || j == 10 || j ==11 || j == 12 || j == 13 || j == 14 || j == 15 || j == 16 ) && entroPrimasDescuentosInferior == false){	
												        	 
											            	if(texto.length()>0){
												        		  celda.setCellValue(Double.parseDouble(texto.toString()));
												        	}else{
												        		  celda.setCellValue(texto);
												            }
												        
											            	
											            }else if( (j == 8 || j == 14 || j == 15 || j == 16) && entroPrimasDescuentosInferior == true ){
											            												            												            	
											            	 if (j == 8 || j == 15){
													        	  
													        	  if(texto.length()>0){
													        		  celda.setCellValue(Double.parseDouble(texto.toString()));
													        	  }else{
													        		  celda.setCellValue(texto);
													              }
												        	  
												        	  }else if(j == 14){
												        		 
												        		  if(montodescuento!= null){
													        		  celda.setCellValue(montodescuento);
													        	  }else{
													        		  celda.setCellValue("");
													              }										        		  
												        	  }else if(j == 16){
												        		  
												        		  if(montoSaldoAcreedor != null){
													        		  celda.setCellValue(montoSaldoAcreedor);
													        	  }else{
													        		  celda.setCellValue("");
													              }														        												            	
											            	  }
											            	
											            }else{
												        	 
												        	celda.setCellValue(texto);  												        
												        }												          												      
										        										        											        	
											  }//INICIOFOR2								    											    											        	
								    			 pocisionFilas=pocisionFilas+1;   								    			
						    		     }		
						    			 						    			  
									}//INICIOFOR1			
						    		
						    }	       				        
					    
						  }//INICIOIF1
						
				
					   }//INICIOFOR1
														
				  }
				
			   }			
											
			
			log.info("Cantidad filas generarCuentasPagos 2:"+hoja.getPhysicalNumberOfRows());
			
			filasCount = hojaG.getPhysicalNumberOfRows();
		
			for (int i = 0; i < 14; i++) {    	
	    		
			  if(i == 0){				
				
	            XSSFRow fila = hojaG.createRow(pocisionFilas+i);
	        	estiloTexto = libro.createFont();	
	        	XSSFCell celda = fila.createCell(0);        	        
	        	XSSFRichTextString texto;
	        	estilosLibro = libro.createCellStyle();
	        	
	            estilosLibro.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
	            estilosLibro.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
	            estilosLibro.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
	            		        	
	            celda.setCellStyle(estilosLibro);								        		      
	            hojaG.setColumnWidth(6, 15000);
	            hojaG.setColumnWidth(1, 10000);	
	            
	            celda = fila.createCell(1);
	        	texto = new XSSFRichTextString("");
	            celda.setCellValue(texto);								        
	            estilosLibro = libro.createCellStyle();
	            
	            estiloTexto = libro.createFont();		
	        	estiloTexto.setFontHeightInPoints((short) 8);
	            estiloTexto.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	            estiloTexto.setColor(HSSFColor.BLACK.index);
	            
	            estilosLibro.setAlignment(CellStyle.ALIGN_CENTER);
	            estilosLibro.setVerticalAlignment(CellStyle.ALIGN_FILL);
	           
	            estilosLibro.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);									        
	            estilosLibro.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);					
	            estilosLibro.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);					
	            estilosLibro.setAlignment(CellStyle.ALIGN_CENTER);			        		        
	            estilosLibro.setFont(estiloTexto);	
	            celda.setCellStyle(estilosLibro);								        
	            celda = fila.createCell(2);
	            texto = new XSSFRichTextString("");
	            celda.setCellValue(texto);								        		        								        
	            celda.setCellStyle(estilosLibro);								        
	            celda = fila.createCell(3);
	            texto = new XSSFRichTextString("");
	            celda.setCellValue(texto);				        		        									        									        
	            celda.setCellStyle(estilosLibro);
	            celda = fila.createCell(4);
	            texto = new XSSFRichTextString("");
	            celda.setCellValue(texto);				        		        									        									        
	            celda.setCellStyle(estilosLibro);
	            celda = fila.createCell(5);
	            texto = new XSSFRichTextString("");
	            celda.setCellValue(texto);				        		        									        									        
	            celda.setCellStyle(estilosLibro);
	            
	            celda = fila.createCell(6);		   	            	          
	            texto = new XSSFRichTextString("TOTALES");	          
	                       
	            celda.setCellValue(texto);			        									        		        									        									        
	            celda.setCellStyle(estilosLibro);			       		        												        
	            
	            celda = fila.createCell(7);	        					        		        		       		             									    									       									  									        
	            celda.setCellStyle(estilosLibro);									        									   								        							        			        		        									        	

	            // primasPagar,  primasCobrar,  siniestrosCobrar,  siniestrosPagar,  cuentasCobrar,  cuentasPagar
	            
	            if(objSaldosBean != null && objSaldosBean.getPrimaPagarReaCedido() != 0){
	            	primasPagarTotal = (BigDecimal.valueOf(objSaldosBean.getPrimaPagarReaCedido()).add(primasPagar)).subtract(primasPagarInferior);	
	            }else{
	            	primasPagarTotal = primasPagar.subtract(primasPagarInferior);
	            }
	            
	            if(objSaldosBean != null && objSaldosBean.getPrimaCobrarReaAceptado() != 0){
	            	primasCobrarTotal = (BigDecimal.valueOf(objSaldosBean.getPrimaCobrarReaAceptado()).add(primasCobrar)).subtract(primasCobrarInferior);	
	            }else{
	            	primasCobrarTotal =primasCobrar.subtract(primasCobrarInferior);
	            }
	            
	            if(objSaldosBean != null && objSaldosBean.getSiniestroCobrarReaAceptado() != 0){
	            	siniestrosCobrarTotal = (BigDecimal.valueOf(objSaldosBean.getSiniestroCobrarReaAceptado()).add(siniestrosCobrar)).subtract(siniestrosCobrarInferior);	
	            }else{
	            	siniestrosCobrarTotal =   siniestrosCobrar.subtract(siniestrosCobrarInferior);
	            }
	            
	            if(objSaldosBean != null && objSaldosBean.getSiniestroPagarReaCedido() != 0){
	            	siniestrosPagarTotal = (BigDecimal.valueOf(objSaldosBean.getSiniestroPagarReaCedido()).add(siniestrosPagar)).subtract(siniestrosPagarInferior);
	            }else{
	            	siniestrosPagarTotal =siniestrosPagar.subtract(siniestrosPagarInferior);
	            }
	            
	            if(objSaldosBean != null && objSaldosBean.getOtrasCobrarReaCedidos() != 0){
	            	cuentasCobrarTotal = (BigDecimal.valueOf(objSaldosBean.getOtrasCobrarReaCedidos()).add(cuentasCobrar)).subtract(cuentasCobrarInferior);	
	            }else{
	            	cuentasCobrarTotal =cuentasCobrar.subtract(cuentasCobrarInferior);
	            }
	            	            	            
	            if( objSaldosBean != null && objSaldosBean.getOtrasCobrarReaAceptado() != 0){
	            	cuentasPagarTotal = (BigDecimal.valueOf(objSaldosBean.getOtrasCobrarReaAceptado()).add(cuentasPagar)).subtract(cuentasPagarInferior);	
	            }else{
	            	cuentasPagarTotal =cuentasPagar.subtract(cuentasPagarInferior);
	            }
	            
	            if(objSaldosBean != null && objSaldosBean.getDescuentosComisiones() != 0){
	            	descuentosComisionesTotal = (BigDecimal.valueOf(objSaldosBean.getDescuentosComisiones()).add(descuentosComisionesParam)).subtract(descuentosComisionesInferior);	
	            }else{
	            	descuentosComisionesTotal = descuentosComisionesParam.subtract(descuentosComisionesInferior) ;
	            }
	            	            
	            saldoAcreedorTotales = saldoAcreedorParam.subtract(saldoAcreedorInferior);
	            saldoDeudorTotales = saldoDeudorParam.subtract(saldoDeudorInferior);
	            
	            celda = fila.createCell(8);	
	            texto = new XSSFRichTextString();
	            celda.setCellValue((Double.parseDouble(primasPagarTotal.toString())));
	            celda.setCellStyle(estilosLibro2);
	            
	            celda = fila.createCell(9);	
	            texto = new XSSFRichTextString();
	            celda.setCellValue((Double.parseDouble(primasCobrarTotal.toString())));	            
	            celda.setCellStyle(estilosLibro2);           
	            
	            celda = fila.createCell(10);	
	            texto = new XSSFRichTextString();
	            celda.setCellValue((Double.parseDouble(siniestrosCobrarTotal.toString())));	            
	            celda.setCellStyle(estilosLibro2); 
	            
	            celda = fila.createCell(11);	
	            texto = new XSSFRichTextString();
	            celda.setCellValue((Double.parseDouble(siniestrosPagarTotal.toString())));	            
	            celda.setCellStyle(estilosLibro2); 
	            
	            celda = fila.createCell(12);	
	            texto = new XSSFRichTextString();
	            celda.setCellValue((Double.parseDouble(cuentasCobrarTotal.toString())));	            
	            celda.setCellStyle(estilosLibro2);		        
	            	     
	            celda = fila.createCell(13);	
	            texto = new XSSFRichTextString();
	            celda.setCellValue((Double.parseDouble(cuentasPagarTotal.toString())));	            
	            celda.setCellStyle(estilosLibro2);
	            	            		        			        		        
	            celda = fila.createCell(14);	
	            texto = new XSSFRichTextString();
	            celda.setCellValue((Double.parseDouble(descuentosComisionesTotal.toString())));
	            celda.setCellStyle(estilosLibro2);
	            
	            celda = fila.createCell(15);
	            texto = new XSSFRichTextString();
	            
	            if(saldoDeudorFormula == null){
	            	celda.setCellValue((Double.parseDouble(saldoDeudorTotales.toString())));	
	            }else{
	            	celda.setCellValue(saldoDeudorFormula+(Double.parseDouble(saldoDeudorTotales.toString())));
	            }	            	            
	            celda.setCellStyle(estilosLibro2);		        			        	
	            
	            celda = fila.createCell(16);	        					        	
	            texto = new XSSFRichTextString();
	            
	            if(saldoAcreedorFormula == null){
	            	celda.setCellValue((Double.parseDouble(saldoAcreedorTotales.toString())));	
	            }else{
	            	celda.setCellValue(saldoAcreedorFormula+(Double.parseDouble(saldoAcreedorTotales.toString())));
	            }	           
	            celda.setCellStyle(estilosLibro2);    		
			  }	 
			  
			  if(i == 2){
				  
				  XSSFRow fila = hojaG.createRow(filasCount+i);		        	
		          XSSFCell celda = fila.createCell(0);        	        
		          XSSFRichTextString texto;		       		          
		          texto = new XSSFRichTextString("Detalle de cada operación con indicación del N° de planilla y documento contable de origen y comprobantes de caja");	          		                       
		          celda.setCellValue(texto);		
		          		          		          
			  }
			  
			  if(i == 10){
				  
				  XSSFRow fila = hojaG.createRow(filasCount+i);		        	
		          XSSFCell celda = fila.createCell(6);        	        
		          XSSFRichTextString texto;		          		           	            	          
		          texto = new XSSFRichTextString("__________________________________________________________");	          		                       
		          celda.setCellValue(texto);		
		          		          		    
		          celda = fila.createCell(11);        	        		          		          		           	            	          
		          texto = new XSSFRichTextString("__________________________________________________________");	          		                       
		          celda.setCellValue(texto);
		          
			  }
			  
			  if(i == 11){
				  
				  estilosLibro = libro.createCellStyle();
				  estiloTexto = libro.createFont();		
		          estiloTexto.setFontHeightInPoints((short) 8);
		          estiloTexto.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		          estiloTexto.setColor(HSSFColor.BLACK.index);		
		          estilosLibro.setFont(estiloTexto);	
		          estilosLibro.setAlignment(CellStyle.ALIGN_CENTER);
		          estilosLibro.setVerticalAlignment(CellStyle.ALIGN_FILL);
		          
				  XSSFRow fila = hojaG.createRow(filasCount+i);						  
		          XSSFCell celda = fila.createCell(6);        	        
		          XSSFRichTextString texto;		          		           	            	          
		          texto = new XSSFRichTextString(firmanteUno);	          		                       
		          celda.setCellValue(texto);		
		          celda.setCellStyle(estilosLibro);
		          
		          celda = fila.createCell(14);        	        		          		          		           	            	          
		          texto = new XSSFRichTextString(firmanteDos);	          		                       
		          celda.setCellValue(texto);
		          celda.setCellStyle(estilosLibro);
			  }
			  
			  if(i == 12){
				  
				  //String rangoModificacion = "L"+filasCount+i+1+":Q"+filasCount+i+1;						  
			  
				  //hojaG.addMergedRegion(CellRangeAddress.valueOf(rangoModificacion));
				  
				  estilosLibro = libro.createCellStyle();
				  estiloTexto = libro.createFont();		
		          estiloTexto.setFontHeightInPoints((short) 8);
		          estiloTexto.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		          estiloTexto.setColor(HSSFColor.BLACK.index);		
		          estilosLibro.setFont(estiloTexto);	
		          estilosLibro.setAlignment(CellStyle.ALIGN_CENTER);
		          estilosLibro.setVerticalAlignment(CellStyle.ALIGN_FILL);
				  
				  XSSFRow fila = hojaG.createRow(filasCount+i);		        	
		          XSSFCell celda = fila.createCell(6);        	        
		          XSSFRichTextString texto;		          		           	            	          
		          texto = new XSSFRichTextString(cargoFirmanteUno);
		          celda.setCellStyle(estilosLibro);
		          celda.setCellValue(texto);		
		          		          		    
		          celda = fila.createCell(14);        	        		          		          		           	            	          
		          texto = new XSSFRichTextString(cargoFirmanteDos);
		          celda.setCellStyle(estilosLibro);
		          celda.setCellValue(texto);
		          
			  }
			  
		
			}			
        	
        } catch (SyncconException e1) {			
        	log.error("Exception(" + e1.getClass().getName() + ") - ERROR: " + e1.getMessage());
			log.error("Exception(" + e1.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e1));			
		}        
		
	}

	private int generarDetalle(int posicion, XSSFSheet hojaParametro, List<CargaOperaciones> cargaOperacionesList,
			                      String glosa, String fechaTransaccion, 
			                      double valorCunetaParam, double valorDescuentoParam, double valorSaldoAcreedorParam, double valorSaldoDeudorParam, 
			                      String modeloReporeParam, String periodoAnioParam, String periodoTrimestreParam, String glosaParam,
			                      String hojaModeloNombreParam, String boPlanillaParam, double tipoCambio, String prefigoNombreHojaParam){
    	
		
		
		  
		log.info("Cantidad filas generarDetalle :"+hoja.getPhysicalNumberOfRows());
		
		log.info("Inicio generarDetalle");
		
		log.info("Longitud lista :"+cargaOperacionesList.size());
		
		
		int detalleRespuesta = 0;    
        	
    	CellStyle estilosLibro=null;
        XSSFFont estiloTexto = null;		 											
		estiloTexto = libro.createFont();	
		estilosLibro = libro.createCellStyle();
		estiloTexto.setFontHeightInPoints((short) 8);									      											        
        estilosLibro.setAlignment(CellStyle.ALIGN_CENTER);
        estilosLibro.setVerticalAlignment(CellStyle.ALIGN_FILL);									        
    	estilosLibro.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
	    estilosLibro.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
	    estilosLibro.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
	    estilosLibro.setFont(estiloTexto);
	    	    
	    CellStyle estilosLibro1=null;	    
	    estilosLibro1 = libro.createCellStyle();										      											        
		estilosLibro1.setAlignment(CellStyle.ALIGN_LEFT);
		estilosLibro1.setVerticalAlignment(CellStyle.ALIGN_FILL);									        
		estilosLibro1.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		estilosLibro1.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		estilosLibro1.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		estilosLibro1.setFont(estiloTexto);
	    	    
		
		XSSFDataFormat df = libro.createDataFormat();
		
	    CellStyle estilosLibro2 =null;	    	    
	    estilosLibro2 = libro.createCellStyle();	
	    //estilosLibro2.setDataFormat(df.getFormat("#,##0.00000000"));
	    estilosLibro2.setDataFormat(df.getFormat("#,##0.##"));
	    estilosLibro2.setAlignment(CellStyle.ALIGN_RIGHT);
	    estilosLibro2.setVerticalAlignment(CellStyle.ALIGN_FILL);									        
	    estilosLibro2.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
	    estilosLibro2.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
	    estilosLibro2.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
	    estilosLibro2.setFont(estiloTexto);
	    
	    
	    posicion = posicion+1; 
	    	   	    
	    XSSFCell celda = null;
	    
	    double contadorValor = 0;
	    double contadorDescuentos = 0;
	    double contadorSaldoAcreedor = 0;
	    double contadorSaldoDeudor = 0;
	    
    	for (int j = 0; j < cargaOperacionesList.size(); j++) {
    		    
    		
        	//HSSFRow fila = hoja.createRow(posicion+j+13);
        	//XSSFRow fila = hoja.createRow(filasCount+j);
    		XSSFRow fila = hojaParametro.createRow(posicion+j);
    		for (int i = 0; i < 17 ; i++) {
				
    			hojaParametro.setColumnWidth(6, 15000);
    			hojaParametro.setColumnWidth(1, 10000);	
  				  	
  				XSSFRichTextString texto = new XSSFRichTextString("");  				
  				celda = fila.createCell(i);    
  				
  				if( i == 0){ 
  					
  					if(fechaTransaccion.length()>0){
  					texto = new XSSFRichTextString(fechaTransaccion) ;  	  					
  					}else{
  					texto = new XSSFRichTextString("") ;  					
  					}
  					celda.setCellValue(texto);
  				}
  				if(i == 1){  					
  					texto = new XSSFRichTextString(cargaOperacionesList.get(j).getNombreAsegurado()) ;
  				
  					if(!(texto == null)){  											    
  	  					celda.setCellValue(texto);		
  	  				}  			        	 
  				
  				}
  				
  				if(i == 2){
  					texto = new XSSFRichTextString(cargaOperacionesList.get(j).getNumPoliza()) ;
  				
  	  				if(!(texto == null)){  	  					
  	  					celda.setCellValue(texto);		
  	  				}
  			        	   				
  				}
  				
  				if(i == 3){
  					texto = new XSSFRichTextString(cargaOperacionesList.get(j).getNumSiniestro()) ;
  				
  	  				if(!(texto == null)){  	  			   
  	  					celda.setCellValue(texto);		
  	  				}
  			        	 
  				}
  				
  				if(i == 6){
  					
  					texto = new XSSFRichTextString("    "+String.valueOf(glosa));
  					
  	  				if(!(texto == null)){  	  			   
  	  					celda.setCellValue(texto);		
  	  				}
  			        	   				
  				}
  				
  				if(i == 8){
  					
  					  				
  					if(cargaOperacionesList.get(j).getPrimaPagarReacedidos() == null){
  					    texto = new XSSFRichTextString("");
  						celda.setCellValue(texto);
  					}else{  						
  					    //texto = new XSSFRichTextString(String.valueOf(cargaOperacionesList.get(j).getPrimaPagarReacedidos()));
  						
  						if(prefigoNombreHojaParam.equalsIgnoreCase("PEND")){
  						
  							celda.setCellValue(cargaOperacionesList.get(j).getPrimaPagarReacedidos().doubleValue() * tipoCambio);  	  						
  	  						contadorValor = contadorValor + (cargaOperacionesList.get(j).getPrimaPagarReacedidos().doubleValue() * tipoCambio);
  							
  						}else{
  							
  							celda.setCellValue(cargaOperacionesList.get(j).getPrimaPagarReacedidos().doubleValue());  	  						
  	  						contadorValor = contadorValor + cargaOperacionesList.get(j).getPrimaPagarReacedidos().doubleValue();
  							
  						}
  						
  						
  					}
  						   				
  				}
  				
  				if(i == 9){
  					
  				
  					if(cargaOperacionesList.get(j).getPrimaPagarReaceptados() == null){
  					   texto = new XSSFRichTextString("");
  						celda.setCellValue(texto);
  					}else{
  						//texto = new XSSFRichTextString(String.valueOf(cargaOperacionesList.get(j).getPrimaPagarReaceptados()));
  						
  						if(prefigoNombreHojaParam.equalsIgnoreCase("PEND")){
  						
  							celda.setCellValue(cargaOperacionesList.get(j).getPrimaPagarReaceptados().doubleValue() * tipoCambio );
  	  						
  	  						contadorValor = contadorValor + (cargaOperacionesList.get(j).getPrimaPagarReaceptados().doubleValue() * tipoCambio);  	  						
  							
  						}else{
  							
  							celda.setCellValue(cargaOperacionesList.get(j).getPrimaPagarReaceptados().doubleValue());
  	  						
  	  						contadorValor = contadorValor + cargaOperacionesList.get(j).getPrimaPagarReaceptados().doubleValue();
  							
  						}
  						
  						
  					}
  						        	 
  				
  				}
  				
  				if(i == 10){
  					
  					if(cargaOperacionesList.get(j).getSiniestroPagarReasedidos() == null){
   					   texto = new XSSFRichTextString("");
   						celda.setCellValue(texto);
   					}else{  						
   						//texto = new XSSFRichTextString(String.valueOf(cargaOperacionesList.get(j).getSiniestroPagarReasedidos()));
   						
   						if(prefigoNombreHojaParam.equalsIgnoreCase("PEND")){
   							
   							celda.setCellValue(cargaOperacionesList.get(j).getSiniestroPagarReasedidos().doubleValue() * tipoCambio );
   	   						
   	   						contadorValor = contadorValor + (cargaOperacionesList.get(j).getSiniestroPagarReasedidos().doubleValue() * tipoCambio);
   						
   						}else{
   							
   							celda.setCellValue(cargaOperacionesList.get(j).getSiniestroPagarReasedidos().doubleValue());
   	   						
   	   						contadorValor = contadorValor + cargaOperacionesList.get(j).getSiniestroPagarReasedidos().doubleValue();
   						
   						}
   						
   						
   					}  					  					
  				
  				}
  				
  				if(i == 11){
  					
  					if(cargaOperacionesList.get(j).getSiniestroPagarReaaceptados() == null){
    					   texto = new XSSFRichTextString("");
    						celda.setCellValue(texto);
    				}else{  						
    				  	//texto = new XSSFRichTextString(String.valueOf(cargaOperacionesList.get(j).getSiniestroPagarReaaceptados()));    
    					
    					if(prefigoNombreHojaParam.equalsIgnoreCase("PEND")){
    					
    						celda.setCellValue(cargaOperacionesList.get(j).getSiniestroPagarReaaceptados().doubleValue() * tipoCambio );
        					
        					contadorValor = contadorValor +  (cargaOperacionesList.get(j).getSiniestroPagarReaaceptados().doubleValue() * tipoCambio );
    						
    					}else{
    						
    						celda.setCellValue(cargaOperacionesList.get(j).getSiniestroPagarReaaceptados().doubleValue());
        					
        					contadorValor = contadorValor + cargaOperacionesList.get(j).getSiniestroPagarReaaceptados().doubleValue();
        					
    					}
    					
    					
    					
    				}  					  					

  				}
  				
  				if(i == 12){  	
  					
  					if(cargaOperacionesList.get(j).getCuentasCobrar() == null){
 					   texto = new XSSFRichTextString("");
 						celda.setCellValue(texto);
	 				}else{  						
	 					//texto = new XSSFRichTextString(String.valueOf(cargaOperacionesList.get(j).getCuentasCobrar()));
	 					
	 					if(prefigoNombreHojaParam.equalsIgnoreCase("PEND")){
	 						
	 						celda.setCellValue(cargaOperacionesList.get(j).getCuentasCobrar().doubleValue() * tipoCambio );
		 					
		 					contadorValor = contadorValor + (cargaOperacionesList.get(j).getCuentasCobrar().doubleValue() * tipoCambio );
		 					
	 						
	 					}else {
	 						
	 						celda.setCellValue(cargaOperacionesList.get(j).getCuentasCobrar().doubleValue());
		 					
		 					contadorValor = contadorValor + cargaOperacionesList.get(j).getCuentasCobrar().doubleValue();
		 					
	 						
	 					}
	 					
	 					
	 				}  
  			
  				}
  				
  				if(i == 13){
  					
  					
  					if(cargaOperacionesList.get(j).getCuentasPagar() == null){
  					   texto = new XSSFRichTextString("");
  						celda.setCellValue(texto);
 	 				}else{  						
 	 					//texto = new XSSFRichTextString(String.valueOf(cargaOperacionesList.get(j).getCuentasPagar())); 	 
 	 					
 	 					if(prefigoNombreHojaParam.equalsIgnoreCase("PEND")){
 	 						
 	 						celda.setCellValue(cargaOperacionesList.get(j).getCuentasPagar().doubleValue() * tipoCambio);
 	 	 					
 	 	 					contadorValor = contadorValor + cargaOperacionesList.get(j).getCuentasPagar().doubleValue() * tipoCambio;
 	 	 					
 	 						
 	 					}else{
 	 					
 	 						celda.setCellValue(cargaOperacionesList.get(j).getCuentasPagar().doubleValue());
 	 	 					
 	 	 					contadorValor = contadorValor + cargaOperacionesList.get(j).getCuentasPagar().doubleValue();
 	 	 					
 	 						
 	 					} 	 					
 	 					
 	 				}  
  					  			  			        	   				
  				}
  				
  				if(i == 14){  					
  					
  					if(cargaOperacionesList.get(j).getDescuentosComisionesRea() == null){
   					   texto = new XSSFRichTextString("");
   						celda.setCellValue(texto);
  	 				}else{  						
  	 					//texto = new XSSFRichTextString(String.valueOf(cargaOperacionesList.get(j).getDescuentosComisionesRea()));
  	 					
  	 					if(prefigoNombreHojaParam.equalsIgnoreCase("PEND")){
  	 						
  	 						celda.setCellValue(cargaOperacionesList.get(j).getDescuentosComisionesRea().doubleValue() * tipoCambio);
  	  	 					
  	  	 					contadorDescuentos = contadorDescuentos + ( cargaOperacionesList.get(j).getDescuentosComisionesRea().doubleValue() * tipoCambio ); 
  	  	 					
  	 					}else{
  	 					
  	 						celda.setCellValue(cargaOperacionesList.get(j).getDescuentosComisionesRea().doubleValue());
  	  	 					
  	  	 					contadorDescuentos = contadorDescuentos + cargaOperacionesList.get(j).getDescuentosComisionesRea().doubleValue(); 
  	  	 					
  	 					}
  	 					
  	 					
  	 				}  
  			        	 
  				}  				

  				if(i == 15){  	
  					
  					if(cargaOperacionesList.get(j).getSaldoDeudor() == null){
    					   texto = new XSSFRichTextString("");
    						celda.setCellValue(texto);
   	 				}else{  						
   	 					//texto = new XSSFRichTextString(String.valueOf(cargaOperacionesList.get(j).getSaldoDeudor()));   
   	 					
   	 				if(prefigoNombreHojaParam.equalsIgnoreCase("PEND")){
   	 					
   	 					celda.setCellValue(cargaOperacionesList.get(j).getSaldoDeudor().doubleValue() * tipoCambio);
	 					
	 					contadorSaldoDeudor = contadorSaldoDeudor + (cargaOperacionesList.get(j).getSaldoDeudor().doubleValue() * tipoCambio );
   	 					
   	 				}else{
   	 					
   	 					celda.setCellValue(cargaOperacionesList.get(j).getSaldoDeudor().doubleValue());
	 					
	 					contadorSaldoDeudor = contadorSaldoDeudor + cargaOperacionesList.get(j).getSaldoDeudor().doubleValue();
   	 					
   	 				}
   	 					
   	 					
   	 					
   	 				}  
	
  				}
  				
  				if(i == 16){
  					
  					if(cargaOperacionesList.get(j).getSaldoAcreeedor() == null){
 					   texto = new XSSFRichTextString("");
 						celda.setCellValue(texto);
	 				}else{  							 					
	 					
	 					if(prefigoNombreHojaParam.equalsIgnoreCase("PEND")){
	 						
	 						celda.setCellValue(cargaOperacionesList.get(j).getSaldoAcreeedor().doubleValue() * tipoCambio);
		 					
		 					contadorSaldoAcreedor = contadorSaldoAcreedor + ( cargaOperacionesList.get(j).getSaldoAcreeedor().doubleValue()* tipoCambio );
	 						
	 					}else{
	 					
	 						celda.setCellValue(cargaOperacionesList.get(j).getSaldoAcreeedor().doubleValue());
		 					
		 					contadorSaldoAcreedor = contadorSaldoAcreedor + cargaOperacionesList.get(j).getSaldoAcreeedor().doubleValue();
	 						
	 					}
	 					
	 					
	 				}  
  		  			        	   					
  				}
  				  			    				
  				if(i == 8 || i == 9 || i == 10 || i ==11 || i == 12 || i == 13 || i == 14 || i == 15 || i == 16){
  					
  					celda.setCellStyle(estilosLibro2);
  				
  				}else if(i == 1 || i == 6){
  					
  					celda.setCellStyle(estilosLibro1);
  					
  				}else{
  					
  					celda.setCellStyle(estilosLibro);
  					
  				}    				
  		         				
			}
    		
		}
    	
    	boolean diferenciaMontos = false;
    	
    	if(valorCunetaParam != redondeoDouble(contadorValor)){
    		diferenciaMontos = true;
    	}
    	   
    	if(valorDescuentoParam != redondeoDouble(contadorDescuentos)){
    		diferenciaMontos = true;
    	}
    	
    	if(valorSaldoDeudorParam != redondeoDouble(contadorSaldoDeudor)){
    		diferenciaMontos = true;
    	}
    	    	
    	if(valorSaldoAcreedorParam != redondeoDouble(contadorSaldoAcreedor)){
    		diferenciaMontos = true;
    	}
    	
    	if(diferenciaMontos == true ){
    		
	    	ReportesGeneradosSBSModeloUnoDetalle listaReporteFiltros = null;																									
			listaReporteFiltros  = reporteSBSModeloUnoDetalleService.consultarReportesParaErrores(modeloReporeParam,periodoAnioParam,periodoTrimestreParam);	
				  
		    if(listaReporteFiltros != null){
			
		       String nombreReporte = "";
			   nombreReporte = obtenerNombreReporte(modeloReporeParam, periodoAnioParam, periodoTrimestreParam);
			   grabarErrorLog(listaReporteFiltros.getCodReporte().longValue(),nombreReporte+" || "+hojaModeloNombreParam,glosaParam,boPlanillaParam,
					          "Uno de los valores entre el resumen y el detalle no son iguales :"+" Valor resumen de cuenta :"+valorCunetaParam
					                                                                             +" - Valor detalle :"+redondeoDouble(contadorValor)
					                                                                             +" - Valor resumen de descuento :"+valorDescuentoParam
					                                                                             +" - Valor detalle :"+redondeoDouble(contadorDescuentos)
					                                                                             +" - Valor resumen de saldo deudor :"+valorSaldoDeudorParam
					                                                                             +" - Valor detalle :"+redondeoDouble(contadorSaldoDeudor)
					                                                                             +" - Valor resumen de saldo acreedir :"+valorSaldoAcreedorParam
					                                                                             +" - Valor detalle :"+redondeoDouble(contadorSaldoAcreedor));
			   
			   
			   reporteSBSModeloUnoDetalleService.actualizarEstadoReporte(listaReporteFiltros.getCodReporte().longValue());
		    
			   detalleRespuesta = 1;
			   
		    }
	    
    	}    	
    	
    	return detalleRespuesta;
    }
        	
	private int clasificarCuenta(String detalleCuentaValor,
			String tipoMovimientoValor, String tipoReaseguroValor) {
		
		int tipoClasificacion = 0; 
		
		/*
		 * PRIMAS POR PAGAR REASEGUROS CEDIDOS
		 * PRIMAS POR COBRAR REASEGUROS ACEPTADOS
		 * SINIESTROS POR COBRAR REASEGUROS CEDIDOS
		 * SINIESTROS POR PAGAR REASEGUROS ACEPTADOS
		 * OTRAS CUENTAS POR COBRAR REASEGUROS CEDIDOS
		 * OTRAS CUENTAS POR PAGAR REASEGUROS ACEPTADOS	
		 * */
		
		if(detalleCuentaValor.equalsIgnoreCase("PRIMA") && tipoMovimientoValor.equalsIgnoreCase("PAGAR") && tipoReaseguroValor.equalsIgnoreCase("CEDIDO") ){
			tipoClasificacion = 8;		
		}
		
		if(detalleCuentaValor.equalsIgnoreCase("PRIMA") && tipoMovimientoValor.equalsIgnoreCase("COBRAR") && tipoReaseguroValor.equalsIgnoreCase("RECIBIDO") ){
			tipoClasificacion = 9;
		}
		
		if(detalleCuentaValor.equalsIgnoreCase("SINIESTROS") && tipoMovimientoValor.equalsIgnoreCase("COBRAR") && tipoReaseguroValor.equalsIgnoreCase("CEDIDO") ){
			tipoClasificacion = 10;
		}
		
		if(detalleCuentaValor.equalsIgnoreCase("SINIESTROS") && tipoMovimientoValor.equalsIgnoreCase("PAGAR") && tipoReaseguroValor.equalsIgnoreCase("RECIBIDO") ){
			tipoClasificacion = 11;
		}
		
		if(detalleCuentaValor.equalsIgnoreCase("OTROS") && tipoMovimientoValor.equalsIgnoreCase("COBRAR") && tipoReaseguroValor.equalsIgnoreCase("CEDIDO") ){
			tipoClasificacion = 12;
		}
		
		if(detalleCuentaValor.equalsIgnoreCase("OTROS") && tipoMovimientoValor.equalsIgnoreCase("PAGAR") && tipoReaseguroValor.equalsIgnoreCase("RECIBIDO") ){
			tipoClasificacion = 13;
		}
		
		return tipoClasificacion;
	}

	@SuppressWarnings("unused")
	public Double obtener_Monto_Cuenta24_Dscto(List<ListaCuentasDataSun> objListaCuentasDataSun2, String numeroCuenta, String tipo_diario, Integer numero_diario, String glosa, String monedaModeloValor, String debitoCredito, Double monto_operacion){		
		
		Double retorno =0.0;	    	
    	List<ListaCuentasDataSun> objListaMontoPrima = new ArrayList<ListaCuentasDataSun>();
    	ListaCuentasDataSun objCuentasDataSun = null;    	    	    	
    	boolean encontro = false;   		
		 
    	for (int i = 0; i < objListaCuentasDataSun2.size(); i++) {	
			
    		if(objListaCuentasDataSun2.get(i).getCodigoCuenta().equals(numeroCuenta)){
    			
    			objCuentasDataSun = new ListaCuentasDataSun();
    			
    			objCuentasDataSun.setImporteBase(objListaCuentasDataSun2.get(i).getImporteBase());
    			objCuentasDataSun.setImporteTransaccion(objListaCuentasDataSun2.get(i).getImporteTransaccion());
    			objCuentasDataSun.setTipoDiario(objListaCuentasDataSun2.get(i).getTipoDiario());
    			objCuentasDataSun.setNumerodiario(objListaCuentasDataSun2.get(i).getNumerodiario());
    			objCuentasDataSun.setGlosa(objListaCuentasDataSun2.get(i).getGlosa());
    			    			
    			objListaMontoPrima.add(objCuentasDataSun);    			
    		}    		
		}
    	
    	for (int i = 0; i < objListaMontoPrima.size(); i++) {
			
    		if(objListaMontoPrima.get(i).getTipoDiario().equals(tipo_diario) 
       		     && objListaMontoPrima.get(i).getNumerodiario().equals(numero_diario) 
       		     && objListaMontoPrima.get(i).getGlosa().equalsIgnoreCase(glosa)
       		     && objListaMontoPrima.get(i).getDebitoCredito().equalsIgnoreCase(debitoCredito)){
    			
    			if(monedaModeloValor.equals("PEN")){        				   
 				   retorno = new Double(objListaMontoPrima.get(i).getImporteBase());        				   
 			   }else{
 				   retorno = new Double(objListaMontoPrima.get(i).getImporteTransaccion());
 			   }
    			
    		   if(retorno != monto_operacion){
    			   encontro = true;    			   
    			   break;
    		   }
    			
    		}
    		
		}
    	
    	if(encontro = true){
    		retorno = redondeoDouble(retorno);
    	}else{
    		retorno = -1.0;
    	}
    		    			
		return retorno;
	}
	
    @SuppressWarnings("unused")
	public Double obtener_Monto_Prima(List<ListaCuentasDataSun> objListaCuentasDataSun2, String numeroCuenta, String tipo_diario, Integer numero_diario, String glosa, String monedaModeloValor, Double monto_operacion){
    	
    	Double retorno =0.0;	
    	Integer contador = 0;
    	List<ListaCuentasDataSun> objListaMontoPrima = new ArrayList<ListaCuentasDataSun>();
    	ListaCuentasDataSun objCuentasDataSun = null;    	    	    	
    	boolean encontro = false;     	
    	 
    	for (int i = 0; i < objListaCuentasDataSun2.size(); i++) {	
			
    		if(objListaCuentasDataSun2.get(i).getCodigoCuenta().equals(numeroCuenta)){
    			
    			objCuentasDataSun = new ListaCuentasDataSun();
    			
    			objCuentasDataSun.setImporteBase(objListaCuentasDataSun2.get(i).getImporteBase());
    			objCuentasDataSun.setImporteTransaccion(objListaCuentasDataSun2.get(i).getImporteTransaccion());
    			objCuentasDataSun.setTipoDiario(objListaCuentasDataSun2.get(i).getTipoDiario());
    			objCuentasDataSun.setNumerodiario(objListaCuentasDataSun2.get(i).getNumerodiario());
    			objCuentasDataSun.setGlosa(objListaCuentasDataSun2.get(i).getGlosa());
    			    			
    			objListaMontoPrima.add(objCuentasDataSun);
    			
    		}    		
		}
    	
    	    	
    	for (int i = 0; i < objListaMontoPrima.size(); i++) {
			
    		   if(objListaMontoPrima.get(i).getTipoDiario().equals(tipo_diario) 
    		     && objListaMontoPrima.get(i).getNumerodiario().equals(numero_diario) 
    		     && objListaMontoPrima.get(i).getGlosa().equalsIgnoreCase(glosa)){
    			   
    			   contador = contador + 1 ;    			   
    		   }    	
		}
    	
    	if(contador <= 1){
    		
    		//VALIDAMOS <= 1 SI CUENTAS ENCONTRADAS SON MENORES O IGUAL A 1
        	for (int i = 0; i < objListaMontoPrima.size(); i++) {
    			
        		   if(objListaMontoPrima.get(i).getTipoDiario().equals(tipo_diario) 
        		     && objListaMontoPrima.get(i).getNumerodiario().equals(numero_diario) 
        		     && objListaMontoPrima.get(i).getGlosa().equalsIgnoreCase(glosa)){
        			           			
        			   if(monedaModeloValor.equals("PEN")){        				   
        				   retorno = new Double(objListaMontoPrima.get(i).getImporteBase());        				   
        			   }else{
        				   retorno = new Double(objListaMontoPrima.get(i).getImporteTransaccion());
        			   }
        			   break;
        		   }
    		}        	        	    		
    	}else{
    		
    		for (int i = 0; i < objListaMontoPrima.size(); i++) {
    			
     		   if(objListaMontoPrima.get(i).getTipoDiario().equals(tipo_diario) 
     		     && objListaMontoPrima.get(i).getNumerodiario().equals(numero_diario) 
     		     && objListaMontoPrima.get(i).getGlosa().equalsIgnoreCase(glosa)){
     			           			
     			   if(monedaModeloValor.equals("PEN")){        				   
     				   retorno = new Double(objListaMontoPrima.get(i).getImporteBase());
     				   
     				   if(Math.abs(monto_operacion) * 2 > Math.abs(retorno)){
     					  encontro = true;  
     					  break;
     				   }     				   
     			   }else{
     				   retorno = new Double(objListaMontoPrima.get(i).getImporteTransaccion());
     				   if(monto_operacion * 2 < Math.abs(retorno)){
     					  encontro = true;
     					  break;
     				   }
     			   }     			   
     		   }
    		}    	
    		
    	}
    	
    	retorno = redondeoDouble(retorno);
    	
    	return retorno;    
    }
	  
    @SuppressWarnings("unused")
	public Double obtener_Monto_Dscto(List<ListaCuentasDataSun> objListaCuentasDataSun2, String numeroCuenta, String tipo_diario, Integer numero_diario, String glosa, String monedaModeloValor, Double monto_operacion, Double montoPrima){
    	
    	Double retorno =0.0;	    	
    	List<ListaCuentasDataSun> objListaMontoPrima = new ArrayList<ListaCuentasDataSun>();
    	ListaCuentasDataSun objCuentasDataSun = null;    	    	    	
    	boolean encontro = false;
    	Double montoResta =0.0;
    	
    	for (int i = 0; i < objListaCuentasDataSun2.size(); i++) {	
			
    		if(objListaCuentasDataSun2.get(i).getCodigoCuenta().equals(numeroCuenta)){
    			
    			objCuentasDataSun = new ListaCuentasDataSun();
    			
    			objCuentasDataSun.setImporteBase(objListaCuentasDataSun2.get(i).getImporteBase());
    			objCuentasDataSun.setImporteTransaccion(objListaCuentasDataSun2.get(i).getImporteTransaccion());
    			objCuentasDataSun.setTipoDiario(objListaCuentasDataSun2.get(i).getTipoDiario());
    			objCuentasDataSun.setNumerodiario(objListaCuentasDataSun2.get(i).getNumerodiario());
    			objCuentasDataSun.setGlosa(objListaCuentasDataSun2.get(i).getGlosa());
    			    			
    			objListaMontoPrima.add(objCuentasDataSun);    			
    		}    		
		}
    	
    	for (int i = 0; i < objListaMontoPrima.size(); i++) {
			
    		if(objListaMontoPrima.get(i).getTipoDiario().equals(tipo_diario) 
    				&& objListaMontoPrima.get(i).getNumerodiario().equals(numero_diario)
    				&& objListaMontoPrima.get(i).getGlosa().equals(glosa)){
    			
    			if(monedaModeloValor.equals("PEN")){
    				retorno =new Double(objListaMontoPrima.get(i).getImporteBase());
    			}else{
    				retorno =new Double(objListaMontoPrima.get(i).getImporteTransaccion());
    			}
    			
    			montoResta= Math.abs(Math.abs(montoPrima)-Math.abs(monto_operacion));    			
    			montoResta = redondeoDouble(montoResta);
    			
    			if( montoResta == redondeoDouble(Math.abs(retorno)) ){
    				break;
    			}
    		}
    		
		}
    	
    	retorno = redondeoDouble(retorno);    	
		return retorno;
    }
	
    
	@SuppressWarnings("unused")
	public Double obtener_Monto_Cuenta24_Prima(List<ListaCuentasDataSun> objListaCuentasDataSun2, String numeroCuenta, String tipo_diario, Integer numero_diario, String glosa, String monedaModeloValor, String debitoCredito, Double monto_operacion){		
		  
		Double retorno =0.0;	
    	List<ListaCuentasDataSun> objListaMontoPrima = new ArrayList<ListaCuentasDataSun>();
    	ListaCuentasDataSun objCuentasDataSun = null;    	    	    	
    	boolean encontro = false;
		
    	for (int i = 0; i < objListaCuentasDataSun2.size(); i++) {	
			
    		if(objListaCuentasDataSun2.get(i).getCodigoCuenta().equals(numeroCuenta)){
    			
    			objCuentasDataSun = new ListaCuentasDataSun();
    			
    			objCuentasDataSun.setImporteBase(objListaCuentasDataSun2.get(i).getImporteBase());
    			objCuentasDataSun.setImporteTransaccion(objListaCuentasDataSun2.get(i).getImporteTransaccion());
    			objCuentasDataSun.setTipoDiario(objListaCuentasDataSun2.get(i).getTipoDiario());
    			objCuentasDataSun.setNumerodiario(objListaCuentasDataSun2.get(i).getNumerodiario());
    			objCuentasDataSun.setGlosa(objListaCuentasDataSun2.get(i).getGlosa());
    			    			
    			objListaMontoPrima.add(objCuentasDataSun);    			
    		}    		
		}
    	
    	for (int i = 0; i < objListaMontoPrima.size(); i++) {
			
    		if(objListaMontoPrima.get(i).getTipoDiario().equals(tipo_diario)
    			&& objListaMontoPrima.get(i).getNumerodiario().equals(numero_diario)
    			&& objListaMontoPrima.get(i).getGlosa().equals(glosa)
    			&& objListaMontoPrima.get(i).getDebitoCredito().equals(debitoCredito)){
    		
    			if(monedaModeloValor.equals("PEN")){
    				
    				retorno = new Double(objListaMontoPrima.get(i).getImporteBase());
    				
    			}else{
    				
    				retorno = new Double(objListaMontoPrima.get(i).getImporteTransaccion());    			
    			}
    			
    			if(retorno != monto_operacion){    				
    				encontro = true;    				
    			}    			    			
    		}    		
		}
    					
    	if (encontro = true){    		    		
    		retorno = redondeoDouble(retorno);
    	}else{
    		retorno = -1.0;
    	}
    			
		return retorno;
	}
	
	public double redondeoDouble(Double numero){
		
		  /*double valor = numero;
	      String val = valor+"";
	      BigDecimal big = new BigDecimal(val);
	      big = big.setScale(2, RoundingMode.HALF_UP);
	      System.out.println("Número : "+big);
		
	      return big.doubleValue();	      */
		
		return Math.rint(numero*100)/100.0;
	}
		
	public void crearCabeceraReporte(String tipoReaseguro,String nombreSocioCuenta,String monedaModeloValor, XSSFSheet hoja, String monedaCuenta, Long codigoEmpresa, String periodoAnioParam, String periodoTrimestreParam){
		
		
		  XSSFDataFormat df = libro.createDataFormat();
		  XSSFFont estiloTexto1 = null;         
		  estiloTexto1 = libro.createFont();		
      	  estiloTexto1.setFontHeightInPoints((short) 8);
	      estiloTexto1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	      estiloTexto1.setColor(HSSFColor.BLACK.index);
		  
		  CellStyle estilosLibro2 =null;
		  estilosLibro2 = libro.createCellStyle();	
		  estilosLibro2.setDataFormat(df.getFormat("#,##0.00"));
		  estilosLibro2.setAlignment(CellStyle.ALIGN_RIGHT);
		  estilosLibro2.setVerticalAlignment(CellStyle.ALIGN_FILL);									        
		  estilosLibro2.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		  estilosLibro2.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		  estilosLibro2.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		  estilosLibro2.setFont(estiloTexto1);
		
		// Al DD de MMM de YYYY
		String anio = null;
		String fechaCabecera="";
	
		
		
		if(periodoTrimestreParam.equalsIgnoreCase("1401")){
			fechaCabecera ="Al "+"31 "+"de "+"marzo "+"de "+periodoAnioParam;
		}else if(periodoTrimestreParam.equalsIgnoreCase("1402")){
			fechaCabecera ="Al "+"30 "+"de "+"junio "+"de "+periodoAnioParam;
		}else if(periodoTrimestreParam.equalsIgnoreCase("1403")){
			fechaCabecera ="Al "+"30 "+"de "+"septiembre "+"de "+periodoAnioParam;
		}else if(periodoTrimestreParam.equalsIgnoreCase("1404")){
			fechaCabecera ="Al "+"31 "+"de "+"diciembre "+"de "+periodoAnioParam;
		}
		
		if(periodoAnioParam.length()>0){
		anio =String.valueOf(Integer.parseInt(periodoAnioParam) - 1);
		}
		
		//CellStyle estilosLibro=null;
		XSSFCellStyle estilosLibro=null; 
		
		XSSFFont estiloTexto = null;      		
      
		for (int i = 0; i < 12; i++) {
			
			// Se crea una fila dentro de la hoja
	        XSSFRow fila = hoja.createRow(i);
    		
	        estilosLibro=null;
	        estiloTexto = null;     
	        
	        // Se crea una celda dentro de la fila								       
	        
	        // Se crea el contenido de la celda y se mete en ella.			
	        if(i == 0){		
	        	XSSFCell celda = fila.createCell(0);
		        XSSFRichTextString texto = new XSSFRichTextString("MODELO N° 1");
		        celda.setCellValue(texto);								        
		        estilosLibro = libro.createCellStyle();
		        estiloTexto = libro.createFont();		
	        	estiloTexto.setFontHeightInPoints((short) 8);
		        estiloTexto.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		        estiloTexto.setColor(HSSFColor.BLACK.index);		
		        estilosLibro.setAlignment(CellStyle.ALIGN_CENTER);
		        estilosLibro.setFont(estiloTexto);	
		        celda.setCellStyle(estilosLibro);								        
		        hoja.addMergedRegion(CellRangeAddress.valueOf("A1:O1"));								        
	        }
	        
	        if(i == 1){
	        	XSSFCell celda = fila.createCell(0);
	        	XSSFRichTextString texto = new XSSFRichTextString("CONTRATO FACULTATIVO - REASEGURADOR LOCAL Y DEL EXTERIOR");
		        celda.setCellValue(texto);								        
		        estilosLibro = libro.createCellStyle();
		        estiloTexto = libro.createFont();		
	        	estiloTexto.setFontHeightInPoints((short) 8);
		        estiloTexto.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		        estiloTexto.setColor(HSSFColor.BLACK.index);									        
		        estilosLibro.setAlignment(CellStyle.ALIGN_CENTER);
		        estilosLibro.setFont(estiloTexto);	
		        celda.setCellStyle(estilosLibro);											        
		        hoja.addMergedRegion(CellRangeAddress.valueOf("A2:O2"));									        
	        }
	        
	        if(i == 3){
	        	
	        	XSSFCell celda = fila.createCell(0);
	        	XSSFRichTextString texto = new XSSFRichTextString(nombreSocioCuenta);
		        celda.setCellValue(texto);								        
		        estilosLibro = libro.createCellStyle();
		        
		        estiloTexto = libro.createFont();		
	        	estiloTexto.setFontHeightInPoints((short) 8);
		        estiloTexto.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		        estiloTexto.setColor(HSSFColor.BLACK.index);
		        
		        estilosLibro.setAlignment(CellStyle.ALIGN_CENTER);
		        estilosLibro.setFont(estiloTexto);	
		        celda.setCellStyle(estilosLibro);								        
		        hoja.addMergedRegion(CellRangeAddress.valueOf("A4:O4"));									        
	        }
	        
	        if(i == 4){
	        				        
	        	XSSFCell celda = fila.createCell(0);
	        	XSSFRichTextString texto = new XSSFRichTextString(fechaCabecera);
		        celda.setCellValue(texto);								        
		        estilosLibro = libro.createCellStyle();
		        
		        estiloTexto = libro.createFont();		
	        	estiloTexto.setFontHeightInPoints((short) 8);
		        estiloTexto.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		        estiloTexto.setColor(HSSFColor.BLACK.index);
		        
		        estilosLibro.setAlignment(CellStyle.ALIGN_CENTER);
		        estilosLibro.setFont(estiloTexto);	
		        celda.setCellStyle(estilosLibro);								        
		        hoja.addMergedRegion(CellRangeAddress.valueOf("A5:O5"));
		        
	        }
	        
	        if(i == 5){
	        	
	        		String monedaDescripcionCabecera = "";
	        	
	        	if(monedaModeloValor.equalsIgnoreCase("PEN")){
	        		monedaDescripcionCabecera = "Nuevos Soles";
	        	}else if(monedaModeloValor.equalsIgnoreCase("USD")){
	        		monedaDescripcionCabecera = "Dolares Americanos";
	        	}
	        	
	        	XSSFCell celda = fila.createCell(0);
	        	XSSFRichTextString texto = new XSSFRichTextString(monedaDescripcionCabecera);
		        celda.setCellValue(texto);								        
		        estilosLibro = libro.createCellStyle();
		        
		        estiloTexto = libro.createFont();		
	        	estiloTexto.setFontHeightInPoints((short) 8);
		        estiloTexto.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		        estiloTexto.setColor(HSSFColor.BLACK.index);
		        
		        estilosLibro.setAlignment(CellStyle.ALIGN_CENTER);
		        estilosLibro.setFont(estiloTexto);	
		        celda.setCellStyle(estilosLibro);								        
		        hoja.addMergedRegion(CellRangeAddress.valueOf("A6:O6"));									        
	        }
	        
	        if(i == 10){
	        	 	estiloTexto = libro.createFont();	
		        	XSSFCell celda = fila.createCell(0);        	        
		        	XSSFRichTextString texto;
		        	estilosLibro = libro.createCellStyle();
		        	
			        estilosLibro.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
			        estilosLibro.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
			        estilosLibro.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
			        		        	
			        celda.setCellStyle(estilosLibro);								        		      
			        hoja.setColumnWidth(6, 15000);
			        hoja.setColumnWidth(1, 10000);	
			        
			        celda = fila.createCell(1);
		        	texto = new XSSFRichTextString("");
			        celda.setCellValue(texto);								        
			        estilosLibro = libro.createCellStyle();
			        
			        estiloTexto = libro.createFont();		
		        	estiloTexto.setFontHeightInPoints((short) 8);
			        estiloTexto.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			        estiloTexto.setColor(HSSFColor.BLACK.index);
			        
			        estilosLibro.setAlignment(CellStyle.ALIGN_CENTER);
			        estilosLibro.setVerticalAlignment(CellStyle.ALIGN_FILL);
			       
			        estilosLibro.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);									        
			        estilosLibro.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);					
			        estilosLibro.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);					
			        estilosLibro.setAlignment(CellStyle.ALIGN_CENTER);			        		        
			        estilosLibro.setFont(estiloTexto);	
			        celda.setCellStyle(estilosLibro);								        
			        celda = fila.createCell(2);
			        texto = new XSSFRichTextString("");
			        celda.setCellValue(texto);								        		        								        
			        celda.setCellStyle(estilosLibro);								        
			        celda = fila.createCell(3);
			        texto = new XSSFRichTextString("");
			        celda.setCellValue(texto);				        		        									        									        
			        celda.setCellStyle(estilosLibro);
			        celda = fila.createCell(4);
			        texto = new XSSFRichTextString("");
			        celda.setCellValue(texto);				        		        									        									        
			        celda.setCellStyle(estilosLibro);
			        celda = fila.createCell(5);
			        texto = new XSSFRichTextString("");
			        celda.setCellValue(texto);				        		        									        									        
			        celda.setCellStyle(estilosLibro);
			        
			        celda = fila.createCell(6);		        
			        texto = new XSSFRichTextString("Saldo anterior al 31 de dicimebre del "+anio);
			        celda.setCellValue(texto);			        									        		        									        									        
			        celda.setCellStyle(estilosLibro);			       		        												        
			        
			        objSaldosBean = new Saldos();
			        
			        objSaldosBean = saldosService.listaSaldosByParam(monedaModeloValor,monedaCuenta,codigoEmpresa,tipoReaseguro);
			        
			        celda = fila.createCell(7);	        					        		        		       		             									    									       									  									        
			        celda.setCellStyle(estilosLibro);									        									   								        							        			        		        									        	
			        
			        if (objSaldosBean != null){
			        
				        celda = fila.createCell(8);			        
				       
				        if(objSaldosBean.getPrimaPagarReaCedido() != 0){
				        celda.setCellValue(objSaldosBean.getPrimaPagarReaCedido());	
				        celda.setCellStyle(estilosLibro2);
				        }else{
				        celda.setCellValue("");	 
				        celda.setCellStyle(estilosLibro);
				        }			        			        			        									
				        	                			        
				        celda = fila.createCell(9);			        
				        
				        if(objSaldosBean.getPrimaCobrarReaAceptado() != 0){
				        celda.setCellValue(objSaldosBean.getPrimaCobrarReaAceptado());			        									
					    celda.setCellStyle(estilosLibro2);		            
				        }else{
				        celda.setCellValue("");			        									
					    celda.setCellStyle(estilosLibro);		            	
				        }
				        
				        
				        celda = fila.createCell(10);			        
				        
				        if(objSaldosBean.getSiniestroCobrarReaAceptado() != 0){
				        celda.setCellValue(objSaldosBean.getSiniestroCobrarReaAceptado());			        									
				        celda.setCellStyle(estilosLibro2);	        		        
				        }else{
				        celda.setCellValue("");			        									
					    celda.setCellStyle(estilosLibro);	        		        
				        }
				        
				        celda = fila.createCell(11);
				        
				        if(objSaldosBean.getSiniestroPagarReaCedido() != 0){
				        celda.setCellValue(objSaldosBean.getSiniestroPagarReaCedido());			        									
					    celda.setCellStyle(estilosLibro2);		                	
				        }else{
				        celda.setCellValue("");			        									
						celda.setCellStyle(estilosLibro);		                	
				        }
				        
				        
				        celda = fila.createCell(12);	        					        		        								    									       									  									        			        
				        
				        if(objSaldosBean.getOtrasCobrarReaCedidos() != 0){
				        	celda.setCellValue(objSaldosBean.getOtrasCobrarReaCedidos());			        									
					        celda.setCellStyle(estilosLibro2);		        	
				        }else{
				        	celda.setCellValue("");			        									
					        celda.setCellStyle(estilosLibro);		        
				        }
				        
				        
				        celda = fila.createCell(13);			        
				        
				        if(objSaldosBean.getOtrasCobrarReaAceptado() != 0){
				        	celda.setCellValue(objSaldosBean.getOtrasCobrarReaAceptado());			        									
					        celda.setCellStyle(estilosLibro2);		        			  	
				        }else{
				        	celda.setCellValue("");			        									
					        celda.setCellStyle(estilosLibro);		        			  
				        }
				              		        
				        
				        celda = fila.createCell(14);	  
				        
				        if(objSaldosBean.getDescuentosComisiones() != 0){
				        	celda.setCellValue(objSaldosBean.getDescuentosComisiones());			        									
					        celda.setCellStyle(estilosLibro2);
				        }else{
				        	celda.setCellValue("");			        									
					        celda.setCellStyle(estilosLibro);	
				        }
				        			        
				        saldoDeudorFormula = 0.0;			        
				        saldoAcreedorFormula = 0.0;
				        
				        celda = fila.createCell(15);
				        
				        //if( (objSaldosBean.getPrimaPagarReaCedido()+objSaldosBean.getSiniestroPagarReaCedido()+objSaldosBean.getOtrasCobrarReaAceptado())<(objSaldosBean.getPrimaCobrarReaAceptado()+objSaldosBean.getSiniestroCobrarReaAceptado()+objSaldosBean.getOtrasCobrarReaCedidos()+objSaldosBean.getDescuentosComisiones()) ){
				        
				        	saldoDeudorFormula =  Math.abs((objSaldosBean.getPrimaCobrarReaAceptado()+objSaldosBean.getSiniestroCobrarReaAceptado()+objSaldosBean.getOtrasCobrarReaCedidos() ));  
				        				   
				        	if(saldoDeudorFormula == null){
				        		saldoDeudorFormula = 0.00;
				        	}
				        	
				        	celda.setCellValue(saldoDeudorFormula);
					        celda.setCellStyle(estilosLibro2);
				        	
				        //}else{
					        /*saldoDeudorFormula=0.0;
				        	celda.setCellValue("");	        					        		        			       		        	        									    									       									  									       
					        celda.setCellStyle(estilosLibro);*/				        	
				        //}
				       			        				        			        
				        celda = fila.createCell(16);	     
				       
				        //if( (objSaldosBean.getPrimaPagarReaCedido()+objSaldosBean.getSiniestroPagarReaCedido()+objSaldosBean.getOtrasCobrarReaAceptado())>(objSaldosBean.getPrimaCobrarReaAceptado()+objSaldosBean.getSiniestroCobrarReaAceptado()+objSaldosBean.getOtrasCobrarReaCedidos()+objSaldosBean.getDescuentosComisiones()) ){
				        	
				        	saldoAcreedorFormula = Math.abs( ( objSaldosBean.getPrimaPagarReaCedido()+objSaldosBean.getSiniestroPagarReaCedido()+objSaldosBean.getOtrasCobrarReaAceptado() ) - ( objSaldosBean.getDescuentosComisiones() ));
				        	
				        	if(saldoAcreedorFormula == null){
				        		saldoAcreedorFormula = 0.00;
				        	}
				        	
				        	celda.setCellValue(saldoAcreedorFormula);
					        celda.setCellStyle(estilosLibro2);
				        /*}else{
				        	saldoAcreedorFormula=0.0;
				        	celda.setCellValue("");	        					        		        			       		        	        									    									       									  									       
					        celda.setCellStyle(estilosLibro);
				        }*/
			        			        			       			        			       
			        }else{
			        celda = fila.createCell(8);	        					        		        			       				        								        									    									       									  									        
				    celda.setCellStyle(estilosLibro);	                
				    celda = fila.createCell(9);	        					        		        
				    celda.setCellStyle(estilosLibro);		            
				    celda = fila.createCell(10);	        					        		        			       									    									       									  									        
				    celda.setCellStyle(estilosLibro);	        		        
				    celda = fila.createCell(11);	        					        		        			       							        									    									       									  									        
				    celda.setCellStyle(estilosLibro);		                
				    celda = fila.createCell(12);	        					        		        								    									       									  									        
				    celda.setCellStyle(estilosLibro);		        
				    celda = fila.createCell(13);	        					        		        			       		        	        									    									       									  									       
				    celda.setCellStyle(estilosLibro);		        			        		        
				    celda = fila.createCell(14);	        					        		        			     									    									       									  									        
				    celda.setCellStyle(estilosLibro);
				    celda = fila.createCell(15);	        					        		        			       		        	        									    									       									  									       
			        celda.setCellStyle(estilosLibro);		        			        		        
			        celda = fila.createCell(16);	        					        		        			     									    									       									  									        
			        celda.setCellStyle(estilosLibro);
			        }
			        
			       
		
	        }
	        
	        if(i == 11){
	        	estiloTexto = libro.createFont();	
	        	XSSFCell celda = fila.createCell(0);        	        
	        	XSSFRichTextString texto;
	        	estilosLibro = libro.createCellStyle();
	        	
		        estilosLibro.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		        estilosLibro.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		        estilosLibro.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		        		        	
		        celda.setCellStyle(estilosLibro);								        		      
		        hoja.setColumnWidth(6, 15000);
		        hoja.setColumnWidth(1, 10000);	
		        
		        celda = fila.createCell(1);
	        	texto = new XSSFRichTextString("");
		        celda.setCellValue(texto);								        
		        estilosLibro = libro.createCellStyle();
		        
		        estiloTexto = libro.createFont();		
	        	estiloTexto.setFontHeightInPoints((short) 8);
		        estiloTexto.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		        estiloTexto.setColor(HSSFColor.BLACK.index);
		        
		        estilosLibro.setAlignment(CellStyle.ALIGN_CENTER);
		        estilosLibro.setVerticalAlignment(CellStyle.ALIGN_FILL);
		       
		        estilosLibro.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);									        
		        estilosLibro.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);					
		        estilosLibro.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);					
		        estilosLibro.setAlignment(CellStyle.ALIGN_CENTER);			        		        
		        estilosLibro.setFont(estiloTexto);	
		        celda.setCellStyle(estilosLibro);								        
		        celda = fila.createCell(2);
		        texto = new XSSFRichTextString("");
		        celda.setCellValue(texto);								        		        								        
		        celda.setCellStyle(estilosLibro);								        
		        celda = fila.createCell(3);
		        texto = new XSSFRichTextString("");
		        celda.setCellValue(texto);				        		        									        									        
		        celda.setCellStyle(estilosLibro);
		        celda = fila.createCell(4);		        
		        texto = new XSSFRichTextString("");
		        celda.setCellValue(texto);			        									        		        									        									        
		        celda.setCellStyle(estilosLibro);			       		        												        
		        celda = fila.createCell(5);	        					        		        		       		             									    									       									  									        
		        celda.setCellStyle(estilosLibro);									        									   								        							        			        		        									        	
		        celda = fila.createCell(6);	        					        		        					        									    									       									  									        
		        celda.setCellStyle(estilosLibro);							      		                
		        celda = fila.createCell(7);	        					        		        			       		     	        									    									       									  									        
		        celda.setCellStyle(estilosLibro);		                
		        celda = fila.createCell(8);	        					        		        			       				        								        									    									       									  									        
		        celda.setCellStyle(estilosLibro);	                
		        celda = fila.createCell(9);	        					        		        
		        celda.setCellStyle(estilosLibro);		            
		        celda = fila.createCell(10);	        					        		        			       									    									       									  									        
		        celda.setCellStyle(estilosLibro);	        		        
		        celda = fila.createCell(11);	        					        		        			       							        									    									       									  									        
		        celda.setCellStyle(estilosLibro);		                
		        celda = fila.createCell(12);	        					        		        								    									       									  									        
		        celda.setCellStyle(estilosLibro);		        
		        celda = fila.createCell(13);	        					        		        			       		        	        									    									       									  									       
		        celda.setCellStyle(estilosLibro);		        			        		        
		        celda = fila.createCell(14);	        					        		        			     									    									       									  									        
		        celda.setCellStyle(estilosLibro);
		        celda = fila.createCell(15);	        					        		        			       		        	        									    									       									  									       
		        celda.setCellStyle(estilosLibro);		        			        		        
		        celda = fila.createCell(16);	        					        		        			     									    									       									  									        
		        celda.setCellStyle(estilosLibro);
	        }
	        
	        if(i == 9){
	            estiloTexto = libro.createFont();	
	        	XSSFCell celda = fila.createCell(0);        	        
	        	XSSFRichTextString texto;
	        	estilosLibro = libro.createCellStyle();
	        	
		        estilosLibro.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		        estilosLibro.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		        estilosLibro.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		        		        	
		        celda.setCellStyle(estilosLibro);								        		      
		        hoja.setColumnWidth(6, 15000);
		        hoja.setColumnWidth(1, 10000);	
		        
		        celda = fila.createCell(1);
	        	texto = new XSSFRichTextString("Nombre del Asegurado");
		        celda.setCellValue(texto);								        
		        estilosLibro = libro.createCellStyle();
		        
		        estiloTexto = libro.createFont();		
	        	estiloTexto.setFontHeightInPoints((short) 8);
		        estiloTexto.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		        estiloTexto.setColor(HSSFColor.BLACK.index);
		        
		        estilosLibro.setAlignment(CellStyle.ALIGN_CENTER);
		        estilosLibro.setVerticalAlignment(CellStyle.ALIGN_FILL);
		       
		        estilosLibro.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);									        
		        estilosLibro.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);					
		        estilosLibro.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);					
		        estilosLibro.setAlignment(CellStyle.ALIGN_CENTER);
		        		        
		        estilosLibro.setFont(estiloTexto);	
		        celda.setCellStyle(estilosLibro);								        
		        celda = fila.createCell(2);
		        texto = new XSSFRichTextString("Número de Póliza");
		        celda.setCellValue(texto);								        		        								        
		        celda.setCellStyle(estilosLibro);									        
		       
		        celda = fila.createCell(3);
		        texto = new XSSFRichTextString("Número de Siniestro");
		        celda.setCellValue(texto);				        		        									        									        
		        celda.setCellStyle(estilosLibro);							        
		        
		        celda = fila.createCell(4);		        
		        texto = new XSSFRichTextString("Documento contable de origen");
		        celda.setCellValue(texto);			        									        		        									        									        
		        celda.setCellStyle(estilosLibro);  
		        
		        celda = fila.createCell(5);	        					        		        		       		             									    									       									  									        		        
		        texto = new XSSFRichTextString("Comprobante de Caja");		        
		        celda.setCellValue(texto);			        									        		        									        									        
		        celda.setCellStyle(estilosLibro);
		        
		        celda = fila.createCell(6);	        					        		        		       		             									    									       									  									        		        
		        texto = new XSSFRichTextString("Descripción");
		        celda.setCellValue(texto);			        									        		        									        									        
		        celda.setCellStyle(estilosLibro);
		        
		        celda = fila.createCell(7);	        					        		        					        									    									       									  									        
		        celda.setCellStyle(estilosLibro);							      		                
		        celda = fila.createCell(8);	        					        		        			       		     	        									    									       									  									        
		        celda.setCellStyle(estilosLibro);		        	        
		        celda = fila.createCell(9);	        					        		        			       				        								        									    									       									  									        
		        celda.setCellStyle(estilosLibro);	                
		        celda = fila.createCell(10);	        					        		        
		        celda.setCellStyle(estilosLibro);		            
		        celda = fila.createCell(11);	        					        		        			       									    									       									  									        
		        celda.setCellStyle(estilosLibro);	        		        
		        celda = fila.createCell(12);	        					        		        			       							        									    									       									  									        
		        celda.setCellStyle(estilosLibro);		                
		        celda = fila.createCell(13);	        					        		        								    									       									  									        
		        celda.setCellStyle(estilosLibro);		                
		        celda = fila.createCell(14);	        					        		        			       		        	        									    									       									  									       
		        celda.setCellStyle(estilosLibro);		        		                
		        celda = fila.createCell(15);	        					        		        			     									    									       									  									        
		        celda.setCellStyle(estilosLibro);
		        celda = fila.createCell(16);	        					        		        			       		        	        									    									       									  									       
		        celda.setCellStyle(estilosLibro);		        		                		       
	        } 
	        
	        if(i == 8){
	        	
	        	XSSFCell celda = fila.createCell(0);
	        	XSSFRichTextString texto = new XSSFRichTextString("(1)");
		        celda.setCellValue(texto);								        
		        estilosLibro = libro.createCellStyle();
		        
		        estiloTexto = libro.createFont();		
	        	estiloTexto.setFontHeightInPoints((short) 8);
		        estiloTexto.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		        estiloTexto.setColor(HSSFColor.BLACK.index);
		       	
		        estilosLibro.setAlignment(CellStyle.ALIGN_CENTER);
		        estilosLibro.setVerticalAlignment(CellStyle.ALIGN_FILL);
		        
		        estilosLibro.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		        estilosLibro.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		        estilosLibro.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		        
		        estilosLibro.setAlignment(CellStyle.ALIGN_CENTER);
		       
		        estilosLibro.setFont(estiloTexto);	
		        celda.setCellStyle(estilosLibro);								        
		      								     									        
		        hoja.addMergedRegion(CellRangeAddress.valueOf("B9:G9"));
		        hoja.setColumnWidth(6, 15000);
		        hoja.setColumnWidth(1, 10000);	
		        
		        celda = fila.createCell(1);
	        	texto = new XSSFRichTextString("(2)");
		        celda.setCellValue(texto);								        
		        estilosLibro = libro.createCellStyle();
		        
		        estiloTexto = libro.createFont();		
	        	estiloTexto.setFontHeightInPoints((short) 8);
		        estiloTexto.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		        estiloTexto.setColor(HSSFColor.BLACK.index);
		        
		        estilosLibro.setAlignment(CellStyle.ALIGN_CENTER);
		        estilosLibro.setVerticalAlignment(CellStyle.ALIGN_FILL);
		       
		        estilosLibro.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);									        
		        estilosLibro.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);								        
		        estilosLibro.setAlignment(CellStyle.ALIGN_CENTER);
		        
		        
		        estilosLibro.setFont(estiloTexto);	
		        celda.setCellStyle(estilosLibro);								        
		        celda = fila.createCell(2);
		        estilosLibro = libro.createCellStyle();
		        estilosLibro.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);		        								        
		        celda.setCellStyle(estilosLibro);	        
		        celda = fila.createCell(3);
		        estilosLibro = libro.createCellStyle();
		        estilosLibro.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);		        									        									        
		        celda.setCellStyle(estilosLibro);									        
		        celda = fila.createCell(4);
		        estilosLibro = libro.createCellStyle();
		        estilosLibro.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		        estilosLibro.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);									        		        									        									        
		        celda.setCellStyle(estilosLibro);
		        celda = fila.createCell(5);
		        estilosLibro = libro.createCellStyle();
		        estilosLibro.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		        estilosLibro.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);									        		        									        									        
		        celda.setCellStyle(estilosLibro);
		        celda = fila.createCell(6);
		        estilosLibro = libro.createCellStyle();
		        estilosLibro.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		        estilosLibro.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);									        		        									        									        
		        celda.setCellStyle(estilosLibro);
		        
		        
		        celda = fila.createCell(7);
	        	texto = new XSSFRichTextString("(3)");
		        celda.setCellValue(texto);								        
		        estilosLibro = libro.createCellStyle();
		        estiloTexto = libro.createFont();		
		        
		        estilosLibro.setAlignment(CellStyle.ALIGN_CENTER);
		        estilosLibro.setVerticalAlignment(CellStyle.ALIGN_FILL);		        		       
		        estilosLibro.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		        estilosLibro.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		        estilosLibro.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		     						        								        									    	
	        	estilosLibro.setWrapText(true);									        
	        	estiloTexto.setFontHeightInPoints((short) 8);
		        estiloTexto.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		        estiloTexto.setColor(HSSFColor.BLACK.index);
		        
		        estilosLibro.setFont(estiloTexto);								       									  									        
		        celda.setCellStyle(estilosLibro);									        									   								        							        	
								        	
		        celda = fila.createCell(8);
		        //hoja.setColumnWidth(7,800);									       
	        	texto = new XSSFRichTextString("(4)");
		        celda.setCellValue(texto);								        
		        estilosLibro = libro.createCellStyle();
		        estiloTexto = libro.createFont();						
		        
		        estilosLibro.setAlignment(CellStyle.ALIGN_CENTER);
		        estilosLibro.setVerticalAlignment(CellStyle.ALIGN_FILL);
		        
		        estilosLibro.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		        estilosLibro.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		        estilosLibro.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		     
		        estilosLibro.setWrapText(true);
		        estilosLibro.setAlignment(CellStyle.ALIGN_CENTER);									        
		        estiloTexto.setFontHeightInPoints((short) 8);
		        estiloTexto.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		        estiloTexto.setColor(HSSFColor.BLACK.index);									       
		        estilosLibro.setFont(estiloTexto);
		        celda.setCellStyle(estilosLibro);									      
		        
		        celda = fila.createCell(9);
	        	texto = new XSSFRichTextString("(5)");
		        celda.setCellValue(texto);								        
		        estilosLibro = libro.createCellStyle();
		        estiloTexto = libro.createFont();						
		        
		        estilosLibro.setAlignment(CellStyle.ALIGN_CENTER);
		        estilosLibro.setVerticalAlignment(CellStyle.ALIGN_FILL);
		        
		        estilosLibro.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		        estilosLibro.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		        estilosLibro.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		     
		        estilosLibro.setWrapText(true);
		        estilosLibro.setAlignment(CellStyle.ALIGN_CENTER);									        
		        estiloTexto.setFontHeightInPoints((short) 8);
		        estiloTexto.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		        estiloTexto.setColor(HSSFColor.BLACK.index);									       
		        estilosLibro.setFont(estiloTexto);
		        celda.setCellStyle(estilosLibro);	
		        
		        celda = fila.createCell(10);
	        	texto = new XSSFRichTextString("(6)");
	        	celda.setCellValue(texto);		
	        	estilosLibro = libro.createCellStyle();
		        estiloTexto = libro.createFont();
		        
		        estilosLibro.setAlignment(CellStyle.ALIGN_CENTER);
		        estilosLibro.setVerticalAlignment(CellStyle.ALIGN_FILL);
		        
		        estilosLibro.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		        estilosLibro.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		        estilosLibro.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		     
		        estilosLibro.setWrapText(true);
		        estilosLibro.setAlignment(CellStyle.ALIGN_CENTER);
		        
		        estiloTexto.setFontHeightInPoints((short) 8);
		        estiloTexto.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		        estiloTexto.setColor(HSSFColor.BLACK.index);
		       
		        estilosLibro.setFont(estiloTexto);
		        celda.setCellStyle(estilosLibro);	
	        		      
		        celda = fila.createCell(11);
	        	texto = new XSSFRichTextString("(7)");
		        celda.setCellValue(texto);								        
		        estilosLibro = libro.createCellStyle();
		        estiloTexto = libro.createFont();
		        
		        estilosLibro.setAlignment(CellStyle.ALIGN_CENTER);
		        estilosLibro.setVerticalAlignment(CellStyle.ALIGN_FILL);
		        
		        estilosLibro.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		        estilosLibro.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		        estilosLibro.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		     
		        estilosLibro.setWrapText(true);
		        estilosLibro.setAlignment(CellStyle.ALIGN_CENTER);
		        
		        estiloTexto.setFontHeightInPoints((short) 8);
		        estiloTexto.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		        estiloTexto.setColor(HSSFColor.BLACK.index);
		       
		        estilosLibro.setFont(estiloTexto);
		        celda.setCellStyle(estilosLibro);	

		        celda = fila.createCell(12);
	        	texto = new XSSFRichTextString("(8)");
		        celda.setCellValue(texto);								        
		        estilosLibro = libro.createCellStyle();
		        estiloTexto = libro.createFont();
		        
		        estilosLibro.setAlignment(CellStyle.ALIGN_CENTER);
		        estilosLibro.setVerticalAlignment(CellStyle.ALIGN_FILL);
		        
		        estilosLibro.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		        estilosLibro.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		        estilosLibro.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		     
		        estilosLibro.setWrapText(true);
		        estilosLibro.setAlignment(CellStyle.ALIGN_CENTER);
		        
		        estiloTexto.setFontHeightInPoints((short) 8);
		        estiloTexto.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		        estiloTexto.setColor(HSSFColor.BLACK.index);
		       
		        estilosLibro.setFont(estiloTexto);
		        celda.setCellStyle(estilosLibro);	
	        		
		        celda = fila.createCell(13);
	        	texto = new XSSFRichTextString("(9)");
		        celda.setCellValue(texto);								        
		        estilosLibro = libro.createCellStyle();
		        estiloTexto = libro.createFont();
		        
		        estilosLibro.setAlignment(CellStyle.ALIGN_CENTER);
		        estilosLibro.setVerticalAlignment(CellStyle.ALIGN_FILL);
		        
		        estilosLibro.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		        estilosLibro.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		        estilosLibro.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		     
		        estilosLibro.setWrapText(true);
		        estilosLibro.setAlignment(CellStyle.ALIGN_CENTER);
		        									       									        
		        estiloTexto.setFontHeightInPoints((short) 8);
		        estiloTexto.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		        estiloTexto.setColor(HSSFColor.BLACK.index);
		       
		        estilosLibro.setFont(estiloTexto);
		        celda.setCellStyle(estilosLibro);	

		        celda = fila.createCell(14);
	        	texto = new XSSFRichTextString("(10)");
	        	celda.setCellValue(texto);		
	        	estilosLibro = libro.createCellStyle();
		        estiloTexto = libro.createFont();
		        
		        estilosLibro.setAlignment(CellStyle.ALIGN_CENTER);
		        estilosLibro.setVerticalAlignment(CellStyle.ALIGN_FILL);
		        
		        estilosLibro.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		        estilosLibro.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		        estilosLibro.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		     
		        estilosLibro.setWrapText(true);
		        estilosLibro.setAlignment(CellStyle.ALIGN_CENTER);
		        
		        estiloTexto.setFontHeightInPoints((short) 8);
		        estiloTexto.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		        estiloTexto.setColor(HSSFColor.BLACK.index);
		       
		        estilosLibro.setFont(estiloTexto);
		        celda.setCellStyle(estilosLibro);	

		        celda = fila.createCell(15);
	        	texto = new XSSFRichTextString("(11)");
	        	celda.setCellValue(texto);		
	        	estilosLibro = libro.createCellStyle();
		        estiloTexto = libro.createFont();
		        
		        estilosLibro.setAlignment(CellStyle.ALIGN_CENTER);
		        estilosLibro.setVerticalAlignment(CellStyle.ALIGN_FILL);
		        
		        estilosLibro.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		        estilosLibro.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		        estilosLibro.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		     
		        estilosLibro.setWrapText(true);
		        estilosLibro.setAlignment(CellStyle.ALIGN_CENTER);
		        
		        estiloTexto.setFontHeightInPoints((short) 8);
		        estiloTexto.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		        estiloTexto.setColor(HSSFColor.BLACK.index);
		       																	       
		        estilosLibro.setFont(estiloTexto);
		        celda.setCellStyle(estilosLibro);	

		        celda = fila.createCell(16);
	        	texto = new XSSFRichTextString("(12)");
	        	celda.setCellValue(texto);		
	        	estilosLibro = libro.createCellStyle();
		        estiloTexto = libro.createFont();
		        
		        estilosLibro.setAlignment(CellStyle.ALIGN_CENTER);
		        estilosLibro.setVerticalAlignment(CellStyle.ALIGN_FILL);
		      
		        estilosLibro.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		        estilosLibro.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		        estilosLibro.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		      
		        estilosLibro.setWrapText(true);
		        estilosLibro.setAlignment(CellStyle.ALIGN_CENTER);
		        
		        estiloTexto.setFontHeightInPoints((short) 8);
		        estiloTexto.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		        estiloTexto.setColor(HSSFColor.BLACK.index);
		       
		        estilosLibro.setFont(estiloTexto);
		        celda.setCellStyle(estilosLibro);		
	
	        }
	        
	        if(i == 7){
	        								        	
	        	XSSFCell celda = fila.createCell(0);
	        	XSSFRichTextString texto = new XSSFRichTextString("FECHA");
		        celda.setCellValue(texto);								        
		        estilosLibro = libro.createCellStyle();
		        
		        estiloTexto = libro.createFont();		
	        	estiloTexto.setFontHeightInPoints((short) 8);
		        estiloTexto.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		        estiloTexto.setColor(HSSFColor.BLACK.index);
		       	
		        estilosLibro.setAlignment(CellStyle.ALIGN_CENTER);
		        estilosLibro.setVerticalAlignment(CellStyle.ALIGN_FILL);
		        		        
		        estilosLibro.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		        estilosLibro.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		        estilosLibro.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
		        
		        estilosLibro.setAlignment(CellStyle.ALIGN_CENTER);
		       
		        estilosLibro.setFont(estiloTexto);	
		        celda.setCellStyle(estilosLibro);								        
						     									        
		        hoja.addMergedRegion(CellRangeAddress.valueOf("B8:G8"));
		        hoja.setColumnWidth(6, 15000);
		        hoja.setColumnWidth(1, 10000);	
		        
		        celda = fila.createCell(1);
	        	texto = new XSSFRichTextString("DETALLE");
		        celda.setCellValue(texto);								        
		        estilosLibro = libro.createCellStyle();
		        
		        estiloTexto = libro.createFont();		
	        	estiloTexto.setFontHeightInPoints((short) 8);
		        estiloTexto.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		        estiloTexto.setColor(HSSFColor.BLACK.index);
		        
		        estilosLibro.setAlignment(CellStyle.ALIGN_CENTER);
		        estilosLibro.setVerticalAlignment(CellStyle.ALIGN_FILL);
		       
		        estilosLibro.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		        estilosLibro.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		        estilosLibro.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
		        
		        
		        estilosLibro.setFont(estiloTexto);	
		        celda.setCellStyle(estilosLibro);								        
		        
		        celda = fila.createCell(2);
		        estilosLibro = libro.createCellStyle();		       
		        estilosLibro.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);									        									        
		        celda.setCellStyle(estilosLibro);
		        									        
		        celda = fila.createCell(3);
		        estilosLibro = libro.createCellStyle();		      
		        estilosLibro.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);									        									        
		        celda.setCellStyle(estilosLibro);
		        							        
		        celda = fila.createCell(4);
		        estilosLibro = libro.createCellStyle();		        
		        estilosLibro.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);									        
		        estilosLibro.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);									        									        
		        celda.setCellStyle(estilosLibro);
		        
		        
		        celda = fila.createCell(5);
		        estilosLibro = libro.createCellStyle();		        
		        estilosLibro.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);									        
		        estilosLibro.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);									        									        
		        celda.setCellStyle(estilosLibro);
		        
		        
		        celda = fila.createCell(6);
		        estilosLibro = libro.createCellStyle();		        
		        estilosLibro.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);									        
		        estilosLibro.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);									        									        
		        celda.setCellStyle(estilosLibro);
        
		        celda = fila.createCell(7);
	        	texto = new XSSFRichTextString("RIESGO");
		        celda.setCellValue(texto);								        
		        estilosLibro = libro.createCellStyle();
		        estiloTexto = libro.createFont();		
		        
		        estilosLibro.setAlignment(CellStyle.ALIGN_CENTER);		        
		        estilosLibro.setVerticalAlignment(CellStyle.ALIGN_CENTER);
		        		       		         		        
		        estilosLibro.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		        estilosLibro.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		        estilosLibro.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
		        									        									        								        									    	
	        	estilosLibro.setWrapText(true);									        
	        	estiloTexto.setFontHeightInPoints((short) 8);
		        estiloTexto.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		        estiloTexto.setColor(HSSFColor.BLACK.index);
		        
		        estilosLibro.setFont(estiloTexto);								       									  									        
		        celda.setCellStyle(estilosLibro);									        									   								        							        	
							        	
		        celda = fila.createCell(8);
		        hoja.setColumnWidth(10,2900);		
		        hoja.setColumnWidth(11,2900);
		        hoja.setColumnWidth(14,2900);
		        
	        	texto = new XSSFRichTextString("PRIMAS POR PAGAR");
		        celda.setCellValue(texto);								        
		        estilosLibro = libro.createCellStyle();
		        estiloTexto = libro.createFont();						
		        
		        estilosLibro.setAlignment(CellStyle.ALIGN_CENTER);		        
		        estilosLibro.setVerticalAlignment(CellStyle.ALIGN_CENTER);
		        		      
		        estilosLibro.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		        estilosLibro.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		        estilosLibro.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
		        								
		        estilosLibro.setWrapText(true);
		        estilosLibro.setAlignment(CellStyle.ALIGN_CENTER);									        
		        estiloTexto.setFontHeightInPoints((short) 8);
		        estiloTexto.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		        estiloTexto.setColor(HSSFColor.BLACK.index);									       
		        estilosLibro.setFont(estiloTexto);
		        celda.setCellStyle(estilosLibro);									      

		        celda = fila.createCell(9);
	        	texto = new XSSFRichTextString("PRIMAS POR COBRAR");
		        celda.setCellValue(texto);								        
		        estilosLibro = libro.createCellStyle();
		        estiloTexto = libro.createFont();						
		        
		        estilosLibro.setAlignment(CellStyle.ALIGN_CENTER);		        
		        estilosLibro.setVerticalAlignment(CellStyle.ALIGN_CENTER);
		        		       
		        estilosLibro.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		        estilosLibro.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		        estilosLibro.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
		        									
		        estilosLibro.setWrapText(true);
		        estilosLibro.setAlignment(CellStyle.ALIGN_CENTER);									        
		        estiloTexto.setFontHeightInPoints((short) 8);
		        estiloTexto.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		        estiloTexto.setColor(HSSFColor.BLACK.index);									       
		        estilosLibro.setFont(estiloTexto);
		        celda.setCellStyle(estilosLibro);	
		        
		        celda = fila.createCell(10);
	        	texto = new XSSFRichTextString("SINIESTROS POR COBRAR");
	        	celda.setCellValue(texto);		
	        	estilosLibro = libro.createCellStyle();
		        estiloTexto = libro.createFont();
		        
		        estilosLibro.setAlignment(CellStyle.ALIGN_CENTER);		        
		        estilosLibro.setVerticalAlignment(CellStyle.ALIGN_CENTER);	       
		        		       
		        estilosLibro.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		        estilosLibro.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		        estilosLibro.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
		        								
		        estilosLibro.setWrapText(true);
		        estilosLibro.setAlignment(CellStyle.ALIGN_CENTER);
		        
		        estiloTexto.setFontHeightInPoints((short) 8);
		        estiloTexto.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		        estiloTexto.setColor(HSSFColor.BLACK.index);
		       
		        estilosLibro.setFont(estiloTexto);
		        celda.setCellStyle(estilosLibro);	

		        celda = fila.createCell(11);
	        	texto = new XSSFRichTextString("SINIESTROS POR PAGAR");
		        celda.setCellValue(texto);								        
		        estilosLibro = libro.createCellStyle();
		        estiloTexto = libro.createFont();
		        
		        estilosLibro.setAlignment(CellStyle.ALIGN_CENTER);		        
		        estilosLibro.setVerticalAlignment(CellStyle.ALIGN_CENTER);
		       
		        estilosLibro.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		        estilosLibro.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		        estilosLibro.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
		        							
		        estilosLibro.setWrapText(true);
		      
		        estiloTexto.setFontHeightInPoints((short) 8);
		        estiloTexto.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		        estiloTexto.setColor(HSSFColor.BLACK.index);
		       
		        estilosLibro.setFont(estiloTexto);
		        celda.setCellStyle(estilosLibro);	

		        celda = fila.createCell(12);
	        	texto = new XSSFRichTextString("OTRAS CUENTAS POR COBRAR");
		        celda.setCellValue(texto);								        
		        estilosLibro = libro.createCellStyle();
		        estiloTexto = libro.createFont();
		        
		        estilosLibro.setAlignment(CellStyle.ALIGN_CENTER);		        
		        estilosLibro.setVerticalAlignment(CellStyle.ALIGN_CENTER);
		      
		        estilosLibro.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		        estilosLibro.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		        estilosLibro.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
		        								
		        estilosLibro.setWrapText(true);
		        estilosLibro.setAlignment(CellStyle.ALIGN_CENTER);
		        
		        estiloTexto.setFontHeightInPoints((short) 8);
		        estiloTexto.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		        estiloTexto.setColor(HSSFColor.BLACK.index);
		       
		        estilosLibro.setFont(estiloTexto);
		        celda.setCellStyle(estilosLibro);	

		        celda = fila.createCell(13);
	        	texto = new XSSFRichTextString("OTRAS CUENTAS POR PAGAR");
		        celda.setCellValue(texto);								        
		        estilosLibro = libro.createCellStyle();
		        estiloTexto = libro.createFont();
		        
		        estilosLibro.setAlignment(CellStyle.ALIGN_CENTER);		        
		        estilosLibro.setVerticalAlignment(CellStyle.ALIGN_CENTER);
		       
		        estilosLibro.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		        estilosLibro.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		        estilosLibro.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
		        								
		        estilosLibro.setWrapText(true);
		        estilosLibro.setAlignment(CellStyle.ALIGN_CENTER);		        
		        estilosLibro.setVerticalAlignment(CellStyle.ALIGN_CENTER);
		        									       									        
		        estiloTexto.setFontHeightInPoints((short) 8);
		        estiloTexto.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		        estiloTexto.setColor(HSSFColor.BLACK.index);
		       
		        estilosLibro.setFont(estiloTexto);
		        celda.setCellStyle(estilosLibro);	

		        celda = fila.createCell(14);
	        	texto = new XSSFRichTextString("DESCUENTOS Y COMISIONES");
	        	celda.setCellValue(texto);		
	        	estilosLibro = libro.createCellStyle();
		        estiloTexto = libro.createFont();
		        
		        estilosLibro.setAlignment(CellStyle.ALIGN_CENTER);
		       
		        estilosLibro.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		        estilosLibro.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		        estilosLibro.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
		        								
		        estilosLibro.setWrapText(true);
		        estilosLibro.setAlignment(CellStyle.ALIGN_CENTER);		        
		        estilosLibro.setVerticalAlignment(CellStyle.ALIGN_CENTER);
		        
		        estiloTexto.setFontHeightInPoints((short) 8);
		        estiloTexto.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		        estiloTexto.setColor(HSSFColor.BLACK.index);
		       
		        estilosLibro.setFont(estiloTexto);
		        celda.setCellStyle(estilosLibro);	

		        celda = fila.createCell(15);
	        	texto = new XSSFRichTextString("SALDO DEUDOR");
	        	celda.setCellValue(texto);		
	        	estilosLibro = libro.createCellStyle();
		        estiloTexto = libro.createFont();
		        
		        estilosLibro.setAlignment(CellStyle.ALIGN_CENTER);		        
		        estilosLibro.setVerticalAlignment(CellStyle.ALIGN_CENTER);
		        
		        estilosLibro.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		        estilosLibro.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		        estilosLibro.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
		        									
		        estilosLibro.setWrapText(true);
		        estilosLibro.setAlignment(CellStyle.ALIGN_CENTER);
		        
		        estiloTexto.setFontHeightInPoints((short) 8);
		        estiloTexto.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		        estiloTexto.setColor(HSSFColor.BLACK.index);
		       																	       
		        estilosLibro.setFont(estiloTexto);
		        celda.setCellStyle(estilosLibro);	
		        		        
		        celda = fila.createCell(16);
	        	texto = new XSSFRichTextString("SALDO ACREEDOR");
	        	celda.setCellValue(texto);		
	        	estilosLibro = libro.createCellStyle();
		        estiloTexto = libro.createFont();
		        
		        estilosLibro.setAlignment(CellStyle.ALIGN_CENTER);		        
		        estilosLibro.setVerticalAlignment(CellStyle.ALIGN_CENTER);
		        
		        estilosLibro.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		        estilosLibro.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		        estilosLibro.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
		       							        
		        estilosLibro.setWrapText(true);
		       
		        estiloTexto.setFontHeightInPoints((short) 8);
		        estiloTexto.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		        estiloTexto.setColor(HSSFColor.BLACK.index);
		       
		        estilosLibro.setFont(estiloTexto);
		        celda.setCellStyle(estilosLibro);				       									        									       		 									        					
	        }								        
	        
		}//INICIOIF2	
	}
	
	public String obtenerNombreReporte(String tipoReporte , String periodoAnioParam, String periodoTrimestre){
		
		/*
		 * 1001	MODELO 1 - DETALLE
		   1002	MODELO 2 - DETALLE
		   1003	MODELO 3 - DETALLE
		   
		 * 1401	1er TRIMESTRE
		   1402	2do TRIMESTRE
		   1403	3ro TRIMESTRE
		   1404	4to TRIMESTRE		   
		*/
		
		String agregadoTrimestre = "";
		String agregarTipoReporte = "";
		
		if(periodoTrimestre.equalsIgnoreCase("1401")){
			agregadoTrimestre ="PrimerTrimestre";
		}else if(periodoTrimestre.equalsIgnoreCase("1402")){
			agregadoTrimestre ="SegundoTrimestre";
		}else if(periodoTrimestre.equalsIgnoreCase("1403")){
			agregadoTrimestre ="TercerTrimestre";
		}else if(periodoTrimestre.equalsIgnoreCase("1404")){
			agregadoTrimestre ="CuartoTrimestre";
		}
				
		if(tipoReporte.equalsIgnoreCase("1001")){
			agregarTipoReporte ="ReporteModeloUnoSBS";
		}else if(tipoReporte.equalsIgnoreCase("1002")){
			agregarTipoReporte ="ReporteModeloDosSBS";
		}else if(tipoReporte.equalsIgnoreCase("1003")){
			agregarTipoReporte ="ReporteModeloTresSBS";
		}
				
						
		String nombreFinal = "";
	
		nombreFinal = agregarTipoReporte+"_"+periodoAnioParam+"_"+agregadoTrimestre+".xlsx";
		
		return nombreFinal;
	}
	
	public void llenarReporte(){
		
		ListaReporte objListaReporteBean = null;
		objListaReporte = new ArrayList<ListaReporte>();
		
		for (int i = 0; i < 6; i++) {
					
			objListaReporteBean = new ListaReporte();
			
			if(i == 0){
				objListaReporteBean.setMonedaCuenta("PEN");
				objListaReporteBean.setMonedaModelo("PEN");
				objListaReporteBean.setTipoReaseguro("Cedido");
					
			}
			
			if(i == 1){
			
			
				objListaReporteBean.setMonedaCuenta("PEN");
				objListaReporteBean.setMonedaModelo("PEN");
				objListaReporteBean.setTipoReaseguro("Recibido");
				
			}
			
			if(i == 2){
			
				objListaReporteBean.setMonedaCuenta("PEN");
				objListaReporteBean.setMonedaModelo("USD");
				objListaReporteBean.setTipoReaseguro("Cedido");
			
			}
			
			if(i == 3){
				
				objListaReporteBean.setMonedaCuenta("PEN");
				objListaReporteBean.setMonedaModelo("USD");
				objListaReporteBean.setTipoReaseguro("Recibido");
				
			}
			
			if(i == 4){
				
				objListaReporteBean.setMonedaCuenta("USD");
				objListaReporteBean.setMonedaModelo("USD");
				objListaReporteBean.setTipoReaseguro("Cedido");
			}
			
			if(i == 5){
				
				objListaReporteBean.setMonedaCuenta("USD");
				objListaReporteBean.setMonedaModelo("USD");
				objListaReporteBean.setTipoReaseguro("Recibido");
			}
			
		
			objListaReporte.add(objListaReporteBean);
		}
				
	}
	

    private List<JournalBeanReportes> ProcesarSuperlista(List<List<JournalBeanReportes>> splistaProcesos2) {
    	
		    List<JournalBeanReportes> retorno = new ArrayList<JournalBeanReportes>();
		    logger.info("Inicio");
		    for (int i = 0; i < splistaProcesos2.size(); i++) {
		    			    			    	
		      List<JournalBeanReportes> aa = splistaProcesos2.get(i);
		      retorno.addAll(aa);
		    }
		    logger.info("Fin");
		    return retorno;
    }
    
    
    public String buscaFirmantes(ValueChangeEvent event) {
		String respuesta = null;
		try {
			log.info("Codigo de Reporte" + event.getNewValue());
			List<FirmanteCargo> lista = new ArrayList<FirmanteCargo>();
			firmanteUno = "";
			firmanteDos = "";
			cargoFirmanteUno = "";
			cargoFirmanteDos = "";
			if (event.getNewValue() != null) {
				lista = reporteSBSModeloUnoDetalleService
						.listafirmanetCargo(event.getNewValue().toString());

				for (int i = 0; i < lista.size(); i++) {
					firmanteUno = lista.get(0).getNomFirmante();
					firmanteDos = lista.get(1).getNomFirmante();
					cargoFirmanteUno = lista.get(0).getNomCargo();
					cargoFirmanteDos = lista.get(1).getNomCargo();
					log.info("Firmante 1:" + firmanteUno);
					log.info("Firmante 2:" + firmanteDos);
					log.info("Cargo 1:" + cargoFirmanteUno);
					log.info("Cargo 2:" + cargoFirmanteDos);

				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = MSJ_ERROR;
		}
		return respuesta;
	}
    
	  
	public String getModeloRepore() {
		return modeloRepore;
	}


	public void setModeloRepore(String modeloRepore) {
		this.modeloRepore = modeloRepore;
	}

	
	public String getCargoFirmanteUno() {
		return cargoFirmanteUno;
	}


	public void setCargoFirmanteUno(String cargoFirmanteUno) {
		this.cargoFirmanteUno = cargoFirmanteUno;
	}


	public String getCargoFirmanteDos() {
		return cargoFirmanteDos;
	}


	public void setCargoFirmanteDos(String cargoFirmanteDos) {
		this.cargoFirmanteDos = cargoFirmanteDos;
	}


	public Date getPeriodoDesde() {
		return periodoDesde;
	}


	public void setPeriodoDesde(Date periodoDesde) {
		this.periodoDesde = periodoDesde;
	}


	public Date getPeriodoHasta() {
		return periodoHasta;
	}


	public void setPeriodoHasta(Date periodoHasta) {
		this.periodoHasta = periodoHasta;
	}


	public BigDecimal getTipoCambio() {
		return tipoCambio;
	}


	public void setTipoCambio(BigDecimal tipoCambio) {
		this.tipoCambio = tipoCambio;
	}



	public String getTipoReporteParametro() {
		return tipoReporteParametro;
	}


	public void setTipoReporteParametro(String tipoReporteParametro) {
		this.tipoReporteParametro = tipoReporteParametro;
	}


	public List<SelectItem> getTipoReporteparametrosItems() {
		return tipoReporteparametrosItems;
	}


	public void setTipoReporteparametrosItems(
			List<SelectItem> tipoReporteparametrosItems) {
		this.tipoReporteparametrosItems = tipoReporteparametrosItems;
	}

	public List<SelectItem> getEstadoReporteparametrosItems() {
		return estadoReporteparametrosItems;
	}

	public void setEstadoReporteparametrosItems(
			List<SelectItem> estadoReporteparametrosItems) {
		this.estadoReporteparametrosItems = estadoReporteparametrosItems;
	}

	public List<SelectItem> getPeriodoReporteparametrosItems() {
		return periodoReporteparametrosItems;
	}

	public void setPeriodoReporteparametrosItems(
			List<SelectItem> periodoReporteparametrosItems) {
		this.periodoReporteparametrosItems = periodoReporteparametrosItems;
	}

	public List<SelectItem> getAnioReporteparametrosItems() {
		return anioReporteparametrosItems;
	}

	public void setAnioReporteparametrosItems(
			List<SelectItem> anioReporteparametrosItems) {
		this.anioReporteparametrosItems = anioReporteparametrosItems;
	}

	public String getFirmanteUno() {
		return firmanteUno;
	}

	public void setFirmanteUno(String firmanteUno) {
		this.firmanteUno = firmanteUno;
	}

	public String getFirmanteDos() {
		return firmanteDos;
	}

	public void setFirmanteDos(String firmanteDos) {
		this.firmanteDos = firmanteDos;
	}

	public String getPeriodoAnio() {
		return periodoAnio;
	}

	public void setPeriodoAnio(String periodoAnio) {
		this.periodoAnio = periodoAnio;
	}

	public String getPeriodoTrimestre() {
		return periodoTrimestre;
	}

	public void setPeriodoTrimestre(String periodoTrimestre) {
		this.periodoTrimestre = periodoTrimestre;
	}

	public List<ReportesGeneradosSBSModeloUnoDetalle> getListaReporte() {
		return listaReporte;
	}

	public void setListaReporte(List<ReportesGeneradosSBSModeloUnoDetalle> listaReporte) {
		this.listaReporte = listaReporte;
	}
	
	public List<Parametro> getListaParametroAnio() {
		return listaParametroAnio;
	}

	public void setListaParametroAnio(List<Parametro> listaParametroAnio) {
		this.listaParametroAnio = listaParametroAnio;
	}

	public List<Parametro> getListaEstadoReporte() {
		return listaEstadoReporte;
	}

	public void setListaEstadoReporte(List<Parametro> listaEstadoReporte) {
		this.listaEstadoReporte = listaEstadoReporte;
	}

	public List<ListaReportesGeneradosBean> getListaReportesGeneradosBean() {
		return listaReportesGeneradosBean;
	}

	public void setListaReportesGeneradosBean(
			List<ListaReportesGeneradosBean> listaReportesGeneradosBean) {
		this.listaReportesGeneradosBean = listaReportesGeneradosBean;
	}

	public String getEstadoReporte() {
		return estadoReporte;
	}

	public void setEstadoReporte(String estadoReporte) {
		this.estadoReporte = estadoReporte;
	}

	public String getCodigoReporte() {
		return codigoReporte;
	}

	public void setCodigoReporte(String codigoReporte) {
		this.codigoReporte = codigoReporte;
	}

	public String getUsuarioReporte() {
		return usuarioReporte;
	}

	public void setUsuarioReporte(String usuarioReporte) {
		this.usuarioReporte = usuarioReporte;
	}

	public List<Parametro> getListaReporteModelo() {
		return listaReporteModelo;
	}

	public void setListaReporteModelo(List<Parametro> listaReporteModelo) {
		this.listaReporteModelo = listaReporteModelo;
	}

	
	
    public SimpleSelection getSelection() {
		return selection;
	}
		
	public void setSelection(SimpleSelection selection) {
		this.selection = selection;
	}
	
	public String getPeriodoAnio2() {
	    return periodoAnio2;
	}
		
	public void setPeriodoAnio2(String periodoAnio2) {
		this.periodoAnio2 = periodoAnio2;
	}
		
	public String getPeriodoTrimestre2() {
		return periodoTrimestre2;
	}
		
	public void setPeriodoTrimestre2(String periodoTrimestre2) {
		this.periodoTrimestre2 = periodoTrimestre2;
	}
	
	public String getTipoReporteParametro2() {
		return tipoReporteParametro2;
	}
	
	public void setTipoReporteParametro2(String tipoReporteParametro2) {
		this.tipoReporteParametro2 = tipoReporteParametro2;
	}	

}
