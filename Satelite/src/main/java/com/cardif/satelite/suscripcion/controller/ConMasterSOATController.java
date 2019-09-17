package com.cardif.satelite.suscripcion.controller;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cardif.framework.controller.BaseController;
import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.configuracion.service.SocioService;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.Socio;
import com.cardif.satelite.model.TramaMaster;
import com.cardif.satelite.suscripcion.bean.ConsultaMasterPrecio;
import com.cardif.satelite.suscripcion.service.CargMasterSoatService;

@Controller("conMasterSOATController")
@Scope("request")
public class ConMasterSOATController extends BaseController
{
	public static final Logger logger = Logger.getLogger(ConMasterSOATController.class);
	
	@Autowired
	private CargMasterSoatService cargMasterSoatService;
	
	@Autowired
	private SocioService socioService;
	
	
	private List<SelectItem> comboListaSocios;
	
	private Long idSocio;
	private Date fecIniVigencia;
	private Date fecCarga;
	
	private List<TramaMaster> listaCabTramaMaster;
	private String numeroCargaMaster = "";
	
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
			this.comboListaSocios = new ArrayList<SelectItem>();
			this.listaCabTramaMaster = new ArrayList<TramaMaster>();
			this.numeroCargaMaster = "0";
			
			//*****************************************************************************
			//************************ CARGAS LOS VALORES INCIALES ************************
			//*****************************************************************************
			
			if (logger.isInfoEnabled()) {logger.info("Buscando la lista de SOCIOS.");}
			List<Socio> listaUsoVehiculos = cargMasterSoatService.getListaSocios();
			this.comboListaSocios.add(new SelectItem("", "- Seleccione -"));
			for (Socio socio : listaUsoVehiculos)
			{
				if (logger.isDebugEnabled()) {logger.debug("SOCIO ==>ID: " + socio.getId() + " - NOMBRE_SOCIO: " + socio.getNombreSocio());}
				this.comboListaSocios.add(new SelectItem(socio.getId(), socio.getNombreSocio()));
			}
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
	
	public String procesarBusqueda()
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		String respuesta = null;
		
		try
		{
			if (logger.isDebugEnabled())
			{
				logger.debug("################ PARAMETROS DE LA BUSQUEDA ################" + 
						"\nID_SOCIO: " + this.idSocio + "\tFEC_INICIO_VIGENCIA: " + this.fecIniVigencia + "\tFEC_CARGA: " + this.fecCarga);
			}
			
			if (logger.isDebugEnabled()) {logger.debug("Se obtiene la CABECERA de las cargas de MASTER SOAT.");}
			this.listaCabTramaMaster = cargMasterSoatService.buscarTramaMasterCabecera(this.idSocio, this.fecIniVigencia, this.fecCarga);
			 this.numeroCargaMaster = String.valueOf(listaCabTramaMaster.size());
			
			
			if (logger.isInfoEnabled()) {logger.info("Se encontraron [" + (null != this.listaCabTramaMaster ? this.listaCabTramaMaster.size() : 0) + "] registros de CABECERA.");}
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
	
	public String exportarDetalleMasterSOAT()
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		String respuesta = null;
		
		try
		{
			Map<String, Object> beans = new HashMap<String, Object>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
			
			String idMasterExp ="";
  		    FacesContext fc = FacesContext.getCurrentInstance();
	  	    Map<String,String> params =   fc.getExternalContext().getRequestParameterMap();
	  	  idMasterExp =  params.get("idMasterEXP"); 
			
			
			long fechaLong = System.currentTimeMillis();
			String fechaActual = sdf.format(new Date(fechaLong));
			
			List<ConsultaMasterPrecio> listaDetalleMasterExport = cargMasterSoatService.consultaMasterDetalle(idMasterExp.toString());
			if (null != listaDetalleMasterExport && 0 < listaDetalleMasterExport.size())
			{
				String rutaTemp = System.getProperty("jav.io.tmpdir") + "Reporte_Detalle_Master_" + fechaActual + "_" + fechaLong + ".xls";
		        if (logger.isDebugEnabled()) {logger.debug("Ruta archivo: " + rutaTemp);}
		        
		        FacesContext facesContext = FacesContext.getCurrentInstance();
		        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
		        
				String rutaTemplate = servletContext.getRealPath(File.separator + "excel" + File.separator + "template_master_export.xls");
		        if (logger.isDebugEnabled()) {logger.debug("Ruta del Template: " + rutaTemplate);}
		        
		        beans.put("exportar", listaDetalleMasterExport);
		        
		        XLSTransformer transformer = new XLSTransformer();
		        transformer.transformXLS(rutaTemplate, beans, rutaTemp);
		        
		        File archivoResp = new File(rutaTemp);
		        FileInputStream fis = new FileInputStream(archivoResp);
				
		        HttpServletResponse response = (HttpServletResponse) contexto.getResponse();
		        byte[] loader = new byte[(int) archivoResp.length()];
		        response.addHeader("Content-Disposition", "attachment;filename=" + "Reporte_Detalle_Master_" + fechaActual + "_" + fechaLong + ".xls");
		        response.setContentType("application/vnd.ms-excel");
		        
		        ServletOutputStream sos = response.getOutputStream();
		        
		        while ((fis.read(loader)) > 0)
		        {
		        	sos.write(loader, 0, loader.length);
		        }
		        
		        fis.close();
		        sos.close();
		        
		        FacesContext.getCurrentInstance().responseComplete();
		        if (logger.isDebugEnabled()) {logger.debug("Exportacion culminada.");}
			}
			else
			{
				throw new SyncconException(ErrorConstants.COD_ERROR_DET_MASTER_PRECIOS_NULO);
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
	} //exportarDetalleMasterSOAT
	
	
	//=========================================================================
	//=========================== GET / SET METHODS ===========================
	//=========================================================================
	
	public List<SelectItem> getComboListaSocios()
	{
		return comboListaSocios;
	}
	
	public void setComboListaSocios(List<SelectItem> comboListaSocios)
	{
		this.comboListaSocios = comboListaSocios;
	}
	
	public Long getIdSocio()
	{
		return idSocio;
	}
	
	public void setIdSocio(Long idSocio)
	{
		this.idSocio = idSocio;
	}
	
	public Date getFecIniVigencia()
	{
		return fecIniVigencia;
	}
	
	public void setFecIniVigencia(Date fecIniVigencia)
	{
		this.fecIniVigencia = fecIniVigencia;
	}
	
	public Date getFecCarga()
	{
		return fecCarga;
	}
	
	public void setFecCarga(Date fecCarga)
	{
		this.fecCarga = fecCarga;
	}
	
	public List<TramaMaster> getListaCabTramaMaster()
	{
		return listaCabTramaMaster;
	}
	
	public void setListaCabTramaMaster(List<TramaMaster> listaCabTramaMaster)
	{
		this.listaCabTramaMaster = listaCabTramaMaster;
	}

	public String getNumeroCargaMaster() {
		return numeroCargaMaster;
	}

	public void setNumeroCargaMaster(String numeroCargaMaster) {
		this.numeroCargaMaster = numeroCargaMaster;
	}
	
	
	
	
} //ConMasterSOATController
