package com.cardif.satelite.reportes.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.reportes.DetalleReporteAPESEG;
import com.cardif.satelite.reportes.bean.RepConsultaAPESEGBean;
import com.cardif.satelite.reportes.dao.DetalleReporteAPESEGMapper;
import com.cardif.satelite.reportes.service.RepConsultaAPESEGService;
import com.cardif.satelite.suscripcion.dao.DetalleTramaDiariaMapper;
import com.cardif.satelite.util.SateliteUtil;

@Service("repConsultaAPESEGService")
public class RepConsultaAPESEGServiceImpl implements RepConsultaAPESEGService
{
	private final Logger logger = Logger.getLogger(RepConsultaAPESEGServiceImpl.class);
	
	@Autowired
	private DetalleTramaDiariaMapper detalleTramaDiariaMapper;
	
	@Autowired
	private DetalleReporteAPESEGMapper detalleReporteAPESEGMapper;
	
	
	@Override
	public List<RepConsultaAPESEGBean> buscarPolizasParaAPESEG(Date fecRegistroDesde, Date fecRegistroHasta) throws SyncconException
	{
		if (logger.isDebugEnabled()) {logger.debug("Inicio");}
		List<RepConsultaAPESEGBean> listaConsultaAPESEG = null; 
		
		try
		{
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + fecRegistroDesde + ", 2.-" + fecRegistroHasta + "]");}
			fecRegistroDesde = (null != fecRegistroDesde ? fecRegistroDesde : SateliteUtil.getInitialCardifDate());
			fecRegistroHasta = (null != fecRegistroHasta ? fecRegistroHasta : new Date());
			
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + fecRegistroDesde + ", 2.-" + fecRegistroHasta + "]");}
			
			listaConsultaAPESEG = detalleTramaDiariaMapper.selectPolizasForAPESEG(fecRegistroDesde, fecRegistroHasta);
			if (logger.isInfoEnabled()) {logger.info("Output [Registros: " + (null != listaConsultaAPESEG ? listaConsultaAPESEG.size() : 0) + "]");}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
		}
		if (logger.isDebugEnabled()) {logger.debug("Fin");}
		return listaConsultaAPESEG;
	} //buscarReporteAPESEG
	
	@Override
	public DetalleReporteAPESEG actualizarReportadoAPESEG(Long idReporteApeseg, String reportadoOpcion) throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		DetalleReporteAPESEG detalleReporteApeseg = null;
		
		try
		{
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + idReporteApeseg + ", 2.-" + reportadoOpcion + "]");}
			
			detalleReporteApeseg = detalleReporteAPESEGMapper.selectByPrimaryKey(idReporteApeseg);
			if (null == detalleReporteApeseg)
			{
				throw new SyncconException(ErrorConstants.COD_ERROR_BD_ACTUALIZAR);
			}
			
			detalleReporteApeseg.setReportado(reportadoOpcion);
			
			int rows = detalleReporteAPESEGMapper.updateByPrimaryKeySelective(detalleReporteApeseg);
			if (logger.isInfoEnabled()) {logger.info("Output [Registros:" + rows + "]");}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_ACTUALIZAR);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return detalleReporteApeseg;
	} //actualizarReportadoAPESEG
	
} //RepConsultaAPESEGServiceImpl
