package com.cardif.satelite.reportes.service.impl;

import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.reportes.bean.RepSunatBean;
import com.cardif.satelite.reportes.bean.RepSunatCabeceraBean;
import com.cardif.satelite.reportes.service.RepSunatService;
import com.cardif.satelite.suscripcion.dao.DetalleTramaDiariaMapper;

@Service("repSunatService")
public class RepSunatServiceImpl implements RepSunatService
{
	private final Logger logger = Logger.getLogger(RepSunatServiceImpl.class);
	
	@Autowired
	private DetalleTramaDiariaMapper detTramaDiariaMapper;
	
	
	@Override
	public List<RepSunatCabeceraBean> buscarReporteSunatCabecera(Integer anioPeriodo) throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		List<RepSunatCabeceraBean> listaSunatCabecera = null;
		
		try
		{
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + anioPeriodo + "]");}
			
			listaSunatCabecera = detTramaDiariaMapper.selectReporteSunatCabecera(anioPeriodo);
			if (logger.isInfoEnabled()) {logger.info("Output [Registros: " + (null != listaSunatCabecera ? listaSunatCabecera.size() : 0) + "]");}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return listaSunatCabecera;
	} //buscarReporteSunatCabecera
	
	@Override
	public List<RepSunatBean> buscarPolizasReporteSunat(Integer anioPeriodo) throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		List<RepSunatBean> listaReporteSunat = null;
		
		try
		{
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + anioPeriodo + "]");}
			
			listaReporteSunat = detTramaDiariaMapper.selectPolizasReporteSunat(anioPeriodo);
			if (logger.isInfoEnabled()) {logger.info("Output [Registros: " + (null != listaReporteSunat ? listaReporteSunat.size() : 0) + "]");}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return listaReporteSunat;
	} //buscarPolizasReporteSunat
	
} //RepSunatServiceImpl
