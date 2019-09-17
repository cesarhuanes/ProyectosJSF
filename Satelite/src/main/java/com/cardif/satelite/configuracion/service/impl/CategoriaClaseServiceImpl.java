package com.cardif.satelite.configuracion.service.impl;

import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.configuracion.dao.CategoriaClaseMapper;
import com.cardif.satelite.configuracion.service.CategoriaClaseService;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.CategoriaClase;

@Service("categoriaClaseService")
public class CategoriaClaseServiceImpl implements CategoriaClaseService
{
	private final Logger logger = Logger.getLogger(CategoriaClaseServiceImpl.class);
	
	@Autowired
	private CategoriaClaseMapper categoriaClaseMapper;
	
	
	@Override
	public List<CategoriaClase> buscarTodos() throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		List<CategoriaClase> listaCategoriaClase = null;
		
		try
		{
			listaCategoriaClase = categoriaClaseMapper.selectAllCategoriaClases();
			int size = (null != listaCategoriaClase && 0 < listaCategoriaClase.size()) ? listaCategoriaClase.size() : 0;
			
			if (logger.isInfoEnabled()) {logger.info("Output [Registros: " + size + "]");}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return listaCategoriaClase;
	} //buscarTodos
	
	@Override
	public CategoriaClase buscarByPK(String id) throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		CategoriaClase categoriaClase = null;
		
		try
		{
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + id + "]");}
			categoriaClase = categoriaClaseMapper.selectByPrimaryKey(id);
			
			if (logger.isInfoEnabled()) {logger.info("Output [CategoriaClase: " + categoriaClase + "]");}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return categoriaClase;
	} //buscarByPK
	
	@Override
	public CategoriaClase buscarByDescripcion(String descripcion) throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		CategoriaClase categoriaClase = null;
		
		try
		{
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + descripcion + "]");}
			categoriaClase = categoriaClaseMapper.selectByDescripcion(descripcion);
			
			if (logger.isInfoEnabled()) {logger.info("Output [CategoriaClase: " + categoriaClase + "]");}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return categoriaClase;
	} //buscarByNombre
	
} //CategoriaClaseServiceImpl
