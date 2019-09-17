package com.cardif.satelite.tesoreria.service.impl;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.tesoreria.bean.ConsultaCertRetencionReversa;
import com.cardif.satelite.tesoreria.dao.ComprobanteElectronicoMapper;
import com.cardif.satelite.tesoreria.model.ComprobanteElectronico;
import com.cardif.satelite.tesoreria.service.DiarioServices;
import com.cardif.satelite.tesoreria.service.GeneracionReversaComprobantesService;
import com.cardif.sunsystems.util.ConstantesSun;

@Service("genReversaComprobantesService")
public class GeneracionReversaComprobantesServiceImpl implements GeneracionReversaComprobantesService
{
	private final Logger logger = Logger.getLogger(GeneracionReversaComprobantesServiceImpl.class);
	
	@Autowired
	private ComprobanteElectronicoMapper comprobanteElectronicoMapper;
	
	@Autowired
	private DiarioServices diarioServices;
	
	
	@Override
	public List<ConsultaCertRetencionReversa> buscarCertificadosRetencionReversar(String idUnidadNegocio, Date fecEmision, String nroCertificado, String rucProveedor) 
			throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		List<ConsultaCertRetencionReversa> listaCertRetencionReversa = null;
		
		try
		{
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + idUnidadNegocio + ", 2.-" + fecEmision + ", 3.-" + nroCertificado + ", 4.-" + rucProveedor + "]");}
			idUnidadNegocio = StringUtils.isNotBlank(idUnidadNegocio) ? MessageFormat.format(ConstantesSun.SSC_BUSINESS_UNIT_PATTERN, idUnidadNegocio) : null;
			nroCertificado = StringUtils.isNotBlank(nroCertificado) ? nroCertificado : null;
			rucProveedor = StringUtils.isNotBlank(rucProveedor) ? rucProveedor : null;
			String fechaEmision = new SimpleDateFormat("dd/MM/yyyy").format(fecEmision);
			
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + idUnidadNegocio + ", 2.-" + fecEmision + ", 3.-" + nroCertificado + ", 4.-" + rucProveedor + "]");}
			
			listaCertRetencionReversa = comprobanteElectronicoMapper.selectCertificadoRetencionReversar(idUnidadNegocio, fechaEmision, nroCertificado, rucProveedor);
			if (logger.isInfoEnabled()) {logger.info("Output [Registros: " + (null != listaCertRetencionReversa ? listaCertRetencionReversa.size() : 0)+ "]");}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return listaCertRetencionReversa;
	} //buscarCertificadosRetencionReversar
	
	@Override
	public void actualizarEstadoCertRetencionReversa(List<ConsultaCertRetencionReversa> listaCertRetencionReversa, String estadoCertificadoRetencion, Integer plazoFechaSunat)
			throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		try
		{
			for (ConsultaCertRetencionReversa certRetencion : listaCertRetencionReversa)
			{
				ComprobanteElectronico compElectronico = comprobanteElectronicoMapper.selectByPrimaryKey(certRetencion.getNroComprobanteEle());
				if (logger.isDebugEnabled()) {logger.debug("Se encontro el objeto ComprobanteElectronico(" + certRetencion.getNroComprobanteEle() + "): " + compElectronico);}
				
				compElectronico.setEstado(estadoCertificadoRetencion);
				compElectronico.setPlazoFechaSunat(plazoFechaSunat);
				int rows = comprobanteElectronicoMapper.updateByPrimaryKeySelective(compElectronico);
				if (logger.isDebugEnabled()) {logger.debug("Se actualizo el objecto ComprobanteElectronico(" + certRetencion.getNroComprobanteEle() + "). Filas afectadas: " + rows);}
			}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_ACTUALIZAR);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
	} //actualizarEstadoCertRetencion

	@Override
	public boolean actualizarEstadoSunsystems(List<ConsultaCertRetencionReversa> listaCertRetencionReversa, String estadoCertificadoRetencion) throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		boolean isOk = true;
		try
		{
			List<Boolean> flagList = new ArrayList<Boolean>();

			for (ConsultaCertRetencionReversa certRetencion : listaCertRetencionReversa)
			{
				String nroCertificado = certRetencion.getNroComprobanteEle();
				String nroDiario = certRetencion.getNroDiario();
				String unidadNegocio = certRetencion.getUnidadNegocio();
				
				if (logger.isDebugEnabled()) {logger.debug("Se extrajo los datos ha actualiza. nroCertificado: " + nroCertificado + " nroDiario: " + nroDiario + " unidadNegocio: " + unidadNegocio + " estado: " + estadoCertificadoRetencion);}
				
				boolean flag = diarioServices.actualizaAsientoPagoRetencion(unidadNegocio, nroDiario, estadoCertificadoRetencion, nroCertificado);
				if (logger.isInfoEnabled()) {logger.info("Se actualizaron los datos en Sunsystems para el Nro_Diario: " + nroDiario + " Nro_Certificado: " + nroCertificado + "\tRespuesta: " + flag);}
				
				flagList.add(flag);
			}
			
			for (Boolean flag : flagList)
			{
				if (!flag) {isOk = false; break;}
			}
		}
		catch (SyncconException e)
		{
			logger.error("SyncconException - ERROR: " + e.getMessage());
			throw e;
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_ACTUALIZAR);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return isOk;
	} //actualizarEstadoSunsystems
	
} //GeneracionReversaComprobantesServiceImpl
