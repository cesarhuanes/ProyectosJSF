package com.cardif.satelite.reportesbs.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.satelite.model.reportesbs.LogErroresReportes;
import com.cardif.satelite.reportesbs.dao.LogErroresReportesMapper;
import com.cardif.satelite.reportesbs.service.LogErroresReportesService;

@Service("logErroresReportesService")
public class LogErroresReportesServiceImpl implements LogErroresReportesService {

	public static final Logger log = Logger
			.getLogger(LogErroresReportesServiceImpl.class);

	@Autowired
	private LogErroresReportesMapper logErroresReportesMapper;

	@Override
	public List<LogErroresReportes> selectListErroresReporteByPrimaryKey(
			String codigoReporte) {
		List<LogErroresReportes> lista = null;

		if (log.isInfoEnabled()) {
			log.info("Inicio");
		}

		try {

			lista = logErroresReportesMapper
					.selectListErroresReporteByPrimaryKey(codigoReporte);

		} catch (Exception e) {

			log.error("Exception(" + e.getClass().getName() + ") - ERROR: "
					+ e.getMessage());
			log.error("Exception(" + e.getClass().getName() + ") -->"
					+ ExceptionUtils.getStackTrace(e));

		}

		if (log.isInfoEnabled()) {
			log.info("Inicio");
		}
		return lista;
	}

	@Override
	public void grabarErrorLog(String usuarioLog, long codReporte,
			String nombreReporte, String glosa, String bo_planilla,
			String descripcion) {

		int cont = 0;

		if (log.isInfoEnabled()) {
			log.info("Inicio");
		}

		try {

			LogErroresReportes logErroresReportesBean = new LogErroresReportes();

			logErroresReportesBean.setBoPlanilla(bo_planilla);
			// logErroresReportesBean.setCodLogErrores(codLogErrores);
			logErroresReportesBean.setDescripcion(descripcion);
			logErroresReportesBean.setFechaCreado(new Date());
			logErroresReportesBean.setGlosa(glosa);
			logErroresReportesBean.setNomReporte(nombreReporte);
			logErroresReportesBean.setUsuarioCreador(usuarioLog);
			logErroresReportesBean.setCodReporte(codReporte);

			cont = logErroresReportesMapper.insert(logErroresReportesBean);

		} catch (Exception e) {

			log.error("Exception(" + e.getClass().getName() + ") - ERROR: "
					+ e.getMessage());
			log.error("Exception(" + e.getClass().getName() + ") -->"
					+ ExceptionUtils.getStackTrace(e));

		}

		if (log.isInfoEnabled()) {
			log.info("Inicio");
		}

	}

	@Override
	public int eliminarLogByCodigo(String codigoReporte) {
			
		int respuesta = 0;
		int cont = 0;
		
		try {
					
		cont = logErroresReportesMapper.deleteByCodReporte(Long.parseLong(codigoReporte));	
		
		} catch (Exception e) {
			
			respuesta = 1;
			
			log.error("Exception(" + e.getClass().getName() + ") - ERROR: "
					+ e.getMessage());
			log.error("Exception(" + e.getClass().getName() + ") -->"
					+ ExceptionUtils.getStackTrace(e));
			
		}
		
		return respuesta;
	}

}
