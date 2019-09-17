package com.cardif.satelite.configuracion.service.impl;

import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.configuracion.dao.ProvinciaMapper;
import com.cardif.satelite.configuracion.service.ProvinciaService;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.Provincia;

@Service("provinciaAutService")
public class ProvinciaServiceImpl implements ProvinciaService
{
	private final Logger logger = Logger.getLogger(ProvinciaServiceImpl.class);
	
	@Autowired
	private ProvinciaMapper provinciaMapper;
	
	
	@Override
	public List<Provincia> buscarProvincias() throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		List<Provincia> listaProvincias = null;
		
		try
		{
			if (logger.isDebugEnabled()) {logger.debug("Input []");}
			
			listaProvincias = provinciaMapper.selectAllProvincias();
			int size = (null != listaProvincias ? listaProvincias.size() : 0);
			
			if (logger.isInfoEnabled()) {logger.info("Output [Registros: " +size + "]");}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return listaProvincias;
	} //buscarProvincias
	
	@Override
	public Provincia buscarByPK(String codProvincia) throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		Provincia provincia = null;
		
		try
		{
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + codProvincia + "]");}
			
			provincia = provinciaMapper.selectByPrimaryKey(codProvincia);
			if (logger.isInfoEnabled()) {logger.info("Output [Provincia: " + provincia +"]"); }
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_OBTENER);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return provincia;
	} //buscarByPK
	
	@Override
	public List<Provincia> buscarByCodDepartamento(String codDepartamento) throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		List<Provincia> listaProvincias = null;
		
		try
		{
			if (logger.isDebugEnabled()) {logger.debug("Input []");}
			
			listaProvincias = provinciaMapper.selectByCodDepartamento(codDepartamento);
			int size = (null != listaProvincias ? listaProvincias.size() : 0);
			
			if (logger.isInfoEnabled()) {logger.info("Output [Registros: " +size + "]");}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return listaProvincias;
	} //buscarByCodDepartamento
	
} //ProvinciaServiceImpl
