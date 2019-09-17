package com.cardif.satelite.configuracion.service.impl;

import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.configuracion.dao.ModeloVehiculoMapper;
import com.cardif.satelite.configuracion.service.ModeloVehiculoService;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.ModeloVehiculo;

@Service("modeloVehiculoConfService")
public class ModeloVehiculoServiceImpl implements ModeloVehiculoService
{
	private final Logger logger = Logger.getLogger(ModeloVehiculoServiceImpl.class);
	
	@Autowired
	private ModeloVehiculoMapper modeloVehiculoMapper;
	
	
	@Override
	public ModeloVehiculo buscarPorPK(Long codUso) throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		ModeloVehiculo modeloVehiculo = null;
		
		try
		{
			if (logger.isInfoEnabled()) {logger.info("Input [1.-" + codUso + "]");}
			modeloVehiculo = modeloVehiculoMapper.selectByPrimaryKey(codUso);
			
			if (logger.isInfoEnabled()) {logger.info("Output [ModeloVehiculo: " + modeloVehiculo + "]");}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return modeloVehiculo;
	} //buscarPorPK
	
	@Override
	public List<ModeloVehiculo> buscarPorMarcaVehiculoCategoriaClase(Long pkMarcaVehiculo) throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		List<ModeloVehiculo> listaModeloVehiculo = null;
		
		try
		{
			if (logger.isInfoEnabled()) {logger.info("Input [1.-" + pkMarcaVehiculo + "]");}
			
			/*
			 * PK CATEGORIA_CLASE
			 * - MOTO 	: MOTO
			 * - NULL 	: OTROS AUTOMOVILES
			 */
			if (logger.isInfoEnabled()) {logger.info("Input [1.-" + pkMarcaVehiculo + "]");}
			
			listaModeloVehiculo = modeloVehiculoMapper.selectByPkMarcaVehiculo(pkMarcaVehiculo);
			int size = (null != listaModeloVehiculo ? listaModeloVehiculo.size() : 0);
			
			if (logger.isInfoEnabled()) {logger.info("Output [Registros: " + size + "]");}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return listaModeloVehiculo;
	} //buscarPorPkMarcaVehiculo
	
} //ModeloVehiculoServiceImpl
