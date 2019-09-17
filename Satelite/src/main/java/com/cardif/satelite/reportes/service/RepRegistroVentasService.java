package com.cardif.satelite.reportes.service;

import java.util.Date;
import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.reportes.DetalleReporteRegVenta;
import com.cardif.satelite.reportes.bean.RepRegistroVentaBean;

public interface RepRegistroVentasService
{
	
	public abstract List<RepRegistroVentaBean> buscarPolizasRegistroVentas(Long codProducto, Date fecEmisionDesde, Date fecEmisionHasta) throws SyncconException;
	
	public abstract DetalleReporteRegVenta registrarNuevaVenta(Long codProducto, Date fecEmision, String tipoDocumento, String nroDocumento, String nomRazonSocial, 
			Double importeTotal, String tipoComprobante) throws SyncconException;
	
	
} //RepRegistroVentasService
