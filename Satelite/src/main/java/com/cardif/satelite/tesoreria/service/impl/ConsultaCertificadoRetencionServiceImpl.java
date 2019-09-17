package com.cardif.satelite.tesoreria.service.impl;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.tesoreria.dao.ComprobanteElectronicoMapper;
import com.cardif.satelite.tesoreria.model.ComprobanteElectronico;
import com.cardif.satelite.tesoreria.service.ConsultaCertificadoRetencionService;
import com.cardif.sunsystems.util.ConstantesSun;

@Service("conCertificadoRetencionService")
public class ConsultaCertificadoRetencionServiceImpl implements ConsultaCertificadoRetencionService
{
	private final Logger logger = Logger.getLogger(ConsultaCertificadoRetencionServiceImpl.class);
	
	@Autowired
	private ComprobanteElectronicoMapper comprobanteElectronicoMapper;
	
	
	@Override
	public List<ComprobanteElectronico> buscarCertificadosRetencion(String idUnidadNegocio, Date fecEmisionDesde, Date fecEmisionHasta, String nroCertificadoDesde, String nroCertificadoHasta, String codEstadoCertificado)
			throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		List<ComprobanteElectronico> listaComprobanteElectronico = null;
		
		try
		{
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + idUnidadNegocio + ", 2.-" + fecEmisionDesde + ", 3.-" + fecEmisionHasta + ", 4.-" + nroCertificadoDesde + ", 5.-" + nroCertificadoHasta + ", 6.-" + codEstadoCertificado + "]");}
			idUnidadNegocio = StringUtils.isNotBlank(idUnidadNegocio) ? MessageFormat.format(ConstantesSun.SSC_BUSINESS_UNIT_PATTERN, idUnidadNegocio) : null;
			fecEmisionDesde = DateUtils.truncate(fecEmisionDesde, Calendar.DAY_OF_MONTH);
			fecEmisionHasta = DateUtils.truncate(fecEmisionHasta, Calendar.DAY_OF_MONTH);
			nroCertificadoDesde = StringUtils.isNotBlank(nroCertificadoDesde) ? nroCertificadoDesde : null;
			nroCertificadoHasta = StringUtils.isNotBlank(nroCertificadoHasta) ? nroCertificadoHasta : null;
			codEstadoCertificado = StringUtils.isNotBlank(codEstadoCertificado) ? codEstadoCertificado : null;
			
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + idUnidadNegocio + ", 2.-" + fecEmisionDesde + ", 3.-" + fecEmisionHasta + ", 4.-" + nroCertificadoDesde + ", 5.-" + nroCertificadoHasta + ", 6.-" + codEstadoCertificado + "]");}
			
			listaComprobanteElectronico = comprobanteElectronicoMapper.selectCertificadoRetencion(idUnidadNegocio, fecEmisionDesde, fecEmisionHasta, nroCertificadoDesde, nroCertificadoHasta, codEstadoCertificado);
			if (logger.isInfoEnabled()) {logger.info("Output [Registros: " + (null != listaComprobanteElectronico ? listaComprobanteElectronico.size() : 0) + "]");}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return listaComprobanteElectronico;
	} //buscarCertificadosRetencion
	
} //ConsultaCertificadoRetencionServiceImpl
