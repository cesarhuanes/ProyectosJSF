package com.cardif.satelite.reportes.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cardif.framework.controller.BaseController;
import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.configuracion.service.ParametroAutomatService;
import com.cardif.satelite.configuracion.service.SocioService;
import com.cardif.satelite.constantes.Constantes;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.ParametroAutomat;
import com.cardif.satelite.model.Socio;
import com.cardif.satelite.model.reportes.ReporteSBS;
import com.cardif.satelite.model.reportes.SBSPeriodo;
import com.cardif.satelite.reportes.bean.RepAnulacionPrimaSBSBean;
import com.cardif.satelite.reportes.bean.RepConsultaSBSBean;
import com.cardif.satelite.reportes.bean.RepContratoSoatSBSBean;
import com.cardif.satelite.reportes.bean.RepProduccionPrimaSBSBean;
import com.cardif.satelite.reportes.service.RepConsultaSBSService;
import com.cardif.satelite.util.Base64Util;
import com.cardif.satelite.util.SateliteUtil;
import com.cardif.satelite.util.Utilitarios;

@Scope("request")
@Controller("repConsultaSBSController")
public class RepConsultaSBSController extends BaseController
{
	private final Logger logger = Logger.getLogger(RepConsultaSBSController.class);
	
	@Autowired
	private RepConsultaSBSService repConsultaSBSService;
	
	@Autowired
	private ParametroAutomatService parametroService;
	
	@Autowired
	private SocioService socioService;
	
	
	private List<SelectItem> comboListaTipoAnexo;
	private List<SelectItem> comboListaTipoAnexoStep2;
	private List<SelectItem> comboListaAnio;
	private List<SelectItem> comboListaAnioStep2;
	private List<SelectItem> comboListaPeriodo;
	private List<SelectItem> comboListaPeriodoStep2;
	
	private String idTipoAnexo;
	private Integer anio;
	private Long idPeriodo;
	
	private String idTipoAnexoStep2;
	private Integer anioStep2;
	private Long idPeriodoStep2;
	private Double tipoCambioUSD;
	
	private List<RepConsultaSBSBean> listaReporteSBSCabecera;
	private String nroConsultaSBS ="0";
	
	private boolean validarTipoCambioUSD=false;
	
	
	/*
	 * Navegacion de Paginas
	 */
	private boolean panelStep1Flag; 
	private boolean panelStep2Flag;
	private String tituloCabecera;
	private ReporteSBS reporteSBSAux;
	
	private boolean disabledComponentes;
	private boolean disabledBtnGenerar;
	private boolean shownSubBotones;
	private boolean btntipocambio;
	
	
	
	@PostConstruct
	@Override
	public String inicio()
	{
		if (logger.isDebugEnabled()) {logger.debug("+inicio()");}
		String respuesta = null;
		
		if (!tieneAcceso())
		{
			if (logger.isDebugEnabled()) {logger.debug("No cuenta con los accesos necesarios.");}
			return "accesoDenegado";
		}
		
		try
		{
			this.comboListaTipoAnexo = new ArrayList<SelectItem>();
			this.comboListaTipoAnexoStep2 = new ArrayList<SelectItem>();
			this.comboListaAnio = new ArrayList<SelectItem>();
			this.comboListaAnioStep2 = new ArrayList<SelectItem>();
			this.comboListaPeriodo = new ArrayList<SelectItem>();
			this.comboListaPeriodoStep2 = new ArrayList<SelectItem>();
			
			this.listaReporteSBSCabecera = new ArrayList<RepConsultaSBSBean>();
			
			this.idTipoAnexo = null;
			this.anio = null;
			this.idPeriodo = null;
			this.nroConsultaSBS ="0";
			
			
			//******************************************************************************
			//************************ CARGAS LOS VALORES INiCIALES ************************
			//******************************************************************************
			if (logger.isInfoEnabled()) {logger.info("Buscando la lista de TIPOS DE ANEXOS.");}
			List<ParametroAutomat> listaTipoAnexo = parametroService.buscarPorCodParam(Constantes.COD_PARAM_SBS_TIPO_ANEXO);
			this.comboListaTipoAnexo.add(new SelectItem("", "- Seleccione -"));
			this.comboListaTipoAnexoStep2.add(new SelectItem("", "- Seleccione -"));
			for (ParametroAutomat tipoAnexo : listaTipoAnexo)
			{
				if (logger.isDebugEnabled()) {logger.debug("TIPO_ANEXO ==> COD_VALOR: " + tipoAnexo.getCodValor() + " - NOMBRE_VALOR: " + tipoAnexo.getNombreValor());}
				this.comboListaTipoAnexo.add(new SelectItem(tipoAnexo.getCodValor(), tipoAnexo.getNombreValor()));
				this.comboListaTipoAnexoStep2.add(new SelectItem(tipoAnexo.getCodValor(), tipoAnexo.getNombreValor()));
			}
			
			
			if (logger.isInfoEnabled()) {logger.info("Generando lista de ANIOS.");}
			ParametroAutomat paramNroUPeriodos = parametroService.obtener(Constantes.COD_PARAM_NRO_ULT_PERIODOS, Constantes.COD_PARAM_NRO_ULT_PERIODOS_VALOR);
			
			List<Integer> listaPeriodos = SateliteUtil.obtenerUltimosPeriodos(Integer.valueOf(paramNroUPeriodos.getNombreValor()));
			this.comboListaAnio.add(new SelectItem("", "- Seleccione -"));
			this.comboListaAnioStep2.add(new SelectItem("", "- Seleccione -"));
			for (Integer anioPeriodo : listaPeriodos)
			{
				if (logger.isDebugEnabled()) {logger.debug("PERIODO ==> VALOR: " + anioPeriodo);}
				this.comboListaAnio.add(new SelectItem(anioPeriodo, anioPeriodo.toString()));
				this.comboListaAnioStep2.add(new SelectItem(anioPeriodo, anioPeriodo.toString()));
			}
			
			
			/*
			 * Colocando el ComboBox de ANIO en el anio actual
			 */
			this.anio = Calendar.getInstance().get(Calendar.YEAR);
			
			
			/* Agregar el valor inicial para el ComboBox de Periodos */
			this.comboListaPeriodo.add(new SelectItem("", "- Seleccione -"));
			
			
			/* Iniciando en la PAGINA 1 */
			this.panelStep1Flag = true;
			this.panelStep2Flag = false;
			
			this.tituloCabecera = null;
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
	
	public String buscarPeriodo(ValueChangeEvent event)
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		String respuesta = null;
		
		try
		{
			this.comboListaPeriodo = new ArrayList<SelectItem>();
			this.comboListaPeriodo.add(new SelectItem("", "- Seleccione -"));
			
			if (null != event.getNewValue())
			{
				if (logger.isDebugEnabled()) {logger.debug("Buscar periodos relacionados al TIPO DE ANEXO: " + (String) event.getNewValue());}
				List<SBSPeriodo> listaSBSPeriodo = repConsultaSBSService.buscarPorTipoAnexo((String) event.getNewValue());
				
				if (logger.isDebugEnabled()) {logger.debug("Se encontraron [" + (null != listaSBSPeriodo ? listaSBSPeriodo.size() : 0)+ "] periodos.");}
				for (SBSPeriodo sbsPeriodo : listaSBSPeriodo)
				{
					this.comboListaPeriodo.add(new SelectItem(sbsPeriodo.getId(), sbsPeriodo.getNombrePeriodo()));
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
	} //buscarPeriodo
	
	public String buscarPeriodoStep2(ValueChangeEvent event)
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		String respuesta = null;
		btntipocambio=false;
		try
		{
			this.comboListaPeriodoStep2 = new ArrayList<SelectItem>();
			//this.comboListaPeriodoStep2.add(new SelectItem("", "- Seleccione -"));
			
			if (null != event.getNewValue())
			{
				if (logger.isDebugEnabled()) {logger.debug("Buscar periodos relacionados al TIPO DE ANEXO: " + (String) event.getNewValue());}
				List<SBSPeriodo> listaSBSPeriodo = repConsultaSBSService.buscarPorTipoAnexo((String) event.getNewValue());
				
				if (logger.isDebugEnabled()) {logger.debug("Se encontraron [" + (null != listaSBSPeriodo ? listaSBSPeriodo.size() : 0)+ "] periodos.");}
				for (SBSPeriodo sbsPeriodo : listaSBSPeriodo)
				{
					this.comboListaPeriodoStep2.add(new SelectItem(sbsPeriodo.getId(), sbsPeriodo.getNombrePeriodo()));
				}
				
				if(event.getNewValue().toString().equals(Constantes.REPORTE_SBS_PRODUCCION_PRIMAS)){
					logger.info("Combo 2: "+event.getNewValue().toString());
					validarTipoCambioUSD = true;
					btntipocambio=true;
				}else{
					validarTipoCambioUSD = false;
					btntipocambio=false;
				}
				
				tipoCambioUSD = null;
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
	} //buscarPeriodo
	
	public String procesarConsulta()
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		String respuesta = null;
		
		try
		{
			if (logger.isDebugEnabled())
			{
				logger.debug("################ PARAMETROS DE LA BUSQUEDA ################" + 
						"\nTIPO_ANEXO: " + this.idTipoAnexo + "\tANIO: " + this.anio + "\tPERIODO: " + this.idPeriodo);
			}
			
			this.listaReporteSBSCabecera = repConsultaSBSService.buscarReporteSBSCabecera(this.idTipoAnexo, this.anio, this.idPeriodo);
			if (logger.isInfoEnabled()) {logger.info("Se encontraron [" + (null != listaReporteSBSCabecera ? listaReporteSBSCabecera.size() : 0) + "] registros para el reporte.");}
		
			this.nroConsultaSBS = String.valueOf(listaReporteSBSCabecera.size());
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
	} //procesarConsulta
	
	public String descargarArchivoAdjunto()
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		String respuesta = null;
		
		FacesContext context = FacesContext.getCurrentInstance();
		Long pkReporteSBS = Long.valueOf(context.getExternalContext().getRequestParameterMap().get("idSBS"));
		String nombreArchivo =  context.getExternalContext().getRequestParameterMap().get("nombreArchivoSBS");
		logger.info("nombreArchivo: "+nombreArchivo);
		
		try
		{
			String archivoAdjunto = repConsultaSBSService.obtenerArchivoAdjuntoSBS(pkReporteSBS);
			
			
			if (StringUtils.isNotBlank(archivoAdjunto))
			{
				if (logger.isDebugEnabled()) {logger.debug("Generando archivo en HTTP Servlet Response.");}
				byte[] reporteBytes = Base64Util.getInstance().convertStringToBase64(archivoAdjunto);
				logger.info("reporteBytes: "+reporteBytes);
				
				ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
						
				HttpServletResponse response = (HttpServletResponse) contexto.getResponse();
				response.addHeader("Content-Disposition", "attachment;filename=" + nombreArchivo);
				response.setContentType("text/plain");
				
				ServletOutputStream sos = response.getOutputStream();
				
				sos.write(reporteBytes);
				sos.flush();
				sos.close();
                
			    FacesContext.getCurrentInstance().responseComplete();
			    if (logger.isDebugEnabled()) {logger.debug("Exportacion culminada.");}
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
	} //descargarArchivoAdjunto
	
	public String cargarGenerarReporte()
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		String respuesta = null;
		
		try
		{
			this.idTipoAnexoStep2 = null;
			this.anioStep2 = null;
			this.idPeriodoStep2 = null;
			this.tipoCambioUSD = null;
			
			/*
			 * Colocando el ComboBox de ANIO en el anio actual
			 */
			this.anioStep2 = Calendar.getInstance().get(Calendar.YEAR);
			
			
			/* Agregar el valor inicial para el ComboBox de Periodos */
			//this.comboListaPeriodoStep2.add(new SelectItem("", "- Seleccione -"));
			
			this.reporteSBSAux = null;
			
			/* Ocultar los sub botones de la PAGINA 2 */
			this.shownSubBotones = false;
			
			/* Cambiando a la PAGINA 2 */
			this.panelStep2Flag = true;
			this.panelStep1Flag = false;
			
			/* Agregando adicional al titulo de cabecera */
			this.tituloCabecera = "> Generar Reporte SBS";
			
			/*
			 * Habilitar componentes
			 * Habilitar boton 'Generar'
			 * Ocultar los botones 'Exportar' y 'Confirmar SBS'
			 */
			this.disabledComponentes = false;
			this.disabledBtnGenerar = false;
			this.shownSubBotones = false;
			this.btntipocambio=false;

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
	} //cargarGenerarReporte
	
	public String procesoGenerarReporte()
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		String respuesta = null;
		
		try
		{
			if (logger.isDebugEnabled())
			{
				logger.debug("\n################ PARAMETROS ################" + 
						"\nTIPO_ANEXO: " + this.idTipoAnexoStep2 + 
						"\nANIO: " + this.anioStep2 + 
						"\nPERIODO: " + this.idPeriodoStep2 + 
						"\nTIPO_CAMBIO: " + this.tipoCambioUSD);
			}
			
			
			boolean existe = repConsultaSBSService.verificarExisteReporte(this.idTipoAnexoStep2, this.anioStep2, this.idPeriodoStep2);
			if (existe)
			{
				throw new SyncconException(ErrorConstants.COD_ERROR_SBS_ANEXO_EXISTE);
			}
			
			
			String fecActual = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
			String rutaTemp = null;
			
			Object objetoReporteList = null;
			if (this.idTipoAnexoStep2.equalsIgnoreCase(Constantes.SBS_TIPO_ANEXO_PROD_PRIMAS))
			{
				List<RepProduccionPrimaSBSBean> listaProduccionPrimaSBS = repConsultaSBSService.extraerSBSProduccionPrimas(this.anioStep2, this.idPeriodoStep2, this.tipoCambioUSD);
				
				if (null == listaProduccionPrimaSBS || 0 == listaProduccionPrimaSBS.size())
				{
					throw new SyncconException(ErrorConstants.COD_ERROR_REP_SBS_ES_11A_SIN_FILAS);
				}
				objetoReporteList = listaProduccionPrimaSBS;
				
				rutaTemp = System.getProperty("java.io.tmpdir") + Constantes.SBS_TIPO_ANEXO_PROD_PRIMAS + fecActual + ".068";
				logger.info("rutaTemp: "+rutaTemp);
				generarReporteProduccionPrimas(listaProduccionPrimaSBS, rutaTemp);
				
				if (logger.isInfoEnabled()) {logger.info("Se genero el reporte de PRODUCCION DE PRIMAS. RutaTemp: " + rutaTemp);}
			}
			else if (this.idTipoAnexoStep2.equalsIgnoreCase(Constantes.SBS_TIPO_ANEXO_ANUL_PRIMAS))
			{
				List<RepAnulacionPrimaSBSBean> listaAnulacionPrimaSBS = repConsultaSBSService.extraerSBSAnulacionPrimas(this.anioStep2, this.idPeriodoStep2, this.tipoCambioUSD);
				
				if (null == listaAnulacionPrimaSBS || 0 == listaAnulacionPrimaSBS.size())
				{
					throw new SyncconException(ErrorConstants.COD_ERROR_REP_SBS_ES_11C_SIN_FILAS);
				}
				objetoReporteList = listaAnulacionPrimaSBS;
				
				rutaTemp = System.getProperty("java.io.tmpdir") + Constantes.SBS_TIPO_ANEXO_ANUL_PRIMAS + fecActual + ".068";
				generarReporteAnulacionPrimas(listaAnulacionPrimaSBS, rutaTemp);
				
				if (logger.isInfoEnabled()) {logger.info("Se genero el reporte de ANULACIIN DE PRIMAS. RutaTemp: " + rutaTemp);}
			}
			else if (this.idTipoAnexoStep2.equalsIgnoreCase(Constantes.SBS_TIPO_ANEXO_CONTRATO_SOAT))
			{
				List<RepContratoSoatSBSBean> listaContratosComercioSBS = repConsultaSBSService.extraerSBSContratosComercioSOAT(anio, this.idPeriodoStep2, this.tipoCambioUSD);
				
				if (null == listaContratosComercioSBS || 0 == listaContratosComercioSBS.size())
				{
					throw new SyncconException(ErrorConstants.COD_ERROR_REP_SBS_S_18_SIN_FILAS);
				}
				objetoReporteList = listaContratosComercioSBS;
				
				List<RepContratoSoatSBSBean> listaAgrupadaContratos = agruparReporteContratos(listaContratosComercioSBS);
				
				rutaTemp = System.getProperty("java.io.tmpdir") + Constantes.SBS_TIPO_ANEXO_CONTRATO_SOAT + fecActual + ".077";
				generarReporteContratosSOAT(listaAgrupadaContratos, rutaTemp);
				
				if (logger.isInfoEnabled()) {logger.info("Se genero el reporte de CONTRATOS DE COMERCIALIZACION SOAT. RutaTemp: " + rutaTemp);}
			}
			else
			{
				throw new SyncconException(ErrorConstants.COD_ERROR_REP_SBS_TIP_ANEXO_INDEFINIDO);
			}
			
			byte[] reportInBytes = Base64Util.getInstance().getBytesFromFile(new File(rutaTemp));
			if (logger.isDebugEnabled()) {logger.debug("Se convirtio el reporte en BYTES: bytes: " + reportInBytes + " size: " + (null != reportInBytes ? reportInBytes.length : 0));}
			
			
			if (logger.isDebugEnabled()) {logger.debug("Guardando el ANEXO del tipo [" + this.idTipoAnexoStep2 + "] del anio [" + this.anioStep2 + "] del periodo [" + this.idPeriodoStep2 + "]");}
			this.reporteSBSAux = repConsultaSBSService.guardarDatosReporte(this.idTipoAnexoStep2, this.anioStep2, this.idPeriodoStep2, this.tipoCambioUSD, reportInBytes, objetoReporteList);
			
			if (logger.isInfoEnabled()) {logger.info("Se registro el objeto ReporteSBS (" + (null != reporteSBSAux ? reporteSBSAux.getId() : null) + ") en la DB.");}
			
			
			/*
			 * Deshabilitar componentes
			 */
			this.disabledComponentes = true;
			this.disabledBtnGenerar = true;
			this.shownSubBotones = true;
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
			logger.error("ex: " + ExceptionUtils.getStackTrace(e));
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = ErrorConstants.MSJ_ERROR;
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return respuesta;
	} //procesoGenerarReporte
	
	public String exportarReporte()
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		String respuesta = null;
		
		try
		{
			if (logger.isInfoEnabled()) {logger.info("Extrayendo el reporte SBS, pk: " + this.reporteSBSAux.getId());}
			
			if (null != this.reporteSBSAux && StringUtils.isNotBlank(reporteSBSAux.getArchivoAdjuntoExportar()))
			{
				byte[] reporteBytes = Base64Util.getInstance().convertStringToBase64(reporteSBSAux.getArchivoAdjuntoExportar());
				
				ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
				
				HttpServletResponse response = (HttpServletResponse) contexto.getResponse();
				response.addHeader("Content-Disposition", "attachment;filename=" + reporteSBSAux.getNombreArchivo());
				response.setContentType("text/plain");
				
				ServletOutputStream sos = response.getOutputStream();
				sos.write(reporteBytes);
				sos.flush();
				sos.close();
                
			    FacesContext.getCurrentInstance().responseComplete();
			    if (logger.isDebugEnabled()) {logger.debug("Exportacion culminada.");}
			}
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
	} //exportarReporte
	
	public String confirmarReporte()
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		String respuesta = null;
		
		try
		{
			repConsultaSBSService.confirmarReporteSBS(reporteSBSAux.getId());
			
			if (logger.isInfoEnabled()) {logger.info("Se confirmo el REPORTE SBS correctamente.");}
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
	} //confirmarReporte
	
	public String volverPantallaPrincipal()
	{
		if (logger.isDebugEnabled()) {logger.debug("Inicio");}
		
		this.panelStep1Flag = true;
		this.panelStep2Flag = false;
		
		this.tituloCabecera = null;
		
		if (logger.isDebugEnabled()) {logger.debug("Fin");}
		return null;
	} //volverPantallaPrincipal
	
	private void generarReporteProduccionPrimas(List<RepProduccionPrimaSBSBean> lista, String rutaTemp) throws Exception
	{
		FileOutputStream fos = new FileOutputStream(rutaTemp);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		for (RepProduccionPrimaSBSBean item : lista)
		{
			String codigoFila = String.format("%-6s", null == item.getCodigoFila() ? "" : item.getCodigoFila());
			String fechaEmision = String.format("%-8s", null == item.getFechaEmision() ? "" : sdf.format(item.getFechaEmision()));
			String nroPoliza = String.format("%-15s", StringUtils.isBlank(item.getNroPoliza()) ? "" : String.format("%15d", Long.valueOf(item.getNroPoliza())).replace(" ", "0"));
			String nroCertificado = String.format("%-15s", StringUtils.isBlank(item.getNroCertificado()) ? "" : String.format("%15d", Long.valueOf(item.getNroCertificado())).replace(" ", "0"));
			String placa = String.format("%-10s", StringUtils.isBlank(item.getPlaca()) ? "" : item.getPlaca().replace("-", "").replace(" ", "").trim());
			String ubigeo = String.format("%-6s", StringUtils.isBlank(item.getUbigeo()) ? "" : String.format("%-6d", Integer.valueOf(item.getUbigeo())  ).replace(" ", "0"));
			String clase = String.format("%-4s", StringUtils.isBlank(item.getClaseVehiculo()) ? "" : item.getClaseVehiculo());
			String uso = String.format("%-4s", StringUtils.isBlank(item.getUsoVehiculo()) ? "" : item.getUsoVehiculo());
			String nroAsientos = String.format("%-2s", null == item.getNroAsientos() ? "" : item.getNroAsientos());
//			String pComercialBruta = String.format("%-15s", null == item.getPrimaComercialBruta() ? "" : SateliteUtil.formatNumber(item.getPrimaComercialBruta()));
			String pComercialBruta = String.format("%-15s", Utilitarios.formateoMonto(item.getPrimaComercialBruta()));
//			String derechoEmision = String.format("%-15s", null == item.getDerechoEmision() ? "" : SateliteUtil.formatNumber(item.getDerechoEmision()));
			String derechoEmision = String.format("%-15s", Utilitarios.formateoMonto(item.getDerechoEmision()));
//			String pComercial = String.format("%-15s", null == item.getPrimaComercial() ? "" : SateliteUtil.formatNumber(item.getPrimaComercial()));
			String pComercial = String.format("%-15s", Utilitarios.formateoMonto(item.getPrimaComercial()));
//			String pComercialCobrada = String.format("%-15s", null == item.getPrimaComercialCobrada() ? "" : SateliteUtil.formatNumber(item.getPrimaComercialCobrada()));
			String pComercialCobrada = String.format("%-15s", Utilitarios.formateoMonto(item.getPrimaComercialCobrada()));
//			String pComercialCobrar = String.format("%-15s", null == item.getPrimaComercialCobrar() ? "" : SateliteUtil.formatNumber(item.getPrimaComercialCobrar()));
			String pComercialCobrar = String.format("%-15s", Utilitarios.formateoMonto(item.getPrimaComercialCobrar()));
//			String comisiones = String.format("%-15s", null == item.getComisiones() ? "" : SateliteUtil.formatNumber(item.getComisiones()));
			String comisiones = String.format("%-15s", Utilitarios.formateoMonto(item.getComisiones()));
//			String gAdministracion = String.format("%-15s", null == item.getGastoAdministracion() ? "" : SateliteUtil.formatNumber(item.getGastoAdministracion()));
			String gAdministracion = String.format("%-15s", Utilitarios.formateoMonto(item.getGastoAdministracion()));
//			String recargoComercial = String.format("%-15s", null == item.getRecargoComercial() ? "" : SateliteUtil.formatNumber(item.getRecargoComercial()));
			String recargoComercial = String.format("%-15s", Utilitarios.formateoMonto(item.getRecargoComercial()));
//			String otrosRecargos = String.format("%-15s", null == item.getOtrosRecargos() ? "" : SateliteUtil.formatNumber(item.getOtrosRecargos()));
			String otrosRecargos = String.format("%-15s", Utilitarios.formateoMonto(item.getOtrosRecargos()));
			String fecIniVigencia = String.format("%-8s", null == item.getFechaIniVigencia() ? "" : sdf.format(item.getFechaIniVigencia()));
			String fecFinVigencia = String.format("%-8s", null == item.getFechaFinVigencia() ? "" : sdf.format(item.getFechaFinVigencia()));
//			String fondoCompensacion = String.format("%-15s", null == item.getFondoCompensacion() ? "" : SateliteUtil.formatNumber(item.getFondoCompensacion()));
			String fondoCompensacion = String.format("%-15s", Utilitarios.formateoMonto(item.getFondoCompensacion()));
			
			
			String linea = Utilitarios.verificarLongitudCampo(codigoFila, 6).
						concat(Utilitarios.verificarLongitudCampo(fechaEmision, 8)).
						concat(Utilitarios.verificarLongitudCampo(nroPoliza, 15)).
						concat(Utilitarios.verificarLongitudCampo(nroCertificado, 15)).
						concat(Utilitarios.verificarLongitudCampo(placa, 10)).
						concat(Utilitarios.verificarLongitudCampo(ubigeo, 6)).
						concat(Utilitarios.verificarLongitudCampo(clase, 4)).
						concat(Utilitarios.verificarLongitudCampo(uso, 4)).
					    concat(Utilitarios.verificarLongitudCampo(nroAsientos, 2)).
					    concat(Utilitarios.verificarLongitudCampo(pComercialBruta, 15)).
					    concat(Utilitarios.verificarLongitudCampo(derechoEmision, 15)).
					    concat(Utilitarios.verificarLongitudCampo(pComercial, 15)).
					    concat(Utilitarios.verificarLongitudCampo(pComercialCobrada, 15)).
					    concat(Utilitarios.verificarLongitudCampo(pComercialCobrar, 15)).
					    concat(Utilitarios.verificarLongitudCampo(comisiones, 15)).
					    concat(Utilitarios.verificarLongitudCampo(gAdministracion, 15)).
					    concat(Utilitarios.verificarLongitudCampo(recargoComercial, 15)).
					    concat(Utilitarios.verificarLongitudCampo(otrosRecargos, 15)).
					    concat(Utilitarios.verificarLongitudCampo(fecIniVigencia, 8)).
					    concat(Utilitarios.verificarLongitudCampo(fecFinVigencia, 8)).
					    concat(Utilitarios.verificarLongitudCampo(fondoCompensacion, 15));
			if (logger.isDebugEnabled()) {logger.debug("-->Linea: " + linea);}
			
			bw.write(linea);
			bw.newLine();
		}
		bw.close();
	} //generarReporteProdPrimas
	
	private void generarReporteAnulacionPrimas(List<RepAnulacionPrimaSBSBean> lista, String rutaTemp) throws Exception
	{
		FileOutputStream fos = new FileOutputStream(rutaTemp);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		for (RepAnulacionPrimaSBSBean item : lista)
		{
			String codigoFila = String.format("%-6s", null == item.getCodigoFila() ? "" : item.getCodigoFila());
			String fecRegAnulacion = String.format("%-8s", null == item.getFecRegistroAnulacion() ? "" : sdf.format(item.getFecRegistroAnulacion()));
			String fecAnulacion = String.format("%-8s", null == item.getFecAnulacion() ? "" : sdf.format(item.getFecAnulacion()));
			String nroPoliza = String.format("%-15s", StringUtils.isBlank(item.getNroPoliza()) ? "" : String.format("%15d", Long.valueOf(item.getNroPoliza())).replace(" ", "0"));
			String nroCertificado = String.format("%-15s", StringUtils.isBlank(item.getNroCertificado()) ? "" : String.format("%15d", Long.valueOf(item.getNroCertificado())).replace(" ", "0"));
			String placa = String.format("%-10s", StringUtils.isBlank(item.getPlaca()) ? "" : item.getPlaca().replace("-", "").replace(" ", "").trim());
//			String pComercialBruta = String.format("%-15s", null == item.getPrimaComercialBruta() ? "" : SateliteUtil.formatNumber(item.getPrimaComercialBruta()));
			String pComercialBruta = String.format("%-15s", Utilitarios.formateoMonto(item.getPrimaComercialBruta()));
//			String pComercial = String.format("%-15s", null == item.getPrimaComercial() ? "" : SateliteUtil.formatNumber(item.getPrimaComercial()));
			String pComercial = String.format("%-15s", Utilitarios.formateoMonto(item.getPrimaComercial()));
//			String montoFondoSOAT = String.format("%-15s", null == item.getMontoFondoSOAT() ? "" : SateliteUtil.formatNumber(item.getPrimaComercial()));
			String montoFondoSOAT = String.format("%-15s", Utilitarios.formateoMonto(item.getMontoFondoSOAT()));
			String fecIniVigencia = String.format("%-8s", null == item.getFechaIniVigencia() ? "" : sdf.format(item.getFechaIniVigencia()));
			
			
			String linea = Utilitarios.verificarLongitudCampo(codigoFila, 6).
						   concat(Utilitarios.verificarLongitudCampo(fecRegAnulacion, 8)).
						   concat(Utilitarios.verificarLongitudCampo(fecAnulacion, 8)).
						   concat(Utilitarios.verificarLongitudCampo(nroPoliza, 15)).
						   concat(Utilitarios.verificarLongitudCampo(nroCertificado, 15)).
						   concat(Utilitarios.verificarLongitudCampo(placa, 10)).
						   concat(Utilitarios.verificarLongitudCampo(pComercialBruta, 15)).
						   concat(Utilitarios.verificarLongitudCampo(pComercial, 15)).
						   concat(Utilitarios.verificarLongitudCampo(montoFondoSOAT, 15)).
						   concat(Utilitarios.verificarLongitudCampo(fecIniVigencia, 8));
			if (logger.isDebugEnabled()) {logger.debug("-->Linea: " + linea);}
			
			bw.write(linea);
			bw.newLine();
		}
		bw.close();
	} //generarReporteAnulPrimas
	
	private List<RepContratoSoatSBSBean> agruparReporteContratos(List<RepContratoSoatSBSBean> lista) throws Exception
	{
		List<RepContratoSoatSBSBean> outputList = null;
		
		try
		{
			List<Socio> listaSocios = socioService.buscarSociosPorGrupoComercio(Constantes.SOC_GRUPO_COMERCIO_ACTIVO);
			if (logger.isInfoEnabled()) {logger.info("Obteniendo los SOCIOS que pertenecen al GRUPO DE COMERCIO.");}
			
			outputList = new ArrayList<RepContratoSoatSBSBean>();
			
			Integer codigoFila = 1;
			for (Socio socio : listaSocios)
			{
				Long idSocio = socio.getId();
				
				Integer nroAsegurados = 0;
				Double montoPrimas = 0D;
				boolean existe = false;
				
				for (RepContratoSoatSBSBean bean : lista)
				{
					if (bean.getIdSocio().equals(idSocio))
					{
						nroAsegurados++;
						montoPrimas += bean.getMontoPrimas();
						
						existe = true;
					}
				}
				
				if (existe)
				{
					RepContratoSoatSBSBean newBean = new RepContratoSoatSBSBean();
					
					newBean.setCodigoFila(codigoFila);
					newBean.setNombreEmpresa(socio.getNombreSocio());
					newBean.setFecFirmaContrato(socio.getFechaContrato());
					newBean.setMontoPrimas(montoPrimas);
					newBean.setNroAsegurados(nroAsegurados);
					
					if (logger.isDebugEnabled()) {logger.debug("Agregando el registro del SOCIO: " + newBean.getNombreEmpresa() + " con Nro. Aseg: " + newBean.getNroAsegurados() + " con Monto Primas: " + newBean.getMontoPrimas());}
					outputList.add(newBean);
					
					codigoFila++;
				}
			} //for
		}
		catch (SyncconException e)
		{
			logger.error("SyncconException() - " + ErrorConstants.ERROR_SYNCCON + e.getMessageComplete());
			throw e;
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			throw e;
		}
		return outputList;
	} //agruparReporteContratos
	
	private void generarReporteContratosSOAT(List<RepContratoSoatSBSBean> lista, String rutaTemp) throws Exception
	{
		FileOutputStream fos = new FileOutputStream(rutaTemp);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		for (RepContratoSoatSBSBean item : lista)
		{
			String codigoFila = String.format("%-6s", null == item.getCodigoFila() ? "" : item.getCodigoFila());
			String nombreEmpresa = String.format("%-100s", StringUtils.isBlank(item.getNombreEmpresa()) ? "" : item.getNombreEmpresa().toUpperCase());
			String fecFirmaContrato = String.format("%-8s", null == item.getFecFirmaContrato() ? "" : sdf.format(item.getFecFirmaContrato()));
			String nroAsegurados = String.format("%-18s", null == item.getNroAsegurados() ? String.format("%18d", 0).replace(" ", "0") : String.format("%18d", item.getNroAsegurados()).replace(" ", "0"));
//			String montoPrimas = String.format("%-18s", null == item.getMontoPrimas() ? "" : String.format("%18d", Long.valueOf(Math.round(item.getMontoPrimas())) ).replace(" ", "0"));
			String montoPrimas = String.format("%-18s", Utilitarios.formateoMonto(item.getMontoPrimas()));
			
			
			String linea = Utilitarios.verificarLongitudCampo(codigoFila, 6).
						   concat(Utilitarios.verificarLongitudCampo(nombreEmpresa, 100)).
						   concat(Utilitarios.verificarLongitudCampo(fecFirmaContrato, 8)).
						   concat(Utilitarios.verificarLongitudCampo(nroAsegurados, 18)).
						   concat(Utilitarios.verificarLongitudCampo(montoPrimas, 18));
			if (logger.isDebugEnabled()) {logger.debug("-->Linea: " + linea);}
			
			bw.write(linea);
			bw.newLine();
		}
		bw.close();
	} //generarReporteContratosSOAT
	
	
	public List<SelectItem> getComboListaTipoAnexo()
	{
		return comboListaTipoAnexo;
	}
	
	public void setComboListaTipoAnexo(List<SelectItem> comboListaTipoAnexo) {
		this.comboListaTipoAnexo = comboListaTipoAnexo;
	}


	public List<SelectItem> getComboListaPeriodo() {
		return comboListaPeriodo;
	}


	public void setComboListaPeriodo(List<SelectItem> comboListaPeriodo) {
		this.comboListaPeriodo = comboListaPeriodo;
	}


	public String getIdTipoAnexo() {
		return idTipoAnexo;
	}


	public void setIdTipoAnexo(String idTipoAnexo) {
		this.idTipoAnexo = idTipoAnexo;
	}


	public Long getIdPeriodo() {
		return idPeriodo;
	}


	public void setIdPeriodo(Long idPeriodo) {
		this.idPeriodo = idPeriodo;
	}

	public List<SelectItem> getComboListaAnio() {
		return comboListaAnio;
	}


	public void setComboListaAnio(List<SelectItem> comboListaAnio) {
		this.comboListaAnio = comboListaAnio;
	}

	public boolean isPanelStep1Flag() {
		return panelStep1Flag;
	}

	public void setPanelStep1Flag(boolean panelStep1Flag) {
		this.panelStep1Flag = panelStep1Flag;
	}

	public boolean isPanelStep2Flag() {
		return panelStep2Flag;
	}

	public void setPanelStep2Flag(boolean panelStep2Flag) {
		this.panelStep2Flag = panelStep2Flag;
	}

	public List<SelectItem> getComboListaTipoAnexoStep2() {
		return comboListaTipoAnexoStep2;
	}

	public void setComboListaTipoAnexoStep2(
			List<SelectItem> comboListaTipoAnexoStep2) {
		this.comboListaTipoAnexoStep2 = comboListaTipoAnexoStep2;
	}

	public List<SelectItem> getComboListaAnioStep2() {
		return comboListaAnioStep2;
	}

	public void setComboListaAnioStep2(List<SelectItem> comboListaAnioStep2) {
		this.comboListaAnioStep2 = comboListaAnioStep2;
	}

	public List<SelectItem> getComboListaPeriodoStep2() {
		return comboListaPeriodoStep2;
	}

	public void setComboListaPeriodoStep2(List<SelectItem> comboListaPeriodoStep2) {
		this.comboListaPeriodoStep2 = comboListaPeriodoStep2;
	}

	public String getIdTipoAnexoStep2() {
		return idTipoAnexoStep2;
	}

	public void setIdTipoAnexoStep2(String idTipoAnexoStep2) {
		this.idTipoAnexoStep2 = idTipoAnexoStep2;
	}

	public Integer getAnio() {
		return anio;
	}

	public void setAnio(Integer anio) {
		this.anio = anio;
	}

	public Integer getAnioStep2() {
		return anioStep2;
	}

	public void setAnioStep2(Integer anioStep2) {
		this.anioStep2 = anioStep2;
	}

	public Long getIdPeriodoStep2() {
		return idPeriodoStep2;
	}

	public void setIdPeriodoStep2(Long idPeriodoStep2) {
		this.idPeriodoStep2 = idPeriodoStep2;
	}

	public List<RepConsultaSBSBean> getListaReporteSBSCabecera() {
		return listaReporteSBSCabecera;
	}

	public void setListaReporteSBSCabecera(
			List<RepConsultaSBSBean> listaReporteSBSCabecera) {
		this.listaReporteSBSCabecera = listaReporteSBSCabecera;
	}

	public boolean isShownSubBotones() {
		return shownSubBotones;
	}

	public void setShownSubBotones(boolean shownSubBotones) {
		this.shownSubBotones = shownSubBotones;
	}

	public Double getTipoCambioUSD() {
		return tipoCambioUSD;
	}

	public void setTipoCambioUSD(Double tipoCambioUSD) {
		this.tipoCambioUSD = tipoCambioUSD;
	}

	public boolean isDisabledComponentes() {
		return disabledComponentes;
	}

	public void setDisabledComponentes(boolean disabledComponentes) {
		this.disabledComponentes = disabledComponentes;
	}

	public boolean isDisabledBtnGenerar() {
		return disabledBtnGenerar;
	}

	public void setDisabledBtnGenerar(boolean disabledBtnGenerar) {
		this.disabledBtnGenerar = disabledBtnGenerar;
	}

	public String getTituloCabecera() {
		return tituloCabecera;
	}

	public void setTituloCabecera(String tituloCabecera) {
		this.tituloCabecera = tituloCabecera;
	}

	public String getNroConsultaSBS() {
		return nroConsultaSBS;
	}

	public void setNroConsultaSBS(String nroConsultaSBS) {
		this.nroConsultaSBS = nroConsultaSBS;
	}
	
	public boolean isValidarTipoCambioUSD() {
		return validarTipoCambioUSD;
	}

	public void setValidarTipoCambioUSD(boolean validarTipoCambioUSD) {
		this.validarTipoCambioUSD = validarTipoCambioUSD;
	}

	public boolean isBtntipocambio() {
		return btntipocambio;
	}

	public void setBtntipocambio(boolean btntipocambio) {
		this.btntipocambio = btntipocambio;
	}
	
	
	
} //RepConsultaSBSController
