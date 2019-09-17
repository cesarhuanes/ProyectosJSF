package com.cardif.satelite.configuracion.service.impl;

import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.configuracion.dao.DepartamentoMapper;
import com.cardif.satelite.configuracion.service.DepartamentoService;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.Departamento;

@Service("departamentoAutService")
public class DepartamentoServiceImpl implements DepartamentoService
{
	private final Logger logger = Logger.getLogger(DepartamentoServiceImpl.class);
	
	@Autowired
	private DepartamentoMapper departamentoMapper;
	
	
	@Override
	public List<Departamento> buscarDepartamentos() throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		List<Departamento> listaDepartamentos = null;
		
		try
		{
			if (logger.isDebugEnabled()) {logger.debug("Input []");}
			
			listaDepartamentos = departamentoMapper.selectAllDepartamentos();
			int size = (null != listaDepartamentos ? listaDepartamentos.size() : 0);
			
			if (logger.isInfoEnabled()) {logger.info("Output [Registros: " + size + "]");}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return listaDepartamentos;
	} //buscarDepartamentos
	
	@Override
	public Departamento buscarByPK(String codDepartamento) throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		Departamento departamento = null;
		
		try
		{
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + codDepartamento + "]");}			
			departamento = departamentoMapper.selectByPrimaryKey(codDepartamento);
			if (logger.isInfoEnabled()) {logger.info("Output [Departamento: " + departamento +"]"); }
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_OBTENER);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return departamento;
	} //buscarByPK
	
} //DepartamentoServiceImpl
