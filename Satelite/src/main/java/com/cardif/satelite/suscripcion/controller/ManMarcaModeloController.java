package com.cardif.satelite.suscripcion.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
import com.cardif.satelite.configuracion.service.MarcaVehiculoService;
import com.cardif.satelite.configuracion.service.ParametroAutomatService;
import com.cardif.satelite.constantes.Constantes;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.MarcaVehiculo;
import com.cardif.satelite.model.ParametroAutomat;
import com.cardif.satelite.suscripcion.service.ManMarcaModeloService;
import com.cardif.satelite.suscripcion.service.ProcesosETLsService;

@Controller("manMarcaModeloController")
@Scope("request")
public class ManMarcaModeloController extends BaseController
{
	private static final Logger logger = Logger.getLogger(ManMarcaModeloController.class);
	
	@Autowired
	private ManMarcaModeloService manMarcaModeloService;
	
	@Autowired
	private ParametroAutomatService parametroService;
	
	@Autowired
	private MarcaVehiculoService marcaVehiculoService;
	
	@Autowired
	private ProcesosETLsService procesosETLsService; 
	
	private List<SelectItem> comboListaCategoria;
	private List<SelectItem> comboListaMarca;
	
	private String idCategoriaClase;
	private Long idMarcaVehiculo;
	
	private boolean flagMarcaVehiNuevo;
	private String nombreMarcaVehiNuevo;
	private String nombreModeloVehiNuevo;
	
	private boolean disabledComboListaMarca;
	private boolean disabledFlagMarcaVehiNuevo;
	private boolean disabledMarcaVehiNuevo;
	private boolean disabledModeloVehiNuevo;
	private boolean disabledRegistrarNuevo;
	
	
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
			this.comboListaCategoria = new ArrayList<SelectItem>();
			this.comboListaMarca = new ArrayList<SelectItem>();
			
			this.idCategoriaClase = null;
			this.idMarcaVehiculo = null;
			this.flagMarcaVehiNuevo = false;
			this.nombreMarcaVehiNuevo = null;
			this.nombreModeloVehiNuevo = null;
			
			
			//*****************************************************************************
			//************************ CARGAS LOS VALORES INCIALES ************************
			//*****************************************************************************
			if (logger.isInfoEnabled()) {logger.info("Buscando la lista de TIPO DE DOCUMENTO DE IDENTIDAD.");}
			List<ParametroAutomat> listaCategoriaClase = parametroService.buscarPorCodParam(Constantes.COD_PARAM_CATEGORIA_CLASE);
			this.comboListaCategoria.add(new SelectItem("", "- Seleccione -"));
			for (ParametroAutomat categoriaClase : listaCategoriaClase)
			{
				if (logger.isDebugEnabled()) {logger.debug("CATEGORIA_CLASE ==> COD_VALOR: " + categoriaClase.getCodValor() + " - NOMBRE_VALOR: " + categoriaClase.getNombreValor());}
				this.comboListaCategoria.add(new SelectItem(categoriaClase.getCodValor(), categoriaClase.getNombreValor()));
			}
			
			/* Agregar el valor inicial para el comboListaMarca */
			this.comboListaMarca.add(new SelectItem("", "- Seleccione -"));
			
			this.disabledComboListaMarca = true;
			this.disabledFlagMarcaVehiNuevo = true;
			this.disabledMarcaVehiNuevo = true;
			this.disabledModeloVehiNuevo = true;
			this.disabledRegistrarNuevo = true;
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
	
	public String buscarMarcaVehiculo(ValueChangeEvent event)
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		String respuesta = null;
		
		try
		{
			/*
			 * Iniciar valores 			
			 */
			this.comboListaMarca = new ArrayList<SelectItem>();
			this.comboListaMarca.add(new SelectItem("", "- Seleccione -"));
			
			
			
			
			
			if (null != event.getNewValue())
			{
				if (logger.isDebugEnabled()) {logger.debug("Buscando las marcas relacionadas a la CATEGORIA: " + (String) event.getNewValue());}
				List<MarcaVehiculo> listaMarcaVehiculos = marcaVehiculoService.buscarPorPkCategoriaClase((String) event.getNewValue());
				
				if (logger.isDebugEnabled()) {logger.debug("Se encontraron [" + (null != listaMarcaVehiculos ? listaMarcaVehiculos.size() : 0) + "] marcas.");}
				for (MarcaVehiculo marcaVehiculo : listaMarcaVehiculos)
				{
					this.comboListaMarca.add(new SelectItem(marcaVehiculo.getId(), marcaVehiculo.getNombreMarcavehiculo()));
				}
				
				
				this.idMarcaVehiculo = null;
				this.flagMarcaVehiNuevo = false;
				this.nombreMarcaVehiNuevo = null;
				this.nombreModeloVehiNuevo = null;
				
				this.disabledComboListaMarca = false;
				this.disabledFlagMarcaVehiNuevo = false;
				this.disabledMarcaVehiNuevo = true;
				this.disabledModeloVehiNuevo = true;
				this.disabledRegistrarNuevo = false;
			}
			else
			{
				this.idMarcaVehiculo = null;
				this.flagMarcaVehiNuevo = false;
				this.nombreMarcaVehiNuevo = null;
				this.nombreModeloVehiNuevo = null;
				
				this.disabledComboListaMarca = true;
				this.disabledFlagMarcaVehiNuevo = true;
				this.disabledMarcaVehiNuevo = true;
				this.disabledModeloVehiNuevo = true;
				this.disabledRegistrarNuevo = true;
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
	} //buscarMarcaVehiculo
	
	public void habilitarModeloVehiculoNuevo(ValueChangeEvent event)
	{
		if (logger.isDebugEnabled()) {logger.debug("Inicio");}
		
		if (null != event.getNewValue() && StringUtils.isNotBlank(event.getNewValue().toString()))
		{
			if (logger.isDebugEnabled()) {logger.debug("Habilitando el ingreso del NUEVO MODELO de VEHICULO.");}
			this.disabledModeloVehiNuevo = false;
		}
		else
		{
			if (logger.isDebugEnabled()) {logger.debug("Deshabilitando el ingreso del NUEVO MODELO de VEHICULO.");}
			this.disabledModeloVehiNuevo = true;
		}
		if (logger.isDebugEnabled()) {logger.debug("Fin");}
	} //habilitarModeloVehiculoNuevo
	
	private String mensajeRegistro = "";
	
	public void habilitarMarcaVehiculoNuevo(ValueChangeEvent event)
	{
		if (logger.isDebugEnabled()) {logger.debug("Inicio");}
		
		if (((Boolean) event.getNewValue()))
		{
			if (logger.isDebugEnabled()) {logger.debug("Habilitando objetos para MARCA NUEVA.");}
			
			/* Deshabilitando el combobox de la MARCA */
			this.disabledComboListaMarca = true;
			
			/* Habilitando los textbox de la NUEVA MARCA y el NUEVO MODELO */
			this.disabledMarcaVehiNuevo = false;
			this.disabledModeloVehiNuevo = false;
			
			
		}
		else
		{
			if (logger.isDebugEnabled()) {logger.debug("Deshabilitando objetos para MARCA NUEVA.");}
			
			/* Habilitando el combobox de la MARCA */
			this.disabledComboListaMarca = false;
			
			/* Deshabilitando los textbox de la NUEVA MARCA y el NUEVO MODELO */
			this.disabledMarcaVehiNuevo = true;
			this.disabledModeloVehiNuevo = true;
			
		}
		
		this.idMarcaVehiculo = null;
		this.nombreMarcaVehiNuevo = null;
		this.nombreModeloVehiNuevo = null;
		if (logger.isDebugEnabled()) {logger.debug("Fin");}
	} //habilitarMarcaVehiculoNuevo
	
	public String registrarMarcaModelo()
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		String respuesta = null;
			
		try
		{
			

			if(validateCharacter(nombreModeloVehiNuevo) || validateCharacter(nombreMarcaVehiNuevo)){
				
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Carácteres Extraños no permitidos!", null));
				
			}else{
				
			if (StringUtils.isNotBlank(this.idCategoriaClase) && (null != this.idMarcaVehiculo || StringUtils.isNotBlank(this.nombreMarcaVehiNuevo)) && StringUtils.isNotBlank(this.nombreModeloVehiNuevo))
			{
				if (this.flagMarcaVehiNuevo)
				{
					if (manMarcaModeloService.verificarMarcaVehiculo(this.nombreMarcaVehiNuevo))
					{
						if (manMarcaModeloService.verificarModeloVehiculo(this.nombreMarcaVehiNuevo, this.nombreModeloVehiNuevo))
						{
							if (logger.isInfoEnabled()) {logger.info("Registrando MARCA - MODELO de VEHICULO.");}
							Map<String, Long> respuestaMap = manMarcaModeloService.registrarMarcaModeloVehiculo(this.idCategoriaClase, this.nombreMarcaVehiNuevo, this.nombreModeloVehiNuevo);
							mensajeRegistro = "Se registró nueva marca y modelo correctamente.";
							Long pkMarcaVehiculo = respuestaMap.get("marcaVehiculo");
							Long pkModeloVehiculo = respuestaMap.get("modeloVehiculo");
							if (logger.isInfoEnabled()) {logger.info("Se registro la MARCA [" + pkMarcaVehiculo + "] y el MODELO [" + pkModeloVehiculo + "].");}
						}
						else
						{
							this.nombreModeloVehiNuevo = null;
							throw new SyncconException(ErrorConstants.COD_ERROR_MODELO_VEHICULO_EXISTE);
						}
					}
					else
					{
						this.nombreMarcaVehiNuevo = null;
						throw new SyncconException(ErrorConstants.COD_ERROR_MARCA_VEHICULO_EXISTE);
					}
				}
				else
				{
					
					if (manMarcaModeloService.verificarModeloVehiculo(this.idMarcaVehiculo, this.nombreModeloVehiNuevo))
					{
						
						if (logger.isInfoEnabled()) {logger.info("Registrando MODELO de VEHICULO.");}
						
						Long pkModeloVehiculo = manMarcaModeloService.registrarModeloVehiculo(this.idCategoriaClase, this.idMarcaVehiculo, this.nombreModeloVehiNuevo);
						mensajeRegistro = "Se registró el nuevo modelo correctamente.";
						if (logger.isInfoEnabled()) {logger.info("Se registro el MODELO [" + pkModeloVehiculo + "].");}
					
					}
					else
					{
						this.nombreModeloVehiNuevo = null;
						throw new SyncconException(ErrorConstants.COD_ERROR_MODELO_VEHICULO_EXISTE);
					}
				}
				
				procesosETLsService.procesosMarcaModelo();
				
			}
			else
			{
				throw new SyncconException(ErrorConstants.COD_ERROR_NUEVA_MARCA_MODELO);
			}
			}
		}
		catch (SyncconException e)
		{
			logger.error("SyncconException() - ERROR: " + ErrorConstants.ERROR_SYNCCON + e.getMessageComplete());
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
	} //registrarMarcaModelo
	
	public Boolean validateCharacter(String cadena){
		Boolean sw = false;
		if(null!=cadena){
			Matcher mat = null;
			Pattern pat = Pattern.compile("^([A-z\\s-ñáéíóúÑÁÉÍÓÚ.0-9]{1,255})$");
			mat = pat.matcher(cadena);	
			if(!mat.matches()){
				sw = true;
			}
			
			char[] ca = cadena.toCharArray();

			for (char c : ca) {			
				switch (c) {
				case '[' : sw = true; break;
				case ']' : sw = true; break;
				case '_' : sw = true; break;
				case '-' : sw = true; break;
				}					
			}
		}
		
		return sw;
	}
	

	public String getMensajeRegistro() {
		return mensajeRegistro;
	}

	public void setMensajeRegistro(String mensajeRegistro) {
		this.mensajeRegistro = mensajeRegistro;
	}

	public List<SelectItem> getComboListaCategoria()
	{
		return comboListaCategoria;
	}
	

	public void setComboListaCategoria(List<SelectItem> comboListaCategoria)
	{
		this.comboListaCategoria = comboListaCategoria;
	}
	

	public List<SelectItem> getComboListaMarca()
	{
		return comboListaMarca;
	}
	

	public void setComboListaMarca(List<SelectItem> comboListaMarca)
	{
		this.comboListaMarca = comboListaMarca;
	}
	

	public String getIdCategoriaClase()
	{
		return idCategoriaClase;
	}
	

	public void setIdCategoriaClase(String idCategoriaClase)
	{
		this.idCategoriaClase = idCategoriaClase;
	}
	

	public Long getIdMarcaVehiculo()
	{
		return idMarcaVehiculo;
	}
	

	public void setIdMarcaVehiculo(Long idMarcaVehiculo)
	{
		this.idMarcaVehiculo = idMarcaVehiculo;
	}
	

	public boolean isFlagMarcaVehiNuevo()
	{
		return flagMarcaVehiNuevo;
	}
	

	public void setFlagMarcaVehiNuevo(boolean flagMarcaVehiNuevo)
	{
		this.flagMarcaVehiNuevo = flagMarcaVehiNuevo;
	}
	

	public String getNombreMarcaVehiNuevo()
	{
		return nombreMarcaVehiNuevo;
	}
	

	public void setNombreMarcaVehiNuevo(String nombreMarcaVehiNuevo)
	{
		this.nombreMarcaVehiNuevo = nombreMarcaVehiNuevo;
	}
	

	public String getNombreModeloVehiNuevo()
	{
		return nombreModeloVehiNuevo;
	}
	

	public void setNombreModeloVehiNuevo(String nombreModeloVehiNuevo)
	{
		this.nombreModeloVehiNuevo = nombreModeloVehiNuevo;
	}
	

	public boolean isDisabledComboListaMarca()
	{
		return disabledComboListaMarca;
	}
	

	public void setDisabledComboListaMarca(boolean disabledComboListaMarca)
	{
		this.disabledComboListaMarca = disabledComboListaMarca;
	}
	

	public boolean isDisabledFlagMarcaVehiNuevo()
	{
		return disabledFlagMarcaVehiNuevo;
	}
	

	public void setDisabledFlagMarcaVehiNuevo(boolean disabledFlagMarcaVehiNuevo)
	{
		this.disabledFlagMarcaVehiNuevo = disabledFlagMarcaVehiNuevo;
	}
	

	public boolean isDisabledMarcaVehiNuevo()
	{
		return disabledMarcaVehiNuevo;
	}
	

	public void setDisabledMarcaVehiNuevo(boolean disabledMarcaVehiNuevo)
	{
		this.disabledMarcaVehiNuevo = disabledMarcaVehiNuevo;
	}
	

	public boolean isDisabledModeloVehiNuevo()
	{
		return disabledModeloVehiNuevo;
	}
	

	public void setDisabledModeloVehiNuevo(boolean disabledModeloVehiNuevo) 
	{
		this.disabledModeloVehiNuevo = disabledModeloVehiNuevo;
	}
	

	public boolean isDisabledRegistrarNuevo()
	{
		return disabledRegistrarNuevo;
	}
	

	public void setDisabledRegistrarNuevo(boolean disabledRegistrarNuevo)
	{
		this.disabledRegistrarNuevo = disabledRegistrarNuevo;
	}
	
} //ManMarcaModeloController
