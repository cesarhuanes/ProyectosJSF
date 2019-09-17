package com.cardif.satelite.tesoreria.service;

import java.util.Date;
import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.tesoreria.bean.ComprobanteElectronicoBean;
import com.cardif.satelite.tesoreria.bean.ComprobanteRelacionadoBean;
import com.cardif.satelite.tesoreria.model.ComprobanteElectronico;

public interface ComprobanteRetencionService
{
	
	public Boolean anularRetencion(String unidadNegocio, ComprobanteElectronico comprobanteElectronicoBean) throws SyncconException;
	
	public List<ComprobanteRelacionadoBean> buscarDetalleRetenciones(String unidadNegocio, Date fechaEmision, String codProveedor, String rucProveedor, String nroComprobanteRetencion) throws SyncconException;
	
	public List<ComprobanteElectronicoBean> buscarRetenciones(String unidadNegocio, Date fechaDesde, Date fechaHasta, String proveedorDesde, String proveedorHasta, String rucProveedor, String nroComprobanteRetencion) throws SyncconException;
	
	public List<ComprobanteElectronicoBean> buscarRetencionesEmitidas(String unidadNegocio, String nroDiario, String nroComprobanteRetencion, Date fechaDesde, Date fechaHasta, String proveedor, String rucProveedor);
	
	public String generaTramaRetencion(String unidadNegocio, ComprobanteElectronicoBean comprobanteElectronicoBean) throws SyncconException;
	
	public String generaTramaReversion(String unidadNegocio, List<ComprobanteElectronicoBean> listComprobanteElectronicoBean) throws SyncconException;
	
	public boolean actualizarEstadoSunsystems(String nroCertificado, String nroDiario, String unidadNegocio, String nuevoEstado) throws SyncconException;
	
	public boolean actualizarEstadoSatelite(String nroCertificado, String nuevoEstado, String mensajeRespuesta) throws SyncconException;
	
	public boolean insertarComprobanteElectronico(ComprobanteElectronicoBean bean, String unidadNegocio, String estado, String mensajePPL, String usuario) throws SyncconException;
	
	public List<ComprobanteElectronico> buscarRetencionesEmitidasAnuladas(String unidadNegocio, String nroDiario, Date fechaDesde,	Date fechaHasta, String rucProveedor, String nroComprobanteRetencion)throws SyncconException;
	
}
