package com.cardif.satelite.tesoreria.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cardif.framework.controller.BaseController;
import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.configuracion.service.ParametroService;
import com.cardif.satelite.constantes.Constantes;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.Parametro;
import com.cardif.satelite.tesoreria.bean.ConsultaCertRetencionReversa;
import com.cardif.satelite.tesoreria.handler.GeneradorTramaHandler;
import com.cardif.satelite.tesoreria.service.GeneracionReversaComprobantesService;
import com.cardif.satelite.util.Utilitarios;
import com.cardif.sunsystems.util.ConstantesSun;

@Controller("genReversaComprobantesController")
@Scope("request")
public class GeneracionReversaComprobantesController extends BaseController
{
	public static final Logger logger = Logger.getLogger(GeneracionReversaComprobantesController.class);
	
	@Autowired
	private GeneracionReversaComprobantesService genReversaComprobantesService;
	
	@Autowired
	private ParametroService parametroService;
	
	private List<SelectItem> comboListaUnidadNegocio;
	
	private String idUnidadNegocio;
	private Date fecEmision;
	private String nroCertificado;
	private String rucProveedor;
	
	private List<ConsultaCertRetencionReversa> listaCertRetencionReversa;
	private boolean disabledBtnGenerarTXT;
	
	
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
			
			this.listaCertRetencionReversa = new ArrayList<ConsultaCertRetencionReversa>();
			this.idUnidadNegocio = null;
			this.fecEmision = null;
			this.nroCertificado = null;
			this.rucProveedor = null;
			
			
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
			
			
			/*
			 * Deshabilita el boton 'Generar TXT'
			 */
			this.disabledBtnGenerarTXT = true;
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
						"\nFEC_EMISION: " + this.fecEmision + "\tNRO_CERTIFICADO: " + this.nroCertificado + "\tRUC_PROVEEDOR: " + this.rucProveedor);
		    }
			
			
			if (logger.isDebugEnabled()) {logger.debug("Buscando los certificados de RETENCION anulados...");}
			this.listaCertRetencionReversa = genReversaComprobantesService.buscarCertificadosRetencionReversar(this.idUnidadNegocio, this.fecEmision, 
					this.nroCertificado, this.rucProveedor);
			
			int size = (null != this.listaCertRetencionReversa ? this.listaCertRetencionReversa.size() : 0);
			if (logger.isInfoEnabled()) {logger.info("Se encontraron [" + size + "] registros de RETENCION anulados.");}
			
			
			/*
			 * Habilita el boton 'Generar TXT'
			 */
			if (0 < size)
			{
				this.disabledBtnGenerarTXT = false;
			}
			else
			{
				this.disabledBtnGenerarTXT = true;
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
	
	public String generarTXT()
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		String respuesta = null;
		
		try
		{
			int size = (null != this.listaCertRetencionReversa ? this.listaCertRetencionReversa.size() : 0);
			if (logger.isInfoEnabled()) {logger.info("Se encontro [" + size + "] registros de RETENCION anulados para REVERSAR.");}
			
			
			/*
			 * Validar el plazo maximo segun SUNAT
			 * Tomamos una fecha de emision por default.
			 */
			Date fechaEmision = this.listaCertRetencionReversa.get(0).getFechaEmision();
			boolean check = Utilitarios.verificarRangoDeFechas(fechaEmision, Calendar.getInstance().getTime(), Constantes.PLAZO_DIAS_HABILES_SUNAT);
			if (check)
			{
				if (logger.isInfoEnabled()) {logger.info("Los documentos se encuentran dentro del plazo maximo de " + Constantes.PLAZO_DIAS_HABILES_SUNAT + " dias.");}
				
				/*
		         * Actualizando el estado a REVERSADO en Sunsystems
		         */
		        boolean sunsystemsOK = genReversaComprobantesService.actualizarEstadoSunsystems(this.listaCertRetencionReversa, ConstantesSun.EST_ASIENTO_REVERSADO);
		        
		        if (sunsystemsOK)
		        {
			        if (logger.isDebugEnabled()) {logger.debug("Se cambio el estado a REVERSADO en SUNSYSTEMS.");}
			        
		        	if (logger.isDebugEnabled()) {logger.debug("Actualizando el estado en SATELITE.");}
		        	
		        	 /*
					 * Actualizando el estado a REVERSADO en la DB de Satelite y actualizar el valor
					 * de la columna PLAZO_FECHA_SUNAT por defecto a 1.
					 * 
					 * NOTA: Este valor del PLAZO_FECHA_SUNAT ya se encuentra en 1, pero para poder utilizar el 
					 * metodo 'actualizarEstadoCertRetencionReversa' es necesario colocar un valor.
					 */
			        genReversaComprobantesService.actualizarEstadoCertRetencionReversa(this.listaCertRetencionReversa, ConstantesSun.EST_ASIENTO_REVERSADO, Constantes.PLAZO_FECHA_SUNAT_OK);
			        if (logger.isDebugEnabled()) {logger.debug("Se cambio el estado a REVERSADO de los certificados de RETENCION.");}
			        
			        
			        GeneradorTramaHandler tramaHandler = GeneradorTramaHandler.newInstance();
					String tramaReversion = tramaHandler.generaTramaReversion(this.listaCertRetencionReversa);
					if (logger.isInfoEnabled()) {logger.info("Se genero la trama de REVERSION: \n\n" + tramaReversion);}
					
					
					/*
					 * Proceso de generacion del archivo TXT
					 */
					String fecEmisionReporte = Utilitarios.convertirFechaACadena(fechaEmision, ConstantesSun.UTL_FORMATO_FECHA_PAPERLESS2);
					
					FileOutputStream fos = new FileOutputStream(System.getProperty("java.io.tmpdir") + "Reversa_Certificado_" + fecEmisionReporte + ".txt");
					BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
					
					
					String[] reversionRegistros = tramaReversion.split("\n");
					for (String registro : reversionRegistros)
					{
						bw.write(registro);
						bw.newLine();
					}
					bw.close();
					
					File archivoResp = new File(System.getProperty("java.io.tmpdir") + "Reversa_Certificado_" + fecEmisionReporte + ".txt");
					FileInputStream fis = new FileInputStream(archivoResp);
					
					ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
					HttpServletResponse response = (HttpServletResponse) contexto.getResponse();
					byte[] loader = new byte[(int) archivoResp.length()];
					response.addHeader("Content-Disposition", "attachment;filename=Revera_Certificado_" + fecEmisionReporte + ".txt");
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
			        
			        
			        if (logger.isDebugEnabled()) {logger.debug("Llamando al metodo buscar() para verificar si existen nuevos certifica ANULADOS con los mismos parametros de busqueda.");}
			        buscar();
		        }
		        else
		        {
		        	if (logger.isInfoEnabled()) {logger.info("No se pudo cambiar el estado a REVERSADO en SUNSYSTEMS.");}
		        	throw new SyncconException(ErrorConstants.COD_ERROR_SUNSYSTEMS_LEDGERTRANSACTION_UPDATE);
		        }
			}
			else
			{
				if (logger.isInfoEnabled()) {logger.info("Los documentos se encuentran fuera del plazo maximo de " + Constantes.PLAZO_DIAS_HABILES_SUNAT + " dias.");}
				
				/*
				 * Actualizando el estado a ANULADO en la DB de Satelite y actualizar el valor 
				 * de la columna PLAZO_FECHA_SUNAT a 0
				 */
				genReversaComprobantesService.actualizarEstadoCertRetencionReversa(this.listaCertRetencionReversa, ConstantesSun.EST_ASIENTO_ANULADO, Constantes.PLAZO_FECHA_SUNAT_NO_OK);
		        if (logger.isDebugEnabled()) {logger.debug("Se cambio el estado a ANULADO de los certificados de RETENCION.");}
		        
		        if (logger.isDebugEnabled()) {logger.debug("Llamando al metodo buscar() para verificar si existen nuevos certifica ANULADOS con los mismos parametros de busqueda.");}
		        buscar();
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
	} //generarTXT
	
	
	public List<SelectItem> getComboListaUnidadNegocio()
	{
		return comboListaUnidadNegocio;
	}
	
	public void setComboListaUnidadNegocio(List<SelectItem> comboListaUnidadNegocio)
	{
		this.comboListaUnidadNegocio = comboListaUnidadNegocio;
	}
	
	public String getIdUnidadNegocio()
	{
		return idUnidadNegocio;
	}
	
	public void setIdUnidadNegocio(String idUnidadNegocio)
	{
		this.idUnidadNegocio = idUnidadNegocio;
	}
	
	public Date getFecEmision()
	{
		return fecEmision;
	}
	
	public void setFecEmision(Date fecEmision)
	{
		this.fecEmision = fecEmision;
	}
	
	public String getNroCertificado()
	{
		return nroCertificado;
	}
	
	public void setNroCertificado(String nroCertificado)
	{
		this.nroCertificado = nroCertificado;
	}
	
	public String getRucProveedor()
	{
		return rucProveedor;
	}
	
	public void setRucProveedor(String rucProveedor)
	{
		this.rucProveedor = rucProveedor;
	}
	
	public List<ConsultaCertRetencionReversa> getListaCertRetencionReversa()
	{
		return listaCertRetencionReversa;
	}
	
	public void setListaCertRetencionReversa(List<ConsultaCertRetencionReversa> listaCertRetencionReversa)
	{
		this.listaCertRetencionReversa = listaCertRetencionReversa;
	}
	
	public boolean isDisabledBtnGenerarTXT()
	{
		return disabledBtnGenerarTXT;
	}
	
	public void setDisabledBtnGenerarTXT(boolean disabledBtnGenerarTXT)
	{
		this.disabledBtnGenerarTXT = disabledBtnGenerarTXT;
	}
	
} //GeneracionReversaComprobantesController
