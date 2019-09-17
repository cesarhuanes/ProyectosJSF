package com.cardif.satelite.moduloimpresion.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.moduloimpresion.service.ActualizarSociosService;
import com.cardif.satelite.soat.cencosud.dao.SocioDigitalesCencosudMapper;
import com.cardif.satelite.soat.comparabien.dao.SocioDigitalesComparaBienMapper;
import com.cardif.satelite.soat.directo.dao.SocioDigitalesDirectoMapper;
import com.cardif.satelite.soat.falabella.dao.SocioDigitalesFalabellaMapper;
import com.cardif.satelite.soat.model.SocioDigitales;
import com.cardif.satelite.soat.ripley.dao.SocioDigitalesRipleyMapper;
import com.sun.org.apache.commons.beanutils.BeanUtils;

@Service("ActualizarSociosService")
public class ActualizarSociosServiceImpl implements ActualizarSociosService {

	@Autowired
	private SocioDigitalesCencosudMapper socioDigitalesCencosudMapper=null;
	
	@Autowired
	private SocioDigitalesComparaBienMapper socioDigitalesComparaBienMapper = null;

	@Autowired
	private SocioDigitalesFalabellaMapper socioDigitalesFalabellaMapper = null;
	
	@Autowired
	private SocioDigitalesDirectoMapper socioDigitalesDirectoMapper = null;
	
	@Autowired
	private SocioDigitalesRipleyMapper socioDigitalesRipleyMapper = null; 
	
	private final Logger logger = Logger.getLogger(BuscarLoteServiceImpl.class);

	
	@Override
	public Boolean actualizarSocioFalabella(SocioDigitales socio) throws SyncconException {
		Boolean sw = false;
		
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		
		try {
			logger.debug("Input [ "+ BeanUtils.describe(socio)  +" ]");
			
			socioDigitalesFalabellaMapper.updateByNroPoliza(socio);
			
			logger.debug("Output [ 1.- ]");
		} catch (Exception e){
					logger.error(e.getMessage(),e);
					throw new SyncconException(ErrorConstants.COD_SOCIODIGITAL_ERROR_UPDATE);
		} 	
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		
		return sw;
	}

	@Override
	public Boolean actualizarSocioRipley(SocioDigitales socio)	throws SyncconException {
		Boolean sw = false;
		
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		
		try {
			logger.debug("Input [ "+ BeanUtils.describe(socio)  +" ]");
		
			socioDigitalesRipleyMapper.updateByNroPoliza(socio);
			
			logger.debug("Output [ 1.- ]");
		} catch (Exception e){
					logger.error(e.getMessage(),e);
					throw new SyncconException(ErrorConstants.COD_SOCIODIGITAL_ERROR_UPDATE);
		} 	
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		
		return sw;
	}

	@Override
	public Boolean actualizarSocioComparaBien(SocioDigitales socio) throws SyncconException {
		Boolean sw = false;
		
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		
		try {
			logger.debug("Input [ "+ BeanUtils.describe(socio)  +" ]");
		
			socioDigitalesComparaBienMapper.updateByNroPoliza(socio);
			
			logger.debug("Output [ 1.- ]");
		} catch (Exception e){
					logger.error(e.getMessage(),e);
					throw new SyncconException(ErrorConstants.COD_SOCIODIGITAL_ERROR_UPDATE);
		} 	
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		
		return sw;
	}

	@Override
	public Boolean actualizarSocioCencosud(SocioDigitales socio) throws SyncconException {
		Boolean sw = false;
		
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		
		try {
			logger.debug("Input [ "+ BeanUtils.describe(socio)  +" ]");
		
			socioDigitalesCencosudMapper.updateByNroPoliza(socio);
			
			logger.debug("Output [ 1.- ]");
		} catch (Exception e){
					logger.error(e.getMessage(),e);
					throw new SyncconException(ErrorConstants.COD_SOCIODIGITAL_ERROR_UPDATE);
		} 	
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		
		return sw;
	}

	@Override
	public Boolean actualizarSocioDirecto(SocioDigitales socio) throws SyncconException {
		
		Boolean sw = false;
		
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		
		try {
			logger.debug("Input [ "+ BeanUtils.describe(socio)  +" ]");
		
			socioDigitalesDirectoMapper.updateByNroPoliza(socio);
			
			logger.debug("Output [ 1.- ]");
		} catch (Exception e){
					logger.error(e.getMessage(),e);
					throw new SyncconException(ErrorConstants.COD_SOCIODIGITAL_ERROR_UPDATE);
		} 	
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		
		return sw;
	}
	
	

}
