package com.cardif.satelite.configuracion.service.impl;

import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.configuracion.dao.CanalProductoMapper;
import com.cardif.satelite.configuracion.service.CanalProductoService;
import com.cardif.satelite.constantes.Constantes;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.CanalProducto;

@Service("canalProductoService")
public class CanalProductoServiceImpl implements CanalProductoService
{
	private final Logger logger = Logger.getLogger(CanalProductoServiceImpl.class);
	
	@Autowired
	private CanalProductoMapper canalProductoMapper;
	
	
	@Override
	public List<CanalProducto> buscarPorEstado(int estado) throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		List<CanalProducto> listaCanalProductos = null;
		
		try
		{
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + estado + "]");}
			listaCanalProductos = canalProductoMapper.selectByEstado(estado);
			
			if (logger.isInfoEnabled()) {logger.info("Output [Registros: " + (null != listaCanalProductos ? listaCanalProductos.size() : 0) + "]");}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return listaCanalProductos;
	} //buscarPorEstado
	
	@Override
	public List<CanalProducto> buscarPorCodSocio(Integer modSuscripcionOpcion, Long codSocio) throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		List<CanalProducto> listCanalProducto = null;
		
		try
		{
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + modSuscripcionOpcion + ", 2.-" + codSocio + "]");}
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + modSuscripcionOpcion + ", 2.-" + Constantes.SOCIO_ESTADO_ACTIVO + ", 3.-" + Constantes.PRODUCTO_ESTADO_ACTIVO +", 4.-" + Constantes.CANAL_PROD_ESTADO_ACTIVO + ", 5.-" + codSocio + "]");}
			
			listCanalProducto = canalProductoMapper.selectByCodSocio(modSuscripcionOpcion, Constantes.SOCIO_ESTADO_ACTIVO, Constantes.PRODUCTO_ESTADO_INACTIVO, Constantes.CANAL_PROD_ESTADO_ACTIVO, codSocio);
			if (logger.isInfoEnabled()) {logger.info("Output [Registros: " + (null != listCanalProducto ? listCanalProducto.size() : 0) + "]");}
			
			for (CanalProducto canalProducto : listCanalProducto)
			{
				logger.info("" + canalProducto.getId() + "" + canalProducto.getNombreCanal());
			}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return listCanalProducto;
	} //buscarPorCodSocio
	
} //CanalProductoServiceImpl
