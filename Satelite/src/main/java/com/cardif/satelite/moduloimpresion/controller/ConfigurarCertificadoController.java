package com.cardif.satelite.moduloimpresion.controller;



import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cardif.framework.controller.BaseController;
import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.configuracion.service.ProductoService;
import com.cardif.satelite.constantes.Constantes;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.moduloimpresion.NumeroDocValorado;
import com.cardif.satelite.moduloimpresion.bean.ConsultaProductoSocio;
import com.cardif.satelite.moduloimpresion.service.ConfigurarCertificadoService;

/**
 * Esta clase contiene la funcionalidad para Configurar los numeros de certificados (documentos valorados). Permite 
 * visualizar y generar rangos de certificados.
 * 
 * @author INFOPARQUEPERU
 *		   Jose Manuel Lucas Barrera
 */
@Controller("configCertificadoController")
@Scope("request")
public class ConfigurarCertificadoController extends BaseController
{
	private static final Logger logger = Logger.getLogger(ConfigurarCertificadoController.class);
	
	@Autowired
	private ProductoService productoConfService;
	
	@Autowired
	private ConfigurarCertificadoService configurarCertificadoService;
	
	
	private List<SelectItem> comboListaProductos;
	
	private List<NumeroDocValorado> listaCertificadosPublicos;
	private List<NumeroDocValorado> listaCertificadosPrivados;
	
	private String idProducto;
	private int numCertificadosPublicos;
	private int numCertificadosPrivados;
	
	/* Popup ASIGNAR CERTIFICADOS */
	private Integer rangoDesde;
	private Integer rangoHasta;
	private String anioCertificado;
	private boolean deshabilitarBtnCertPub;
	private boolean deshabilitarBtnCertPri;
	private String[] certRepetidosPub;
	private String[] certRepetidosPri;
	
	
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
			
			listaCertificadosPublicos = null;
			listaCertificadosPrivados = null;
			
			this.listaCertificadosPublicos = new ArrayList<NumeroDocValorado>();
			this.listaCertificadosPrivados = new ArrayList<NumeroDocValorado>();
			
			
			//*****************************************************************************
			//************************ CARGAS LOS VALORES INCIALES ************************
			//*****************************************************************************
			if (logger.isInfoEnabled()) {logger.info("Buscando la lista de PRODUCTOS.");}
			List<ConsultaProductoSocio> listaProductoSocio = productoConfService.buscarPorModImpresionNombreCanal(Constantes.PRODUCTO_MOD_IMPRESION_SI, Constantes.CANAL_PROD_NOMBRE_DIGITAL);
			this.comboListaProductos.add(new SelectItem("", "- Seleccione -"));
			for (ConsultaProductoSocio productoSocio : listaProductoSocio)
			{
				
				if (logger.isDebugEnabled()) {logger.debug("PRODUCTO ==> ID: " + productoSocio.getId() + " - ID_SOCIO: " + productoSocio.getIdSocio() + " - NOMBRE_PRODUCTO: " + productoSocio.getNombreProducto() + " - NOMBRE_SOCIO: " + productoSocio.getNombreSocio());}
				this.comboListaProductos.add(new SelectItem(productoSocio.getId().toString(), productoSocio.getNombreSocio().toUpperCase()));
				
			}
			
			/*
			 * Deshabilitar los botones de asignacion de rangos
			 */
			this.deshabilitarBtnCertPub = true;
			this.deshabilitarBtnCertPri = true;
			
			this.certRepetidosPub = null;
			 certRepetidosPub= new String[2];
			this.certRepetidosPri = null;
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
	} //inicio
	
	public String consultaCertificacoPublicosPrivados(ValueChangeEvent event)
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		String respuesta = null;
		long producto ;

		
		try
		{
			if(null!=event.getNewValue()){
				
			    log.info("EVENT: " + (String) event.getNewValue() );
			    producto =  Long.valueOf(event.getNewValue().toString());
			
			if (logger.isDebugEnabled()) {logger.debug("Buscando los certificados publicos y privados del PRODUCTO: " + producto);}
			
			/*
			 * Buscar certificados publicos del SOCIO
			 */
			this.listaCertificadosPublicos = configurarCertificadoService.buscarCertificadosPublicos(producto);
			
			this.numCertificadosPublicos = (null != this.listaCertificadosPublicos ? this.listaCertificadosPublicos.size() : 0);
			if (logger.isInfoEnabled()) {logger.info("Certificados publicos encontrados: [" + this.numCertificadosPublicos + "]");}
			
			
			/*
			 * Buscar certificados privados del SOCIO
			 */
			this.listaCertificadosPrivados = configurarCertificadoService.buscarCertificadosPrivados(producto);
			
			this.numCertificadosPrivados = (null != this.listaCertificadosPrivados ? this.listaCertificadosPrivados.size() : 0);
			if (logger.isInfoEnabled()) {logger.info("Certificados privados encontrados: [" + this.numCertificadosPrivados + "]");}
			
			
			/*
			 * Habilitar los botones de asignacion
			 */
			this.deshabilitarBtnCertPub = false;
			this.deshabilitarBtnCertPri = false;
			
			}else{
				this.numCertificadosPrivados = 0;
				this.numCertificadosPublicos = 0;
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
	} //consultaCertificacoPublicosPrivados
	
	public void cargarCertificadosPublicosPopup()
	{
		if (logger.isDebugEnabled()) {logger.debug("Inicio");}
		this.rangoDesde = new Integer(0);
		this.rangoHasta = new Integer(0);
		
		Calendar calendar = Calendar.getInstance();
		this.anioCertificado = String.valueOf(calendar.get(Calendar.YEAR));
		if (logger.isDebugEnabled()) {logger.debug("Fin");}
	} //cargarCertificadosPublicosPopup
	
	public void cargarCertificadosPrivadosPopup()
	{
		if (logger.isDebugEnabled()) {logger.debug("Inicio");}
		this.rangoDesde = new Integer(0);
		this.rangoHasta = new Integer(0);
		
		Calendar calendar = Calendar.getInstance();
		this.anioCertificado = String.valueOf(calendar.get(Calendar.YEAR));
		if (logger.isDebugEnabled()) {logger.debug("Fin");}
	} //cargarCertificadosPrivadosPopup
	
	public String asignarCertificadosPublicos(ActionEvent actionEvent)
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio.");}
		String respuesta = null;
		
		try
		{
			if (logger.isDebugEnabled()) {logger.debug("Insertando rango de documentos valorados del PRODUCTO: " + this.idProducto);}
			
			
			if (this.rangoDesde.equals(0) || this.rangoHasta.equals(0) ){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No se aceptán valores Cero!", null));
			}else{
			
			
			if(this.rangoDesde>this.rangoHasta){
				//FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "El Rango Final, es mayor que rango Inicial!", null);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El Rango Final, es menor que el Rango Inicial!", null));
			}else{
				
			
			this.certRepetidosPub = configurarCertificadoService.insertarRangoCertificadosPublicos(this.rangoDesde, this.rangoHasta, this.anioCertificado, Long.valueOf(idProducto));
			if  (StringUtils.isNotBlank(this.certRepetidosPub[1]))
			{
					if (logger.isInfoEnabled()) {logger.info("Se retorno los numeros de certificados publicos repetidos: " + this.certRepetidosPub);}
			}
			
					if (logger.isDebugEnabled()) {logger.debug("Buscando los certificados publicos disponibles.");}
			
			this.listaCertificadosPublicos = configurarCertificadoService.buscarCertificadosPublicos(Long.valueOf(idProducto));
			
			this.numCertificadosPublicos = (null != this.listaCertificadosPublicos ? this.listaCertificadosPublicos.size() : 0);
			if (logger.isInfoEnabled()) {logger.info("Certificados publicos DISPONIBLES: [" + this.numCertificadosPublicos + "]");}
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
	    	FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
	    	FacesContext.getCurrentInstance().addMessage(null, facesMsg);
	    	respuesta = ErrorConstants.MSJ_ERROR;
		}
		if (logger.isInfoEnabled()) {logger.info("Fin.");}
		return respuesta;
	} //asignarCertificadosPublicos
	
	public String asignarCertificadosPrivados(ActionEvent actionEvent) 
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio.");}
		String respuesta = null;
		
		//Pattern pat = Pattern.compile("^([0-9]{1,100})$");
	    //Matcher mat = pat.matcher(String.valueOf(rangoDesde));
		try
		{
			if (logger.isDebugEnabled()) {logger.debug("Insertando rango de documentos valorados del PRODUCTO: " + this.idProducto);}
			
			if (this.rangoDesde.equals(0) || this.rangoHasta.equals(0) ){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No se aceptán valores Cero!", null));
			}else{
				if(this.rangoDesde>this.rangoHasta){
					//FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "El Rango Final, es mayor que rango Inicial!", null);
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El Rango Final, es menor que el Rango Inicial!", null));
				}else{
				
				this.certRepetidosPri = configurarCertificadoService.insertarRangoCertificadosPrivados(this.rangoDesde, this.rangoHasta, this.anioCertificado, Long.valueOf(idProducto));
				if  (StringUtils.isNotBlank(this.certRepetidosPri[1]))
				{
					if (logger.isInfoEnabled()) {logger.info("Se retorno los numeros de certificados privados repetidos: " + this.certRepetidosPri);}
				}

				if (logger.isDebugEnabled()) {logger.debug("Buscando los certificados privados disponibles.");}
				
				this.listaCertificadosPrivados = configurarCertificadoService.buscarCertificadosPrivados(Long.valueOf(idProducto));
				
				this.numCertificadosPrivados = (null != this.listaCertificadosPrivados ? this.listaCertificadosPrivados.size() : 0);
				if (logger.isInfoEnabled()) {logger.info("Certificados privados DISPONIBLES: [" + this.numCertificadosPrivados + "]");}
				
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
	    	FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
	    	FacesContext.getCurrentInstance().addMessage(null, facesMsg);
	    	respuesta = ErrorConstants.MSJ_ERROR;
		}
		if (logger.isInfoEnabled()) {logger.info("Fin.");}
		return respuesta;
	} //asignarCertificadosPrivados
	
	public String eliminarCertificadosPublicos()
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		String respuesta = null;
		logger.debug(this.listaCertificadosPublicos.size());
		try
		{
			if (null != this.listaCertificadosPublicos && 0 < this.listaCertificadosPublicos.size())
			{
				List<NumeroDocValorado> certPublicosEliminar = getCertificadosAEliminar(this.listaCertificadosPublicos);
				
				if (null != certPublicosEliminar && 0 < certPublicosEliminar.size())
				{
					for (NumeroDocValorado nDocValorado : certPublicosEliminar)
					{
						String eliminado = configurarCertificadoService.eliminarCertificadoByParametros(nDocValorado.getNumCertificado(), nDocValorado.getTipoCertificado(), 
								nDocValorado.getAnio(), nDocValorado.getProducto());
						
						if (logger.isDebugEnabled()) {logger.debug("Respuesta de eliminar el certificado: " + eliminado);}
					}
					
					if (logger.isDebugEnabled()) {logger.debug("Buscando los certificados publicos disponibles.");}
					
					this.listaCertificadosPublicos = configurarCertificadoService.buscarCertificadosPublicos(Long.valueOf(idProducto));
					
					this.numCertificadosPublicos = (null != this.listaCertificadosPublicos ? this.listaCertificadosPublicos.size() : 0);
					if (logger.isInfoEnabled()) {logger.info("Certificados publicos DISPONIBLES: [" + this.numCertificadosPublicos + "]");}
				}
				else
				{
					throw new SyncconException(ErrorConstants.COD_ERROR_CONF_CERTIFICADO_PUB_ELIM_NULA);
				}
			}
			else
			{
				throw new SyncconException(ErrorConstants.COD_ERROR_CONF_CERTIFICADO_PUB_LISTA_NULA);
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
	} //eliminarCertificadosPublicos
	
	public String eliminarCertificadosPrivados()
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		String respuesta = null;
		
	
		logger.debug(this.listaCertificadosPrivados.size());
		try{
			if (null != this.listaCertificadosPrivados && 0 < this.listaCertificadosPrivados.size())
			{
				List<NumeroDocValorado> certPrivadosEliminar = getCertificadosAEliminar(this.listaCertificadosPrivados);
				
				if (null != certPrivadosEliminar && 0 < certPrivadosEliminar.size())
				{
					for (NumeroDocValorado nDocValorado : certPrivadosEliminar)
					{
						String eliminado = configurarCertificadoService.eliminarCertificadoByParametros(nDocValorado.getNumCertificado(), nDocValorado.getTipoCertificado(), 
								nDocValorado.getAnio(), nDocValorado.getProducto());
						
						if (logger.isDebugEnabled()) {logger.debug("Respuesta de eliminar el certificado: " + eliminado);}
					}
					
					if (logger.isDebugEnabled()) {logger.debug("Buscando los certificados privados disponibles.");}
					
					this.listaCertificadosPrivados = configurarCertificadoService.buscarCertificadosPrivados(Long.valueOf(idProducto));
					
					this.numCertificadosPrivados = (null != this.listaCertificadosPrivados ? this.listaCertificadosPrivados.size() : 0);
					if (logger.isInfoEnabled()) {logger.info("Certificados privados DISPONIBLES: [" + this.numCertificadosPrivados + "]");}
				}
				else
				{
					throw new SyncconException(ErrorConstants.COD_ERROR_CONF_CERTIFICADO_PRI_ELIM_NULA);
				}
			}
			else
			{
				throw new SyncconException(ErrorConstants.COD_ERROR_CONF_CERTIFICADO_PRI_LISTA_NULA);
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
		
	} //eliminarCertificadosPrivados
	
	private List<NumeroDocValorado> getCertificadosAEliminar(List<NumeroDocValorado> listaCertificadosAEliminar)
	{
		if (logger.isDebugEnabled()) {logger.debug("Inicio");}
		List<NumeroDocValorado> response = new ArrayList<NumeroDocValorado>();
		
		for (NumeroDocValorado nDocValorado : listaCertificadosAEliminar)
		{
			if (nDocValorado.isDeleted())
			{
				response.add(nDocValorado);
			}
		}
		if (logger.isDebugEnabled()) {logger.debug("Fin");}
		return response;
	} //getCertPublicosAEliminar
	
	
	public List<SelectItem> getComboListaProductos()
	{
		return comboListaProductos;
	}
	
	public void setComboListaProductos(List<SelectItem> comboListaProductos)
	{
		this.comboListaProductos = comboListaProductos;
	}
	
	public List<NumeroDocValorado> getListaCertificadosPublicos()
	{
		return listaCertificadosPublicos;
	}
	
	public void setListaCertificadosPublicos(List<NumeroDocValorado> listaCertificadosPublicos)
	{
		this.listaCertificadosPublicos = listaCertificadosPublicos;
	}
	
	public List<NumeroDocValorado> getListaCertificadosPrivados()
	{
		return listaCertificadosPrivados;
	}
	
	public void setListaCertificadosPrivados(List<NumeroDocValorado> listaCertificadosPrivados)
	{
		this.listaCertificadosPrivados = listaCertificadosPrivados;
	}
		
	public String getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(String idProducto) {
		this.idProducto = idProducto;
	}

	public int getNumCertificadosPublicos()
	{
		return numCertificadosPublicos;
	}
	
	public void setNumCertificadosPublicos(int numCertificadosPublicos)
	{
		this.numCertificadosPublicos = numCertificadosPublicos;
	}
	
	public int getNumCertificadosPrivados() 
	{
		return numCertificadosPrivados;
	}
	
	public void setNumCertificadosPrivados(int numCertificadosPrivados)
	{
		this.numCertificadosPrivados = numCertificadosPrivados;
	}
	
	public Integer getRangoDesde()
	{
		return rangoDesde;
	}
	
	public void setRangoDesde(Integer rangoDesde)
	{
		this.rangoDesde = rangoDesde;
	}
	
	public Integer getRangoHasta()
	{
		return rangoHasta;
	}
	
	public void setRangoHasta(Integer rangoHasta)
	{
		this.rangoHasta = rangoHasta;
	}
	
	public String getAnioCertificado()
	{
		return anioCertificado;
	}
	
	public void setAnioCertificado(String anioCertificado)
	{
		this.anioCertificado = anioCertificado;
	}
	
	public boolean isDeshabilitarBtnCertPub()
	{
		return deshabilitarBtnCertPub;
	}
	
	public void setDeshabilitarBtnCertPub(boolean deshabilitarBtnCertPub)
	{
		this.deshabilitarBtnCertPub = deshabilitarBtnCertPub;
	}
	
	public boolean isDeshabilitarBtnCertPri()
	{
		return deshabilitarBtnCertPri;
	}
	
	public void setDeshabilitarBtnCertPri(boolean deshabilitarBtnCertPri)
	{
		this.deshabilitarBtnCertPri = deshabilitarBtnCertPri;
	}
	
	public String[] getCertRepetidosPub()
	{
		return certRepetidosPub;
	}

	public void setCertRepetidosPub(String[] certRepetidosPub) {
		this.certRepetidosPub = certRepetidosPub;
	}

	public String[] getCertRepetidosPri() {
		return certRepetidosPri;
	}

	public void setCertRepetidosPri(String[] certRepetidosPri) {
		this.certRepetidosPri = certRepetidosPri;
	}
	
} //ConfigurarCertificadoController
