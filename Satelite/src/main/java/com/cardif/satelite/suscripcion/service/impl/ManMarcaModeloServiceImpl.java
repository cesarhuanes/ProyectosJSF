package com.cardif.satelite.suscripcion.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.configuracion.dao.MarcaVehiculoMapper;
import com.cardif.satelite.configuracion.dao.ModeloVehiculoMapper;
import com.cardif.satelite.constantes.Constantes;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.MarcaVehiculo;
import com.cardif.satelite.model.ModeloVehiculo;
import com.cardif.satelite.soat.directo.dao.MarcaVehiculoDirectoMapper;
import com.cardif.satelite.soat.directo.dao.ModeloVehiculoDirectoMapper;
import com.cardif.satelite.suscripcion.bean.TarifaModeloBean;
import com.cardif.satelite.suscripcion.service.ManMarcaModeloService;

@Service("manMarcaModeloService")
public class ManMarcaModeloServiceImpl implements ManMarcaModeloService
{
	private final Logger logger = Logger.getLogger(ManMarcaModeloServiceImpl.class);
	
	@Autowired
	private MarcaVehiculoMapper marcaMapper;
	@Autowired
	private ModeloVehiculoMapper modeloMapper;
	
	@Autowired
	private MarcaVehiculoDirectoMapper marcaDirectoMapper;
	@Autowired
	private ModeloVehiculoDirectoMapper modeloDirectoMapper;
	@Autowired
	private com.cardif.satelite.soat.directo.dao.TarifaExcepcionMapper tarifaExDirectoMapper;
	
	@Autowired
	private com.cardif.satelite.soat.falabella.dao.MarcaVehiculoMapper marcaFalabellaMapper;
	@Autowired
	private com.cardif.satelite.soat.falabella.dao.ModeloVehiculoMapper modeloFalabellaMapper;
	@Autowired
	private com.cardif.satelite.soat.falabella.dao.TarifaExcepcionMapper tarifaExFalabellaMapper;
	
	@Autowired
	private com.cardif.satelite.soat.ripley.dao.MarcaVehiculoMapper marcaRipleyMapper;
	@Autowired
	private com.cardif.satelite.soat.ripley.dao.ModeloVehiculoMapper modeloRipleyMapper;
	@Autowired
	private com.cardif.satelite.soat.ripley.dao.TarifaExcepcionMapper tarifaExRipleyMapper;
	
	@Autowired
	private com.cardif.satelite.soat.comparabien.dao.MarcaVehiculoMapper marcaComparaBienMapper;
	@Autowired
	private com.cardif.satelite.soat.comparabien.dao.ModeloVehiculoMapper modeloComparaBienMapper;
	@Autowired
	private com.cardif.satelite.soat.comparabien.dao.TarifaExcepcionMapper tarifaExComparaBienMapper;
	
	@Autowired
	private com.cardif.satelite.soat.sucursal.dao.MarcaVehiculoMapper marcaSucursalMapper;
	@Autowired
	private com.cardif.satelite.soat.sucursal.dao.ModeloVehiculoMapper modeloSucursalMapper;
	@Autowired
	private com.cardif.satelite.soat.sucursal.dao.TarifaExcepcionMapper tarifaExSucursalMapper;
	
	@Autowired
	private com.cardif.satelite.soat.multisocio.dao.MarcaVehiculoMapper marcaMultisocioMapper;
	@Autowired
	private com.cardif.satelite.soat.multisocio.dao.ModeloVehiculoMapper modeloMultisocioMapper;
	@Autowired
	private com.cardif.satelite.soat.multisocio.dao.TarifaExcepcionMapper tarifaExMultisocioMapper;
	
	
	@Override
	public boolean verificarMarcaVehiculo(String nombreMarcaVehiculo) throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		boolean flag = false;
		
		try
		{
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + nombreMarcaVehiculo + "]");}
			MarcaVehiculo marcaVehiculo = marcaMapper.selectByNombreMarcaVehiculo(nombreMarcaVehiculo);
			
			if (logger.isDebugEnabled()) {logger.debug("Output [MarcaVehiculo: " + marcaVehiculo + "]");}
			
			if (null == marcaVehiculo)
			{
				flag = true;
			}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return flag;
	} //verificarMarcaVehiculo
	
	@Override
	public boolean verificarModeloVehiculo(Object marcaVehiculoObj, String nombreModeloVehiculo) throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		boolean flag = false;
		
		try
		{
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + marcaVehiculoObj + ", 2.-" + nombreModeloVehiculo + "]");}
			
			ModeloVehiculo modeloVehiculo = null;
			if (marcaVehiculoObj instanceof Long) /* PK de la MARCA */
			{
				modeloVehiculo = modeloMapper.selectByPkMarcaAndNombreModelo((Long) marcaVehiculoObj, nombreModeloVehiculo);
			}
			else if (marcaVehiculoObj instanceof String) /* NOMBRE de la MARCA */
			{
				modeloVehiculo = modeloMapper.selectByNombreMarcaAndNombreModelo((String) marcaVehiculoObj, nombreModeloVehiculo);
			}
			
			
			if (logger.isDebugEnabled()) {logger.debug("Output [ModeloVehiculo: " + modeloVehiculo + "]");}
			
			if (null == modeloVehiculo)
			{
				flag = true;
			}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return flag;
	} //verificarModeloVehiculo
	
	
	@Override
	public Map<String, Boolean> validacionMarca(String codCategoriaClase,String codMarcaVehiculo) {
			
		if(logger.isInfoEnabled()){logger.info("Inicio");}
		
		if(logger.isDebugEnabled()){logger.info("Input [1.-"+codCategoriaClase+" 2.- "+ codMarcaVehiculo+"]");}
		Map<String, Boolean>  valStatus = new HashMap<String, Boolean>();
		
		try {
			
			try {
				
				int countDirecto = 0;
				
				countDirecto = marcaDirectoMapper.validateMarcar(codMarcaVehiculo);
				
				if(countDirecto>0){
					valStatus.put("directo", true); 
				}else{
					valStatus.put("directo", false);
				}
				
				
				if (logger.isInfoEnabled()) {logger.info("Inserto MARCA - MODELO en DB Directo.");}				
			}
			catch (Exception e)
			{
				logger.error("Exception(" + e.getClass().getName() + ") - ERROR_DIRECTO: " + e.getMessage());
				logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			}
			
			try {
				int countFallabela = 0;
				countFallabela = marcaFalabellaMapper.validateMarcar(codMarcaVehiculo);
				
				if(countFallabela>0){
					valStatus.put("fallabela", true); 
				}else{
					valStatus.put("fallabela", false);
				}
				
				if (logger.isInfoEnabled()) {logger.info("Inserto MARCA - MODELO en DB Fallabela.");}				
			}
			catch (Exception e)
			{
				logger.error("Exception(" + e.getClass().getName() + ") - ERROR_FALLABELA: " + e.getMessage());
				logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			}
			
			try {
				int countRipley = 0;
				countRipley = marcaRipleyMapper.validateMarcar(codMarcaVehiculo);
				
				if(countRipley>0){
					valStatus.put("ripley", true); 
				}else{
					valStatus.put("ripley", false);
				}
				
				if (logger.isInfoEnabled()) {logger.info("Inserto MARCA - MODELO en DB Ripley.");}				
			}
			catch (Exception e)
			{
				logger.error("Exception(" + e.getClass().getName() + ") - ERROR_RIPLEY: " + e.getMessage());
				logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			}
	
			try {
				
				int countComparaBien = 0;				
				countComparaBien = marcaComparaBienMapper.validateMarcar(codMarcaVehiculo);
				
				if(countComparaBien>0){
					valStatus.put("comparabien", true); 
				}else{
					valStatus.put("comparabien", false);
				}
				
				if (logger.isInfoEnabled()) {logger.info("Inserto MARCA - MODELO en DB ComparaBien.");}		
			}
			catch (Exception e)
			{
				logger.error("Exception(" + e.getClass().getName() + ") - ERROR_COMPARABIEN: " + e.getMessage());
				logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			}
	
			try {
				int countSucursal = 0;
				countSucursal = marcaSucursalMapper.validateMarcar(codMarcaVehiculo);
				
				if(countSucursal>0){
					valStatus.put("sucursal", true); 
				}else{
					valStatus.put("sucursal", false);
				}
				
				if (logger.isInfoEnabled()) {logger.info("Inserto MARCA - MODELO en DB SUCURSAL.");}				
			}
			catch (Exception e)
			{
				logger.error("Exception(" + e.getClass().getName() + ") - ERROR_SUCURAL: " + e.getMessage());
				logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			}
			
			try {
				int countMultisocio = 0;
				countMultisocio = marcaMultisocioMapper.validateMarcar(codMarcaVehiculo);
				
				if(countMultisocio>0){
					valStatus.put("multisocios", true); 
				}else{
					valStatus.put("multisocios", false);
				}
				if (logger.isInfoEnabled()) {logger.info("Inserto MARCA - MODELO en DB MULTISOCIOS.");}				
			}
			catch (Exception e)
			{
				logger.error("Exception(" + e.getClass().getName() + ") - ERROR_MULTISOCIOS: " + e.getMessage());
				logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			}
	
		
			
		} catch (Exception e) {
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR_VALIDACION_MARCA: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
		}
		
		
		if(logger.isInfoEnabled()){logger.info("Fin");}
		
		return valStatus;
	}
	
	
	@Override
	public Map<String, Boolean> validacionModelo(String nombreModelo,String nombreMarca) {		
		Map<String, Boolean> valStatus = new HashMap<String, Boolean>();
		if(logger.isInfoEnabled()){logger.info("Inicio");}		
		if(logger.isDebugEnabled()){logger.info("Input [1.-"+nombreModelo+" 2.- "+ nombreMarca+"]");}
		
		try {
			int countDirecto = 0;
			countDirecto = modeloDirectoMapper.validateModelo(nombreModelo,nombreMarca);
			
			if(countDirecto>0){
				valStatus.put("directo", true); 
			}else{
				valStatus.put("directo", false);
			}
			if (logger.isInfoEnabled()) {logger.info("Inserto MARCA - MODELO en DB MULTISOCIOS.");}				
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR_MULTISOCIOS: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
		}
		
		
		try {
			int countFallabela = 0;
			countFallabela = modeloFalabellaMapper.validateModelo(nombreModelo,nombreMarca);
			
			if(countFallabela>0){
				valStatus.put("fallabela", true); 
			}else{
				valStatus.put("fallabela", false);
			}
			if (logger.isInfoEnabled()) {logger.info("Inserto MARCA - MODELO en DB MULTISOCIOS.");}				
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR_MULTISOCIOS: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
		}
		
		try {
			int countRipley = 0;
			countRipley = modeloRipleyMapper.validateModelo(nombreModelo,nombreMarca);
			
			if(countRipley>0){
				valStatus.put("ripley", true); 
			}else{
				valStatus.put("ripley", false);
			}
			if (logger.isInfoEnabled()) {logger.info("Inserto MARCA - MODELO en DB MULTISOCIOS.");}				
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR_MULTISOCIOS: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
		}
		
		try {
			int countComparaBien = 0;
			countComparaBien = modeloComparaBienMapper.validateModelo(nombreModelo,nombreMarca);
			
			if(countComparaBien>0){				
				valStatus.put("comparabien", true); 
			}else{
				valStatus.put("comparabien", false);
			}
			if (logger.isInfoEnabled()) {logger.info("Inserto MARCA - MODELO en DB MULTISOCIOS.");}				
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR_MULTISOCIOS: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
		}
		
		try {
			int countSucursal = 0;
			countSucursal = modeloSucursalMapper.validateModelo(nombreModelo,nombreMarca);
			
			if(countSucursal>0){				
				valStatus.put("sucursal", true); 
			}else{
				valStatus.put("sucursal", false);
			}
			if (logger.isInfoEnabled()) {logger.info("Inserto MARCA - MODELO en DB MULTISOCIOS.");}				
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR_MULTISOCIOS: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
		}
		
		try {
			int countMultisocios = 0;
			countMultisocios = modeloMultisocioMapper.validateModelo(nombreModelo,nombreMarca);
			
			if(countMultisocios>0){				
				valStatus.put("multisocios", true); 
			}else{
				valStatus.put("multisocios", false);
			}
			if (logger.isInfoEnabled()) {logger.info("Inserto MARCA - MODELO en DB MULTISOCIOS.");}				
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR_MULTISOCIOS: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
		}
		
		if(logger.isInfoEnabled()){logger.info("Fin");}

		return valStatus;
	}
	//Habilitando el ingreso del NUEVO MODELO de VEHICULO.
	@Override
	public Map<String, Long> registrarMarcaModeloVehiculo(String codCategoriaClase, String nomMarcaVehiculo, String nomModeloVehiculo) throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		Map<String, Long> respuestaMap = null;
		
		try
		{
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + codCategoriaClase + ", 2.-" + nomMarcaVehiculo + ", 3.-" + nomModeloVehiculo + "]");}
			
			if (codCategoriaClase.equals("MOTO")){
				codCategoriaClase = codCategoriaClase.equalsIgnoreCase(Constantes.COD_CATEGORIA_CLASE_MOTO) ? codCategoriaClase : null;
			}else{
				codCategoriaClase = codCategoriaClase.equalsIgnoreCase(Constantes.COD_CATEGORIA_CLASE_AUTOMOVIL) ? codCategoriaClase : null;
			}
			
			if (logger.isInfoEnabled()) {logger.info("Input [1.-" + codCategoriaClase + ", 2.-" + nomMarcaVehiculo + ", 3.-" + nomModeloVehiculo + "]");}
			
			MarcaVehiculo marcaVehiculo = new MarcaVehiculo();
			marcaVehiculo.setNombreMarcavehiculo(nomMarcaVehiculo);
			marcaVehiculo.setStatus(1);
			marcaMapper.insertSelective(marcaVehiculo);
			
			ModeloVehiculo modeloVehiculo = new ModeloVehiculo();
			modeloVehiculo.setNombreModelovehiculo(nomModeloVehiculo);
			modeloVehiculo.setMarcaVehiculo(marcaVehiculo.getId());
			modeloVehiculo.setCategoriaClaseVehiculo(codCategoriaClase);
			modeloVehiculo.setStatus(1);
			
			modeloMapper.insertSelective(modeloVehiculo);
			
			if (logger.isInfoEnabled()) {logger.info("Inserto MARCA - MODELO en DB AUTOMATIZACION.");}		
			respuestaMap = new HashMap<String, Long>();
			respuestaMap.put("marcaVehiculo", marcaVehiculo.getId());
			respuestaMap.put("modeloVehiculo", modeloVehiculo.getId());
			
			if (logger.isInfoEnabled()) {logger.info("Se genero respuesta al metodo.");}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_INSERTAR);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return respuestaMap;
	} //registrarMarcaModeloVehiculo
	
	@Override
	public Long registrarModeloVehiculo(String codCategoriaClase, Long codMarcaVehiculo, String nomModeloVehiculo) throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		Long pkModeloVehiculo = null;
		
		try
		{
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + codCategoriaClase + ", 2.-" + codMarcaVehiculo + ", 3.-" + nomModeloVehiculo + "]");}

			if (codCategoriaClase.equals("MOTO")){
			codCategoriaClase = codCategoriaClase.equalsIgnoreCase(Constantes.COD_CATEGORIA_CLASE_MOTO) ? codCategoriaClase : null;
			}else{
			codCategoriaClase = codCategoriaClase.equalsIgnoreCase(Constantes.COD_CATEGORIA_CLASE_AUTOMOVIL) ? codCategoriaClase : null;
			}
		
			if (logger.isInfoEnabled()) {logger.info("Input [1.-" + codCategoriaClase + ", 2.-" + codMarcaVehiculo + ", 3.-" + nomModeloVehiculo + "]");}
			
			
			ModeloVehiculo modeloVehiculo = new ModeloVehiculo();
			modeloVehiculo.setNombreModelovehiculo(nomModeloVehiculo);
			modeloVehiculo.setMarcaVehiculo(codMarcaVehiculo);
			modeloVehiculo.setCategoriaClaseVehiculo(codCategoriaClase);
			modeloVehiculo.setStatus(1);
			modeloMapper.insertSelective(modeloVehiculo);
			if (logger.isInfoEnabled()) {logger.info("Inserto el MODELO en DB de AUTOMATIZACION.");}			
			
			pkModeloVehiculo = modeloVehiculo.getId();
			
			if (logger.isInfoEnabled()) {logger.info("Se genero respuesta al metodo.");}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_INSERTAR);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return pkModeloVehiculo;
	} //registrarModeloVehiculo
	
	
	private TarifaModeloBean getFitTarifaModeloBean(List<TarifaModeloBean> tarifaModeloList)
	{
		if (logger.isDebugEnabled()) {logger.debug("Inicio - Fin");}
		TarifaModeloBean tarifaModelo = null;
		
		int length = 255;
		for (int i=0; i<tarifaModeloList.size(); i++)
		{
			if (tarifaModeloList.get(i).getNombreModelo().length() < length)
			{
				length = tarifaModeloList.get(i).getNombreModelo().length();
				tarifaModelo = tarifaModeloList.get(i);
			}
		}
		
		return tarifaModelo;
	} //getFitTarifaModeloBean

	

	
	
} //ManMarcaModeloServiceImpl
