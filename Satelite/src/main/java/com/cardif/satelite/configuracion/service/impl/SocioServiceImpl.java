package com.cardif.satelite.configuracion.service.impl;

import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.configuracion.dao.SocioMapper;
import com.cardif.satelite.configuracion.service.SocioService;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.Socio;

@Service("socioConfService")
public class SocioServiceImpl implements SocioService
{
	private final Logger logger = Logger.getLogger(SocioServiceImpl.class);
	
	@Autowired
	private SocioMapper socioMapper;
	
	
	@Override
	public Socio insert(Socio socio) throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		try
		{
			int rows = socioMapper.insert(socio);
			if (logger.isDebugEnabled()) {logger.debug("El SOCIO[" + socio.getId() + "] fue registrado correctamente. Filas afectadas: " + rows);}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_INSERTAR);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return socio;
	} //insert
	
	@Override
	public Socio getSocioByRuc(String rucSocio) throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		Socio socio = null;
		
		try
		{
			socio = socioMapper.selectByRuc(rucSocio);
			if (logger.isDebugEnabled()) {logger.debug("El SOCIO[" + (null != socio ? socio.getId() : -1)+ "] fue encontrado.");}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return socio;
	} //getSocioByRuc
	
	@Override
	public List<Socio> buscarPorModSuscripcionEstadoProdEstadoSocio(Integer modSuscripcionOpcion, Integer estadoProducto, Integer estadoSocio) throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		List<Socio> listSocio = null;
		
		try
		{
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + modSuscripcionOpcion + ", 2.-" + estadoProducto + "3.-" + estadoSocio + "]");}
			
			listSocio = socioMapper.selectByModSuscripcionAndEstadoProdAndEstadoSocio(modSuscripcionOpcion, estadoProducto, estadoSocio);
			if (logger.isInfoEnabled()) {logger.info("Output [Registros: " + (null != listSocio ? listSocio.size() : 0) + "]");}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return listSocio;
	} //buscarPorModSuscripcionEstadoProdEstadoSocio
	
	@Override
	public List<Socio> buscarTodosSocios() throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		List<Socio> listaSocio = null;
		
		try
		{
			listaSocio = socioMapper.selectAllSocios();
			if (logger.isInfoEnabled()) {logger.info("Output [Registros: " + (null != listaSocio ? listaSocio.size() : 0) + "]");}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return listaSocio;
	} //buscarTodosSocios

	@Override
	public List<Socio> buscarSociosPorGrupoComercio(String grupoComercio) throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		List<Socio> listaSocio = null;
		
		try
		{
			listaSocio = socioMapper.selectSociosByGrupoComercio(grupoComercio);
			if (logger.isInfoEnabled()) {logger.info("Output [Registros: " + (null != listaSocio ? listaSocio.size() : 0) + "]");}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return listaSocio;
	} //buscarSociosPorGrupoComercio

	@Override
	public List<Socio> buscarSocio(Integer estado) throws SyncconException {
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		List<Socio> listSocio = null;
		
		try
		{
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + estado + "]");}
			
			listSocio = socioMapper.selectBySocioEstadoProdAndEstadoSocio(estado);
			if (logger.isInfoEnabled()) {logger.info("Output [Registros: " + (null != listSocio ? listSocio.size() : 0) + "]");}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return listSocio;
	}
	
} //SocioServiceImpl
