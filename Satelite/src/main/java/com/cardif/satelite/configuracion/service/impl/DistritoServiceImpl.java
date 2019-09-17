package com.cardif.satelite.configuracion.service.impl;

import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.configuracion.dao.DistritoMapper;
import com.cardif.satelite.configuracion.service.DistritoService;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.Distrito;

@Service("distritoAutService")
public class DistritoServiceImpl implements DistritoService
{
	private final Logger logger = Logger.getLogger(DistritoServiceImpl.class);
	
	@Autowired
	private DistritoMapper distritoMapper;
	
	
	@Override
	public List<Distrito> buscarDistritos() throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		List<Distrito> listaDistritos = null;
		
		try
		{
			if (logger.isDebugEnabled()) {logger.debug("Input []");}
			
			listaDistritos = distritoMapper.selectAllDistrito();
			int size = (null != listaDistritos ? listaDistritos.size() : 0);
			
			if (logger.isInfoEnabled()) {logger.info("Output [Registros: " +size + "]");}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return listaDistritos;
	} //buscarDistritos
	
	@Override
	public Distrito buscarByPK(String codDistrito) throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		Distrito distrito = null;
		
		try
		{
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + codDistrito + "]");}
			
			distrito = distritoMapper.selectByPrimaryKey(codDistrito);
			if (logger.isInfoEnabled()) {logger.info("Output [Distrito: " + distrito +"]"); }
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_OBTENER);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return distrito;
	} //buscarByPK
	
	@Override
	public List<Distrito> buscarByCodProvincia(String codProvincia) throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		List<Distrito> listaDistritos = null;
		
		try
		{
			if (logger.isDebugEnabled()) {logger.debug("Input []");}
			
			listaDistritos = distritoMapper.selectByCodProvincia(codProvincia);
			int size = (null != listaDistritos ? listaDistritos.size() : 0);
			
			if (logger.isInfoEnabled()) {logger.info("Output [Registros: " +size + "]");}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return listaDistritos;
	} //buscarByCodProvincia
	
} //DistritoServiceImpl
