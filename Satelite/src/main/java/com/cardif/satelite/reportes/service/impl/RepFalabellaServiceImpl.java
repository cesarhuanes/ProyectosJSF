package com.cardif.satelite.reportes.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.reportes.bean.RepFalabellaBean;
import com.cardif.satelite.reportes.service.RepFalabellaService;
import com.cardif.satelite.suscripcion.dao.DetalleTramaDiariaMapper;

@Service("repFalabellaService")
public class RepFalabellaServiceImpl implements RepFalabellaService
{
	private final Logger logger = Logger.getLogger(RepFalabellaServiceImpl.class);
	
	@Autowired
	private DetalleTramaDiariaMapper detTramaDiariaMapper;
	
	
	@Override
	public List<RepFalabellaBean> buscarPolizasDespacho(Date fecVentaDesde, Date fecVentaHasta) throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		List<RepFalabellaBean> listaReporteFalabella = null;
		
		try
		{
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + fecVentaDesde + ", 2.-" + fecVentaHasta + "]");}
			listaReporteFalabella = detTramaDiariaMapper.selectPolizasFalabellaAltas(fecVentaDesde, fecVentaHasta);
			
			if (logger.isInfoEnabled()) {logger.info("Output [Registros:" + (null != listaReporteFalabella ? listaReporteFalabella.size() : 0) + "]");}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return listaReporteFalabella;
	} //buscarPolizasDespacho
	
	@Override
	public List<RepFalabellaBean> buscarPolizasAnulacion(Date fecVentaDesde, Date fecVentaHasta) throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		List<RepFalabellaBean> listaReporteFalabella = null;
		
		try
		{
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + fecVentaDesde + ", 2.-" + fecVentaHasta + "]");}
			listaReporteFalabella = detTramaDiariaMapper.selectPolizasFalabellaBajas(fecVentaDesde, fecVentaHasta);
			
			if (logger.isInfoEnabled()) {logger.info("Output [Registros:" + (null != listaReporteFalabella ? listaReporteFalabella.size() : 0) + "]");}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return listaReporteFalabella;
	} //buscarPolizasAnulacion
	
} //RepFalabellaServiceImpl
