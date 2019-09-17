package com.cardif.satelite.configuracion.service.impl;

import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.configuracion.dao.MarcaVehiculoMapper;
import com.cardif.satelite.configuracion.service.MarcaVehiculoService;
import com.cardif.satelite.constantes.Constantes;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.MarcaVehiculo;

@Service("marcaVehiculoConfService")
public class MarcaVehiculoServiceImpl implements MarcaVehiculoService
{
	private final Logger logger = Logger.getLogger(MarcaVehiculoServiceImpl.class);
	
	@Autowired
	private MarcaVehiculoMapper marcaVehiculoMapper;
	
	
	@Override
	public MarcaVehiculo buscarPorPK(Long id) throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		MarcaVehiculo marcaVehiculo = null;
		
		try
		{
			if (logger.isInfoEnabled()) {logger.info("Input [1.-" + id + "]");}
			marcaVehiculo = marcaVehiculoMapper.selectByPrimaryKey(id);
			
			if (logger.isInfoEnabled()) {logger.info("Output [MarcaVehiculo: " + marcaVehiculo + "]");}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return marcaVehiculo;
	} //buscarPorPK
	
	@Override
	public List<MarcaVehiculo> buscarPorPkCategoriaClase(String pkCategoriaClase) throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		List<MarcaVehiculo> listaMarcaVehiculo = null;
		
		try
		{
			if (logger.isInfoEnabled()) {logger.info("Input [1.-" + pkCategoriaClase + "]");}
			
			/*
			 * PK CATEGORIA_CLASE
			 * - MOTO 	: MOTO
			 * - NULL 	: OTROS AUTOMOVILES
			 */
			if (pkCategoriaClase.equalsIgnoreCase("MOTO")){
				pkCategoriaClase = Constantes.COD_CATEGORIA_CLASE_MOTO;
			}else{
				pkCategoriaClase = Constantes.COD_CATEGORIA_CLASE_AUTOMOVIL;
			}
					
			logger.debug("pkCategoriaClase :"+pkCategoriaClase);
			
			if (logger.isInfoEnabled()) {logger.info("Input [1.-" + pkCategoriaClase + "]");}
			
			listaMarcaVehiculo = marcaVehiculoMapper.selectByPkCategoriaClase(pkCategoriaClase);
			int size = (null != listaMarcaVehiculo ? listaMarcaVehiculo.size() : 0);
			
			if (logger.isInfoEnabled()) {logger.info("Output [Registros: " + size + "]");}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		
		
		
		return listaMarcaVehiculo;
	} //buscarPorCategoriaClase
	
} //MarcaVehiculoServiceImpl
