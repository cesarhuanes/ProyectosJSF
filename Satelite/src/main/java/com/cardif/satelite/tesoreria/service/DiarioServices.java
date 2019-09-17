package com.cardif.satelite.tesoreria.service;

import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.tesoreria.bean.JournalBean;

public interface DiarioServices
{
	
	public List<JournalBean> buscarRetenciones(String unidadNegocio, String fechaDesde, String fechaHasta, String proveedorDesde, String proveedorHasta, String rucProveedor, String nroComprobante) throws SyncconException;
  	
  	public List<JournalBean> buscarDetalleRetenciones(String unidadNegocio, String fechaEmision, String nroComprobante) throws SyncconException;
  	
  	public List<JournalBean> buscarRetencionesEmitidas(String unidadNegocio, String nroDiario, String fechaDesde, String fechaHasta, String proveedorDesde, String proveedorHasta, String rucProveedor, String nroComprobanteRetencion) throws SyncconException;
  	
  	public JournalBean buscarProvision(String unidadNegocio, String proveedor, String rucProveedor, String nroComprobante) throws SyncconException;
  	
  	public boolean actualizaAsientoPagoRetencion(String unidadNegocio, String nroDiario, String estadoAsiento, String nroCertificado) throws SyncconException;
  	
}
