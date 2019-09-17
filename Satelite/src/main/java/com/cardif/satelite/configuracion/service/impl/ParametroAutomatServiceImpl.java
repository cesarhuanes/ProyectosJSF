package com.cardif.satelite.configuracion.service.impl;

import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.configuracion.dao.ParametroAutomatMapper;
import com.cardif.satelite.configuracion.service.ParametroAutomatService;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.ParametroAutomat;

@Service("parametroAutomatService")
public class ParametroAutomatServiceImpl implements ParametroAutomatService
{
	private final Logger logger = Logger.getLogger(ParametroAutomatServiceImpl.class);
	
	@Autowired
	private ParametroAutomatMapper parametroAutomatMapper;
	
	
	@Override
	public List<ParametroAutomat> buscarPorCodParam(String codParam) throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		List<ParametroAutomat> response = null;
		
		try
		{
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + codParam + "]");}
			response = parametroAutomatMapper.selectParametro(codParam);
			
			if (logger.isDebugEnabled()) {logger.debug("Output [Registros: " + (null != response ? response.size() : 0) + "]");}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return response;
	} //buscarPorCodParam
	
	@Override
	public ParametroAutomat obtener(String codParam, String codValor) throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		ParametroAutomat response = null;
		
		try
		{
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + codParam + "]");}
			response = parametroAutomatMapper.getParametro(codParam, codValor);
			
			if (logger.isDebugEnabled()) {logger.debug("Output [ParametroAutomat: " + response +"]");}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_OBTENER);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return response;
	} //obtener
	
	@Override
	public void actualizar(ParametroAutomat parametro) throws SyncconException
	{
		if (logger.isDebugEnabled()) {logger.debug("Inicio");}
		
		try
		{
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + parametro.getCodParam() + ", 2.-" + parametro.getCodValor() + ", 3.-" + parametro.getNombreValor() + ", 4.-" + parametro.getNumOrden() + "]");}
			
			int rows = parametroAutomatMapper.updateByPrimaryKeySelective(parametro);
			if (logger.isInfoEnabled()) {logger.info("Output [Registros: " + rows + "]");}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_ACTUALIZAR);
		}
		if (logger.isDebugEnabled()) {logger.debug("Fin");}
	} //actualizar
	
	@Override
	public void actualizar(String codParam, String codValor, String nombreValor, Integer numOrden) throws SyncconException
	{	
		if (logger.isDebugEnabled()) {logger.debug("Inicio");}
		
		try
		{
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + codParam + ", 2.-" + codValor + ", 3.-" + nombreValor + ", 4.-" + numOrden + "]");}
			
			ParametroAutomat parametro = parametroAutomatMapper.getParametro(codParam, codValor);
			
			if (null == parametro)
			{
				throw new SyncconException(ErrorConstants.COD_ERROR_BD_ACTUALIZAR);
			}
			
			parametro.setNombreValor(nombreValor);
			parametro.setNumOrden(numOrden);
				
			int rows = parametroAutomatMapper.updateByPrimaryKeySelective(parametro);
			if (logger.isInfoEnabled()) {logger.info("Output [Registros: " + rows + "]");}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_ACTUALIZAR);
		}
		if (logger.isDebugEnabled()) {logger.debug("Fin");}
	} //actualizar
	
} //ParametroAutomatServiceImpl
