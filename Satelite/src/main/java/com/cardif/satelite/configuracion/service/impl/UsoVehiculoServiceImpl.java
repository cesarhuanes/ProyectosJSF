package com.cardif.satelite.configuracion.service.impl;

import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.configuracion.dao.UsoVehiculoMapper;
import com.cardif.satelite.configuracion.service.UsoVehiculoService;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.UsoVehiculo;

@Service("usoVehiculoConfService")
public class UsoVehiculoServiceImpl implements UsoVehiculoService
{
	private final Logger logger = Logger.getLogger(UsoVehiculoServiceImpl.class);
	
	@Autowired
	private UsoVehiculoMapper usoVehiculoMapper;
	
	
	@Override
	public UsoVehiculo buscarByPK(String codUso) throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		UsoVehiculo usoVehiculo = null;
		
		try
		{
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + codUso + "]");}
			usoVehiculo = usoVehiculoMapper.selectByPrimaryKey(codUso);
			
			if (logger.isInfoEnabled()) {logger.info("Output [UsoVehiculo: " + usoVehiculo + "]");}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_OBTENER);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return usoVehiculo;
	} //buscarByPK
	
	@Override
	public List<UsoVehiculo> buscarUsoVehiculos() throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		List<UsoVehiculo> listaUsoVehiculos = null;
		
		try
		{
			listaUsoVehiculos = usoVehiculoMapper.selectAllUsoVehiculos();
			int size = (null != listaUsoVehiculos ? listaUsoVehiculos.size() : 0);
			
			if (logger.isInfoEnabled()) {logger.info("Output [Registros: " + size + "]");}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return listaUsoVehiculos;
	} //buscarUsoVehiculos
	
} //UsoVehiculo
