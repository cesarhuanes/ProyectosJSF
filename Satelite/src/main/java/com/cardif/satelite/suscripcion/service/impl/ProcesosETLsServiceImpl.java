package com.cardif.satelite.suscripcion.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.constantes.Constantes;
import com.cardif.satelite.ssis.dao.SsisMapper;
import com.cardif.satelite.suscripcion.service.ProcesosETLsService;

@Service("ProcesosETLsService")
public class ProcesosETLsServiceImpl implements ProcesosETLsService {

	private static final Logger logger = Logger.getLogger(ProcesosETLsServiceImpl.class);
	
	@Autowired
	private SsisMapper ssisMapper;

	@Override
	public boolean procesosMarcaModelo() throws SyncconException {

		Boolean status = false;
		
		logger.info("inicio");
		
		try {
			
			ssisMapper.ejecutarJob(Constantes.SUSCRIPCION_JOB_MARCA_MODELO);

			logger.info("SUSCRIPCION_JOB_MARCA_MODELO");
			
		} catch (Exception e) {
			logger.info(e.getMessage());
			logger.info(e.getStackTrace());
			}
		
		

		logger.info("fin");
		return status;
		
		
		
	}

	@Override
	public boolean procesosCargaMaster() throws SyncconException {
	Boolean status = false;
		
		logger.info("inicio");
		
		try {
			
			ssisMapper.ejecutarJob(Constantes.SUSCRIPCION_JOB_CARGA_MASTER);

			logger.info("JOB_SOAT_Carga_Master");
			
		} catch (Exception e) {
			logger.info(e.getMessage());
			logger.info(e.getStackTrace());
			}
		
		

		logger.info("fin");
		return status;
	}

	@Override
	public boolean procesosTramaDiaria() throws SyncconException {
	Boolean status = false;
		
		logger.info("inicio");
		
		try {
			
			ssisMapper.ejecutarJob(Constantes.SUSCRIPCION_JOB_CARGA_DIARIA);

			logger.info("JOB_SOAT_Trama_Diaria");
			
		} catch (Exception e) {
			logger.info(e.getMessage());
			logger.info(e.getStackTrace());
			}
		
		

		logger.info("fin");
		return status;
	}

	@Override
	public boolean procesosTramaMensual() throws SyncconException {
	Boolean status = false;
		
		logger.info("inicio");
		
		try {
			
			ssisMapper.ejecutarJob(Constantes.SUSCRIPCION_JOB_CARGA_MENSUAL);

			logger.info("JOB_SOAT_Trama_Mensual");
			
		} catch (Exception e) {
			logger.info(e.getMessage());
			logger.info(e.getStackTrace());
			}
		
		

		logger.info("fin");
		return status;
	}

	@Override
	public boolean procesosCargaConciliacionTrama() throws SyncconException {
		Boolean status = false;
		
		logger.info("inicio");
		
		try {
			
			ssisMapper.ejecutarJob(Constantes.SUSCRIPCION_JOB_CARGA_CONCILIACION_TRAMA);

			logger.info("JOB_SOAT_Conciliacion_Trama");
			
		} catch (Exception e) {
			logger.info(e.getMessage());
			logger.info(e.getStackTrace());
			}
		
		

		logger.info("fin");
		return status;
	}

	@Override
	public boolean procesosCargaConciliacion() throws SyncconException {
Boolean status = false;
		
		logger.info("inicio");
		
		try {
			
			ssisMapper.ejecutarJob(Constantes.SUSCRIPCION_JOB_CARGA_CONCILIACION);

			logger.info("JOB_SOAT_Conciliacion");
			
		} catch (Exception e) {
			logger.info(e.getMessage());
			logger.info(e.getStackTrace());
			}
		
		

		logger.info("fin");
		return status;
	}

	
	
	
}
