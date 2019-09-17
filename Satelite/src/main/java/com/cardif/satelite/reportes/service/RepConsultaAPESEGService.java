package com.cardif.satelite.reportes.service;

import java.util.Date;
import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.reportes.DetalleReporteAPESEG;
import com.cardif.satelite.reportes.bean.RepConsultaAPESEGBean;

public interface RepConsultaAPESEGService
{
	
	/**
	 * Este metodo a implementar buscará las registros de polizas en la tabla 'DETALLE_TRAMA_DIARIA' y 'DETALLE_REPORTE_APESEG', 
	 * según la fecha de registro (DESDE - HASTA).
	 * 
	 * @param fecRegistroDesde
	 * 		Fecha inicial de registro para la busqueda de las polizas.
	 * @param fecRegistroHasta
	 * 		Fecha final de registro para la busqueda de las polizas.
	 * @return
	 * 		Retorna los registros de polizas que cumplan con los parametros de fecha de registro.
	 * @throws SyncconException
	 */
	public List<RepConsultaAPESEGBean> buscarPolizasParaAPESEG(Date fecRegistroDesde, Date fecRegistroHasta) throws SyncconException;
	
	public DetalleReporteAPESEG actualizarReportadoAPESEG(Long idReporteApeseg, String reportadoOpcion) throws SyncconException;
	
} //RepConsultaAPESEGService
