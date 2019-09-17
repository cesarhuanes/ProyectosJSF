package com.cardif.satelite.reportes.dao;

import com.cardif.satelite.model.reportes.DetalleReporteLiquidacion;

public interface DetalleReporteLiquidacionMapper
{
	
	public DetalleReporteLiquidacion selectByPrimaryKey(Long id);
	
	public DetalleReporteLiquidacion selectByDetalleTramaDiaria(Long pkDetalleTramaDiaria);
	
	public int deleteByPrimaryKey(Long id);
	
} //DetalleReporteLiquidacionesMapper
