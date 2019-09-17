package com.cardif.satelite.reportesbs.service;

import java.util.List;

import com.cardif.satelite.model.reportesbs.LogErroresReportes;

public interface LogErroresReportesService {

	List<LogErroresReportes> selectListErroresReporteByPrimaryKey(
			String codigoReporte);

	void grabarErrorLog(String usuarioLog, long codReporte,
			String nombreReporte, String glosa, String bo_planilla,
			String descripcion);

	int eliminarLogByCodigo(String codigoReporte);

}
