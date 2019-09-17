package com.cardif.satelite.reportes.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
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
import com.cardif.satelite.constantes.Constantes;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.ParametroAutomat;
import com.cardif.satelite.reportes.bean.RepSunatBean;
import com.cardif.satelite.reportes.bean.RepSunatCabeceraBean;
import com.cardif.satelite.reportes.service.RepSunatService;
import com.cardif.satelite.util.SateliteUtil;
import com.cardif.satelite.util.Utilitarios;

@Controller("repSunatController")
@Scope("request")
public class RepSunatController extends BaseController
{
	private final Logger logger = Logger.getLogger(RepSunatController.class);
	
	@Autowired
	private RepSunatService repSunatService;
	
	@Autowired
	private ParametroAutomatService parametroService;
	
	
	private List<SelectItem> comboListaPeriodo;
	
	private Integer idPeriodo;
	
	private List<RepSunatCabeceraBean> listaReporteCabSunat;
	private List<RepSunatBean> listaReporteSunat;
	
	private boolean disabledBtnExportar;
	
	
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
			this.comboListaPeriodo = new ArrayList<SelectItem>();
			
			this.listaReporteCabSunat = new ArrayList<RepSunatCabeceraBean>();
			this.listaReporteSunat = new ArrayList<RepSunatBean>();
			
			this.idPeriodo = null;
			
			
			//*****************************************************************************
			//************************ CARGAS LOS VALORES INCIALES ************************
			//*****************************************************************************
			if (logger.isInfoEnabled()) {logger.info("Generando lista de PERIODOS.");}
			ParametroAutomat paramNroUPeriodos = parametroService.obtener(Constantes.COD_PARAM_NRO_ULT_PERIODOS, Constantes.COD_PARAM_NRO_ULT_PERIODOS_VALOR);
			
			List<Integer> listaPeriodos = SateliteUtil.obtenerUltimosPeriodos(Integer.valueOf(paramNroUPeriodos.getNombreValor()));
			this.comboListaPeriodo.add(new SelectItem("", "- Seleccione -"));
			for (Integer anioPeriodo : listaPeriodos)
			{
				if (logger.isDebugEnabled()) {logger.debug("PERIODO ==> VALOR: " + anioPeriodo);}
				this.comboListaPeriodo.add(new SelectItem(anioPeriodo, anioPeriodo.toString()));
			}
			
			
			/*
			 * Colocar el combobox en la fecha actual
			 */
			this.idPeriodo = Calendar.getInstance().get(Calendar.YEAR);
			
			/*
			 * Deshabilitar boton Exportar
			 */
			this.disabledBtnExportar = true;
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
	
	public String procesarConsulta()
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		String respuesta = null;
		
		try
		{
			if (logger.isDebugEnabled())
			{
				logger.debug("################ PARAMETROS DE LA BUSQUEDA ################" + 
						"\nPERIODO: " + this.idPeriodo);
			}
			
			
			/*
			 * Informacion para la visualizacion de la PAGINA
			 */
			this.listaReporteCabSunat = repSunatService.buscarReporteSunatCabecera(this.idPeriodo);
			if (logger.isDebugEnabled()) {logger.debug("Se extrajo la informacion de CABECERA.");}
			
			/*
			 * Polizas para el reporte
			 */
			this.listaReporteSunat = repSunatService.buscarPolizasReporteSunat(this.idPeriodo);
			int nroRegistros = (null != listaReporteSunat && listaReporteSunat.size() > 0) ? listaReporteSunat.size() : 0;
			if (logger.isInfoEnabled()) {logger.info("Se extrajo las polizas para la generacion del REPORTE, Nro. Polizas: " + nroRegistros);}
			
			if (nroRegistros > 0)
			{
				/*
				 * Habilitando el boton Exportar
				 */
				this.disabledBtnExportar = false;
			}
			else
			{
				this.disabledBtnExportar = true;
				
				this.listaReporteCabSunat = new ArrayList<RepSunatCabeceraBean>();
				
				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "No se encontró Pólizas para la generación del reporte.", null);
				FacesContext.getCurrentInstance().addMessage(null, facesMsg);
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
	} //procesarConsulta
	
	public String exportar()
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		String respuesta = null;
		
		try
		{
			if (null != this.listaReporteSunat && 0 < this.listaReporteSunat.size())
			{
				ParametroAutomat rutaArchivoParam = parametroService.obtener(Constantes.COD_PARAM_RUTA_SUNAT, Constantes.COD_PARAM_RUTA_SUNAT_VALOR);
				
				String nombreArchivo = getNombreArchivoSunat();
				FacesContext facesContext = FacesContext.getCurrentInstance();
			    ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
			        
				//String rutaTemp = rutaArchivoParam.getNombreValor() + File.separator + nombreArchivo + ".txt";
				//String rutaTemp = servletContext.getRealPath(File.separator + "excel" + File.separator + nombreArchivo + ".txt");
			        String rutaTemp = System.getProperty("java.io.tmpdir") + nombreArchivo + ".txt";
			        logger.info(rutaTemp);
			        
				if (logger.isDebugEnabled()) {logger.debug("El ruta del archivo es: " + rutaTemp);}
				
				FileOutputStream fos = new FileOutputStream(rutaTemp);
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
				
				
				NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
				DecimalFormat df = (DecimalFormat)nf;
				df.applyPattern("###.00");
				
				for (RepSunatBean item : this.listaReporteSunat)
				{
					String rucInformante = String.format("%-11s", (StringUtils.isNotBlank(item.getRucInformante()) ? item.getRucInformante() : ""));
					String tipDocID = String.format("%-2s", (StringUtils.isNotBlank(item.getTipDocID()) ? item.getTipDocID() : ""));
					String numDocID = String.format("%-15s", (StringUtils.isNotBlank(item.getNumDocID()) ? item.getNumDocID() : "")); 			
					String apePateProp = String.format("%-50s", (StringUtils.isNotBlank(item.getApePateProp()) ? item.getApePateProp() : ""));
					String apeMateProp = String.format("%-50s", (StringUtils.isNotBlank(item.getApeMateProp()) ? item.getApeMateProp() : ""));
					String nomProp = String.format("%-80s", (StringUtils.isNotBlank(item.getNomProp()) ? item.getNomProp() : ""));
					String razonSocProp = String.format("%-100s", (StringUtils.isNotBlank(item.getRazonSocProp()) ? item.getRazonSocProp() : ""));
					String marcaVehiculo = String.format("%-20s", (StringUtils.isNotBlank(item.getMarcaVehiculo()) ? item.getMarcaVehiculo() : ""));
					String anioFab = String.format("%-4s", (null != item.getAnioFab() ? item.getAnioFab() : ""));
					String colorVehiculo = String.format("%-20s", (StringUtils.isNotBlank(item.getColorVehiculo()) ? item.getColorVehiculo() : ""));
					String nroPlaca = String.format("%-10s", (StringUtils.isNotBlank(item.getNroPlaca()) ? item.getNroPlaca() : ""));
					String usoVehiculo = String.format("%-20s", (StringUtils.isNotBlank(item.getUsoVehiculo()) ? item.getUsoVehiculo() : ""));
					String claseVehiculo = String.format("%-20s", (StringUtils.isNotBlank(item.getClaseVehiculo()) ? item.getClaseVehiculo() : ""));
					String nroAsientos = String.format("%-3s", (null != item.getNroAsientos() ? item.getNroAsientos() : ""));
					String modeloVehiculo = String.format("%-20s", (StringUtils.isNotBlank(item.getModeloVehiculo()) ? item.getModeloVehiculo() : ""));
					String nroSerie = String.format("%-20s", (StringUtils.isNotBlank(item.getNroSerie()) ? item.getNroSerie() : ""));
					String provincia = String.format("%-50s", (StringUtils.isNotBlank(item.getProvincia()) ? item.getProvincia() : ""));
					String departamento = String.format("%-50s", (StringUtils.isNotBlank(item.getDepartamento()) ? item.getDepartamento() : ""));
					String direccion = String.format("%-50s", (StringUtils.isNotBlank(item.getDireccion()) ? item.getDireccion() : ""));
					String telefono = String.format("%-10s", (StringUtils.isNotBlank(item.getTelefono()) ? item.getTelefono() : ""));
					String fechaPoliza = String.format("%-10s", (StringUtils.isNotBlank(item.getFechaPoliza()) ? item.getFechaPoliza() : ""));
					String nroPoliza = String.format("%-20s", (StringUtils.isNotBlank(item.getNroPoliza()) ? item.getNroPoliza() : ""));
					String montoPrima = String.format("%-15s", (null != item.getMontoPrima() ? df.format(item.getMontoPrima()) : ""));
					
					String linea = Utilitarios.verificarLongitudCampo(rucInformante,11).
							concat(Utilitarios.verificarLongitudCampo(tipDocID,2)).
							concat(Utilitarios.verificarLongitudCampo(numDocID,15)).
							concat(Utilitarios.verificarLongitudCampo(apePateProp,50)).
							concat(Utilitarios.verificarLongitudCampo(apeMateProp,50)).
							concat(Utilitarios.verificarLongitudCampo(nomProp,80)).
							concat(Utilitarios.verificarLongitudCampo(razonSocProp,100)).
							concat(Utilitarios.verificarLongitudCampo(marcaVehiculo,20)).
							concat(Utilitarios.verificarLongitudCampo(anioFab,4)).
							concat(Utilitarios.verificarLongitudCampo(colorVehiculo,20)).
							concat(Utilitarios.verificarLongitudCampo(nroPlaca,10)).
							concat(Utilitarios.verificarLongitudCampo(usoVehiculo,20)).
							concat(Utilitarios.verificarLongitudCampo(claseVehiculo,20)).
							concat(Utilitarios.verificarLongitudCampo(nroAsientos,3)).
							concat(Utilitarios.verificarLongitudCampo(modeloVehiculo,20)).
							concat(Utilitarios.verificarLongitudCampo(nroSerie,20)).
							concat(Utilitarios.verificarLongitudCampo(provincia,50)).
							concat(Utilitarios.verificarLongitudCampo(departamento,50)).
							concat(Utilitarios.verificarLongitudCampo(direccion,50)).
							concat(Utilitarios.verificarLongitudCampo(telefono,10)).
							concat(Utilitarios.verificarLongitudCampo(fechaPoliza,10)).
							concat(Utilitarios.verificarLongitudCampo(nroPoliza,20)).
							concat(Utilitarios.verificarLongitudCampo(montoPrima,15));
					if (logger.isDebugEnabled()) {logger.debug("-->Linea: " + linea);}
					
					bw.write(linea);
					bw.newLine();
				}
				bw.close();
				
				
				File archivoResp = new File(rutaTemp);
				logger.info("archivoResp "+ archivoResp);
				FileInputStream fis = new FileInputStream(archivoResp);
				
				
		
				
				ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
				
				HttpServletResponse response = (HttpServletResponse) contexto.getResponse();
				byte[] loader = new byte[(int) archivoResp.length()];
				response.addHeader("Content-Disposition", "attachment;filename=" + nombreArchivo + ".txt");
				response.setContentType("text/plain");
				
				ServletOutputStream sos = response.getOutputStream();
			    
			    while ((fis.read(loader)) > 0)
			    {
			    	sos.write(loader, 0, loader.length);
			    }
			    
			    fis.close();
			    sos.close();
			  
				
			    FacesContext.getCurrentInstance().responseComplete();
			    if (logger.isDebugEnabled()) {logger.debug("Exportacion culminada.");}
			    
			    
			    //archivoResp.delete();
			}
			
			
		}
		catch (SyncconException e)
		{
			logger.error("SyncconException() - " + ErrorConstants.ERROR_SYNCCON + e.getMessageComplete());
			logger.error("SyncconException() -->" + ExceptionUtils.getStackTrace(e));
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
	} //exportar
	
	private String getNombreArchivoSunat()
	{
//		return Constantes.RUC_CARDIF_SEGUROS + Constantes.REP_SUNAT_COD_AAA + Calendar.getInstance().get(Calendar.YEAR) + Constantes.REP_SUNAT_PERIODO_ANUAL;
		return Constantes.RUC_CARDIF_SEGUROS + Constantes.REP_SUNAT_COD_AAA + this.idPeriodo + Constantes.REP_SUNAT_PERIODO_ANUAL;
	} //getNombreArchivoSunat
	
	
	public List<SelectItem> getComboListaPeriodo()
	{
		return comboListaPeriodo;
	}
	
	public void setComboListaPeriodo(List<SelectItem> comboListaPeriodo)
	{
		this.comboListaPeriodo = comboListaPeriodo;
	}
	
	public Integer getIdPeriodo()
	{
		return idPeriodo;
	}
	
	public void setIdPeriodo(Integer idPeriodo)
	{
		this.idPeriodo = idPeriodo;
	}
	
	public List<RepSunatCabeceraBean> getListaReporteCabSunat()
	{
		return listaReporteCabSunat;
	}
	
	public void setListaReporteCabSunat(List<RepSunatCabeceraBean> listaReporteCabSunat)
	{
		this.listaReporteCabSunat = listaReporteCabSunat;
	}
	
	public List<RepSunatBean> getListaReporteSunat()
	{
		return listaReporteSunat;
	}
	
	public void setListaReporteSunat(List<RepSunatBean> listaReporteSunat)
	{
		this.listaReporteSunat = listaReporteSunat;
	}
	
	public boolean isDisabledBtnExportar()
	{
		return disabledBtnExportar;
	}
	
	public void setDisabledBtnExportar(boolean disabledBtnExportar)
	{
		this.disabledBtnExportar = disabledBtnExportar;
	}
	
} //RepSunatController
