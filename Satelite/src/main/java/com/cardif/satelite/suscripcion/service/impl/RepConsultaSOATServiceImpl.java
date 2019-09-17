package com.cardif.satelite.suscripcion.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.suscripcion.bean.RepPolizasSOATBean;
import com.cardif.satelite.suscripcion.dao.ConciTramaDiariaMapper;
import com.cardif.satelite.suscripcion.service.RepConsultaSOATService;
import com.cardif.satelite.util.SateliteUtil;

@Service("repConsultaSOATService")
public class RepConsultaSOATServiceImpl implements RepConsultaSOATService
{
	private final Logger logger = Logger.getLogger(RepConsultaSOATServiceImpl.class);
	
	@Autowired
	private ConciTramaDiariaMapper conciTramaDiariaMapper;
	
	
	/**
	 * Este metodo busca la lista de polizas de los diferentes SOCIOS, almacenadas en la tabla CONCI_TRAMA_DIARIA.
	 */
	@Override
	public List<RepPolizasSOATBean> buscarPolizasSOAT(String codSocio, String numPlaca, String numCertificado, String numDocumentoID, String nombreContratante, 
			Date fechaVentaDesde, Date fechaVentaHasta) throws SyncconException
	{
		if (logger.isDebugEnabled()) {logger.debug("Inicio");}
		List<RepPolizasSOATBean> listaPolizasSOAT = null;
		
		try
		{
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + codSocio + ", 2.-" + numPlaca + ", 3.-" + numCertificado + ", 4.-" + numDocumentoID + ", 5.-" + nombreContratante + ", 6.-" + fechaVentaDesde + ", 7.-" + fechaVentaHasta + "]");}
			
			codSocio = StringUtils.isNotBlank(codSocio) ? codSocio : null;
			numPlaca = StringUtils.isNotBlank(numPlaca) ? numPlaca : null;
			numCertificado = StringUtils.isNotBlank(numCertificado) ? numCertificado : null;
			numDocumentoID = StringUtils.isNotBlank(numDocumentoID) ? numDocumentoID : null;
			nombreContratante = StringUtils.isNotBlank(nombreContratante) ? nombreContratante : null;
			fechaVentaDesde = (null != fechaVentaDesde ? fechaVentaDesde : SateliteUtil.getInitialCardifDate());
			fechaVentaHasta = (null != fechaVentaHasta ? fechaVentaHasta :  new Date());
			
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + codSocio + ", 2.-" + numPlaca + ", 3.-" + numCertificado + ", 4.-" + numDocumentoID + ", 5.-" + nombreContratante + ", 6.-" + fechaVentaDesde + ", 7.-" + fechaVentaHasta + "]");}
			
			
			listaPolizasSOAT = conciTramaDiariaMapper.selectConsultaPolizasSOAT(codSocio, numPlaca, numCertificado, numDocumentoID, nombreContratante, fechaVentaDesde, fechaVentaHasta);
			int size = (null != listaPolizasSOAT ? listaPolizasSOAT.size() : 0);
			if (logger.isInfoEnabled()) {logger.info("Output [Registros: " + size + "]");}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
		}
		if (logger.isDebugEnabled()) {logger.debug("Fin");}
		return listaPolizasSOAT;
	} //buscarPolizasSOAT
	
} //RepConsultaSOATServiceImpl
