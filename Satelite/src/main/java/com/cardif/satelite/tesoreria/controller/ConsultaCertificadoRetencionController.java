package com.cardif.satelite.tesoreria.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
import org.richfaces.model.selection.SimpleSelection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cardif.framework.controller.BaseController;
import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.configuracion.service.ParametroService;
import com.cardif.satelite.constantes.Constantes;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.Parametro;
import com.cardif.satelite.tesoreria.model.ComprobanteElectronico;
import com.cardif.satelite.tesoreria.service.ConsultaCertificadoRetencionService;
import com.cardif.satelite.util.Utilitarios;
import com.cardif.sunsystems.util.ConstantesSun;

@Controller("conCertificadoRetencionController")
@Scope("request")
public class ConsultaCertificadoRetencionController extends BaseController
{
	private static final Logger logger = Logger.getLogger(ConsultaCertificadoRetencionController.class);
	
	@Autowired
	private ConsultaCertificadoRetencionService conCertificadoRetencionService;
	
	@Autowired
	private ParametroService parametroService;
	
	private List<SelectItem> comboListaUnidadNegocio;
	private List<SelectItem> comboListaEstadoCert;
	private SimpleSelection selection;
	
	private String idUnidadNegocio;
	private Date fecEmisionDesde;
	private Date fecEmisionHasta;
	private String nroCertificadoDesde;
	private String nroCertificadoHasta;
	private String idEstadoCertificado;
	
	private List<ComprobanteElectronico> listaComprobanteElectronico;
	private ComprobanteElectronico comprobanteElectronicoSelected;
	private boolean disabledBtnExportar;
	private boolean disabledBtnVerDetalle;
	
	
	@PostConstruct
	@Override
	public String inicio()
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		String respuesta = null;
		
		if (!tieneAcceso())
		{
			if (logger.isDebugEnabled()){logger.debug("No cuenta con los accesos necesarios.");}
		    return "accesoDenegado";
		}
		
		try
		{
			this.comboListaUnidadNegocio = new ArrayList<SelectItem>();
			this.comboListaEstadoCert = new ArrayList<SelectItem>();
			
			this.listaComprobanteElectronico = new ArrayList<ComprobanteElectronico>();
			this.idUnidadNegocio = null;
			this.fecEmisionDesde = null;
			this.fecEmisionHasta = null;
			this.nroCertificadoDesde = null;
			this.nroCertificadoHasta = null;
			this.idEstadoCertificado = null;
			
			
			// *****************************************************************************
		    // ************************ CARGAS LOS VALORES INCIALES ************************
		    // *****************************************************************************
		    if (logger.isInfoEnabled()) {logger.info("Buscando la lista de UNIDADES DE NEGOCIO.");}
		    List<Parametro> listaUnidadNegocios = parametroService.buscar(Constantes.COD_PARAM_COMPANIA, Constantes.TIP_PARAM_DETALLE);
		    this.comboListaUnidadNegocio.add(new SelectItem("", "- Seleccione -"));

		    for (Parametro unidadNegocio : listaUnidadNegocios)
		    {
		        if (logger.isDebugEnabled()) {logger.debug("UNIDAD_NEGOCIO ==> COD_VALOR: " + unidadNegocio.getCodValor() + " - NOM_VALOR: " + unidadNegocio.getNomValor());}
		        this.comboListaUnidadNegocio.add(new SelectItem(unidadNegocio.getCodValor(), unidadNegocio.getNomValor()));
		    }
		    
		    
		    if (logger.isInfoEnabled()) {logger.info("Buscando la lista de ESTADOS DEL CERTIFICADO.");}
		    List<Parametro> listaEstadosCertificado = parametroService.buscar(Constantes.COD_PARAM_ESTADO_CERTIFICADO, Constantes.TIP_PARAM_DETALLE);
		    this.comboListaEstadoCert.add(new SelectItem("", "- Seleccione -"));
		    
		    for (Parametro estadoCert : listaEstadosCertificado)
		    {
		    	if (logger.isDebugEnabled()) {logger.debug("ESTADO_CERTIFICADO ==> COD_VALOR: " + estadoCert.getCodValor() + " - NOM_VALOR: " + estadoCert.getNomValor());}
		    	this.comboListaEstadoCert.add(new SelectItem(estadoCert.getCodValor(), estadoCert.getNomValor()));
		    }
		    
			
			/*
			 * Deshabilita el boton 'Exportar' y 'Ver Detalle'
			 */
			this.disabledBtnExportar = true;
			this.disabledBtnVerDetalle = true;
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
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = ErrorConstants.MSJ_ERROR;
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return respuesta;
	} //inicio
	
	public String buscar()
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		String respuesta = null;
		
		try
		{
			if (logger.isDebugEnabled())
			{
				logger.debug("################ PARAMETROS DE LA BUSQUEDA ################" + 
						"\nUNIDAD_NEGOCIO: " + this.idUnidadNegocio + 
						"\nFEC_EMISION_DESDE: " + this.fecEmisionDesde + "\tFEC_EMISION_HASTA: " + this.fecEmisionHasta + 
						"\nNRO_CERTIFICADO_DESDE: " + this.nroCertificadoDesde + "\tNRO_CERTIFICADO_HASTA: " + this.nroCertificadoHasta + "\tESTADO: " + this.idEstadoCertificado);
		    }
			
			
			if (logger.isDebugEnabled()) {logger.debug("Buscando los certificados de RETENCION...");}
			this.listaComprobanteElectronico = conCertificadoRetencionService.buscarCertificadosRetencion(this.idUnidadNegocio, 
					this.fecEmisionDesde, this.fecEmisionHasta, this.nroCertificadoDesde, this.nroCertificadoHasta, this.idEstadoCertificado);
			
			int size = (null != this.listaComprobanteElectronico ? this.listaComprobanteElectronico.size() : 0);
			if (logger.isInfoEnabled()) {logger.info("Se encontraron [" + size + "] registros de certificados de RETENCION.");}
			
			/*
			 * Habilita el boton 'Exportar' y Deshabilita el boton 'Ver Detalle'
			 */
			if (0 < size)
			{
				this.disabledBtnExportar = false;
				this.disabledBtnVerDetalle = true;
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
		    logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
		    FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
		    FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		    respuesta = ErrorConstants.MSJ_ERROR;
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return respuesta;
	} //buscar
	
	public String seleccionarCertificadoRetencion()
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		String respuesta = null;
		
		try
		{
			Iterator<Object> it = getSelection().getKeys();
			if (it.hasNext())
			{
				int key = (Integer) it.next();
				ComprobanteElectronico comprobanteElectronico = this.listaComprobanteElectronico.get(key);
				if (logger.isInfoEnabled()) {logger.info("Nro.ComprobanteElect: " + comprobanteElectronico.getNroComprobanteElectronico());}
				
				
				/* Guardando el objeto */
				setComprobanteElectronicoSelected(comprobanteElectronico);
			}
			
			this.disabledBtnVerDetalle = false;
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
	} //seleccionarDetalleCertificadoRetencion
	
	public String verDetalleRespuestaPPL()
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		logger.info("Seleccionado: " + getComprobanteElectronicoSelected());
		
		
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return null;
	} //verDetalleRespuestaPPL
	
	public String exportar()
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		String respuesta = null;
		
		try
		{
			if (null != this.listaComprobanteElectronico && !this.listaComprobanteElectronico.isEmpty())
			{
				if (logger.isInfoEnabled()) {logger.info("Se obtiene los registros para el reporte. Nro registros: " + this.listaComprobanteElectronico.size());}
				
				Map<String, Object> beans = new HashMap<String, Object>();
				String fechaActual = Utilitarios.convertirFechaACadena(Calendar.getInstance().getTime(), ConstantesSun.UTL_FORMATO_FECHA_PAPERLESS);
				long timeMillis = System.currentTimeMillis();
				String nombreArchivo = "Reporte_Retencion_" + fechaActual + "_" + timeMillis;
				
				ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
				
				
				String rutaTemp = System.getProperty("java.io.tmpdir") + nombreArchivo + ".xls";
		        if (logger.isDebugEnabled()) {logger.debug("Ruta temporal del Archivo: " + rutaTemp);}
		        
		        FacesContext facesContext = FacesContext.getCurrentInstance();
		        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
		        
		        String rutaTemplate = servletContext.getRealPath(File.separator + "excel" + File.separator + "template_exportar_reporte_cert_retencion.xls");
		        if (logger.isDebugEnabled()) {logger.debug("Ruta del Template: " + rutaTemplate);}
		        
		        beans.put("exportar", this.listaComprobanteElectronico);
		        
		        XLSTransformer transformer = new XLSTransformer();
		        transformer.transformXLS(rutaTemplate, beans, rutaTemp);
		        
		        File archivoResp = new File(rutaTemp);
		        FileInputStream fis = new FileInputStream(archivoResp);
		        
		        HttpServletResponse response = (HttpServletResponse) contexto.getResponse();
				byte[] loader = new byte[(int) archivoResp.length()];
				response.addHeader("Content-Disposition", "attachment;filename=" + nombreArchivo + ".xls");
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
				throw new SyncconException(ErrorConstants.COD_ERROR_EXPORTAR_CERT_RETENCION);
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
		    logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
		    FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
		    FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		    respuesta = ErrorConstants.MSJ_ERROR;
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return respuesta;
	} //exportar
	
	
	public List<SelectItem> getComboListaUnidadNegocio()
	{
		return comboListaUnidadNegocio;
	}
	
	public void setComboListaUnidadNegocio(List<SelectItem> comboListaUnidadNegocio)
	{
		this.comboListaUnidadNegocio = comboListaUnidadNegocio;
	}
	
	public List<SelectItem> getComboListaEstadoCert()
	{
		return comboListaEstadoCert;
	}
	
	public void setComboListaEstadoCert(List<SelectItem> comboListaEstadoCert)
	{
		this.comboListaEstadoCert = comboListaEstadoCert;
	}
	
	public SimpleSelection getSelection()
	{
		return selection;
	}
	
	public void setSelection(SimpleSelection selection)
	{
		this.selection = selection;
	}
	
	public String getIdUnidadNegocio()
	{
		return idUnidadNegocio;
	}
	
	public void setIdUnidadNegocio(String idUnidadNegocio)
	{
		this.idUnidadNegocio = idUnidadNegocio;
	}
	
	public Date getFecEmisionDesde()
	{
		return fecEmisionDesde;
	}
	
	public void setFecEmisionDesde(Date fecEmisionDesde)
	{
		this.fecEmisionDesde = fecEmisionDesde;
	}
	
	public Date getFecEmisionHasta()
	{
		return fecEmisionHasta;
	}
	
	public void setFecEmisionHasta(Date fecEmisionHasta)
	{
		this.fecEmisionHasta = fecEmisionHasta;
	}
	
	public String getNroCertificadoDesde()
	{
		return nroCertificadoDesde;
	}
	
	public void setNroCertificadoDesde(String nroCertificadoDesde)
	{
		this.nroCertificadoDesde = nroCertificadoDesde;
	}
	
	public String getNroCertificadoHasta()
	{
		return nroCertificadoHasta;
	}
	
	public void setNroCertificadoHasta(String nroCertificadoHasta)
	{
		this.nroCertificadoHasta = nroCertificadoHasta;
	}
	
	public String getIdEstadoCertificado()
	{
		return idEstadoCertificado;
	}
	
	public void setIdEstadoCertificado(String idEstadoCertificado)
	{
		this.idEstadoCertificado = idEstadoCertificado;
	}
	
	public List<ComprobanteElectronico> getListaComprobanteElectronico()
	{
		return listaComprobanteElectronico;
	}
	
	public void setListaComprobanteElectronico(List<ComprobanteElectronico> listaComprobanteElectronico)
	{
		this.listaComprobanteElectronico = listaComprobanteElectronico;
	}
	
	public ComprobanteElectronico getComprobanteElectronicoSelected()
	{
		return comprobanteElectronicoSelected;
	}
	
	public void setComprobanteElectronicoSelected(ComprobanteElectronico comprobanteElectronicoSelected)
	{
		this.comprobanteElectronicoSelected = comprobanteElectronicoSelected;
	}
	
	public boolean isDisabledBtnExportar()
	{
		return disabledBtnExportar;
	}
	
	public void setDisabledBtnExportar(boolean disabledBtnExportar)
	{
		this.disabledBtnExportar = disabledBtnExportar;
	}
	
	public boolean isDisabledBtnVerDetalle()
	{
		return disabledBtnVerDetalle;
	}
	
	public void setDisabledBtnVerDetalle(boolean disabledBtnVerDetalle)
	{
		this.disabledBtnVerDetalle = disabledBtnVerDetalle;
	}
	
} //ReporteCertificadoRetencionController
